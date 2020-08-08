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
package com.nawforce.common.cst

import com.nawforce.common.api.{Name, ServerOps}
import com.nawforce.common.documents.ApexClassDocument
import com.nawforce.common.org.OrgImpl
import com.nawforce.common.path.PathFactory
import com.nawforce.common.types.apex.FullDeclaration
import com.nawforce.common.types.core.TypeDeclaration
import com.nawforce.runtime.parsers.SourceData
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class ClassModifierTest extends AnyFunSuite with BeforeAndAfter {

  private val defaultPath = PathFactory("Dummy.cls").toString
  private val defaultDoc = ApexClassDocument(PathFactory("Dummy.cls"), Name("Dummy"))
  private var defaultOrg: OrgImpl = _

  def typeDeclaration(clsText: String): TypeDeclaration = {
    OrgImpl.current.withValue(defaultOrg) {
      val td = FullDeclaration.create(defaultOrg.unmanaged, defaultDoc, SourceData(clsText)).head
      defaultOrg.unmanaged.upsertMetadata(td)
      td.validate()
      td
    }
  }

  def typeDeclarationInner(clsText: String): TypeDeclaration = {
    typeDeclaration(clsText).asInstanceOf[ClassDeclaration]
      .bodyDeclarations.head.asInstanceOf[TypeDeclaration]
  }

  before {
    ServerOps.setAutoFlush(false)
    defaultOrg = new OrgImpl
  }

  after {
    ServerOps.setAutoFlush(true)
  }

  test("Global outer") {
    assert(typeDeclaration("global class Dummy {}").modifiers sameElements Array(GLOBAL_MODIFIER))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Public outer") {
    assert(typeDeclaration("public class Dummy {}").modifiers sameElements Array(PUBLIC_MODIFIER))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Public outer (mixed case)") {
    assert(typeDeclaration("puBlIc class Dummy {}").modifiers sameElements Array(PUBLIC_MODIFIER))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Protected outer") {
    assert(typeDeclaration("protected class Dummy {}").modifiers.isEmpty)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 0-9: Modifier 'protected' is not supported on classes\n")
  }

  test("Private outer") {
    assert(typeDeclaration("private class Dummy {}").modifiers.isEmpty)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Warning: line 1 at 14-19: Private modifier is not allowed on outer classes\n")
  }

  test("No modifier class") {
    assert(typeDeclaration("class Dummy {}").modifiers sameElements Array(PUBLIC_MODIFIER))
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 6-11: Outer classes must be declared either 'global' or 'public'\n")
  }

  test("Illegal modifier class") {
    assert(typeDeclaration("global static class Dummy {}").modifiers sameElements  Array(GLOBAL_MODIFIER))
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 7-13: Modifier 'static' is not supported on classes\n")
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
      "Error: line 1 at 0-8: Annotation '@HttpGet' is not supported on classes\n")
  }

  test("SuppressWarnings & isTest annotation class") {
    val modifiers = typeDeclaration("@SuppressWarnings public @isTest class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, SUPPRESS_WARNINGS_ANNOTATION, ISTEST_ANNOTATION))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Global inner") {
    assert(typeDeclarationInner("global class Dummy {global class Inner{}}").modifiers sameElements  Array(GLOBAL_MODIFIER))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Global inner of public outer") {
    assert(typeDeclarationInner("public class Dummy {global class Inner{}}").modifiers sameElements Array(GLOBAL_MODIFIER))
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 13-18: Classes enclosing globals or webservices must also be declared global\n")
  }

  test("Public inner") {
    assert(typeDeclarationInner("public class Dummy {public class Inner{}}").modifiers sameElements Array(PUBLIC_MODIFIER))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Protected inner") {
    assert(typeDeclarationInner("public class Dummy {protected class Inner{}}").modifiers.isEmpty)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 20-29: Modifier 'protected' is not supported on classes\n")
  }

  test("Private inner") {
    assert(typeDeclarationInner("public class Dummy {private class Inner{}}").modifiers sameElements Array(PRIVATE_MODIFIER))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("No modifier inner class") {
    assert(typeDeclarationInner("public class Dummy {class Inner{}}").modifiers.isEmpty)
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Illegal modifier inner class") {
    assert(typeDeclarationInner("global class Dummy {static class Inner{}}").modifiers.isEmpty)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 20-26: Modifier 'static' is not supported on classes\n")
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

  test("Inherited sharing inner class") {
    val modifiers = typeDeclarationInner("public inherited sharing class Dummy {inherited sharing class Inner {}}").modifiers
    assert(modifiers.toSet == Set(INHERITED_SHARING_MODIFIER))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Abstract methods must be in abstract class") {
    typeDeclaration("public class Dummy {abstract void func();}")
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 13-18: Classes with abstract methods must be abstract\n")
  }

  test("Virtual no needed on abstract class") {
    typeDeclaration("public virtual abstract class Dummy {}")
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 30-35: Abstract classes do not need virtual keyword\n")
  }

  test("Non abstract methods must have a body") {
    typeDeclaration("public class Dummy {void func();}")
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 25-29: Method must have an implementations or be marked abstract\n")
  }

  test("Abstract methods must not have a body") {
    typeDeclaration("public abstract class Dummy {abstract void func() {}}")
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 43-47: Abstract methods can not have an implementation\n")
  }

  test("Virtual not needed on abstract method") {
    typeDeclaration("public abstract class Dummy {abstract virtual void func();}")
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 51-55: Abstract methods do not need virtual keyword\n")
  }
}
