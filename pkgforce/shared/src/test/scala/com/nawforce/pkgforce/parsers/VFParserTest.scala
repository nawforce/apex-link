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

import com.nawforce.pkgforce.parsers.VFParserTest.ParserIssue
import com.nawforce.runtime.SourceBlob
import com.nawforce.runtime.parsers.{PageParser, Source, VFParser}
import com.nawforce.runtime.platform.Path
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class VFParserTest extends AnyFunSuite with Matchers {

  test("Empty page") {
    VFParserTest.parse("") should matchPattern {
      case Left(Seq(VFParserTest.ParserIssue(1, 0, err)))
          if err.startsWith("mismatched input '<EOF>' expecting {") =>
    }
  }

  test("Minimal page") {
    val ctx = parseOrThrow("<apex:page/>")
    assert(Option(ctx.element()).nonEmpty)
  }

  test("Single element") {
    val ctx = parseOrThrow("<apex:page></apex:page>")
    assert(Option(ctx.element()).nonEmpty)
  }

  test("Mismatched tags") {
    val ctx = parseOrThrow("<apex:page></apex:apex>")
    assert(Option(ctx.element()).nonEmpty)
  }

  test("Missing close tag") {
    VFParserTest.parse("<apex:page>") should matchPattern {
      case Left(Seq(VFParserTest.ParserIssue(1, 11, err)))
          if err.startsWith("mismatched input '<EOF>' expecting {") =>
    }
  }

  test("Duplicate root tags") {
    VFParserTest.parse("<apex:page/><apex:page/>") should matchPattern {
      case Left(Seq(VFParserTest.ParserIssue(1, 12, err)))
          if err.startsWith("mismatched input '<' expecting {") =>
    }
  }

  test("Empty attribute (single quote)") {
    val ctx = parseOrThrow("<apex:page foo=''/>")
    assert(Option(ctx.element()).nonEmpty)
    assert(ctx.element().attribute().size == 1)
    assert(PageParser.getText(ctx.element().attribute(0).attributeName()) == "foo")
    assert(ctx.element().attribute(0).attributeValues().size == 0)
  }

  test("Empty attribute (double quote)") {
    val ctx = parseOrThrow("<apex:page foo=\"\"/>")
    assert(Option(ctx.element()).nonEmpty)
    assert(PageParser.getText(ctx.element().attribute(0).attributeName()) == "foo")
    assert(ctx.element().attribute(0).attributeValues().size == 0)
  }

  test("Simple attribute (single quote)") {
    val ctx = parseOrThrow("<apex:page foo='abc'/>")
    assert(Option(ctx.element()).nonEmpty)
    assert(ctx.element().attribute().size == 1)
    assert(ctx.element().attribute(0).attributeValues().size == 1)
    assert(PageParser.getText(ctx.element().attribute(0).attributeValues(0)) == "abc")
  }

  test("Simple attribute (double quote)") {
    val ctx = parseOrThrow("<apex:page foo=\"abc\"/>")
    assert(Option(ctx.element()).nonEmpty)
    assert(ctx.element().attribute().size == 1)
    assert(PageParser.getText(ctx.element().attribute(0).attributeName()) == "foo")
    assert(ctx.element().attribute(0).attributeValues().size == 1)
    assert(PageParser.getText(ctx.element().attribute(0).attributeValues(0)) == "abc")
  }

  test("Multiple attributes") {
    val ctx = parseOrThrow("<apex:page foo=\"abc\" bar='xyz'/>")
    assert(Option(ctx.element()).nonEmpty)
    assert(ctx.element().attribute().size == 2)
    assert(PageParser.getText(ctx.element().attribute(0).attributeName()) == "foo")
    assert(ctx.element().attribute(0).attributeValues().size == 1)
    assert(PageParser.getText(ctx.element().attribute(0).attributeValues(0)) == "abc")
    assert(PageParser.getText(ctx.element().attribute(1).attributeName()) == "bar")
    assert(ctx.element().attribute(1).attributeValues().size == 1)
    assert(PageParser.getText(ctx.element().attribute(1).attributeValues(0)) == "xyz")
  }

  test("EL as attribute value text") {
    val ctx = parseOrThrow("<apex:page foo='{!bar}'/>")
    assert(Option(ctx.element()).nonEmpty)
    assert(ctx.element().attribute().size == 1)
    assert(PageParser.getText(ctx.element().attribute(0).attributeName()) == "foo")
    assert(ctx.element().attribute(0).attributeValues().size == 1)
    assert(PageParser.getText(ctx.element().attribute(0).attributeValues(0)) == "{!bar}")
  }

  test("EL in attribute value text") {
    val ctx = parseOrThrow("<apex:page foo='a{!bar}c'/>")
    assert(Option(ctx.element()).nonEmpty)
    assert(ctx.element().attribute().size == 1)
    assert(PageParser.getText(ctx.element().attribute(0).attributeName()) == "foo")
    assert(ctx.element().attribute(0).attributeValues().size == 3)
    assert(PageParser.getText(ctx.element().attribute(0).attributeValues(0)) == "a")
    assert(PageParser.getText(ctx.element().attribute(0).attributeValues(1)) == "{!bar}")
    assert(PageParser.getText(ctx.element().attribute(0).attributeValues(2)) == "c")
  }

  test("Simple chardata") {
    val ctx = parseOrThrow("<apex:page>abc</apex:page>")
    assert(Option(ctx.element()).nonEmpty)
    val content = PageParser.toScala(ctx.element().content()).get
    assert(content.chardata().size == 1)
    assert(PageParser.getText(content.chardata(0)) == "abc")
  }

  test("Entity as chardata") {
    val ctx = parseOrThrow("<apex:page>&amp;</apex:page>")
    assert(Option(ctx.element()).nonEmpty)
    val content = PageParser.toScala(ctx.element().content()).get
    assert(content.chardata().size == 1)
    assert(PageParser.getText(content.chardata(0)) == "&amp;")
  }

  test("Entity in chardata") {
    val ctx = parseOrThrow("<apex:page>a&amp;b</apex:page>")
    assert(Option(ctx.element()).nonEmpty)
    val content = PageParser.toScala(ctx.element().content()).get
    assert(content.chardata().size == 1)
    assert(PageParser.childCount(content.chardata(0)) == 3)
    assert(PageParser.getText(content.chardata(0).getChild(0)) == "a")
    assert(PageParser.getText(content.chardata(0).getChild(1)) == "&amp;")
    assert(PageParser.getText(content.chardata(0).getChild(2)) == "b")
  }

  test("CharRef as chardata") {
    val ctx = parseOrThrow("<apex:page>&#32;</apex:page>")
    assert(Option(ctx.element()).nonEmpty)
    val content = PageParser.toScala(ctx.element().content()).get
    assert(content.chardata().size == 1)
    assert(PageParser.getText(content.chardata(0)) == "&#32;")
  }

  test("CharRef in chardata") {
    val ctx = parseOrThrow("<apex:page>a&#32;b</apex:page>")
    assert(Option(ctx.element()).nonEmpty)
    val content = PageParser.toScala(ctx.element().content()).get
    assert(content.chardata().size == 1)
    assert(PageParser.childCount(content.chardata(0)) == 3)
    assert(PageParser.getText(content.chardata(0).getChild(0)) == "a")
    assert(PageParser.getText(content.chardata(0).getChild(1)) == "&#32;")
    assert(PageParser.getText(content.chardata(0).getChild(2)) == "b")
  }

  test("EL as chardata") {
    val ctx = parseOrThrow("<apex:page>{!foo}</apex:page>")
    assert(Option(ctx.element()).nonEmpty)
    val content = PageParser.toScala(ctx.element().content()).get
    assert(content.chardata().size == 1)
    assert(PageParser.childCount(content.chardata(0)) == 3)
    assert(PageParser.getText(content.chardata(0).getChild(0)) == "{!")
    assert(PageParser.getText(content.chardata(0).getChild(1)) == "foo")
    assert(PageParser.getText(content.chardata(0).getChild(2)) == "}")
  }

  test("EL in chardata") {
    val ctx = parseOrThrow("<apex:page>a{!foo}b</apex:page>")
    assert(Option(ctx.element()).nonEmpty)
    val content = PageParser.toScala(ctx.element().content()).get
    assert(content.chardata().size == 1)
    assert(PageParser.childCount(content.chardata(0)) == 5)
    assert(PageParser.getText(content.chardata(0).getChild(0)) == "a")
    assert(PageParser.getText(content.chardata(0).getChild(1)) == "{!")
    assert(PageParser.getText(content.chardata(0).getChild(2)) == "foo")
    assert(PageParser.getText(content.chardata(0).getChild(3)) == "}")
    assert(PageParser.getText(content.chardata(0).getChild(4)) == "b")
  }

  test("Whitespace before root") {
    val ctx = parseOrThrow(" \n <apex:page/>")
    assert(Option(ctx.element()).nonEmpty)
  }

  test("Whitespace after root") {
    val ctx = parseOrThrow("<apex:page/> \n ")
    assert(Option(ctx.element()).nonEmpty)
  }

  test("Comments before root") {
    val ctx = parseOrThrow("<!-- abc --><!-- xyz --><apex:page/>")
    assert(ctx.COMMENT().size == 2)
    assert(Option(ctx.element()).nonEmpty)
  }

  test("Comments in root") {
    val ctx = parseOrThrow("<apex:page><!-- abc --><!-- xyz --></apex:page>")
    assert(Option(ctx.element()).nonEmpty)
    val content = PageParser.toScala(ctx.element().content()).get
    assert(content.COMMENT().size == 2)
  }

  test("Comments after root") {
    val ctx = parseOrThrow("<apex:page/><!-- abc --><!-- xyz -->")
    assert(ctx.COMMENT().size == 2)
    assert(Option(ctx.element()).nonEmpty)
  }

  test("PI before root") {
    val ctx = parseOrThrow("<?foo a='b'?><apex:page/>")
    assert(ctx.processingInstruction().size == 1)
    assert(Option(ctx.element()).nonEmpty)
  }

  test("PIs in root") {
    val ctx = parseOrThrow("<apex:page><?foo a='b'?><?foo a='b'?></apex:page>")
    assert(Option(ctx.element()).nonEmpty)
    val content = PageParser.toScala(ctx.element().content()).get
    assert(content.processingInstruction().size == 2)
  }

  test("Child element") {
    val ctx = parseOrThrow("<apex:page><a></a></apex:page>")
    assert(Option(ctx.element()).nonEmpty)
    val content = PageParser.toScala(ctx.element().content()).get
    assert(content.element().size == 1)
  }

  test("Child element with whitespace") {
    val ctx = parseOrThrow("<apex:page> \n <a/> \n </apex:page>")
    assert(Option(ctx.element()).nonEmpty)
    val content = PageParser.toScala(ctx.element().content()).get
    assert(content.element().size == 1)
  }

  test("Child elements") {
    val ctx = parseOrThrow("<apex:page><a/><b></b></apex:page>")
    assert(Option(ctx.element()).nonEmpty)
    val content = PageParser.toScala(ctx.element().content()).get
    assert(content.element().size == 2)
  }

  test("Less than in script") {
    val ctx = parseOrThrow("<script> a < b</script>")
    assert(Option(ctx.element()).nonEmpty)
    assert(ctx.element().scriptChardata().SCRIPT_TEXT().size == 2)
  }

  test("Script with attributes") {
    val ctx = parseOrThrow("<script a='b'> a < b</script>")
    assert(Option(ctx.element()).nonEmpty)
    assert(ctx.element().attribute().size == 1)
    assert(ctx.element().scriptChardata().SCRIPT_TEXT().size == 2)
  }

  test("CDATA") {
    val ctx = parseOrThrow("<apex:page><![CDATA[x < y & z]]></apex:page>")
    assert(Option(ctx.element()).nonEmpty)
    val content = PageParser.toScala(ctx.element().content()).get
    assert(content.chardata().size == 1)
    assert(PageParser.getText(content.chardata(0).CDATA_TEXT(0)) == "x < y & z")
  }

  test("CDATA with EL") {
    val ctx = parseOrThrow("<apex:page><![CDATA[a{!foo}b]]></apex:page>")
    assert(Option(ctx.element()).nonEmpty)
    val content = PageParser.toScala(ctx.element().content()).get
    assert(content.chardata().size == 1)
    assert(PageParser.getText(content.chardata(0).EL_BODY(0)) == "foo")
  }

  class IssueException(val issues: Seq[ParserIssue]) extends Exception

  def parseOrThrow(contents: String): VFParser.VfUnitContext = {
    VFParserTest.parse(contents) match {
      case Right(ctx)   => ctx
      case Left(issues) => throw new IssueException(issues)
    }
  }
}

object VFParserTest {

  case class ParserIssue(line: Int, offset: Int, message: String)

  def parse(pageContents: String): Either[Seq[ParserIssue], VFParser.VfUnitContext] = {

    val parser = new PageParser(Source(Path("test.page"), SourceBlob(pageContents)))
    val result = parser.parsePage()
    if (result.issues.nonEmpty) {
      Left(
        result.issues.map(
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
