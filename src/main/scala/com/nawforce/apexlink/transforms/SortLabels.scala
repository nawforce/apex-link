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
package com.nawforce.apexlink.transforms

import com.nawforce.apexlink.diff.FileChanger
import com.nawforce.apexlink.metadata.{Label, SymbolReaderContext}
import com.nawforce.apexlink.utils.XMLWriter


class SortLabels {

  def exec(ctx: SymbolReaderContext, fileChanger: FileChanger): Unit = {

    // Sort the labels
    val seq = ctx.getLabels.values.toIndexedSeq
    val sorted = seq.sortBy { label => label.fullName }

    // Output, this is a bit crude but we need some very specific handling
    val sb = new StringBuilder()
    val writer = new XMLWriter(sb)

    writer.writeDecl()
    writer.startElement("CustomLabels", newline = true, "=\"http://soap.sforce.com/2006/04/metadata\"" :: Nil)
    sorted.foreach((label: Label) => {
      label.write(writer)
    })
    writer.endElement()

    fileChanger.replaceFile(ctx.getBaseDir.resolve("labels/CustomLabels.labels").toString, sb.toString())
  }
}
