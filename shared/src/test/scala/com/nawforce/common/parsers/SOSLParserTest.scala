/*
 [The "BSD licence"]
 Copyright (c) 2021 Kevin Jones
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

import com.nawforce.common.path.PathFactory
import com.nawforce.runtime.SourceBlob
import com.nawforce.runtime.parsers.{ApexParser, CodeParser, Source}
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
}

object SOSLParser {

  case class ParserIssue(line: Int, offset: Int, message: String)

  def parse(sosl: String): Either[Seq[ParserIssue], ApexParser.SoslLiteralContext] = {

    val parser = new CodeParser(Source(PathFactory("test.sosl"), SourceBlob(sosl)))
    parser.parseSOSL() match {
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
