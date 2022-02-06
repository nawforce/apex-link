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

import scala.collection.immutable.ArraySeq
import scala.collection.mutable

/** Schema.SObjectType implementation. By its nature this is a container for information about SObjects so it is also
  * used to initialise supporting types needed for SObjects. Salesforce have hacked their Apex parser to have explicit
  * knowledge of expressions involving SObjects but I could not bring myself to do something so ungodly. Instead, we
  * use generics and pass the SObject TypeName as a type arguments. There is no automatic way to create these types
  * so either it is done as a side effect of loading SObject metadata or on-demand.
  *
  * The way generics are used here can be a bit confusing as there are few hard dependencies between the classes.
  * What generally happens is that during expression evaluation, a field type name will be given as one of the generic
  * types with the type argument set to a specific SObject, that type name will then be resolved to one of the handlers
  * below that has been created specifically for dealing with that aspect of the SObject reflective access.
  */
final case class SchemaSObjectType(module: Module)
    extends BasicTypeDeclaration(PathLike.emptyPaths, module, TypeNames.SObjectType)
    with PlatformTypes.PlatformTypeObserver {

  /** Cache of SObject accessible via SObjectType name */
  private val sobjectFields: mutable.Map[Name, Option[FieldDeclaration]] = mutable.Map()

  /* Create SObjectFields$<SObject>, to support assignability checks with SObjectField */
  module.upsertMetadata(SObjectFields(TypeNames.SObject, module))

  /** Callback for loading of Platform Type that may be SObjects so we can hoist correct describe structure around
    * them.
    */
  override def loaded(td: PlatformTypeDeclaration): Unit = {
    if (td.isSObject) {
      add(td.name, hasFieldSets = true)
    }
  }

  /** Add an SObject, this will create supporting types needed for reflective access to this SObject. */
  def add(sobjectName: Name, hasFieldSets: Boolean): FieldDeclaration = {

    // Handlers for Schema.SObjectType.<name>.Fields, Schema.<name>.Fields & Schema.<name>.SObjectType
    val typeName = TypeName(sobjectName, Nil, Some(TypeNames.Schema))
    val fields   = SObjectFields(typeName, module)
    module.upsertMetadata(SObjectTypeFields(sobjectName, module))
    module.upsertMetadata(fields)
    module.upsertMetadata(SObjectTypeImpl(sobjectName, fields, module))

    // Optional handlers for fieldSets & shares
    val sobjectTypeName = TypeName(sobjectName, Nil, Some(TypeNames.Schema))
    if (hasFieldSets)
      module.upsertMetadata(SObjectTypeFieldSets(sobjectName, module))
    if (sobjectTypeName.isShare)
      module.upsertMetadata(SObjectFieldRowCause(sobjectName, module))

    // The Schema.SObjectType.<name> field that anchors all this
    val describeField =
      CustomFieldDeclaration(
        sobjectName,
        TypeNames.describeSObjectResultOf(sobjectTypeName),
        None,
        asStatic = true
      )
    sobjectFields.put(sobjectName, Some(describeField))
    describeField
  }

  def remove(sobjectName: Name): Unit = {
    module.removeMetadata(
      TypeNames.sObjectFields$(TypeName(sobjectName, Nil, Some(TypeNames.Schema)))
    )
    module.removeMetadata(
      TypeNames.sObjectTypeFields$(TypeName(sobjectName, Nil, Some(TypeNames.Schema)))
    )
    module.removeMetadata(
      TypeNames.sObjectType$(TypeName(sobjectName, Nil, Some(TypeNames.Schema)))
    )
    module.removeMetadata(
      TypeNames.sObjectTypeFieldSets$(TypeName(sobjectName, Nil, Some(TypeNames.Schema)))
    )
    module.removeMetadata(
      TypeNames.sObjectFieldRowCause$(TypeName(sobjectName, Nil, Some(TypeNames.Schema)))
    )
    sobjectFields.remove(sobjectName)
  }

  /* Find a specific SObject */
  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    if (!staticContext.contains(true))
      return None

    val typeName = EncodedName(name).asTypeName
    if (module.isGhostedType(typeName)) {
      return Some(add(name, hasFieldSets = true))
    }

    sobjectFields
      .getOrElseUpdate(
        name, {
          // This handles cases where describe is used on a Platform SObject without the SObject first being used
          // directly in Apex which would normally cause the describe handler to be created.
          val td = TypeResolver(TypeName(name), module).toOption
          if (td.nonEmpty && td.get.isSObject) {
            Some(add(name, hasFieldSets = true))
          } else {
            None
          }
        }
      )
  }

  override def findMethod(
    name: Name,
    params: ArraySeq[TypeName],
    staticContext: Option[Boolean],
    verifyContext: VerifyContext
  ): Either[String, MethodDeclaration] = {
    PlatformTypes.sObjectTypeType.findMethod(name, params, staticContext, verifyContext)
  }
}

/** Handler for Internal.SObjectType$<SObject> that provides reflective access for custom objects via expressions
  * starting with <name>.SObjectType.
  */
final case class SObjectTypeImpl(sobjectName: Name, sobjectFields: SObjectFields, module: Module)
    extends BasicTypeDeclaration(
      PathLike.emptyPaths,
      module,
      TypeNames.sObjectType$(TypeName(sobjectName, Nil, Some(TypeNames.Schema)))
    ) {

  private lazy val fieldField = CustomFieldDeclaration(
    Names.Fields,
    TypeNames.sObjectFields$(TypeName(sobjectName, Nil, Some(TypeNames.Schema))),
    None,
    asStatic = true
  )

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

  override def findMethod(
    name: Name,
    params: ArraySeq[TypeName],
    staticContext: Option[Boolean],
    verifyContext: VerifyContext
  ): Either[String, MethodDeclaration] = {
    PlatformTypes.sObjectTypeType.findMethod(name, params, staticContext, verifyContext)
  }
}

/** Handler for Internal.SObjectTypeFields$<SObject> that provides fields access for custom objects via expressions
  * of the form Schema.SObjectType.<name>.Fields.
  */
final case class SObjectTypeFields(sobjectName: Name, module: Module)
    extends BasicTypeDeclaration(
      PathLike.emptyPaths,
      module,
      TypeNames.sObjectTypeFields$(TypeName(sobjectName, Nil, Some(TypeNames.Schema)))
    ) {

  private lazy val sobjectFields: Map[Name, FieldDeclaration] = {
    TypeResolver(TypeName(sobjectName), module).toOption match {
      case Some(sobject: TypeDeclaration) =>
        sobject.fields
          .map(
            field =>
              (field.name, CustomFieldDeclaration(field.name, TypeNames.DescribeFieldResult, None))
          )
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

  override def findMethod(
    name: Name,
    params: ArraySeq[TypeName],
    staticContext: Option[Boolean],
    verifyContext: VerifyContext
  ): Either[String, MethodDeclaration] = {
    if (staticContext.contains(false)) {
      val method = methodMap.get((name, params.length))
      if (method.nonEmpty)
        return Right(method.get)
    }
    super.findMethod(name, params, staticContext, verifyContext)
  }

  lazy val methodMap: Map[(Name, Int), MethodDeclaration] =
    Seq(
      CustomMethodDeclaration(
        com.nawforce.pkgforce.path.Location.empty,
        Name("getMap"),
        TypeNames.mapOf(TypeNames.String, TypeNames.SObjectField),
        CustomMethodDeclaration.emptyParameters
      )
    )
      .map(m => ((m.name, m.parameters.length), m))
      .toMap
}

/** Handler for Internal.SObjectFields$<SObject> that provides fields access for custom objects via expressions
  * of the form Schema.<name>.Fields.
  */
final case class SObjectFields(baseType: TypeName, module: Module)
    extends BasicTypeDeclaration(PathLike.emptyPaths, module, TypeNames.sObjectFields$(baseType)) {

  // Extend SObjectField for when used as return type for lookup SObjectField
  override val superClass: Option[TypeName] = Some(TypeNames.SObjectField)
  override lazy val superClassDeclaration: Option[TypeDeclaration] = Some(
    PlatformTypes.sObjectFieldType
  )

  private lazy val sobjectFields: Map[Name, FieldDeclaration] = {
    val shareTypeName = if (typeName.isShare) Some(typeName) else None
    TypeResolver(baseType, module).toOption match {
      case Some(sobject: TypeDeclaration) =>
        sobject.fields
          .map(field => (field.name, field.getSObjectStaticField(shareTypeName, Some(module))))
          .toMap
      case _ => Map()
    }
  }

  private val ghostedSobjectFields: mutable.Map[Name, FieldDeclaration] = mutable.Map()

  /* Future: I think this needs to allow both static & non-static access, maybe it could be simplified */
  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {

    // Mimic SObjectType as we proxy for SObjectField
    // Future: Check if this is needed as its not hit bu unit tests
    if (name == Names.SObjectType)
      return Some(CustomFieldDeclaration(name, TypeNames.sObjectType$(baseType), None))

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

  override def findMethod(
    name: Name,
    params: ArraySeq[TypeName],
    staticContext: Option[Boolean],
    verifyContext: VerifyContext
  ): Either[String, MethodDeclaration] = {
    PlatformTypes.sObjectFieldType.findMethod(name, params, staticContext, verifyContext)
  }
}

/** Handler for Internal.SObjectTypeFieldSets$<SObject> that provides fieldSet access for custom objects */
final case class SObjectTypeFieldSets(sobjectName: Name, module: Module)
    extends BasicTypeDeclaration(
      PathLike.emptyPaths,
      module,
      TypeNames.sObjectTypeFieldSets$(TypeName(sobjectName, Nil, Some(TypeNames.Schema)))
    ) {

  // Cache of discovered fieldSets
  private lazy val sobjectFieldSets: Map[Name, FieldDeclaration] = {
    TypeResolver(TypeName(sobjectName), module).toOption match {
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
    // Name might be namespaced already, although that is unusual
    val encodedName = EncodedName(name)
    if (encodedName.namespace.isEmpty || encodedName.namespace == module.namespace) {
      sobjectFieldSets.get(encodedName.name)
    } else if (module.isGhostedFieldName(name)) {
      Some(CustomFieldDeclaration(name, TypeNames.FieldSet, None))
    } else {
      None
    }
  }

  /** Intercept method lookup to provide Map() function. */
  override def findMethod(
    name: Name,
    params: ArraySeq[TypeName],
    staticContext: Option[Boolean],
    verifyContext: VerifyContext
  ): Either[String, MethodDeclaration] = {
    PlatformTypes.sObjectTypeFieldSets.findMethod(name, params, staticContext, verifyContext)
  }
}

/** Handler for Internal.SObjectFieldRowClause$<SObject> that joins the standard sharing reasons with any sharing
  * reasons that are declared on the the SObject.
  */
final case class SObjectFieldRowCause(sobjectName: Name, module: Module)
    extends BasicTypeDeclaration(
      PathLike.emptyPaths,
      module,
      TypeNames.sObjectFieldRowCause$(TypeName(sobjectName, Nil, Some(TypeNames.Schema)))
    ) {

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
    TypeResolver(TypeName(sobjectTarget), module).toOption match {
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
