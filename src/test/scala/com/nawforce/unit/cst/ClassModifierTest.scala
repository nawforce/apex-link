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

import com.nawforce.documents.DocumentLoader
import com.nawforce.types._
import com.nawforce.utils.IssueLog
import org.scalatest.FunSuite

class ClassModifierTest extends FunSuite {

  private val defaultName = TestDocumentLoader.defaultName
  private val defaultPath = TestDocumentLoader.defaultPath

  def typeDeclaration(clsText: String): TypeDeclaration = {
    IssueLog.clear()
    DocumentLoader.defaultDocumentLoader = new TestDocumentLoader(clsText)
    ApexTypeDeclaration.create(defaultName).get
  }

  test("Global outer") {
    assert(typeDeclaration("global class Dummy {}").modifiers == Seq(GLOBAL_MODIFIER))
    assert(!IssueLog.hasMessages)
  }

  test("Public outer") {
    assert(typeDeclaration("public class Dummy {}").modifiers == Seq(PUBLIC_MODIFIER))
    assert(!IssueLog.hasMessages)
  }

  test("Public outer (mixed case)") {
    assert(typeDeclaration("puBlIc class Dummy {}").modifiers == Seq(PUBLIC_MODIFIER))
    assert(!IssueLog.hasMessages)
  }

  test("Protected outer") {
    assert(typeDeclaration("protected class Dummy {}").modifiers.isEmpty)
    assert(IssueLog.getMessages(defaultPath) ==
      "line 1 at 0-9: Modifier 'protected' is not supported on classes\n")
  }

  test("Private outer") {
    assert(typeDeclaration("private class Dummy {}").modifiers.isEmpty)
    assert(IssueLog.getMessages(defaultPath) ==
      "line 1 at 14-19: Private modifier is not allowed on outer classes\n")
  }

  test("No modifier class") {
    assert(typeDeclaration("class Dummy {}").modifiers == Seq(PUBLIC_MODIFIER))
    assert(IssueLog.getMessages(defaultPath) ==
      "line 1 at 6-11: Outer classes must be declared either 'global' or 'public'\n")
  }

  test("Illegal modifier class") {
    assert(typeDeclaration("global static class Dummy {}").modifiers == Seq(GLOBAL_MODIFIER))
    assert(IssueLog.getMessages(defaultPath) ==
      "line 1 at 7-13: Modifier 'static' is not supported on classes\n")
  }

  test("With sharing class") {
    val modifiers = typeDeclaration("public with sharing class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, WITH_SHARING_MODIFIER))
    assert(!IssueLog.hasMessages)
  }

  test("Without sharing class") {
    val modifiers = typeDeclaration("public without sharing class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, WITHOUT_SHARING_MODIFIER))
    assert(!IssueLog.hasMessages)
  }

  test("Inherited sharing class") {
    val modifiers = typeDeclaration("public inherited sharing class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, INHERITED_SHARING_MODIFIER))
    assert(!IssueLog.hasMessages)
  }

  test("Inherited sharing class (mixed case)") {
    val modifiers = typeDeclaration("public inHerited shaRing class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, INHERITED_SHARING_MODIFIER))
    assert(!IssueLog.hasMessages)
  }

  test("Deprecated annotation class") {
    val modifiers = typeDeclaration("@Deprecated public class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, DEPRECATED_ANNOTATION))
    assert(!IssueLog.hasMessages)
  }

  test("Deprecated annotation class (mixed case)") {
    val modifiers = typeDeclaration("@DeprecAted public class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, DEPRECATED_ANNOTATION))
    assert(!IssueLog.hasMessages)
  }

  test("IsTest annotation class") {
    val modifiers = typeDeclaration("@IsTest public class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, ISTEST_ANNOTATION))
    assert(!IssueLog.hasMessages)
  }

  test("TestVisible annotation class") {
    val modifiers = typeDeclaration("@TestVisible public class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, TEST_VISIBLE_ANNOTATION))
    assert(!IssueLog.hasMessages)
  }

  test("SuppressWarnings annotation class") {
    val modifiers = typeDeclaration("@SuppressWarnings public class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, SUPPRESS_WARNINGS_ANNOTATION))
    assert(!IssueLog.hasMessages)
  }

  test("RestResource annotation class") {
    val modifiers = typeDeclaration("@RestResource public class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, REST_RESOURCE_ANNOTATION))
    assert(!IssueLog.hasMessages)
  }

  test("HttpGet annotation class") {
    val modifiers = typeDeclaration("@HttpGet public class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER))
    assert(IssueLog.getMessages(defaultPath) ==
      "line 1 at 0-8: Unexpected annotation 'HttpGet' on class declaration\n")
  }

  test("SuppressWarnings & isTest annotation class") {
    val modifiers = typeDeclaration("@SuppressWarnings public @isTest class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, SUPPRESS_WARNINGS_ANNOTATION, ISTEST_ANNOTATION))
    assert(!IssueLog.hasMessages)
  }
}
