/*
 Copyright (c) 2017 Kevin Jones, All rights reserved.
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
package com.nawforce.apexlink.pkg

import com.nawforce.apexlink.org.PackageImpl
import com.nawforce.apexlink.{FileSystemHelper, TestHelper}
import com.nawforce.pkgforce.names.{Name, Names, TypeName}
import com.nawforce.pkgforce.path.PathLike
import org.scalatest.funsuite.AnyFunSuite

class RefreshSObjectTest extends AnyFunSuite with TestHelper {

  private def refresh(pkg: PackageImpl, path: PathLike, source: String): Unit = {
    path.write(source)
    pkg.refresh(path)
  }

  test("Valid custom object upsert") {
    withManualFlush {
      val customObjectMetadata = customObject("Foo", Seq())
      FileSystemHelper.run(Map("Foo__c/Foo__c.object" -> customObjectMetadata)) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        refresh(pkg, root.join("Foo__c").join("Foo__c.object"), customObjectMetadata)
        assert(org.flush())
        assert(org.issues.isEmpty)
        assert(
          pkg.orderedModules.head.types
            .contains(TypeName(Name("Foo__c"), Nil, Some(TypeName(Names.Schema)))))
      }
    }
  }

  test("Valid custom object upsert (new)") {
    withManualFlush {
      FileSystemHelper.run(Map()) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        root.createDirectory("Foo__c")
        refresh(pkg, root.join("Foo__c").join("Foo__c.object"), customObject("Foo", Seq()))
        assert(org.flush())
        assert(org.issues.isEmpty)
        assert(
          pkg.orderedModules.head.types
            .contains(TypeName(Name("Foo__c"), Nil, Some(TypeName(Names.Schema)))))
      }
    }
  }

  test("Valid custom object upsert (changed)") {
    withManualFlush {
      val customObjectMetadata = customObject("Foo", Seq())
      FileSystemHelper.run(Map("Foo__c/Foo__c.object" -> customObjectMetadata)) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        refresh(pkg, root.join("Foo__c").join("Foo__c.object"), customObjectMetadata + " ")
        assert(org.issues.isEmpty)
        assert(
          pkg.orderedModules.head.types
            .contains(TypeName(Name("Foo__c"), Nil, Some(TypeName(Names.Schema)))))
      }
    }
  }

  test("Valid custom object upsert (new component)") {
    withManualFlush {
      FileSystemHelper.run(Map("Foo__c/Foo__c.object" -> customObject("Foo", Seq()))) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        root.createDirectory("Bar__c")
        refresh(pkg, root.join("Bar__c").join("Bar__c.object"), customObject("Bar", Seq()))
        assert(org.flush())
        assert(org.issues.isEmpty)
        assert(
          pkg.orderedModules.head.types
            .contains(TypeName(Name("Foo__c"), Nil, Some(TypeName(Names.Schema)))))
        assert(
          pkg.orderedModules.head.types
            .contains(TypeName(Name("Bar__c"), Nil, Some(TypeName(Names.Schema)))))
      }
    }
  }

  test("Field refresh updates custom object") {
    withManualFlush {
      FileSystemHelper.run(Map()) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        val objectDir = root.createDirectory("Foo__c").toOption.get
        objectDir.createFile("Foo__c.object-meta.xml", customObject("Foo", Seq()))
        val fieldsDir = objectDir.createDirectory("fields").toOption.get
        val fieldFile = fieldsDir
          .createFile("Bar__c.field-meta.xml", customField("Bar__c", "Text", None))
          .toOption
          .get

        pkg.refresh(fieldFile)
        assert(org.flush())
        assert(org.issues.isEmpty)
        assert(
          pkg.orderedModules.head.types
            .contains(TypeName(Name("Foo__c"), Nil, Some(TypeName(Names.Schema)))))
      }
    }
  }

  test("FieldSet refresh updates custom object") {
    withManualFlush {
      FileSystemHelper.run(Map()) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        val objectDir = root.createDirectory("Foo__c").toOption.get
        objectDir.createFile("Foo__c.object-meta.xml", customObject("Foo", Seq()))
        val fieldSetDir = objectDir.createDirectory("fieldSets").toOption.get
        val fieldSetFile = fieldSetDir
          .createFile("Bar.fieldSet-meta.xml", customFieldSet("Bar"))
          .toOption
          .get

        pkg.refresh(fieldSetFile)
        assert(org.flush())
        assert(org.issues.isEmpty)
        assert(
          pkg.orderedModules.head.types
            .contains(TypeName(Name("Foo__c"), Nil, Some(TypeName(Names.Schema)))))
      }
    }
  }

  test("SharingReason refresh updates custom object") {
    withManualFlush {
      FileSystemHelper.run(Map()) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        val objectDir = root.createDirectory("Foo__c").toOption.get
        objectDir.createFile("Foo__c.object-meta.xml", customObject("Foo", Seq()))
        val sharingReasonDir = objectDir.createDirectory("sharingReasons").toOption.get
        val sharingReasonFile = sharingReasonDir
          .createFile("Bar.sharingReason-meta.xml", customSharingReason("Bar"))
          .toOption
          .get

        pkg.refresh(sharingReasonFile)
        assert(org.flush())
        assert(org.issues.isEmpty)
        assert(
          pkg.orderedModules.head.types
            .contains(TypeName(Name("Foo__c"), Nil, Some(TypeName(Names.Schema)))))
      }
    }
  }

  test("Valid custom object class dependent") {
    withManualFlush {
      FileSystemHelper.run(
        Map("Foo__c/Foo__c.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
            "Dummy.cls" -> "public class Dummy { {Foo__c a = new Foo__c(Bar__c = '');}}")) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        refresh(pkg, root.join("Foo__c").join("Foo__c.object"), customObject("Foo", Seq()))
        assert(org.flush())
        assert(getMessages() == "/Dummy.cls: Missing: line 1 at 44-50: Unknown field 'Bar__c' on SObject 'Schema.Foo__c'\n")
      }
    }
  }

  test("Valid custom object class dependent (reversed)") {
    withManualFlush {
      FileSystemHelper.run(
        Map("Foo__c/Foo__c.object" -> customObject("Foo", Seq()),
            "Dummy.cls" -> "public class Dummy { {Foo__c a = new Foo__c(Bar__c = '');}}")) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(getMessages() == "/Dummy.cls: Missing: line 1 at 44-50: Unknown field 'Bar__c' on SObject 'Schema.Foo__c'\n")

        refresh(pkg,
                root.join("Foo__c").join("Foo__c.object"),
                customObject("Foo", Seq(("Bar__c", Some("Text"), None))))
        assert(org.flush())
        assert(org.issues.isEmpty)
      }
    }
  }

  test("Base extended & refreshed") {
    withManualFlush {
      FileSystemHelper.run(
        Map("sfdx-project.json" -> """{"packageDirectories": [{"path": "base"}, {"path": "ext"}]}""",
            "base/Foo__c/Foo__c.object" -> customObject("Foo", Seq()),
            "ext/Foo__c/Foo__c.object" -> customObject("Foo",
                                                       Seq(("Baz__c", Some("Text"), None)),
                                                       Set(),
                                                       Set(),
                                                       extending = true))) { root: PathLike =>
        val org = createHappyOrg(root)
        refresh(org.unmanaged,
                root.join("base").join("Foo__c").join("Foo__c.object"),
                customObject("Foo", Seq(("Bar__c", Some("Text"), None))))
        assert(org.flush())
        assert(org.issues.isEmpty)
        assert(unmanagedSObject("Foo__c").get.fields.exists(_.name.value == "Baz__c"))
        assert(unmanagedSObject("Foo__c").get.fields.exists(_.name.value == "Bar__c"))
      }
    }
  }

  test("Base field extended & refreshed") {
    withManualFlush {
      FileSystemHelper.run(
        Map("sfdx-project.json" -> """{"packageDirectories": [{"path": "base"}, {"path": "ext"}]}""",
            "base/Foo__c/Foo__c.object-meta.xml" -> customObject("Foo", Seq()),
            "ext/Foo__c/fields/Baz__c.field-meta.xml" -> customField("Baz__c", "Text", None))) { root: PathLike =>
        val org = createHappyOrg(root)
        refresh(org.unmanaged,
                root.join("base").join("Foo__c").join("Foo__c.object"),
                customObject("Foo", Seq(("Bar__c", Some("Text"), None))))
        assert(org.flush())
        assert(org.issues.isEmpty)
        assert(unmanagedSObject("Foo__c").get.fields.exists(_.name.value == "Baz__c"))
        assert(unmanagedSObject("Foo__c").get.fields.exists(_.name.value == "Bar__c"))
      }
    }
  }

  test("Base extended & deleted") {
    withManualFlush {
      FileSystemHelper.run(
        Map("sfdx-project.json" -> """{"packageDirectories": [{"path": "base"}, {"path": "ext"}]}""",
            "base/Foo__c.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
            "ext/Foo__c.object" -> customObject("Foo",
                                                Seq(("Baz__c", Some("Text"), None)),
                                                Set(),
                                                Set(),
                                                extending = true))) { root: PathLike =>
        val org = createHappyOrg(root)

        val basePath = root.join("base").join("Foo__c.object")
        basePath.delete()
        org.unmanaged.refresh(basePath)
        assert(org.flush())
        //assert(getMessages() == "/ext/Foo__c.object: Error: line 1: SObject is extending an unknown SObject, '/ext/Foo__c.object'\n")
        assert(getMessages().contains("Extending unknown SObject error"))
      }
    }
  }

  test("Base field extended & deleted") {
    withManualFlush {
      FileSystemHelper.run(
        Map("sfdx-project.json" -> """{"packageDirectories": [{"path": "base"}, {"path": "ext"}]}""",
            "base/Foo__c/Foo__c.object-meta.xml" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
            "ext/Foo__c/fields/Baz__c.field-meta.xml" -> customField("Baz__c", "Text", None))) { root: PathLike =>
        val org = createHappyOrg(root)

        val basePath = root.join("base").join("Foo__c").join("Foo__c.object")
        basePath.delete()
        org.unmanaged.refresh(basePath)
        assert(org.flush())
        //assert(getMessages() == "/ext/Foo__c/fields/Baz__c.field-meta.xml: Error: line 1: SObject is extending an unknown SObject, '/ext/Foo__c'\n")
        assert(getMessages().contains("Extending unknown SObject error"))
      }
    }
  }

  test("MDAPI delete") {
    withManualFlush {
      FileSystemHelper.run(Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))))) {
        root: PathLike =>
          val org = createHappyOrg(root)

          val startTypes = org.unmanaged.modules.head.types.size
          val basePath = root.join("Foo__c.object")
          basePath.delete()
          org.unmanaged.refresh(basePath)
          assert(org.flush())

          // There are 4 primary SObjects with Share, Feed & History, each has 5 supporting internal types except Share
          // which has 6 for RowClause handling
          assert(startTypes - org.unmanaged.modules.head.types.size == 21)
      }
    }
  }

  test("SFDX delete") {
    withManualFlush {
      FileSystemHelper.run(
        Map("Foo__c/Foo__c.object-meta.xml" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))))) {
        root: PathLike =>
          val org = createHappyOrg(root)

          val startTypes = org.unmanaged.modules.head.types.size
          val basePath = root.join("Foo__c").join("Foo__c.object")
          basePath.delete()
          org.unmanaged.refresh(basePath)
          assert(org.flush())

          // There are 4 primary SObjects with Share, Feed & History, each has 5 supporting internal types except Share
          // which has 6 for RowClause handling
          assert(startTypes - org.unmanaged.modules.head.types.size == 21)
      }
    }
  }

  test("MDAPI lookup dependency delete") {
    withManualFlush {
      FileSystemHelper.run(
        Map("Bar__c.object" -> customObject("Bar", Seq()),
            "Foo__c.object" -> customObject("Foo", Seq(("Lookup__c", Some("Lookup"), Some("Bar__c")))))) {
        root: PathLike =>
          val org = createHappyOrg(root)

          val basePath = root.join("Bar__c.object")
          basePath.delete()
          org.unmanaged.refresh(basePath)
          assert(org.flush())

          assert(getMessages() ==
            "/Foo__c.object: Error: line 10: Lookup object Schema.Bar__c does not exist for field 'Lookup__c'\n")
      }
    }
  }

  test("SFDX lookup dependency delete") {
    withManualFlush {
      FileSystemHelper.run(
        Map("Bar__c/Bar__c.object-meta.xml" -> customObject("Bar", Seq()),
          "Foo__c/Foo__c.object-meta.xml" -> customObject("Foo", Seq(("Lookup__c", Some("Lookup"), Some("Bar__c")))))) {
        root: PathLike =>
          val org = createHappyOrg(root)

          val basePath = root.join("Bar__c").join("Bar__c.object")
          basePath.delete()
          org.unmanaged.refresh(basePath)
          assert(org.flush())

          assert(getMessages() ==
            "/Foo__c/Foo__c.object-meta.xml: Error: line 10: Lookup object Schema.Bar__c does not exist for field 'Lookup__c'\n")
      }
    }
  }

  test("Custom metadata upsert") {
    withManualFlush {
      val customObjectMetadata = customObject("Foo", Seq())
      FileSystemHelper.run(Map("Foo__mdt/Foo__mdt.object" -> customObjectMetadata)) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        refresh(pkg, root.join("Foo__mdt").join("Foo__mdt.object"), customObjectMetadata)
        assert(org.flush())
        assert(org.issues.isEmpty)
        assert(
          pkg.orderedModules.head.types
            .contains(TypeName(Name("Foo__mdt"), Nil, Some(TypeName(Names.Schema)))))
      }
    }
  }

  test("Custom metadata upsert (new)") {
    withManualFlush {
      FileSystemHelper.run(Map()) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        root.createDirectory("Foo__mdt")
        refresh(pkg, root.join("Foo__mdt").join("Foo__mdt.object"), customObject("Foo", Seq()))
        assert(org.flush())
        assert(org.issues.isEmpty)
        assert(
          pkg.orderedModules.head.types
            .contains(TypeName(Name("Foo__mdt"), Nil, Some(TypeName(Names.Schema)))))
      }
    }
  }

  test("Custom metadata upsert (changed)") {
    withManualFlush {
      val customObjectMetadata = customObject("Foo", Seq())
      FileSystemHelper.run(Map("Foo__mdt/Foo__mdt.object" -> customObjectMetadata)) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        refresh(pkg, root.join("Foo__mdt").join("Foo__mdt.object"), customObjectMetadata + " ")
        assert(org.issues.isEmpty)
        assert(
          pkg.orderedModules.head.types
            .contains(TypeName(Name("Foo__mdt"), Nil, Some(TypeName(Names.Schema)))))
      }
    }
  }

  test("Field refresh updates custom metadata") {
    withManualFlush {
      FileSystemHelper.run(Map()) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        val objectDir = root.createDirectory("Foo__mdt").toOption.get
        objectDir.createFile("Foo__mdt.object-meta.xml", customObject("Foo", Seq()))
        val fieldsDir = objectDir.createDirectory("fields").toOption.get
        val fieldFile = fieldsDir
          .createFile("Bar__c.field-meta.xml", customField("Bar__c", "Text", None))
          .toOption
          .get

        pkg.refresh(fieldFile)
        assert(org.flush())
        assert(org.issues.isEmpty)
        assert(
          pkg.orderedModules.head.types
            .contains(TypeName(Name("Foo__mdt"), Nil, Some(TypeName(Names.Schema)))))
      }
    }
  }

  test("Valid custom metadata class dependent") {
    withManualFlush {
      FileSystemHelper.run(
        Map("Foo__mdt/Foo__mdt.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
          "Dummy.cls" -> "public class Dummy { {Foo__mdt a = new Foo__mdt(Bar__c = '');}}")) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        refresh(pkg, root.join("Foo__mdt").join("Foo__mdt.object"), customObject("Foo", Seq()))
        assert(org.flush())
        assert(getMessages() == "/Dummy.cls: Missing: line 1 at 48-54: Unknown field 'Bar__c' on SObject 'Schema.Foo__mdt'\n")
      }
    }
  }

  test("Valid custom metadata class dependent (reversed)") {
    withManualFlush {
      FileSystemHelper.run(
        Map("Foo__mdt/Foo__mdt.object" -> customObject("Foo", Seq()),
          "Dummy.cls" -> "public class Dummy { {Foo__mdt a = new Foo__mdt(Bar__c = '');}}")) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(getMessages() == "/Dummy.cls: Missing: line 1 at 48-54: Unknown field 'Bar__c' on SObject 'Schema.Foo__mdt'\n")

        refresh(pkg,
          root.join("Foo__mdt").join("Foo__mdt.object"),
          customObject("Foo", Seq(("Bar__c", Some("Text"), None))))
        assert(org.flush())
        assert(org.issues.isEmpty)
      }
    }
  }

  test("Custom Metadata MDAPI delete") {
    withManualFlush {
      FileSystemHelper.run(Map("Foo__mdt.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))))) {
        root: PathLike =>
          val org = createHappyOrg(root)

          val startTypes = org.unmanaged.modules.head.types.size
          val basePath = root.join("Foo__mdt.object")
          basePath.delete()
          org.unmanaged.refresh(basePath)
          assert(org.flush())

          assert(startTypes - org.unmanaged.modules.head.types.size == 1)
      }
    }
  }

  test("Custom Metadata SFDX delete") {
    withManualFlush {
      FileSystemHelper.run(
        Map("Foo__mdt/Foo__mdt.object-meta.xml" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))))) {
        root: PathLike =>
          val org = createHappyOrg(root)

          val startTypes = org.unmanaged.modules.head.types.size
          val basePath = root.join("Foo__mdt").join("Foo__mdt.object")
          basePath.delete()
          org.unmanaged.refresh(basePath)
          assert(org.flush())

          assert(startTypes - org.unmanaged.modules.head.types.size == 1)
      }
    }
  }

  test("Platform event upsert") {
    withManualFlush {
      val customObjectMetadata = customObject("Foo", Seq())
      FileSystemHelper.run(Map("Foo__e/Foo__e.object" -> customObjectMetadata)) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        refresh(pkg, root.join("Foo__e").join("Foo__e.object"), customObjectMetadata)
        assert(org.flush())
        assert(org.issues.isEmpty)
        assert(
          pkg.orderedModules.head.types
            .contains(TypeName(Name("Foo__e"), Nil, Some(TypeName(Names.Schema)))))
      }
    }
  }

  test("Platform event upsert (new)") {
    withManualFlush {
      FileSystemHelper.run(Map()) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        root.createDirectory("Foo__e")
        refresh(pkg, root.join("Foo__e").join("Foo__e.object"), customObject("Foo", Seq()))
        assert(org.flush())
        assert(org.issues.isEmpty)
        assert(
          pkg.orderedModules.head.types
            .contains(TypeName(Name("Foo__e"), Nil, Some(TypeName(Names.Schema)))))
      }
    }
  }

  test("Platform event upsert (changed)") {
    withManualFlush {
      val customObjectMetadata = customObject("Foo", Seq())
      FileSystemHelper.run(Map("Foo__e/Foo__e.object" -> customObjectMetadata)) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        refresh(pkg, root.join("Foo__e").join("Foo__e.object"), customObjectMetadata + " ")
        assert(org.issues.isEmpty)
        assert(
          pkg.orderedModules.head.types
            .contains(TypeName(Name("Foo__e"), Nil, Some(TypeName(Names.Schema)))))
      }
    }
  }

  test("Field refresh updates platform event") {
    withManualFlush {
      FileSystemHelper.run(Map()) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        val objectDir = root.createDirectory("Foo__e").toOption.get
        objectDir.createFile("Foo__e.object-meta.xml", customObject("Foo", Seq()))
        val fieldsDir = objectDir.createDirectory("fields").toOption.get
        val fieldFile = fieldsDir
          .createFile("Bar__c.field-meta.xml", customField("Bar__c", "Text", None))
          .toOption
          .get

        pkg.refresh(fieldFile)
        assert(org.flush())
        assert(org.issues.isEmpty)
        assert(
          pkg.orderedModules.head.types
            .contains(TypeName(Name("Foo__e"), Nil, Some(TypeName(Names.Schema)))))
      }
    }
  }

  test("Valid platform event class dependent") {
    withManualFlush {
      FileSystemHelper.run(
        Map("Foo__e/Foo__e.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
          "Dummy.cls" -> "public class Dummy { {Foo__e a = new Foo__e(Bar__c = '');}}")) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        refresh(pkg, root.join("Foo__e").join("Foo__e.object"), customObject("Foo", Seq()))
        assert(org.flush())
        assert(getMessages() == "/Dummy.cls: Missing: line 1 at 44-50: Unknown field 'Bar__c' on SObject 'Schema.Foo__e'\n")
      }
    }
  }

  test("Valid platform event class dependent (reversed)") {
    withManualFlush {
      FileSystemHelper.run(
        Map("Foo__e/Foo__e.object" -> customObject("Foo", Seq()),
          "Dummy.cls" -> "public class Dummy { {Foo__e a = new Foo__e(Bar__c = '');}}")) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(getMessages() == "/Dummy.cls: Missing: line 1 at 44-50: Unknown field 'Bar__c' on SObject 'Schema.Foo__e'\n")

        refresh(pkg,
          root.join("Foo__e").join("Foo__e.object"),
          customObject("Foo", Seq(("Bar__c", Some("Text"), None))))
        assert(org.flush())
        assert(org.issues.isEmpty)
      }
    }
  }

  test("Platform event MDAPI delete") {
    withManualFlush {
      FileSystemHelper.run(Map("Foo__e.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))))) {
        root: PathLike =>
          val org = createHappyOrg(root)

          val startTypes = org.unmanaged.modules.head.types.size
          val basePath = root.join("Foo__e.object")
          basePath.delete()
          org.unmanaged.refresh(basePath)
          assert(org.flush())

          assert(startTypes - org.unmanaged.modules.head.types.size == 1)
      }
    }
  }

  test("Platform event SFDX delete") {
    withManualFlush {
      FileSystemHelper.run(
        Map("Foo__e/Foo__e.object-meta.xml" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))))) {
        root: PathLike =>
          val org = createHappyOrg(root)

          val startTypes = org.unmanaged.modules.head.types.size
          val basePath = root.join("Foo__e").join("Foo__e.object")
          basePath.delete()
          org.unmanaged.refresh(basePath)
          assert(org.flush())

          assert(startTypes - org.unmanaged.modules.head.types.size == 1)
      }
    }
  }

  test("Big object upsert") {
    withManualFlush {
      val customObjectMetadata = customObject("Foo", Seq())
      FileSystemHelper.run(Map("Foo__b/Foo__b.object" -> customObjectMetadata)) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        refresh(pkg, root.join("Foo__b").join("Foo__b.object"), customObjectMetadata)
        assert(org.flush())
        assert(org.issues.isEmpty)
        assert(
          pkg.orderedModules.head.types
            .contains(TypeName(Name("Foo__b"), Nil, Some(TypeName(Names.Schema)))))
      }
    }
  }

  test("Big object upsert (new)") {
    withManualFlush {
      FileSystemHelper.run(Map()) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        root.createDirectory("Foo__b")
        refresh(pkg, root.join("Foo__b").join("Foo__b.object"), customObject("Foo", Seq()))
        assert(org.flush())
        assert(org.issues.isEmpty)
        assert(
          pkg.orderedModules.head.types
            .contains(TypeName(Name("Foo__b"), Nil, Some(TypeName(Names.Schema)))))
      }
    }
  }

  test("Big object upsert (changed)") {
    withManualFlush {
      val customObjectMetadata = customObject("Foo", Seq())
      FileSystemHelper.run(Map("Foo__b/Foo__b.object" -> customObjectMetadata)) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        refresh(pkg, root.join("Foo__b").join("Foo__b.object"), customObjectMetadata + " ")
        assert(org.issues.isEmpty)
        assert(
          pkg.orderedModules.head.types
            .contains(TypeName(Name("Foo__b"), Nil, Some(TypeName(Names.Schema)))))
      }
    }
  }

  test("Field refresh updates big object") {
    withManualFlush {
      FileSystemHelper.run(Map()) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        val objectDir = root.createDirectory("Foo__b").toOption.get
        objectDir.createFile("Foo__b.object-meta.xml", customObject("Foo", Seq()))
        val fieldsDir = objectDir.createDirectory("fields").toOption.get
        val fieldFile = fieldsDir
          .createFile("Bar__c.field-meta.xml", customField("Bar__c", "Text", None))
          .toOption
          .get

        pkg.refresh(fieldFile)
        assert(org.flush())
        assert(org.issues.isEmpty)
        assert(
          pkg.orderedModules.head.types
            .contains(TypeName(Name("Foo__b"), Nil, Some(TypeName(Names.Schema)))))
      }
    }
  }

  test("Valid big object class dependent") {
    withManualFlush {
      FileSystemHelper.run(
        Map("Foo__b/Foo__b.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
          "Dummy.cls" -> "public class Dummy { {Foo__b a = new Foo__b(Bar__c = '');}}")) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        refresh(pkg, root.join("Foo__b").join("Foo__b.object"), customObject("Foo", Seq()))
        assert(org.flush())
        assert(getMessages() == "/Dummy.cls: Missing: line 1 at 44-50: Unknown field 'Bar__c' on SObject 'Schema.Foo__b'\n")
      }
    }
  }

  test("Valid big object class dependent (reversed)") {
    withManualFlush {
      FileSystemHelper.run(
        Map("Foo__b/Foo__b.object" -> customObject("Foo", Seq()),
          "Dummy.cls" -> "public class Dummy { {Foo__b a = new Foo__b(Bar__c = '');}}")) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(getMessages() == "/Dummy.cls: Missing: line 1 at 44-50: Unknown field 'Bar__c' on SObject 'Schema.Foo__b'\n")

        refresh(pkg,
          root.join("Foo__b").join("Foo__b.object"),
          customObject("Foo", Seq(("Bar__c", Some("Text"), None))))
        assert(org.flush())
        assert(org.issues.isEmpty)
      }
    }
  }

  test("Big object MDAPI delete") {
    withManualFlush {
      FileSystemHelper.run(Map("Foo__b.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))))) {
        root: PathLike =>
          val org = createHappyOrg(root)

          val startTypes = org.unmanaged.modules.head.types.size
          val basePath = root.join("Foo__b.object")
          basePath.delete()
          org.unmanaged.refresh(basePath)
          assert(org.flush())

          assert(startTypes - org.unmanaged.modules.head.types.size == 1)
      }
    }
  }

  test("Big object SFDX delete") {
    withManualFlush {
      FileSystemHelper.run(
        Map("Foo__b/Foo__b.object-meta.xml" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))))) {
        root: PathLike =>
          val org = createHappyOrg(root)

          val startTypes = org.unmanaged.modules.head.types.size
          val basePath = root.join("Foo__b").join("Foo__b.object")
          basePath.delete()
          org.unmanaged.refresh(basePath)
          assert(org.flush())

          assert(startTypes - org.unmanaged.modules.head.types.size == 1)
      }
    }
  }

}
