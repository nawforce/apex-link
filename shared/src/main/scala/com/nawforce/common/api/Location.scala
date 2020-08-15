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

package com.nawforce.common.api

import upickle.default.{macroRW, ReadWriter => RW}

/** Location interface for identify a sub-part of a file */
sealed trait Location

/** Single line location */
@upickle.implicits.key("LineLocation")
case class LineLocation(line: Int) extends Location

/** Range of lines location */
@upickle.implicits.key("LineRangeLocation")
case class LineRangeLocation(start: Int, end: Int) extends Location

/** Position in a file */
@upickle.implicits.key("Position")
case class Position(line: Int, offset: Int)

/** Single position location */
@upickle.implicits.key("PointLocation")
case class PointLocation(position: Position) extends Location

/** Range between to positions */
@upickle.implicits.key("RangeLocation")
case class RangeLocation(start: Position, end: Position) extends Location

/** Combined path and location within the path */
@upickle.implicits.key("PathLocation")
case class PathLocation(path: String, location: Location)

object Location {
  implicit val rw: RW[Location] = RW.merge(LineLocation.rw, LineRangeLocation.rw, PointLocation.rw, RangeLocation.rw)
}

object LineLocation {
  implicit val rw: RW[LineLocation] = macroRW
}

object LineRangeLocation {
  implicit val rw: RW[LineRangeLocation] = macroRW
}

object PointLocation {
  implicit val rw: RW[PointLocation] = macroRW
}

object RangeLocation {
  implicit val rw: RW[RangeLocation] = macroRW
}

object Position {
  implicit val rw: RW[Position] = macroRW
}

object PathLocation {
  implicit val rw: RW[PathLocation] = macroRW
}
