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
package com.nawforce.types

import com.nawforce.api.Org
import com.nawforce.utils.DotName
import scalaz.Memo

trait TypeFinder {
  /** Find a type relative to a starting type with a local or global name*/
  def getTypeFor(dotName: DotName, from: TypeDeclaration): Option[TypeDeclaration] = {
    typeCache(dotName, from)
  }

  private val typeCache = Memo.immutableHashMapMemo[(DotName, TypeDeclaration), Option[TypeDeclaration]] {
    case (name: DotName, from: TypeDeclaration) => findTypeFor(name.demangled, from, localOnly = false)
  }

  private def findTypeFor(dotName: DotName, from: TypeDeclaration, localOnly: Boolean): Option[TypeDeclaration] = {
    var matched =
      if (dotName.firstName == from.name) {
        if (dotName.isCompound)
          findTypeFor(dotName.tail, from, localOnly = true)
        else
          Some(from)
      } else {
        None
      }

    if (matched.nonEmpty)
      return matched

    matched = getNestedType(dotName, from)
      .orElse(getFromSuperType(dotName, from, localOnly)
        .orElse(getFromOuterType(dotName, from, localOnly))
      )
    if (matched.nonEmpty)
      return matched

    if (!localOnly) {
      Org.getType(from.namespace, dotName)
    } else {
      None
    }
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

  private def getFromSuperType(dotName: DotName, from: TypeDeclaration, localOnly: Boolean): Option[TypeDeclaration] = {
    if (from.superClass.isEmpty)
      return None

    // Ignore if looking for the superType itself, must be found by other means to avoid recursion
    if (dotName == from.superClass.get.asDotName)
      return None

    val superType = getTypeFor(from.superClass.get.asDotName, from)

    // Ignore if a classes super type is an inner of that class to avoid recursion
    if (superType.nonEmpty && !superType.get.outerTypeName.contains(from.typeName)) {
        return superType.flatMap(st => findTypeFor(dotName, st, localOnly))
    }
    None
  }

  private def getFromOuterType(dotName: DotName, from: TypeDeclaration, localOnly: Boolean): Option[TypeDeclaration] = {
    if (dotName.isCompound || from.outerTypeName.isEmpty) {
      None
    } else {
      val outerType = Org.getType(from.namespace, from.outerTypeName.get.asDotName)
      if (outerType.nonEmpty) {
        if (dotName.names.head == outerType.get.name)
          outerType
        else
          findTypeFor(dotName, outerType.get, localOnly)
      } else {
        None
      }
    }
  }
}

class StandardTypeFinder extends TypeFinder