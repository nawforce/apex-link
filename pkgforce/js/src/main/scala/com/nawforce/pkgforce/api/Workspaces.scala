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
package com.nawforce.pkgforce.api

import com.nawforce.pkgforce.names.DotName
import com.nawforce.pkgforce.path.PathFactory
import com.nawforce.pkgforce.workspace.{Workspace => SWorkspace}

import scala.collection.mutable
import scala.scalajs.js
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("WorkspaceException")
class WorkspaceException(val message: String) extends Exception(message)

@JSExportTopLevel("Workspace")
class Workspace(val workspace: SWorkspace) {

  @JSExport
  def findType(name: String): js.Array[String] = {
    workspace.get(DotName(name).asTypeName()).map(_.path.toString).toJSArray
  }
}

@JSExportTopLevel("Workspaces")
object Workspaces {
  private val workspaces = new mutable.HashMap[String, Workspace]()

  @JSExport
  def get(wsPath: String): Workspace = {

    val ws = workspaces.get(wsPath)
    if (ws.nonEmpty)
      return ws.get

    val issuesAndWorkspace = SWorkspace(PathFactory(wsPath))
    if (issuesAndWorkspace.issues.nonEmpty) {
      throw new WorkspaceException(issuesAndWorkspace.issues.head.asString)
    }

    issuesAndWorkspace.value
      .map(workspace => {
        val jsWorkspace = new Workspace(workspace)
        workspaces.put(wsPath, new Workspace(workspace))
        jsWorkspace
      })
      .orNull
  }
}
