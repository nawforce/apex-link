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

import com.nawforce.common.FileSystemHelper
import com.nawforce.common.api.{Name, Org, ServerOps}
import com.nawforce.common.org.OrgImpl
import com.nawforce.common.path.PathLike
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

import scala.collection.immutable.ArraySeq.ofRef

class CustomObjectTest extends AnyFunSuite with BeforeAndAfter {

  before {
    ServerOps.setAutoFlush(false)
  }

  after {
    ServerOps.setAutoFlush(true)
  }

  def customObject(label: String,
                   fields: Seq[(String, String, Option[String])],
                   fieldSets: Set[String] = Set(),
                   sharingReason: Set[String] = Set()): String = {
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

    val fieldSetMetadata = fieldSets.map(fieldSet => {
      s"""
         |    <fieldSets>
         |        <fullName>$fieldSet</fullName>
         |    </fieldSets>
         |""".stripMargin
    })

    val sharingReasonMetadata = sharingReason.map(sharingReason => {
      s"""
         |    <sharingReasons>
         |        <fullName>$sharingReason</fullName>
         |    </sharingReasons>
         |""".stripMargin
    })

    s"""<?xml version="1.0" encoding="UTF-8"?>
      |<CustomObject xmlns="http://soap.sforce.com/2006/04/metadata">
      |    <fullName>$label</fullName>
      |    $fieldMetadata
      |    $fieldSetMetadata
      |    $sharingReasonMetadata
      |</CustomObject>
      |""".stripMargin
  }

  def customField(name: String, fieldType: String, relationshipName: Option[String]): String = {
    s"""<?xml version="1.0" encoding="UTF-8"?>
       |<CustomField xmlns="http://soap.sforce.com/2006/04/metadata">
       |    <fullName>$name</fullName>
       |    <type>$fieldType</type>
       |    ${if (relationshipName.nonEmpty) s"<referenceTo>${relationshipName.get}</referenceTo>"
       else ""}
       |    ${if (relationshipName.nonEmpty)
         s"<relationshipName>${name.replaceAll("__c$", "")}</relationshipName>"
       else ""}
       |</CustomField>
       |""".stripMargin
  }

  def customFieldSet(name: String): String = {
    s"""<?xml version="1.0" encoding="UTF-8"?>
       |<FieldSet xmlns="http://soap.sforce.com/2006/04/metadata">
       |    <fullName>$name</fullName>
       |</FieldSet>
       |""".stripMargin
  }

  def customSharingReason(name: String): String = {
    s"""<?xml version="1.0" encoding="UTF-8"?>
       |<SharingReason xmlns="http://soap.sforce.com/2006/04/metadata">
       |    <fullName>$name</fullName>
       |</SharingReason>
       |""".stripMargin
  }

  test("Bad field type") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", "Silly", None))))) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(
          org.issues.getMessages(root.join("Foo__c.object").toString) ==
            "Error: line 5: Unexpected type 'Silly' on custom field\n")
    }
  }

  test("Illegal Map construction") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", "Text", None))),
          "Dummy.cls" -> "public class Dummy { {SObject a = new Foo__c{'a' => 'b'};} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(org.issues.getMessages("/Dummy.cls") ==
          "Error: line 1 at 44-56: Expression pair list construction is only supported for Map types, not 'Schema.Foo__c'\n")
    }
  }

  test("Illegal Set construction") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", "Text", None))),
          "Dummy.cls" -> "public class Dummy { {SObject a = new Foo__c{'a', 'b'};} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(org.issues.getMessages("/Dummy.cls") ==
          "Error: line 1 at 44-54: Expression list construction is only supported for Set or List types, not 'Schema.Foo__c'\n")
    }
  }

  test("No-arg construction") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", "Text", None))),
          "Dummy.cls" -> "public class Dummy { {SObject a = new Foo__c();} }")) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.newMDAPIPackageInternal(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Single arg construction") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", "Text", None))),
          "Dummy.cls" -> "public class Dummy { {Object a = new Foo__c(Bar__c = 'A');} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Bad arg construction") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", "Text", None))),
          "Dummy.cls" -> "public class Dummy { {Object a = new Foo__c(Baz__c = 'A');} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(
          org.issues.getMessages("/Dummy.cls") ==
            "Missing: line 1 at 44-50: Unknown field 'Baz__c' on SObject 'Schema.Foo__c'\n")
    }
  }

  test("Multi arg construction") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo",
                                        Seq(("Bar__c", "Text", None), ("Baz__c", "Text", None))),
        "Dummy.cls" -> "public class Dummy { {Object a = new Foo__c(Baz__c = 'A', Bar__c = 'B');} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Duplicate arg construction") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", "Text", None))),
          "Dummy.cls" -> "public class Dummy { {Object a = new Foo__c(Bar__c = 'A', Bar__c = 'A');} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(org.issues.getMessages("/Dummy.cls") ==
          "Error: line 1 at 58-64: Duplicate assignment to field 'Bar__c' on SObject type 'Schema.Foo__c'\n")
    }
  }

  test("None name=value construction") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", "Text", None))),
          "Dummy.cls" -> "public class Dummy { {Object a = new Foo__c('Silly');} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(org.issues.getMessages("/Dummy.cls") ==
          "Error: line 1 at 44-51: SObject type 'Schema.Foo__c' construction needs '<field name> = <value>' arguments\n")
    }
  }

  test("Id & Name construction") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", "Text", None))),
          "Dummy.cls" -> "public class Dummy { {Object a = new Foo__c(Id='', Name='');} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Lookup construction Id") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", "Lookup", Some("Account")))),
          "Dummy.cls" -> "public class Dummy { {Object a = new Foo__c(Id='', Name='');} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Lookup construction relationship") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", "Lookup", Some("Account")))),
          "Dummy.cls" -> "public class Dummy { {Object a = new Foo__c(Bar__r = null);} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("MasterDetail construction Id") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", "MasterDetail", Some("Account")))),
          "Dummy.cls" -> "public class Dummy { {Object a = new Foo__c(Bar__c = '');} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("MasterDetail construction relationship") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", "MasterDetail", Some("Account")))),
          "Dummy.cls" -> "public class Dummy { {Object a = new Foo__c(Bar__r = null);} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Cross package new") {
    FileSystemHelper.run(
      Map("pkg1/Foo__c.object" -> customObject("Foo__c", Seq(("Bar__c", "Text", None))),
          "pkg2/Dummy.cls" -> "public class Dummy { {Object a = new pkg1__Foo__c();} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        val pkg1 = org.newMDAPIPackageInternal(Some(Name("pkg1")), Seq(root.join("pkg1")), Seq())
        org.newMDAPIPackageInternal(Some(Name("pkg2")), Seq(root.join("pkg2")), Seq(pkg1))
        assert(!org.issues.hasMessages)
    }
  }

  test("RecordTypeId field") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo__c", new ofRef(Array(("Bar__c", "Text", None)))),
          "Dummy.cls" -> "public class Dummy { {Foo__c a; a.RecordTypeId = '';} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Standard field reference") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo__c", new ofRef(Array(("Bar__c", "Text", None)))),
          "Dummy.cls" -> "public class Dummy { {SObjectField a = Foo__c.Name;} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Custom field reference") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", new ofRef(Array(("Bar__c", "Text", None)))),
          "Dummy.cls" -> "public class Dummy { {SObjectField a = Foo__c.Bar__c;} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Invalid field reference") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", new ofRef(Array(("Bar__c", "Text", None)))),
          "Dummy.cls" -> "public class Dummy { {SObjectField a = Foo__c.Baz__c;} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(
          org.issues.getMessages("/Dummy.cls") ==
            "Missing: line 1 at 39-52: Unknown field 'Baz__c' on SObject 'Schema.Foo__c'\n")
    }
  }

  test("Cross package field reference") {
    FileSystemHelper.run(
      Map("pkg1/Foo__c.object" -> customObject("Foo__c", Seq(("Bar__c", "Text", None))),
          "pkg2/Dummy.cls" -> "public class Dummy { {pkg1__Foo__c a = null;} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        val pkg1 = org.newMDAPIPackageInternal(Some(Name("pkg1")), Seq(root.join("pkg1")), Seq())
        org.newMDAPIPackageInternal(Some(Name("pkg2")), Seq(root.join("pkg2")), Seq(pkg1))
        assert(!org.issues.hasMessages)
    }
  }

  test("UserRecordAccess available") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo__c", Seq()),
        "Dummy.cls" -> "public class Dummy { {Foo__c a; Boolean x = a.UserRecordAccess.HasDeleteAccess;} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Lookup related list") {
    FileSystemHelper.run(
      Map("Bar__c.object" -> customObject("Bar", Seq()),
          "Foo__c.object" -> customObject("Foo", Seq(("Lookup__c", "Lookup", Some("Bar__c")))),
          "Dummy.cls" -> "public class Dummy { {SObjectField a = Bar__c.Lookup__r;} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Lookup related list (ghosted target)") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo",
                                        Seq(("Lookup__c", "Lookup", Some("ghosted__Bar__c")))),
        "Dummy.cls" -> "public class Dummy { {SObjectField a = ghosted__Bar__c.Lookup__r;} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        val pkg1 = org.newMDAPIPackageInternal(Some(Name("ghosted")), Seq(), Seq())
        org.newMDAPIPackageInternal(None, Seq(root), Seq(pkg1))
        assert(!org.issues.hasMessages)
    }
  }

  test("Object describable") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", "Text", None))),
          "Dummy.cls" -> "public class Dummy { {DescribeSObjectResult a = SObjectType.Foo__c;} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Unknown Object describe error") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy { {DescribeSObjectResult a = SObjectType.Foo__c;} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(
          org.issues.getMessages("/Dummy.cls") ==
            "Missing: line 1 at 48-66: Unknown field or type 'Foo__c' on 'Schema.SObjectType'\n")
    }
  }

  test("Field describable") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", "Text", None))),
          "Dummy.cls" -> "public class Dummy { {DescribeSObjectResult a = SObjectType.Foo__c.Fields.Bar__c;} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Field describable via Object") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", "Text", None))),
          "Dummy.cls" -> "public class Dummy { {DescribeFieldResult a = Foo__c.SObjectType.Fields.Bar__c;} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Field describable via Object (without Fields)") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", "Text", None))),
          "Dummy.cls" -> "public class Dummy { {DescribeFieldResult a = Foo__c.SObjectType.Bar__c;} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Unknown Field describe error") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", "Text", None))),
          "Dummy.cls" -> "public class Dummy { {DescribeSObjectResult a = SObjectType.Foo__c.Fields.Baz__c;} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(org.issues.getMessages("/Dummy.cls") ==
          "Missing: line 1 at 48-80: Unknown field or type 'Baz__c' on 'Schema.SObjectType.Foo__c.Fields'\n")
    }
  }

  test("FieldSet describable") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(), Set("TestFS")),
          "Dummy.cls" -> "public class Dummy { {DescribeSObjectResult a = SObjectType.Foo__c.FieldSets.TestFS;} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Unknown FieldSet describe error") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(), Set("TestFS")),
          "Dummy.cls" -> "public class Dummy { {DescribeSObjectResult a = SObjectType.Foo__c.FieldSets.OtherFS;} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(org.issues.getMessages("/Dummy.cls") ==
          "Missing: line 1 at 48-84: Unknown field or type 'OtherFS' on 'Schema.SObjectType.Foo__c.FieldSets'\n")
    }
  }

  test("Sfdx field reference") {
    FileSystemHelper.run(
      Map("Foo__c/Foo__c.object-meta.xml" -> customObject("Foo", Seq()),
          "Foo__c/fields/Bar__c.field-meta.xml" -> customField("Bar__c", "Text", None),
          "Dummy.cls" -> "public class Dummy { {SObjectField a = Foo__c.Bar__c;} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Sfdx FieldSet describable") {
    FileSystemHelper.run(
      Map("Foo__c/Foo__c.object-meta.xml" -> customObject("Foo", Seq()),
          "Foo__c/fieldSets/TestFS.fieldSet-meta.xml" -> customFieldSet("TestFS"),
          "Dummy.cls" -> "public class Dummy { {DescribeSObjectResult a = SObjectType.Foo__c.FieldSets.TestFS;} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Schema sObject access describable") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", "Text", None))),
          "Dummy.cls" -> "public class Dummy { {SObjectType a = Schema.Foo__c.SObjectType;} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Share visible") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", "Text", None))),
          "Dummy.cls" -> "public class Dummy { {SObjectType a = Foo__Share.SObjectType;} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("History visible") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", "Text", None))),
          "Dummy.cls" -> "public class Dummy { {SObjectType a = Foo__History.SObjectType;} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Feed visible") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", "Text", None))),
          "Dummy.cls" -> "public class Dummy { {SObjectType a = Foo__Feed.SObjectType;} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("SObjectField reference on custom object") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", "Text", None))),
          "Dummy.cls" -> "public class Dummy {public static SObjectField a = Foo__c.SObjectField.Bar__c;}")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Standard fields") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", "Text", None))),
          "Dummy.cls" ->
            s"""public class Dummy {
           |  public static Foo__c a;
           |  {
           |    Id id = a.Id;
           |    String name = a.Name;
           |    Id recordTypeId = a.RecordTypeId;
           |    User createdBy = a.CreatedBy;
           |    Id createdById = a.CreatedById;
           |    Datetime createdDate = a.CreatedDate;
           |    User lastModifiedBy = a.LastModifiedBy;
           |    Id lastModifiedById = a.LastModifiedById;
           |    Datetime lastModifiedDate = a.LastModifiedDate;
           |    Boolean isDeleted = a.isDeleted;
           |    Datetime systemModstamp = a.SystemModstamp;
           |  }
           |  }""".stripMargin)) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.newMDAPIPackageInternal(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Lookup SObjectField (via Id field)") {
    FileSystemHelper.run(
      Map("Bar__c.object" -> customObject("Bar", Seq(("MyField__c", "Text", None))),
          "Foo__c.object" -> customObject("Foo", Seq(("MyBar__c", "Lookup", Some("Bar__c")))),
          "Dummy.cls" -> "public class Dummy { {SObjectField a = Foo__c.MyBar__c.MyField__c;} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Lookup SObjectField (via relationship field)") {
    FileSystemHelper.run(
      Map("Bar__c.object" -> customObject("Bar", Seq(("MyField__c", "Text", None))),
          "Foo__c.object" -> customObject("Foo", Seq(("MyBar__c", "Lookup", Some("Bar__c")))),
          "Dummy.cls" -> "public class Dummy { {SObjectField a = Foo__c.MyBar__r.MyField__c;} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Lookup SObjectField (via relationship field twice)") {
    FileSystemHelper.run(
      Map("Bar__c.object" -> customObject("Bar", Seq(("MyField__c", "Lookup", Some("Account")))),
          "Foo__c.object" -> customObject("Foo", Seq(("MyBar__c", "Lookup", Some("Bar__c")))),
          "Dummy.cls" -> "public class Dummy { {SObjectField a = Foo__c.MyBar__r.MyField__r.Id;} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Standard RowClause") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", "Text", None))),
          "Dummy.cls" ->
            """
          | public class Dummy {
          |  public static String a = Foo__Share.RowCause.Manual;
          |}
          |""".stripMargin)) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.newMDAPIPackageInternal(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Custom RowClause") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject(
          "Foo",
          Seq(("Bar__c", "Text", None)), Set(), Set("MyReason__c")),
        "Dummy.cls" ->
          """
            | public class Dummy {
            |  public static String a = Foo__Share.RowCause.MyReason__c;
            |}
            |""".stripMargin)) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.newMDAPIPackageInternal(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Sfdx Custom RowClause") {
    FileSystemHelper.run(
      Map("Foo__c/Foo__c.object-meta.xml" -> customObject("Foo", Seq()),
          "Foo__c/sharingReasons/MyReason__c.sharingReason-meta.xml" -> customSharingReason("MyReason__c"),
          "Dummy.cls" ->
            """
            | public class Dummy {
            |  public static String a = Foo__Share.RowCause.MyReason__c;
            |}
            |""".stripMargin)) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.newMDAPIPackageInternal(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

}
