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

package com.nawforce.apexlink.parsers

import com.nawforce.apexparser.ApexParser.LiteralContext
import com.nawforce.runtime.parsers.{CodeParser, SourceData}
import com.nawforce.runtime.platform.Path
import org.scalatest.funsuite.AnyFunSuite

class LiteralTokenTest extends AnyFunSuite {

  private def literal(literal: String): LiteralContext = {
    val result = CodeParser(Path(""), SourceData(literal)).parseLiteral()
    assert(result.issues.isEmpty)
    result.value
  }

  test("empty string literal") {
    assert(literal("''").StringLiteral() != null)
  }

  test("non-empty string literal") {
    assert(literal("'abc'").StringLiteral() != null)
  }

  test("string literal with tab") {
    assert(literal("'a\tbc'").StringLiteral() != null)
  }

  test("string literal with quote") {
    assert(literal("'a\\'bc'").StringLiteral() != null)
  }

  test("string literal with unicode") {
    assert(literal("'a\\u12f3xx'").StringLiteral() != null)
  }

  test("boolean literal true") {
    assert(literal("true").BooleanLiteral() != null)
  }

  test("boolean literal false") {
    assert(literal("false").BooleanLiteral() != null)
  }

  test("boolean literal true (mixed case)") {
    assert(literal("trUe").BooleanLiteral() != null)
  }

  test("null literal") {
    assert(literal("null").NULL() != null)
  }

  test("null literal (mixed case)") {
    assert(literal("nuLl").NULL() != null)
  }

  test("integer literal zero") {
    assert(literal("0").IntegerLiteral() != null)
  }

  test("long integer literal zero long") {
    assert(literal("0l").LongLiteral() != null)
  }

  test("integer literal one") {
    assert(literal("1").IntegerLiteral() != null)
  }

  test("long integer literal one long") {
    assert(literal("1l").LongLiteral() != null)
  }

  test("integer literal ten") {
    assert(literal("10").IntegerLiteral() != null)
  }

  test("long integer literal ten long") {
    assert(literal("10l").LongLiteral() != null)
  }

  test("number literal zero") {
    assert(literal("0.0").NumberLiteral() != null)
  }

  test("number literal zero double") {
    assert(literal("0.0d").NumberLiteral() != null)
  }

  test("number literal one") {
    assert(literal("1.0").NumberLiteral() != null)
  }

  test("number literal one double") {
    assert(literal("1.0d").NumberLiteral() != null)
  }

  test("number literal ten") {
    assert(literal("10.0").NumberLiteral() != null)
  }

  test("number literal ten double") {
    assert(literal("10.0d").NumberLiteral() != null)
  }
}
