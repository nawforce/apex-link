/*
 [The "BSD licence"]
 Copyright (c) 2017 Kevin Jones
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
package com.nawforce.pkgforce.names

import org.scalatest.funsuite.AnyFunSuite

class TypeIdentifierTest extends AnyFunSuite {

  test("parse triggers type names") {
    assert(
      TypeIdentifier.apply("__sfdc_trigger/Foo") === Right(
        TypeIdentifier(None, TypeName(Name("__sfdc_trigger/Foo")))
      )
    )
    assert(
      TypeIdentifier.apply("__sfdc_trigger/ns/Foo") === Right(
        TypeIdentifier(None, TypeName(Name("__sfdc_trigger/ns/Foo")))
      )
    )
  }

  test("parse type name") {
    assert(TypeIdentifier.apply("Foo") == Right(TypeIdentifier(None, TypeName(Name("Foo")))))
    assert(
      TypeIdentifier.apply("Foo.Bar") == Right(
        TypeIdentifier(None, TypeName(Name("Bar"), Nil, Some(TypeName(Name("Foo")))))
      )
    )
  }

  test("parse type name with namespace") {
    assert(
      TypeIdentifier
        .apply("Foo [n]") == Right(TypeIdentifier(Some(Name("n")), TypeName(Name("Foo"))))
    )
    assert(
      TypeIdentifier.apply("Foo.Bar [ns]") == Right(
        TypeIdentifier(Some(Name("ns")), TypeName(Name("Bar"), Nil, Some(TypeName(Name("Foo")))))
      )
    )
    assert(
      TypeIdentifier.apply("Foo (n)") == Right(
        TypeIdentifier(Some(Name("n")), TypeName(Name("Foo"), Nil, Some(TypeName(Name("n")))))
      )
    )
    assert(
      TypeIdentifier.apply("Foo.Bar (ns)") == Right(
        TypeIdentifier(
          Some(Name("ns")),
          TypeName(Name("Bar"), Nil, Some(TypeName(Name("Foo"), Nil, Some(TypeName(Name("ns"))))))
        )
      )
    )
  }

  test("parse bad type name") {
    assert(TypeIdentifier.apply("") == Left("Empty identifier found in type name"))
    assert(TypeIdentifier.apply(" ") == Left("Empty identifier found in type name"))
    assert(TypeIdentifier.apply("  ") == Left("Empty identifier found in type name"))
    assert(
      TypeIdentifier
        .apply("Foo__") == Left("Illegal identifier at 'Foo__': can not start or end with '_'")
    )
  }

  test("parse bad namespace") {
    assert(TypeIdentifier.apply("Foo ") == Left("Expecting brackets around namespace in 'Foo '"))
    assert(TypeIdentifier.apply("Foo (") == Left("Expecting brackets around namespace in 'Foo ('"))
    assert(
      TypeIdentifier.apply("Foo ()") == Left("Expecting brackets around namespace in 'Foo ()'")
    )
    assert(
      TypeIdentifier.apply("Foo ( )") == Left(
        "Illegal namespace ' ': can only use characters A-Z, a-z, 0-9 or _"
      )
    )
    assert(
      TypeIdentifier
        .apply("Foo (ns_)") == Left("Illegal namespace 'ns_': can not start or end with '_'")
    )
    assert(TypeIdentifier.apply("Foo [") == Left("Expecting brackets around namespace in 'Foo ['"))
    assert(
      TypeIdentifier.apply("Foo []") == Left("Expecting brackets around namespace in 'Foo []'")
    )
    assert(
      TypeIdentifier.apply("Foo [ ]") == Left(
        "Illegal namespace ' ': can only use characters A-Z, a-z, 0-9 or _"
      )
    )
    assert(
      TypeIdentifier
        .apply("Foo [ns_]") == Left("Illegal namespace 'ns_': can not start or end with '_'")
    )
  }

  test("toString") {
    assert(TypeIdentifier.apply("Foo").toOption.get.toString == "Foo")
    assert(TypeIdentifier.apply("Foo.Bar").toOption.get.toString == "Foo.Bar")
    assert(TypeIdentifier.apply("Foo.Bar [ns]").toOption.get.toString == "Foo.Bar [ns]")
    assert(TypeIdentifier.apply("ns.Foo.Bar [ns]").toOption.get.toString == "Foo.Bar (ns)")
    assert(TypeIdentifier.apply("Foo.Bar (ns)").toOption.get.toString == "Foo.Bar (ns)")
    assert(TypeIdentifier.apply("ns.Foo.Bar (ns)").toOption.get.toString == "ns.Foo.Bar (ns)")
  }

}
