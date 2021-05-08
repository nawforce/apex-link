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

package com.nawforce.common.org

import com.nawforce.common.documents._
import com.nawforce.common.names.{EncodedName, TypeNames, _}
import com.nawforce.common.types.core.TypeDeclaration
import com.nawforce.common.types.other._
import com.nawforce.common.types.platform.PlatformTypeDeclaration

import scala.collection.mutable

class PackageImpl(val org: OrgImpl, val namespace: Option[Name], val basePackages: Seq[PackageImpl])
    extends PackageAPI {

  /** Modules used in this package, mutable just to aid Module construction with back link to package. */
  private[nawforce] val modules = new mutable.ArrayBuffer[Module]()

  /** Add a new module to the package, modules must be added in 'deploy order' */
  private[nawforce] def add(module: Module): Unit = {
    modules.append(module)
  }

  /** Package modules in reverse deploy order, note ghost packages have no modules. */
  lazy val orderedModules: Seq[Module] = modules.reverse.toSeq

  /** Is this a ghost package, aka it has no modules. */
  lazy val isGhosted: Boolean = modules.isEmpty

  /** Is this or any base package of this a ghost package. */
  lazy val hasGhosted: Boolean = isGhosted || basePackages.exists(_.hasGhosted)

  /** Labels defined in the package. */
  lazy val labels: Option[LabelDeclaration] = orderedModules.headOption.map(_.labels)

  /** Get summary of package context containing namespace & base package namespace information. */
  def packageContext: PackageContext = {
    val ghostedPackages = basePackages
      .groupBy(_.isGhosted)
      .map(kv => (kv._1, kv._2.map(_.namespace.map(_.value).getOrElse("")).sorted.toArray))
    PackageContext(namespace.map(_.value), ghostedPackages(true), ghostedPackages(false))
  }

  /** Set of namespaces used by this package and its base packages. */
  lazy val namespaces: Set[Name] = {
    namespace.toSet ++
      basePackages.flatMap(_.namespaces) ++
      PlatformTypeDeclaration.namespaces
  }

  /* Check if a type is ghosted in this package */
  def isGhostedType(typeName: TypeName): Boolean = {
    if (typeName.outer.contains(TypeNames.Schema)) {
      val encName = EncodedName(typeName.name)
      basePackages.filter(_.isGhosted).exists(_.namespace == encName.namespace)
    } else {
      basePackages.filter(_.isGhosted).exists(_.namespace.contains(typeName.outerName)) ||
      typeName.params.exists(isGhostedType)
    }
  }

  /** Check if a field name is ghosted in this package. */
  def isGhostedFieldName(name: Name): Boolean = {
    EncodedName(name).namespace match {
      case None     => false
      case Some(ns) => basePackages.filter(_.isGhosted).exists(_.namespace.contains(ns))
    }
  }

  /** Check all summary types have propagated their dependencies. */
  def propagateAllDependencies(): Unit = {
    modules.foreach(_.propagateAllDependencies())
  }

  /** Find a type in this package.
    * TODO: This should be correct but its rather a brute force approach, conflict detection might give a way to
    * improve how this is done.
    */
  def getPackageType(typeName: TypeName): Option[TypeDeclaration] = {
    modules.view.flatMap(_.findModuleType(typeName)).headOption
  }
}
