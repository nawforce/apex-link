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

package com.nawforce.apexlink.cst.stmts

import com.nawforce.apexlink.cst._
import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.org.OrgImpl
import com.nawforce.apexlink.types.core.TypeDeclaration
import com.nawforce.apexparser.ApexParser.{
  SwitchStatementContext,
  WhenControlContext,
  WhenLiteralContext,
  WhenValueContext
}
import com.nawforce.pkgforce.names.{EncodedName, TypeName}
import com.nawforce.pkgforce.parsers.ENUM_NATURE
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
  def construct(literal: WhenLiteralContext): Option[WhenLiteral] = {
    val whenLiteral = CodeParser
      .toScala(literal.NULL())
      .map(_ => new WhenNullLiteral())
      .orElse(
        CodeParser
          .toScala(literal.IntegerLiteral())
          .map(
            l =>
              WhenIntegerLiteral(CodeParser.toScala(literal.SUB()).nonEmpty, CodeParser.getText(l))
          )
      )
      .orElse(
        CodeParser
          .toScala(literal.StringLiteral())
          .map(l => WhenStringLiteral(CodeParser.getText(l)))
      )
      .orElse(
        CodeParser
          .toScala(literal.id())
          .map(l => WhenIdLiteral(Id.construct(l)))
      )

    whenLiteral.map(_.withContext(literal))
  }
}

sealed abstract class WhenValue extends CST {
  def checkMatchableTo(typeName: TypeName): Seq[String]
  def checkIsSObject(context: BlockVerifyContext): Seq[String]
  def checkEnumValue(typeDeclaration: TypeDeclaration, context: BlockVerifyContext): Seq[String]
  def verify(context: BlockVerifyContext): Unit = {}
}

final class WhenElseValue extends WhenValue {
  def checkMatchableTo(typeName: TypeName): Seq[String]        = Seq()
  def checkIsSObject(context: BlockVerifyContext): Seq[String] = Seq()
  def checkEnumValue(typeDeclaration: TypeDeclaration, context: BlockVerifyContext): Seq[String] =
    Seq()
}

final case class WhenLiteralsValue(literals: Seq[WhenLiteral]) extends WhenValue {
  override def checkMatchableTo(typeName: TypeName): Seq[String] = {

    def processErrors(
      invalid: Seq[WhenLiteral],
      all: Seq[WhenLiteral],
      typeName: TypeName
    ): Seq[String] = {
      if (invalid.nonEmpty) {
        OrgImpl.logError(invalid.head.location, s"A $typeName literal is required for this value")
        Seq()
      } else {
        all.map(_.toString())
      }
    }

    val nonNull = literals.filterNot(_.isInstanceOf[WhenNullLiteral])
    typeName match {
      case TypeNames.Integer | TypeNames.Long =>
        processErrors(nonNull.filter(!_.isInstanceOf[WhenIntegerLiteral]), nonNull, typeName)
      case TypeNames.String =>
        processErrors(nonNull.filter(!_.isInstanceOf[WhenStringLiteral]), nonNull, typeName)
      case _ => assert(false); Seq()
    }
  }

  override def checkIsSObject(context: BlockVerifyContext): Seq[String] = {
    val nonNull = literals.filterNot(_.isInstanceOf[WhenNullLiteral])
    if (nonNull.nonEmpty)
      OrgImpl.logError(
        nonNull.head.location,
        "An SObject name and variable name are required for this value"
      )
    Seq()
  }

  override def checkEnumValue(
    typeDeclaration: TypeDeclaration,
    context: BlockVerifyContext
  ): Seq[String] = {
    val nonNull = literals.filterNot(_.isInstanceOf[WhenNullLiteral])
    val notEnum = nonNull.filter(!_.isInstanceOf[WhenIdLiteral])
    if (notEnum.nonEmpty) {
      OrgImpl.logError(notEnum.head.location, "An Enum value is required for this value")
      return Seq()
    }

    nonNull.foreach {
      case iv: WhenIdLiteral =>
        val field = typeDeclaration.findField(iv.id.name, Some(true))
        field.foreach(context.addDependency)
        if (field.isEmpty) {
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

  override def checkEnumValue(
    typeDeclaration: TypeDeclaration,
    context: BlockVerifyContext
  ): Seq[String] = {
    ids.headOption.foreach(head => {
      OrgImpl.logError(head.location, "Expecting an enum constant value")
    })
    Seq()
  }

  override def verify(context: BlockVerifyContext): Unit = {
    if (ids.size > 1) {
      context.addVar(ids(1).name, ids(1), TypeName(ids.head.name, Nil, Some(TypeNames.Schema)))
    }
  }
}

object WhenValue {
  def construct(value: WhenValueContext): WhenValue = {
    if (CodeParser.toScala(value.ELSE()).nonEmpty)
      new WhenElseValue()
    else {
      val literals = CodeParser.toScala(value.whenLiteral()).flatMap(l => WhenLiteral.construct(l))
      if (literals.nonEmpty)
        WhenLiteralsValue(literals)
      else
        WhenIdsValue(
          CodeParser
            .toScala(value.id())
            .map(id => Id.construct(id).withContext(id))
        )
    }
  }
}

final case class WhenControl(whenValue: WhenValue, block: Block) extends CST {
  def verify(context: BlockVerifyContext): Unit = {
    val blockContext = new InnerBlockVerifyContext(context)
    whenValue.verify(blockContext)
    block.verify(blockContext)
    context.typePlugin.onBlockValidated(block, context.isStatic, blockContext)
  }
}

object WhenControl {
  def construct(parser: CodeParser, whenControl: WhenControlContext): WhenControl = {
    WhenControl(
      CodeParser.toScala(whenControl.whenValue()).map(v => WhenValue.construct(v)).get,
      Block.construct(parser, whenControl.block(), isTrigger = false)
    )
  }
}

final case class SwitchStatement(expression: Expression, whenControls: List[WhenControl])
    extends Statement {
  override def verify(context: BlockVerifyContext): Unit = {
    val result = expression.verify(context)
    if (result.isDefined) {
      val values = result.typeName match {
        case TypeNames.Integer | TypeNames.Long | TypeNames.String =>
          checkMatchableTo(result.typeName)
        case TypeNames.SObject =>
          checkIsSObject(context)
        case _ if result.typeDeclaration.nature == ENUM_NATURE =>
          checkEnumValue(result.typeDeclaration, context)
        case _ =>
          OrgImpl.logError(
            expression.location,
            s"Switch expression must be a Integer, Long, String, SObject record or enum value, not '${result.typeName}'"
          )
          return;
      }
      checkWhenElseIsLast()
      checkForDoubleNull()
      val duplicates = values.groupBy(identity).collect { case (_, Seq(_, y, _*)) => y }
      duplicates.headOption.foreach(
        dup => OrgImpl.logError(expression.location, s"Duplicate when case for $dup")
      )

      whenControls.foreach(_.verify(context))
    }
  }

  private def checkMatchableTo(typeName: TypeName): Seq[String] = {
    whenControls.flatMap(_.whenValue.checkMatchableTo(typeName))
  }

  private def checkIsSObject(context: BlockVerifyContext): Seq[String] = {
    whenControls.flatMap(_.whenValue.checkIsSObject(context))
  }

  private def checkEnumValue(
    typeDeclaration: TypeDeclaration,
    context: BlockVerifyContext
  ): Seq[String] = {
    whenControls.flatMap(_.whenValue.checkEnumValue(typeDeclaration, context))
  }

  private def checkWhenElseIsLast(): Unit = {
    val notLastElse = whenControls.zipWithIndex
      .filter(_._1.whenValue.isInstanceOf[WhenElseValue])
      .find(_._2 != whenControls.length - 1)
    if (notLastElse.nonEmpty)
      OrgImpl.logError(notLastElse.get._1.location, "'when else' must be the last when block")
  }

  private def checkForDoubleNull(): Unit = {
    val literals = whenControls.flatMap(wc => {
      wc.whenValue match {
        case l: WhenLiteralsValue => l.literals
        case _                    => Seq()
      }
    })
    if (literals.count(_.isInstanceOf[WhenNullLiteral]) > 1)
      OrgImpl.logError(
        literals.last.location,
        "There should only be one 'when null' block in a switch"
      )
  }
}

object SwitchStatement {
  def construct(parser: CodeParser, switchStatement: SwitchStatementContext): SwitchStatement = {
    SwitchStatement(
      Expression.construct(switchStatement.expression()),
      CodeParser
        .toScala(switchStatement.whenControl())
        .map(wc => WhenControl.construct(parser, wc).withContext(wc))
        .toList
    )
  }
}
