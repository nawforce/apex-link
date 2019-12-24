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
package com.nawforce.runtime.types

import java.io.StringReader

import com.nawforce.common.cst.{ConstructContext, Literal}
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.path.{PathFactory, PathLike}
import com.nawforce.common.types.{DependencyHolder, TypeDeclaration}
import com.nawforce.runtime.parsers.{ApexLexer, ApexParser, CaseInsensitiveInputStream, ThrowingErrorListener}
import org.antlr.v4.runtime.CommonTokenStream
import org.scalatest.funsuite.AnyFunSuite

class LiteralTypeTest extends AnyFunSuite
{
  private val defaultPath = PathFactory("Dummy.cls")

  private def parse(path: PathLike, data: String) = {
    val listener = new ThrowingErrorListener
    val cis: CaseInsensitiveInputStream = new CaseInsensitiveInputStream(path.toString, new StringReader(data))

    val lexer: ApexLexer = new ApexLexer(cis)
    lexer.removeErrorListeners()
    lexer.addErrorListener(listener)

    val tokens: CommonTokenStream = new CommonTokenStream(lexer)
    val parser: ApexParser = new ApexParser(tokens)
    parser.removeErrorListeners()
    parser.addErrorListener(listener)
    parser
  }

  def typeLiteral(data: String): DependencyHolder = {
    val context = new ConstructContext()
    Literal.construct(parse(defaultPath, data).literal(), context).getType
  }

  def compareLiteral(p: String, r: TypeName): Unit = {
    val t = typeLiteral(p)
    assert(t != null)

    if (t.asInstanceOf[TypeDeclaration].typeName != r) {
      System.out.println("Type mismatch:")
      System.out.println("Expected: " + r)
      System.out.println("Got: " + t)
      assert(false)
    }
  }

  def literal(value: String, r: TypeName = null) : Unit =
    compareLiteral(value, r)

  test("Primary literal") {
    literal("0", TypeName.Integer)
    literal("1", TypeName.Integer)
    literal("0l", TypeName.Long)
    literal("1l", TypeName.Long)
    literal("0L", TypeName.Long)
    literal("1L", TypeName.Long)
    literal("''", TypeName.String)
    literal("'a'", TypeName.String)
    literal("'az'", TypeName.String)
    literal("'\t'", TypeName.String)
    literal("true", TypeName.Boolean)
    literal("False", TypeName.Boolean)
    literal("null", TypeName(Name("Null$"), Nil, Some(TypeName(Name.Internal))))
    literal("0.0", TypeName.Decimal)
    literal(".0", TypeName.Decimal)
    literal("0.123", TypeName.Decimal)
    literal("0.123456789012345678901234567890123456789012345678", TypeName.Decimal)
    literal("0.1234567890123456789012345678901234567890123456789", TypeName.Double)
  }
}
