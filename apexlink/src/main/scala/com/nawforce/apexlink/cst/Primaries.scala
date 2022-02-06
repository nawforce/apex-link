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

import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.types.apex.{ApexClassDeclaration, ApexFieldLike}
import com.nawforce.apexlink.types.core.{FieldDeclaration, TypeDeclaration}
import com.nawforce.apexlink.types.platform.PlatformTypes
import com.nawforce.apexparser.ApexParser._
import com.nawforce.apexparser.ApexParserBaseVisitor
import com.nawforce.pkgforce.names.{EncodedName, Name, Names, TypeName}

import scala.collection.compat.immutable.ArraySeq

sealed abstract class Primary extends CST {
  def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext
}

final case class EmptyPrimary() extends Primary {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    ExprContext.empty
  }
}

final case class ThisPrimary() extends Primary {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {

    if (input.declaration.isEmpty) {
      context.logError(location, "")
    }

    if (input.isStatic.contains(true)) {
      context.logError(location, s"'this' can not be used in a static context")
      ExprContext.empty
    } else {
      // Allow this to reference statics platform bug
      ExprContext(isStatic = None, context.thisType)
    }
  }
}

final case class SuperPrimary() extends Primary {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    if (input.isStatic.contains(true)) {
      context.logError(location, s"'super' can not be used in a static context")
      ExprContext.empty
    } else {
      ExprContext(isStatic = Some(false), context.superType)
    }
  }
}

final case class LiteralPrimary(literal: Literal) extends Primary {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    literal.verify(context)
    ExprContext(isStatic = Some(false), Some(literal.getType))
  }
}

final case class TypeReferencePrimary(typeName: TypeName) extends Primary {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {

    // Workaround miss parsing of Foo__c.SObjectType.class as a typeRef
    val targetTypeName =
      if (typeName.outer.nonEmpty && typeName.name == Names.SObjectType) {
        TypeNames.sObjectType$(typeName.outer.get)
      } else {
        typeName
      }

    val td = context.getTypeAndAddDependency(targetTypeName, context.thisType).toOption
    if (td.isEmpty)
      context.missingType(location, typeName)
    ExprContext(isStatic = Some(false), Some(PlatformTypes.typeType))
  }
}

final case class IdPrimary(id: Id) extends Primary {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    isVarReference(context)
      .getOrElse(
        isFieldReference(input, context)
          .getOrElse(isTypeReference(context).getOrElse({
            if (input.typeDeclaration.isComplete)
              context.missingIdentifier(location, input.typeName, id.name)
            ExprContext.empty
          }))
      )
  }

  private def isVarReference(context: ExpressionVerifyContext): Option[ExprContext] = {
    context
      .isVar(id.name, markUsed = true)
      .map(varTypeAndDefinition => {
        ExprContext(
          isStatic = Some(false),
          Some(varTypeAndDefinition.declaration),
          varTypeAndDefinition.definition
        )
      })
  }

  private def isFieldReference(
    input: ExprContext,
    context: ExpressionVerifyContext
  ): Option[ExprContext] = {
    val td            = input.typeDeclaration
    val staticContext = Some(true).filter(input.isStatic.contains)

    val field = findField(id.name, td, staticContext)
    if (field.nonEmpty && isAccessible(td, field.get, staticContext)) {
      context.addDependency(field.get)
      Some(
        context
          .getTypeAndAddDependency(field.get.typeName, td)
          .toOption
          .map(target => {
            ExprContext(isStatic = Some(false), Some(target), field.get)
          })
          .getOrElse({
            context.missingType(location, field.get.typeName)
            ExprContext.empty
          })
      )
    } else {
      None
    }
  }

  private def isTypeReference(context: ExpressionVerifyContext): Option[ExprContext] = {
    context.getTypeAndAddDependency(TypeName(id.name), context.thisType) match {
      case Right(td) => Some(ExprContext(isStatic = Some(true), Some(td), td))
      case _         => None
    }
  }

  private def findField(
    name: Name,
    td: TypeDeclaration,
    staticContext: Option[Boolean]
  ): Option[FieldDeclaration] = {
    val encodedName   = EncodedName(name)
    val namespaceName = encodedName.defaultNamespace(td.moduleDeclaration.flatMap(_.namespace))
    td.findField(namespaceName.fullName, staticContext)
      .orElse({
        if (encodedName != namespaceName)
          td.findField(encodedName.fullName, staticContext)
        else None
      })
  }

  private def isAccessible(
    td: TypeDeclaration,
    field: FieldDeclaration,
    staticContext: Option[Boolean]
  ): Boolean = {
    // From static context, we can only use locally defined static fields, but can only test this with Apex
    // defined fields & types.
    (staticContext.contains(true), td, field) match {
      case (true, ad: ApexClassDeclaration, af: ApexFieldLike) => ad.localFields.contains(af)
      case _                                                   => true
    }
  }
}

final case class SOQL(boundExpressions: ArraySeq[Expression]) extends Primary {

  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    boundExpressions.foreach(expr => {
      expr.verify(input, context)
    })

    ExprContext(isStatic = Some(false), context.module.any)
  }
}

object SOQL {
  def apply(query: QueryContext): SOQL = {
    val visitor     = new BoundExprVisitor()
    val expressions = visitor.visit(query).map(ec => Expression.construct(ec))
    SOQL(expressions)
  }
}

final case class SOSL(query: SoslLiteralContext) extends Primary {

  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    ExprContext(isStatic = Some(false), context.module.any)
  }
}

class BoundExprVisitor extends ApexParserBaseVisitor[ArraySeq[ExpressionContext]] {

  override def defaultResult(): ArraySeq[ExpressionContext] = ArraySeq[ExpressionContext]()

  override protected def aggregateResult(
    aggregate: ArraySeq[ExpressionContext],
    nextResult: ArraySeq[ExpressionContext]
  ): ArraySeq[ExpressionContext] = {
    aggregate ++ nextResult
  }

  override def visitBoundExpression(
    boundExpressionContext: BoundExpressionContext
  ): ArraySeq[ExpressionContext] = {
    ArraySeq(boundExpressionContext.expression())
  }
}

object Primary {
  def construct(from: PrimaryContext): Primary = {
    val cst =
      from match {
        case _: ThisPrimaryContext =>
          ThisPrimary()
        case _: SuperPrimaryContext =>
          SuperPrimary()
        case ctx: LiteralPrimaryContext =>
          LiteralPrimary(Literal.construct(ctx.literal()))
        case ctx: TypeRefPrimaryContext =>
          TypeReferencePrimary(TypeReference.construct(ctx.typeRef()))
        case id: IdPrimaryContext =>
          IdPrimary(Id.construct(id.id()))
        case ctx: SoqlPrimaryContext =>
          SOQL(ctx.soqlLiteral().query())
        case ctx: SoslPrimaryContext =>
          SOSL(ctx.soslLiteral())
        case _ =>
          EmptyPrimary()
      }
    cst.withContext(from)
  }
}
