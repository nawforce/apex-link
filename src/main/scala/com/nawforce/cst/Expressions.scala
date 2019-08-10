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
 3. the name of the author may not be used to endorse or promote products
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
import com.nawforce.types.{TypeDeclaration, TypeName}
import com.nawforce.utils.DotName

import scala.collection.JavaConverters._

sealed abstract class Expression extends CST {
  def verify(context: ExpressionVerifyContext): Unit
  def verify(context: BlockVerifyContext): Unit = {
    verify(new ExpressionVerifyContext(context))
  }
}

final case class IdExpression(expression: Expression, id: Id) extends Expression {
  override def children(): List[CST] = expression :: id :: Nil

  override def verify(context: ExpressionVerifyContext): Unit = {
    val dotName = Expression.asDotName(this)
    if (dotName.nonEmpty) {
      Expression.verify(dotName.get, this, context)
    } else {
      expression.verify(context)
    }
  }
}

final case class ArrayExpression(expression: Expression, arrayExpression: Expression) extends Expression {
  override def children(): List[CST] = expression :: arrayExpression :: Nil

  override def verify(context: ExpressionVerifyContext): Unit = {
    expression.verify(context)
    arrayExpression.verify(context)
  }
}

final case class FunctionCall(callee: Expression, arguments: List[Expression]) extends Expression {
  override def children(): List[CST] = callee :: arguments

  override def verify(context: ExpressionVerifyContext): Unit = {
    callee.verify(context)
    arguments.foreach(_.verify(context))
  }
}

final case class NewExpression(creator: Creator) extends Expression {
  override def children(): List[CST] = creator :: Nil

  override def verify(context: ExpressionVerifyContext): Unit = {
    creator.verify(context)
  }
}

final case class CastExpression(typeName: TypeName, expression: Expression) extends Expression {
  override def children(): List[CST] = expression :: Nil

  override def verify(context: ExpressionVerifyContext): Unit = {
    val castType = context.getTypeAndAddDependency(typeName)
    if (castType.isEmpty)
      Org.missingType(location, typeName)
    expression.verify(context)
  }
}

final case class PostOpExpression(expression: Expression, op: String) extends Expression {
  override def children(): List[CST] = expression :: Nil

  override def verify(context: ExpressionVerifyContext): Unit = {
    expression.verify(context)
  }
}

final case class PreOpExpression(expression: Expression, op: String) extends Expression {
  override def children(): List[CST] = expression :: Nil

  override def verify(context: ExpressionVerifyContext): Unit = {
    expression.verify(context)
  }
}

final case class NegExpression(expression: Expression, op: String) extends Expression {
  override def children(): List[CST] = expression :: Nil

  override def verify(context: ExpressionVerifyContext): Unit = {
    expression.verify(context)
  }
}

final case class BinaryExpression(lhs: Expression, rhs: Expression, op: String) extends Expression {
  override def children(): List[CST] = lhs :: rhs :: Nil

  override def verify(context: ExpressionVerifyContext): Unit = {
    lhs.verify(context)
    rhs.verify(context)
  }
}

final case class InstanceOfExpression(expression: Expression, typeName: TypeName) extends Expression {
  override def children(): List[CST] = expression :: Nil

  override def verify(context: ExpressionVerifyContext): Unit = {
    val instanceOfType = context.getTypeAndAddDependency(typeName)
    if (instanceOfType.isEmpty)
      Org.missingType(location, typeName)
    expression.verify(context)
  }
}

final case class QueryExpression(query: Expression, lhs: Expression, rhs: Expression) extends Expression {
  override def children(): List[CST] = query :: lhs :: rhs :: Nil

  override def verify(context: ExpressionVerifyContext): Unit = {
    query.verify(context)
    lhs.verify(context)
    rhs.verify(context)
  }
}

final case class PrimaryExpression(var primary: Primary) extends Expression {
  override def children(): List[CST] = primary :: Nil

  override def verify(context: ExpressionVerifyContext): Unit = {
    primary.verify(context)
  }
}

object Expression {
  def construct(from: ExpressionContext, context: ConstructContext): Expression = {
    val cst =
      from match {
        case expr: IdExpressionContext =>
          IdExpression(
            Expression.construct(expr.expression(), context),
            Id.construct(expr.id(), context)
          )
        case expr: ArrayExpressionContext =>
          ArrayExpression(
            Expression.construct(expr.expression(0), context),
            Expression.construct(expr.expression(1), context)
          )
        case expr: FunctionCallExpressionContext =>
          FunctionCall(
            Expression.construct(expr.expression, context),
            if (expr.expressionList() != null) {
              val expression: Seq[ExpressionContext] = expr.expressionList().expression().asScala
              Expression.construct(expression.toList, context)
            } else {
              List()
            }
          )
        case expr: NewExpressionContext =>
          NewExpression(Creator.construct(expr.creator(), context))
        case expr: CastExpressionContext =>
          CastExpression(TypeRef.construct(expr.typeRef(), context), Expression.construct(expr.expression(), context))
        case expr: PostOpExpressionContext =>
          PostOpExpression(Expression.construct(expr.expression(), context), expr.getChild(1).getText)
        case expr: PreOpExpressionContext =>
          PreOpExpression(Expression.construct(expr.expression(), context), expr.getChild(0).getText)
        case expr: NegExpressionContext =>
          NegExpression(Expression.construct(expr.expression(), context), expr.getChild(0).getText)
        case expr: Arth1ExpressionContext =>
          BinaryExpression(Expression.construct(expr.expression(0), context),
            Expression.construct(expr.expression(1), context), expr.getChild(1).getText)
        case expr: Arth2ExpressionContext =>
          BinaryExpression(Expression.construct(expr.expression(0), context),
            Expression.construct(expr.expression(1), context), expr.getChild(1).getText)
        case expr: Cmp1ExpressionContext =>
          BinaryExpression(Expression.construct(expr.expression(0), context),
            Expression.construct(expr.expression(1), context), expr.getChild(1).getText)
        case expr: Cmp2ExpressionContext =>
          BinaryExpression(Expression.construct(expr.expression(0), context),
            Expression.construct(expr.expression(1), context), expr.getChild(1).getText)
        case expr: InstanceOfExpressionContext =>
          InstanceOfExpression(Expression.construct(expr.expression(), context),
            TypeRef.construct(expr.typeRef(), context))
        case expr: EqualityExpressionContext =>
          BinaryExpression(Expression.construct(expr.expression(0), context),
            Expression.construct(expr.expression(1), context), expr.getChild(1).getText)
        case expr: BitAndExpressionContext =>
          BinaryExpression(Expression.construct(expr.expression(0), context),
            Expression.construct(expr.expression(1), context), expr.getChild(1).getText)
        case expr: BitNotExpressionContext =>
          BinaryExpression(Expression.construct(expr.expression(0), context),
            Expression.construct(expr.expression(1), context), expr.getChild(1).getText)
        case expr: BitOrExpressionContext =>
          BinaryExpression(Expression.construct(expr.expression(0), context),
            Expression.construct(expr.expression(1), context), expr.getChild(1).getText)
        case expr: LogAndExpressionContext =>
          BinaryExpression(Expression.construct(expr.expression(0), context),
            Expression.construct(expr.expression(1), context), expr.getChild(1).getText)
        case expr: LogOrExpressionContext =>
          BinaryExpression(Expression.construct(expr.expression(0), context),
            Expression.construct(expr.expression(1), context), expr.getChild(1).getText)
        case expr: CondExpressionContext =>
          QueryExpression(Expression.construct(expr.expression(0), context),
            Expression.construct(expr.expression(1), context), Expression.construct(expr.expression(2), context))
        case expr: AssignExpressionContext =>
          BinaryExpression(Expression.construct(expr.expression(0), context),
            Expression.construct(expr.expression(1), context), expr.getChild(1).getText)
        case expr: PrimaryExpressionContext =>
          PrimaryExpression(Primary.construct(expr.primary(), context))
      }
    cst.withContext(from, context)
  }

  def construct(expression: List[ExpressionContext], context: ConstructContext): List[Expression] = {
    expression.map(x => Expression.construct(x, context))
  }

  def asDotName(expr: Expression) : Option[DotName] = {
    expr match {
      case idExpr: IdExpression =>
        asDotName(idExpr.expression).map(_.append(idExpr.id.name))
      case primaryExpression: PrimaryExpression =>
        primaryExpression.primary match {
          case idPrimary: IdPrimary => Some(DotName(idPrimary.id.name))
          case _ => None
        }
      case _ => None
    }
  }

  def verify(dotName: DotName, expr: Expression, context: ExpressionVerifyContext): Unit = {
    if (context.isVar(dotName.firstName))
      return

    val td = getType(dotName, context)
    if (td.isEmpty)
      Org.missingType(expr.location, TypeName(dotName.head.names.reverse))
  }

  def getType(dotName: DotName, context: ExpressionVerifyContext, outer: Option[TypeName]=None):
    Option[TypeDeclaration] = {
    val typeName = TypeName(dotName.firstName, Nil, outer)
    val td = context.getTypeAndAddDependency(typeName)
    if (td.isEmpty && dotName.names.size>1) {
      getType(dotName.tail, context, Some(typeName))
    } else {
      td
    }

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

final case class ClassCreatorRest(arguments: List[Expression]) extends CST {
  override def children(): List[CST] = arguments

  def verify(context: ExpressionVerifyContext): Unit = {
    arguments.foreach(_.verify(context))
  }
}

object ClassCreatorRest {
  def construct(from: ClassCreatorRestContext, context: ConstructContext): ClassCreatorRest = {
    ClassCreatorRest(Arguments.construct(from.arguments(), context)).withContext(from, context)
  }
}

final case class ArrayCreatorRest(expressions: Option[Expression], arrayInitializer: Option[ArrayInitializer],
                                  ) extends CST {
  override def children(): List[CST] = List[CST]() ++ expressions ++ arrayInitializer

  def verify(context: ExpressionVerifyContext): Unit = {
    expressions.foreach(_.verify(context))
    arrayInitializer.foreach(_.verify(context))
  }
}

object ArrayCreatorRest {
  def construct(from: ArrayCreatorRestContext, context: ConstructContext): ArrayCreatorRest = {
    ArrayCreatorRest(
      Option(from.expression()).map(Expression.construct(_, context)),
      Option(from.arrayInitializer()).map(ArrayInitializer.construct(_, context))
    )
  }
}

final case class ArrayInitializer(variableInitializers: List[VariableInitializer]) extends CST {
  override def children(): List[CST] = variableInitializers

  def verify(context: ExpressionVerifyContext): Unit = {
    variableInitializers.foreach(_.verify(context))
  }
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

  def verify(context: ExpressionVerifyContext): Unit = {
    pairs.foreach(_.verify(context))
  }
}

object MapCreatorRest {
  def construct(from: MapCreatorRestContext, context: ConstructContext): MapCreatorRest = {
    val pairs: Seq[MapCreatorRestPairContext] = from.mapCreatorRestPair().asScala
    MapCreatorRest(MapCreatorRestPair.construct(pairs.toList, context)).withContext(from, context)
  }
}

final case class MapCreatorRestPair(from: Expression, to: Expression) extends CST {
  override def children(): List[CST] = from :: to :: Nil

  def verify(context: ExpressionVerifyContext): Unit = {
    from.verify(context)
    to.verify(context)
  }
}

object MapCreatorRestPair {
  def construct(aList: List[MapCreatorRestPairContext], context: ConstructContext): List[MapCreatorRestPair] = {
    aList.map(x => MapCreatorRestPair.construct(x, context))
  }

  def construct(from: MapCreatorRestPairContext, context: ConstructContext): MapCreatorRestPair = {
    MapCreatorRestPair(
      Expression.construct(from.expression(0), context),
      Expression.construct(from.expression(1), context)
    ).withContext(from, context)
  }
}

final case class SetCreatorRest(parts: List[Expression]) extends CST {
  override def children(): List[CST] = parts

  def verify(context: ExpressionVerifyContext): Unit = {
    parts.foreach(_.verify(context))
  }
}

object SetCreatorRest {
  def construct(from: SetCreatorRestContext, context: ConstructContext): SetCreatorRest = {
    val parts: Seq[ExpressionContext] = from.expression().asScala
    SetCreatorRest(Expression.construct(parts.toList, context)).withContext(from, context)
  }
}
