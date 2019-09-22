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
import com.nawforce.names.{DotName, EncodedName, Name, TypeName}

import scala.collection.mutable

final case class SObjectDeclaration(pkg: PackageDeclaration, _typeName: TypeName,
                                    override val fields: Seq[FieldDeclaration], override val isComplete: Boolean)
  extends NamedTypeDeclaration(pkg, _typeName) {

  override val superClass: Option[TypeName] = Some(TypeName.SObject)
  override def superClassDeclaration: Option[TypeDeclaration] = {
    pkg.getTypeFor(TypeName.SObject, this)
  }

  def validateConstructor(input: ExprContext, creator: Creator, context: ExpressionVerifyContext): ExprContext = {
    assert(creator.arrayCreatorRest.isEmpty)

    if (creator.mapCreatorRest.nonEmpty) {
      Org.logMessage(creator.location, s"Map construction not supported on SObject type '$typeName'")
    } else if (creator.setCreatorRest.nonEmpty) {
      Org.logMessage(creator.location, s"Set construction not supported on SObject type '$typeName'")
    } else if (creator.classCreatorRest.nonEmpty) {
      validateConstructorArguments(creator.classCreatorRest.get.arguments, context)
    } else {
      ExprContext(isStatic = false, Some(this))
    }
    ExprContext.empty
  }

  def validateConstructorArguments(arguments: Seq[Expression], context: ExpressionVerifyContext): ExprContext = {
    val validArgs = arguments.flatMap(argument => {
      argument match {
        case BinaryExpression(PrimaryExpression(IdPrimary(id)), _, "=") =>
          var field : Option[FieldDeclaration] = None

          if (context.pkg.namespace.nonEmpty) {
            field = findField(context.defaultNamespace(id.name), staticOnly = false)
          }

          if (field.isEmpty)
            field = findField(id.name, staticOnly = false)

          if (field.isEmpty) {
            if (isComplete)
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

  override def findField(name: Name, staticOnly: Boolean): Option[FieldDeclaration] = {
    super.findFieldSObject(name, staticOnly).orElse({
      pkg.schema().relatedLists.findField(typeName, name, staticOnly)
    })
  }
}

object SObjectDeclaration {
  def create(pkg: PackageDeclaration, path: Path, data: InputStream): Seq[TypeDeclaration] = {
    val typeName = parseName(path, pkg.namespace)
    if (typeName.name.value.endsWith("__c") && typeName.outer.map(_.name) == pkg.namespace) {
      createNew(path, typeName, pkg)
    } else {
        if (pkg.isGhostedType(typeName))
          Seq(extendExisting(path, typeName, pkg, None))
        else {
          val sobjectType = pkg.getTypeOption(PlatformGetRequest(typeName, None))
          if (sobjectType.isEmpty || !sobjectType.get.superClassDeclaration.exists(superClass => superClass.typeName == TypeName.SObject)) {
            Org.logMessage(LineLocation(path, 0), s"No sObject declaration found for '$typeName'")
            return Seq()
          }
          Seq(extendExisting(path, typeName, pkg, sobjectType))
        }
    }
  }

  private def createNew(path: Path, typeName: TypeName, pkg: PackageDeclaration): Seq[TypeDeclaration] = {
    val fields =
      CustomFieldDeclaration(Name.NameName, PlatformTypes.stringType.typeName) +:
      CustomFieldDeclaration(Name.RecordTypeId, PlatformTypes.idType.typeName) +:
        (PlatformTypes.sObjectType.fields ++ CustomFieldDeclaration.parse(path, pkg, typeName))

    val sobjects: Seq[SObjectDeclaration] = Seq(
      new SObjectDeclaration(pkg, typeName, fields, isComplete = true),

      // TODO: Check fields & when should be available
      createShare(pkg, typeName),
      createFeed(pkg, typeName),
      createHistory(pkg, typeName)
    )
    sobjects.foreach(pkg.schema().sobjectTypes.add)
    sobjects
  }

  private def extendExisting(path: Path, typeName: TypeName, pkg: PackageDeclaration, base: Option[TypeDeclaration]): TypeDeclaration = {
    val isComplete = base.nonEmpty && pkg.basePackages.forall(!_.isGhosted)
    val fields = collectBaseFields(typeName.asDotName, pkg)
    base.getOrElse(PlatformTypes.sObjectType).fields.foreach(field => fields.put(field.name, field))
    CustomFieldDeclaration.parse(path, pkg, typeName).foreach(field => {fields.put(field.name, field)})
    new SObjectDeclaration(pkg, typeName, fields.values.toSeq, isComplete)
  }

  private def collectBaseFields(sObject: DotName, pkg: PackageDeclaration): mutable.Map[Name, FieldDeclaration] = {
    val collected: mutable.Map[Name, FieldDeclaration] = mutable.Map()
    pkg.basePackages.filterNot(_.isGhosted).foreach(basePkg => {
      val fields: Seq[FieldDeclaration] = basePkg.getTypeOption(PlatformGetRequest(sObject.asTypeName(), None)).map {
        case baseTd: SObjectDeclaration => baseTd.fields
        case _ => Nil
      }.getOrElse(Seq())
      fields.foreach(field => {collected.put(field.name, field)})
    })
    collected
  }

  private def parseName(path: Path, namespace: Option[Name]): TypeName = {
    val dt = DocumentType.apply(path)
    assert(dt.exists(_.isInstanceOf[SObjectDocument]))
    EncodedName(dt.get.name).defaultNamespace(namespace).asTypeName
  }

  private def createShare(pkg: PackageDeclaration, typeName: TypeName): SObjectDeclaration = {
    val shareName = typeName.withNameReplace("__c$", "__Share")
    SObjectDeclaration(pkg, shareName, shareFields, isComplete = true)
  }

  private lazy val shareFields = PlatformTypes.sObjectType.fields ++ Seq(
    CustomFieldDeclaration(Name.AccessLevel, PlatformTypes.stringType.typeName),
    CustomFieldDeclaration(Name.ParentId, PlatformTypes.idType.typeName),
    CustomFieldDeclaration(Name.RowCause, PlatformTypes.stringType.typeName),
    CustomFieldDeclaration(Name.UserOrGroupId, PlatformTypes.idType.typeName)
  )

  private def createFeed(pkg: PackageDeclaration, typeName: TypeName): SObjectDeclaration = {
    val shareName = typeName.withNameReplace("__c$", "__Feed")
    SObjectDeclaration(pkg, shareName, feedFields, isComplete = true)
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

  private def createHistory(pkg: PackageDeclaration, typeName: TypeName): SObjectDeclaration = {
    val shareName = typeName.withNameReplace("__c$", "__Feed")
    SObjectDeclaration(pkg, shareName, historyFields, isComplete = true)
  }

  private lazy val historyFields = PlatformTypes.sObjectType.fields ++ Seq(
    CustomFieldDeclaration(Name.Field, PlatformTypes.stringType.typeName),
    CustomFieldDeclaration(Name.NewValue, PlatformTypes.objectType.typeName),
    CustomFieldDeclaration(Name.OldValue, PlatformTypes.objectType.typeName),
  )
}