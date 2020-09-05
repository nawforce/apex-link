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

import com.nawforce.common.api.{IssueOptions, LoggerOps, Name, Org, Package, PathLocation, ServerOps}
import com.nawforce.common.diagnostics.{ERROR_CATEGORY, Issue, IssueLog}
import com.nawforce.common.documents._
import com.nawforce.common.memory.IdentityBox
import com.nawforce.common.names.{DotName, Names, _}
import com.nawforce.common.path.{PathFactory, PathLike}
import com.nawforce.common.sfdx.{MDAPIWorkspace, Project, SFDXWorkspace, Workspace}

import scala.collection.immutable.ArraySeq.ofRef
import scala.collection.mutable.ArrayBuffer
import scala.util.DynamicVariable

/** Org abstraction, a simulation of the metadata installed on an org. Use the 'current' dynamic variable to access
  * the org being currently worked on. Typically only one org will be being used but some use cases might require
  * multiple. Problems with the metadata are recorded in the the associated issue log.
  */
class OrgImpl(val analysis: Boolean=true) extends Org {

  /** Parsed data cache */
  private[nawforce] val parsedCache = ParsedCache.create match {
    case Right(pc) => Some(pc)
    case Left(err) => LoggerOps.error(err); None
  }

  /** Is this Org using auto-flushing */
  private val autoFlush = ServerOps.getAutoFlush
  ServerOps.debug(ServerOps.Trace, s"Org created with autoFlush = $autoFlush")

  /** The Org flusher */
  private val flusher = if (autoFlush) new CacheFlusher(this, parsedCache) else new Flusher(this, parsedCache)

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

  /**
    * Packages in bottom-up ordering.
    */
  def orderedPackages: Seq[PackageImpl] = {
    val ordered = new ArrayBuffer[PackageImpl]()
    var unassigned = packagesByNamespace.values.map(new IdentityBox(_)).toList

    while (unassigned.nonEmpty) {
      val available = unassigned.filter(_.value.basePackages.forall(ordered.contains))
      available.foreach(b => ordered.append(b.value))
      unassigned = unassigned.filterNot(available.contains)
    }
    ordered.toSeq
  }

  /** Current package list for Org */
  override def getPackages: Array[Package] = packagesByNamespace.values.toArray

  /** Find a specific package */
  def getPackage(namespace: Option[Name]): Option[PackageImpl] = {
    packagesByNamespace.get(namespace)
  }

  /** Create a MDAPI format package */
  override def newMDAPIPackage(namespace: String, directories: Array[String], basePackages: Array[Package]): Package = {
    newMDAPIPackageInternal(Names.safeApply(namespace), new ofRef(directories).map(PathFactory(_)), new ofRef(basePackages))
  }

  private[nawforce] def newMDAPIPackageInternal(namespace: Option[Name], directories: Seq[PathLike],
                                                basePackages: Seq[Package]): PackageImpl = {
    addPackage(new MDAPIWorkspace(namespace, getDirectoryPaths(directories)), collectPackages(basePackages))
  }

  /** Create a SFDX format package */
  override def newSFDXPackage(directory: String): Package = {
    newSFDXPackageInternal(PathFactory(directory))
  }

  /** Create a SFDX format package */
  private[nawforce] def newSFDXPackageInternal(directory: PathLike): PackageImpl = {
    val path = getDirectoryPaths(Seq(directory)).head
    Project(path) match {
      case Left(err) =>
        throw new IllegalArgumentException(err)
      case Right(project) =>
        addPackage(new SFDXWorkspace(path, project), resolveDependentProjects(project))
    }
  }

  /** Find or create dependent packages for a project */
  private def resolveDependentProjects(project: Project): Seq[PackageImpl] = {
    project.dependencies.map(pkg => {
      packagesByNamespace.getOrElse(Some(pkg.namespace), {
        if (pkg.path.isEmpty || !pkg.path.get.join("sfdx-project.json").exists)
          newMDAPIPackageInternal(Some(pkg.namespace), pkg.path.toSeq, Seq())
        else
          newSFDXPackageInternal(pkg.path.get)
      })
    })
  }

  /** Check paths are directories */
  private def getDirectoryPaths(paths: Seq[PathLike]): Seq[PathLike] = {
    val missing = paths.filterNot(_.isDirectory)
    if (missing.nonEmpty)
      throw new IllegalArgumentException(s"Workspace '${missing.head}' is not a directory")
    paths
  }

  /** Collect as PackageImpl checking they are for correct Org as we go */
  private def collectPackages(basePackages: Seq[Package]): Seq[PackageImpl] = {
    basePackages.map(pkg => {
      val pkgImpl = pkg.asInstanceOf[PackageImpl]
      if (pkgImpl.org != this)
        throw new IllegalArgumentException(s"Base package '${pkgImpl.namespace.getOrElse("")}' was created for use in a different org")
      pkgImpl
    })
  }

  /** Create a Package over a Workspace */
  private[nawforce] def addPackage(workspace: Workspace, basePackages: Seq[PackageImpl]): PackageImpl = {

    val ns = workspace.namespace
    if (ns.nonEmpty) {
      if (packagesByNamespace.contains(ns))
        throw new IllegalArgumentException(s"A package using namespace '$ns' already exists")
    } else if (!unmanaged.isGhosted) {
      throw new IllegalArgumentException("An \"unmanaged\" package using an empty namespace already exists")
    }

    OrgImpl.current.withValue(this) {
      val pkg = new PackageImpl(this, workspace, basePackages)
      if (pkg.namespace.isEmpty) {
        unmanaged = pkg
      }
      packagesByNamespace = packagesByNamespace + (pkg.namespace -> pkg)
      if (autoFlush)
        flusher.refreshAndFlush()
      pkg
    }
  }

  def isFlushed(): Boolean = {
    flusher.isFlushed
  }

  /** Write dirty metadata to the cache, only works for manual flush orgs */
  def flush(): Boolean = {
    if (!autoFlush)
      flusher.refreshAndFlush()
    else
      false
  }

  /** Queue a refresh request */
  def queueRefresh(request: RefreshRequest): Unit = {
    flusher.queue(request)
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

  /** Find a location for an identifier */
  override def getIdentifierLocation(identifier: String): PathLocation = {
    val typeName = DotName(identifier).asTypeName()
    var loc: Option[PathLocation] = None

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
      loc = packagesByNamespace.get(Some(n)).flatMap(_.getTypeLocation(typeName))
    )

    // Otherwise try unmanaged
    if (loc.isEmpty) {
      loc = unmanaged.getTypeLocation(typeName)
    }

    loc.orNull
  }

  /** Dump current issues to standard out */
  private[nawforce] def dumpIssues(): Unit = issues.dump()
}

object OrgImpl {
  /** Access the in-scope Org */
  private[nawforce] val current: DynamicVariable[OrgImpl] = new DynamicVariable[OrgImpl](null)

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
