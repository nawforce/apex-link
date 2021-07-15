/*
 Copyright (c) 2019 Kevin Jones, All rights reserved.
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
 */

package com.nawforce.apexlink.org

import com.nawforce.apexlink.api.{FileIssueOptions, IssueOptions, Org, Package, ServerOps}
import com.nawforce.apexlink.cst.UnusedLog
import com.nawforce.apexlink.deps.DownWalker
import com.nawforce.apexlink.rpc.{DependencyGraph, DependencyLink, DependencyNode, LocationLink}
import com.nawforce.apexlink.types.apex.ApexDeclaration
import com.nawforce.apexlink.types.core.TypeDeclaration
import com.nawforce.pkgforce.diagnostics._
import com.nawforce.pkgforce.documents._
import com.nawforce.pkgforce.names.{Name, TypeIdentifier}
import com.nawforce.pkgforce.path.PathFactory
import com.nawforce.pkgforce.workspace.{ModuleLayer, Workspace}
import com.nawforce.runtime.parsers.CodeParser

import java.io.File
import java.util
import java.util.jar.JarFile
import scala.collection.mutable.ArrayBuffer
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
      case Left(err) => LoggerOps.info(err); None
    }

  /** Is this Org using auto-flushing of the parsedCache. */
  private val autoFlush = ServerOps.getAutoFlush

  /** The Org flusher. */
  private val flusher =
    if (autoFlush) new CacheFlusher(this, parsedCache) else new Flusher(this, parsedCache)

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
        dependents.foldLeft(acc)((acc, layer) => {
          val issuesAndIndex = workspace.indexes(layer)
          issuesAndIndex.issues.foreach(issues.add)
          val module = new Module(pkg, issuesAndIndex.value, dependents.flatMap(acc.get))
          pkg.add(module)
          acc + (layer -> module)
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
      CodeParser.clearCaches()
      all
    }
  }

  /** After loading packages we want to flush, but flushing depends on the package list being available. */
  private val initialFlush =
    if (autoFlush)
      flusher.refreshAndFlush()

  /** All orgs have an unmanaged package, it has to be the last entry in 'packages'. */
  val unmanaged: PackageImpl = packages.last

  /** Get all loaded packages. */
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

  /** CHeck for errors in the log. */
  override def hasErrors(): Boolean = issues.hasErrors

  /** Collect all issues into a String log */
  override def getIssues(options: IssueOptions): String = {
    OrgImpl.current.withValue(this) {
      reportableIssues(options).asString(options.includeWarnings,
                                         options.includeZombies,
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
          pkg
            .getTypeOfPathInternal(PathFactory(path))
            .flatMap(typeId => typeId.module.findModuleType(typeId.typeName))
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
          pkg.orderedModules.foreach(module => {
            allIssues.merge(module.reportUnused())
          })
        })
      allIssues
    } else {
      issues
    }
  }

  def getPackageForPath(path: String): Package = {
    packages.find(_.isPackagePath(path)).orNull
  }

  private def propagateAllDependencies(): Unit = {
    // This is lazy evaluated in classes so safe to call again
    packages.foreach(pkg => {
      pkg.propagateAllDependencies()
    })
  }

  /** Get a array of type identifiers available across all packages. */
  def getTypeIdentifiers(apexOnly: Boolean): Array[TypeIdentifier] = {
    OrgImpl.current.withValue(this) {
      packages.foldLeft(Array[TypeIdentifier]())((acc, pkg) => acc ++ pkg.getTypeIdentifiers(apexOnly))
    }
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
  override def getIdentifierLocation(identifier: TypeIdentifier): PathLocation = {
    OrgImpl.current.withValue(this) {
      (findTypeIdentifier(identifier) match {
        case Some(ad: ApexDeclaration) => Some(PathLocation(ad.path.toString, ad.nameLocation))
        case _                         => None
      }).orNull
    }
  }

  /** Search package modules for the TypeDeclaration matching a TypeIdentifier. */
  def findTypeIdentifier(identifier: TypeIdentifier): Option[TypeDeclaration] = {
    packagesByNamespace
      .get(identifier.namespace)
      .flatMap(pkg => {
        pkg.orderedModules.view.flatMap(_.packageType(identifier.typeName)).headOption
      })
  }

  def getDependencyGraph(identifier: TypeIdentifier, depth: Integer, apexOnly: Boolean): DependencyGraph = {
    OrgImpl.current.withValue(this) {
      val depWalker = new DownWalker(this, apexOnly)
      val nodeData = depWalker
        .walk(identifier, depth)
        .map(n => {
          DependencyNode(n.id, nodeFileSize(n.id), n.nature, n.transitiveCount, n.extending, n.implementing, n.using)
        })

      val nodeIndex = nodeData.map(_.identifier).zipWithIndex.toMap

      val linkData = new ArrayBuffer[DependencyLink]()
      nodeData.foreach(n => {
        val source = nodeIndex(n.identifier)

        def safeLink(nature: String)(identifier: TypeIdentifier): Unit = {
          nodeIndex
            .get(identifier)
            .foreach(target => if (source != target) linkData += DependencyLink(source, target, nature))
        }

        n.extending.foreach(safeLink("extends"))
        n.implementing.foreach(safeLink("implements"))
        n.using.foreach(safeLink("uses"))
      })

      DependencyGraph(nodeData, linkData.toArray)
    }
  }

  private def nodeFileSize(identifier: TypeIdentifier): Int = {
    Option(getIdentifierLocation(identifier))
      .map(location => PathFactory(location.path).size.toInt)
      .getOrElse(0)
  }

  /** Locate a definition for a symbol */
  def getDefinition(path: String, line: Int, offset: Int, content: String): Array[LocationLink] = {
    packages.find(_.isPackagePath(path)).map(_.getDefinition(PathFactory(path), line, offset, Option(content))).getOrElse(Array.empty)
  }
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
