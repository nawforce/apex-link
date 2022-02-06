package com.nawforce.apexlink.cst

import com.nawforce.apexlink.TestHelper
import org.scalatest.funsuite.AnyFunSuite

class VariablesTest extends AnyFunSuite with TestHelper {

  test("Boolean variable declaration") {
    typeDeclaration("public class Dummy { {Boolean b = 1;} }")
    assert(
      dummyIssues == "Error: line 1 at 30-35: Incompatible types in assignment, from 'System.Integer' to 'System.Boolean'\n"
    )
  }

  test("Database.QueryLocator variable declaration") {
    typeDeclaration("""public class Dummy {{
                   Database.QueryLocator result;
                   System.Iterator < SObject > iteratorResult = result.iterator();
                   }}""")
    assert(!hasIssues)
  }

}
