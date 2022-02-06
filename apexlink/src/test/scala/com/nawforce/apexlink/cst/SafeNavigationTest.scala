/*
 Copyright (c) 2021 Kevin Jones, All rights reserved.
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

class SafeNavigationTest extends AnyFunSuite with TestHelper {

  test("Field Reference") {
    typeDeclaration("public class Dummy {String a; { String b = this?.a; }}")
    assert(!hasIssues)
  }

  test("Method Reference") {
    typeDeclaration("public class Dummy {String func(){} { String b = this?.func(); }}")
    assert(!hasIssues)
  }

  test("Field nested reference") {
    typeDeclaration("public class Dummy {Dummy a; { Dummy b = this?.a?.a; }}")
    assert(!hasIssues)
  }

  test("Method nexted reference") {
    typeDeclaration("public class Dummy {Dummy func(){} { Dummy b = this?.func()?.func(); }}")
    assert(!hasIssues)
  }

  test("Static field reference") {
    typeDeclaration("public class Dummy {static String a; { String b = Dummy?.a; }}")
    assert(
      dummyIssues == "Error: line 1 at 50-58: Safe navigation operator (?.) can not be used on static references\n"
    )
  }

  test("Static method reference") {
    typeDeclaration("public class Dummy {static String func(){} { String b = Dummy?.func(); }}")
    assert(
      dummyIssues == "Error: line 1 at 56-69: Safe navigation operator (?.) can not be used on static references\n"
    )
  }
}
