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
package com.nawforce.common.cmds

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path, Paths}

import com.nawforce.common.names._
import com.nawforce.common.types.platform.PlatformTypeDeclaration
import upickle.default._

object Pickler {
  def main(args: Array[String]): Unit = {

    if (args.length != 1) {
      println("Use: <directory>")
      return
    }
    val dir = Paths.get(args(0))
    if (!Files.isDirectory(dir)) {
      println(s"'$dir' is not a directory")
      return
    }

    PlatformTypeDeclaration.classNames.toSeq.foreach(className => {
      PlatformTypeDeclaration.getDeclaration(className) match {
        case Some(td: PlatformTypeDeclaration) => writeDeclaration(dir, td)
        case None => assert(false)
      }
    })
  }

  def writeDeclaration(dir: Path, td: PlatformTypeDeclaration): Unit = {
    val ns =
      if (td.isSObject)
        "SObjects"
      else
        td.typeName.outerName.value
    val nsDir = dir.resolve(ns)
    if (!Files.isDirectory(nsDir))
      Files.createDirectory(nsDir)

    val file = nsDir.resolve(td.typeName.name.value+".json")
    Files.write(file, write(td.serialise).getBytes(StandardCharsets.UTF_8))
  }
}


