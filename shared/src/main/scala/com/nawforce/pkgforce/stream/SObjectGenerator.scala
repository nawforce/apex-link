/*
 [The "BSD licence"]
 Copyright (c) 2021 Kevin Jones
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

package com.nawforce.pkgforce.stream

import com.nawforce.pkgforce.diagnostics._
import com.nawforce.pkgforce.documents._
import com.nawforce.pkgforce.names.Name
import com.nawforce.pkgforce.path.PathLike
import com.nawforce.pkgforce.xml.{XMLDocumentLike, XMLElementLike, XMLException, XMLFactory}
import com.nawforce.runtime.xml.XMLDocument

import scala.collection.mutable

sealed abstract class SharingModel(val value: String)
case object PrivateSharingModel extends SharingModel("Private")
case object ReadSharingModel extends SharingModel("Read")
case object ReadWriteSharingModel extends SharingModel("ReadWrite")
case object ReadWriteTransferSharingModel extends SharingModel("ReadWriteTransfer")
case object FullAccessSharingModel extends SharingModel("FullAccess")
case object ControlledByParentSharingModel extends SharingModel("ControlledByParent")
case object ControlledByCampaignSharingModel extends SharingModel("ControlledByCampaign")
case object ControlledByLeadOrContractSharingModel
    extends SharingModel("ControlledByLeadOrContract")

sealed trait CustomSettingType
case object ListCustomSetting extends CustomSettingType
case object HierarchyCustomSetting extends CustomSettingType

final case class SObjectEvent(
  sourceInfo: Option[SourceInfo],
  reportingPath: PathLike, // SFDX SObject directory or MDAPI .object file
  isDefining: Boolean, // Metadata is defining a new SObject
  customSettingsType: Option[CustomSettingType],
  sharingModel: Option[SharingModel])
    extends PackageEvent
final case class CustomFieldEvent(sourceInfo: SourceInfo,
                                  name: Name,
                                  rawType: Name,
                                  referenceTo: Option[(Name, Name)])
    extends PackageEvent
final case class FieldsetEvent(sourceInfo: SourceInfo, name: Name) extends PackageEvent
final case class SharingReasonEvent(sourceInfo: SourceInfo, name: Name) extends PackageEvent

/** Convert SObject documents/folders into PackageEvents. We must call this even if there is not object-meta.xml file
  * present to collect the SFDX fields, fieldSets and sharingRules. */
object SObjectGenerator {

  def iterator(index: DocumentIndex): Iterator[PackageEvent] = iterator(index.get(SObjectNature))

  private def iterator(documents: Iterator[MetadataDocument]): Iterator[PackageEvent] = {

    // SObjects need ordering so lookup target is output before the object using lookup
    val eventsByName =
      documents.map(document => (document.name, toEvents(document).toArray)).to(mutable.Map)
    val emitted = new mutable.HashSet[Name]()
    val output = new mutable.ArrayBuffer[Array[PackageEvent]]()

    var found = true
    while (found && eventsByName.nonEmpty) {
      found = false
      eventsByName.foreach(kv => {
        val depends = kv._2
          .collect { case CustomFieldEvent(_, _, _, Some((referenceTo, _))) => referenceTo }
          .filter(eventsByName.contains)
        if (depends.forall(d => emitted.contains(d))) {
          eventsByName.remove(kv._1)
          emitted.add(kv._1)
          output.append(kv._2)
          found = true
        }
      })
    }

    // If ordering failed, apply any left to end, this will fail on deploy
    eventsByName.foreach(kv => output.append(kv._2))
    output.flatten.iterator
  }

  private def toEvents(document: MetadataDocument): Iterator[PackageEvent] = {
    // The object-meta.xml file is annoyingly optional when extending
    val path = if (document.path.exists) Some(document.path) else None

    // Which makes parsing more fun
    val sourceData = path.flatMap(_.readSourceData().toOption)
    val sourceInfo =
      sourceData.map(source => SourceInfo(PathLocation(path.get.toString, Location.all), source))
    val parsed = sourceData.map(source => XMLDocument(path.get, source))
    if (parsed.nonEmpty && parsed.get.issues.nonEmpty)
      return IssuesEvent.iterator(parsed.get.issues)

    val doc = parsed.flatMap(_.value)
    val customSettingsType =
      doc.map(doc => extractCustomSettingsType(doc)).getOrElse(IssuesAnd(None))
    val sharingModelType = doc.map(doc => extractSharingModel(doc)).getOrElse(IssuesAnd(None))
    val isDefining = doc.exists(doc => doc.rootElement.getChildren("label").nonEmpty)
    val reportingPath =
      if (document.path.toString.endsWith("object-meta.xml"))
        document.path.parent
      else
        document.path

    // Collect whatever we can find into the stream, this is deliberately lax we are not trying to find errors here
    Iterator(
      SObjectEvent(sourceInfo,
                   reportingPath,
                   isDefining,
                   customSettingsType.value,
                   sharingModelType.value)) ++
      IssuesEvent.iterator(customSettingsType.issues) ++ IssuesEvent.iterator(
      sharingModelType.issues) ++
      doc
        .map(doc => {
          val rootElement = doc.rootElement
          rootElement
            .getChildren("fields")
            .flatMap(field => {
              createField(SourceInfo(PathLocation(path.toString, Location(field.line)),
                                     sourceData.get),
                          field,
                          path.get)
            }) ++
            rootElement
              .getChildren("fieldSets")
              .flatMap(fieldSet => {
                createFieldSet(SourceInfo(PathLocation(path.toString, Location(fieldSet.line)),
                                          sourceData.get),
                               fieldSet,
                               path.get)
              }) ++
            rootElement
              .getChildren("sharingReasons")
              .flatMap(sharingReason => {
                createSharingReason(SourceInfo(PathLocation(path.toString,
                                                            Location(sharingReason.line)),
                                               sourceData.get),
                                    sharingReason,
                                    path.get)
              })
        })
        .getOrElse(Iterator()) ++
      collectSfdxFields(document.path) ++
      collectSfdxFieldSets(document.path) ++
      collectSfdxSharingReason(document.path)
  }

  private def extractCustomSettingsType(
    doc: XMLDocumentLike): IssuesAnd[Option[CustomSettingType]] = {
    doc.rootElement.getOptionalSingleChildAsString("customSettingsType") match {
      case Some("List")      => IssuesAnd(Some(ListCustomSetting))
      case Some("Hierarchy") => IssuesAnd(Some(HierarchyCustomSetting))
      case Some(x) =>
        IssuesAnd(
          Array(
            Issue(doc.path,
                  ERROR_CATEGORY,
                  Location(doc.rootElement.line),
                  s"Unexpected customSettingsType value '$x', should be 'List' or 'Hierarchy'")),
          None)
      case None => IssuesAnd(None)
    }
  }

  private val allSharingModels = Seq(PrivateSharingModel,
                                     ReadSharingModel,
                                     ReadWriteSharingModel,
                                     ReadWriteTransferSharingModel,
                                     FullAccessSharingModel,
                                     ControlledByParentSharingModel,
                                     ControlledByCampaignSharingModel,
                                     ControlledByLeadOrContractSharingModel)

  private def extractSharingModel(doc: XMLDocumentLike): IssuesAnd[Option[SharingModel]] = {
    val sharingModel = doc.rootElement.getOptionalSingleChildAsString("sharingModel")
    if (sharingModel.nonEmpty) {
      val matched = allSharingModels.find(_.value == sharingModel.get)
      if (matched.nonEmpty) {
        IssuesAnd(matched)
      } else {
        IssuesAnd(Array(
                    Issue(doc.path,
                          ERROR_CATEGORY,
                          Location(doc.rootElement.line),
                          s"Unexpected sharingModel value '${sharingModel.get}'")),
                  None)
      }
    } else {
      IssuesAnd(None)
    }
  }

  private def collectSfdxFields(path: PathLike): Iterator[PackageEvent] = {
    collectMetadata(path.parent.join("fields"), ".field-meta.xml", "CustomField", createField)
  }

  private def collectSfdxFieldSets(path: PathLike): Iterator[PackageEvent] = {
    collectMetadata(path.parent.join("fieldSets"), ".fieldSet-meta.xml", "FieldSet", createFieldSet)
  }

  private def collectSfdxSharingReason(path: PathLike): Iterator[PackageEvent] = {
    collectMetadata(path.parent.join("sharingReasons"),
                    ".sharingReason-meta.xml",
                    "SharingReason",
                    createSharingReason)
  }

  private def createField(sourceInfo: SourceInfo,
                          elem: XMLElementLike,
                          path: PathLike): Iterator[PackageEvent] = {
    catchXMLExceptions(path) {
      val name = Name(elem.getSingleChildAsString("fullName").trim)

      // We only need custom fields
      if (!name.toString.endsWith("__c"))
        return Iterator()

      val rawType = elem.getSingleChildAsString("type").trim
      if (!fieldTypes.contains(rawType)) {
        return IssuesEvent.iterator(
          Array(
            Issue(path.toString,
                  Diagnostic(ERROR_CATEGORY,
                             Location(elem.line),
                             s"Unrecognised type '$rawType' on custom field '$name'"))))
      }

      // Create additional fields & lookup relationships for special fields
      val target = rawType match {
        case "Lookup" | "MasterDetail" | "MetadataRelationship" =>
          Some(
            (Name(elem.getSingleChildAsString("referenceTo").trim),
             Name(elem.getSingleChildAsString("relationshipName").trim)))
        case _ => None
      }

      Iterator(CustomFieldEvent(sourceInfo, name, Name(rawType), target))
    }
  }

  private def createFieldSet(sourceInfo: SourceInfo,
                             elem: XMLElementLike,
                             path: PathLike): Iterator[PackageEvent] = {
    catchXMLExceptions(path) {
      Iterator(FieldsetEvent(sourceInfo, Name(elem.getSingleChildAsString("fullName"))))
    }
  }

  private def createSharingReason(sourceInfo: SourceInfo,
                                  elem: XMLElementLike,
                                  path: PathLike): Iterator[PackageEvent] = {
    catchXMLExceptions(path) {
      Iterator(SharingReasonEvent(sourceInfo, Name(elem.getSingleChildAsString("fullName"))))
    }
  }

  private def collectMetadata(path: PathLike,
                              suffix: String,
                              rootElement: String,
                              op: (SourceInfo, XMLElementLike, PathLike) => Iterator[PackageEvent])
    : Iterator[PackageEvent] = {
    if (!path.isDirectory)
      return Iterator()

    path.directoryList() match {
      case Left(_) => Iterator()
      case Right(entries) =>
        entries.iterator
          .filter(_.endsWith(suffix))
          .flatMap(entry => {
            val filePath = path.join(entry)
            catchXMLExceptions(filePath) {
              filePath.readSourceData() match {
                case Left(err) =>
                  IssuesEvent.iterator(
                    Array(Issue(path.toString, Diagnostic(ERROR_CATEGORY, Location(0), err))))
                case Right(sourceData) =>
                  XMLFactory.parse(filePath) match {
                    case IssuesAnd(issues, doc) if doc.isEmpty => IssuesEvent.iterator(issues)
                    case IssuesAnd(_, doc) =>
                      doc.get.rootElement.checkIsOrThrow(rootElement)
                      op(SourceInfo(PathLocation(filePath.toString, Location.all), sourceData),
                         doc.get.rootElement,
                         filePath)
                  }
              }
            }
          })
    }
  }

  private def catchXMLExceptions(path: PathLike)(
    op: => Iterator[PackageEvent]): Iterator[PackageEvent] = {
    try {
      op
    } catch {
      case e: XMLException =>
        IssuesEvent.iterator(
          Array(Issue(path.toString, Diagnostic(ERROR_CATEGORY, e.where, e.msg))))
    }
  }

  private val fieldTypes = Set[String]("MasterDetail",
                                       "Lookup",
                                       "MetadataRelationship",
                                       "AutoNumber",
                                       "Checkbox",
                                       "Currency",
                                       "Date",
                                       "DateTime",
                                       "Email",
                                       "EncryptedText",
                                       "Number",
                                       "Percent",
                                       "Phone",
                                       "Picklist",
                                       "MultiselectPicklist",
                                       "Summary",
                                       "Text",
                                       "TextArea",
                                       "LongTextArea",
                                       "Url",
                                       "File",
                                       "Location",
                                       "Time",
                                       "Html")
}
