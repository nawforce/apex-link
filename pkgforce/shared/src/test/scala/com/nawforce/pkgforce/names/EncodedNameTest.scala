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

class EncodedNameTest extends AnyFunSuite {

  test("Empty name") {
    val testName = EncodedName("")
    assert(testName == EncodedName(Names.Empty, None, None))
    assert(testName.localName == Names.Empty)
    assert(testName.fullName == Names.Empty)
    assert(testName.asTypeName == TypeName(Names.Empty))
  }

  test("Simple name") {
    val testName = EncodedName("Account")
    assert(testName == EncodedName(Name("Account"), None, None))
    assert(testName.localName == Name("Account"))
    assert(testName.fullName == Name("Account"))
    assert(testName.asTypeName == TypeName(Name("Account")))
  }

  test("Suffix name") {
    val testName = EncodedName("Custom__c")
    assert(testName == EncodedName(Name("Custom"), Some(Name("c")), None))
    assert(testName.localName == Name("Custom__c"))
    assert(testName.fullName == Name("Custom__c"))
    assert(testName.asTypeName == TypeName(Name("Custom__c")))
  }

  test("Empty suffix") {
    val testName = EncodedName("Custom__")
    assert(testName == EncodedName(Name("Custom__"), None, None))
    assert(testName.localName == Name("Custom__"))
    assert(testName.fullName == Name("Custom__"))
    assert(testName.asTypeName == TypeName(Name("Custom__")))
  }

  test("Longer suffix name") {
    val testName = EncodedName("Custom__mdt")
    assert(testName == EncodedName(Name("Custom"), Some(Name("mdt")), None))
    assert(testName.localName == Name("Custom__mdt"))
    assert(testName.fullName == Name("Custom__mdt"))
    assert(testName.asTypeName == TypeName(Name("Custom__mdt")))
  }

  test("Namespace name") {
    val testName = EncodedName("ns__Custom__mdt")
    assert(testName == EncodedName(Name("Custom"), Some(Name("mdt")), Some(Name("ns"))))
    assert(testName.localName == Name("Custom__mdt"))
    assert(testName.fullName == Name("ns__Custom__mdt"))
    assert(testName.asTypeName == TypeName(Name("Custom__mdt"), Nil, Some(TypeName(Name("ns")))))
  }

  test("Empty namespace name") {
    val testName = EncodedName("__Custom__mdt")
    assert(testName == EncodedName(Name("__Custom__mdt"), None, None))
    assert(testName.localName == Name("__Custom__mdt"))
    assert(testName.fullName == Name("__Custom__mdt"))
    assert(testName.asTypeName == TypeName(Name("__Custom__mdt")))
  }

  test("Extra separator") {
    val testName = EncodedName("ns__Custom__Name__mdt")
    assert(testName == EncodedName(Name("Custom__Name"), Some(Name("mdt")), Some(Name("ns"))))
    assert(testName.localName == Name("Custom__Name__mdt"))
    assert(testName.fullName == Name("ns__Custom__Name__mdt"))
    assert(
      testName.asTypeName == TypeName(Name("Custom__Name__mdt"), Nil, Some(TypeName(Name("ns"))))
    )
  }

  test("Subfield without namespace") {
    val testName = EncodedName("Field__Subfield__s")
    assert(testName == EncodedName(Name("Field"), Some(Name("Subfield__s")), None))
    assert(testName.localName == Name("Field__Subfield__s"))
    assert(testName.fullName == Name("Field__Subfield__s"))
    assert(testName.asTypeName == TypeName(Name("Field__Subfield__s"), Nil, None))
  }

  test("Subfield with namespace") {
    val testName = EncodedName("ns__Field__Subfield__s")
    assert(testName == EncodedName(Name("Field"), Some(Name("Subfield__s")), Some(Name("ns"))))
    assert(testName.localName == Name("Field__Subfield__s"))
    assert(testName.fullName == Name("ns__Field__Subfield__s"))
    assert(
      testName.asTypeName == TypeName(Name("Field__Subfield__s"), Nil, Some(TypeName(Name("ns"))))
    )
  }

  test("Subfield with missing field") {
    val testName = EncodedName("Subfield__s")
    assert(testName == EncodedName(Name("Subfield__s"), None, None))
    assert(testName.localName == Name("Subfield__s"))
    assert(testName.fullName == Name("Subfield__s"))
    assert(testName.asTypeName == TypeName(Name("Subfield__s"), Nil, None))
  }

  test("Page name with namespace") {
    val testName = EncodedName("ns__MyPage")
    assert(testName == EncodedName(Name("MyPage"), None, Some(Name("ns"))))
    assert(testName.localName == Name("MyPage"))
    assert(testName.fullName == Name("ns__MyPage"))
    assert(testName.asTypeName == TypeName(Name("MyPage"), Nil, Some(TypeName(Name("ns")))))
  }

  test("Bad extension") {
    val testName = EncodedName("ns__Foo__x")
    assert(testName == EncodedName(Name("ns__Foo__x"), None, None))
    assert(testName.localName == Name("ns__Foo__x"))
    assert(testName.fullName == Name("ns__Foo__x"))
    assert(testName.asTypeName == TypeName(Name("ns__Foo__x"), Nil, None))
  }
}
