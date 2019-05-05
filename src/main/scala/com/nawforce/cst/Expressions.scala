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

import com.nawforce.parsers.ApexParser._
import com.nawforce.types.TypeName

import scala.collection.JavaConverters._
import scala.collection.mutable

trait ExpressionRHS extends CST {
  def verify(imports: mutable.Set[TypeName]): Unit
  def resolve(context: ResolveExprContext)
}

sealed abstract class Expression extends CST {
  def verify(imports: mutable.Set[TypeName]): Unit
  def resolve(context: ResolveExprContext)
}

final case class LHSExpression(lhs: Expression, rhs: ExpressionRHS) extends Expression {
  override def children(): List[CST] = lhs :: rhs :: Nil

  override def verify(imports: mutable.Set[TypeName]): Unit = {
    lhs.verify(imports)
    rhs.verify(imports)
  }

  override def resolve(context: ResolveExprContext): Unit = {
    lhs.resolve(context)
    rhs.resolve(context)
  }
}

final case class FunctionCall(callee: Expression, arguments: List[Expression]) extends Expression {
  override def children(): List[CST] = callee :: arguments

  override def verify(imports: mutable.Set[TypeName]): Unit = {
    callee.verify(imports)
    arguments.foreach(_.verify(imports))
  }

  override def resolve(context: ResolveExprContext): Unit = {
    callee.resolve(context)
    arguments.foreach(_.resolve(context))
  }
}

final case class NewExpression(creator: Creator) extends Expression {
  override def children(): List[CST] = creator :: Nil

  override def verify(imports: mutable.Set[TypeName]): Unit = {
    creator.verify(imports)
  }

  def resolve(context: ResolveExprContext): Unit = {}
}

final case class TypeExpression(typeRef: TypeName, expression: Expression) extends Expression {
  override def children(): List[CST] = expression :: Nil

  override def verify(imports: mutable.Set[TypeName]): Unit = {
    imports.add(typeRef)
    expression.verify(imports)
  }

  def resolve(context: ResolveExprContext): Unit = expression.resolve(context)
}

final case class PostOpExpression(expression: Expression, op: String) extends Expression {
  override def children(): List[CST] = expression :: Nil

  override def verify(imports: mutable.Set[TypeName]): Unit = {
    expression.verify(imports)
  }

  def resolve(context: ResolveExprContext): Unit = expression.resolve(context)
}

final case class PreOpExpression(expression: Expression, op: String) extends Expression {
  override def children(): List[CST] = expression :: Nil

  override def verify(imports: mutable.Set[TypeName]): Unit = {
    expression.verify(imports)
  }

  def resolve(context: ResolveExprContext): Unit = expression.resolve(context)
}

final case class BinaryExpression(lhs: Expression, rhs: Expression, op: String) extends Expression {
  override def children(): List[CST] = lhs :: rhs :: Nil

  override def verify(imports: mutable.Set[TypeName]): Unit = {
    lhs.verify(imports)
    rhs.verify(imports)
  }

  override def resolve(context: ResolveExprContext): Unit = {
    lhs.resolve(context)
    rhs.resolve(context)
  }
}

final case class InstanceOfExpression(expression: Expression, typeRef: TypeName) extends Expression {
  override def children(): List[CST] = expression :: Nil

  override def verify(imports: mutable.Set[TypeName]): Unit = {
    imports.add(typeRef)
    expression.verify(imports)
  }

  override def resolve(context: ResolveExprContext): Unit = expression.resolve(context)
}

final case class QueryExpression(query: Expression, lhs: Expression, rhs: Expression) extends Expression {
  override def children(): List[CST] = query :: lhs :: rhs :: Nil

  override def verify(imports: mutable.Set[TypeName]): Unit = {
    query.verify(imports)
    lhs.verify(imports)
    rhs.verify(imports)
  }

  override def resolve(context: ResolveExprContext): Unit = {
    query.resolve(context)
    lhs.resolve(context)
    rhs.resolve(context)
  }
}

final case class PrimaryExpression(var primary: Primary) extends Expression {
  override def children(): List[CST] = primary :: Nil

  override def verify(imports: mutable.Set[TypeName]): Unit = primary.verify(imports)

  override def resolve(context: ResolveExprContext): Unit = {
    // Link variable reference
    /* TODO: Do we need this
    primary match {
      case Id(name) =>
        context.getVarDeclaration(name).foreach(declaration => {
          primary = VarRef(declaration)
        })
      case _ =>
    }*/
  }
}

final case class RHSId(id: String) extends ExpressionRHS {
  override def children(): List[CST] = Nil

  override def verify(imports: mutable.Set[TypeName]): Unit = {}

  override def resolve(context: ResolveExprContext): Unit = {}
}

final case class RHSArrayExpression(expression: Expression) extends ExpressionRHS {
  override def children(): List[CST] = expression :: Nil

  override def verify(imports: mutable.Set[TypeName]): Unit = {
    expression.verify(imports)
  }

  override def resolve(context: ResolveExprContext): Unit = expression.resolve(context)
}

object Expression {
  def construct(from: ExpressionContext, context: ConstructContext): Expression = {
    val cst =
      from match {
        case alt1: Alt1ExpressionContext =>
          val lHSExpression = Expression.construct(alt1.expression(), context)
          // TODO: Do we need this
          /*
          lHSExpression match {
            case PrimaryExpression(Id(id)) =>
              QName(id :: alt1.id().getText :: Nil)
            case QName(ids) =>
              QName(ids ::: (alt1.id().getText :: Nil))
            case _ =>
              LHSExpression(lHSExpression, RHSId(alt1.id().getText))
          }*/
          lHSExpression
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
        case alt8: NewExpressionContext =>
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

  def verify(imports: mutable.Set[TypeName]): Unit = {
    arguments.foreach(_.verify(imports))
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

  def verify(imports: mutable.Set[TypeName]): Unit = {
    expressions.foreach(_.verify(imports))
    arrayInitializer.foreach(_.verify(imports))
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

  def verify(imports: mutable.Set[TypeName]): Unit = {
    variableInitializers.foreach(_.verify(imports))
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

  def verify(imports: mutable.Set[TypeName]): Unit = {
    pairs.foreach(_.verify(imports))
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

  def verify(imports: mutable.Set[TypeName]): Unit = {
    from.verify(imports)
    to.verify(imports)
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

  def verify(imports: mutable.Set[TypeName]): Unit = {
    parts.foreach(_.verify(imports))
  }
}

object SetCreatorRest {
  def construct(from: SetCreatorRestContext, context: ConstructContext): SetCreatorRest = {
    val parts: Seq[ExpressionContext] = from.expression().asScala
    SetCreatorRest(Expression.construct(parts.toList, context)).withContext(from, context)
  }
}
