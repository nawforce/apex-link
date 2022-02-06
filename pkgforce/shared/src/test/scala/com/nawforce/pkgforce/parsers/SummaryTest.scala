/*
 [The "BSD licence"]
 Copyright (c) 2017 Kevin Jones
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
package com.nawforce.pkgforce.parsers

import com.nawforce.pkgforce.modifiers.{
  PRIVATE_MODIFIER,
  PUBLIC_MODIFIER,
  STATIC_MODIFIER,
  VIRTUAL_MODIFIER
}
import com.nawforce.pkgforce.names.Name
import com.nawforce.pkgforce.path.{Location, PathLocation}
import com.nawforce.runtime.parsers.{CodeParser, SourceData}
import com.nawforce.runtime.platform.Path
import org.scalatest.funsuite.AnyFunSuite

import scala.collection.immutable.ArraySeq

class SummaryTest extends AnyFunSuite {

  test("Class summary") {
    val path   = Path("Dummy.cls")
    val cp     = CodeParser(path, SourceData("public class Dummy {}"))
    val result = cp.parseClass()
    assert(result.issues.isEmpty)
    val root = ApexNode(cp, result.value).get
    assert(root.nature == CLASS_NATURE)
    assert(root.location == PathLocation(path, Location(1, 0, 1, 21)))
    assert(root.name == Name("Dummy"))
    assert(root.idLocation == Location(1, 13, 1, 18))
    assert(root.children.isEmpty)
    assert(root.modifiers == ArraySeq(PUBLIC_MODIFIER))
    assert(root.parseIssues.isEmpty)
    assert(root.signature == "public class Dummy")
    assert(root.description == "public")
  }

  test("Interface summary") {
    val path   = Path("Dummy.cls")
    val cp     = CodeParser(path, SourceData("public interface Dummy {}"))
    val result = cp.parseClass()
    assert(result.issues.isEmpty)
    val root = ApexNode(cp, result.value).get
    assert(root.nature == INTERFACE_NATURE)
    assert(root.location == PathLocation(path, Location(1, 0, 1, 25)))
    assert(root.name == Name("Dummy"))
    assert(root.idLocation == Location(1, 17, 1, 22))
    assert(root.children.isEmpty)
    assert(root.modifiers == ArraySeq(PUBLIC_MODIFIER))
    assert(root.parseIssues.isEmpty)
    assert(root.signature == "public interface Dummy")
    assert(root.description == "public")
  }

  test("Enum summary") {
    val path   = Path("Dummy.cls")
    val cp     = CodeParser(path, SourceData("public enum Dummy {}"))
    val result = cp.parseClass()
    assert(result.issues.isEmpty)
    val root = ApexNode(cp, result.value).get
    assert(root.nature == ENUM_NATURE)
    assert(root.location == PathLocation(path, Location(1, 0, 1, 20)))
    assert(root.name == Name("Dummy"))
    assert(root.idLocation == Location(1, 12, 1, 17))
    assert(root.children.isEmpty)
    assert(root.modifiers == ArraySeq(PUBLIC_MODIFIER))
    assert(root.parseIssues.isEmpty)
    assert(root.signature == "public enum Dummy")
    assert(root.description == "public")
  }

  test("Class with constructor summary") {
    val path = Path("Dummy.cls")
    val cp = CodeParser(
      path,
      SourceData("public class Dummy { private Dummy(final String bar, Integer foo) {} }")
    )
    val result = cp.parseClass()
    assert(result.issues.isEmpty)
    val root = ApexNode(cp, result.value).get
    assert(root.nature == CLASS_NATURE)
    assert(root.children.size == 1)

    val ctor = root.children.head
    assert(ctor.nature == CONSTRUCTOR_NATURE)
    assert(ctor.location == PathLocation(path, Location(1, 21, 1, 68)))
    assert(ctor.name == Name("Dummy"))
    assert(ctor.idLocation == Location(1, 29, 1, 34))
    assert(ctor.children.isEmpty)
    assert(ctor.modifiers == ArraySeq(PRIVATE_MODIFIER))
    assert(ctor.parseIssues.isEmpty)
    assert(ctor.signature == "private Dummy(final String bar, Integer foo)")
    assert(ctor.description == "(final String bar, Integer foo) private")
  }

  test("Class with method summary") {
    val path = Path("Dummy.cls")
    val cp = CodeParser(
      path,
      SourceData("public class Dummy { private Static void Foo(final String bar) {} }")
    )
    val result = cp.parseClass()
    assert(result.issues.isEmpty)
    val root = ApexNode(cp, result.value).get
    assert(root.nature == CLASS_NATURE)
    assert(root.children.size == 1)

    val method = root.children.head
    assert(method.nature == METHOD_NATURE)
    assert(method.location == PathLocation(path, Location(1, 21, 1, 65)))
    assert(method.name == Name("Foo"))
    assert(method.idLocation == Location(1, 41, 1, 44))
    assert(method.children.isEmpty)
    assert(method.modifiers == ArraySeq(PRIVATE_MODIFIER, STATIC_MODIFIER))
    assert(method.parseIssues.isEmpty)
    assert(method.signature == "private static void Foo(final String bar)")
    assert(method.description == "void (final String bar) private static")
  }

  test("Class with field summary") {
    val path   = Path("Dummy.cls")
    val cp     = CodeParser(path, SourceData("public class Dummy { private Static Integer Foo; }"))
    val result = cp.parseClass()
    assert(result.issues.isEmpty)
    val root = ApexNode(cp, result.value).get
    assert(root.nature == CLASS_NATURE)
    assert(root.children.size == 1)

    val field = root.children.head
    assert(field.nature == FIELD_NATURE)
    assert(field.location == PathLocation(path, Location(1, 21, 1, 48)))
    assert(field.name == Name("Foo"))
    assert(field.idLocation == Location(1, 44, 1, 47))
    assert(field.children.isEmpty)
    assert(field.modifiers == ArraySeq(PRIVATE_MODIFIER, STATIC_MODIFIER))
    assert(field.parseIssues.isEmpty)
    assert(field.signature == "private static Integer Foo")
    assert(field.description == "Integer private static")
  }

  test("Class with two fields declarators summary") {
    val path   = Path("Dummy.cls")
    val cp     = CodeParser(path, SourceData("public class Dummy { private Static Integer Foo, bar; }"))
    val result = cp.parseClass()
    assert(result.issues.isEmpty)
    val root = ApexNode(cp, result.value).get
    assert(root.nature == CLASS_NATURE)
    assert(root.children.size == 2)

    val fooField = root.children.head
    assert(fooField.location == PathLocation(path, Location(1, 44, 1, 47)))
    assert(fooField.name == Name("Foo"))
    assert(fooField.idLocation == Location(1, 44, 1, 47))

    val barField = root.children(1)
    assert(barField.location == PathLocation(path, Location(1, 49, 1, 52)))
    assert(barField.name == Name("bar"))
    assert(barField.idLocation == Location(1, 49, 1, 52))
  }

  test("Class with property summary") {
    val path = Path("Dummy.cls")
    val cp =
      CodeParser(path, SourceData("public class Dummy { private Static Integer Foo{get; set;} }"))
    val result = cp.parseClass()
    assert(result.issues.isEmpty)
    val root = ApexNode(cp, result.value).get
    assert(root.nature == CLASS_NATURE)
    assert(root.children.size == 1)

    val field = root.children.head
    assert(field.nature == PROPERTY_NATURE)
    assert(field.location == PathLocation(path, Location(1, 21, 1, 58)))
    assert(field.idLocation == Location(1, 44, 1, 47))
    assert(field.name == Name("Foo"))

    assert(field.children.isEmpty)
    assert(field.modifiers == ArraySeq(PRIVATE_MODIFIER, STATIC_MODIFIER))
    assert(field.parseIssues.isEmpty)
    assert(field.signature == "private static Integer Foo")
    assert(field.description == "Integer private static")
  }

  test("Interface with method summary") {
    val path   = Path("Dummy.cls")
    val cp     = CodeParser(path, SourceData("public interface Dummy { void Foo(final String bar); }"))
    val result = cp.parseClass()
    assert(result.issues.isEmpty)
    val root = ApexNode(cp, result.value).get
    assert(root.nature == INTERFACE_NATURE)
    assert(root.children.size == 1)

    val method = root.children.head
    assert(method.nature == METHOD_NATURE)
    assert(method.location == PathLocation(path, Location(1, 25, 1, 52)))
    assert(method.name == Name("Foo"))
    assert(method.idLocation == Location(1, 30, 1, 33))
    assert(method.children.isEmpty)
    assert(method.modifiers == ArraySeq(VIRTUAL_MODIFIER, PUBLIC_MODIFIER))
    assert(method.parseIssues.isEmpty)
    assert(method.signature == "virtual public void Foo(final String bar)")
    assert(method.description == "void (final String bar) virtual public")
  }

  test("Enum with constant summary") {
    val path   = Path("Dummy.cls")
    val cp     = CodeParser(path, SourceData("public enum Dummy { BaR }"))
    val result = cp.parseClass()
    assert(result.issues.isEmpty)
    val root = ApexNode(cp, result.value).get
    assert(root.nature == ENUM_NATURE)
    assert(root.children.size == 1)

    val constant = root.children.head
    assert(constant.nature == ENUM_CONSTANT_NATURE)
    assert(constant.location == PathLocation(path, Location(1, 20, 1, 23)))
    assert(constant.name == Name("BaR"))
    assert(constant.idLocation == Location(1, 20, 1, 23))

    assert(constant.children.isEmpty)
    assert(constant.modifiers.isEmpty)
    assert(constant.parseIssues.isEmpty)
    assert(constant.signature == "BaR")
    assert(constant.description == "BaR")
  }

  test("Nested class summary") {
    val path   = Path("Dummy.cls")
    val cp     = CodeParser(path, SourceData("public class Dummy { private class Inner { } }"))
    val result = cp.parseClass()
    assert(result.issues.isEmpty)
    val root = ApexNode(cp, result.value).get
    assert(root.nature == CLASS_NATURE)
    assert(root.children.size == 1)

    val inner = root.children.head
    assert(inner.nature == CLASS_NATURE)
  }

  test("Nested interface summary") {
    val path   = Path("Dummy.cls")
    val cp     = CodeParser(path, SourceData("public class Dummy { private interface Inner { } }"))
    val result = cp.parseClass()
    assert(result.issues.isEmpty)
    val root = ApexNode(cp, result.value).get
    assert(root.nature == CLASS_NATURE)
    assert(root.children.size == 1)

    val inner = root.children.head
    assert(inner.nature == INTERFACE_NATURE)
  }

  test("Nested enum summary") {
    val path   = Path("Dummy.cls")
    val cp     = CodeParser(path, SourceData("public class Dummy { private enum Inner { } }"))
    val result = cp.parseClass()
    assert(result.issues.isEmpty)
    val root = ApexNode(cp, result.value).get
    assert(root.nature == CLASS_NATURE)
    assert(root.children.size == 1)

    val inner = root.children.head
    assert(inner.nature == ENUM_NATURE)
  }

  test("Global field in public class") {
    val path   = Path("Dummy.cls")
    val cp     = CodeParser(path, SourceData("public class Dummy {global String a;}"))
    val result = cp.parseClass()
    assert(result.issues.isEmpty)
    val root   = ApexNode(cp, result.value).get
    val issues = root.collectIssues()

    assert(issues.length == 1)
    assert(issues.head.diagnostic.location.displayPosition == "line 1 at 34-35")
    assert(
      issues.head.diagnostic.message == "Enclosing class must be declared global to use global or webservice modifiers"
    )
  }

  test("Global inner interface in public class") {
    val path   = Path("Dummy.cls")
    val cp     = CodeParser(path, SourceData("public class Dummy {global interface Inside {}}"))
    val result = cp.parseClass()
    assert(result.issues.isEmpty)
    val root   = ApexNode(cp, result.value).get
    val issues = root.collectIssues()

    assert(issues.length == 1)
    assert(issues.head.diagnostic.location.displayPosition == "line 1 at 37-43")
    assert(
      issues.head.diagnostic.message == "Enclosing class must be declared global to use global or webservice modifiers"
    )
  }

  test("Wrong constructor name") {
    val path   = Path("Dummy.cls")
    val cp     = CodeParser(path, SourceData("public class Dummy {public Foo() {} }"))
    val result = cp.parseClass()
    assert(result.issues.isEmpty)
    val root   = ApexNode(cp, result.value).get
    val issues = root.collectIssues()

    assert(issues.length == 1)
    assert(issues.head.diagnostic.location.displayPosition == "line 1 at 27-30")
    assert(
      issues.head.diagnostic.message == "Constructors should have same name as the class, maybe method return type is missing?"
    )
  }

  test("Duplicate constructor") {
    val path = Path("Dummy.cls")
    val cp = CodeParser(
      path,
      SourceData("public class Dummy {public Dummy(String a) {} public Dummy(String a) {}}")
    )
    val result = cp.parseClass()
    assert(result.issues.isEmpty)
    val root   = ApexNode(cp, result.value).get
    val issues = root.collectIssues()

    assert(issues.length == 1)
    assert(issues.head.diagnostic.location.displayPosition == "line 1 at 53-58")
    assert(
      issues.head.diagnostic.message == "Constructor is a duplicate of an earlier constructor at line 1 at 27-32"
    )
  }

  test("Duplicate method") {
    val path = Path("Dummy.cls")
    val cp = CodeParser(
      path,
      SourceData("public class Dummy {public void Foo(String a) {} public void Foo(String a) {}}")
    )
    val result = cp.parseClass()
    assert(result.issues.isEmpty)
    val root   = ApexNode(cp, result.value).get
    val issues = root.collectIssues()

    assert(issues.length == 1)
    assert(issues.head.diagnostic.location.displayPosition == "line 1 at 61-64")
    assert(
      issues.head.diagnostic.message == "Method is a duplicate of an earlier method at line 1 at 32-35"
    )
  }

}
