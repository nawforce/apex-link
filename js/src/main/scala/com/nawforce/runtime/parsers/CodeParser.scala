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

import com.nawforce.common.documents.{PositionImpl, RangeLocationImpl}
import com.nawforce.common.path.PathLike
import com.nawforce.runtime.parsers.CodeParser.ParserRuleContext
import com.nawforce.runtime.parsers.antlr.CommonTokenStream
import io.scalajs.nodejs.console

import scala.scalajs.js
import scala.scalajs.js.JavaScriptException
import scala.util.hashing.MurmurHash3

class CodeParser (val source: Source) {
  // We would like to extend this but it angers the JavaScript gods
  val cis = source.asStream

  def parseClass(): Either[SyntaxException, ApexParser.CompilationUnitContext] = {
    try {
      Right(getParser.compilationUnit())
    } catch {
      case ex: JavaScriptException => Left(ex.exception.asInstanceOf[SyntaxException])
    }
  }

  def parseTrigger(): Either[SyntaxException, ApexParser.TriggerUnitContext] = {
    try {
      Right(getParser.triggerUnit())
    } catch {
      case ex: JavaScriptException => Left(ex.exception.asInstanceOf[SyntaxException])
    }
  }

  def parseBlock(): Either[SyntaxException, ApexParser.BlockContext] = {
    try {
      Right(getParser.block())
    } catch {
      case ex: JavaScriptException => Left(ex.exception.asInstanceOf[SyntaxException])
    }
  }

  // Test use only
  def parseLiteral(): ApexParser.LiteralContext = {
    getParser.literal()
  }

  /** Find a location for a rule, adapts based on source offsets to give absolute position in file */
  def getRangeLocation(context: ParserRuleContext): RangeLocationImpl = {
    source.getRangeLocation(context)
  }

  /** Extract the source used for a parser rule */
  def extractSource(context: ParserRuleContext): Source = {
    source.extractSource(context)
  }

  private def getParser: ApexParser = {
    val tokenStream = new CommonTokenStream(new ApexLexer(cis))
    tokenStream.fill()

    val parser = new ApexParser(tokenStream)
    parser.removeErrorListeners()
    parser.addErrorListener(new ThrowingErrorListener())
    parser
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
    if (context.childCount == 0) return ""

    val builder = new StringBuilder
    for (i <- 0 until context.childCount) {
      builder.append(context.getChild(i).text)
    }
    builder.toString
  }

  // Helper for JS Portability
  def getText(node: TerminalNode): String = {
    node.text
  }

  // Helper for JS Portability
  def toScala[T](collection: js.Array[T]): Seq[T] = {
    collection
  }

  // Helper for JS Portability
  def toScala[T](value: js.UndefOr[T]): Option[T] = {
    value.toOption
  }
}
