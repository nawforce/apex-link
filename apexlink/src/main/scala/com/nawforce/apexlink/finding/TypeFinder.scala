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

import com.nawforce.apexlink.names.TypeNames.TypeNameUtils
import com.nawforce.apexlink.org.Module
import com.nawforce.apexlink.types.core.TypeDeclaration
import com.nawforce.pkgforce.names.{DotName, TypeName}

/** Helper that implements local type searching, extracted out as logic is a bit involved. */
trait TypeFinder {
  this: Module =>

  /** Find a type relative to a starting type from a local or global name. */
  def getTypeFor(typeName: TypeName, from: TypeDeclaration): Option[TypeDeclaration] = {
    getLocalTypeFor(typeName, from).orElse(this.findType(typeName, from).toOption)
  }

  /** Find a type relative to a starting type assuming a local name. */
  def getLocalTypeFor(typeName: TypeName, from: TypeDeclaration): Option[TypeDeclaration] = {
    // Only non-generics can be locally defined, currently
    if (typeName.params.isEmpty)
      findLocalTypeFor(typeName.asDotName, from)
    else
      None
  }

  private def findLocalTypeFor(dotName: DotName, from: TypeDeclaration): Option[TypeDeclaration] = {
    val matched =
      if (dotName.firstName == from.name) {
        if (dotName.isCompound)
          findLocalTypeFor(dotName.tail, from)
        else
          Some(from)
      } else {
        None
      }

    if (matched.nonEmpty)
      return matched

    getNestedType(dotName, from)
      .orElse(
        getFromOuterType(dotName, from)
          .orElse(getFromSuperType(dotName, from))
      )
  }

  private def getNestedType(dotName: DotName, from: TypeDeclaration): Option[TypeDeclaration] = {
    if (dotName.isCompound) {
      None
    } else {
      from.findNestedType(dotName.names.head)
    }
  }

  private def getFromSuperType(dotName: DotName, from: TypeDeclaration): Option[TypeDeclaration] = {
    if (from.superClass.isEmpty)
      return None

    // Ignore if looking for the superType itself, must be found by other means to avoid recursion
    if (dotName == from.superClass.get.asDotName)
      return None

    val superType = getTypeFor(from.superClass.get, from)

    // TODO: Can you really do this?
    // Ignore if super type is in another package to avoid rogue absolute matches
    if (superType.nonEmpty && superType.get.namespace != from.namespace)
      return None

    // Ignore if a classes super type is an inner of that class to avoid recursion
    if (superType.nonEmpty && !superType.get.outerTypeName.contains(from.typeName)) {
      return superType.flatMap(st => findLocalTypeFor(dotName, st))
    }
    None
  }

  private def getFromOuterType(dotName: DotName, from: TypeDeclaration): Option[TypeDeclaration] = {
    if (dotName.isCompound || from.outerTypeName.isEmpty) {
      None
    } else {
      val outerType = this.findType(from.outerTypeName.get, from)
      if (outerType.isRight) {
        if (dotName.names.head == outerType.getOrElse(throw new NoSuchElementException).name)
          outerType.toOption
        else
          findLocalTypeFor(dotName, outerType.getOrElse(throw new NoSuchElementException))
      } else {
        None
      }
    }
  }
}
