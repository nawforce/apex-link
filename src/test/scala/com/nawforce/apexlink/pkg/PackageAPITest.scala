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

import com.nawforce.apexlink.types.apex.SummaryDeclaration
import com.nawforce.apexlink.{FileSystemHelper, TestHelper}
import com.nawforce.pkgforce.diagnostics.{Location, PathLocation}
import com.nawforce.pkgforce.documents.ParsedCache
import com.nawforce.pkgforce.names.Name
import com.nawforce.pkgforce.path.PathLike
import org.scalatest.funsuite.AnyFunSuite

class PackageAPITest extends AnyFunSuite with TestHelper {

  test("type of path") {
    FileSystemHelper.run(
      Map("classes/Dummy.cls" -> "public class Dummy {}",
          "triggers/Foo.trigger" -> "trigger Foo on Account (before insert) {}")) {
      root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(!org.issues.hasMessages)

        assert(pkg.getTypeOfPath(null) == null)
        assert(pkg.getTypeOfPath("") == null)

        assert(
          pkg.getTypeOfPathInternal(root.join("classes").join("Dummy.cls")).get.toString == "Dummy")
        assert(pkg.getTypeOfPathInternal(root.join("classes").join("Dummy2.cls")).isEmpty)
        assert(pkg.getTypeOfPathInternal(root.join("classes").join("Dummy.object")).isEmpty)
        assert(pkg.getTypeOfPathInternal(root.join("classes2").join("Dummy.cls")).isEmpty)

        assert(
          pkg
            .getTypeOfPathInternal(root.join("triggers").join("Foo.trigger"))
            .get
            .toString == "__sfdc_trigger/Foo")
        assert(pkg.getTypeOfPathInternal(root.join("triggers").join("Foo2.trigger")).isEmpty)
        assert(pkg.getTypeOfPathInternal(root.join("triggers").join("Foo.object")).isEmpty)
        assert(pkg.getTypeOfPathInternal(root.join("triggers2").join("Foo2.trigger")).isEmpty)
    }
  }

  test("type of path with namespace") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          |"namespace": "test",
          |"packageDirectories": [{"path": "pkg"}]
          |}""".stripMargin,
        "pkg/classes/Dummy.cls" -> "public class Dummy {}",
        "pkg/triggers/Foo.trigger" -> "trigger Foo on Account (before insert) {}")) {
      root: PathLike =>
        val org = createOrg(root)
        val pkg = org.packagesByNamespace(Some(Name("test")))
        assert(!org.issues.hasMessages)

        assert(pkg.getTypeOfPath(null) == null)
        assert(pkg.getTypeOfPath("") == null)

        assert(
          pkg
            .getTypeOfPathInternal(root.join("pkg").join("classes").join("Dummy.cls"))
            .get
            .toString == "test.Dummy (test)")
        assert(pkg.getTypeOfPathInternal(root.join("classes").join("Dummy2.cls")).isEmpty)
        assert(pkg.getTypeOfPathInternal(root.join("classes").join("Dummy.object")).isEmpty)
        assert(pkg.getTypeOfPathInternal(root.join("classes2").join("Dummy.cls")).isEmpty)

        assert(
          pkg
            .getTypeOfPathInternal(root.join("pkg").join("triggers").join("Foo.trigger"))
            .get
            .toString == "__sfdc_trigger/test/Foo (test)")
        assert(pkg.getTypeOfPathInternal(root.join("triggers").join("Foo2.trigger")).isEmpty)
        assert(pkg.getTypeOfPathInternal(root.join("triggers").join("Foo.object")).isEmpty)
        assert(pkg.getTypeOfPathInternal(root.join("triggers2").join("Foo2.trigger")).isEmpty)
    }
  }

  test("path of type") {
    FileSystemHelper.run(
      Map("classes/Dummy.cls" -> "public class Dummy {}",
          "triggers/Foo.trigger" -> "trigger Foo on Account (before insert) {}")) {
      root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(!org.issues.hasMessages)

        val dummyType =
          pkg.getTypeOfPathInternal(root.join("classes").join("Dummy.cls")).get.asTypeIdentifier
        val fooType =
          pkg.getTypeOfPathInternal(root.join("triggers").join("Foo.trigger")).get.asTypeIdentifier

        assert(pkg.getPathsOfType(null).isEmpty)

        assert(dummyType.toString == "Dummy")
        assert(pkg.getPathsOfType(dummyType).sameElements(Array("/classes/Dummy.cls")))

        assert(fooType.toString == "__sfdc_trigger/Foo")
        assert(pkg.getPathsOfType(fooType).sameElements(Array("/triggers/Foo.trigger")))
    }
  }

  test("path of type with namespace") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          |"namespace": "test",
          |"packageDirectories": [{"path": "pkg"}]
          |}""".stripMargin,
        "pkg/classes/Dummy.cls" -> "public class Dummy {}",
        "pkg/triggers/Foo.trigger" -> "trigger Foo on Account (before insert) {}")) {
      root: PathLike =>
        val org = createOrg(root)
        val pkg = org.packagesByNamespace(Some(Name("test")))
        assert(!org.issues.hasMessages)

        val dummyType =
          pkg
            .getTypeOfPathInternal(root.join("pkg").join("classes").join("Dummy.cls"))
            .get
            .asTypeIdentifier
        val fooType =
          pkg
            .getTypeOfPathInternal(root.join("pkg").join("triggers").join("Foo.trigger"))
            .get
            .asTypeIdentifier

        assert(pkg.getPathsOfType(null).isEmpty)

        assert(dummyType.toString == "test.Dummy (test)")
        assert(pkg.getPathsOfType(dummyType).sameElements(Array("/pkg/classes/Dummy.cls")))

        assert(fooType.toString == "__sfdc_trigger/test/Foo (test)")
        assert(pkg.getPathsOfType(fooType).sameElements(Array("/pkg/triggers/Foo.trigger")))
    }
  }

  test("summary of type") {
    FileSystemHelper.run(Map("classes/Dummy.cls" -> "public class Dummy {}")) { root: PathLike =>
      val org = createOrg(root)
      val pkg = org.unmanaged
      assert(!org.issues.hasMessages)

      val typeLike =
        pkg.getTypeOfPathInternal(root.join("classes").join("Dummy.cls")).get.asTypeIdentifier
      val summary = pkg.getSummaryOfType(typeLike)

      assert(summary.name == "Dummy")
      assert(summary.typeName.toString == "Dummy")
      assert(summary.idRange.contains(Location(1, 13, 1, 18)))
      assert(summary.modifiers sameElements Seq("public"))
    }
  }

  test("summary of type with namespace") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
        |"namespace": "test",
        |"packageDirectories": [{"path": "pkg"}]
        |}""".stripMargin,
        "pkg/classes/Dummy.cls" -> "@isTest puBlic class Dummy {}")) { root: PathLike =>
      val org = createOrg(root)
      val pkg = org.packagesByNamespace(Some(Name("test")))
      assert(!org.issues.hasMessages)

      val typeLike =
        pkg
          .getTypeOfPathInternal(root.join("pkg").join("classes").join("Dummy.cls"))
          .get
          .asTypeIdentifier
      val summary = pkg.getSummaryOfType(typeLike)

      assert(summary.name == "Dummy")
      assert(summary.typeName.toString == "test.Dummy")
      assert(summary.idRange.contains(Location(1, 21, 1, 26)))
      assert(summary.modifiers sameElements Array("@IsTest", "public"))
    }
  }

  test("summary of type with namespace (cached)") {
    ParsedCache.clear()
    withManualFlush {

      FileSystemHelper.run(
        Map("sfdx-project.json" ->
              """{
          |"namespace": "test",
          |"packageDirectories": [{"path": "pkg"}]
          |}""".stripMargin,
            "pkg/classes/Dummy.cls" -> "@isTest puBlic class Dummy {}")) { root: PathLike =>
        val org = createOrg(root)
        assert(!org.issues.hasMessages)
        org.flush()

        val org2 = createOrg(root)
        val pkg2 = org2.packagesByNamespace(Some(Name("test")))
        assert(!org2.issues.hasMessages)

        val typeLike =
          pkg2
            .getTypeOfPathInternal(root.join("pkg").join("classes").join("Dummy.cls"))
            .get
            .asTypeIdentifier
        val summary = pkg2.getSummaryOfType(typeLike)

        assert(
          pkg2.orderedModules.head
            .findModuleType(typeLike.typeName)
            .get
            .isInstanceOf[SummaryDeclaration])
        assert(summary.name == "Dummy")
        assert(summary.typeName.toString == "test.Dummy")
        assert(summary.idRange.contains(Location(1, 21, 1, 26)))
        assert(summary.modifiers sameElements Array("@IsTest", "public"))

      }
    }
  }

  test("summary of trigger") {
    FileSystemHelper.run(
      Map("triggers/Dummy.trigger" -> "trigger Dummy on Account (before insert) {}")) {
      root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(!org.issues.hasMessages)

        val typeLike = pkg
          .getTypeOfPathInternal(root.join("triggers").join("Dummy.trigger"))
          .get
          .asTypeIdentifier
        val summary = pkg.getSummaryOfType(typeLike)

        assert(summary.name == "__sfdc_trigger/Dummy")
        assert(summary.typeName.toString == "__sfdc_trigger/Dummy")
        assert(summary.idRange.contains(Location(1, 8, 1, 13)))
        assert(summary.modifiers.isEmpty)
    }
  }

  test("summary of trigger with namespace") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "test",
            |"packageDirectories": [{"path": "pkg"}]
            |}""".stripMargin,
        "pkg/triggers/Dummy.trigger" -> "trigger Dummy on Account (before insert) {}")) {
      root: PathLike =>
        val org = createOrg(root)
        val pkg = org.packagesByNamespace(Some(Name("test")))
        assert(!org.issues.hasMessages)

        val typeLike = pkg
          .getTypeOfPathInternal(root.join("pkg").join("triggers").join("Dummy.trigger"))
          .get
          .asTypeIdentifier
        val summary = pkg.getSummaryOfType(typeLike)

        assert(summary.name == "__sfdc_trigger/test/Dummy")
        assert(summary.typeName.toString == "__sfdc_trigger/test/Dummy")
        assert(summary.idRange.contains(Location(1, 8, 1, 13)))
        assert(summary.modifiers.isEmpty)
    }
  }

  test("No dependencies") {
    FileSystemHelper.run(Map("classes/Foo.cls" -> "public class Foo {}")) { root: PathLike =>
      val org = createOrg(root)
      val pkg = org.unmanaged
      assert(!org.issues.hasMessages)

      val fooTypeLike =
        pkg.getTypeOfPathInternal(root.join("classes").join("Foo.cls")).get.asTypeIdentifier

      assert(pkg.getDependencyHolders(fooTypeLike).isEmpty)
    }
  }

  def fooHoldsBarCached(files: Map[String, String], outerInheritanceOnly: Boolean = false): Unit = {
    ParsedCache.clear()
    withManualFlush {
      FileSystemHelper.run(files) { root: PathLike => // Basic non-cached test
      {

        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(!org.issues.hasMessages)

        val fooTypeLike =
          pkg.getTypeOfPathInternal(root.join("classes").join("Foo.cls")).get.asTypeIdentifier
        val barTypeLike =
          pkg.getTypeOfPathInternal(root.join("classes").join("Bar.cls")).get.asTypeIdentifier

        assert(pkg.getDependencyHolders(fooTypeLike).sameElements(Array(barTypeLike)))
        assert(pkg.getDependencyHolders(barTypeLike).isEmpty)

        if (outerInheritanceOnly) {
          assert(
            pkg
              .getDependencies(barTypeLike, outerInheritanceOnly = true)
              .sameElements(Array(fooTypeLike)))
        } else {
          assert(pkg.getDependencies(barTypeLike, outerInheritanceOnly = true).isEmpty)
        }
        assert(
          pkg
            .getDependencies(barTypeLike, outerInheritanceOnly = false)
            .sameElements(Array(fooTypeLike)))
        assert(pkg.getDependencies(fooTypeLike, outerInheritanceOnly = false).isEmpty)

        org.flush()
      }

      // Extended cache test
      {
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(!org.issues.hasMessages)

        val fooTypeLike =
          pkg.getTypeOfPathInternal(root.join("classes").join("Foo.cls")).get.asTypeIdentifier
        val barTypeLike =
          pkg.getTypeOfPathInternal(root.join("classes").join("Bar.cls")).get.asTypeIdentifier

        assert(
          pkg.orderedModules.head
            .findModuleType(fooTypeLike.typeName)
            .get
            .isInstanceOf[SummaryDeclaration])
        assert(
          pkg.orderedModules.head
            .findModuleType(barTypeLike.typeName)
            .get
            .isInstanceOf[SummaryDeclaration])

        assert(pkg.getDependencyHolders(fooTypeLike).sameElements(Array(barTypeLike)))
        assert(pkg.getDependencyHolders(barTypeLike).isEmpty)

        if (outerInheritanceOnly) {
          assert(
            pkg
              .getDependencies(barTypeLike, outerInheritanceOnly = true)
              .sameElements(Array(fooTypeLike)))
        } else {
          assert(pkg.getDependencies(barTypeLike, outerInheritanceOnly = true).isEmpty)
        }
        assert(
          pkg
            .getDependencies(barTypeLike, outerInheritanceOnly = false)
            .sameElements(Array(fooTypeLike)))
        assert(pkg.getDependencies(fooTypeLike, outerInheritanceOnly = false).isEmpty)
      }
      }
    }
  }

  test("Superclass dependency") {
    fooHoldsBarCached(Map("classes/Foo.cls" -> "public virtual class Foo {}",
                          "classes/Bar.cls" -> "public class Bar extends Foo {}"),
                      outerInheritanceOnly = true)
  }

  test("Interface dependency") {
    fooHoldsBarCached(Map("classes/Foo.cls" -> "public interface Foo {}",
                          "classes/Bar.cls" -> "public class Bar implements Foo {}"),
                      outerInheritanceOnly = true)
  }

  test("Block dependency") {
    fooHoldsBarCached(
      Map("classes/Foo.cls" -> "public class Foo {}",
          "classes/Bar.cls" -> "public class Bar {{Object a = new Foo();}}"))
  }

  test("Field type dependency") {
    fooHoldsBarCached(
      Map("classes/Foo.cls" -> "public class Foo {}",
          "classes/Bar.cls" -> "public class Bar {Foo a;}"))
  }

  test("Field expression dependency") {
    fooHoldsBarCached(
      Map("classes/Foo.cls" -> "public class Foo {}",
          "classes/Bar.cls" -> "public class Bar {Object a = new Foo();}"))
  }

  test("Method type dependency") {
    fooHoldsBarCached(
      Map("classes/Foo.cls" -> "public class Foo {}",
          "classes/Bar.cls" -> "public class Bar {Foo func(){return null;} }"))
  }

  test("Method argument dependency") {
    fooHoldsBarCached(
      Map("classes/Foo.cls" -> "public class Foo {}",
          "classes/Bar.cls" -> "public class Bar {Object func(Foo a){return null;} }"))
  }

  test("Method body dependency") {
    fooHoldsBarCached(
      Map("classes/Foo.cls" -> "public class Foo {}",
          "classes/Bar.cls" -> "public class Bar {Object func(){return new Foo();} }"))
  }

  test("Constructor argument dependency") {
    fooHoldsBarCached(
      Map("classes/Foo.cls" -> "public class Foo {}",
          "classes/Bar.cls" -> "public class Bar { Bar(Foo a){} }"))
  }

  test("Constructor body dependency") {
    fooHoldsBarCached(
      Map("classes/Foo.cls" -> "public class Foo {}",
          "classes/Bar.cls" -> "public class Bar { Bar(){Object a = new Foo();} }"))
  }

  test("Superclass dependency (nested)") {
    fooHoldsBarCached(
      Map("classes/Foo.cls" -> "public class Foo {public virtual class Baz {}}",
          "classes/Bar.cls" -> "public class Bar extends Foo.Baz {}"),
      outerInheritanceOnly = true)
  }

  test("Interface dependency (nested)") {
    fooHoldsBarCached(
      Map("classes/Foo.cls" -> "public class Foo {public interface Baz {}}",
          "classes/Bar.cls" -> "public class Bar implements Foo.Baz {}"),
      outerInheritanceOnly = true)
  }

  test("Block dependency (nested)") {
    fooHoldsBarCached(
      Map("classes/Foo.cls" -> "public class Foo {public class Baz {}}",
          "classes/Bar.cls" -> "public class Bar {{Object a = new Foo.Baz();}}"))
  }

  test("Field type dependency (nested)") {
    fooHoldsBarCached(
      Map("classes/Foo.cls" -> "public class Foo {public class Baz {}}",
          "classes/Bar.cls" -> "public class Bar {Foo.Baz a;}"))
  }

  test("Field expression dependency (nested)") {
    fooHoldsBarCached(
      Map("classes/Foo.cls" -> "public class Foo {public class Baz {}}",
          "classes/Bar.cls" -> "public class Bar {Object a = new Foo.Baz();}"))
  }

  test("Method type dependency (nested)") {
    fooHoldsBarCached(
      Map("classes/Foo.cls" -> "public class Foo {public class Baz {}}",
          "classes/Bar.cls" -> "public class Bar {Foo.Baz func(){return null;} }"))
  }

  test("Method argument dependency (nested)") {
    fooHoldsBarCached(
      Map("classes/Foo.cls" -> "public class Foo {public class Baz {}}",
          "classes/Bar.cls" -> "public class Bar {Object func(Foo.Baz a){return null;} }"))
  }

  test("Method body dependency (nested)") {
    fooHoldsBarCached(
      Map("classes/Foo.cls" -> "public class Foo {public class Baz {}}",
          "classes/Bar.cls" -> "public class Bar {Object func(){return new Foo.Baz();} }"))
  }

  test("Constructor argument dependency (nested)") {
    fooHoldsBarCached(
      Map("classes/Foo.cls" -> "public class Foo {public class Baz {}}",
          "classes/Bar.cls" -> "public class Bar { Bar(Foo.Baz a){} }"))
  }

  test("Constructor body dependency (nested)") {
    fooHoldsBarCached(
      Map("classes/Foo.cls" -> "public class Foo {public class Baz {}}",
          "classes/Bar.cls" -> "public class Bar { Bar(){Object a = new Foo.Baz();} }"))
  }

  test("Superclass dependency (from nested)") {
    fooHoldsBarCached(
      Map("classes/Foo.cls" -> "public virtual class Foo {}",
          "classes/Bar.cls" -> "public class Bar {public class Baz extends Foo {}}"))
  }

  test("Interface dependency (from nested)") {
    fooHoldsBarCached(
      Map("classes/Foo.cls" -> "public interface Foo {}",
          "classes/Bar.cls" -> "public class Bar {public class Baz implements Foo {}}"))
  }

  test("Block dependency (from nested)") {
    fooHoldsBarCached(
      Map("classes/Foo.cls" -> "public class Foo {}",
          "classes/Bar.cls" -> "public class Bar {public class Baz { {Object a = new Foo();} }}"))
  }

  test("Field type dependency (from nested)") {
    fooHoldsBarCached(
      Map("classes/Foo.cls" -> "public class Foo {}",
          "classes/Bar.cls" -> "public class Bar {public class Baz {Foo a;} }"))
  }

  test("Field expression dependency (from nested)") {
    fooHoldsBarCached(
      Map("classes/Foo.cls" -> "public class Foo {}",
          "classes/Bar.cls" -> "public class Bar {public class Baz {Object a = new Foo();} }"))
  }

  test("Method type dependency (from nested)") {
    fooHoldsBarCached(
      Map("classes/Foo.cls" -> "public class Foo {}",
          "classes/Bar.cls" -> "public class Bar {public class Baz {Foo func(){return null;}} }"))
  }

  test("Method argument dependency (from nested)") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {public class Baz {Object func(Foo a){return null;} }}"))
  }

  test("Method body dependency (from nested)") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {public class Baz {Object func(){return new Foo();} }}"))
  }

  test("Constructor argument dependency (from nested)") {
    fooHoldsBarCached(
      Map("classes/Foo.cls" -> "public class Foo {}",
          "classes/Bar.cls" -> "public class Bar {public class Baz { Bar(Foo a){} }}"))
  }

  test("Constructor body dependency (from nested)") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {public class Baz { Bar(){Object a = new Foo();} }}"))
  }

  test("Unmanaged to Managed Dependency") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          |"namespace": "pkg2",
          |"packageDirectories": [{"path": "pkg2"}],
          |"plugins": {"dependencies": [{"namespace": "pkg1", "path": "pkg1"}]}
          |}""".stripMargin,
        "pkg1/Foo.cls" -> "global virtual class Foo {}",
        "pkg2/Bar.cls" -> "public class Bar extends pkg1.Foo {}")) { root: PathLike =>
      val org = createOrg(root)
      val pkg1 = org.packagesByNamespace(Some(Name("pkg1")))
      val pkg2 = org.packagesByNamespace(Some(Name("pkg2")))
      assert(!org.issues.hasMessages)

      val fooTypeLike =
        pkg1.getTypeOfPathInternal(root.join("pkg1").join("Foo.cls")).get.asTypeIdentifier
      val barTypeLike =
        pkg2.getTypeOfPathInternal(root.join("pkg2").join("Bar.cls")).get.asTypeIdentifier

      assert(pkg1.getDependencyHolders(fooTypeLike).sameElements(Array(barTypeLike)))
      assert(pkg2.getDependencyHolders(barTypeLike).isEmpty)
    }
  }

  test("Unmanaged to Managed Dependency (cached)") {
    ParsedCache.clear()
    withManualFlush {

      FileSystemHelper.run(
        Map("sfdx-project.json" ->
              """{
              |"namespace": "pkg2",
              |"packageDirectories": [{"path": "pkg2"}],
              |"plugins": {"dependencies": [{"namespace": "pkg1", "path": "pkg1"}]}
              |}""".stripMargin,
            "pkg1/Foo.cls" -> "global virtual class Foo {}",
            "pkg2/Bar.cls" -> "public class Bar extends pkg1.Foo {}")) { root: PathLike =>
        val org = createOrg(root)
        assert(!org.issues.hasMessages)
        org.flush()

        val org2 = createOrg(root)
        val pkg21 = org2.packagesByNamespace(Some(Name("pkg1")))
        val pkg22 = org2.packagesByNamespace(Some(Name("pkg2")))
        assert(!org2.issues.hasMessages)

        val fooTypeLike =
          pkg21.getTypeOfPathInternal(root.join("pkg1").join("Foo.cls")).get.asTypeIdentifier
        val barTypeLike =
          pkg22.getTypeOfPathInternal(root.join("pkg2").join("Bar.cls")).get.asTypeIdentifier

        assert(
          pkg21.orderedModules.head
            .findModuleType(fooTypeLike.typeName)
            .get
            .isInstanceOf[SummaryDeclaration])
        assert(
          pkg22.orderedModules.head
            .findModuleType(barTypeLike.typeName)
            .get
            .isInstanceOf[SummaryDeclaration])

        assert(pkg21.getDependencyHolders(fooTypeLike).sameElements(Array(barTypeLike)))
        assert(pkg22.getDependencyHolders(barTypeLike).isEmpty)
      }
    }
  }

  test("Managed to Managed Dependency") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          |"namespace": "pkg2",
          |"packageDirectories": [{"path": "pkg2"}],
          |"plugins": {"dependencies": [{"namespace": "pkg1", "path": "pkg1"}]}
          |}""".stripMargin,
        "pkg1/Foo.cls" -> "global virtual class Foo {}",
        "pkg2/Bar.cls" -> "public class Bar extends pkg1.Foo {}")) { root: PathLike =>
      val org = createOrg(root)
      val pkg1 = org.packagesByNamespace(Some(Name("pkg1")))
      val pkg2 = org.packagesByNamespace(Some(Name("pkg2")))
      assert(!org.issues.hasMessages)

      val fooTypeLike =
        pkg1.getTypeOfPathInternal(root.join("pkg1").join("Foo.cls")).get.asTypeIdentifier
      val barTypeLike =
        pkg2.getTypeOfPathInternal(root.join("pkg2").join("Bar.cls")).get.asTypeIdentifier

      assert(pkg1.getDependencyHolders(fooTypeLike).sameElements(Array(barTypeLike)))
      assert(pkg2.getDependencyHolders(barTypeLike).isEmpty)
    }
  }

  test("Managed to Managed Dependency (cached)") {
    ParsedCache.clear()

    withManualFlush {
      FileSystemHelper.run(
        Map("sfdx-project.json" ->
              """{
              |"namespace": "pkg2",
              |"packageDirectories": [{"path": "pkg2"}],
              |"plugins": {"dependencies": [{"namespace": "pkg1", "path": "pkg1"}]}
              |}""".stripMargin,
            "pkg1/Foo.cls" -> "global virtual class Foo {}",
            "pkg2/Bar.cls" -> "public class Bar extends pkg1.Foo {}")) { root: PathLike =>
        val org = createOrg(root)
        assert(!org.issues.hasMessages)
        org.flush()

        val org2 = createOrg(root)
        val pkg21 = org2.packagesByNamespace(Some(Name("pkg1")))
        val pkg22 = org2.packagesByNamespace(Some(Name("pkg2")))
        assert(!org2.issues.hasMessages)

        val fooTypeLike =
          pkg21.getTypeOfPathInternal(root.join("pkg1").join("Foo.cls")).get.asTypeIdentifier
        val barTypeLike =
          pkg22.getTypeOfPathInternal(root.join("pkg2").join("Bar.cls")).get.asTypeIdentifier

        assert(
          pkg21.orderedModules.head
            .findModuleType(fooTypeLike.typeName)
            .get
            .isInstanceOf[SummaryDeclaration])
        assert(
          pkg22.orderedModules.head
            .findModuleType(barTypeLike.typeName)
            .get
            .isInstanceOf[SummaryDeclaration])

        assert(pkg21.getDependencyHolders(fooTypeLike).sameElements(Array(barTypeLike)))
        assert(pkg22.getDependencyHolders(barTypeLike).isEmpty)
      }
    }
  }

  test("Trigger with no block") {
    FileSystemHelper.run(Map("triggers/Foo.trigger" -> "trigger Foo on Account (before insert) {}")) {
      root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(!org.issues.hasMessages)

        val fooTypeLike =
          pkg.getTypeOfPathInternal(root.join("triggers").join("Foo.trigger")).get.asTypeIdentifier

        assert(pkg.getDependencies(fooTypeLike, outerInheritanceOnly = false).isEmpty)
    }
  }

  test("Trigger with block") {
    FileSystemHelper.run(
      Map("classes/Bar.cls" -> "public class Bar {}",
          "triggers/Foo.trigger" -> "trigger Foo on Account (before insert) {Bar b;}")) {
      root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(!org.issues.hasMessages)

        val barTypeLike =
          pkg.getTypeOfPathInternal(root.join("classes").join("Bar.cls")).get.asTypeIdentifier
        val fooTypeLike =
          pkg.getTypeOfPathInternal(root.join("triggers").join("Foo.trigger")).get.asTypeIdentifier

        assert(
          pkg
            .getDependencies(fooTypeLike, outerInheritanceOnly = false)
            .sameElements(Array(barTypeLike)))
    }
  }

  test("Trigger with block with namespace") {
    FileSystemHelper.run(
      Map("classes/Bar.cls" -> "public class Bar {}",
          "triggers/Foo.trigger" -> "trigger Foo on Account (before insert) {Bar b;}")) {
      root: PathLike =>
        val org = createOrg(root)
        val pkg = org.unmanaged
        assert(!org.issues.hasMessages)

        val barTypeLike =
          pkg.getTypeOfPathInternal(root.join("classes").join("Bar.cls")).get.asTypeIdentifier
        val fooTypeLike =
          pkg.getTypeOfPathInternal(root.join("triggers").join("Foo.trigger")).get.asTypeIdentifier

        assert(
          pkg
            .getDependencies(fooTypeLike, outerInheritanceOnly = false)
            .sameElements(Array(barTypeLike)))
    }
  }

  test("location of type") {
    FileSystemHelper.run(Map("classes/Dummy.cls" -> "public class Dummy {}")) { root: PathLike =>
      val org = createOrg(root)
      assert(!org.issues.hasMessages)

      assert(
        org.getIdentifierLocation("Dummy") ==
          PathLocation("/classes/Dummy.cls", Location(1, 13, 1, 18)))
    }
  }

  test("location of type (packaged)") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          |"namespace": "test",
          |"packageDirectories": [{"path": "test"}]
          |}""".stripMargin,
        "test/Dummy.cls" -> "public class Dummy {}")) { root: PathLike =>
      val org = createOrg(root)
      assert(!org.issues.hasMessages)

      assert(org.getIdentifierLocation("Dummy") == null)
      assert(
        org.getIdentifierLocation("test.Dummy") ==
          PathLocation("/test/Dummy.cls", Location(1, 13, 1, 18)))
    }
  }

  test("location of type (packaged & nested)") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          |"namespace": "test",
          |"packageDirectories": [{"path": "test"}]
          |}""".stripMargin,
        "test/Dummy.cls" -> "public class Dummy {class Inner {}}")) { root: PathLike =>
      val org = createOrg(root)
      assert(!org.issues.hasMessages)

      assert(org.getIdentifierLocation("Dummy") == null)
      assert(org.getIdentifierLocation("Dummy.Inner") == null)
      assert(
        org.getIdentifierLocation("test.Dummy.Inner") ==
          PathLocation("/test/Dummy.cls", Location(1, 26, 1, 31)))
    }
  }

  test("location of trigger") {
    FileSystemHelper.run(Map("triggers/Foo.trigger" -> "trigger Foo on Account (before insert) {}")) {
      root: PathLike =>
        val org = createOrg(root)
        assert(!org.issues.hasMessages)

        assert(org.getIdentifierLocation("Foo") == null)
        assert(
          org.getIdentifierLocation("__sfdc_trigger/Foo") ==
            PathLocation("/triggers/Foo.trigger", Location(1, 8, 1, 11)))
    }
  }

  test("location of trigger (packaged)") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          |"namespace": "test",
          |"packageDirectories": [{"path": "test"}]
          |}""".stripMargin,
        "test/Foo.trigger" -> "trigger Foo on Account (before insert) {}")) { root: PathLike =>
      val org = createOrg(root)
      assert(!org.issues.hasMessages)

      assert(org.getIdentifierLocation("Foo") == null)
      assert(org.getIdentifierLocation("__sfdc_trigger/Foo") == null)
      assert(
        org.getIdentifierLocation("__sfdc_trigger/test/Foo") ==
          PathLocation("/test/Foo.trigger", Location(1, 8, 1, 11)))
    }
  }
}
