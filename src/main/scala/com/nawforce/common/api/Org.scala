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
package com.nawforce.common.api

import com.nawforce.common.diagnostics.{ERROR_CATEGORY, IssueCategory, IssueLog}
import com.nawforce.common.documents._
import com.nawforce.common.names.Name
import com.nawforce.common.path.{DIRECTORY, PathFactory, PathLike}
import com.nawforce.common.sfdx.{MDAPIWorkspace, Workspace}

import scala.util.DynamicVariable

/** Org abstraction, a simulation of the metadata installed on an org. Use the 'current' dynamic variable to access
  * the org being currently worked on. Typically only one org will be being used but some use cases might require
  * multiple. Problems with the metadata are recorded in the the associated issue log.
  */
class Org {
  /**
    * Map of Package namespace to Package. This contains all known Packages, each Package maintains it's own
    * list of dependent Package so that we can enforce boundaries between unrelated Packages.
    * TODO: This only support 1GP model, work needed for 2GP handling
    */
  var packages: Map[Option[Name], Package] = Map()

  /**
    * The default unmanaged package for the Org. This is created empty but can be added to or replaced with
    * another package. The unmanaged package is unique in not having any namespace and it automatically depends
    * on every other package installed in the Org.
    */
  var unmanaged: Package = {
    Org.current.withValue(this) {
      val pkg = new Package(this, new MDAPIWorkspace(None, Seq()), Seq())
      packages = Map(None -> pkg)
      pkg
    }
  }

  // TODO: Split issue log so can be persisted with source code
  val issues = new IssueLog

  /** All Packages in Org */
  def allPackages: Seq[Package] = packages.values.toSeq

  /** Collect all issues into a JSON log */
  def issuesAsJSON(warnings: Boolean, zombie: Boolean): String = {
    if (zombie) {
      packages.values.foreach(pkg => {
        issues.merge(pkg.reportUnused())
      })
    }
    issues.asJSON(warnings, maxErrors = 100)
  }

  def typeCount: Int= packages.values.map(_.typeCount).sum

  /** Create a new package in the org, directories should be priority ordered for duplicate detection. Use
    * namespaces to indicate dependent packages which must already have been created as packages. */
  def newPackage(namespace: String, directories: Array[String], baseNamespaces: Array[String]): Package = {
    val namespaceName: Option[Name] = Name.safeApply(namespace)

    val basePackages = baseNamespaces.flatMap(ns => {
      val pkg = packages.get(Some(Name(ns))).filterNot(_.namespace.isEmpty)
      if (pkg.isEmpty)
        throw new IllegalArgumentException(s"No package found using namespace '$ns'")
      pkg
    })

    val paths = directories.filterNot(_.isEmpty).map(directory => PathFactory(directory))

    addPackage(namespaceName, paths, basePackages)
  }

  /** Create a Package over a set of paths */
  private[nawforce] def addPackage(namespace: Option[Name], paths: Seq[PathLike], basePackages: Seq[Package]): Package = {
    val workspace =
      Workspace(namespace, paths) match {
        case Left(err) => throw new IllegalArgumentException(err)
        case Right(workspace) => workspace
      }
    addPackage(workspace, basePackages)
  }

  /** Create a Package over a Workspace */
  private[nawforce] def addPackage(workspace: Workspace, basePackages: Seq[Package]): Package = {
    if (workspace.namespace.nonEmpty) {
      if (packages.contains(workspace.namespace))
        throw new IllegalArgumentException(s"A package using namespace '${workspace.namespace}' already exists")
    }

    workspace.paths.foreach(path => {
      if (path.nature != DIRECTORY)
        throw new IllegalArgumentException(s"Package root '${path.toString}' must be a directory")
    })

    Org.current.withValue(this) {
      val pkg = new Package(this, workspace, basePackages)
      if (pkg.namespace.isEmpty) {
        unmanaged = pkg
      }
      packages = packages + (pkg.namespace -> pkg)
      pkg
    }
  }
}

object Org {
  /** Access the in-scope Org */
  val current: DynamicVariable[Org] = new DynamicVariable[Org](null)

  /** Unmanaged package of in-scope org */
  def unmanaged(): Package = {
    Org.current.value.unmanaged
  }

  /** All packages of in-scope org */
  def allPackages(): Seq[Package] = {
    Org.current.value.allPackages
  }

  /** Log an issue against the in-scope org */
  def log(location: Location, msg: String, category: IssueCategory): Unit = {
    Org.current.value.issues.logMessage(location, msg, category)
  }

  /** Log an error issue against the in-scope org */
  def logMessage(location: Location, msg: String): Unit = {
    log(location, msg, ERROR_CATEGORY)
  }
}
