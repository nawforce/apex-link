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
package com.nawforce.common.cst

import com.nawforce.common.org.OrgImpl
import com.nawforce.common.path.PathFactory
import com.nawforce.common.types.TypeDeclaration
import com.nawforce.common.types.apex.FullDeclaration
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class SwitchTest extends AnyFunSuite with BeforeAndAfter {
  private val defaultPath = PathFactory("Dummy.cls")
  private var defaultOrg: OrgImpl = new OrgImpl

  def typeDeclaration(clsText: String): Option[TypeDeclaration] = {
    OrgImpl.current.withValue(defaultOrg) {
      val td = FullDeclaration.create(defaultOrg.unmanaged, defaultPath, clsText)
      td.headOption.foreach(_.validate())
      td.headOption
    }
  }

  before {
    defaultOrg = new OrgImpl
  }

  test("Empty switch") {
    typeDeclaration("public class Dummy {{switch on 'A' {}}}")
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1: mismatched input '}' expecting 'when'\n")
  }

  test("Bad switch type") {
    typeDeclaration("public class Dummy {{Object a; switch on a {when 'A' {} }}}")
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 41-42: Switch expression must be a Integer, Long, String, SObject record or enum value, not 'Object'\n")
  }

  test("Bad SObject type") {
    typeDeclaration("public class Dummy {{Account a; switch on a {when Account r {} }}}")
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 42-43: Switch expression must be a Integer, Long, String, SObject record or enum value, not 'Schema.Account'\n")
  }

  test("When else not last") {
    typeDeclaration("public class Dummy {{switch on 'A' {when else {} when 'A' {} }}}")
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 36-48: 'when else' must be the last when block\n")
  }

  test("Double null") {
    typeDeclaration("public class Dummy {{switch on 'A' {when null {} when null {} }}}")
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 54-58: There should only be one 'when null' block in a switch\n")
  }

  test("Enum single control") {
    typeDeclaration("public class Dummy {{ApexPages.Severity a;switch on a {when CONFIRM {} }}}")
  }

  test("Enum single control (bad value)") {
    typeDeclaration("public class Dummy {{ApexPages.Severity a;switch on a {when BAD {} }}}")
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 60-63: Value must be a enum constant\n")
  }

  test("Enum multi control") {
    typeDeclaration("public class Dummy {{ApexPages.Severity a;switch on a {when CONFIRM {} when ERROR {}}}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Enum multi control (duplicates)") {
    typeDeclaration("public class Dummy {{ApexPages.Severity a;switch on a {when CONFIRM {} when CONFIRM {}}}}")
    assert(defaultOrg.issues.getMessages(defaultPath) == "Error: line 1 at 52-53: Duplicate when case for confirm\n")
  }

  test("Enum multi-part control") {
    typeDeclaration("public class Dummy {{ApexPages.Severity a;switch on a {when CONFIRM, ERROR {} }}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Enum multi-part control (duplicates)") {
    typeDeclaration("public class Dummy {{ApexPages.Severity a;switch on a {when CONFIRM, Confirm {} }}}")
    assert(defaultOrg.issues.getMessages(defaultPath) == "Error: line 1 at 52-53: Duplicate when case for confirm\n")
  }

  test("Enum switch with Null") {
    typeDeclaration("public class Dummy {{ApexPages.Severity a;switch on a {when null {} when else {}}}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Enum switch with string") {
    typeDeclaration("public class Dummy {{ApexPages.Severity a;switch on a {when 'a' {}}}}")
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 60-63: An Enum value is required for this value\n")
  }

  test("Enum switch with id id") {
    typeDeclaration("public class Dummy {{ApexPages.Severity a;switch on a {when Account record {}}}}")
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 60-67: Expecting an enum constant value\n")
  }

  test("Enum switch with integer") {
    typeDeclaration("public class Dummy {{ApexPages.Severity a;switch on a {when 42 {}}}}")
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 60-62: An Enum value is required for this value\n")
  }

  test("String single control") {
    typeDeclaration("public class Dummy {{switch on 'A' {when 'A' {} }}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("String multi control") {
    typeDeclaration("public class Dummy {{switch on 'A' {when 'A' {} when 'B' {}}}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("String multi control (duplicate)") {
    typeDeclaration("public class Dummy {{switch on 'A' {when 'A' {} when 'A' {}}}}")
    assert(defaultOrg.issues.getMessages(defaultPath) == "Error: line 1 at 31-34: Duplicate when case for 'A'\n")
  }

  test("String multi-part control") {
    typeDeclaration("public class Dummy {{switch on 'A' {when 'A', 'B' {} }}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("String multi-part control (duplicate)") {
    typeDeclaration("public class Dummy {{switch on 'A' {when 'A', 'A' {} }}}")
    assert(defaultOrg.issues.getMessages(defaultPath) == "Error: line 1 at 31-34: Duplicate when case for 'A'\n")
  }

  test("String switch with Null") {
    typeDeclaration("public class Dummy {{switch on 'A' {when null {} when else {}}}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("String switch with id") {
    typeDeclaration("public class Dummy {{switch on 'A' {when Bar {}}}}")
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 41-44: A System.String literal is required for this value\n")
  }

  test("String switch with id id") {
    typeDeclaration("public class Dummy {{switch on 'A' {when Account record {}}}}")
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 41-48: A System.String literal is required for this value\n")
  }

  test("String switch with integer") {
    typeDeclaration("public class Dummy {{switch on 'A' {when 42 {}}}}")
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 41-43: A System.String literal is required for this value\n")
  }

  test("Integer single control") {
    typeDeclaration("public class Dummy {{switch on 1 {when 1 {} }}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Integer multi control") {
    typeDeclaration("public class Dummy {{switch on 1 {when 1 {} when 2 {}}}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Integer multi control (duplicate)") {
    typeDeclaration("public class Dummy {{switch on 1 {when 1 {} when 1 {}}}}")
    assert(defaultOrg.issues.getMessages(defaultPath) == "Error: line 1 at 31-32: Duplicate when case for 1\n")
  }

  test("Integer multi-part control") {
    typeDeclaration("public class Dummy {{switch on 1 {when 1, 2 {} }}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Integer multi-part control (duplicate)") {
    typeDeclaration("public class Dummy {{switch on 1 {when 1, 1 {} }}}")
    assert(defaultOrg.issues.getMessages(defaultPath) == "Error: line 1 at 31-32: Duplicate when case for 1\n")
  }

  test("Integer switch with Null") {
    typeDeclaration("public class Dummy {{switch on 1 {when null {} when else {}}}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Integer switch with id") {
    typeDeclaration("public class Dummy {{switch on 1 {when Bar {}}}}")
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 39-42: A System.Integer literal is required for this value\n")
  }

  test("Integer switch with id id") {
    typeDeclaration("public class Dummy {{switch on 1 {when Account record {}}}}")
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 39-46: A System.Integer literal is required for this value\n")
  }

  test("Integer switch with string") {
    typeDeclaration("public class Dummy {{switch on 1 {when 'a' {}}}}")
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 39-42: A System.Integer literal is required for this value\n")
  }

  test("Long single control") {
    typeDeclaration("public class Dummy {{switch on 1l {when 1 {} }}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("SObject single control") {
    typeDeclaration("public class Dummy {{SObject a; switch on a {when Account r{} }}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("SObject multi control") {
    typeDeclaration("public class Dummy {{SObject a; switch on a {when Account r1{} when Case r2{}}}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("SObject multi control (duplicate)") {
    typeDeclaration("public class Dummy {{SObject a; switch on a {when Account r1{} when Account r2{}}}}")
    assert(defaultOrg.issues.getMessages(defaultPath) == "Error: line 1 at 42-43: Duplicate when case for account\n")
  }

  test("SObject without var") {
    typeDeclaration("public class Dummy {{SObject a; switch on a {when Account {} }}}")
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 50-57: An SObject name and variable name are required for this value\n")
  }

  test("SObject bad name") {
    typeDeclaration("public class Dummy {{SObject a; switch on a {when Foo r {} }}}")
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 50-53: No type declaration found for 'Foo'\n")
  }

  test("SObject switch with Null") {
    typeDeclaration("public class Dummy {{SObject a; switch on a {when null {} when else {}}}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("SObject switch with string") {
    typeDeclaration("public class Dummy {{SObject a; switch on a {when 'a' {}}}}")
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1 at 50-53: An SObject name and variable name are required for this value\n")
  }

  test("SObject control introduces var") {
    typeDeclaration("public class Dummy {{SObject a; switch on a {when Account r {Account s = r;} }}}")
    defaultOrg.issues.dumpMessages(false)
    assert(!defaultOrg.issues.hasMessages)
  }
}
