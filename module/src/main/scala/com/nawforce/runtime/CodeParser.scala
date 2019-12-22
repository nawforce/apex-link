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
package com.nawforce.runtime

import com.nawforce.parsers._
import com.nawforce.parsers.antlr.CommonTokenStream
import com.nawforce.path.PathLike

import scala.scalajs.js
import scala.scalajs.js.JavaScriptException

object CodeParser {
  type ParserRuleContext = com.nawforce.parsers.antlr.ParserRuleContext

  def parseCompilationUnit(path: PathLike, data: String): Either[SyntaxException, CompilationUnitContext] = {
    try {
      Right(createParser(path, data).compilationUnit())
    } catch {
      case ex: JavaScriptException => Left(ex.exception.asInstanceOf[SyntaxException])
    }
  }

  def parseBlock(path: PathLike, data: Array[Byte]): Either[SyntaxException, ApexParser.BlockContext] = {
    try {
      Right(createParser(path, new String(data)).block())
    } catch {
      case ex: JavaScriptException => Left(ex.exception.asInstanceOf[SyntaxException])
    }
  }

  def getRange(context: ParserRuleContext): CSTRange = {
    CSTRange(
      context.start.inputStream.path,
      context.start.line,
      context.start.charPositionInLine,
      context.stop.line,
      context.stop.charPositionInLine + context.stop.text.length)
  }

  def getText(context: ParserRuleContext): String = {
    if (context.childCount == 0) return ""

    val builder = new StringBuilder
    for (i <- 0 until context.childCount) {
      builder.append(context.getChild(i).text)
    }
    builder.toString
  }

  def toScala[T](collection: js.Array[T]): Seq[T] = {
    collection
  }

  def toScala[T](value: js.UndefOr[T]): Option[T] = {
    value.toOption
  }

  private def createParser(path: PathLike, data: String): ApexParser = {
    val listener = new ThrowingErrorListener()
    val cis = new CaseInsensitiveInputStream(path.absolute.toString, data)
    val lexer = new ApexLexer(cis)

    val tokens = new CommonTokenStream(lexer)
    tokens.fill()

    val parser: ApexParser = new ApexParser(tokens)
    parser.removeErrorListeners()
    parser.addErrorListener(listener)
    parser
  }
}
