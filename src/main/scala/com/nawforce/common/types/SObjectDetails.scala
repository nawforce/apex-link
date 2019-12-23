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
package com.nawforce.common.types

import com.nawforce.common.documents._
import com.nawforce.common.names.{EncodedName, Name, TypeName}
import com.nawforce.common.path.{DIRECTORY, DOES_NOT_EXIST, NONEMPTY_FILE, PathLike}
import com.nawforce.common.xml.{XMLElementLike, XMLException, XMLFactory}
import com.nawforce.runtime.api.Org

sealed abstract class SObjectNature(val nature: String) {
  override def toString: String = nature
}
abstract class IntroducingNature(_nature: String) extends SObjectNature(_nature)
case object ListCustomSettingNature extends IntroducingNature("List")
case object HierarchyCustomSettingsNature extends IntroducingNature("Hierarchy")
case object CustomObjectNature extends IntroducingNature("CustomObject")
case object CustomMetadataNature extends SObjectNature("CustomMetadata")
case object PlatformObjectNature extends SObjectNature("PlatformObject")
case object PlatformEventNature extends SObjectNature("PlatformEvent")

final case class SObjectDetails(sobjectNature: SObjectNature, typeName: TypeName,
                                fields: Seq[CustomFieldDeclaration], fieldSets: Set[Name]) {

  def isIntroducing(pkg: PackageDeclaration): Boolean = {
    if (sobjectNature.isInstanceOf[IntroducingNature]) {
      EncodedName(typeName.name).namespace == pkg.namespace
    } else{
      sobjectNature == CustomMetadataNature || sobjectNature == PlatformEventNature
    }
  }

  def withTypeName(newTypeName: TypeName): SObjectDetails = {
    SObjectDetails(sobjectNature, newTypeName, fields, fieldSets)
  }
}

object SObjectDetails {
  def parseSObject(path: PathLike, pkg: PackageDeclaration): Option[SObjectDetails] = {
    val dt = DocumentType(path)
    assert(dt.exists(_.isInstanceOf[SObjectLike]))
    val typeName = TypeName(EncodedName(dt.get.name).defaultNamespace(pkg.namespace).fullName, Nil, Some(TypeName.Schema))

    // TODO: Improve handling of ghosted SObject types
    if (path.nature == DOES_NOT_EXIST) {
      val sobjectNature: SObjectNature = dt match {
        case Some(x: SObjectDocument) if x.name.value.endsWith("__c") => CustomObjectNature
        case Some(_: SObjectDocument) => PlatformObjectNature
      }

      val sfdxFields = parseSfdxFields(path, pkg, typeName, sobjectNature)
      val sfdxFieldSets = parseSfdxFieldSets(path, pkg)
      return Some(SObjectDetails(sobjectNature, typeName, sfdxFields, sfdxFieldSets.toSet))
    }

    val parseResult = XMLFactory.parse(path)
    if (parseResult.isLeft) {
      Org.logMessage(parseResult.left.get._1, parseResult.left.get._2)
      return None
    }
    val rootElement = parseResult.right.get.rootElement

    try {
      rootElement.assertIs("CustomObject")

      val sobjectNature: SObjectNature = dt match {
        case Some(_: CustomMetadataDocument) => CustomMetadataNature
        case Some(_: PlatformEventDocument) => PlatformEventNature
        case Some(x: SObjectDocument) if x.name.value.endsWith("__c") =>
          rootElement.getOptionalSingleChildAsString("customSettingsType") match {
            case Some("List") => ListCustomSettingNature
            case Some("Hierarchy") => HierarchyCustomSettingsNature
            case Some(x) =>
              Org.logMessage(RangeLocation(path, TextRange(rootElement.line)),
                s"Unexpected customSettingsType value '$x', should be 'List' or 'Hierarchy'")
              CustomObjectNature
            case _ => CustomObjectNature
          }
        case Some(_: SObjectDocument) => PlatformObjectNature
      }

      val fields = rootElement.getChildren("fields")
        .flatMap(f => CustomFieldDeclaration.parseField(f, path, pkg, typeName, sobjectNature))
      val sfdxFields = parseSfdxFields(path, pkg, typeName, sobjectNature)

      val fieldSets = rootElement.getChildren("fieldSets")
        .map(f => parseFieldSet(f, path, pkg))
      val sfdxFieldSets = parseSfdxFieldSets(path, pkg)

      Some(SObjectDetails(sobjectNature, typeName, fields ++ sfdxFields, (fieldSets ++ sfdxFieldSets).toSet))

    } catch {
      case e: XMLException =>
        Org.logMessage(RangeLocation(path, e.where), e.msg)
        None
    }
  }

  private def parseFieldSet(elem: XMLElementLike, path: PathLike, pkg: PackageDeclaration): Name = {
    EncodedName(elem.getSingleChildAsString("fullName"))
      .defaultNamespace(pkg.namespace).fullName
  }

  private def parseSfdxFields(path: PathLike, pkg: PackageDeclaration, sObjectType: TypeName,
                              sObjectNature: SObjectNature): Seq[CustomFieldDeclaration] = {

    val fieldsDir = path.parent.join("fields")
    if (fieldsDir.nature != DIRECTORY)
      return Seq()

    fieldsDir.directoryList() match {
      case Left(_) => Seq()
      case Right(entries) =>
        entries.filter(_.endsWith(".field-meta.xml"))
          .flatMap(entry => {
            val fieldPath = fieldsDir.join(entry)
            if (fieldPath.nature == NONEMPTY_FILE) {
              val parseResult = XMLFactory.parse(fieldPath)
              if (parseResult.isLeft) {
                Org.logMessage(parseResult.left.get._1, parseResult.left.get._2)
                None
              } else {
                val rootElement = parseResult.right.get.rootElement
                rootElement.assertIs("CustomField")
                CustomFieldDeclaration.parseField(rootElement, fieldPath, pkg, sObjectType, sObjectNature)
              }
            } else {
              None
            }
          })
    }
  }

  private def parseSfdxFieldSets(path: PathLike, pkg: PackageDeclaration): Seq[Name] = {
    val fieldsDir = path.parent.join("fieldSets")
    if (fieldsDir.nature != DIRECTORY)
      return Seq()

    fieldsDir.directoryList() match {
      case Left(_) => Seq()
      case Right(entries) =>
        entries.filter(_.endsWith(".fieldSet-meta.xml"))
          .flatMap(entry => {
            val fieldPath = fieldsDir.join(entry)
            if (fieldPath.nature == NONEMPTY_FILE) {
              val parseResult = XMLFactory.parse(fieldPath)
              if (parseResult.isLeft) {
                Org.logMessage(parseResult.left.get._1, parseResult.left.get._2)
                None
              } else {
                val rootElement = parseResult.right.get.rootElement
                rootElement.assertIs("FieldSet")
                Some(parseFieldSet(rootElement, fieldPath, pkg))
              }
            } else {
              None
            }
          })
    }
  }
}
