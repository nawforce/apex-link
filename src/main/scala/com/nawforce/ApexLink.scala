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
package com.nawforce

import java.nio.file.Paths

import com.nawforce.api.{LogUtils, Org}
import com.nawforce.utils.IssueLog

object ApexLink {
  def main(args: Array[String]): Unit = {
    val options = Set("-verbose", "-json")

    val validArgs = args.flatMap {
      case option if options.contains(option)=> Some(option)
      case arg => Some(arg)
    }

    if (validArgs.length != args.length) {
      println(s"Usage: ApexLink [-json] [-verbose] <dir1> <dir2> ...")
      return
    }

    var paths: Seq[String] = validArgs.filterNot(options.contains)
    if (paths.isEmpty)
      paths = Seq(Paths.get("").toAbsolutePath.toString)
    val json = validArgs.contains("-json")
    val verbose = !json && validArgs.contains("-verbose")
    LogUtils.setLoggingLevel(verbose)

    val parseStart = System.currentTimeMillis()
    val org = new Org()
    val pkg = org.addPackage(paths.toArray)
    val resultJson = pkg.deployAll()
    val parseEnd = System.currentTimeMillis()

    if (!json)
      org.issues.dumpMessages(json = false)
    else
      println(resultJson)

    if (verbose)
      println(s"Parsed ${pkg.classCount} files, with average time/file of ${(parseEnd-parseStart)/pkg.classCount}ms")
  }
}
