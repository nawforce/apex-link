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

import com.nawforce.parsers.ApexParser._
import com.nawforce.types.{ApexModifiers, GLOBAL_MODIFIER, Modifier, TypeName}

import scala.collection.JavaConverters._
import scala.collection.mutable

abstract class ClassBodyDeclaration(val modifiers: Seq[Modifier]) extends CST {
  lazy val isGlobal: Boolean = modifiers.contains(GLOBAL_MODIFIER)

  lazy val imports: Set[TypeName] = {
    val imports = mutable.Set[TypeName]()
    verify(imports)
    imports.diff(TypeName.ApexTypes).toSet
  }

  protected def verify(imports: mutable.Set[TypeName]): Unit
  def resolve(index: CSTIndex): Unit = {}
}

object ClassBodyDeclaration {
  def construct(outerTypeName: Option[TypeName], modifiers: List[ModifierContext],
                memberDeclarationContext: MemberDeclarationContext, context: ConstructContext): ClassBodyDeclaration = {
    val m = ApexModifiers.construct(modifiers, context)
    val cst: ClassBodyDeclaration =
      if (memberDeclarationContext.methodDeclaration() != null) {
        MethodDeclaration.construct(m, memberDeclarationContext.methodDeclaration(), context)
      } else if (memberDeclarationContext.fieldDeclaration() != null) {
        FieldDeclaration.construct(m, memberDeclarationContext.fieldDeclaration(), context)
      } else if (memberDeclarationContext.constructorDeclaration() != null) {
        ConstructorDeclaration.construct(m, memberDeclarationContext.constructorDeclaration(), context)
      } else if (memberDeclarationContext.interfaceDeclaration() != null) {
        InterfaceDeclaration.construct(outerTypeName, m, memberDeclarationContext.interfaceDeclaration(), context)
      } else if (memberDeclarationContext.enumDeclaration() != null) {
        EnumDeclaration.construct(outerTypeName, m, memberDeclarationContext.enumDeclaration(), context)
      } else if (memberDeclarationContext.propertyDeclaration() != null) {
        PropertyDeclaration.construct(m, memberDeclarationContext.propertyDeclaration(), context)
      } else if (memberDeclarationContext.classDeclaration() != null) {
        ClassDeclaration.construct(outerTypeName,
          ApexModifiers.classModifiers(modifiers, context, outer = false, memberDeclarationContext.classDeclaration().id()),
          memberDeclarationContext.classDeclaration(), context)
      } else {
        throw new CSTException()
      }
    cst.withContext(memberDeclarationContext, context)
  }
}

final case class InitialiserBlock(_modifiers: Seq[Modifier], block: Block) extends ClassBodyDeclaration(_modifiers) {
  override def children(): List[CST] = block.children()

  override def verify(imports: mutable.Set[TypeName]): Unit = {
    block.verify(imports)
  }

  override def resolve(index: CSTIndex): Unit = {
    index.add(this)
  }
}

object InitialiserBlock {
  def construct(modifiers: Seq[Modifier], block: BlockContext, context: ConstructContext): InitialiserBlock = {
    InitialiserBlock(modifiers, Block.construct(block, context))
  }
}

final case class MethodDeclaration(_modifiers: Seq[Modifier], typeRef: Option[TypeName], id: String,
                                   formalParameters: List[FormalParameter], block: Option[Block])
  extends ClassBodyDeclaration(_modifiers) {

  override def children(): List[CST] = List() ++ formalParameters ++ block

  override def verify(imports: mutable.Set[TypeName]): Unit = {
    typeRef.foreach(imports.add)
    formalParameters.foreach(_.verify(imports))
    block.foreach(_.verify(imports))
  }

  override def resolve(index: CSTIndex): Unit = {
    index.add(this)
    val rc = new ResolveStmtContext(index)
    block.foreach(b => b.resolve(rc))
    rc.complete()
  }
}

object MethodDeclaration {
  def construct(modifiers: Seq[Modifier], from: MethodDeclarationContext, context: ConstructContext): MethodDeclaration = {
    val typeRef = if (from.typeRef() != null) Some(TypeRef.construct(from.typeRef(), context)) else None
    val block = if (from.block != null) Some(Block.construct(from.block, context)) else None

    MethodDeclaration(modifiers,
      typeRef,
      from.id.getText,
      FormalParameters.construct(from.formalParameters(), context),
      block
    ).withContext(from, context)
  }
}

final case class FieldDeclaration(_modifiers: Seq[Modifier], typeRef: TypeName, variableDeclarators: VariableDeclarators)
  extends ClassBodyDeclaration(_modifiers) {
  override def children(): List[CST] = variableDeclarators :: Nil

  override def verify(imports: mutable.Set[TypeName]): Unit = {
    imports.add(typeRef)
    variableDeclarators.verify(imports)
  }
}

object FieldDeclaration {
  def construct(modifiers: Seq[Modifier], fieldDeclaration: FieldDeclarationContext, context: ConstructContext): FieldDeclaration = {
    FieldDeclaration(modifiers, TypeRef.construct(fieldDeclaration.typeRef(), context),
      VariableDeclarators.construct(fieldDeclaration.variableDeclarators(), context)).withContext(fieldDeclaration, context)
  }
}

final case class ConstructorDeclaration(_modifiers: Seq[Modifier], qualifiedName: QualifiedName,
                                        formalParameters: List[FormalParameter],
                                        block: Block)
  extends ClassBodyDeclaration(_modifiers) {
  override def children(): List[CST] = formalParameters ++ List(block)

  override def verify(imports: mutable.Set[TypeName]): Unit = {
    formalParameters.foreach(_.verify(imports))
    block.verify(imports)
  }
}

object ConstructorDeclaration {
  def construct(modifiers: Seq[Modifier], from: ConstructorDeclarationContext, context: ConstructContext): ConstructorDeclaration = {
    ConstructorDeclaration(modifiers,
      QualifiedName.construct(from.qualifiedName(), context),
      FormalParameters.construct(from.formalParameters(), context),
      Block.construct(from.block(), context)
    ).withContext(from, context)
  }
}

final case class FormalParameter(modifiers: Seq[Modifier], typeRef: TypeName, id: Id) extends CST {
  override def children(): List[CST] = List(id)

  def verify(imports: mutable.Set[TypeName]): Unit = {
    imports.add(typeRef)
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
