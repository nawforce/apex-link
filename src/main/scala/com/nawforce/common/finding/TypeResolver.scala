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
package com.nawforce.common.finding

import com.nawforce.common.api.TypeName
import com.nawforce.common.org.PackageImpl
import com.nawforce.common.types.core.TypeDeclaration
import com.nawforce.common.types.platform.PlatformTypes

import scala.collection.mutable

/** Helper for resolving types based on different coordinates.
  *
  * To speed repeated searches caches are used so TypeResolvers should not be persisted across API calls
  * that may mutate Org/Package state.
  *
  * Note: Platform TypeDeclarations are not packaged & not all code (triggers, anon) comes from TypeDeclarations,
  * unmanaged code is part of a special package declaration with no namespace.
  **/
class TypeResolver {
  type TypeResponse = Either[TypeError, TypeDeclaration]
  private lazy val typeCache = mutable.Map[(TypeName, TypeDeclaration), Option[TypeDeclaration]]()

  /** Search for a Package, Dependent Package or Platform Type */
  def find(typeName: TypeName, pkg: PackageImpl, excludeSObjects: Boolean): TypeResponse = {
    pkg.getType(typeName, None, excludeSObjects)
  }

  /** Search for TypeDeclaration Local, Package, Dependent Package or Platform Type */
  def find(typeName: TypeName, from: TypeDeclaration, excludeSObjects: Boolean): TypeResponse = {
    val pkg = from.packageDeclaration
    if (pkg.nonEmpty) {
      getPackageTypeFor(pkg.get, typeName, from) match {
        case Some(td) => Right(td)
        case None => Left(MissingType(typeName))
      }
    } else {
      PlatformTypes.get(typeName, Some(from), excludeSObjects)
    }
  }

  /** Search is selected based on what information is provided */
  def find(typeName: TypeName, from: Option[TypeDeclaration], pkg: Option[PackageImpl],
            excludeSObjects: Boolean): TypeResponse = {
    if (from.nonEmpty) {
      // Allow override of platform types in packages to support Schema.SObjectType handling
      if (from.get.packageDeclaration.isEmpty && pkg.nonEmpty) {
        val tr = find(typeName, pkg.get, excludeSObjects)
        if (tr.isRight)
          return tr
      }
      find(typeName, from.get, excludeSObjects)
    } else if (pkg.nonEmpty)
      find(typeName, pkg.get, excludeSObjects)
    else
      PlatformTypes.get(typeName, None, excludeSObjects)
  }

  private def getPackageTypeFor(pkg: PackageImpl, typeName: TypeName, from: TypeDeclaration): Option[TypeDeclaration] = {
    typeCache.getOrElseUpdate((typeName, from), {
      val td = pkg.getTypeFor(typeName, from)
      typeCache.put((typeName, from), td)
      td
    })
  }
}

object TypeResolver {
  type TypeResponse = Either[TypeError, TypeDeclaration]

  def apply(typeName: TypeName, pkg: PackageImpl, excludeSObjects: Boolean): TypeResponse = {
    new TypeResolver().find(typeName, pkg, excludeSObjects)
  }

  def apply(typeName: TypeName, from: TypeDeclaration, excludeSObjects: Boolean): TypeResponse = {
    new TypeResolver().find(typeName, from, excludeSObjects)
  }

  def apply(typeName: TypeName, from: Option[TypeDeclaration], pkg: Option[PackageImpl],
            excludeSObjects: Boolean): TypeResponse = {
    new TypeResolver().find(typeName, from, pkg, excludeSObjects)
  }
}
