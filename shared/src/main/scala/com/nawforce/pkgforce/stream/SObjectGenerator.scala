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

import scala.collection.mutable

final case class SObjectEvent(path: PathLike, customSettingsType: Option[String])
    extends PackageEvent
final case class CustomFieldEvent(name: Name, rawType: Name, referenceTo: Option[(Name, Name)])
    extends PackageEvent
final case class FieldsetEvent(name: Name) extends PackageEvent
final case class SharingReasonEvent(name: Name) extends PackageEvent

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
          .collect { case CustomFieldEvent(_, _, Some((referenceTo, _))) => referenceTo }
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
    // The object-meta.xml file is annoyingly optional
    val path = if (document.path.exists) Some(document.path) else None

    // Which makes extracting the customSettingsType more fun
    val parsed = path.map(XMLFactory.parse)
    val customSettingsType = (parsed map {
      case Right(doc) => extractCustomSettingsType(doc)
      case _          => IssuesAnd(None)
    }).getOrElse(IssuesAnd(None))

    // Collect whatever we can find into the stream, this is deliberately lax we are not trying to find errors here
    Iterator(SObjectEvent(document.path, customSettingsType.value)) ++
      IssuesEvent.iterator(customSettingsType.issues) ++
      (parsed match {
        case Some(Left(issue)) => IssuesEvent.iterator(Seq(issue))
        case Some(Right(root)) =>
          val rootElement = root.rootElement
          rootElement.getChildren("fields").flatMap(field => createField(field, path.get)) ++
            rootElement
              .getChildren("fieldSets")
              .flatMap(field => createFieldSet(field, path.get)) ++
            rootElement
              .getChildren("sharingReasons")
              .flatMap(field => createSharingReason(field, path.get))

        case None => Iterator()
      }) ++
      collectSfdxFields(document.path) ++
      collectSfdxFieldSets(document.path) ++
      collectSfdxSharingReason(document.path)
  }

  private def extractCustomSettingsType(doc: XMLDocumentLike): IssuesAnd[Option[String]] = {
    doc.rootElement.getOptionalSingleChildAsString("customSettingsType") match {
      case Some("List")      => IssuesAnd(Some("List"))
      case Some("Hierarchy") => IssuesAnd(Some("Hierarchy"))
      case Some(x) =>
        IssuesAnd(
          Seq(
            Issue(doc.path,
                  ERROR_CATEGORY,
                  Location(doc.rootElement.line),
                  s"Unexpected customSettingsType value '$x', should be 'List' or 'Hierarchy'")),
          None)
      case None => IssuesAnd(None)
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

  private def createField(elem: XMLElementLike, path: PathLike): Iterator[PackageEvent] = {
    catchXMLExceptions(path) {
      val name = Name(elem.getSingleChildAsString("fullName").trim)

      // We only need custom fields
      if (!name.toString.endsWith("__c"))
        return Iterator()

      val rawType = elem.getSingleChildAsString("type").trim
      if (!fieldTypes.contains(rawType)) {
        return IssuesEvent.iterator(
          Seq(
            Issue(path.toString,
                  Diagnostic(ERROR_CATEGORY,
                             Location(elem.line),
                             s"Unrecognised type '$rawType' on custom field '$name'"))))
      }

      // Create additional fields & lookup relationships for special fields
      val target = rawType match {
        case "Lookup" | "MasterDetail" | "MetadataRelationship" =>
          Some((Name(elem.getSingleChildAsString("referenceTo").trim),
            Name(elem.getSingleChildAsString("relationshipName").trim)))
        case _ => None
      }

      Iterator(CustomFieldEvent(name, Name(rawType), target))
    }
  }

  private def createFieldSet(elem: XMLElementLike, path: PathLike): Iterator[PackageEvent] = {
    catchXMLExceptions(path) {
      Iterator(FieldsetEvent(Name(elem.getSingleChildAsString("fullName"))))
    }
  }

  private def createSharingReason(elem: XMLElementLike, path: PathLike): Iterator[PackageEvent] = {
    catchXMLExceptions(path) {
      Iterator(SharingReasonEvent(Name(elem.getSingleChildAsString("fullName"))))
    }
  }

  private def collectMetadata(
    path: PathLike,
    suffix: String,
    rootElement: String,
    op: (XMLElementLike, PathLike) => Iterator[PackageEvent]): Iterator[PackageEvent] = {
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
              XMLFactory.parse(filePath) match {
                case Left(issue) => IssuesEvent.iterator(Seq(issue))
                case Right(root) =>
                  root.rootElement.checkIsOrThrow(rootElement)
                  op(root.rootElement, filePath)
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
        IssuesEvent.iterator(Seq(Issue(path.toString, Diagnostic(ERROR_CATEGORY, e.where, e.msg))))
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
