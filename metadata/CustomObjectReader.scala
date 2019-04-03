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

import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{Files, Path}

import com.nawforce.documents.{LineLocation, TraversePath}
import com.nawforce.utils._

class CustomObjectReader extends SymbolReader {

  override def loadSymbols(ctx: SymbolReaderContext): Unit = {
    try {
      val objectsDir = ctx.getBaseDir.resolve("objects")
      if (Files.exists(objectsDir)) {
        IssueLog.ifNotLogAndThrow(Files.isDirectory(objectsDir), 0, "objects is present but not a directory")

        val traverse = new TraversePath(objectsDir)
        traverse foreach {
          case (file: Path, attr: BasicFileAttributes) =>
            if (attr.isRegularFile && file.toString.endsWith(".object")) {
              loadObject(ctx, file.getFileName.toString.replaceFirst(".object$", ""), file)
            } else if (attr.isRegularFile) {
              if (!isIgnoreable(file))
                IssueLog.logMessage(LineLocation(file.toUri, 0), "Unexpected file in objects directory")
            } else {
              IssueLog.logMessage(LineLocation(file.toUri, 0), "Only expected to find files in objects directory")
            }
        }
      }
    }
    catch {
      case _: LinkerException => () // Ignore, just used to abort processing
    }
  }

  def loadObject(ctx: SymbolReaderContext, fullName: String, objectFile: Path): Unit = {
    IssueLog.pushContext(objectFile.toUri)
    try {
      val root = XMLLineLoader.loadFile(objectFile.toString)
      XMLUtils.ifNotElemLogAndThrow(root, "CustomObject")

      CustomObject.create(fullName, root).foreach(o => ctx.addCustomObject(o))
    } finally {
      IssueLog.popContext()
    }
  }
}
