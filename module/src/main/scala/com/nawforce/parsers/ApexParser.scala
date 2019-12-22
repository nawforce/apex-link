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
package com.nawforce.parsers

import com.nawforce.parsers.ApexParser.BlockContext
import com.nawforce.parsers.antlr.{CommonTokenStream, ParserRuleContext}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("apex-parser", "ApexParser")
class ApexParser(tokens: CommonTokenStream) extends js.Object {

  def removeErrorListeners(): Unit = js.native
  def addErrorListener(listener: ThrowingErrorListener): Unit = js.native

  def compilationUnit(): CompilationUnitContext = js.native
  def block(): BlockContext = js.native
}

object ApexParser {

  @js.native
  @JSImport("apex-parser", "IdContext")
  class IdContext extends ParserRuleContext

  @js.native
  @JSImport("apex-parser", "BlockContext")
  class BlockContext extends ParserRuleContext

  @js.native
  @JSImport("apex-parser", "QualifiedNameContext")
  class QualifiedNameContext extends ParserRuleContext {
    def id(): js.Array[IdContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "AnnotationContext")
  class AnnotationContext extends ParserRuleContext {
    def elementValue(): js.UndefOr[ElementValueContext] = js.native
    def elementValuePairs(): js.UndefOr[ElementValuePairsContext] = js.native
    def qualifiedName(): QualifiedNameContext = js.native
  }

  @js.native
  @JSImport("apex-parser", "ElementValueContext")
  class ElementValueContext extends ParserRuleContext {
    def expression(): js.UndefOr[ExpressionContext] = js.native
    def annotation(): js.UndefOr[AnnotationContext] = js.native
    def elementValueArrayInitializer(): js.UndefOr[ElementValueArrayInitializerContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "ElementValuePairsContext")
  class ElementValuePairsContext extends ParserRuleContext {
    def elementValuePair(): js.Array[ElementValuePairContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "ExpressionContext")
  class ExpressionContext extends ParserRuleContext {
  }

  @js.native
  @JSImport("apex-parser", "ElementValueArrayInitializerContext")
  class ElementValueArrayInitializerContext extends ParserRuleContext {
    def elementValue(): js.Array[ElementValueContext] = js.native
  }

  @js.native
  @JSImport("apex-parser", "ElementValuePairContext")
  class ElementValuePairContext extends ParserRuleContext {
    def id(): IdContext = js.native
    def elementValue(): ElementValueContext = js.native
  }
}
