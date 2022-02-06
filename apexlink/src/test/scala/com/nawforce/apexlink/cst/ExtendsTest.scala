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
import org.scalatest.funsuite.AnyFunSuite

class ExtendsTest extends AnyFunSuite with TestHelper {

  test("Duplicate inner type names") {
    assert(
      typeDeclarations(
        Map("Dummy.cls" -> "global class Dummy {class Inner {} interface Inner{}}")
      ).nonEmpty
    )
    assert(
      dummyIssues ==
        "Error: line 1 at 35-52: Duplicate type name 'Inner'\n"
    )
  }

  test("Duplicate outer & inner type names") {
    assert(typeDeclarations(Map("Dummy.cls" -> "global class Dummy {class Dummy{}}")).nonEmpty)
    assert(
      dummyIssues ==
        "Error: line 1 at 20-33: Duplicate type name 'Dummy'\n"
    )
  }

  test("Missing superclass") {
    assert(typeDeclarations(Map("Dummy.cls" -> "global class Dummy extends Foo {}")).nonEmpty)
    assert(
      dummyIssues ==
        "Missing: line 1 at 13-18: No type declaration found for 'Foo'\n"
    )
  }

  test("Non-virtual superclass") {
    assert(
      typeDeclarations(
        Map(
          "SuperClass.cls" -> "global class SuperClass {}",
          "Dummy.cls"      -> "global class Dummy extends SuperClass {}"
        )
      ).size == 2
    )
    assert(
      dummyIssues ==
        "Error: line 1 at 13-18: Parent class 'SuperClass' must be declared virtual or abstract\n"
    )
  }

  test("Interface superclass") {
    assert(
      typeDeclarations(
        Map(
          "SuperInterface.cls" -> "global interface SuperInterface {}",
          "Dummy.cls"          -> "global class Dummy extends SuperInterface {}"
        )
      ).size == 2
    )
    assert(
      dummyIssues ==
        "Error: line 1 at 13-18: Parent type 'SuperInterface' must be a class\n"
    )
  }

  test("Enum superclass") {
    assert(
      typeDeclarations(
        Map(
          "SuperEnum.cls" -> "global enum SuperEnum {}",
          "Dummy.cls"     -> "global class Dummy extends SuperEnum {}"
        )
      ).size == 2
    )
    assert(
      dummyIssues ==
        "Error: line 1 at 13-18: Parent type 'SuperEnum' must be a class\n"
    )
  }

  test("External superclass") {
    assert(
      typeDeclarations(
        Map(
          "SuperClass.cls" -> "global virtual class SuperClass {}",
          "Dummy.cls"      -> "global class Dummy extends SuperClass {}"
        )
      ).size == 2
    )
    assert(!hasIssues)
  }

  test("Inner superclass") {
    assert(
      typeDeclarations(
        Map("Dummy.cls" -> "global class Dummy extends Inner {virtual class Inner{}}")
      ).nonEmpty
    )
    assert(!hasIssues)
  }

  test("Outer superclass") {
    assert(
      typeDeclarations(
        Map("Dummy.cls" -> "global virtual class Dummy {class Inner extends Dummy {}}")
      ).nonEmpty
    )
    assert(!hasIssues)
  }
}
