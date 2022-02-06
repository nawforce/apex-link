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

import scala.collection.immutable.ArraySeq

class ClassModifierTest extends AnyFunSuite with TestHelper {

  test("Global outer") {
    assert(typeDeclaration("global class Dummy {}").modifiers == ArraySeq(GLOBAL_MODIFIER))
    assert(dummyIssues.isEmpty)
  }

  test("Public outer") {
    assert(typeDeclaration("public class Dummy {}").modifiers == ArraySeq(PUBLIC_MODIFIER))
    assert(dummyIssues.isEmpty)
  }

  test("Public outer (mixed case)") {
    assert(typeDeclaration("puBlIc class Dummy {}").modifiers == ArraySeq(PUBLIC_MODIFIER))
    assert(dummyIssues.isEmpty)
  }

  test("Protected outer") {
    assert(typeDeclaration("protected class Dummy {}").modifiers.isEmpty)
    assert(
      dummyIssues == "Error: line 1 at 0-9: Modifier 'protected' is not supported on classes\n"
    )
  }

  test("Private outer") {
    assert(typeDeclaration("private class Dummy {}").modifiers.isEmpty)
    assert(
      dummyIssues == "Error: line 1 at 14-19: Private modifier is not allowed on outer classes\n"
    )
  }

  test("No modifier class") {
    assert(typeDeclaration("class Dummy {}").modifiers == ArraySeq(PUBLIC_MODIFIER))
    assert(
      dummyIssues == "Error: line 1 at 6-11: Outer classes must be declared either 'global' or 'public'\n"
    )
  }

  test("Illegal modifier class") {
    assert(typeDeclaration("global static class Dummy {}").modifiers == ArraySeq(GLOBAL_MODIFIER))
    assert(dummyIssues == "Error: line 1 at 7-13: Modifier 'static' is not supported on classes\n")
  }

  test("With sharing class") {
    val modifiers = typeDeclaration("public with sharing class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, WITH_SHARING_MODIFIER))
    assert(dummyIssues.isEmpty)
  }

  test("Without sharing class") {
    val modifiers = typeDeclaration("public without sharing class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, WITHOUT_SHARING_MODIFIER))
    assert(dummyIssues.isEmpty)
  }

  test("Inherited sharing class") {
    val modifiers = typeDeclaration("public inherited sharing class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, INHERITED_SHARING_MODIFIER))
    assert(dummyIssues.isEmpty)
  }

  test("Inherited sharing class (mixed case)") {
    val modifiers = typeDeclaration("public inHerited shaRing class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, INHERITED_SHARING_MODIFIER))
    assert(dummyIssues.isEmpty)
  }

  test("Deprecated annotation class") {
    val modifiers = typeDeclaration("@Deprecated public class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, DEPRECATED_ANNOTATION))
    assert(dummyIssues.isEmpty)
  }

  test("Deprecated annotation class (mixed case)") {
    val modifiers = typeDeclaration("@DeprecAted public class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, DEPRECATED_ANNOTATION))
    assert(dummyIssues.isEmpty)
  }

  test("IsTest annotation class") {
    val modifiers = typeDeclaration("@IsTest public class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, ISTEST_ANNOTATION))
    assert(dummyIssues.isEmpty)
  }

  test("TestVisible annotation class") {
    val modifiers = typeDeclaration("@TestVisible public class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, TEST_VISIBLE_ANNOTATION))
    assert(dummyIssues.isEmpty)
  }

  test("SuppressWarnings annotation class") {
    val modifiers = typeDeclaration("@SuppressWarnings('PMD') public class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, SUPPRESS_WARNINGS_ANNOTATION_PMD))
    assert(dummyIssues.isEmpty)
  }

  test("RestResource annotation class") {
    val modifiers = typeDeclaration("@RestResource public class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER, REST_RESOURCE_ANNOTATION))
    assert(dummyIssues.isEmpty)
  }

  test("HttpGet annotation class") {
    val modifiers = typeDeclaration("@HttpGet public class Dummy {}").modifiers
    assert(modifiers.toSet == Set(PUBLIC_MODIFIER))
    assert(
      dummyIssues == "Error: line 1 at 0-8: Annotation '@HttpGet' is not supported on classes\n"
    )
  }

  test("SuppressWarnings & isTest annotation class") {
    val modifiers =
      typeDeclaration("@SuppressWarnings('PMD') public @isTest class Dummy {}").modifiers
    assert(
      modifiers.toSet == Set(PUBLIC_MODIFIER, SUPPRESS_WARNINGS_ANNOTATION_PMD, ISTEST_ANNOTATION)
    )
    assert(dummyIssues.isEmpty)
  }

  test("Global inner") {
    assert(
      typeDeclarationInner("global class Dummy {global class Inner{}}").modifiers == ArraySeq(
        GLOBAL_MODIFIER
      )
    )
    assert(dummyIssues.isEmpty)
  }

  test("Global inner of public outer") {
    assert(
      typeDeclarationInner("public class Dummy {global class Inner{}}").modifiers == ArraySeq(
        GLOBAL_MODIFIER
      )
    )
    assert(
      dummyIssues ==
        "Error: line 1 at 33-38: Enclosing class must be declared global to use global or webservice modifiers\n"
    )
  }

  test("Public inner") {
    assert(
      typeDeclarationInner("public class Dummy {public class Inner{}}").modifiers == ArraySeq(
        PUBLIC_MODIFIER
      )
    )
    assert(dummyIssues.isEmpty)
  }

  test("Protected inner") {
    assert(typeDeclarationInner("public class Dummy {protected class Inner{}}").modifiers.isEmpty)
    assert(
      dummyIssues ==
        "Error: line 1 at 20-29: Modifier 'protected' is not supported on classes\n"
    )
  }

  test("Private inner") {
    assert(
      typeDeclarationInner("public class Dummy {private class Inner{}}").modifiers == ArraySeq(
        PRIVATE_MODIFIER
      )
    )
    assert(dummyIssues.isEmpty)
  }

  test("No modifier inner class") {
    assert(typeDeclarationInner("public class Dummy {class Inner{}}").modifiers.isEmpty)
    assert(dummyIssues.isEmpty)
  }

  test("Illegal modifier inner class") {
    assert(typeDeclarationInner("global class Dummy {static class Inner{}}").modifiers.isEmpty)
    assert(
      dummyIssues ==
        "Error: line 1 at 20-26: Modifier 'static' is not supported on classes\n"
    )
  }

  test("With sharing inner class") {
    val modifiers =
      typeDeclarationInner("public class Dummy {with sharing class Inner {}}").modifiers
    assert(modifiers.toSet == Set(WITH_SHARING_MODIFIER))
    assert(dummyIssues.isEmpty)
  }

  test("Without sharing inner class") {
    val modifiers = typeDeclarationInner(
      "public without sharing class Dummy {without sharing class Inner {}}"
    ).modifiers
    assert(modifiers.toSet == Set(WITHOUT_SHARING_MODIFIER))
    assert(dummyIssues.isEmpty)
  }

  test("Inherited sharing inner class") {
    val modifiers = typeDeclarationInner(
      "public inherited sharing class Dummy {inherited sharing class Inner {}}"
    ).modifiers
    assert(modifiers.toSet == Set(INHERITED_SHARING_MODIFIER))
    assert(dummyIssues.isEmpty)
  }

  test("Abstract methods must be in abstract class") {
    typeDeclaration("public class Dummy {abstract void func();}")
    assert(
      dummyIssues ==
        "Error: line 1 at 34-38: abstract methods can only be declared on abstract classes\n"
    )
  }

  test("Virtual no needed on abstract class") {
    typeDeclaration("public virtual abstract class Dummy {}")
    assert(
      dummyIssues ==
        "Error: line 1 at 30-35: Abstract classes are virtual classes\n"
    )
  }

  test("Non abstract methods must have a body") {
    typeDeclaration("public class Dummy {void func();}")
    assert(
      dummyIssues ==
        "Error: line 1 at 25-29: Method must have an implementations or be marked abstract\n"
    )
  }

  test("Abstract methods must not have a body") {
    typeDeclaration("public abstract class Dummy {abstract void func() {}}")
    assert(
      dummyIssues ==
        "Error: line 1 at 43-47: Abstract methods can not have an implementation\n"
    )
  }

  test("Virtual not needed on abstract method") {
    typeDeclaration("public abstract class Dummy {abstract virtual void func();}")
    assert(
      dummyIssues ==
        "Error: line 1 at 51-55: abstract methods are virtual methods\n"
    )
  }
}
