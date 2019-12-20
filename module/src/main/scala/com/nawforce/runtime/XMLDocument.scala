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
package com.nawforce.runtime

import com.nawforce.documents.{Location, PointLocation, Position}
import com.nawforce.imports.xml.{DOMParser, Document, Element, Node, Text}
import com.nawforce.path.PathLike
import com.nawforce.xml.{XMLDocumentLike, XMLElementLike, XMLName}
import io.scalajs.nodejs.console

import scala.collection.mutable.ArrayBuffer
import scala.scalajs.js
import scala.scalajs.js.typedarray.ArrayBuffer
import scala.scalajs.js.{Dynamic, Object}
import scala.util.matching.Regex

class XMLElement(element: Element) extends XMLElementLike {
  override lazy val line: Int = element.lineNumber

  override lazy val name: XMLName = XMLName(element.namespaceURI, element.localName)

  override lazy val text: String = {
    val nl = element.childNodes

    val sb = StringBuilder.newBuilder
    for (i <- 0 until nl.length) {
      val n = nl.item(i)
      if (n.nodeType == Node.TEXT_NODE) {
        sb.append(n.asInstanceOf[Text].data)
      }
    }
    sb.toString()
  }

  override def getChildren(name: String): Seq[XMLElementLike] = {
    val matched = ArrayBuffer[XMLElementLike]()
    val nl = element.childNodes
    for (i <- 0 until nl.length) {
      val n = nl.item(i)
      if (n.nodeType == Node.ELEMENT_NODE && n.namespaceURI == XMLDocument.sfNamespace && n.localName == name) {
        matched.append(new XMLElement(n.asInstanceOf[Element]))
      }
    }
    matched
  }
}

class XMLDocument(path: PathLike, doc: Document) extends XMLDocumentLike(path) {
  override lazy val rootElement: XMLElementLike = new XMLElement(doc.documentElement)
}

object XMLDocument {
  val sfNamespace = "http://soap.sforce.com/2006/04/metadata"
  var errors: List[(Location, String)] = Nil

  def apply(path: PathLike, data: String): Either[(Location, String), XMLDocument] = {
    errors = Nil
    val parser = new DOMParser(getOptions(path))
    val doc = parser.parseFromString(data, "text/xml")
    if (errors.nonEmpty) {
      Left(errors.last)
    } else {
      Right(new XMLDocument(path, doc))
    }
  }

  private def getOptions(path: PathLike): Object with Dynamic = {
    js.Dynamic.literal(
      "locator" -> js.Dynamic.literal(),
      "errorHandler" ->
        js.Dynamic.literal(
          "warning" -> { msg: String => captureErrors(path, msg)},
          "error" -> { msg: String => captureErrors(path, msg)},
          "fatalError" -> { msg: String => captureErrors(path, msg)}
        )
    )
  }

  private def captureErrors(path: PathLike, msg: String): Unit = {
    errors = toError(path, msg) :: errors
  }

  private val lineMatch: Regex = "line:[0-9]*".r
  private val columnMatch: Regex = "col:[0-9]*".r

  private def toError(path: PathLike, msg: String): (Location, String) = {
    val line = lineMatch.findFirstIn(msg) match {
      case Some(l) => try {
        l.replaceFirst("line:","").toInt
      } catch {
        case _: NumberFormatException => 0
      }
      case None => 0
    }

    val column = columnMatch.findFirstIn(msg) match {
      case Some(l) => try {
        l.replaceFirst("col:","").toInt
      } catch {
        case _: NumberFormatException => 0
      }
      case None => 0
    }

    val trimmedMsg =
      if (line == 0 || column == 0)
        msg.split("@#\\[")(0)
      else
        msg

    (PointLocation(path, Position(line, column)), trimmedMsg)
  }
}
