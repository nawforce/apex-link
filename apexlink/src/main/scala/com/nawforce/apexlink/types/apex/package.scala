/*
 Copyright (c) 2020 Kevin Jones, All rights reserved.
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

package com.nawforce.apexlink.types

import com.nawforce.apexlink.api._
import com.nawforce.apexlink.names.TypeNames._
import com.nawforce.pkgforce.memory.InternCache
import com.nawforce.pkgforce.names.{Names, TypeIdentifier}

package object apex {

  /** Interning support for DependentSummary, used to reduce memory load from cached data. */
  implicit class DependentSummaryOps(dependency: DependentSummary) {

    def intern: DependentSummary = {
      dependency match {
        case d: TypeDependentSummary   => intern(d)
        case d: FieldDependentSummary  => intern(d)
        case d: MethodDependentSummary => intern(d)
      }
    }

    private def intern(dependency: TypeDependentSummary): TypeDependentSummary = {
      TypeDependentSummary(dependency.typeId.intern, dependency.sourceHash)
    }

    private def intern(dependency: FieldDependentSummary): FieldDependentSummary = {
      FieldDependentSummary(dependency.typeId.intern, Names(dependency.name).value)
    }

    private def intern(dependency: MethodDependentSummary): MethodDependentSummary = {
      MethodDependentSummary(
        dependency.typeId.intern,
        Names(dependency.name).value,
        dependency.parameterTypes.map(_.intern)
      )
    }
  }

  /** Interning support for TypeIdentifier, used to reduce memory load from cached data. */
  implicit class TypeIdentifierOps(typeIdentifier: TypeIdentifier) {
    def intern: TypeIdentifier = {
      TypeIdentifierOps.intern(
        TypeIdentifier(
          typeIdentifier.namespace.map(n => Names(n.value)),
          typeIdentifier.typeName.intern
        )
      )
    }
  }

  object TypeIdentifierOps extends InternCache[TypeIdentifier]

}
