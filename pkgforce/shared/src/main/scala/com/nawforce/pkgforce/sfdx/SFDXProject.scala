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
package com.nawforce.pkgforce.sfdx

import com.nawforce.pkgforce.diagnostics._
import com.nawforce.pkgforce.documents.MetadataDocument
import com.nawforce.pkgforce.names.Name
import com.nawforce.pkgforce.path.{Location, PathLike}
import com.nawforce.pkgforce.workspace.{ModuleLayer, NamespaceLayer}
import com.nawforce.pkgforce.diagnostics.Duplicates.IterableOps
import ujson.Value

import scala.annotation.tailrec
import scala.util.{Failure, Success, Try}

class SFDXProjectError(val line: Int, val offset: Int, message: String) extends Throwable(message)

object SFDXProjectError {
  def apply(lineAndOffset: (Int, Int), message: String) =
    new SFDXProjectError(lineAndOffset._1, lineAndOffset._2, message)
}

case class VersionedPackageLayer(version: Option[VersionNumber], packageLayer: ModuleLayer)

class SFDXProject(val projectPath: PathLike, config: ValueWithPositions) {
  private val projectFile = projectPath.join("sfdx-project.json")
  private val localLogger = new CatchingLogger

  val sourceApiVersion: Option[String] =
    try {
      config.root("sourceApiVersion") match {
        case value: ujson.Str => Some(value.value)
        case value =>
          config
            .lineAndOffsetOf(value)
            .map(lineAndOffset => {
              throw SFDXProjectError(lineAndOffset, "'sourceApiVersion' should be a string")
            })
            .getOrElse(None)
      }
    } catch {
      case _: NoSuchElementException => None
    }

  val packageDirectories: Seq[PackageDirectory] =
    try {
      config.root("packageDirectories") match {
        case value: ujson.Arr =>
          value.value.map(pd => PackageDirectory(projectPath, config, pd)).toSeq
        case value =>
          config
            .lineAndOffsetOf(value)
            .map(lineAndOffset => {
              throw SFDXProjectError(lineAndOffset, "'packageDirectories' should be an array")
            })
            .getOrElse(Seq.empty)
      }
    } catch {
      case _: NoSuchElementException =>
        config
          .lineAndOffsetOf(config.root)
          .map(lineAndOffset => {
            throw SFDXProjectError(lineAndOffset, "'packageDirectories' is required")
          })
          .getOrElse(Seq.empty)
    }

  val namespace: Option[Name] = config.root.optIdentifier(config, "namespace")

  val plugins: Map[String, Value.Value] =
    try {
      config.root("plugins") match {
        case value: ujson.Obj => value.value.toMap
        case value =>
          config
            .lineAndOffsetOf(value)
            .map(lineAndOffset => {
              throw SFDXProjectError(lineAndOffset, "'plugins' should be an object")
            })
            .getOrElse(Map.empty)
      }
    } catch {
      case _: NoSuchElementException => Map()
    }

  val dependencies: Seq[PackageDependent] =
    plugins.getOrElse("dependencies", ujson.Arr()) match {
      case value: ujson.Arr =>
        value.value.toSeq.zipWithIndex.map(dp => PackageDependent(projectPath, config, dp._1))
      case value =>
        config
          .lineAndOffsetOf(value)
          .map(lineAndOffset => {
            throw SFDXProjectError(lineAndOffset, "'dependencies' should be an array")
          })
          .getOrElse(Seq.empty)
    }

  val unpackagedMetadata: Seq[PackageDirectory] =
    plugins.getOrElse("unpackagedMetadata", ujson.Arr()) match {
      case value: ujson.Arr =>
        value.value.toSeq.map(
          value => PackageDirectory.fromUnpackagedMetadata(projectPath, config, value)
        )
      case value =>
        config
          .lineAndOffsetOf(value)
          .map(lineAndOffset => {
            throw SFDXProjectError(lineAndOffset, "'unpackagedMetadata' should be an array")
          })
          .getOrElse(Seq.empty)
    }

  val additionalNamespaces: Array[Option[Name]] =
    plugins.getOrElse("additionalNamespaces", ujson.Arr()) match {
      case value: ujson.Arr =>
        val namespaces = value.value.toSeq
          .flatMap(value => {
            value match {
              case ujson.Str(value) => Some(value)
              case _ =>
                config
                  .lineAndOffsetOf(value)
                  .map(
                    lineAndOffset =>
                      throw SFDXProjectError(
                        lineAndOffset,
                        "'additionalNamespaces' entries should all be strings"
                      )
                  )
                None
            }
          })
          .map {
            case "unmanaged" => None
            case ns          => Some(Name(ns))
          }
        val dups = namespaces.duplicates(_.getOrElse("unmanaged"))
        if (dups.nonEmpty) {
          config
            .lineAndOffsetOf(value)
            .map(
              lineAndOffset =>
                throw SFDXProjectError(
                  lineAndOffset,
                  s"namespace '${dups.head._1.getOrElse("unmanaged")}' is duplicated in additionalNamespaces'"
                )
            )
        }
        namespaces.toArray
      case value =>
        config
          .lineAndOffsetOf(value)
          .map(lineAndOffset => {
            throw SFDXProjectError(lineAndOffset, "'additionalNamespaces' should be an array")
          })
          .getOrElse(Array.empty)
    }

  val maxDependencyCount: Option[Int] = {
    plugins.get("maxDependencyCount") match {
      case None => None
      case Some(value: ujson.Num) if value.toString.matches("[0-9]+") =>
        Try(value.toString().toInt) match {
          case Success(value) => Some(value)
          case Failure(_) =>
            config
              .lineAndOffsetOf(value)
              .map(lineAndOffset => {
                throw SFDXProjectError(
                  lineAndOffset,
                  s"'maxDependencyCount' value '${value.toString}' is not an integer"
                )
              })
              .getOrElse(None)
        }
      case Some(value) =>
        config
          .lineAndOffsetOf(value)
          .map(lineAndOffset => {
            throw SFDXProjectError(
              lineAndOffset,
              s"'maxDependencyCount' value '${value.toString}' should be a positive integer"
            )
          })
          .getOrElse(None)
    }
  }

  private val gulpPackages = additionalNamespaces.flatMap(ns => {
    createGulpPath(ns) match {
      case Left(err) =>
        config
          .lineAndOffsetOf(plugins.get("additionalNamespaces"))
          .map(lineAndOffset => throw SFDXProjectError(lineAndOffset, err))
          .getOrElse(None)
        None
      case Right(path) if ns != namespace =>
        Some(NamespaceLayer(ns, isGulped = true, Seq(ModuleLayer(projectPath, path, Seq()))))
      case Right(_) => None
    }
  })

  private val extendedPackageDirectories = packageDirectories ++ unpackagedMetadata

  private val externalPackages =
    dependencies.flatMap(dependent => packageDependentLayers(localLogger, dependent))

  def metadataGlobs: Seq[String] = {
    val glob = MetadataDocument.extensionsGlob
    (extendedPackageDirectories.map(packageDirectory => packageDirectory.relativePath) ++
      externalPackages.flatMap(_.layers.map(layer => layer.pathRelativeTo(projectPath))) ++
      additionalNamespaces.map(gulpPath).map(pathParts => pathParts.mkString("/")))
      .map(prefix => s"$prefix/**/*.$glob")
  }

  def layers(logger: IssueLogger): Seq[NamespaceLayer] = {
    logger.logAll(localLogger.issues)

    if (packageDirectories.isEmpty) {
      config
        .lineAndOffsetOf(config.root("packageDirectories"))
        .foreach(lineAndOffset => {
          logger.log(
            Issue(
              projectFile,
              Diagnostic(
                ERROR_CATEGORY,
                Location(lineAndOffset._1, lineAndOffset._2),
                s"packageDirectories must have at least one entry"
              )
            )
          )
        })
      return Seq.empty
    }

    val localModules = extendedPackageDirectories
      .foldLeft((Map[String, VersionedPackageLayer](), List[ModuleLayer]()))(
        foldPackageDirectory(logger)
      )
      ._2
      .reverse

    val gulpLocalModule =
      if (additionalNamespaces.contains(namespace))
        Seq(ModuleLayer(projectPath, gulpPath(namespace).mkString("/"), Seq()))
      else
        Seq()

    val localPackage = NamespaceLayer(namespace, isGulped = false, gulpLocalModule ++ localModules)

    if (!validatePackagePathsLocal(localPackage.layers, logger))
      return Seq.empty

    val layers = externalPackages ++ gulpPackages :+ localPackage

    if (layers.map(_.namespace).toSet.size != layers.size) {
      logger.log(
        Issue(
          projectFile,
          Diagnostic(
            ERROR_CATEGORY,
            dependencies.head.location,
            s"plugin additionalNamespaces/dependencies must use unique namespaces"
          )
        )
      )
      return Seq.empty
    }

    layers
  }

  private def gulpPath(namespace: Option[Name]): Seq[String] = {
    Seq(".apexlink", "gulp", namespace.map(_.value).getOrElse("unmanaged"))
  }

  private def createGulpPath(namespace: Option[Name]): Either[String, String] = {
    @tailrec
    def createDirectoryPath(root: PathLike, parts: Seq[String]): Either[String, PathLike] = {
      if (parts.isEmpty) return Right(root)

      val next = root.join(parts.head)
      if (next.isDirectory)
        createDirectoryPath(next, parts.tail)
      else
        root.createDirectory(parts.head) match {
          case Left(err)   => Left(err)
          case Right(path) => createDirectoryPath(path, parts.tail)
        }
    }

    val pathParts = gulpPath(namespace)
    createDirectoryPath(projectPath, pathParts)
    Right(pathParts.mkString("/"))
  }

  private def validatePackagePathsLocal(modules: Seq[ModuleLayer], logger: IssueLogger): Boolean = {
    val enclosingPath = projectPath.toString
    val escaping = modules.flatMap(module => {
      if (!module.path.toString.startsWith(enclosingPath)) Some(module.path) else None
    })
    escaping.foreach(
      path =>
        logger.log(
          Issue(
            projectFile,
            Diagnostic(
              ERROR_CATEGORY,
              Location.empty,
              s"Package directory '$path' is not within the project directory '$projectPath'"
            )
          )
        )
    )
    escaping.isEmpty
  }

  private def packageDependentLayers(
    logger: IssueLogger,
    dependent: PackageDependent
  ): Seq[NamespaceLayer] = {
    (dependent.namespace.nonEmpty, dependent.relativePath.nonEmpty) match {
      case (false, false) =>
        logger.log(
          Issue(
            projectFile,
            Diagnostic(
              ERROR_CATEGORY,
              dependent.location,
              s"plugin dependencies must include either a namespace, a path or both"
            )
          )
        )
        Seq.empty
      case (true, false) =>
        Seq(NamespaceLayer(dependent.namespace, isGulped = false, Nil))
      case (true, true) =>
        Seq(
          NamespaceLayer(
            dependent.namespace,
            isGulped = false,
            Seq(ModuleLayer(projectPath.join(dependent.relativePath.get), ".", Seq.empty))
          )
        )
      case (false, true) =>
        SFDXProject(projectPath.join(dependent.relativePath.get), logger)
          .map(project => {
            project.layers(logger)
          })
          .getOrElse(Seq.empty)
    }
  }

  private def foldPackageDirectory(logger: IssueLogger)(
    layers: (Map[String, VersionedPackageLayer], List[ModuleLayer]),
    packageDirectory: PackageDirectory
  ): (Map[String, VersionedPackageLayer], List[ModuleLayer]) = {

    // Check dependencies are well formed
    val dependencies = packageDirectory.dependencies.flatMap(
      dependency => validateDependency(layers._1, dependency, logger)
    )
    val newLayer = VersionedPackageLayer(
      packageDirectory.version,
      ModuleLayer(projectPath, packageDirectory.relativePath, dependencies.map(_.packageLayer))
    )

    // For 2GP, named packages need a version as well
    if (packageDirectory.name.nonEmpty) {
      if (packageDirectory.version.isEmpty)
        logger.logError(
          projectFile,
          packageDirectory.location,
          s"Package '${packageDirectory.name.get}' should have a versionNumber"
        )
      (layers._1 + (packageDirectory.name.get -> newLayer), newLayer.packageLayer :: layers._2)
    } else {
      (layers._1, newLayer.packageLayer :: layers._2)
    }
  }

  private def validateDependency(
    current: Map[String, VersionedPackageLayer],
    dependency: ModuleDependent,
    logger: IssueLogger
  ): Option[VersionedPackageLayer] = {
    val existing = current.get(dependency.name)
    if (existing.isEmpty) {
      if (dependency.name.contains('@')) {
        logger.logWarning(
          projectFile,
          dependency.location,
          s"Package dependency for '${dependency.name}' ignored as metadata is not available for analysis."
        )
      } else {
        logger.logWarning(
          projectFile,
          dependency.location,
          s"Dependency '${dependency.name}' must be defined in project before being referenced"
        )
      }
    } else if (dependency.version.isEmpty) {
      logger.logWarning(
        projectFile,
        dependency.location,
        s"Dependency '${dependency.name}' must provide a version number"
      )
    } else if (!dependency.version.get.isCompatible(existing.get.version.get)) {
      logger.logWarning(
        projectFile,
        dependency.location,
        s"Dependency version '${dependency.version.get}' for '${dependency.name}' is not compatible with '${existing.get.version.get}'"
      )
    }
    existing
  }
}

object SFDXProject {
  def apply(path: PathLike, logger: IssueLogger): Option[SFDXProject] = {
    val projectFile = path.join("sfdx-project.json")
    if (!projectFile.isFile) {
      logger.logError(
        projectFile,
        Location.empty,
        s"Missing sfdx-project.json file at $projectFile"
      )
      None
    } else {
      projectFile.read() match {
        case Left(err) => logger.logError(projectFile, Location.empty, err); None
        case Right(data) =>
          try {
            Some(new SFDXProject(path, PositionParser.parse(data)))
          } catch {
            case ex: SFDXProjectError =>
              logger.logError(projectFile, Location(ex.line, ex.offset), ex.getMessage)
              None
            case ex: Throwable =>
              logger.logError(projectFile, Location.empty, s"Failed to parse - ${ex.toString}")
              None
          }
      }
    }
  }
}
