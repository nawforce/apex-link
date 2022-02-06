/*
 Copyright (c) 2021 Kevin Jones, All rights reserved.
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

import scala.collection.immutable.ArraySeq

class SOSLParserTest extends AnyFunSuite with Matchers {

  test("Empty find") {
    assert(SOSLParser.parse("[Find {}]").isRight)
  }

  test("Simple find") {
    assert(SOSLParser.parse("[Find {something}]").isRight)
  }

  test("Escaped terminator in find") {
    assert(SOSLParser.parse("[Find {some\\}thing}]").isRight)
  }

  test("Unescaped terminator in find") {
    SOSLParser.parse("[Find {some}thing}]") should matchPattern {
      case Left(ArraySeq(SOSLParser.ParserIssue(1, 12, err)))
          if err.startsWith("mismatched input 'thing' expecting") =>
    }
  }

  test("Bound find") {
    assert(SOSLParser.parse("[Find :foo]").isRight)
  }

  test("In clause") {
    assert(SOSLParser.parse("[Find :foo IN PHONE FIELDS]").isRight)
  }

  test("Returning Object") {
    assert(SOSLParser.parse("[Find :foo RETURNING Account]").isRight)
  }

  test("Returning Fields") {
    assert(SOSLParser.parse("[Find :foo RETURNING Account(Id, Name)]").isRight)
  }

  test("Returning Multiple Objects/Fields") {
    assert(SOSLParser.parse("[Find :foo RETURNING Account(Id, Name), Contact(Foo)]").isRight)
  }

  test("Returning Where") {
    assert(
      SOSLParser
        .parse("[Find :foo RETURNING Account (Name, Industry WHERE Name like 'test')]")
        .isRight
    )
  }

  test("Returning Listview") {
    assert(
      SOSLParser
        .parse("[Find :foo RETURNING Account(Id, Name USING ListView=MVPCustomers)]")
        .isRight
    )
  }

  test("Returning order by") {
    assert(SOSLParser.parse("[Find :foo RETURNING Account (Name, Industry ORDER BY Name)]").isRight)
  }

  test("Returning limit and offset") {
    assert(
      SOSLParser.parse("[Find :foo RETURNING Account (Name, Industry LIMIT 5 OFFSET 10)]").isRight
    )
  }

  test("With Division") {
    assert(
      SOSLParser
        .parse(
          "[FIND {test} RETURNING Account (id where name like '%test%'), " +
            "Contact (id where name like '%test%') WITH DIVISION = 'Global']"
        )
        .isRight
    )
  }

  test("With Data Category") {
    assert(
      SOSLParser
        .parse(
          "[FIND {tourism} RETURNING FAQ__kav(Id, Title WHERE PublishStatus='archived') " +
            "WITH DATA CATEGORY Geography__c AT Iceland__c]"
        )
        .isRight
    )
  }

  test("With Snippet") {
    assert(
      SOSLParser
        .parse(
          "[FIND {San Francisco} IN ALL FIELDS RETURNING " +
            "KnowledgeArticleVersion(id, title WHERE PublishStatus = 'Online' AND Language = 'en_US') " +
            "WITH SNIPPET(target_length=120)]"
        )
        .isRight
    )
  }

  test("With Network") {
    assert(
      SOSLParser
        .parse(
          "[FIND {test} RETURNING User (id), FeedItem (id, ParentId WHERE " +
            "CreatedDate = THIS_YEAR Order by CreatedDate DESC) WITH NETWORK " +
            "IN ('NetworkId1', 'NetworkId2', 'NetworkId3')]"
        )
        .isRight
    )
  }

  test("With Network =") {
    assert(
      SOSLParser
        .parse(
          "[FIND {test} RETURNING User (id), FeedItem (id, ParentId WHERE CreatedDate =  " +
            "THIS_YEAR Order by CreatedDate DESC) WITH NETWORK = '00000000000000']"
        )
        .isRight
    )
  }

  test("With Pricebook") {
    assert(
      SOSLParser
        .parse("[Find {laptop} RETURNING Product2 WITH PricebookId = '01sxx0000002MffAAE']")
        .isRight
    )
  }

  test("With Metadata") {
    assert(
      SOSLParser.parse("[FIND {Acme} RETURNING Account(Id, Name) WITH METADATA='LABELS']").isRight
    )
  }

  test("Limit Clause") {
    assert(
      SOSLParser.parse("[FIND {test} RETURNING Account(id LIMIT 20), Contact LIMIT 100]").isRight
    )
  }

  test("Update Tracking") {
    assert(
      SOSLParser
        .parse(
          "[FIND {Keyword} RETURNING KnowledgeArticleVersion (Title WHERE " +
            "PublishStatus='Online' and language='en_US') UPDATE TRACKING ]"
        )
        .isRight
    )
  }
}

object SOSLParser {

  case class ParserIssue(line: Int, offset: Int, message: String)

  def parse(sosl: String): Either[Seq[ParserIssue], ApexParser.SoslLiteralContext] = {

    val parser = new CodeParser(Source(Path("test.sosl"), SourceBlob(sosl)))
    val result = parser.parseSOSL()
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
