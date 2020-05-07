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

import com.nawforce.common.documents.LocationImpl
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.org.PackageImpl
import com.nawforce.common.org.stream.PackageStream
import com.nawforce.common.path.{PathFactory, PathLike}
import com.nawforce.common.types.core.{BasicTypeDeclaration, FieldDeclaration, InnerBasicTypeDeclaration, TypeDeclaration}
import com.nawforce.common.types.platform.PlatformTypes

/** An individual component being represented as a nested type. */
final case class Component(pkg: PackageImpl, location: LocationImpl, componentName: Name)
  extends BasicTypeDeclaration(Seq(PathFactory(location.path)), pkg, TypeName(componentName, Nil, Some(TypeName(Name.Component)))) {

  override val paths: Seq[PathLike] = Seq(PathFactory(location.path))
  override val superClass: Option[TypeName] = Some(TypeName.ApexPagesComponent)
  override lazy val superClassDeclaration: Option[TypeDeclaration] = Some(PlatformTypes.componentType)
  override val fields: Seq[FieldDeclaration] = PlatformTypes.componentType.fields
}

/** Component namespace handler */
final case class ComponentDeclaration(pkg: PackageImpl, nestedComponents: Seq[TypeDeclaration])
  extends BasicTypeDeclaration(Seq.empty, pkg, TypeName.Component) {

  override def nestedTypes: Seq[TypeDeclaration] = nestedComponents ++ namespaceDeclaration.toSeq ++ Seq(cDeclaration)

  // This is the optional Component.namespace implementation
  private var namespaceDeclaration = pkg.namespace.map(ns => new NamespaceDeclaration(ns))

  // This is the Component.c implementation
  private var cDeclaration = new NamespaceDeclaration(Name.c)

  class NamespaceDeclaration(name: Name, nestedComponents: Seq[Component] = Seq())
    extends InnerBasicTypeDeclaration(Seq(), pkg, TypeName(name, Nil, Some(TypeName.Component))) {
    override def nestedTypes: Seq[TypeDeclaration] = nestedComponents

    def merge(stream: PackageStream): NamespaceDeclaration = {
      new NamespaceDeclaration(name, nestedComponents ++ stream.components.map(fe => Component(pkg, fe.location, fe.name)))
    }
  }


  /** Create new labels from merging those in the provided stream */
  def merge(stream: PackageStream): ComponentDeclaration = {
    val components = stream.components.map(fe => Component(pkg, fe.location, fe.name))
    val componentDeclaration = new ComponentDeclaration(pkg, nestedComponents ++ components)
    componentDeclaration.namespaceDeclaration.foreach(td =>
      componentDeclaration.namespaceDeclaration = Some(td.merge(stream)))
    componentDeclaration.cDeclaration = componentDeclaration.cDeclaration.merge(stream)
    componentDeclaration
  }
}

/** Component.ns implementation for exposing components from dependent packages. As the exposed components are
  * owned elsewhere there is no need to set a controller here.
  */
final class PackageComponents(pkg: PackageImpl, componentDeclaration: ComponentDeclaration)
  extends InnerBasicTypeDeclaration(Seq(), pkg,
    TypeName(componentDeclaration.packageDeclaration.get.namespace.get, Nil, Some(TypeName.Component))) {

  override def nestedTypes: Seq[TypeDeclaration] = componentDeclaration.nestedTypes
}

object ComponentDeclaration {
  val componentTypes = Seq(PlatformTypes.apexComponent, PlatformTypes.chatterComponent)

  def apply(pkg: PackageImpl): ComponentDeclaration = {
    new ComponentDeclaration(pkg, componentTypes ++ collectBaseComponents(pkg))
  }

  private def collectBaseComponents(pkg: PackageImpl): Seq[PackageComponents] = {
    // TODO: We should support ghosted packages here but that would require the ability to on-demand create
    // nested types which is not currently handled by TypeDeclaration
    pkg.transitiveBasePackages.toSeq.map(basePkg => new PackageComponents(pkg, basePkg.components))
  }
}


