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
import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.org.Module
import com.nawforce.apexlink.types.core.{
  BasicTypeDeclaration,
  FieldDeclaration,
  MethodDeclaration,
  TypeDeclaration
}
import com.nawforce.apexlink.types.platform.PlatformTypes
import com.nawforce.apexlink.types.synthetic.{CustomMethodDeclaration, CustomParameterDeclaration}
import com.nawforce.pkgforce.modifiers._
import com.nawforce.pkgforce.names.{Name, TypeName}
import com.nawforce.pkgforce.path.PathLike

sealed abstract class SObjectNature(val nature: String) {
  override def toString: String = nature
}
case object ListCustomSettingNature extends SObjectNature("List")
case object HierarchyCustomSettingsNature extends SObjectNature("Hierarchy")
case object CustomObjectNature extends SObjectNature("CustomObject")
case object CustomMetadataNature extends SObjectNature("CustomMetadata")
case object BigObjectNature extends SObjectNature("BigObject")
case object PlatformObjectNature extends SObjectNature("PlatformObject")
case object PlatformEventNature extends SObjectNature("PlatformEvent")

final case class SObjectDeclaration(_paths: Array[PathLike],
                                    module: Module,
                                    _typeName: TypeName,
                                    sobjectNature: SObjectNature,
                                    fieldSets: Array[Name],
                                    sharingReasons: Array[Name],
                                    baseFields: Array[FieldDeclaration],
                                    _isComplete: Boolean)
    extends BasicTypeDeclaration(_paths, module, _typeName) {

  override lazy val isComplete: Boolean = _isComplete

  override val modifiers: Array[Modifier] = SObjectDeclaration.globalModifiers

  override val superClass: Option[TypeName] = {
    Some(TypeNames.SObject)
  }

  override lazy val superClassDeclaration: Option[TypeDeclaration] = {
    module.getTypeFor(superClass.get, this)
  }

  override val fields: Array[FieldDeclaration] = {
    if (sobjectNature == CustomObjectNature) {
      (PlatformTypes.customSObject.fields ++ baseFields).map(f => (f.name, f)).toMap.values.toArray
    } else {
      baseFields
    }
  }

  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    super.findFieldSObject(name, staticContext)
  }

  override def findMethod(name: Name,
                          params: Array[TypeName],
                          staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Array[MethodDeclaration] = {
    if (staticContext.contains(true)) {
      val customMethods = sobjectNature match {
        case HierarchyCustomSettingsNature => hierarchyCustomSettingsMethods
        case ListCustomSettingNature       => listCustomSettingsMethods
        case _                             => SObjectDeclaration.sObjectMethodMap
      }
      val customMethod = customMethods.get((name, params.length))
      if (customMethod.nonEmpty)
        return customMethod.toArray
    }
    defaultFindMethod(name, params, staticContext, verifyContext)
  }

  def defaultFindMethod(name: Name,
                        params: Array[TypeName],
                        staticContext: Option[Boolean],
                        verifyContext: VerifyContext): Array[MethodDeclaration] = {
    val clone = cloneMethods.get((name, params.length, staticContext.contains(true)))
    if (clone.nonEmpty)
      clone.toArray
    else
      PlatformTypes.sObjectType.findMethod(name, params, staticContext, verifyContext)
  }

  private lazy val cloneMethods: Map[(Name, Int, Boolean), MethodDeclaration] = {
    val preserveId = CustomParameterDeclaration(Name("preserveId"), TypeNames.Boolean)
    val isDeepClone = CustomParameterDeclaration(Name("isDeepClone"), TypeNames.Boolean)
    val preserveReadOnlyTimestamps =
      CustomParameterDeclaration(Name("preserveReadOnlyTimestamps"), TypeNames.Boolean)
    val preserveAutonumber =
      CustomParameterDeclaration(Name("preserveAutonumber"), TypeNames.Boolean)
    Seq(CustomMethodDeclaration(None, Name("clone"), typeName, Array()),
        CustomMethodDeclaration(None, Name("clone"), typeName, Array(preserveId)),
        CustomMethodDeclaration(None, Name("clone"), typeName, Array(preserveId, isDeepClone)),
        CustomMethodDeclaration(None,
                                Name("clone"),
                                typeName,
                                Array(preserveId, isDeepClone, preserveReadOnlyTimestamps)),
        CustomMethodDeclaration(
          None,
          Name("clone"),
          typeName,
          Array(preserveId, isDeepClone, preserveReadOnlyTimestamps, preserveAutonumber)))
      .map(m => ((m.name, m.parameters.length, m.isStatic), m))
      .toMap
  }

  private lazy val hierarchyCustomSettingsMethods: Map[(Name, Int), MethodDeclaration] =
    Seq(CustomMethodDeclaration(None, Name("getInstance"), typeName, Array()),
        CustomMethodDeclaration(None,
                                Name("getInstance"),
                                typeName,
                                Array(CustomParameterDeclaration(Name("Id"), TypeNames.IdType))),
        CustomMethodDeclaration(None, Name("getOrgDefaults"), typeName, Array()),
        CustomMethodDeclaration(None,
                                Name("getValues"),
                                typeName,
                                Array(CustomParameterDeclaration(Name("Id"), TypeNames.IdType))),
    ).map(m => ((m.name, m.parameters.length), m)).toMap

  private lazy val listCustomSettingsMethods: Map[(Name, Int), MethodDeclaration] =
    Seq(
      CustomMethodDeclaration(None,
                              Name("getAll"),
                              TypeNames.mapOf(TypeNames.String, typeName),
                              Array()),
      CustomMethodDeclaration(None, Name("getInstance"), typeName, Array()),
      CustomMethodDeclaration(None,
                              Name("getInstance"),
                              typeName,
                              Array(CustomParameterDeclaration(Name("Name"), TypeNames.String))),
      CustomMethodDeclaration(None,
                              Name("getValues"),
                              typeName,
                              Array(CustomParameterDeclaration(Name("Name"), TypeNames.String))),
    ).map(m => ((m.name, m.parameters.length), m)).toMap
}

object SObjectDeclaration {
  val globalModifiers: Array[Modifier] = Array(GLOBAL_MODIFIER)

  private lazy val sObjectMethodMap: Map[(Name, Int), MethodDeclaration] =
    PlatformTypes.sObjectType.methods.map(m => ((m.name, m.parameters.length), m)).toMap
}
