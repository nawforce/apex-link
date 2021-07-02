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
import com.nawforce.apexlink.types.apex.{ApexClassDeclaration, FullDeclaration}
import com.nawforce.apexlink.types.core.{Nature, TypeDeclaration}
import com.nawforce.pkgforce.diagnostics.PathLocation
import com.nawforce.pkgforce.names.{Name, TypeName}

import scala.collection.mutable

/** Context to aid RelativeTypeName resolve via the originating ApexDeclaration. This needs freezing after
  * RelativeTypeNames are constructed due to the FullDeclaration not being constructed until after its constituent
  * parts, such as constructors and methods which use RelativeTypeName.
  */
final class RelativeTypeContext {
  var contextTypeDeclaration: FullDeclaration = _
  var deepHash: Int = _
  private val typeCache = mutable.Map[TypeName, TypeDeclaration]()

  /** Freeze the RelativeTypeContext by providing access to the enclosing Apex class. */
  def freeze(typeDeclaration: FullDeclaration): Unit = {
    assert(contextTypeDeclaration == null)
    contextTypeDeclaration = typeDeclaration
    deepHash = typeDeclaration.deepHash
  }

  /** Reset internal caching if the deep hash of the associated type changes so that relative names are resolve
    * accurately to any updated type. */
  def resetIfInvalid(): Unit = {
    val newHash = deepHash
    if (newHash != deepHash) {
      typeCache.clear()
      deepHash = newHash
    }
  }

  /** Resolve the passed typeName relative to the context class. */
  def resolve(typeName: TypeName): TypeResponse = {
    val cached = typeCache.get(typeName)
    if (cached.nonEmpty) return Right(cached.get)

    val response =
      // Workaround a stupid bug where the wrong type is returned sometimes...
      if (typeName.outer.nonEmpty) {
        TypeResolver(typeName, contextTypeDeclaration.module) match {
          case Right(td) => Right(td)
          case Left(_)   => TypeResolver(typeName, contextTypeDeclaration)
        }
      } else {
        TypeResolver(typeName, contextTypeDeclaration)
      }

    // It's only safe to cache relative types as we can't invalidate when any type at all changes
    // We can detect relative types by the difference in type name & and that types being apex defined
    response match {
      case Right(td: ApexClassDeclaration) if td.typeName != typeName =>
        typeCache.put(typeName, td)
      case _ => ()
    }

    response
  }
}

/* Lazy TypeName resolver for relative types. The package & enclosing (outer) typename are used to allow
 * the relative TypeName to be converted to an absolute form. Assumes outerTypeName can always be resolved
 * against the module!
 */
final case class RelativeTypeName(typeContext: RelativeTypeContext, relativeTypeName: TypeName) {

  /** Is this the magical void? */
  def isVoid: Boolean = {
    relativeTypeName == TypeNames.Void
  }

  /* Obtain raw resolve response for the relative type, beware there is no type for Void */
  def resolve: TypeResponse = {
    typeContext.resolve(relativeTypeName)
  }

  /* Obtain absolute type or fallback to relative if not found. */
  def typeName: TypeName = {
    if (isVoid) return TypeNames.Void
    typeContext.resolve(relativeTypeName) match {
      case Right(td) => td.typeName
      case _         => relativeTypeName
    }
  }

  /** Helper for introducing formal parameters into a block context. */
  def addVar(location: PathLocation, name: Name, context: BlockVerifyContext): Unit = {
    typeContext.resolve(relativeTypeName) match {
      case Right(td) =>
        context.addVar(name, td)
        context.addDependency(td)
      case _ =>
        context.missingType(location, relativeTypeName)
        context.addVar(name, typeContext.contextTypeDeclaration.module.any)
    }
  }

  /** Helper for obtaining the nature of the outer type. */
  def outerNature: Nature = typeContext.contextTypeDeclaration.nature
}
