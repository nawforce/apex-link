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

import com.nawforce.common.names.TypeNames
import com.nawforce.common.org.{Module, PackageImpl}
import com.nawforce.common.types.core._
import com.nawforce.common.types.platform.PlatformTypes
import com.nawforce.common.types.synthetic.CustomFieldDeclaration
import com.nawforce.pkgforce.documents.{MetadataDocument, SourceInfo}
import com.nawforce.pkgforce.names.{Name, Names, TypeName}
import com.nawforce.pkgforce.path.{PathFactory, PathLike}
import com.nawforce.pkgforce.stream.{ComponentEvent, PackageStream}

import scala.collection.mutable
import scala.util.hashing.MurmurHash3

/** An individual component being represented as a nested type. */
final case class Component(module: Module,
                           location: Option[PathLike],
                           componentName: Name,
                           attributes: Option[Array[Name]])
    extends InnerBasicTypeDeclaration(
      location.toArray,
      module,
      TypeName(componentName, Nil, Some(TypeName(Names.Component)))) {

  override val superClass: Option[TypeName] = Some(TypeNames.ApexPagesComponent)
  override lazy val superClassDeclaration: Option[TypeDeclaration] = Some(
    PlatformTypes.componentType)
  override val fields: Array[FieldDeclaration] = attributes
    .getOrElse(Array())
    .map(a => CustomFieldDeclaration(a, TypeNames.Any, None)) ++ PlatformTypes.componentType.fields

  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    if (attributes.isEmpty)
      Some(CustomFieldDeclaration(name, TypeNames.Any, None))
    else
      super.findField(name, staticContext)
  }
}

object Component {
  def apply(module: Module, event: ComponentEvent): Component = {
    val path = PathFactory(event.sourceInfo.path)
    val document = MetadataDocument(path)
    new Component(module, Some(path), document.get.name, Some(event.attributes))
  }

  val emptyComponents: Array[Component] = Array()
}

/** Component namespace handler */
final case class ComponentDeclaration(sources: Seq[SourceInfo],
                                      module: Module,
                                      components: Seq[TypeDeclaration],
                                      nestedComponents: Seq[NestedComponents])
    extends BasicTypeDeclaration(PathLike.emptyPaths, module, TypeNames.Component)
    with DependentType
    with OtherTypeDeclaration {

  val sourceHash: Int = MurmurHash3.unorderedHash(sources.map(_.hash), 0)

  override def nestedTypes: Array[TypeDeclaration] =
    (components ++ nestedComponents ++ namespaceDeclaration.toSeq ++ Seq(cDeclaration)).toArray

  // This is the optional Component.namespace implementation
  private var namespaceDeclaration = module.namespace.map(ns => new NamespaceDeclaration(ns))

  // This is the Component.c implementation
  private var cDeclaration = new NamespaceDeclaration(Names.c)

  // Propagate dependencies to nested
  nestedComponents.foreach(_.addTypeDependencyHolder(typeId))

  override def collectDependenciesByTypeName(dependsOn: mutable.Set[TypeId]): Unit = {
    nestedComponents.foreach(ni => ni.componentTypeId.foreach(dependsOn.add))
  }

  class NamespaceDeclaration(name: Name,
                             nestedComponents: Array[TypeDeclaration] =
                               TypeDeclaration.emptyTypeDeclarations)
      extends InnerBasicTypeDeclaration(PathLike.emptyPaths,
                                        module,
                                        TypeName(name, Nil, Some(TypeNames.Component))) {
    override def nestedTypes: Array[TypeDeclaration] = nestedComponents

    def merge(stream: PackageStream): NamespaceDeclaration = {
      new NamespaceDeclaration(name,
                               nestedComponents ++
                                 stream.components.map(ce => Component(module, ce)))
    }
  }

  /** Create new components from merging those in the provided stream */
  def merge(stream: PackageStream): ComponentDeclaration = {
    val components = ComponentDeclaration.standardTypes ++
      stream.components.map(ce => Component(module, ce))
    val sourceInfo = stream.components.map(_.sourceInfo).distinct
    val componentDeclaration =
      new ComponentDeclaration(sourceInfo, module, components, nestedComponents)
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
final class PackageComponents(module: Module, componentDeclaration: ComponentDeclaration)
    extends InnerBasicTypeDeclaration(
      PathLike.emptyPaths,
      module,
      TypeName(componentDeclaration.module.pkg.namespace.get, Nil, Some(TypeNames.Component)))
    with NestedComponents {

  override val componentTypeId: Option[TypeId] = Some(componentDeclaration.typeId)

  override def addTypeDependencyHolder(typeId: TypeId): Unit = {
    componentDeclaration.addTypeDependencyHolder(typeId)
  }

  override def nestedTypes: Array[TypeDeclaration] = componentDeclaration.nestedTypes
}

final class GhostedComponents(module: Module, ghostedPackage: PackageImpl)
    extends InnerBasicTypeDeclaration(
      PathLike.emptyPaths,
      module,
      TypeName(ghostedPackage.namespace.get, Nil, Some(TypeNames.Interview)))
    with NestedComponents {

  override val componentTypeId: Option[TypeId] = None

  override def addTypeDependencyHolder(typeId: TypeId): Unit = {}

  override def findNestedType(name: Name): Option[TypeDeclaration] = {
    Some(Component(module, None, name, None))
  }
}

object ComponentDeclaration {
  val standardTypes = Seq(PlatformTypes.apexComponent, PlatformTypes.chatterComponent)

  def apply(module: Module): ComponentDeclaration = {
    new ComponentDeclaration(Seq(), module, standardTypes, collectBaseComponents(module))
  }

  private def collectBaseComponents(module: Module): Seq[NestedComponents] = {
    module.basePackages
      .map(basePkg => {
        if (basePkg.isGhosted) {
          new GhostedComponents(module, basePkg)
        } else {
          new PackageComponents(module, basePkg.components.get)
        }
      })
  }
}
