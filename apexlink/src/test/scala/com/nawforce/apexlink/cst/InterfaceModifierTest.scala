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

package com.nawforce.apexlink.cst

import com.nawforce.apexlink.TestHelper
import com.nawforce.pkgforce.modifiers._
import org.scalatest.funsuite.AnyFunSuite

class InterfaceModifierTest extends AnyFunSuite with TestHelper {

  test("Global outer") {
    assert(
      typeDeclaration("global interface Dummy {}").modifiers sameElements Array(GLOBAL_MODIFIER)
    )
    assert(!hasIssues)
  }

  test("Public outer") {
    assert(
      typeDeclaration("public interface Dummy {}").modifiers sameElements Array(PUBLIC_MODIFIER)
    )
    assert(!hasIssues)
  }

  test("Public outer (mixed case)") {
    assert(
      typeDeclaration("puBlIc interface Dummy {}").modifiers sameElements Array(PUBLIC_MODIFIER)
    )
    assert(!hasIssues)
  }

  test("Protected outer") {
    assert(typeDeclaration("protected interface Dummy {}").modifiers.isEmpty)
    assert(
      dummyIssues ==
        "Error: line 1 at 0-9: Modifier 'protected' is not supported on interfaces\n"
    )
  }

  test("Private outer") {
    assert(typeDeclaration("private interface Dummy {}").modifiers.isEmpty)
    assert(
      dummyIssues ==
        "Error: line 1 at 18-23: Private modifier is not allowed on outer interfaces\n"
    )
  }

  test("No modifier class") {
    assert(typeDeclaration("interface Dummy {}").modifiers sameElements Array(PUBLIC_MODIFIER))
    assert(
      dummyIssues ==
        "Error: line 1 at 10-15: Outer interfaces must be declared either 'global' or 'public'\n"
    )
  }

  test("Illegal modifier") {
    assert(
      typeDeclaration("global static interface Dummy {}").modifiers sameElements Array(
        GLOBAL_MODIFIER
      )
    )
    assert(
      dummyIssues ==
        "Error: line 1 at 7-13: Modifier 'static' is not supported on interfaces\n"
    )
  }

  test("Deprecated annotation") {
    val modifiers = typeDeclaration("@Deprecated public interface Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, DEPRECATED_ANNOTATION))
    assert(!hasIssues)
  }

  test("Deprecated annotation (mixed case)") {
    val modifiers = typeDeclaration("@DeprecAted public interface Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, DEPRECATED_ANNOTATION))
    assert(!hasIssues)
  }

  test("TestVisible annotation") {
    val modifiers = typeDeclaration("@TestVisible public interface Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, TEST_VISIBLE_ANNOTATION))
    assert(!hasIssues)
  }

  test("SuppressWarnings annotation") {
    val modifiers = typeDeclaration("@SuppressWarnings('PMD') public interface Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, SUPPRESS_WARNINGS_ANNOTATION_PMD))
    assert(!hasIssues)
  }

  test("SuppressWarnings & TestVisible annotation class") {
    val modifiers =
      typeDeclaration("@SuppressWarnings('PMD') public @TestVisible class Dummy {}").modifiers
    assert(
      modifiers.toSet == Set(
        PUBLIC_MODIFIER,
        SUPPRESS_WARNINGS_ANNOTATION_PMD,
        TEST_VISIBLE_ANNOTATION
      )
    )
    assert(!hasIssues)
  }

  test("Global inner") {
    assert(
      typeDeclarationInner(
        "global class Dummy {global interface Inner{}}"
      ).modifiers sameElements Array(GLOBAL_MODIFIER)
    )
    assert(!hasIssues)
  }

  test("Global inner of public outer") {
    assert(
      typeDeclarationInner(
        "public class Dummy {global interface Inner{}}"
      ).modifiers sameElements Array(GLOBAL_MODIFIER)
    )
    assert(
      dummyIssues ==
        "Error: line 1 at 37-42: Enclosing class must be declared global to use global or webservice modifiers\n"
    )
  }

  test("Public inner") {
    assert(
      typeDeclarationInner(
        "public class Dummy {public interface Inner{}}"
      ).modifiers sameElements Array(PUBLIC_MODIFIER)
    )
    assert(!hasIssues)
  }

  test("Protected inner") {
    assert(
      typeDeclarationInner("public class Dummy {protected interface Inner{}}").modifiers.isEmpty
    )
    assert(
      dummyIssues ==
        "Error: line 1 at 20-29: Modifier 'protected' is not supported on interfaces\n"
    )
  }

  test("Private inner") {
    assert(
      typeDeclarationInner(
        "public class Dummy {private interface Inner{}}"
      ).modifiers sameElements Array(PRIVATE_MODIFIER)
    )
    assert(!hasIssues)
  }

  test("No modifier inner") {
    assert(typeDeclarationInner("public class Dummy {class Inner{}}").modifiers.isEmpty)
    assert(!hasIssues)
  }

  test("Illegal modifier inner") {
    assert(typeDeclarationInner("global class Dummy {static interface Inner{}}").modifiers.isEmpty)
    assert(
      dummyIssues ==
        "Error: line 1 at 20-26: Modifier 'static' is not supported on interfaces\n"
    )
  }

  test("Illegal method modifier") {
    typeDeclaration("global interface Dummy {public void foo();}")
    assert(
      dummyIssues ==
        "Error: line 1 at 24-30: Modifier 'public' is not supported on interface methods\n"
    )
  }

  test("Illegal method annotation") {
    typeDeclaration("global interface Dummy {@isTest void foo();}")
    assert(
      dummyIssues ==
        "Error: line 1 at 24-31: Annotation '@isTest' is not supported on interface methods\n"
    )
  }
}
