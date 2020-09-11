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

import com.nawforce.common.types.core.TypeDeclaration
import com.nawforce.common.types.platform.PlatformTypes
import com.nawforce.runtime.parsers.ApexParser.LiteralContext
import com.nawforce.runtime.parsers.CodeParser

sealed abstract class Literal() extends CST {
  def getType: TypeDeclaration
}

final case class IntegerLiteral(value: String) extends Literal {
  override def getType: TypeDeclaration =
    if (value.endsWith("l") || value.endsWith("L"))
      PlatformTypes.longType
    else
      PlatformTypes.integerType
}

final case class NumberLiteral(value: String) extends Literal {
  override def getType: TypeDeclaration =
    if (value.length() > 50)
      PlatformTypes.doubleType
    else
      PlatformTypes.decimalType
}

final case class StringLiteral(value: String) extends Literal {
  override def getType: TypeDeclaration = PlatformTypes.stringType
}

final case class BooleanLiteral(value: String) extends Literal {
  override def getType: TypeDeclaration = PlatformTypes.booleanType
}

final case class NullLiteral() extends Literal {
  override def getType: TypeDeclaration = PlatformTypes.nullType
}

object Literal {
  def construct(from: LiteralContext): Literal = {
    val cst: Option[Literal] =
      CodeParser
        .toScala(from.IntegerLiteral())
        .map(x => IntegerLiteral(CodeParser.getText(x)))
        .orElse(
          CodeParser.toScala(from.NumberLiteral()).map(x => NumberLiteral(CodeParser.getText(x))))
        .orElse(
          CodeParser.toScala(from.StringLiteral()).map(x => StringLiteral(CodeParser.getText(x))))
        .orElse(
          CodeParser.toScala(from.BooleanLiteral()).map(x => BooleanLiteral(CodeParser.getText(x))))
        .orElse(Some(NullLiteral()))
    cst.get.withContext(from)
  }
}
