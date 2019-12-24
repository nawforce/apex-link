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
package com.nawforce.runtime.diagnostics

import com.nawforce.common.documents.{Position, RangeLocation, TextRange}
import com.nawforce.runtime.parsers.CaseInsensitiveInputStream
import com.nawforce.runtime.parsers.CodeParser.ParserRuleContext
import com.nawforce.runtime.path.Path

object LocationHelper {
  def asTextRange(context: ParserRuleContext): TextRange = {
    TextRange(
      Position(context.getStart.getLine, context.getStart.getCharPositionInLine),
      Position(context.getStop.getLine, context.getStop.getCharPositionInLine + context.getStop.getText.length),
    )
  }

  def asRangeLocation(context: ParserRuleContext, lineOffset: Int=0, positionOffset: Int=0): RangeLocation = {
    RangeLocation(
      Path(context.start.getInputStream.asInstanceOf[CaseInsensitiveInputStream].path),
      Position(context.start.getLine, context.start.getCharPositionInLine)
        .adjust(lineOffset, positionOffset),
      Position(context.stop.getLine, context.stop.getCharPositionInLine + context.stop.getText.length)
        .adjust(lineOffset, positionOffset)
    )
  }
}
