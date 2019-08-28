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
import com.nawforce.cst._
import com.nawforce.documents._
import com.nawforce.utils.{DotName, Name}
import com.nawforce.xml.XMLUtils.getLine
import com.nawforce.xml.{XMLException, XMLLineLoader, XMLUtils}

import scala.xml.{Elem, SAXParseException}

final case class CustomFieldDeclaration(name: Name, typeName: TypeName)
  extends FieldDeclaration {

  override val modifiers: Seq[Modifier] = Seq(PUBLIC_MODIFIER)
  override val readAccess: Modifier = PUBLIC_MODIFIER
  override val writeAccess: Modifier = PUBLIC_MODIFIER

  override lazy val isStatic: Boolean = false
}

final case class CustomObjectDeclaration(_typeName: TypeName,
                                         override val fields: Seq[FieldDeclaration])
  extends NamedTypeDeclaration(_typeName) {

  override val superClass: Option[TypeName] = Some(TypeName.SObject)
  override def superClassDeclaration: Option[TypeDeclaration] = {
    new StandardTypeFinder().getTypeFor(TypeName.SObject.asDotName, this)
  }

  def validateConstructor(input: ExprContext, creator: Creator): ExprContext = {
    assert(creator.arrayCreatorRest.isEmpty)

    if (creator.mapCreatorRest.nonEmpty) {
      Org.logMessage(creator.location, s"Map construction not supported on SObject type '$typeName'")
    } else if (creator.setCreatorRest.nonEmpty) {
      Org.logMessage(creator.location, s"Set construction not supported on SObject type '$typeName'")
    } else if (creator.classCreatorRest.nonEmpty) {
      validateConstructorArguments(creator.classCreatorRest.get.arguments)
    } else {
      ExprContext(isStatic = false, Some(this))
    }
    ExprContext.empty
  }

  def validateConstructorArguments(arguments: Seq[Expression]): ExprContext = {
    val validArgs = arguments.flatMap(argument => {
      argument match {
        case BinaryExpression(PrimaryExpression(IdPrimary(id)), rhs, "=") =>
          val field = findField(id.name, staticOnly = false)
          if (field.isEmpty) {
            Org.logMessage(id.location, s"Unknown field '${id.name}' on SObject type '$typeName'")
            return ExprContext.empty
          } else {
            Some(id)
          }
        case _ =>
          Org.logMessage(argument.location, s"SObject type '$typeName' construction needs '<field name> = <value>' arguments")
          return ExprContext.empty
      }
    })

    if (validArgs.size == arguments.size) {
      val duplicates = validArgs.groupBy(_.name).collect { case (_, List(_, y, _*)) => y }
      if (duplicates.nonEmpty) {
        Org.logMessage(duplicates.head.location,
          s"Duplicate assignment to field '${duplicates.head.name}' on SObject type '$typeName'")
      } else {
        return ExprContext(isStatic = false, Some(this))
      }
    }
    ExprContext.empty
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
    val fields = CustomFieldDeclaration(Name.NameName, PlatformTypes.stringType.typeName) +:
      (PlatformTypes.sObjectType.fields ++ parse(path))

    // TODO: field types
    Seq(
      new CustomObjectDeclaration(typeName, fields),

      // TODO: Check fields & when should be available
      createShare(typeName),
      createFeed(typeName),
      createHistory(typeName)
    )
  }

  private def parse(path: Path): Seq[CustomFieldDeclaration] = {
    try {
      val root = XMLLineLoader.load(StreamProxy.getInputStream(path))
      XMLUtils.assertIs(root, "CustomObject")

      root.child.flatMap {
        case elem: Elem if elem.namespace == XMLUtils.sfNamespace && elem.label == "fields" =>
          parseField(elem)
        case _ => None
      }

    } catch {
      case e: XMLException => Org.logMessage(RangeLocation(path, e.where), e.msg); Seq()
      case e: SAXParseException => Org.logMessage(PointLocation(path,
        Position(e.getLineNumber, e.getColumnNumber)), e.getLocalizedMessage); Seq()
    }
  }

  private def parseField(elem: Elem): Seq[CustomFieldDeclaration] = {

    val name = XMLUtils.getSingleChildAsString(elem, "fullName")
    val rawType: String = XMLUtils.getSingleChildAsString(elem, "type")

    val dataType = rawType match {
      case "MasterDetail" => PlatformTypes.idType
      case "Lookup" => PlatformTypes.idType
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
      case _ => throw XMLException(TextRange(getLine(elem)), s"Unexpected type '$rawType' on custom field")
    }

    Seq(CustomFieldDeclaration(Name(name), dataType.typeName)) ++
      (if (rawType == "Lookup" || rawType == "MasterDetail") {
        val refType = TypeName(Name(XMLUtils.getSingleChildAsString(elem, "referenceTo")))
        Seq(CustomFieldDeclaration(Name(name.replaceAll("__c$", "__r")), refType))
      } else
        Seq())
  }

  private def createShare(typeName: TypeName): CustomObjectDeclaration = {
    val shareName = typeName.withNameReplace("__c$", "__Share")
    CustomObjectDeclaration(shareName, shareFields)
  }

  private lazy val shareFields = PlatformTypes.sObjectType.fields ++ Seq(
    CustomFieldDeclaration(Name.AccessLevel, PlatformTypes.stringType.typeName),
    CustomFieldDeclaration(Name.ParentId, PlatformTypes.idType.typeName),
    CustomFieldDeclaration(Name.RowCause, PlatformTypes.stringType.typeName),
    CustomFieldDeclaration(Name.UserOrGroupId, PlatformTypes.idType.typeName)
  )

  private def createFeed(typeName: TypeName): CustomObjectDeclaration = {
    val shareName = typeName.withNameReplace("__c$", "__Feed")
    CustomObjectDeclaration(shareName, feedFields)
  }

  private lazy val feedFields = PlatformTypes.sObjectType.fields ++ Seq(
    CustomFieldDeclaration(Name.BestCommentId, PlatformTypes.idType.typeName),
    CustomFieldDeclaration(Name.Body, PlatformTypes.stringType.typeName),
    CustomFieldDeclaration(Name.CommentCount, PlatformTypes.decimalType.typeName),
    CustomFieldDeclaration(Name.ConnectionId, PlatformTypes.idType.typeName),
    CustomFieldDeclaration(Name.InsertedById, PlatformTypes.idType.typeName),
    CustomFieldDeclaration(Name.IsRichText, PlatformTypes.booleanType.typeName),
    CustomFieldDeclaration(Name.LikeCount, PlatformTypes.decimalType.typeName),
    CustomFieldDeclaration(Name.LinkUrl, PlatformTypes.stringType.typeName),
    CustomFieldDeclaration(Name.NetworkScope, PlatformTypes.stringType.typeName),
    CustomFieldDeclaration(Name.ParentId, PlatformTypes.idType.typeName),
    CustomFieldDeclaration(Name.RelatedRecordId, PlatformTypes.idType.typeName),
    CustomFieldDeclaration(Name.Title, PlatformTypes.stringType.typeName),
    CustomFieldDeclaration(Name.Type, PlatformTypes.stringType.typeName),
    CustomFieldDeclaration(Name.Visibility, PlatformTypes.stringType.typeName),
  )

  private def createHistory(typeName: TypeName): CustomObjectDeclaration = {
    val shareName = typeName.withNameReplace("__c$", "__Feed")
    CustomObjectDeclaration(shareName, historyFields)
  }

  private lazy val historyFields = PlatformTypes.sObjectType.fields ++ Seq(
    CustomFieldDeclaration(Name.Field, PlatformTypes.stringType.typeName),
    CustomFieldDeclaration(Name.NewValue, PlatformTypes.objectType.typeName),
    CustomFieldDeclaration(Name.OldValue, PlatformTypes.objectType.typeName),
  )
}