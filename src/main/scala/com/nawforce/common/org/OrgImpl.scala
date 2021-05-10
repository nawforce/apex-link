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

import java.io.File
import java.util
import java.util.jar.JarFile

import com.nawforce.common.api.{FileIssueOptions, IssueOptions, Org, Package, ServerOps}
import com.nawforce.common.cst.UnusedLog
import com.nawforce.common.diagnostics._
import com.nawforce.common.documents._
import com.nawforce.common.names.{DotName, _}
import com.nawforce.common.path.PathFactory
import com.nawforce.common.workspace.{ModuleLayer, Workspace}

import scala.util.DynamicVariable
import scala.util.hashing.MurmurHash3

/** Org abstraction, a simulation of the metadata installed on an org. Use the 'current' dynamic variable to access
  * the org being currently worked on. Typically only one org will be being used but some use cases might require
  * multiple. Problems with the metadata are recorded in the the associated issue log.
  */
class OrgImpl(initWorkspace: Option[Workspace]) extends Org {

  val workspace: Workspace = initWorkspace.getOrElse(new Workspace(Seq()))

  /** Issues log for all packages in org. This is managed independently as errors may be raised against files
    * for which there is no natural type representation. */
  private[nawforce] val issues = new IssueLog

  /** Parsed Apex data cache, the cache holds summary information about Apex types to speed startup */
  private[nawforce] val parsedCache =
    ParsedCache.create(MurmurHash3.stringHash(OrgImpl.implementationBuild)) match {
      case Right(pc) => Some(pc)
      case Left(err) => LoggerOps.error(err); None
    }

  /** Lookup of available packages from the namespace (which must be unique), populated when packages created */
  var packagesByNamespace: Map[Option[Name], PackageImpl] = _

  val packages: Seq[PackageImpl] = {
    OrgImpl.current.withValue(this) {

      // Fold over layers to create packages - with any package(namespace) dependencies linked to each package
      // The workspace layers form a deploy ordering, so each is dependent on all previously created
      val packagesAndModules =
        workspace.layers.foldLeft(Seq[(PackageImpl, Seq[ModuleLayer])]())((acc, pkgLayer) => {
          val pkg = new PackageImpl(this, pkgLayer.namespace, acc.map(_._1))
          acc :+ (pkg, pkgLayer.layers)
        })

      packagesByNamespace = packagesAndModules.map(p => (p._1.namespace, p._1)).toMap

      // Fold over leaf layers to create modules of each package with dependency links, assumes everything is in deploy
      // order so dependent layers have been created before being referenced
      packagesAndModules.foldLeft(Map[ModuleLayer, Module]())((acc, packageAndDependent) => {
        val pkg = packageAndDependent._1
        val dependents = packageAndDependent._2
        acc ++ dependents.map(layer => {
          val issuesAndIndex = workspace.indexes(layer)
          issuesAndIndex.issues.foreach(issues.add)
          val module = new Module(pkg, issuesAndIndex.value, dependents.flatMap(acc.get))
          pkg.add(module)
          layer -> module
        })
      })
      val packages = packagesAndModules.map(_._1)

      // If no unmanaged, create it
      val unmanaged =
        if (packages.isEmpty || packages.lastOption.exists(_.namespace.nonEmpty))
          Seq(new PackageImpl(this, None, packages))
        else
          Seq.empty

      // Finally, freeze everything
      val all = packages ++ unmanaged
      all.foreach(_.freeze())
      all
    }
  }

  /** All orgs have an unmanaged package, it has to be the last entry in 'packages'. */
  var unmanaged: PackageImpl = packages.last

  /** Is this Org using auto-flushing of the parsedCache */
  private val autoFlush = ServerOps.getAutoFlush
  ServerOps.debug(
    ServerOps.Trace,
    s"Org created with autoFlush = $autoFlush, build = ${OrgImpl.implementationBuild}")

  /** The Org flusher */
  private val flusher =
    if (autoFlush) new CacheFlusher(this, parsedCache) else new Flusher(this, parsedCache)

  def getPackages(): Array[Package] = packages.toArray[Package]

  /** Check to see if cache has been flushed */
  override def isDirty(): Boolean = flusher.isDirty

  /** Write dirty metadata to the cache, only works for manual flush orgs */
  def flush(): Boolean = {
    if (!autoFlush)
      flusher.refreshAndFlush()
    else
      false
  }

  /** Queue a metadata refresh request */
  def queueMetadataRefresh(request: RefreshRequest): Unit = {
    flusher.queue(request)
  }

  /** Collect all issues into a String log */
  override def getIssues(options: IssueOptions): String = {
    OrgImpl.current.withValue(this) {
      reportableIssues(options).asString(options.includeWarnings,
                                         options.maxErrorsPerFile,
                                         options.format)
    }
  }

  /** Collect file specific issues */
  def getFileIssues(fileName: String, options: FileIssueOptions): Array[Issue] = {
    val path = PathFactory(fileName).toString
    OrgImpl.current.withValue(this) {
      val fileIssues = new IssueLog()
      fileIssues.push(path, issues.getIssues.getOrElse(path, Nil))

      if (options.includeZombies) {
        propagateAllDependencies()
        packagesByNamespace.values.foreach(pkg => {
          Option(pkg.getTypeOfPath(path))
            .flatMap(typeId => pkg.modules.head.findModuleType(typeId.typeName))
            .foreach(typeDecl => fileIssues.merge(new UnusedLog(Iterable(typeDecl))))
        })
      }
      fileIssues.getIssues.getOrElse(path, Nil).toArray
    }
  }

  def reportableIssues(options: IssueOptions): IssueLog = {
    if (options.includeZombies) {
      propagateAllDependencies()
      val allIssues = IssueLog(issues)
      packages
        .filterNot(_.isGhosted)
        .foreach(pkg => {
          allIssues.merge(pkg.orderedModules.head.reportUnused())
        })
      allIssues
    } else {
      issues
    }
  }

  private def propagateAllDependencies(): Unit = {
    // This is lazy evaluated in classes so safe to call again
    packages.foreach(pkg => {
      pkg.propagateAllDependencies()
    })
  }

  /** Extract all dependencies */
  override def getDependencies: java.util.Map[String, Array[String]] = {
    OrgImpl.current.withValue(this) {
      val dependencies = new util.HashMap[String, Array[String]]()
      packages
        .filterNot(_.isGhosted)
        .foreach(_.orderedModules.head.populateDependencies(dependencies))
      dependencies
    }
  }

  /** Find a location for an identifier */
  override def getIdentifierLocation(identifier: String): PathLocation = {
    val typeName = DotName(identifier).asTypeName()
    var loc: Option[PathLocation] = None

    // Extract namespace
    val namespace =
      typeName.outer
        .map(_ => typeName.outerName)
        .orElse({
          val triggerPattern = """__sfdc_trigger/(.*)/.*""".r
          typeName.name.value match {
            case triggerPattern(ns) => Some(Name(ns))
            case _                  => None
          }
        })

    // Package lookup
    namespace.foreach(
      n =>
        loc =
          packagesByNamespace.get(Some(n)).flatMap(_.orderedModules.head.getTypeLocation(typeName)))

    // Otherwise try unmanaged
    if (loc.isEmpty) {
      loc = unmanaged.orderedModules.head.getTypeLocation(typeName)
    }

    loc.orNull
  }

  /** Find a TypeIdentifier */
  def getIdentifier(identifier: String): Option[TypeIdentifier] = {
    val typeName = DotName(identifier).asTypeName()

    // Extract namespace
    val namespace =
      typeName.outer
        .map(_ => typeName.outerName)
        .orElse({
          val triggerPattern = """__sfdc_trigger/(.*)/.*""".r
          typeName.name.value match {
            case triggerPattern(ns) => Some(Name(ns))
            case _                  => None
          }
        })

    packagesByNamespace.get(namespace).map(pkg => TypeIdentifier(pkg.namespace, typeName))
  }

  /** Dump current issues to standard out */
  private[nawforce] def dumpIssues(): Unit = issues.dump()
}

object OrgImpl {

  lazy val implementationBuild: String = {
    val sourceURI = classOf[OrgImpl].getProtectionDomain.getCodeSource.getLocation.toURI
    if (!sourceURI.toString.endsWith(".jar"))
      ""
    else {
      val manifest = new JarFile(new File(sourceURI)).getManifest
      Option(manifest.getMainAttributes.getValue("Implementation-Build")).getOrElse("")
    }
  }

  /** Access the in-scope Org */
  private[nawforce] val current: DynamicVariable[OrgImpl] = new DynamicVariable[OrgImpl](null)

  /** Log an issue against the in-scope org */
  private[nawforce] def log(issue: Issue): Unit = {
    OrgImpl.current.value.issues.add(issue)
  }

  /** Log a general error against the in-scope org */
  // TODO: Remove this in favour of passing issues around
  private[nawforce] def logError(pathLocation: PathLocation, message: String): Unit = {
    OrgImpl.current.value.issues
      .add(new Issue(pathLocation.path, Diagnostic(ERROR_CATEGORY, pathLocation.location, message)))
  }
}
