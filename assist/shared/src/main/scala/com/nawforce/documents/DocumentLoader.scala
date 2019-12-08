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
package com.nawforce.documents

import com.nawforce.cache.Path
import com.nawforce.names.Name
import io.scalajs.nodejs.fs.Fs

import scala.collection.mutable

class DocumentLoadingException(msg: String, ex: Exception=null) extends Exception(msg, ex)

class DocumentLoader(paths: Seq[Path]) {
  private val documentByName = new mutable.HashMap[Name, Path]()
  private val documentsByExtension = new mutable.HashMap[Name, List[Path]]() withDefaultValue List()

  index()

  def getByExtension(name: Name): Seq[Path] = {
    documentsByExtension(name)
  }

  private def index(): Unit = {
    paths.reverse.foreach(indexRoot)
    createGhostSObjectFiles(Name("field"))
    createGhostSObjectFiles(Name("fieldSet"))
  }

  private def indexRoot(path: Path): Unit = {
    indexPath(path.toAbsolute)
  }

  private def indexPath(path: Path): Unit = {
    if (path.filename.toString.startsWith("."))
      return

    if (path.isDirectory) {
      path.directoryContents() match {
        case Left(err) => () // TODO
        case Right(parts) => parts.foreach(indexPath)
      }
    } else {
      insertDocument(DocumentType(path))
    }
  }

  private def insertDocument(documentType: Option[DocumentType]): Unit = {
    documentType match {
      case Some(docType: MetadataDocumentType) if !docType.ignorable =>
        if (docType.indexByName) {
          val duplicate = documentByName.get(docType.name)
          if (duplicate.nonEmpty) {
            val duplicate = documentByName.get(docType.name)
            // TODO
            // Org.logMessage(LineLocation(docType.path, 0), s"File has same name as ${duplicate.get}, ignoring")
          } else {
            documentByName.put(docType.name, docType.path)
          }
        }
        documentsByExtension.put(docType.extension, docType.path :: documentsByExtension(docType.extension))
      case _ => ()
    }
  }

  private def createGhostSObjectFiles(name: Name): Unit = {
    getByExtension(name).foreach(path => {
      val metaFile = path.parent.join(path.filename + ".object-meta.xml")
      if (!metaFile.exists) {
        if (!documentsByExtension.getOrElse(Name("object"), Seq()).contains(metaFile)) {
          documentsByExtension.put(Name("object"), metaFile :: documentsByExtension(Name("object")))
        }
      }
    })
  }
}
