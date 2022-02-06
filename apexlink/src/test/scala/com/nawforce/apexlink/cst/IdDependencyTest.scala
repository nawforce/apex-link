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
import com.nawforce.pkgforce.names.Name
import org.scalatest.funsuite.AnyFunSuite

class IdDependencyTest extends AnyFunSuite with TestHelper {

  test("Local func does not create dependencies") {
    val tds = typeDeclarations(Map("Dummy.cls" -> "public class Dummy {void func() {func();} }"))
    assert(!hasIssues)
    assert(tds.head.dependencies().isEmpty)
    assert(tds.head.methods.head.dependencies().isEmpty)
  }

  test("Missing Static func creates error") {
    val tds = typeDeclarations(Map("Dummy.cls" -> "public class Dummy {void func() {A.func();} }"))
    assert(
      dummyIssues ==
        "Missing: line 1 at 33-34: No variable or type found for 'A' on 'Dummy'\n"
    )
    assert(tds.head.dependencies().isEmpty)
  }

  test("Static func creates method dependency") {
    val tds = typeDeclarations(
      Map(
        "Dummy.cls" -> "public class Dummy {static void func() {A.func();} }",
        "A.cls"     -> "public class A {public static void func() {}}"
      )
    )
    assert(!hasIssues)
    assert(tds.head.dependencies().isEmpty)
    assert(
      tds.head.methods
        .find(_.name == Name("func"))
        .get
        .dependencies()
        .toSet
        .contains(tds.tail.head.methods.find(_.name == Name("func")).get)
    )
  }

  test("Platform func does not create dependency") {
    val tds = typeDeclarations(
      Map("Dummy.cls" -> "public class Dummy {static void func() {System.debug('Hello');} }")
    )
    assert(!hasIssues)
    assert(tds.head.dependencies().isEmpty)
    assert(tds.head.methods.find(_.name == Name("func")).get.dependencies().isEmpty)
  }

  test("Field reference creates method dependency") {
    val tds = typeDeclarations(
      Map("Dummy.cls" -> "public class Dummy {Object a; void func() {a = null;} }")
    )
    assert(!hasIssues)
    assert(tds.head.dependencies().isEmpty)

    val func  = tds.head.methods.find(_.name == Name("func")).get
    val field = tds.head.fields.find(_.name == Name("a")).get
    assert(func.dependencies().toSet.contains(field))
  }

  test("Superclass field reference creates method dependent") {
    val tds = typeDeclarations(
      Map(
        "Dummy.cls" -> "public class Dummy extends A {void func() {a = null;} }",
        "A.cls"     -> "public virtual class A {Object a;}"
      )
    )
    assert(!hasIssues)
    assert(tds.head.dependencies().toSet == tds.tail.toSet)

    val func  = tds.head.methods.find(_.name == Name("func")).get
    val field = tds(1).fields.find(_.name == Name("a")).get
    assert(func.dependencies().toSet.contains(field))
  }

  test("Hidden outer class field reference creates error") {
    val tds = typeDeclarations(
      Map("Dummy.cls" -> "public class Dummy {Object a; class B {void func() {a = null;} } }")
    )
    assert(
      dummyIssues ==
        "Missing: line 1 at 52-53: No variable or type found for 'a' on 'Dummy.B'\n"
    )
    assert(tds.head.dependencies().isEmpty)
    assert(tds.head.nestedTypes.head.dependencies().isEmpty)
    assert(tds.head.nestedTypes.head.methods.head.dependencies().isEmpty)
  }

  test("Outer class static field creates dependency") {
    val tds = typeDeclarations(
      Map(
        "Dummy.cls" -> "public class Dummy {static Object a; class B {void func() {a = null;} } }"
      )
    )
    assert(!hasIssues)
    assert(tds.head.dependencies().isEmpty)
    assert(tds.head.nestedTypes.head.dependencies().isEmpty)

    val func  = tds.head.nestedTypes.head.methods.find(_.name == Name("func")).get
    val field = tds.head.fields.find(_.name == Name("a")).get
    assert(func.dependencies().toSet.contains(field))
  }

  test("Property reference creates dependency") {
    val tds = typeDeclarations(
      Map("Dummy.cls" -> "public class Dummy {Object a {get;} void func() {a = null;} }")
    )
    assert(!hasIssues)
    assert(tds.head.dependencies().isEmpty)

    val func  = tds.head.methods.find(_.name == Name("func")).get
    val field = tds.head.fields.find(_.name == Name("a")).get
    assert(func.dependencies().toSet.contains(field))
  }

  test("Superclass property creates dependency") {
    val tds = typeDeclarations(
      Map(
        "Dummy.cls" -> "public class Dummy extends A {void func() {a = null;} }",
        "A.cls"     -> "public virtual class A {Object a {get;}}"
      )
    )
    assert(!hasIssues)
    assert(tds.head.dependencies().toSet == tds.tail.toSet)

    val func  = tds.head.methods.find(_.name == Name("func")).get
    val field = tds(1).fields.find(_.name == Name("a")).get
    assert(func.dependencies().toSet.contains(field))
  }

  test("Local var not dependent") {
    val tds = typeDeclarations(
      Map("Dummy.cls" -> "public class Dummy {void func() {Object a; a = null;} }")
    )
    assert(!hasIssues)
    assert(tds.head.methods.find(_.name == Name("func")).get.dependencies().isEmpty)
  }
}
