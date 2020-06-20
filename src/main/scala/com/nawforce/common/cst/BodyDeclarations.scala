/*
 [The "BSD licence"]
 Copyright (c) 2019 Kevin Jones
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

import com.nawforce.common.api.{Name, TypeName}
import com.nawforce.common.diagnostics.Issue
import com.nawforce.common.documents.RangeLocationImpl
import com.nawforce.common.finding.RelativeTypeName
import com.nawforce.common.names.TypeNames
import com.nawforce.common.org.{OrgImpl, PackageImpl}
import com.nawforce.common.types.apex.{ApexBlockLike, ApexConstructorLike, ApexFieldLike, ApexMethodLike}
import com.nawforce.common.types.core._
import com.nawforce.runtime.gc.SkinnySet
import com.nawforce.runtime.parsers.ApexParser._
import com.nawforce.runtime.parsers.CodeParser

import scala.collection.mutable

abstract class ClassBodyDeclaration(modifierResults: ModifierResults) extends CST with DependencyHolder {
  val modifiers: Array[Modifier] = modifierResults.modifiers
  def modifierIssues: Array[Issue] = modifierResults.issues
  lazy val isGlobal: Boolean = modifiers.contains(GLOBAL_MODIFIER) || modifiers.contains(WEBSERVICE_MODIFIER)

  protected var depends: Option[SkinnySet[Dependent]] = None

  override def dependencies(): Iterable[Dependent] = {
    depends.map(_.toIterable).getOrElse(Array().toIterable)
  }

  def setDepends(dependencies: SkinnySet[Dependent]): Unit = {
    if (dependencies.isEmpty)
      depends = None
    else
      depends = Some(dependencies)
  }

  def collectDependencies(dependsOn: mutable.Set[Dependent]): Unit = {
    dependencies().foreach(dependsOn.add)
  }

  def validate(context: BodyDeclarationVerifyContext): Unit = {
    modifierIssues.foreach(context.log)
    verify(context)
  }

  protected def verify(context: BodyDeclarationVerifyContext): Unit
}

object ClassBodyDeclaration {
  def construct(parser: CodeParser, pkg: PackageImpl, outerTypeName: TypeName,
                modifiers: Seq[ModifierContext], memberDeclarationContext: MemberDeclarationContext)
  : Seq[ClassBodyDeclaration] = {

    val outerTypeId = TypeId(pkg, outerTypeName)
    val declarations: Option[Seq[ClassBodyDeclaration]] =
      CodeParser.toScala(memberDeclarationContext.methodDeclaration())
        .map(x => Seq(ApexMethodDeclaration.construct(parser, pkg, outerTypeId,
          ApexModifiers.methodModifiers(parser, modifiers, x.id()),
          x)))
      .orElse(CodeParser.toScala(memberDeclarationContext.fieldDeclaration())
        .map(x => ApexFieldDeclaration.construct(outerTypeId,
          ApexModifiers.fieldModifiers(parser, modifiers,
            CodeParser.toScala(x.variableDeclarators().variableDeclarator()).head.id()),
          x)))
      .orElse(CodeParser.toScala(memberDeclarationContext.constructorDeclaration())
        .map(x => Seq(ApexConstructorDeclaration.construct(parser, pkg, outerTypeName,
          ApexModifiers.constructorModifiers(parser, modifiers, x),
          x))))
      .orElse(CodeParser.toScala(memberDeclarationContext.interfaceDeclaration())
        .map(x => Seq(InterfaceDeclaration.construct(parser, pkg, Some(outerTypeName),
          ApexModifiers.interfaceModifiers(parser, modifiers, outer = false, x.id()),
          x))))
      .orElse(CodeParser.toScala(memberDeclarationContext.enumDeclaration())
        .map(x => Seq(EnumDeclaration.construct(parser, pkg, Some(outerTypeName),
          ApexModifiers.enumModifiers(parser, modifiers, outer = false, x.id()),
          x))))
      .orElse(CodeParser.toScala(memberDeclarationContext.propertyDeclaration())
        .map(x => Seq(ApexPropertyDeclaration.construct(parser, outerTypeId,
          ApexModifiers.fieldModifiers(parser, modifiers, x.id()),
          x))))
      .orElse(CodeParser.toScala(memberDeclarationContext.classDeclaration())
        .map(x => Seq(ClassDeclaration.construct(parser, pkg, Some(outerTypeName),
          ApexModifiers.classModifiers(parser, modifiers, outer = false, x.id()),
          x))))

    if (declarations.isEmpty)
      throw new CSTException()
    else
      declarations.get.map(_.withContext(memberDeclarationContext))
  }
}

final case class ApexInitialiserBlock(_modifiers: ModifierResults, block: Block)
  extends ClassBodyDeclaration(_modifiers) with ApexBlockLike {

  override val isStatic: Boolean = modifiers.contains(STATIC_MODIFIER)

  override def verify(context: BodyDeclarationVerifyContext): Unit = {
    val blockContext = new OuterBlockVerifyContext(context, isStatic)
    block.verify(blockContext)
    setDepends(context.dependencies)
    context.propagateDependencies()
  }
}

object ApexInitialiserBlock {
  def construct(parser: CodeParser, modifiers: ModifierResults, block: BlockContext): ApexInitialiserBlock = {
    ApexInitialiserBlock(modifiers, Block.constructLazy(parser, block))
  }
}

final class ApexMethodDeclaration(override val outerTypeId: TypeId, _modifiers: ModifierResults,
                                  relativeTypeName: RelativeTypeName, id: Id,
                                  override val parameters: Array[ParameterDeclaration], val block: Option[Block])
  extends ClassBodyDeclaration(_modifiers) with ApexMethodLike {

  override def nameRange: RangeLocationImpl = id.location
  override val name: Name = id.name

  override lazy val typeName: TypeName = relativeTypeName.typeName

  /* All parameters are FormalParameters but we need to bypass Array being invariant */
  def formalParameters: Array[FormalParameter] = parameters.collect {case p: FormalParameter => p}

  override def verify(context: BodyDeclarationVerifyContext): Unit = {

    if (relativeTypeName.outerNature == CLASS_NATURE) {
      if (isAbstract && block.nonEmpty)
        context.logError(id.location, "Abstract methods can not have an implementation")
      else if (!isAbstract && block.isEmpty)
        context.logError(id.location, "Method must have an implementations or be marked abstract")
      else if (isAbstract && isVirtual)
        context.logError(id.location, "Abstract methods do not need virtual keyword")
    } else if (relativeTypeName.outerNature == INTERFACE_NATURE) {
      if (modifiers.nonEmpty)
        context.logError(id.location, s"Modifier '${modifiers.head.name}' is not supported on interface methods")
    }

    relativeTypeName.typeRequest match {
      case Some(Left(error)) => OrgImpl.log(error.asIssue(id.location))
      case Some(Right(td)) => context.addDependency(td)
      case _ => ()
    }

    formalParameters.foreach(_.verify(context))

    block.foreach(blk => {
      val blockContext = new OuterBlockVerifyContext(context, modifiers.contains(STATIC_MODIFIER))
      formalParameters.foreach(param => param.addVar(blockContext))
      blk.verify(blockContext)
    })

    setDepends(context.dependencies)
    context.propagateDependencies()
  }
}

object ApexMethodDeclaration {
  def construct(parser: CodeParser, pkg: PackageImpl, outerTypeId: TypeId, modifiers: ModifierResults,
                from: MethodDeclarationContext): ApexMethodDeclaration = {
    val typeName = CodeParser.toScala(from.typeRef()).map(tr => TypeReference.construct(tr)).getOrElse(TypeNames.Void)
    val block = CodeParser.toScala(from.block())
      .map(b => Block.constructLazy(parser, b))

    new ApexMethodDeclaration(outerTypeId,
      modifiers, RelativeTypeName(pkg, outerTypeId.typeName, typeName),
      Id.construct(from.id()),
      FormalParameters.construct(parser, pkg, outerTypeId.typeName, from.formalParameters()),
      block
    ).withContext(from)
  }

  def construct(parser: CodeParser, pkg: PackageImpl, outerTypeId: TypeId, modifiers: ModifierResults,
                from: InterfaceMethodDeclarationContext): ApexMethodDeclaration = {
    val typeName = CodeParser.toScala(from.typeRef()).map(tr => TypeReference.construct(tr)).getOrElse(TypeNames.Void)
    new ApexMethodDeclaration(outerTypeId,
      modifiers, RelativeTypeName(pkg, outerTypeId.typeName, typeName),
      Id.construct(from.id()),
      FormalParameters.construct(parser, pkg, outerTypeId.typeName, from.formalParameters()),
      None
    ).withContext(from)
  }
}

final case class ApexFieldDeclaration(outerTypeId: TypeId, _modifiers: ModifierResults, typeName: TypeName,
                                      variableDeclarator: VariableDeclarator)
  extends ClassBodyDeclaration(_modifiers) with ApexFieldLike {

  val id: Id = variableDeclarator.id
  override val nameRange: RangeLocationImpl = id.location
  override val name: Name = id.name
  private val visibility: Option[Modifier] = _modifiers.modifiers.find(m => ApexModifiers.visibilityModifiers.contains(m))
  override val readAccess: Modifier = visibility.getOrElse(PRIVATE_MODIFIER)
  override val writeAccess: Modifier = readAccess

  override def verify(context: BodyDeclarationVerifyContext): Unit = {
    val staticContext = if (isStatic) Some(true) else None
    variableDeclarator.verify(ExprContext(staticContext, context.thisType),
      new OuterBlockVerifyContext(context, modifiers.contains(STATIC_MODIFIER)))
    setDepends(context.dependencies)
    context.propagateDependencies()
  }
}

object ApexFieldDeclaration {
  def construct(outerTypeId: TypeId, modifiers: ModifierResults, fieldDeclaration: FieldDeclarationContext)
      : Seq[ApexFieldDeclaration] = {
    val typeName = TypeReference.construct(fieldDeclaration.typeRef())
    VariableDeclarators.construct(typeName, fieldDeclaration.variableDeclarators()).declarators.map(vd => {
      ApexFieldDeclaration(outerTypeId, modifiers, typeName, vd).withContext(fieldDeclaration)
    })
  }
}

final case class ApexConstructorDeclaration(_modifiers: ModifierResults, qualifiedName: QualifiedName,
                                            parameters: Array[ParameterDeclaration], block: Block)
  extends ClassBodyDeclaration(_modifiers) with ApexConstructorLike {

  override val nameRange: RangeLocationImpl = qualifiedName.location

  /* All parameters are FormalParameters but we need to bypass Array being invariant */
  def formalParameters: Array[FormalParameter] = parameters.collect {case p: FormalParameter => p}

  override def verify(context: BodyDeclarationVerifyContext): Unit = {
    formalParameters.foreach(_.verify(context))

    val blockContext = new OuterBlockVerifyContext(context, isStaticContext = false)
    formalParameters.foreach(param => blockContext.addVar(param.name, param.location, param.typeName))
    block.verify(blockContext)
    setDepends(context.dependencies)
    context.propagateDependencies()
  }
}

object ApexConstructorDeclaration {
  def construct(parser: CodeParser, pkg: PackageImpl, outerTypeName: TypeName, modifiers: ModifierResults,
                from: ConstructorDeclarationContext): ApexConstructorDeclaration = {
    ApexConstructorDeclaration(modifiers,
      QualifiedName.construct(from.qualifiedName()),
      FormalParameters.construct(parser, pkg, outerTypeName, from.formalParameters()),
      Block.constructLazy(parser, from.block())
    ).withContext(from)
  }
}

final case class FormalParameter(pkg: PackageImpl, outerTypeName: TypeName, modifiers: ModifierResults,
                                 relativeTypeName: RelativeTypeName, id: Id)
  extends CST with ParameterDeclaration {

  override val name: Name = id.name

  override lazy val typeName: TypeName = relativeTypeName.typeName

  def addVar(context: BlockVerifyContext): Unit = {
    relativeTypeName.addVar(location, id.name, context: BlockVerifyContext)
  }

  def verify(context: BodyDeclarationVerifyContext): Unit = {
    modifiers.issues.foreach(context.log)
    // This is validated when made available to a Block
  }
}

object FormalParameter {
  def construct(parser: CodeParser, pkg: PackageImpl, outerTypeName: TypeName, items: Array[FormalParameterContext])
  : Array[ParameterDeclaration] = {
    items.map(x => FormalParameter.construct(parser, pkg, outerTypeName, x))
  }

  def construct(parser: CodeParser, pkg: PackageImpl, outerTypeName: TypeName, from: FormalParameterContext): FormalParameter = {
    FormalParameter(pkg, outerTypeName,
      ApexModifiers.parameterModifiers(parser, CodeParser.toScala(from.modifier()), from),
      RelativeTypeName(pkg, outerTypeName, TypeReference.construct(from.typeRef())),
      Id.construct(from.id())).withContext(from)
  }
}

object FormalParameterList {
  def construct(parser: CodeParser, pkg: PackageImpl, outerTypeName: TypeName, from: FormalParameterListContext)
  : Array[ParameterDeclaration] = {
    if (from.formalParameter() != null) {
      FormalParameter.construct(parser, pkg, outerTypeName, CodeParser.toScala(from.formalParameter()).toArray)
    } else {
      Array()
    }
  }
}

object FormalParameters {
  def construct(parser: CodeParser, pkg: PackageImpl, outerTypeName: TypeName, from: FormalParametersContext)
  : Array[ParameterDeclaration] = {
    CodeParser.toScala(from.formalParameterList())
      .map(x => FormalParameterList.construct(parser, pkg, outerTypeName, x))
      .getOrElse(Array())
  }
}
