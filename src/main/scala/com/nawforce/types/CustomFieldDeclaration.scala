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

import java.nio.file.Path

import com.nawforce.documents.{RangeLocation, TextRange}
import com.nawforce.names.{EncodedName, Name, TypeName}
import com.nawforce.xml.{XMLException, XMLUtils}

import scala.xml.Elem

final case class CustomFieldDeclaration(name: Name, typeName: TypeName, asStatic: Boolean = false)
  extends FieldDeclaration {

  override val modifiers: Seq[Modifier] = Seq(PUBLIC_MODIFIER) ++
    (if (asStatic) Seq(STATIC_MODIFIER) else Seq())
  override val readAccess: Modifier = PUBLIC_MODIFIER
  override val writeAccess: Modifier = PUBLIC_MODIFIER

  override lazy val isStatic: Boolean = asStatic
}

object CustomFieldDeclaration {

  def parseField(elem: Elem, path: Path, pkg: PackageDeclaration, sObjectType: TypeName): Seq[CustomFieldDeclaration] = {
    val rawName: String = XMLUtils.getSingleChildAsString(elem, "fullName").trim
    val name = Name(pkg.namespace.map(ns => s"${ns.value}__$rawName").getOrElse(rawName))
    val rawType: String = XMLUtils.getSingleChildAsString(elem, "type").trim

    val dataType = rawType match {
      case "MasterDetail" => PlatformTypes.idType
      case "Lookup" => PlatformTypes.idType
      case "MetadataRelationship" => PlatformTypes.idType
      case "AutoNumber" => PlatformTypes.stringType
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
      case _ => throw XMLException(TextRange(XMLUtils.getLine(elem)), s"Unexpected type '$rawType' on custom field")
    }

    Seq(CustomFieldDeclaration(name, dataType.typeName)) ++
      (if (rawType == "Lookup" || rawType == "MasterDetail" || rawType == "MetadataRelationship") {
        val referenceTo = Name(XMLUtils.getSingleChildAsString(elem, "referenceTo").trim)
        val relName = Name(XMLUtils.getSingleChildAsString(elem, "relationshipName").trim+"__r")
        val refTypeName = TypeName(EncodedName(referenceTo).defaultNamespace(pkg.namespace).fullName, Nil, Some(TypeName.Schema))

        pkg.schema().relatedLists.add(refTypeName, relName, name, sObjectType,
          RangeLocation(path, TextRange(XMLUtils.getLine(elem))))

        Seq(CustomFieldDeclaration(name.replaceAll("__c$", "__r"), refTypeName))
      } else if (rawType == "Location") {
        Seq(
          CustomFieldDeclaration(name.replaceAll("__c$", "__latitude__s"), TypeName.Double),
          CustomFieldDeclaration(name.replaceAll("__c$", "__longitude__s"), TypeName.Double)
        )
      } else
        Seq())
  }

  /* TypeNames that may be used in SObjects (see above for when */
  def isSObjectPrimitive(typeName: TypeName): Boolean = {
    typeName match {
      case TypeName.Id | TypeName.String | TypeName.Boolean | TypeName.Decimal | TypeName.Integer |
           TypeName.Date | TypeName.Datetime | TypeName.Time | TypeName.Blob | TypeName.Location=> true
      case _ => false
    }
  }
}

