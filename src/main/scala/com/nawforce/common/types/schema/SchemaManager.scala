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
package com.nawforce.common.types.schema

import com.nawforce.common.cst.VerifyContext
import com.nawforce.common.documents.LocationImpl
import com.nawforce.common.finding.TypeRequest
import com.nawforce.common.names.{EncodedName, Name, TypeName}
import com.nawforce.common.org.{OrgImpl, PackageImpl}
import com.nawforce.common.types.platform.PlatformTypes
import com.nawforce.common.types.{schema, _}
import com.nawforce.runtime.types._

import scala.collection.mutable

/* Support for Schema.* handling in Apex */
class SchemaManager(pkg: PackageImpl) extends PlatformTypes.PlatformTypeObserver {
  val sobjectTypes: SchemaSObjectType = SchemaSObjectType(pkg)
  val relatedLists: RelatedLists = new RelatedLists(pkg)

  PlatformTypes.addLoadingObserver(this)

  override def loaded(td: PlatformTypeDeclaration): Unit = {
    if (td.isSObject) {
      sobjectTypes.createSObjectTypeDeclarations(td.name)
    }
  }
}

/* Relationship field tracker, handles finding related lists */
class RelatedLists(pkg: PackageImpl) {
  private val relationshipFields = mutable.Map[TypeName, Seq[(CustomFieldDeclaration, Name, LocationImpl)]]() withDefaultValue Seq()

  /* Declare a new relationship field */
  def add(sObject: TypeName, relationshipName: Name, holdingFieldName: Name, holdingSObject: TypeName, location: LocationImpl): Unit = {
    val encodedName = EncodedName(relationshipName).defaultNamespace(pkg.namespace).fullName
    val field = CustomFieldDeclaration(encodedName, TypeName.recordSetOf(holdingSObject), None)
    relationshipFields.put(sObject, (field, holdingFieldName, location) +: relationshipFields(sObject))
  }

  /* Post object loading validation to make sure relationships exist */
  def validate(): Unit = {
    val changedObjects = mutable.Set[TypeDeclaration]()

    // Validate lookups will function
    val sobjects = relationshipFields.keys.toSet
    sobjects.foreach(sobject => {
      val td = TypeRequest(sobject, pkg, excludeSObjects = false).toOption
      if ((td.isEmpty || !td.exists(_.isSObject)) && !pkg.isGhostedType(sobject)) {
        relationshipFields(sobject).foreach(field => {
          OrgImpl.logError(field._3,
            s"Lookup object $sobject does not exist for field '${field._2}'")
        })
      } else if (td.exists(sobject => sobject.isInstanceOf[PlatformTypeDeclaration] && sobject.isSObject)) {
        changedObjects.add(td.get)
      }
      if (td.nonEmpty && sobject != td.get.typeName) {
        relationshipFields.put(td.get.typeName, relationshipFields(sobject))
        relationshipFields.remove(sobject)
      }
    })

    // Wrap any objects with lookups relationships so they are visible
    changedObjects.foreach(td => {
      pkg.upsertMetadata(schema.SObjectDeclaration(pkg, td.typeName, CustomObjectNature, Set(), td.fields, isComplete = true))
    })
  }

  /* Find for a relationship field on an SObject*/
  def findField(sobjectType: TypeName, name: Name): Option[FieldDeclaration] = {
    val encodedName = EncodedName(name).defaultNamespace(pkg.namespace).fullName
    relationshipFields(sobjectType).find(field => field._1.name == encodedName).map(_._1).orElse({
      pkg.basePackages.flatMap(basePkg => {
        basePkg.schema().relatedLists.findField(sobjectType, encodedName)
      }).headOption
    })
  }
}

/* Schema.SObjectType implementation */
final case class SchemaSObjectType(pkg: PackageImpl) extends BasicTypeDeclaration(pkg, TypeName.SObjectType) {
  private val sobjectFields: mutable.Map[Name, FieldDeclaration] = mutable.Map()
  private val sobjectTypeDeclarationsCreated = mutable.Set[Name]()

  /* Allow adding of virtual SObjects such as for shares etc */
  def add(sObject: SObjectDeclaration): Unit = {
    createSObjectDescribeField(sObject.name, sObject.typeName)
  }

  /* Find a specific SObject */
  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    if (!staticContext.contains(true))
      return None

    val typeName = EncodedName(name).asTypeName
    if (pkg.isGhostedType(typeName)) {
      return Some(createSObjectDescribeField(name, typeName))
    }

    sobjectFields.get(name).orElse({
      /* If not yet present check if we should create and cache */
      val td = TypeRequest(TypeName(name), pkg, excludeSObjects = false).toOption
      if (td.nonEmpty && td.get.superClassDeclaration.exists(superClass => superClass.typeName == TypeName.SObject)) {
        Some(createSObjectDescribeField(name, td.get.typeName))
      } else {
        None
      }
    })
  }

  override def findMethod(name: Name, params: Seq[TypeName], staticContext: Option[Boolean],
        verifyContext: VerifyContext): Seq[MethodDeclaration] = {
    PlatformTypes.sObjectTypeType.findMethod(name, params, staticContext, verifyContext)
  }

  /* Create the describe entries for an SObject, note we are using generics to tunnel the type so that
   * we can support Field & FieldSet access via injecting virtual TypeDeclarations for these.
   */
  private def createSObjectDescribeField(sobjectName: Name, typeName: TypeName): FieldDeclaration = {
    createSObjectTypeDeclarations(sobjectName)

    val describeField = CustomFieldDeclaration(sobjectName, TypeName.describeSObjectResultOf(typeName), None, asStatic = true)
    sobjectFields.put(sobjectName, describeField)
    describeField
  }

  /* Inject virtual type declarations for an sobject to override the platform generic versions. This is done
   * dynamically so we don't need to create for every SObject */
  def createSObjectTypeDeclarations(sobjectName: Name): Unit = {
    if (!sobjectTypeDeclarationsCreated.contains(sobjectName)) {
      sobjectTypeDeclarationsCreated.add(sobjectName)
      val fields = SObjectFields(sobjectName, pkg)
      val typeFields = SObjectTypeFields(sobjectName, pkg)
      pkg.upsertMetadata(typeFields)
      pkg.upsertMetadata(fields)
      pkg.upsertMetadata(SObjectTypeImpl(sobjectName, fields, pkg))
      pkg.upsertMetadata(SObjectTypeFieldSets(sobjectName, pkg))
    }
  }
}

final case class SObjectTypeImpl(sobjectName: Name, sobjectFields: SObjectFields, pkg: PackageImpl)
  extends BasicTypeDeclaration(pkg, TypeName.sObjectType$(TypeName(sobjectName, Nil, Some(TypeName.Schema)))) {

  private lazy val fieldField = CustomFieldDeclaration(Name.Fields,
    TypeName.sObjectFields$(TypeName(sobjectName, Nil, Some(TypeName.Schema))), None, asStatic = true)

  override val superClass: Option[TypeName] = Some(TypeName.SObjectType)

  override lazy val superClassDeclaration: Option[TypeDeclaration] = {
    superClass.flatMap(sc => PlatformTypes.get(sc, None).toOption)
  }

  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    (name, staticContext) match {
      case (Name.Fields, Some(false)) => Some(fieldField)
      // Workaround for bug when doing Account.SObjectType.SObjectType
      case (Name.SObjectType, Some(false)) => Some(CustomFieldDeclaration(Name.SObjectType, typeName, None))
      case _ => sobjectFields.findField(name, staticContext)
    }
  }

  override def findMethod(name: Name, params: Seq[TypeName], staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Seq[MethodDeclaration] = {
    PlatformTypes.sObjectTypeType.findMethod(name, params, staticContext, verifyContext)
  }
}

final case class SObjectTypeFields(sobjectName: Name, pkg: PackageImpl)
  extends BasicTypeDeclaration(pkg, TypeName.sObjectTypeFields$(TypeName(sobjectName, Nil, Some(TypeName.Schema)))) {

  private lazy val sobjectFields: Map[Name, FieldDeclaration] = {
    TypeRequest(TypeName(sobjectName), pkg, excludeSObjects = false).toOption match {
      case Some(sobject: TypeDeclaration) =>
        sobject.fields.map(field => (field.name, CustomFieldDeclaration(field.name, TypeName.DescribeFieldResult, None))).toMap
      case _ => Map()
    }
  }

  private val ghostedSobjectFields: mutable.Map[Name, FieldDeclaration] = mutable.Map()

  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    // TODO: check staticContext

    sobjectFields.get(name)
      .orElse(ghostedSobjectFields.get(name))
      .orElse({
        val typeName = EncodedName(name).asTypeName
        if (pkg.isGhostedType(typeName)) {
          ghostedSobjectFields.put(name, CustomFieldDeclaration(name, TypeName.DescribeFieldResult, None))
        }
        ghostedSobjectFields.get(name)
      })
  }

  override def findMethod(name: Name, params: Seq[TypeName], staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Seq[MethodDeclaration] = {
    if (staticContext.contains(false)) {
      val method = methodMap.get((name, params.size))
      if (method.nonEmpty)
        return method.toSeq
    }
    super.findMethod(name, params, staticContext, verifyContext)
  }

  lazy val methodMap: Map[(Name, Int), MethodDeclaration] =
    Seq(
      CustomMethodDeclaration(None, Name("getMap"), TypeName.mapOf(TypeName.String, TypeName.SObjectField), Seq()),
    ).map(m => ((m.name, m.parameters.size),m)).toMap
}

final case class SObjectFields(sobjectName: Name, pkg: PackageImpl)
  extends BasicTypeDeclaration(pkg, TypeName.sObjectFields$(TypeName(sobjectName, Nil, Some(TypeName.Schema)))) {

  // Extend SObjectField for when used as return type for lookup SObjectField
  override val superClass: Option[TypeName] = Some(TypeName.SObjectField)
  override lazy val superClassDeclaration: Option[TypeDeclaration] = Some(PlatformTypes.sObjectFieldType)

  private lazy val sobjectFields: Map[Name, FieldDeclaration] = {
    TypeRequest(TypeName(sobjectName), pkg, excludeSObjects = false).toOption match {
      case Some(sobject: TypeDeclaration) =>
        sobject.fields.map(field => (field.name, field.getSObjectField)).toMap
      case _ => Map()
    }
  }

  private val ghostedSobjectFields: mutable.Map[Name, FieldDeclaration] = mutable.Map()

  /* Future: I think this needs to allow both static & non-static access, maybe it could be simplified */
  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {

    // Mimic SObjectType as we proxy for SObjectField
    if (name == Name.SObjectType)
      return Some(CustomFieldDeclaration(name, TypeName.sObjectType$(TypeName(sobjectName, Nil, Some(TypeName.Schema))), None))

    // Provide other fields on the SObject
    sobjectFields.get(name)
      .orElse(ghostedSobjectFields.get(name))
      .orElse({
        val typeName = EncodedName(name).asTypeName
        if (pkg.isGhostedType(typeName)) {
          ghostedSobjectFields.put(name, CustomFieldDeclaration(name, TypeName.SObjectField, None))
        }
        ghostedSobjectFields.get(name)
      })
  }

  override def findMethod(name: Name, params: Seq[TypeName], staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Seq[MethodDeclaration] = {
    PlatformTypes.sObjectFieldType.findMethod(name, params, staticContext, verifyContext)
  }
}

final case class SObjectTypeFieldSets(sobjectName: Name, pkg: PackageImpl)
  extends BasicTypeDeclaration(pkg, TypeName.sObjectTypeFieldSets$(TypeName(sobjectName, Nil, Some(TypeName.Schema)))) {

  private lazy val sobjectFieldSets: Map[Name, FieldDeclaration] = {
    val typeName = TypeName(sobjectName)
    TypeRequest(typeName, pkg, excludeSObjects = false).toOption match {
      case Some(sobject: SObjectDeclaration) =>
        sobject.fieldSets.map(name => (name, CustomFieldDeclaration(name, TypeName.FieldSet, None))).toMap
      case _ => Map()
    }
  }

  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    sobjectFieldSets.get(name)
  }

  override def findMethod(name: Name, params: Seq[TypeName], staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Seq[MethodDeclaration] = {
    PlatformTypes.sObjectTypeFieldSets.findMethod(name, params, staticContext, verifyContext)
  }
}
