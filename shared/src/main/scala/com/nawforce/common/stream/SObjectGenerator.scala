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

package com.nawforce.common.stream

import com.nawforce.common.diagnostics.{Diagnostic, ERROR_CATEGORY, Issue, Location}
import com.nawforce.common.documents._
import com.nawforce.common.names.Name
import com.nawforce.common.path.PathLike
import com.nawforce.common.xml.{XMLElementLike, XMLException, XMLFactory}

case class SObjectEvent(path: String) extends PackageEvent

case class CustomFieldEvent(name: Name, rawType: Name, idTarget: Option[Name]) extends PackageEvent
case class FieldsetEvent(name: Name) extends PackageEvent
case class SharingReasonEvent(name: Name) extends PackageEvent

/** Convert SObject documents/folders into PackageEvents. We must call this even if there is not object-meta.xml file
 * present to collect the SFDX fields, fieldSets and sharingRules. */
object SObjectGenerator extends Generator {

  protected def toEvents(document: MetadataDocument): Iterator[PackageEvent] = {
    // The object-meta.xml file is annoyingly optional
    val path = if (document.path.exists) Some(document.path) else None

    // Collect whatever we can find into the stream, this is deliberately lax we are not trying to find errors here
    Iterator(SObjectEvent(document.path.toString)) ++ (path.map(XMLFactory.parse) match {
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
      collectSfdxFields(document.path) ++ collectSfdxFieldSets(document.path) ++ collectSfdxSharingReason(
      document.path)
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
      val idTarget = rawType match {
        case "Lookup" | "MasterDetail" | "MetadataRelationship" =>
          Some(Name(elem.getSingleChildAsString("referenceTo").trim))
        case _ => None
      }

      Iterator(CustomFieldEvent(name, Name(rawType), idTarget))
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
                  root.rootElement.assertIs(rootElement)
                  op(root.rootElement, filePath)
              }
            }
          })
    }
  }

  def catchXMLExceptions(path: PathLike)(op: => Iterator[PackageEvent]): Iterator[PackageEvent] = {
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
