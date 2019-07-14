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
package com.nawforce.cst

import com.nawforce.api.Org
import com.nawforce.types.{DependencyDeclaration, TypeDeclaration, TypeName}
import com.nawforce.utils.{DotName, Name}
import scalaz.Memo

import scala.collection.mutable

trait TypeResolver {
  /** Find a type relative to a starting type with a local or global name*/
  def getTypeFor(dotName: DotName, from: TypeDeclaration): Option[TypeDeclaration] = {
    typeCache(dotName, from)
  }

  private val typeCache = Memo.immutableHashMapMemo[(DotName, TypeDeclaration), Option[TypeDeclaration]] {
    case (name: DotName, from: TypeDeclaration) => findTypeFor(name, from)
  }

  private def findTypeFor(dotName: DotName, from: TypeDeclaration): Option[TypeDeclaration] = {
    getNestedType(dotName, from)
      .orElse(getFromSuperType(dotName, from)
        .orElse(getFromOuterType(dotName, from)
          .orElse(
            Org.getType(from.namespace.map(ns => dotName.prepend(ns)).getOrElse(dotName))
              .orElse(Org.getType(dotName))
          )
        )
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

    // Avoid recursion of searching for super class in super class, will be found later
    if (dotName == from.superClass.get.asDotName)
      return None

    val superType = getTypeFor(from.superClass.get.asDotName, from)
    if (superType.exists(_.path != from.path)) {
      superType.flatMap(st => getTypeFor(dotName, st))
    } else {
      None
    }
  }

  private def getFromOuterType(dotName: DotName, from: TypeDeclaration): Option[TypeDeclaration] = {
    if (dotName.isCompound || from.outerTypeName.isEmpty) {
      None
    } else {
      val outerType = Org.getType(from.outerTypeName.get.asDotName)
      if (outerType.nonEmpty) {
        if (dotName.names.head == outerType.get.name)
          outerType
        else
          getTypeFor(dotName, outerType.get)
      } else {
        None
      }
    }
  }
}

trait VerifyContext {
  def parent(): Option[VerifyContext]

  def isVar(name: Name): Boolean

  def getTypeAndAddDependency(typeName: TypeName): Option[TypeDeclaration]
}

abstract class HolderVerifyContext {
  private val _dependencies = mutable.Set[DependencyDeclaration] ()

  def dependencies: Set[DependencyDeclaration] = _dependencies.toSet

  def addDependency(dependentDeclaration: DependencyDeclaration): Unit = {
    _dependencies += dependentDeclaration
  }

  def getTypeFor(typeName: TypeName): Option[TypeDeclaration]

  def getTypeAndAddDependency(typeName: TypeName): Option[TypeDeclaration] = {
    val paramTypes = typeName.params.flatMap(getTypeFor)
    if (paramTypes.size == typeName.params.size) {
      val tdOpt = getTypeFor(typeName)
      tdOpt.foreach(td => {
        addDependency(td)
        paramTypes.foreach(addDependency)
      })
      tdOpt
    } else {
      None
    }
  }
}

class TypeVerifyContext(parentContext: Option[VerifyContext], typeDeclaration: TypeDeclaration)
  extends HolderVerifyContext with VerifyContext with TypeResolver {

  def parent(): Option[VerifyContext] = parentContext

  def isVar(name: Name): Boolean = {
    typeDeclaration.fields.exists(_.name == name)
  }

  def addFieldDependency(name: Name): Unit = {
    typeDeclaration.fields.find(_.name == name).foreach(addDependency)
  }

  override def getTypeFor(typeName: TypeName): Option[TypeDeclaration] = {
    getTypeFor(typeName.asDotName, typeDeclaration)
  }
}

class BodyDeclarationVerifyContext(parentContext: TypeVerifyContext, classBodyDeclaration: ClassBodyDeclaration)
  extends HolderVerifyContext with VerifyContext {

  def parent(): Option[VerifyContext] = Some(parentContext)

  override def getTypeFor(typeName: TypeName): Option[TypeDeclaration] = {
    parentContext.getTypeFor(typeName)
  }

  def isVar(name: Name): Boolean = {
    parentContext.isVar(name)
  }
}

class BlockVerifyContext(parentContext: VerifyContext)
  extends VerifyContext {

  private val vars = mutable.Set[Name]()

  def parent(): Option[VerifyContext] = Some(parentContext)

  def getTypeAndAddDependency(typeName: TypeName): Option[TypeDeclaration] = {
    parentContext.getTypeAndAddDependency(typeName)
  }

  def addVar(name: Name): Unit = {
    vars.add(name)
  }

  def isVar(name: Name): Boolean = {
    vars.contains(name) || parentContext.isVar(name)
  }
}

class ExpressionVerifyContext(parentContext: BlockVerifyContext)
  extends VerifyContext {

  def parent(): Option[VerifyContext] = Some(parentContext)

  def getTypeAndAddDependency(typeName: TypeName): Option[TypeDeclaration] = {
    parentContext.getTypeAndAddDependency(typeName)
  }

  def isVar(name: Name): Boolean = {
    parentContext.isVar(name)
  }
}
