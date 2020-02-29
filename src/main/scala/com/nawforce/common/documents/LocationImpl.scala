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

import com.nawforce.common.api._
import com.nawforce.common.path.PathLike
import upickle.default.{macroRW, ReadWriter => RW}

abstract sealed class LocationImpl(val path: String, val line: Int) {
  def startPosition: (Int, Int) = (line, 0)
  def endPosition: (Int, Int) = (line+1, 0)
  def displayPosition: String
  def asJSON: String
  def toLocation: Location
}

object LocationImpl {
  def apply(path: String, location: Location): LocationImpl = {
    location match {
      case l: LineLocation => LineLocationImpl(path, l.line)
      case l: LineRangeLocation => LineRangeLocationImpl(path, l.start, l.end)
      case l: PointLocation => PointLocationImpl(path, PositionImpl(l.position))
      case l: RangeLocation => RangeLocationImpl(path, PositionImpl(l.start), PositionImpl(l.end))
    }
  }

  implicit val rw: RW[LocationImpl] = macroRW
}

case class LineLocationImpl(_path: String, _line: Int) extends LocationImpl(_path, _line) {
  override def displayPosition: String = s"line $line"
  override def asJSON: String = s""""start": {"line": $line}"""
  override def toLocation: LineLocation = LineLocation(line)
}

object LineLocationImpl {
  implicit val rw: RW[LineLocationImpl] = macroRW
}

case class LineRangeLocationImpl(_path: String, start: Int, end: Int) extends LocationImpl(_path, start) {
  override def endPosition: (Int, Int) = (end+1, 0)
  override def displayPosition: String = s"line $start to $end"
  override def asJSON: String = s""""start": {"line": $start}, "end": {"line": $end}"""
  override def toLocation: LineRangeLocation = LineRangeLocation(start, end)
}

object LineRangeLocationImpl {
  implicit val rw: RW[LineRangeLocationImpl] = macroRW
}

case class PositionImpl(line: Int, offset: Int) {
  def getPosition: (Int, Int) = (line, offset)
  def displayPosition: String = s"line $line at $offset"
  def asJSON: String = s"""{"line": $line, "offset": $offset}"""

  def adjust(lineOffset: Int, positionOffset: Int): PositionImpl = {
    if (lineOffset == 0 && positionOffset == 0) {
      return this
    }

    if (line == 1)
      PositionImpl(line + lineOffset, offset + positionOffset)
    else
      PositionImpl(line + lineOffset, offset)
  }

  def toPosition: Position = Position(line, offset)
}

object PositionImpl {
  implicit val rw: RW[PositionImpl] = macroRW

  def apply(position: Position): PositionImpl = {
    PositionImpl(position.line, position.offset)
  }
}

case class TextRange(start: PositionImpl, end: PositionImpl) {
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

object TextRange {
  implicit val rw: RW[TextRange] = macroRW

  val empty: TextRange = TextRange(PositionImpl(0, 0), PositionImpl(0, 0))

  def apply(line: Int): TextRange = {
    TextRange(
      PositionImpl(line, 0),
      PositionImpl(line+1, 0)
    )
  }
}

case class PointLocationImpl(_path: String, start: PositionImpl) extends LocationImpl(_path, start.line) {
  override def startPosition: (Int, Int) = start.getPosition
  override def endPosition: (Int, Int) = (startPosition._1+1, 0)
  override def displayPosition: String = start.displayPosition
  override def asJSON: String = start.asJSON
  override def toLocation: PointLocation = PointLocation(start.toPosition)
}

object PointLocationImpl {
  implicit val rw: RW[PointLocationImpl] = macroRW
}

case class RangeLocationImpl(_path: String, start: PositionImpl, end: PositionImpl) extends LocationImpl(_path, start.line) {
  override def startPosition: (Int, Int) = start.getPosition
  override def endPosition: (Int, Int) = end.getPosition
  override def displayPosition: String = TextRange(start, end).displayPosition
  override def asJSON: String = TextRange(start, end).asJSON
  override def toLocation: RangeLocation = RangeLocation(start.toPosition, end.toPosition)
}

object RangeLocationImpl {
  implicit val rw: RW[RangeLocationImpl] = macroRW

  def apply(path: PathLike, range: TextRange): RangeLocationImpl = {
    RangeLocationImpl(path.toString, range.start, range.end)
  }
}

