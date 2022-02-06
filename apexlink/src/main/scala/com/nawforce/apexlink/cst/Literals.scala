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

import com.nawforce.apexlink.types.core.TypeDeclaration
import com.nawforce.apexlink.types.platform.PlatformTypes
import com.nawforce.apexparser.ApexParser.LiteralContext
import com.nawforce.pkgforce.names.Name
import com.nawforce.runtime.parsers.CodeParser

sealed abstract class Literal() {
  def getType: TypeDeclaration

  def verify(context: ExpressionVerifyContext): Unit = {}
}

case object IntegerLiteral extends Literal {
  override def getType: TypeDeclaration = PlatformTypes.integerType
}

case object LongLiteral extends Literal {
  override def getType: TypeDeclaration = PlatformTypes.longType
}

case object DoubleLiteral extends Literal {
  override def getType: TypeDeclaration = PlatformTypes.doubleType
}

case object DecimalLiteral extends Literal {
  override def getType: TypeDeclaration = PlatformTypes.decimalType
}

case object StringLiteral extends Literal {
  private val boundMatch  = ":\\s*[0-9a-zA-Z_]*".r
  private val boundPrefix = ":\\s*".r

  override def getType: TypeDeclaration = PlatformTypes.stringType

  def apply(value: String): Literal = {
    val bound = boundMatch
      .findAllIn(value)
      .map(v => Name(boundPrefix.replaceFirstIn(v, "")))
      .toSet
    if (bound.nonEmpty)
      BoundStringLiteral(bound)
    else
      StringLiteral
  }
}

final case class BoundStringLiteral(bound: Set[Name]) extends Literal {
  override def getType: TypeDeclaration = PlatformTypes.stringType

  override def verify(context: ExpressionVerifyContext): Unit = {
    bound.foreach(bound => {
      if (context.isVar(bound, markUsed = true).isEmpty) {
        context.thisType
          .findField(bound, None)
          .map(field => context.addDependency(field))
      }
    })
  }
}

case object BooleanLiteral extends Literal {
  override def getType: TypeDeclaration = PlatformTypes.booleanType
}

case object NullLiteral extends Literal {
  override def getType: TypeDeclaration = PlatformTypes.nullType
}

object IntegerOrLongLiteral {
  def apply(value: String): Literal = {
    if (value.endsWith("l") || value.endsWith("L"))
      LongLiteral
    else
      IntegerLiteral
  }
}

object DoubleOrDecimalLiteral {
  def apply(value: String): Literal = {
    if (value.length() > 50)
      DoubleLiteral
    else
      DecimalLiteral
  }
}

object Literal {
  def construct(from: LiteralContext): Literal = {
    CodeParser
      .toScala(from.IntegerLiteral())
      .map(x => IntegerOrLongLiteral(CodeParser.getText(x)))
      .orElse(
        CodeParser
          .toScala(from.LongLiteral())
          .map(x => IntegerOrLongLiteral(CodeParser.getText(x)))
      )
      .orElse(
        CodeParser
          .toScala(from.NumberLiteral())
          .map(x => DoubleOrDecimalLiteral(CodeParser.getText(x)))
      )
      .orElse(
        CodeParser
          .toScala(from.StringLiteral())
          .map(x => StringLiteral(CodeParser.getText(x)))
      )
      .orElse(CodeParser.toScala(from.BooleanLiteral()).map(_ => BooleanLiteral))
      .getOrElse(NullLiteral)
  }
}
