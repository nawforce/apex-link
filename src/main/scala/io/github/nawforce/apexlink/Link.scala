/*
 [The "BSD licence"]
 Copyright (c) 2017 Kevin Jones
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
package io.github.nawforce.apexlink

import java.nio.file.{Files, Paths}

import io.github.nawforce.apexlink.diff.FileChanger
import io.github.nawforce.apexlink.metadata.{ApexClassReader, CustomObjectReader, LabelReader, SymbolReaderContext}
import io.github.nawforce.apexlink.transforms._
import io.github.nawforce.apexlink.transforms.experimental.{AssertDelete, LS_QueryLoops}
import io.github.nawforce.apexlink.utils.LinkerLog

object Link {
  private val usage =
    """
      |Usage [-verbose] -transform <transform> <directory>
    """.stripMargin

  def main(args: Array[String]): Unit = {
    System.exit(link(args))
  }

  def link(args: Array[String]): Integer = {
    type OptionMap = Map[String, Any]

    class Options {
      var isVerbose = false
      var transforms: List[String] = List[String]()
      var unknown: List[String] = List[String]()
    }

    def nextOption(options: Options, list: List[String]): Options = {
      list match {
        case Nil => options
        case "-verbose" :: tail => options.isVerbose = true; nextOption(options, tail)
        case "-transform" :: value :: tail => options.transforms = value :: options.transforms; nextOption(options, tail)
        case value :: tail => options.unknown = value :: options.unknown; nextOption(options, tail)
      }
    }

    val options = nextOption(new Options(), args.toList)
    if (options.unknown.length != 1) {
      println(usage)
      return 1
    }
    val directory = Paths.get(options.unknown.head)
    if (!Files.isDirectory(directory)) {
      println("There is no directory at " + directory)
      println(usage)
      return 1
    }

    // Load from passed directory
    println("Loading from " + directory)
    val ctx = new SymbolReaderContext(directory, options.isVerbose)
    new LabelReader().loadSymbols(ctx)
    new CustomObjectReader().loadSymbols(ctx)
    // TODO: Re-enable page reading with HTML parser
    //new PageReader().loadSymbols(ctx)
    new ApexClassReader().loadSymbols(ctx)
    ctx.report()

    if (LinkerLog.hasMessages) {
      println("Problems found during linking, aborting run")
      LinkerLog.dumpMessages()
      return 1
    }

    // Run any transforms
    val fileChanger: FileChanger = new FileChanger()
    options.transforms.foreach(transform => {
      println("Running transform " + transform)
      transform match {
        case "assert-delete" => new AssertDelete().exec(ctx, fileChanger)
        case "ls-query-loops" => new LS_QueryLoops().exec(ctx, fileChanger)
        case "sort-labels" => new SortLabels().exec(ctx, fileChanger)
        case "make-istest" => new MakeIsTest().exec(ctx, fileChanger)
        case "bang-comments" => new BangComments().exec(ctx, fileChanger)
        case _ =>
          println("There is no transform " + transform)
          return 1
      }
    })
    fileChanger.diff()
    0
  }
}
