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
import com.nawforce.pkgforce.modifiers._
import com.nawforce.pkgforce.names.Name
import org.scalatest.funsuite.AnyFunSuite

class FieldTest extends AnyFunSuite with TestHelper {

  test("Empty class has no fields") {
    assert(typeDeclaration("public class Dummy {}").fields.isEmpty)
    assert(!hasIssues)
  }

  test("Field visible") {
    val field = typeDeclaration("public class Dummy {String foo;}").fields.head
    assert(!hasIssues)
    assert(field.name == Name("foo"))
    assert(field.readAccess == PRIVATE_MODIFIER)
    assert(field.writeAccess == PRIVATE_MODIFIER)
  }

  test("Multiple fields visible") {
    val fields = typeDeclaration("public class Dummy {String foo; Integer bar;}").fields
    assert(!hasIssues)
    assert(fields.map(_.name).toSet == Set(Name("foo"), Name("bar")))
  }

  test("Duplicate field reports error on duplicate") {
    val td = typeDeclaration("public class Dummy {String foo; String foo;}")
    val fields = withOrg { _ =>
      td.fields
    }
    assert(fields.length == 1)
    assert(fields.head.name == Name("foo"))
    assert(
      dummyIssues ==
        "Error: line 1 at 39-42: Duplicate field/property: 'foo'\n"
    )
  }

  test("More than one duplicate field reports error on duplicates") {
    val td = typeDeclaration("public class Dummy {String foo; Integer foo; String foo;}")
    val fields = withOrg { _ =>
      td.fields
    }
    assert(fields.length == 1)
    assert(fields.head.name == Name("foo"))
    assert(
      dummyIssues ==
        "Error: line 1 at 40-43: Duplicate field/property: 'foo'\nError: line 1 at 52-55: Duplicate field/property: 'foo'\n"
    )
  }
}
