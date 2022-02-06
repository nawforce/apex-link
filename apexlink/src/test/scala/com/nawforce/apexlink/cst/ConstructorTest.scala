package com.nawforce.apexlink.cst

import com.nawforce.apexlink.TestHelper
import org.scalatest.funsuite.AnyFunSuite

class ConstructorTest extends AnyFunSuite with TestHelper {

  test("Basic constructor") {
    typeDeclaration("public class Dummy {public dummY() {}}")
    assert(dummyIssues.isEmpty)
  }

  test("Bad name constructor") {
    typeDeclaration("public class Dummy {public Foo() {}}")
    assert(
      dummyIssues ==
        "Error: line 1 at 27-30: Constructors should have same name as the class, maybe method return type is missing?\n"
    )
  }

  test("Duplicate no args constructor") {
    typeDeclaration("public class Dummy {public Dummy() {} private Dummy() {}}")
    assert(
      dummyIssues ==
        "Error: line 1 at 46-51: Constructor is a duplicate of an earlier constructor at line 1 at 27-32\n"
    )
  }

  test("Duplicate same single args constructor") {
    typeDeclaration("public class Dummy {public Dummy(String a) {} private Dummy(String b) {}}")
    assert(
      dummyIssues ==
        "Error: line 1 at 54-59: Constructor is a duplicate of an earlier constructor at line 1 at 27-32\n"
    )
  }

  test("Duplicate same multi args constructor") {
    typeDeclaration(
      "public class Dummy {public Dummy(String a, Integer b) {} private Dummy(String c, Integer d) {}}"
    )
    assert(
      dummyIssues ==
        "Error: line 1 at 65-70: Constructor is a duplicate of an earlier constructor at line 1 at 27-32\n"
    )
  }

  test("Duplicate same args (different typeRef) constructor") {
    typeDeclaration(
      "public class Dummy {public Dummy(String a) {} private Dummy(System.String b) {}}"
    )
    assert(
      dummyIssues ==
        "Error: line 1 at 54-59: Constructor is a duplicate of an earlier constructor at line 1 at 27-32\n"
    )
  }

  test("Multiple Duplicate no args constructor") {
    typeDeclaration("public class Dummy {public Dummy() {} private Dummy() {} private Dummy() {}}")
    assert(
      dummyIssues ==
        "Error: line 1 at 46-51: Constructor is a duplicate of an earlier constructor at line 1 at 27-32\n" +
          "Error: line 1 at 65-70: Constructor is a duplicate of an earlier constructor at line 1 at 27-32\n"
    )
  }
}
