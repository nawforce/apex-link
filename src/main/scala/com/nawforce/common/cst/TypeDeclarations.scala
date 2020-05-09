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

import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.org.PackageImpl
import com.nawforce.common.types.apex.{ApexVisibleMethodLike, FullDeclaration}
import com.nawforce.common.types.core.{CLASS_NATURE, ENUM_NATURE, INTERFACE_NATURE, Nature, TypeId}
import com.nawforce.common.types.synthetic.{CustomMethodDeclaration, CustomParameterDeclaration}
import com.nawforce.runtime.parsers.ApexParser._
import com.nawforce.runtime.parsers.{CodeParser, Source}

class CompilationUnit(val typeDeclaration: FullDeclaration) extends CST

object CompilationUnit {
  def construct(parser: CodeParser, pkg: PackageImpl,  compilationUnit: CompilationUnitContext)
      : CompilationUnit = {
    CST.sourceContext.withValue(Some(parser.source)) {
      new CompilationUnit(FullDeclaration.construct(parser, pkg, None, compilationUnit.typeDeclaration()))
        .withContext(compilationUnit)
    }
  }
}

final case class ClassDeclaration(_source: Source, _pkg: PackageImpl, _outerTypeName: Option[TypeName], _id: Id,
                                  _modifiers: ModifierResults, _extendsType: Option[TypeName], _implementsTypes: Seq[TypeName],
                                  _bodyDeclarations: Seq[ClassBodyDeclaration]) extends
  FullDeclaration(_source, _pkg, _outerTypeName, _id, _modifiers, _extendsType, _implementsTypes, _bodyDeclarations) {

  override val nature: Nature = CLASS_NATURE

  override def verify(context: TypeVerifyContext): Unit = {
    verifyCommon(context)
    super.verify(context)
  }

  override def verify(context: BodyDeclarationVerifyContext): Unit = {
    verifyCommon(context)
    super.verify(new TypeVerifyContext(Some(context), this, context.shouldPropagateDependencies))
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
  def construct(parser: CodeParser, pkg: PackageImpl, outerTypeName: Option[TypeName], modifiers: ModifierResults,
                classDeclaration: ClassDeclarationContext): ClassDeclaration = {

    val thisType = TypeName(Name(CodeParser.getText(classDeclaration.id())), Nil,
      outerTypeName.orElse(pkg.namespace.map(TypeName(_))))
    val extendType =
      CodeParser.toScala(classDeclaration.typeRef())
        .map(tr => TypeReference.construct(tr))
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
            .map(x => Seq(ApexInitialiserBlock.construct(parser,
                ModifierResults(CodeParser.toScala(cbd.STATIC()).map(_ => Seq(STATIC_MODIFIER)).getOrElse(Seq()), Nil), x)))
          .orElse(CodeParser.toScala(cbd.memberDeclaration())
            .map(x => ClassBodyDeclaration.construct(parser, pkg, thisType, CodeParser.toScala(cbd.modifier()), x))
          )
          .orElse(throw new CSTException())
        ).flatten

    ClassDeclaration(parser.source, pkg, outerTypeName, Id.construct(classDeclaration.id()), modifiers,
      Some(extendType),implementsType, bodyDeclarations).withContext(classDeclaration)
  }
}

final case class InterfaceDeclaration(_source: Source, _pkg: PackageImpl, _outerTypeName: Option[TypeName],
                                      _id: Id, _modifiers: ModifierResults, _implementsTypes: Seq[TypeName],
                                      _bodyDeclarations: Seq[ClassBodyDeclaration])
  extends FullDeclaration(_source, _pkg, _outerTypeName, _id, _modifiers, None, _implementsTypes, _bodyDeclarations) {

  override val nature: Nature = INTERFACE_NATURE

  override def verify(context: BodyDeclarationVerifyContext): Unit = {
    super.verify(new TypeVerifyContext(Some(context), this, context.shouldPropagateDependencies))
  }
}

object InterfaceDeclaration {
  def construct(parser: CodeParser, pkg: PackageImpl, outerTypeName: Option[TypeName], modifiers: ModifierResults,
                interfaceDeclaration: InterfaceDeclarationContext)
  : InterfaceDeclaration = {
    val thisType = TypeName(Name(CodeParser.getText(interfaceDeclaration.id())), Nil,
      outerTypeName.orElse(pkg.namespace.map(TypeName(_))))

    val implementsType =
      CodeParser.toScala(interfaceDeclaration.typeList())
        .map(x => TypeList.construct(x))
        .getOrElse(Seq())

    val methods: Seq[ApexMethodDeclaration]
        = CodeParser.toScala(interfaceDeclaration.interfaceBody().interfaceMethodDeclaration()).map(m =>
            ApexMethodDeclaration.construct(parser, pkg, TypeId(pkg, thisType),
              ApexModifiers.methodModifiers(parser, CodeParser.toScala(m.modifier()), m.id()), m)
    )

    InterfaceDeclaration(parser.source, pkg, outerTypeName, Id.construct(interfaceDeclaration.id()), modifiers,
      implementsType, methods).withContext(interfaceDeclaration)
  }
}

final case class EnumDeclaration(_source: Source, _pkg: PackageImpl, _outerTypeName: Option[TypeName], _id: Id,
                                 _modifiers:ModifierResults, _bodyDeclarations: Seq[ClassBodyDeclaration])
  extends FullDeclaration(_source, _pkg, _outerTypeName, _id, _modifiers, None, Seq(), _bodyDeclarations) {

  override val nature: Nature = ENUM_NATURE

  override def verify(context: BodyDeclarationVerifyContext): Unit = {
    super.verify(new TypeVerifyContext(Some(context), this, context.shouldPropagateDependencies))
  }

  override lazy val localMethods: Seq[ApexVisibleMethodLike] =
    Seq(
      CustomMethodDeclaration(Some(id.location), Name("name"), TypeName.String, Seq()),
      CustomMethodDeclaration(Some(id.location),Name("ordinal"), TypeName.Integer, Seq()),
      CustomMethodDeclaration(Some(id.location),Name("values"), TypeName.listOf(typeName), Seq(), asStatic = true),
      CustomMethodDeclaration(Some(id.location),Name("equals"), TypeName.Boolean, Seq(
        CustomParameterDeclaration(Name("other"), TypeName.InternalObject))),
      CustomMethodDeclaration(Some(id.location),Name("hashCode"), TypeName.Integer, Seq())
    )
}

object EnumDeclaration {
  def construct(parser: CodeParser, pkg: PackageImpl, outerTypeName: Option[TypeName], typeModifiers: ModifierResults,
                enumDeclaration: EnumDeclarationContext): EnumDeclaration = {

    // FUTURE: Add standard enum methods

    val id = Id.construct(enumDeclaration.id())
    val thisType = TypeName(id.name, Nil,
      outerTypeName.orElse(pkg.namespace.map(TypeName(_)))
    )
    val constants = CodeParser.toScala(enumDeclaration.enumConstants())
      .map(ec => CodeParser.toScala(ec.id())).getOrElse(Seq())
    val fields = constants.map(constant => {
      ApexFieldDeclaration(TypeId(pkg, thisType), ModifierResults(Seq(PUBLIC_MODIFIER, STATIC_MODIFIER), Nil), thisType,
        VariableDeclarator(
          thisType,
          Id.construct(constant),
          None
        ).withContext(constant)
      ).withContext(constant)
    })

    EnumDeclaration(parser.source, pkg, outerTypeName,id, typeModifiers, fields).withContext(enumDeclaration)
  }
}
