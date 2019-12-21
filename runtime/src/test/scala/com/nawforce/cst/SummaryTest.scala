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
package com.nawforce.cst

import com.nawforce.api._
import com.nawforce.path.PathFactory
import com.nawforce.types._
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class SummaryTest extends AnyFunSuite with BeforeAndAfter {
  private val defaultPath = PathFactory("Dummy.cls")
  private var defaultOrg: Org = new Org

  def typeDeclarationSummary(clsText: String, hasMessages: Boolean = false): TypeSummary = {
    Org.current.withValue(defaultOrg) {
      val td = ApexTypeDeclaration.create(defaultOrg.unmanaged, defaultPath, clsText)
      td.headOption.foreach(defaultOrg.getUnmanagedPackage.upsertType)
      if (td.isEmpty || defaultOrg.issues.hasMessages != hasMessages)
        defaultOrg.issues.dumpMessages(json = false)
      assert(defaultOrg.issues.hasMessages == hasMessages)
      td.head.summary
    }
  }

  before {
    defaultOrg = new Org
  }

  test("Public outer class") {
    assert(typeDeclarationSummary("public class Dummy {}") ==
      TypeSummary("Dummy", "Dummy", "class", List("public"),
        "Object", Nil,
        Nil, Nil,
        List(
          MethodSummary("clone",List("public"),"Dummy",List()),
          MethodSummary("equals",List("public"),"System.Boolean",List(ParameterSummary("other","Object"))),
          MethodSummary("hashCode",List("public"),"System.Integer",List()),
          MethodSummary("toString",List("public"),"System.String",List())
        ),
        Nil
      )
    )
  }

  test("Global outer class") {
    assert(typeDeclarationSummary("global class Dummy {}") ==
      TypeSummary("Dummy", "Dummy", "class", List("global"),
        "Object", Nil,
        Nil, Nil,
        List(
          MethodSummary("clone",List("public"),"Dummy",List()),
          MethodSummary("equals",List("public"),"System.Boolean",List(ParameterSummary("other","Object"))),
          MethodSummary("hashCode",List("public"),"System.Integer",List()),
          MethodSummary("toString",List("public"),"System.String",List())
        ),
        Nil
      )
    )
  }

  test("Global outer class with isTest") {
    assert(typeDeclarationSummary("@isTest global class Dummy {}") ==
      TypeSummary("Dummy", "Dummy", "class", List("@IsTest", "global"),
        "Object", Nil,
        Nil, Nil,
        List(
          MethodSummary("clone",List("public"),"Dummy",List()),
          MethodSummary("equals",List("public"),"System.Boolean",List(ParameterSummary("other","Object"))),
          MethodSummary("hashCode",List("public"),"System.Integer",List()),
          MethodSummary("toString",List("public"),"System.String",List())
        ),
        Nil
      )
    )
  }

  test("Interface") {
    assert(typeDeclarationSummary("public interface Dummy {}") ==
      TypeSummary("Dummy", "Dummy", "interface", List("public"),
        "", Nil,
        Nil, Nil, Nil,
        Nil
      )
    )
  }

  test("Enum") {
    assert(typeDeclarationSummary("public enum Dummy {}") ==
      TypeSummary("Dummy", "Dummy", "enum", List("public"),
        "", Nil,
        Nil, Nil, Nil,
        Nil)
    )
  }

  test("Class with unknown super class") {
    assert(typeDeclarationSummary("public class Dummy extends Bar {}") ==
      TypeSummary("Dummy", "Dummy", "class", List("public"),
        "Bar", Nil,
        Nil, Nil,
        List(
          MethodSummary("clone",List("public"),"Dummy",List())
        ),
        Nil)
    )
  }

  test("Class with interfaces") {
    assert(typeDeclarationSummary("public class Dummy implements A, B {}") ==
      TypeSummary("Dummy", "Dummy", "class", List("public"),
        "Object", List("A", "B"),
        Nil, Nil,
        List(
          MethodSummary("clone",List("public"),"Dummy",List()),
          MethodSummary("equals",List("public"),"System.Boolean",List(ParameterSummary("other","Object"))),
          MethodSummary("hashCode",List("public"),"System.Integer",List()),
          MethodSummary("toString",List("public"),"System.String",List())
        ),
        Nil
      )
    )
  }

  test("Interface with interfaces") {
    assert(typeDeclarationSummary("public interface Dummy extends A, B {}") ==
      TypeSummary("Dummy", "Dummy", "interface", List("public"),
        "", List("A", "B"),
        Nil, Nil, Nil,
        Nil)
    )
  }

  test("Class with fields") {
    assert(typeDeclarationSummary("public class Dummy {private String B; public Integer A;}") ==
      TypeSummary("Dummy", "Dummy", "class", List("public"),
        "Object", Nil,
        List(
          FieldSummary("A", List("public"), "Integer", "public", "public"),
          FieldSummary("B", List("private"), "String", "private", "private"),
        ),
        Nil,
        List(
          MethodSummary("clone",List("public"),"Dummy",List()),
          MethodSummary("equals",List("public"),"System.Boolean",List(ParameterSummary("other","Object"))),
          MethodSummary("hashCode",List("public"),"System.Integer",List()),
          MethodSummary("toString",List("public"),"System.String",List())
        ),
        Nil
      )
    )
  }

  test("Class with properties") {
    assert(typeDeclarationSummary("public class Dummy {" +
      "private String B {get; set;} public Integer A {private set; get;} }") ==
      TypeSummary("Dummy", "Dummy", "class", List("public"),
        "Object", Nil,
        List(
          FieldSummary("A", List("public"), "Integer", "public", "private"),
          FieldSummary("B", List("private"), "String", "private", "private"),
        ),
        Nil,
        List(
          MethodSummary("clone",List("public"),"Dummy",List()),
          MethodSummary("equals",List("public"),"System.Boolean",List(ParameterSummary("other","Object"))),
          MethodSummary("hashCode",List("public"),"System.Integer",List()),
          MethodSummary("toString",List("public"),"System.String",List())
        ),
        Nil
      )
    )
  }

  test("Class with constructors") {
    assert(typeDeclarationSummary("public class Dummy {public Dummy(String a) {} Dummy() {} }") ==
      TypeSummary("Dummy", "Dummy", "class", List("public"),
        "Object", Nil,
        Nil,
        List(
          ConstructorSummary(List("private"), Nil),
          ConstructorSummary(List("public"), List(ParameterSummary("a", "System.String")))
        ),
        List(
          MethodSummary("clone",List("public"),"Dummy",List()),
          MethodSummary("equals",List("public"),"System.Boolean",List(ParameterSummary("other","Object"))),
          MethodSummary("hashCode",List("public"),"System.Integer",List()),
          MethodSummary("toString",List("public"),"System.String",List())
        ),
        Nil
      )
    )
  }

  test("Class with methods") {
    assert(typeDeclarationSummary("public class Dummy {public String foo(String a) {} void bar() {} }") ==
      TypeSummary("Dummy", "Dummy", "class", List("public"),
        "Object", Nil,
        Nil,
        Nil,
        List(
          MethodSummary("bar", List(), "void", Nil),
          MethodSummary("clone",List("public"),"Dummy",List()),
          MethodSummary("equals",List("public"),"System.Boolean",List(ParameterSummary("other","Object"))),
          MethodSummary("foo", List("public"), "System.String", List(ParameterSummary("a", "System.String"))),
          MethodSummary("hashCode",List("public"),"System.Integer",List()),
          MethodSummary("toString",List("public"),"System.String",List())
        ),
        Nil)
    )
  }

  test("Interfaces with methods") {
    assert(typeDeclarationSummary("public interface Dummy {public String foo(String a); void bar(); }") ==
      TypeSummary("Dummy", "Dummy", "interface", List("public"),
        "", Nil,
        Nil,
        Nil,
        List(
          MethodSummary("bar", List(), "void", Nil),
          MethodSummary("foo", List("public"), "System.String", List(ParameterSummary("a", "System.String")))
        ),
        Nil)
    )
  }

  test("Enum with values") {
    assert(typeDeclarationSummary("public enum Dummy {B, A, C }") ==
      TypeSummary("Dummy", "Dummy", "enum", List("public"),
        "", Nil,
        List(
          FieldSummary("A", List("public", "static"), "Dummy", "public", "public"),
          FieldSummary("B", List("public", "static"), "Dummy", "public", "public"),
          FieldSummary("C", List("public", "static"), "Dummy", "public", "public"),
        ),
        Nil,
        Nil,
        Nil)
    )
  }
}
