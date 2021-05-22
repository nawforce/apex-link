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
import com.nawforce.common.diagnostics.PathLocation
import com.nawforce.common.finding.TypeResolver
import com.nawforce.common.names.TypeNames._
import com.nawforce.common.names.{EncodedName, Name, Names, TypeName, TypeNames}
import com.nawforce.common.org.{Module, OrgImpl}
import com.nawforce.common.path.PathLike
import com.nawforce.common.types.core.{BasicTypeDeclaration, FieldDeclaration, MethodDeclaration, TypeDeclaration}
import com.nawforce.common.types.platform.{PlatformTypeDeclaration, PlatformTypes}
import com.nawforce.common.types.schema
import com.nawforce.common.types.synthetic.{CustomFieldDeclaration, CustomMethodDeclaration}

import scala.collection.mutable

/* Support for Schema.* handling in Apex */
class SchemaManager(module: Module) extends PlatformTypes.PlatformTypeObserver {
  val sobjectTypes: SchemaSObjectType = SchemaSObjectType(module)
  val relatedLists: RelatedLists = new RelatedLists(module)

  PlatformTypes.addLoadingObserver(this)

  override def loaded(td: PlatformTypeDeclaration): Unit = {
    if (td.isSObject) {
      sobjectTypes.createSObjectTypeDeclarations(td.name)
    }
  }
}

/* Relationship field tracker, handles finding related lists */
class RelatedLists(module: Module) {
  private val relationshipFields = mutable
    .Map[TypeName, Seq[(CustomFieldDeclaration, Name, PathLocation)]]() withDefaultValue Seq()

  /* Declare a new relationship field */
  def add(sObject: TypeName,
          relationshipName: Name,
          holdingFieldName: Name,
          holdingSObject: TypeName,
          location: PathLocation): Unit = {
    val encodedName = EncodedName(relationshipName).defaultNamespace(module.namespace).fullName
    val field = CustomFieldDeclaration(encodedName, TypeNames.recordSetOf(holdingSObject), None)
    relationshipFields.put(sObject,
                           (field, holdingFieldName, location) +: relationshipFields(sObject))
  }

  /* Post object loading validation to make sure relationships exist */
  def validate(): Unit = {
    val changedObjects = mutable.Set[TypeDeclaration]()

    // Validate lookups will function
    val sobjects = relationshipFields.keys.toSet
    sobjects.foreach(sobject => {
      val td = TypeResolver(sobject, module, excludeSObjects = false).toOption
      if ((td.isEmpty || !td.exists(_.isSObject)) && !module.isGhostedType(sobject)) {
        relationshipFields(sobject).foreach(field => {
          OrgImpl.logError(field._3,
                           s"Lookup object $sobject does not exist for field '${field._2}'")
        })
      } else if (td.exists(
                   sobject => sobject.isInstanceOf[PlatformTypeDeclaration] && sobject.isSObject)) {
        changedObjects.add(td.get)
      }
      if (td.nonEmpty && sobject != td.get.typeName) {
        relationshipFields.put(td.get.typeName, relationshipFields(sobject))
        relationshipFields.remove(sobject)
      }
    })

    // Wrap any objects with lookups relationships so they are visible
    changedObjects.foreach(td => {
      // TODO: Provide paths
      module.upsertMetadata(
        schema.SObjectDeclaration(PathLike.emptyPaths,
                                  module,
                                  td.typeName,
                                  CustomObjectNature,
                                  Name.emptyNames,
                                  Name.emptyNames,
                                  td.fields,
                                  _isComplete = true))
    })
  }

  /* Find for a relationship field on an SObject*/
  def findField(sobjectType: TypeName, name: Name): Option[FieldDeclaration] = {
    val encodedName = EncodedName(name).defaultNamespace(module.namespace).fullName
    relationshipFields(sobjectType)
      .find(field => field._1.name == encodedName)
      .map(_._1)
      .orElse({
        module.baseModules
          .flatMap(baseModule => {
            baseModule.schema().relatedLists.findField(sobjectType, encodedName)
          })
          .headOption
      })
  }
}

/* Schema.SObjectType implementation */
final case class SchemaSObjectType(module: Module)
    extends BasicTypeDeclaration(PathLike.emptyPaths, module, TypeNames.SObjectType) {
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
    if (module.isGhostedType(typeName)) {
      return Some(createSObjectDescribeField(name, typeName))
    }

    sobjectFields
      .get(name)
      .orElse({
        /* If not yet present check if we should create and cache */
        val td = TypeResolver(TypeName(name), module, excludeSObjects = false).toOption
        if (td.nonEmpty && td.get.superClassDeclaration.exists(superClass =>
              superClass.typeName == TypeNames.SObject)) {
          Some(createSObjectDescribeField(name, td.get.typeName))
        } else {
          None
        }
      })
  }

  override def findMethod(name: Name,
                          params: Array[TypeName],
                          staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Array[MethodDeclaration] = {
    PlatformTypes.sObjectTypeType.findMethod(name, params, staticContext, verifyContext)
  }

  /* Create the describe entries for an SObject, note we are using generics to tunnel the type so that
   * we can support Field & FieldSet access via injecting virtual TypeDeclarations for these.
   */
  private def createSObjectDescribeField(sobjectName: Name,
                                         typeName: TypeName): FieldDeclaration = {
    createSObjectTypeDeclarations(sobjectName)

    val describeField = CustomFieldDeclaration(sobjectName,
                                               TypeNames.describeSObjectResultOf(typeName),
                                               None,
                                               asStatic = true)
    sobjectFields.put(sobjectName, describeField)
    describeField
  }

  /* Inject virtual type declarations for an sobject to override the platform generic versions. This is done
   * dynamically so we don't need to create for every SObject */
  def createSObjectTypeDeclarations(sobjectName: Name): Unit = {
    if (!sobjectTypeDeclarationsCreated.contains(sobjectName)) {
      sobjectTypeDeclarationsCreated.add(sobjectName)
      val fields = SObjectFields(sobjectName, module)
      val typeFields = SObjectTypeFields(sobjectName, module)
      module.upsertMetadata(typeFields)
      module.upsertMetadata(fields)
      module.upsertMetadata(SObjectTypeImpl(sobjectName, fields, module))
      module.upsertMetadata(SObjectTypeFieldSets(sobjectName, module))
      module.upsertMetadata(SObjectFieldRowCause(sobjectName, module))
    }
  }
}

// TODO: Provide paths
final case class SObjectTypeImpl(sobjectName: Name, sobjectFields: SObjectFields, module: Module)
    extends BasicTypeDeclaration(
      PathLike.emptyPaths,
      module,
      TypeNames.sObjectType$(TypeName(sobjectName, Nil, Some(TypeNames.Schema)))) {

  private lazy val fieldField = CustomFieldDeclaration(
    Names.Fields,
    TypeNames.sObjectFields$(TypeName(sobjectName, Nil, Some(TypeNames.Schema))),
    None,
    asStatic = true)

  override val superClass: Option[TypeName] = Some(TypeNames.SObjectType)

  override lazy val superClassDeclaration: Option[TypeDeclaration] = {
    superClass.flatMap(sc => PlatformTypes.get(sc, None).toOption)
  }

  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    (name, staticContext) match {
      case (Names.Fields, Some(false)) => Some(fieldField)
      // Workaround for bug when doing Account.SObjectType.SObjectType
      case (Names.SObjectType, Some(false)) =>
        Some(CustomFieldDeclaration(Names.SObjectType, typeName, None))
      case _ => sobjectFields.findField(name, staticContext)
    }
  }

  override def findMethod(name: Name,
                          params: Array[TypeName],
                          staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Array[MethodDeclaration] = {
    PlatformTypes.sObjectTypeType.findMethod(name, params, staticContext, verifyContext)
  }
}

// TODO: Provide paths
final case class SObjectTypeFields(sobjectName: Name, module: Module)
    extends BasicTypeDeclaration(
      PathLike.emptyPaths,
      module,
      TypeNames.sObjectTypeFields$(TypeName(sobjectName, Nil, Some(TypeNames.Schema)))) {

  private lazy val sobjectFields: Map[Name, FieldDeclaration] = {
    TypeResolver(TypeName(sobjectName), module, excludeSObjects = false).toOption match {
      case Some(sobject: TypeDeclaration) =>
        sobject.fields
          .map(field =>
            (field.name, CustomFieldDeclaration(field.name, TypeNames.DescribeFieldResult, None)))
          .toMap
      case _ => Map()
    }
  }

  private val ghostedSobjectFields: mutable.Map[Name, FieldDeclaration] = mutable.Map()

  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    // TODO: check staticContext

    sobjectFields
      .get(name)
      .orElse(ghostedSobjectFields.get(name))
      .orElse({
        val typeName = EncodedName(name).asTypeName
        if (module.isGhostedType(typeName)) {
          ghostedSobjectFields
            .put(name, CustomFieldDeclaration(name, TypeNames.DescribeFieldResult, None))
        }
        ghostedSobjectFields.get(name)
      })
  }

  override def findMethod(name: Name,
                          params: Array[TypeName],
                          staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Array[MethodDeclaration] = {
    if (staticContext.contains(false)) {
      val method = methodMap.get((name, params.length))
      if (method.nonEmpty)
        return method.toArray
    }
    super.findMethod(name, params, staticContext, verifyContext)
  }

  lazy val methodMap: Map[(Name, Int), MethodDeclaration] =
    Seq(
      CustomMethodDeclaration(None,
                              Name("getMap"),
                              TypeNames.mapOf(TypeNames.String, TypeNames.SObjectField),
                              Array())).map(m => ((m.name, m.parameters.length), m)).toMap
}

// TODO: Provide paths
final case class SObjectFields(sobjectName: Name, module: Module)
    extends BasicTypeDeclaration(
      PathLike.emptyPaths,
      module,
      TypeNames.sObjectFields$(TypeName(sobjectName, Nil, Some(TypeNames.Schema)))) {

  // Extend SObjectField for when used as return type for lookup SObjectField
  override val superClass: Option[TypeName] = Some(TypeNames.SObjectField)
  override lazy val superClassDeclaration: Option[TypeDeclaration] = Some(
    PlatformTypes.sObjectFieldType)

  private lazy val sobjectFields: Map[Name, FieldDeclaration] = {
    val shareTypeName = if (typeName.isShare) Some(typeName) else None
    TypeResolver(TypeName(sobjectName), module, excludeSObjects = false).toOption match {
      case Some(sobject: TypeDeclaration) =>
        sobject.fields.map(field => (field.name, field.getSObjectField(shareTypeName))).toMap
      case _ => Map()
    }
  }

  private val ghostedSobjectFields: mutable.Map[Name, FieldDeclaration] = mutable.Map()

  /* Future: I think this needs to allow both static & non-static access, maybe it could be simplified */
  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {

    // Mimic SObjectType as we proxy for SObjectField
    if (name == Names.SObjectType)
      return Some(
        CustomFieldDeclaration(
          name,
          TypeNames.sObjectType$(TypeName(sobjectName, Nil, Some(TypeNames.Schema))),
          None))

    // Provide other fields on the SObject
    sobjectFields
      .get(name)
      .orElse(ghostedSobjectFields.get(name))
      .orElse({
        val typeName = EncodedName(name).asTypeName
        if (module.isGhostedType(typeName)) {
          ghostedSobjectFields.put(name, CustomFieldDeclaration(name, TypeNames.SObjectField, None))
        }
        ghostedSobjectFields.get(name)
      })
  }

  override def findMethod(name: Name,
                          params: Array[TypeName],
                          staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Array[MethodDeclaration] = {
    PlatformTypes.sObjectFieldType.findMethod(name, params, staticContext, verifyContext)
  }
}

// TODO: Provide paths
final case class SObjectTypeFieldSets(sobjectName: Name, module: Module)
    extends BasicTypeDeclaration(
      PathLike.emptyPaths,
      module,
      TypeNames.sObjectTypeFieldSets$(TypeName(sobjectName, Nil, Some(TypeNames.Schema)))) {

  private lazy val sobjectFieldSets: Map[Name, FieldDeclaration] = {
    val typeName = TypeName(sobjectName)
    TypeResolver(typeName, module, excludeSObjects = false).toOption match {
      case Some(sobject: SObjectDeclaration) =>
        sobject.fieldSets
          .map(name => (name, CustomFieldDeclaration(name, TypeNames.FieldSet, None)))
          .toMap
      case _ => Map()
    }
  }

  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    sobjectFieldSets.get(name)
  }

  override def findMethod(name: Name,
                          params: Array[TypeName],
                          staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Array[MethodDeclaration] = {
    PlatformTypes.sObjectTypeFieldSets.findMethod(name, params, staticContext, verifyContext)
  }
}

final case class SObjectFieldRowCause(sobjectName: Name, module: Module)
    extends BasicTypeDeclaration(
      PathLike.emptyPaths,
      module,
      TypeNames.sObjectFieldRowCause$(TypeName(sobjectName, Nil, Some(TypeNames.Schema)))) {

  private lazy val sharingReasonFields: Map[Name, FieldDeclaration] = {
    // Locate SObject that is holding the sharing reasons
    val sobjectTarget =
      if (sobjectName.toString().endsWith("__Share"))
        sobjectName
      else
        Name(sobjectName.toString().replaceFirst("Share$", ""))
    val typeName = TypeName(sobjectTarget)
    TypeResolver(typeName, module, excludeSObjects = false).toOption match {
      case Some(sobject: SObjectDeclaration) =>
        sobject.sharingReason
          .map(name => (name, CustomFieldDeclaration(name, TypeNames.String, None)))
          .toMap
      case _ => Map()
    }
  }

  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    if (sharingReasonFields.contains(name))
      sharingReasonFields.get(name)
    else
      PlatformTypes.sObjectFieldRowCause$.findField(name, staticContext)
  }

  override def findMethod(name: Name,
                          params: Array[TypeName],
                          staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Array[MethodDeclaration] = {
    PlatformTypes.sObjectFieldRowCause$.findMethod(name, params, staticContext, verifyContext)
  }
}
