/*
 [The "BSD licence"]
 Copyright (c) 2020 Kevin Jones
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

import com.nawforce.common.api.{Org, ServerOps}
import com.nawforce.runtime.json.JSON

import scala.collection.mutable

object ClassInfo {
  final val STATUS_EXCEPTION: Int = 3

  def main(args: Array[String]): Unit = {

    ServerOps.setAutoFlush(false)
    ServerOps.setDebugLogging(Array("ALL"))

    val classPathSplit = args.groupBy(_.endsWith(".cls"))

    val org = Org.newOrg()
    try {
      OrgLoader.load(classPathSplit.getOrElse(false, Array()), org)
      classPathSplit
        .getOrElse(true, Array())
        .map(clsName => {
          writeDependenciesAsJSON(clsName.replaceAll("\\.cls$", ""), org);
        })
      org.flush()

    } catch {
      case ex: Throwable =>
        ex.printStackTrace(System.err)
        System.exit(STATUS_EXCEPTION)
    }
  }

  private def writeDependenciesAsJSON(className: String, org: Org): Unit = {
    val buffer = new StringBuilder()
    var first = true
    val orgDeps = org.getDependencies
    val deps = mutable.Set[String]()
    transSet(className, orgDeps, deps, 1)

    buffer ++= s"""{ "dependencies": [\n"""
    deps
      .map(name => {
        val tdeps = mutable.Set[String]()
        transSet(name, orgDeps, tdeps, 1000)
        (name, tdeps.size)
      })
      .map(kv => {
        if (kv._2 != null) {
          if (!first)
            buffer ++= ",\n"
          first = false

          buffer ++= s"""{ "name": "${JSON.encode(kv._1)}", "count": ${kv._2}}"""
        }
      })
    buffer ++= "]}\n"
    print(buffer.mkString)
  }

  private def transSet(className: String,
                       orgDeps: java.util.Map[String, Array[String]],
                       accum: mutable.Set[String],
                       depth: Int): Unit = {
    if (depth > 0) {
      orgDeps
        .getOrDefault(className, Array())
        .filterNot(accum.contains)
        .foreach(dep => {
          accum += dep
          transSet(dep, orgDeps, accum, depth - 1)
        })
    }
  }
}
