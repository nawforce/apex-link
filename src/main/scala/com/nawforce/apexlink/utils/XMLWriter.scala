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
package com.nawforce.apexlink.utils

class XMLWriter(sb: StringBuilder) {
  private var _indent: Int = 0
  private var _endText: List[String] = Nil

  def writeDecl(): Unit = {
    sb ++= "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
  }

  def startElement(name: String, newline: Boolean, ns: List[String] = Nil): Unit = {
    writeIndent()
    _indent += 1

    sb ++= "<" + name
    ns.foreach(n => {
      sb ++= " xmlns" + n
    })
    sb ++= ">"
    if (newline)
      sb += '\n'

    _endText = ("</" + name + ">\n") :: _endText
  }

  def endElement(indent: Boolean = true): Unit = {
    _indent -= 1
    if (indent)
      writeIndent()

    _endText match {
      case h :: t => sb ++= h; _endText = t
      case _ =>
    }
  }

  def text(text: String): Unit = {
    text.foreach(c =>
      sb ++= (c match {
        case '\'' => "&apos;"
        case '"' => "&quot;"
        case '&' => "&amp;"
        case '<' => "&lt;"
        case '>' => "&gt;"
        case _ => c.toString
      })
    )
  }

  def elementValue(name: String, value: String): Unit = {
    startElement(name, newline = false)
    text(value)
    endElement(false)
  }

  private def writeIndent(indent: Integer = this._indent): Unit = {
    if (indent > 0) {
      sb ++= "    "
      writeIndent(indent - 1)
    }
  }
}
