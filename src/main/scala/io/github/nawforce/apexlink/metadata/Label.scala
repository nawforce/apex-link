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
package io.github.nawforce.apexlink.metadata

import io.github.nawforce.apexlink.utils.{Location, XMLUtils, XMLWriter}

import scala.language.implicitConversions
import scala.xml.Elem

case class Label(location: Location, fullName: String, language: String, protect: Boolean, shortDescription: String,
                 value: String, categories: Option[Array[String]]) extends Symbol {

  val scopedName: String = "Label." + fullName

  def write(writer: XMLWriter): Unit = {
    writer.startElement("labels", newline = true)

    writer.elementValue("fullName", fullName)
    if (categories.isDefined)
      writer.elementValue("categories", categories.get.mkString(","))
    writer.elementValue("language", language)
    writer.elementValue("protected", if (protect) "true" else "false")
    writer.elementValue("shortDescription", shortDescription)
    writer.elementValue("value", value)

    writer.endElement()
  }
}

object Label {

  def create(elem: Elem): Option[Label] = {
    implicit def optToBool(opt: Option[_]): Boolean = opt.isDefined

    val fullName: Option[String] = XMLUtils.getSingleChildAsString(elem, "fullName")
    val language: Option[String] = XMLUtils.getSingleChildAsString(elem, "language")
    val protect: Option[Boolean] = XMLUtils.getSingleChildAsBoolean(elem, "protected")
    val shortDescription: Option[String] = XMLUtils.getSingleChildAsString(elem, "shortDescription")
    val value: Option[String] = XMLUtils.getSingleChildAsString(elem, "value")
    val categories: Option[String] = XMLUtils.getOptionalSingleChildAsString(elem, "categories")

    if (fullName && language && shortDescription && value && protect)
      Some(new Label(XMLUtils.getLocation(elem), fullName.get, language.get, protect.get, shortDescription.get, value.get,
        categories.flatMap(x => Some(x.split(',')))))
    else
      None
  }
}
