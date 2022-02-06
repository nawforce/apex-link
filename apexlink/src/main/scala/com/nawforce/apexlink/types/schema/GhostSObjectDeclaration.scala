/*
 Copyright (c) 2021 Kevin Jones & FinancialForce, All rights reserved.
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
import com.nawforce.apexlink.finding.TypeResolver.TypeCache
import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.org.{Module, SObjectDeployer}
import com.nawforce.apexlink.types.core._
import com.nawforce.pkgforce.modifiers.{GLOBAL_MODIFIER, Modifier}
import com.nawforce.pkgforce.names.{Name, TypeName}

import scala.collection.immutable.ArraySeq
import scala.collection.mutable

final case class GhostSObjectDeclaration(module: Module, _typeName: TypeName)
    extends BasicTypeDeclaration(ArraySeq(), module, _typeName)
    with SObjectLikeDeclaration
    with SObjectFieldFinder
    with SObjectMethods {

  override lazy val isComplete: Boolean = false

  override val modifiers: ArraySeq[Modifier] = ArraySeq(GLOBAL_MODIFIER)

  override val superClass: Option[TypeName] = {
    Some(TypeNames.SObject)
  }

  override lazy val superClassDeclaration: Option[TypeDeclaration] = {
    TypeResolver(superClass.get, this).toOption
  }

  override def gatherDependencies(
    dependents: mutable.Set[TypeId],
    apexOnly: Boolean,
    outerTypesOnly: Boolean,
    typeCache: TypeCache
  ): Unit = {
    // TODO: Should you be able to depend on a ghost?
  }

  override val fields: ArraySeq[FieldDeclaration] = {
    ArraySeq.unsafeWrapArray(
      SObjectDeployer.standardCustomObjectFields.map(f => (f.name, f)).toMap.values.toArray
    )
  }

  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    findFieldSObject(name, staticContext)
  }

  override def findMethod(
    name: Name,
    params: ArraySeq[TypeName],
    staticContext: Option[Boolean],
    verifyContext: VerifyContext
  ): Either[String, MethodDeclaration] = {
    if (staticContext.contains(true)) {
      SObjectDeclaration.sObjectMethodMap.get((name, params.length)) match {
        case None    => Left("No matching method found")
        case Some(m) => Right(m)
      }
    } else {
      defaultFindMethod(name, params, staticContext, verifyContext)
    }
  }
}
