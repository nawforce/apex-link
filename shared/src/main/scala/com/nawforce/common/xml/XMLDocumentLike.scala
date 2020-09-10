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
package com.nawforce.common.xml

import com.nawforce.common.api.Location
import com.nawforce.common.path.PathLike

final case class XMLException(where: Location, msg: String) extends Exception

case class XMLName(namespace: String, label: String)

trait XMLElementLike {
  // Input line number for element
  val line: Int

  // Namespace & node label
  val name: XMLName

  // Text of node
  val text: String

  // Assert name of an element, throws XMLException on error
  def assertIs(value: String): Unit = {
    if (name.namespace != XMLDocumentLike.sfNamespace) {
      throw XMLException(
        Location(line),
        s"Expected element in Salesforce namespace, but has namespace '${name.namespace}' ")
    }
    if (name.label != value) {
      throw XMLException(Location(line),
                         s"Expected element named '$name', but found '${name.label}'")
    }
  }

  // Child elements with specific name
  def getChildren(name: String): Seq[XMLElementLike]

  // Get child element of given name iff there is a single child
  def getOptionalSingleChild(name: String): Option[XMLElementLike] = {
    val matched = getChildren(name)
    if (matched.length == 1)
      Some(matched.head)
    else {
      None
    }
  }

  // Get text of an element, throws if a single child with text can not be found
  def getSingleChildAsString(name: String): String = {
    val text = getOptionalSingleChildAsString(name)
    if (text.isEmpty)
      throw XMLException(Location(line), s"Expecting element to have single '$name' child")
    text.get
  }

  // Get text of an element, returns None if a single child with text can not be found
  def getOptionalSingleChildAsString(name: String): Option[String] = {
    getOptionalSingleChild(name).map(e => e.text)
  }

  // Get boolean value of an element, throws if a single child with true/false can not be found
  def getSingleChildAsBoolean(name: String): Boolean = {
    val value = getOptionalSingleChildAsBoolean(name)
    if (value.isEmpty)
      throw XMLException(Location(line), s"Expecting element to have single '$name' child")
    value.get
  }

  // Get boolean value of an element, returns None if a single child with true/false can not be found
  def getOptionalSingleChildAsBoolean(name: String): Option[Boolean] = {
    val matched = getOptionalSingleChild(name)
    if (matched.isDefined) {
      val isBoolean = matched.get.text.matches("true|false")
      if (!isBoolean)
        throw XMLException(
          Location(line),
          s"Expecting value to be either 'true' or 'false', found '${matched.get.text}'")
      Some(matched.get.text == "true")
    } else {
      None
    }
  }
}

abstract class XMLDocumentLike(val path: PathLike) {
  // Root element of document
  val rootElement: XMLElementLike
}

object XMLDocumentLike {
  val sfNamespace = "http://soap.sforce.com/2006/04/metadata"
}
