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
package com.nawforce.common.types.other

import com.nawforce.common.api.{Name, TypeName}
import com.nawforce.common.documents.{LocationImpl, SourceInfo}
import com.nawforce.common.names.{Names, TypeNames}
import com.nawforce.common.org.PackageImpl
import com.nawforce.common.org.stream.PackageStream
import com.nawforce.common.path.PathFactory
import com.nawforce.common.types.core._
import com.nawforce.common.types.platform.PlatformTypes

import scala.collection.mutable
import scala.util.hashing.MurmurHash3

/** An individual component being represented as a nested type. */
final case class Component(pkg: PackageImpl, location: Option[LocationImpl], componentName: Name)
  extends InnerBasicTypeDeclaration(location.map(l => PathFactory(l.path)).toSeq,
    pkg, TypeName(componentName, Nil, Some(TypeName(Names.Component)))) {

  override val superClass: Option[TypeName] = Some(TypeNames.ApexPagesComponent)
  override lazy val superClassDeclaration: Option[TypeDeclaration] = Some(PlatformTypes.componentType)
  override val fields: Seq[FieldDeclaration] = PlatformTypes.componentType.fields
}

/** Component namespace handler */
final case class ComponentDeclaration(sources: Seq[SourceInfo], pkg: PackageImpl, components: Seq[TypeDeclaration],
                                      nestedComponents: Seq[NestedComponents])
  extends BasicTypeDeclaration(Seq.empty, pkg, TypeNames.Component)
    with DependentType with OtherTypeDeclaration {

  val sourceHash: Int = MurmurHash3.unorderedHash(sources.map(_.hash),0)

  override def nestedTypes: Seq[TypeDeclaration] = components ++ nestedComponents ++ namespaceDeclaration.toSeq ++ Seq(cDeclaration)

  // This is the optional Component.namespace implementation
  private var namespaceDeclaration = pkg.namespace.map(ns => new NamespaceDeclaration(ns))

  // This is the Component.c implementation
  private var cDeclaration = new NamespaceDeclaration(Names.c)

  // Propagate dependencies to nested
  nestedComponents.foreach(_.addTypeDependencyHolder(typeId))

  override def collectDependenciesByTypeName(dependsOn: mutable.Set[TypeId]): Unit = {
    nestedComponents.foreach(ni => ni.componentTypeId.foreach(dependsOn.add))
  }

  class NamespaceDeclaration(name: Name, nestedComponents: Seq[Component] = Seq())
    extends InnerBasicTypeDeclaration(Seq(), pkg, TypeName(name, Nil, Some(TypeNames.Component))) {
    override def nestedTypes: Seq[TypeDeclaration] = nestedComponents

    def merge(stream: PackageStream): NamespaceDeclaration = {
      new NamespaceDeclaration(name, nestedComponents ++
        stream.components.map(fe => Component(pkg, Some(fe.location), fe.name)))
    }
  }

  /** Create new components from merging those in the provided stream */
  def merge(stream: PackageStream): ComponentDeclaration = {
    val components = ComponentDeclaration.standardTypes ++
      stream.components.map(fe => Component(pkg, Some(fe.location), fe.name))
    val sourceInfo = stream.components.map(_.sourceInfo).distinct
    val componentDeclaration = new ComponentDeclaration(sourceInfo, pkg, components, nestedComponents)
    componentDeclaration.namespaceDeclaration.foreach(td =>
      componentDeclaration.namespaceDeclaration = Some(td.merge(stream)))
    componentDeclaration.cDeclaration = componentDeclaration.cDeclaration.merge(stream)
    componentDeclaration
  }
}

trait NestedComponents extends TypeDeclaration {
  val componentTypeId: Option[TypeId]

  def addTypeDependencyHolder(typeId: TypeId): Unit
}

/** Component.ns implementation for exposing components from dependent packages. As the exposed components are
  * owned elsewhere there is no need to set a controller here.
  */
final class PackageComponents(pkg: PackageImpl, componentDeclaration: ComponentDeclaration)
  extends InnerBasicTypeDeclaration(Seq(), pkg,
    TypeName(componentDeclaration.packageDeclaration.get.namespace.get, Nil, Some(TypeNames.Component)))
  with NestedComponents {

  override val componentTypeId: Option[TypeId] = Some(componentDeclaration.typeId)

  override def addTypeDependencyHolder(typeId: TypeId): Unit = {
    componentDeclaration.addTypeDependencyHolder(typeId)
  }

  override def nestedTypes: Seq[TypeDeclaration] = componentDeclaration.nestedTypes
}

final class GhostedComponents(pkg: PackageImpl, ghostedPackage: PackageImpl)
  extends InnerBasicTypeDeclaration(Seq.empty, pkg,
    TypeName(ghostedPackage.namespace.get, Nil, Some(TypeNames.Interview)))
  with NestedComponents {

  override val componentTypeId: Option[TypeId] = None

  override def addTypeDependencyHolder(typeId: TypeId): Unit = {}

  override def findNestedType(name: Name): Option[TypeDeclaration] = {
    Some(Component(ghostedPackage, None, name))
  }
}

object ComponentDeclaration {
  val standardTypes = Seq(PlatformTypes.apexComponent, PlatformTypes.chatterComponent)

  def apply(pkg: PackageImpl): ComponentDeclaration = {
    new ComponentDeclaration(Seq(), pkg, standardTypes, collectBaseComponents(pkg))
  }

  private def collectBaseComponents(pkg: PackageImpl): Seq[NestedComponents] = {
    pkg.transitiveBasePackages.map(basePkg => {
      if (basePkg.isGhosted) {
        new GhostedComponents(pkg, basePkg)
      } else {
        new PackageComponents(pkg, basePkg.components)
      }
    }).toSeq
  }
}


