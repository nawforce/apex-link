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
package com.nawforce.runtime.api

import java.nio.file.{Path, Paths}

import com.nawforce.common.api
import com.nawforce.common.diagnostics.{ERROR_CATEGORY, IssueCategory, IssueLog}
import com.nawforce.common.documents._
import com.nawforce.common.finding.TypeRequest
import com.nawforce.common.names.{DotName, Name}
import com.nawforce.common.types.TypeDeclaration
import com.nawforce.runtime.path.Path
import com.typesafe.scalalogging.LazyLogging

import scala.util.DynamicVariable

/** Org abstraction, a simulation of the metadata installed on an org. Use the 'current' dynamic variable to access
  * the org being currently worked on. Typically only one org will be being used but some use cases might require
  * multiple. Problems with the metadata are recorded in the the associated issue log.
  */
class Org extends LazyLogging {
  var unmanaged = new api.Package(this, None, Seq(), Seq())
  var packages: Map[Option[Name], api.Package] = Map(None -> unmanaged)

  val issues = new IssueLog
  def issuesAsJSON(warnings: Boolean, zombie: Boolean): String = {
    if (zombie) {
      packages.values.foreach(pkg => {
        issues.merge(pkg.reportUnused())
      })
    }
    issues.asJSON(warnings, maxErrors = 100)
  }
  def typeCount: Int= packages.values.map(_.typeCount).sum

  def getUnmanagedPackage: api.Package = unmanaged

  /** Create a new package in the org, directories should be priority ordered for duplicate detection. Use
    * namespaces to indicate dependant packages which must already have been created as packages. */
  def addPackage(namespace: String, directories: Array[String], baseNamespaces: Array[String]): api.Package = {
    val namespaceName: Option[Name] = Name.safeApply(namespace)

    if (namespaceName.nonEmpty) {
      if (packages.contains(namespaceName))
        throw new IllegalArgumentException(s"A package using namespace '$namespaceName' already exists")
    }

    val basePackages = baseNamespaces.flatMap(ns => {
      val pkg = packages.get(Some(Name(ns))).filterNot(_.namespace.isEmpty)
      if (pkg.isEmpty)
        throw new IllegalArgumentException(s"No package found using namespace '$ns'")
      pkg
    })

    val paths = directories.filterNot(_.isEmpty).map(directory => Paths.get(directory))
    paths.foreach(path => {
      if (!path.toFile.isDirectory)
        throw new IllegalArgumentException(s"Package root '${path.toString}' must be a directory")
    })

    addPackageInternal(namespaceName, paths, basePackages)
  }

  def addPackageInternal(namespace: Option[Name], paths: Seq[java.nio.file.Path], basePackages: Seq[api.Package]): api.Package = {
    Org.current.withValue(this) {
      val pkg = new api.Package(this, namespace, paths.map(p => com.nawforce.runtime.path.Path(p)), basePackages)
      if (pkg.namespace.isEmpty) {
        unmanaged = pkg
      } else {
        unmanaged.addDependency(pkg)
      }
      packages = packages + (pkg.namespace -> pkg)
      pkg
    }
  }

  def getType(namespace: String, dotName: String): TypeDeclaration = {
    getType(Name.safeApply(namespace), DotName(dotName)).orNull
  }

  def getType(namespace: Option[Name], dotName: DotName): Option[TypeDeclaration] = {
    val pkgOpt = packages.get(namespace)
    pkgOpt.flatMap(pkg => TypeRequest(dotName.asTypeName(), pkg, excludeSObjects = false).toOption)
  }

  /** Get a list of Apex types in the org*/
  def getApexTypeNames: java.util.List[String] = {
    scala.collection.JavaConverters.seqAsJavaList(packages.values.flatMap(_.getApexTypeNames).toSeq)
  }

  /** Retrieve type information for declaration. Separate compound names with a '.', e.g. 'System.String'. Returns
    * null if the type if not found */
  def getTypeInfo(name: String): TypeInfo = {
    TypeRequest(DotName(name).asTypeName(), unmanaged, excludeSObjects = false).toOption.map(td => TypeInfo(td)).orNull
  }
}

object Org {
  val current: DynamicVariable[Org] = new DynamicVariable[Org](null)

  def log(location: Location, msg: String, category: IssueCategory): Unit = {
    Org.current.value.issues.logMessage(location, msg, category)
  }

  def logMessage(location: Location, msg: String): Unit = {
    log(location, msg, ERROR_CATEGORY)
  }
}
