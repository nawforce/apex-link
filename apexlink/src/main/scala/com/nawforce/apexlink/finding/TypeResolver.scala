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

package com.nawforce.apexlink.finding

import com.nawforce.apexlink.org.Module
import com.nawforce.apexlink.types.core.TypeDeclaration
import com.nawforce.apexlink.types.platform.{PlatformTypeDeclaration, PlatformTypes}
import com.nawforce.apexlink.types.schema.{PlatformObjectNature, SObjectDeclaration}
import com.nawforce.pkgforce.names.TypeName

import scala.collection.immutable.ArraySeq
import scala.collection.mutable

/** Various forms of TypeDeclaration searches. There are broadly three types of searches used, a local typename can
  * be resolved  given a starting declaration to search from. Absolute type names can be resolved against module types,
  * either in a passed module or a dependent module of that. And absolute types names may also match against platform
  * defined types.
  *
  * Platform types also support a form of relative type name resolution via allowing the 'System' and 'Schema'
  * namespaces to be dropped, this is a different mechanism than is used for local searching for type declarations so
  * we can mostly ignore it.
  */
object TypeResolver {
  type TypeResponse = Either[TypeError, TypeDeclaration]
  type TypeCache    = mutable.HashMap[(TypeName, Module), TypeResponse]

  /* Search for TypeDeclaration from a absolute typename from given modules perspective. */
  def apply(typeName: TypeName, module: Module): TypeResponse = {
    sobjectIntercept(Some(module)) {
      module.findType(typeName)
    }
  }

  /** Search for TypeDeclaration from local or absolute typename from perspective of the module of 'from', if
    * it has one.
    */
  def apply(typeName: TypeName, from: TypeDeclaration): TypeResponse = {
    TypeResolver(typeName, from, from.moduleDeclaration)
  }

  /** Search for TypeDeclaration in Local local to 'from', Module, Dependent Module or Platform Type . */
  def apply(typeName: TypeName, from: TypeDeclaration, module: Option[Module]): TypeResponse = {
    // Allow override of platform types in modules to support Schema.SObjectType handling.  This is a hack caused by
    // assuming platform types always live outside the module system and then deciding to inject some within it. It
    // might be fixable by assigning them to the correct module on construction/injection.
    if (from.moduleDeclaration.isEmpty && module.nonEmpty) {
      val tr = module.get.findType(typeName)
      if (tr.isRight)
        return tr
    }

    // Search module if we have one, otherwise short-cut to platform types
    sobjectIntercept(module) {
      from.moduleDeclaration
        .map(
          module =>
            module.getTypeFor(typeName, from) match {
              case Some(td) => Right(td)
              case None     => Left(MissingType(typeName))
            }
        )
        .getOrElse {
          platformType(typeName, from)
        }
    }
  }

  /** Search for platform TypeDeclaration from a local or absolute typename. */
  def platformType(typeName: TypeName, from: TypeDeclaration): TypeResponse = {
    sobjectIntercept(from.moduleDeclaration) {
      PlatformTypes.get(typeName, Some(from))
    }
  }

  /** Search for platform TypeDeclaration from a absolute typename. */
  def platformTypeOnly(typeName: TypeName, module: Module): TypeResponse = {
    sobjectIntercept(Some(module)) {
      PlatformTypes.get(typeName, None)
    }
  }

  /** Hook to upgrade a SObject defined as a platform type into an SObject for the module. This allows us to support
    * dependencies on Standard SObjects but also allows for module specific versions to be managed.
    */
  private def sobjectIntercept(module: Option[Module])(op: => TypeResponse): TypeResponse = {
    val result = op
    module
      .map(m => {
        result match {
          case Right(base) if base.isSObject && base.isInstanceOf[PlatformTypeDeclaration] =>
            val td = new SObjectDeclaration(
              Array(),
              m,
              base.typeName,
              PlatformObjectNature,
              ArraySeq(),
              ArraySeq(),
              base.fields,
              _isComplete = true
            )
            m.upsertMetadata(td)
            Right(td)
          case x => x
        }
      })
      .getOrElse(result)
  }
}
