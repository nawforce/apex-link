/*
 Copyright (c) 2021 Kevin Jones, All rights reserved.
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
import com.nawforce.apexlink.org.Module
import com.nawforce.apexlink.types.core.{BasicTypeDeclaration, FieldDeclaration, MethodDeclaration, TypeDeclaration, TypeId}
import com.nawforce.apexlink.types.platform.PlatformTypes
import com.nawforce.apexlink.types.synthetic.{CustomMethodDeclaration, CustomParameterDeclaration}
import com.nawforce.pkgforce.modifiers.{GLOBAL_MODIFIER, Modifier}
import com.nawforce.pkgforce.names.{Name, TypeName}

import scala.collection.mutable

final case class GhostSObjectDeclaration(module: Module, _typeName: TypeName)
    extends BasicTypeDeclaration(Array(), module, _typeName) with SObjectLikeDeclaration {

  override lazy val isComplete: Boolean = false

  override val modifiers: Array[Modifier] = Array(GLOBAL_MODIFIER)

  override val superClass: Option[TypeName] = {
    Some(TypeNames.SObject)
  }

  override lazy val superClassDeclaration: Option[TypeDeclaration] = {
    TypeResolver(superClass.get, this).toOption
  }

  override def collectDependenciesByTypeName(dependents: mutable.Set[TypeId]): Unit = {
    // TODO: Should you be able to depend on a ghost?
  }

  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    None
  }

  override def findMethod(name: Name,
                          params: Array[TypeName],
                          staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Option[MethodDeclaration] = {
    if (staticContext.contains(true)) {
      SObjectDeclaration.sObjectMethodMap.get((name, params.length))
    } else {
      defaultFindMethod(name, params, staticContext, verifyContext)
    }
  }

  def defaultFindMethod(name: Name,
                        params: Array[TypeName],
                        staticContext: Option[Boolean],
                        verifyContext: VerifyContext): Option[MethodDeclaration] = {
    val clone = cloneMethods.get((name, params.length, staticContext.contains(true)))
    if (clone.nonEmpty)
      clone
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
        CustomMethodDeclaration(None,
                                Name("clone"),
                                typeName,
                                Array(preserveId, isDeepClone, preserveReadOnlyTimestamps, preserveAutonumber)))
      .map(m => ((m.name, m.parameters.length, m.isStatic), m))
      .toMap
  }
}
