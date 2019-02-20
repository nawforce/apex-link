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
package com.nawforce.apexlink.api

import java.nio.file.Path

import com.nawforce.apexlink.diff.FileChanger
import com.nawforce.apexlink.metadata.{ApexClassReader, CustomObjectReader, LabelReader, SymbolReaderContext}
import com.nawforce.apexlink.transforms.experimental.{AssertDelete, LS_QueryLoops}
import com.nawforce.apexlink.transforms.{BangComments, MakeIsTest, SortLabels}

import scala.collection.JavaConverters._

 class LinkContext private (path: Path, verbose: Boolean) {

  val ctx = new SymbolReaderContext(path, verbose)
  new LabelReader().loadSymbols(ctx)
  new CustomObjectReader().loadSymbols(ctx)
  // TODO: Re-enable page reading with HTML parser
  //new PageReader().loadSymbols(ctx)
  new ApexClassReader().loadSymbols(ctx)

  def report(): Unit = ctx.report()

  def transform(transforms : java.util.List[String]) : Unit = {
    val fileChanger: FileChanger = new FileChanger()
    transforms.asScala.foreach(transform => {
      println("Running transform " + transform)
      transform match {
        case "sort-labels" => new SortLabels().exec(ctx, fileChanger)
        case "make-istest" => new MakeIsTest().exec(ctx, fileChanger)
        case "bang-comments" => new BangComments().exec(ctx, fileChanger)
        case "exp.assert-delete" => new AssertDelete().exec(ctx, fileChanger)
        case "exp.ls-query-loops" => new LS_QueryLoops().exec(ctx, fileChanger)
        case _ =>
          println("There is no transform " + transform)
      }
    })
    fileChanger.diff()
  }
}

object LinkContext {

  def create(path: Path, verbose: Boolean) : LinkContext = {
    new LinkContext(path, verbose)
  }
}
