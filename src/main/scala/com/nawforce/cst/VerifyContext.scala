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

import com.nawforce.types.{DependencyDeclaration, STATIC_MODIFIER, TypeDeclaration, TypeFinder, TypeName}
import com.nawforce.utils.Name

import scala.collection.mutable

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
  extends HolderVerifyContext with VerifyContext with TypeFinder {

  def parent(): Option[VerifyContext] = parentContext

  def isVar(name: Name): Boolean = {
    typeDeclaration.fields.exists(_.name == name) ||
    typeDeclaration.outerTypeName.flatMap(getTypeFor).exists(
      _.fields.exists(field => field.name == name && field.modifiers.contains(STATIC_MODIFIER)))
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
