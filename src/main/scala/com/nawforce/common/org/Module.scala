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

package com.nawforce.common.org

import com.nawforce.common.cst.UnusedLog
import com.nawforce.common.diagnostics.{IssueLog, PathLocation}
import com.nawforce.common.documents._
import com.nawforce.common.finding.TypeFinder
import com.nawforce.common.finding.TypeResolver.TypeResponse
import com.nawforce.common.modifiers.GLOBAL_MODIFIER
import com.nawforce.common.names.{EncodedName, TypeNames, _}
import com.nawforce.common.types.apex.{ApexClassDeclaration, ApexDeclaration}
import com.nawforce.common.types.core.{TypeDeclaration, TypeId}
import com.nawforce.common.types.other.{InterviewDeclaration, _}
import com.nawforce.common.types.platform.PlatformTypes
import com.nawforce.common.types.schema.SchemaManager

import scala.collection.mutable

class Module(val pkg: PackageImpl, val index: DocumentIndex, dependents: Seq[Module])
    extends TypeFinder
    with ModuleDeploy {

  val namespace: Option[Name] = pkg.namespace
  val baseModules: Seq[Module] = dependents.reverse
  val basePackages: Seq[PackageImpl] = pkg.basePackages.reverse

  private[nawforce] val types = mutable.Map[TypeName, TypeDeclaration]()
  private[nawforce] val schemaManager = new SchemaManager(this)
  private[nawforce] val anyDeclaration = AnyDeclaration(this)

  var labels: LabelDeclaration = _
  var pages: PageDeclaration = _
  var interviews: InterviewDeclaration = _
  var components: ComponentDeclaration = _

  def typeCount: Int = types.size

  initTypes()

  private def initTypes(): Unit = {
  }

  def any(): AnyDeclaration = anyDeclaration
  def schema(): SchemaManager = schemaManager

  /* Transitive Bases (dependent modules for this modules & its dependents) */
  def transitiveBaseModules: Set[Module] = {
    namespace
      .map(_ => dependents.toSet ++ dependents.flatMap(_.transitiveBaseModules))
      .getOrElse(baseModules.toSet)
  }

  /** Check all summary types have propagated their dependencies */
  def propagateAllDependencies(): Unit = {
    types.values.foreach({
      case ad: ApexClassDeclaration => ad.propagateAllDependencies()
      case _                        => ()
    })
  }

  /* Iterator over available types */
  def getTypes: Iterable[TypeDeclaration] = {
    types.values
  }

  /* All available types */
  def getApexTypeIdentifiers: Set[String] = {
    types.values.collect { case ad: ApexDeclaration => ad }.map(_.typeName.toString()).toSet
  }

  /* Search for a specific outer or inner type */
  def packageType(typeName: TypeName): Option[TypeDeclaration] = {
    types
      .get(typeName)
      .orElse(
        typeName.outer
          .flatMap(types.get)
          .flatMap(_.nestedTypes.find(_.typeName == typeName)))
  }

  def replaceType(typeName: TypeName, typeDeclaration: Option[TypeDeclaration]): Unit = {
    if (typeDeclaration.nonEmpty) {
      val td = typeDeclaration.get
      types.put(typeName, td)

      // Handle special cases
      typeName match {
        case TypeNames.Label =>
          labels = td.asInstanceOf[LabelDeclaration]
          types.put(TypeName(labels.name), labels)
        case TypeNames.Page      => pages = td.asInstanceOf[PageDeclaration]
        case TypeNames.Interview => interviews = td.asInstanceOf[InterviewDeclaration]
        case TypeNames.Component => components = td.asInstanceOf[ComponentDeclaration]
        case _                   => ()
      }

    } else {
      types.remove(typeName)
    }
  }

  def isGhostedType(typeName: TypeName): Boolean = pkg.isGhostedType(typeName)

  /* Check if a field name is ghosted in this package */
  def isGhostedFieldName(name: Name): Boolean = pkg.isGhostedFieldName(name)

  /* Find a document location for the type */
  def getTypeLocation(typeName: TypeName): Option[PathLocation] = {
    packageType(typeName) match {
      case Some(ad: ApexDeclaration) => Some(ad.nameLocation)
      case _                         => None
    }
  }

  // Upsert some metadata to the package
  def upsertMetadata(td: TypeDeclaration, altTypeName: Option[TypeName] = None): Unit = {
    types.put(altTypeName.getOrElse(td.typeName), td)
  }

  // Remove some metadata from the package
  // Future: Support component & flow removal
  def removeMetadata(td: TypeDeclaration): Unit = {
    types.remove(td.typeName)
  }

  /** Obtain log with unused metadata warnings */
  def reportUnused(): IssueLog = {
    new UnusedLog(types.values)
  }

  // Add dependencies for Apex types to a map
  def populateDependencies(dependencies: java.util.Map[String, Array[String]]): Unit = {
    types.values.foreach {
      case td: ApexClassDeclaration =>
        val depends = mutable.Set[TypeId]()
        td.collectDependenciesByTypeName(depends)
        depends.remove(td.typeId)
        if (depends.nonEmpty)
          dependencies.put(td.typeName.toString, depends.map(_.typeName.toString).toArray)
      case _ => ()
    }
  }

  /* Find a package/platform type. For general needs don't call this direct, use TypeRequest which will delegate here
   * if needed. This is the fallback handling for the TypeFinder which performs local searching for types, so this is
   * only useful if you know local searching is not required.
   */
  def findType(typeName: TypeName,
               from: Option[TypeDeclaration],
               excludeSObjects: Boolean = false): TypeResponse = {

    // Find locally, or fallback to a package search
    def getPackageType(typeName: TypeName, inPackage: Boolean = true): Option[TypeDeclaration] = {
      var declaration = findModuleType(typeName)
      if (declaration.nonEmpty) {
        if (inPackage || declaration.get.modifiers.contains(GLOBAL_MODIFIER))
          return declaration
        else
          return None
      }

      if (typeName.outer.nonEmpty) {
        declaration = getPackageType(typeName.outer.get, inPackage = inPackage)
          .flatMap(_.findNestedType(typeName.name).filter(td =>
            td.isExternallyVisible || inPackage))
        if (declaration.nonEmpty)
          return declaration
      }

      basePackages.view.flatMap(pkg => pkg.getPackageType(typeName)).headOption
    }

    if (!excludeSObjects) {
      var td = getPackageType(typeName).map(Right(_))
      if (td.nonEmpty)
        return td.get

      if (namespace.nonEmpty) {
        td = getPackageType(typeName.withTail(TypeName(namespace.get))).map(Right(_))
        if (td.nonEmpty)
          return td.get
      }
    }

    // From may be used to locate type variable types so must be accurate even for a platform type request
    PlatformTypes.get(typeName, from, excludeSObjects)
  }

  /** Find a type in this module. */
  def findModuleType(typeName: TypeName): Option[TypeDeclaration] = {
    var declaration = types.get(typeName)
    if (declaration.nonEmpty)
      return declaration

    declaration = types.get(typeName.withTail(TypeNames.Schema))
    if (declaration.nonEmpty)
      return declaration

    if (typeName.params.isEmpty && (typeName.outer.isEmpty || typeName.outer.contains(
          TypeNames.Schema))) {
      val encName = EncodedName(typeName.name).defaultNamespace(namespace)
      if (encName.ext.nonEmpty) {
        return types.get(TypeName(encName.fullName, Nil, Some(TypeNames.Schema)))
      }
    }
    baseModules.reverse.view.flatMap(_.findModuleType(typeName)).headOption
  }
}
