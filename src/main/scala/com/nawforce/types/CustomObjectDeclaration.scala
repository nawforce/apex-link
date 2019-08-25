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

import java.io.InputStream
import java.nio.file.Path

import com.nawforce.api.Org
import com.nawforce.documents._
import com.nawforce.utils.{DotName, Name}
import com.nawforce.xml.{XMLException, XMLLineLoader, XMLUtils}

import scala.xml.{Elem, SAXParseException}

final case class CustomFieldDeclaration(name: Name, dataType: TypeDeclaration)
  extends FieldDeclaration {

  override val modifiers: Seq[Modifier] = Seq(PUBLIC_MODIFIER)
  override val typeName: TypeName = dataType.typeName
  override val readAccess: Modifier = PUBLIC_MODIFIER
  override val writeAccess: Modifier = PUBLIC_MODIFIER

  override lazy val isStatic: Boolean = false
}

final case class CustomObjectDeclaration(_typeName: TypeName,
                                         override val fields: Seq[CustomFieldDeclaration])
  extends NamedTypeDeclaration(_typeName) {

  override val superClass: Option[TypeName] = Some(TypeName.SObject)
  override def superClassDeclaration: Option[TypeDeclaration] = {
    new StandardTypeFinder().getTypeFor(TypeName.SObject.asDotName, this)
  }
}

object CustomObjectDeclaration {
  def create(pkg: PackageDeclaration, path: Path, data: InputStream): Seq[CustomObjectDeclaration] = {
    val name = DotName(DocumentType.apply(path).get.name).demangled
    val ns = if (pkg.namespace.value.isEmpty) None else Some(TypeName(pkg.namespace))
    val typeName =
      if (!name.isCompound)
        TypeName(name.firstName, Nil, ns)
      else
        TypeName(name.names(1), Nil, Some(TypeName(name.firstName)))
    val fields = parse(path)
    Seq(
      new CustomObjectDeclaration(typeName, fields),
      new CustomObjectDeclaration(typeName.withNameReplace("__c$", "__Share"), fields),
      new CustomObjectDeclaration(typeName.withNameReplace("__c$", "__Feed"), fields),
      new CustomObjectDeclaration(typeName.withNameReplace("__c$", "__History"), fields)
    )
  }

  private def parse(path: Path): Seq[CustomFieldDeclaration] = {
    try {
      val root = XMLLineLoader.load(StreamProxy.getInputStream(path))
      XMLUtils.assertIs(root, "CustomObject")

      root.child.flatMap {
        case elem: Elem if elem.namespace == XMLUtils.sfNamespace && elem.label == "fields" =>
          Some(parseField(elem))
        case _ => None
      }

    } catch {
      case e: XMLException => Org.logMessage(RangeLocation(path, e.where), e.msg); Seq()
      case e: SAXParseException => Org.logMessage(PointLocation(path,
        Position(e.getLineNumber, e.getColumnNumber)), e.getLocalizedMessage); Seq()
    }
  }

  private def parseField(elem: Elem): CustomFieldDeclaration = {

    val name = XMLUtils.getSingleChildAsString(elem, "fullName")
    val rawType: String = XMLUtils.getSingleChildAsString(elem, "type")
    val dataType = rawType match {
      case "MasterDetail" => PlatformTypes.idType
      case "AutoNumber" => PlatformTypes.stringType
      case "Lookup" => PlatformTypes.idType
      case "Checkbox" => PlatformTypes.booleanType
      case "Currency" => PlatformTypes.decimalType
      case "Date" => PlatformTypes.dateType
      case "DateTime" => PlatformTypes.datetimeType
      case "Email" => PlatformTypes.stringType
      case "EncryptedText" => PlatformTypes.blobType
      case "Number" => PlatformTypes.decimalType
      case "Percent" => PlatformTypes.decimalType
      case "Phone" => PlatformTypes.stringType
      case "Picklist" => PlatformTypes.stringType
      case "MultiselectPicklist" => PlatformTypes.stringType
      case "Summary" => PlatformTypes.decimalType
      case "Text" => PlatformTypes.stringType
      case "TextArea" => PlatformTypes.stringType
      case "LongTextArea" => PlatformTypes.stringType
      case "Url" => PlatformTypes.stringType
      case "File" => PlatformTypes.stringType
      case "Location" => PlatformTypes.locationType
      case "Time" => PlatformTypes.timeType
      case "Html" => PlatformTypes.stringType
      // TODO: Log a message on unexpected type
    }

    CustomFieldDeclaration(Name(name), dataType)
  }

}