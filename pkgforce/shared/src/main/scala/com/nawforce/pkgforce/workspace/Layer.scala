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

import com.nawforce.pkgforce.diagnostics.{CatchingLogger, IssuesAnd}
import com.nawforce.pkgforce.documents.DocumentIndex
import com.nawforce.pkgforce.names.Name
import com.nawforce.pkgforce.path.PathLike

/** Project metadata is modeled as an ordered sequence of namespace layers which contain an ordered sequence of module
  * layers. The layers should be ordered by deploy order, this means external layers defined via $.plugins.dependencies
  * will appear first before the namespace layer for the target project. Namespaces must be unique, which means you
  * can not use dependencies to reference a second sfdx-project.json using the same namespace, all 2GP modules must be
  * contained in a single sfdx-project.json.
  */
sealed trait Layer

/** A namespace layer provides the namespace for some list of module layers. The list will be empty for 'ghosted' packages
  * where only knowledge of a namespace is provided. In a 1GP package each layer will depend on its predecessor, with
  * 2GP the layer dependencies are declared.
  */
case class NamespaceLayer(namespace: Option[Name], layers: Seq[ModuleLayer]) extends Layer {
  def indexes: Map[ModuleLayer, IssuesAnd[DocumentIndex]] =
    layers.foldLeft(Map[ModuleLayer, IssuesAnd[DocumentIndex]]())(
      (acc, layer) => acc + (layer -> layer.index(namespace))
    )
}

/** A package layer encompasses a packageDirectory from sfdx-project.json or a 1GP style MDAPI metadata directory. The
  * dependencies should only reference layers defined within the same NamespaceLayer.
  */
case class ModuleLayer(
  projectPath: PathLike,
  relativePath: String,
  dependencies: Seq[ModuleLayer]
) {

  val path: PathLike = projectPath.join(relativePath)

  def index(namespace: Option[Name]): IssuesAnd[DocumentIndex] = {
    val logger = new CatchingLogger
    val index  = DocumentIndex(logger, namespace, projectPath, path)
    IssuesAnd(logger.issues, index)
  }
}
