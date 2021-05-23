/*
 Copyright (c) 2017 Kevin Jones, All rights reserved.
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

import com.nawforce.apexlink.cst.{CST, Literal}
import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.types.core.{DependencyHolder, TypeDeclaration}
import com.nawforce.pkgforce.names.{Name, Names, TypeName}
import com.nawforce.pkgforce.path.PathFactory
import com.nawforce.runtime.parsers.{CodeParser, Source, SourceData}
import org.scalatest.funsuite.AnyFunSuite

class LiteralTypeTest extends AnyFunSuite {
  def typeLiteral(data: String): DependencyHolder = {
    val source = Source(PathFactory("Dummy.cls"), SourceData(""), 0, 0, None)
    CST.sourceContext.withValue(Some(source)) {
      Literal.construct(CodeParser(PathFactory(""), SourceData(data)).parseLiteral()).getType
    }
  }

  def compareLiteral(p: String, r: TypeName): Unit = {
    val t = typeLiteral(p)
    assert(t != null)

    if (t.asInstanceOf[TypeDeclaration].typeName != r) {
      System.out.println("Type mismatch:")
      System.out.println("Expected: " + r)
      System.out.println("Got: " + t)
      assert(false)
    }
  }

  def literal(value: String, r: TypeName = null): Unit =
    compareLiteral(value, r)

  test("Primary literal") {
    literal("0", TypeNames.Integer)
    literal("1", TypeNames.Integer)
    literal("0l", TypeNames.Long)
    literal("1l", TypeNames.Long)
    literal("0L", TypeNames.Long)
    literal("1L", TypeNames.Long)
    literal("''", TypeNames.String)
    literal("'a'", TypeNames.String)
    literal("'az'", TypeNames.String)
    literal("'\t'", TypeNames.String)
    literal("true", TypeNames.Boolean)
    literal("False", TypeNames.Boolean)
    literal("null", TypeName(Name("Null$"), Nil, Some(TypeName(Names.Internal))))
    literal("0.0", TypeNames.Decimal)
    literal(".0", TypeNames.Decimal)
    literal("0.123", TypeNames.Decimal)
    literal("0.123456789012345678901234567890123456789012345678", TypeNames.Decimal)
    literal("0.1234567890123456789012345678901234567890123456789", TypeNames.Double)
  }
}
