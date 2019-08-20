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

import com.nawforce.parsers.ApexParser.LiteralContext
import com.nawforce.types.{DependencyHolder, PlatformTypes, TypeName}

sealed abstract class Literal() extends CST {
  override def children(): List[CST] = Nil

  def getType: DependencyHolder
}

final case class IntegerLit(value: String) extends Literal {
  override def getType: DependencyHolder =
    if (value.endsWith("l") || value.endsWith("L"))
      PlatformTypes.getType(TypeName.Long).get
    else
      PlatformTypes.getType(TypeName.Integer).get
}

final case class NumberLit(value: String) extends Literal {
  override def getType: DependencyHolder =
    if (value.length() > 50)
      PlatformTypes.getType(TypeName.Double).get
    else
      PlatformTypes.getType(TypeName.Decimal).get
}

final case class StringLit(value: String) extends Literal {
  override def getType: DependencyHolder = PlatformTypes.getType(TypeName.String).get
}

final case class BooleanLit(value: String) extends Literal {
  override def getType: DependencyHolder = PlatformTypes.getType(TypeName.Boolean).get
}

final case class NullLit() extends Literal {
  override def getType: DependencyHolder = PlatformTypes.nullType
}

object Literal {
  def construct(from: LiteralContext, context: ConstructContext): Literal = {
    val cst =
      if (from.IntegerLiteral() != null)
        IntegerLit(from.IntegerLiteral().getText)
      else if (from.NumberLiteral() != null)
        NumberLit(from.NumberLiteral().getText)
      else if (from.StringLiteral() != null)
        StringLit(from.StringLiteral().getText)
      else if (from.BooleanLiteral() != null)
        BooleanLit(from.BooleanLiteral().getText)
      else
        NullLit()
    cst.withContext(from, context)
  }
}
