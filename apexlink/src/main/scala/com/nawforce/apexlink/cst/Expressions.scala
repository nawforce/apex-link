/*
 Copyright (c) 2019 Kevin Jones, All rights reserved.
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
 */

package com.nawforce.apexlink.cst

import com.nawforce.apexlink.diagnostics.IssueOps
import com.nawforce.apexlink.finding.TypeResolver
import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.names.TypeNames._
import com.nawforce.apexlink.org.{Module, OrgImpl}
import com.nawforce.apexlink.types.apex.ApexClassDeclaration
import com.nawforce.apexlink.types.core.{FieldDeclaration, TypeDeclaration}
import com.nawforce.apexlink.types.other.AnyDeclaration
import com.nawforce.apexlink.types.platform.{PlatformTypeDeclaration, PlatformTypes}
import com.nawforce.apexparser.ApexParser._
import com.nawforce.pkgforce.diagnostics.{Issue, WARNING_CATEGORY}
import com.nawforce.pkgforce.names.{EncodedName, Name, TypeName}
import com.nawforce.pkgforce.path.{Locatable, PathLocation}
import com.nawforce.runtime.parsers.CodeParser

import scala.collection.immutable.ArraySeq

/* Context used during expression verification to indicate focus & return state. Declaration provides the
 * current context TypeDeclaration. isStatic=None is used as a marker that we have yet to enter an explicit
 * static/instance context as you find on the outermost expression used in an instance method. In this state
 * declaration == this & both static/instance resolution is allowed. If isStatic is set the specific expression
 * should become restricted to either static or instance resolution.
 */
final case class ExprContext(
  isStatic: Option[Boolean],
  declaration: Option[TypeDeclaration],
  locatable: Option[Locatable] = None
) {
  def isDefined: Boolean =
    declaration.nonEmpty && !declaration.exists(_.isInstanceOf[AnyDeclaration])

  def moduleDeclarationOpt: Option[Module] = declaration.flatMap(_.moduleDeclaration)

  def typeDeclaration: TypeDeclaration = declaration.get

  def moduleDeclaration: Module = moduleDeclarationOpt.get

  def typeName: TypeName = declaration.get.typeName
}

object ExprContext {
  val empty: ExprContext = new ExprContext(None, None)

  def apply(isStatic: Option[Boolean], declaration: TypeDeclaration): ExprContext = {
    new ExprContext(isStatic, Some(declaration))
  }

  /* We allow flex here on the locatable type as it a cross cutting concern of many sorts of things */
  def apply(
    isStatic: Option[Boolean],
    declaration: Option[TypeDeclaration],
    locatable: Any
  ): ExprContext = {
    locatable match {
      case l: Locatable => new ExprContext(isStatic, declaration, Some(l))
      case _            => new ExprContext(isStatic, declaration, None)
    }
  }
}

sealed abstract class Expression extends CST {
  def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext

  def verify(context: BlockVerifyContext): ExprContext = {
    val staticContext = if (context.isStatic) Some(true) else None
    verify(ExprContext(staticContext, context.thisType), new ExpressionVerifyContext(context))
  }
}

final case class EmptyExpr() extends Expression {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    ExprContext.empty
  }
}

object DotExpression {
  def findField(
    name: Name,
    td: TypeDeclaration,
    module: Module,
    staticContext: Option[Boolean]
  ): Option[FieldDeclaration] = {
    val encodedName   = EncodedName(name)
    val namespaceName = encodedName.defaultNamespace(module.namespace)
    td.findField(namespaceName.fullName, staticContext)
      .orElse({
        if (encodedName != namespaceName) td.findField(encodedName.fullName, staticContext)
        else None
      })
  }
}

final case class DotExpressionWithId(expression: Expression, safeNavigation: Boolean, target: Id)
    extends Expression {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    assert(input.declaration.nonEmpty)

    // When we have a leading IdPrimary there are a couple of special cases to handle, we could with a better
    // understanding of how these are handled, likely it via some parser hack
    val intercept = getInterceptPrimary(input, context).flatMap(primary => {
      // It might be a namespace
      if (isNamespace(primary.id.name, input.declaration.get)) {
        val typeName = TypeName(target.name, Nil, Some(TypeName(primary.id.name))).intern
        val td       = context.getTypeAndAddDependency(typeName, context.thisType).toOption
        td.map(
          td =>
            context.saveResult(this) {
              ExprContext(isStatic = Some(true), Some(td), td)
            }
        )
      } else {
        // It might be a static reference to an outer class that failed normal analysis due to class name shadowing
        // This occurs where say A has an inner of B and in C with an inner of A we reference 'A.B' which is valid.
        TypeResolver(TypeName(primary.id.name), context.module).toOption match {
          case Some(td: ApexClassDeclaration) =>
            val result = verifyWithId(ExprContext(isStatic = Some(true), td), context)
            if (result.isDefined) {
              context.addDependency(td)
              Some(result)
            } else {
              None
            }
          case _ => None
        }
      }
    })
    intercept.map(result => context.saveResult(this)(result))
    intercept.getOrElse(verifyInternal(input, context))
  }

  private def getInterceptPrimary(
    input: ExprContext,
    context: ExpressionVerifyContext
  ): Option[IdPrimary] = {
    expression match {
      case PrimaryExpression(primary: IdPrimary) if !safeNavigation =>
        // Found one but we must check can not be resolved as local var/field
        if (
          context.isVar(primary.id.name).isEmpty &&
          DotExpression
            .findField(primary.id.name, input.declaration.get, context.module, input.isStatic)
            .isEmpty
        ) {
          Some(primary)
        } else {
          None
        }
      case _ => None
    }
  }

  private def verifyInternal(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    val inter = expression.verify(input, context)
    if (inter.isDefined) {
      if (inter.isStatic.contains(true) && safeNavigation) {
        context.logError(
          location,
          "Safe navigation operator (?.) can not be used on static references"
        )
        ExprContext.empty
      } else {
        context.saveResult(this) {
          verifyWithId(inter, context)
        }
      }
    } else {
      ExprContext.empty
    }
  }

  private def isNamespace(name: Name, td: TypeDeclaration): Boolean = {
    if (td.moduleDeclaration.nonEmpty)
      td.moduleDeclaration.get.pkg.namespaces.contains(name)
    else
      PlatformTypeDeclaration.namespaces.contains(name)
  }

  def verifyWithId(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    val inputType = input.declaration.get

    val name = target.name
    val field: Option[FieldDeclaration] =
      DotExpression.findField(name, inputType, context.module, input.isStatic)
    if (field.nonEmpty) {
      context.addDependency(field.get)
      val target = context.getTypeAndAddDependency(field.get.typeName, inputType).toOption
      if (target.isEmpty) {
        context.missingType(location, field.get.typeName)
        return ExprContext.empty
      }
      return ExprContext(isStatic = Some(false), target, field.get)
    }

    // TODO: Private/protected types?
    if (input.isStatic.contains(true)) {
      val nt = input.declaration.get.findLocalType(TypeName(name))
      if (nt.nonEmpty) {
        return ExprContext(isStatic = Some(true), nt.get)
      }
    }

    if (inputType.isComplete) {
      if (inputType.isSObject) {
        if (!context.module.isGhostedFieldName(name)) {
          context.log(IssueOps.unknownFieldOnSObject(location, name, inputType.typeName))
        }
      } else {
        context.log(IssueOps.unknownFieldOrType(location, name, inputType.typeName))
      }
    }
    ExprContext.empty
  }
}

final case class DotExpressionWithMethod(
  expression: Expression,
  safeNavigation: Boolean,
  target: Option[MethodCallWithId]
) extends Expression {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    assert(input.declaration.nonEmpty)

    interceptAmbiguousMethodCall(input, context)
      .getOrElse({
        val inter = expression.verify(input, context)
        if (inter.isDefined) {
          if (inter.isStatic.contains(true) && safeNavigation) {
            context.logError(
              location,
              "Safe navigation operator (?.) can not be used on static references"
            )
            ExprContext.empty
          } else {
            target
              .map(
                target =>
                  target.verify(location, inter.typeDeclaration, inter.isStatic, input, context)
              )
              .getOrElse(ExprContext.empty)
          }
        } else {
          // When we can't find method we should still verify args for dependency side-effects
          target.map(target => target.arguments.map(_.verify(input, context)))
          ExprContext.empty
        }
      })
  }

  /** Intercept static method call to BusinessHours or Site as these operate on System.* rather than Schema.* classes.
    * This hack avoids having to pass additional context into platform type loading to disambiguate.
    */
  private def interceptAmbiguousMethodCall(
    input: ExprContext,
    context: ExpressionVerifyContext
  ): Option[ExprContext] = {
    expression match {
      case PrimaryExpression(primary: IdPrimary)
          if DotExpressionWithMethod.isAmbiguousName.contains(primary.id.name) &&
            context.isVar(primary.id.name).isEmpty &&
            DotExpression
              .findField(primary.id.name, input.typeDeclaration, context.module, None)
              .isEmpty =>
        context
          .getTypeAndAddDependency(
            TypeName(primary.id.name, Nil, Some(TypeNames.System)),
            context.thisType
          )
          .toOption
          .flatMap(
            td => target.map(target => target.verify(location, td, Some(true), input, context))
          )
      case _ => None
    }
  }
}

object DotExpressionWithMethod {
  private val isAmbiguousName = Set(Name("BusinessHours"), Name("Site"))
}

final case class ArrayExpression(expression: Expression, arrayExpression: Expression)
    extends Expression {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {

    val index =
      arrayExpression.verify(ExprContext(isStatic = Some(false), context.thisType), context)
    if (index.declaration.isEmpty)
      return ExprContext.empty
    if (index.typeName != TypeNames.Integer) {
      context.logError(
        arrayExpression.location,
        s"Array indexes must be Integers, found '${index.typeName}'"
      )
      return ExprContext.empty
    }

    val inter = expression.verify(input, context)
    if (!inter.isDefined)
      return ExprContext.empty

    val listType = inter.typeName.getArrayType
    if (inter.isStatic.contains(true) || listType.isEmpty) {
      context.logError(
        location,
        s"Only Lists can be de-referenced as an array, found '${inter.typeName}'"
      )
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

abstract class MethodCall extends Expression

final case class MethodCallWithId(target: Id, arguments: ArraySeq[Expression]) extends MethodCall {

  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    verify(location, input.typeDeclaration, None, input, context)
  }

  def verify(
    location: PathLocation,
    callee: TypeDeclaration,
    staticContext: Option[Boolean],
    input: ExprContext,
    context: ExpressionVerifyContext
  ): ExprContext = {

    val args = arguments.map(_.verify(input, context))

    // If we failed to get argument type (maybe due to ghosting), use null as assignable to anything
    val argTypes = args.map(arg => if (arg.isDefined) arg.typeName else TypeNames.Any)
    callee.findMethod(target.name, argTypes, staticContext, context) match {
      case Right(method) =>
        context.addDependency(method)
        if (method.typeName != TypeNames.Void) {
          val td = context.getTypeAndAddDependency(method.typeName, context.thisType)
          td match {
            case Left(error) =>
              if (!context.module.isGhostedType(method.typeName))
                context.log(error.asIssue(location))
              context.saveResult(this, target.location.location) {
                ExprContext(None, None, method)
              }
            case Right(td) =>
              context.saveResult(this, target.location.location) {
                ExprContext(isStatic = Some(false), Some(td), method)
              }
          }
        } else {
          // TODO: How to error if attempt to use return
          context.saveResult(this, target.location.location) {
            ExprContext(None, None, method)
          }
        }

      case Left(err) =>
        if (callee.isComplete) {
          if (argTypes.contains(TypeNames.Any)) {
            context.log(
              Issue(
                location.path,
                WARNING_CATEGORY,
                location.location,
                s"$err for '${target.name}' on '${callee.typeName}' " +
                  s"taking arguments '${argTypes.map(_.toString).mkString(", ")}', likely due to unknown type"
              )
            )
          } else if (argTypes.isEmpty) {
            context.logError(
              location,
              s"$err for '${target.name}' on '${callee.typeName}' taking no arguments"
            )
          } else {
            context.logError(
              location,
              s"$err for '${target.name}' on '${callee.typeName}' " +
                s"taking arguments '${argTypes.map(_.toString).mkString(", ")}'"
            )
          }
        }
        ExprContext.empty
    }
  }
}

final case class MethodCallCtor(isSuper: Boolean, arguments: ArraySeq[Expression])
    extends MethodCall {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {

    // Verify args so vars don't show as unused
    arguments.map(_.verify(input, context))

    // TODO
    ExprContext.empty
  }
}

object MethodCall {
  def construct(from: MethodCallContext): MethodCall = {
    CodeParser
      .toScala(from.id())
      .map(id => {
        MethodCallWithId(Id.construct(id), expressions(from.expressionList())).withContext(from)
      })
      .getOrElse({
        MethodCallCtor(
          CodeParser.toScala(from.SUPER()).nonEmpty,
          expressions(from.expressionList())
        ).withContext(from)
      })
  }

  def construct(from: DotMethodCallContext): MethodCallWithId = {
    MethodCallWithId(Id.constructAny(from.anyId()), expressions(from.expressionList()))
      .withContext(from)
  }

  private def expressions(from: ExpressionListContext): ArraySeq[Expression] = {
    CodeParser
      .toScala(from)
      .map(el => CodeParser.toScala(el.expression()).map(e => Expression.construct(e)))
      .getOrElse(Expression.emptyExpressions)
  }
}

final case class NewExpression(creator: Creator) extends Expression {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    context.saveResult(this) { creator.verify(input, context) }
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

final case class SubExpression(expression: Expression) extends Expression {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    expression.verify(input, context)
  }
}

final case class PostfixExpression(expression: Expression, op: String) extends Expression {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    val inter = expression.verify(input, context)
    if (!inter.isDefined)
      return inter

    val td = inter.declaration.get
    td.typeName match {
      case TypeNames.Integer | TypeNames.Long | TypeNames.Decimal | TypeNames.Double
          if inter.isStatic.contains(false) =>
        inter
      case _ =>
        OrgImpl.logError(
          location,
          s"Postfix increment/decrement is not supported on type '${td.typeName}'"
        )
        ExprContext.empty
    }
  }
}

final case class PrefixExpression(expression: Expression, op: String) extends Expression {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    val inter = expression.verify(input, context)
    if (!inter.isDefined)
      return inter

    val td = inter.declaration.get
    td.typeName match {
      case TypeNames.Integer | TypeNames.Long | TypeNames.Decimal | TypeNames.Double
          if inter.isStatic.contains(false) =>
        inter
      case _ if inter.isStatic.contains(false) && op == "+" =>
        ExprContext(isStatic = Some(false), PlatformTypes.stringType)
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

    val td = inter.declaration.get
    td.typeName match {
      case TypeNames.Boolean if !isBitwise && inter.isStatic.contains(false) => inter
      case TypeNames.Integer if isBitwise && inter.isStatic.contains(false)  => inter
      case TypeNames.Long if isBitwise && inter.isStatic.contains(false)     => inter
      case _ =>
        OrgImpl.logError(location, s"Negation operations is not supported on type '${td.typeName}'")
        ExprContext.empty
    }
  }
}

final case class BinaryExpression(lhs: Expression, rhs: Expression, op: String) extends Expression {
  private lazy val operation = op match {
    case "="    => AssignmentOperation
    case "&&"   => LogicalOperation
    case "||"   => LogicalOperation
    case "=="   => EqualityOperation
    case "!="   => EqualityOperation
    case "<>"   => EqualityOperation
    case "==="  => ExactEqualityOperation
    case "!=="  => ExactEqualityOperation
    case "<"    => CompareOperation
    case ">"    => CompareOperation
    case "<="   => CompareOperation
    case ">="   => CompareOperation
    case "+"    => PlusOperation
    case "-"    => ArithmeticOperation
    case "*"    => ArithmeticOperation
    case "/"    => ArithmeticOperation
    case "+="   => ArithmeticAddSubtractAssignmentOperation
    case "-="   => ArithmeticAddSubtractAssignmentOperation
    case "*="   => ArithmeticMultiplyDivideAssignmentOperation
    case "/="   => ArithmeticMultiplyDivideAssignmentOperation
    case "&"    => BitwiseOperation
    case "|"    => BitwiseOperation
    case "^"    => BitwiseOperation
    case "<<"   => BitwiseOperation
    case ">>"   => BitwiseOperation
    case ">>>"  => BitwiseOperation
    case "^="   => BitwiseAssignmentOperation
    case "&="   => BitwiseAssignmentOperation
    case "|="   => BitwiseAssignmentOperation
    case "<<="  => BitwiseAssignmentOperation
    case ">>="  => BitwiseAssignmentOperation
    case ">>>=" => BitwiseAssignmentOperation
  }

  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    val leftInter  = lhs.verify(input, context)
    val rightInter = rhs.verify(input, context)

    if (!leftInter.isDefined || !rightInter.isDefined)
      return ExprContext.empty

    if (leftInter.isStatic.contains(true))
      OrgImpl.logError(
        location,
        s"Expecting instance for operation, not type '${leftInter.typeName}'"
      )

    if (rightInter.isStatic.contains(true))
      OrgImpl.logError(
        location,
        s"Expecting instance for operation, not type '${rightInter.typeName}'"
      )

    operation.verify(leftInter, rightInter, op, context) match {
      case Left(error) =>
        OrgImpl.logError(location, error)
        ExprContext.empty
      case Right(context) => context
    }
  }
}

final case class InstanceOfExpression(expression: Expression, typeName: TypeName)
    extends Expression {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    val instanceOfType = context.getTypeAndAddDependency(typeName, context.thisType).toOption
    if (instanceOfType.isEmpty)
      context.missingType(location, typeName)
    expression.verify(input, context)
    ExprContext(isStatic = Some(false), PlatformTypes.booleanType)
  }
}

final case class QueryExpression(query: Expression, lhs: Expression, rhs: Expression)
    extends Expression {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    query.verify(input, context)
    val leftInter  = lhs.verify(input, context)
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
    context.saveResult(this) {
      primary.verify(ExprContext(isStatic = input.isStatic, context.thisType), context)
    }
  }
}

object Expression {
  val emptyExpressions: ArraySeq[Expression] = ArraySeq()

  def construct(from: ExpressionContext): Expression = {
    val cst: Expression = {
      from match {
        case expr: DotExpressionContext =>
          CodeParser
            .toScala(expr.anyId())
            .map(id => {
              DotExpressionWithId(
                Expression.construct(expr.expression()),
                CodeParser.toScala(expr.DOT()).isEmpty,
                Id.constructAny(id)
              )
            })
            .getOrElse({
              DotExpressionWithMethod(
                Expression.construct(expr.expression()),
                CodeParser.toScala(expr.DOT()).isEmpty,
                CodeParser
                  .toScala(expr.dotMethodCall())
                  .map(method => {
                    MethodCall.construct(method)
                  })
              )
            })

        case expr: ArrayExpressionContext =>
          val expressions = CodeParser.toScala(expr.expression())
          if (expressions.length == 2)
            ArrayExpression(
              Expression.construct(expressions.head),
              Expression.construct(expressions(1))
            )
          else
            EmptyExpr()

        case expr: MethodCallExpressionContext =>
          MethodCall.construct(expr.methodCall())

        case expr: NewExpressionContext =>
          NewExpression(Creator.construct(expr.creator()))

        case expr: CastExpressionContext =>
          CastExpression(
            TypeReference.construct(expr.typeRef()),
            Expression.construct(expr.expression())
          )

        case expr: SubExpressionContext =>
          SubExpression(Expression.construct(expr.expression()))

        case expr: PostOpExpressionContext =>
          val op = CodeParser
            .toScala(expr.INC())
            .orElse(CodeParser.toScala(expr.DEC()))
          PostfixExpression(Expression.construct(expr.expression()), CodeParser.getText(op.get))

        case expr: PreOpExpressionContext =>
          val op = CodeParser
            .toScala(expr.ADD())
            .orElse(CodeParser.toScala(expr.DEC()))
            .orElse(CodeParser.toScala(expr.INC()))
            .orElse(CodeParser.toScala(expr.SUB()))
          PrefixExpression(Expression.construct(expr.expression()), CodeParser.getText(op.get))

        case expr: NegExpressionContext =>
          val op = CodeParser
            .toScala(expr.BANG())
            .orElse(CodeParser.toScala(expr.TILDE()))
          NegationExpression(
            Expression.construct(expr.expression()),
            CodeParser.getText(op.get) == "~"
          )

        case expr: Arth1ExpressionContext =>
          val op = CodeParser
            .toScala(expr.DIV())
            .orElse(CodeParser.toScala(expr.MOD()))
            .orElse(CodeParser.toScala(expr.MUL()))
          val expressions = CodeParser.toScala(expr.expression())
          if (expressions.length == 2) {
            BinaryExpression(
              Expression.construct(expressions.head),
              Expression.construct(expressions(1)),
              CodeParser.getText(op.get)
            )
          } else {
            EmptyExpr()
          }

        case expr: Arth2ExpressionContext =>
          val op = CodeParser
            .toScala(expr.ADD())
            .orElse(CodeParser.toScala(expr.SUB()))
          val expressions = CodeParser.toScala(expr.expression())
          if (expressions.length == 2) {
            BinaryExpression(
              Expression.construct(expressions.head),
              Expression.construct(expressions(1)),
              CodeParser.getText(op.get)
            )
          } else {
            EmptyExpr()
          }

        case expr: BitExpressionContext =>
          val gt = ">" * CodeParser.toScala(expr.GT()).size
          val lt = "<" * CodeParser.toScala(expr.LT()).size
          assert(gt.nonEmpty != lt.nonEmpty)
          val expressions = CodeParser.toScala(expr.expression())
          if (expressions.length == 2) {
            BinaryExpression(
              Expression.construct(expressions.head),
              Expression.construct(expressions(1)),
              gt + lt
            )
          } else {
            EmptyExpr()
          }

        case expr: CmpExpressionContext =>
          val assign = CodeParser.toScala(expr.ASSIGN()).nonEmpty
          val op = CodeParser.getText(
            CodeParser.toScala(expr.GT()).orElse(CodeParser.toScala(expr.LT())).get
          )
          val opText = (assign, op) match {
            case (true, ">") => ">="
            case (true, "<") => "<="
            case (false, op) => op
            case _           => assert(false); ""
          }
          val expressions = CodeParser.toScala(expr.expression())
          if (expressions.length == 2) {
            BinaryExpression(
              Expression.construct(expressions.head),
              Expression.construct(expressions(1)),
              opText
            )
          } else {
            EmptyExpr()
          }

        case expr: InstanceOfExpressionContext =>
          InstanceOfExpression(
            Expression.construct(expr.expression()),
            TypeReference.construct(expr.typeRef())
          )

        case expr: EqualityExpressionContext =>
          val op = CodeParser
            .toScala(expr.EQUAL())
            .orElse(CodeParser.toScala(expr.LESSANDGREATER()))
            .orElse(CodeParser.toScala(expr.NOTEQUAL()))
            .orElse(CodeParser.toScala(expr.TRIPLEEQUAL()))
            .orElse(CodeParser.toScala(expr.TRIPLENOTEQUAL()))
          val expressions = CodeParser.toScala(expr.expression())
          if (expressions.length == 2) {
            BinaryExpression(
              Expression.construct(expressions.head),
              Expression.construct(expressions(1)),
              CodeParser.getText(op.get)
            )
          } else {
            EmptyExpr()
          }

        case expr: BitAndExpressionContext =>
          val expressions = CodeParser.toScala(expr.expression())
          if (expressions.length == 2) {
            BinaryExpression(
              Expression.construct(expressions.head),
              Expression.construct(expressions(1)),
              "&"
            )
          } else {
            EmptyExpr()
          }

        case expr: BitNotExpressionContext =>
          val expressions = CodeParser.toScala(expr.expression())
          if (expressions.length == 2) {
            BinaryExpression(
              Expression.construct(expressions.head),
              Expression.construct(expressions(1)),
              "^"
            )
          } else {
            EmptyExpr()
          }

        case expr: BitOrExpressionContext =>
          val expressions = CodeParser.toScala(expr.expression())
          if (expressions.length == 2) {
            BinaryExpression(
              Expression.construct(expressions.head),
              Expression.construct(expressions(1)),
              "|"
            )
          } else {
            EmptyExpr()
          }

        case expr: LogAndExpressionContext =>
          val expressions = CodeParser.toScala(expr.expression())
          if (expressions.length == 2) {
            BinaryExpression(
              Expression.construct(expressions.head),
              Expression.construct(expressions(1)),
              "&&"
            )
          } else {
            EmptyExpr()
          }

        case expr: LogOrExpressionContext =>
          val expressions = CodeParser.toScala(expr.expression())
          if (expressions.length == 2) {
            BinaryExpression(
              Expression.construct(expressions.head),
              Expression.construct(expressions(1)),
              "||"
            )
          } else {
            EmptyExpr()
          }

        case expr: CondExpressionContext =>
          val expressions = CodeParser.toScala(expr.expression())
          if (expressions.length == 3) {
            QueryExpression(
              Expression.construct(expressions.head),
              Expression.construct(expressions(1)),
              Expression.construct(expressions(2))
            )
          } else {
            EmptyExpr()
          }

        case expr: AssignExpressionContext =>
          val op = CodeParser
            .toScala(expr.ADD_ASSIGN())
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
          BinaryExpression(
            Expression.construct(expressions.head),
            Expression.construct(expressions(1)),
            CodeParser.getText(op.get)
          )
        case expr: PrimaryExpressionContext =>
          PrimaryExpression(Primary.construct(expr.primary()))

        case _ => EmptyExpr()
      }
    }
    cst.withContext(from)
  }

  def construct(expression: ArraySeq[ExpressionContext]): ArraySeq[Expression] = {
    expression.map(x => Expression.construct(x))
  }
}

final case class TypeArguments(typeList: List[TypeName]) extends CST

object TypeArguments {
  def construct(from: TypeArgumentsContext): TypeArguments = {
    val types = CodeParser.toScala(from.typeList().typeRef())
    TypeArguments(TypeReference.construct(types.toList)).withContext(from)
  }
}

object Arguments {
  def construct(from: ArgumentsContext): ArraySeq[Expression] = {
    val el = CodeParser.toScala(from.expressionList())
    if (el.nonEmpty) {
      Expression.construct(CodeParser.toScala(el.get.expression()))
    } else {
      Expression.emptyExpressions
    }
  }
}
