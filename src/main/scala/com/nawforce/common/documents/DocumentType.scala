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

import com.nawforce.common.names.Name
import com.nawforce.common.path.{EMPTY_FILE, PathLike}

trait DocumentType {
  val name: Name
}

class PathDocument(val path: PathLike, val name: Name) extends DocumentType {
}

abstract class MetadataDocumentType(_path: PathLike, _name: Name)
  extends PathDocument(_path, _name) {
  val extension: Name
  val indexByName: Boolean = false
  val ignorable: Boolean = false
}

final case class LabelsDocument(_path: PathLike, _name: Name) extends MetadataDocumentType(_path, _name) {
  lazy val extension: Name = Name("labels")
}

final case class ApexDocument(_path: PathLike, _name: Name)
  extends MetadataDocumentType(_path, _name) {
  lazy val extension: Name = Name("cls")
  override val ignorable: Boolean = {
    path.nature == EMPTY_FILE || path.read().toOption.contains("(hidden)")
  }
  override val indexByName: Boolean = true
}

final case class ComponentDocument(_path: PathLike, _name: Name)
  extends MetadataDocumentType(_path, _name) {
  lazy val extension: Name = Name("component")
}

abstract class SObjectLike(_path: PathLike, _name: Name) extends MetadataDocumentType(_path, _name)

final case class SObjectDocument(_path: PathLike, _name: Name)
  extends SObjectLike(_path, _name) {
  lazy val extension: Name = Name("object")
  override val ignorable: Boolean = path.nature == EMPTY_FILE
}

final case class SObjectFieldDocument(_path: PathLike, _name: Name)
  extends MetadataDocumentType(_path, _name) {
  lazy val extension: Name = Name("field")
}

final case class SObjectFieldSetDocument(_path: PathLike, _name: Name)
  extends MetadataDocumentType(_path, _name) {
  lazy val extension: Name = Name("fieldSet")
}

final case class CustomMetadataDocument(_path: PathLike, _name: Name)
  extends SObjectLike(_path, _name) {
  lazy val extension: Name = Name("object")
}

final case class PlatformEventDocument(_path: PathLike, _name: Name)
  extends SObjectLike(_path, _name) {
  lazy val extension: Name = Name("object")
}

final case class PageDocument(_path: PathLike, _name: Name)
  extends MetadataDocumentType(_path, _name) {
  lazy val extension: Name = Name("page")
}

object DocumentType {
  def apply(path: PathLike): Option[DocumentType] = {
    splitFilename(path) match {
      case Seq(name, Name("cls")) =>
        Some(ApexDocument(path, name))

      case Seq(name, Name("component")) =>
        Some(ComponentDocument(path, name))

      case Seq(name, Name("object")) if name.value.endsWith("__mdt") =>
        Some(CustomMetadataDocument(path, name))
      case Seq(name, Name("object-meta"), Name("xml")) if name.value.endsWith("__mdt") =>
        Some(CustomMetadataDocument(path, name))

      case Seq(name, Name("object")) if name.value.endsWith("__e") =>
        Some(PlatformEventDocument(path, name))
      case Seq(name, Name("object-meta"), Name("xml")) if name.value.endsWith("__e") =>
        Some(PlatformEventDocument(path, name))

      case Seq(name, Name("object")) =>
        Some(SObjectDocument(path, name))
      case Seq(name, Name("object-meta"), Name("xml")) =>
        Some(SObjectDocument(path, name))

      case Seq(name, Name("field-meta"), Name("xml")) =>
        Some(SObjectFieldDocument(path, name))

      case Seq(name, Name("fieldset-meta"), Name("xml")) =>
        Some(SObjectFieldSetDocument(path, name))

      case Seq(name, Name("labels")) =>
        Some(LabelsDocument(path, name))
      case Seq(name, Name("labels-meta"), Name("xml")) =>
        Some(LabelsDocument(path, name))

      case Seq(name, Name("page")) =>
        Some(PageDocument(path, name))
      case _ => None
    }
  }


  private def splitFilename(path: PathLike): Seq[Name] = {
    var parts = path.basename.toString.split('.')
    if (parts.length > 3) {
      parts = List(parts.slice(0, parts.length-2).mkString("."),
        parts(parts.length-2).toLowerCase(), parts(parts.length-1).toLowerCase).toArray
    } else if (parts.length == 3) {
      parts = List(parts(0), parts(1).toLowerCase, parts(2).toLowerCase).toArray
    } else if (parts.length == 2) {
      parts = List(parts(0), parts(1).toLowerCase).toArray
    }
    parts.map(Name(_))
  }
}

