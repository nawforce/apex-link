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
package com.nawforce.unit.cst

import java.io.ByteArrayInputStream
import java.net.URI
import java.nio.charset.StandardCharsets
import java.nio.file.Paths

import com.nawforce.cst.{CompilationUnit, ConstructContext}
import com.nawforce.metadata.ApexClass
import com.nawforce.parsers.{ApexLexer, ApexParser, CaseInsensitiveInputStream}
import com.nawforce.utils._
import org.antlr.v4.runtime.CommonTokenStream

object ClassParser {
  lazy val defaultUri: URI = Paths.get("dummy.cls").toUri

  def parseSingle(clsText: String, uri: URI = defaultUri, displayMessages: Boolean = false)
    : Option[ApexClass] = {
    try {
      val listener = new ThrowingErrorListener
      val stream = new ByteArrayInputStream(clsText.getBytes(StandardCharsets.UTF_8))
      val cis: CaseInsensitiveInputStream = new CaseInsensitiveInputStream(stream)
      val lexer: ApexLexer = new ApexLexer(cis)
      lexer.removeErrorListeners()
      lexer.addErrorListener(listener)

      val tokens: CommonTokenStream = new CommonTokenStream(lexer)
      tokens.fill()

      val parser: ApexParser = new ApexParser(tokens)
      parser.removeErrorListeners()
      parser.setTrace(false)
      parser.addErrorListener(listener)

      IssueLog.pushContext(uri)
      val cu = CompilationUnit.construct(parser.compilationUnit(), new ConstructContext())
      cu.verify()
      if (IssueLog.hasMessages && displayMessages)
        IssueLog.dumpMessages()
      Some(new ApexClass(LineLocation(uri, 0), "dummy", cu))
    } catch {
      case se: SyntaxException =>
        IssueLog.logMessage(LineLocation(uri, se.line), se.msg)
        None
    } finally {
      IssueLog.popContext()
    }
  }


}
