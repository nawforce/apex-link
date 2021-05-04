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
package com.nawforce.common.sfdx

import com.nawforce.common.diagnostics._
import com.nawforce.common.names.Name
import com.nawforce.common.path.PathLike
import com.nawforce.common.workspace.{ModuleLayer, NamespaceLayer}
import ujson.Value

class SFDXProjectError(val jsonPath: String, message: String) extends Throwable(message)

case class VersionedPackageLayer(version: Option[VersionNumber], packageLayer: ModuleLayer)

class SFDXProject(val projectPath: PathLike, config: Value.Value) {
  private val projectFile = projectPath.join("sfdx-project.json")

  val packageDirectories: Seq[PackageDirectory] =
    try {
      config("packageDirectories") match {
        case ujson.Arr(value) =>
          value.toSeq.zipWithIndex.map(pd =>
            PackageDirectory(projectPath, s"$$.packageDirectories[${pd._2}]", pd._1))
        case _ =>
          throw new SFDXProjectError("$.packageDirectories",
                                     "'packageDirectories' should be an array")
      }
    } catch {
      case _: NoSuchElementException =>
        throw new SFDXProjectError("$.packageDirectories", "'packageDirectories' is required")
    }

  val namespace: Option[Name] = config.optIdentifier("$", "namespace")

  val plugins: Map[String, Value.Value] =
    try {
      config("plugins") match {
        case ujson.Obj(value) => value.toMap
        case _                => throw new SFDXProjectError("$.plugins", "'plugins' should be an object")
      }
    } catch {
      case _: NoSuchElementException => Map()
    }

  val dependencies: Seq[PackageDependent] =
    plugins.getOrElse("dependencies", ujson.Arr()) match {
      case ujson.Arr(value) =>
        value.toSeq.zipWithIndex.map(dp =>
          PackageDependent(projectPath, s"$$.plugins.dependencies[${dp._2}]", dp._1))
      case _ =>
        throw new SFDXProjectError("$.plugins.dependencies", "'dependencies' should be an array")
    }

  def layers(logger: IssueLogger): Seq[NamespaceLayer] = {
    if (packageDirectories.nonEmpty) {
      // Fold package directory entries into layers, validating as we go
      val localPackages = NamespaceLayer(
        namespace,
        packageDirectories
          .foldLeft((Map[String, VersionedPackageLayer](), List[ModuleLayer]()))(
            foldPackageDirectory(logger))
          ._2
          .reverse)

      // Fold in external 1GP dependencies if needed
      if (dependencies.nonEmpty) {
        dependencies.flatMap(dependent => packageDependentLayers(logger, dependent)) :+ localPackages
      } else {
        Seq(localPackages)
      }
    } else {
      Seq()
    }
  }

  private def packageDependentLayers(logger: IssueLogger,
                                     dependent: PackageDependent): Seq[NamespaceLayer] = {
    (dependent.namespace.nonEmpty, dependent.path.nonEmpty) match {
      case (false, false) =>
        logger.log(
          Issue(projectFile.toString,
                Diagnostic(ERROR_CATEGORY,
                           Location.empty,
                           s"${dependent.jsonPath} must include either a namespace, a path or both")))
        Seq.empty
      case (true, false) =>
        Seq(NamespaceLayer(dependent.namespace, Nil))
      case (true, true) =>
        Seq(NamespaceLayer(dependent.namespace, Seq(ModuleLayer(dependent.path.get, Seq.empty))))
      case (false, true) =>
        SFDXProject(dependent.path.get, logger)
          .map(project => {
            project.layers(logger)
          })
          .getOrElse(Seq.empty)
    }
  }

  private def foldPackageDirectory(logger: IssueLogger)(
    layers: (Map[String, VersionedPackageLayer], List[ModuleLayer]),
    packageDirectory: PackageDirectory): (Map[String, VersionedPackageLayer], List[ModuleLayer]) = {

    // Check dependencies are well formed
    val dependencies = packageDirectory.dependencies.flatMap(dependency =>
      validateDependency(layers._1, dependency, logger))
    val newLayer = VersionedPackageLayer(
      packageDirectory.version,
      ModuleLayer(packageDirectory.path, dependencies.map(_.packageLayer)))

    // For 2GP, named packages need a version as well
    if (packageDirectory.name.nonEmpty) {
      if (packageDirectory.version.isEmpty)
        logger.logError(projectFile,
                        Location.empty,
                        s"Package '${packageDirectory.name.get}' should have a versionNumber")
      (layers._1 + (packageDirectory.name.get -> newLayer), newLayer.packageLayer :: layers._2)
    } else {
      (layers._1, newLayer.packageLayer :: layers._2)
    }
  }

  private def validateDependency(current: Map[String, VersionedPackageLayer],
                                 dependency: ModuleDependent,
                                 logger: IssueLogger): Option[VersionedPackageLayer] = {
    val existing = current.get(dependency.name)
    if (existing.isEmpty)
      logger.logError(
        projectFile,
        Location.empty,
        s"Dependency '${dependency.name}' must be defined in project before being referenced")
    else if (dependency.version.isEmpty)
      logger.logError(projectFile,
                      Location.empty,
                      s"Dependency '${dependency.name}' must provide a version number")
    else if (!dependency.version.get.isCompatible(existing.get.version.get))
      logger.logError(projectFile,
                      Location.empty,
                      s"Dependency version '${dependency.version.get}' for '${dependency.name}' is not compatible with '${existing.get.version.get}'")

    existing
  }
}

object SFDXProject {
  def apply(path: PathLike, logger: IssueLogger): Option[SFDXProject] = {
    val projectFile = path.join("sfdx-project.json")
    if (!projectFile.isFile) {
      logger.logError(projectFile,
                      Location.empty,
                      s"Missing sfdx-project.json file at $projectFile")
      None
    } else {
      projectFile.read() match {
        case Left(err) => logger.logError(projectFile, Location.empty, err); None
        case Right(data) =>
          try {
            Some(new SFDXProject(path, ujson.read(data)))
          } catch {
            case ex: SFDXProjectError =>
              logger.logError(projectFile, Location.empty, s"${ex.jsonPath} - ${ex.getMessage}")
              None
            case ex: Throwable =>
              logger.logError(projectFile, Location.empty, s"Failed to parse - ${ex.toString}")
              None
          }
      }
    }
  }
}
