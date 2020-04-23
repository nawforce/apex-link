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
package com.nawforce.common.metadata

import com.nawforce.common.api.{DependentSummary, FieldDependentSummary, MethodDependentSummary, TypeDependentSummary}
import com.nawforce.common.types.BlockDeclaration
import com.nawforce.common.types.apex.{ApexClassDeclaration, ApexConstructorLike, ApexFieldLike, ApexMethodLike}

import scala.collection.mutable

/* Dependents are referencable elements in code such as types, fields, constructors, methods & labels. Each dependent
 * may have a controller which is the Dependent that acts as a parent type for re-loading purposes. A dependent has a
 * set of 'holders' of that dependency for reverse lookup although the set may be stale. They use identity equality
 * to help with collections performance.
 */
trait Dependent {

  /** Another dependent who is considered to be controlling from an re-loading perspective for this dependent. */
  private var controller: Option[Dependent] = None

  /**
    * Update the controlling dependent. This is mutable because as it often needs to be set in the reverse
    * of construction order, i.e. child->parent
    */
  def setController(controlling: Option[Dependent]): Unit = controller = controlling


  // The set of holders on this dependency element, may be stale!
  private val dependencyHolders = mutable.Set[DependencyHolder]()

  // Has any holders
  def hasHolders: Boolean = dependencyHolders.nonEmpty

  // The set of current holders
  def getDependencyHolders: Set[DependencyHolder] = dependencyHolders.toSet

  // Add a new holder
  def addDependencyHolder(dependencyHolder: DependencyHolder): Unit = {
    dependencyHolders.add(dependencyHolder)
  }

  // Identity equality
  override def equals(that: Any): Boolean = {
    that match {
      case other: Dependent => other.eq(this)
      case _ => false
    }
  }

  // Identity hash, may not be unique
  override def hashCode(): Int = System.identityHashCode(this)
}

/* Holder of a dependency, for convenience all holders are assumed to be potential dependents.
 */
trait DependencyHolder extends Dependent {

  // Get Dependents being held, default to empty for holders who do not use this, override as needed
  // TODO: Narrow where we introduce this so default not needed
  def dependencies(): Iterable[Dependent] = Iterable.empty

  // Inform each dependent this is holding a dependency to them
  def propagateDependencies(): Unit = {
    dependencies().foreach(_.addDependencyHolder(this))
  }

  // Convert dependencies into a summary format
  def dependencySummary(): Set[DependentSummary] = {
    dependencies().flatMap {
      case td: ApexClassDeclaration =>
        Some(TypeDependentSummary(td.typeName, td.sourceHash))
      case fd: ApexFieldLike =>
        Some(FieldDependentSummary(fd.outerTypeName, fd.name.value))
      case md: ApexMethodLike =>
        Some(MethodDependentSummary(md.outerTypeName, md.name.value, md.parameters.map(_.summary).toList))
      case _: ApexConstructorLike => None
      case _: BlockDeclaration => None
    }.toSet
  }
}
