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
package com.nawforce.utils

import java.net.URI

import org.antlr.v4.runtime.ParserRuleContext

abstract class Location(val uri: URI, val line: Int) {
  def displayPosition: String
}

case class LineLocation(_uri: URI, _line: Int) extends Location(_uri, _line) {
  override def displayPosition: String = {
    s"line $line"
  }
}

case class LineRangeLocation(_uri: URI, start: Int, end: Int) extends Location(_uri, start) {
  override def displayPosition: String = {
    s"line $start to $end"
  }
}

case class Position(line: Int, offset: Int) {
  def displayPosition: String = {
    s"line $line at $offset"
  }
}

case class TextRange(start: Position, end: Position) {
  def displayPosition: String = {
    if (start.line == end.line)
      s"line ${start.line} at ${start.offset}-${end.offset}"
    else
      s"line ${start.line} to ${end.line}"
  }
}

case class PointLocation(_uri: URI, start: Position) extends Location(_uri, start.line) {
  override def displayPosition: String = start.displayPosition
}

case class RangeLocation(_uri: URI, start: Position, end: Position) extends Location(_uri, start.line) {
  override def displayPosition: String = TextRange(start, end).displayPosition
}

object TextRange {
  val empty = TextRange(Position(0, 0), Position(0, 0))

  def apply(context: ParserRuleContext): TextRange = {
    TextRange(
      Position(context.getStart.getLine, context.getStart.getCharPositionInLine),
      Position(context.getStop.getLine, context.getStop.getCharPositionInLine + context.getStop.getText.length),
    )
  }
}

object RangeLocation {
  def apply(uri: URI, range: TextRange): RangeLocation = {
    RangeLocation(uri, range.start, range.end)
  }
}


