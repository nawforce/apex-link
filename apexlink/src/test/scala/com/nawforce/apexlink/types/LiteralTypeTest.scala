/*
 Copyright (c) 2017 Kevin Jones, All rights reserved.
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
package com.nawforce.apexlink.types

import com.nawforce.apexlink.cst.{BoundStringLiteral, CST, Literal}
import com.nawforce.apexlink.names.TypeNames
import com.nawforce.pkgforce.names.{Name, Names, TypeName}
import com.nawforce.runtime.parsers.{CodeParser, Source, SourceData}
import com.nawforce.runtime.platform.Path
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class LiteralTypeTest extends AnyFunSuite with Matchers {
  def typeLiteral(data: String): Literal = {
    val source = Source(Path("Dummy.cls"), SourceData(""), 0, 0, None)
    CST.sourceContext.withValue(Some(source)) {
      val result = CodeParser(Path(""), SourceData(data)).parseLiteral()
      assert(result.issues.isEmpty)
      Literal.construct(result.value)
    }
  }

  def compareLiteral(p: String, r: TypeName): Unit = {
    val t = typeLiteral(p).getType
    assert(t != null)

    if (t.typeName != r) {
      System.out.println("Type mismatch:")
      System.out.println("Expected: " + r)
      System.out.println("Got: " + t)
      assert(false)
    }
  }

  def literal(value: String, r: TypeName = null): Unit =
    compareLiteral(value, r)

  test("Primary literal") {
    literal("0", TypeNames.Integer)
    literal("1", TypeNames.Integer)
    literal("0l", TypeNames.Long)
    literal("1l", TypeNames.Long)
    literal("0L", TypeNames.Long)
    literal("1L", TypeNames.Long)
    literal("''", TypeNames.String)
    literal("'a'", TypeNames.String)
    literal("'az'", TypeNames.String)
    literal("'\t'", TypeNames.String)
    literal("true", TypeNames.Boolean)
    literal("False", TypeNames.Boolean)
    literal("null", TypeName(Name("Null$"), Nil, Some(TypeName(Names.Internal))))
    literal("0.0", TypeNames.Decimal)
    literal(".0", TypeNames.Decimal)
    literal("0.123", TypeNames.Decimal)
    literal("0.123456789012345678901234567890123456789012345678", TypeNames.Decimal)
    literal("0.1234567890123456789012345678901234567890123456789", TypeNames.Double)
  }

  test("Bound string literal") {
    val aSet        = Set(Name("a"))
    val abSet       = Set(Name("a"), Name("b"))
    val abcSet      = Set(Name("a"), Name("b"), Name("c"))
    val abJoinedSet = Set(Name("ab"))
    val mixedSet    = Set(Name("1a2b3"))
    typeLiteral("':a'") should matchPattern { case BoundStringLiteral(bound) if bound == aSet => }
    typeLiteral("' :a'") should matchPattern { case BoundStringLiteral(bound) if bound == aSet => }
    typeLiteral("': a'") should matchPattern { case BoundStringLiteral(bound) if bound == aSet => }
    typeLiteral("':a '") should matchPattern { case BoundStringLiteral(bound) if bound == aSet => }
    typeLiteral("'  :  a  '") should matchPattern {
      case BoundStringLiteral(bound) if bound == aSet =>
    }
    typeLiteral("':ab'") should matchPattern {
      case BoundStringLiteral(bound) if bound == abJoinedSet =>
    }
    typeLiteral("':a b'") should matchPattern { case BoundStringLiteral(bound) if bound == aSet => }
    typeLiteral("'b:a'") should matchPattern { case BoundStringLiteral(bound) if bound == aSet => }
    typeLiteral("':1a2b3'") should matchPattern {
      case BoundStringLiteral(bound) if bound == mixedSet =>
    }
    typeLiteral("':a:b'") should matchPattern {
      case BoundStringLiteral(bound) if bound == abSet =>
    }
    typeLiteral("':a :b :c'") should matchPattern {
      case BoundStringLiteral(bound) if bound == abcSet =>
    }
  }
}
