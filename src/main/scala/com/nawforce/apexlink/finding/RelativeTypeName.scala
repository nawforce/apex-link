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

import com.nawforce.apexlink.cst.BlockVerifyContext
import com.nawforce.apexlink.finding.TypeResolver.TypeResponse
import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.types.apex.ApexDeclaration
import com.nawforce.apexlink.types.core.Nature
import com.nawforce.pkgforce.diagnostics.PathLocation
import com.nawforce.pkgforce.names.{Name, TypeName}

import scala.collection.mutable

/** Context to aid RelativeTypeName resolve via the originating ApexDeclaration. This needs freezing after
  * RelativeTypeNames are constructed due to the ApexDeclaration not being constructed until after its constituent
  * parts such as constructors and methods which use RelativeTypeName.
  */
final class RelativeTypeContext {
  private var contextTypeDeclaration: ApexDeclaration = _
  private val typeCache = mutable.Map[TypeName, TypeResponse]()

  /* Freeze the RelativeTypeContext by providing access to the enclosing Apex class. */
  def freeze(typeDeclaration: ApexDeclaration): Unit = {
    assert(contextTypeDeclaration == null)
    contextTypeDeclaration = typeDeclaration
  }

  def outerTypeDeclaration: ApexDeclaration = {
    assert(contextTypeDeclaration != null)
    contextTypeDeclaration
  }

  /* Resolve the passed typeName relative to the context class. */
  def resolve(typeName: TypeName): TypeResponse = {
    assert(contextTypeDeclaration != null)
    typeCache.getOrElseUpdate(typeName, TypeResolver(typeName, contextTypeDeclaration))
  }
}

/* Lazy TypeName resolver for relative types. The package & enclosing (outer) typename are used to allow
 * the relative TypeName to be converted to an absolute form. Assumes outerTypeName can always be resolved
 * against the module!
 */
final case class RelativeTypeName(typeContext: RelativeTypeContext, relativeTypeName: TypeName) {

  def addVar(location: PathLocation, name: Name, context: BlockVerifyContext): Unit = {
    typeRequest match {
      case Some(Right(td)) =>
        context.addVar(name, td)
        context.addDependency(td)
      case _ =>
        context.missingType(location, relativeTypeName)
        context.addVar(name, typeContext.outerTypeDeclaration.module.any)
    }
  }

  // Returns absolute type or may fallback to relative if not found, use typeRequest for error detection
  lazy val typeName: TypeName = {
    // We need the absolute type if we can get it
    typeRequest.map(_.map(_.typeName)) match {
      case Some(Right(tn)) => tn
      case _               => relativeTypeName
    }
  }

  // TypeRequest for the relative type, None if not required
  def typeRequest: Option[TypeResponse] = {
    if (relativeTypeName == TypeNames.Void)
      return None

    // Simulation of a bug, the type resolves against package, ignoring outer, sometimes..
    val result =
      if (relativeTypeName.outer.nonEmpty) {
        TypeResolver(relativeTypeName, typeContext.outerTypeDeclaration.module) match {
          case Right(td) => Right(td)
          case Left(_)   => typeContext.resolve(relativeTypeName)
        }
      } else {
        typeContext.resolve(relativeTypeName)
      }

    if (result.isLeft && typeContext.outerTypeDeclaration.module.isGhostedType(relativeTypeName))
      None
    else
      Some(result)
  }

  // Recover outer types nature, bit of a hack but sometimes useful
  def outerNature: Nature = typeContext.outerTypeDeclaration.nature
}
