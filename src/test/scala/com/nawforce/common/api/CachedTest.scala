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

import com.nawforce.common.names._
import com.nawforce.common.org.{OrgImpl, PackageImpl}
import com.nawforce.common.path.PathLike
import com.nawforce.common.types.apex.{FullDeclaration, SummaryDeclaration}
import com.nawforce.runtime.FileSystemHelper
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class CachedTest extends AnyFunSuite with BeforeAndAfter {

  before {
    ServerOps.setParsedDataCaching(true)
  }

  after {
    ServerOps.setParsedDataCaching(false)
  }

  def assertIsNotDeclaration(pkg: PackageImpl, name: String, namespace: Option[Name]=None): Unit = {
    assert(pkg.findTypes(Seq(TypeName(Name(name)).withNamespace(namespace))).isEmpty)
  }

  def assertIsFullDeclaration(pkg: PackageImpl, name: String, namespace: Option[Name]=None): Unit = {
    assert(pkg.findTypes(Seq(TypeName(Name(name)).withNamespace(namespace))).head.isInstanceOf[FullDeclaration])
  }

  def assertIsSummaryDeclaration(pkg: PackageImpl, name: String, namespace: Option[Name]=None): Unit = {
    assert(pkg.findTypes(Seq(TypeName(Name(name)).withNamespace(namespace))).head.isInstanceOf[SummaryDeclaration])
  }

  def cacheTest(bar: String, foo:String, newBar: String): Unit = {
    FileSystemHelper.run(Map(
      "Bar.cls" -> bar,
      "Foo.cls" -> foo
    ), setupCache = true) { root: PathLike =>
      // Cache classes
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(None, Seq(root), Seq()).asInstanceOf[PackageImpl]
      assert(!org.issues.hasMessages)
      assertIsFullDeclaration(pkg, "Bar")
      assertIsFullDeclaration(pkg, "Foo")
      org.flush()

      // Reload from cache
      val org2 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg2 = org2.addPackage(None, Seq(root), Seq()).asInstanceOf[PackageImpl]
      assert(!org2.issues.hasMessages)
      assertIsSummaryDeclaration(pkg2, "Bar")
      assertIsSummaryDeclaration(pkg2, "Foo")
      org2.flush()

      // Change super class
      root.createFile("Bar.cls", newBar)
      root.join("Foo.cls").delete()
      val org3 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg3 = org3.addPackage(None, Seq(root), Seq()).asInstanceOf[PackageImpl]
      assert(!org3.issues.hasMessages)
      assertIsFullDeclaration(pkg3, "Bar")
      assertIsNotDeclaration(pkg3, "Foo")
      org3.flush()

      // Test if we notice change, i.e. Foo should not be cached
      root.createFile("Foo.cls", foo)
      val org4 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg4 = org4.addPackage(None, Seq(root), Seq()).asInstanceOf[PackageImpl]
      assert(!org4.issues.hasMessages)
      assertIsSummaryDeclaration(pkg4, "Bar")
      assertIsFullDeclaration(pkg4, "Foo")
      org4.flush()
    }
  }

  test("Cached super class") {
    cacheTest(
      "public virtual class Bar {}",
      "public class Foo extends Bar {}",
      "public virtual class Bar {/* Changed */}"
    )
  }

  test("Cached interface") {
    cacheTest(
      "public interface Bar {}",
      "public class Foo implements Bar {}",
      "public interface Bar {/* Changed */}"
    )
  }

  test("Cached field type") {
    cacheTest(
      "public class Bar {}",
      "public class Foo {Bar field;}",
      "public class Bar {/* Changed */}"
    )
  }

  test("Cached field expr type") {
    cacheTest(
      "public class Bar {}",
      "public class Foo {Object field = new Bar();}",
      "public class Bar {/* Changed */}"
    )
  }

  test("Cached method return type") {
    cacheTest(
      "public class Bar {}",
      "public class Foo {Bar func() {return null;}}",
      "public class Bar {/* Changed */}"
    )
  }

  test("Cached method parameter type") {
    cacheTest(
      "public class Bar {}",
      "public class Foo {void func(Bar a) {}}",
      "public class Bar {/* Changed */}"
    )
  }

  test("Cached method body type") {
    cacheTest(
      "public class Bar {}",
      "public class Foo {void func() {Bar a;}}",
      "public class Bar {/* Changed */}"
    )
  }

  test("Cached constructor parameter type") {
    cacheTest(
      "public class Bar {}",
      "public class Foo {Foo(Bar a) {}}",
      "public class Bar {/* Changed */}"
    )
  }

  test("Cached constructor body type") {
    cacheTest(
      "public class Bar {}",
      "public class Foo {Foo() {Bar a;}}",
      "public class Bar {/* Changed */}"
    )
  }

  test("Cached nested type super class") {
    cacheTest(
      "public virtual class Bar {}",
      "public class Foo {class Nested extends Bar {}}",
      "public virtual class Bar {/* Changed */}"
    )
  }

  test("Cached super class (nested type)") {
    cacheTest(
      "public class Bar {public virtual class Nested {}}",
      "public class Foo extends Bar.Nested {}",
      "public class Bar {public virtual class Nested {} /* Changed */}"
    )
  }

  test("Missing error is not cached") {
    FileSystemHelper.run(Map(
      "pkg1/Foo.cls" -> "public class Foo extends Bar {}",
      "pkg2/Foo.cls" -> "public class Foo extends Bar {}"
    ), setupCache = true) { root: PathLike =>
      // Setup as cached
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(None, Seq(root.join("pkg1")), Seq()).asInstanceOf[PackageImpl]
      assertIsFullDeclaration(pkg, "Foo")
      assert(org.getIssues(new IssueOptions()) == "/pkg1/Foo.cls\nMissing: line 1 at 13-16: No type declaration found for 'Bar'\n")

      val org2 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg2 = org2.addPackage(None, Seq(root.join("pkg2")), Seq()).asInstanceOf[PackageImpl]
      assertIsFullDeclaration(pkg2, "Foo")
      assert(org2.getIssues(new IssueOptions()) == "/pkg2/Foo.cls\nMissing: line 1 at 13-16: No type declaration found for 'Bar'\n")
    }
  }

  test("General error is cached") {
    FileSystemHelper.run(Map(
      "pkg1/Foo.cls" -> "public class Foo {{bar();}}",
      "pkg2/Foo.cls" -> "public class Foo {{bar();}}"
    ), setupCache = true) { root: PathLike =>
      // Setup as cached
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(None, Seq(root.join("pkg1")), Seq()).asInstanceOf[PackageImpl]
      assertIsFullDeclaration(pkg, "Foo")
      assert(org.getIssues(new IssueOptions()) ==
        "/pkg1/Foo.cls\nError: line 1 at 19-24: No matching method found for 'bar' on 'Foo' taking no arguments\n")
      org.flush()

      val org2 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg2 = org2.addPackage(None, Seq(root.join("pkg2")), Seq()).asInstanceOf[PackageImpl]
      assertIsSummaryDeclaration(pkg2, "Foo")
      assert(org2.getIssues(new IssueOptions()) ==
        "/pkg2/Foo.cls\nError: line 1 at 19-24: No matching method found for 'bar' on 'Foo' taking no arguments\n")
    }
  }

  test("Label dependency is cached") {
    FileSystemHelper.run(Map(
      "CustomLabels.labels" ->
        """<?xml version="1.0" encoding="UTF-8"?>
          |<CustomLabels xmlns="http://soap.sforce.com/2006/04/metadata">
          |    <labels>
          |        <fullName>TestLabel</fullName>
          |        <language>en_US</language>
          |        <protected>false</protected>
          |        <shortDescription>TestLabel Description</shortDescription>
          |        <value>TestLabel Value</value>
          |    </labels>
          |</CustomLabels>
          |""".stripMargin,
      "Dummy.cls" -> "public class Dummy { {String a = System.label.TestLabel;} }"
    ), setupCache = true) { root: PathLike =>
      val org1 = Org.newOrg().asInstanceOf[OrgImpl]
      org1.addPackage(None, Seq(root), Seq())
      assert(!org1.issues.hasMessages)
      org1.flush()

      val org2 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg2 = org2.addPackage(None, Seq(root), Seq()).asInstanceOf[PackageImpl]
      assert(!org2.issues.hasMessages)

      assertIsSummaryDeclaration(pkg2, "Dummy")
      assert(pkg2.getDependencies(TypeIdentifier(None, TypeName(Name("Dummy"))), inheritanceOnly = false)
        .sameElements(Array(TypeIdentifier(None, TypeNames.Label))))

      root.createFile("CustomLabels.labels",
        "<CustomLabels xmlns=\"http://soap.sforce.com/2006/04/metadata\"/>")

      val org3 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg3 = org3.addPackage(None, Seq(root), Seq()).asInstanceOf[PackageImpl]
      assertIsFullDeclaration(pkg3, "Dummy")
      assert(org3.getIssues(new IssueOptions()) ==
        "/Dummy.cls\nMissing: line 1 at 33-55: Unknown field or type 'TestLabel' on 'System.Label'\n")
    }
  }

  test("Packaged label dependency is cached") {
    FileSystemHelper.run(Map(
      "pkg1/CustomLabels.labels" ->
        """<?xml version="1.0" encoding="UTF-8"?>
          |<CustomLabels xmlns="http://soap.sforce.com/2006/04/metadata">
          |    <labels>
          |        <fullName>TestLabel</fullName>
          |        <language>en_US</language>
          |        <protected>false</protected>
          |        <shortDescription>TestLabel Description</shortDescription>
          |        <value>TestLabel Value</value>
          |    </labels>
          |</CustomLabels>
          |""".stripMargin,
      "pkg2/Dummy.cls" -> "public class Dummy { {String a = System.label.pkg1.TestLabel;} }"
    ), setupCache = true) { root: PathLike =>
      val org1 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg11 = org1.addPackage(Some(Name("pkg1")), Seq(root.join("pkg1")), Seq()).asInstanceOf[PackageImpl]
      org1.addPackage(Some(Name("pkg2")), Seq(root.join("pkg2")), Seq(pkg11))
      assert(!org1.issues.hasMessages)
      org1.flush()

      val org2 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg21 = org2.addPackage(Some(Name("pkg1")), Seq(root.join("pkg1")), Seq()).asInstanceOf[PackageImpl]
      val pkg22 = org2.addPackage(Some(Name("pkg2")), Seq(root.join("pkg2")), Seq(pkg21)).asInstanceOf[PackageImpl]
      assert(!org2.issues.hasMessages)

      assertIsSummaryDeclaration(pkg22, "Dummy")
      assert(pkg22.getDependencies(
        TypeIdentifier(Some(Name("pkg2")), TypeName(Name("Dummy"), Nil, Some(TypeName(Name("pkg2"))))), inheritanceOnly = false)
        .sameElements(Array(TypeIdentifier(Some(Name("pkg2")), TypeNames.Label))))

      root.createFile("pkg1/CustomLabels.labels",
        "<CustomLabels xmlns=\"http://soap.sforce.com/2006/04/metadata\"/>")

      val org3 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg31 = org3.addPackage(Some(Name("pkg1")), Seq(root.join("pkg1")), Seq()).asInstanceOf[PackageImpl]
      val pkg32 = org3.addPackage(Some(Name("pkg2")), Seq(root.join("pkg2")), Seq(pkg31)).asInstanceOf[PackageImpl]

      // Still summary as Dummy does not *directly* depend on pkg1 labels
      assertIsSummaryDeclaration(pkg32, "Dummy")
      assert(!org3.issues.hasMessages)
    }
  }

  test("Multi-file label dependency is cached") {
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
      "AltLabels.labels" ->
        """<?xml version="1.0" encoding="UTF-8"?>
          |<CustomLabels xmlns="http://soap.sforce.com/2006/04/metadata">
          |    <labels>
          |        <fullName>TestLabel2</fullName>
          |        <protected>false</protected>
          |    </labels>
          |</CustomLabels>
          |""".stripMargin,
      "Dummy.cls" -> "public class Dummy { {String a = System.label.TestLabel2;} }"
    ), setupCache = true) { root: PathLike =>
      val org1 = Org.newOrg().asInstanceOf[OrgImpl]
      org1.addPackage(None, Seq(root), Seq())
      assert(!org1.issues.hasMessages)
      org1.flush()

      val org2 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg2 = org2.addPackage(None, Seq(root), Seq()).asInstanceOf[PackageImpl]
      assert(!org2.issues.hasMessages)

      assertIsSummaryDeclaration(pkg2, "Dummy")
      assert(pkg2.getDependencies(TypeIdentifier(None, TypeName(Name("Dummy"))), inheritanceOnly = false)
        .sameElements(Array(TypeIdentifier(None, TypeNames.Label))))

      root.createFile("AltLabels.labels",
        "<CustomLabels xmlns=\"http://soap.sforce.com/2006/04/metadata\"/>")

      val org3 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg3 = org3.addPackage(None, Seq(root), Seq()).asInstanceOf[PackageImpl]
      assertIsFullDeclaration(pkg3, "Dummy")
      assert(org3.getIssues(new IssueOptions()) ==
        "/Dummy.cls\nMissing: line 1 at 33-56: Unknown field or type 'TestLabel2' on 'System.Label'\n")
    }
  }

  test("Flow dependency is cached") {
    FileSystemHelper.run(Map(
      "Test.flow-meta.xml" -> "",
      "Dummy.cls" -> "public class Dummy { {Flow.Interview i = new Flow.Interview.Test(new Map<String, Object>());} }"
    ), setupCache = true) { root: PathLike =>
      val org1 = Org.newOrg().asInstanceOf[OrgImpl]
      org1.addPackage(None, Seq(root), Seq())
      assert(!org1.issues.hasMessages)
      org1.flush()

      val org2 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg2 = org2.addPackage(None, Seq(root), Seq()).asInstanceOf[PackageImpl]
      assert(!org2.issues.hasMessages)

      assertIsSummaryDeclaration(pkg2, "Dummy")
      assert(pkg2.getDependencies(TypeIdentifier(None, TypeName(Name("Dummy"))), inheritanceOnly = false)
        .sameElements(Array(TypeIdentifier(None, TypeNames.Interview))))

      root.join("Test.flow-meta.xml").delete()

      val org3 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg3 = org3.addPackage(None, Seq(root), Seq()).asInstanceOf[PackageImpl]
      assertIsFullDeclaration(pkg3, "Dummy")
      assert(org3.getIssues(new IssueOptions()) ==
        "/Dummy.cls\nMissing: line 1 at 45-64: No type declaration found for 'Flow.Interview.Test'\n")
    }
  }

  test("Packaged flow dependency is cached") {
    FileSystemHelper.run(Map(
      "pkg1/Test.flow-meta.xml" -> "",
      "pkg2/Dummy.cls" -> "public class Dummy { {Flow.Interview i = new Flow.Interview.pkg1.Test(new Map<String, Object>());} }"
    ), setupCache = true) { root: PathLike =>
      val org1 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg11 = org1.addPackage(Some(Name("pkg1")), Seq(root.join("pkg1")), Seq()).asInstanceOf[PackageImpl]
      org1.addPackage(Some(Name("pkg2")), Seq(root.join("pkg2")), Seq(pkg11))
      assert(!org1.issues.hasMessages)
      org1.flush()

      val org2 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg21 = org2.addPackage(Some(Name("pkg1")), Seq(root.join("pkg1")), Seq()).asInstanceOf[PackageImpl]
      val pkg22 = org2.addPackage(Some(Name("pkg2")), Seq(root.join("pkg2")), Seq(pkg21)).asInstanceOf[PackageImpl]
      assert(!org2.issues.hasMessages)

      assertIsSummaryDeclaration(pkg22, "Dummy")
      assert(pkg22.getDependencies(
        TypeIdentifier(Some(Name("pkg2")), TypeName(Name("Dummy"), Nil, Some(TypeName(Name("pkg2"))))), inheritanceOnly = false)
        .sameElements(Array(TypeIdentifier(Some(Name("pkg2")), TypeNames.Interview))))

      root.join("pkg1/Test.flow-meta.xml").delete()

      val org3 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg31 = org3.addPackage(Some(Name("pkg1")), Seq(root.join("pkg1")), Seq()).asInstanceOf[PackageImpl]
      val pkg32 = org3.addPackage(Some(Name("pkg2")), Seq(root.join("pkg2")), Seq(pkg31)).asInstanceOf[PackageImpl]

      assertIsFullDeclaration(pkg32, "Dummy")
      assert(org3.getIssues(new IssueOptions()) ==
        "/pkg2/Dummy.cls\nMissing: line 1 at 45-69: No type declaration found for 'Flow.Interview.pkg1.Test'\n")
    }
  }

  test("Page dependency is cached") {
    FileSystemHelper.run(Map(
      "TestPage.page" -> "",
      "Dummy.cls" -> "public class Dummy { {PageReference a = Page.TestPage;} }"
    ), setupCache = true) { root: PathLike =>
      val org1 = Org.newOrg().asInstanceOf[OrgImpl]
      org1.addPackage(None, Seq(root), Seq())
      assert(!org1.issues.hasMessages)
      org1.flush()

      val org2 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg2 = org2.addPackage(None, Seq(root), Seq()).asInstanceOf[PackageImpl]
      assert(!org2.issues.hasMessages)

      assertIsSummaryDeclaration(pkg2, "Dummy")
      assert(pkg2.getDependencies(TypeIdentifier(None, TypeName(Name("Dummy"))), inheritanceOnly = false)
        .sameElements(Array(TypeIdentifier(None, TypeNames.Page))))

      root.join("TestPage.page").delete()

      val org3 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg3 = org3.addPackage(None, Seq(root), Seq()).asInstanceOf[PackageImpl]
      assertIsFullDeclaration(pkg3, "Dummy")
      assert(org3.getIssues(new IssueOptions()) ==
        "/Dummy.cls\nMissing: line 1 at 40-53: Unknown field or type 'TestPage' on 'Page'\n")
    }
  }

  test("Packaged page dependency is cached") {
    FileSystemHelper.run(Map(
      "pkg1/TestPage.page" -> "",
      "pkg2/Dummy.cls" -> "public class Dummy { {PageReference a = Page.pkg1__TestPage;} }"
    ), setupCache = true) { root: PathLike =>
      val org1 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg11 = org1.addPackage(Some(Name("pkg1")), Seq(root.join("pkg1")), Seq()).asInstanceOf[PackageImpl]
      org1.addPackage(Some(Name("pkg2")), Seq(root.join("pkg2")), Seq(pkg11))
      assert(!org1.issues.hasMessages)
      org1.flush()

      val org2 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg21 = org2.addPackage(Some(Name("pkg1")), Seq(root.join("pkg1")), Seq()).asInstanceOf[PackageImpl]
      val pkg22 = org2.addPackage(Some(Name("pkg2")), Seq(root.join("pkg2")), Seq(pkg21)).asInstanceOf[PackageImpl]
      assert(!org2.issues.hasMessages)

      assertIsSummaryDeclaration(pkg22, "Dummy")
      assert(pkg22.getDependencies(
        TypeIdentifier(Some(Name("pkg2")), TypeName(Name("Dummy"), Nil, Some(TypeName(Name("pkg2"))))), inheritanceOnly = false)
        .sameElements(Array(TypeIdentifier(Some(Name("pkg2")), TypeNames.Page))))

      root.join("pkg1/TestPage.page").delete()

      val org3 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg31 = org3.addPackage(Some(Name("pkg1")), Seq(root.join("pkg1")), Seq()).asInstanceOf[PackageImpl]
      val pkg32 = org3.addPackage(Some(Name("pkg2")), Seq(root.join("pkg2")), Seq(pkg31)).asInstanceOf[PackageImpl]

      // Still summary as Dummy does not *directly* depend on pkg1 pages
      assertIsSummaryDeclaration(pkg32, "Dummy")
      assert(!org3.issues.hasMessages)
    }
  }

  test("Component dependency is cached") {
    FileSystemHelper.run(Map(
      "Test.component" -> "",
      "Dummy.cls" -> "public class Dummy { {Component c = new Component.Test();} }"
    ), setupCache = true) { root: PathLike =>
      val org1 = Org.newOrg().asInstanceOf[OrgImpl]
      org1.addPackage(None, Seq(root), Seq())
      assert(!org1.issues.hasMessages)
      org1.flush()

      val org2 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg2 = org2.addPackage(None, Seq(root), Seq()).asInstanceOf[PackageImpl]
      assert(!org2.issues.hasMessages)

      assertIsSummaryDeclaration(pkg2, "Dummy")
      assert(pkg2.getDependencies(TypeIdentifier(None, TypeName(Name("Dummy"))), inheritanceOnly = false)
        .sameElements(Array(TypeIdentifier(None, TypeNames.Component))))

      root.join("Test.component").delete()

      val org3 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg3 = org3.addPackage(None, Seq(root), Seq()).asInstanceOf[PackageImpl]
      assertIsFullDeclaration(pkg3, "Dummy")
      assert(org3.getIssues(new IssueOptions()) ==
        "/Dummy.cls\nMissing: line 1 at 40-54: No type declaration found for 'Component.Test'\n")
    }
  }

  test("Packaged component dependency is cached") {
    FileSystemHelper.run(Map(
      "pkg1/Test.component" -> "",
      "pkg2/Dummy.cls" -> "public class Dummy { {Component c = new Component.pkg1.Test();} }"
    ), setupCache = true) { root: PathLike =>
      val org1 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg11 = org1.addPackage(Some(Name("pkg1")), Seq(root.join("pkg1")), Seq()).asInstanceOf[PackageImpl]
      org1.addPackage(Some(Name("pkg2")), Seq(root.join("pkg2")), Seq(pkg11))
      assert(!org1.issues.hasMessages)
      org1.flush()

      val org2 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg21 = org2.addPackage(Some(Name("pkg1")), Seq(root.join("pkg1")), Seq()).asInstanceOf[PackageImpl]
      val pkg22 = org2.addPackage(Some(Name("pkg2")), Seq(root.join("pkg2")), Seq(pkg21)).asInstanceOf[PackageImpl]
      assert(!org2.issues.hasMessages)

      assertIsSummaryDeclaration(pkg22, "Dummy")
      assert(pkg22.getDependencies(
        TypeIdentifier(Some(Name("pkg2")), TypeName(Name("Dummy"), Nil, Some(TypeName(Name("pkg2"))))), inheritanceOnly = false)
        .sameElements(Array(TypeIdentifier(Some(Name("pkg2")), TypeNames.Component))))

      root.join("pkg1/Test.component").delete()

      val org3 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg31 = org3.addPackage(Some(Name("pkg1")), Seq(root.join("pkg1")), Seq()).asInstanceOf[PackageImpl]
      val pkg32 = org3.addPackage(Some(Name("pkg2")), Seq(root.join("pkg2")), Seq(pkg31)).asInstanceOf[PackageImpl]

      assertIsFullDeclaration(pkg32, "Dummy")
      assert(org3.getIssues(new IssueOptions()) ==
        "/pkg2/Dummy.cls\nMissing: line 1 at 40-59: No type declaration found for 'Component.pkg1.Test'\n")
    }
  }
}
