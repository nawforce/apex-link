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
import com.nawforce.path.PathLike
import com.nawforce.xml.{XMLDocumentLike, XMLElementLike, XMLLineLoader, XMLName}

import scala.xml.{Elem, SAXParseException}

class XMLElement(element: Elem) extends XMLElementLike {
  override lazy val line: Int = element.attribute("line").get.toString().toInt

  override lazy val name: XMLName = XMLName(element.namespace, element.label)

  override lazy val text: String = element.text

  override def getOptionalSingleChild(name: String): Option[XMLElementLike] = {
    val matched = (element \ name).filter(x => x.namespace == XMLDocument.sfNamespace)
    if (matched.length == 1)
      Some(new XMLElement(matched.head.asInstanceOf[Elem]))
    else {
      None
    }
  }
}

class XMLDocument(path: PathLike, elem: Elem) extends XMLDocumentLike(path) {
  override lazy val rootElement: XMLElementLike = new XMLElement(elem)
}

object XMLDocument {
  val sfNamespace = "http://soap.sforce.com/2006/04/metadata"

  def apply(path: PathLike, data: String): Either[(Location, String), XMLDocument] = {
    try {
      Right(new XMLDocument(path, XMLLineLoader.loadString(data)))
    } catch {
      case e: SAXParseException => Left(
        (PointLocation(path, Position(e.getLineNumber, e.getColumnNumber)), e.getLocalizedMessage))
    }
  }
}
