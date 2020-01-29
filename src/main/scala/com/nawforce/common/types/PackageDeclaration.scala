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

package com.nawforce.common.types

import com.nawforce.common.api.Org
import com.nawforce.common.cst.UnusedLog
import com.nawforce.common.diagnostics.IssueLog
import com.nawforce.common.documents.{ComponentDocument, DocumentIndex, MetadataDocumentType}
import com.nawforce.common.finding.TypeRequest.TypeRequest
import com.nawforce.common.finding.{TypeFinder, TypeRequest}
import com.nawforce.common.metadata.MetadataDeclaration
import com.nawforce.common.names.{EncodedName, Name, TypeName}
import com.nawforce.common.sfdx.Workspace
import com.nawforce.common.types.other._
import com.nawforce.common.types.platform.PlatformTypes
import com.nawforce.common.types.schema.SchemaManager
import com.nawforce.runtime.types.PlatformTypeDeclaration

import scala.collection.mutable

abstract class PackageDeclaration(val workspace: Workspace, bases: Seq[PackageDeclaration])
  extends TypeFinder {
  val namespace: Option[Name] = workspace.namespace
  protected val documents = new DocumentIndex(workspace.paths, workspace.ignorePath)
  protected val types: mutable.Map[TypeName, TypeDeclaration] = mutable.Map[TypeName, TypeDeclaration]()
  protected val other: mutable.Map[Name, MetadataDeclaration] = mutable.Map[Name, MetadataDeclaration]()

  private val schemaManager = new SchemaManager(this)
  private val anyDeclaration = AnyDeclaration(this)
  private val labelDeclaration = LabelDeclaration(this)
  private val pageDeclaration = PageDeclaration(this)
  private val flowDeclaration = FlowDeclaration(this)
  private val componentDeclaration = ComponentDeclaration(this)
  initTypes()

  private def initTypes(): Unit = {
    upsertMetadata(anyDeclaration)
    upsertMetadata(schemaManager.sobjectTypes)
    upsertMetadata(schemaManager.sobjectTypes, Some(TypeName(schemaManager.sobjectTypes.name)))
    upsertMetadata(labelDeclaration)
    upsertMetadata(labelDeclaration, Some(TypeName(labelDeclaration.name)))
    upsertMetadata(pageDeclaration)
    upsertMetadata(flowDeclaration)
    upsertMetadata(componentDeclaration)
  }

  def documentsByExtension(ext: Name): Seq[MetadataDocumentType] = documents.getByExtension(ext)

  def isGhosted: Boolean = workspace.paths.isEmpty
  def hasGhosted: Boolean = isGhosted || basePackages.exists(_.hasGhosted)
  def any(): AnyDeclaration = anyDeclaration
  def schema(): SchemaManager = schemaManager
  def labels(): LabelDeclaration = labelDeclaration
  def pages(): PageDeclaration = pageDeclaration

  def basePackages: Seq[PackageDeclaration] = {
    // Override for unmanaged package to allow to be immutable
    if (namespace.isEmpty)
      Org.allPackages().filter(_.namespace.nonEmpty)
    else
      bases
  }

  /* Set of namespaces used by this package and its base packages */
  lazy val namespaces: Set[Name] = {
    workspace.namespace.toSet ++ basePackages.flatMap(_.namespaces) ++ PlatformTypeDeclaration.namespaces
  }

  /* Check if a type is ghosted in this package */
  def isGhostedType(typeName: TypeName): Boolean = {
    if (typeName.outer.contains(TypeName.Schema)) {
      val encName = EncodedName(typeName.name)
      basePackages.filter(_.isGhosted).exists(_.namespace == encName.namespace)
    } else {
      basePackages.filter(_.isGhosted).exists(_.namespace.contains(typeName.outerName)) ||
      typeName.params.exists(isGhostedType)
    }
  }

  // TODO: Would be nice to standardise this special case
  def upsertComponent(namespace: Option[Name], component: ComponentDocument): Unit = {
    componentDeclaration.upsertComponent(namespace, component)
  }

  /* Upsert some metadata
   * TODO: How are we going to handle invalidation
   */
  def upsertMetadata(md: MetadataDeclaration, altTypeName: Option[TypeName]=None): Unit = {
    md match {
      case td: TypeDeclaration if td.isSearchable =>
        types.put(altTypeName.getOrElse(td.typeName), td)
      case _ =>
        other.put(md.internalName, md)
    }
  }

  /** Obtain log with unused metadata warnings */
  def reportUnused(): IssueLog = {
    new UnusedLog(types.values)
  }

  /* Current count of known types */
  def typeCount: Int = types.size

  /* Find package accessible type(s) */
  def findTypes(typeNames: Seq[TypeName]): Seq[TypeDeclaration] = {
    typeNames.flatMap(typeName => TypeRequest(typeName, this, excludeSObjects = false).toOption)
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
    var declaration = types.get(typeName)
    if (declaration.nonEmpty)
      return declaration

    declaration = types.get(typeName.withTail(TypeName.Schema))
    if (declaration.nonEmpty)
      return declaration

    if (typeName.params.isEmpty && (typeName.outer.isEmpty || typeName.outer.contains(TypeName.Schema))) {
      val encName = EncodedName(typeName.name).defaultNamespace(namespace)
      if (encName.ext.nonEmpty) {
        return types.get(TypeName(encName.fullName, Nil, Some(TypeName.Schema)))
      }
    }
    None
  }

  private def getDependentPackageType(typeName: TypeName): Option[TypeDeclaration] = {
    basePackages.view.flatMap(pkg => pkg.getPackageType(typeName, inPackage = false)).headOption
  }
}
