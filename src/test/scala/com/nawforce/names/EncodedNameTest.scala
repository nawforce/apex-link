package com.nawforce.names

import org.scalatest.FunSuite

class EncodedNameTest extends FunSuite {

  test("Empty name") {
    val testName = EncodedName("")
    assert(testName == EncodedName(Name.Empty, None, None))
    assert(testName.localName == Name.Empty)
    assert(testName.fullName == Name.Empty)
    assert(testName.asTypeName == TypeName(Name.Empty))
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
    assert(testName == EncodedName(Name("__Custom"), Some(Name("mdt")), None))
    assert(testName.localName == Name("__Custom__mdt"))
    assert(testName.fullName == Name("__Custom__mdt"))
    assert(testName.asTypeName == TypeName(Name("__Custom__mdt")))
  }

  test("Extra separator") {
    val testName = EncodedName("ns__Custom__Name__mdt")
    assert(testName == EncodedName(Name("Custom__Name"), Some(Name("mdt")), Some(Name("ns"))))
    assert(testName.localName == Name("Custom__Name__mdt"))
    assert(testName.fullName == Name("ns__Custom__Name__mdt"))
    assert(testName.asTypeName == TypeName(Name("Custom__Name__mdt"), Nil, Some(TypeName(Name("ns")))))
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
    assert(testName.asTypeName == TypeName(Name("Field__Subfield__s"), Nil, Some(TypeName(Name("ns")))))
  }

  test("Subfield with missing field") {
    val testName = EncodedName("Subfield__s")
    assert(testName == EncodedName(Name("Subfield__s"), None, None))
    assert(testName.localName == Name("Subfield__s"))
    assert(testName.fullName == Name("Subfield__s"))
    assert(testName.asTypeName == TypeName(Name("Subfield__s"), Nil, None))
  }
}
