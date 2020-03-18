package com.nawforce.common.types

import com.nawforce.common.api.{Org, ServerOps}
import com.nawforce.common.names.Name
import com.nawforce.common.org.{OrgImpl, PackageImpl}
import com.nawforce.common.path.PathLike
import com.nawforce.runtime.FileSystemHelper
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class StandardObjectTest extends AnyFunSuite with BeforeAndAfter {

  before {
    ServerOps.setParsedDataCaching(false)
  }

  def customObject(label: String, fields: Seq[(String, Option[String], Option[String])]): String = {
    val fieldMetadata = fields.map(field => {
      s"""
         |    <fields>
         |        <fullName>${field._1}</fullName>
         |        ${if (field._2.nonEmpty) s"<type>${field._2.get}</type>" else ""}
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

  def customField(name: String, fieldType: String, relationshipName: Option[String]): String = {
    s"""<?xml version="1.0" encoding="UTF-8"?>
       |<CustomField xmlns="http://soap.sforce.com/2006/04/metadata">
       |    <fullName>$name</fullName>
       |    <type>$fieldType</type>
       |    ${if (relationshipName.nonEmpty) s"<referenceTo>${relationshipName.get}</referenceTo>" else ""}
       |    ${if (relationshipName.nonEmpty) s"<relationshipName>${name.replaceAll("__c$","")}</relationshipName>" else ""}
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

  test("Not a standard object") {
    FileSystemHelper.run(Map(
      "Foo.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(org.issues.getMessages(root.join("Foo.object").toString) ==
        "Error: line 0: No SObject declaration found for 'Schema.Foo'\n")
    }
  }

  test("Not a sObject") {
    FileSystemHelper.run(Map(
      "String.object" -> customObject("String", Seq(("Bar__c", Some("Text"), None))),
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(org.issues.getMessages(root.join("String.object").toString) ==
        "Error: line 0: No SObject declaration found for 'Schema.String'\n")
    }
  }

  test("UserRecordAccess available") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy { {Account a; Boolean x = a.UserRecordAccess.HasDeleteAccess;} }",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Custom field") {
    FileSystemHelper.run(Map(
      "Account.object" -> customObject("Account", Seq(("Bar__c", Some("Text"), None))),
      "Dummy.cls" -> "public class Dummy { {Account a; a.Bar__c = '';} }",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Custom field (wrong name)") {
    FileSystemHelper.run(Map(
      "Account.object" -> customObject("Account", Seq(("Bar__c", Some("Text"), None))),
      "Dummy.cls" -> "public class Dummy { {Account a; a.Baz__c = '';} }",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(org.issues.getMessages("/Dummy.cls") ==
        "Error: line 1 at 33-41: Unknown field or type 'Baz__c' on 'Schema.Account'\n")
    }
  }

  test("Custom base package field") {
    FileSystemHelper.run(Map(
      "pkg1/Account.object" -> customObject("Account", Seq(("Bar__c", Some("Text"), None))),
      "pkg2/Dummy.cls" -> "public class Dummy { {Account a; a.pkg1__Bar__c = '';} }",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg1 = org.addPackage(Some(Name("pkg1")), Seq(root.join("pkg1")), Seq()).asInstanceOf[PackageImpl]
      org.addPackage(Some(Name("pkg2")), Seq(root.join("pkg2")), Seq(pkg1))
      assert(!org.issues.hasMessages)
    }
  }

  test("Custom base package without namespace") {
    FileSystemHelper.run(Map(
      "pkg1/Account.object" -> customObject("Account", Seq(("Bar__c", Some("Text"), None))),
      "pkg2/Dummy.cls" -> "public class Dummy { {Account a; a.Bar__c = '';} }",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg1 = org.addPackage(Some(Name("pkg1")), Seq(root.join("pkg1")), Seq()).asInstanceOf[PackageImpl]
      org.addPackage(Some(Name("pkg2")), Seq(root.join("pkg2")), Seq(pkg1))
      assert(org.issues.getMessages("/pkg2/Dummy.cls") ==
        "Error: line 1 at 33-41: Unknown field or type 'Bar__c' on 'Schema.Account'\n")
    }
  }

  test("RecordTypeId field") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy { {Account a; a.RecordTypeId = '';} }",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Standard field reference") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy { {DescribeFieldResult a = Contract.Name.getDescribe();} }",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Standard field reference via fields") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy { {DescribeFieldResult a = Contract.fields.Name.getDescribe();} }",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Standard field reference via SObjectType fields") {
    FileSystemHelper.run(Map(
      "Dummy.cls" ->
        "public class Dummy { {DescribeFieldResult a = SObjectType.Contract.fields.BillingCity.getDefaultValue();} }",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Standard field reference via SObjectType fields (alt)") {
    FileSystemHelper.run(Map(
      "Dummy.cls" ->
        "public class Dummy { {Object a = Contract.SObjectType.fields.BillingCity.getDescribe();} }",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Standard field reference (ambiguous)") {
    FileSystemHelper.run(Map(
      "Dummy.cls" ->
        "public class Dummy { {SObjectField a = BusinessHours.FridayEndTime;} }",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Custom field reference") {
    FileSystemHelper.run(Map(
      "Account.object" -> customObject("Account", Seq(("Bar__c", Some("Text"), None))),
      "Dummy.cls" -> "public class Dummy { {SObjectField a = Account.Bar__c;} }",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Invalid field reference") {
    FileSystemHelper.run(Map(
      "Account.object" -> customObject("Account", Seq(("Bar__c", Some("Text"), None))),
      "Dummy.cls" -> "public class Dummy { {SObjectField a = Account.Baz__c;} }",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(org.issues.getMessages("/Dummy.cls") ==
        "Error: line 1 at 39-53: Unknown field or type 'Baz__c' on 'Schema.Account'\n")
    }
  }

  test("Lookup related list") {
    FileSystemHelper.run(Map(
      "Foo__c.object" -> customObject("Foo", Seq(("Lookup__c", Some("Lookup"), Some("Account")))),
      "Dummy.cls" -> "public class Dummy { {SObjectField a = Account.Lookup__r;} }",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Lookup related list (packaged)") {
    FileSystemHelper.run(Map(
      "pkg1/Foo__c.object" -> customObject("Foo", Seq(("Lookup__c", Some("Lookup"), Some("Account")))),
      "pkg2/Dummy.cls" -> "public class Dummy { {SObjectField a = Account.pkg1__Lookup__r;} }",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg1 = org.addPackage(Some(Name("pkg1")), Seq(root.join("pkg1")), Seq()).asInstanceOf[PackageImpl]
      org.addPackage(Some(Name("pkg2")), Seq(root.join("pkg2")), Seq(pkg1))
      assert(!org.issues.hasMessages)
    }
  }

  test("Object describable") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy { {DescribeSObjectResult a = SObjectType.Account;} }",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Unknown Object describe error") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy { {DescribeSObjectResult a = SObjectType.Foo;} }",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(org.issues.getMessages("/Dummy.cls") ==
        "Error: line 1 at 48-63: Unknown field or type 'Foo' on 'Schema.SObjectType'\n")
    }
  }

  test("Field describable") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy { {DescribeSObjectResult a = SObjectType.Account.Fields.Fax;} }",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Field describable via Object") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy { {DescribeFieldResult a = Contact.SObjectType.Fields.Fax;} }",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Unknown Field describe error") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy { {DescribeSObjectResult a = SObjectType.Account.Fields.Foo;} }",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(org.issues.getMessages("/Dummy.cls") ==
        "Error: line 1 at 48-78: Unknown field or type 'Foo' on 'Schema.SObjectType.Account.Fields'\n")
    }
  }

  test("Unknown FieldSet describe error") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy { {DescribeSObjectResult a = SObjectType.Account.FieldSets.Foo;} }",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(org.issues.getMessages("/Dummy.cls") ==
        "Error: line 1 at 48-81: Unknown field or type 'Foo' on 'Schema.SObjectType.Account.FieldSets'\n")
    }
  }

  test("Sfdx field reference") {
    FileSystemHelper.run(Map(
      "Account/Account.object-meta.xml" -> customObject("Account", Seq()),
      "Account/fields/Bar__c.field-meta.xml" -> customField("Bar__c", "Text", None),
      "Dummy.cls" -> "public class Dummy { {SObjectField a = Account.Bar__c;} }",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Sfdx FieldSet describable") {
    FileSystemHelper.run(Map(
      "Account/Account.object-meta.xml" -> customObject("Account", Seq()),
      "Account/fieldSets/TestFS.fieldSet-meta.xml" -> customFieldSet("TestFS"),
      "Dummy.cls" -> "public class Dummy { {DescribeSObjectResult a = SObjectType.Account.FieldSets.TestFS;} }",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Schema sObject access describable") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy { {SObjectType a = Schema.Account.SObjectType;} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("SObjectField reference on standard object") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy {public static SObjectField a = Account.SObjectField.Fax;}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Standard field without a type") {
    FileSystemHelper.run(Map(
      "Account.object" -> customObject("Account", Seq(("AccountNumber", None, None)))
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Custom field without a type") {
    FileSystemHelper.run(Map(
      "Account.object" -> customObject("Account", Seq(("AccountNumber__c", None, None)))
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(org.issues.getMessages(root.join("Account.object").toString) ==
        "Error: line 5 to 6: Expecting custom field 'AccountNumber__c' to have 'type' child element\n")
    }
  }
}
