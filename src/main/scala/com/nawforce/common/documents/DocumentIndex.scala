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
package com.nawforce.common.documents

import com.nawforce.common.api.{Org, ServerOps}
import com.nawforce.common.names.Name
import com.nawforce.common.path.{DIRECTORY, DOES_NOT_EXIST, FILE, PathLike}

import scala.collection.mutable

class DocumentIndexException(msg: String) extends Throwable

class DocumentIndex(paths: Seq[PathLike], ignorePath: Option[PathLike] = None) {
  // Partitioned by normalised extension
  private val documentNames = new mutable.HashMap[Name, mutable.Set[Name]]() withDefaultValue mutable.Set()
  private val documents = new mutable.HashMap[Name, List[MetadataDocumentType]]() withDefaultValue List()

  index()

  val size: Int = documents.values.map(_.size).sum

  def getByExtension(name: Name): Seq[MetadataDocumentType] = {
    documents(name)
  }

  private def index(): Unit = {
    val forceIgnore = createForceIgnore()
    paths.reverse.foreach(p => indexPath(p, forceIgnore))
    createGhostSObjectFiles(Name("field"), forceIgnore)
    createGhostSObjectFiles(Name("fieldSet"), forceIgnore)
  }

  private def createForceIgnore(): Option[ForceIgnore] = {
    if (ignorePath.nonEmpty && ignorePath.get.nature.isInstanceOf[FILE]) {
      ForceIgnore(ignorePath.get) match {
        case Left(err) =>
          Org.logMessage(LineLocation(ignorePath.get, 0), s"Could not read .forceignore, error: $err")
          None
        case Right(forceIgnore) =>
          Some(forceIgnore)
      }
    } else {
      None
    }
  }

  private def indexPath(path: PathLike, forceIgnore: Option[ForceIgnore]): Unit = {
    if (path.basename.toString.startsWith("."))
      return

    if (path.nature == DIRECTORY) {
      if (forceIgnore.forall(_.includeDirectory(path))) {
        path.directoryList() match {
          case Left(err) => throw new DocumentIndexException(err)
          case Right(parts) => parts.foreach(part => indexPath(path.join(part), forceIgnore))
        }
      } else {
        ServerOps.debug(ServerOps.Trace, s"Ignoring directory $path")
      }
    } else if (path.nature.isInstanceOf[FILE]) {
      if (forceIgnore.forall(_.includeFile(path))) {
        insertDocument(DocumentType(path))
      } else {
        ServerOps.debug(ServerOps.Trace, s"Ignoring file $path")
      }
    }
  }

  private def insertDocument(documentType: Option[DocumentType]): Unit = {
    documentType match {
      case Some(docType: MetadataDocumentType) if !docType.ignorable =>
        if (docType.indexByName) {
          if (documentNames(docType.extension).contains(docType.name)) {
            val duplicate = documents(docType.extension).find(_.name == docType.name)
            // TODO: Re-install this
            //Org.logMessage(LineLocation(docType.path, 0), s"File has same name as ${duplicate.get}, ignoring")
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

  private def createGhostSObjectFiles(name: Name, forceIgnore: Option[ForceIgnore]): Unit = {
    getByExtension(name).foreach(docType => {
      val objectDir = docType.path.parent.parent
      val metaFile = objectDir.join(objectDir.basename + ".object-meta.xml")
      if (metaFile.nature == DOES_NOT_EXIST) {
        if (forceIgnore.forall(_.includeDirectory(metaFile.parent))) {
          if (!documents(Name("object")).map(_.path).contains(metaFile)) {
            documents.put(Name("object"),
              SObjectDocument(metaFile, Name(objectDir.basename)) :: documents(Name("object")))
          }
        }
      }
    })
  }
}
