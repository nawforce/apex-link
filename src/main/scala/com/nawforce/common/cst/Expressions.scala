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
package com.nawforce.common.cst

import com.nawforce.common.diagnostics.Issue
import com.nawforce.common.documents.LocationImpl
import com.nawforce.common.names.{EncodedName, Name, TypeName}
import com.nawforce.common.org.{OrgImpl, PackageImpl}
import com.nawforce.common.types.platform.PlatformTypes
import com.nawforce.common.types.{FieldDeclaration, TypeDeclaration}
import com.nawforce.runtime.parsers.ApexParser._
import com.nawforce.runtime.parsers.CodeParser
import com.nawforce.runtime.types.PlatformTypeDeclaration

trait ExprContext {
  def isStatic: Option[Boolean]
  def isDefined: Boolean

  def typeDeclarationOpt: Option[TypeDeclaration]
  def packageDeclarationOpt: Option[PackageImpl]

  def typeDeclaration: TypeDeclaration = typeDeclarationOpt.get
  def packageDeclaration: PackageImpl = packageDeclarationOpt.get
  def typeName: TypeName = typeDeclarationOpt.get.typeName
}

case class TypeExprContext(isStatic: Option[Boolean], declaration: Option[TypeDeclaration]) extends ExprContext {
  override def isDefined: Boolean = declaration.nonEmpty && !declaration.exists(_.isAny)

  override def typeDeclarationOpt: Option[TypeDeclaration] = declaration
  override def packageDeclarationOpt: Option[PackageImpl] = typeDeclarationOpt.flatMap(_.packageDeclaration)
}

object ExprContext {
  lazy val empty: ExprContext = TypeExprContext(None, None)

  def apply(isStatic: Option[Boolean], typeDeclaration: TypeDeclaration): ExprContext = {
    TypeExprContext(isStatic, Some(typeDeclaration))
  }

  def apply(isStatic: Option[Boolean], typeDeclaration: Option[TypeDeclaration]): ExprContext = {
     TypeExprContext(isStatic, typeDeclaration)
  }
}

sealed abstract class Expression extends CST {
  def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext
  def verify(context: BlockVerifyContext): ExprContext = {
    val staticContext = if (context.isStatic) Some(true) else None
    verify(ExprContext(staticContext, context.thisType), new ExpressionVerifyContext(context))
  }
}

final case class DotExpression(expression: Expression, target: Either[Id, MethodCall]) extends Expression {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    assert(input.typeDeclarationOpt.nonEmpty)
    val td = input.typeDeclarationOpt.get

    // Preemptive check for a preceding namespace
    if (target.isLeft) {
      expression match {
        case PrimaryExpression(primary: IdPrimary) if isNamespace(primary.id.name, td) =>
          val typeName = TypeName(target.left.get.name, Nil, Some(TypeName(primary.id.name)))
          val td = context.getTypeAndAddDependency(typeName, None).toOption
          if (td.nonEmpty)
            return ExprContext(isStatic = Some(true), td.get)
        case _ =>
      }
    }

    // Intercept static call to System Type that may clash with SObject, there are currently only three of these
    // Approval, BusinessHours & Site so we could handle non-generically if needed to bypass
    if (target.isRight) {
      expression match {
        case PrimaryExpression(primary: IdPrimary) if context.isVar(primary.id.name).isEmpty =>
          if (findField(primary.id.name, input.typeDeclaration, context.pkg, None).isEmpty) {
            val td = context.getTypeAndAddDependency(TypeName(primary.id.name), None, excludeSObjects = true).toOption
            if (td.nonEmpty) {
              return verifyWithMethod(ExprContext(isStatic = Some(true), td.get), input, context)
            }
          }
        case _ =>
      }
    }

    val inter = expression.verify(input, context)
    if (inter.isDefined) {
      if (target.isLeft)
        verifyWithId(inter, context)
      else
        verifyWithMethod(inter, input, context)
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
    assert(input.typeDeclarationOpt.nonEmpty)

    input.typeDeclarationOpt.get match {
      case inputType: TypeDeclaration =>
        val name = target.left.get.name
        val field: Option[FieldDeclaration] = findField(name, inputType, context.pkg, input.isStatic)
        if (field.nonEmpty) {
          context.addDependency(field.get)
          val target = context.getTypeAndAddDependency(field.get.typeName, Some(inputType)).toOption
          if (target.isEmpty) {
            context.missingType(location, field.get.typeName)
            return ExprContext.empty
          }
          return ExprContext(isStatic = Some(false), target.get)
        }

        // TODO: Private/protected types?
        if (input.isStatic.contains(true)) {
          val nt = input.typeDeclarationOpt.get.findLocalType(TypeName(target.left.get.name))
          if (nt.nonEmpty) {
            return ExprContext(isStatic = Some(true), nt.get)
          }
        }

        if (inputType.isComplete)
          context.log(Issue.unknownFieldOrType(location, target.left.get.name, inputType.typeName))
        ExprContext.empty

      case _ =>
        context.missingIdentifier(location, input.typeName, target.left.get.name)
        ExprContext.empty
    }
  }

  def verifyWithMethod(callee: ExprContext, input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    assert(input.typeDeclarationOpt.nonEmpty)

    val method = target.right.get
    method.verify(location, callee.typeDeclaration, callee.isStatic, input, context)
  }

  private def findField(name: Name, td: TypeDeclaration, pkg: PackageImpl, staticContext: Option[Boolean]) : Option[FieldDeclaration] = {
    val encodedName = EncodedName(name)
    val namespaceName = encodedName.defaultNamespace(pkg.namespace)
    td.findField(namespaceName.fullName, staticContext).orElse({
      if (encodedName != namespaceName) td.findField(encodedName.fullName, staticContext) else None
    })
  }
}

final case class ArrayExpression(expression: Expression, arrayExpression: Expression) extends Expression {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {

    val index = arrayExpression.verify(ExprContext(isStatic = Some(false), context.thisType), context)
    if (index.typeDeclarationOpt.isEmpty)
      return ExprContext.empty
    if (index.typeName != TypeName.Integer) {
      context.logError(arrayExpression.location,
        s"Array indexes must be Integers, found '${index.typeName}'")
      return ExprContext.empty
    }

    val inter = expression.verify(input, context)
    if (!inter.isDefined)
      return ExprContext.empty

    val listType = inter.typeName.getArrayType
    if (inter.isStatic.contains(true) || listType.isEmpty) {
      context.logError(location, s"Only Lists can be de-referenced as an array, found '${inter.typeName}'")
      return ExprContext.empty
    }

    context.getTypeAndAddDependency(listType.get, context.thisType) match {
      case Left(_) =>
        context.missingType(location, listType.get)
        ExprContext.empty
      case Right(td) =>
        ExprContext(isStatic = Some(false), td)
    }
  }
}

final case class MethodCall(target: Either[Boolean, Id], arguments: Seq[Expression]) extends Expression {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    verify(location, input.typeDeclaration, None, input, context)
  }

  def verify(location: LocationImpl, callee: TypeDeclaration, staticContext: Option[Boolean], input: ExprContext,
             context: ExpressionVerifyContext): ExprContext = {

    val args = arguments.map(_.verify(input, context))
    if (args.exists(!_.isDefined))
      return ExprContext.empty

    target match {
      case Right(id) =>
        val argTypes = args.map(_.typeName)
        val methods = callee.findMethod(id.name, argTypes, staticContext, context)
        methods.foreach(context.addDependency)
        if (methods.isEmpty) {
          if (callee.isComplete && argTypes.forall(!context.pkg.isGhostedType(_))) {
            if (argTypes.isEmpty)
              context.logError(location,
                s"No matching method found for '${id.name}' on '${callee.typeName}' taking no arguments")
            else
              context.logError(location,
                s"No matching method found for '${id.name}' on '${callee.typeName}' " +
                  s"taking arguments '${argTypes.map(_.toString).mkString(", ")}'")
          }
          ExprContext.empty
        } else if (methods.head.typeName != TypeName.Void && !context.pkg.isGhostedType(methods.head.typeName)) {
          val td = context.getTypeAndAddDependency(methods.head.typeName, context.thisType)
          td match {
            case Left(error) =>
              context.log(error.asIssue(location))
              ExprContext.empty
            case Right(td) =>
              ExprContext(isStatic = Some(false), td)
          }
        } else {
          // TODO: How to error if attempt to use return
          ExprContext.empty
        }
      case Left(_) =>
        // TODO:
        ExprContext.empty
    }
  }
}

object MethodCall {
  def construct(from: MethodCallContext): MethodCall = {
    val caller = CodeParser.toScala(from.id()).map(id => Right(Id.construct(id))).getOrElse(
      Left(CodeParser.toScala(from.THIS()).nonEmpty)
    )

    MethodCall(caller,
      CodeParser.toScala(from.expressionList())
        .map(el => CodeParser.toScala(el.expression()).map(e => Expression.construct(e)))
        .getOrElse(Seq())
    )
  }

  def construct(from: DotMethodCallContext): MethodCall = {
    val caller = Right(Id.constructAny(from.anyId()))
    MethodCall(caller,
      CodeParser.toScala(from.expressionList())
        .map(el => CodeParser.toScala(el.expression()).map(e => Expression.construct(e)))
        .getOrElse(Seq())
    )
  }
}

final case class NewExpression(creator: Creator) extends Expression {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    creator.verify(input, context)
  }
}

final case class CastExpression(typeName: TypeName, expression: Expression) extends Expression {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    expression.verify(input, context)

    val castType = context.getTypeAndAddDependency(typeName, context.thisType).toOption
    if (castType.isEmpty) {
      context.missingType(location, typeName)
      ExprContext.empty
    } else {
      ExprContext(isStatic = Some(false), castType.get)
    }
  }
}

final case class PostfixExpression(expression: Expression, op: String) extends Expression {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    val inter = expression.verify(input, context)
    if (!inter.isDefined)
      return inter

    val td = inter.typeDeclarationOpt.get
    td.typeName match {
      case TypeName.Integer | TypeName.Long | TypeName.Decimal | TypeName.Double if inter.isStatic.contains(false) => inter
      case _ =>
        OrgImpl.logError(location, s"Postfix increment/decrement is not supported on type '${td.typeName}'")
        ExprContext.empty
    }
  }
}

final case class PrefixExpression(expression: Expression, op: String) extends Expression {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    val inter = expression.verify(input, context)
    if (!inter.isDefined)
      return inter

    val td = inter.typeDeclarationOpt.get
    td.typeName match {
      case TypeName.Integer | TypeName.Long | TypeName.Decimal | TypeName.Double if inter.isStatic.contains(false) => inter
      case _ if inter.isStatic.contains(false) && op == "+" => ExprContext(isStatic = Some(false), PlatformTypes.stringType)
      case _ =>
        OrgImpl.logError(location, s"Prefix operations are not supported on type '${td.typeName}'")
        ExprContext.empty
    }
  }
}

final case class NegationExpression(expression: Expression, isBitwise: Boolean) extends Expression {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    val inter = expression.verify(input, context)
    if (!inter.isDefined)
      return inter

    val td = inter.typeDeclarationOpt.get
    td.typeName match {
      case TypeName.Boolean if !isBitwise && inter.isStatic.contains(false) => inter
      case TypeName.Integer if isBitwise && inter.isStatic.contains(false) => inter
      case TypeName.Long if isBitwise && inter.isStatic.contains(false) => inter
      case _ =>
        OrgImpl.logError(location, s"Negation operations is not supported on type '${td.typeName}'")
        ExprContext.empty
    }
  }
}

final case class BinaryExpression(lhs: Expression, rhs: Expression, op: String) extends Expression {
  private lazy val operation = op match {
    case "=" => AssignmentOperation
    case "&&" => LogicalOperation
    case "||" => LogicalOperation
    case "==" => EqualityOperation
    case "!=" => EqualityOperation
    case "<>" => EqualityOperation
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
    case "+=" => ArithmeticAddSubtractAssignmentOperation
    case "-=" => ArithmeticAddSubtractAssignmentOperation
    case "*=" => ArithmeticMultiplyDivideAssignmentOperation
    case "/=" => ArithmeticMultiplyDivideAssignmentOperation
    case "&" => BitwiseOperation
    case "|" => BitwiseOperation
    case "^" => BitwiseOperation
    case "<<" => BitwiseOperation
    case ">>" => BitwiseOperation
    case ">>>" => BitwiseOperation
    case "^=" => BitwiseAssignmentOperation
    case "&=" => BitwiseAssignmentOperation
    case "|=" => BitwiseAssignmentOperation
    case "<<=" => BitwiseAssignmentOperation
    case ">>=" => BitwiseAssignmentOperation
    case ">>>=" => BitwiseAssignmentOperation
  }

  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    val leftInter = lhs.verify(input, context)
    val rightInter = rhs.verify(input, context)

    if (!leftInter.isDefined || !rightInter.isDefined)
      return ExprContext.empty

    if (leftInter.isStatic.contains(true))
      OrgImpl.logError(location, s"Expecting instance for operation, not type '${leftInter.typeName}'")

    if (rightInter.isStatic.contains(true))
      OrgImpl.logError(location, s"Expecting instance for operation, not type '${rightInter.typeName}'")

    operation.verify(leftInter, rightInter, op, context) match {
      case Left(error) =>
        OrgImpl.logError(location, error)
        ExprContext.empty
      case Right(context) => context
    }
  }
}

final case class InstanceOfExpression(expression: Expression, typeName: TypeName) extends Expression {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    val instanceOfType = context.getTypeAndAddDependency(typeName, context.thisType).toOption
    if (instanceOfType.isEmpty)
      context.missingType(location, typeName)
    expression.verify(input, context)
    ExprContext(isStatic = Some(false), PlatformTypes.booleanType)
  }
}

final case class QueryExpression(query: Expression, lhs: Expression, rhs: Expression) extends Expression {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    query.verify(input, context)
    val leftInter = lhs.verify(input, context)
    val rightInter = rhs.verify(input, context)

    if (!leftInter.isDefined || !rightInter.isDefined)
      return ExprContext.empty

    ConditionalOperation.verify(leftInter, rightInter, "?", context) match {
      case Left(error) =>
        OrgImpl.logError(location, error)
        ExprContext.empty
      case Right(context) => context
    }
  }
}

final case class PrimaryExpression(var primary: Primary) extends Expression {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    primary.verify(ExprContext(isStatic = input.isStatic, context.thisType), context)
  }
}

object Expression {
  def construct(from: ExpressionContext): Expression = {
    val cst =
      from match {
        case expr: DotExpressionContext =>
          DotExpression(
            Expression.construct(expr.expression()),
            CodeParser.toScala(expr.anyId()).map(id => Left(Id.constructAny(id))).getOrElse(
              Right(MethodCall.construct(CodeParser.toScala(expr.dotMethodCall()).get))
            )
          )
        case expr: ArrayExpressionContext =>
          val expressions = CodeParser.toScala(expr.expression())
          ArrayExpression(
            Expression.construct(expressions.head),
            Expression.construct(expressions(1))
          )
        case expr: MethodCallExpressionContext =>
          MethodCall.construct(expr.methodCall())
        case expr: NewExpressionContext =>
          NewExpression(Creator.construct(expr.creator()))
        case expr: CastExpressionContext =>
          CastExpression(TypeRef.construct(expr.typeRef()), Expression.construct(expr.expression()))
        case expr: PostOpExpressionContext =>
          val op = CodeParser.toScala(expr.INC())
            .orElse(CodeParser.toScala(expr.DEC()))
          PostfixExpression(Expression.construct(expr.expression()), CodeParser.getText(op.get))
        case expr: PreOpExpressionContext =>
          val op = CodeParser.toScala(expr.ADD())
            .orElse(CodeParser.toScala(expr.DEC()))
            .orElse(CodeParser.toScala(expr.INC()))
            .orElse(CodeParser.toScala(expr.SUB()))
          PrefixExpression(Expression.construct(expr.expression()), CodeParser.getText(op.get))
        case expr: NegExpressionContext =>
          val op = CodeParser.toScala(expr.BANG())
            .orElse(CodeParser.toScala(expr.TILDE()))
          NegationExpression(Expression.construct(expr.expression()), CodeParser.getText(op.get) == "~")
        case expr: Arth1ExpressionContext =>
          val op = CodeParser.toScala(expr.DIV())
            .orElse(CodeParser.toScala(expr.MOD()))
            .orElse(CodeParser.toScala(expr.MUL()))
          val expressions = CodeParser.toScala(expr.expression())
          BinaryExpression(Expression.construct(expressions.head),
            Expression.construct(expressions(1)), CodeParser.getText(op.get))
        case expr: Arth2ExpressionContext =>
          val op = CodeParser.toScala(expr.ADD())
            .orElse(CodeParser.toScala(expr.SUB()))
          val expressions = CodeParser.toScala(expr.expression())
          BinaryExpression(Expression.construct(expressions.head),
            Expression.construct(expressions(1)), CodeParser.getText(op.get))
        case expr: BitExpressionContext =>
          val gt = ">" * CodeParser.toScala(expr.GT()).size
          val lt = "<" * CodeParser.toScala(expr.LT()).size
          assert(gt.nonEmpty != lt.nonEmpty)
          val expressions = CodeParser.toScala(expr.expression())
          BinaryExpression(Expression.construct(expressions.head),
            Expression.construct(expressions(1)), gt + lt)
        case expr: CmpExpressionContext =>
          val assign = CodeParser.toScala(expr.ASSIGN()).nonEmpty
          val op = CodeParser.getText(CodeParser.toScala(expr.GT()).orElse(CodeParser.toScala(expr.LT())).get)
          val opText = (assign, op) match {
            case (true, ">") => ">="
            case (true, "<") => "<="
            case (false, op) => op
            case _ => assert(false); ""
          }
          val expressions = CodeParser.toScala(expr.expression())
          BinaryExpression(Expression.construct(expressions.head),
            Expression.construct(expressions(1)), opText)
        case expr: InstanceOfExpressionContext =>
          InstanceOfExpression(Expression.construct(expr.expression()),
            TypeRef.construct(expr.typeRef()))
        case expr: EqualityExpressionContext =>
          val op = CodeParser.toScala(expr.EQUAL())
            .orElse(CodeParser.toScala(expr.LESSANDGREATER()))
            .orElse(CodeParser.toScala(expr.NOTEQUAL()))
            .orElse(CodeParser.toScala(expr.TRIPLEEQUAL()))
            .orElse(CodeParser.toScala(expr.TRIPLENOTEQUAL()))
          val expressions = CodeParser.toScala(expr.expression())
          BinaryExpression(Expression.construct(expressions.head),
            Expression.construct(expressions(1)), CodeParser.getText(op.get))
        case expr: BitAndExpressionContext =>
          val expressions = CodeParser.toScala(expr.expression())
          BinaryExpression(Expression.construct(expressions.head),
            Expression.construct(expressions(1)), "&")
        case expr: BitNotExpressionContext =>
          val expressions = CodeParser.toScala(expr.expression())
          BinaryExpression(Expression.construct(expressions.head),
            Expression.construct(expressions(1)), "^")
        case expr: BitOrExpressionContext =>
          val expressions = CodeParser.toScala(expr.expression())
          BinaryExpression(Expression.construct(expressions.head),
            Expression.construct(expressions(1)), "|")
        case expr: LogAndExpressionContext =>
          val expressions = CodeParser.toScala(expr.expression())
          BinaryExpression(Expression.construct(expressions.head),
            Expression.construct(expressions(1)), "&&")
        case expr: LogOrExpressionContext =>
          val expressions = CodeParser.toScala(expr.expression())
          BinaryExpression(Expression.construct(expressions.head),
            Expression.construct(expressions(1)), "||")
        case expr: CondExpressionContext =>
          val expressions = CodeParser.toScala(expr.expression())
          QueryExpression(Expression.construct(expressions.head),
            Expression.construct(expressions(1)), Expression.construct(expressions(2)))
        case expr: AssignExpressionContext =>
          val op = CodeParser.toScala(expr.ADD_ASSIGN())
            .orElse(CodeParser.toScala(expr.AND_ASSIGN()))
            .orElse(CodeParser.toScala(expr.ASSIGN()))
            .orElse(CodeParser.toScala(expr.DIV_ASSIGN()))
            .orElse(CodeParser.toScala(expr.LSHIFT_ASSIGN()))
            .orElse(CodeParser.toScala(expr.MOD_ASSIGN()))
            .orElse(CodeParser.toScala(expr.MUL_ASSIGN()))
            .orElse(CodeParser.toScala(expr.OR_ASSIGN()))
            .orElse(CodeParser.toScala(expr.RSHIFT_ASSIGN()))
            .orElse(CodeParser.toScala(expr.SUB_ASSIGN()))
            .orElse(CodeParser.toScala(expr.URSHIFT_ASSIGN()))
            .orElse(CodeParser.toScala(expr.XOR_ASSIGN()))
          val expressions = CodeParser.toScala(expr.expression())
          BinaryExpression(Expression.construct(expressions.head),
            Expression.construct(expressions(1)), CodeParser.getText(op.get))
        case expr: PrimaryExpressionContext =>
          PrimaryExpression(Primary.construct(expr.primary()))
      }
    cst.withContext(from)
  }

  def construct(expression: Seq[ExpressionContext]): Seq[Expression] = {
    expression.map(x => Expression.construct(x))
  }
}

final case class TypeArguments(typeList: List[TypeName]) extends CST

object TypeArguments {
  def construct(from: TypeArgumentsContext): TypeArguments = {
    val types: Seq[TypeRefContext] = CodeParser.toScala(from.typeList().typeRef())
    TypeArguments(TypeRef.construct(types.toList)).withContext(from)
  }
}

object Arguments {
  def construct(from: ArgumentsContext): Seq[Expression] = {
    val el = CodeParser.toScala(from.expressionList())
    if (el.nonEmpty) {
      val expressions: Seq[ExpressionContext] = CodeParser.toScala(el.get.expression())
      Expression.construct(expressions.toList)
    } else {
      Seq()
    }
  }
}

