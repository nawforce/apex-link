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

class TypePrimaryTest extends FunSuite
{
  def primary(p: String, r: TypeName, ctx: TypeContext = null) : Unit =
    TypeTestHelper.comparePrimary(p, r, ctx)

  test("Primary literal") {
    primary("0", TypeName.Integer)
    primary("1", TypeName.Integer)
    primary("0l", TypeName.Long)
    primary("1l", TypeName.Long)
    primary("0L", TypeName.Long)
    primary("1L", TypeName.Long)
    primary("''", TypeName.String)
    primary("'a'", TypeName.String)
    primary("'az'", TypeName.String)
    primary("'\t'", TypeName.String)
    primary("true", TypeName.Boolean)
    primary("False", TypeName.Boolean)
    primary("null", TypeName.Null)
    primary("0.0", TypeName.Decimal)
    primary(".0", TypeName.Decimal)
    primary("0.123", TypeName.Decimal)
    primary("0.123456789012345678901234567890123456789012345678", TypeName.Decimal)
    primary("0.1234567890123456789012345678901234567890123456789", TypeName.Double)
  }

  test("This literal") {
    val ctx = new TypeContextTest(_thisType = TypeName.Null)
    primary("this", TypeName.Null, ctx)
  }

  test("Super literal") {
    val ctx = new TypeContextTest(_superType = TypeName.Null)
    primary("super", TypeName.Null, ctx)
  }

  /* TODO Re-enable ?
  test("Field") {
    val ctx = new TypeContextTest(identifierTypes = Map(("anId", TypeName.Null)))
    primary("anId", TypeName.Null, ctx)
  }
  */
}
