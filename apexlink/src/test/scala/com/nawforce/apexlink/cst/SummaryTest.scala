/*
 Copyright (c) 2019 Kevin Jones, All rights reserved.
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
 */

package com.nawforce.apexlink.cst

import com.nawforce.apexlink.TestHelper
import com.nawforce.apexlink.api._
import com.nawforce.apexlink.names.TypeNames
import com.nawforce.pkgforce.modifiers._
import com.nawforce.pkgforce.names.{DotName, Name, TypeIdentifier, TypeName}
import com.nawforce.pkgforce.parsers.{FIELD_NATURE, PROPERTY_NATURE}
import com.nawforce.pkgforce.path.Location
import org.scalatest.funsuite.AnyFunSuite

import scala.collection.immutable.ArraySeq

class SummaryTest extends AnyFunSuite with TestHelper {

  private val dummyTypeName      = DotName("Dummy").asTypeName()
  private val dummyTypeId        = TypeIdentifier.fromJava(null, dummyTypeName)
  private val objectTypeName     = DotName("Internal.Object$").asTypeName()
  private val interfaceTypeName  = DotName("Internal.Interface$").asTypeName()
  private val rawIntegerTypeName = DotName("Integer").asTypeName()
  private val rawStringTypeName  = DotName("String").asTypeName()

  test("Public outer class") {
    assert(
      classSummary("public class Dummy {}") ==
        TypeSummary(
          1193498817,
          Location(1, 0, 1, 21),
          Location(1, 13, 1, 18),
          "Dummy",
          dummyTypeName,
          "class",
          ArraySeq(PUBLIC_MODIFIER),
          inTest = false,
          Some(objectTypeName),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          Array()
        )
    )
  }

  test("Global outer class") {
    assert(
      classSummary("global class Dummy {}") ==
        TypeSummary(
          -497317248,
          Location(1, 0, 1, 21),
          Location(1, 13, 1, 18),
          "Dummy",
          dummyTypeName,
          "class",
          ArraySeq(GLOBAL_MODIFIER),
          inTest = false,
          Some(objectTypeName),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          Array()
        )
    )
  }

  test("Global outer class with isTest") {
    assert(
      classSummary("@isTest global class Dummy {}") ==
        TypeSummary(
          -8782017,
          Location(1, 0, 1, 29),
          Location(1, 21, 1, 26),
          "Dummy",
          dummyTypeName,
          "class",
          ArraySeq(ISTEST_ANNOTATION, GLOBAL_MODIFIER),
          inTest = true,
          Some(objectTypeName),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          Array()
        )
    )
  }

  test("Interface") {
    assert(
      classSummary("public interface Dummy {}") ==
        TypeSummary(
          1991290676,
          Location(1, 0, 1, 25),
          Location(1, 17, 1, 22),
          "Dummy",
          dummyTypeName,
          "interface",
          ArraySeq(PUBLIC_MODIFIER),
          inTest = false,
          None,
          ArraySeq(interfaceTypeName),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          Array()
        )
    )
  }

  test("Enum") {
    val idLocation = Location(1, 12, 1, 17)
    assert(
      classSummary("public enum Dummy {}") ==
        TypeSummary(
          -1196814154,
          Location(1, 0, 1, 20),
          idLocation,
          "Dummy",
          dummyTypeName,
          "enum",
          ArraySeq(PUBLIC_MODIFIER),
          inTest = false,
          None,
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(
            MethodSummary(
              idLocation,
              idLocation,
              "equals",
              ArraySeq(PUBLIC_MODIFIER),
              TypeNames.Boolean,
              ArraySeq(ParameterSummary("other", TypeNames.InternalObject)),
              hasBlock = true,
              Array()
            ),
            MethodSummary(
              idLocation,
              idLocation,
              "hashCode",
              ArraySeq(PUBLIC_MODIFIER),
              TypeNames.Integer,
              ArraySeq(),
              hasBlock = true,
              Array()
            ),
            MethodSummary(
              idLocation,
              idLocation,
              "name",
              ArraySeq(PUBLIC_MODIFIER),
              TypeNames.String,
              ArraySeq(),
              hasBlock = true,
              Array()
            ),
            MethodSummary(
              idLocation,
              idLocation,
              "ordinal",
              ArraySeq(PUBLIC_MODIFIER),
              TypeNames.Integer,
              ArraySeq(),
              hasBlock = true,
              Array()
            ),
            MethodSummary(
              idLocation,
              idLocation,
              "valueOf",
              ArraySeq(PUBLIC_MODIFIER, STATIC_MODIFIER),
              dummyTypeName,
              ArraySeq(ParameterSummary("name", TypeNames.String)),
              hasBlock = true,
              Array()
            ),
            MethodSummary(
              idLocation,
              idLocation,
              "values",
              ArraySeq(PUBLIC_MODIFIER, STATIC_MODIFIER),
              TypeNames.listOf(dummyTypeName),
              ArraySeq(),
              hasBlock = true,
              Array()
            )
          ),
          ArraySeq(),
          Array()
        )
    )
  }

  test("Class with unknown super class") {
    assert(
      classSummary("public class Dummy extends Bar {}", hasMessages = true) ==
        TypeSummary(
          -261265427,
          Location(1, 0, 1, 33),
          Location(1, 13, 1, 18),
          "Dummy",
          dummyTypeName,
          "class",
          ArraySeq(PUBLIC_MODIFIER),
          inTest = false,
          Some(TypeName(Name("Bar"))),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          Array()
        )
    )
  }

  test("Class with unknown interfaces") {
    assert(
      classSummary("public class Dummy implements A, B {}", hasMessages = true) ==
        TypeSummary(
          162295607,
          Location(1, 0, 1, 37),
          Location(1, 13, 1, 18),
          "Dummy",
          dummyTypeName,
          "class",
          ArraySeq(PUBLIC_MODIFIER),
          inTest = false,
          Some(objectTypeName),
          ArraySeq(TypeName(Name("A")), TypeName(Name("B"))),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          Array()
        )
    )
  }

  test("Interface with interfaces") {
    assert(
      classSummary("public interface Dummy extends A, B {}", hasMessages = true) ==
        TypeSummary(
          -1368148342,
          Location(1, 0, 1, 38),
          Location(1, 17, 1, 22),
          "Dummy",
          dummyTypeName,
          "interface",
          ArraySeq(PUBLIC_MODIFIER),
          inTest = false,
          None,
          ArraySeq(TypeName(Name("A")), TypeName(Name("B"))),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          Array()
        )
    )
  }

  test("Class with fields") {
    assert(
      classSummary("public class Dummy {private String B; public Integer A;}") ==
        TypeSummary(
          350433220,
          Location(1, 0, 1, 56),
          Location(1, 13, 1, 18),
          "Dummy",
          dummyTypeName,
          "class",
          ArraySeq(PUBLIC_MODIFIER),
          inTest = false,
          Some(objectTypeName),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(
            FieldSummary(
              Location(1, 45, 1, 55),
              Location(1, 53, 1, 54),
              "A",
              FIELD_NATURE,
              ArraySeq(PUBLIC_MODIFIER),
              rawIntegerTypeName,
              PUBLIC_MODIFIER,
              PUBLIC_MODIFIER,
              Array()
            ),
            FieldSummary(
              Location(1, 28, 1, 37),
              Location(1, 35, 1, 36),
              "B",
              FIELD_NATURE,
              ArraySeq(PRIVATE_MODIFIER),
              rawStringTypeName,
              PRIVATE_MODIFIER,
              PRIVATE_MODIFIER,
              Array()
            )
          ),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          Array()
        )
    )
  }

  test("Class with properties") {
    assert(
      classSummary(
        "public class Dummy {" +
          "private String B {get; set;} public Integer A {private set; get;} }"
      ) ==
        TypeSummary(
          1703738882,
          Location(1, 0, 1, 87),
          Location(1, 13, 1, 18),
          "Dummy",
          dummyTypeName,
          "class",
          ArraySeq(PUBLIC_MODIFIER),
          inTest = false,
          Some(objectTypeName),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(
            FieldSummary(
              Location(1, 56, 1, 85),
              Location(1, 64, 1, 65),
              "A",
              PROPERTY_NATURE,
              ArraySeq(PUBLIC_MODIFIER),
              rawIntegerTypeName,
              PUBLIC_MODIFIER,
              PRIVATE_MODIFIER,
              Array()
            ),
            FieldSummary(
              Location(1, 28, 1, 48),
              Location(1, 35, 1, 36),
              "B",
              PROPERTY_NATURE,
              ArraySeq(PRIVATE_MODIFIER),
              rawStringTypeName,
              PRIVATE_MODIFIER,
              PRIVATE_MODIFIER,
              Array()
            )
          ),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          Array()
        )
    )
  }

  test("Class with constructors") {
    assert(
      classSummary("public class Dummy {public Dummy(String a) {} Dummy() {} }") ==
        TypeSummary(
          95956350,
          Location(1, 0, 1, 58),
          Location(1, 13, 1, 18),
          "Dummy",
          dummyTypeName,
          "class",
          ArraySeq(PUBLIC_MODIFIER),
          inTest = false,
          Some(objectTypeName),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(
            ConstructorSummary(
              Location(1, 46, 1, 56),
              Location(1, 46, 1, 51),
              ArraySeq(PRIVATE_MODIFIER),
              ArraySeq(),
              Array()
            ),
            ConstructorSummary(
              Location(1, 27, 1, 45),
              Location(1, 27, 1, 32),
              ArraySeq(PUBLIC_MODIFIER),
              ArraySeq(ParameterSummary("a", TypeNames.String)),
              Array()
            )
          ),
          ArraySeq(),
          ArraySeq(),
          Array()
        )
    )
  }

  test("Class with methods") {
    assert(
      classSummary("public class Dummy {public String foo(String a) {} void bar() {} }") ==
        TypeSummary(
          -140800034,
          Location(1, 0, 1, 66),
          Location(1, 13, 1, 18),
          "Dummy",
          dummyTypeName,
          "class",
          ArraySeq(PUBLIC_MODIFIER),
          inTest = false,
          Some(objectTypeName),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(
            MethodSummary(
              Location(1, 51, 1, 64),
              Location(1, 56, 1, 59),
              "bar",
              ArraySeq(),
              TypeNames.Void,
              ArraySeq(),
              hasBlock = true,
              Array()
            ),
            MethodSummary(
              Location(1, 27, 1, 50),
              Location(1, 34, 1, 37),
              "foo",
              ArraySeq(PUBLIC_MODIFIER),
              TypeNames.String,
              ArraySeq(ParameterSummary("a", TypeNames.String)),
              hasBlock = true,
              Array()
            )
          ),
          ArraySeq(),
          Array()
        )
    )
  }

  test("Interfaces with methods") {
    val expected = TypeSummary(
      -1437805576,
      Location(1, 0, 1, 59),
      Location(1, 17, 1, 22),
      "Dummy",
      dummyTypeName,
      "interface",
      ArraySeq(PUBLIC_MODIFIER),
      inTest = false,
      None,
      ArraySeq(interfaceTypeName),
      ArraySeq(),
      ArraySeq(),
      ArraySeq(),
      ArraySeq(
        MethodSummary(
          Location(1, 46, 1, 57),
          Location(1, 51, 1, 54),
          "bar",
          ArraySeq(VIRTUAL_MODIFIER, PUBLIC_MODIFIER),
          TypeNames.Void,
          ArraySeq(),
          hasBlock = false,
          Array()
        ),
        MethodSummary(
          Location(1, 24, 1, 45),
          Location(1, 31, 1, 34),
          "foo",
          ArraySeq(VIRTUAL_MODIFIER, PUBLIC_MODIFIER),
          TypeNames.String,
          ArraySeq(ParameterSummary("a", TypeNames.String)),
          hasBlock = false,
          Array()
        )
      ),
      ArraySeq(),
      Array()
    )

    val actual = classSummary("public interface Dummy {String foo(String a); void bar(); }")
    assert(actual == expected)
  }

  test("Enum with values") {
    val idLocation = Location(1, 12, 1, 17)
    assert(
      classSummary("public enum Dummy {B, A, C }") ==
        TypeSummary(
          -1270140630,
          Location(1, 0, 1, 28),
          idLocation,
          "Dummy",
          dummyTypeName,
          "enum",
          ArraySeq(PUBLIC_MODIFIER),
          inTest = false,
          None,
          ArraySeq(),
          ArraySeq(),
          ArraySeq(
            FieldSummary(
              Location(1, 22, 1, 23),
              Location(1, 22, 1, 23),
              "A",
              FIELD_NATURE,
              ArraySeq(PUBLIC_MODIFIER, STATIC_MODIFIER),
              dummyTypeName,
              PUBLIC_MODIFIER,
              PUBLIC_MODIFIER,
              Array(TypeDependentSummary(dummyTypeId, -1270140630))
            ),
            FieldSummary(
              Location(1, 19, 1, 20),
              Location(1, 19, 1, 20),
              "B",
              FIELD_NATURE,
              ArraySeq(PUBLIC_MODIFIER, STATIC_MODIFIER),
              dummyTypeName,
              PUBLIC_MODIFIER,
              PUBLIC_MODIFIER,
              Array(TypeDependentSummary(dummyTypeId, -1270140630))
            ),
            FieldSummary(
              Location(1, 25, 1, 26),
              Location(1, 25, 1, 26),
              "C",
              FIELD_NATURE,
              ArraySeq(PUBLIC_MODIFIER, STATIC_MODIFIER),
              dummyTypeName,
              PUBLIC_MODIFIER,
              PUBLIC_MODIFIER,
              Array(TypeDependentSummary(dummyTypeId, -1270140630))
            )
          ),
          ArraySeq(),
          ArraySeq(
            MethodSummary(
              idLocation,
              idLocation,
              "equals",
              ArraySeq(PUBLIC_MODIFIER),
              TypeNames.Boolean,
              ArraySeq(ParameterSummary("other", TypeNames.InternalObject)),
              hasBlock = true,
              Array()
            ),
            MethodSummary(
              idLocation,
              idLocation,
              "hashCode",
              ArraySeq(PUBLIC_MODIFIER),
              TypeNames.Integer,
              ArraySeq(),
              hasBlock = true,
              Array()
            ),
            MethodSummary(
              idLocation,
              idLocation,
              "name",
              ArraySeq(PUBLIC_MODIFIER),
              TypeNames.String,
              ArraySeq(),
              hasBlock = true,
              Array()
            ),
            MethodSummary(
              idLocation,
              idLocation,
              "ordinal",
              ArraySeq(PUBLIC_MODIFIER),
              TypeNames.Integer,
              ArraySeq(),
              hasBlock = true,
              Array()
            ),
            MethodSummary(
              idLocation,
              idLocation,
              "valueOf",
              ArraySeq(PUBLIC_MODIFIER, STATIC_MODIFIER),
              dummyTypeName,
              ArraySeq(ParameterSummary("name", TypeNames.String)),
              hasBlock = true,
              Array()
            ),
            MethodSummary(
              idLocation,
              idLocation,
              "values",
              ArraySeq(PUBLIC_MODIFIER, STATIC_MODIFIER),
              TypeNames.listOf(dummyTypeName),
              ArraySeq(),
              hasBlock = true,
              Array()
            )
          ),
          ArraySeq(),
          Array()
        )
    )
  }

  test("Empty trigger") {
    val name = "__sfdc_trigger/Dummy"
    assert(
      triggerSummary("trigger Dummy on Account(before insert) {}") ==
        TypeSummary(
          -1234478444,
          Location(1, 0, 1, 47),
          Location(1, 8, 1, 13),
          name,
          DotName(name).asTypeName(),
          "trigger",
          ArraySeq(),
          inTest = false,
          None,
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          ArraySeq(),
          Array()
        )
    )
  }
}
