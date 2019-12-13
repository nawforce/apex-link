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

import com.nawforce.api.Org
import com.nawforce.cache.{DIRECTORY, DOES_NOT_EXIST, FILE, Path}
import com.nawforce.names.Name

import scala.collection.mutable
import scala.scalajs.js

class DocumentIndexException(msg: String) extends js.JavaScriptException(msg)

class DocumentIndex(paths: Seq[Path]) {
  // Partitioned by normalised extension
  private val documentNames = new mutable.HashMap[Name, mutable.Set[Name]]() withDefaultValue mutable.Set()
  private val documents = new mutable.HashMap[Name, List[MetadataDocumentType]]() withDefaultValue List()

  index()

  val size: Int = documents.values.map(_.size).sum

  def getByExtension(name: Name): Seq[MetadataDocumentType] = {
    documents(name)
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

    if (path.nature == DIRECTORY) {
      path.directoryList() match {
        case Left(err) => throw new DocumentIndexException(err)
        case Right(parts) => parts.foreach(part => indexPath(path.join(part)))
      }
    } else if (path.nature.isInstanceOf[FILE]) {
      insertDocument(DocumentType(path))
    }
  }

  private def insertDocument(documentType: Option[DocumentType]): Unit = {
    documentType match {
      case Some(docType: MetadataDocumentType) if !docType.ignorable =>
        if (docType.indexByName) {
          if (documentNames(docType.extension).contains(docType.name)) {
            val duplicate = documents(docType.extension).find(_.name == docType.name)
            Org.logMessage(LineLocation(docType.path, 0), s"File has same name as ${duplicate.get}, ignoring")
          } else {
            documentNames(docType.extension).add(docType.name)
            documents.put(docType.extension, docType :: documents(docType.extension))
          }
        } else {
          documents.put(docType.extension, docType :: documents(docType.extension))
        }
      case _ => ()
    }
  }

  private def createGhostSObjectFiles(name: Name): Unit = {
    getByExtension(name).foreach(docType => {
      val objectDir = docType.path.parent.parent
      val metaFile = objectDir.join(objectDir.filename + ".object-meta.xml")
      if (metaFile.nature == DOES_NOT_EXIST) {
        if (!documents(Name("object")).map(_.path).contains(metaFile)) {
          documents.put(Name("object"),
            SObjectDocument(metaFile, Name(objectDir.filename)) :: documents(Name("object")))
        }
      }
    })
  }
}
