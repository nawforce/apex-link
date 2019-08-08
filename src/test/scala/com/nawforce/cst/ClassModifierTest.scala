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
package com.nawforce.cst

import java.io.ByteArrayInputStream
import java.nio.file.{Path, Paths}

import com.nawforce.api.Org
import com.nawforce.types._
import com.nawforce.utils.Name
import org.scalatest.{BeforeAndAfter, FunSuite}

class ClassModifierTest extends FunSuite with BeforeAndAfter {

  private val defaultName: Name = Name("Dummy")
  private val defaultPath: Path = Paths.get(defaultName.toString)
  private var defaultOrg: Org = new Org

  def typeDeclaration(clsText: String): TypeDeclaration = {
    Org.current.withValue(defaultOrg) {
      val td = ApexTypeDeclaration.create(defaultOrg.unmanaged, defaultPath, new ByteArrayInputStream(clsText.getBytes())).head
      Org.current.value.issues.context.withValue(defaultPath) {
        td.validate()
      }
      td
    }
  }

  def typeDeclarationInner(clsText: String): TypeDeclaration = {
    typeDeclaration(clsText).asInstanceOf[ClassDeclaration]
      .bodyDeclarations.head.asInstanceOf[TypeDeclaration]
  }

  before {
    defaultOrg = new Org
  }

  test("Global outer") {
    assert(typeDeclaration("global class Dummy {}").modifiers == Seq(GLOBAL_MODIFIER))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Public outer") {
    assert(typeDeclaration("public class Dummy {}").modifiers == Seq(PUBLIC_MODIFIER))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Public outer (mixed case)") {
    assert(typeDeclaration("puBlIc class Dummy {}").modifiers == Seq(PUBLIC_MODIFIER))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Protected outer") {
    assert(typeDeclaration("protected class Dummy {}").modifiers.isEmpty)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 0-9: Modifier 'protected' is not supported on classes\n")
  }

  test("Private outer") {
    assert(typeDeclaration("private class Dummy {}").modifiers.isEmpty)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 14-19: Private modifier is not allowed on outer classes\n")
  }

  test("No modifier class") {
    assert(typeDeclaration("class Dummy {}").modifiers == Seq(PUBLIC_MODIFIER))
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 6-11: Outer classes must be declared either 'global' or 'public'\n")
  }

  test("Illegal modifier class") {
    assert(typeDeclaration("global static class Dummy {}").modifiers == Seq(GLOBAL_MODIFIER))
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 7-13: Modifier 'static' is not supported on classes\n")
  }

  test("With sharing class") {
    val modifiers = typeDeclaration("public with sharing class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, WITH_SHARING_MODIFIER))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Without sharing class") {
    val modifiers = typeDeclaration("public without sharing class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, WITHOUT_SHARING_MODIFIER))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Inherited sharing class") {
    val modifiers = typeDeclaration("public inherited sharing class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, INHERITED_SHARING_MODIFIER))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Inherited sharing class (mixed case)") {
    val modifiers = typeDeclaration("public inHerited shaRing class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, INHERITED_SHARING_MODIFIER))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Deprecated annotation class") {
    val modifiers = typeDeclaration("@Deprecated public class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, DEPRECATED_ANNOTATION))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Deprecated annotation class (mixed case)") {
    val modifiers = typeDeclaration("@DeprecAted public class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, DEPRECATED_ANNOTATION))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("IsTest annotation class") {
    val modifiers = typeDeclaration("@IsTest public class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, ISTEST_ANNOTATION))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("TestVisible annotation class") {
    val modifiers = typeDeclaration("@TestVisible public class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, TEST_VISIBLE_ANNOTATION))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("SuppressWarnings annotation class") {
    val modifiers = typeDeclaration("@SuppressWarnings public class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, SUPPRESS_WARNINGS_ANNOTATION))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("RestResource annotation class") {
    val modifiers = typeDeclaration("@RestResource public class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, REST_RESOURCE_ANNOTATION))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("HttpGet annotation class") {
    val modifiers = typeDeclaration("@HttpGet public class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER))
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 0-8: Unexpected annotation 'HttpGet' on class declaration\n")
  }

  test("SuppressWarnings & isTest annotation class") {
    val modifiers = typeDeclaration("@SuppressWarnings public @isTest class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, SUPPRESS_WARNINGS_ANNOTATION, ISTEST_ANNOTATION))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Global inner") {
    assert(typeDeclarationInner("global class Dummy {global class Inner{}}").modifiers == Seq(GLOBAL_MODIFIER))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Global inner of public outer") {
    assert(typeDeclarationInner("public class Dummy {global class Inner{}}").modifiers == Seq(GLOBAL_MODIFIER))
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 13-18: Classes enclosing globals or webservices must also be declared global\n")
  }

  test("Public inner") {
    assert(typeDeclarationInner("public class Dummy {public class Inner{}}").modifiers == Seq(PUBLIC_MODIFIER))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Protected inner") {
    assert(typeDeclarationInner("public class Dummy {protected class Inner{}}").modifiers.isEmpty)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 20-29: Modifier 'protected' is not supported on classes\n")
  }

  test("Private inner") {
    assert(typeDeclarationInner("public class Dummy {private class Inner{}}").modifiers == Seq(PRIVATE_MODIFIER))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("No modifier inner class") {
    assert(typeDeclarationInner("public class Dummy {class Inner{}}").modifiers.isEmpty)
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Illegal modifier inner class") {
    assert(typeDeclarationInner("global class Dummy {static class Inner{}}").modifiers.isEmpty)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 20-26: Modifier 'static' is not supported on classes\n")
  }

  test("With sharing inner class") {
    val modifiers = typeDeclarationInner("public class Dummy {with sharing class Inner {}}").modifiers
    assert(modifiers.toSet == Set(WITH_SHARING_MODIFIER))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Without sharing inner class") {
    val modifiers = typeDeclarationInner("public without sharing class Dummy {without sharing class Inner {}}").modifiers
    assert(modifiers.toSet == Set(WITHOUT_SHARING_MODIFIER))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Inherited sharing inner  class") {
    val modifiers = typeDeclarationInner("public inherited sharing class Dummy {inherited sharing class Inner {}}").modifiers
    assert(modifiers.toSet == Set(INHERITED_SHARING_MODIFIER))
    assert(!defaultOrg.issues.hasMessages)
  }
}
