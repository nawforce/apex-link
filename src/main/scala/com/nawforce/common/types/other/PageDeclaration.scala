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

import com.nawforce.common.cst.{GLOBAL_MODIFIER, Modifier, PRIVATE_MODIFIER, STATIC_MODIFIER}
import com.nawforce.common.documents.{DocumentType, LineLocation, Location, PageDocument}
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.types._
import com.nawforce.common.types.pkg.PackageDeclaration

import scala.collection.mutable

final case class PageDeclaration(pkg: PackageDeclaration, pages: Seq[Page]) extends TypeDeclaration {

  override val packageDeclaration: Option[PackageDeclaration] = Some(pkg)
  override val name: Name = Name.Page
  override val typeName: TypeName = TypeName(name)
  override val outerTypeName: Option[TypeName] = None
  override val nature: Nature = CLASS_NATURE
  override val modifiers: Seq[Modifier] = Seq.empty
  override def isComplete: Boolean = !pkg.hasGhosted
  override val isExternallyVisible: Boolean = true

  override val superClass: Option[TypeName] = None
  override val interfaces: Seq[TypeName] = Seq.empty
  override val nestedTypes: Seq[TypeDeclaration] = Seq.empty

  override val blocks: Seq[BlockDeclaration] = Seq.empty
  override val fields: Seq[FieldDeclaration]= pages
  override val constructors: Seq[ConstructorDeclaration] = Seq.empty
  override val methods: Seq[MethodDeclaration]= Seq.empty

  override def validate(): Unit = {}
}

object PageDeclaration {
  def apply(pkg: PackageDeclaration): PageDeclaration = {
    val pages = collectBasePages(pkg).values.flatten ++
      pkg.documentsByExtension(Name("page")).map(page => DocumentType(page.path)).flatMap {
        case Some(page: PageDocument) => Some(Page(LineLocation(page.path, 0), page.name))
        case _ => None
      }
    new PageDeclaration(pkg, pages.toSeq)
  }

  private def collectBasePages(pkg: PackageDeclaration, collected: mutable.Map[Name, Seq[Page]]=mutable.Map())
  : mutable.Map[Name, Seq[Page]] = {
    pkg.basePackages.foreach(basePkg => {
      val ns = basePkg.namespace.get
      if (!collected.contains(ns)) {
        val pages = basePkg.pages().pages.map(page => Page(page.location, Name(s"${ns}__${page.name}")))
        collected.put(ns, pages)
        collectBasePages(basePkg, collected)
      }
    })
    collected
  }
}

case class Page(location: Location, name: Name) extends FieldDeclaration {
  override lazy val modifiers: Seq[Modifier] = Seq(STATIC_MODIFIER, GLOBAL_MODIFIER)
  override lazy val typeName: TypeName = TypeName.PageReference
  override lazy val readAccess: Modifier = GLOBAL_MODIFIER
  override lazy val writeAccess: Modifier = PRIVATE_MODIFIER
}

