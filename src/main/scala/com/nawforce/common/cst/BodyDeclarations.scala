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

import com.nawforce.common.api
import com.nawforce.common.api.{MethodSummary, ParameterSummary}
import com.nawforce.common.finding.RelativeTypeName
import com.nawforce.common.metadata._
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.types._
import com.nawforce.common.types.apex.ApexFieldLike
import com.nawforce.runtime.parsers.ApexParser._
import com.nawforce.runtime.parsers.CodeParser

import scala.collection.mutable

abstract class ClassBodyDeclaration(val modifiers: Seq[Modifier]) extends CST with DependencyHolder {
  lazy val isGlobal: Boolean = modifiers.contains(GLOBAL_MODIFIER) || modifiers.contains(WEBSERVICE_MODIFIER)

  protected var depends: Option[Set[Dependant]] = None

  override def dependencies(): Set[Dependant] = {
    depends.get
  }

  def collectDependencies(dependsOn: mutable.Set[Dependant]): Unit = {
    dependencies().foreach(dependsOn.add)
  }

  def validate(context: BodyDeclarationVerifyContext): Unit = {
    verify(context)
  }

  protected def verify(context: BodyDeclarationVerifyContext): Unit
}

object ClassBodyDeclaration {
  def construct(pkg: PackageDeclaration, outerTypeName: TypeName, modifiers: Seq[ModifierContext],
                memberDeclarationContext: MemberDeclarationContext, context: ConstructContext)
  : Seq[ClassBodyDeclaration] = {

    val declarations: Option[Seq[ClassBodyDeclaration]] =
      CodeParser.toScala(memberDeclarationContext.methodDeclaration())
        .map(x => Seq(ApexMethodDeclaration.construct(pkg, outerTypeName,
          ApexModifiers.methodModifiers(modifiers, context, x.id()),
          x, context)))
      .orElse(CodeParser.toScala(memberDeclarationContext.fieldDeclaration())
        .map(x => ApexFieldDeclaration.construct(
          ApexModifiers.fieldModifiers(modifiers, context,
            CodeParser.toScala(x.variableDeclarators().variableDeclarator()).head.id()),
          x, context)))
      .orElse(CodeParser.toScala(memberDeclarationContext.constructorDeclaration())
        .map(x => Seq(ApexConstructorDeclaration.construct(pkg, outerTypeName,
          ApexModifiers.constructorModifiers(modifiers, context, x),
          x, context))))
      .orElse(CodeParser.toScala(memberDeclarationContext.interfaceDeclaration())
        .map(x => Seq(InterfaceDeclaration.construct(pkg, Some(outerTypeName),
          ApexModifiers.interfaceModifiers(modifiers, context, outer = false, x.id()),
          x, context))))
      .orElse(CodeParser.toScala(memberDeclarationContext.enumDeclaration())
        .map(x => Seq(EnumDeclaration.construct(pkg, Some(outerTypeName),
          ApexModifiers.enumModifiers(modifiers, context, outer = false, x.id()),
          x, context))))
      .orElse(CodeParser.toScala(memberDeclarationContext.propertyDeclaration())
        .map(x => Seq(ApexPropertyDeclaration.construct(
          ApexModifiers.fieldModifiers(modifiers, context, x.id()),
          x, context))))
      .orElse(CodeParser.toScala(memberDeclarationContext.classDeclaration())
        .map(x => Seq(ClassDeclaration.construct(pkg, Some(outerTypeName),
          ApexModifiers.classModifiers(modifiers, context, outer = false, x.id()),
          x, context))))

    if (declarations.isEmpty)
      throw new CSTException()
    else
      declarations.get.map(_.withContext(memberDeclarationContext, context))
  }
}

final case class InitialiserBlock(_modifiers: Seq[Modifier], block: Block)
  extends ClassBodyDeclaration(_modifiers) with BlockDeclaration {

  override val isStatic: Boolean = modifiers.contains(STATIC_MODIFIER)

  override def verify(context: BodyDeclarationVerifyContext): Unit = {
    val blockContext = new OuterBlockVerifyContext(context, isStatic)
    block.verify(blockContext)
    depends = Some(context.dependencies)
    propagateDependencies()
  }
}

object InitialiserBlock {
  def construct(modifiers: Seq[Modifier], block: BlockContext, context: ConstructContext): InitialiserBlock = {
    InitialiserBlock(modifiers, Block.construct(block, context))
  }
}

final case class ApexMethodDeclaration(_modifiers: Seq[Modifier], relativeTypeName: RelativeTypeName, id: Id,
                                       parameters: Seq[FormalParameter], block: Option[LazyBlock])
  extends ClassBodyDeclaration(_modifiers) with MethodDeclaration {

  override val name: Name = id.name

  override lazy val typeName: TypeName = relativeTypeName.typeName

  override def verify(context: BodyDeclarationVerifyContext): Unit = {

    if (relativeTypeName.outerNature == CLASS_NATURE) {
      if (isAbstract && block.nonEmpty)
        context.logMessage(id.location, "Abstract methods can not have an implementation")
      else if (!isAbstract && block.isEmpty)
        context.logMessage(id.location, "Method must have an implementations or be marked abstract")
      else if (isAbstract && isVirtual)
        context.logMessage(id.location, "Abstract methods do not need virtual keyword")
    } else if (relativeTypeName.outerNature == INTERFACE_NATURE) {
      if (modifiers.nonEmpty)
        context.logMessage(id.location, s"Modifier '${modifiers.head.name}' is not supported on interface methods")
    }

    relativeTypeName.typeRequest match {
      case Some(Left(error)) => context.logMessage(id.location, error.toString)
      case Some(Right(td)) => context.addDependency(td)
      case _ => ()
    }

    parameters.foreach(_.verify(context))

    block.foreach(blk => {
      val blockContext = new OuterBlockVerifyContext(context, modifiers.contains(STATIC_MODIFIER))
      parameters.foreach(param => param.addVar(blockContext))
      blk.verify(blockContext)
    })

    depends = Some(context.dependencies)
    propagateDependencies()
  }

  // Populated by type MethodMap construction
  lazy val shadows: mutable.Set[MethodDeclaration] = mutable.Set()

  lazy val isEntry: Boolean = {
    modifiers.contains(ISTEST_ANNOTATION) ||
      modifiers.contains(TEST_SETUP_ANNOTATION) ||
      modifiers.contains(TEST_METHOD_MODIFIER) ||
      modifiers.contains(GLOBAL_MODIFIER)
  }

  def isUsed: Boolean = {
    isEntry || hasHolders ||
      shadows.exists({
        case am: ApexMethodDeclaration => am.isUsed
        case _: MethodDeclaration => true
        case _ => false
      })
  }

  // Override to attempt to resolve relative type name
  override lazy val summary: MethodSummary = api.MethodSummary(
    MethodSummary.defaultVersion,
    name.toString, modifiers.map(_.toString).sorted.toList, relativeTypeName.relativeTypeName.asString,
    parameters.map(_.summary).toList
  )

}

object ApexMethodDeclaration {
  def construct(pkg: PackageDeclaration, outerTypeName: TypeName, modifiers: Seq[Modifier],
                from: MethodDeclarationContext, context: ConstructContext): ApexMethodDeclaration = {
    val typeName = CodeParser.toScala(from.typeRef()).map(tr => TypeRef.construct(tr)).getOrElse(TypeName.Void)
    val block = CodeParser.toScala(from.block())
      .map(b => Block.constructLazy(b, context, modifiers.contains(STATIC_MODIFIER)))

    ApexMethodDeclaration(
      modifiers, RelativeTypeName(pkg, outerTypeName, typeName),
      Id.construct(from.id(), context),
      FormalParameters.construct(pkg, outerTypeName, from.formalParameters(), context),
      block
    ).withContext(from, context)
  }

  def construct(pkg: PackageDeclaration, outerTypeName: TypeName, modifiers: Seq[Modifier],
                from: InterfaceMethodDeclarationContext, context: ConstructContext): ApexMethodDeclaration = {
    val typeName = CodeParser.toScala(from.typeRef()).map(tr => TypeRef.construct(tr)).getOrElse(TypeName.Void)
    ApexMethodDeclaration(
      modifiers, RelativeTypeName(pkg, outerTypeName, typeName),
      Id.construct(from.id(), context),
      FormalParameters.construct(pkg, outerTypeName, from.formalParameters(), context),
      None
    ).withContext(from, context)
  }
}

final case class ApexFieldDeclaration(_modifiers: Seq[Modifier], typeName: TypeName,
                                      variableDeclarator: VariableDeclarator)
  extends ClassBodyDeclaration(_modifiers) with ApexFieldLike {

  val id: Id = variableDeclarator.id
  override val name: Name = id.name
  private val visibility: Option[Modifier] = _modifiers.find(m => ApexModifiers.visibilityModifiers.contains(m))
  override val readAccess: Modifier = visibility.getOrElse(PRIVATE_MODIFIER)
  override val writeAccess: Modifier = readAccess

  override def verify(context: BodyDeclarationVerifyContext): Unit = {
    val staticContext = if (isStatic) Some(true) else None
    variableDeclarator.verify(ExprContext(staticContext, context.thisType),
      new OuterBlockVerifyContext(context, modifiers.contains(STATIC_MODIFIER)))
    depends = Some(context.dependencies)
    propagateDependencies()
  }
}

object ApexFieldDeclaration {
  def construct(modifiers: Seq[Modifier], fieldDeclaration: FieldDeclarationContext, context: ConstructContext):
        Seq[ApexFieldDeclaration] = {

    val typeName = TypeRef.construct(fieldDeclaration.typeRef())
    VariableDeclarators.construct(typeName, fieldDeclaration.variableDeclarators(), context).declarators.map(vd => {
      ApexFieldDeclaration(modifiers, typeName, vd).withContext(fieldDeclaration, context)
    })
  }
}

final case class ApexConstructorDeclaration(_modifiers: Seq[Modifier], qualifiedName: QualifiedName,
                                            parameters: Seq[FormalParameter],
                                            block: Block)
  extends ClassBodyDeclaration(_modifiers) with ConstructorDeclaration {

  override def verify(context: BodyDeclarationVerifyContext): Unit = {
    parameters.foreach(_.verify(context))

    val blockContext = new OuterBlockVerifyContext(context, isStaticContext = false)
    parameters.foreach(param => blockContext.addVar(param.name, param.location, param.typeName))
    block.verify(blockContext)
    depends = Some(context.dependencies)
    propagateDependencies()
  }
}

object ApexConstructorDeclaration {
  def construct(pkg: PackageDeclaration, outerTypeName: TypeName, modifiers: Seq[Modifier],
                from: ConstructorDeclarationContext, context: ConstructContext): ApexConstructorDeclaration = {
    ApexConstructorDeclaration(modifiers,
      QualifiedName.construct(from.qualifiedName(), context),
      FormalParameters.construct(pkg, outerTypeName, from.formalParameters(), context),
      Block.construct(from.block(), context)
    ).withContext(from, context)
  }
}

final case class FormalParameter(pkg: PackageDeclaration, outerTypeName: TypeName, modifiers: Seq[Modifier],
                                 relativeTypeName: RelativeTypeName, id: Id)
  extends CST with ParameterDeclaration {

  override val name: Name = id.name

  override lazy val typeName: TypeName = relativeTypeName.typeName

  def addVar(context: BlockVerifyContext): Unit = {
    relativeTypeName.addVar(location, id.name, context: BlockVerifyContext)
  }

  def verify(context: BodyDeclarationVerifyContext): Unit = {
    // This is validated when made available to a Block
  }

  // Override to avoid attempts to resolve relative type name
  override lazy val summary: ParameterSummary = {
    ParameterSummary(ParameterSummary.defaultVersion, name.toString, relativeTypeName.relativeTypeName.asString)
  }
}

object FormalParameter {
  def construct(pkg: PackageDeclaration, outerTypeName: TypeName, aList: Seq[FormalParameterContext],
                context: ConstructContext): Seq[FormalParameter] = {
    aList.map(x => FormalParameter.construct(pkg, outerTypeName, x, context))
  }

  def construct(pkg: PackageDeclaration, outerTypeName: TypeName, from: FormalParameterContext,
                context: ConstructContext): FormalParameter = {
    FormalParameter(pkg, outerTypeName,
      ApexModifiers.construct(CodeParser.toScala(from.modifier()), context),
      RelativeTypeName(pkg, outerTypeName, TypeRef.construct(from.typeRef())),
      Id.construct(from.id(), context)).withContext(from, context)
  }
}

object FormalParameterList {
  def construct(pkg: PackageDeclaration, outerTypeName: TypeName, from: FormalParameterListContext,
                context: ConstructContext): Seq[FormalParameter] = {
    if (from.formalParameter() != null) {
      val m: Seq[FormalParameterContext] = CodeParser.toScala(from.formalParameter())
      FormalParameter.construct(pkg, outerTypeName, m.toList, context)
    } else {
      Seq()
    }
  }
}

object FormalParameters {
  def construct(pkg: PackageDeclaration, outerTypeName: TypeName, from: FormalParametersContext,
                context: ConstructContext): Seq[FormalParameter] = {
    CodeParser.toScala(from.formalParameterList())
      .map(x => FormalParameterList.construct(pkg, outerTypeName, x, context))
      .getOrElse(Seq())
  }
}
