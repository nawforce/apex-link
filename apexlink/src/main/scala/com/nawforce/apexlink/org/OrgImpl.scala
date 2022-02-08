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

import com.nawforce.apexlink.api.{Org, Package, ServerOps, TypeSummary}
import com.nawforce.apexlink.deps.{DownWalker, TransitiveCollector}
import com.nawforce.apexlink.plugins.PluginsManager
import com.nawforce.apexlink.rpc._
import com.nawforce.apexlink.types.apex.ApexDeclaration
import com.nawforce.apexlink.types.core.TypeDeclaration
import com.nawforce.pkgforce.diagnostics._
import com.nawforce.pkgforce.documents._
import com.nawforce.pkgforce.modifiers.{ISTEST_ANNOTATION, TEST_METHOD_MODIFIER}
import com.nawforce.pkgforce.names.{Name, TypeIdentifier}
import com.nawforce.pkgforce.path.{PathLike, PathLocation}
import com.nawforce.pkgforce.workspace.{ModuleLayer, Workspace}
import com.nawforce.runtime.parsers.CodeParser
import com.nawforce.runtime.platform.Path

import java.io.File
import java.util
import java.util.jar.JarFile
import scala.collection.immutable.ArraySeq
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.DynamicVariable
import scala.util.hashing.MurmurHash3

/** Org abstraction, a simulation of the metadata installed on an org. Use the 'current' dynamic variable to access
  * the org being currently worked on. Typically only one org will be being used but some use cases might require
  * multiple. Problems with the metadata are recorded in the the associated issue log.
  */
class OrgImpl(val path: PathLike, initWorkspace: Option[Workspace]) extends Org {
  val workspace: Workspace = initWorkspace.getOrElse(new Workspace(Seq()))

  /** Issues log for all packages in org. This is managed independently as errors may be raised against files
    * for which there is no natural type representation.
    */
  private[nawforce] val issueManager = new IssuesManager

  /** Manager for post validation plugins */
  private[nawforce] val pluginsManager = new PluginsManager

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
        val pkg        = packageAndDependent._1
        val dependents = packageAndDependent._2
        dependents.foldLeft(acc)((acc, layer) => {
          val issuesAndIndex = workspace.indexes(layer)
          issuesAndIndex.issues.foreach(issueManager.add)
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

  /** Provide access to IssueManager for org */
  override def issues: IssuesManager = issueManager

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

  def getPackageForPath(path: String): Package = {
    packages.find(_.isPackagePath(path)).orNull
  }

  /** Get a array of type identifiers available across all packages. */
  def getTypeIdentifiers(apexOnly: Boolean): Array[TypeIdentifier] = {
    OrgImpl.current.withValue(this) {
      packages.foldLeft(Array[TypeIdentifier]())(
        (acc, pkg) => acc ++ pkg.getTypeIdentifiers(apexOnly)
      )
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
        case Some(ad: ApexDeclaration) => Some(PathLocation(ad.location.path, ad.idLocation))
        case _                         => None
      }).orNull
    }
  }

  /** Search package modules for the TypeDeclaration matching a TypeIdentifier. */
  def findTypeIdentifier(identifier: TypeIdentifier): Option[TypeDeclaration] = {
    packagesByNamespace
      .get(identifier.namespace)
      .flatMap(pkg => {
        pkg.orderedModules.view.flatMap(_.moduleType(identifier.typeName)).headOption
      })
  }

  def getDependencyGraph(
    identifiers: Array[TypeIdentifier],
    depth: Integer,
    apexOnly: Boolean,
    ignoring: Array[TypeIdentifier]
  ): DependencyGraph = {
    OrgImpl.current.withValue(this) {
      val depWalker = new DownWalker(this, apexOnly)
      val nodeData = depWalker
        .walk(identifiers, depth, ignoring)
        .map(n => {
          DependencyNode(
            n.id,
            nodeFileSize(n.id),
            n.nature,
            n.transitiveCount,
            n.extending,
            n.implementing,
            n.using
          )
        })
      val nodeIndex = nodeData.map(_.identifier).zipWithIndex.toMap

      val linkData = new ArrayBuffer[DependencyLink]()
      nodeData.foreach(n => {
        val source = nodeIndex(n.identifier)

        def safeLink(nature: String)(identifier: TypeIdentifier): Unit = {
          nodeIndex
            .get(identifier)
            .foreach(
              target => if (source != target) linkData += DependencyLink(source, target, nature)
            )
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
      .map(location => location.path.size.toInt)
      .getOrElse(0)
  }

  /** Locate a definition for a symbol */
  override def getDefinition(
    path: String,
    line: Int,
    offset: Int,
    content: String
  ): Array[LocationLink] = {
    if (path == null)
      return Array.empty

    OrgImpl.current.withValue(this) {
      packages
        .find(_.isPackagePath(path))
        .map(_.getDefinition(Path(path), line, offset, Option(content)))
        .getOrElse(Array.empty)
    }
  }

  override def getCompletionItems(
    path: String,
    line: Int,
    offset: Int,
    content: String
  ): Array[CompletionItemLink] = {
    getCompletionItemsInternal(Path(path), line, offset, content)
  }

  def getCompletionItemsInternal(
    path: PathLike,
    line: Int,
    offset: Int,
    content: String
  ): Array[CompletionItemLink] = {
    if (path == null || content == null)
      return Array.empty

    OrgImpl.current.withValue(this) {
      packages
        .find(_.isPackagePathInternal(path))
        .map(_.getCompletionItems(path, line, offset, content))
        .getOrElse(Array.empty)
    }
  }

  def getDependencyBombs(count: Int): Array[BombScore] = {
    val maxBombs   = Math.max(0, count)
    val allClasses = packages.flatMap(_.orderedModules.flatMap(_.nonTestClasses.toSeq))
    val bombs      = mutable.PriorityQueue[BombScore]()(Ordering.by(1000 - _.score))
    allClasses.foreach(cls => {
      if (!cls.inTest) {
        val score = cls.bombScore(allClasses.size)
        if (score._3 > 0)
          bombs.enqueue(BombScore(cls.typeId.asTypeIdentifier, score._2, score._1, score._3))
        if (bombs.size > maxBombs) {
          bombs.dequeue()
        }
      }
    })
    bombs.dequeueAll.toArray.reverse
  }

  def getTestClassNames(paths: Array[String], findTests: Boolean): Array[String] = {
    def findPackageIdentifierAndSummary(
      path: String
    ): Option[(Package, TypeIdentifier, TypeSummary)] = {
      packages.view
        .flatMap(pkg => {
          Option(pkg.getTypeOfPath(path))
            .flatMap(
              typeId =>
                Option(pkg.getSummaryOfType(typeId))
                  .map(summary => (pkg, typeId, summary))
            )
        })
        .headOption
    }

    def findReferencedTestPaths(
      pkg: Package,
      typeId: TypeIdentifier,
      summary: TypeSummary,
      filterTypeId: TypeIdentifier
    ): Array[String] = {
      if (summary.modifiers.contains(ISTEST_ANNOTATION)) return Array(summary.name)
      if (!findTests) return Array.empty

      Option(pkg.getDependencyHolders(typeId, apexOnly = true)).getOrElse(Array.empty).flatMap {
        dependentTypeId =>
          Option(pkg.getSummaryOfType(dependentTypeId)).toArray
            .filter { dependentSummary =>
              dependentSummary.modifiers.contains(ISTEST_ANNOTATION)
            }
            .filter { _ =>
              pkg.hasDependency(dependentTypeId, filterTypeId)
            }
            .map { dependentSummary =>
              dependentSummary.name
            }
      }
    }

    def targetsForInterfaces(
      pkg: Package,
      summary: TypeSummary
    ): ArraySeq[(TypeIdentifier, TypeIdentifier, TypeSummary)] = {
      summary.interfaces.flatMap { interface =>
        Option(pkg.getTypeIdentifier(interface))
          .flatMap { interfaceTypeId =>
            val outerTypeId =
              interfaceTypeId.typeName.outer.map(pkg.getTypeIdentifier).getOrElse(interfaceTypeId)
            Option(pkg.getSummaryOfType(outerTypeId))
              .map((interfaceTypeId, outerTypeId, _))
          }
      }
    }

    def targetsForSuperclass(
      pkg: Package,
      summary: TypeSummary
    ): Array[(TypeIdentifier, TypeIdentifier, TypeSummary)] = {
      summary.superClass
        .flatMap { tn =>
          Option(pkg.getTypeIdentifier(tn))
            .flatMap { tid =>
              Option(pkg.getSummaryOfType(tid))
                .flatMap { summary =>
                  val otid = tid.typeName.outer.map(pkg.getTypeIdentifier).getOrElse(tid)
                  Some(Array((tid, otid, summary)) ++ targetsForInterfaces(pkg, summary))
                }
            }
        }
        .getOrElse(Array.empty)
    }

    paths.flatMap { path =>
      findPackageIdentifierAndSummary(path).toArray.flatMap {
        case (pkg, typeId, summary) =>
          val interfaces = targetsForInterfaces(pkg, summary)
          val nestedInterfaces = summary.nestedTypes.flatMap { nestedSummary =>
            targetsForInterfaces(pkg, nestedSummary)
          }
          val superClassTargets = targetsForSuperclass(pkg, summary)

          val targets =
            Seq((typeId, typeId, summary)) ++ interfaces ++ nestedInterfaces ++ superClassTargets

          targets.flatMap {
            case (actualTypeId, outerTypeId, outerSummary) =>
              findReferencedTestPaths(pkg, outerTypeId, outerSummary, actualTypeId)
          }
      }
    }.distinct
  }

  def getDependencyCounts(paths: Array[String]): Array[(String, Int)] = {

    def getTypeOfPath(path: String): Option[TypeIdentifier] =
      packages.view.flatMap(pkg => Option(pkg.getTypeOfPath(path))).headOption

    def countTransitiveDependencies(
      typeId: TypeIdentifier,
      transitiveDependencies: Array[TypeIdentifier]
    ): Int = {
      transitiveDependencies.count(t => t != typeId)
    }

    val collector = new TransitiveCollector(this, true)

    paths
      .flatMap { path =>
        getTypeOfPath(path)
          .map { typeId =>
            (typeId, collector.transitives(typeId))
          }
          .map {
            case (typeId, transitiveDependencies) =>
              (path, countTransitiveDependencies(typeId, transitiveDependencies))
          }
      }
  }

  def getAllTestMethods: Array[TestMethod] = {
    val allClasses = packages.flatMap(_.orderedModules.flatMap(_.testClasses.toSeq))

    allClasses
      .flatMap(
        c =>
          c.methods
            .filter(
              m =>
                m.modifiers.contains(ISTEST_ANNOTATION) || m.modifiers
                  .contains(TEST_METHOD_MODIFIER)
            )
            .map(m => TestMethod(c.name.toString(), m.name.toString()))
      )
      .toSet
      .toArray
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
    if (issue.path != null)
      OrgImpl.current.value.issueManager.add(issue)
  }

  /** Log a general error against the in-scope org */
  private[nawforce] def logError(pathLocation: PathLocation, message: String): Unit = {
    log(new Issue(pathLocation.path, Diagnostic(ERROR_CATEGORY, pathLocation.location, message)))
  }
}
