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
package com.nawforce

import java.nio.file.Paths

import com.nawforce.documents.{ApexDocument, DocumentLoader, DocumentType}
import com.nawforce.types.ApexTypeDeclaration
import com.nawforce.utils.{IssueLog, Name}

object ApexLink {
  def main(args: Array[String]): Unit = {
    val paths = args.flatMap(arg => {
      val path = Paths.get(arg)
      if (path.toFile.isDirectory) Some(path) else None
    })

    if (paths.length != args.length) {
      println(s"Usage: ApexLink <dir1> <dir2> ...")
      return
    }

    DocumentLoader.defaultDocumentLoader = new DocumentLoader(
      if (paths.isEmpty) Seq(Paths.get("").toAbsolutePath) else paths)

    DocumentLoader.getByExtension(Name("cls")).par.foreach(path => {
      DocumentType(path) match {
        case docType: ApexDocument => ApexTypeDeclaration.create(docType.name)
        case _ => println(s"Unexpected document type at: $path")
      }
    })

    IssueLog.dumpMessages()
  }
}
