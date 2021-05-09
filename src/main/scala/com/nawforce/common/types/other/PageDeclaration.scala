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

import com.nawforce.common.documents._
import com.nawforce.common.modifiers.{GLOBAL_MODIFIER, Modifier, PRIVATE_MODIFIER, STATIC_MODIFIER}
import com.nawforce.common.names.{Name, TypeName, TypeNames}
import com.nawforce.common.org.Module
import com.nawforce.common.path.{PathFactory, PathLike}
import com.nawforce.common.stream.{PackageStream, PageEvent}
import com.nawforce.common.types.core.{
  BasicTypeDeclaration,
  DependentType,
  FieldDeclaration,
  TypeId
}

import scala.collection.mutable
import scala.util.hashing.MurmurHash3

/** A individual Page being represented as a static field. */
case class Page(module: Module, path: PathLike, name: Name) extends FieldDeclaration {
  override lazy val modifiers: Array[Modifier] = Array(STATIC_MODIFIER, GLOBAL_MODIFIER)
  override lazy val typeName: TypeName = TypeNames.PageReference
  override lazy val readAccess: Modifier = GLOBAL_MODIFIER
  override lazy val writeAccess: Modifier = PRIVATE_MODIFIER
  override val idTarget: Option[TypeName] = None
}

object Page {
  def apply(module: Module, event: PageEvent): Page = {
    val path = PathFactory(event.sourceInfo.path)
    val document = MetadataDocument(path)
    new Page(module, path, document.get.name)
  }
}

/** Page 'namespace' implementation. Provides access to pages in the package as well as pages that are accessible in
  * base packages via the `namespace__name` format.
  */
final case class PageDeclaration(sources: Array[SourceInfo],
                                 override val module: Module,
                                 pages: Array[Page])
    extends BasicTypeDeclaration(pages.map(p => p.path).distinct, module, TypeNames.Page)
    with DependentType
    with OtherTypeDeclaration {

  // Propagate dependencies to base packages
  module.baseModules.foreach(_.pages.addTypeDependencyHolder(typeId))

  val sourceHash: Int = MurmurHash3.unorderedHash(sources.map(_.hash), 0)

  override lazy val isComplete: Boolean = !module.pkg.hasGhosted
  override val fields: Array[FieldDeclaration] = pages.asInstanceOf[Array[FieldDeclaration]]

  /** Create new pages from merging those in the provided stream */
  def merge(stream: PackageStream): PageDeclaration = {
    val newPages = pages ++ stream.pages.map(pe => Page(module, pe))
    val sourceInfo = stream.pages.map(_.sourceInfo).distinct.toArray
    new PageDeclaration(sourceInfo, module, newPages)
  }

  override def collectDependenciesByTypeName(dependsOn: mutable.Set[TypeId]): Unit = {
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
        basePkg.pages.map(_.pages.map(page => {
          if (page.name.contains("__"))
            page
          else
            Page(module, page.path, Name(s"${ns}__${page.name}"))
        }))
      })
      .flatten
      .toArray
  }
}
