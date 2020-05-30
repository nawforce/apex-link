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

import com.nawforce.common.documents.{PositionImpl, RangeLocationImpl}
import com.nawforce.common.path.PathLike
import com.nawforce.runtime.parsers.CodeParser.ParserRuleContext
import com.nawforce.runtime.{SourceData, _}

import scala.util.hashing.MurmurHash3

/** A line & column position in a source block */
case class SourcePosition(lineOffset: Int=0, columnOffset: Int=0)

/** Encapsulation of a chunk of Apex code, position tells you where it came from in path */
case class Source(path: PathLike, code: SourceData, position: SourcePosition, outer: Option[Source]) {
  lazy val hash: Int = MurmurHash3.arrayHash(code)

  def slice(start: Int, end: Int): SourceData = {
    // Done via string in case there are non-ASCII chars
    SourceData(code.asString.substring(start, end))
  }

  def asStream: CaseInsensitiveInputStream = {
    new CaseInsensitiveInputStream(new ByteArrayInputStream(code))
  }

  def asUTF8: Array[Byte] = {
    code
  }

  /** Find a location for a rule, adapts based on source offsets to give absolute position in file */
  def getRangeLocation(context: ParserRuleContext): RangeLocationImpl = {
    RangeLocationImpl(
      path.toString,
      PositionImpl(context.start.getLine, context.start.getCharPositionInLine)
        .adjust(position.lineOffset, position.columnOffset),
      PositionImpl(context.stop.getLine, context.stop.getCharPositionInLine + context.stop.getText.length)
        .adjust(position.lineOffset, position.columnOffset)
    )
  }
}
