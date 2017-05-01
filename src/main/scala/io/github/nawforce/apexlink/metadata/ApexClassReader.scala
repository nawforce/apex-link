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
package io.github.nawforce.apexlink.metadata

import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{Files, Path}

import io.github.nawforce.apexlink.utils._

class ApexClassReader extends SymbolReader {

  override def loadSymbols(ctx: SymbolReaderContext): Unit = {
    try {
      val classesDir = ctx.getBaseDir.resolve("classes")
      if (Files.exists(classesDir)) {
        LinkerLog.ifNotLogAndThrow(Files.isDirectory(classesDir), 0, "classes is present but not a directory")

        val traverse = new TraversePath(classesDir)
        traverse foreach {
          case (file: Path, attr: BasicFileAttributes) =>
            if (attr.isRegularFile && file.toString.endsWith(".cls")) {
              loadApexClass(ctx, file.getFileName.toString.replaceFirst(".cls$", ""), file).foreach(o => ctx.addApexClass(o))
            } else if (attr.isRegularFile && file.toString.endsWith(".cls-meta.xml")) {
              // Ignore
            } else if (attr.isRegularFile) {
              if (!isIgnoreable(file))
                LinkerLog.logMessage(file.toString, 0, "Unexpected file in classes directory")
            } else {
              LinkerLog.logMessage(file.toString, 0, "Only expected to find files in classes directory")
            }
        }
      }

    } catch {
      case _: LinkerException => () // Ignore, just used to abort processing
    }
  }

  def loadSymbolsFrom(ctx: SymbolReaderContext, path: Path): Unit = {
    try {
      LinkerLog.ifNotLogAndThrow(Files.isDirectory(path), 0, "Directory is not present")

      val traverse = new TraversePath(path)
      traverse foreach {
        case (file: Path, attr: BasicFileAttributes) =>
          if (attr.isRegularFile && file.toString.endsWith(".cls")) {
            loadApexClass(null, file.getFileName.toString.replaceFirst(".cls$", ""), file)
          }
      }
    }
    catch {
      case _: LinkerException => () // Ignore, just used to abort processing
    }
  }

  def loadApexClass(ctx: SymbolReaderContext, fullName: String, path: Path) : Option[ApexClass] = {
    if (ctx.isVerbose) println("Loading " + path)
    LinkerLog.pushContext(path.toString)
    try {
      ApexClass.create(fullName, path.toString)
    } finally {
      LinkerLog.popContext()
    }
  }
}
