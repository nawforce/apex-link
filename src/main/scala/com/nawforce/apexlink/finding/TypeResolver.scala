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
import com.nawforce.apexlink.types.platform.PlatformTypes
import com.nawforce.pkgforce.names.TypeName

object TypeResolver {
  type TypeResponse = Either[TypeError, TypeDeclaration]

  /* Search for non-relative typename from given modules perspective */
  def apply(typeName: TypeName, module: Module): TypeResponse = {
    module.findType(typeName, None)
  }

  /** Search for relative or non-relative typename from perspective of module of 'from', if it has one. */
  def apply(typeName: TypeName, from: TypeDeclaration): TypeResponse = {
    TypeResolver(typeName, from, from.moduleDeclaration)
  }

  /** Search for TypeDeclaration in Local, Module, Dependent Module or Platform Type */
  def apply(typeName: TypeName, from: TypeDeclaration, module: Option[Module]): TypeResponse = {
    // Allow override of platform types in packages to support Schema.SObjectType handling.  This is a hack caused by
    // assuming platform types always live outside the module system and then deciding to inject some within it. It
    // might be fixable by assigning them correct module to them on construction/injection.
    if (from.moduleDeclaration.isEmpty && module.nonEmpty) {
      val tr = module.get.findType(typeName, None)
      if (tr.isRight)
        return tr
    }

    // Search module if we have one, otherwise short-cut to platform types
    from.moduleDeclaration
      .map(module =>
        module.getTypeFor(typeName, from) match {
          case Some(td) => Right(td)
          case None     => Left(MissingType(typeName))
      })
      .getOrElse(PlatformTypes.get(typeName, Some(from)))
  }

}
