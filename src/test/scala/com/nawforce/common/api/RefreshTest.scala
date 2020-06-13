/*
 [The "BSD licence"]
 Copyright (c) 2017 Kevin Jones
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
import com.nawforce.common.org.OrgImpl
import com.nawforce.common.path.PathLike
import com.nawforce.runtime.{FileSystemHelper, SourceBlob}
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class RefreshTest extends AnyFunSuite with BeforeAndAfter {

  test("Valid refresh") {
    FileSystemHelper.run(Map(
      "pkg/Foo.cls" -> "public class Foo {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(pkg.refresh(root.join("pkg/Foo.cls"), Some(SourceBlob("public class Foo {}"))) == null)
      assert(!org.issues.hasMessages)
    }
  }

  test("Valid refresh (new)") {
    FileSystemHelper.run(Map(
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(pkg.refresh(root.join("pkg").join("Foo.cls"), Some(SourceBlob("public class Foo {}"))) == null)
      assert(!org.issues.hasMessages)
      assert(pkg.getTypeOfPathInternal(root.join("pkg").join("Foo.cls")) != null)
    }
  }

  test("Valid refresh with changes") {
    FileSystemHelper.run(Map(
      "pkg/Foo.cls" -> "public class Foo {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(pkg.refresh(root.join("pkg/Foo.cls"), Some(SourceBlob("public class Foo {Object a;}"))) == null)
      assert(!org.issues.hasMessages)
    }
  }

  test("Valid refresh with non-significant changes") {
    FileSystemHelper.run(Map(
      "pkg/Foo.cls" -> "public class Foo {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(pkg.refresh(root.join("pkg/Foo.cls"), Some(SourceBlob("public class Foo {/* A change */}"))) == null)
      assert(!org.issues.hasMessages)
    }
  }

  test("Refresh creates missing") {
    FileSystemHelper.run(Map(
      "pkg/Foo.cls" -> "public class Foo {Bar.Inner b;}",
      "pkg/Bar.cls" -> "public class Bar {public class Inner {}}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.refresh(root.join("pkg/Bar.cls"), Some(SourceBlob("public class Bar {}"))) == null)
      assert(org.issues.getMessages("/pkg/Foo.cls")
        == "Missing: line 1 at 28-29: No type declaration found for 'Bar.Inner'\n")
    }
  }

  test("Refresh resolves missing") {
    FileSystemHelper.run(Map(
      "pkg/Foo.cls" -> "public class Foo {Bar.Inner b;}",
      "pkg/Bar.cls" -> "public class Bar {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(org.issues.getMessages("/pkg/Foo.cls")
        == "Missing: line 1 at 28-29: No type declaration found for 'Bar.Inner'\n")

      assert(pkg.refresh(root.join("pkg/Bar.cls"), Some(SourceBlob("public class Bar {public class Inner {}}"))) == null)
      assert(!org.issues.hasMessages)
    }
  }

  test("Dependencies created") {
    FileSystemHelper.run(Map(
      "pkg/Foo.cls" -> "public class Foo {}",
      "pkg/Bar.cls" -> "public class Bar {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      pkg.refresh(root.join("pkg/Foo.cls"), Some(SourceBlob("public class Foo {Bar b;}")))
      assert(!org.issues.hasMessages)

      val fooTypeId = pkg.getTypeOfPathInternal(root.join("pkg").join("Foo.cls")).get.asTypeIdentifier
      val barTypeId = pkg.getTypeOfPathInternal(root.join("pkg").join("Bar.cls")).get.asTypeIdentifier

      assert(pkg.getDependencyHolders(fooTypeId).isEmpty)
      assert(pkg.getDependencies(fooTypeId, inheritanceOnly = false).sameElements(Array(barTypeId)))

      assert(pkg.getDependencyHolders(barTypeId).sameElements(Array(fooTypeId)))
      assert(pkg.getDependencies(barTypeId, inheritanceOnly = false).isEmpty)
    }
  }

  test("Dependencies created cross package") {
    FileSystemHelper.run(Map(
      "pkg1/Bar.cls" -> "global class Bar {}",
      "pkg2/Foo.cls" -> "public class Foo {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg1 = org.addMDAPITestPackage(Some(Name("p1")), Seq(root.join("pkg1")), Seq())
      val pkg2 = org.addMDAPITestPackage(Some(Name("p2")), Seq(root.join("pkg2")), Seq(pkg1))
      pkg2.refresh(root.join("pkg2/Foo.cls"), Some(SourceBlob("public class Foo {p1.Bar b;}")))
      assert(!org.issues.hasMessages)

      val barTypeId = pkg1.getTypeOfPathInternal(root.join("pkg1").join("Bar.cls")).get.asTypeIdentifier
      val fooTypeId = pkg2.getTypeOfPathInternal(root.join("pkg2").join("Foo.cls")).get.asTypeIdentifier

      assert(pkg2.getDependencyHolders(fooTypeId).isEmpty)
      assert(pkg2.getDependencies(fooTypeId, inheritanceOnly = false).sameElements(Array(barTypeId)))

      assert(pkg1.getDependencyHolders(barTypeId).sameElements(Array(fooTypeId)))
      assert(pkg1.getDependencies(barTypeId, inheritanceOnly = false).isEmpty)
    }
  }

  test("Valid trigger refresh") {
    FileSystemHelper.run(Map(
      "pkg/Foo.trigger" -> "trigger Foo on Account (before insert) {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      pkg.refresh(root.join("pkg/Foo.trigger"), Some(SourceBlob("trigger Foo on Account (before insert) {}")))
      assert(!org.issues.hasMessages)
    }
  }

  test("Valid trigger refresh (new)") {
    FileSystemHelper.run(Map(
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      pkg.refresh(root.join("pkg/Foo.trigger"), Some(SourceBlob("trigger Foo on Account (before insert) {}")))
      assert(!org.issues.hasMessages)
      assert(pkg.getTypeOfPathInternal(root.join("pkg").join("Foo.trigger")) != null)
    }
  }

  test("Valid trigger refresh with changes") {
    FileSystemHelper.run(Map(
      "pkg/Foo.trigger" -> "trigger Foo on Account (before insert) {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      pkg.refresh(root.join("pkg/Foo.trigger"), Some(SourceBlob("trigger Foo on Account (before insert) {Object a;}")))
      assert(!org.issues.hasMessages)
    }
  }

  test("Refresh creates trigger missing") {
    FileSystemHelper.run(Map(
      "pkg/Foo.trigger" -> "trigger Foo on Account (before insert) {Bar.Inner b;}",
      "pkg/Bar.cls" -> "public class Bar {public class Inner {}}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      pkg.refresh(root.join("pkg/Bar.cls"), Some(SourceBlob("public class Bar {}")))
      assert(org.issues.getMessages("/pkg/Foo.trigger")
        == "Missing: line 1 at 50-51: No type declaration found for 'Bar.Inner'\n")
    }
  }

  test("Refresh resolves trigger missing") {
    FileSystemHelper.run(Map(
      "pkg/Foo.trigger" -> "trigger Foo on Account (before insert) {Bar.Inner b;}",
      "pkg/Bar.cls" -> "public class Bar {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(org.issues.getMessages("/pkg/Foo.trigger")
        == "Missing: line 1 at 50-51: No type declaration found for 'Bar.Inner'\n")

      pkg.refresh(root.join("pkg/Bar.cls"), Some(SourceBlob("public class Bar {public class Inner {}}")))
      assert(!org.issues.hasMessages)
    }
  }

  test("Trigger dependencies created") {
    FileSystemHelper.run(Map(
      "pkg/Foo.trigger" -> "trigger Foo on Account (before insert) {}",
      "pkg/Bar.cls" -> "public class Bar {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      pkg.refresh(root.join("pkg/Foo.trigger"), Some(SourceBlob("trigger Foo on Account (before insert) {Bar b;}")))

      val fooTypeId = pkg.getTypeOfPathInternal(root.join("pkg").join("Foo.trigger")).get.asTypeIdentifier
      val barTypeId = pkg.getTypeOfPathInternal(root.join("pkg").join("Bar.cls")).get.asTypeIdentifier

      assert(pkg.getDependencyHolders(fooTypeId).isEmpty)
      assert(pkg.getDependencies(fooTypeId, inheritanceOnly = false).sameElements(Array(barTypeId)))

      assert(pkg.getDependencyHolders(barTypeId).sameElements(Array(fooTypeId)))
      assert(pkg.getDependencies(barTypeId, inheritanceOnly = false).isEmpty)
    }
  }

  test("Trigger dependencies created cross package") {
    FileSystemHelper.run(Map(
      "pkg1/Bar.cls" -> "global class Bar {}",
      "pkg2/Foo.trigger" -> "trigger Foo on Account (before insert) {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg1 = org.addMDAPITestPackage(Some(Name("p1")), Seq(root.join("pkg1")), Seq())
      val pkg2 = org.addMDAPITestPackage(Some(Name("p2")), Seq(root.join("pkg2")), Seq(pkg1))
      pkg2.refresh(root.join("pkg2/Foo.trigger"),
        Some(SourceBlob("trigger Foo on Account (before insert) {p1.Bar b;}")))

      val fooTypeId = pkg2.getTypeOfPathInternal(root.join("pkg2").join("Foo.trigger")).get.asTypeIdentifier
      val barTypeId = pkg1.getTypeOfPathInternal(root.join("pkg1").join("Bar.cls")).get.asTypeIdentifier

      assert(pkg2.getDependencyHolders(fooTypeId).isEmpty)
      assert(pkg2.getDependencies(fooTypeId, inheritanceOnly = false).sameElements(Array(barTypeId)))

      assert(pkg1.getDependencyHolders(barTypeId).sameElements(Array(fooTypeId)))
      assert(pkg1.getDependencies(barTypeId, inheritanceOnly = false).isEmpty)
    }
  }

  test("Valid label upsert") {
    FileSystemHelper.run(Map(
      "CustomLabels.labels" -> "<CustomLabels xmlns=\"http://soap.sforce.com/2006/04/metadata\"/>"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      pkg.refresh(root.join("CustomLabels.labels"), Some(SourceBlob(
        "<CustomLabels xmlns=\"http://soap.sforce.com/2006/04/metadata\"/>")))
      assert(!org.issues.hasMessages)
    }
  }

  test("Valid label upsert (new)") {
    FileSystemHelper.run(Map(
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      pkg.refresh(root.join("CustomLabels.labels"), Some(SourceBlob(
        "<CustomLabels xmlns=\"http://soap.sforce.com/2006/04/metadata\"/>")))
      assert(!org.issues.hasMessages)
    }
  }

  test("Valid label upsert (changed)") {
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
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      pkg.refresh(root.join("CustomLabels.labels"), Some(SourceBlob(
        """<?xml version="1.0" encoding="UTF-8"?>
          |<CustomLabels xmlns="http://soap.sforce.com/2006/04/metadata">
          |    <labels>
          |        <fullName>TestLabel2</fullName>
          |        <protected>false</protected>
          |    </labels>
          |</CustomLabels>
          |""".stripMargin,
      )))
      val labels = pkg.packageType(TypeNames.Label).get
      assert(labels.fields.size == 1)
      assert(labels.fields.exists(_.name.value == "TestLabel2"))
    }
  }

  test("Valid label upsert (alt file)") {
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
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      pkg.refresh(root.join("Alt.labels"), Some(SourceBlob(
        """<?xml version="1.0" encoding="UTF-8"?>
          |<CustomLabels xmlns="http://soap.sforce.com/2006/04/metadata">
          |    <labels>
          |        <fullName>TestLabel2</fullName>
          |        <protected>false</protected>
          |    </labels>
          |</CustomLabels>
          |""".stripMargin,
      )))
      val labels = pkg.packageType(TypeNames.Label).get
      assert(labels.fields.size == 2)
      assert(labels.fields.exists(_.name.value == "TestLabel"))
      assert(labels.fields.exists(_.name.value == "TestLabel2"))
    }
  }

  test("Valid flow upsert") {
    FileSystemHelper.run(Map(
      "Test.flow-meta.xml" -> ""
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      pkg.refresh(root.join("Test.flow-meta.xml"), Some(SourceBlob("")))
      assert(pkg.interviews.findNestedType(Name("Test")).nonEmpty)
      assert(!org.issues.hasMessages)
    }
  }

  test("Valid flow upsert (new)") {
    FileSystemHelper.run(Map(
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      pkg.refresh(root.join("Test.flow-meta.xml"), Some(SourceBlob("")))
      assert(pkg.interviews.findNestedType(Name("Test")).nonEmpty)
      assert(!org.issues.hasMessages)
    }
  }

  test("Valid flow upsert (changed)") {
    FileSystemHelper.run(Map(
      "Test.flow-meta.xml" -> ""
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      pkg.refresh(root.join("Test.flow-meta.xml"), Some(SourceBlob("Changed")))
      assert(pkg.interviews.findNestedType(Name("Test")).nonEmpty)
    }
  }

  test("Valid flow upsert (new flow)") {
    FileSystemHelper.run(Map(
      "Test.flow-meta.xml" -> ""
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      pkg.refresh(root.join("Test2.flow-meta.xml"), Some(SourceBlob("")))
      assert(pkg.interviews.nestedTypes.map(_.name).toSet == Set(Name("Test"), Name("Test2")))
    }
  }

  test("Valid page upsert") {
    FileSystemHelper.run(Map(
      "TestPage.page" -> ""
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      pkg.refresh(root.join("TestPage.page"), Some(SourceBlob("")))
      assert(pkg.pages.findField(Name("TestPage"), Some(true)).nonEmpty)
    }
  }

  test("Valid page upsert (new)") {
    FileSystemHelper.run(Map(
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      pkg.refresh(root.join("TestPage.page"), Some(SourceBlob("")))
      assert(pkg.pages.findField(Name("TestPage"), Some(true)).nonEmpty)
    }
  }

  test("Valid page upsert (changed)") {
    FileSystemHelper.run(Map(
      "TestPage.page" -> ""
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      pkg.refresh(root.join("TestPage.page"), Some(SourceBlob("Changed")))
      assert(pkg.pages.findField(Name("TestPage"), Some(true)).nonEmpty)
    }
  }

  test("Valid page upsert (new page)") {
    FileSystemHelper.run(Map(
      "TestPage.page" -> ""
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      pkg.refresh(root.join("TestPage2.page"), Some(SourceBlob("")))
      assert(pkg.pages.fields.map(_.name).toSet == Set(Name("TestPage"), Name("TestPage2")))
    }
  }

  test("Valid component upsert") {
    FileSystemHelper.run(Map(
      "Test.component" -> ""
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      pkg.refresh(root.join("Test.component"), Some(SourceBlob("")))
      assert(pkg.components.findNestedType(Name("Test")).nonEmpty)
    }
  }

  test("Valid component upsert (new)") {
    FileSystemHelper.run(Map(
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      pkg.refresh(root.join("Test.component"), Some(SourceBlob("")))
      assert(pkg.components.findNestedType(Name("Test")).nonEmpty)
    }
  }

  test("Valid component upsert (changed)") {
    FileSystemHelper.run(Map(
      "Test.component" -> ""
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      pkg.refresh(root.join("Test.component"), Some(SourceBlob("Changed")))
      assert(pkg.components.findNestedType(Name("Test")).nonEmpty)
    }
  }

  test("Valid component upsert (new component)") {
    FileSystemHelper.run(Map(
      "Test.component" -> ""
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      pkg.refresh(root.join("Test2.component"), Some(SourceBlob("")))
      assert(pkg.components.nestedTypes.map(_.name).toSet ==
        Set(Name("Test"), Name("Test2"), Names.c, Names.Apex, Names.Chatter))
    }
  }
}
