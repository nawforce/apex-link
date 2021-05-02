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
package com.nawforce.common.cmds.benchmarks

import java.nio.file.{Files, Paths}

import com.nawforce.common.api.ServerOps
import com.nawforce.common.path.PathLike
import com.nawforce.common.sfdx.WorkspaceConfig
import com.nawforce.runtime.parsers.PageParser
import com.nawforce.runtime.platform.Path

import scala.collection.mutable.ArrayBuffer

object PageBench {

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

    //Thread.sleep(10000)

    // Indexing
    ServerOps.setDebugLogging(Array("ALL"))
    val config = new WorkspaceConfig {
      override val rootPaths: Seq[PathLike] = Seq(new Path(dir))
      override val namespace: Option[Name] = None
      override val paths: Seq[PathLike] = rootPaths
    }
    val workspace = new Workspace(config)

    // Parsing
    val parseTimes = ArrayBuffer[(Long, PathLike)]()
    ServerOps.debugTime("Parsed") {
      workspace
        .getByExtension(Name("page"))
        .foreach(doc => {
          val start = System.currentTimeMillis()
          var size = 0
          try {
            doc.path.readSourceData() match {
              case Left(err) => println(err)
              case Right(data) =>
                size = data.length
                val parser = PageParser(doc.path, data)
                parser.parsePage() match {
                  case Left(issues) =>
                    issues.foreach(issue =>
                      println(
                        s"${doc.path}:${issue.diagnostic.location.startLine} ${issue.diagnostic.message}"))
                  case Right(_) => ()
                }
            }
          } finally {
            val end = System.currentTimeMillis()
            parseTimes.append((end - start, doc.path))
          }
        })
    }

    parseTimes.toArray
      .sortBy(_._1)(Ordering[Long].reverse)
      .foreach(pair => {
        println(s"${pair._1}ms ${pair._2}")
      })
  }
}
