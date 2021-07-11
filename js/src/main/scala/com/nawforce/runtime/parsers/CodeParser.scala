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

import com.nawforce.pkgforce.diagnostics.{IssuesAnd, Location}
import com.nawforce.pkgforce.path.PathLike
import com.nawforce.runtime.parsers.CodeParser.ParserRuleContext
import com.nawforce.runtime.parsers.antlr.CommonTokenStream

import scala.scalajs.js

class CodeParser(val source: Source) {
  // We would like to extend this but it angers the JavaScript gods
  val cis: CaseInsensitiveInputStream = source.asInsensitiveStream

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
    val tokenStream = new CommonTokenStream(new ApexLexer(cis))
    tokenStream.fill()

    val listener = new CollectingErrorListener(source.path.toString)
    val parser = new ApexParser(tokenStream)
    parser.removeErrorListeners()
    parser.addErrorListener(listener)

    val result = parse(parser)
    IssuesAnd(listener.issues, result)
  }
}

object CodeParser {
  type ParserRuleContext = com.nawforce.runtime.parsers.antlr.ParserRuleContext
  type TerminalNode = com.nawforce.runtime.parsers.antlr.TerminalNode

  def apply(path: PathLike, code: SourceData): CodeParser = {
    new CodeParser(Source(path, code, 0, 0, None))
  }

  def clearCaches(): Unit = {
    // Not supported
  }

  // Helper for JS Portability
  def getText(context: ParserRuleContext): String = {
    context.childCount match {
      case 0 => ""
      case 1 => context.getChild(0).text
      case _ =>
        val builder = new StringBuilder
        for (i <- 0 until context.childCount) {
          builder.append(context.getChild(i).text)
        }
        builder.toString
    }
  }

  // Helper for JS Portability
  def getText(node: TerminalNode): String = {
    node.text
  }

  // Helper for JS Portability
  def toScala[T](collection: js.Array[T]): Seq[T] = {
    collection.toSeq
  }

  // Helper for JS Portability
  def toScala[T](value: js.UndefOr[T]): Option[T] = {
    value.toOption
  }
}
