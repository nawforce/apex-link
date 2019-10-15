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
import com.nawforce.names.{EncodedName, Name, TypeName}
import com.nawforce.parsers.ApexParser._
import com.nawforce.types._

import scala.collection.JavaConverters._

case class ExprContext(isStatic: Boolean, declaration: Option[TypeDeclaration]) {
  def isDefined: Boolean = declaration.nonEmpty && !declaration.exists(_.isAny)

  def typeDeclaration: TypeDeclaration = declaration.get
  def typeName: TypeName = typeDeclaration.typeName
}

object ExprContext {
  val empty = ExprContext(isStatic = false, None)
}

sealed abstract class Expression extends CST {
  def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext
  def verify(context: BlockVerifyContext): Unit = {
    verify(ExprContext(isStatic = false, context.thisType), new ExpressionVerifyContext(context))
  }
}

final case class DotExpression(expression: Expression, target: Either[Id, MethodCall]) extends Expression {
  override def children(): List[CST] = expression :: target.right.toSeq.toList

  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    assert(input.declaration.nonEmpty)
    val td = input.declaration.get

    // Preemptive check for a preceding namespace
    if (target.isLeft) {
      expression match {
        case PrimaryExpression(primary: IdPrimary) if isNamespace(primary.id.name, td) =>
          val typeName = TypeName(target.left.get.name, Nil, Some(TypeName(primary.id.name)))
          val td = context.getTypeAndAddDependency(typeName, None).toOption
          if (td.nonEmpty)
            return ExprContext(isStatic = true, td)
        case _ =>
      }
    }

    val inter = expression.verify(input, context)
    if (inter.isDefined) {
      if (target.isLeft)
        verifyWithId(inter, context)
      else
        verifyWithMethod(inter, context)
    } else {
      ExprContext.empty
    }
  }

  private def isNamespace(name: Name, td: TypeDeclaration): Boolean = {
    if (td.packageDeclaration.nonEmpty)
      td.packageDeclaration.get.namespaces.contains(name)
    else
      PlatformTypeDeclaration.namespaces.contains(name)
  }

  def verifyWithId(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    assert(input.declaration.nonEmpty)

    input.declaration.get match {
      case td: TypeDeclaration =>
        val name = target.left.get.name
        val field: Option[FieldDeclaration] = findField(name, td, context.pkg, input.isStatic)
        if (field.nonEmpty) {
          val target = context.getTypeAndAddDependency(field.get.typeName, td).toOption
          return ExprContext(isStatic = false, target)
        }

        // TODO: Private/protected types?
        if (input.isStatic) {
          val nt = input.declaration.get.findLocalType(TypeName(target.left.get.name))
          if (nt.nonEmpty) {
            return ExprContext(isStatic = true, nt)
          }
        }

        if (td.isComplete)
          context.logMessage(location, s"Unknown field or type '${target.left.get.name}' on '${td.typeName}'")
        ExprContext.empty

      case _ =>
        context.missingIdentifier(location, input.typeName, target.left.get.name)
        ExprContext.empty
    }
  }

  def verifyWithMethod(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    assert(input.declaration.nonEmpty)

    // TODO
    ExprContext.empty
  }

  private def findField(name: Name, td: TypeDeclaration, pkg: PackageDeclaration, staticOnly: Boolean) : Option[FieldDeclaration] = {
    val encodedName = EncodedName(name)
    val namespaceName = encodedName.defaultNamespace(pkg.namespace)
    td.findField(namespaceName.fullName, staticOnly).orElse({
      if (encodedName != namespaceName) td.findField(encodedName.fullName, staticOnly) else None
    })
  }
}

final case class ArrayExpression(expression: Expression, arrayExpression: Expression) extends Expression {
  override def children(): List[CST] = expression :: arrayExpression :: Nil

  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {

    val index = arrayExpression.verify(ExprContext(isStatic = false, context.thisType), context)
    if (index.declaration.isEmpty)
      return ExprContext.empty
    if (index.declaration.get ne PlatformTypes.integerType) {
      context.logMessage(arrayExpression.location,
        s"Array indexes must be Integers, found '${index.typeName}'")
      return ExprContext.empty
    }

    val inter = expression.verify(input, context)
    if (!inter.isDefined)
      return ExprContext.empty

    val listType = inter.typeName.getListType
    if (inter.isStatic || listType.isEmpty) {
      context.logMessage(location, s"Only Lists can be de-referenced as an array, found '${inter.typeName}'")
      return ExprContext.empty
    }

    context.getTypeAndAddDependency(listType.get, context.thisType) match {
      case Left(error) =>
        context.missingType(location, listType.get)
        ExprContext.empty
      case Right(td) =>
        ExprContext(isStatic = false, Some(td))
    }
  }
}

final case class MethodCall(callee: Either[Boolean, Id], arguments: List[Expression]) extends Expression {
  override def children(): List[CST] = arguments

  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    arguments.foreach(_.verify(input, context))
    // TODO
    ExprContext.empty
  }
}

object MethodCall {
  def construct(from: MethodCallContext, context: ConstructContext): MethodCall = {
    val caller = Option(from.id()).map(id => Right(Id.construct(id, context))).getOrElse(
      Left(Option(from.THIS()).nonEmpty)
    )

    MethodCall(caller,
      if (from.expressionList() != null) {
        val expression: Seq[ExpressionContext] = from.expressionList().expression().asScala
        Expression.construct(expression.toList, context)
      } else {
        List()
      }
    )
  }
}

final case class NewExpression(creator: Creator) extends Expression {
  override def children(): List[CST] = creator :: Nil

  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    creator.verify(input, context)
  }
}

final case class CastExpression(typeName: TypeName, expression: Expression) extends Expression {
  override def children(): List[CST] = expression :: Nil

  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    val castType = context.getTypeAndAddDependency(typeName, context.thisType).toOption
    if (castType.isEmpty)
      context.missingType(location, typeName)
    expression.verify(input, context)
    ExprContext(isStatic = false, castType)
  }
}

final case class PostfixExpression(expression: Expression, op: String) extends Expression {
  override def children(): List[CST] = expression :: Nil

  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    val inter = expression.verify(input, context)
    if (!inter.isDefined)
      return inter

    val td = inter.declaration.get
    td.typeName match {
      case TypeName.Integer | TypeName.Long | TypeName.Decimal | TypeName.Double if !inter.isStatic => inter
      case _ =>
        Org.logMessage(location, s"Postfix increment/decrement is not supported on type '${td.typeName}'")
        ExprContext.empty
    }
  }
}

final case class PrefixExpression(expression: Expression, op: String) extends Expression {
  override def children(): List[CST] = expression :: Nil

  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    val inter = expression.verify(input, context)
    if (!inter.isDefined)
      return inter

    val td = inter.declaration.get
    td.typeName match {
      case TypeName.Integer | TypeName.Long | TypeName.Decimal | TypeName.Double if !inter.isStatic => inter
      case TypeName.String if !inter.isStatic && op == "+" => inter  // Apex Compiler bug
      case _ =>
        Org.logMessage(location, s"Prefix operations are not supported on type '${td.typeName}'")
        ExprContext.empty
    }
  }
}

final case class NegationExpression(expression: Expression, isBitwise: Boolean) extends Expression {
  override def children(): List[CST] = expression :: Nil

  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    val inter = expression.verify(input, context)
    if (!inter.isDefined)
      return inter

    val td = inter.declaration.get
    td.typeName match {
      case TypeName.Boolean if !isBitwise && !inter.isStatic => inter
      case TypeName.Integer if isBitwise && !inter.isStatic => inter
      case TypeName.Long if isBitwise && !inter.isStatic => inter
      case _ =>
        Org.logMessage(location, s"Negation operations is not supported on type '${td.typeName}'")
        ExprContext.empty
    }
  }
}

final case class BinaryExpression(lhs: Expression, rhs: Expression, op: String) extends Expression {
  override def children(): List[CST] = lhs :: rhs :: Nil

  /* https://developer.salesforce.com/docs/atlas.en-us.apexcode.meta/apexcode/langCon_apex_expressions_operators_understanding.htm */
  /* & | ^ ^= << >> >>> */
  /* += -= *= /= |= &= <<= >>= >>>= */

  private lazy val operation = op match {
    case "=" => AssignmentOperation
    case "&&" => LogicalOperation
    case "||" => LogicalOperation
    case "==" => EqualityOperation
    case "!=" => EqualityOperation
    case "===" => ExactEqualityOperation
    case "!==" => ExactEqualityOperation
    case "<" => CompareOperation
    case ">" => CompareOperation
    case "<=" => CompareOperation
    case ">=" => CompareOperation
    case "+" => PlusOperation
    case "-" => ArithmeticOperation
    case "*" => ArithmeticOperation
    case "/" => ArithmeticOperation
    case _ => NopOperation
  }

  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    val leftInter = lhs.verify(input, context)
    val rightInter = rhs.verify(input, context)

    if (!leftInter.isDefined || !rightInter.isDefined)
      return ExprContext.empty

    if (leftInter.isStatic)
      Org.logMessage(location, s"Expecting instance for operation, not type '${leftInter.typeName}")

    if (rightInter.isStatic)
      Org.logMessage(location, s"Expecting instance for operation, not type '${rightInter.typeName}")

    operation.verify(leftInter, rightInter, op) match {
      case Left(error) =>
        Org.logMessage(location, error)
        ExprContext.empty
      case Right(context) => context
    }
  }
}

final case class InstanceOfExpression(expression: Expression, typeName: TypeName) extends Expression {
  override def children(): List[CST] = expression :: Nil

  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    val instanceOfType = context.getTypeAndAddDependency(typeName, context.thisType).toOption
    if (instanceOfType.isEmpty)
      context.missingType(location, typeName)
    expression.verify(input, context)
    ExprContext(isStatic = false, Some(PlatformTypes.booleanType))
  }
}

final case class QueryExpression(query: Expression, lhs: Expression, rhs: Expression) extends Expression {
  override def children(): List[CST] = query :: lhs :: rhs :: Nil

  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    query.verify(input, context)
    lhs.verify(input, context)
    rhs.verify(input, context)
    // TODO
    ExprContext.empty
  }
}

final case class PrimaryExpression(var primary: Primary) extends Expression {
  override def children(): List[CST] = primary :: Nil

  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    primary.verify(ExprContext(isStatic = false, context.thisType), context)
  }
}

object Expression {
  def construct(from: ExpressionContext, context: ConstructContext): Expression = {
    val cst =
      from match {
        case expr: DotExpressionContext =>
          DotExpression(
            Expression.construct(expr.expression(), context),
            Option(expr.id).map(id => Left(Id.construct(id, context))).getOrElse(
              Right(MethodCall.construct(expr.methodCall(), context))
            )
          )
        case expr: ArrayExpressionContext =>
          ArrayExpression(
            Expression.construct(expr.expression(0), context),
            Expression.construct(expr.expression(1), context)
          )
        case expr: MethodCallExpressionContext =>
          MethodCall.construct(expr.methodCall(), context)
        case expr: NewExpressionContext =>
          NewExpression(Creator.construct(expr.creator(), context))
        case expr: CastExpressionContext =>
          CastExpression(TypeRef.construct(expr.typeRef()), Expression.construct(expr.expression(), context))
        case expr: PostOpExpressionContext =>
          PostfixExpression(Expression.construct(expr.expression(), context), expr.getChild(1).getText)
        case expr: PreOpExpressionContext =>
          PrefixExpression(Expression.construct(expr.expression(), context), expr.getChild(0).getText)
        case expr: NegExpressionContext =>
          NegationExpression(Expression.construct(expr.expression(), context), expr.getChild(0).getText == "~")
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
            TypeRef.construct(expr.typeRef()))
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
}

final case class TypeArguments(typeList: List[TypeName]) extends CST {
  override def children(): List[CST] = Nil
}

object TypeArguments {
  def construct(from: TypeArgumentsContext, context: ConstructContext): TypeArguments = {
    val types: Seq[TypeRefContext] = from.typeList().typeRef().asScala
    TypeArguments(TypeRef.construct(types.toList)).withContext(from, context)
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

