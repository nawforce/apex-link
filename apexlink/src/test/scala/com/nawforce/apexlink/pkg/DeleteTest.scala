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

import com.nawforce.apexlink.{FileSystemHelper, TestHelper}
import com.nawforce.pkgforce.names.{Name, Names}
import com.nawforce.pkgforce.path.PathLike
import com.nawforce.runtime.platform.Path
import org.scalatest.funsuite.AnyFunSuite

class DeleteTest extends AnyFunSuite with TestHelper {

  test("Valid delete") {
    withManualFlush {
      FileSystemHelper.run(Map("pkg/Foo.cls" -> "public class Foo {}")) { root: PathLike =>
        val path = root.join("pkg/Foo.cls")
        val org  = createOrg(root)
        val pkg  = org.unmanaged

        path.delete()
        pkg.refresh(path)
        assert(org.flush())

        assert(pkg.getTypeOfPath(path.toString) == null)
        assert(org.issues.isEmpty)
      }
    }
  }

  test("Delete creates missing") {
    withManualFlush {
      FileSystemHelper.run(
        Map("pkg/Foo.cls" -> "public class Foo {Bar b;}", "pkg/Bar.cls" -> "public class Bar {}")
      ) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        val path = root.join("pkg/Bar.cls")
        path.delete()
        pkg.refresh(path)
        assert(org.flush())

        assert(
          getMessages(root.join("pkg").join("Foo.cls")) ==
            "Missing: line 1 at 22-23: No type declaration found for 'Bar'\n"
        )
      }
    }
  }

  test("Trigger valid delete") {
    withManualFlush {
      FileSystemHelper.run(Map("pkg/Foo.trigger" -> "trigger Foo on Account (before insert) {}")) {
        root: PathLike =>
          val org = createOrg(root)
          val pkg = org.unmanaged

          val path = root.join("pkg/Foo.trigger")
          path.delete()
          pkg.refresh(path)
          assert(org.flush())

          val fooTypeId = pkg.getTypeOfPath(root.join("pkg/Foo.trigger").toString)
          assert(pkg.getPathsOfType(fooTypeId).isEmpty)
          assert(org.issues.isEmpty)
      }
    }
  }

  test("Delete creates trigger missing") {
    withManualFlush {
      FileSystemHelper.run(
        Map(
          "pkg/Foo.trigger" -> "trigger Foo on Account (before insert) {Bar b;}",
          "pkg/Bar.cls"     -> "public class Bar {}"
        )
      ) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        val path = root.join("pkg/Bar.cls")
        path.delete()
        pkg.refresh(path)
        assert(org.flush())
        assert(
          getMessages(root.join("pkg").join("Foo.trigger"))
            == "Missing: line 1 at 44-45: No type declaration found for 'Bar'\n"
        )
      }
    }
  }

  test("Delete label file") {
    withManualFlush {
      FileSystemHelper.run(
        Map(
          "CustomLabels.labels" ->
            """<?xml version="1.0" encoding="UTF-8"?>
            |<CustomLabels xmlns="http://soap.sforce.com/2006/04/metadata">
            |    <labels>
            |        <fullName>TestLabel</fullName>
            |        <protected>false</protected>
            |    </labels>
            |</CustomLabels>
            |""".stripMargin
        )
      ) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        val path = root.join("CustomLabels.labels")
        path.delete()
        pkg.refresh(path)
        assert(org.flush())

        assert(pkg.orderedModules.head.labels.fields.isEmpty)
      }
    }
  }

  test("Delete label file (multiple files)") {
    withManualFlush {
      FileSystemHelper.run(
        Map(
          "CustomLabels.labels" ->
            """<?xml version="1.0" encoding="UTF-8"?>
              |<CustomLabels xmlns="http://soap.sforce.com/2006/04/metadata">
              |    <labels>
              |        <fullName>TestLabel</fullName>
              |        <protected>false</protected>
              |    </labels>
              |</CustomLabels>
              |""".stripMargin,
          "Alt.labels" ->
            """<?xml version="1.0" encoding="UTF-8"?>
              |<CustomLabels xmlns="http://soap.sforce.com/2006/04/metadata">
              |    <labels>
              |        <fullName>TestLabel2</fullName>
              |        <protected>false</protected>
              |    </labels>
              |</CustomLabels>
              |""".stripMargin
        )
      ) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        val path = root.join("CustomLabels.labels")
        path.delete()
        pkg.refresh(path)
        assert(org.flush())

        val labels = pkg.orderedModules.head.labels
        assert(labels.fields.length == 1)
        assert(labels.fields.exists(_.name.value == "TestLabel2"))
      }
    }
  }

  test("Delete flow file") {
    withManualFlush {
      FileSystemHelper.run(Map("Test.flow-meta.xml" -> "")) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        val path = root.join("Test.flow-meta.xml")
        path.delete()
        pkg.refresh(path)
        assert(org.flush())
        assert(pkg.orderedModules.head.interviews.nestedTypes.isEmpty)
      }
    }
  }

  test("Delete flow file (multiple)") {
    withManualFlush {
      FileSystemHelper.run(Map("Test.flow-meta.xml" -> "", "Test2.flow-meta.xml" -> "")) {
        root: PathLike =>
          val org = createOrg(root)
          val pkg = org.unmanaged
          assert(org.issues.isEmpty)

          val path = root.join("Test.flow-meta.xml")
          path.delete()
          pkg.refresh(path)
          assert(org.flush())
          assert(
            pkg.orderedModules.head.interviews.nestedTypes.map(_.name).toSet == Set(Name("Test2"))
          )
      }
    }
  }

  test("Delete page file") {
    withManualFlush {
      FileSystemHelper.run(Map("TestPage.page" -> "<apex:page/>")) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        val path = root.join("TestPage.page")
        path.delete()
        pkg.refresh(path)
        assert(org.flush())
        assert(pkg.orderedModules.head.pages.fields.isEmpty)
      }
    }
  }

  test("Delete page file (multiple)") {
    withManualFlush {
      FileSystemHelper.run(Map("Test.page" -> "<apex:page/>", "Test2.page" -> "<apex:page/>")) {
        root: PathLike =>
          val org = createOrg(root)
          val pkg = org.unmanaged
          assert(org.issues.isEmpty)

          val path = root.join("Test.page")
          path.delete()
          pkg.refresh(path)
          assert(org.flush())
          assert(pkg.orderedModules.head.pages.fields.map(_.name).toSet == Set(Name("Test2")))
      }
    }
  }

  test("Delete component file") {
    withManualFlush {
      FileSystemHelper.run(Map("Test.component" -> "<apex:component/>")) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        val path = root.join("Test.component")
        path.delete()
        pkg.refresh(path)
        assert(org.flush())
        assert(
          pkg.orderedModules.head.components.nestedTypes
            .map(_.name)
            .toSet == Set(Names.c, Names.Apex, Names.Chatter)
        )
      }
    }
  }

  test("Delete component file (multiple)") {
    withManualFlush {
      FileSystemHelper.run(
        Map("Test.component" -> "<apex:component/>", "Test2.component" -> "<apex:component/>")
      ) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        val path = root.join("Test.component")
        path.delete()
        pkg.refresh(path)
        assert(org.flush())
        assert(
          pkg.orderedModules.head.components.nestedTypes
            .map(_.name)
            .toSet == Set(Name("Test2"), Names.c, Names.Apex, Names.Chatter)
        )
      }
    }
  }

  /*
  test("Valid custom object class dependent (removal)") {
    withManualFlush {
      FileSystemHelper.run(
        Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", Some("Text"), None))),
            "Dummy.cls" -> "public class Dummy { {Foo__c a = new Foo__c(Bar__c = '');}}")) { root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(org.issues.isEmpty)

        removeAndRefresh(pkg, root.join("Foo__c.object"))
        assert(org.flush())
        assert(
          org.getIssues(new IssueOptions()) == "/Dummy.cls\nMissing: line 1 at 44-50: Unknown field 'Bar__c' on SObject 'Schema.Foo__c'\n")
      }
    }
  }
   */
}
