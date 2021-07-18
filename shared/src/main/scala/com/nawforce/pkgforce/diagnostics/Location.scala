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

package com.nawforce.pkgforce.diagnostics

import upickle.default.{macroRW, ReadWriter => RW}

/** Location for identifying a sub-part of a file */
@upickle.implicits.key("Location")
sealed case class Location(startLine: Int, startPosition: Int, endLine: Int, endPosition: Int) {
  def asJSON: String =
    s""""start": {"line": $startLine, "offset": $startPosition}, "end": {"line": $endLine, "offset": $endPosition}"""

  def displayPosition: String = {
    if (startLine == endLine) {
      if (startPosition == 0 && endPosition == 0)
        s"line $startLine"
      else if (startPosition == endPosition)
        s"line $startLine at $startPosition"
      else
        s"line $startLine at $startPosition-$endPosition"
    } else {
      if (startPosition == 0 && endPosition == 0)
        s"line $startLine to $endLine"
      else
        s"line $startLine at $startPosition-$endPosition"
    }
  }

}

object Location {
  implicit val rw: RW[Location] = macroRW

  val empty: Location = Location(1)

  def apply(line: Int) = new Location(line, 0, line, 0)
  def apply(line: Int, position: Int) = new Location(line, position, line, position)
  def apply(startLine: Int, startPosition: Int, endLine: Int, endPosition: Int): Location = {
    new Location(startLine, startPosition, endLine, endPosition)
  }
}

sealed case class LocationAnd[T](location: Location, value: T)

/** Location within a specific file. */
@upickle.implicits.key("PathLocation")
sealed case class PathLocation(path: String, location: Location) {
  override def toString: String = {
    s"$path: ${location.displayPosition}"
  }
}

object PathLocation {
  implicit val rw: RW[Location] = macroRW
}
