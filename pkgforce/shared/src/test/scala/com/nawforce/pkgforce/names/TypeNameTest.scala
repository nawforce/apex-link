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

class TypeNameTest extends AnyFunSuite {

  test("parse basic type names") {
    assert(TypeName.apply("Foo") == Right(TypeName(Name("Foo"))))
    assert(
      TypeName.apply("Foo.Bar") == Right(TypeName(Name("Bar"), Nil, Some(TypeName(Name("Foo")))))
    )
    assert(
      TypeName.apply("Foo.Bar.Baz") == Right(
        TypeName(Name("Baz"), Nil, Some(TypeName(Name("Bar"), Nil, Some(TypeName(Name("Foo"))))))
      )
    )
  }

  test("parse basic generic type names") {
    assert(
      TypeName.apply("Foo<Bar>") == Right(TypeName(Name("Foo"), Seq(TypeName(Name("Bar"))), None))
    )
    assert(
      TypeName.apply("Baz.Foo<Bar>") == Right(
        TypeName(Name("Foo"), Seq(TypeName(Name("Bar"))), Some(TypeName(Name("Baz"))))
      )
    )
    assert(
      TypeName.apply("Foo<Bar>.Baz") == Right(
        TypeName(Name("Baz"), Nil, Some(TypeName(Name("Foo"), Seq(TypeName(Name("Bar"))), None)))
      )
    )
    assert(
      TypeName.apply("Foo<Bar.Baz>") == Right(
        TypeName(Name("Foo"), Seq(TypeName(Name("Baz"), Nil, Some(TypeName(Name("Bar"))))), None)
      )
    )
    assert(
      TypeName.apply("Foo<Bar,Baz>") == Right(
        TypeName(Name("Foo"), Seq(TypeName(Name("Bar")), TypeName(Name("Baz"))), None)
      )
    )
  }

  test("parse nested generic type names") {
    assert(
      TypeName.apply("Foo<Bar<Baz>>") == Right(
        TypeName(Name("Foo"), Seq(TypeName(Name("Bar"), Seq(TypeName(Name("Baz"))), None)), None)
      )
    )

    assert(
      TypeName.apply("Foo<Bar<Baz,Quz>>") == Right(
        TypeName(
          Name("Foo"),
          Seq(TypeName(Name("Bar"), Seq(TypeName(Name("Baz")), TypeName(Name("Quz"))), None)),
          None
        )
      )
    )

    assert(
      TypeName.apply("Foo<Bar<Baz>,Quz>") == Right(
        TypeName(
          Name("Foo"),
          Seq(TypeName(Name("Bar"), Seq(TypeName(Name("Baz"))), None), TypeName(Name("Quz"))),
          None
        )
      )
    )
  }

  test("parse trigger type names") {
    assert(
      TypeName.apply("__sfdc_trigger/foo/baz") == Right(TypeName(Name("__sfdc_trigger/foo/baz")))
    )
    assert(TypeName.apply("__sfdc_trigger/baz") == Right(TypeName(Name("__sfdc_trigger/baz"))))
  }

  test("parse bad type names") {
    assert(TypeName.apply("") == Left("Empty identifier found in type name"))
    assert(
      TypeName
        .apply(" ") == Left("Illegal identifier at ' ': can only use characters A-Z, a-z, 0-9 or _")
    )
    assert(
      TypeName
        .apply("__Bar") == Left("Illegal identifier at '__Bar': can not start or end with '_'")
    )
    assert(
      TypeName.apply("1Bar") == Left("Illegal identifier at '1Bar': can not start with a digit")
    )
    assert(
      TypeName.apply("Foo ") == Left(
        "Illegal identifier at 'Foo ': can only use characters A-Z, a-z, 0-9 or _"
      )
    )
    assert(
      TypeName
        .apply("Foo.__Bar") == Left("Illegal identifier at '__Bar': can not start or end with '_'")
    )
    assert(
      TypeName
        .apply("__Bar.Foo") == Left("Illegal identifier at '__Bar': can not start or end with '_'")
    )
    assert(TypeName.apply("Foo<Bar") == Left("Unmatched '<' found in 'Foo<Bar'"))
    assert(TypeName.apply("Foo<") == Left("Unmatched '<' found in 'Foo<'"))
    assert(TypeName.apply("Foo<<") == Left("Unmatched '<' found in 'Foo<<'"))
    assert(TypeName.apply("Foo<Bar, Baz") == Left("Unmatched '<' found in 'Foo<Bar, Baz'"))
    assert(TypeName.apply("Foo<>") == Left("Unmatched '<' found in 'Foo<>'"))
    assert(
      TypeName.apply("__sfdc_trigger/baz/") == Left(
        "Illegal identifier at '__sfdc_trigger/baz/': can only be in the format '__sfdc_trigger/namespace/name' or '__sfdc_trigger/name'"
      )
    )
  }
}
