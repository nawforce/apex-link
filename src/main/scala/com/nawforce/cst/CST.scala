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
package com.nawforce.cst

import com.nawforce.documents.TextRange
import com.nawforce.parsers.ApexParser._
import com.nawforce.types.{ApexModifiers, GLOBAL_MODIFIER, Modifier, TypeName}
import com.nawforce.utils.Name
import org.antlr.v4.runtime.ParserRuleContext

import scala.collection.JavaConverters._
import scala.collection.mutable

class CSTException extends Exception

abstract class CST {
  var textRange: TextRange = TextRange.empty

  // TODO: Not all CST produce types
  def getType(ctx: TypeContext): TypeName = {
    throw new CSTException
  }

  def children(): List[CST]

  def descendants(cst: CST): List[CST] = {
    List(cst) ++ cst.children().flatMap(x => descendants(x))
  }

  def withContext(context: ParserRuleContext, constructContext: ConstructContext): this.type = {
    textRange = TextRange(context)
    this
  }

  def findExpressions(onlyOuter: Boolean): List[Expression] = {
    this match {
      case e: Expression => List(e) ++ (if (!onlyOuter) this.children().flatMap(_.findExpressions(onlyOuter)) else Nil)
      case _ => this.children().flatMap(_.findExpressions(onlyOuter))
    }
  }

  def findStatements(onlyOuter: Boolean): List[Statement] = {
    this match {
      case e: Statement => List(e) ++ (if (!onlyOuter) this.children().flatMap(_.findStatements(onlyOuter)) else Nil)
      case _ => this.children().flatMap(_.findStatements(onlyOuter))
    }
  }
}

class CSTIndex {
  private val cstIndex = new mutable.HashMap[String, mutable.Set[CST]] with mutable.MultiMap[String, CST]

  def add(cst: CST): Unit = {
    cstIndex.addBinding(cst.getClass.getSimpleName, cst)
  }

  def get(name: String): mutable.Set[CST] = {
    val nodes: Option[mutable.Set[CST]] = cstIndex.get(name)
    if (nodes.isDefined)
      nodes.get
    else
      mutable.Set[CST]()
  }
}

final case class Id(name: Name) extends CST {
  override def children(): List[CST] = List()
}

object Id {
  def construct(idContext: IdContext, context: ConstructContext): Id = {
    Id(Name(idContext.getText)).withContext(idContext, context)
  }
}

abstract class TypeContext {
  def thisType: TypeName

  def superType: TypeName

  def getIdentifierType(id: String): TypeName
}

final case class QualifiedName(names: List[Name]) extends CST {
  override def children(): List[CST] = Nil
}

object QualifiedName {
  def construct(aList: List[QualifiedNameContext], context: ConstructContext): List[QualifiedName] = {
    aList.map(n => QualifiedName.construct(n, context))
  }

  def construct(qualifiedName: QualifiedNameContext, context: ConstructContext): QualifiedName = {
    val ids: Seq[IdContext] = qualifiedName.id().asScala
    QualifiedName(ids.toList.map(id => Name(id.getText))).withContext(qualifiedName, context)
  }
}

final case class Annotation(name: QualifiedName, elementValuePairs: List[ElementValuePair], elementValue: Option[ElementValue]) extends CST {
  override def children(): List[CST] = List[CST]() ++ elementValuePairs ++ elementValue
}

object Annotation {
  def construct(annotation: AnnotationContext, context: ConstructContext): Annotation = {
    val elementValue =
      if (annotation.elementValue() != null) {
        Some(ElementValue.construct(annotation.elementValue(), context))
      } else {
        None
      }
    Annotation(QualifiedName.construct(annotation.qualifiedName(), context),
      ElementValuePairs.construct(annotation.elementValuePairs(), context),
      elementValue).withContext(annotation, context)
  }
}

sealed abstract class ElementValue() extends CST

final case class ExpressionElementValue(expression: Expression) extends ElementValue {
  override def children(): List[CST] = expression :: Nil
}

final case class AnnotationElementValue(annotation: Annotation) extends ElementValue {
  override def children(): List[CST] = annotation :: Nil
}

final case class ArrayInitializerElementValue(arrayInitializer: ElementValueArrayInitializer) extends ElementValue {
  override def children(): List[CST] = arrayInitializer :: Nil
}

object ElementValue {
  def construct(aList: List[ElementValueContext], context: ConstructContext): List[ElementValue] = {
    aList.map(x => ElementValue.construct(x, context))
  }

  def construct(elementValue: ElementValueContext, context: ConstructContext): ElementValue = {
    if (elementValue.expression() != null) {
      ExpressionElementValue(Expression.construct(elementValue.expression(), context)).withContext(elementValue, context)
    } else if (elementValue.annotation() != null) {
      AnnotationElementValue(Annotation.construct(elementValue.annotation(), context)).withContext(elementValue, context)
    } else if (elementValue.elementValueArrayInitializer() != null) {
      ArrayInitializerElementValue(ElementValueArrayInitializer.construct(
        elementValue.elementValueArrayInitializer(), context)).withContext(elementValue, context)
    } else {
      throw new CSTException()
    }
  }
}

final case class ElementValueArrayInitializer(elementValues: List[ElementValue]) extends CST {
  override def children(): List[CST] = elementValues
}

object ElementValueArrayInitializer {
  def construct(from: ElementValueArrayInitializerContext, context: ConstructContext): ElementValueArrayInitializer = {
    val elements: Seq[ElementValueContext] = from.elementValue().asScala
    ElementValueArrayInitializer(elements.toList.map(x => ElementValue.construct(x, context))).withContext(from, context)
  }
}

final case class ElementValuePair(id: String, elementValue: ElementValue) extends CST {
  override def children(): List[CST] = elementValue :: Nil
}

object ElementValuePair {
  def construct(aList: List[ElementValuePairContext], context: ConstructContext): List[ElementValuePair] = {
    aList.map(x => ElementValuePair.construct(x, context))
  }

  def construct(from: ElementValuePairContext, context: ConstructContext): ElementValuePair = {
    ElementValuePair(from.id().getText, ElementValue.construct(from.elementValue(), context)).withContext(from, context)
  }
}

object ElementValuePairs {
  def construct(from: ElementValuePairsContext, context: ConstructContext): List[ElementValuePair] = {
    if (from != null) {
      val pairs: Seq[ElementValuePairContext] = from.elementValuePair().asScala
      pairs.toList.map(x => ElementValuePair.construct(x, context))
    } else {
      List()
    }
  }
}

trait ClassBodyDeclaration extends CST {
  def isGlobal: Boolean

  def verify(imports: mutable.Set[TypeName]): Unit
  def resolve(index: CSTIndex): Unit = {
    // Default to ignore
  }
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

final case class InitialiserBlock(block: Block) extends ClassBodyDeclaration {
  override def children(): List[CST] = block.children()

  override def isGlobal: Boolean = false

  override def verify(imports: mutable.Set[TypeName]): Unit = {
    block.verify(imports)
  }

  override def resolve(index: CSTIndex): Unit = {
    index.add(this)
  }
}

object InitialiserBlock {
  def construct(block: BlockContext, context: ConstructContext): InitialiserBlock = {
    InitialiserBlock(Block.construct(block, context))
  }
}

final case class MethodDeclaration(modifiers: Seq[Modifier], typeRef: Option[TypeName], id: String,
                                   formalParameters: List[FormalParameter], block: Option[Block]) extends ClassBodyDeclaration {

  override def children(): List[CST] = List() ++ formalParameters ++ block

  override def isGlobal: Boolean = modifiers.contains(GLOBAL_MODIFIER)

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

final case class FieldDeclaration(modifiers: Seq[Modifier], typeRef: TypeName, variableDeclarators: VariableDeclarators) extends ClassBodyDeclaration {
  override def children(): List[CST] = variableDeclarators :: Nil

  override def verify(imports: mutable.Set[TypeName]): Unit = {
    imports.add(typeRef)
    variableDeclarators.verify(imports)
  }

  override def isGlobal: Boolean = modifiers.contains(GLOBAL_MODIFIER)
}

object FieldDeclaration {
  def construct(modifiers: Seq[Modifier], fieldDeclaration: FieldDeclarationContext, context: ConstructContext): FieldDeclaration = {
    FieldDeclaration(modifiers, TypeRef.construct(fieldDeclaration.typeRef(), context),
      VariableDeclarators.construct(fieldDeclaration.variableDeclarators(), context)).withContext(fieldDeclaration, context)
  }
}

final case class ConstructorDeclaration(modifiers: Seq[Modifier], qualifiedName: QualifiedName,
                                        formalParameters: List[FormalParameter],
                                        block: Block) extends ClassBodyDeclaration {
  override def children(): List[CST] = formalParameters ++ List(block)

  override def verify(imports: mutable.Set[TypeName]): Unit = {
    formalParameters.foreach(_.verify(imports))
    block.verify(imports)
  }

  override def isGlobal: Boolean = modifiers.contains(GLOBAL_MODIFIER)
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

