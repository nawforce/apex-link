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
import com.nawforce.common.documents.ApexClassDocument
import com.nawforce.common.names.{DotName, TypeNames}
import com.nawforce.common.org.OrgImpl
import com.nawforce.common.path.PathFactory
import com.nawforce.common.types.apex.{FullDeclaration, TriggerDeclaration}
import com.nawforce.runtime.parsers.SourceData
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class SummaryTest extends AnyFunSuite with BeforeAndAfter {
  private val defaultPath = PathFactory("Dummy.cls")
  private val defaultDoc = ApexClassDocument(PathFactory("Dummy.cls"), Name("Dummy"))
  private var defaultOrg: OrgImpl = _
  private val dummyTypeName = DotName("Dummy").asTypeName()
  private val dummyTypeId = TypeIdentifier.fromJava(null, dummyTypeName)
  private val objectTypeName = DotName("Internal.Object$").asTypeName()
  private val rawIntegerTypeName = DotName("Integer").asTypeName()
  private val rawStringTypeName = DotName("String").asTypeName()

  def classSummary(text: String, hasMessages: Boolean = false): TypeSummary = {
    OrgImpl.current.withValue(defaultOrg) {
      val td = FullDeclaration.create(defaultOrg.unmanaged, defaultDoc, SourceData(text))
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
      val td = TriggerDeclaration.create(defaultOrg.unmanaged, defaultPath, SourceData(text))
      td.foreach(defaultOrg.unmanaged.upsertMetadata(_))
      td.foreach(_.validate())
      if (td.isEmpty || defaultOrg.issues.hasMessages != hasMessages)
        defaultOrg.dumpIssues()
      assert(defaultOrg.issues.hasMessages == hasMessages)
      td.head.summary
    }
  }

  before {
    ServerOps.setAutoFlush(false)
    defaultOrg = new OrgImpl
  }

  after {
    ServerOps.setAutoFlush(true)
  }

  test("Public outer class") {
    assert(classSummary("public class Dummy {}") ==
      TypeSummary(1202117904, Some(new RangeLocation(new Position(1,13), new Position(1,18))),
        "Dummy", dummyTypeName, "class", Array("public"), Some(objectTypeName),
        Array(), Array(), Array(), Array(), Array(), Array(), Array()
      )
    )
  }

  test("Global outer class") {
    assert(classSummary("global class Dummy {}") ==
      TypeSummary(-548216944, Some(new RangeLocation(new Position(1,13), new Position(1,18))),
        "Dummy", dummyTypeName, "class", Array("global"), Some(objectTypeName),
        Array(), Array(), Array(), Array(), Array(), Array(), Array()
      )
    )
  }

  test("Global outer class with isTest") {
    assert(classSummary("@isTest global class Dummy {}") ==
      TypeSummary(1213450638, Some(new RangeLocation(new Position(1,21), new Position(1,26))),
        "Dummy", dummyTypeName, "class", Array("@IsTest", "global"), Some(objectTypeName),
        Array(), Array(), Array(), Array(), Array(), Array(), Array()
      )
    )
  }

  test("Interface") {
    assert(classSummary("public interface Dummy {}") ==
      TypeSummary(-790821413, Some(new RangeLocation(new Position(1,17), new Position(1,22))),
        "Dummy", dummyTypeName, "interface", Array("public"), None,
        Array(), Array(), Array(), Array(), Array(), Array(), Array()
      )
    )
  }

  test("Enum") {
    val idLocation = Some(RangeLocation(Position(1,12),Position(1,17)))
    assert(classSummary("public enum Dummy {}") ==
      TypeSummary(582020380, idLocation,
        "Dummy", dummyTypeName, "enum", Array("public"),
        None, Array(), Array(), Array(), Array(),
        Array(
          MethodSummary(idLocation, "equals", Array("public"), TypeNames.Boolean, Array(ParameterSummary("other", TypeNames.InternalObject)),Array()),
          MethodSummary(idLocation, "hashCode", Array("public"), TypeNames.Integer, Array(), Array()),
          MethodSummary(idLocation, "name", Array("public"), TypeNames.String, Array(), Array()),
          MethodSummary(idLocation, "ordinal", Array("public"), TypeNames.Integer, Array(), Array()),
          MethodSummary(idLocation, "values", Array("public", "static"), TypeNames.listOf(dummyTypeName), Array(), Array())),
        Array(), Array()
      )
    )
  }

  test("Class with unknown super class") {
    assert(classSummary("public class Dummy extends Bar {}", hasMessages = true) ==
      TypeSummary(-1732644072, Some(new RangeLocation(new Position(1,13), new Position(1,18))),
        "Dummy", dummyTypeName, "class", Array("public"), Some(TypeName(Name("Bar"))),
          Array(), Array(), Array(), Array(), Array(), Array(), Array()
      )
    )
  }

  test("Class with unknown interfaces") {
    assert(classSummary("public class Dummy implements A, B {}", hasMessages = true) ==
      TypeSummary(1270273994, Some(new RangeLocation(new Position(1,13), new Position(1,18))),
        "Dummy", dummyTypeName, "class", Array("public"), Some(objectTypeName),
        Array(TypeName(Name("A")), TypeName(Name("B"))),
        Array(), Array(), Array(), Array(), Array(), Array()
      )
    )
  }

  test("Interface with interfaces") {
    assert(classSummary("public interface Dummy extends A, B {}", hasMessages = true) ==
      TypeSummary(1951586885, Some(new RangeLocation(new Position(1,17), new Position(1,22))),
        "Dummy", dummyTypeName, "interface", Array("public"), None,
        Array(TypeName(Name("A")), TypeName(Name("B"))),
        Array(), Array(), Array(), Array(), Array(), Array()
      )
    )
  }

  test("Class with fields") {
    assert(classSummary("public class Dummy {private String B; public Integer A;}") ==
      TypeSummary(349895857, Some(new RangeLocation(new Position(1,13), new Position(1,18))),
        "Dummy", dummyTypeName, "class", Array("public"), Some(objectTypeName), Array(), Array(),
        Array(
          FieldSummary(Some(new RangeLocation(new Position(1,53), new Position(1,54))),
            "A", Array("public"), rawIntegerTypeName, "public", "public", Array()),
          FieldSummary(Some(new RangeLocation(new Position(1,35), new Position(1, 36))),
            "B", Array("private"), rawStringTypeName, "private", "private", Array()),
        ),
        Array(), Array(), Array(), Array()
      )
    )
  }

  test("Class with properties") {
    assert(classSummary("public class Dummy {" +
      "private String B {get; set;} public Integer A {private set; get;} }") ==
      TypeSummary(1509489293, Some(new RangeLocation(new Position(1,13), new Position(1,18))),
        "Dummy", dummyTypeName, "class", Array("public"),
        Some(objectTypeName), Array(), Array(),
        Array(
          FieldSummary(Some(new RangeLocation(new Position(1,64), new Position(1,65))),
            "A", Array("public"), rawIntegerTypeName, "public", "private", Array()),
          FieldSummary(Some(new RangeLocation(new Position(1,35), new Position(1,36))),
            "B", Array("private"), rawStringTypeName, "private", "private", Array()),
        ),
        Array(), Array(), Array(), Array()
      )
    )
  }

  test("Class with constructors") {
    assert(classSummary("public class Dummy {public Dummy(String a) {} Dummy() {} }") ==
      TypeSummary(-380622695, Some(new RangeLocation(new Position(1,13), new Position(1,18))),
        "Dummy", dummyTypeName, "class", Array("public"), Some(objectTypeName), Array(), Array(), Array(),
        Array(
          ConstructorSummary(Some(new RangeLocation(new Position(1,46), new Position(1,51))),
            Array("private"), Array(), Array()),
          ConstructorSummary(Some(new RangeLocation(new Position(1,27), new Position(1,32))),
            Array("public"), Array(ParameterSummary("a", TypeNames.String)), Array())
        ),
        Array(), Array(), Array()
      )
    )
  }

  test("Class with methods") {
    assert(classSummary("public class Dummy {public String foo(String a) {} void bar() {} }") ==
      TypeSummary(1735220165, Some(new RangeLocation(new Position(1,13), new Position(1,18))),
        "Dummy", dummyTypeName, "class", Array("public"), Some(objectTypeName), Array(), Array(), Array(), Array(),
        Array(
          MethodSummary(Some(new RangeLocation(new Position(1,56), new Position(1,59))),
            "bar", Array(), TypeNames.Void, Array(), Array()),
          MethodSummary(Some(new RangeLocation(new Position(1,34), new Position(1,37))),
            "foo", Array("public"), TypeNames.String, Array(ParameterSummary("a", TypeNames.String)), Array()),
        ),
        Array(), Array()
      )
    )
  }

  test("Interfaces with methods") {
    assert(classSummary("public interface Dummy {public String foo(String a); void bar(); }", hasMessages = true) ==
      TypeSummary(-403162503, Some(new RangeLocation(new Position(1,17), new Position(1,22))),
        "Dummy", dummyTypeName, "interface", Array("public"),None, Array(), Array(), Array(), Array(),
        Array(
          MethodSummary(Some(new RangeLocation(new Position(1,58), new Position(1,61))),
            "bar", Array(), TypeNames.Void, Array(), Array()),
          MethodSummary(Some(new RangeLocation(new Position(1,38), new Position(1,41))),
            "foo", Array("public"), TypeNames.String, Array(ParameterSummary("a", TypeNames.String)), Array())
        ),
        Array(), Array()
      )
    )
  }

  test("Enum with values") {
    val idLocation = Some(RangeLocation(Position(1,12),Position(1,17)))
    assert(classSummary("public enum Dummy {B, A, C }") ==
      TypeSummary(-1528660812, idLocation,
        "Dummy", dummyTypeName, "enum", Array("public"), None, Array(), Array(),
        Array(
          FieldSummary(Some(new RangeLocation(new Position(1,22), new Position(1,23))),
            "A", Array("public", "static"), dummyTypeName, "public", "public", Array(TypeDependentSummary(dummyTypeId,-1528660812))),
          FieldSummary(Some(new RangeLocation(new Position(1,19), new Position(1,20))),
            "B", Array("public", "static"), dummyTypeName, "public", "public", Array(TypeDependentSummary(dummyTypeId,-1528660812))),
          FieldSummary(Some(new RangeLocation(new Position(1,25), new Position(1,26))),
            "C", Array("public", "static"), dummyTypeName, "public", "public", Array(TypeDependentSummary(dummyTypeId,-1528660812))),
        ),
        Array(),
        Array(
          MethodSummary(idLocation, "equals", Array("public"), TypeNames.Boolean, Array(ParameterSummary("other", TypeNames.InternalObject)), Array()),
          MethodSummary(idLocation, "hashCode", Array("public"), TypeNames.Integer, Array(), Array()),
          MethodSummary(idLocation, "name", Array("public"), TypeNames.String, Array(), Array()),
          MethodSummary(idLocation, "ordinal", Array("public"), TypeNames.Integer, Array(), Array()),
          MethodSummary(idLocation, "values", Array("public", "static"),TypeNames.listOf(dummyTypeName),Array(), Array())),
        Array(), Array()
      )
    )
  }

  test("Empty trigger") {
    val name = "__sfdc_trigger/Foo"
    assert(triggerSummary("trigger Foo on Account(before insert) {}") ==
      TypeSummary(0, Some(RangeLocation(Position(1,8),Position(1,11))), name, DotName(name).asTypeName(), "trigger",
        Array(), None, Array(), Array(), Array(), Array(), Array(), Array(), Array()
      )
    )
  }
}
