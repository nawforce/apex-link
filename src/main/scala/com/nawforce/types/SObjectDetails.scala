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
package com.nawforce.types

import java.nio.file.{Files, Path}

import com.nawforce.api.Org
import com.nawforce.documents._
import com.nawforce.names.{EncodedName, Name, TypeName}
import com.nawforce.types.CustomFieldDeclaration.parseField
import com.nawforce.xml.{XMLException, XMLLineLoader, XMLUtils}

import scala.collection.JavaConverters._
import scala.xml.{Elem, SAXParseException}

sealed abstract class SObjectNature(val nature: String) {
  override def toString: String = nature
}
abstract class IntroducingNature(_nature: String) extends SObjectNature(_nature)
case object ListCustomSettingNature extends IntroducingNature("List")
case object HierarchyCustomSettingsNature extends IntroducingNature("Hierarchy")
case object CustomObjectNature extends IntroducingNature("CustomObject")
case object CustomMetadataNature extends SObjectNature("CustomMetadata")
case object PlatformObjectNature extends SObjectNature("PlatformObject")

final case class SObjectDetails(sobjectNature: SObjectNature, typeName: TypeName,
                                fields: Seq[CustomFieldDeclaration], fieldSets: Set[Name]) {

  def isIntroducing(pkg: PackageDeclaration): Boolean = {
    (sobjectNature.isInstanceOf[IntroducingNature] && typeName.outer.map(_.name) == pkg.namespace) ||
      sobjectNature == CustomMetadataNature
  }

  def withTypeName(newTypeName: TypeName): SObjectDetails = {
    SObjectDetails(sobjectNature, newTypeName, fields, fieldSets)
  }
}

object SObjectDetails {
  def parseSObject(path: Path, pkg: PackageDeclaration): Option[SObjectDetails] = {
    try {
      val dt = DocumentType.apply(path)
      assert(dt.exists(_.isInstanceOf[SObjectLike]))
      val typeName = EncodedName(dt.get.name).defaultNamespace(pkg.namespace).asTypeName

      // TODO: Improve handling of ghosted SObject types
      if (!Files.exists(path)) {
        val sobjectNature: SObjectNature = dt match {
          case Some(x: SObjectDocument) if x.name.value.endsWith("__c") => CustomObjectNature
          case Some(_: SObjectDocument) => PlatformObjectNature
        }

        val sfdxFields = parseSfdxFields(path, pkg, typeName)
        val sfdxFieldSets = parseSfdxFieldSets(path, pkg)
        return Some(SObjectDetails(sobjectNature, typeName, sfdxFields, sfdxFieldSets.toSet))
      }

      val root = XMLLineLoader.load(StreamProxy.getInputStream(path))
      XMLUtils.assertIs(root, "CustomObject")

      val sobjectNature: SObjectNature = dt match {
        case Some(_: CustomMetadataDocument) => CustomMetadataNature
        case Some(x: SObjectDocument) if x.name.value.endsWith("__c") =>
          XMLUtils.getOptionalSingleChildAsString(root, "customSettingsType") match {
            case Some("List") => ListCustomSettingNature
            case Some("Hierarchy") => HierarchyCustomSettingsNature
            case Some(x) =>
              Org.logMessage(RangeLocation(path, TextRange(XMLUtils.getLine(root))),
                s"Unexpected customSettingsType value '$x', should be 'List' or 'Hierarchy'")
              CustomObjectNature
            case _ => CustomObjectNature
          }
        case Some(_: SObjectDocument) => PlatformObjectNature
      }

      val fields = root.child.flatMap {
        case elem: Elem if elem.namespace == XMLUtils.sfNamespace && elem.label == "fields" =>
          parseField(elem, path, pkg, typeName)
        case _ => None
      }
      val sfdxFields = parseSfdxFields(path, pkg, typeName)

      val fieldsSets = root.child.flatMap {
        case elem: Elem if elem.namespace == XMLUtils.sfNamespace && elem.label == "fieldSets" =>
          Some(parseFieldSet(elem, path, pkg))
        case _ => None
      }
      val sfdxFieldSets = parseSfdxFieldSets(path, pkg)

      Some(SObjectDetails(sobjectNature, typeName, fields ++ sfdxFields, (fieldsSets ++ sfdxFieldSets).toSet))

    } catch {
      case e: XMLException =>
        Org.logMessage(RangeLocation(path, e.where), e.msg)
        None
      case e: SAXParseException => Org.logMessage(PointLocation(path,
        Position(e.getLineNumber, e.getColumnNumber)), e.getLocalizedMessage)
        None
    }
  }

  private def parseFieldSet(elem: Elem, path: Path, pkg: PackageDeclaration): Name = {
    EncodedName(XMLUtils.getSingleChildAsString(elem, "fullName"))
      .defaultNamespace(pkg.namespace).fullName
  }

  private def parseSfdxFields(path: Path, pkg: PackageDeclaration, sObjectType: TypeName): Seq[CustomFieldDeclaration] = {
    val fieldsDir = path.getParent.resolve("fields")
    if (Files.isDirectory(fieldsDir)) {
      Files.newDirectoryStream(fieldsDir).asScala.flatMap(file => {
        if (Files.isRegularFile(file) && file.toString.endsWith(".field-meta.xml")) {
          try {
            val root = XMLLineLoader.load(StreamProxy.getInputStream(file))
            XMLUtils.assertIs(root, "CustomField")
            parseField(root, path, pkg, sObjectType)
          } catch {
            case e: XMLException =>
              Org.logMessage(RangeLocation(file, e.where), e.msg)
              None
            case e: SAXParseException => Org.logMessage(PointLocation(file,
              Position(e.getLineNumber, e.getColumnNumber)), e.getLocalizedMessage)
              None
          }
        } else {
          None
        }
      }).toSeq
    } else {
      Seq()
    }
  }

  private def parseSfdxFieldSets(path: Path, pkg: PackageDeclaration): Seq[Name] = {
    val fieldsDir = path.getParent.resolve("fieldSets")
    if (Files.isDirectory(fieldsDir)) {
      Files.newDirectoryStream(fieldsDir).asScala.flatMap(file => {
        if (Files.isRegularFile(file) && file.toString.endsWith(".fieldSet-meta.xml")) {
          try {
            val root = XMLLineLoader.load(StreamProxy.getInputStream(file))
            XMLUtils.assertIs(root, "FieldSet")
            Some(parseFieldSet(root, path, pkg))
          } catch {
            case e: XMLException =>
              Org.logMessage(RangeLocation(file, e.where), e.msg)
              None
            case e: SAXParseException => Org.logMessage(PointLocation(file,
              Position(e.getLineNumber, e.getColumnNumber)), e.getLocalizedMessage)
              None
          }
        } else {
          None
        }
      }).toSeq
    } else {
      Seq()
    }
  }
}
