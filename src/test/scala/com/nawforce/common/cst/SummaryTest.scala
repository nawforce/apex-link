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

import com.nawforce.common.api._
import com.nawforce.common.documents.{PositionImpl, TextRange}
import com.nawforce.common.names.{DotName, Name, TypeName}
import com.nawforce.common.org.OrgImpl
import com.nawforce.common.path.PathFactory
import com.nawforce.common.types.apex.FullDeclaration
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class SummaryTest extends AnyFunSuite with BeforeAndAfter {
  private val defaultPath = PathFactory("Dummy.cls")
  private var defaultOrg: OrgImpl = new OrgImpl
  private val dummyTypeName = DotName("Dummy").asTypeName()
  private val objectTypeName = DotName("Internal.Object$").asTypeName()
  private val rawIntegerTypeName = DotName("Integer").asTypeName()
  private val rawStringTypeName = DotName("String").asTypeName()

  def typeDeclarationSummary(clsText: String, hasMessages: Boolean = false): TypeSummary = {
    OrgImpl.current.withValue(defaultOrg) {
      val td = FullDeclaration.create(defaultOrg.unmanaged, defaultPath, clsText)
      td.foreach(defaultOrg.unmanaged.upsertMetadata(_))
      td.foreach(_.validate())
      if (td.isEmpty || defaultOrg.issues.hasMessages != hasMessages)
        defaultOrg.dumpIssues()
      assert(defaultOrg.issues.hasMessages == hasMessages)
      td.head.summary
    }
  }

  before {
    defaultOrg = new OrgImpl
  }

  test("Public outer class") {
    assert(typeDeclarationSummary("public class Dummy {}") ==
      TypeSummary(727760095, Some(new TextRange(new PositionImpl(1,13), new PositionImpl(1,18))),
        "Dummy", dummyTypeName, "class", List("public"), Some(objectTypeName),
        Nil, Nil, Nil, Nil, Nil, Nil, Set()
      )
    )
  }

  test("Global outer class") {
    assert(typeDeclarationSummary("global class Dummy {}") ==
      TypeSummary(-2072849596, Some(new TextRange(new PositionImpl(1,13), new PositionImpl(1,18))),
        "Dummy", dummyTypeName, "class", List("global"), Some(objectTypeName),
        Nil, Nil, Nil, Nil, Nil, Nil, Set()
      )
    )
  }

  test("Global outer class with isTest") {
    assert(typeDeclarationSummary("@isTest global class Dummy {}") ==
      TypeSummary(-8400113, Some(new TextRange(new PositionImpl(1,21), new PositionImpl(1,26))),
        "Dummy", dummyTypeName, "class", List("@IsTest", "global"), Some(objectTypeName),
        Nil, Nil, Nil, Nil, Nil, Nil, Set()
      )
    )
  }

  test("Interface") {
    assert(typeDeclarationSummary("public interface Dummy {}") ==
      TypeSummary(-1556463390, Some(new TextRange(new PositionImpl(1,17), new PositionImpl(1,22))),
        "Dummy", dummyTypeName, "interface", List("public"), None, Nil, Nil, Nil, Nil, Nil, Nil, Set())
    )
  }

  test("Enum") {
    assert(typeDeclarationSummary("public enum Dummy {}") ==
      TypeSummary(-1129410070, Some(new TextRange(new PositionImpl(1,12), new PositionImpl(1,17))),
        "Dummy", dummyTypeName, "enum", List("public"), None, Nil, Nil, Nil, Nil, Nil, Nil, Set())
    )
  }

  test("Class with unknown super class") {
    assert(typeDeclarationSummary("public class Dummy extends Bar {}", hasMessages = true) ==
      TypeSummary(-264364603, Some(new TextRange(new PositionImpl(1,13), new PositionImpl(1,18))),
        "Dummy", dummyTypeName, "class", List("public"), Some(TypeName(Name("Bar"))),
          Nil, Nil, Nil, Nil, Nil, Nil, Set())
    )
  }

  test("Class with unknown interfaces") {
    assert(typeDeclarationSummary("public class Dummy implements A, B {}", hasMessages = true) ==
      TypeSummary(-1699589909, Some(new TextRange(new PositionImpl(1,13), new PositionImpl(1,18))),
        "Dummy", dummyTypeName, "class", List("public"), Some(objectTypeName),
        List(TypeName(Name("A")), TypeName(Name("B"))), Nil, Nil, Nil, Nil, Nil, Set())
    )
  }

  test("Interface with interfaces") {
    assert(typeDeclarationSummary("public interface Dummy extends A, B {}", hasMessages = true) ==
      TypeSummary(-1967820565, Some(new TextRange(new PositionImpl(1,17), new PositionImpl(1,22))),
        "Dummy", dummyTypeName, "interface", List("public"), None,
        List(TypeName(Name("A")), TypeName(Name("B"))), Nil, Nil, Nil, Nil, Nil, Set())
    )
  }

  test("Class with fields") {
    assert(typeDeclarationSummary("public class Dummy {private String B; public Integer A;}") ==
      TypeSummary(574678240, Some(new TextRange(new PositionImpl(1,13), new PositionImpl(1,18))),
        "Dummy", dummyTypeName, "class", List("public"), Some(objectTypeName), Nil, Nil,
        List(
          FieldSummary(Some(new TextRange(new PositionImpl(1,53), new PositionImpl(1,54))),
            "A", List("public"), rawIntegerTypeName, "public", "public", Set()),
          FieldSummary(Some(new TextRange(new PositionImpl(1,35), new PositionImpl(1, 36))),
            "B", List("private"), rawStringTypeName, "private", "private", Set()),
        ),
        Nil, Nil, Nil, Set())
    )
  }

  test("Class with properties") {
    assert(typeDeclarationSummary("public class Dummy {" +
      "private String B {get; set;} public Integer A {private set; get;} }") ==
      TypeSummary(-1261132507, Some(new TextRange(new PositionImpl(1,13), new PositionImpl(1,18))),
        "Dummy", dummyTypeName, "class", List("public"),
        Some(objectTypeName), Nil, Nil,
        List(
          FieldSummary(Some(new TextRange(new PositionImpl(1,64), new PositionImpl(1,65))),
            "A", List("public"), rawIntegerTypeName, "public", "private", Set()),
          FieldSummary(Some(new TextRange(new PositionImpl(1,35), new PositionImpl(1,36))),
            "B", List("private"), rawStringTypeName, "private", "private", Set()),
        ),
        Nil, Nil, Nil, Set())
    )
  }

  test("Class with constructors") {
    assert(typeDeclarationSummary("public class Dummy {public Dummy(String a) {} Dummy() {} }") ==
      TypeSummary(1268538768, Some(new TextRange(new PositionImpl(1,13), new PositionImpl(1,18))),
        "Dummy", dummyTypeName, "class", List("public"), Some(objectTypeName), Nil, Nil, Nil,
        List(
          ConstructorSummary(Some(new TextRange(new PositionImpl(1,46), new PositionImpl(1,51))),
            List("private"), Nil, Set()),
          ConstructorSummary(Some(new TextRange(new PositionImpl(1,27), new PositionImpl(1,32))),
            List("public"), List(ParameterSummary("a", TypeName.String)), Set())
        ),
        Nil, Nil, Set())
    )
  }


  test("Class with methods") {
    assert(typeDeclarationSummary("public class Dummy {public String foo(String a) {} void bar() {} }") ==
      TypeSummary(-162282491, Some(new TextRange(new PositionImpl(1,13), new PositionImpl(1,18))),
        "Dummy", dummyTypeName, "class", List("public"), Some(objectTypeName), Nil, Nil, Nil, Nil,
        List(
          MethodSummary(Some(new TextRange(new PositionImpl(1,56), new PositionImpl(1,59))),
            "bar", List(), TypeName.Void, Nil, Set()),
          MethodSummary(Some(new TextRange(new PositionImpl(1,34), new PositionImpl(1,37))),
            "foo", List("public"), TypeName.String, List(ParameterSummary("a", TypeName.String)), Set()),
        ),
        Nil, Set())
    )
  }


  test("Interfaces with methods") {
    assert(typeDeclarationSummary("public interface Dummy {public String foo(String a); void bar(); }", hasMessages = true) ==
      TypeSummary(-688836916, Some(new TextRange(new PositionImpl(1,17), new PositionImpl(1,22))),
        "Dummy", dummyTypeName, "interface", List("public"),None, Nil, Nil, Nil, Nil,
        List(
          MethodSummary(Some(new TextRange(new PositionImpl(1,58), new PositionImpl(1,61))),
            "bar", List(), TypeName.Void, Nil, Set()),
          MethodSummary(Some(new TextRange(new PositionImpl(1,38), new PositionImpl(1,41))),
            "foo", List("public"), TypeName.String, List(ParameterSummary("a", TypeName.String)), Set())
        ),
        Nil, Set())
    )
  }

  test("Enum with values") {
    assert(typeDeclarationSummary("public enum Dummy {B, A, C }") ==
      TypeSummary(1277314056, Some(new TextRange(new PositionImpl(1,12), new PositionImpl(1,17))),
        "Dummy", dummyTypeName, "enum", List("public"), None, Nil, Nil,
        List(
          FieldSummary(Some(new TextRange(new PositionImpl(1,22), new PositionImpl(1,23))),
            "A", List("public", "static"), dummyTypeName, "public", "public", Set(TypeDependentSummary(dummyTypeName,1277314056))),
          FieldSummary(Some(new TextRange(new PositionImpl(1,19), new PositionImpl(1,20))),
            "B", List("public", "static"), dummyTypeName, "public", "public", Set(TypeDependentSummary(dummyTypeName,1277314056))),
          FieldSummary(Some(new TextRange(new PositionImpl(1,25), new PositionImpl(1,26))),
            "C", List("public", "static"), dummyTypeName, "public", "public", Set(TypeDependentSummary(dummyTypeName,1277314056))),
        ),
        Nil, Nil, Nil, Set())
    )
  }
}
