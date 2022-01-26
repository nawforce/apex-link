/*
 Copyright (c) 2019 Kevin Jones, All rights reserved.
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
 */

package com.nawforce.apexlink.types.other

import com.nawforce.apexlink.finding.TypeResolver.TypeCache
import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.org.{Module, PackageImpl}
import com.nawforce.apexlink.types.core._
import com.nawforce.apexlink.types.platform.PlatformTypes
import com.nawforce.apexlink.types.synthetic.CustomFieldDeclaration
import com.nawforce.pkgforce.documents.{MetadataDocument, SourceInfo}
import com.nawforce.pkgforce.names.{Name, Names, TypeName}
import com.nawforce.pkgforce.path.{PathLike, PathLocation, UnsafeLocatable}
import com.nawforce.pkgforce.stream.{ComponentEvent, PackageStream}

import scala.collection.immutable.ArraySeq
import scala.collection.mutable
import scala.util.hashing.MurmurHash3

/** An individual component being represented as a nested type. */
final case class Component(module: Module,
                           location: PathLocation,
                           componentName: Name,
                           attributes: Option[ArraySeq[Name]],
                           vfContainer: Option[VFContainer])
    extends InnerBasicTypeDeclaration(ArraySeq.unsafeWrapArray(Option(location).map(_.path).toArray),
      module,
      TypeName(componentName, Nil, Some(TypeName(Names.Component))))
      with UnsafeLocatable
      with Dependent
      with DependencyHolder {

  private var depends: Option[Set[Dependent]] = None

  override val inTest: Boolean = false
  override val superClass: Option[TypeName] = Some(TypeNames.ApexPagesComponent)
  override lazy val superClassDeclaration: Option[TypeDeclaration] = Some(PlatformTypes.componentType)
  override val fields: ArraySeq[FieldDeclaration] = {
    attributes.getOrElse(ArraySeq())
      .map(a => CustomFieldDeclaration(a, TypeNames.Any, None)) ++ PlatformTypes.componentType.fields
  }

  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    if (attributes.isEmpty)
      Some(CustomFieldDeclaration(name, TypeNames.Any, None))
    else
      super.findField(name, staticContext)
  }

  override def dependencies(): Iterable[Dependent] = depends.map(_.toIterable).getOrElse(Array().toIterable)

  override def validate(): Unit = {
    super.validate()
    vfContainer.foreach(vf => {
      depends = Some(vf.validate())
      propagateDependencies()
    })
  }
}

object Component {
  def apply(module: Module, event: ComponentEvent): Component = {
    val location = event.sourceInfo.location
    val document = MetadataDocument(location.path)
    new Component(module, location, document.get.name, Some(event.attributes), Some(new VFContainer(module, event)))
  }

  val emptyComponents: Array[Component] = Array()
}

/** Component namespace handler */
final case class ComponentDeclaration(sources: ArraySeq[SourceInfo],
                                      module: Module,
                                      components: Seq[TypeDeclaration],
                                      nestedComponents: Seq[NestedComponents])
  extends BasicTypeDeclaration(PathLike.emptyPaths, module, TypeNames.Component)
    with DependentType {

  val sourceHash: Int = MurmurHash3.unorderedHash(sources.map(_.hash), 0)

  override def nestedTypes: ArraySeq[TypeDeclaration] = {
    val nested = components ++ nestedComponents ++ namespaceDeclaration.toSeq ++ Seq(cDeclaration)
    ArraySeq.unsafeWrapArray(nested.toArray)
  }

  // This is the optional Component.namespace implementation
  private var namespaceDeclaration = module.namespace.map(ns => new NamespaceDeclaration(ns))

  // This is the Component.c implementation
  private var cDeclaration = new NamespaceDeclaration(Names.c)

  // Propagate dependencies to nested
  nestedComponents.foreach(_.addTypeDependencyHolder(typeId))

  override def validate(): Unit = {
    components.foreach(_.validate())
    propagateOuterDependencies(new TypeCache())
  }

  override def gatherDependencies(dependsOn: mutable.Set[TypeId],
                                  apexOnly: Boolean,
                                  outerTypesOnly: Boolean,
                                  typeCache: TypeCache): Unit = {
    val dependents = mutable.Set[Dependent]()
    components
      .collect { case component: DependencyHolder => component }
      .foreach(component => component.dependencies().foreach(dependents.add))
    DependentType.dependentsToTypeIds(module, dependents, apexOnly, outerTypesOnly, dependsOn)
    if (!apexOnly)
      nestedComponents.foreach(ni => ni.componentTypeId.foreach(dependsOn.add))
  }

  class NamespaceDeclaration(name: Name,
                             nestedComponents: ArraySeq[TypeDeclaration] = TypeDeclaration.emptyTypeDeclarations)
      extends InnerBasicTypeDeclaration(PathLike.emptyPaths, module, TypeName(name, Nil, Some(TypeNames.Component))) {
    override def nestedTypes: ArraySeq[TypeDeclaration] = nestedComponents

    def merge(events: ArraySeq[ComponentEvent]): NamespaceDeclaration = {
      new NamespaceDeclaration(name,
        nestedComponents ++
          events.map(ce => Component(module, ce)))
    }
  }

  /** Create new components from merging those in the provided stream */
  def merge(stream: PackageStream): ComponentDeclaration = {
    merge(stream.components)
  }

  def merge(events: ArraySeq[ComponentEvent]): ComponentDeclaration = {
    val components = ComponentDeclaration.standardTypes ++
      events.map(ce => Component(module, ce))
    val sourceInfo = events.map(_.sourceInfo).distinct
    val componentDeclaration =
      new ComponentDeclaration(sourceInfo, module, components, nestedComponents)
    componentDeclaration.namespaceDeclaration.foreach(td =>
      componentDeclaration.namespaceDeclaration = Some(td.merge(events)))
    componentDeclaration.cDeclaration = componentDeclaration.cDeclaration.merge(events)
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

  override def nestedTypes: ArraySeq[TypeDeclaration] = componentDeclaration.nestedTypes
}

final class GhostedComponents(module: Module, ghostedPackage: PackageImpl)
    extends InnerBasicTypeDeclaration(PathLike.emptyPaths,
                                      module,
                                      TypeName(ghostedPackage.namespace.get, Nil, Some(TypeNames.Interview)))
    with NestedComponents {

  override val componentTypeId: Option[TypeId] = None

  override def addTypeDependencyHolder(typeId: TypeId): Unit = {}

  override def findNestedType(name: Name): Option[TypeDeclaration] = {
    Some(Component(module, null, name, None, None))
  }
}

object ComponentDeclaration {
  val standardTypes = Seq(PlatformTypes.apexComponent, PlatformTypes.chatterComponent)

  def apply(module: Module): ComponentDeclaration = {
    new ComponentDeclaration(ArraySeq(), module, standardTypes, collectBaseComponents(module))
  }

  private def collectBaseComponents(module: Module): Seq[NestedComponents] = {
    module.basePackages
      .map(basePkg => {
        basePkg.orderedModules.headOption
          .map(m => new PackageComponents(module, m.components))
          .getOrElse(new GhostedComponents(module, basePkg))
      })
  }
}
