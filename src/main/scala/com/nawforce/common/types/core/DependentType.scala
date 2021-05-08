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

package com.nawforce.common.types.core

import com.nawforce.common.finding.TypeResolver
import com.nawforce.common.memory.SkinnySet
import com.nawforce.common.org.Module

import scala.collection.mutable

/** Add-in for supporting type level dependency analysis. This builds on the fine grained dependency handling found
  * in Dependency & DependencyHolder. Type level analysis is more useful for the API as it more directly maps
  * to file updates that drive invalidation handling.
  */
trait DependentType extends TypeDeclaration {

  /** The owning package, this is needed to disambiguate but restricts where DependentType can be used currently. */
  val module: Module

  /** TypeId for this type */
  lazy val typeId: TypeId = TypeId(module, typeName)

  /** TypeId for outer type (might be self) */
  lazy val outerTypeId: TypeId = outerTypeName.map(TypeId(module, _)).getOrElse(typeId)

  /** Current set of holders from this TypeDeclaration */
  private var typeDependencyHolders: SkinnySet[TypeId] = _

  /** Get current dependency holders */
  def getTypeDependencyHolders: SkinnySet[TypeId] = {
    Option(typeDependencyHolders).getOrElse(DependentType.emptyTypeDependencyHolders)
  }

  /** Set type dependency holders, useful when carrying forward during upsert */
  def updateTypeDependencyHolders(holders: SkinnySet[TypeId]): Unit = {
    typeDependencyHolders = holders
  }

  /** Collect set of TypeNames that this declaration is dependent on */
  def collectDependenciesByTypeName(dependents: mutable.Set[TypeId]): Unit

  def addTypeDependencyHolder(typeId: TypeId): Unit = {
    if (typeId != this.typeId) {
      if (typeDependencyHolders == null || typeDependencyHolders == DependentType.emptyTypeDependencyHolders)
        typeDependencyHolders = new SkinnySet()
      typeDependencyHolders.add(typeId)
    }
  }

  // Update holders on outer dependencies
  def propagateOuterDependencies(): Unit = {
    val dependsOn = mutable.Set[TypeId]()
    collectDependenciesByTypeName(dependsOn)
    dependsOn.foreach(dependentTypeName =>
      getOutermostDeclaration(dependentTypeName).map(_.addTypeDependencyHolder(this.typeId)))
  }

  private def getOutermostDeclaration(typeId: TypeId): Option[DependentType] = {
    TypeResolver(typeId.typeName, typeId.module, excludeSObjects = false) match {
      case Right(td: DependentType) =>
        td.outerTypeName
          .map(ot => getOutermostDeclaration(TypeId(typeId.module, ot)))
          .getOrElse(Some(td))
      case Right(_) => None
      case Left(_)  => None
    }
  }
}

object DependentType {
  val emptyTypeDependencyHolders: SkinnySet[TypeId] = new SkinnySet[TypeId]()
}
