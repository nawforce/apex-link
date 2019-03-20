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
import org.scalatest.FunSuite

class TypePrimaryTest extends FunSuite
{
  def primary(p: String, r: Type, ctx: TypeContext = null) : Unit =
    TypeTestHelper.comparePrimary(p, r, ctx)

  test("Primary literal") {
    primary("0", IntegerType(0))
    primary("1", IntegerType(0))
    primary("0l", LongType(0))
    primary("1l", LongType(0))
    primary("0L", LongType(0))
    primary("1L", LongType(0))
    primary("''", StringType(0))
    primary("'a'", StringType(0))
    primary("'az'", StringType(0))
    primary("'\t'", StringType(0))
    primary("true", BooleanType(0))
    primary("False", BooleanType(0))
    primary("null", NullType())
    primary("0.0", DecimalType(0))
    primary(".0", DecimalType(0))
    primary("0.123", DecimalType(0))
    primary("0.123456789012345678901234567890123456789012345678", DecimalType(0))
    primary("0.1234567890123456789012345678901234567890123456789", DoubleType(0))
  }

  test("This literal") {
    val ctx = new TypeContextTest(_thisType = NullType())
    primary("this", NullType(), ctx)
  }

  test("Super literal") {
    val ctx = new TypeContextTest(_superType = NullType())
    primary("super", NullType(), ctx)
  }

  test("Field") {
    val ctx = new TypeContextTest(identifierTypes = Map(("anId", NullType())))
    primary("anId", NullType(), ctx)
  }
}
