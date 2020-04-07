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

import com.nawforce.common.finding.TypeRequest
import com.nawforce.common.names.{EncodedName, Name, TypeName}
import com.nawforce.common.types.platform.PlatformTypes
import com.nawforce.common.types.{FieldDeclaration, TypeDeclaration}
import com.nawforce.runtime.parsers.ApexParser._
import com.nawforce.runtime.parsers.CodeParser

sealed abstract class Primary extends CST {
  def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext
}

final case class ExpressionPrimary(expression: Expression) extends Primary {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    expression.verify(input, context)
  }
}

final case class ThisPrimary() extends Primary {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {

    if (input.typeDeclarationOpt.isEmpty) {
      context.logError(location, "")
    }


    assert(input.typeDeclarationOpt.nonEmpty)
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
    assert(input.typeDeclarationOpt.nonEmpty)
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
    assert(input.typeDeclarationOpt.nonEmpty)
    ExprContext(isStatic = Some(false), Some(literal.getType))
  }
}

final case class TypeRefPrimary(typeName: TypeName) extends Primary {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    assert(input.typeDeclarationOpt.nonEmpty)
    val td = context.getTypeAndAddDependency(typeName, context.thisType).toOption
    if (td.isEmpty)
      context.missingType(location, typeName)
    ExprContext(isStatic = Some(false), Some(PlatformTypes.typeType))
  }
}

final case class IdPrimary(id: Id) extends Primary {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    assert(input.typeDeclarationOpt.nonEmpty)

    val td = context.isVar(id.name)
    if (td.nonEmpty)
      return ExprContext(isStatic = Some(false), td.get)

    input.typeDeclarationOpt.get match {
      case td: TypeDeclaration =>
        val name = id.name
        val staticContext = Some(true).filter(input.isStatic.contains)
        val field = findField(name, td, staticContext)
        if (field.nonEmpty) {
          context.addDependency(field.get)
          val target = context.getTypeAndAddDependency(field.get.typeName, Some(td)).toOption
          if (target.isEmpty) {
            context.missingType(location, field.get.typeName)
            return ExprContext.empty
          }
          return ExprContext(isStatic = Some(false), target.get)
        }

        val typeRef = td.findLocalType(TypeName(id.name))
        if (typeRef.nonEmpty) {
          return ExprContext(isStatic = Some(true), typeRef.get)
        }

        if (!td.isComplete)
          return ExprContext.empty

      case _ => ()
    }

    val absTd = TypeRequest(TypeName(id.name), context.pkg, excludeSObjects = false)
    if (absTd.isRight) {
      context.addDependency(absTd.right.get)
      return ExprContext(isStatic =  Some(true), absTd.right.get)
    }

    context.missingIdentifier(location, input.typeName, id.name)
    ExprContext.empty
  }

  private def findField(name: Name, td: TypeDeclaration, staticContext: Option[Boolean]) : Option[FieldDeclaration] = {
    val encodedName = EncodedName(name)
    val namespaceName = encodedName.defaultNamespace(td.packageDeclaration.flatMap(_.namespace))
    td.findField(namespaceName.fullName, staticContext).orElse({
      if (encodedName != namespaceName)
        td.findField(encodedName.fullName, staticContext) else None
    })
  }
}

final case class SOQL(soql: String) extends Primary {
  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    assert(input.typeDeclarationOpt.nonEmpty)

    ExprContext(isStatic = Some(false), context.pkg.any())
  }
}

object Primary {
  def construct(from: PrimaryContext): Primary = {
    val cst =
      from match {
        case ctx: SubPrimaryContext =>
          ExpressionPrimary(Expression.construct(ctx.expression()))
        case _: ThisPrimaryContext =>
          ThisPrimary()
        case _: SuperPrimaryContext =>
          SuperPrimary()
        case ctx: LiteralPrimaryContext =>
          LiteralPrimary(Literal.construct(ctx.literal()))
        case ctx: TypeRefPrimaryContext =>
          TypeRefPrimary(TypeRef.construct(ctx.typeRef()))
        case id: IdPrimaryContext =>
          IdPrimary(Id.construct(id.id()))
        case ctx: SoqlPrimaryContext =>
          SOQL(CodeParser.getText(ctx))
      }
    cst.withContext(from)
  }
}
