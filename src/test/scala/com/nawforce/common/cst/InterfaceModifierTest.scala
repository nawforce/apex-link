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

import com.nawforce.common.org.OrgImpl
import com.nawforce.common.path.PathFactory
import com.nawforce.common.types.apex.FullDeclaration
import com.nawforce.common.types.core.TypeDeclaration
import com.nawforce.runtime.parsers.SourceData
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class InterfaceModifierTest extends AnyFunSuite with BeforeAndAfter {
  private val defaultPath = PathFactory("Dummy.cls").toString
  private var defaultOrg: OrgImpl = new OrgImpl

  def typeDeclaration(clsText: String): TypeDeclaration = {
    OrgImpl.current.withValue(defaultOrg) {
      val td = FullDeclaration.create(defaultOrg.unmanaged, PathFactory("Dummy.cls"), SourceData(clsText)).head
      defaultOrg.unmanaged.upsertMetadata(td)
      td.validate()
      td
    }
  }

  before {
    defaultOrg = new OrgImpl
  }

  def typeDeclarationInner(clsText: String): TypeDeclaration = {
    typeDeclaration(clsText).asInstanceOf[ClassDeclaration]
        .bodyDeclarations.head.asInstanceOf[TypeDeclaration]
  }

  test("Global outer") {
    assert(typeDeclaration("global interface Dummy {}").modifiers sameElements Array(GLOBAL_MODIFIER))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Public outer") {
    assert(typeDeclaration("public interface Dummy {}").modifiers sameElements Array(PUBLIC_MODIFIER))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Public outer (mixed case)") {
    assert(typeDeclaration("puBlIc interface Dummy {}").modifiers sameElements Array(PUBLIC_MODIFIER))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Protected outer") {
    assert(typeDeclaration("protected interface Dummy {}").modifiers.isEmpty)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 0-9: Modifier 'protected' is not supported on interfaces\n")
  }

  test("Private outer") {
    assert(typeDeclaration("private interface Dummy {}").modifiers.isEmpty)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 18-23: Private modifier is not allowed on outer interfaces\n")
  }

  test("No modifier class") {
    assert(typeDeclaration("interface Dummy {}").modifiers sameElements Array(PUBLIC_MODIFIER))
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 10-15: Outer interfaces must be declared either 'global' or 'public'\n")
  }

  test("Illegal modifier") {
    assert(typeDeclaration("global static interface Dummy {}").modifiers sameElements Array(GLOBAL_MODIFIER))
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 7-13: Modifier 'static' is not supported on interfaces\n")
  }

  test("Deprecated annotation") {
    val modifiers = typeDeclaration("@Deprecated public interface Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, DEPRECATED_ANNOTATION))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Deprecated annotation (mixed case)") {
    val modifiers = typeDeclaration("@DeprecAted public interface Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, DEPRECATED_ANNOTATION))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("TestVisible annotation") {
    val modifiers = typeDeclaration("@TestVisible public interface Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, TEST_VISIBLE_ANNOTATION))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("SuppressWarnings annotation") {
    val modifiers = typeDeclaration("@SuppressWarnings public interface Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, SUPPRESS_WARNINGS_ANNOTATION))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("SuppressWarnings & TestVisible annotation class") {
    val modifiers = typeDeclaration("@SuppressWarnings public @TestVisible class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, SUPPRESS_WARNINGS_ANNOTATION, TEST_VISIBLE_ANNOTATION))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Global inner") {
    assert(typeDeclarationInner("global class Dummy {global interface Inner{}}").modifiers sameElements Array(GLOBAL_MODIFIER))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Global inner of public outer") {
    assert(typeDeclarationInner("public class Dummy {global interface Inner{}}").modifiers sameElements Array(GLOBAL_MODIFIER))
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 13-18: Classes enclosing globals or webservices must also be declared global\n")
  }

  test("Public inner") {
    assert(typeDeclarationInner("public class Dummy {public interface Inner{}}").modifiers sameElements Array(PUBLIC_MODIFIER))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Protected inner") {
    assert(typeDeclarationInner("public class Dummy {protected interface Inner{}}").modifiers.isEmpty)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 20-29: Modifier 'protected' is not supported on interfaces\n")
  }

  test("Private inner") {
    assert(typeDeclarationInner("public class Dummy {private interface Inner{}}").modifiers sameElements Array(PRIVATE_MODIFIER))
    assert(!defaultOrg.issues.hasMessages)
  }

  test("No modifier inner") {
    assert(typeDeclarationInner("public class Dummy {class Inner{}}").modifiers.isEmpty)
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Illegal modifier inner") {
    assert(typeDeclarationInner("global class Dummy {static interface Inner{}}").modifiers.isEmpty)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 20-26: Modifier 'static' is not supported on interfaces\n")
  }

  test("Illegal method modifier") {
    typeDeclaration("global interface Dummy {public void foo();}")
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 36-39: Modifier 'public' is not supported on interface methods\n")
  }

  test("Illegal method annotation") {
    typeDeclaration("global interface Dummy {@isTest void foo();}")
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 37-40: Modifier '@IsTest' is not supported on interface methods\n")
  }
}
