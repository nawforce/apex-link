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

import java.util

import com.nawforce.common.api.{IssueOptions, Name, Org, Package, PathLocation, ServerOps}
import com.nawforce.common.diagnostics.{ERROR_CATEGORY, Issue, IssueLog}
import com.nawforce.common.documents._
import com.nawforce.common.names.{DotName, Names, _}
import com.nawforce.common.path.{PathFactory, PathLike}
import com.nawforce.common.sfdx.{MDAPIWorkspace, Project, SFDXWorkspace, Workspace}
import com.nawforce.common.types.apex.ApexDeclaration
import com.nawforce.common.types.core.TypeDeclaration

import scala.util.DynamicVariable

/** Org abstraction, a simulation of the metadata installed on an org. Use the 'current' dynamic variable to access
  * the org being currently worked on. Typically only one org will be being used but some use cases might require
  * multiple. Problems with the metadata are recorded in the the associated issue log.
  */
class OrgImpl extends Org {
  /**
    * Map of Package namespace to Package. This contains all known Packages, each Package maintains it's own
    * list of dependent Package so that we can enforce boundaries between unrelated Packages.
    * Future: This only supports 1GP model, work needed for 2GP handling
    */
  private[nawforce] var packagesByNamespace: Map[Option[Name], PackageImpl] = Map()

  /**
    * Issues log for all packages in org. This managed independently as errors may be raised against files
    * for which there is no natural type representation. Use issuesAsJSON to access */
  private[nawforce] val issues = new IssueLog

  /**
    * The default unmanaged package for the Org. This is created empty but can be added to or replaced with
    * another package. The unmanaged package is unique in not having any namespace and it automatically depends
    * on every other package installed in the Org.
    */
  var unmanaged: PackageImpl = {
    OrgImpl.current.withValue(this) {
      val pkg = new PackageImpl(this, new MDAPIWorkspace(None, Seq()), Seq())
      packagesByNamespace = Map(None -> pkg)
      pkg
    }
  }

  /** Current package list for Org */
  override def getPackages: Array[Package] = packagesByNamespace.values.toArray

  /** Find a specific package */
  def getPackage(namespace: Option[Name]): Option[PackageImpl] = {
    packagesByNamespace.get(namespace)
  }

  /** Create a MDAPI format package */
  override def newMDAPIPackage(namespace: String, directories: Array[String], basePackages: Array[Package]): Package = {
    addPackage(new MDAPIWorkspace(Names.safeApply(namespace), getDirectoryPaths(directories)), collectPackages(basePackages))
  }

  /** Create a SFDX format package */
  override def newSFDXPackage(directory: String, basePackages: Array[Package]): Package = {
    val path = getDirectoryPaths(Array(directory)).head
    Project(path) match {
      case Left(err) =>
        throw new IllegalArgumentException(err)
      case Right(project) =>
        addPackage(new SFDXWorkspace(path, project), collectPackages(basePackages))
    }
  }

  /** Convert directory strings to paths, checking they are valid directories */
  private def getDirectoryPaths(directories: Array[String]): Seq[PathLike] = {
    val paths = directories.map(PathFactory(_))
    val missing = paths.filterNot(_.isDirectory)
    if (missing.nonEmpty)
      throw new IllegalArgumentException(s"Workspace '${missing.head}' is not a directory")
    paths
  }

  /** Collect as PackageImpl checking they are for correct Org as we go */
  private def collectPackages(basePackages: Array[Package]): Seq[PackageImpl] = {
    basePackages.map(pkg => {
      val pkgImpl = pkg.asInstanceOf[PackageImpl]
      if (pkgImpl.org != this)
        throw new IllegalArgumentException(s"Base package '${pkgImpl.namespace.getOrElse("")}' was created for use in a different org")
      pkgImpl
    })
  }

  /** Test helper to allow passing a PathLike for virtual FS support */
  private[nawforce] def addMDAPITestPackage(namespace: Option[Name], paths: Seq[PathLike],
                                            basePackages: Seq[PackageImpl]): PackageImpl = {
    addPackage(new MDAPIWorkspace(namespace, paths), basePackages)
  }

  /** Test helper to allow passing a PathLike for virtual FS support */
  private[nawforce] def addSFDXTestPackage(path: PathLike, basePackages: Seq[PackageImpl]): PackageImpl = {
    addPackage(new SFDXWorkspace(path, Project(path).right.get), basePackages)
  }

  /** Create a Package over a Workspace */
  private[nawforce] def addPackage(workspace: Workspace, basePackages: Seq[PackageImpl]): PackageImpl = {

    if (workspace.namespace.isLeft) {
      throw new IllegalArgumentException(workspace.namespace.left.get)
    }

    val ns = workspace.namespace.getOrElse(None)
    if (ns.nonEmpty) {
      if (packagesByNamespace.contains(ns))
        throw new IllegalArgumentException(s"A package using namespace '$ns' already exists")
    }


    workspace.paths.foreach(path => {
      if (!path.isDirectory)
        throw new IllegalArgumentException(s"Package root '${path.toString}' must be a directory")
    })

    OrgImpl.current.withValue(this) {
      val pkg = new PackageImpl(this, workspace, basePackages)
      if (pkg.namespace.isEmpty) {
        unmanaged = pkg
      }
      packagesByNamespace = packagesByNamespace + (pkg.namespace -> pkg)
      pkg
    }
  }

  /** Write dirty metadata to the cache */
  def flush(): Unit = {
    ServerOps.debugTime("Org flushed") {
      OrgImpl.current.withValue(this) {
        ParsedCache.create match {
          case Left(err) => ServerOps.error(err)
          case Right(pc) => packagesByNamespace.values.foreach(_.flush(pc))
        }
      }
    }
  }

  /** Collect all issues into a JSON log */
  override def getIssues(options: IssueOptions): String = {
    OrgImpl.current.withValue(this) {
      val reportableIssues =
        if (options.includeZombies) {
          val allIssues = IssueLog(issues)
          packagesByNamespace.values.foreach(pkg => {
            pkg.propagateAllDependencies()
            allIssues.merge(pkg.reportUnused())
          })
          allIssues
        } else {
          issues
        }
      reportableIssues.asString(options.includeWarnings, options.maxErrorsPerFile, options.format)
    }
  }

  /** Extract all dependencies */
  override def getDependencies: java.util.Map[String, Array[String]] = {
    OrgImpl.current.withValue(this) {
      val dependencies = new util.HashMap[String, Array[String]]()
      packagesByNamespace.values.foreach(_.populateDependencies(dependencies))
      dependencies
    }
  }

  /** Find a specific type */
  def getTypeLocation(name: String): PathLocation = {
    val typeName = DotName(name).asTypeName()
    var td: Option[TypeDeclaration] = None

    // Extract namespace
    val namespace =
      typeName.outer.map(_ => typeName.outerName)
      .orElse({
        val triggerPattern = """__sfdc_trigger/(.*)/.*""".r
        typeName.name.value match {
          case triggerPattern(ns) => Some(Name(ns))
          case _ => None
        }
      })

    // Package lookup
    namespace.foreach(n =>
      td = packagesByNamespace.get(Some(n)).flatMap(_.searchTypes(typeName))
    )

    // Otherwise try unmanaged
    if (td.isEmpty) {
      td = unmanaged.searchTypes(typeName)
    }

    td.flatMap {
      case ad: ApexDeclaration => Some(PathLocation(ad.path.toString, ad.nameLocation.toLocation))
      case _ => None
    }.orNull
  }

  /** Dump current issues to standard out */
  private[nawforce] def dumpIssues(): Unit = issues.dump()
}

object OrgImpl {
  /** Access the in-scope Org */
  private[nawforce] val current: DynamicVariable[OrgImpl] = new DynamicVariable[OrgImpl](null)

  /** Unmanaged package of in-scope org */
  private[nawforce] def unmanaged(): PackageImpl = {
    OrgImpl.current.value.unmanaged
  }

  /** Log an issue against the in-scope org */
  private[nawforce] def log(issue: Issue): Unit = {
    OrgImpl.current.value.issues.add(issue)
  }

  /** Log a general error against the in-scope org */
  // TODO: Remove this in favour of passing issues around
  private[nawforce] def logError(location: LocationImpl, message: String): Unit = {
    OrgImpl.current.value.issues.add(new Issue(ERROR_CATEGORY, location, message))
  }
}
