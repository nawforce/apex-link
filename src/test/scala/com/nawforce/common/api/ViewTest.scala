/*
 [The "BSD licence"]
 Copyright (c) 2020 Kevin Jones
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
package com.nawforce.common.api

import com.nawforce.common.names.{Names, TypeNames}
import com.nawforce.common.org.{OrgImpl, PackageImpl, ViewInfoImpl}
import com.nawforce.common.path.PathLike
import com.nawforce.runtime.{FileSystemHelper, SourceBlob}
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class ViewTest extends AnyFunSuite with BeforeAndAfter {

  test("good path type (mdapi)") {
    FileSystemHelper.run(Map(
      "pkg/Foo.cls" -> "public class Foo {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      val view = pkg.getViewOfType(root.join("pkg/Foo.cls"), None)
      assert(view.hasType)
      assert(view.diagnostics.isEmpty)
      assert(view.asInstanceOf[ViewInfoImpl].td.get.name == Name("Foo"))
    }
  }

  test("bad path type (mdapi)") {
    FileSystemHelper.run(Map(
      "foo.scala" -> ""
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      val view = pkg.getViewOfType(root.join("foo.scala"), None)
      assert(!view.hasType)
      assert(view.diagnostics.sameElements(Array(Diagnostic("Error", LineLocation(0),
        "Path does not identify a supported metadata type"))))
    }
  }

  test("good path (sfdx)") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{\"packageDirectories\" : [{ \"path\": \"force-app\", \"default\": true}]}",
      "force-app/pkg/Foo.cls" -> "public class Foo {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      val view = pkg.getViewOfType(root.join("force-app/pkg/Foo.cls"), None)
      assert(view.hasType)
      assert(view.diagnostics.isEmpty)
      assert(view.asInstanceOf[ViewInfoImpl].td.get.name == Name("Foo"))
    }
  }

  test("ignored path (sfdx)") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{\"packageDirectories\" : [{ \"path\": \"force-app\", \"default\": true}]}",
      ".forceignore" -> "force-app/pkg/",
      "force-app/pkg/Foo.cls" -> ""
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addSFDXTestPackage(root, Array[PackageImpl]())
      val view = pkg.getViewOfType(root.join("force-app/pkg/Foo.cls"), None)
      assert(!view.hasType)
      assert(view.diagnostics.sameElements(Array(Diagnostic("Error", LineLocation(0),
        "Path is being ignored in this workspace"))))
    }
  }

  test("new parse error") {
    FileSystemHelper.run(Map(
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      val view = pkg.getViewOfType(root.join("Foo.cls"), Some(SourceBlob("")))
      assert(!view.hasType)
      assert(view.diagnostics.length == 1)
      assert(view.diagnostics.head.category == "Error")
      assert(view.diagnostics.head.location == LineLocation(1))
      assert(view.diagnostics.head.message.nonEmpty)
    }
  }

  test("replacement parse error") {
    FileSystemHelper.run(Map(
      "pkg/Foo.cls" -> "public class Foo {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      val view = pkg.getViewOfType(root.join("pkg/Foo.cls"), Some(SourceBlob("")))
      assert(!view.hasType)
      assert(view.diagnostics.length == 1)
      assert(view.diagnostics.head.category == "Error")
      assert(view.diagnostics.head.location == LineLocation(1))
      assert(view.diagnostics.head.message.nonEmpty)
    }
  }

  test("good replacement") {
    FileSystemHelper.run(Map(
      "pkg/Foo.cls" -> "public class Foo {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      val view = pkg.getViewOfType(root.join("pkg/Foo.cls"), Some(SourceBlob("public class Foo {}")))
      assert(view.hasType)
      assert(view.diagnostics.isEmpty)
    }
  }

  test("validate error") {
    FileSystemHelper.run(Map(
      "pkg/Foo.cls" -> "public class Foo {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      val view = pkg.getViewOfType(root.join("pkg/Foo.cls"), Some(SourceBlob("public class Foo {Bar b;}")))
      assert(view.hasType)
      assert(view.diagnostics.length == 1)
      assert(view.diagnostics.head.category == "Missing")
      assert(view.diagnostics.head.location == RangeLocation(Position(1,22),Position(1,23)))
      assert(view.diagnostics.head.message.nonEmpty)
    }
  }

  test("Replacement does not create dependency holder") {
    FileSystemHelper.run(Map(
      "pkg/Bar.cls" -> "public class Bar {}",
      "pkg/Foo.cls" -> "public class Foo {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      val view = pkg.getViewOfType(root.join("pkg/Foo.cls"), Some(SourceBlob("public class Foo {Bar b;}")))
      assert(view.hasType)
      assert(view.diagnostics.isEmpty)

      val barTypeId = pkg.getTypeOfPath(root.join("pkg/Bar.cls").toString)
      assert(pkg.getDependencyHolders(barTypeId).sameElements(Array[TypeName]()))
    }
  }

  test("Good path trigger (mdapi)") {
    FileSystemHelper.run(Map(
      "pkg/Foo.trigger" -> "trigger Foo on Account (before insert) {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      val view = pkg.getViewOfType(root.join("pkg/Foo.trigger"), None)
      assert(view.hasType)
      assert(view.diagnostics.isEmpty)
      assert(view.asInstanceOf[ViewInfoImpl].td.get.name == Name("__sfdc_trigger/Foo"))
    }
  }

  test("New trigger parse error") {
    FileSystemHelper.run(Map(
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      val view = pkg.getViewOfType(root.join("Foo.trigger"), Some(SourceBlob("")))
      assert(!view.hasType)
      assert(view.diagnostics.length == 1)
      assert(view.diagnostics.head.category == "Error")
      assert(view.diagnostics.head.location == LineLocation(1))
      assert(view.diagnostics.head.message.nonEmpty)
    }
  }

  test("Replacement trigger parse error") {
    FileSystemHelper.run(Map(
      "pkg/Foo.trigger" -> "trigger Foo on Account (before insert) {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      val view = pkg.getViewOfType(root.join("pkg/Foo.trigger"), Some(SourceBlob("")))
      assert(!view.hasType)
      assert(view.diagnostics.length == 1)
      assert(view.diagnostics.head.category == "Error")
      assert(view.diagnostics.head.location == LineLocation(1))
      assert(view.diagnostics.head.message.nonEmpty)
    }
  }

  test("Good replacement trigger") {
    FileSystemHelper.run(Map(
      "pkg/Foo.trigger" -> "trigger Foo on Account (before insert) {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      val view = pkg.getViewOfType(root.join("pkg/Foo.cls"), Some(SourceBlob("public class Foo {}")))
      assert(view.hasType)
      assert(view.diagnostics.isEmpty)
    }
  }

  test("Validate trigger error") {
    FileSystemHelper.run(Map(
      "pkg/Foo.trigger" -> "trigger Foo on Account (before insert) {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      val view = pkg.getViewOfType(root.join("pkg/Foo.trigger"),
        Some(SourceBlob("trigger Foo on Account (before insert) {Bar b;}")))
      assert(view.hasType)
      assert(view.diagnostics.length == 1)
      assert(view.diagnostics.head.category == "Missing")
      assert(view.diagnostics.head.location == RangeLocation(Position(1,44),Position(1,45)))
      assert(view.diagnostics.head.message.nonEmpty)
    }
  }

  test("Empty labels file") {
    FileSystemHelper.run(Map(
      "CustomLabels.labels" -> "<CustomLabels xmlns=\"http://soap.sforce.com/2006/04/metadata\"/>",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      val view = pkg.getViewOfType(root.join("CustomLabels.labels"), None)
      assert(view.hasType)
      assert(view.diagnostics.isEmpty)
      assert(view.typeName == TypeNames.Label)
    }
  }

  test("Labels replacement error") {
    FileSystemHelper.run(Map(
      "CustomLabels.labels" -> "<CustomLabels xmlns=\"http://soap.sforce.com/2006/04/metadata\"/>",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      val view = pkg.getViewOfType(root.join("CustomLabels.labels"), Some(SourceBlob("")))
      assert(view.hasType)
      assert(view.diagnostics.sameElements(Array(Diagnostic("Error",PointLocation(Position(1,1)),"Premature end of file."))))
      assert(view.typeName == TypeNames.Label)
    }
  }

  test("Multiple label files ") {
    FileSystemHelper.run(Map(
      "CustomLabels.labels" ->
        """<?xml version="1.0" encoding="UTF-8"?>
          |<CustomLabels xmlns="http://soap.sforce.com/2006/04/metadata">
          |    <labels>
          |        <fullName>TestLabel</fullName>
          |        <protected>false</protected>
          |    </labels>
          |</CustomLabels>
          |""".stripMargin,
      "AltLabels.labels" -> "<CustomLabels xmlns=\"http://soap.sforce.com/2006/04/metadata\"/>",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      val view = pkg.getViewOfType(root.join("AltLabels.labels"), Some(SourceBlob(
        """<?xml version="1.0" encoding="UTF-8"?>
          |<CustomLabels xmlns="http://soap.sforce.com/2006/04/metadata">
          |    <labels>
          |        <fullName>TestLabel2</fullName>
          |        <protected>false</protected>
          |    </labels>
          |</CustomLabels>
          |""".stripMargin,
      ))).asInstanceOf[ViewInfoImpl]
      assert(view.hasType)
      assert(view.diagnostics.isEmpty)
      assert(view.typeName == TypeNames.Label)
      assert(view.td.get.fields.map(_.name).toSet == Set(Name("TestLabel"), Name("TestLabel2")))
    }
  }

  test("Flow") {
    FileSystemHelper.run(Map(
      "Test.flow-meta.xml" -> "",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      val view = pkg.getViewOfType(root.join("Test.flow-meta.xml"), None).asInstanceOf[ViewInfoImpl]
      assert(view.hasType)
      assert(view.diagnostics.isEmpty)
      assert(view.typeName == TypeNames.Interview)
      assert(view.td.get.nestedTypes.map(_.name).toSet == Set(Name("Test")))
    }
  }

  test("Additional Flow") {
    FileSystemHelper.run(Map(
      "Test.flow-meta.xml" -> "",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      val view = pkg.getViewOfType(root.join("Test2.flow-meta.xml"), Some(SourceBlob(""))).asInstanceOf[ViewInfoImpl]
      assert(view.hasType)
      assert(view.diagnostics.isEmpty)
      assert(view.typeName == TypeNames.Interview)
      assert(view.td.get.nestedTypes.map(_.name).toSet == Set(Name("Test"), Name("Test2")))
    }
  }

  test("Page") {
    FileSystemHelper.run(Map(
      "TestPage.page" -> "",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      val view = pkg.getViewOfType(root.join("TestPage.page"), None).asInstanceOf[ViewInfoImpl]
      assert(view.hasType)
      assert(view.diagnostics.isEmpty)
      assert(view.typeName == TypeNames.Page)
      assert(view.td.get.fields.map(_.name).toSet == Set(Name("TestPage")))
    }
  }

  test("Additional Page") {
    FileSystemHelper.run(Map(
      "TestPage.page" -> "",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      val view = pkg.getViewOfType(root.join("TestPage2.page"), Some(SourceBlob(""))).asInstanceOf[ViewInfoImpl]
      assert(view.hasType)
      assert(view.diagnostics.isEmpty)
      assert(view.typeName == TypeNames.Page)
      assert(view.td.get.fields.map(_.name).toSet == Set(Name("TestPage"), Name("TestPage2")))
    }
  }

  test("Component") {
    FileSystemHelper.run(Map(
      "Test.component" -> "",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      val view = pkg.getViewOfType(root.join("Test.component"), None).asInstanceOf[ViewInfoImpl]
      assert(view.hasType)
      assert(view.diagnostics.isEmpty)
      assert(view.typeName == TypeNames.Component)
      assert(view.td.get.nestedTypes.map(_.name).toSet == Set(Name("Test"), Names.c, Names.Apex, Names.Chatter))
    }
  }

  test("Additional Component") {
    FileSystemHelper.run(Map(
      "Test.component" -> "",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      val view = pkg.getViewOfType(root.join("Test2.component"), Some(SourceBlob(""))).asInstanceOf[ViewInfoImpl]
      assert(view.hasType)
      assert(view.diagnostics.isEmpty)
      assert(view.typeName == TypeNames.Component)
      assert(view.td.get.nestedTypes.map(_.name).toSet == Set(Name("Test"), Name("Test2"), Names.c, Names.Apex, Names.Chatter))
    }
  }
}
