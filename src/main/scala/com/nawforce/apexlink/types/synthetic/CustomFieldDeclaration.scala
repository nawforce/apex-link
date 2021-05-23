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
package com.nawforce.apexlink.types.synthetic

import com.nawforce.apexlink.names.{TypeNames, _}
import com.nawforce.apexlink.org.Module
import com.nawforce.apexlink.types.core.FieldDeclaration
import com.nawforce.apexlink.types.platform.PlatformTypes
import com.nawforce.apexlink.types.schema.{PlatformObjectNature, SObjectNature}
import com.nawforce.pkgforce.diagnostics.{Location, PathLocation}
import com.nawforce.pkgforce.modifiers._
import com.nawforce.pkgforce.names.{EncodedName, Name, TypeName}
import com.nawforce.pkgforce.path.PathLike
import com.nawforce.pkgforce.xml.{XMLElementLike, XMLException}

final case class CustomFieldDeclaration(name: Name,
                                        typeName: TypeName,
                                        idTarget: Option[TypeName],
                                        asStatic: Boolean = false)
    extends FieldDeclaration {

  override val modifiers: Array[Modifier] = CustomFieldDeclaration.getModifiers(asStatic)
  override val readAccess: Modifier = PUBLIC_MODIFIER
  override val writeAccess: Modifier = PUBLIC_MODIFIER

  override lazy val isStatic: Boolean = asStatic
}

object CustomFieldDeclaration {
  val standardModifiers: Array[Modifier] = Array(PUBLIC_MODIFIER)
  val staticModifiers: Array[Modifier] = Array(PUBLIC_MODIFIER, STATIC_MODIFIER)

  def getModifiers(isStatic: Boolean): Array[Modifier] = {
    if (isStatic) standardModifiers else standardModifiers
  }

  def parseField(elem: XMLElementLike,
                 path: PathLike,
                 module: Module,
                 sObjectType: TypeName,
                 sObjectNature: SObjectNature): Seq[CustomFieldDeclaration] = {

    val rawName: String = elem.getSingleChildAsString("fullName").trim
    if (!rawName.endsWith("__c"))
      return Seq()

    val name = Name(module.namespace.map(ns => s"${ns.value}__$rawName").getOrElse(rawName))
    val rawTypeOption = elem.getOptionalSingleChildAsString("type").map(_.trim)

    if (rawTypeOption.isEmpty && sObjectNature == PlatformObjectNature && EncodedName(name).ext.isEmpty) {
      // Allow missing type on standard fields of standard objects
      return Seq()
    } else if (rawTypeOption.isEmpty) {
      throw XMLException(Location(elem.line),
                         s"Expecting custom field '$name' to have 'type' child element")
    }

    val rawType = rawTypeOption.get
    val dataType = rawType match {
      case "MasterDetail"         => PlatformTypes.idType
      case "Lookup"               => PlatformTypes.idType
      case "MetadataRelationship" => PlatformTypes.idType
      case "AutoNumber"           => PlatformTypes.stringType
      case "Checkbox"             => PlatformTypes.booleanType
      case "Currency"             => PlatformTypes.decimalType
      case "Date"                 => PlatformTypes.dateType
      case "DateTime"             => PlatformTypes.datetimeType
      case "Email"                => PlatformTypes.stringType
      case "EncryptedText"        => PlatformTypes.stringType
      case "Number"               => PlatformTypes.decimalType
      case "Percent"              => PlatformTypes.decimalType
      case "Phone"                => PlatformTypes.stringType
      case "Picklist"             => PlatformTypes.stringType
      case "MultiselectPicklist"  => PlatformTypes.stringType
      case "Summary"              => PlatformTypes.decimalType
      case "Text"                 => PlatformTypes.stringType
      case "TextArea"             => PlatformTypes.stringType
      case "LongTextArea"         => PlatformTypes.stringType
      case "Url"                  => PlatformTypes.stringType
      case "File"                 => PlatformTypes.stringType
      case "Location"             => PlatformTypes.locationType
      case "Time"                 => PlatformTypes.timeType
      case "Html"                 => PlatformTypes.stringType
      case _ =>
        throw XMLException(Location(elem.line), s"Unexpected type '$rawType' on custom field")
    }

    // Create additional fields & lookup relationships for special fields
    var idTarget: Option[TypeName] = None
    var referenceFields = Seq[CustomFieldDeclaration]()
    if (rawType == "Lookup" || rawType == "MasterDetail" || rawType == "MetadataRelationship") {
      val referenceTo = Name(elem.getSingleChildAsString("referenceTo").trim)
      val relName = Name(elem.getSingleChildAsString("relationshipName").trim + "__r")
      val refTypeName = TypeName(
        EncodedName(referenceTo).defaultNamespace(module.namespace).fullName,
        Nil,
        Some(TypeNames.Schema))
      idTarget = Some(refTypeName)

      module
        .schema()
        .relatedLists
        .add(refTypeName,
             relName,
             name,
             sObjectType,
             PathLocation(path.toString, Location(elem.line)))

      referenceFields = Seq(
        CustomFieldDeclaration(name.replaceAll("__c$", "__r"), refTypeName, None))
    } else if (rawType == "Location") {
      referenceFields = Seq(
        CustomFieldDeclaration(name.replaceAll("__c$", "__latitude__s"), TypeNames.Double, None),
        CustomFieldDeclaration(name.replaceAll("__c$", "__longitude__s"), TypeNames.Double, None))
    }

    Seq(CustomFieldDeclaration(name, dataType.typeName, idTarget)) ++ referenceFields
  }

  /* TypeNames that may be used in SObjects (see above for when */
  def isSObjectPrimitive(typeName: TypeName): Boolean = {
    typeName match {
      case TypeNames.IdType | TypeNames.String | TypeNames.Boolean | TypeNames.Decimal |
          TypeNames.Integer | TypeNames.Date | TypeNames.Datetime | TypeNames.Time |
          TypeNames.Blob | TypeNames.Location | TypeNames.Address =>
        true
      case _ => false
    }
  }
}
