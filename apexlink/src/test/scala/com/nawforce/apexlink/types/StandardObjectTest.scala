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

class StandardObjectTest extends AnyFunSuite with TestHelper {

  test("Not a standard object") {
    FileSystemHelper.run(
      Map("Foo/Foo.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))))
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(root.join("Foo").join("Foo.object")) ==
          "Error: line 1: No SObject declaration found for 'Schema.Foo'\n"
      )
    }
  }

  test("Not a sObject") {
    FileSystemHelper.run(
      Map("String/String.object" -> customObject("String", Seq(("Bar__c", Some("Text"), None))))
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(root.join("String").join("String.object")) ==
          "Error: line 1: No SObject declaration found for 'Schema.String'\n"
      )
    }
  }

  test("UserRecordAccess available") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy { {Account a; Boolean x = a.UserRecordAccess.HasDeleteAccess;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Account").get,
          unmanagedSObject("UserRecordAccess").get
        )
      )
    }
  }

  test("Custom field") {
    FileSystemHelper.run(
      Map(
        "Account.object" -> customObject("Account", Seq(("Bar__c", Some("Text"), None))),
        "Dummy.cls"      -> "public class Dummy { {Account a; a.Bar__c = '';} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Account").get
        )
      )
    }
  }

  test("Custom field (wrong name)") {
    FileSystemHelper.run(
      Map(
        "Account.object" -> customObject("Account", Seq(("Bar__c", Some("Text"), None))),
        "Dummy.cls"      -> "public class Dummy { {Account a; a.Baz__c = '';} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(Path("/Dummy.cls")) ==
          "Missing: line 1 at 33-41: Unknown field 'Baz__c' on SObject 'Schema.Account'\n"
      )
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Account").get
        )
      )
    }
  }

  test("Custom base package field") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          |"namespace": "pkg2",
          |"packageDirectories": [{"path": "pkg2"}],
          |"plugins": {"dependencies": [{"namespace": "pkg1"}]}
          |}""".stripMargin,
        "pkg1/Account.object" -> customObject("Account", Seq(("Bar__c", Some("Text"), None))),
        "pkg2/Dummy.cls"      -> "public class Dummy { {Account a; a.pkg1__Bar__c = '';} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        packagedClass("pkg2", "Dummy").get.blocks.head.dependencies().toSet == Set(
          packagedSObject("pkg2", "Account").get
        )
      )
    }
  }

  test("Custom base package without namespace") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          |"namespace": "pkg2",
          |"packageDirectories": [{"path": "pkg2"}],
          |"plugins": {"dependencies": [{"namespace": "pkg1"}]}
          |}""".stripMargin,
        "pkg1/Account.object" -> customObject("Account", Seq(("Bar__c", Some("Text"), None))),
        "pkg2/Dummy.cls"      -> "public class Dummy { {Account a; a.Bar__c = '';} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(Path("/pkg2/Dummy.cls")) ==
          "Missing: line 1 at 33-41: Unknown field 'Bar__c' on SObject 'Schema.Account'\n"
      )
      assert(
        packagedClass("pkg2", "Dummy").get.blocks.head.dependencies().toSet == Set(
          packagedSObject("pkg2", "Account").get
        )
      )
    }
  }

  test("RecordTypeId field") {
    FileSystemHelper.run(
      Map("Dummy.cls" -> "public class Dummy { {Account a; a.RecordTypeId = '';} }")
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Account").get
        )
      )
    }
  }

  test("Standard field reference") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy { {DescribeFieldResult a = Contract.ContractNumber.getDescribe();} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Contract").get
        )
      )
    }
  }

  test("Standard field reference via fields") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy { {DescribeFieldResult a = Contract.fields.ContractNumber.getDescribe();} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Contract").get
        )
      )
    }
  }

  test("Standard field reference via SObjectType fields") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          "public class Dummy { {Object a = SObjectType.Contract.fields.BillingCity.getDefaultValue();} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Contract").get
        )
      )
    }
  }

  test("Standard field reference via SObjectType fields (alt)") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          "public class Dummy { {Object a = Contract.SObjectType.fields.BillingCity.getDescribe();} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Contract").get
        )
      )
    }
  }

  test("Lookup SObjectField (via relationship field)") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          "public class Dummy { {SObjectField a = Opportunity.Account.Name;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Opportunity").get,
          unmanagedSObject("Account").get
        )
      )
    }
  }

  test("Lookup SObjectField (via id field)") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          "public class Dummy { {SObjectField a = Opportunity.AccountId.Name;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Opportunity").get,
          unmanagedSObject("Account").get
        )
      )
    }
  }

  test("Lookup SObjectField (passed to method)") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          "public class Dummy { {func(Opportunity.Account);} void func(SObjectField a) {}}"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Opportunity").get,
          unmanagedSObject("Account").get,
          unmanagedClass("Dummy").get.methods.find(_.name.value == "func").get
        )
      )
    }
  }

  test("Custom field reference") {
    FileSystemHelper.run(
      Map(
        "Account.object" -> customObject("Account", Seq(("Bar__c", Some("Text"), None))),
        "Dummy.cls"      -> "public class Dummy { {SObjectField a = Account.Bar__c;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Account").get
        )
      )
    }
  }

  test("Invalid field reference") {
    FileSystemHelper.run(
      Map(
        "Account.object" -> customObject("Account", Seq(("Bar__c", Some("Text"), None))),
        "Dummy.cls"      -> "public class Dummy { {SObjectField a = Account.Baz__c;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(Path("/Dummy.cls")) ==
          "Missing: line 1 at 39-53: Unknown field 'Baz__c' on SObject 'Schema.Account'\n"
      )
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Account").get
        )
      )
    }
  }

  test("Lookup related list") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo", Seq(("Lookup__c", Some("Lookup"), Some("Account")))),
        "Dummy.cls"     -> "public class Dummy { {SObjectField a = Account.Lookup__r;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Account").get,
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("Lookup related list to field") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject(
          "Foo",
          Seq(("Lookup__c", Some("Lookup"), Some("Account")), ("Bar__c", Some("Text"), None))
        ),
        "Dummy.cls" -> "public class Dummy { {SObjectField a = Account.Lookup__r.Bar__c;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Account").get,
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("Lookup related list (packaged)") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "pkg2",
            |"packageDirectories": [{"path": "pkg2"}],
            |"plugins": {"dependencies": [{"namespace": "pkg1"}]}
            |}""".stripMargin,
        "pkg1/Foo__c.object" -> customObject(
          "Foo",
          Seq(("Lookup__c", Some("Lookup"), Some("Account")))
        ),
        "pkg2/Dummy.cls" -> "public class Dummy { {SObjectField a = Account.pkg1__Lookup__r;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        packagedClass("pkg2", "Dummy").get.blocks.head.dependencies().toSet == Set(
          packagedSObject("pkg2", "Account").get
        )
      )
    }
  }

  test("Object describable") {
    FileSystemHelper.run(
      Map("Dummy.cls" -> "public class Dummy { {DescribeSObjectResult a = SObjectType.Account;} }")
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Account").get
        )
      )
    }
  }

  test("Unknown Object describe error") {
    FileSystemHelper.run(
      Map("Dummy.cls" -> "public class Dummy { {DescribeSObjectResult a = SObjectType.Foo;} }")
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(Path("/Dummy.cls")) ==
          "Missing: line 1 at 48-63: Unknown field or type 'Foo' on 'Schema.SObjectType'\n"
      )
      assert(unmanagedClass("Dummy").get.blocks.head.dependencies().isEmpty)
    }
  }

  test("Field describable") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy { {DescribeFieldResult a = SObjectType.Account.Fields.Fax;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Account").get
        )
      )
    }
  }

  test("Field describable via Object") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy { {SObjectField a = Contact.SObjectType.Fields.Fax;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Contact").get
        )
      )
    }
  }

  test("Unknown Field describe error") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy { {DescribeSObjectResult a = SObjectType.Account.Fields.Foo;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(Path("/Dummy.cls")) ==
          "Missing: line 1 at 48-78: Unknown field or type 'Foo' on 'Schema.SObjectType.Account.Fields'\n"
      )
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Account").get
        )
      )
    }
  }

  test("Unknown FieldSet describe error") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy { {DescribeSObjectResult a = SObjectType.Account.FieldSets.Foo;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(Path("/Dummy.cls")) ==
          "Missing: line 1 at 48-81: Unknown field or type 'Foo' on 'Schema.SObjectType.Account.FieldSets'\n"
      )
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Account").get
        )
      )
    }
  }

  test("Sfdx field reference") {
    FileSystemHelper.run(
      Map(
        "Account/Account.object-meta.xml"      -> customObject("Account", Seq()),
        "Account/fields/Bar__c.field-meta.xml" -> customField("Bar__c", "Text", None),
        "Dummy.cls"                            -> "public class Dummy { {SObjectField a = Account.Bar__c;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Account").get
        )
      )
    }
  }

  test("Sfdx FieldSet describable") {
    FileSystemHelper.run(
      Map(
        "Account/Account.object-meta.xml"            -> customObject("Account", Seq()),
        "Account/fieldSets/TestFS.fieldSet-meta.xml" -> customFieldSet("TestFS"),
        "Dummy.cls"                                  -> "public class Dummy { {FieldSet a = SObjectType.Account.FieldSets.TestFS;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Account").get
        )
      )
    }
  }

  test("Schema sObject access describable") {
    FileSystemHelper.run(
      Map("Dummy.cls" -> "public class Dummy { {SObjectType a = Schema.Account.SObjectType;} }")
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Account").get
        )
      )
    }
  }

  test("SObjectField reference on standard object") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy {public static SObjectField a = Account.SObjectField.Fax;}"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.fields.head.dependencies().toSet == Set(
          unmanagedSObject("Account").get
        )
      )
    }
  }

  test("Standard field without a type") {
    FileSystemHelper.run(
      Map("Account.object" -> customObject("Account", Seq(("AccountNumber", None, None))))
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
    }
  }

  test("Custom field without a type") {
    FileSystemHelper.run(
      Map("Account.object" -> customObject("Account", Seq(("AccountNumber__c", None, None))))
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(root.join("Account.object"))
          .startsWith(
            "Error: line 10: Expecting element 'fields' to have a single 'type' child element\n"
          )
      )
    }
  }

  test("Standard RowClause") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" ->
          """
            | public class Dummy {
            |  public static String a = AccountShare.RowCause.Manual;
            |}
            |""".stripMargin
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.fields.head.dependencies().toSet == Set(
          unmanagedSObject("AccountShare").get
        )
      )
    }
  }

  test("Custom RowClause") {
    FileSystemHelper.run(
      Map(
        "Account/Account.object-meta.xml" -> customObject(
          "Account",
          Seq(),
          Set(),
          Set("MyReason__c")
        ),
        "Dummy.cls" ->
          """
            | public class Dummy {
            |  public static String a = AccountShare.RowCause.MyReason__c;
            |}
            |""".stripMargin
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.fields.head.dependencies().toSet == Set(
          unmanagedSObject("AccountShare").get
        )
      )
    }
  }

  test("Sfdx Custom RowClause") {
    FileSystemHelper.run(
      Map(
        "Account/Account.object-meta.xml" -> customObject("Account", Seq()),
        "Account/sharingReasons/MyReason__c.sharingReason-meta.xml" -> customSharingReason(
          "MyReason__c"
        ),
        "Dummy.cls" ->
          """
          | public class Dummy {
          |  public static String a = AccountShare.RowCause.MyReason__c;
          |}
          |""".stripMargin
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.fields.head.dependencies().toSet == Set(
          unmanagedSObject("AccountShare").get
        )
      )
    }
  }
}
