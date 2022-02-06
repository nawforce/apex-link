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

package com.nawforce.apexlink.types

import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.types.platform.PlatformTypes
import com.nawforce.pkgforce.names.{Name, Names, TypeName}
import org.scalatest.funsuite.AnyFunSuite

class TypeStoreTest extends AnyFunSuite {

  test("Bad type not found") {
    assert(PlatformTypes.get(TypeName(Name("Hello")), None).isLeft)
  }

  test("Scoped system class found") {
    assert(
      PlatformTypes
        .get(TypeNames.String, None)
        .getOrElse(throw new NoSuchElementException)
        .typeName == TypeNames.String
    )
  }

  test("Unscoped system class found") {
    assert(
      PlatformTypes
        .get(TypeName(Names.String), None)
        .getOrElse(throw new NoSuchElementException)
        .typeName == TypeNames.String
    )
  }

  test("Unscoped schema class found") {
    assert(
      PlatformTypes
        .get(TypeName(Names.SObjectType), None)
        .getOrElse(throw new NoSuchElementException)
        .typeName == TypeNames.SObjectType
    )
  }

  test("Unscoped database class not found") {
    assert(PlatformTypes.get(TypeName(Name("QueryLocator")), None).isLeft)
  }

  test("Inner class found") {
    val typeName =
      TypeName(
        Name("Header"),
        Nil,
        Some(TypeName(Name("InboundEmail"), Nil, Some(TypeName(Name("Messaging")))))
      )
    assert(
      PlatformTypes
        .get(typeName, None)
        .getOrElse(throw new NoSuchElementException)
        .typeName == typeName
    )
  }

  test("Bad inner class not found") {
    val typeName =
      TypeName(
        Name("BadHeader"),
        Nil,
        Some(TypeName(Name("InboundEmail"), Nil, Some(TypeName(Name("Messaging")))))
      )
    assert(PlatformTypes.get(typeName, None).isLeft)
  }
}
