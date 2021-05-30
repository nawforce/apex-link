/*
 Copyright (c) 2019 Kevin Jones, All rights reserved.
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
 */

package com.nawforce.apexlink.types.schema

import com.nawforce.apexlink.cst.VerifyContext
import com.nawforce.apexlink.finding.TypeResolver
import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.names.TypeNames._
import com.nawforce.apexlink.org.Module
import com.nawforce.apexlink.types.core.{
  BasicTypeDeclaration,
  FieldDeclaration,
  MethodDeclaration,
  TypeDeclaration
}
import com.nawforce.apexlink.types.platform.{PlatformTypeDeclaration, PlatformTypes}
import com.nawforce.apexlink.types.synthetic.{CustomFieldDeclaration, CustomMethodDeclaration}
import com.nawforce.pkgforce.names.{EncodedName, Name, Names, TypeName}
import com.nawforce.pkgforce.path.PathLike

import scala.collection.mutable

/* Schema.SObjectType implementation */
final case class SchemaSObjectType(module: Module)
    extends BasicTypeDeclaration(PathLike.emptyPaths, module, TypeNames.SObjectType) with PlatformTypes.PlatformTypeObserver {
  private val sobjectFields: mutable.Map[Name, FieldDeclaration] = mutable.Map()
  private val sobjectTypeDeclarationsCreated = mutable.Set[Name]()

  PlatformTypes.addLoadingObserver(this)

  // Callback for loading of Pltform Type that may be SObjects so we can hoist correct describe structure around them
  override def loaded(td: PlatformTypeDeclaration): Unit = {
    if (td.isSObject) {
      createSObjectTypeDeclarations(td.name, hasFieldSets = true)
    }
  }

  /* Allow adding of virtual SObjects such as for shares etc */
  def add(sObject: SObjectDeclaration): Unit = {
    createSObjectDescribeField(sObject.name, sObject.typeName, sObject.sobjectNature == CustomObjectNature)
  }

  /* Find a specific SObject */
  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    if (!staticContext.contains(true))
      return None

    val typeName = EncodedName(name).asTypeName
    if (module.isGhostedType(typeName)) {
      return Some(createSObjectDescribeField(name, typeName, hasFieldsets = true))
    }

    // TODO: Should this cache None
    sobjectFields
      .get(name)
      .orElse({
        /* If not yet present check if we should create and cache */
        val td = TypeResolver(TypeName(name), module, excludeSObjects = false).toOption
        if (td.nonEmpty && td.get.superClassDeclaration.exists(superClass =>
              superClass.typeName == TypeNames.SObject)) {
          Some(createSObjectDescribeField(name, td.get.typeName, hasFieldsets = true))
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
                                         typeName: TypeName, hasFieldsets: Boolean): FieldDeclaration = {
    createSObjectTypeDeclarations(sobjectName, hasFieldsets)

    val describeField = CustomFieldDeclaration(sobjectName,
                                               TypeNames.describeSObjectResultOf(typeName),
                                               None,
                                               asStatic = true)
    sobjectFields.put(sobjectName, describeField)
    describeField
  }

  /* Inject virtual type declarations for an sobject to override the platform generic versions. This is done
   * dynamically so we don't need to create for every SObject */
  def createSObjectTypeDeclarations(sobjectName: Name, hasFieldSets: Boolean): Unit = {
    if (!sobjectTypeDeclarationsCreated.contains(sobjectName)) {
      sobjectTypeDeclarationsCreated.add(sobjectName)

      val typeName = TypeName(sobjectName, Nil, Some(TypeNames.Schema))
      val fields = SObjectFields(sobjectName, module)
      val typeFields = SObjectTypeFields(sobjectName, module)

      module.upsertMetadata(typeFields)
      module.upsertMetadata(fields)
      module.upsertMetadata(SObjectTypeImpl(sobjectName, fields, module))
      if (hasFieldSets)
        module.upsertMetadata(SObjectTypeFieldSets(sobjectName, module))
      if (typeName.isShare)
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

/** Handler for Internal.SObjectTypeFieldSets$<SObject> that provides fieldSet access for custom objects. */
final case class SObjectTypeFieldSets(sobjectName: Name, module: Module)
    extends BasicTypeDeclaration(
      PathLike.emptyPaths,
      module,
      TypeNames.sObjectTypeFieldSets$(TypeName(sobjectName, Nil, Some(TypeNames.Schema)))) {

  // Cache of discovered fieldSets
  private lazy val sobjectFieldSets: Map[Name, FieldDeclaration] = {
    TypeResolver(TypeName(sobjectName), module, excludeSObjects = false).toOption match {
      case Some(sobject: SObjectDeclaration) =>
        sobject.fieldSets
          .map(name => (name, CustomFieldDeclaration(name, TypeNames.FieldSet, None)))
          .toMap
      case _ => Map()
      // TODO: Should this error if type does not support fieldsets?
    }
  }

  /** Intercept field lookup to provide fieldSets on demand. */
  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    sobjectFieldSets.get(name)
  }

  /** Intercept method lookup to provide Map() function. */
  override def findMethod(name: Name,
                          params: Array[TypeName],
                          staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Array[MethodDeclaration] = {
    PlatformTypes.sObjectTypeFieldSets.findMethod(name, params, staticContext, verifyContext)
  }
}

/** Handler for Internal.SObjectFieldRowClause$<SObject> that joins the standard sharing reasons with any sharing
  * reasons that are declared on the the SObject. */
final case class SObjectFieldRowCause(sobjectName: Name, module: Module)
    extends BasicTypeDeclaration(
      PathLike.emptyPaths,
      module,
      TypeNames.sObjectFieldRowCause$(TypeName(sobjectName, Nil, Some(TypeNames.Schema)))) {

  assert(sobjectName.value.endsWith("Share"))

  // Cache of discovered sharing reasons
  private lazy val sharingReasonFields: Map[Name, FieldDeclaration] = {
    // Locate SObject (custom or standard) that is holding the sharing reasons
    val sobjectTarget =
      if (sobjectName.toString().endsWith("__Share"))
        sobjectName
      else
        Name(sobjectName.toString().replaceFirst("Share$", ""))

    // Extract the sharing reasons as fields
    TypeResolver(TypeName(sobjectTarget), module, excludeSObjects = false).toOption match {
      case Some(sobject: SObjectDeclaration) =>
        sobject.sharingReasons
          .map(name => (name, CustomFieldDeclaration(name, TypeNames.String, None)))
          .toMap
      case Some(sobject: PlatformTypeDeclaration) if sobject.isSObject => Map()
      case _                                                           => assert(false); Map()
    }
  }

  /** Intercept field lookup to provide sharing reasons on demand. */
  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    sharingReasonFields
      .get(name)
      .orElse(PlatformTypes.sObjectFieldRowCause$.findField(name, staticContext))
  }
}
