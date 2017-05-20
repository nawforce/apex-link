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
package io.github.nawforce.apexlink

import java.io.StringReader

import io.github.nawforce.apexlink.antlr.{ApexLexer, ApexParser}
import io.github.nawforce.apexlink.cst._
import io.github.nawforce.apexlink.utils.{CSTException, CaseInsensitiveInputStream, ThrowingErrorListener}
import org.antlr.v4.runtime.{CommonTokenStream, Token}

class TypeContextTest(_thisType: Type = null, _superType: Type = null, identifierTypes: Map[String, Type] = null) extends TypeContext {
  def thisType: Type =
    if (_thisType == null)
      throw new CSTException()
    else
      _thisType

  def superType: Type =
    if (_superType == null)
      throw new CSTException()
    else
      _superType

  def getIdentifierType(id: String): Type =
    if (identifierTypes == null || identifierTypes.get(id).isEmpty)
      throw new CSTException()
    else
      identifierTypes(id)
}

object TypeTestHelper {

  def typePrimary(p: String, typeCtx: TypeContext): Type = {
    val context = new ConstructContext(new Array[Token](0))
    Primary.construct(parse(p).primary(), context).getType(typeCtx)
  }

  def typeExpression(p: String, typeCtx: TypeContext): Expression = {
    val context = new ConstructContext(new Array[Token](0))
    Expression.construct(parse(p).expression(), context)
  }

  def comparePrimary(p: String, r: Type, typeCtx: TypeContext): Unit = {
    val t = typePrimary(p, typeCtx)
    if (t == null)
      throw new CSTException

    if (t != r) {
      System.out.println("Type mismatch:")
      System.out.println("Expected: " + r)
      System.out.println("Got: " + t)
      assert(false)
    }
  }

  private def parse(p: String) = {
    val listener = new ThrowingErrorListener
    val cis: CaseInsensitiveInputStream = new CaseInsensitiveInputStream(new StringReader(p))

    val lexer: ApexLexer = new ApexLexer(cis)
    lexer.removeErrorListeners()
    lexer.addErrorListener(listener)

    val tokens: CommonTokenStream = new CommonTokenStream(lexer)
    val parser: ApexParser = new ApexParser(tokens)
    parser.removeErrorListeners()
    parser.addErrorListener(listener)
    parser
  }
}
