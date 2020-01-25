/*
 [The "BSD licence"]
 Copyright (c) 2019 Kevin Jones
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
package com.nawforce.common.types

import com.nawforce.common.api.Org
import com.nawforce.common.path.{PathFactory, PathLike}
import com.nawforce.runtime.FileSystemHelper
import org.scalatest.funsuite.AnyFunSuite

class PlatformEventTest extends AnyFunSuite {

  def platformEvent(label: String, fields: Seq[(String, String, Option[String])]): String = {
    val fieldMetadata = fields.map(field => {
      s"""
         |    <fields>
         |        <fullName>${field._1}</fullName>
         |        <type>${field._2}</type>
         |        ${if (field._3.nonEmpty) s"<referenceTo>${field._3.get}</referenceTo>" else ""}
         |        ${if (field._3.nonEmpty) s"<relationshipName>${field._1.replaceAll("__c$", "")}</relationshipName>" else ""}
         |    </fields>
         |""".stripMargin
    })

    s"""<?xml version="1.0" encoding="UTF-8"?>
       |<CustomObject xmlns="http://soap.sforce.com/2006/04/metadata">
       |    <fullName>$label</fullName>
       |    $fieldMetadata
       |</CustomObject>
       |""".stripMargin
  }

  test("Standard field reference") {
    FileSystemHelper.run(Map(
      "Foo__e.object" -> platformEvent("Foo__e", Seq(("Bar__c", "Text", None))),
      "Dummy.cls" -> "public class Dummy { {SObjectField a = Foo__e.ReplayId;} }"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      pkg.deployAll()
      assert(!org.issues.hasMessages)
    }
  }

  test("Custom field reference") {
    FileSystemHelper.run(Map(
      "Foo__e.object" -> platformEvent("Foo__e", Seq(("Bar__c", "Text", None))),
      "Dummy.cls" -> "public class Dummy { {SObjectField a = Foo__e.Bar__c;} }"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      pkg.deployAll()
      assert(!org.issues.hasMessages)
    }
  }

  test("Invalid field reference") {
    FileSystemHelper.run(Map(
      "Foo__e.object" -> platformEvent("Foo__e", Seq(("Bar__c", "Text", None))),
      "Dummy.cls" -> "public class Dummy { {SObjectField a = Foo__e.Baz__c;} }"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      pkg.deployAll()
      assert(org.issues.getMessages(PathFactory("/Dummy.cls")) ==
        "Error: line 1 at 39-52: Unknown field or type 'Baz__c' on 'Schema.Foo__e'\n")
    }
  }
}
