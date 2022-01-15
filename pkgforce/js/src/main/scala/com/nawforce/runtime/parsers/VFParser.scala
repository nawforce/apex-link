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
package com.nawforce.runtime.parsers

import com.nawforce.runtime.parsers.PageParser.TerminalNode
import com.nawforce.runtime.parsers.VFParser.VfUnitContext
import com.nawforce.runtime.parsers.antlr.{CommonTokenStream, ParserRuleContext}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("vf-parser", "VFParser")
class VFParser(tokens: CommonTokenStream) extends js.Object {

  def removeErrorListeners(): Unit                              = js.native
  def addErrorListener(listener: CollectingErrorListener): Unit = js.native

  def vfUnit(): VfUnitContext = js.native
}

object VFParser {

  @js.native
  @JSImport("vf-parser", "VfUnitContext")
  class VfUnitContext extends ParserRuleContext {
    def element(): ElementContext = js.native

    def COMMENT(): js.Array[TerminalNode] = js.native
    def COMMENT(i: Int): TerminalNode     = js.native

    def processingInstruction(): js.Array[ProcessingInstructionContext] = js.native
    def processingInstruction(i: Int): ProcessingInstructionContext     = js.native
  }

  @js.native
  @JSImport("vf-parser", "ElementContext")
  class ElementContext extends ParserRuleContext {
    def Name(): js.Array[TerminalNode]          = js.native
    def Name(i: Int): TerminalNode              = js.native
    def attribute(): js.Array[AttributeContext] = js.native
    def attribute(i: Int): AttributeContext     = js.native
    def content(): js.UndefOr[ContentContext]   = js.native
    def scriptChardata(): ScriptChardataContext = js.native
  }

  @js.native
  @JSImport("vf-parser", "AttributeContext")
  class AttributeContext extends ParserRuleContext {
    def attributeName(): AttributeNameContext               = js.native
    def attributeValues(): js.Array[AttributeValuesContext] = js.native
    def attributeValues(i: Int): AttributeValuesContext     = js.native
  }

  @js.native
  @JSImport("vf-parser", "AttributeNameContext")
  class AttributeNameContext extends ParserRuleContext {}

  @js.native
  @JSImport("vf-parser", "AttributeValuesContext")
  class AttributeValuesContext extends ParserRuleContext {}

  @js.native
  @JSImport("vf-parser", "AttributeValuesContext")
  class ContentContext extends ParserRuleContext {

    def element(): js.Array[ElementContext] = js.native
    def element(i: Int): ElementContext     = js.native

    def chardata(): js.Array[ChardataContext] = js.native
    def chardata(i: Int): ChardataContext     = js.native

    def COMMENT(): js.Array[TerminalNode] = js.native
    def COMMENT(i: Int): TerminalNode     = js.native

    def processingInstruction(): js.Array[ProcessingInstructionContext] = js.native
    def processingInstruction(i: Int): ProcessingInstructionContext     = js.native
  }

  @js.native
  @JSImport("vf-parser", "ChardataContext")
  class ChardataContext extends ParserRuleContext {
    def CDATA_TEXT(): js.Array[TerminalNode] = js.native
    def CDATA_TEXT(i: Int): TerminalNode     = js.native

    def EL_BODY(): js.Array[TerminalNode] = js.native
    def EL_BODY(i: Int): TerminalNode     = js.native
  }

  @js.native
  @JSImport("vf-parser", "ScriptChardataContext")
  class ScriptChardataContext extends ParserRuleContext {
    def SCRIPT_TEXT(): js.Array[TerminalNode] = js.native
    def SCRIPT_TEXT(i: Int): TerminalNode     = js.native
  }

  @js.native
  @JSImport("vf-parser", "ProcessingInstructionContext")
  class ProcessingInstructionContext extends ParserRuleContext {}
}
