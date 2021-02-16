/*
 [The "BSD licence"]
 Copyright (c) 2020 Kevin Jones
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

import com.nawforce.common.parsers.VFParserTest.ParserIssue
import com.nawforce.common.path.PathFactory
import com.nawforce.runtime.SourceBlob
import com.nawforce.runtime.parsers.{PageParser, Source, VFParser}
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
    assert(ctx.element().attribute().size()==1)
    assert(ctx.element().attribute(0).Name.getText=="foo")
    assert(ctx.element().attribute(0).attributeValues().size==0)
  }

  test("Empty attribute (double quote)") {
    val ctx = parseOrThrow("<apex:page foo=\"\"/>")
    assert(Option(ctx.element()).nonEmpty)
    assert(ctx.element().attribute(0).Name.getText=="foo")
    assert(ctx.element().attribute(0).attributeValues().size==0)
  }

  test("Simple attribute (single quote)") {
    val ctx = parseOrThrow("<apex:page foo='abc'/>")
    assert(Option(ctx.element()).nonEmpty)
    assert(ctx.element().attribute().size()==1)
    assert(ctx.element().attribute(0).attributeValues().size==1)
    assert(ctx.element().attribute(0).attributeValues(0).getText=="abc")
  }

  test("Simple attribute (double quote)") {
    val ctx = parseOrThrow("<apex:page foo=\"abc\"/>")
    assert(Option(ctx.element()).nonEmpty)
    assert(ctx.element().attribute().size()==1)
    assert(ctx.element().attribute(0).Name.getText=="foo")
    assert(ctx.element().attribute(0).attributeValues().size==1)
    assert(ctx.element().attribute(0).attributeValues(0).getText=="abc")
  }

  test("Multiple attributes") {
    val ctx = parseOrThrow("<apex:page foo=\"abc\" bar='xyz'/>")
    assert(Option(ctx.element()).nonEmpty)
    assert(ctx.element().attribute().size()==2)
    assert(ctx.element().attribute(0).Name.getText=="foo")
    assert(ctx.element().attribute(0).attributeValues().size==1)
    assert(ctx.element().attribute(0).attributeValues(0).getText=="abc")
    assert(ctx.element().attribute(1).Name.getText=="bar")
    assert(ctx.element().attribute(1).attributeValues().size==1)
    assert(ctx.element().attribute(1).attributeValues(0).getText=="xyz")
  }

  test("Entity as attribute value text") {
    val ctx = parseOrThrow("<apex:page foo='&amp;'/>")
    assert(Option(ctx.element()).nonEmpty)
    assert(ctx.element().attribute().size()==1)
    assert(ctx.element().attribute(0).Name.getText=="foo")
    assert(ctx.element().attribute(0).attributeValues().size==1)
    assert(ctx.element().attribute(0).attributeValues(0).getText=="&amp;")
  }

  test("Entity in attribute value text") {
    val ctx = parseOrThrow("<apex:page foo='a&amp;c'/>")
    assert(Option(ctx.element()).nonEmpty)
    assert(ctx.element().attribute().size()==1)
    assert(ctx.element().attribute(0).Name.getText=="foo")
    assert(ctx.element().attribute(0).attributeValues().size==3)
    assert(ctx.element().attribute(0).attributeValues(0).getText=="a")
    assert(ctx.element().attribute(0).attributeValues(1).getText=="&amp;")
    assert(ctx.element().attribute(0).attributeValues(2).getText=="c")
  }

  test("CharRef as attribute value text") {
    val ctx = parseOrThrow("<apex:page foo='&#32;'/>")
    assert(Option(ctx.element()).nonEmpty)
    assert(ctx.element().attribute().size()==1)
    assert(ctx.element().attribute(0).Name.getText=="foo")
    assert(ctx.element().attribute(0).attributeValues().size==1)
    assert(ctx.element().attribute(0).attributeValues(0).getText=="&#32;")
  }

  test("CharRef in attribute value text") {
    val ctx = parseOrThrow("<apex:page foo='a&#32;c'/>")
    assert(Option(ctx.element()).nonEmpty)
    assert(ctx.element().attribute().size()==1)
    assert(ctx.element().attribute(0).Name.getText=="foo")
    assert(ctx.element().attribute(0).attributeValues().size==3)
    assert(ctx.element().attribute(0).attributeValues(0).getText=="a")
    assert(ctx.element().attribute(0).attributeValues(1).getText=="&#32;")
    assert(ctx.element().attribute(0).attributeValues(2).getText=="c")
  }

  test("EL as attribute value text") {
    val ctx = parseOrThrow("<apex:page foo='{!bar}'/>")
    assert(Option(ctx.element()).nonEmpty)
    assert(ctx.element().attribute().size()==1)
    assert(ctx.element().attribute(0).Name.getText=="foo")
    assert(ctx.element().attribute(0).attributeValues().size==1)
    assert(ctx.element().attribute(0).attributeValues(0).getText=="{!bar}")
  }

  test("EL in attribute value text") {
    val ctx = parseOrThrow("<apex:page foo='a{!bar}c'/>")
    assert(Option(ctx.element()).nonEmpty)
    assert(ctx.element().attribute().size()==1)
    assert(ctx.element().attribute(0).Name.getText=="foo")
    assert(ctx.element().attribute(0).attributeValues().size==3)
    assert(ctx.element().attribute(0).attributeValues(0).getText=="a")
    assert(ctx.element().attribute(0).attributeValues(1).getText=="{!bar}")
    assert(ctx.element().attribute(0).attributeValues(2).getText=="c")
  }

  test("Simple chardata") {
    val ctx = parseOrThrow("<apex:page>abc</apex:page>")
    assert(Option(ctx.element()).nonEmpty)
    assert(ctx.element().content().chardata().size()==1)
    assert(ctx.element().content().chardata(0).getText=="abc")
  }

  test("Entity as chardata") {
    val ctx = parseOrThrow("<apex:page>&amp;</apex:page>")
    assert(Option(ctx.element()).nonEmpty)
    assert(ctx.element().content().chardata().size()==1)
    assert(ctx.element().content().chardata(0).getText=="&amp;")
  }

  test("Entity in chardata") {
    val ctx = parseOrThrow("<apex:page>a&amp;b</apex:page>")
    assert(Option(ctx.element()).nonEmpty)
    assert(ctx.element().content().chardata().size()==1)
    assert(ctx.element().content().chardata(0).children.size()==3)
    assert(ctx.element().content().chardata(0).children.get(0).getText=="a")
    assert(ctx.element().content().chardata(0).children.get(1).getText=="&amp;")
    assert(ctx.element().content().chardata(0).children.get(2).getText=="b")
  }

  test("CharRef as chardata") {
    val ctx = parseOrThrow("<apex:page>&#32;</apex:page>")
    assert(Option(ctx.element()).nonEmpty)
    assert(ctx.element().content().chardata().size()==1)
    assert(ctx.element().content().chardata(0).getText=="&#32;")
  }

  test("CharRef in chardata") {
    val ctx = parseOrThrow("<apex:page>a&#32;b</apex:page>")
    assert(Option(ctx.element()).nonEmpty)
    assert(ctx.element().content().chardata().size()==1)
    assert(ctx.element().content().chardata(0).children.size()==3)
    assert(ctx.element().content().chardata(0).children.get(0).getText=="a")
    assert(ctx.element().content().chardata(0).children.get(1).getText=="&#32;")
    assert(ctx.element().content().chardata(0).children.get(2).getText=="b")
  }

  test("EL as chardata") {
    val ctx = parseOrThrow("<apex:page>{!foo}</apex:page>")
    assert(Option(ctx.element()).nonEmpty)
    assert(ctx.element().content().chardata().size()==1)
    assert(ctx.element().content().chardata(0).children.size()==3)
    assert(ctx.element().content().chardata(0).children.get(0).getText=="{!")
    assert(ctx.element().content().chardata(0).children.get(1).getText=="foo")
    assert(ctx.element().content().chardata(0).children.get(2).getText=="}")
  }

  test("EL in chardata") {
    val ctx = parseOrThrow("<apex:page>a{!foo}b</apex:page>")
    assert(Option(ctx.element()).nonEmpty)
    assert(ctx.element().content().chardata().size()==1)
    assert(ctx.element().content().chardata(0).children.size()==5)
    assert(ctx.element().content().chardata(0).children.get(0).getText=="a")
    assert(ctx.element().content().chardata(0).children.get(1).getText=="{!")
    assert(ctx.element().content().chardata(0).children.get(2).getText=="foo")
    assert(ctx.element().content().chardata(0).children.get(3).getText=="}")
    assert(ctx.element().content().chardata(0).children.get(4).getText=="b")
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
    assert(ctx.COMMENT().size()==2)
    assert(Option(ctx.element()).nonEmpty)
  }

  test("Comments in root") {
    val ctx = parseOrThrow("<apex:page><!-- abc --><!-- xyz --></apex:page>")
    assert(Option(ctx.element()).nonEmpty)
    assert(ctx.element().content().COMMENT().size()==2)
  }

  test("Comments after root") {
    val ctx = parseOrThrow("<apex:page/><!-- abc --><!-- xyz -->")
    assert(ctx.COMMENT().size()==2)
    assert(Option(ctx.element()).nonEmpty)
  }

  test("PI before root") {
    val ctx = parseOrThrow("<?foo a='b'?><apex:page/>")
    assert(ctx.processingInstruction().size()==1)
    assert(Option(ctx.element()).nonEmpty)
  }

  test("PIs in root") {
    val ctx = parseOrThrow("<apex:page><?foo a='b'?><?foo a='b'?></apex:page>")
    assert(Option(ctx.element()).nonEmpty)
    assert(ctx.element().content().processingInstruction().size()==2)
  }

  class IssueException(val issues: Seq[ParserIssue]) extends Exception

  def parseOrThrow(contents: String): VFParser.VfUnitContext = {
    VFParserTest.parse(contents) match {
      case Right(ctx) => ctx
      case Left(issues) => throw new IssueException(issues)
    }
  }
}

object VFParserTest {

  case class ParserIssue(line: Int, offset: Int, message: String)

  def parse(pageContents: String): Either[Seq[ParserIssue], VFParser.VfUnitContext] = {

    val parser = new PageParser(Source(PathFactory("test.page"), SourceBlob(pageContents)))
    parser.parsePage() match {
      case Left(issues) =>
        Left(
          issues.toIndexedSeq.map(
            issue =>
              ParserIssue(issue.diagnostic.location.startLine,
                          issue.diagnostic.location.startPosition,
                          issue.diagnostic.message)))
      case Right(ctx) =>
        Right(ctx)
    }
  }
}
