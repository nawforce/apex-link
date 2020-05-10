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
import com.nawforce.common.names.{DotName, TypeNames}
import com.nawforce.common.org.OrgImpl
import com.nawforce.common.path.PathFactory
import com.nawforce.common.types.apex.{FullDeclaration, TriggerDeclaration}
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class SummaryTest extends AnyFunSuite with BeforeAndAfter {
  private val defaultPath = PathFactory("Dummy.cls")
  private var defaultOrg: OrgImpl = new OrgImpl
  private val dummyTypeName = DotName("Dummy").asTypeName()
  private val dummyTypeId = TypeIdentifier(null, dummyTypeName)
  private val objectTypeName = DotName("Internal.Object$").asTypeName()
  private val rawIntegerTypeName = DotName("Integer").asTypeName()
  private val rawStringTypeName = DotName("String").asTypeName()

  def classSummary(text: String, hasMessages: Boolean = false): TypeSummary = {
    OrgImpl.current.withValue(defaultOrg) {
      val td = FullDeclaration.create(defaultOrg.unmanaged, defaultPath, text)
      td.foreach(defaultOrg.unmanaged.upsertMetadata(_))
      td.foreach(_.validate())
      if (td.isEmpty || defaultOrg.issues.hasMessages != hasMessages)
        defaultOrg.dumpIssues()
      assert(defaultOrg.issues.hasMessages == hasMessages)
      td.head.summary
    }
  }

  def triggerSummary(text: String, hasMessages: Boolean = false): TypeSummary = {
    OrgImpl.current.withValue(defaultOrg) {
      val td = TriggerDeclaration.create(defaultOrg.unmanaged, defaultPath, text)
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
    assert(classSummary("public class Dummy {}") ==
      TypeSummary(727760095, Some(new RangeLocation(new Position(1,13), new Position(1,18))),
        "Dummy", dummyTypeName, "class", List("public"), Some(objectTypeName),
        Nil, Nil, Nil, Nil, Nil, Nil, Set()
      )
    )
  }

  test("Global outer class") {
    assert(classSummary("global class Dummy {}") ==
      TypeSummary(-2072849596, Some(new RangeLocation(new Position(1,13), new Position(1,18))),
        "Dummy", dummyTypeName, "class", List("global"), Some(objectTypeName),
        Nil, Nil, Nil, Nil, Nil, Nil, Set()
      )
    )
  }

  test("Global outer class with isTest") {
    assert(classSummary("@isTest global class Dummy {}") ==
      TypeSummary(-8400113, Some(new RangeLocation(new Position(1,21), new Position(1,26))),
        "Dummy", dummyTypeName, "class", List("@IsTest", "global"), Some(objectTypeName),
        Nil, Nil, Nil, Nil, Nil, Nil, Set()
      )
    )
  }

  test("Interface") {
    assert(classSummary("public interface Dummy {}") ==
      TypeSummary(-1556463390, Some(new RangeLocation(new Position(1,17), new Position(1,22))),
        "Dummy", dummyTypeName, "interface", List("public"),
        None, Nil, Nil, Nil, Nil, Nil, Nil, Set()
      )
    )
  }

  test("Enum") {
    val idLocation = Some(RangeLocation(Position(1,12),Position(1,17)))
    assert(classSummary("public enum Dummy {}") ==
      TypeSummary(-1129410070, idLocation,
        "Dummy", dummyTypeName, "enum", List("public"),
        None, Nil, Nil, Nil, Nil,
        List(
          MethodSummary(idLocation, "equals", List("public"), TypeNames.Boolean, List(ParameterSummary("other", TypeNames.InternalObject)),Set()),
          MethodSummary(idLocation, "hashCode", List("public"), TypeNames.Integer, List(),Set()),
          MethodSummary(idLocation, "name", List("public"), TypeNames.String, List(),Set()),
          MethodSummary(idLocation, "ordinal", List("public"), TypeNames.Integer, List(),Set()),
          MethodSummary(idLocation, "values", List("public", "static"),TypeNames.listOf(dummyTypeName),List(),Set())),
        Nil, Set()
      )
    )
  }

  test("Class with unknown super class") {
    assert(classSummary("public class Dummy extends Bar {}", hasMessages = true) ==
      TypeSummary(-264364603, Some(new RangeLocation(new Position(1,13), new Position(1,18))),
        "Dummy", dummyTypeName, "class", List("public"), Some(TypeName(Name("Bar"))),
          Nil, Nil, Nil, Nil, Nil, Nil, Set()
      )
    )
  }

  test("Class with unknown interfaces") {
    assert(classSummary("public class Dummy implements A, B {}", hasMessages = true) ==
      TypeSummary(-1699589909, Some(new RangeLocation(new Position(1,13), new Position(1,18))),
        "Dummy", dummyTypeName, "class", List("public"), Some(objectTypeName),
        List(TypeName(Name("A")), TypeName(Name("B"))),
        Nil, Nil, Nil, Nil, Nil, Set()
      )
    )
  }

  test("Interface with interfaces") {
    assert(classSummary("public interface Dummy extends A, B {}", hasMessages = true) ==
      TypeSummary(-1967820565, Some(new RangeLocation(new Position(1,17), new Position(1,22))),
        "Dummy", dummyTypeName, "interface", List("public"), None,
        List(TypeName(Name("A")), TypeName(Name("B"))),
        Nil, Nil, Nil, Nil, Nil, Set()
      )
    )
  }

  test("Class with fields") {
    assert(classSummary("public class Dummy {private String B; public Integer A;}") ==
      TypeSummary(574678240, Some(new RangeLocation(new Position(1,13), new Position(1,18))),
        "Dummy", dummyTypeName, "class", List("public"), Some(objectTypeName), Nil, Nil,
        List(
          FieldSummary(Some(new RangeLocation(new Position(1,53), new Position(1,54))),
            "A", List("public"), rawIntegerTypeName, "public", "public", Set()),
          FieldSummary(Some(new RangeLocation(new Position(1,35), new Position(1, 36))),
            "B", List("private"), rawStringTypeName, "private", "private", Set()),
        ),
        Nil, Nil, Nil, Set()
      )
    )
  }

  test("Class with properties") {
    assert(classSummary("public class Dummy {" +
      "private String B {get; set;} public Integer A {private set; get;} }") ==
      TypeSummary(-1261132507, Some(new RangeLocation(new Position(1,13), new Position(1,18))),
        "Dummy", dummyTypeName, "class", List("public"),
        Some(objectTypeName), Nil, Nil,
        List(
          FieldSummary(Some(new RangeLocation(new Position(1,64), new Position(1,65))),
            "A", List("public"), rawIntegerTypeName, "public", "private", Set()),
          FieldSummary(Some(new RangeLocation(new Position(1,35), new Position(1,36))),
            "B", List("private"), rawStringTypeName, "private", "private", Set()),
        ),
        Nil, Nil, Nil, Set()
      )
    )
  }

  test("Class with constructors") {
    assert(classSummary("public class Dummy {public Dummy(String a) {} Dummy() {} }") ==
      TypeSummary(1268538768, Some(new RangeLocation(new Position(1,13), new Position(1,18))),
        "Dummy", dummyTypeName, "class", List("public"), Some(objectTypeName), Nil, Nil, Nil,
        List(
          ConstructorSummary(Some(new RangeLocation(new Position(1,46), new Position(1,51))),
            List("private"), Nil, Set()),
          ConstructorSummary(Some(new RangeLocation(new Position(1,27), new Position(1,32))),
            List("public"), List(ParameterSummary("a", TypeNames.String)), Set())
        ),
        Nil, Nil, Set()
      )
    )
  }

  test("Class with methods") {
    assert(classSummary("public class Dummy {public String foo(String a) {} void bar() {} }") ==
      TypeSummary(-162282491, Some(new RangeLocation(new Position(1,13), new Position(1,18))),
        "Dummy", dummyTypeName, "class", List("public"), Some(objectTypeName), Nil, Nil, Nil, Nil,
        List(
          MethodSummary(Some(new RangeLocation(new Position(1,56), new Position(1,59))),
            "bar", List(), TypeNames.Void, Nil, Set()),
          MethodSummary(Some(new RangeLocation(new Position(1,34), new Position(1,37))),
            "foo", List("public"), TypeNames.String, List(ParameterSummary("a", TypeNames.String)), Set()),
        ),
        Nil, Set()
      )
    )
  }

  test("Interfaces with methods") {
    assert(classSummary("public interface Dummy {public String foo(String a); void bar(); }", hasMessages = true) ==
      TypeSummary(-688836916, Some(new RangeLocation(new Position(1,17), new Position(1,22))),
        "Dummy", dummyTypeName, "interface", List("public"),None, Nil, Nil, Nil, Nil,
        List(
          MethodSummary(Some(new RangeLocation(new Position(1,58), new Position(1,61))),
            "bar", List(), TypeNames.Void, Nil, Set()),
          MethodSummary(Some(new RangeLocation(new Position(1,38), new Position(1,41))),
            "foo", List("public"), TypeNames.String, List(ParameterSummary("a", TypeNames.String)), Set())
        ),
        Nil, Set()
      )
    )
  }

  test("Enum with values") {
    val idLocation = Some(RangeLocation(Position(1,12),Position(1,17)))
    assert(classSummary("public enum Dummy {B, A, C }") ==
      TypeSummary(1277314056, idLocation,
        "Dummy", dummyTypeName, "enum", List("public"), None, Nil, Nil,
        List(
          FieldSummary(Some(new RangeLocation(new Position(1,22), new Position(1,23))),
            "A", List("public", "static"), dummyTypeName, "public", "public", Set(TypeDependentSummary(dummyTypeId,1277314056))),
          FieldSummary(Some(new RangeLocation(new Position(1,19), new Position(1,20))),
            "B", List("public", "static"), dummyTypeName, "public", "public", Set(TypeDependentSummary(dummyTypeId,1277314056))),
          FieldSummary(Some(new RangeLocation(new Position(1,25), new Position(1,26))),
            "C", List("public", "static"), dummyTypeName, "public", "public", Set(TypeDependentSummary(dummyTypeId,1277314056))),
        ),
        Nil,
        List(
          MethodSummary(idLocation, "equals", List("public"), TypeNames.Boolean, List(ParameterSummary("other", TypeNames.InternalObject)),Set()),
          MethodSummary(idLocation, "hashCode", List("public"), TypeNames.Integer, List(),Set()),
          MethodSummary(idLocation, "name", List("public"), TypeNames.String, List(),Set()),
          MethodSummary(idLocation, "ordinal", List("public"), TypeNames.Integer, List(),Set()),
          MethodSummary(idLocation, "values", List("public", "static"),TypeNames.listOf(dummyTypeName),List(),Set())),
        Nil, Set()
      )
    )
  }

  test("Empty trigger") {
    val name = "__sfdc_trigger/Foo"
    assert(triggerSummary("trigger Foo on Account(before insert) {}") ==
      TypeSummary(0, Some(RangeLocation(Position(1,8),Position(1,11))), name, DotName(name).asTypeName(), "trigger",
        List(), None, Nil, Nil, Nil, Nil, Nil, Nil, Set()
      )
    )
  }
}
