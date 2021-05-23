/*
 [The "BSD licence"]
 Copyright (c) 2019 Kevin Jones
 All rights reserved.

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

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.nawforce.apexlink.cst

import com.nawforce.apexlink.TestHelper
import org.scalatest.funsuite.AnyFunSuite

class ImplementsTest extends AnyFunSuite with TestHelper {

  test("Missing class interface") {
    assert(typeDeclarations(Map("Dummy.cls" -> "global class Dummy implements A {}")).nonEmpty)
    assert(
      dummyIssues ==
        "Missing: line 1 at 13-18: No type declaration found for 'A'\n")
  }

  test("Missing class second interface") {
    typeDeclarations(
      Map("Dummy.cls" -> "global class Dummy implements A, B {}",
          "A.cls" -> "public interface A {}"))
    assert(
      dummyIssues ==
        "Missing: line 1 at 13-18: No type declaration found for 'B'\n")
  }

  test("Class implements class") {
    typeDeclarations(
      Map("Dummy.cls" -> "global class Dummy implements A {}", "A.cls" -> "public class A {}"))
    assert(
      dummyIssues ==
        "Error: line 1 at 13-18: Type 'A' must be an interface\n")
  }

  test("Class implements enum") {
    typeDeclarations(
      Map("Dummy.cls" -> "global class Dummy implements A {}", "A.cls" -> "public enum A {}"))
    assert(
      dummyIssues ==
        "Error: line 1 at 13-18: Type 'A' must be an interface\n")
  }

  test("Interface extends class") {
    typeDeclarations(
      Map("Dummy.cls" -> "global interface Dummy extends A {}", "A.cls" -> "public class A {}"))
    assert(
      dummyIssues ==
        "Error: line 1 at 17-22: Type 'A' must be an interface\n")
  }

  test("Interface extends enum") {
    typeDeclarations(
      Map("Dummy.cls" -> "global interface Dummy extends A {}", "A.cls" -> "public enum A {}"))
    assert(
      dummyIssues ==
        "Error: line 1 at 17-22: Type 'A' must be an interface\n")
  }

  test("Class implements Database.Batchable<sObject>") {
    typeDeclarations(
      Map("Dummy.cls" ->
        """
          | global class Dummy implements Database.Batchable<sObject> {
          |   Iterable<sObject> start(Database.BatchableContext param1) {}
          |   void execute(Database.BatchableContext param1, List<SObject> param2) {}
          |   void finish(Database.BatchableContext param1) {}
          | }
          |""".stripMargin))
    assert(dummyIssues == "")
  }
}
