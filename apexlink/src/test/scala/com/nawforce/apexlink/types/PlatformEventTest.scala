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

import com.nawforce.apexlink.{FileSystemHelper, TestHelper}
import com.nawforce.pkgforce.path.PathLike
import com.nawforce.runtime.platform.Path
import org.scalatest.funsuite.AnyFunSuite

class PlatformEventTest extends AnyFunSuite with TestHelper {

  def platformEvent(label: String, fields: Seq[(String, String, Option[String])]): String = {
    val fieldMetadata = fields.map(field => {
      s"""
         |    <fields>
         |        <fullName>${field._1}</fullName>
         |        <type>${field._2}</type>
         |        ${if (field._3.nonEmpty) s"<referenceTo>${field._3.get}</referenceTo>" else ""}
         |        ${if (field._3.nonEmpty)
        s"<relationshipName>${field._1.replaceAll("__c$", "")}</relationshipName>"
      else ""}
         |    </fields>
         |""".stripMargin
    })

    s"""<?xml version="1.0" encoding="UTF-8"?>
       |<CustomObject xmlns="http://soap.sforce.com/2006/04/metadata">
       |    <fullName>$label</fullName>
       |    <label>Hello</label>
       |    $fieldMetadata
       |</CustomObject>
       |""".stripMargin
  }

  test("Standard field reference") {
    FileSystemHelper.run(
      Map(
        "Foo__e/Foo__e.object" -> platformEvent("Foo__e", Seq(("Bar__c", "Text", None))),
        "Dummy.cls"            -> "public class Dummy { {SObjectField a = Foo__e.ReplayId;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
    }
  }

  test("Custom field reference") {
    FileSystemHelper.run(
      Map(
        "Foo__e/Foo__e.object" -> platformEvent("Foo__e", Seq(("Bar__c", "Text", None))),
        "Dummy.cls"            -> "public class Dummy { {SObjectField a = Foo__e.Bar__c;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
    }
  }

  test("Invalid field reference") {
    FileSystemHelper.run(
      Map(
        "Foo__e/Foo__e.object" -> platformEvent("Foo__e", Seq(("Bar__c", "Text", None))),
        "Dummy.cls"            -> "public class Dummy { {SObjectField a = Foo__e.Baz__c;} }"
      )
    ) { root: PathLike =>
      createOrg(root)
      assert(
        getMessages(root.join("Dummy.cls")) ==
          "Missing: line 1 at 39-52: Unknown field 'Baz__c' on SObject 'Schema.Foo__e'\n"
      )
    }
  }
}
