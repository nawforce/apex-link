/*
 [The "BSD licence"]
 Copyright (c) 2021 Kevin Jones
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
package com.nawforce.common.workspace

import com.nawforce.common.diagnostics.{CatchingLogger, IssuesAnd}
import com.nawforce.common.documents.DocumentIndex
import com.nawforce.common.names.Name
import com.nawforce.common.path.PathLike

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
    layers.foldLeft(Map[ModuleLayer, IssuesAnd[DocumentIndex]]())((acc, layer) =>
      acc + (layer -> layer.index(namespace)))
}

/** A package layer encompasses a packageDirectory from sfdx-project.json or a 1GP style MDAPI metadata directory. The
 * dependencies should only reference layers defined within the same NamespaceLayer. */
case class ModuleLayer(projectPath: PathLike, path: PathLike, dependencies: Seq[ModuleLayer]) {
  def index(namespace: Option[Name]): IssuesAnd[DocumentIndex] = {
    val logger = new CatchingLogger
    val index = DocumentIndex(logger, namespace, projectPath, path)
    IssuesAnd(logger.issues, index)
  }
}
