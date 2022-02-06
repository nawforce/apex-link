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
import com.nawforce.apexlink.types.apex.ApexClassDeclaration
import com.nawforce.apexlink.types.core.TypeDeclaration
import com.nawforce.pkgforce.modifiers._
import com.nawforce.pkgforce.names.Name
import org.scalatest.funsuite.AnyFunSuite

import scala.collection.compat.immutable.ArraySeq

class PropertyTest extends AnyFunSuite with TestHelper {

  override def typeDeclaration(clsText: String): ApexClassDeclaration = {
    val td = super.typeDeclaration(clsText)
    withOrg(_ -> td.fields)
    td
  }

  test("Empty class has no properties") {
    assert(typeDeclaration("public class Dummy {}").fields.isEmpty)
    assert(!hasIssues)
  }

  test("Property visible") {
    val property = typeDeclaration("public class Dummy {String foo{get; set;} }").fields.head
    assert(property.name == Name("foo"))
    assert(!hasIssues)
  }

  test("Multiple properties visible") {
    val fields =
      typeDeclaration("public class Dummy {String foo{get; set;} Integer bar{get; set;}}").fields
    assert(fields.map(_.name).toSet == Set(Name("foo"), Name("bar")))
    assert(!hasIssues)
  }

  test("Duplicate property reports error on duplicate") {
    val fields =
      typeDeclaration("public class Dummy {String foo{get; set;} String foo{get; set;}}").fields
    assert(fields.length == 1)
    assert(fields.head.name == Name("foo"))
    assert(
      dummyIssues ==
        "Error: line 1 at 49-52: Duplicate field/property: 'foo'\n"
    )
  }

  test("Property without blocks") {
    val property =
      typeDeclaration("public class Dummy {String foo{} }").fields.head
    assert(property.name == Name("foo"))
    assert(
      dummyIssues ==
        "Error: line 1 at 20-32: Properties must have either a single 'get' and/or a single 'set' block\n"
    )
  }

  test("Property with dual set") {
    val property =
      typeDeclaration("public class Dummy {String foo{set; set;} }").fields.head
    assert(property.name == Name("foo"))
    assert(
      dummyIssues ==
        "Error: line 1 at 20-41: Properties must have either a single 'get' and/or a single 'set' block\n"
    )
  }

  test("Property with dual get & a set") {
    val property = typeDeclaration("public class Dummy {String foo{get; set; get;} }").fields.head
    assert(property.name == Name("foo"))
    assert(
      dummyIssues ==
        "Error: line 1 at 20-46: Properties must have either a single 'get' and/or a single 'set' block\n"
    )
  }

  test("More than one duplicate property reports error on duplicates") {
    val fields = typeDeclaration(
      "public class Dummy {String foo{get; set;} Integer foo{get; set;} String foo{get; set;}}"
    ).fields
    assert(fields.length == 1)
    assert(fields.head.name == Name("foo"))
    assert(
      dummyIssues ==
        "Error: line 1 at 50-53: Duplicate field/property: 'foo'\nError: line 1 at 72-75: Duplicate field/property: 'foo'\n"
    )
  }

  test("Default property access private") {
    val property = typeDeclaration("public class Dummy {String foo{get; set;}}").fields.head
    assert(property.modifiers == ArraySeq(PRIVATE_MODIFIER))
    assert(property.readAccess == PRIVATE_MODIFIER)
    assert(property.writeAccess == property.readAccess)
    assert(!hasIssues)
  }

  test("Private property access") {
    val property = typeDeclaration("public class Dummy {private String foo{get; set;}}").fields.head
    assert(property.modifiers == ArraySeq(PRIVATE_MODIFIER))
    assert(property.readAccess == PRIVATE_MODIFIER)
    assert(property.writeAccess == property.readAccess)
    assert(!hasIssues)
  }

  test("Protected property access") {
    val property =
      typeDeclaration("public class Dummy {protected String foo{get; set;}}").fields.head
    assert(property.modifiers == ArraySeq(PROTECTED_MODIFIER))
    assert(property.readAccess == PROTECTED_MODIFIER)
    assert(property.writeAccess == property.readAccess)
    assert(!hasIssues)
  }

  test("Public property access") {
    val property = typeDeclaration("public class Dummy {public String foo{get; set;}}").fields.head
    assert(property.modifiers == ArraySeq(PUBLIC_MODIFIER))
    assert(property.readAccess == PUBLIC_MODIFIER)
    assert(property.writeAccess == property.readAccess)
    assert(!hasIssues)
  }

  test("Global property access") {
    val property = typeDeclaration("public class Dummy {global String foo{get; set;}}").fields.head
    assert(property.modifiers == ArraySeq(GLOBAL_MODIFIER))
    assert(property.readAccess == GLOBAL_MODIFIER)
    assert(property.writeAccess == property.readAccess)
    assert(
      dummyIssues ==
        "Error: line 1 at 34-37: Enclosing class must be declared global to use global or webservice modifiers\n"
    )
  }

  test("Global property access in global class") {
    val property = typeDeclaration("global class Dummy {global String foo{get; set;}}").fields.head
    assert(property.modifiers == ArraySeq(GLOBAL_MODIFIER))
    assert(property.readAccess == GLOBAL_MODIFIER)
    assert(property.writeAccess == property.readAccess)
    assert(!hasIssues)
  }

  test("Global property access with get modifier in global class") {
    val property =
      typeDeclaration("global class Dummy {global String foo{global get; public set;}}").fields.head
    assert(property.modifiers == ArraySeq(GLOBAL_MODIFIER))
    assert(property.readAccess == GLOBAL_MODIFIER)
    assert(property.writeAccess == PUBLIC_MODIFIER)
    assert(!hasIssues)
  }

  test("Webservice property access") {
    val property =
      typeDeclaration("public class Dummy {webservice String foo{get; set;}}").fields.head
    assert(property.modifiers == ArraySeq(GLOBAL_MODIFIER, WEBSERVICE_MODIFIER))
    assert(property.readAccess == GLOBAL_MODIFIER)
    assert(property.writeAccess == property.readAccess)
    assert(
      dummyIssues ==
        "Error: line 1 at 38-41: Enclosing class must be declared global to use global or webservice modifiers\n"
    )
  }

  test("Webservice property access with get/set modifiers") {
    val property = typeDeclaration(
      "global class Dummy {webservice String foo{global get; public set;}}"
    ).fields.head
    assert(property.modifiers == ArraySeq(GLOBAL_MODIFIER, WEBSERVICE_MODIFIER))
    assert(property.readAccess == GLOBAL_MODIFIER)
    assert(property.writeAccess == PUBLIC_MODIFIER)
    assert(!hasIssues)
  }

  test("Webservice property access in global class") {
    val property =
      typeDeclaration("global class Dummy {webservice String foo{get; set;}}").fields.head
    assert(property.modifiers == ArraySeq(GLOBAL_MODIFIER, WEBSERVICE_MODIFIER))
    assert(property.readAccess == GLOBAL_MODIFIER)
    assert(property.writeAccess == property.readAccess)
    assert(!hasIssues)
  }

  test("Property setter lower visibility") {
    val property =
      typeDeclaration("public class Dummy {public String foo{get; private set;}}").fields.head
    assert(property.modifiers == ArraySeq(PUBLIC_MODIFIER))
    assert(property.readAccess == PUBLIC_MODIFIER)
    assert(property.writeAccess == PRIVATE_MODIFIER)
    assert(!hasIssues)
  }

  test("Property setter higher visibility") {
    val property =
      typeDeclaration("public class Dummy {private String foo{get; public set;}}").fields.head
    assert(property.modifiers == ArraySeq(PRIVATE_MODIFIER))
    assert(property.readAccess == PRIVATE_MODIFIER)
    assert(property.writeAccess == PUBLIC_MODIFIER)
    assert(
      dummyIssues ==
        "Error: line 1 at 28-56: Setter visibility must be same or less than property\n"
    )
  }

  test("Property getter lower visibility") {
    val property =
      typeDeclaration("public class Dummy {public String foo{private get; set;}}").fields.head
    assert(property.modifiers == ArraySeq(PUBLIC_MODIFIER))
    assert(property.readAccess == PRIVATE_MODIFIER)
    assert(property.writeAccess == PUBLIC_MODIFIER)
    assert(!hasIssues)
  }

  test("Property getter higher visibility") {
    val property =
      typeDeclaration("public class Dummy {private String foo{public get; set;}}").fields.head
    assert(property.modifiers == ArraySeq(PRIVATE_MODIFIER))
    assert(property.readAccess == PUBLIC_MODIFIER)
    assert(property.writeAccess == PRIVATE_MODIFIER)
    assert(
      dummyIssues ==
        "Error: line 1 at 28-56: Getter visibility must be same or less than property\n"
    )
  }

  test("Static property") {
    val property = typeDeclaration("public class Dummy {static String foo{get; set;}}").fields.head
    assert(property.modifiers == ArraySeq(PRIVATE_MODIFIER, STATIC_MODIFIER))
    assert(property.readAccess == PRIVATE_MODIFIER)
    assert(property.writeAccess == property.readAccess)
    assert(!hasIssues)
  }

  test("Final property") {
    val property = typeDeclaration("public class Dummy {final String foo{get; set;}}").fields.head
    assert(property.modifiers == ArraySeq(PRIVATE_MODIFIER, FINAL_MODIFIER))
    assert(property.readAccess == PRIVATE_MODIFIER)
    assert(property.writeAccess == property.readAccess)
    assert(!hasIssues)
  }

  test("Many modifiers property") {
    val property = typeDeclaration(
      "public class Dummy {protected transient final static String foo{get; set;}}"
    ).fields.head
    assert(
      property.modifiers == ArraySeq(
        PROTECTED_MODIFIER,
        TRANSIENT_MODIFIER,
        FINAL_MODIFIER,
        STATIC_MODIFIER
      )
    )
    assert(property.readAccess == PROTECTED_MODIFIER)
    assert(property.writeAccess == property.readAccess)
    assert(!hasIssues)
  }

  test("Duplicate modifiers property") {
    val property =
      typeDeclaration("public class Dummy {protected protected String foo{get; set;}}").fields.head
    assert(property.modifiers == ArraySeq(PROTECTED_MODIFIER))
    assert(property.readAccess == PROTECTED_MODIFIER)
    assert(property.writeAccess == property.readAccess)
    assert(
      dummyIssues ==
        "Error: line 1 at 47-50: Modifier 'protected' is used more than once\n"
    )
  }

  test("Mixed access property") {
    val property =
      typeDeclaration("public class Dummy {global webservice String foo{get; set;}}").fields.head
    assert(property.modifiers == ArraySeq(GLOBAL_MODIFIER, WEBSERVICE_MODIFIER))
    assert(property.readAccess == GLOBAL_MODIFIER)
    assert(property.writeAccess == property.readAccess)
    assert(
      dummyIssues ==
        "Error: line 1 at 45-48: Enclosing class must be declared global to use global or webservice modifiers\n"
    )
  }

  test("AuraEnabled property") {
    val property =
      typeDeclaration("public class Dummy {@AuraEnabled String foo{get; set;}}").fields.head
    assert(property.modifiers == ArraySeq(PRIVATE_MODIFIER, AURA_ENABLED_ANNOTATION))
    assert(property.readAccess == PRIVATE_MODIFIER)
    assert(property.writeAccess == property.readAccess)
    assert(!hasIssues)
  }

  test("Deprecated property") {
    val property =
      typeDeclaration("public class Dummy {@Deprecated String foo{get; set;}}").fields.head
    assert(property.modifiers == ArraySeq(PRIVATE_MODIFIER, DEPRECATED_ANNOTATION))
    assert(property.readAccess == PRIVATE_MODIFIER)
    assert(property.writeAccess == property.readAccess)
    assert(!hasIssues)
  }

  test("InvocableVariable property") {
    val property =
      typeDeclaration("public class Dummy {@InvocableVariable String foo{get; set;}}").fields.head
    assert(property.modifiers == ArraySeq(PRIVATE_MODIFIER, INVOCABLE_VARIABLE_ANNOTATION))
    assert(property.readAccess == PRIVATE_MODIFIER)
    assert(property.writeAccess == property.readAccess)
    assert(!hasIssues)
  }

  test("TestVisible property") {
    val property =
      typeDeclaration("public class Dummy {@TestVisible String foo{get; set;}}").fields.head
    assert(property.modifiers == ArraySeq(PRIVATE_MODIFIER, TEST_VISIBLE_ANNOTATION))
    assert(property.readAccess == PRIVATE_MODIFIER)
    assert(property.writeAccess == property.readAccess)
    assert(!hasIssues)
  }

  test("SuppressWarnings property") {
    val property =
      typeDeclaration(
        "public class Dummy {@SuppressWarnings('PMD') String foo{get; set;}}"
      ).fields.head
    assert(property.modifiers == ArraySeq(PRIVATE_MODIFIER, SUPPRESS_WARNINGS_ANNOTATION_PMD))
    assert(property.readAccess == PRIVATE_MODIFIER)
    assert(property.writeAccess == property.readAccess)
    assert(!hasIssues)
  }

  test("Bad annotation property") {
    val property =
      typeDeclaration("public class Dummy {@TestSetup String foo{get; set;}}").fields.head
    assert(property.modifiers == ArraySeq(PRIVATE_MODIFIER))
    assert(property.readAccess == PRIVATE_MODIFIER)
    assert(property.writeAccess == property.readAccess)
    assert(
      dummyIssues ==
        "Error: line 1 at 20-30: Annotation '@TestSetup' is not supported on fields\n"
    )
  }

  test("Duplicate annotation property") {
    val property =
      typeDeclaration(
        "public class Dummy {@TestVisible @TestVisible String foo{get; set;}}"
      ).fields.head
    assert(property.modifiers == ArraySeq(PRIVATE_MODIFIER, TEST_VISIBLE_ANNOTATION))
    assert(property.readAccess == PRIVATE_MODIFIER)
    assert(property.writeAccess == property.readAccess)
    assert(
      dummyIssues ==
        "Error: line 1 at 53-56: Modifier '@TestVisible' is used more than once\n"
    )
  }
}
