package com.nawforce.common.cst

import com.nawforce.common.TestHelper
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
      dummyIssues == "Error: line 1 at 50-58: Safe navigation operator (?.) can not be used on static references\n")
  }

  test("Static method reference") {
    typeDeclaration("public class Dummy {static String func(){} { String b = Dummy?.func(); }}")
    assert(
      dummyIssues == "Error: line 1 at 56-69: Safe navigation operator (?.) can not be used on static references\n")
  }
}
