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
package com.nawforce.cst

import com.nawforce.api.Org
import com.nawforce.parsers.ApexParser._
import com.nawforce.types._
import com.nawforce.utils.Name

import scala.collection.JavaConverters._
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
  def construct(outerTypeName: TypeName, modifiers: List[ModifierContext],
                memberDeclarationContext: MemberDeclarationContext, context: ConstructContext)
  : Seq[ClassBodyDeclaration] = {
    val m = ApexModifiers.construct(modifiers, context)
    val declarations =
      if (memberDeclarationContext.methodDeclaration() != null) {
        Seq(ApexMethodDeclaration.construct(m, memberDeclarationContext.methodDeclaration(), context))
      } else if (memberDeclarationContext.fieldDeclaration() != null) {
        val id = memberDeclarationContext.fieldDeclaration().variableDeclarators().variableDeclarator().get(0).id()
        ApexFieldDeclaration.construct(
          ApexModifiers.fieldModifiers(modifiers, context, id),
          memberDeclarationContext.fieldDeclaration(), context)
      } else if (memberDeclarationContext.constructorDeclaration() != null) {
        Seq(ApexConstructorDeclaration.construct(m, memberDeclarationContext.constructorDeclaration(), context))
      } else if (memberDeclarationContext.interfaceDeclaration() != null) {
        Seq(InterfaceDeclaration.construct(Right(outerTypeName),
          ApexModifiers.interfaceModifiers(modifiers, context, outer = false, memberDeclarationContext.interfaceDeclaration().id()),
          memberDeclarationContext.interfaceDeclaration(), context))
      } else if (memberDeclarationContext.enumDeclaration() != null) {
        Seq(EnumDeclaration.construct(Right(outerTypeName),
          ApexModifiers.enumModifiers(modifiers, context, outer = false, memberDeclarationContext.enumDeclaration().id()),
          memberDeclarationContext.enumDeclaration(), context))
      } else if (memberDeclarationContext.propertyDeclaration() != null) {
        Seq(ApexPropertyDeclaration.construct(
            ApexModifiers.fieldModifiers(modifiers, context, memberDeclarationContext.propertyDeclaration().id()),
            memberDeclarationContext.propertyDeclaration(), context))
      } else if (memberDeclarationContext.classDeclaration() != null) {
        Seq(ClassDeclaration.construct(Right(outerTypeName),
          ApexModifiers.classModifiers(modifiers, context, outer = false, memberDeclarationContext.classDeclaration().id()),
          memberDeclarationContext.classDeclaration(), context))
      } else {
        throw new CSTException()
      }
    declarations.map(_.withContext(memberDeclarationContext, context))
  }
}

final case class InitialiserBlock(_modifiers: Seq[Modifier], block: Block)
  extends ClassBodyDeclaration(_modifiers) with BlockDeclaration {
  override def children(): List[CST] = block.children()

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

final case class ApexMethodDeclaration(_modifiers: Seq[Modifier], typeName: TypeName, id: Id,
                                       parameters: List[FormalParameter], block: Option[LazyBlock])
  extends ClassBodyDeclaration(_modifiers) with MethodDeclaration {

  override def children(): List[CST] = List() ++ parameters ++ block

  override val name: Name = id.name

  override def verify(context: BodyDeclarationVerifyContext): Unit = {
    if (typeName != TypeName.Void) {
      val returnType = context.getTypeAndAddDependency(typeName)
      if (returnType.isEmpty)
        Org.missingType(id.location, typeName)
    }
    parameters.foreach(_.verify(context))

    val blockContext = new OuterBlockVerifyContext(context, modifiers.contains(STATIC_MODIFIER))
    parameters.foreach(param => blockContext.addVar(param.name))
    block.foreach(_.verify(blockContext))
    depends = Some(context.dependencies)
    propagateDependencies()
  }
}

object ApexMethodDeclaration {
  def construct(modifiers: Seq[Modifier], from: MethodDeclarationContext, context: ConstructContext): ApexMethodDeclaration = {
    val typeName = Option(from.typeRef()).map(tr => TypeRef.construct(tr, context)).getOrElse(TypeName.Void)
    val block = Option(from.block).map(blk => Block.constructLazy(blk, context, modifiers.contains(STATIC_MODIFIER)))

    ApexMethodDeclaration(modifiers,
      typeName,
      Id.construct(from.id(), context),
      FormalParameters.construct(from.formalParameters(), context),
      block
    ).withContext(from, context)
  }

  def construct(modifiers: Seq[Modifier], from: InterfaceMethodDeclarationContext, context: ConstructContext): ApexMethodDeclaration = {
    val typeName = if (from.typeRef() != null) TypeRef.construct(from.typeRef(), context) else TypeName.Void

    ApexMethodDeclaration(modifiers,
      typeName,
      Id.construct(from.id(), context),
      FormalParameters.construct(from.formalParameters(), context),
      None
    ).withContext(from, context)
  }
}

final case class ApexFieldDeclaration(_modifiers: Seq[Modifier], typeName: TypeName, variableDeclarator: VariableDeclarator)
  extends ClassBodyDeclaration(_modifiers) with FieldDeclaration {

  override val name: Name = variableDeclarator.id.name
  private val visibility: Option[Modifier] = _modifiers.find(m => ApexModifiers.allVisibilityModifiers.contains(m))
  override val readAccess: Modifier = visibility.getOrElse(PRIVATE_MODIFIER)
  override val writeAccess: Modifier = readAccess

  override def children(): List[CST] = variableDeclarator :: Nil

  override def verify(context: BodyDeclarationVerifyContext): Unit = {
    val td = context.getTypeAndAddDependency(typeName)
    if (td.isEmpty)
      Org.missingType(variableDeclarator.id.location, typeName)

    variableDeclarator.verify(new OuterBlockVerifyContext(context, modifiers.contains(STATIC_MODIFIER)))
    depends = Some(context.dependencies)
    propagateDependencies()
  }
}

object ApexFieldDeclaration {
  def construct(modifiers: Seq[Modifier], fieldDeclaration: FieldDeclarationContext, context: ConstructContext):
        Seq[ApexFieldDeclaration] = {

    val typeName = TypeRef.construct(fieldDeclaration.typeRef(), context)
    VariableDeclarators.construct(fieldDeclaration.variableDeclarators(), context).declarators.map(vd => {
      ApexFieldDeclaration(modifiers, typeName, vd).withContext(fieldDeclaration, context)
    })
  }
}

final case class ApexConstructorDeclaration(_modifiers: Seq[Modifier], qualifiedName: QualifiedName,
                                            parameters: List[FormalParameter],
                                            block: Block)
  extends ClassBodyDeclaration(_modifiers) with ConstructorDeclaration {

  override def children(): List[CST] = parameters ++ List(block)

  override def verify(context: BodyDeclarationVerifyContext): Unit = {
    parameters.foreach(_.verify(context))

    val blockContext = new OuterBlockVerifyContext(context, isStaticContext = false)
    parameters.foreach(param => blockContext.addVar(param.name))
    block.verify(blockContext)
    depends = Some(context.dependencies)
    propagateDependencies()
  }
}

object ApexConstructorDeclaration {
  def construct(modifiers: Seq[Modifier], from: ConstructorDeclarationContext, context: ConstructContext): ApexConstructorDeclaration = {
    ApexConstructorDeclaration(modifiers,
      QualifiedName.construct(from.qualifiedName(), context),
      FormalParameters.construct(from.formalParameters(), context),
      Block.construct(from.block(), context)
    ).withContext(from, context)
  }
}

final case class FormalParameter(modifiers: Seq[Modifier], typeName: TypeName, id: Id)
  extends CST with ParameterDeclaration {

  override def children(): List[CST] = List(id)

  val name: Name = id.name

  def verify(context: BodyDeclarationVerifyContext): Unit = {
    val paramType = context.getTypeAndAddDependency(typeName)
    if (paramType.isEmpty)
      Org.missingType(id.location, typeName)
  }
}

object FormalParameter {
  def construct(aList: List[FormalParameterContext], context: ConstructContext): List[FormalParameter] = {
    aList.map(x => FormalParameter.construct(x, context))
  }

  def construct(from: FormalParameterContext, context: ConstructContext): FormalParameter = {
    FormalParameter(
      ApexModifiers.construct(from.modifier().asScala, context),
      TypeRef.construct(from.typeRef(), context), Id.construct(from.id, context)).withContext(from, context)
  }
}

object FormalParameterList {
  def construct(from: FormalParameterListContext, context: ConstructContext): List[FormalParameter] = {
    if (from.formalParameter() != null) {
      val m: Seq[FormalParameterContext] = from.formalParameter().asScala
      FormalParameter.construct(m.toList, context)
    } else {
      List()
    }
  }
}

object FormalParameters {
  def construct(from: FormalParametersContext, context: ConstructContext): List[FormalParameter] = {
    if (from.formalParameterList() != null) {
      FormalParameterList.construct(from.formalParameterList(), context)
    } else {
      List()
    }
  }
}
