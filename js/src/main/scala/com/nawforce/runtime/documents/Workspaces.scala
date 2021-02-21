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
package com.nawforce.runtime.documents

import com.nawforce.common.documents.Workspace
import com.nawforce.common.path.PathFactory
import com.nawforce.common.sfdx.{MDAPIWorkspaceConfig, SFDXProject, SFDXWorkspaceConfig}

import scala.collection.mutable
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("WorkspaceException")
class JSWorkspaceException(val message: String) extends Exception(message)

@JSExportTopLevel("Workspace")
class JSWorkspace(val workspace: Workspace) {}

@JSExportTopLevel("Workspaces")
object JSWorkspaces {
  private val workspaces = new mutable.HashMap[String, JSWorkspace]()

  @JSExport
  def get(wsPath: String): JSWorkspace = {
    workspaces.getOrElseUpdate(
      wsPath, {
        val path = PathFactory(wsPath)
        if (!path.exists || !path.isDirectory)
          throw new JSWorkspaceException(s"No directory at $path")

        val config =
          if (path.join("sfdx-project.json").exists) {
            SFDXProject(path) match {
              case Left(err) =>
                throw new JSWorkspaceException(err)
              case Right(project) =>
                new SFDXWorkspaceConfig(path, project)
            }
          } else {
            new MDAPIWorkspaceConfig(None, Seq(path))
          }
        new JSWorkspace(new Workspace(config))
      })
  }
}
