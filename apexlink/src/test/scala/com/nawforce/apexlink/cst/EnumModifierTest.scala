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

class EnumModifierTest extends AnyFunSuite with TestHelper {

  test("Global outer") {
    assert(typeDeclaration("global enum Dummy {}").modifiers sameElements Array(GLOBAL_MODIFIER))
    assert(dummyIssues.isEmpty)
  }

  test("Public outer") {
    assert(typeDeclaration("public enum Dummy {}").modifiers sameElements Array(PUBLIC_MODIFIER))
    assert(dummyIssues.isEmpty)
  }

  test("Public outer (mixed case)") {
    assert(typeDeclaration("puBlIc enum Dummy {}").modifiers sameElements Array(PUBLIC_MODIFIER))
    assert(dummyIssues.isEmpty)
  }

  test("Protected outer") {
    assert(typeDeclaration("protected enum Dummy {}").modifiers.isEmpty)
    assert(
      dummyIssues ==
        "Error: line 1 at 0-9: Modifier 'protected' is not supported on enums\n"
    )
  }

  test("Private outer") {
    assert(typeDeclaration("private enum Dummy {}").modifiers.isEmpty)
    assert(
      dummyIssues ==
        "Error: line 1 at 13-18: Private modifier is not allowed on outer enums\n"
    )
  }

  test("No modifier class") {
    assert(typeDeclaration("enum Dummy {}").modifiers sameElements Array(PUBLIC_MODIFIER))
    assert(
      dummyIssues ==
        "Error: line 1 at 5-10: Outer enums must be declared either 'global' or 'public'\n"
    )
  }

  test("Illegal modifier") {
    assert(
      typeDeclaration("global static enum Dummy {}").modifiers sameElements Array(GLOBAL_MODIFIER)
    )
    assert(
      dummyIssues ==
        "Error: line 1 at 7-13: Modifier 'static' is not supported on enums\n"
    )
  }

  test("Deprecated annotation") {
    val modifiers = typeDeclaration("@Deprecated public enum Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, DEPRECATED_ANNOTATION))
    assert(dummyIssues.isEmpty)
  }

  test("Deprecated annotation (mixed case)") {
    val modifiers = typeDeclaration("@DeprecAted public enum Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, DEPRECATED_ANNOTATION))
    assert(dummyIssues.isEmpty)
  }

  test("TestVisible annotation") {
    val modifiers = typeDeclaration("@TestVisible public enum Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, TEST_VISIBLE_ANNOTATION))
    assert(dummyIssues.isEmpty)
  }

  test("SuppressWarnings annotation") {
    val modifiers = typeDeclaration("@SuppressWarnings('PMD') public enum Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, SUPPRESS_WARNINGS_ANNOTATION_PMD))
    assert(dummyIssues.isEmpty)
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
    assert(dummyIssues.isEmpty)
  }

  test("Global inner") {
    assert(
      typeDeclarationInner("global class Dummy {global enum Inner{}}").modifiers sameElements Array(
        GLOBAL_MODIFIER
      )
    )
    assert(dummyIssues.isEmpty)
  }

  test("Global inner of public outer") {
    assert(
      typeDeclarationInner("public class Dummy {global enum Inner{}}").modifiers sameElements Array(
        GLOBAL_MODIFIER
      )
    )
    assert(
      dummyIssues ==
        "Error: line 1 at 32-37: Enclosing class must be declared global to use global or webservice modifiers\n"
    )
  }

  test("Public inner") {
    assert(
      typeDeclarationInner("public class Dummy {public enum Inner{}}").modifiers sameElements Array(
        PUBLIC_MODIFIER
      )
    )
    assert(dummyIssues.isEmpty)
  }

  test("Protected inner") {
    assert(typeDeclarationInner("public class Dummy {protected enum Inner{}}").modifiers.isEmpty)
    assert(
      dummyIssues ==
        "Error: line 1 at 20-29: Modifier 'protected' is not supported on enums\n"
    )
  }

  test("Private inner") {
    assert(
      typeDeclarationInner(
        "public class Dummy {private enum Inner{}}"
      ).modifiers sameElements Array(PRIVATE_MODIFIER)
    )
    assert(dummyIssues.isEmpty)
  }

  test("No modifier inner") {
    assert(typeDeclarationInner("public class Dummy {class Inner{}}").modifiers.isEmpty)
    assert(dummyIssues.isEmpty)
  }

  test("Illegal modifier inner") {
    assert(typeDeclarationInner("global class Dummy {static enum Inner{}}").modifiers.isEmpty)
    assert(
      dummyIssues ==
        "Error: line 1 at 20-26: Modifier 'static' is not supported on enums\n"
    )
  }
}
