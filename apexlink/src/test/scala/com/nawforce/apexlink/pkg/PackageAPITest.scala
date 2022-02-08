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

import com.nawforce.apexlink.rpc.BombScore
import com.nawforce.apexlink.types.apex.SummaryDeclaration
import com.nawforce.apexlink.{FileSystemHelper, TestHelper}
import com.nawforce.pkgforce.documents.ParsedCache
import com.nawforce.pkgforce.modifiers.{ISTEST_ANNOTATION, PUBLIC_MODIFIER}
import com.nawforce.pkgforce.names.{Name, TypeIdentifier, TypeName}
import com.nawforce.pkgforce.path.{Location, PathLike, PathLocation}
import com.nawforce.runtime.platform.Path
import org.scalatest.funsuite.AnyFunSuite

import scala.collection.immutable.ArraySeq

class PackageAPITest extends AnyFunSuite with TestHelper {

  test("Is package file") {
    FileSystemHelper.run(Map("pkg/Dummy.cls" -> "public class Dummy {}")) { root: PathLike =>
      val org    = createOrg(root.join("pkg"))
      val pkg    = org.unmanaged
      val pkgDir = root.join("pkg")
      assert(pkg.isPackagePathInternal(pkgDir.join("Dummy.cls")))
      assert(pkg.isPackagePathInternal(pkgDir.join("something/Dummy.cls")))
      assert(pkg.isPackagePathInternal(pkgDir.join("Dummy2.cls")))
      assert(pkg.isPackagePathInternal(pkgDir.join("Dummy2.labels")))
      assert(pkg.isPackagePathInternal(pkgDir.join("Dummy2.labels-meta.xml")))
      assert(!pkg.isPackagePathInternal(pkgDir.join("Dummy.cls2")))
      assert(!pkg.isPackagePathInternal(root.join("Dummy.cls")))
    }
  }

  test("type of path") {
    FileSystemHelper.run(
      Map(
        "classes/Dummy.cls"    -> "public class Dummy {}",
        "triggers/Foo.trigger" -> "trigger Foo on Account (before insert) {}"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      val pkg = org.unmanaged
      assert(org.issues.isEmpty)

      assert(pkg.getTypeOfPath(null) == null)
      assert(pkg.getTypeOfPath("") == null)

      assert(
        pkg.getTypeOfPathInternal(root.join("classes").join("Dummy.cls")).get.toString == "Dummy"
      )
      assert(pkg.getTypeOfPathInternal(root.join("classes").join("Dummy2.cls")).isEmpty)
      assert(pkg.getTypeOfPathInternal(root.join("classes").join("Dummy.object")).isEmpty)
      assert(pkg.getTypeOfPathInternal(root.join("classes2").join("Dummy.cls")).isEmpty)

      assert(
        pkg
          .getTypeOfPathInternal(root.join("triggers").join("Foo.trigger"))
          .get
          .toString == "__sfdc_trigger/Foo"
      )
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
        "pkg/classes/Dummy.cls"    -> "public class Dummy {}",
        "pkg/triggers/Foo.trigger" -> "trigger Foo on Account (before insert) {}"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      val pkg = org.packagesByNamespace(Some(Name("test")))
      assert(org.issues.isEmpty)

      assert(pkg.getTypeOfPath(null) == null)
      assert(pkg.getTypeOfPath("") == null)

      assert(
        pkg
          .getTypeOfPathInternal(root.join("pkg").join("classes").join("Dummy.cls"))
          .get
          .toString == "Dummy (test)"
      )
      assert(pkg.getTypeOfPathInternal(root.join("classes").join("Dummy2.cls")).isEmpty)
      assert(pkg.getTypeOfPathInternal(root.join("classes").join("Dummy.object")).isEmpty)
      assert(pkg.getTypeOfPathInternal(root.join("classes2").join("Dummy.cls")).isEmpty)

      assert(
        pkg
          .getTypeOfPathInternal(root.join("pkg").join("triggers").join("Foo.trigger"))
          .get
          .toString == "__sfdc_trigger/test/Foo [test]"
      )
      assert(pkg.getTypeOfPathInternal(root.join("triggers").join("Foo2.trigger")).isEmpty)
      assert(pkg.getTypeOfPathInternal(root.join("triggers").join("Foo.object")).isEmpty)
      assert(pkg.getTypeOfPathInternal(root.join("triggers2").join("Foo2.trigger")).isEmpty)
    }
  }

  test("path of type") {
    FileSystemHelper.run(
      Map(
        "classes/Dummy.cls"    -> "public class Dummy {}",
        "triggers/Foo.trigger" -> "trigger Foo on Account (before insert) {}"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      val pkg = org.unmanaged
      assert(org.issues.isEmpty)

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
        "pkg/classes/Dummy.cls"    -> "public class Dummy {}",
        "pkg/triggers/Foo.trigger" -> "trigger Foo on Account (before insert) {}"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      val pkg = org.packagesByNamespace(Some(Name("test")))
      assert(org.issues.isEmpty)

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

      assert(dummyType.toString == "Dummy (test)")
      assert(pkg.getPathsOfType(dummyType).sameElements(Array("/pkg/classes/Dummy.cls")))

      assert(fooType.toString == "__sfdc_trigger/test/Foo [test]")
      assert(pkg.getPathsOfType(fooType).sameElements(Array("/pkg/triggers/Foo.trigger")))
    }
  }

  test("summary of type") {
    FileSystemHelper.run(Map("classes/Dummy.cls" -> "public class Dummy {}")) { root: PathLike =>
      val org = createOrg(root)
      val pkg = org.unmanaged
      assert(org.issues.isEmpty)

      val typeLike =
        pkg.getTypeOfPathInternal(root.join("classes").join("Dummy.cls")).get.asTypeIdentifier
      val summary = pkg.getSummaryOfType(typeLike)

      assert(summary.name == "Dummy")
      assert(summary.typeName.toString == "Dummy")
      assert(summary.idLocation == Location(1, 13, 1, 18))
      assert(summary.modifiers == ArraySeq(PUBLIC_MODIFIER))
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
        "pkg/classes/Dummy.cls" -> "@isTest puBlic class Dummy {}"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      val pkg = org.packagesByNamespace(Some(Name("test")))
      assert(org.issues.isEmpty)

      val typeLike =
        pkg
          .getTypeOfPathInternal(root.join("pkg").join("classes").join("Dummy.cls"))
          .get
          .asTypeIdentifier
      val summary = pkg.getSummaryOfType(typeLike)

      assert(summary.name == "Dummy")
      assert(summary.typeName.toString == "test.Dummy")
      assert(summary.idLocation == Location(1, 21, 1, 26))
      assert(summary.modifiers == ArraySeq(ISTEST_ANNOTATION, PUBLIC_MODIFIER))
    }
  }

  test("summary of type with namespace (cached)") {
    ParsedCache.clear()
    withManualFlush {

      FileSystemHelper.run(
        Map(
          "sfdx-project.json" ->
            """{
          |"namespace": "test",
          |"packageDirectories": [{"path": "pkg"}]
          |}""".stripMargin,
          "pkg/classes/Dummy.cls" -> "@isTest puBlic class Dummy {}"
        )
      ) { root: PathLike =>
        val org = createOrg(root)
        assert(org.issues.isEmpty)
        org.flush()

        val org2 = createOrg(root)
        val pkg2 = org2.packagesByNamespace(Some(Name("test")))
        assert(org2.issues.isEmpty)

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
            .isInstanceOf[SummaryDeclaration]
        )
        assert(summary.name == "Dummy")
        assert(summary.typeName.toString == "test.Dummy")
        assert(summary.idLocation == Location(1, 21, 1, 26))
        assert(summary.modifiers == ArraySeq(ISTEST_ANNOTATION, PUBLIC_MODIFIER))

      }
    }
  }

  test("summary of trigger") {
    FileSystemHelper.run(
      Map("triggers/Dummy.trigger" -> "trigger Dummy on Account (before insert) {}")
    ) { root: PathLike =>
      val org = createOrg(root)
      val pkg = org.unmanaged
      assert(org.issues.isEmpty)

      val typeLike = pkg
        .getTypeOfPathInternal(root.join("triggers").join("Dummy.trigger"))
        .get
        .asTypeIdentifier
      val summary = pkg.getSummaryOfType(typeLike)

      assert(summary.name == "__sfdc_trigger/Dummy")
      assert(summary.typeName.toString == "__sfdc_trigger/Dummy")
      assert(summary.idLocation == Location(1, 8, 1, 13))
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
        "pkg/triggers/Dummy.trigger" -> "trigger Dummy on Account (before insert) {}"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      val pkg = org.packagesByNamespace(Some(Name("test")))
      assert(org.issues.isEmpty)

      val typeLike = pkg
        .getTypeOfPathInternal(root.join("pkg").join("triggers").join("Dummy.trigger"))
        .get
        .asTypeIdentifier
      val summary = pkg.getSummaryOfType(typeLike)

      assert(summary.name == "__sfdc_trigger/test/Dummy")
      assert(summary.typeName.toString == "__sfdc_trigger/test/Dummy")
      assert(summary.idLocation == Location(1, 8, 1, 13))
      assert(summary.modifiers.isEmpty)
    }
  }

  test("No dependencies") {
    FileSystemHelper.run(Map("classes/Foo.cls" -> "public class Foo {}")) { root: PathLike =>
      val org = createOrg(root)
      val pkg = org.unmanaged
      assert(org.issues.isEmpty)

      val fooTypeLike =
        pkg.getTypeOfPathInternal(root.join("classes").join("Foo.cls")).get.asTypeIdentifier

      assert(pkg.getDependencyHolders(fooTypeLike, apexOnly = false).isEmpty)
    }
  }

  def fooHoldsBarCached(files: Map[String, String], outerInheritanceOnly: Boolean = false): Unit = {
    ParsedCache.clear()
    withManualFlush {
      FileSystemHelper.run(files) { root: PathLike => // Basic non-cached test
        {

          val org = createOrg(root)
          val pkg = org.unmanaged
          assert(org.issues.isEmpty)

          val fooTypeLike =
            pkg.getTypeOfPathInternal(root.join("classes").join("Foo.cls")).get.asTypeIdentifier
          val barTypeLike =
            pkg.getTypeOfPathInternal(root.join("classes").join("Bar.cls")).get.asTypeIdentifier

          assert(
            pkg.getDependencyHolders(fooTypeLike, apexOnly = false).sameElements(Array(barTypeLike))
          )
          assert(pkg.getDependencyHolders(barTypeLike, apexOnly = false).isEmpty)

          if (outerInheritanceOnly) {
            assert(
              pkg
                .getDependencies(barTypeLike, outerInheritanceOnly = true, apexOnly = false)
                .sameElements(Array(fooTypeLike))
            )
          } else {
            assert(
              pkg
                .getDependencies(barTypeLike, outerInheritanceOnly = true, apexOnly = false)
                .isEmpty
            )
          }
          assert(
            pkg
              .getDependencies(barTypeLike, outerInheritanceOnly = false, apexOnly = false)
              .sameElements(Array(fooTypeLike))
          )
          assert(
            pkg.getDependencies(fooTypeLike, outerInheritanceOnly = false, apexOnly = false).isEmpty
          )

          org.flush()
        }

        // Extended cache test
        {
          val org = createOrg(root)
          val pkg = org.unmanaged
          assert(org.issues.isEmpty)

          val fooTypeLike =
            pkg.getTypeOfPathInternal(root.join("classes").join("Foo.cls")).get.asTypeIdentifier
          val barTypeLike =
            pkg.getTypeOfPathInternal(root.join("classes").join("Bar.cls")).get.asTypeIdentifier

          assert(
            pkg.orderedModules.head
              .findModuleType(fooTypeLike.typeName)
              .get
              .isInstanceOf[SummaryDeclaration]
          )
          assert(
            pkg.orderedModules.head
              .findModuleType(barTypeLike.typeName)
              .get
              .isInstanceOf[SummaryDeclaration]
          )

          assert(
            pkg.getDependencyHolders(fooTypeLike, apexOnly = false).sameElements(Array(barTypeLike))
          )
          assert(pkg.getDependencyHolders(barTypeLike, apexOnly = false).isEmpty)

          if (outerInheritanceOnly) {
            assert(
              pkg
                .getDependencies(barTypeLike, outerInheritanceOnly = true, apexOnly = false)
                .sameElements(Array(fooTypeLike))
            )
          } else {
            assert(
              pkg
                .getDependencies(barTypeLike, outerInheritanceOnly = true, apexOnly = false)
                .isEmpty
            )
          }
          assert(
            pkg
              .getDependencies(barTypeLike, outerInheritanceOnly = false, apexOnly = false)
              .sameElements(Array(fooTypeLike))
          )
          assert(
            pkg.getDependencies(fooTypeLike, outerInheritanceOnly = false, apexOnly = false).isEmpty
          )
        }
      }
    }
  }

  test("Superclass dependency") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public virtual class Foo {}",
        "classes/Bar.cls" -> "public class Bar extends Foo {}"
      ),
      outerInheritanceOnly = true
    )
  }

  test("Interface dependency") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public interface Foo {}",
        "classes/Bar.cls" -> "public class Bar implements Foo {}"
      ),
      outerInheritanceOnly = true
    )
  }

  test("Block dependency") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public class Foo {}",
        "classes/Bar.cls" -> "public class Bar {{Object a = new Foo();}}"
      )
    )
  }

  test("Field type dependency") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public class Foo {}",
        "classes/Bar.cls" -> "public class Bar {Foo a;}"
      )
    )
  }

  test("Field expression dependency") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public class Foo {}",
        "classes/Bar.cls" -> "public class Bar {Object a = new Foo();}"
      )
    )
  }

  test("Method type dependency") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public class Foo {}",
        "classes/Bar.cls" -> "public class Bar {Foo func(){return null;} }"
      )
    )
  }

  test("Method argument dependency") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public class Foo {}",
        "classes/Bar.cls" -> "public class Bar {Object func(Foo a){return null;} }"
      )
    )
  }

  test("Method body dependency") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public class Foo {}",
        "classes/Bar.cls" -> "public class Bar {Object func(){return new Foo();} }"
      )
    )
  }

  test("Constructor argument dependency") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public class Foo {}",
        "classes/Bar.cls" -> "public class Bar { Bar(Foo a){} }"
      )
    )
  }

  test("Constructor body dependency") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public class Foo {}",
        "classes/Bar.cls" -> "public class Bar { Bar(){Object a = new Foo();} }"
      )
    )
  }

  test("Superclass dependency (nested)") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public class Foo {public virtual class Baz {}}",
        "classes/Bar.cls" -> "public class Bar extends Foo.Baz {}"
      ),
      outerInheritanceOnly = true
    )
  }

  test("Interface dependency (nested)") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public class Foo {public interface Baz {}}",
        "classes/Bar.cls" -> "public class Bar implements Foo.Baz {}"
      ),
      outerInheritanceOnly = true
    )
  }

  test("Block dependency (nested)") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public class Foo {public class Baz {}}",
        "classes/Bar.cls" -> "public class Bar {{Object a = new Foo.Baz();}}"
      )
    )
  }

  test("Field type dependency (nested)") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public class Foo {public class Baz {}}",
        "classes/Bar.cls" -> "public class Bar {Foo.Baz a;}"
      )
    )
  }

  test("Field expression dependency (nested)") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public class Foo {public class Baz {}}",
        "classes/Bar.cls" -> "public class Bar {Object a = new Foo.Baz();}"
      )
    )
  }

  test("Method type dependency (nested)") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public class Foo {public class Baz {}}",
        "classes/Bar.cls" -> "public class Bar {Foo.Baz func(){return null;} }"
      )
    )
  }

  test("Method argument dependency (nested)") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public class Foo {public class Baz {}}",
        "classes/Bar.cls" -> "public class Bar {Object func(Foo.Baz a){return null;} }"
      )
    )
  }

  test("Method body dependency (nested)") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public class Foo {public class Baz {}}",
        "classes/Bar.cls" -> "public class Bar {Object func(){return new Foo.Baz();} }"
      )
    )
  }

  test("Constructor argument dependency (nested)") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public class Foo {public class Baz {}}",
        "classes/Bar.cls" -> "public class Bar { Bar(Foo.Baz a){} }"
      )
    )
  }

  test("Constructor body dependency (nested)") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public class Foo {public class Baz {}}",
        "classes/Bar.cls" -> "public class Bar { Bar(){Object a = new Foo.Baz();} }"
      )
    )
  }

  test("Superclass dependency (from nested)") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public virtual class Foo {}",
        "classes/Bar.cls" -> "public class Bar {public class Baz extends Foo {}}"
      )
    )
  }

  test("Interface dependency (from nested)") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public interface Foo {}",
        "classes/Bar.cls" -> "public class Bar {public class Baz implements Foo {}}"
      )
    )
  }

  test("Block dependency (from nested)") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public class Foo {}",
        "classes/Bar.cls" -> "public class Bar {public class Baz { {Object a = new Foo();} }}"
      )
    )
  }

  test("Field type dependency (from nested)") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public class Foo {}",
        "classes/Bar.cls" -> "public class Bar {public class Baz {Foo a;} }"
      )
    )
  }

  test("Field expression dependency (from nested)") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public class Foo {}",
        "classes/Bar.cls" -> "public class Bar {public class Baz {Object a = new Foo();} }"
      )
    )
  }

  test("Method type dependency (from nested)") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public class Foo {}",
        "classes/Bar.cls" -> "public class Bar {public class Baz {Foo func(){return null;}} }"
      )
    )
  }

  test("Method argument dependency (from nested)") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public class Foo {}",
        "classes/Bar.cls" -> "public class Bar {public class Baz {Object func(Foo a){return null;} }}"
      )
    )
  }

  test("Method body dependency (from nested)") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public class Foo {}",
        "classes/Bar.cls" -> "public class Bar {public class Baz {Object func(){return new Foo();} }}"
      )
    )
  }

  test("Constructor argument dependency (from nested)") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public class Foo {}",
        "classes/Bar.cls" -> "public class Bar {public class Baz { Baz(Foo a){} }}"
      )
    )
  }

  test("Constructor body dependency (from nested)") {
    fooHoldsBarCached(
      Map(
        "classes/Foo.cls" -> "public class Foo {}",
        "classes/Bar.cls" -> "public class Bar {public class Baz { Baz(){Object a = new Foo();} }}"
      )
    )
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
        "pkg2/Bar.cls" -> "public class Bar extends pkg1.Foo {}"
      )
    ) { root: PathLike =>
      val org  = createOrg(root)
      val pkg1 = org.packagesByNamespace(Some(Name("pkg1")))
      val pkg2 = org.packagesByNamespace(Some(Name("pkg2")))
      assert(org.issues.isEmpty)

      val fooTypeLike =
        pkg1.getTypeOfPathInternal(root.join("pkg1").join("Foo.cls")).get.asTypeIdentifier
      val barTypeLike =
        pkg2.getTypeOfPathInternal(root.join("pkg2").join("Bar.cls")).get.asTypeIdentifier

      assert(
        pkg1.getDependencyHolders(fooTypeLike, apexOnly = false).sameElements(Array(barTypeLike))
      )
      assert(pkg2.getDependencyHolders(barTypeLike, apexOnly = false).isEmpty)
    }
  }

  test("Unmanaged to Managed Dependency (cached)") {
    ParsedCache.clear()
    withManualFlush {

      FileSystemHelper.run(
        Map(
          "sfdx-project.json" ->
            """{
              |"namespace": "pkg2",
              |"packageDirectories": [{"path": "pkg2"}],
              |"plugins": {"dependencies": [{"namespace": "pkg1", "path": "pkg1"}]}
              |}""".stripMargin,
          "pkg1/Foo.cls" -> "global virtual class Foo {}",
          "pkg2/Bar.cls" -> "public class Bar extends pkg1.Foo {}"
        )
      ) { root: PathLike =>
        val org = createOrg(root)
        assert(org.issues.isEmpty)
        org.flush()

        val org2  = createOrg(root)
        val pkg21 = org2.packagesByNamespace(Some(Name("pkg1")))
        val pkg22 = org2.packagesByNamespace(Some(Name("pkg2")))
        assert(org2.issues.isEmpty)

        val fooTypeLike =
          pkg21.getTypeOfPathInternal(root.join("pkg1").join("Foo.cls")).get.asTypeIdentifier
        val barTypeLike =
          pkg22.getTypeOfPathInternal(root.join("pkg2").join("Bar.cls")).get.asTypeIdentifier

        assert(
          pkg21.orderedModules.head
            .findModuleType(fooTypeLike.typeName)
            .get
            .isInstanceOf[SummaryDeclaration]
        )
        assert(
          pkg22.orderedModules.head
            .findModuleType(barTypeLike.typeName)
            .get
            .isInstanceOf[SummaryDeclaration]
        )

        assert(
          pkg21.getDependencyHolders(fooTypeLike, apexOnly = false).sameElements(Array(barTypeLike))
        )
        assert(pkg22.getDependencyHolders(barTypeLike, apexOnly = false).isEmpty)
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
        "pkg2/Bar.cls" -> "public class Bar extends pkg1.Foo {}"
      )
    ) { root: PathLike =>
      val org  = createOrg(root)
      val pkg1 = org.packagesByNamespace(Some(Name("pkg1")))
      val pkg2 = org.packagesByNamespace(Some(Name("pkg2")))
      assert(org.issues.isEmpty)

      val fooTypeLike =
        pkg1.getTypeOfPathInternal(root.join("pkg1").join("Foo.cls")).get.asTypeIdentifier
      val barTypeLike =
        pkg2.getTypeOfPathInternal(root.join("pkg2").join("Bar.cls")).get.asTypeIdentifier

      assert(
        pkg1.getDependencyHolders(fooTypeLike, apexOnly = false).sameElements(Array(barTypeLike))
      )
      assert(pkg2.getDependencyHolders(barTypeLike, apexOnly = false).isEmpty)
    }
  }

  test("Managed to Managed Dependency (cached)") {
    ParsedCache.clear()

    withManualFlush {
      FileSystemHelper.run(
        Map(
          "sfdx-project.json" ->
            """{
              |"namespace": "pkg2",
              |"packageDirectories": [{"path": "pkg2"}],
              |"plugins": {"dependencies": [{"namespace": "pkg1", "path": "pkg1"}]}
              |}""".stripMargin,
          "pkg1/Foo.cls" -> "global virtual class Foo {}",
          "pkg2/Bar.cls" -> "public class Bar extends pkg1.Foo {}"
        )
      ) { root: PathLike =>
        val org = createOrg(root)
        assert(org.issues.isEmpty)
        org.flush()

        val org2  = createOrg(root)
        val pkg21 = org2.packagesByNamespace(Some(Name("pkg1")))
        val pkg22 = org2.packagesByNamespace(Some(Name("pkg2")))
        assert(org2.issues.isEmpty)

        val fooTypeLike =
          pkg21.getTypeOfPathInternal(root.join("pkg1").join("Foo.cls")).get.asTypeIdentifier
        val barTypeLike =
          pkg22.getTypeOfPathInternal(root.join("pkg2").join("Bar.cls")).get.asTypeIdentifier

        assert(
          pkg21.orderedModules.head
            .findModuleType(fooTypeLike.typeName)
            .get
            .isInstanceOf[SummaryDeclaration]
        )
        assert(
          pkg22.orderedModules.head
            .findModuleType(barTypeLike.typeName)
            .get
            .isInstanceOf[SummaryDeclaration]
        )

        assert(
          pkg21.getDependencyHolders(fooTypeLike, apexOnly = false).sameElements(Array(barTypeLike))
        )
        assert(pkg22.getDependencyHolders(barTypeLike, apexOnly = false).isEmpty)
      }
    }
  }

  test("Trigger with no block") {
    FileSystemHelper.run(
      Map("triggers/Foo.trigger" -> "trigger Foo on Account (before insert) {}")
    ) { root: PathLike =>
      val org = createOrg(root)
      val pkg = org.unmanaged
      assert(org.issues.isEmpty)

      val fooTypeLike =
        pkg.getTypeOfPathInternal(root.join("triggers").join("Foo.trigger")).get.asTypeIdentifier

      assert(
        pkg.getDependencies(fooTypeLike, outerInheritanceOnly = false, apexOnly = false).isEmpty
      )
    }
  }

  test("Trigger with block") {
    FileSystemHelper.run(
      Map(
        "classes/Bar.cls"      -> "public class Bar {}",
        "triggers/Foo.trigger" -> "trigger Foo on Account (before insert) {Bar b;}"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      val pkg = org.unmanaged
      assert(org.issues.isEmpty)

      val barTypeLike =
        pkg.getTypeOfPathInternal(root.join("classes").join("Bar.cls")).get.asTypeIdentifier
      val fooTypeLike =
        pkg.getTypeOfPathInternal(root.join("triggers").join("Foo.trigger")).get.asTypeIdentifier

      assert(
        pkg
          .getDependencies(fooTypeLike, outerInheritanceOnly = false, apexOnly = false)
          .sameElements(Array(barTypeLike))
      )
    }
  }

  test("Trigger with block with namespace") {
    FileSystemHelper.run(
      Map(
        "classes/Bar.cls"      -> "public class Bar {}",
        "triggers/Foo.trigger" -> "trigger Foo on Account (before insert) {Bar b;}"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      val pkg = org.unmanaged
      assert(org.issues.isEmpty)

      val barTypeLike =
        pkg.getTypeOfPathInternal(root.join("classes").join("Bar.cls")).get.asTypeIdentifier
      val fooTypeLike =
        pkg.getTypeOfPathInternal(root.join("triggers").join("Foo.trigger")).get.asTypeIdentifier

      assert(
        pkg
          .getDependencies(fooTypeLike, outerInheritanceOnly = false, apexOnly = false)
          .sameElements(Array(barTypeLike))
      )
    }
  }

  test("location of type") {
    FileSystemHelper.run(Map("classes/Dummy.cls" -> "public class Dummy {}")) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)

      assert(
        org.getIdentifierLocation(TypeIdentifier(None, TypeName(Name("Dummy")))) ==
          PathLocation(root.join("classes").join("Dummy.cls"), Location(1, 13, 1, 18))
      )
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
        "test/Dummy.cls" -> "public class Dummy {}"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)

      assert(
        org
          .getIdentifierLocation(
            TypeIdentifier(Some(Name("test")), TypeName(Name("Dummy")))
          ) == null
      )
      assert(
        org.getIdentifierLocation(
          TypeIdentifier(
            Some(Name("test")),
            TypeName(Name("Dummy"), Nil, Some(TypeName(Name("test"))))
          )
        ) ==
          PathLocation(root.join("test").join("Dummy.cls"), Location(1, 13, 1, 18))
      )
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
        "test/Dummy.cls" -> "public class Dummy {class Inner {}}"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)

      assert(org.getIdentifierLocation(TypeIdentifier(None, TypeName(Name("Dummy")))) == null)
      assert(
        org.getIdentifierLocation(
          TypeIdentifier(
            Some(Name("test")),
            TypeName(Name("Inner"), Nil, Some(TypeName(Name("Dummy"))))
          )
        ) == null
      )
      assert(
        org.getIdentifierLocation(
          TypeIdentifier(
            Some(Name("test")),
            TypeName(
              Name("Inner"),
              Nil,
              Some(TypeName(Name("Dummy"), Nil, Some(TypeName(Name("test")))))
            )
          )
        ) ==
          PathLocation(root.join("test").join("Dummy.cls"), Location(1, 26, 1, 31))
      )
    }
  }

  test("location of trigger") {
    FileSystemHelper.run(
      Map("triggers/Foo.trigger" -> "trigger Foo on Account (before insert) {}")
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)

      assert(org.getIdentifierLocation(TypeIdentifier(None, TypeName(Name("Foo")))) == null)
      assert(
        org.getIdentifierLocation(TypeIdentifier(None, TypeName(Name("__sfdc_trigger/Foo")))) ==
          PathLocation(root.join("triggers").join("Foo.trigger"), Location(1, 8, 1, 11))
      )
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
        "test/Foo.trigger" -> "trigger Foo on Account (before insert) {}"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)

      assert(
        org
          .getIdentifierLocation(TypeIdentifier(Some(Name("test")), TypeName(Name("Foo")))) == null
      )
      assert(
        org.getIdentifierLocation(
          TypeIdentifier(Some(Name("test")), TypeName(Name("__sfdc_trigger/Foo")))
        ) == null
      )
      assert(
        org.getIdentifierLocation(
          TypeIdentifier(Some(Name("test")), TypeName(Name("__sfdc_trigger/test/Foo")))
        ) ==
          PathLocation(root.join("test").join("Foo.trigger"), Location(1, 8, 1, 11))
      )
    }
  }

  test("typeIdentifiers") {
    FileSystemHelper.run(
      Map(
        "classes/Dummy.cls"    -> "public class Dummy {}",
        "triggers/Foo.trigger" -> "trigger Foo on Account (before insert) {}"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)

      assert(
        org.getTypeIdentifiers(false).map(_.toString).toSet == Set[String](
          "__sfdc_trigger/Foo",
          "Schema.Account",
          "Dummy"
        )
      )
    }
  }

  test("typeIdentifiers - apex only") {
    FileSystemHelper.run(
      Map(
        "classes/Dummy.cls"    -> "public class Dummy {}",
        "triggers/Foo.trigger" -> "trigger Foo on Account (before insert) {}"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)

      assert(
        org.getTypeIdentifiers(true).map(_.toString).toSet == Set[String](
          "__sfdc_trigger/Foo",
          "Dummy"
        )
      )
    }
  }

  test("typeIdentifiers (namespaced)") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "test",
            |"packageDirectories": [{"path": "pkg"}]
            |}""".stripMargin,
        "pkg/classes/Dummy.cls"    -> "public class Dummy {}",
        "pkg/triggers/Foo.trigger" -> "trigger Foo on Account (before insert) {}"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)

      assert(
        org.getTypeIdentifiers(false).map(_.toString).toSet == Set[String](
          "__sfdc_trigger/test/Foo [test]",
          "Schema.Account [test]",
          "Dummy (test)"
        )
      )
    }
  }

  test("empty project bombs") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" -> """{"namespace": "test", "packageDirectories": [{"path": "pkg"}] }""".stripMargin
      )
    ) { root: PathLike =>
      val org = createHappyOrg(root)

      assert(org.getDependencyBombs(10).isEmpty)
    }
  }

  test("single file bombs") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" -> """{"namespace": "test", "packageDirectories": [{"path": "pkg"}] }""".stripMargin,
        "pkg/Dummy.cls"     -> "public class Dummy {}"
      )
    ) { root: PathLike =>
      val org = createHappyOrg(root)

      assert(org.getDependencyBombs(10).isEmpty)
    }
  }

  test("multi file bombs") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" -> """{"namespace": "test", "packageDirectories": [{"path": "pkg"}] }""".stripMargin,
        "pkg/Dummy1.cls"    -> "public class Dummy1 { {Object a = new Dummy2(); Object b = new Dummy3();} }",
        "pkg/Dummy2.cls"    -> "public class Dummy2 { {Object a = new Dummy3(); }}",
        "pkg/Dummy3.cls"    -> "public class Dummy3 {}"
      )
    ) { root: PathLike =>
      val org = createHappyOrg(root)

      val dummy2Id = new TypeIdentifier(
        Some(Name("test")),
        TypeName(Name("Dummy2"), Nil, Some(TypeName(Name("test"))))
      )
      assert(org.getDependencyBombs(10) sameElements Array(BombScore(dummy2Id, 1, 1, 73.2)))
    }
  }

  test("multi file bombs - two scores") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" -> """{"namespace": "test", "packageDirectories": [{"path": "pkg"}] }""".stripMargin,
        "pkg/Dummy1.cls"    -> "public class Dummy1 { {Object a = new Dummy2(); Object b = new Dummy3();} }",
        "pkg/Dummy2.cls"    -> "public class Dummy2 { {Object a = new Dummy3(); Object b = new Dummy1(); Object c = new Dummy4();}}",
        "pkg/Dummy3.cls"    -> "public class Dummy3 {}",
        "pkg/Dummy4.cls"    -> "public class Dummy4 {}"
      )
    ) { root: PathLike =>
      val org = createHappyOrg(root)

      val dummy1Id = new TypeIdentifier(
        Some(Name("test")),
        TypeName(Name("Dummy1"), Nil, Some(TypeName(Name("test"))))
      )
      val dummy2Id = new TypeIdentifier(
        Some(Name("test")),
        TypeName(Name("Dummy2"), Nil, Some(TypeName(Name("test"))))
      )
      assert(
        org.getDependencyBombs(10) sameElements Array(
          BombScore(dummy2Id, 1, 3, 78.69),
          BombScore(dummy1Id, 1, 2, 74.33)
        )
      )
    }
  }

  test("multi file bombs - size limited") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" -> """{"namespace": "test", "packageDirectories": [{"path": "pkg"}] }""".stripMargin,
        "pkg/Dummy1.cls"    -> "public class Dummy1 { {Object a = new Dummy2(); Object b = new Dummy3();} }",
        "pkg/Dummy2.cls"    -> "public class Dummy2 { {Object a = new Dummy3(); Object b = new Dummy1(); Object c = new Dummy4();}}",
        "pkg/Dummy3.cls"    -> "public class Dummy3 {}",
        "pkg/Dummy4.cls"    -> "public class Dummy4 {}"
      )
    ) { root: PathLike =>
      val org = createHappyOrg(root)

      val dummy1Id = new TypeIdentifier(
        Some(Name("test")),
        TypeName(Name("Dummy1"), Nil, Some(TypeName(Name("test"))))
      )
      val dummy2Id = new TypeIdentifier(
        Some(Name("test")),
        TypeName(Name("Dummy2"), Nil, Some(TypeName(Name("test"))))
      )
      assert(org.getDependencyBombs(1) sameElements Array(BombScore(dummy2Id, 1, 3, 78.69)))
    }
  }

}
