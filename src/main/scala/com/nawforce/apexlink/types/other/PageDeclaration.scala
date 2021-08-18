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
import com.nawforce.apexlink.names.XNames.NameUtils
import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.org.Module
import com.nawforce.apexlink.types.core._
import com.nawforce.pkgforce.documents._
import com.nawforce.pkgforce.modifiers.{GLOBAL_MODIFIER, Modifier, PRIVATE_MODIFIER, STATIC_MODIFIER}
import com.nawforce.pkgforce.names.{Name, TypeName}
import com.nawforce.pkgforce.path.PathLike
import com.nawforce.pkgforce.stream.{PackageStream, PageEvent}

import scala.collection.mutable
import scala.util.hashing.MurmurHash3

/** A individual Page being represented as a static field. */
case class Page(module: Module, path: PathLike, name: Name, vfContainer: VFContainer) extends FieldDeclaration {
  override lazy val modifiers: Array[Modifier] = Array(STATIC_MODIFIER, GLOBAL_MODIFIER)
  override lazy val typeName: TypeName = TypeNames.PageReference
  override lazy val readAccess: Modifier = GLOBAL_MODIFIER
  override lazy val writeAccess: Modifier = PRIVATE_MODIFIER
  override val idTarget: Option[TypeName] = None

  private var depends: Option[Set[Dependent]] = None

  override def dependencies(): Iterable[Dependent] = depends.map(_.toIterable).getOrElse(Array().toIterable)

  def validate(): Unit = {
    depends = Some(vfContainer.validate())
    propagateDependencies()
  }
}

object Page {
  def apply(module: Module, event: PageEvent): Page = {
    val path = event.sourceInfo.path
    val document = MetadataDocument(path)
    new Page(module, path, document.get.name, new VFContainer(module, event))
  }
}

/** Page 'namespace' implementation. Provides access to pages in the package as well as pages that are accessible in
  * base packages via the `namespace__name` format.
  */
final case class PageDeclaration(sources: Array[SourceInfo], override val module: Module, pages: Array[Page])
    extends BasicTypeDeclaration(pages.map(p => p.path).distinct, module, TypeNames.Page)
    with DependentType {

  // Propagate dependencies to base packages
  module.baseModules.foreach(_.pages.addTypeDependencyHolder(typeId))

  val sourceHash: Int = MurmurHash3.unorderedHash(sources.map(_.hash), 0)

  override lazy val isComplete: Boolean = !module.pkg.hasGhosted
  override val fields: Array[FieldDeclaration] = pages.asInstanceOf[Array[FieldDeclaration]]

  /** Create new pages from merging those in the provided stream */
  def merge(stream: PackageStream): PageDeclaration = {
    merge(stream.pages)
  }

  def merge(pageEvents: Array[PageEvent]): PageDeclaration = {
    val newPages = pages ++ pageEvents.map(pe => Page(module, pe))
    val sourceInfo = pageEvents.map(_.sourceInfo).distinct
    new PageDeclaration(sourceInfo, module, newPages)
  }

  override def validate(): Unit = {
    pages.foreach(_.validate())
    propagateOuterDependencies(new TypeCache())
  }

  override def collectDependenciesByTypeName(dependsOn: mutable.Set[TypeId],
                                             apexOnly: Boolean,
                                             typeCache: TypeCache): Unit = {
    val dependents = mutable.Set[Dependent]()
    pages.foreach(page => page.dependencies().foreach(dependents.add))
    DependentType.dependentsToTypeIds(module, dependents, apexOnly, dependsOn)
    if (!apexOnly)
      module.baseModules.foreach(bp => dependsOn.add(bp.pages.typeId))
  }
}

object PageDeclaration {
  def apply(module: Module): PageDeclaration = {
    new PageDeclaration(Array(), module, collectBasePages(module))
  }

  private def collectBasePages(module: Module): Array[Page] = {
    module.basePackages
      .flatMap(basePkg => {
        val ns = basePkg.namespace.get
        basePkg.orderedModules.headOption.map(m => {
          m.pages.pages.map(page => {
            if (page.name.contains("__"))
              page
            else
              Page(module, page.path, Name(s"${ns}__${page.name}"), page.vfContainer)
          })
        })
      })
      .flatten
      .toArray
  }
}
