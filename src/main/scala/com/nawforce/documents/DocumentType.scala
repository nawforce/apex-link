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

import java.io.File
import java.nio.file.Path

import com.nawforce.utils.Name

trait DocumentType {
  val name: Name
}

class PathDocument(val path: Path) extends DocumentType {
  lazy val name = Name(path.getFileName.toString)
}

abstract class MetadataDocumentType(path: Path) extends PathDocument(path) {
  val extension: Name
}

case class ApexDocument(_path: Path) extends MetadataDocumentType(_path) {
  lazy val extension: Name = Name("cls")
}

object DocumentType {
  def apply(path: Path): DocumentType = {
    extensionOf(path) match {
      case Some("cls") => ApexDocument(path)
      case _ => new PathDocument(path)
    }
  }

  def apply(file: File): DocumentType = apply(file.toPath)

  private def extensionOf(path: Path): Option[String] = {
    val pathToFile = path.toString
    val splitAt = pathToFile.lastIndexOf('.')
    if (splitAt == -1)
      None
    else
      Some(pathToFile.substring(splitAt+1).toLowerCase())
  }
}

