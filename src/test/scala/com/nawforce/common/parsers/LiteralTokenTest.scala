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
package com.nawforce.common.parsers

import com.nawforce.common.ParserHelper
import org.scalatest.funsuite.AnyFunSuite

class LiteralTokenTest extends AnyFunSuite {

  test("empty string literal") {
    assert(ParserHelper.literal("''").StringLiteral() != null)
  }

  test("non-empty string literal") {
    assert(ParserHelper.literal("'abc'").StringLiteral() != null)
  }

  test("string literal with tab") {
    assert(ParserHelper.literal("'a\tbc'").StringLiteral() != null)
  }

  test("string literal with quote") {
    assert(ParserHelper.literal("'a\\'bc'").StringLiteral() != null)
  }

  test("string literal with unicode") {
    assert(ParserHelper.literal("'a\\u12f3xx'").StringLiteral() != null)
  }

  test("boolean literal true") {
    assert(ParserHelper.literal("true").BooleanLiteral() != null)
  }

  test("boolean literal false") {
    assert(ParserHelper.literal("false").BooleanLiteral() != null)
  }

  test("boolean literal true (mixed case)") {
    assert(ParserHelper.literal("trUe").BooleanLiteral() != null)
  }

  test("null literal") {
    assert(ParserHelper.literal("null").NULL() != null)
  }

  test("null literal (mixed case)") {
    assert(ParserHelper.literal("nuLl").NULL() != null)
  }

  test("integer literal zero") {
    assert(ParserHelper.literal("0").IntegerLiteral() != null)
  }

  test("integer literal zero long") {
    assert(ParserHelper.literal("0l").IntegerLiteral() != null)
  }

  test("integer literal one") {
    assert(ParserHelper.literal("1").IntegerLiteral() != null)
  }

  test("integer literal one long") {
    assert(ParserHelper.literal("1l").IntegerLiteral() != null)
  }

  test("integer literal ten") {
    assert(ParserHelper.literal("10").IntegerLiteral() != null)
  }

  test("integer literal ten long") {
    assert(ParserHelper.literal("10l").IntegerLiteral() != null)
  }

  test("number literal zero") {
    assert(ParserHelper.literal("0.0").NumberLiteral() != null)
  }

  test("number literal zero double") {
    assert(ParserHelper.literal("0.0d").NumberLiteral() != null)
  }

  test("number literal one") {
    assert(ParserHelper.literal("1.0").NumberLiteral() != null)
  }

  test("number literal one double") {
    assert(ParserHelper.literal("1.0d").NumberLiteral() != null)
  }

  test("number literal ten") {
    assert(ParserHelper.literal("10.0").NumberLiteral() != null)
  }

  test("number literal ten double") {
    assert(ParserHelper.literal("10.0d").NumberLiteral() != null)
  }
}
