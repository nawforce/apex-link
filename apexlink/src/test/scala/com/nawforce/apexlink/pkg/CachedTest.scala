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

import com.nawforce.apexlink.api.{IssueOptions, ServerOps}
import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.names.TypeNames.TypeNameUtils
import com.nawforce.apexlink.org.PackageImpl
import com.nawforce.apexlink.types.apex.{FullDeclaration, SummaryDeclaration}
import com.nawforce.apexlink.{FileSystemHelper, TestHelper}
import com.nawforce.pkgforce.names.{Name, TypeIdentifier, TypeName}
import com.nawforce.pkgforce.path.PathLike
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class CachedTest extends AnyFunSuite with TestHelper with BeforeAndAfter {

  def assertIsNotDeclaration(pkg: PackageImpl, name: String, namespace: Option[Name] = None): Unit = {
    assert(pkg.orderedModules.head.findModuleType(TypeName(Name(name)).withNamespace(namespace)).isEmpty)
  }

  def assertIsFullDeclaration(pkg: PackageImpl, name: String, namespace: Option[Name] = None): Unit = {
    assert(
      pkg.orderedModules.head
        .findModuleType(TypeName(Name(name)).withNamespace(namespace))
        .head
        .isInstanceOf[FullDeclaration])
  }

  def assertIsSummaryDeclaration(pkg: PackageImpl, name: String, namespace: Option[Name] = None): Unit = {
    assert(
      pkg.orderedModules.head
        .findModuleType(TypeName(Name(name)).withNamespace(namespace))
        .head
        .isInstanceOf[SummaryDeclaration])
  }

  def cacheTest(bar: String, foo: String, newBar: String): Unit = {
    FileSystemHelper.run(Map("Bar.cls" -> bar, "Foo.cls" -> foo)) { root: PathLike =>
      // Cache classes
      val org = createOrg(root)
      assert(org.issues.isEmpty)
      val pkg = org.unmanaged
      assertIsFullDeclaration(pkg, "Bar")
      assertIsFullDeclaration(pkg, "Foo")
      org.flush()

      // Reload from cache
      val org2 = createOrg(root)
      assert(org2.issues.isEmpty)
      val pkg2 = org2.unmanaged
      assertIsSummaryDeclaration(pkg2, "Bar")
      assertIsSummaryDeclaration(pkg2, "Foo")
      org2.flush()

      // Change super class
      root.createFile("Bar.cls", newBar)
      root.join("Foo.cls").delete()
      val org3 = createOrg(root)
      val pkg3 = org3.unmanaged
      assert(org3.issues.isEmpty)
      assertIsFullDeclaration(pkg3, "Bar")
      assertIsNotDeclaration(pkg3, "Foo")
      org3.flush()

      // Test if we notice change, i.e. Foo should not be cached
      root.createFile("Foo.cls", foo)
      val org4 = createOrg(root)
      val pkg4 = org4.unmanaged
      assert(org4.issues.isEmpty)
      assertIsSummaryDeclaration(pkg4, "Bar")
      assertIsFullDeclaration(pkg4, "Foo")
      org4.flush()

    }
  }

  before {
    ServerOps.setAutoFlush(false)
  }

  after {
    ServerOps.setAutoFlush(true)
  }

  test("Cached super class") {
    cacheTest("public virtual class Bar {}",
              "public class Foo extends Bar {}",
              "public virtual class Bar {/* Changed */}")
  }

  test("Cached interface") {
    cacheTest("public interface Bar {}", "public class Foo implements Bar {}", "public interface Bar {/* Changed */}")
  }

  test("Cached field type") {
    cacheTest("public class Bar {}", "public class Foo {Bar field;}", "public class Bar {/* Changed */}")
  }

  test("Cached field expr type") {
    cacheTest("public class Bar {}", "public class Foo {Object field = new Bar();}", "public class Bar {/* Changed */}")
  }

  test("Cached method return type") {
    cacheTest("public class Bar {}", "public class Foo {Bar func() {return null;}}", "public class Bar {/* Changed */}")
  }

  test("Cached method parameter type") {
    cacheTest("public class Bar {}", "public class Foo {void func(Bar a) {}}", "public class Bar {/* Changed */}")
  }

  test("Cached method body type") {
    cacheTest("public class Bar {}", "public class Foo {void func() {Bar a;}}", "public class Bar {/* Changed */}")
  }

  test("Cached constructor parameter type") {
    cacheTest("public class Bar {}", "public class Foo {Foo(Bar a) {}}", "public class Bar {/* Changed */}")
  }

  test("Cached constructor body type") {
    cacheTest("public class Bar {}", "public class Foo {Foo() {Bar a;}}", "public class Bar {/* Changed */}")
  }

  test("Cached nested type super class") {
    cacheTest("public virtual class Bar {}",
              "public class Foo {class Nested extends Bar {}}",
              "public virtual class Bar {/* Changed */}")
  }

  test("Cached super class (nested type)") {
    cacheTest("public class Bar {public virtual class Nested {}}",
              "public class Foo extends Bar.Nested {}",
              "public class Bar {public virtual class Nested {} /* Changed */}")
  }

  test("Missing error is not cached") {
    FileSystemHelper.run(
      Map("pkg1/Foo.cls" -> "public class Foo extends Bar {}", "pkg2/Foo.cls" -> "public class Foo extends Bar {}")) {
      root: PathLike =>
        // Setup as cached
        val org = createOrg(root.join("pkg1"))
        val pkg = org.unmanaged
        assertIsFullDeclaration(pkg, "Foo")
        assert(getMessages(org) == "/pkg1/Foo.cls: Missing: line 1 at 13-16: No type declaration found for 'Bar'\n")

        val org2 = createOrg(root.join("pkg2"))
        val pkg2 = org2.unmanaged
        assertIsFullDeclaration(pkg2, "Foo")
        assert(getMessages(org2) == "/pkg2/Foo.cls: Missing: line 1 at 13-16: No type declaration found for 'Bar'\n")
    }
  }

  test("General error is cached") {
    FileSystemHelper.run(
      Map("pkg1/Foo.cls" -> "public class Foo {{bar();}}", "pkg2/Foo.cls" -> "public class Foo {{bar();}}")) {
      root: PathLike =>
        // Setup as cached
        val org = createOrg(root.join("pkg1"))
        val pkg = org.unmanaged
        assertIsFullDeclaration(pkg, "Foo")
        assert(getMessages(org) ==
          "/pkg1/Foo.cls: Error: line 1 at 19-24: No matching method found for 'bar' on 'Foo' taking no arguments\n")
        org.flush()

        val org2 = createOrg(root.join("pkg2"))
        val pkg2 = org2.unmanaged
        assertIsSummaryDeclaration(pkg2, "Foo")
        assert(getMessages(org2) ==
          "/pkg2/Foo.cls: Error: line 1 at 19-24: No matching method found for 'bar' on 'Foo' taking no arguments\n")
    }
  }

  test("Label dependency is cached") {
    FileSystemHelper.run(
      Map(
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
        "Dummy.cls" -> "public class Dummy { {String a = System.label.TestLabel;} }")) { root: PathLike =>
      val org1 = createOrg(root)
      assert(org1.issues.isEmpty)
      org1.flush()

      val org2 = createOrg(root)
      val pkg2 = org2.unmanaged
      assert(org2.issues.isEmpty)

      assertIsSummaryDeclaration(pkg2, "Dummy")
      assert(pkg2
        .getDependencies(TypeIdentifier(None, TypeName(Name("Dummy"))), outerInheritanceOnly = false, apexOnly = false)
        .sameElements(Array(TypeIdentifier(None, TypeNames.Label))))

      root.createFile("CustomLabels.labels", "<CustomLabels xmlns=\"http://soap.sforce.com/2006/04/metadata\"/>")

      val org3 = createOrg(root)
      val pkg3 = org3.unmanaged
      assertIsFullDeclaration(pkg3, "Dummy")
      assert(getMessages(org3) ==
        "/Dummy.cls: Missing: line 1 at 33-55: Unknown field or type 'TestLabel' on 'System.Label'\n")
    }
  }

  test("Packaged label dependency is cached") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "pkg2",
            |"packageDirectories": [{"path": "pkg2"}],
            |"plugins": {"dependencies": [{"namespace": "pkg1", "path": "pkg1"}]}
            |}""".stripMargin,
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
        "pkg2/Dummy.cls" -> "public class Dummy { {String a = System.label.pkg1.TestLabel;} }")) { root: PathLike =>
      val org1 = createOrg(root)
      assert(org1.issues.isEmpty)
      org1.flush()

      val org2 = createOrg(root)
      val pkg22 = org2.packagesByNamespace(Some(Name("pkg2")))
      assert(org2.issues.isEmpty)

      assertIsSummaryDeclaration(pkg22, "Dummy", Some(Name("pkg2")))
      assert(
        pkg22
          .getDependencies(TypeIdentifier(Some(Name("pkg2")),
                                          TypeName(Name("Dummy"), Nil, Some(TypeName(Name("pkg2"))))),
                           outerInheritanceOnly = false,
                           apexOnly = false)
          .sameElements(Array(TypeIdentifier(Some(Name("pkg2")), TypeNames.Label))))

      root.createFile("pkg1/CustomLabels.labels", "<CustomLabels xmlns=\"http://soap.sforce.com/2006/04/metadata\"/>")

      val org3 = createOrg(root)
      val pkg32 = org3.packagesByNamespace(Some(Name("pkg2")))

      // Still summary as Dummy does not *directly* depend on pkg1 labels
      assertIsSummaryDeclaration(pkg32, "Dummy", Some(Name("pkg2")))
      assert(org3.issues.isEmpty)
    }
  }

  test("Multi-file label dependency is cached") {
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
        "AltLabels.labels" ->
          """<?xml version="1.0" encoding="UTF-8"?>
          |<CustomLabels xmlns="http://soap.sforce.com/2006/04/metadata">
          |    <labels>
          |        <fullName>TestLabel2</fullName>
          |        <protected>false</protected>
          |    </labels>
          |</CustomLabels>
          |""".stripMargin,
        "Dummy.cls" -> "public class Dummy { {String a = System.label.TestLabel2;} }")) { root: PathLike =>
      val org1 = createOrg(root)
      assert(org1.issues.isEmpty)
      org1.flush()

      val org2 = createOrg(root)
      val pkg2 = org2.unmanaged
      assert(org2.issues.isEmpty)

      assertIsSummaryDeclaration(pkg2, "Dummy")
      assert(pkg2
        .getDependencies(TypeIdentifier(None, TypeName(Name("Dummy"))), outerInheritanceOnly = false, apexOnly = false)
        .sameElements(Array(TypeIdentifier(None, TypeNames.Label))))

      root.createFile("AltLabels.labels", "<CustomLabels xmlns=\"http://soap.sforce.com/2006/04/metadata\"/>")

      val org3 = createOrg(root)
      val pkg3 = org3.unmanaged
      assertIsFullDeclaration(pkg3, "Dummy")
      assert(getMessages(org3) ==
        "/Dummy.cls: Missing: line 1 at 33-56: Unknown field or type 'TestLabel2' on 'System.Label'\n")
    }
  }

  test("Flow dependency is cached") {
    FileSystemHelper.run(Map(
      "Test.flow-meta.xml" -> "",
      "Dummy.cls" -> "public class Dummy { {Flow.Interview i = new Flow.Interview.Test(new Map<String, Object>());} }")) {
      root: PathLike =>
        val org1 = createOrg(root)
        assert(org1.issues.isEmpty)
        org1.flush()

        val org2 = createOrg(root)
        val pkg2 = org2.unmanaged
        assert(org2.issues.isEmpty)

        assertIsSummaryDeclaration(pkg2, "Dummy")
        assert(
          pkg2
            .getDependencies(TypeIdentifier(None, TypeName(Name("Dummy"))),
              outerInheritanceOnly = false,
              apexOnly = false)
            .sameElements(Array(TypeIdentifier(None, TypeNames.Interview))))

        root.join("Test.flow-meta.xml").delete()

        val org3 = createOrg(root)
        val pkg3 = org3.unmanaged
        assertIsFullDeclaration(pkg3, "Dummy")
        assert(getMessages(org3) ==
          "/Dummy.cls: Missing: line 1 at 45-64: No type declaration found for 'Flow.Interview.Test'\n")
    }
  }

  test("Packaged flow dependency is cached") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          |"namespace": "pkg2",
          |"packageDirectories": [{"path": "pkg2"}],
          |"plugins": {"dependencies": [{"namespace": "pkg1", "path": "pkg1"}]}
          |}""".stripMargin,
        "pkg1/Test.flow-meta.xml" -> "",
        "pkg2/Dummy.cls" -> "public class Dummy { {Flow.Interview i = new Flow.Interview.pkg1.Test(new Map<String, Object>());} }")) {
      root: PathLike =>
        val org1 = createOrg(root)
        assert(org1.issues.isEmpty)
        org1.flush()

        val org2 = createOrg(root)
        val pkg22 = org2.packagesByNamespace(Some(Name("pkg2")))
        assert(org2.issues.isEmpty)

        assertIsSummaryDeclaration(pkg22, "Dummy", Some(Name("pkg2")))
        assert(
          pkg22
            .getDependencies(TypeIdentifier(Some(Name("pkg2")),
                                            TypeName(Name("Dummy"), Nil, Some(TypeName(Name("pkg2"))))),
              outerInheritanceOnly = false,
              apexOnly = false)
            .sameElements(Array(TypeIdentifier(Some(Name("pkg1")), TypeNames.Interview))))

        root.join("pkg1/Test.flow-meta.xml").delete()

        val org3 = createOrg(root)
        val pkg32 = org3.packagesByNamespace(Some(Name("pkg2")))

        assertIsFullDeclaration(pkg32, "Dummy", Some(Name("pkg2")))
        assert(getMessages(org3) ==
          "/pkg2/Dummy.cls: Missing: line 1 at 45-69: No type declaration found for 'Flow.Interview.pkg1.Test'\n")
    }
  }

  test("Page dependency is cached") {
    FileSystemHelper.run(
      Map("TestPage.page" -> "<apex:page/>",
          "Dummy.cls" -> "public class Dummy { {PageReference a = Page.TestPage;} }")) { root: PathLike =>
      val org1 = createOrg(root)
      assert(org1.issues.isEmpty)
      org1.flush()

      val org2 = createOrg(root)
      val pkg2 = org2.unmanaged
      assert(org2.issues.isEmpty)

      assertIsSummaryDeclaration(pkg2, "Dummy")
      assert(pkg2
        .getDependencies(TypeIdentifier(None, TypeName(Name("Dummy"))), outerInheritanceOnly = false, apexOnly = false)
        .sameElements(Array(TypeIdentifier(None, TypeNames.Page))))

      root.join("TestPage.page").delete()

      val org3 = createOrg(root)
      val pkg3 = org3.unmanaged
      assertIsFullDeclaration(pkg3, "Dummy")
      assert(getMessages(org3) ==
        "/Dummy.cls: Missing: line 1 at 40-53: Unknown field or type 'TestPage' on 'Page'\n")
    }
  }

  test("Packaged page dependency is cached") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          |"namespace": "pkg2",
          |"packageDirectories": [{"path": "pkg2"}],
          |"plugins": {"dependencies": [{"namespace": "pkg1", "path": "pkg1"}]}
          |}""".stripMargin,
        "pkg1/TestPage.page" -> "<apex:page/>",
        "pkg2/Dummy.cls" -> "public class Dummy { {PageReference a = Page.pkg1__TestPage;} }")) { root: PathLike =>
      val org1 = createOrg(root)
      assert(org1.issues.isEmpty)
      org1.flush()

      val org2 = createOrg(root)
      val pkg22 = org2.packagesByNamespace(Some(Name("pkg2")))
      assert(org2.issues.isEmpty)

      assertIsSummaryDeclaration(pkg22, "Dummy", Some(Name("pkg2")))
      assert(
        pkg22
          .getDependencies(TypeIdentifier(Some(Name("pkg2")),
                                          TypeName(Name("Dummy"), Nil, Some(TypeName(Name("pkg2"))))),
                           outerInheritanceOnly = false,
                           apexOnly = false)
          .sameElements(Array(TypeIdentifier(Some(Name("pkg2")), TypeNames.Page))))

      root.join("pkg1/TestPage.page").delete()

      val org3 = createOrg(root)
      val pkg32 = org3.packagesByNamespace(Some(Name("pkg2")))

      // Still summary as Dummy does not *directly* depend on pkg1 pages
      assertIsSummaryDeclaration(pkg32, "Dummy", Some(Name("pkg2")))
      assert(org3.issues.isEmpty)
    }
  }

  test("Component dependency is cached") {
    FileSystemHelper.run(
      Map("Test.component" -> "<apex:component/>",
          "Dummy.cls" -> "public class Dummy { {Component.Test c = new Component.Test();} }")) { root: PathLike =>
      val org1 = createOrg(root)
      assert(org1.issues.isEmpty)
      org1.flush()

      val org2 = createOrg(root)
      val pkg2 = org2.unmanaged
      assert(org2.issues.isEmpty)

      assertIsSummaryDeclaration(pkg2, "Dummy")
      assert(pkg2
        .getDependencies(TypeIdentifier(None, TypeName(Name("Dummy"))), outerInheritanceOnly = false, apexOnly = false)
        .sameElements(Array(TypeIdentifier(None, TypeNames.Component))))

      root.join("Test.component").delete()

      val org3 = createOrg(root)
      val pkg3 = org3.unmanaged
      assertIsFullDeclaration(pkg3, "Dummy")
      assert(getMessages(org3) ==
        "/Dummy.cls: Missing: line 1 at 37-61: No type declaration found for 'Component.Test'\n" +
          "/Dummy.cls: Missing: line 1 at 45-59: No type declaration found for 'Component.Test'\n")
    }
  }

  test("Packaged component dependency is cached") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "pkg2",
            |"packageDirectories": [{"path": "pkg2"}],
            |"plugins": {"dependencies": [{"namespace": "pkg1", "path": "pkg1"}]}
            |}""".stripMargin,
        "pkg1/Test.component" -> "<apex:component/>",
        "pkg2/Dummy.cls" -> "public class Dummy { {Component.pkg1.Test c = new Component.pkg1.Test();} }")) { root: PathLike =>
      val org1 = createOrg(root)
      assert(org1.issues.isEmpty)
      org1.flush()

      val org2 = createOrg(root)
      val pkg22 = org2.packagesByNamespace(Some(Name("pkg2")))
      assert(org2.issues.isEmpty)

      assertIsSummaryDeclaration(pkg22, "Dummy", Some(Name("pkg2")))
      assert(
        pkg22
          .getDependencies(TypeIdentifier(Some(Name("pkg2")),
                                          TypeName(Name("Dummy"), Nil, Some(TypeName(Name("pkg2"))))),
            outerInheritanceOnly = false,
            apexOnly = false)
          .sameElements(Array(TypeIdentifier(Some(Name("pkg1")), TypeNames.Component))))

      root.join("pkg1/Test.component").delete()

      val org3 = createOrg(root)
      val pkg32 = org3.packagesByNamespace(Some(Name("pkg2")))

      assertIsFullDeclaration(pkg32, "Dummy", Some(Name("pkg2")))
      assert(getMessages(org3) ==
        "/pkg2/Dummy.cls: Missing: line 1 at 42-71: No type declaration found for 'Component.pkg1.Test'\n" +
          "/pkg2/Dummy.cls: Missing: line 1 at 50-69: No type declaration found for 'Component.pkg1.Test'\n")
    }
  }

  test("Cached abstract method used (bug)") {
    FileSystemHelper.run(
      Map(
        "MyAbstract.cls" ->
          """public abstract class MyAbstract {
            |  {myMethod();}
            |  public abstract void myMethod();
            |}""".stripMargin,
        "MyConcrete.cls" ->
          """public class MyConcrete extends MyAbstract {
            |  public override void myMethod() {}
            |}""".stripMargin)) { root: PathLike =>
      val org1 = createOrg(root)
      val pkg1 = org1.unmanaged
      assert(org1.issues.isEmpty)
      org1.flush()

      val org2 = createOrg(root)
      val pkg2 = org2.unmanaged

      {
        assert(org2.issues.isEmpty)
        val options = new IssueOptions()
        options.includeZombies = true
        assert(getMessages(org2) == "")
      }

      pkg2.refresh(root.join("MyConcrete.cls"))

      {
        assert(org2.issues.isEmpty)
        val options = new IssueOptions()
        options.includeZombies = true
        assert(getMessages(org2) == "")
      }
    }
  }
}
