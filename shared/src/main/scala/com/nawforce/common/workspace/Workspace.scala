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

import com.nawforce.common.diagnostics.{CatchingLogger, IssueLogger, IssuesAnd, Location}
import com.nawforce.common.path.PathFactory
import com.nawforce.common.sfdx.{MDAPIWorkspaceConfig, SFDXProject, SFDXWorkspaceConfig, WorkspaceConfig}
import com.nawforce.common.stream.PackageEvent

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
case class Workspace(layers: Seq[Layer]) {
}

object Workspace {
  def apply(wsPath: String): IssuesAnd[Option[Workspace]] = {
    val logger = new CatchingLogger
    val workspace = Workspace(wsPath, logger)
    IssuesAnd(logger.issues, workspace)
  }

  def apply(wsPath: String, logger: IssueLogger): Option[Workspace] = {
    val path = PathFactory(wsPath)
    if (!path.exists || !path.isDirectory) {
      logger.logError(path, Location.empty, "No directory at $path")
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
