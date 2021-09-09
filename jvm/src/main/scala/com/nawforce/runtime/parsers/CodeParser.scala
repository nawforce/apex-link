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
package com.nawforce.runtime.parsers

import com.nawforce.apexparser.{ApexLexer, ApexParser, CaseInsensitiveInputStream}
import com.nawforce.pkgforce.diagnostics.{IssuesAnd, Location}
import com.nawforce.pkgforce.path.PathLike
import com.nawforce.runtime.parsers.CodeParser.ParserRuleContext
import org.antlr.v4.runtime.CommonTokenStream

import java.io.ByteArrayInputStream
import scala.jdk.CollectionConverters._

/** Apex class parser helper */
class CodeParser(val source: Source) {
  private val cis = source.asInsensitiveStream

  var lastTokenStream: Option[CommonTokenStream] = None

  def parseClass(): IssuesAnd[ApexParser.CompilationUnitContext] = {
    parse(parser => parser.compilationUnit())
  }

  def parseTrigger(): IssuesAnd[ApexParser.TriggerUnitContext] = {
    parse(parser => parser.triggerUnit())
  }

  def parseBlock(): IssuesAnd[ApexParser.BlockContext] = {
    parse(parser => parser.block())
  }

  def parseSOQL(): IssuesAnd[ApexParser.QueryContext] = {
    parse(parser => parser.query())
  }

  def parseSOSL(): IssuesAnd[ApexParser.SoslLiteralContext] = {
    parse(parser => parser.soslLiteral())
  }

  def parseExpression(): IssuesAnd[ApexParser.ExpressionContext] = {
    parse(parser => parser.expression())
  }

  // Test use only
  def parseLiteral(): IssuesAnd[ApexParser.LiteralContext] = {
    parse(parser => parser.literal())
  }

  /** Find a location for a rule, adapts based on source offsets to give absolute position in file */
  def getPathAndLocation(context: ParserRuleContext): (PathLike, Location) = {
    source.getLocation(context)
  }

  /** Extract the source used for a parser rule */
  def extractSource(context: ParserRuleContext): Source = {
    source.extractSource(context)
  }

  def parse[T](parse: ApexParser => T): IssuesAnd[T] = {
    CodeParser.autoClearCache()

    lastTokenStream = None
    val tokenStream = new CommonTokenStream(new ApexLexer(cis))
    tokenStream.fill()

    val listener = new CollectingErrorListener(source.path.toString)
    val parser = new ApexParser(tokenStream)
    parser.removeErrorListeners()
    parser.addErrorListener(listener)

    val result = parse(parser)
    lastTokenStream = Some(tokenStream)
    IssuesAnd(listener.issues, result)
  }
}

object CodeParser {
  type ParserRuleContext = org.antlr.v4.runtime.ParserRuleContext
  type TerminalNode = org.antlr.v4.runtime.tree.TerminalNode

  private var useCount = 0

  def apply(path: PathLike, code: SourceData): CodeParser = {
    new CodeParser(Source(path, code, 0, 0, None))
  }

  private def autoClearCache(): Unit = {
    useCount += 1
    if (useCount % 500 == 0) {
      clearCaches()
    }
  }

  def clearCaches(): Unit = {
    val lexer = new ApexLexer(
      new CaseInsensitiveInputStream(new ByteArrayInputStream(Array[Byte]())))
    val parser = new ApexParser(new CommonTokenStream(lexer))
    lexer.clearCache()
    parser.clearCache()
  }

  // Helper for JS Portability
  def getText(context: ParserRuleContext): String = {
    context.getText
  }

  // Helper for JS Portability
  def getText(context: TerminalNode): String = {
    context.getText
  }

  // Helper for JS Portability
  def toScala[T](collection: java.util.List[T]): Seq[T] = {
    collection.asScala.toSeq
  }

  // Helper for JS Portability
  def toScala[T](value: T): Option[T] = {
    Option(value)
  }
}
