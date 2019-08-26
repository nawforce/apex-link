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
import com.nawforce.documents.Location
import com.nawforce.types._
import com.nawforce.utils.Name

import scala.collection.mutable

trait VerifyContext {
  def parent(): Option[VerifyContext]

  /** Namespace of current code */
  def namespace: Option[Name]

  /** Get type declaration of 'this', option as not set in trigger */
  def thisType: Option[TypeDeclaration]

  /** Get type declaration of 'super' */
  def superType: Option[TypeDeclaration]

  /** Declare a dependency on dependant */
  def addDependency(dependant: Dependant): Unit

  /** Helper to locate type and add as dependency if found */
  def getTypeAndAddDependency(typeName: TypeName): Option[TypeDeclaration]
}

abstract class HolderVerifyContext {
  private val _dependencies = mutable.Set[Dependant]()

  def dependencies: Set[Dependant] = _dependencies.toSet

  def addDependency(dependant: Dependant): Unit = {
    _dependencies += dependant
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

  override def parent(): Option[VerifyContext] = parentContext

  override def namespace: Option[Name] = typeDeclaration.namespace

  override def thisType: Option[TypeDeclaration] = Some(typeDeclaration)

  override def superType: Option[TypeDeclaration] = typeDeclaration.superClassDeclaration

  override def getTypeFor(typeName: TypeName): Option[TypeDeclaration] = {
    getTypeFor(typeName.asDotName, typeDeclaration)
  }
}

class BodyDeclarationVerifyContext(parentContext: TypeVerifyContext, classBodyDeclaration: ClassBodyDeclaration)
  extends HolderVerifyContext with VerifyContext {

  override def parent(): Option[VerifyContext] = Some(parentContext)

  override def namespace: Option[Name] = parentContext.namespace

  override def thisType: Option[TypeDeclaration] = parentContext.thisType

  override def superType: Option[TypeDeclaration] = parentContext.superType

  override def getTypeFor(typeName: TypeName): Option[TypeDeclaration] =  parentContext.getTypeFor(typeName)
}

abstract class BlockVerifyContext(parentContext: VerifyContext)
  extends VerifyContext {

  private val vars = mutable.Map[Name, TypeDeclaration]()

  override def parent(): Option[VerifyContext] = Some(parentContext)

  override def namespace: Option[Name] = parentContext.namespace

  override def thisType: Option[TypeDeclaration] = parentContext.thisType

  override def superType: Option[TypeDeclaration] = parentContext.superType

  override def addDependency(dependant: Dependant): Unit = parentContext.addDependency(dependant)

  override def getTypeAndAddDependency(typeName: TypeName): Option[TypeDeclaration] =
    parentContext.getTypeAndAddDependency(typeName)

  def isVar(name: Name): Option[TypeDeclaration] = {
    vars.get(name)
  }

  def addVar(name: Name, typeDeclaration: TypeDeclaration): Unit = {
    vars.put(name, typeDeclaration)
  }

  def addVar(name: Name, location: Location, typeName: TypeName): Unit = {
    val td = getTypeAndAddDependency(typeName)
    if (td.isEmpty)
      Org.missingType(location, typeName)

    // TODO: This should really be an any
    vars.put(name, td.getOrElse(PlatformTypes.objectType))
  }


  def isStatic: Boolean
}

class OuterBlockVerifyContext(parentContext: VerifyContext, isStaticContext: Boolean)
  extends BlockVerifyContext(parentContext) {

  assert(!parentContext.isInstanceOf[BlockVerifyContext])

  override val isStatic: Boolean = isStaticContext
}

class InnerBlockVerifyContext(parentContext: BlockVerifyContext)
  extends BlockVerifyContext(parentContext) {

  override def isStatic: Boolean = parentContext.isStatic

  override def isVar(name: Name): Option[TypeDeclaration] = {
    super.isVar(name).orElse(parentContext.isVar(name))
  }
}

class ExpressionVerifyContext(parentContext: BlockVerifyContext)
  extends VerifyContext {

  override def parent(): Option[VerifyContext] = Some(parentContext)

  override def namespace: Option[Name] = parentContext.namespace

  override def thisType: Option[TypeDeclaration] = parentContext.thisType

  override def superType: Option[TypeDeclaration] = parentContext.superType

  override def addDependency(dependant: Dependant): Unit = parentContext.addDependency(dependant)

  override def getTypeAndAddDependency(typeName: TypeName): Option[TypeDeclaration] =
    parentContext.getTypeAndAddDependency(typeName)

  def isVar(name: Name): Option[TypeDeclaration] = parentContext.isVar(name)
}
