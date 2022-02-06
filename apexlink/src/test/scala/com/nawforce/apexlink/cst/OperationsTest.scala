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

class OperationsTest extends AnyFunSuite with TestHelper {

  test("String prefix bug on SObject") {
    typeDeclaration("public class Dummy {{Object a;  String b = '' + + a;}}")
    assert(!hasIssues)
  }

  test("Date addition") {
    typeDeclaration("public class Dummy {{Date a;  Date b = a + 1;}}")
    assert(!hasIssues)
  }

  test("Date subtraction") {
    typeDeclaration("public class Dummy {{Date a;  Date b = a - 12l;}}")
    assert(!hasIssues)
  }

  test("Datetime addition") {
    typeDeclaration("public class Dummy {{Datetime a;  Datetime b = a + 1;}}")
    assert(!hasIssues)
  }

  test("Datetime subtraction") {
    typeDeclaration("public class Dummy {{Datetime a;  Datetime b = a - 12l;}}")
    assert(!hasIssues)
  }

  test("Time addition") {
    typeDeclaration("public class Dummy {{Time a;  Time b = a + 1;}}")
    assert(!hasIssues)
  }

  test("Time subtraction") {
    typeDeclaration("public class Dummy {{Time a;  Time b = a - 12l;}}")
    assert(!hasIssues)
  }

  test("List assignment to Object") {
    typeDeclaration("public class Dummy {{Object a; a = new List<String>{''}; }}")
    assert(!hasIssues)
  }

  test("sObject List to Specific Object List") {
    typeDeclaration("public class Dummy {{List<Account> a; a = new List<sObject>(); }}")
    assert(!hasIssues)
  }

  test("Alternative not equal") {
    typeDeclaration("public class Dummy {{Boolean a; a = 1 <> 2; }}")
    assert(!hasIssues)
  }

  test("Split comparator") {
    typeDeclaration("public class Dummy {{Boolean a; a = 1 < = 2; }}")
    assert(!hasIssues)
  }

  test("Bitwise Integer to Integer") {
    typeDeclaration("public class Dummy {{Integer a; a = a & 2; }}")
    assert(!hasIssues)
  }

  test("Bitwise Long to Integer") {
    typeDeclaration("public class Dummy {{Long a; a = a | 2; }}")
    assert(!hasIssues)
  }

  test("Bitwise Boolean to Boolean") {
    typeDeclaration("public class Dummy {{Boolean a; a = a ^ false; }}")
    assert(!hasIssues)
  }

  test("Bitwise Assigment Integer to Integer") {
    typeDeclaration("public class Dummy {{Integer a; a ^= a; }}")
    assert(!hasIssues)
  }

  test("Bitwise Assigment Integer to Long") {
    typeDeclaration("public class Dummy {{Long a; a &= 2; }}")
    assert(!hasIssues)
  }

  test("Bitwise Assigment Long to Integer") {
    typeDeclaration("public class Dummy {{Integer a; a |= 22l; }}")
    assert(
      dummyIssues ==
        "Error: line 1 at 32-40: Bitwise operation only allowed between Integer, Long & Boolean types, not 'System.Integer' and 'System.Long'\n"
    )
  }

  test("Bitwise Shift in Array Index") {
    typeDeclaration("public class Dummy {{List<String> a; Integer b; String c = a[b << 2];}}")
    assert(!hasIssues)
  }

  test("String concat assignment") {
    typeDeclaration("public class Dummy {{String a; a += 12;}}")
    assert(!hasIssues)
  }

  test("Enum value assignment") {
    typeDeclaration("public class Dummy {enum MyEnum{A} {MyEnum a; a = Dummy.MyEnum.A;}}")
    assert(!hasIssues)
  }

  test("Nested ternary") {
    typeDeclaration("public class Dummy {{ String a; a = (1==0) ? 'a' : (1==-1) ? null : 'b';}}")
    assert(!hasIssues)
  }

  test("Ternary common base") {
    typeDeclaration(
      "public virtual class Dummy {class A extends Dummy {} class B extends Dummy {} { A a; B b; Object c = 2>0 ? a : b;}}"
    )
    assert(!hasIssues)
  }

  test("Ternary SObjectType") {
    typeDeclaration(
      "public virtual class Dummy {{ SObjectType a = 2>0 ? Account.SObjectType : Contact.SObjectType;}}"
    )
    assert(!hasIssues)
  }
}
