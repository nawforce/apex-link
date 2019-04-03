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

import com.nawforce.cst.ClassDeclaration
import com.nawforce.documents.DocumentLoader
import com.nawforce.types._
import com.nawforce.utils.IssueLog
import org.scalatest.FunSuite

class InnerClassModifierTest extends FunSuite {

  private val defaultName = TestDocumentLoader.defaultName
  private val defaultPath = TestDocumentLoader.defaultPath

  def typeDeclaration(clsText: String): TypeDeclaration = {
    IssueLog.clear()
    DocumentLoader.defaultDocumentLoader = new TestDocumentLoader(clsText)
    ApexTypeDeclaration.create(defaultName).get.asInstanceOf[ClassDeclaration]
      .bodyDeclarations.head.asInstanceOf[ClassDeclaration]
  }

  test("Global inner") {
    assert(typeDeclaration("global class Dummy {global class Inner{}}").modifiers == Seq(GLOBAL_MODIFIER))
    assert(!IssueLog.hasMessages)
  }

  test("Global inner of public outer") {
    assert(typeDeclaration("public class Dummy {global class Inner{}}").modifiers == Seq(GLOBAL_MODIFIER))
    assert(IssueLog.getMessages(defaultPath) ==
      "line 1 at 13-18: Classes enclosing globals must also be declared global\n")
  }

  test("Public inner") {
    assert(typeDeclaration("public class Dummy {public class Inner{}}").modifiers == Seq(PUBLIC_MODIFIER))
    assert(!IssueLog.hasMessages)
  }

  test("Protected inner") {
    assert(typeDeclaration("public class Dummy {protected class Inner{}}").modifiers.isEmpty)
    assert(IssueLog.getMessages(defaultPath) ==
      "line 1 at 20-29: Modifier 'protected' is not supported on classes\n")
  }

  test("Private inner") {
    assert(typeDeclaration("public class Dummy {private class Inner{}}").modifiers == Seq(PRIVATE_MODIFIER))
    assert(!IssueLog.hasMessages)
  }

  test("No modifier inner class") {
    assert(typeDeclaration("public class Dummy {class Inner{}}").modifiers.isEmpty)
    assert(!IssueLog.hasMessages)
  }

  test("Illegal modifier inner class") {
    assert(typeDeclaration("global class Dummy {static class Inner{}}").modifiers.isEmpty)
    assert(IssueLog.getMessages(defaultPath) ==
      "line 1 at 20-26: Modifier 'static' is not supported on classes\n")
  }

  test("With sharing inner class") {
    val modifiers = typeDeclaration("public class Dummy {with sharing class Inner {}}").modifiers
    assert(modifiers.toSet == Set(WITH_SHARING_MODIFIER))
    assert(!IssueLog.hasMessages)
  }

  test("Without sharing inner class") {
    val modifiers = typeDeclaration("public without sharing class Dummy {without sharing class Inner {}}").modifiers
    assert(modifiers.toSet == Set(WITHOUT_SHARING_MODIFIER))
    assert(!IssueLog.hasMessages)
  }

  test("Inherited sharing inner  class") {
    val modifiers = typeDeclaration("public inherited sharing class Dummy {inherited sharing class Inner {}}").modifiers
    assert(modifiers.toSet == Set(INHERITED_SHARING_MODIFIER))
    assert(!IssueLog.hasMessages)
  }
}
