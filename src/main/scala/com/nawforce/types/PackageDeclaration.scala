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
import java.util.concurrent.ConcurrentHashMap

import com.nawforce.api.Package
import com.nawforce.documents.DocumentLoader
import com.nawforce.finding.TypeFinder
import com.nawforce.finding.TypeRequest.TypeRequest
import com.nawforce.names.{EncodedName, Name, TypeName}

abstract class PackageDeclaration(val namespace: Option[Name], val paths: Seq[Path], var basePackages: Seq[PackageDeclaration])
  extends TypeFinder {
  protected val documents = new DocumentLoader(paths)
  protected val types = new ConcurrentHashMap[TypeName, TypeDeclaration]()

  def documentsByExtension(ext: Name): Seq[Path] = documents.getByExtension(ext)

  def isGhosted: Boolean = paths.isEmpty
  def hasGhosted: Boolean = isGhosted || basePackages.exists(_.hasGhosted)
  def any(): AnyDeclaration
  def schema(): SchemaManager
  def labels(): LabelDeclaration
  def pages(): PageDeclaration

  /* Set of namespaces used by this package and its base packages */
  lazy val namespaces: Set[Name] = {
    namespace.toSet ++ basePackages.flatMap(_.namespaces) ++ PlatformTypeDeclaration.namespaces
  }

  /* Check if a type is ghost in this package */
  def isGhostedType(typeName: TypeName): Boolean = {
    if (typeName.outer.contains(TypeName.Schema)) {
      val encName = EncodedName(typeName.name)
      basePackages.filter(_.isGhosted).exists(_.namespace == encName.namespace)
    } else {
      basePackages.filter(_.isGhosted).exists(_.namespace.contains(typeName.outerName)) ||
      typeName.params.exists(isGhostedType(_))
    }
  }

  def addDependency(pkg: Package): Unit = {
    // TODO: Make immutable
    basePackages = basePackages :+ pkg
  }

  /* Find a package/platform type. For general needs don't call this direct, use TypeRequest which will delegate here
   * if needed. This is the fallback handling for the TypeFinder which performs local searching for types, so this is
   * only useful if *you* know local searching is not required.
   */
  def getType(typeName: TypeName, from: Option[TypeDeclaration], excludeSObjects: Boolean = false): TypeRequest = {

    if (!excludeSObjects) {
      var td = getPackageType(typeName).map(Right(_))
      if (td.nonEmpty)
        return td.get

      if (namespace.nonEmpty && typeName.outerName != namespace.get) {
        td = getPackageType(typeName.withTail(TypeName(namespace.get))).map(Right(_))
        if (td.nonEmpty)
          return td.get
      }
    }

    // From may be used to locate type variable types so must be accurate even for a platform type request
    PlatformTypes.get(typeName, from, excludeSObjects)
  }

  // TODO: Remove this
  def getTypeOption(typeName: TypeName, from: Option[TypeDeclaration]): Option[TypeDeclaration] = {
    getType(typeName, from).toOption
  }

  // TODO: Remove this
  def getTypeOption(typeName: TypeName): Option[TypeDeclaration] = {
    getType(typeName, None).toOption
  }

  private def getPackageType(typeName: TypeName, inPackage: Boolean=true): Option[TypeDeclaration] = {
    var declaration = findType(typeName)
    if (declaration.nonEmpty) {
      if (inPackage || declaration.get.isExternallyVisible)
        return declaration
      else
        return None
    }

    if (typeName.outer.nonEmpty) {
      declaration = getPackageType(typeName.outer.get, inPackage = inPackage).flatMap(
        _.nestedTypes.find(td => td.name == typeName.name && (td.isExternallyVisible || inPackage)))
      if (declaration.nonEmpty)
        return declaration
    }

    declaration = getDependentPackageType(typeName)
    if (declaration.nonEmpty)
      return declaration

    None
  }

  private def findType(typeName: TypeName): Option[TypeDeclaration] = {
    var declaration = Option(types.get(typeName))
    if (declaration.nonEmpty)
      return declaration

    declaration = Option(types.get(typeName.withTail(TypeName.Schema)))
    if (declaration.nonEmpty)
      return declaration

    if (typeName.params.isEmpty && (typeName.outer.isEmpty || typeName.outer.contains(TypeName.Schema))) {
      val encName = EncodedName(typeName.name).defaultNamespace(namespace)
      if (encName.ext.nonEmpty) {
        return Option(types.get(TypeName(encName.fullName, Nil, Some(TypeName.Schema))))
      }
    }
    None
  }

  private def getDependentPackageType(typeName: TypeName): Option[TypeDeclaration] = {
    basePackages.view.flatMap(pkg => pkg.getPackageType(typeName, inPackage = false)).headOption
  }

  def upsertType(declaration: TypeDeclaration): Unit = {
    upsertType(declaration.typeName, declaration)
  }

  protected def upsertType(typeName: TypeName, declaration: TypeDeclaration): Unit = {
    types.put(typeName, declaration)
  }
}
