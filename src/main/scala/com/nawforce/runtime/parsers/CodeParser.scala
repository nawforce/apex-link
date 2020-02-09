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

import java.io.ByteArrayInputStream

import com.nawforce.common.parsers._
import com.nawforce.common.path.{PathFactory, PathLike}
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.misc.Interval

import scala.collection.JavaConverters._

case class ClippedText(path: PathLike, text: String, line: Int, column: Int)

object CodeParser {
  type ParserRuleContext = org.antlr.v4.runtime.ParserRuleContext
  type TerminalNode = org.antlr.v4.runtime.tree.TerminalNode

  def parseClass(path: PathLike, data: String): Either[SyntaxException, ApexParser.CompilationUnitContext] = {
    try {
      Right(createParser(Some(path), data).compilationUnit())
    } catch {
      case ex: SyntaxException => Left(ex)
    }
  }

  def parseTrigger(path: PathLike, data: String): Either[SyntaxException, ApexParser.TriggerUnitContext] = {
    try {
      Right(createParser(Some(path), data).triggerUnit())
    } catch {
      case ex: SyntaxException => Left(ex)
    }
  }

  def parseBlock(path: PathLike, data: String): Either[SyntaxException, ApexParser.BlockContext] = {
    try {
      Right(createParser(Some(path), data).block())
    } catch {
      case ex: SyntaxException => Left(ex)
    }
  }

  def parseTypeRef(data: String): Either[SyntaxException, ApexParser.TypeRefContext] = {
    try {
      Right(createParser(None, data).typeRef())
    } catch {
      case ex: SyntaxException => Left(ex)
    }
  }

  def getRange(context: ParserRuleContext): CSTRange = {
    CSTRange(
      context.getStart.getInputStream.asInstanceOf[CaseInsensitiveInputStream].path,
      context.getStart.getLine,
      context.getStart.getCharPositionInLine,
      context.getStop.getLine,
      context.getStop.getCharPositionInLine + context.getStop.getText.length)
  }

  def getText(context: ParserRuleContext): String = {
    context.getText
  }

  def getText(context: TerminalNode): String = {
    context.getText
  }

  def clipText(context: ParserRuleContext): ClippedText = {
    val is = context.start.getInputStream
    val text = is.getText(new Interval(context.start.getStartIndex, context.stop.getStopIndex))
    val path = context.start.getInputStream.asInstanceOf[CaseInsensitiveInputStream].path

    ClippedText(PathFactory(path), text, context.start.getLine-1, context.start.getCharPositionInLine)
  }

  def toScala[T](collection: java.util.List[T]): Seq[T] = {
    collection.asScala
  }

  def toScala[T](value: T): Option[T] = {
    Option(value)
  }

  def createParser(path: Option[PathLike], data: String): ApexParser = {
    val listener = new ThrowingErrorListener()
    val cis = new CaseInsensitiveInputStream(path.map(_.toString).getOrElse("<Unknown>"), new ByteArrayInputStream(data.getBytes))
    val lexer = new ApexLexer(cis)

    val tokens = new CommonTokenStream(lexer)
    tokens.fill()

    val parser = new ApexParser(tokens)
    parser.removeErrorListeners()
    parser.addErrorListener(listener)
    parser
  }
}
