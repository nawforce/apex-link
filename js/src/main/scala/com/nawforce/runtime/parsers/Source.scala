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
import com.nawforce.runtime.SourceBlob
import com.nawforce.runtime.parsers.CodeParser.ParserRuleContext

trait Locatable {
  var locationPath: String
  var startLine: Int
  var startOffset: Int
  var endLine: Int
  var endOffset: Int

  def location: RangeLocationImpl = {
    RangeLocationImpl(
      locationPath,
      PositionImpl(startLine, startOffset),
      PositionImpl(endLine, endOffset)
    )
  }
}

/** Encapsulation of a chunk of Apex code, position tells you where it came from in path */
case class Source(path: PathLike, code: SourceData, lineOffset: Int, columnOffset: Int, outer: Option[Source]) {
  lazy val hash: Int = code.hash

  def extractSource(context: ParserRuleContext): Source = {
    val subdata = code.subdata(context.start.startIndex, context.stop.stopIndex+1)
    new Source(path, subdata, context.start.line-1, context.start.charPositionInLine, outer = Some(this))
  }

  def asStream: CaseInsensitiveInputStream = {
    code.asStream
  }

  def asUTF8: Array[Byte] = {
    code.asUTF8
  }

  /** Find a location for a rule, adapts based on source offsets to give absolute position in file */
  def getRangeLocation(context: ParserRuleContext): RangeLocationImpl = {
    RangeLocationImpl(
      path.toString,
      PositionImpl(context.start.line, context.start.charPositionInLine)
        .adjust(lineOffset, columnOffset),
      PositionImpl(context.stop.line, context.stop.charPositionInLine + context.stop.text.length)
        .adjust(lineOffset, columnOffset)
    )
  }

  def stampLocation(locatable: Locatable, context: ParserRuleContext): Unit = {
    locatable.locationPath = path.toString
    locatable.startLine = context.start.line + lineOffset
    locatable.startOffset =
      if (context.start.line == 1)
        context.start.charPositionInLine + columnOffset
      else
        context.start.charPositionInLine
    locatable.endLine = context.stop.line + lineOffset
    locatable.endOffset =
      if (context.stop.line == 1)
        context.stop.charPositionInLine + context.stop.text.length + columnOffset
      else
        context.stop.charPositionInLine + context.stop.text.length
  }
}

object Source {
  def apply(path: PathLike, source: SourceBlob): Source = {
    new Source(path, SourceData(source), lineOffset = 0, columnOffset = 0, None)
  }
}
