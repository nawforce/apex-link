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
import org.scalatest.funsuite.AnyFunSuite

class VarTest extends AnyFunSuite with TestHelper {

  test("Reserved local var") {
    typeDeclaration("public class Dummy { void func() {String package;}}")
    assert(dummyIssues == "Error: line 1 at 41-48: 'package' is a reserved identifier in Apex\n")
  }

  test("Duplicate local var") {
    typeDeclaration("public class Dummy { void func() {String a; String a;}}")
    assert(dummyIssues == "Error: line 1 at 51-52: Duplicate variable 'a'\n")
  }

  test("Duplicate local var, same declaration") {
    typeDeclaration("public class Dummy { void func() {String a, a;}}")
    assert(dummyIssues == "Error: line 1 at 44-45: Duplicate variable 'a'\n")
  }

  test("Duplicate local var, nested") {
    typeDeclaration("public class Dummy { void func() {String a; while (true) {String a;}}}")
    assert(dummyIssues == "Error: line 1 at 65-66: Duplicate variable 'a'\n")
  }

  test("Reserved for var") {
    typeDeclaration(
      "public class Dummy { void func() {for (Integer package=0; package<0; package++){}}}"
    )
    assert(dummyIssues == "Error: line 1 at 47-54: 'package' is a reserved identifier in Apex\n")
  }

  test("Reserved for-each var") {
    typeDeclaration(
      "public class Dummy { void func() {for (Integer package: new List<Integer>{}){}}}"
    )
    assert(dummyIssues == "Error: line 1 at 47-54: 'package' is a reserved identifier in Apex\n")
  }

  test("Duplicate for vars") {
    typeDeclaration(
      "public class Dummy { void func() {for (Integer i=0; i<0; i++){} for(Integer i=0; i<0; i++){} }}"
    )
    assert(!hasIssues)
  }

  test("Shadow local var") {
    typeDeclaration("public class Dummy { String a; void func() {String a;}}")
    assert(
      dummyIssues
        .startsWith("Warning: line 1 at 44-52: Local variable is hiding class field 'a', see")
    )
  }

  test("Shadow local var, inner class") {
    typeDeclaration("public class Dummy { class Dummy2 {String a; void func() {String a;}}}")
    assert(
      dummyIssues
        .startsWith("Warning: line 1 at 58-66: Local variable is hiding class field 'a', see")
    )
  }

  test("Shadow local var, not extending") {
    typeDeclaration("public class Dummy {String a; class Dummy2 { void func() {String a;}}}")
    assert(!hasIssues)
  }

  test("Shadow local var, extending") {
    typeDeclaration(
      "public virtual class Dummy {String a; class Dummy2 extends Dummy { void func() {String a;}}}"
    )
    assert(
      dummyIssues
        .startsWith("Warning: line 1 at 80-88: Local variable is hiding class field 'a', see ")
    )
  }
}
