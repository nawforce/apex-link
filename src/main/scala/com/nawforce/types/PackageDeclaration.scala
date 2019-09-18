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

package com.nawforce.types

import java.nio.file.Path

import com.nawforce.documents.DocumentLoader
import com.nawforce.names.{DotName, EncodedName, Name, TypeName}

abstract class PackageDeclaration(val namespace: Option[Name], val paths: Seq[Path]) {
  private val documents = new DocumentLoader(paths)

  def documentsByExtension(ext: Name): Seq[Path] = documents.getByExtension(ext)

  def isGhosted: Boolean = paths.isEmpty
  def basePackages(): Seq[PackageDeclaration]
  def schema(): SchemaManager
  def labels(): LabelDeclaration
  def pages(): PageDeclaration

  def namespaceWithDot: String = namespace.map(_.value + ".").getOrElse("")

  def getType(dotName: DotName): Option[TypeDeclaration]

  def upsertType(declaration: TypeDeclaration): Unit

  def isGhostedName(name: Name): Boolean = {
    val decodedName = EncodedName(name)
    basePackages().filter(_.isGhosted).exists(_.namespace == decodedName.namespace)
  }

  def isGhostedType(typeName: TypeName): Boolean = {
    basePackages().filter(_.isGhosted).exists(_.namespace == typeName.outer.map(_.name))
  }

  def wrapSObject(typeName: TypeName): Option[TypeDeclaration] = {
    val tdOption = getType(typeName.asDotName)
    tdOption.filter(td => td.isSObject && td.isInstanceOf[PlatformTypeDeclaration]).map(td =>{
      val wrapped = SObjectDeclaration(this, td.typeName, td.fields, isComplete = true)
      upsertType(wrapped)
      wrapped
    })
  }
}
