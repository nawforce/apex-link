/*
 [The "BSD licence"]
 Copyright (c) 2017 Kevin Jones
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
    derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.nawforce.common.cst

import com.nawforce.common.documents.Source
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.org.PackageImpl
import com.nawforce.common.path.PathLike
import com.nawforce.common.types._
import com.nawforce.common.types.apex.FullDeclaration
import com.nawforce.runtime.parsers.ApexParser._
import com.nawforce.runtime.parsers.CodeParser

final case class CompilationUnit(path: PathLike, private val _typeDeclaration: FullDeclaration) extends CST {
  def children(): List[CST] = List(_typeDeclaration)

  def typeDeclaration(): FullDeclaration = {
    _typeDeclaration
  }
}

object CompilationUnit {
  def construct(source: Source, pkg: PackageImpl, path: PathLike, compilationUnit: CompilationUnitContext,
                context: ConstructContext): CompilationUnit = {
    CompilationUnit(path,
      FullDeclaration.construct(source, pkg, None, compilationUnit.typeDeclaration(), context))
      .withContext(compilationUnit, context)
  }
}

final case class ClassDeclaration(_source: Source, _pkg: PackageImpl, _outerTypeName: Option[TypeName], _id: Id,
                                  _modifiers: Seq[Modifier], _extendsType: Option[TypeName], _implementsTypes: Seq[TypeName],
                                  _bodyDeclarations: Seq[ClassBodyDeclaration]) extends
  FullDeclaration(_source, _pkg, _outerTypeName, _id, _modifiers, _extendsType, _implementsTypes, _bodyDeclarations) {

  override val nature: Nature = CLASS_NATURE

  override def verify(context: TypeVerifyContext): Unit = {
    verifyCommon(context)
    super.verify(context)
  }

  override def verify(context: BodyDeclarationVerifyContext): Unit = {
    verifyCommon(context)
    super.verify(new TypeVerifyContext(Some(context), this))
  }

  private def verifyCommon(context: VerifyContext): Unit = {
    if (bodyDeclarations.exists(_.isGlobal) && !modifiers.contains(GLOBAL_MODIFIER)) {
      context.logError(id.location, "Classes enclosing globals or webservices must also be declared global")
    } else if (!modifiers.contains(ABSTRACT_MODIFIER) && methods.exists(_.isAbstract)) {
      context.logError(id.location, "Classes with abstract methods must be abstract")
    } else if(modifiers.contains(ABSTRACT_MODIFIER) && modifiers.contains(VIRTUAL_MODIFIER)) {
      context.logError(id.location, "Abstract classes do not need virtual keyword")
    }
  }
}

object ClassDeclaration {
  def construct(source: Source, pkg: PackageImpl, outerTypeName: Option[TypeName], modifiers: Seq[Modifier],
                classDeclaration: ClassDeclarationContext, context: ConstructContext): ClassDeclaration = {

    val thisType = TypeName(Name(CodeParser.getText(classDeclaration.id())), Nil,
      outerTypeName.orElse(pkg.namespace.map(TypeName(_))))
    val extendType =
      CodeParser.toScala(classDeclaration.typeRef())
        .map(tr => TypeRef.construct(tr))
        .getOrElse(TypeName.InternalObject)
    val implementsType =
      CodeParser.toScala(classDeclaration.typeList())
        .map(tl => TypeList.construct(tl))
        .getOrElse(Seq())

    val classBody = classDeclaration.classBody()
    val classBodyDeclarations: Seq[ClassBodyDeclarationContext] = CodeParser.toScala(classBody.classBodyDeclaration())
    val bodyDeclarations: Seq[ClassBodyDeclaration] =
        classBodyDeclarations.flatMap(cbd =>
          CodeParser.toScala(cbd.block())
            .map(x => Seq(ApexInitialiserBlock.construct(
                CodeParser.toScala(cbd.STATIC()).map(_ => Seq(STATIC_MODIFIER)).getOrElse(Seq()),
              x, context)))
          .orElse(CodeParser.toScala(cbd.memberDeclaration())
            .map(x => ClassBodyDeclaration.construct(pkg, thisType, source, CodeParser.toScala(cbd.modifier()), x, context))
          )
          .orElse(throw new CSTException())
        ).flatten

    ClassDeclaration(source, pkg, outerTypeName, Id.construct(classDeclaration.id(), context), modifiers,
      Some(extendType),implementsType, bodyDeclarations).withContext(classDeclaration, context)
  }
}

final case class InterfaceDeclaration(_source: Source, _pkg: PackageImpl, _outerTypeName: Option[TypeName],
                                      _id: Id, _modifiers: Seq[Modifier], _implementsTypes: Seq[TypeName],
                                      _bodyDeclarations: Seq[ClassBodyDeclaration])
  extends FullDeclaration(_source, _pkg, _outerTypeName, _id, _modifiers, None, _implementsTypes, _bodyDeclarations) {

  override val nature: Nature = INTERFACE_NATURE

  override def verify(context: BodyDeclarationVerifyContext): Unit = {
    super.verify(new TypeVerifyContext(Some(context), this))
  }
}

object InterfaceDeclaration {
  def construct(source: Source, pkg: PackageImpl, outerTypeName: Option[TypeName], modifiers: Seq[Modifier],
                interfaceDeclaration: InterfaceDeclarationContext, context: ConstructContext)
  : InterfaceDeclaration = {
    val thisType = TypeName(Name(CodeParser.getText(interfaceDeclaration.id())), Nil,
      outerTypeName.orElse(pkg.namespace.map(TypeName(_))))

    val implementsType =
      CodeParser.toScala(interfaceDeclaration.typeList())
        .map(x => TypeList.construct(x))
        .getOrElse(Seq())

    val methods: Seq[ApexMethodDeclaration]
        = CodeParser.toScala(interfaceDeclaration.interfaceBody().interfaceMethodDeclaration()).map(m =>
            ApexMethodDeclaration.construct(pkg, thisType,
              ApexModifiers.methodModifiers(CodeParser.toScala(m.modifier()), context, m.id()), m, context)
    )

    InterfaceDeclaration(source, pkg, outerTypeName, Id.construct(interfaceDeclaration.id(), context), modifiers,
      implementsType, methods).withContext(interfaceDeclaration, context)
  }
}

final case class EnumDeclaration(_source: Source, _pkg: PackageImpl, _outerTypeName: Option[TypeName], _id: Id,
                                 _modifiers: Seq[Modifier], _bodyDeclarations: Seq[ClassBodyDeclaration])
  extends FullDeclaration(_source, _pkg, _outerTypeName, _id, _modifiers, None, Seq(), _bodyDeclarations) {

  override val nature: Nature = ENUM_NATURE

  override def verify(context: BodyDeclarationVerifyContext): Unit = {
    super.verify(new TypeVerifyContext(Some(context), this))
  }

  override lazy val localMethods: Seq[MethodDeclaration] =
    Seq(
      CustomMethodDeclaration(Name("name"), TypeName.String, Seq()),
      CustomMethodDeclaration(Name("ordinal"), TypeName.Integer, Seq()),
      CustomMethodDeclaration(Name("values"), TypeName.listOf(typeName), Seq(), asStatic = true),
      CustomMethodDeclaration(Name("equals"), TypeName.Boolean, Seq(
        CustomParameterDeclaration(Name("other"), TypeName.InternalObject))),
      CustomMethodDeclaration(Name("hashCode"), TypeName.Integer, Seq())
    )
}

object EnumDeclaration {
  def construct(source: Source, pkg: PackageImpl, outerTypeName: Option[TypeName], typeModifiers: Seq[Modifier],
                enumDeclaration: EnumDeclarationContext, context: ConstructContext): EnumDeclaration = {

    // FUTURE: Add standard enum methods

    val id = Id.construct(enumDeclaration.id(), context)
    val thisType = TypeName(id.name, Nil,
      outerTypeName.orElse(pkg.namespace.map(TypeName(_)))
    )
    val constants = CodeParser.toScala(enumDeclaration.enumConstants())
      .map(ec => CodeParser.toScala(ec.id())).getOrElse(Seq())
    val fields = constants.map(constant => {
      ApexFieldDeclaration(thisType, Seq(PUBLIC_MODIFIER, STATIC_MODIFIER), thisType,
        VariableDeclarator(
          thisType,
          Id.construct(constant, context),
          None
        ).withContext(constant, context)
      ).withContext(constant, context)
    })

    EnumDeclaration(source, pkg, outerTypeName,id, typeModifiers, fields).withContext(enumDeclaration, context)
  }
}
