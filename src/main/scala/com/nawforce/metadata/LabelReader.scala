/*
 [The "BSD licence"]
 Copyright (c) 2017 Kevin Jones
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
package com.nawforce.metadata

import java.nio.file.Files

import com.nawforce.utils.{LinkerException, LinkerLog, XMLLineLoader, XMLUtils}

import scala.xml.Elem

class LabelReader extends SymbolReader {

  override def loadSymbols(ctx: SymbolReaderContext): Unit = {
    try {
      val labelsFile = ctx.getBaseDir.resolve("labels").resolve("CustomLabels.labels")
      LinkerLog.pushContext(labelsFile.toString)
      if (Files.exists(labelsFile)) {
        LinkerLog.ifNotLogAndThrow(Files.isRegularFile(labelsFile), 0, "Labels file is not a regular file")
        LinkerLog.ifNotLogAndThrow(Files.isReadable(labelsFile), 0, "Labels file is not readable")

        val root = XMLLineLoader.loadFile(labelsFile.toString)
        XMLUtils.ifNotElemLogAndThrow(root, "CustomLabels")

        root.child.foreach {
          case elem: Elem =>
            XMLUtils.ifNotElemLogAndThrow(elem, "labels")
            Label.create(elem).foreach(l => ctx.addLabel(l))
          case _ =>
        }
      }
    } catch {
      case _: LinkerException => () // Ignore, just used to abort processing
    }
  }
}
