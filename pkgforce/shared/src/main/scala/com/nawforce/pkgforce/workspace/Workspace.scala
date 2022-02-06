/*
 Copyright (c) 2021 Kevin Jones, All rights reserved.
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
package com.nawforce.pkgforce.workspace

import com.nawforce.pkgforce.diagnostics.{CatchingLogger, IssueLogger, IssuesAnd}
import com.nawforce.pkgforce.documents.{DocumentIndex, MetadataDocument}
import com.nawforce.pkgforce.names.TypeName
import com.nawforce.pkgforce.path.{Location, PathLike}
import com.nawforce.pkgforce.sfdx.{
  MDAPIWorkspaceConfig,
  SFDXProject,
  SFDXWorkspaceConfig,
  WorkspaceConfig
}
import com.nawforce.pkgforce.stream.{IssuesEvent, PackageEvent, PackageStream}

/** Metadata workspace, maintains information on available metadata within a project/package.
  *
  * Duplicate detection is based on the relevant MetadataDocumentType(s) being able to generate an accurate TypeName
  * for the metadata. Where multiple metadata items may contribute to a type, e.g. labels, make sure that
  * duplicatesAllowed is set which will bypass the duplicate detection. Duplicates are reported as errors and then
  * ignored.
  *
  * During an upsert/deletion of new types the index will also need to be updated so that it maintains an accurate
  * view of the metadata files being used.
  */
case class Workspace(layers: Seq[NamespaceLayer]) {

  // Document indexes for each layer of actual metadata
  val indexes: Map[ModuleLayer, IssuesAnd[DocumentIndex]] =
    layers.foldLeft(Map[ModuleLayer, IssuesAnd[DocumentIndex]]())(
      (acc, layer) => acc ++ layer.indexes
    )

  def get(typeName: TypeName): Set[MetadataDocument] = {
    val indexes = deployOrderedIndexes.toSeq.reverse.map(_.value)
    indexes
      .find(_.get(typeName).nonEmpty)
      .map(index => {
        index.get(typeName)
      })
      .getOrElse(Set())
  }

  def events: Iterator[PackageEvent] = {
    deployOrderedIndexes.flatMap(
      index => PackageStream.eventStream(index.value) ++ IssuesEvent.iterator(index.issues)
    )
  }

  def deployOrderedIndexes: Iterator[IssuesAnd[DocumentIndex]] = {
    layers.iterator.flatMap(layer => layer.layers).flatMap(indexes.get)
  }
}

object Workspace {
  def apply(path: PathLike): IssuesAnd[Option[Workspace]] = {
    val logger    = new CatchingLogger
    val workspace = Workspace(path, logger)
    IssuesAnd(logger.issues, workspace)
  }

  def apply(path: PathLike, logger: IssueLogger): Option[Workspace] = {
    if (!path.exists || !path.isDirectory) {
      logger.logError(path, Location.empty, s"No directory at $path")
      None
    }

    val catchingLogger = new CatchingLogger()
    val config: Option[WorkspaceConfig] =
      if (path.join("sfdx-project.json").exists) {
        SFDXProject(path, catchingLogger).map(p => new SFDXWorkspaceConfig(path, p))
      } else {
        Some(new MDAPIWorkspaceConfig(None, Seq(path)))
      }
    if (catchingLogger.issues.nonEmpty) {
      catchingLogger.issues.foreach(logger.log)
      None
    }

    config.map(config => {
      val layers = config.layers(catchingLogger)
      if (catchingLogger.issues.nonEmpty) {
        catchingLogger.issues.foreach(logger.log)
        None
      }
      new Workspace(layers)
    })
  }
}
