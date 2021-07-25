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

import com.nawforce.apexlink.api.IssueOptions
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
        assert(!org.issues.hasErrorsOrWarnings)

        refresh(pkg, root.join("Foo__c").join("Foo__c.object"), customObjectMetadata)
        assert(org.flush())
        assert(!org.issues.hasErrorsOrWarnings)
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
        assert(!org.issues.hasErrorsOrWarnings)

        root.createDirectory("Foo__c")
        refresh(pkg, root.join("Foo__c").join("Foo__c.object"), customObject("Foo", Seq()))
        assert(org.flush())
        assert(!org.issues.hasErrorsOrWarnings)
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
        assert(!org.issues.hasErrorsOrWarnings)

        refresh(pkg, root.join("Foo__c").join("Foo__c.object"), customObjectMetadata + " ")
        assert(!org.issues.hasErrorsOrWarnings)
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
        assert(!org.issues.hasErrorsOrWarnings)

        root.createDirectory("Bar__c")
        refresh(pkg, root.join("Bar__c").join("Bar__c.object"), customObject("Bar", Seq()))
        assert(org.flush())
        assert(!org.issues.hasErrorsOrWarnings)
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
        assert(!org.issues.hasErrorsOrWarnings)

        val objectDir = root.createDirectory("Foo__c").toOption.get
        objectDir.createFile("Foo__c.object-meta.xml", customObject("Foo", Seq()))
        val fieldsDir = objectDir.createDirectory("fields").toOption.get
        val fieldFile = fieldsDir
          .createFile("Bar__c.field-meta.xml", customField("Bar__c", "Text", None))
          .toOption
          .get

        pkg.refresh(fieldFile)
        assert(org.flush())
        assert(!org.issues.hasErrorsOrWarnings)
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
        assert(!org.issues.hasErrorsOrWarnings)

        val objectDir = root.createDirectory("Foo__c").toOption.get
        objectDir.createFile("Foo__c.object-meta.xml", customObject("Foo", Seq()))
        val fieldSetDir = objectDir.createDirectory("fieldSets").toOption.get
        val fieldSetFile = fieldSetDir
          .createFile("Bar.fieldSet-meta.xml", customFieldSet("Bar"))
          .toOption
          .get

        pkg.refresh(fieldSetFile)
        assert(org.flush())
        assert(!org.issues.hasErrorsOrWarnings)
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
        assert(!org.issues.hasErrorsOrWarnings)

        val objectDir = root.createDirectory("Foo__c").toOption.get
        objectDir.createFile("Foo__c.object-meta.xml", customObject("Foo", Seq()))
        val sharingReasonDir = objectDir.createDirectory("sharingReasons").toOption.get
        val sharingReasonFile = sharingReasonDir
          .createFile("Bar.sharingReason-meta.xml", customSharingReason("Bar"))
          .toOption
          .get

        pkg.refresh(sharingReasonFile)
        assert(org.flush())
        assert(!org.issues.hasErrorsOrWarnings)
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
        assert(!org.issues.hasErrorsOrWarnings)

        refresh(pkg, root.join("Foo__c").join("Foo__c.object"), customObject("Foo", Seq()))
        assert(org.flush())
        assert(
          org.getIssues(new IssueOptions()) == "/Dummy.cls\nMissing: line 1 at 44-50: Unknown field 'Bar__c' on SObject 'Schema.Foo__c'\n")
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
        assert(
          org.getIssues(new IssueOptions()) == "/Dummy.cls\nMissing: line 1 at 44-50: Unknown field 'Bar__c' on SObject 'Schema.Foo__c'\n")

        refresh(pkg,
                root.join("Foo__c").join("Foo__c.object"),
                customObject("Foo", Seq(("Bar__c", Some("Text"), None))))
        assert(org.flush())
        assert(!org.issues.hasErrorsOrWarnings)
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
        assert(!org.issues.hasErrorsOrWarnings)
        assert(unmanagedSObject("Foo__c").get.fields.exists(_.name.value == "Baz__c"))
        assert(unmanagedSObject("Foo__c").get.fields.exists(_.name.value == "Bar__c"))
      }
    }
  }

  test("Base field extended & refreshed") {
    withManualFlush {
      FileSystemHelper.run(
        Map("sfdx-project.json" -> """{"packageDirectories": [{"path": "base"}, {"path": "ext"}]}""",
            "base/Foo__c/Foo__c.object" -> customObject("Foo", Seq()),
            "ext/Foo__c/fields/Baz__c.field-meta.xml" -> customField("Baz__c", "Text", None))) { root: PathLike =>
        val org = createHappyOrg(root)
        refresh(org.unmanaged,
                root.join("base").join("Foo__c").join("Foo__c.object"),
                customObject("Foo", Seq(("Bar__c", Some("Text"), None))))
        assert(org.flush())
        assert(!org.issues.hasErrorsOrWarnings)
        assert(unmanagedSObject("Foo__c").get.fields.exists(_.name.value == "Baz__c"))
        assert(unmanagedSObject("Foo__c").get.fields.exists(_.name.value == "Bar__c"))
      }
    }
  }

  test("Base extended & deleted") {
    withManualFlush {
      FileSystemHelper.run(
        Map("sfdx-project.json" -> """{"packageDirectories": [{"path": "base"}, {"path": "ext"}]}""",
            "base/Foo__c/Foo__c.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
            "ext/Foo__c/Foo__c.object" -> customObject("Foo",
                                                       Seq(("Baz__c", Some("Text"), None)),
                                                       Set(),
                                                       Set(),
                                                       extending = true))) { root: PathLike =>
        val org = createHappyOrg(root)

        val basePath = root.join("base").join("Foo__c").join("Foo__c.object")
        basePath.delete()
        org.unmanaged.refresh(basePath)
        assert(org.flush())
        assert(
          org.getIssues(new IssueOptions()) == "/ext/Foo__c/Foo__c.object\nError: line 1: Metadata is extending an unknown SObject, '/ext/Foo__c'\n")
      }
    }
  }

  test("Base field extended & deleted") {
    withManualFlush {
      FileSystemHelper.run(
        Map("sfdx-project.json" -> """{"packageDirectories": [{"path": "base"}, {"path": "ext"}]}""",
            "base/Foo__c/Foo__c.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
            "ext/Foo__c/fields/Baz__c.field-meta.xml" -> customField("Baz__c", "Text", None))) { root: PathLike =>
        val org = createHappyOrg(root)

        val basePath = root.join("base").join("Foo__c").join("Foo__c.object")
        basePath.delete()
        org.unmanaged.refresh(basePath)
        assert(org.flush())
        assert(
          org.getIssues(new IssueOptions()) == "/ext/Foo__c/fields/Baz__c.field-meta.xml\nError: line 1: Metadata is extending an unknown SObject, '/ext/Foo__c'\n")

      }
    }
  }

}
