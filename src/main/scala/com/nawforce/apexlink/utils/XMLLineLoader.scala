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

import org.xml.sax.Locator

import scala.collection.mutable
import scala.xml.parsing.NoBindingFactoryAdapter
import scala.xml.{Elem, factory, parsing, _}

trait WithLocation extends NoBindingFactoryAdapter {
  private var locator: org.xml.sax.Locator = _
  private val startLines = mutable.Stack[Int]()

  // Get location
  abstract override def setDocumentLocator(locator: Locator) {
    this.locator = locator
    super.setDocumentLocator(locator)
  }

  abstract override def createNode(pre: String, label: String, attrs: MetaData, scope: NamespaceBinding, children: List[Node]): Elem = (
    super.createNode(pre, label, attrs, scope, children)
      % Attribute("line", Text(startLines.pop.toString), Null)
    )

  abstract override def startElement(uri: scala.Predef.String, _localName: scala.Predef.String, qname: scala.Predef.String, attributes: org.xml.sax.Attributes): scala.Unit = {
    startLines.push(locator.getLineNumber)
    super.startElement(uri, _localName, qname, attributes)
  }
}

object XMLLineLoader extends factory.XMLLoader[Elem] {
  override def adapter = new parsing.NoBindingFactoryAdapter with WithLocation
}
