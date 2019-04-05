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

import java.io.{File, FileInputStream, InputStream}
import java.nio.file.{Path, Paths}

import com.nawforce.utils.Name

import scala.collection.mutable

class DocumentLoadingException(msg: String, ex: Exception=null) extends Exception(msg, ex)

class DocumentLoader(paths: Seq[Path]) {
  private val cwd = Paths.get("").toAbsolutePath
  private val documentByName = new mutable.HashMap[Name, Path]()
  private val documentsByExtension = new mutable.HashMap[Name, List[Path]]() withDefaultValue List()

  index()

  private def index(): Unit = {
      paths.reverse.foreach(p => indexPath(cwd.resolve(p)))
  }

  private def indexPath(path: Path): Unit = {
    val directory = path.toFile
    if (directory.isDirectory) {
      directory.listFiles().foreach(file => {
        if (file.isDirectory)
          indexPath(file.toPath)
        else
          insertDocument(DocumentType(file))
      })
    } else {
      throw new DocumentLoadingException(s"Expecting directory at $directory")
    }
  }

  private def insertDocument(documentType: DocumentType): Unit = {
    documentType match {
      case docType: MetadataDocumentType =>
        documentsByExtension.put(docType.extension, docType.path :: documentsByExtension(docType.extension))
        documentByName.put(docType.name, docType.path)
      case _ => ()
    }
  }

  def getByName(name: Name): Option[(Path, InputStream)] = {
    documentByName.get(name).map(path => (path, new FileInputStream(path.toFile)))
  }

  def getByExtension(name: Name): Seq[Path] = {
    documentsByExtension(name)
  }
}

object DocumentLoader {
  var defaultDocumentLoader: DocumentLoader = _

  def getByName(name: Name): Option[(Path, InputStream)] = defaultDocumentLoader.getByName(name)
  def getByExtension(name: Name): Seq[Path] = defaultDocumentLoader.getByExtension(name)
}
