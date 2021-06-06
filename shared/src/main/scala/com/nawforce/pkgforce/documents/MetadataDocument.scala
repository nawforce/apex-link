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
package com.nawforce.pkgforce.documents

import com.nawforce.pkgforce.diagnostics._
import com.nawforce.pkgforce.names.{TypeName, _}
import com.nawforce.pkgforce.path.PathLike
import com.nawforce.runtime.parsers.SourceData

/** The type of some metadata, such as Trigger metadata. Partial type metadata signals that multiple documents
  * may contribute to the same type. */
sealed abstract class MetadataNature(val partialType: Boolean = false)

case object LabelNature extends MetadataNature(partialType = true)
case object ApexNature extends MetadataNature
case object ExtendedApexNature extends MetadataNature
case object TriggerNature extends MetadataNature
case object ComponentNature extends MetadataNature
case object PageNature extends MetadataNature
case object FlowNature extends MetadataNature
case object SObjectNature extends MetadataNature
case object FieldNature extends MetadataNature
case object FieldSetNature extends MetadataNature
case object SharingReasonNature extends MetadataNature

/** A piece of Metadata described in a file */
abstract class MetadataDocument(val path: PathLike, val name: Name) {
  /* MDAPI extension for this metadata, may not match actual extension for SFDX */
  val nature: MetadataNature

  /** Set true to avoid indexing bad metadata such as empty files */
  val ignorable: Boolean = false

  /** Generate a typename for this metadata, if this is not unique you may need to set duplicatesAllowed. */
  def typeName(namespace: Option[Name]): TypeName

  /** Obtain source data for this document */
  def source: IssuesAnd[Option[SourceData]] = {
    path.readSourceData() match {
      case Left(err) =>
        IssuesAnd(List(Issue(path.toString, Diagnostic(ERROR_CATEGORY, Location.empty, err))), None)
      case Right(data) => IssuesAnd(Some(data))
    }
  }
}

final case class LabelsDocument(_path: PathLike, _name: Name)
    extends MetadataDocument(_path, _name) {
  override val nature: MetadataNature = LabelNature
  override def typeName(namespace: Option[Name]): TypeName = TypeName.Label
}

abstract class ClassDocument(_path: PathLike, _name: Name) extends MetadataDocument(_path, _name)

final case class ApexClassDocument(_path: PathLike, _name: Name)
    extends ClassDocument(_path, _name) {
  override val nature: MetadataNature = ApexNature
  override def typeName(namespace: Option[Name]): TypeName = {
    TypeName(name, Seq(), namespace.map(TypeName(_)))
  }
}

object ApexClassDocument {
  def apply(path: PathLike): ApexClassDocument = {
    assert(path.basename.toLowerCase.endsWith(".cls"))
    new ApexClassDocument(path, Name(path.basename.replaceFirst("(?i)\\.cls$", "")))
  }
}

final case class ApexTriggerDocument(_path: PathLike, _name: Name)
    extends MetadataDocument(_path, _name) {
  override val nature: MetadataNature = TriggerNature
  override def typeName(namespace: Option[Name]): TypeName = {
    val qname: String = namespace
      .map(ns => s"__sfdc_trigger/${ns.value}/${name.value}")
      .getOrElse(s"__sfdc_trigger/${name.value}")
    TypeName(Name(qname))
  }
}

object ApexTriggerDocument {
  def apply(path: PathLike): ApexTriggerDocument = {
    assert(path.basename.toLowerCase.endsWith(".trigger"))
    new ApexTriggerDocument(path, Name(path.basename.replaceFirst("(?i)\\.trigger$", "")))
  }
}

final case class ExtendedApexDocument(_path: PathLike, _name: Name)
    extends ClassDocument(_path, _name) {
  override val nature: MetadataNature = ExtendedApexNature
  override def typeName(namespace: Option[Name]): TypeName = {
    TypeName(name, Seq(), namespace.map(TypeName(_)))
  }
}

object ExtendedApexDocument {
  def apply(path: PathLike): ExtendedApexDocument = {
    assert(path.basename.toLowerCase.endsWith(".xcls"))
    new ExtendedApexDocument(path, Name(path.basename.replaceFirst("(?i)\\.xcls$", "")))
  }
}

final case class ComponentDocument(_path: PathLike, _name: Name)
    extends MetadataDocument(_path, _name) {
  override val nature: MetadataNature = ComponentNature
  override def typeName(namespace: Option[Name]): TypeName = {
    namespace
      .map(ns => TypeName(name, Nil, Some(TypeName(ns, Nil, Some(TypeName.Component)))))
      .getOrElse(TypeName(name, Nil, Some(TypeName.Component)))
  }
}

abstract class SObjectLike(_path: PathLike, _name: Name) extends MetadataDocument(_path, _name) {
  override val nature: MetadataNature = SObjectNature
}

final case class SObjectDocument(_path: PathLike, _name: Name) extends SObjectLike(_path, _name) {

  private val isCustom = name.value.endsWith("__c")
  override val ignorable: Boolean = path.size == 0
  override def typeName(namespace: Option[Name]): TypeName = {
    val prefix =
      if (isCustom)
        namespace.map(ns => s"${ns}__").getOrElse("")
      else
        ""
    TypeName(Name(prefix + name), Nil, Some(TypeName.Schema))
  }
}

object NamespacePrefix {
  def apply(namespace: Option[Name], name: String): Name = {
    Name(namespace.map(ns => s"${ns}__").getOrElse("") + name)
  }
}

abstract class SimpleSObjectLike(_path: PathLike, _name: Name) extends SObjectLike(_path, _name) {
  override def typeName(namespace: Option[Name]): TypeName = {
    TypeName(NamespacePrefix(namespace, name.value), Nil, Some(TypeName.Schema))
  }
}

final case class CustomMetadataDocument(_path: PathLike, _name: Name)
    extends SimpleSObjectLike(_path, _name)

final case class BigObjectDocument(_path: PathLike, _name: Name)
    extends SimpleSObjectLike(_path, _name)

final case class PlatformEventDocument(_path: PathLike, _name: Name)
    extends SimpleSObjectLike(_path, _name)

final case class SObjectFieldDocument(_path: PathLike, _name: Name)
    extends MetadataDocument(_path, _name) {
  override val nature: MetadataNature = FieldNature
  override def typeName(namespace: Option[Name]): TypeName = {
    val sobjectName = path.parent.parent.basename
    val fieldsType =
      TypeName.sObjectTypeFields$(
        TypeName(NamespacePrefix(namespace, sobjectName), Nil, Some(TypeName.Schema)))
    TypeName(name, Nil, Some(fieldsType))
  }
}

final case class SObjectFieldSetDocument(_path: PathLike, _name: Name)
    extends MetadataDocument(_path, _name) {
  override val nature: MetadataNature = FieldSetNature
  override def typeName(namespace: Option[Name]): TypeName = {
    val sobjectName = path.parent.parent.basename
    val fieldSetType = TypeName.sObjectTypeFieldSets$(
      TypeName(NamespacePrefix(namespace, sobjectName), Nil, Some(TypeName.Schema)))
    TypeName(name, Nil, Some(fieldSetType))
  }
}

final case class SObjectSharingReasonDocument(_path: PathLike, _name: Name)
  extends MetadataDocument(_path, _name) {
  override val nature: MetadataNature = SharingReasonNature
  override def typeName(namespace: Option[Name]): TypeName = {
    val sobjectName = path.parent.parent.basename
    val sharingReasonType = TypeName.sObjectTypeRowClause$(
      TypeName(NamespacePrefix(namespace, sobjectName), Nil, Some(TypeName.Schema)))
    TypeName(name, Nil, Some(sharingReasonType))
  }
}

final case class PageDocument(_path: PathLike, _name: Name)
    extends MetadataDocument(_path, _name) {
  override val nature: MetadataNature = PageNature
  override def typeName(namespace: Option[Name]): TypeName = {
    TypeName(NamespacePrefix(namespace, name.value), Nil, Some(TypeName.Page))
  }
}

final case class FlowDocument(_path: PathLike, _name: Name)
    extends MetadataDocument(_path, _name) {
  override lazy val nature: MetadataNature = FlowNature
  override def typeName(namespace: Option[Name]): TypeName = {
    namespace
      .map(ns => TypeName(name, Nil, Some(TypeName(ns, Nil, Some(TypeName.Interview)))))
      .getOrElse(TypeName(name, Nil, Some(TypeName.Interview)))
  }
}

object MetadataDocument {
  def apply(path: PathLike): Option[MetadataDocument] = {
    splitFilename(path) match {
      case Array(name, Name("cls")) =>
        Some(ApexClassDocument(path, name))

      case Array(name, Name("trigger")) =>
        Some(ApexTriggerDocument(path, name))

      case Array(name, Name("xcls")) =>
        Some(ExtendedApexDocument(path, name))

      case Array(name, Name("component")) =>
        Some(ComponentDocument(path, name))

      case Array(name, Name("object")) if name.value.endsWith("__mdt") =>
        Some(CustomMetadataDocument(path, name))
      case Array(name, Name("object-meta"), Name("xml")) if name.value.endsWith("__mdt") =>
        Some(CustomMetadataDocument(path, name))

      case Array(name, Name("object")) if name.value.endsWith("__b") =>
        Some(BigObjectDocument(path, name))
      case Array(name, Name("object-meta"), Name("xml")) if name.value.endsWith("__b") =>
        Some(BigObjectDocument(path, name))

      case Array(name, Name("object")) if name.value.endsWith("__e") =>
        Some(PlatformEventDocument(path, name))
      case Array(name, Name("object-meta"), Name("xml")) if name.value.endsWith("__e") =>
        Some(PlatformEventDocument(path, name))

      case Array(name, Name("object")) =>
        Some(SObjectDocument(path, name))
      case Array(name, Name("object-meta"), Name("xml")) =>
        Some(SObjectDocument(path, name))

      case Array(name, Name("field-meta"), Name("xml"))
          if path.parent.basename.equalsIgnoreCase("fields") && !path.parent.parent.isRoot =>
        Some(SObjectFieldDocument(path, name))

      case Array(name, Name("fieldSet-meta"), Name("xml"))
          if path.parent.basename.equalsIgnoreCase("fieldSets") && !path.parent.parent.isRoot =>
        Some(SObjectFieldSetDocument(path, name))

      case Array(name, Name("sharingReason-meta"), Name("xml"))
        if path.parent.basename.equalsIgnoreCase("sharingReasons") && !path.parent.parent.isRoot =>
        Some(SObjectSharingReasonDocument(path, name))

      case Array(name, Name("flow")) =>
        Some(FlowDocument(path, name))
      case Array(name, Name("flow-meta"), Name("xml")) =>
        Some(FlowDocument(path, name))

      case Array(name, Name("labels")) =>
        Some(LabelsDocument(path, name))
      case Array(name, Name("labels-meta"), Name("xml")) =>
        Some(LabelsDocument(path, name))

      case Array(name, Name("page")) =>
        Some(PageDocument(path, name))
      case _ => None
    }
  }

  private def splitFilename(path: PathLike): Array[Name] = {
    val parts = path.basename.split('.')
    if (parts.length > 3) {
      Array(Name(parts.slice(0, parts.length - 2).mkString(".")),
            Name(parts(parts.length - 2)),
            Name(parts(parts.length - 1)))
    } else if (parts.length == 3) {
      Array(Name(parts(0)), Name(parts(1)), Name(parts(2)))
    } else if (parts.length == 2) {
      Array(Name(parts(0)), Name(parts(1)))
    } else {
      Array(Name(parts.head))
    }
  }
}
