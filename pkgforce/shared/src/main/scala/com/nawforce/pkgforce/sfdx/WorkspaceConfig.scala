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

import com.nawforce.pkgforce.diagnostics.IssueLogger
import com.nawforce.pkgforce.names.Name
import com.nawforce.pkgforce.path.PathLike
import com.nawforce.pkgforce.workspace.{ModuleLayer, NamespaceLayer}

trait WorkspaceConfig {
  def layers(logger: IssueLogger): Seq[NamespaceLayer]
}

class MDAPIWorkspaceConfig(namespace: Option[Name], paths: Seq[PathLike]) extends WorkspaceConfig {

  override def layers(logger: IssueLogger): Seq[NamespaceLayer] =
    Seq(NamespaceLayer(namespace, paths.map(path => ModuleLayer(path, ".", Seq())).toList))

  override def toString: String =
    s"MDAPIWorkspace(namespace=$namespace, paths=${paths.map(_.toString).mkString(", ")})"
}

class SFDXWorkspaceConfig(val rootPath: PathLike, project: SFDXProject) extends WorkspaceConfig {

  override def layers(logger: IssueLogger): Seq[NamespaceLayer] = project.layers(logger)

  override def toString: String = s"SFDXWorkspace(${project.projectPath})"
}
