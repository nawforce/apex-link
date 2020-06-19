/*
 [The "BSD licence"]
 Copyright (c) 2020 Kevin Jones
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

package com.nawforce.common.types

import com.nawforce.common.api._
import com.nawforce.common.names.Names

import scala.collection.mutable
import scala.ref.WeakReference

package object apex {

  implicit class DependentSummaryOps(dependency: DependentSummary) {

    def intern: DependentSummary = {
      dependency match {
        case d: TypeDependentSummary => intern(d)
        case d: FieldDependentSummary => intern(d)
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
      MethodDependentSummary(dependency.typeId.intern, Names(dependency.name).value,
        dependency.parameterTypes.map(_.intern))
    }
  }

  implicit class TypeIdentifierOps(typeIdentifier: TypeIdentifier) {
    def intern: TypeIdentifier = {
      TypeIdentifierOps.weakCache(
        TypeIdentifier(typeIdentifier.namespace.map(n => Names(n.value)), typeIdentifier.typeName.intern)
      )
    }
  }

  object TypeIdentifierOps {
    private val cache = mutable.WeakHashMap[TypeIdentifier, WeakReference[TypeIdentifier]]()

    def weakCache(typeName: TypeIdentifier): TypeIdentifier = {
      cache.getOrElseUpdate(typeName, WeakReference(typeName)).get.get
    }
  }


  implicit class TypeNameOps(typeName: TypeName) {
    def intern: TypeName = {
      TypeNameOps.weakCache(
        TypeName(intern(typeName.name), typeName.params.map(_.intern), typeName.outer.map(_.intern))
      )
    }

    private def intern(name: Name): Name = {
      Names(name.value)
    }
  }

  object TypeNameOps {
    private val cache = mutable.WeakHashMap[TypeName, WeakReference[TypeName]]()

    def weakCache(typeName: TypeName): TypeName = {
      cache.getOrElseUpdate(typeName, WeakReference(typeName)).get.get
    }
  }

}
