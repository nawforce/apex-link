/*
 Copyright (c) 2019 Kevin Jones, All rights reserved.
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
 */
package com.nawforce.runtime.xml

import scala.scalajs.js
import scala.scalajs.js.annotation._

@js.native
trait NodeList extends js.Object {
  val length: Int         = js.native
  def item(at: Int): Node = js.native
}

@js.native
trait Node extends js.Object {
  val lineNumber: Int   = js.native
  val columnNumber: Int = js.native

  val nodeType: Int    = js.native
  val nodeName: String = js.native

  val namespaceURI: String = js.native
  val localName: String    = js.native

  val childNodes: NodeList = js.native
}

object Node {
  val ELEMENT_NODE                = 1
  val ATTRIBUTE_NODE              = 2
  val TEXT_NODE                   = 3
  val CDATA_SECTION_NODE          = 4
  val ENTITY_REFERENCE_NODE       = 5
  val ENTITY_NODE                 = 6
  val PROCESSING_INSTRUCTION_NODE = 7
  val COMMENT_NODE                = 8
  val DOCUMENT_NODE               = 9
  val DOCUMENT_TYPE_NODE          = 10
  val DOCUMENT_FRAGMENT_NODE      = 11
  val NOTATION_NODE               = 12
}

@js.native
trait Element extends Node {}

@js.native
trait CharacterData extends Node {
  val data: String = js.native
}

@js.native
trait Text extends CharacterData {}

@js.native
trait Document extends js.Object {
  val documentElement: Element = js.native
}

@js.native
@JSImport("@xmldom/xmldom", "DOMParser")
class DOMParser(options: js.Dynamic) extends js.Object {
  def parseFromString(xmlSource: String, mimeType: String): Document = js.native
}
