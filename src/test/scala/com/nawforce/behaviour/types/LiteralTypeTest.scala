/*
 [The "BSD licence"]
 Copyright (c) 2017 Kevin Jones
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
package com.nawforce.behaviour.types

import com.nawforce.cst._
import com.nawforce.types.TypeName
import org.scalatest.FunSuite

class LiteralTypeTest extends FunSuite
{
  def literal(p: String, r: TypeName, ctx: TypeContext = null) : Unit =
    TypeTestHelper.compareLiteral(p, r, ctx)

  test("Primary literal") {
    literal("0", TypeName.Integer)
    literal("1", TypeName.Integer)
    literal("0l", TypeName.Long)
    literal("1l", TypeName.Long)
    literal("0L", TypeName.Long)
    literal("1L", TypeName.Long)
    literal("''", TypeName.String)
    literal("'a'", TypeName.String)
    literal("'az'", TypeName.String)
    literal("'\t'", TypeName.String)
    literal("true", TypeName.Boolean)
    literal("False", TypeName.Boolean)
    literal("null", TypeName.Null)
    literal("0.0", TypeName.Decimal)
    literal(".0", TypeName.Decimal)
    literal("0.123", TypeName.Decimal)
    literal("0.123456789012345678901234567890123456789012345678", TypeName.Decimal)
    literal("0.1234567890123456789012345678901234567890123456789", TypeName.Double)
  }
}
