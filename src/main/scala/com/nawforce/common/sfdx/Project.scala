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

import com.nawforce.common.api.Name
import com.nawforce.common.names._
import com.nawforce.common.path.PathLike
import ujson.Value

class ProjectError(message: String) extends Throwable(message)

class Project(projectPath: PathLike, config: Value.Value) {
  val packageDirectories: Seq[PackageDirectory] =
    try {
      config("packageDirectories") match {
        case ujson.Arr(value) => value.map(pd => new PackageDirectory(projectPath, pd))
        case _ => throw new ProjectError("'packageDirectories' should be an array")
      }
    } catch {
      case _: NoSuchElementException => throw new ProjectError("'packageDirectories' is required")
    }

  val namespace: Option[Name] =
    try {
      val ns = config("namespace") match {
        case ujson.Str(value) => Name(value)
        case _ => throw new ProjectError("'namespace' should be a string")
      }
      if (ns.isEmpty)
        None
      else {
        ns.isLegalIdentifier match {
          case None => Some(ns)
          case Some(error) => throw new ProjectError(s"namespace '$ns' is not valid, $error")
        }
      }
    } catch {
      case _: NoSuchElementException => None
    }

  val plugins: Map[String, Value.Value] =
    try {
      config("plugins") match {
        case ujson.Obj(value) => value.toMap
        case _ => throw new ProjectError("'plugins' should be an object")
      }
    } catch {
      case _: NoSuchElementException => Map()
    }

  val projectOptions: Option[ProjectOptions] =
    plugins.get("apexlink").map(v => new ProjectOptions(projectPath, v))
}

object Project {
  def apply(path: PathLike): Either[String, Project] = {
    val projectFile = path.join("sfdx-project.json")
    if (!projectFile.isFile) {
      Left(s"Missing project file at $projectFile")
    } else {
      projectFile.read() match {
        case Left(err) => Left(err)
        case Right(data) =>
          try {
            Right(new Project(path, ujson.read(data)))
          } catch {
            case ex: ProjectError =>
              Left(s"Failed to parse '$projectFile', error: ${ex.getMessage}")
            case ex: Throwable =>
              Left(s"Failed to parse '$projectFile', error: ${ex.toString}")
          }
      }
    }
  }
}
