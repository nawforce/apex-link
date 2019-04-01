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
package com.nawforce.unit.cst

import java.net.URI

import com.nawforce.cst.TypeDeclaration
import com.nawforce.types._
import com.nawforce.utils.IssueLog
import org.scalatest.FunSuite

class ClassModifierTest extends FunSuite {

  lazy val defaultUri: URI = ClassParser.defaultUri

  def typeDeclaration(clsText: String): TypeDeclaration = {
    IssueLog.clear()
    ClassParser.parseSingle(clsText).get.compilationUnit.typeDeclaration
  }

  test("Global outer") {
    assert(typeDeclaration("global class Dummy {}").modifiers == Seq(GLOBAL))
    assert(!IssueLog.hasMessages)
  }

  test("Public outer") {
    assert(typeDeclaration("public class Dummy {}").modifiers == Seq(PUBLIC))
    assert(!IssueLog.hasMessages)
  }

  test("Protected outer") {
    assert(typeDeclaration("protected class Dummy {}").modifiers.isEmpty)
    assert(IssueLog.getMessages(defaultUri) ==
      "line 1 at 0-9: Modifier 'protected' is not supported on classes\n")
  }

  test("Private outer") {
    assert(typeDeclaration("private class Dummy {}").modifiers.isEmpty)
    assert(IssueLog.getMessages(defaultUri) ==
      "line 1 at 0-7: Modifier 'private' is not supported on outer classes\n")
  }

  test("No modifier class") {
    assert(typeDeclaration("class Dummy {}").modifiers.isEmpty)
    assert(IssueLog.getMessages(defaultUri) ==
      "line 1 at 6-11: Outer classes must be declared either 'global' or 'public'\n")
  }

  test("Illegal modifier class") {
    assert(typeDeclaration("global static class Dummy {}").modifiers == Seq(GLOBAL))
    assert(IssueLog.getMessages(defaultUri) ==
      "line 1 at 7-13: Modifier 'static' is not supported on classes\n")
  }

  test("With sharing class") {
    val modifiers = typeDeclaration("public with sharing class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC, WITH_SHARING))
    assert(!IssueLog.hasMessages)
  }

  test("Without sharing class") {
    val modifiers = typeDeclaration("public without sharing class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC, WITHOUT_SHARING))
    assert(!IssueLog.hasMessages)
  }

  test("Inherited sharing class") {
    val modifiers = typeDeclaration("public inherited sharing class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC, INHERITED_SHARING))
    assert(!IssueLog.hasMessages)
  }
}
