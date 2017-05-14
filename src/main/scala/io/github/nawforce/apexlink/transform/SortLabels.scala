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

    // Sort the labels
    val seq = ctx.getLabels.values.toIndexedSeq
    val sorted = seq.sortBy{label => label.fullName}

    // Output, this is a bit crude but we some very salesforce specific handling
    val sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
    sb ++= "<CustomLabels xmlns=\"http://soap.sforce.com/2006/04/metadata\">\n"

    var indent = 1
    sorted.foreach((label : Label) => {
      indentString(sb, indent)
      sb ++= "<labels>\n"
      indent += 1

      writeValue(sb, indent, "fullName", label.fullName)
      if (label.categories.isDefined)
        writeValue(sb, indent, "categories", label.categories.get.mkString(","))
      writeValue(sb, indent, "language", label.language)
      writeValue(sb, indent, "protected", if (label.protect) "true" else "false")
      writeValue(sb, indent, "shortDescription", label.shortDescription)
      writeValue(sb, indent, "value", label.value)

      indent -= 1
      indentString(sb, indent)
      sb ++= "</labels>\n"
    })
    sb ++= "</CustomLabels>"
    fileChanger.replaceFile(ctx.getBaseDir.resolve("labels/CustomLabels.labels").toString, sb.toString())
  }

  private def indentString(sb: StringBuilder, indent: Integer) : Unit = {
    if (indent>0) {
      sb ++= "    "
      indentString(sb, indent - 1)
    }
  }

  private def writeValue(sb: StringBuilder, indent: Integer, name: String, text: String) = {
    indentString(sb, indent)
    sb ++= "<" + name + ">"
    writeText(sb, text)
    sb ++= "</" + name + ">\n"
  }

  private def writeText(sb: StringBuilder, text: String) : Unit = {
    text.foreach(c =>
      sb ++= (c match {
        case '\'' => "&apos;"
        case '"' => "&quot;"
        case '&' =>  "&amp;"
        case '<' =>  "&lt;"
        case '>' =>  "&gt;"
        case _ => c.toString
      })
    )
  }
}
