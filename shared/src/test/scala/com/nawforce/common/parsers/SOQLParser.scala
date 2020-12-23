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

package com.nawforce.common.parsers

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

import com.nawforce.runtime.parsers.{
  ApexLexer,
  ApexParser,
  CaseInsensitiveInputStream,
  CollectingErrorListener
}
import org.antlr.v4.runtime.CommonTokenStream

case class ParserIssue(line: Int, offset: Int, message: String)

object SOQLParser {

  def parse(soql: String): Either[Seq[ParserIssue], ApexParser.QueryContext] = {
    val bytes = soql.getBytes(StandardCharsets.UTF_8)
    val cis = new CaseInsensitiveInputStream(new ByteArrayInputStream(bytes, 0, bytes.length))

    val tokenStream = new CommonTokenStream(new ApexLexer(cis))
    tokenStream.fill()

    val listener = new CollectingErrorListener("")
    val parser = new ApexParser(tokenStream)
    parser.removeErrorListeners()
    parser.addErrorListener(listener)

    val context = parser.query()
    val issues = listener.issues
    if (issues.nonEmpty)
      Left(
        issues
          .map(
            i =>
              ParserIssue(i.diagnostic.location.startLine,
                          i.diagnostic.location.startPosition,
                          i.diagnostic.message))
          .toSeq)
    else
      Right(context)
  }
}
