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
package com.nawforce.runtime.xml

import scala.scalajs.js
import scala.scalajs.js.annotation._

@js.native
trait NodeList extends js.Object {
  val length: Int = js.native
  def item(at: Int): Node = js.native
}

@js.native
trait Node extends js.Object {
  val lineNumber: Int = js.native
  val columnNumber: Int = js.native

  val nodeType: Int = js.native
  val nodeName: String = js.native

  val namespaceURI: String = js.native
  val localName: String = js.native

  val childNodes: NodeList = js.native
}

object Node {
  var ELEMENT_NODE = 1
  var ATTRIBUTE_NODE = 2
  var TEXT_NODE = 3
  var CDATA_SECTION_NODE = 4
  var ENTITY_REFERENCE_NODE = 5
  var ENTITY_NODE = 6
  var PROCESSING_INSTRUCTION_NODE = 7
  var COMMENT_NODE = 8
  var DOCUMENT_NODE = 9
  var DOCUMENT_TYPE_NODE = 10
  var DOCUMENT_FRAGMENT_NODE = 11
  var NOTATION_NODE = 12
}

@js.native
trait Element extends Node {
}

@js.native
trait CharacterData extends Node {
  val data: String = js.native
}

@js.native
trait Text extends CharacterData {
}

@js.native
trait Document extends js.Object {
  val documentElement: Element = js.native
}

@js.native
@JSImport("xmldom", "DOMParser")
class DOMParser(options: js.Dynamic) extends js.Object {
  def parseFromString(xmlSource: String, mimeType: String): Document = js.native
}
