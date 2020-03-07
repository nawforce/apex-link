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

package com.nawforce.common.cst.stmts

import com.nawforce.common.cst._
import com.nawforce.common.names.TypeName
import com.nawforce.common.org.OrgImpl
import com.nawforce.common.types.{ENUM_NATURE, TypeDeclaration}
import com.nawforce.runtime.parsers.ApexParser.{SwitchStatementContext, WhenControlContext, WhenLiteralContext, WhenValueContext}
import com.nawforce.runtime.parsers.CodeParser

sealed abstract class WhenLiteral extends CST

final class WhenNullLiteral extends WhenLiteral
final case class WhenIdLiteral(id: Id) extends WhenLiteral {
  override def toString: String = id.name.value.toLowerCase
}
final case class WhenStringLiteral(value: String) extends WhenLiteral {
  override def toString: String = value
}
final case class WhenIntegerLiteral(negate: Boolean, value: String) extends WhenLiteral {
  override def toString: String = (if (negate) "-" else "") + value
}

object WhenLiteral {
  def construct(literal: WhenLiteralContext, context: ConstructContext): WhenLiteral = {
    val whenLiteral = CodeParser.toScala(literal.NULL())
      .map(_ => new WhenNullLiteral())
      .orElse(CodeParser.toScala(literal.IntegerLiteral())
        .map(l => WhenIntegerLiteral(CodeParser.toScala(literal.SUB()).nonEmpty, CodeParser.getText(l))))
      .orElse(CodeParser.toScala(literal.StringLiteral())
        .map(l => WhenStringLiteral(CodeParser.getText(l))))
      .orElse(CodeParser.toScala(literal.id())
        .map(l => WhenIdLiteral(Id.construct(l, context))))

    if (whenLiteral.isEmpty)
      throw new CSTException()
    else
      whenLiteral.get.withContext(literal, context)
  }
}

sealed abstract class WhenValue extends CST {
  def checkMatchableTo(typeName: TypeName): Seq[String]
  def checkIsSObject(context: BlockVerifyContext): Seq[String]
  def checkEnumValue(typeDeclaration: TypeDeclaration): Seq[String]
  def verify(context: BlockVerifyContext): Unit = {}
}

final class WhenElseValue extends WhenValue {
  def checkMatchableTo(typeName: TypeName): Seq[String] = Seq()
  def checkIsSObject(context: BlockVerifyContext): Seq[String] = Seq()
  def checkEnumValue(typeDeclaration: TypeDeclaration): Seq[String] = Seq()
}

final case class WhenLiteralsValue(literals: Seq[WhenLiteral]) extends WhenValue {
  override def checkMatchableTo(typeName: TypeName): Seq[String] = {

    def processErrors(invalid: Seq[WhenLiteral], all: Seq[WhenLiteral], typeName: TypeName): Seq[String] = {
      if (invalid.nonEmpty) {
        OrgImpl.logError(invalid.head.location, s"A $typeName literal is required for this value")
        Seq()
      } else {
        all.map(_.toString())
      }
    }

    val nonNull = literals.filterNot(_.isInstanceOf[WhenNullLiteral])
    typeName match {
      case TypeName.Integer | TypeName.Long =>
        processErrors(nonNull.filter(!_.isInstanceOf[WhenIntegerLiteral]), nonNull, typeName)
      case TypeName.String =>
        processErrors(nonNull.filter(!_.isInstanceOf[WhenStringLiteral]), nonNull, typeName)
      case _ => assert(false); Seq()
    }
  }

  override def checkIsSObject(context: BlockVerifyContext): Seq[String] = {
    val nonNull = literals.filterNot(_.isInstanceOf[WhenNullLiteral])
    if (nonNull.nonEmpty)
      OrgImpl.logError(nonNull.head.location, "An SObject name and variable name are required for this value")
    Seq()
  }

  override def checkEnumValue(typeDeclaration: TypeDeclaration): Seq[String] = {
    val nonNull = literals.filterNot(_.isInstanceOf[WhenNullLiteral])
    val notEnum = nonNull.filter(!_.isInstanceOf[WhenIdLiteral])
    if (notEnum.nonEmpty) {
      OrgImpl.logError(notEnum.head.location, "An Enum value is required for this value")
      return Seq()
    }

    nonNull.foreach {
      case iv: WhenIdLiteral =>
        if (typeDeclaration.findField(iv.id.name, Some(true)).isEmpty) {
          OrgImpl.logError(iv.id.location, "Value must be a enum constant")
          return Seq()
        }
      case _ =>
    }
    nonNull.map(_.toString)
  }
}

final case class WhenIdsValue(ids: Seq[Id]) extends WhenValue {
  override def checkMatchableTo(typeName: TypeName): Seq[String] = {
    OrgImpl.logError(ids.head.location, s"A $typeName literal is required for this value")
    Seq()
  }

  override def checkIsSObject(context: BlockVerifyContext): Seq[String] = {
    // Check can be deferred to verify()
    Seq(ids.head.name.value.toLowerCase())
  }

  override def checkEnumValue(typeDeclaration: TypeDeclaration): Seq[String] = {
    OrgImpl.logError(ids.head.location, "Expecting an enum constant value")
    Seq()
  }

  override def verify(context: BlockVerifyContext): Unit = {
    context.addVar(ids(1).name, ids.head.location, TypeName(ids.head.name))
  }
}

object WhenValue {
  def construct(value: WhenValueContext, context: ConstructContext): WhenValue = {
    if (CodeParser.toScala(value.ELSE()).nonEmpty)
      new WhenElseValue()
    else {
      val literals = CodeParser.toScala(value.whenLiteral()).map(l => WhenLiteral.construct(l, context))
      if (literals.nonEmpty)
        WhenLiteralsValue(literals)
      else
        WhenIdsValue(CodeParser.toScala(value.id())
          .map(id => Id.construct(id, context).withContext(id, context)))
    }
  }
}

final case class WhenControl(whenValue: WhenValue, block: Block) extends CST {
  def verify(context: BlockVerifyContext): Unit = {
    val bc = new InnerBlockVerifyContext(context)
    whenValue.verify(bc)
    block.verify(bc)
  }
}

object WhenControl {
  def construct(whenControl: WhenControlContext, context: ConstructContext): WhenControl = {
    WhenControl(
      CodeParser.toScala(whenControl.whenValue()).map(v => WhenValue.construct(v, context)).get,
      Block.construct(whenControl.block(), context))
  }
}

final case class SwitchStatement(expression: Expression, whenControls: List[WhenControl]) extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    val result = expression.verify(context)
    if (result.isDefined) {
      val values = result.typeName match {
        case TypeName.Integer | TypeName.Long | TypeName.String =>
          checkMatchableTo(result.typeName)
        case TypeName.SObject =>
          checkIsSObject(context)
        case _ if result.typeDeclaration.nature == ENUM_NATURE =>
          checkEnumValue(result.typeDeclaration)
        case _ =>
          OrgImpl.logError(expression.location,
            s"Switch expression must be a Integer, Long, String, SObject record or enum value, not '${result.typeName}'")
          return;
      }
      checkWhenElseIsLast()
      checkForDoubleNull()
      val duplicates = values.groupBy(identity).collect { case (_, Seq(_, y, _*)) => y }
      duplicates.headOption.foreach(dup => OrgImpl.logError(expression.location, s"Duplicate when case for $dup"))

      whenControls.foreach(_.verify(context))
    }
  }

  private def checkMatchableTo(typeName: TypeName): Seq[String] = {
    whenControls.flatMap(_.whenValue.checkMatchableTo(typeName))
  }

  private def checkIsSObject(context: BlockVerifyContext): Seq[String] = {
    whenControls.flatMap(_.whenValue.checkIsSObject(context))
  }

  private def checkEnumValue(typeDeclaration: TypeDeclaration): Seq[String] = {
    whenControls.flatMap(_.whenValue.checkEnumValue(typeDeclaration))
  }

  private def checkWhenElseIsLast(): Unit = {
    val notLastElse = whenControls.zipWithIndex
      .filter(_._1.whenValue.isInstanceOf[WhenElseValue])
      .find(_._2 != whenControls.length-1)
    if (notLastElse.nonEmpty)
      OrgImpl.logError(notLastElse.get._1.location, "'when else' must be the last when block")
  }

  private def checkForDoubleNull(): Unit = {
    val literals = whenControls.flatMap(wc => {
      wc.whenValue match {
        case l: WhenLiteralsValue => l.literals
        case _ => Seq()
      }
    })
    if (literals.count(_.isInstanceOf[WhenNullLiteral]) > 1 )
      OrgImpl.logError(literals.last.location, "There should only be one 'when null' block in a switch")
  }
}

object SwitchStatement {
  def construct(switchStatement: SwitchStatementContext, context: ConstructContext): SwitchStatement = {
    SwitchStatement(
      Expression.construct(switchStatement.expression(), context),
      CodeParser.toScala(switchStatement.whenControl())
        .map(wc => WhenControl.construct(wc, context).withContext(wc, context)).toList,
    )
  }
}
