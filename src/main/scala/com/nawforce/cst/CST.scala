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

final case class QualifiedName(names: List[String]) extends CST {
  override def children(): List[CST] = Nil
}

object QualifiedName {
  def construct(aList: List[QualifiedNameContext], context: ConstructContext): List[QualifiedName] = {
    aList.map(n => QualifiedName.construct(n, context))
  }

  def construct(qualifiedName: QualifiedNameContext, context: ConstructContext): QualifiedName = {
    val ids: Seq[IdContext] = qualifiedName.id().asScala
    QualifiedName(ids.toList.map(id => id.getText)).withContext(qualifiedName, context)
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

final case class StaticBlock(block: Block) extends ClassBodyDeclaration {
  override def children(): List[CST] = block.children()

  override def isGlobal: Boolean = false

  override def resolve(index: CSTIndex): Unit = {
    index.add(this)
  }
}

object StaticBlock {
  def construct(block: BlockContext, context: ConstructContext): StaticBlock = {
    StaticBlock(Block.construct(block, context))
  }
}

final case class MethodDeclaration(modifiers: Seq[Modifier], typeRef: Option[TypeName], id: String,
                                   formalParameters: List[FormalParameter], block: Option[Block]) extends ClassBodyDeclaration {

  override def children(): List[CST] = List() ++ formalParameters ++ block

  override def isGlobal: Boolean = modifiers.contains(GLOBAL_MODIFIER)

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

final case class PropertyDeclaration(modifiers: Seq[Modifier], typeRef: TypeName, variableDeclarators: VariableDeclarators, propertyDeclaration: PropertyBodyDeclaration) extends ClassBodyDeclaration {
  override def children(): List[CST] = variableDeclarators :: propertyDeclaration :: Nil

  override def isGlobal: Boolean = modifiers.contains(GLOBAL_MODIFIER)
}

object PropertyDeclaration {
  def construct(modifiers: Seq[Modifier], propertyDeclaration: PropertyDeclarationContext, context: ConstructContext): PropertyDeclaration = {
    PropertyDeclaration(modifiers, TypeRef.construct(propertyDeclaration.typeRef(), context),
      VariableDeclarators.construct(propertyDeclaration.variableDeclarators(), context),
      PropertyBodyDeclaration.construct(propertyDeclaration.propertyBodyDeclaration(), context)).withContext(propertyDeclaration, context)
  }
}

sealed abstract class VariableInitializer() extends CST

object VariableInitializer {
  def construct(variableInitializers: List[VariableInitializerContext], context: ConstructContext): List[VariableInitializer] = {
    variableInitializers.map(x => VariableInitializer.construct(x, context))
  }

  def construct(variableInitializer: VariableInitializerContext, context: ConstructContext): VariableInitializer = {
    val cst =
      if (variableInitializer.expression() != null) {
        ExpressionVariableInitializer(Expression.construct(variableInitializer.expression(), context))
      } else if (variableInitializer.arrayInitializer() != null) {
        ArrayVariableInitializer.construct(variableInitializer.arrayInitializer(), context)
      } else {
        throw new CSTException()
      }
    cst.withContext(variableInitializer, context)
  }
}

final case class ArrayVariableInitializer(variableInitializers: List[VariableInitializer]) extends VariableInitializer {
  override def children(): List[CST] = variableInitializers
}

final case class ExpressionVariableInitializer(expression: Expression) extends VariableInitializer {
  override def children(): List[CST] = expression :: Nil
}

object ArrayVariableInitializer {
  def construct(arrayInitializer: ArrayInitializerContext, context: ConstructContext): ArrayVariableInitializer = {
    val variableInitializers: Seq[VariableInitializerContext] = arrayInitializer.variableInitializer().asScala
    ArrayVariableInitializer(variableInitializers.toList.map(x =>
      VariableInitializer.construct(x, context)
    )).withContext(arrayInitializer, context)
  }
}

final case class VariableDeclarator(id: Identifier, init: Option[VariableInitializer]) extends CST with VarIntroducer {
  override def children(): List[CST] = List[CST](id) ++ init
}

object VariableDeclarator {
  def construct(variableDeclarator: VariableDeclaratorContext, context: ConstructContext): VariableDeclarator = {
    val init =
      if (variableDeclarator.variableInitializer() != null) {
        Some(VariableInitializer.construct(variableDeclarator.variableInitializer(), context))
      } else {
        None
      }
    VariableDeclarator(Identifier(variableDeclarator.id().getText), init).withContext(variableDeclarator, context)
  }
}

final case class VariableDeclarators(declarators: List[VariableDeclarator]) extends CST {
  override def children(): List[CST] = declarators

  def resolve(typeRef: TypeName, context: ResolveStmtContext): Unit = {
    declarators.foreach(x => {
      context.addVarDeclaration(VarDeclaration(x.id, typeRef, x))
      x.init.foreach {
        case ExpressionVariableInitializer(expression) => x.addAssign(expression)
        case _ =>
      }
    })
  }
}

object VariableDeclarators {
  def construct(variableDeclaratorsContext: VariableDeclaratorsContext, context: ConstructContext): VariableDeclarators = {
    val variableDeclarators: Seq[VariableDeclaratorContext] = variableDeclaratorsContext.variableDeclarator().asScala
    VariableDeclarators(variableDeclarators.toList.map(x => VariableDeclarator.construct(x, context))).withContext(variableDeclaratorsContext, context)
  }
}


final case class LocalVariableDeclaration(modifiers: Seq[Modifier], typeRef: TypeName, variableDeclarators: VariableDeclarators) extends CST {
  override def children(): List[CST] = variableDeclarators :: Nil

  def resolve(context: ResolveStmtContext): Unit = variableDeclarators.resolve(typeRef, context)
}

object LocalVariableDeclaration {
  def construct(localVariableDeclaration: LocalVariableDeclarationContext, context: ConstructContext): LocalVariableDeclaration = {
    LocalVariableDeclaration(
      ApexModifiers.construct(localVariableDeclaration.modifier().asScala, context),
      TypeRef.construct(localVariableDeclaration.typeRef(), context),
      VariableDeclarators.construct(localVariableDeclaration.variableDeclarators(), context)).withContext(localVariableDeclaration, context)
  }
}

sealed abstract class PropertyBlock extends CST

final case class GetterPropertyBlock(modifiers: Seq[Modifier], block: Option[Block]) extends PropertyBlock {
  override def children(): List[CST] = List() ++ block
}

final case class SetterPropertyBlock(modifiers: Seq[Modifier], block: Option[Block]) extends PropertyBlock {
  override def children(): List[CST] = List() ++ block
}

object PropertyBlock {
  def construct(propertyBlockContext: PropertyBlockContext, context: ConstructContext): PropertyBlock = {
    val modifiers: Seq[ModifierContext] = propertyBlockContext.modifier().asScala
    val cst =
      if (propertyBlockContext.getter() != null) {
        GetterPropertyBlock(ApexModifiers.construct(modifiers.toList, context), Block.constructOption(propertyBlockContext.getter().block(), context))
      } else if (propertyBlockContext.setter() != null) {
        SetterPropertyBlock(ApexModifiers.construct(modifiers.toList, context), Block.constructOption(propertyBlockContext.setter().block(), context))
      } else {
        throw new CSTException()
      }
    cst.withContext(propertyBlockContext, context)
  }
}

final case class PropertyBodyDeclaration(propertyBlocks: List[PropertyBlock]) extends CST {
  override def children(): List[CST] = propertyBlocks
}

object PropertyBodyDeclaration {
  def construct(propertyBodyDeclarationContext: PropertyBodyDeclarationContext, context: ConstructContext): PropertyBodyDeclaration = {
    val blocks: Seq[PropertyBlockContext] = propertyBodyDeclarationContext.propertyBlock().asScala
    PropertyBodyDeclaration(blocks.toList.map(x => PropertyBlock.construct(x, context))).withContext(propertyBodyDeclarationContext, context)
  }
}

final case class FormalParameter(modifiers: Seq[Modifier], typeRef: TypeName, id: Identifier) extends CST {
  override def children(): List[CST] = List(id)
}

object FormalParameter {
  def construct(aList: List[FormalParameterContext], context: ConstructContext): List[FormalParameter] = {
    aList.map(x => FormalParameter.construct(x, context))
  }

  def construct(from: FormalParameterContext, context: ConstructContext): FormalParameter = {
    FormalParameter(
      ApexModifiers.construct(from.modifier().asScala, context),
      TypeRef.construct(from.typeRef(), context), Identifier(from.id.getText)).withContext(from, context)
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

trait ExpressionRHS extends CST {
  def resolve(context: ResolveExprContext)
}

sealed abstract class Expression extends CST {
  def resolve(context: ResolveExprContext)
}

final case class LHSExpression(lhs: Expression, rhs: ExpressionRHS) extends Expression {
  override def children(): List[CST] = lhs :: rhs :: Nil

  override def resolve(context: ResolveExprContext): Unit = {
    lhs.resolve(context)
    rhs.resolve(context)
  }
}

final case class QName(ids: List[String]) extends Expression {
  override def children(): List[CST] = Nil

  def resolve(context: ResolveExprContext): Unit = {}
}

final case class FunctionCall(callee: Expression, arguments: List[Expression]) extends Expression {
  override def children(): List[CST] = callee :: arguments

  override def resolve(context: ResolveExprContext): Unit = {
    callee.resolve(context)
    arguments.foreach(_.resolve(context))
  }
}

final case class NewExpression(creator: Creator) extends Expression {
  override def children(): List[CST] = creator :: Nil

  def resolve(context: ResolveExprContext): Unit = {}
}

final case class TypeExpression(typeRef: TypeName, expression: Expression) extends Expression {
  override def children(): List[CST] = expression :: Nil

  def resolve(context: ResolveExprContext): Unit = expression.resolve(context)
}

final case class PostOpExpression(expression: Expression, op: String) extends Expression {
  override def children(): List[CST] = expression :: Nil

  def resolve(context: ResolveExprContext): Unit = expression.resolve(context)
}

final case class PreOpExpression(expression: Expression, op: String) extends Expression {
  override def children(): List[CST] = expression :: Nil

  def resolve(context: ResolveExprContext): Unit = expression.resolve(context)
}

final case class BinaryExpression(lhs: Expression, rhs: Expression, op: String) extends Expression {
  override def children(): List[CST] = lhs :: rhs :: Nil

  def resolve(context: ResolveExprContext): Unit = {
    lhs.resolve(context)
    rhs.resolve(context)
  }
}

final case class InstanceOfExpression(expression: Expression, typeRef: TypeName) extends Expression {
  override def children(): List[CST] = expression :: Nil

  def resolve(context: ResolveExprContext): Unit = expression.resolve(context)
}

final case class QueryExpression(query: Expression, lhs: Expression, rhs: Expression) extends Expression {
  override def children(): List[CST] = query :: lhs :: rhs :: Nil

  def resolve(context: ResolveExprContext): Unit = {
    query.resolve(context)
    lhs.resolve(context)
    rhs.resolve(context)
  }
}

final case class PrimaryExpression(var primary: Primary) extends Expression {
  override def children(): List[CST] = primary :: Nil

  def resolve(context: ResolveExprContext): Unit = {
    // Link variable reference
    primary match {
      case Identifier(name) =>
        context.getVarDeclaration(name).foreach(declaration => {
          primary = VarRef(declaration)
        })
      case _ =>
    }
  }
}

final case class RHSId(id: String) extends ExpressionRHS {
  override def children(): List[CST] = Nil

  def resolve(context: ResolveExprContext): Unit = {}
}

final case class RHSThis() extends ExpressionRHS {
  override def children(): List[CST] = Nil

  def resolve(context: ResolveExprContext): Unit = {}
}

final case class RHSNew(nonWildcardTypeArguments: Option[NonWildcardTypeArguments], innerCreator: InnerCreator) extends ExpressionRHS {
  override def children(): List[CST] = List[CST]() ++ nonWildcardTypeArguments ++ List(innerCreator)

  def resolve(context: ResolveExprContext): Unit = {}
}

final case class RHSSuper(superSuffix: SuperSuffix) extends ExpressionRHS {
  override def children(): List[CST] = superSuffix :: Nil

  def resolve(context: ResolveExprContext): Unit = {}
}

final case class RHSExplicitGenericInvocation(explicitGenericInvocation: ExplicitGenericInvocation) extends ExpressionRHS {
  override def children(): List[CST] = explicitGenericInvocation :: Nil

  def resolve(context: ResolveExprContext): Unit = {}
}

final case class RHSArrayExpression(expression: Expression) extends ExpressionRHS {
  override def children(): List[CST] = expression :: Nil

  def resolve(context: ResolveExprContext): Unit = expression.resolve(context)
}

object Expression {
  def construct(from: ExpressionContext, context: ConstructContext): Expression = {
    val cst =
      from match {
        case alt1: Alt1ExpressionContext =>
          val lHSExpression = Expression.construct(alt1.expression(), context)
          lHSExpression match {
            case PrimaryExpression(Identifier(id)) =>
              QName(id :: alt1.id().getText :: Nil)
            case QName(ids) =>
              QName(ids ::: (alt1.id().getText :: Nil))
            case _ =>
              LHSExpression(lHSExpression, RHSId(alt1.id().getText))
          }
        case alt2: Alt2ExpressionContext =>
          LHSExpression(Expression.construct(alt2.expression(), context), RHSThis())
        case alt3: Alt3ExpressionContext =>
          LHSExpression(Expression.construct(alt3.expression(), context), RHSNew(
            if (alt3.nonWildcardTypeArguments() != null)
              Some(NonWildcardTypeArguments.construct(alt3.nonWildcardTypeArguments(), context))
            else
              None,
            InnerCreator.construct(alt3.innerCreator(), context)
          ))
        case alt4: Alt4ExpressionContext =>
          LHSExpression(Expression.construct(alt4.expression(), context), RHSSuper(
            SuperSuffix.construct(alt4.superSuffix(), context)
          ))
        case alt5: Alt5ExpressionContext =>
          LHSExpression(Expression.construct(alt5.expression(), context), RHSExplicitGenericInvocation(
            ExplicitGenericInvocation.construct(alt5.explicitGenericInvocation(), context)
          ))
        case alt6: Alt6ExpressionContext =>
          LHSExpression(Expression.construct(alt6.expression(0), context), RHSArrayExpression(
            Expression.construct(alt6.expression(1), context)
          ))
        case alt7: FunctionCallExpressionContext =>
          FunctionCall(Expression.construct(alt7.expression, context),
            if (alt7.expressionList() != null) {
              val expression: Seq[ExpressionContext] = alt7.expressionList().expression().asScala
              Expression.construct(expression.toList, context)
            } else {
              List()
            }
          )
        case alt8: Alt8ExpressionContext =>
          NewExpression(Creator.construct(alt8.creator(), context))
        case alt9: Alt9ExpressionContext =>
          TypeExpression(TypeRef.construct(alt9.typeRef(), context), Expression.construct(alt9.expression(), context))
        case alt10: Alt10ExpressionContext =>
          PostOpExpression(Expression.construct(alt10.expression(), context), alt10.getChild(1).getText)
        case alt11: Alt11ExpressionContext =>
          PreOpExpression(Expression.construct(alt11.expression(), context), alt11.getChild(0).getText)
        case alt12: Alt12ExpressionContext =>
          PostOpExpression(Expression.construct(alt12.expression(), context), alt12.getChild(0).getText)
        case alt13: Alt13ExpressionContext =>
          BinaryExpression(Expression.construct(alt13.expression(0), context), Expression.construct(alt13.expression(1), context), alt13.getChild(1).getText)
        case alt14: Alt14ExpressionContext =>
          BinaryExpression(Expression.construct(alt14.expression(0), context), Expression.construct(alt14.expression(1), context), alt14.getChild(1).getText)
        case alt15: Alt15ExpressionContext =>
          BinaryExpression(Expression.construct(alt15.expression(0), context), Expression.construct(alt15.expression(1), context), alt15.getChild(1).getText)
        case alt16: Alt16ExpressionContext =>
          BinaryExpression(Expression.construct(alt16.expression(0), context), Expression.construct(alt16.expression(1), context), alt16.getChild(1).getText)
        case alt17: Alt17ExpressionContext =>
          InstanceOfExpression(Expression.construct(alt17.expression(), context), TypeRef.construct(alt17.typeRef(), context))
        case alt18: Alt18ExpressionContext =>
          BinaryExpression(Expression.construct(alt18.expression(0), context), Expression.construct(alt18.expression(1), context), alt18.getChild(1).getText)
        case alt19: Alt19ExpressionContext =>
          BinaryExpression(Expression.construct(alt19.expression(0), context), Expression.construct(alt19.expression(1), context), alt19.getChild(1).getText)
        case alt20: Alt20ExpressionContext =>
          BinaryExpression(Expression.construct(alt20.expression(0), context), Expression.construct(alt20.expression(1), context), alt20.getChild(1).getText)
        case alt21: Alt21ExpressionContext =>
          BinaryExpression(Expression.construct(alt21.expression(0), context), Expression.construct(alt21.expression(1), context), alt21.getChild(1).getText)
        case alt22: Alt22ExpressionContext =>
          BinaryExpression(Expression.construct(alt22.expression(0), context), Expression.construct(alt22.expression(1), context), alt22.getChild(1).getText)
        case alt23: Alt23ExpressionContext =>
          BinaryExpression(Expression.construct(alt23.expression(0), context), Expression.construct(alt23.expression(1), context), alt23.getChild(1).getText)
        case alt24: Alt24ExpressionContext =>
          QueryExpression(Expression.construct(alt24.expression(0), context), Expression.construct(alt24.expression(1), context), Expression.construct(alt24.expression(2), context))
        case alt25: Alt25ExpressionContext =>
          BinaryExpression(Expression.construct(alt25.expression(0), context), Expression.construct(alt25.expression(1), context), alt25.getChild(1).getText)
        case alt26: Alt26ExpressionContext =>
          PrimaryExpression(Primary.construct(alt26.primary(), context))
      }
    cst.withContext(from, context)
  }

  def construct(expression: List[ExpressionContext], context: ConstructContext): List[Expression] = {
    expression.map(x => Expression.construct(x, context))
  }
}

final case class NonWildcardTypeArguments(typeList: Seq[TypeName]) extends CST {
  override def children(): List[CST] = Nil
}

object NonWildcardTypeArguments {
  def construct(from: NonWildcardTypeArgumentsContext, context: ConstructContext): NonWildcardTypeArguments = {
    NonWildcardTypeArguments(TypeList.construct(from.typeList(), context)).withContext(from, context)
  }
}

final case class NonWildcardTypeArgumentsOrDiamond(nonWildcardTypeArguments: Option[NonWildcardTypeArguments]) extends CST {
  override def children(): List[CST] = List[CST]() ++ nonWildcardTypeArguments
}

object NonWildcardTypeArgumentsOrDiamond {
  def construct(from: NonWildcardTypeArgumentsOrDiamondContext, context: ConstructContext): NonWildcardTypeArgumentsOrDiamond = {
    NonWildcardTypeArgumentsOrDiamond(
      if (from.nonWildcardTypeArguments() != null)
        Some(NonWildcardTypeArguments.construct(from.nonWildcardTypeArguments(), context))
      else None).withContext(from, context)
  }
}

final case class TypeArgumentsOrDiamond(typeArguments: Option[TypeArguments]) extends CST {
  override def children(): List[CST] = List[CST]() ++ typeArguments
}

object TypeArgumentsOrDiamond {
  def construct(from: TypeArgumentsOrDiamondContext, context: ConstructContext): TypeArgumentsOrDiamond = {
    TypeArgumentsOrDiamond(
      if (from.typeArguments() != null)
        Some(TypeArguments.construct(from.typeArguments(), context))
      else
        None
    ).withContext(from, context)
  }
}

final case class TypeArguments(typeList: List[TypeName]) extends CST {
  override def children(): List[CST] = Nil
}

object TypeArguments {
  def construct(from: TypeArgumentsContext, context: ConstructContext): TypeArguments = {
    val types: Seq[TypeRefContext] = from.typeList().typeRef().asScala
    TypeArguments(TypeRef.construct(types.toList, context)).withContext(from, context)
  }
}

final case class ClassCreatorRest(arguments: List[Expression], expressionList: List[Expression]) extends CST {
  override def children(): List[CST] = arguments ::: expressionList
}

object ClassCreatorRest {
  def construct(from: ClassCreatorRestContext, context: ConstructContext): ClassCreatorRest = {
    ClassCreatorRest(
      if (from.arguments() != null)
        Arguments.construct(from.arguments(), context)
      else
        List(),
      if (from.expressionList() != null) {
        val expressions: Seq[ExpressionContext] = from.expressionList().expression().asScala
        Expression.construct(expressions.toList, context)
      } else {
        List()
      }
    ).withContext(from, context)
  }
}

final case class ArrayCreatorRest(arraySubs: Int, arrayInitializer: Option[ArrayInitializer],
                                  expressions: List[Expression]) extends CST {
  override def children(): List[CST] = List[CST]() ++ arrayInitializer ++ expressions
}

object ArrayCreatorRest {
  def construct(from: ArrayCreatorRestContext, context: ConstructContext): ArrayCreatorRest = {
    if (from.arrayInitializer() != null) {
      val arraySubs = 1 + from.arraySubscripts().getText.count(_ == '[')
      ArrayCreatorRest(arraySubs, Some(ArrayInitializer.construct(from.arrayInitializer(), context)), List()).withContext(from, context)
    } else {
      val arraySubs = from.arraySubscripts().getText.count(_ == '[')
      val expressions: Seq[ExpressionContext] = from.expression().asScala
      ArrayCreatorRest(arraySubs, None, Expression.construct(expressions.toList, context)).withContext(from, context)
    }
  }
}

final case class ArrayInitializer(variableInitializers: List[VariableInitializer]) extends CST {
  override def children(): List[CST] = variableInitializers
}

object ArrayInitializer {
  def construct(from: ArrayInitializerContext, context: ConstructContext): ArrayInitializer = {
    val initializers: Seq[VariableInitializerContext] = from.variableInitializer().asScala
    ArrayInitializer(VariableInitializer.construct(initializers.toList, context)).withContext(from, context)
  }
}

object Arguments {
  def construct(from: ArgumentsContext, context: ConstructContext): List[Expression] = {
    if (from.expressionList() != null) {
      val expressions: Seq[ExpressionContext] = from.expressionList().expression().asScala
      Expression.construct(expressions.toList, context)
    } else {
      List()
    }
  }
}

final case class MapCreatorRest(pairs: List[MapCreatorRestPair]) extends CST {
  override def children(): List[CST] = pairs
}

object MapCreatorRest {
  def construct(from: MapCreatorRestContext, context: ConstructContext): MapCreatorRest = {
    val pairs: Seq[MapCreatorRestPairContext] = from.mapCreatorRestPair().asScala
    MapCreatorRest(MapCreatorRestPair.construct(pairs.toList, context)).withContext(from, context)
  }
}

final case class MapCreatorRestPair(from: IdOrExpression, to: LiteralOrExpression) extends CST {
  override def children(): List[CST] = from :: to :: Nil
}

object MapCreatorRestPair {
  def construct(aList: List[MapCreatorRestPairContext], context: ConstructContext): List[MapCreatorRestPair] = {
    aList.map(x => MapCreatorRestPair.construct(x, context))
  }

  def construct(from: MapCreatorRestPairContext, context: ConstructContext): MapCreatorRestPair = {
    MapCreatorRestPair(
      IdOrExpression.construct(from.idOrExpression(), context),
      LiteralOrExpression.construct(from.literalOrExpression(), context)
    ).withContext(from, context)
  }
}

final case class SetCreatorRest(parts: List[LiteralOrExpression]) extends CST {
  override def children(): List[CST] = parts
}

object SetCreatorRest {
  def construct(from: SetCreatorRestContext, context: ConstructContext): SetCreatorRest = {
    val parts: Seq[LiteralOrExpressionContext] = from.literalOrExpression().asScala
    SetCreatorRest(LiteralOrExpression.construct(parts.toList, context)).withContext(from, context)
  }
}

final case class IdOrExpression(id: Option[String], expression: Option[Expression]) extends CST {
  override def children(): List[CST] = List[CST]() ++ expression
}

object IdOrExpression {
  def construct(from: IdOrExpressionContext, context: ConstructContext): IdOrExpression = {
    IdOrExpression(
      if (from.id() != null)
        Some(from.id().getText)
      else
        None,
      if (from.expression() != null)
        Some(Expression.construct(from.expression(), context))
      else
        None
    ).withContext(from, context)
  }
}

final case class LiteralOrExpression(literal: Option[Literal], expression: Option[Expression]) extends CST {
  override def children(): List[CST] = literal match {
    case Some(x) => x :: Nil
    case None => expression match {
      case Some(x) => x :: Nil
      case None => Nil
    }
  }
}

object LiteralOrExpression {
  def construct(aList: List[LiteralOrExpressionContext], context: ConstructContext): List[LiteralOrExpression] = {
    aList.map(x => LiteralOrExpression.construct(x, context))
  }

  def construct(from: LiteralOrExpressionContext, context: ConstructContext): LiteralOrExpression = {
    LiteralOrExpression(
      if (from.literal() != null)
        Some(Literal.construct(from.literal(), context))
      else
        None,
      if (from.expression() != null)
        Some(Expression.construct(from.expression(), context))
      else
        None
    ).withContext(from, context)
  }
}

final case class InnerCreator(nonWildcardTypeArgumentsOrDiamond: Option[NonWildcardTypeArgumentsOrDiamond],
                              classCreatorRest: ClassCreatorRest) extends CST {
  override def children(): List[CST] = List[CST]() ++ nonWildcardTypeArgumentsOrDiamond ++ List(classCreatorRest)
}

object InnerCreator {
  def construct(from: InnerCreatorContext, context: ConstructContext): InnerCreator = {
    InnerCreator(
      if (from.nonWildcardTypeArgumentsOrDiamond() != null)
        Some(NonWildcardTypeArgumentsOrDiamond.construct(from.nonWildcardTypeArgumentsOrDiamond(), context))
      else
        None,
      ClassCreatorRest.construct(from.classCreatorRest(), context)
    ).withContext(from, context)
  }
}

sealed abstract class CreatedName extends CST

final case class IdCreatedName(idPairs: List[IdCreatedNamePair]) extends CreatedName {
  override def children(): List[CST] = idPairs
}

object CreatedName {
  def construct(from: CreatedNameContext, context: ConstructContext): CreatedName = {
    val pairs: Seq[IdCreatedNamePairContext] = from.idCreatedNamePair().asScala
    IdCreatedName(IdCreatedNamePair.construct(pairs.toList, context)).withContext(from, context)
  }
}

final case class IdCreatedNamePair(id: String, typeArgumentsOrDiamond: Option[TypeArgumentsOrDiamond]) extends CST {
  override def children(): List[CST] = List[CST]() ++ typeArgumentsOrDiamond
}

object IdCreatedNamePair {
  def construct(aList: List[IdCreatedNamePairContext], context: ConstructContext): List[IdCreatedNamePair] = {
    aList.map(x => IdCreatedNamePair.construct(x, context))
  }

  def construct(from: IdCreatedNamePairContext, context: ConstructContext): IdCreatedNamePair = {
    IdCreatedNamePair(from.id.getText,
      if (from.typeArgumentsOrDiamond() != null)
        Some(TypeArgumentsOrDiamond.construct(from.typeArgumentsOrDiamond(), context))
      else
        None
    ).withContext(from, context)
  }
}

final case class SuperSuffix(id: Option[String], arguments: List[Expression]) extends CST {
  override def children(): List[CST] = arguments
}

object SuperSuffix {
  def construct(from: SuperSuffixContext, context: ConstructContext): SuperSuffix = {
    SuperSuffix(
      if (from.id() != null)
        Some(from.id.getText)
      else
        None,
      if (from.arguments() != null)
        Arguments.construct(from.arguments(), context)
      else
        List()
    ).withContext(from, context)
  }
}

final case class ExplicitGenericInvocationSuffix(supperSuffix: Option[SuperSuffix], id: Option[String], arguments: List[Expression]) extends CST {
  override def children(): List[CST] = List[CST]() ++ supperSuffix ++ arguments
}

object ExplicitGenericInvocationSuffix {
  def construct(from: ExplicitGenericInvocationSuffixContext, context: ConstructContext): ExplicitGenericInvocationSuffix = {
    if (from.superSuffix() != null)
      ExplicitGenericInvocationSuffix(Some(SuperSuffix.construct(from.superSuffix(), context)), None, List()).withContext(from, context)
    else
      ExplicitGenericInvocationSuffix(None, Some(from.id.getText), Arguments.construct(from.arguments(), context)).withContext(from, context)
  }
}

final case class ExplicitGenericInvocation(nonWildcardTypeArguments: NonWildcardTypeArguments,
                                           explicitGenericInvocationSuffix: ExplicitGenericInvocationSuffix) extends CST {
  override def children(): List[CST] = nonWildcardTypeArguments :: explicitGenericInvocationSuffix :: Nil
}

object ExplicitGenericInvocation {
  def construct(from: ExplicitGenericInvocationContext, context: ConstructContext): ExplicitGenericInvocation = {
    ExplicitGenericInvocation(
      NonWildcardTypeArguments.construct(from.nonWildcardTypeArguments(), context),
      ExplicitGenericInvocationSuffix.construct(from.explicitGenericInvocationSuffix(), context)
    ).withContext(from, context)
  }
}

sealed abstract class Creator extends CST

final case class Alt1Creator(nonWildcardTypeArguments: NonWildcardTypeArguments, createdName: CreatedName,
                             classCreatorRest: ClassCreatorRest) extends Creator {
  override def children(): List[CST] = nonWildcardTypeArguments :: createdName :: classCreatorRest :: Nil
}

final case class Alt2Creator(createdName: CreatedName, arrayCreatorRest: Option[ArrayCreatorRest], classCreatorRest: Option[ClassCreatorRest],
                             mapCreatorRest: Option[MapCreatorRest], setCreatorRest: Option[SetCreatorRest]) extends Creator {
  override def children(): List[CST] = List[CST](createdName) ++ arrayCreatorRest ++ classCreatorRest ++ mapCreatorRest ++ setCreatorRest
}

object Creator {
  def construct(from: CreatorContext, context: ConstructContext): Creator = {
    from match {
      case alt1: Alt1CreatorContext =>
        Alt1Creator(
          NonWildcardTypeArguments.construct(alt1.nonWildcardTypeArguments(), context),
          CreatedName.construct(alt1.createdName(), context),
          ClassCreatorRest.construct(alt1.classCreatorRest(), context)
        ).withContext(from, context)
      case alt2: Alt2CreatorContext =>
        Alt2Creator(
          CreatedName.construct(alt2.createdName(), context),
          if (alt2.arrayCreatorRest() != null) Some(ArrayCreatorRest.construct(alt2.arrayCreatorRest(), context)) else None,
          if (alt2.classCreatorRest() != null) Some(ClassCreatorRest.construct(alt2.classCreatorRest(), context)) else None,
          if (alt2.mapCreatorRest() != null) Some(MapCreatorRest.construct(alt2.mapCreatorRest(), context)) else None,
          if (alt2.setCreatorRest() != null) Some(SetCreatorRest.construct(alt2.setCreatorRest(), context)) else None
        ).withContext(from, context)
    }
  }
}

sealed abstract class Literal() extends CST {
  override def children(): List[CST] = Nil
}

final case class IntegerLit(value: String) extends Literal {
  override def getType(ctx: TypeContext): TypeName =
    if (value.endsWith("l") || value.endsWith("L"))
      TypeName.Long
    else
      TypeName.Integer
}

final case class NumberLit(value: String) extends Literal {
  override def getType(ctx: TypeContext): TypeName =
    if (value.length() > 50)
      TypeName.Double
    else
      TypeName.Decimal
}

final case class StringLit(value: String) extends Literal {
  override def getType(ctx: TypeContext): TypeName = TypeName.String
}

final case class BooleanLit(value: String) extends Literal {
  override def getType(ctx: TypeContext): TypeName = TypeName.Boolean
}

final case class NullLit() extends Literal {
  override def getType(ctx: TypeContext): TypeName = TypeName.Null
}

object Literal {
  def construct(from: LiteralContext, context: ConstructContext): Literal = {
    val cst =
      if (from.IntegerLiteral() != null)
        IntegerLit(from.IntegerLiteral().getText)
      else if (from.NumberLiteral() != null)
        NumberLit(from.NumberLiteral().getText)
      else if (from.StringLiteral() != null)
        StringLit(from.StringLiteral().getText)
      else if (from.BooleanLiteral() != null)
        BooleanLit(from.BooleanLiteral().getText)
      else
        NullLit()
    cst.withContext(from, context)
  }
}

sealed abstract class Primary extends CST

final case class ExpressionPrimary(expression: Expression) extends Primary {
  override def children(): List[CST] = expression :: Nil

  override def getType(ctx: TypeContext): TypeName = expression.getType(ctx)
}

final case class ThisPrimary() extends Primary {
  override def children(): List[CST] = Nil

  override def getType(ctx: TypeContext): TypeName = ctx.thisType
}

final case class SuperPrimary() extends Primary {
  override def children(): List[CST] = Nil

  override def getType(ctx: TypeContext): TypeName = ctx.superType
}

final case class LiteralPrimary(literal: Literal) extends Primary {
  override def children(): List[CST] = literal :: Nil

  override def getType(ctx: TypeContext): TypeName = literal.getType(ctx)
}

final case class Identifier(text: String) extends Primary {
  override def children(): List[CST] = Nil

  override def getType(ctx: TypeContext): TypeName = ctx.getIdentifierType(text)
}

final case class TypeRefClassPrimary(typeRef: TypeName) extends Primary {
  override def children(): List[CST] = Nil

  override def getType(ctx: TypeContext): TypeName = typeRef.asClassOf
}

final case class VoidClassPrimary() extends Primary {
  override def children(): List[CST] = Nil

  override def getType(ctx: TypeContext): TypeName = TypeName.Void
}

// Fake node for linking variable refs to their declarations
final case class VarRef(varDeclaration: VarDeclaration) extends Primary {
  override def children(): List[CST] = List(varDeclaration.name)
}

final case class MethodPrimary(nonWildcardTypeArguments: NonWildcardTypeArguments,
                               explicitGenericInvocationSuffix: Option[ExplicitGenericInvocationSuffix], arguments: List[Expression]) extends Primary {
  override def children(): List[CST] = List[CST](nonWildcardTypeArguments) ++ explicitGenericInvocationSuffix ++ arguments
}

final case class SOQL(soql: String) extends Primary {
  override def children(): List[CST] = Nil
}

object Primary {
  def construct(from: PrimaryContext, context: ConstructContext): Primary = {
    val cst =
      from match {
        case alt1: Alt1PrimaryContext =>
          ExpressionPrimary(Expression.construct(alt1.expression(), context))
        case _: Alt2PrimaryContext =>
          ThisPrimary()
        case _: Alt3PrimaryContext =>
          SuperPrimary()
        case alt4: Alt4PrimaryContext =>
          LiteralPrimary(Literal.construct(alt4.literal(), context))
        case alt5: Alt5PrimaryContext =>
          Identifier(alt5.id().getText)
        case alt6: Alt6PrimaryContext =>
          TypeRefClassPrimary(TypeRef.construct(alt6.typeRef(), context))
        case _: Alt7PrimaryContext =>
          VoidClassPrimary()
        case alt8: Alt8PrimaryContext =>
          MethodPrimary(
            NonWildcardTypeArguments.construct(alt8.nonWildcardTypeArguments(), context),
            if (alt8.explicitGenericInvocationSuffix() != null)
              Some(ExplicitGenericInvocationSuffix.construct(alt8.explicitGenericInvocationSuffix(), context))
            else
              None,
            if (alt8.arguments() != null)
              Arguments.construct(alt8.arguments(), context)
            else
              List()
          )
        case alt9: Alt9PrimaryContext =>
          SOQL(alt9.soqlLiteral().getText)
      }
    cst.withContext(from, context)
  }
}
