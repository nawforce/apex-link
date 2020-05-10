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
import com.nawforce.common.names.{DotName, _}
import com.nawforce.common.org.PackageImpl
import com.nawforce.common.types.core.TypeDeclaration

import scala.collection.mutable

/** Helper that implements local type searching, extracted out as logic is a bit involved */
trait TypeFinder {
  this: PackageImpl =>

  private val typeCache = mutable.Map[(TypeName, TypeDeclaration.TID), Option[TypeDeclaration]]()

  /** Find a type relative to a starting type with a local or global name*/
  def getTypeFor(typeName: TypeName, from: TypeDeclaration): Option[TypeDeclaration] = {
      typeCache.getOrElseUpdate((typeName, from.tid), {
        val td = getLocalTypeFor(typeName, from).orElse(this.getType(typeName, Some(from)).toOption)
        typeCache.put((typeName, from.tid), td)
        td
      })
  }

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
      .orElse(getFromOuterType(dotName, from)
        .orElse(getFromSuperType(dotName, from))
      )
  }

  private def getNestedType(dotName: DotName, from: TypeDeclaration): Option[TypeDeclaration] = {
    if (dotName.isCompound) {
      None
    } else {
      val matched = from.nestedTypes.filter(_.name == dotName.names.head)
      assert(matched.size < 2)
      matched.headOption
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
      val outerType = this.getType(from.outerTypeName.get, Some(from))
      if (outerType.isRight) {
        if (dotName.names.head == outerType.right.get.name)
          outerType.toOption
        else
          findLocalTypeFor(dotName, outerType.right.get)
      } else {
        None
      }
    }
  }
}
