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
import com.nawforce.common.diagnostics.Location
import com.nawforce.common.names.{DotName, Name, TypeIdentifier, TypeName, TypeNames}
import org.scalatest.funsuite.AnyFunSuite

class SummaryTest extends AnyFunSuite with CSTTestHelper {

  private val dummyTypeName = DotName("Dummy").asTypeName()
  private val dummyTypeId = TypeIdentifier.fromJava(null, dummyTypeName)
  private val objectTypeName = DotName("Internal.Object$").asTypeName()
  private val interfaceTypeName = DotName("Internal.Interface$").asTypeName()
  private val rawIntegerTypeName = DotName("Integer").asTypeName()
  private val rawStringTypeName = DotName("String").asTypeName()

  test("Public outer class") {
    assert(
      classSummary("public class Dummy {}") ==
        TypeSummary(1202117904,
                    Some(Location(1, 13, 1, 18)),
                    "Dummy",
                    dummyTypeName,
                    "class",
                    Array("public"),
                    Some(objectTypeName),
                    Array(),
                    Array(),
                    Array(),
                    Array(),
                    Array(),
                    Array(),
                    Array()))
  }

  test("Global outer class") {
    assert(
      classSummary("global class Dummy {}") ==
        TypeSummary(-548216944,
                    Some(Location(1, 13, 1, 18)),
                    "Dummy",
                    dummyTypeName,
                    "class",
                    Array("global"),
                    Some(objectTypeName),
                    Array(),
                    Array(),
                    Array(),
                    Array(),
                    Array(),
                    Array(),
                    Array()))
  }

  test("Global outer class with isTest") {
    assert(
      classSummary("@isTest global class Dummy {}") ==
        TypeSummary(1213450638,
                    Some(Location(1, 21, 1, 26)),
                    "Dummy",
                    dummyTypeName,
                    "class",
                    Array("@IsTest", "global"),
                    Some(objectTypeName),
                    Array(),
                    Array(),
                    Array(),
                    Array(),
                    Array(),
                    Array(),
                    Array()))
  }

  test("Interface") {
    assert(
      classSummary("public interface Dummy {}") ==
        TypeSummary(-790821413,
                    Some(Location(1, 17, 1, 22)),
                    "Dummy",
                    dummyTypeName,
                    "interface",
                    Array("public"),
                    None,
                    Array(interfaceTypeName),
                    Array(),
                    Array(),
                    Array(),
                    Array(),
                    Array(),
                    Array()))
  }

  test("Enum") {
    val idLocation = Some(Location(1, 12, 1, 17))
    assert(
      classSummary("public enum Dummy {}") ==
        TypeSummary(582020380,
                    idLocation,
                    "Dummy",
                    dummyTypeName,
                    "enum",
                    Array("public"),
                    None,
                    Array(),
                    Array(),
                    Array(),
                    Array(),
                    Array(
                      MethodSummary(idLocation,
                                    "equals",
                                    Array("public"),
                                    TypeNames.Boolean,
                                    Array(ParameterSummary("other", TypeNames.InternalObject)),
                                    hasBlock = true,
                                    Array()),
                      MethodSummary(idLocation,
                                    "hashCode",
                                    Array("public"),
                                    TypeNames.Integer,
                                    Array(),
                                    hasBlock = true,
                                    Array()),
                      MethodSummary(idLocation,
                                    "name",
                                    Array("public"),
                                    TypeNames.String,
                                    Array(),
                                    hasBlock = true,
                                    Array()),
                      MethodSummary(idLocation,
                                    "ordinal",
                                    Array("public"),
                                    TypeNames.Integer,
                                    Array(),
                                    hasBlock = true,
                                    Array()),
                      MethodSummary(idLocation,
                                    "values",
                                    Array("public", "static"),
                                    TypeNames.listOf(dummyTypeName),
                                    Array(),
                                    hasBlock = true,
                                    Array())),
                    Array(),
                    Array()))
  }

  test("Class with unknown super class") {
    assert(
      classSummary("public class Dummy extends Bar {}", hasMessages = true) ==
        TypeSummary(-1732644072,
                    Some(Location(1, 13, 1, 18)),
                    "Dummy",
                    dummyTypeName,
                    "class",
                    Array("public"),
                    Some(TypeName(Name("Bar"))),
                    Array(),
                    Array(),
                    Array(),
                    Array(),
                    Array(),
                    Array(),
                    Array()))
  }

  test("Class with unknown interfaces") {
    assert(
      classSummary("public class Dummy implements A, B {}", hasMessages = true) ==
        TypeSummary(1270273994,
                    Some(Location(1, 13, 1, 18)),
                    "Dummy",
                    dummyTypeName,
                    "class",
                    Array("public"),
                    Some(objectTypeName),
                    Array(TypeName(Name("A")), TypeName(Name("B"))),
                    Array(),
                    Array(),
                    Array(),
                    Array(),
                    Array(),
                    Array()))
  }

  test("Interface with interfaces") {
    assert(
      classSummary("public interface Dummy extends A, B {}", hasMessages = true) ==
        TypeSummary(1951586885,
                    Some(Location(1, 17, 1, 22)),
                    "Dummy",
                    dummyTypeName,
                    "interface",
                    Array("public"),
                    None,
                    Array(TypeName(Name("A")), TypeName(Name("B"))),
                    Array(),
                    Array(),
                    Array(),
                    Array(),
                    Array(),
                    Array()))
  }

  test("Class with fields") {
    assert(
      classSummary("public class Dummy {private String B; public Integer A;}") ==
        TypeSummary(349895857,
                    Some(Location(1, 13, 1, 18)),
                    "Dummy",
                    dummyTypeName,
                    "class",
                    Array("public"),
                    Some(objectTypeName),
                    Array(),
                    Array(),
                    Array(
                      FieldSummary(Some(Location(1, 53, 1, 54)),
                                   "A",
                                   Array("public"),
                                   rawIntegerTypeName,
                                   "public",
                                   "public",
                                   Array()),
                      FieldSummary(Some(Location(1, 35, 1, 36)),
                                   "B",
                                   Array("private"),
                                   rawStringTypeName,
                                   "private",
                                   "private",
                                   Array()),
                    ),
                    Array(),
                    Array(),
                    Array(),
                    Array()))
  }

  test("Class with properties") {
    assert(
      classSummary("public class Dummy {" +
        "private String B {get; set;} public Integer A {private set; get;} }") ==
        TypeSummary(1509489293,
                    Some(Location(1, 13, 1, 18)),
                    "Dummy",
                    dummyTypeName,
                    "class",
                    Array("public"),
                    Some(objectTypeName),
                    Array(),
                    Array(),
                    Array(
                      FieldSummary(Some(Location(1, 64, 1, 65)),
                                   "A",
                                   Array("public"),
                                   rawIntegerTypeName,
                                   "public",
                                   "private",
                                   Array()),
                      FieldSummary(Some(Location(1, 35, 1, 36)),
                                   "B",
                                   Array("private"),
                                   rawStringTypeName,
                                   "private",
                                   "private",
                                   Array()),
                    ),
                    Array(),
                    Array(),
                    Array(),
                    Array()))
  }

  test("Class with constructors") {
    assert(
      classSummary("public class Dummy {public Dummy(String a) {} Dummy() {} }") ==
        TypeSummary(-380622695,
                    Some(Location(1, 13, 1, 18)),
                    "Dummy",
                    dummyTypeName,
                    "class",
                    Array("public"),
                    Some(objectTypeName),
                    Array(),
                    Array(),
                    Array(),
                    Array(
                      ConstructorSummary(Some(Location(1, 46, 1, 51)),
                                         Array("private"),
                                         Array(),
                                         Array()),
                      ConstructorSummary(Some(Location(1, 27, 1, 32)),
                                         Array("public"),
                                         Array(ParameterSummary("a", TypeNames.String)),
                                         Array())),
                    Array(),
                    Array(),
                    Array()))
  }

  test("Class with methods") {
    assert(
      classSummary("public class Dummy {public String foo(String a) {} void bar() {} }") ==
        TypeSummary(1735220165,
                    Some(Location(1, 13, 1, 18)),
                    "Dummy",
                    dummyTypeName,
                    "class",
                    Array("public"),
                    Some(objectTypeName),
                    Array(),
                    Array(),
                    Array(),
                    Array(),
                    Array(
                      MethodSummary(Some(Location(1, 56, 1, 59)),
                                    "bar",
                                    Array(),
                                    TypeNames.Void,
                                    Array(),
                                    hasBlock = true,
                                    Array()),
                      MethodSummary(Some(Location(1, 34, 1, 37)),
                                    "foo",
                                    Array("public"),
                                    TypeNames.String,
                                    Array(ParameterSummary("a", TypeNames.String)),
                                    hasBlock = true,
                                    Array()),
                    ),
                    Array(),
                    Array()))
  }

  test("Interfaces with methods") {
    val expected = TypeSummary(-875397803,
                               Some(Location(1, 17, 1, 22)),
                               "Dummy",
                               dummyTypeName,
                               "interface",
                               Array("public"),
                               None,
                               Array(interfaceTypeName),
                               Array(),
                               Array(),
                               Array(),
                               Array(
                                 MethodSummary(Some(Location(1, 51, 1, 54)),
                                               "bar",
                                               Array(),
                                               TypeNames.Void,
                                               Array(),
                                               hasBlock = false,
                                               Array()),
                                 MethodSummary(Some(Location(1, 31, 1, 34)),
                                               "foo",
                                               Array(),
                                               TypeNames.String,
                                               Array(ParameterSummary("a", TypeNames.String)),
                                               hasBlock = false,
                                               Array())),
                               Array(),
                               Array())

    val actual = classSummary("public interface Dummy {String foo(String a); void bar(); }")
    assert(expected == actual)
  }

  test("Enum with values") {
    val idLocation = Some(Location(1, 12, 1, 17))
    assert(
      classSummary("public enum Dummy {B, A, C }") ==
        TypeSummary(-1528660812,
                    idLocation,
                    "Dummy",
                    dummyTypeName,
                    "enum",
                    Array("public"),
                    None,
                    Array(),
                    Array(),
                    Array(
                      FieldSummary(Some(Location(1, 22, 1, 23)),
                                   "A",
                                   Array("public", "static"),
                                   dummyTypeName,
                                   "public",
                                   "public",
                                   Array(TypeDependentSummary(dummyTypeId, -1528660812))),
                      FieldSummary(Some(Location(1, 19, 1, 20)),
                                   "B",
                                   Array("public", "static"),
                                   dummyTypeName,
                                   "public",
                                   "public",
                                   Array(TypeDependentSummary(dummyTypeId, -1528660812))),
                      FieldSummary(Some(Location(1, 25, 1, 26)),
                                   "C",
                                   Array("public", "static"),
                                   dummyTypeName,
                                   "public",
                                   "public",
                                   Array(TypeDependentSummary(dummyTypeId, -1528660812))),
                    ),
                    Array(),
                    Array(
                      MethodSummary(idLocation,
                                    "equals",
                                    Array("public"),
                                    TypeNames.Boolean,
                                    Array(ParameterSummary("other", TypeNames.InternalObject)),
                                    hasBlock = true,
                                    Array()),
                      MethodSummary(idLocation,
                                    "hashCode",
                                    Array("public"),
                                    TypeNames.Integer,
                                    Array(),
                                    hasBlock = true,
                                    Array()),
                      MethodSummary(idLocation,
                                    "name",
                                    Array("public"),
                                    TypeNames.String,
                                    Array(),
                                    hasBlock = true,
                                    Array()),
                      MethodSummary(idLocation,
                                    "ordinal",
                                    Array("public"),
                                    TypeNames.Integer,
                                    Array(),
                                    hasBlock = true,
                                    Array()),
                      MethodSummary(idLocation,
                                    "values",
                                    Array("public", "static"),
                                    TypeNames.listOf(dummyTypeName),
                                    Array(),
                                    hasBlock = true,
                                    Array())),
                    Array(),
                    Array()))
  }

  test("Empty trigger") {
    val name = "__sfdc_trigger/Dummy"
    assert(
      triggerSummary("trigger Dummy on Account(before insert) {}") ==
        TypeSummary(0,
                    Some(Location(1, 8, 1, 13)),
                    name,
                    DotName(name).asTypeName(),
                    "trigger",
                    Array(),
                    None,
                    Array(),
                    Array(),
                    Array(),
                    Array(),
                    Array(),
                    Array(),
                    Array()))
  }
}
