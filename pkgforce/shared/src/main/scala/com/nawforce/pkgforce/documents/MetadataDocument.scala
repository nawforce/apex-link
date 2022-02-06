/*
 Copyright (c) 2019 Kevin Jones, All rights reserved.
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
 */
package com.nawforce.pkgforce.documents

import com.nawforce.pkgforce.diagnostics._
import com.nawforce.pkgforce.names._
import com.nawforce.pkgforce.path.{Location, PathLike}
import com.nawforce.runtime.parsers.SourceData

import scala.collection.compat.immutable.ArraySeq

/** The type of some metadata, such as Trigger metadata. Partial type metadata signals that multiple documents
  * may contribute to the same type.
  */
sealed abstract class MetadataNature(val partialType: Boolean = false)

case object LabelNature         extends MetadataNature(partialType = true)
case object ApexNature          extends MetadataNature
case object TriggerNature       extends MetadataNature
case object ComponentNature     extends MetadataNature
case object PageNature          extends MetadataNature
case object FlowNature          extends MetadataNature
case object SObjectNature       extends MetadataNature
case object FieldNature         extends MetadataNature
case object FieldSetNature      extends MetadataNature
case object SharingReasonNature extends MetadataNature

/** A piece of Metadata described in a file */
abstract class MetadataDocument(val path: PathLike, val name: Name) {

  /** Type of metadata, this could be stored but we prefer to save space ;-) */
  def nature: MetadataNature = {
    this match {
      case _: LabelsDocument               => LabelNature
      case _: ApexClassDocument            => ApexNature
      case _: ApexTriggerDocument          => TriggerNature
      case _: ComponentDocument            => ComponentNature
      case _: PageDocument                 => PageNature
      case _: FlowDocument                 => FlowNature
      case _: PlatformEventDocument        => SObjectNature
      case _: BigObjectDocument            => SObjectNature
      case _: CustomMetadataDocument       => SObjectNature
      case _: SObjectDocument              => SObjectNature
      case _: SObjectFieldDocument         => FieldNature
      case _: SObjectFieldSetDocument      => FieldSetNature
      case _: SObjectSharingReasonDocument => SharingReasonNature
    }
  }

  /** Return true to avoid indexing bad metadata such as empty files */
  def ignorable(): Boolean = false

  /** Generate a typename for this metadata, if this is not unique you may need to set duplicatesAllowed. */
  def typeName(namespace: Option[Name]): TypeName

  /** Obtain source data for this document */
  def source: IssuesAnd[Option[SourceData]] = {
    path.readSourceData() match {
      case Left(err) =>
        IssuesAnd(ArraySeq(Issue(path, Diagnostic(ERROR_CATEGORY, Location.empty, err))), None)
      case Right(data) => IssuesAnd(Some(data))
    }
  }
}

final case class LabelsDocument(_path: PathLike, _name: Name)
    extends MetadataDocument(_path, _name) {

  override def typeName(namespace: Option[Name]): TypeName = TypeName.Label
}

abstract class ClassDocument(_path: PathLike, _name: Name) extends MetadataDocument(_path, _name)

final case class ApexClassDocument(_path: PathLike, _name: Name)
    extends ClassDocument(_path, _name) {
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

final case class ComponentDocument(_path: PathLike, _name: Name)
    extends MetadataDocument(_path, _name) {
  override def typeName(namespace: Option[Name]): TypeName = {
    namespace
      .map(ns => TypeName(name, Nil, Some(TypeName(ns, Nil, Some(TypeName.Component)))))
      .getOrElse(TypeName(name, Nil, Some(TypeName.Component)))
  }
}

abstract class SObjectLike(_path: PathLike, _name: Name) extends MetadataDocument(_path, _name) {}

final case class SObjectDocument(_path: PathLike, _name: Name) extends SObjectLike(_path, _name) {

  private val isCustom            = name.value.endsWith("__c")
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
  override def typeName(namespace: Option[Name]): TypeName = {
    val sobjectName = path.parent.parent.basename
    val fieldsType =
      TypeName.sObjectTypeFields$(
        TypeName(NamespacePrefix(namespace, sobjectName), Nil, Some(TypeName.Schema))
      )
    TypeName(name, Nil, Some(fieldsType))
  }
}

final case class SObjectFieldSetDocument(_path: PathLike, _name: Name)
    extends MetadataDocument(_path, _name) {
  override def typeName(namespace: Option[Name]): TypeName = {
    val sobjectName = path.parent.parent.basename
    val fieldSetType = TypeName.sObjectTypeFieldSets$(
      TypeName(NamespacePrefix(namespace, sobjectName), Nil, Some(TypeName.Schema))
    )
    TypeName(name, Nil, Some(fieldSetType))
  }
}

final case class SObjectSharingReasonDocument(_path: PathLike, _name: Name)
    extends MetadataDocument(_path, _name) {
  override def typeName(namespace: Option[Name]): TypeName = {
    val sobjectName = path.parent.parent.basename
    val sharingReasonType = TypeName.sObjectTypeRowClause$(
      TypeName(NamespacePrefix(namespace, sobjectName), Nil, Some(TypeName.Schema))
    )
    TypeName(name, Nil, Some(sharingReasonType))
  }
}

final case class PageDocument(_path: PathLike, _name: Name) extends MetadataDocument(_path, _name) {
  override def typeName(namespace: Option[Name]): TypeName = {
    TypeName(NamespacePrefix(namespace, name.value), Nil, Some(TypeName.Page))
  }
}

final case class FlowDocument(_path: PathLike, _name: Name) extends MetadataDocument(_path, _name) {
  override def typeName(namespace: Option[Name]): TypeName = {
    namespace
      .map(ns => TypeName(name, Nil, Some(TypeName(ns, Nil, Some(TypeName.Interview)))))
      .getOrElse(TypeName(name, Nil, Some(TypeName.Interview)))
  }
}

object MetadataDocument {
  // These are slightly over general, additional constraints are applied below
  private val extensions: Seq[String] = Seq(
    "cls",
    "trigger",
    "component",
    "object",
    "object-meta.xml",
    "field",
    "field-meta.xml",
    "fieldSet",
    "fieldSet-meta.xml",
    "sharingReason",
    "sharingReason-meta.xml",
    "flow",
    "flow-meta.xml",
    "labels",
    "labels-meta.xml",
    "page"
  )

  def extensionsGlob: String = s"{${extensions.mkString(",")}}"

  def apply(path: PathLike): Option[MetadataDocument] = {
    var parts = path.basename.split('.')

    // If we have over split, likely due to '.' in name, try to recombine
    while (parts.length > 3 || (parts.length == 3 && parts(2) != "xml")) {
      parts = Array(s"${parts.head}.${parts(1)}") ++ parts.takeRight(parts.length - 2)
    }

    if (parts.length == 2) {
      val name = Name(parts.head)
      parts(1) match {
        case "cls"                                    => Some(ApexClassDocument(path, name))
        case "trigger"                                => Some(ApexTriggerDocument(path, name))
        case "object" if name.value.endsWith("__mdt") => Some(CustomMetadataDocument(path, name))
        case "object" if name.value.endsWith("__b")   => Some(BigObjectDocument(path, name))
        case "object" if name.value.endsWith("__e")   => Some(PlatformEventDocument(path, name))
        case "object"                                 => Some(SObjectDocument(path, name))
        case "component"                              => Some(ComponentDocument(path, name))
        case "flow"                                   => Some(FlowDocument(path, name))
        case "labels"                                 => Some(LabelsDocument(path, name))
        case "page"                                   => Some(PageDocument(path, name))
        case _                                        => None
      }
    } else if (parts.length == 3 && parts(2) == "xml") {
      val name = Name(parts.head)
      parts(1) match {
        case "field-meta"
            if path.parent.basename.equalsIgnoreCase("fields") && !path.parent.parent.isRoot =>
          Some(SObjectFieldDocument(path, name))
        case "fieldSet-meta"
            if path.parent.basename.equalsIgnoreCase("fieldSets") && !path.parent.parent.isRoot =>
          Some(SObjectFieldSetDocument(path, name))
        case "sharingReason-meta"
            if path.parent.basename.equalsIgnoreCase(
              "sharingReasons"
            ) && !path.parent.parent.isRoot =>
          Some(SObjectSharingReasonDocument(path, name))
        case "object-meta" if name.value.endsWith("__mdt") =>
          Some(CustomMetadataDocument(path, name))
        case "object-meta" if name.value.endsWith("__b") => Some(BigObjectDocument(path, name))
        case "object-meta" if name.value.endsWith("__e") => Some(PlatformEventDocument(path, name))
        case "object-meta"                               => Some(SObjectDocument(path, name))
        case "flow-meta"                                 => Some(FlowDocument(path, name))
        case "labels-meta"                               => Some(LabelsDocument(path, name))
        case _                                           => None
      }
    } else {
      None
    }
  }
}
