/*
 Copyright (c) 2020 Kevin Jones, All rights reserved.
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

package com.nawforce.pkgforce.parsers

import com.nawforce.apexparser.ApexParser
import com.nawforce.runtime.SourceBlob
import com.nawforce.runtime.parsers.{CodeParser, Source}
import com.nawforce.runtime.platform.Path
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class SOQLParserTest extends AnyFunSuite with Matchers {

  test("Empty query") {
    SOQLParser.parse(" ") should matchPattern {
      case Left(Seq(SOQLParser.ParserIssue(1, 1, "mismatched input '<EOF>' expecting 'select'"))) =>
    }
  }

  test("Whitespace query") {
    SOQLParser.parse(" ") should matchPattern {
      case Left(Seq(SOQLParser.ParserIssue(1, 1, "mismatched input '<EOF>' expecting 'select'"))) =>
    }
  }

  test("Missing fields") {
    SOQLParser.parse("select") should matchPattern {
      case Left(Seq(SOQLParser.ParserIssue(1, 6, err)))
          if err.startsWith("mismatched input '<EOF>' expecting {") =>
    }
  }

  test("Missing from") {
    SOQLParser.parse("select A") should matchPattern {
      case Left(Seq(SOQLParser.ParserIssue(1, 8, err)))
          if err.startsWith("mismatched input '<EOF>' expecting {") =>
    }
  }

  test("Missing table") {
    SOQLParser.parse("select A from ") should matchPattern {
      case Left(Seq(SOQLParser.ParserIssue(1, 14, err)))
          if err.startsWith("mismatched input '<EOF>' expecting {") =>
    }
  }

  test("Single field") {
    assert(SOQLParser.parse("Select A from Table").isRight)
  }

  test("Single field, multi-table") {
    assert(SOQLParser.parse("Select A from Table1, Table2").isRight)
  }

  test("Multi field") {
    assert(SOQLParser.parse("Select A, B, C from Table").isRight)
  }

  test("Simple aggregate") {
    assert(SOQLParser.parse("Select A, AVG(B), C from Table").isRight)
  }

  test("Count aggregate") {
    assert(SOQLParser.parse("Select A, B, COUNT() from Table").isRight)
  }

  test("Unknown aggregate") {
    SOQLParser.parse("Select UNKNOWN(A), B, C from Table") should matchPattern {
      case Left(Seq(SOQLParser.ParserIssue(1, 14, err)))
          if err.startsWith("mismatched input '(' expecting {") =>
    }
  }

  test("Simple sub-query") {
    assert(SOQLParser.parse("Select A, (Select B from Table2) from Table1").isRight)
  }

  test("Sub-query with aggregate") {
    assert(SOQLParser.parse("Select A, (Select COUNT(B) from Table2) from Table1").isRight)
  }

  test("Multiple sub-queries") {
    assert(
      SOQLParser
        .parse("Select A, (Select COUNT(B) from Table2), (Select C from Table3) from Table1")
        .isRight
    )
  }

  test("Nested sub-query") {
    SOQLParser.parse(
      "Select A, (Select (Select C from Table3) from Table2) from Table1"
    ) should matchPattern {
      case Left(Seq(SOQLParser.ParserIssue(1, 18, err)))
          if err.startsWith("extraneous input '(' expecting {") =>
    }
  }

  test("Simple typeof") {
    assert(
      SOQLParser
        .parse("""Select
        |   Typeof A
        |     When A Then B, C
        |   end
        |   from Table
        |""".stripMargin)
        .isRight
    )
  }

  test("typeof when with else") {
    assert(
      SOQLParser
        .parse("""Select
        |   Typeof A
        |     When A Then B, C
        |     Else D
        |   end
        |   from Table
        |""".stripMargin)
        .isRight
    )
  }

  test("typeof multiple when") {
    assert(
      SOQLParser
        .parse("""Select
        |   Typeof A
        |     When A Then B, C
        |     When B Then B, C
        |     Else D
        |   end
        |   from Table
        |""".stripMargin)
        .isRight
    )
  }

  test("Simple scope use") {
    assert(SOQLParser.parse("Select A from Table Using scope SomeScope").isRight)
  }

  test("Sub-query scope use") {
    SOQLParser.parse(
      "Select A, (Select B from Table2 Using Scope SomeScope) from Table1"
    ) should matchPattern {
      case Left(Seq(SOQLParser.ParserIssue(1, 32, err), _))
          if err.startsWith("missing ')' at 'Using'") =>
    }
  }

  test("Simple where") {
    assert(SOQLParser.parse("Select A from Table Where B = 1").isRight)
  }

  test("Logical where") {
    assert(SOQLParser.parse("Select A from Table Where B > 1 AND (C = true OR D='hello')").isRight)
  }

  test("Where bound expr") {
    assert(SOQLParser.parse("Select A from Table Where B in :a").isRight)
  }

  test("Simple Group by ") {
    assert(SOQLParser.parse("Select A from Table Group By B Having C <> 1").isRight)
  }

  test("Rollup") {
    assert(SOQLParser.parse("Select A from Table Group By Rollup(B, C)").isRight)
  }

  test("Cube") {
    assert(SOQLParser.parse("Select A from Table Group By Cube(B, C)").isRight)
  }

  test("Order by") {
    assert(SOQLParser.parse("Select A from Table Order By B desc nulls last").isRight)
  }

  test("Order by function") {
    assert(SOQLParser.parse("Select A from Table Order By Count(B) asc, C desc").isRight)
  }

  test("Limit") {
    assert(SOQLParser.parse("Select A from Table limit 100").isRight)
  }

  test("Limit bound expr") {
    assert(SOQLParser.parse("Select A from Table limit : a-10").isRight)
  }

  test("Offset") {
    assert(SOQLParser.parse("Select A from Table offset 100").isRight)
  }

  test("Offset bound expr") {
    assert(SOQLParser.parse("Select A from Table offset :a-10").isRight)
  }

  test("All rows") {
    assert(SOQLParser.parse("Select A from Table All Rows").isRight)
  }

  test("Single For") {
    assert(SOQLParser.parse("Select A from Table For View").isRight)
  }

  test("Multiple For") {
    assert(SOQLParser.parse("Select A from Table For Update For Reference").isRight)
  }
}

object SOQLParser {

  case class ParserIssue(line: Int, offset: Int, message: String)

  def parse(soql: String): Either[Seq[ParserIssue], ApexParser.QueryContext] = {

    val parser = new CodeParser(Source(Path("test.soql"), SourceBlob(soql)))
    val result = parser.parseSOQL()
    if (result.issues.nonEmpty) {
      Left(
        result.issues.toIndexedSeq.map(
          issue =>
            ParserIssue(
              issue.diagnostic.location.startLine,
              issue.diagnostic.location.startPosition,
              issue.diagnostic.message
            )
        )
      )
    } else {
      Right(result.value)
    }
  }
}
