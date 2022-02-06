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

import scala.collection.immutable.ArraySeq.ofRef

class CustomObjectTest extends AnyFunSuite with TestHelper {

  test("Bad field type") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", Some("Silly"), None))))
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(root.join("Foo__c.object")) ==
          "Error: line 10: Unrecognised type 'Silly' on custom field 'Bar__c'\n"
      )
    }
  }

  test("Illegal Map construction") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
        "Dummy.cls"     -> "public class Dummy { {SObject a = new Foo__c{'a' => 'b'};} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(Path("/Dummy.cls")) ==
          "Error: line 1 at 44-56: Expression pair list construction is only supported for Map types, not 'Schema.Foo__c'\n"
      )
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("Illegal Set construction") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
        "Dummy.cls"     -> "public class Dummy { {SObject a = new Foo__c{'a', 'b'};} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(Path("/Dummy.cls")) ==
          "Error: line 1 at 44-54: Expression list construction is only supported for Set or List types, not 'Schema.Foo__c'\n"
      )
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("No-arg construction") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
        "Dummy.cls"     -> "public class Dummy { {SObject a = new Foo__c();} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("Single arg construction") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
        "Dummy.cls"     -> "public class Dummy { {Object a = new Foo__c(Bar__c = 'A');} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("Bad arg construction") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
        "Dummy.cls"     -> "public class Dummy { {Object a = new Foo__c(Baz__c = 'A');} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(Path("/Dummy.cls")) ==
          "Missing: line 1 at 44-50: Unknown field 'Baz__c' on SObject 'Schema.Foo__c'\n"
      )
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("Multi arg construction") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject(
          "Foo",
          Seq(("Bar__c", Some("Text"), None), ("Baz__c", Some("Text"), None))
        ),
        "Dummy.cls" -> "public class Dummy { {Object a = new Foo__c(Baz__c = 'A', Bar__c = 'B');} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("Duplicate arg construction") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
        "Dummy.cls"     -> "public class Dummy { {Object a = new Foo__c(Bar__c = 'A', Bar__c = 'A');} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(Path("/Dummy.cls")) ==
          "Error: line 1 at 58-64: Duplicate assignment to field 'Bar__c' on SObject type 'Schema.Foo__c'\n"
      )
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("None name=value construction") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
        "Dummy.cls"     -> "public class Dummy { {Object a = new Foo__c('Silly');} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(Path("/Dummy.cls")) ==
          "Error: line 1 at 44-51: SObject type 'Schema.Foo__c' construction needs '<field name> = <value>' arguments\n"
      )
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("Id & Name construction") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
        "Dummy.cls"     -> "public class Dummy { {Object a = new Foo__c(Id='', Name='');} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("Lookup construction Id") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo", Seq(("Bar__c", Some("Lookup"), Some("Account")))),
        "Dummy.cls"     -> "public class Dummy { {Object a = new Foo__c(Id='', Name='');} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("Lookup construction relationship") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo", Seq(("Bar__c", Some("Lookup"), Some("Account")))),
        "Dummy.cls"     -> "public class Dummy { {Object a = new Foo__c(Bar__r = null);} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("MasterDetail construction Id") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject(
          "Foo",
          Seq(("Bar__c", Some("MasterDetail"), Some("Account")))
        ),
        "Dummy.cls" -> "public class Dummy { {Object a = new Foo__c(Bar__c = '');} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("MasterDetail construction relationship") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject(
          "Foo",
          Seq(("Bar__c", Some("MasterDetail"), Some("Account")))
        ),
        "Dummy.cls" -> "public class Dummy { {Object a = new Foo__c(Bar__r = null);} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("Cross package new") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          |"namespace": "pkg2",
          |"packageDirectories": [{"path": "pkg2"}],
          |"plugins": {"dependencies": [{"namespace": "pkg1", "path": "pkg1"}]}
          |}""".stripMargin,
        "pkg1/Foo__c.object" -> customObject("Foo__c", Seq(("Bar__c", Some("Text"), None))),
        "pkg2/Dummy.cls"     -> "public class Dummy { {Object a = new pkg1__Foo__c();} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        packagedClass("pkg2", "Dummy").get.blocks.head.dependencies().toSet == Set(
          packagedSObject("pkg1", "pkg1__Foo__c").get
        )
      )
    }
  }

  test("RecordTypeId field") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo__c", new ofRef(Array(("Bar__c", Some("Text"), None)))),
        "Dummy.cls"     -> "public class Dummy { {Foo__c a; a.RecordTypeId = '';} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("Standard field reference") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo__c", new ofRef(Array(("Bar__c", Some("Text"), None)))),
        "Dummy.cls"     -> "public class Dummy { {SObjectField a = Foo__c.Name;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("Custom field reference") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo", new ofRef(Array(("Bar__c", Some("Text"), None)))),
        "Dummy.cls"     -> "public class Dummy { {SObjectField a = Foo__c.Bar__c;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("Invalid field reference") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo", new ofRef(Array(("Bar__c", Some("Text"), None)))),
        "Dummy.cls"     -> "public class Dummy { {SObjectField a = Foo__c.Baz__c;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(Path("/Dummy.cls")) ==
          "Missing: line 1 at 39-52: Unknown field 'Baz__c' on SObject 'Schema.Foo__c'\n"
      )
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("Cross package field reference") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "pkg2",
            |"packageDirectories": [{"path": "pkg2"}],
            |"plugins": {"dependencies": [{"namespace": "pkg1", "path": "pkg1"}]}
            |}""".stripMargin,
        "pkg1/Foo__c.object" -> customObject("Foo__c", Seq(("Bar__c", Some("Text"), None))),
        "pkg2/Dummy.cls"     -> "public class Dummy { {pkg1__Foo__c a = null;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        packagedClass("pkg2", "Dummy").get.blocks.head.dependencies().toSet == Set(
          packagedSObject("pkg1", "pkg1__Foo__c").get
        )
      )
    }
  }

  test("UserRecordAccess available") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo__c", Seq()),
        "Dummy.cls"     -> "public class Dummy { {Foo__c a; Boolean x = a.UserRecordAccess.HasDeleteAccess;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get,
          unmanagedSObject("UserRecordAccess").get
        )
      )
    }
  }

  test("Lookup related list") {
    FileSystemHelper.run(
      Map(
        "Bar__c.object" -> customObject("Bar", Seq()),
        "Foo__c.object" -> customObject("Foo", Seq(("Lookup__c", Some("Lookup"), Some("Bar__c")))),
        "Dummy.cls"     -> "public class Dummy { {SObjectField a = Bar__c.Lookup__r;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get,
          unmanagedSObject("Bar__c").get
        )
      )
    }
  }

  test("Lookup related list (ghosted target)") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "pkg",
            |"packageDirectories": [{"path": "pkg"}],
            |"plugins": {"dependencies": [{"namespace": "ghosted"}]}
            |}""".stripMargin,
        "pkg/Foo__c.object" -> customObject(
          "Foo",
          Seq(("Lookup__c", Some("Lookup"), Some("ghosted__Bar__c")))
        ),
        "pkg/Dummy.cls" -> "public class Dummy { {SObjectField a = ghosted__Bar__c.Lookup__r;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(packagedClass("pkg", "Dummy").get.blocks.head.dependencies().isEmpty)
    }
  }

  test("Object describable") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
        "Dummy.cls"     -> "public class Dummy { {DescribeSObjectResult a = SObjectType.Foo__c;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("Unknown Object describe error") {
    FileSystemHelper.run(
      Map("Dummy.cls" -> "public class Dummy { {DescribeSObjectResult a = SObjectType.Foo__c;} }")
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(Path("/Dummy.cls")) ==
          "Missing: line 1 at 48-66: Unknown field or type 'Foo__c' on 'Schema.SObjectType'\n"
      )
      assert(unmanagedClass("Dummy").get.blocks.head.dependencies().isEmpty)

    }
  }

  test("Field describable") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
        "Dummy.cls"     -> "public class Dummy { {DescribeFieldResult a = SObjectType.Foo__c.Fields.Bar__c;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("Field describable via Object") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
        "Dummy.cls"     -> "public class Dummy { {SObjectField a = Foo__c.SObjectType.Fields.Bar__c;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("Field describable via Object (without Fields)") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
        "Dummy.cls"     -> "public class Dummy { {SObjectField a = Foo__c.SObjectType.Bar__c;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("Unknown Field describe error") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
        "Dummy.cls"     -> "public class Dummy { {DescribeSObjectResult a = SObjectType.Foo__c.Fields.Baz__c;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(Path("/Dummy.cls")) ==
          "Missing: line 1 at 48-80: Unknown field or type 'Baz__c' on 'Schema.SObjectType.Foo__c.Fields'\n"
      )
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("FieldSet describable") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo", Seq(), Set("TestFS")),
        "Dummy.cls"     -> "public class Dummy { {FieldSet a = SObjectType.Foo__c.FieldSets.TestFS;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("Unknown FieldSet describe error") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo", Seq(), Set("TestFS")),
        "Dummy.cls"     -> "public class Dummy { {DescribeSObjectResult a = SObjectType.Foo__c.FieldSets.OtherFS;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages(Path("/Dummy.cls")) ==
          "Missing: line 1 at 48-84: Unknown field or type 'OtherFS' on 'Schema.SObjectType.Foo__c.FieldSets'\n"
      )
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("Sfdx field reference") {
    FileSystemHelper.run(
      Map(
        "Foo__c/Foo__c.object-meta.xml"       -> customObject("Foo", Seq()),
        "Foo__c/fields/Bar__c.field-meta.xml" -> customField("Bar__c", "Text", None),
        "Dummy.cls"                           -> "public class Dummy { {SObjectField a = Foo__c.Bar__c;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("Sfdx FieldSet describable") {
    FileSystemHelper.run(
      Map(
        "Foo__c/Foo__c.object-meta.xml"             -> customObject("Foo", Seq()),
        "Foo__c/fieldSets/TestFS.fieldSet-meta.xml" -> customFieldSet("TestFS"),
        "Dummy.cls"                                 -> "public class Dummy { {FieldSet a = SObjectType.Foo__c.FieldSets.TestFS;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("Schema sObject access describable") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
        "Dummy.cls"     -> "public class Dummy { {SObjectType a = Schema.Foo__c.SObjectType;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("Share visible") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
        "Dummy.cls"     -> "public class Dummy { {SObjectField a = Foo__Share.ParentId;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet ==
          Set(unmanagedSObject("Foo__Share").get)
      )
    }
  }

  test("History visible") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
        "Dummy.cls"     -> "public class Dummy { {SObjectField a = Foo__History.Field;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet ==
          Set(unmanagedSObject("Foo__History").get)
      )
    }
  }

  test("Feed visible") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
        "Dummy.cls"     -> "public class Dummy { {SObjectField a = Foo__Feed.BestCommentId;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet ==
          Set(unmanagedSObject("Foo__Feed").get)
      )
    }
  }

  test("SObjectField reference on custom object") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
        "Dummy.cls"     -> "public class Dummy {public static SObjectField a = Foo__c.SObjectField.Bar__c;}"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.fields.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("Standard fields") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
        "Dummy.cls" ->
          s"""public class Dummy {
               |  public static Foo__c a;
               |  {
               |    System.debug(a.RecordTypeId);
               |    System.debug(a.RecordType);
               |    System.debug(a.OwnerId);
               |    System.debug(a.Owner);
               |    System.debug(a.CurrencyIsoCode);
               |    System.debug(a.CreatedBy);
               |    System.debug(a.CreatedById);
               |    System.debug(a.CreatedDate);
               |    System.debug(a.Id);
               |    System.debug(a.LastModifiedBy);
               |    System.debug(a.LastModifiedById);
               |    System.debug(a.LastModifiedDate);
               |    System.debug(a.LastReferencedDate);
               |    System.debug(a.LastViewedDate);
               |    System.debug(a.LastActivityDate);
               |    System.debug(a.Name);
               |    System.debug(a.isDeleted);
               |    System.debug(a.Tasks);
               |    System.debug(a.Notes);
               |    System.debug(a.NotesAndAttachments);
               |    System.debug(a.Attachments);
               |    System.debug(a.ContentDocumentLinks);
               |    System.debug(a.ProcessSteps);
               |    System.debug(a.SystemModstamp);
               |  }
               |  }""".stripMargin
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
    }
  }

  test("ControllerByParent has no Owner") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject(
          "Foo",
          Seq(("Bar__c", Some("Text"), None)),
          Set(),
          Set(),
          "ControlledByParent"
        ),
        "Dummy.cls" ->
          s"""public class Dummy {
             |  {
             |    Foo__c a;
             |    System.debug(a.OwnerId);
             |    System.debug(a.Owner);
             |  }
             |  }""".stripMargin
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(
        getMessages() ==
          "/Dummy.cls: Missing: line 4 at 17-26: Unknown field 'OwnerId' on SObject 'Schema.Foo__c'\n" +
            "/Dummy.cls: Missing: line 5 at 17-24: Unknown field 'Owner' on SObject 'Schema.Foo__c'\n"
      )
    }
  }

  test("Lookup SObjectField (via Id field)") {
    FileSystemHelper.run(
      Map(
        "Bar__c.object" -> customObject("Bar", Seq(("MyField__c", Some("Text"), None))),
        "Foo__c.object" -> customObject("Foo", Seq(("MyBar__c", Some("Lookup"), Some("Bar__c")))),
        "Dummy.cls"     -> "public class Dummy { {SObjectField a = Foo__c.MyBar__c.MyField__c;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get,
          unmanagedSObject("Bar__c").get
        )
      )
    }
  }

  test("Lookup SObjectField (via relationship field)") {
    FileSystemHelper.run(
      Map(
        "Bar__c.object" -> customObject("Bar", Seq(("MyField__c", Some("Text"), None))),
        "Foo__c.object" -> customObject("Foo", Seq(("MyBar__c", Some("Lookup"), Some("Bar__c")))),
        "Dummy.cls"     -> "public class Dummy { {SObjectField a = Foo__c.MyBar__r.MyField__c;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
    }
  }

  test("Lookup SObjectField (via relationship field twice)") {
    FileSystemHelper.run(
      Map(
        "Bar__c.object" -> customObject(
          "Bar",
          Seq(("MyField__c", Some("Lookup"), Some("Account")))
        ),
        "Foo__c.object" -> customObject("Foo", Seq(("MyBar__c", Some("Lookup"), Some("Bar__c")))),
        "Dummy.cls"     -> "public class Dummy { {SObjectField a = Foo__c.MyBar__r.MyField__r.Id;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get,
          unmanagedSObject("Bar__c").get,
          unmanagedSObject("Account").get
        )
      )
    }
  }

  test("Standard RowClause") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
        "Dummy.cls" ->
          """
          | public class Dummy {
          |  public static String a = Foo__Share.RowCause.Manual;
          |}
          |""".stripMargin
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.fields.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__Share").get
        )
      )
    }
  }

  test("Custom RowClause") {
    FileSystemHelper.run(
      Map(
        "Foo__c.object" -> customObject(
          "Foo",
          Seq(("Bar__c", Some("Text"), None)),
          Set(),
          Set("MyReason__c")
        ),
        "Dummy.cls" ->
          """
            | public class Dummy {
            |  public static String a = Foo__Share.RowCause.MyReason__c;
            |}
            |""".stripMargin
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.fields.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__Share").get
        )
      )
    }
  }

  test("Sfdx Custom RowClause") {
    FileSystemHelper.run(
      Map(
        "Foo__c/Foo__c.object-meta.xml" -> customObject("Foo", Seq()),
        "Foo__c/sharingReasons/MyReason__c.sharingReason-meta.xml" -> customSharingReason(
          "MyReason__c"
        ),
        "Dummy.cls" ->
          """
            | public class Dummy {
            |  public static String a = Foo__Share.RowCause.MyReason__c;
            |}
            |""".stripMargin
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.fields.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__Share").get
        )
      )
    }
  }

  test("Extended SObject reference (1GP MDAPI)") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"packageDirectories": [{"path": "mod1"}, {"path": "mod2"}]
            |}""".stripMargin,
        "mod1/Foo__c.object" -> customObject("Foo__c", Seq(("Bar__c", Some("Text"), None))),
        "mod2/Foo__c.object" -> customObject(
          "Foo__c",
          Seq(("Baz__c", Some("Text"), None)),
          Set(),
          Set(),
          extending = true
        ),
        "mod2/Dummy.cls" -> "public class Dummy { {Foo__c a = null; a.Bar__c = ''; a.Baz__c = '';} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("Extended SObject reference (2GP)") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"packageDirectories": [
            |  { "package": "first", "versionNumber": "1.2.3.NEXT", "path": "mod1"},
            |  { "package": "second", "versionNumber": "4.5.6.7", "path": "mod2",
            |    "dependencies": [
            |      {"package": "first", "versionNumber": "1.2.3.LATEST" }
            |    ]
            |  }
            |]
            |}""".stripMargin,
        "mod1/Foo__c/Foo__c.object-meta.xml"       -> customObject("Foo__c", Seq()),
        "mod1/Foo__c/fields/Bar__c.field-meta.xml" -> customField("Bar__c", "Text", None),
        "mod2/Foo__c/fields/Baz__c.field-meta.xml" -> customField("Baz__c", "Text", None),
        "mod2/Dummy.cls"                           -> "public class Dummy { {Foo__c a = null; a.Bar__c = ''; a.Baz__c = '';} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      assert(
        unmanagedClass("Dummy").get.blocks.head.dependencies().toSet == Set(
          unmanagedSObject("Foo__c").get
        )
      )
    }
  }

  test("Ghosted custom object custom field visible") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "pkg",
            |"packageDirectories": [{"path": "pkg"}],
            |"plugins": {"dependencies": [{"namespace": "ghosted"}]}
            |}""".stripMargin,
        "pkg/Foo__c/Foo__c.object-meta.xml" -> customObject(
          "Foo",
          Seq(("Bar__c", Some("Lookup"), Some("ghosted__Bar__c")))
        ),
        "pkg/Dummy.cls" ->
          """
            |public class Dummy {
            |{
            | ghosted__Bar__c record;
            | String hello = record.field__c;
            |}
            |}""".stripMargin
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
    }
  }

  test("Ghosted custom object standard field visible") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "pkg",
            |"packageDirectories": [{"path": "pkg"}],
            |"plugins": {"dependencies": [{"namespace": "ghosted"}]}
            |}""".stripMargin,
        "pkg/Foo__c/Foo__c.object-meta.xml" -> customObject(
          "Foo",
          Seq(("Bar__c", Some("Lookup"), Some("ghosted__Bar__c")))
        ),
        "pkg/Dummy.cls" ->
          """
            |public class Dummy {
            |{
            | ghosted__Bar__c record;
            | String hello = record.Name;
            |}
            |}""".stripMargin
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
    }
  }
}
