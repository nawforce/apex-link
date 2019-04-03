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
package com.nawforce.utils

import com.nawforce.documents.{LineLocation, Location}

import scala.xml.Elem

object XMLUtils {
val sfNamespace = "http://soap.sforce.com/2006/04/metadata"

  def getLine(elem: Elem): Integer = {
    elem.attribute("line").get.toString().toInt
  }

  def getLocation(elem: Elem): Location = {
    LineLocation(IssueLog.context.get, getLine(elem))
  }

  def ifNotElemLogAndThrow(elem: Elem, name: String): Unit = {
    if (ifNotLog(elem.namespace == sfNamespace, elem, "Expected element in Salesforce namespace, but has namespace '" + elem.namespace + "'")) {
      throw new LinkerException()
    }
    if (ifNotLog(elem.label == name, elem, "Expected element named '" + name + "', but found '" + elem.label + "'")) {
      throw new LinkerException()
    }
  }

  def ifNotLogAndThrow(condition: Boolean, elem: Elem, msg: String): Unit = {
    if (ifNotLog(condition, elem, msg)) {
      throw new LinkerException()
    }
  }

  def ifNotLog(condition: Boolean, elem: Elem, msg: String): Boolean = {
    if (!condition) {
      IssueLog.logMessage(getLine(elem), msg)
    }
    !condition
  }

  def getSingleChildAsString(elem: Elem, name: String): Option[String] = {
    val text = getOptionalSingleChildAsString(elem, name)
    if (text.isEmpty)
      IssueLog.logMessage(getLine(elem), "Expecting element to have single '" + name + "' child")
    text
  }

  def getOptionalSingleChildAsString(elem: Elem, name: String): Option[String] = {
    getOptionalSingleChild(elem, name).map(e => e.text)
  }

  def getSingleChildAsBoolean(elem: Elem, name: String): Option[Boolean] = {
    val value = getOptionalSingleChildAsBoolean(elem, name)
    if (value.isEmpty)
      IssueLog.logMessage(getLine(elem), "Expecting element to have single '" + name + "' child")
    value
  }

  def getOptionalSingleChildAsBoolean(elem: Elem, name: String): Option[Boolean] = {
    val matched = getOptionalSingleChild(elem, name)
    if (matched.isDefined) {
      val isBoolean = matched.get.text.matches("true|false")
      if (!isBoolean)
        IssueLog.logMessage(getLine(matched.get), "Expecting value to be either 'true' or 'false', found '" + matched.get.text + "'")
      Some(matched.get.text == "true")
    } else {
      None
    }
  }

  def getSingleChild(elem: Elem, name: String): Option[Elem] = {
    val child = getOptionalSingleChild(elem, name)
    if (child.isEmpty)
      IssueLog.logMessage(getLine(elem), "Expecting element to have single '" + name + "' child")
    child
  }

  def getOptionalSingleChild(elem: Elem, name: String): Option[Elem] = {
    val matched = (elem \ name).filter(x => x.namespace == sfNamespace)
    if (matched.length == 1)
      Some(matched.head.asInstanceOf[Elem])
    else {
      None
    }
  }

  def getMultipleChildren(elem: Elem, name: String): List[Elem] = {
    (elem \ name).filter(x => x.namespace == sfNamespace).asInstanceOf[Seq[Elem]].toList
  }
}
