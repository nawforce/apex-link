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
package io.github.nawforce.apexlink.transform

import scala.xml.NodeBuffer

import io.github.nawforce.apexlink.diff.FileChanger
import io.github.nawforce.apexlink.metadata.{Label, SymbolReaderContext}

class SortLabels {

  def exec(ctx: SymbolReaderContext, fileChanger: FileChanger): Unit = {

    val seq = ctx.getLabels.values.toIndexedSeq
    val sorted = seq.sortBy{label => label.fullName}
    var buffer = new NodeBuffer

    sorted.foreach((label : Label) => {
      val output =
        <labels>
          <fullName>{label.fullName}</fullName>
          {if (label.categories.isDefined) <categories>{label.categories.get}</categories>}
          <language>{label.language}</language>
          <protected>{label.protect}</protected>
          <shortDescription>{label.shortDescription}</shortDescription>
          <value>{label.value}</value>
        </labels>
      buffer += output
    })

    if (buffer.nonEmpty) {
      val xml =
        <CustomLabels xmlns="http://soap.sforce.com/2006/04/metadata">
          {buffer}
        </CustomLabels>
      val p = new scala.xml.PrettyPrinter(1000, 4)
      val text = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + p.format(xml)
      // Salesforce wrongly escapes ' so we replicate to avoid extra differences
      val escaped = text.replace("'","&apos;")
      fileChanger.replaceFile(ctx.getBaseDir.resolve("labels/CustomLabels.labels").toString, escaped)
    }
  }
}
