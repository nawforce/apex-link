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

import com.nawforce.common.names.Name
import com.nawforce.common.path.PathLike

/** Project metadata is modeled as an ordered sequence of layers. There are various types of layer which may in turn
 * reference other lower layers, see individual types for details on how they are employed. The layers should be
 * ordered by deploy order, this means external/ghosted layers will be defined first, followed by namespace layers which
 * encapsulate package layers in packageDependency order.
 *
 * FUTURE: This model does not support layers that may be extracted from an Org or dependencies between external layers.
 * The latter can be supported within the existing package handling but there has not yet been a need to support
 * via sfdx-project.json.
 */
sealed trait Layer
sealed trait MetadataLayer extends Layer

/** A namespace layer provides the namespace for some list of layers. The list will be empty for 'ghosted' packages
 * where only knowledge of a namespace is provided. For 1GP packages only a single child layer may be provided, in 2GP
 * this is relaxed to allow multiple layers to be enclosed within the same namespace. Namespaces must of course be
 * unique within any sequence of layers.
 */
case class NamespaceLayer(namespace: Option[Name], layers: List[MetadataLayer]) extends Layer

/** A package layer encompass a packageDirectory from sfdx-project.json. In 1GP it may not have any child dependencies,
 * while in 2GP it may depend on other layers, the 2GP dependencies here will always be a reference to another layer
 * already defined in the overall ordered sequence of Layers. */
case class PackageLayer(path: PathLike, dependencies: Seq[MetadataLayer]) extends MetadataLayer

/** External layers model metadata available outside the project, they are also defined in sfdx-project.json but as
 * part of the custom "$.plugin.dependencies" property. External layers may only model 1GP projects, this also happens
 * to enforce the constraint that 2GP packages may depend on 1GP packages but not the reverse. */
case class ExternalLayer(path: PathLike) extends MetadataLayer
