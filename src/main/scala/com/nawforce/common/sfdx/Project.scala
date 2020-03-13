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

import com.nawforce.common.names.Name
import com.nawforce.common.path.PathLike
import ujson.{Arr, Value}

class Project(config: Value.Value) {
  lazy val packageDirectories: Seq[Value.Value] =
    try {
      config("packageDirectories").asInstanceOf[Arr].value
    } catch {
      case _: Throwable => Arr().value
    }

  lazy val paths: Seq[Either[String, String]] = {
    packageDirectories.map(dir => {
      try {
        Right(dir("path").str)
      } catch {
        case err: Throwable =>
          Left(s"Expecting all 'path' properties to be strings in packageDirectories, error: $err")
      }
    })
  }

  lazy val namespace: Option[Name] =
    try {
      Some(Name(config("namespace").str))
    } catch {
      case _: Throwable => None
    }
}

object Project {
  def apply(path: PathLike): Either[String, Option[Project]] = {
    val projectFile = path.join("sfdx-project.json").absolute
    if (!projectFile.isFile) {
      return Right(None)
    }

    projectFile.read() match {
      case Left(err) => Left(err)
      case Right(data) =>
        try {
          Right(Some(new Project(ujson.read(data))))
        } catch {
          case ex: Throwable =>
            Left(s"Failed to parse '$projectFile', error: ${ex.toString}")
        }
    }
  }
}
