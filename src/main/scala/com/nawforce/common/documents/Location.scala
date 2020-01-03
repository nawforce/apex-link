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
package com.nawforce.common.documents

import com.nawforce.common.path.PathLike

abstract class Location(val path: PathLike, val line: Int) {
  def startPosition: (Int, Int) = (line, 0)
  def endPosition: (Int, Int) = (line+1, 0)
  def displayPosition: String
  def asJSON: String
}

case class LineLocation(_path: PathLike, _line: Int) extends Location(_path, _line) {
  override def displayPosition: String = s"line $line"
  override def asJSON: String = s""""start": {"line": $line}"""
}

case class LineRangeLocation(_path: PathLike, start: Int, end: Int) extends Location(_path, start) {
  override def endPosition: (Int, Int) = (end+1, 0)
  override def displayPosition: String = s"line $start to $end"
  override def asJSON: String = s""""start": {"line": $start}, "end": {"line": $end}"""
}

case class Position(line: Int, offset: Int) {
  def getPosition: (Int, Int) = (line, offset)
  def displayPosition: String = s"line $line at $offset"
  def asJSON: String = s"""{"line": $line, "offset": $offset}"""

  def adjust(lineOffset: Int, positionOffset: Int): Position = {
    if (lineOffset == 0 && positionOffset == 0) {
      return this
    }

    if (line == 1)
      Position(line + lineOffset, offset + positionOffset)
    else
      Position(line + lineOffset, offset)
  }
}

case class TextRange(start: Position, end: Position) {
  def startPosition: (Int, Int) = start.getPosition
  def displayPosition: String = {
    if (start.line == end.line)
      s"line ${start.line} at ${start.offset}-${end.offset}"
    else
      s"line ${start.line} to ${end.line}"
  }

  def asJSON: String =
    s""""start": ${start.asJSON}, "end": ${end.asJSON}"""

  def adjust(lineOffset: Int, positionOffset: Int) : TextRange = {
    if (lineOffset == 0 && positionOffset == 0) {
      return this
    }

    TextRange(
      start.adjust(lineOffset, positionOffset),
      end.adjust(lineOffset, positionOffset)
    )
  }
}

case class PointLocation(_path: PathLike, start: Position) extends Location(_path, start.line) {
  override def startPosition: (Int, Int) = start.getPosition
  override def endPosition: (Int, Int) = (startPosition._1+1, 0)
  override def displayPosition: String = start.displayPosition
  override def asJSON: String = start.asJSON
}

case class RangeLocation(_path: PathLike, start: Position, end: Position) extends Location(_path, start.line) {
  override def startPosition: (Int, Int) = start.getPosition
  override def endPosition: (Int, Int) = end.getPosition
  override def displayPosition: String = TextRange(start, end).displayPosition
  override def asJSON: String = TextRange(start, end).asJSON
}

object TextRange {
  val empty: TextRange = TextRange(Position(0, 0), Position(0, 0))

  def apply(line: Int): TextRange = {
    TextRange(
      Position(line, 0),
      Position(line+1, 0)
    )
  }
}

object RangeLocation {
  def apply(path: PathLike, range: TextRange): RangeLocation = {
    RangeLocation(path, range.start, range.end)
  }
}
