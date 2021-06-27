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

import com.nawforce.apexlink.names.TypeNames.TypeNameUtils
import com.nawforce.apexlink.org.{OrgImpl, PackageImpl}
import com.nawforce.apexlink.types.apex.{FullDeclaration, SummaryDeclaration}
import com.nawforce.apexlink.{FileSystemHelper, TestHelper}
import com.nawforce.pkgforce.names.{Name, TypeName}
import com.nawforce.pkgforce.path.PathLike
import org.scalatest.funsuite.AnyFunSuite

class UnusedTest extends AnyFunSuite with TestHelper {

  test("Unused method") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {public void foo() {}}")) {
      root: PathLike =>
        val org = createOrg(root)
        val module = org.unmanaged.orderedModules.headOption.get
        assert(!org.issues.hasMessages)
        assert(
          module.reportUnused().getMessages(root.join("Dummy.cls").toString) ==
            "Unused: line 1 at 32-35: Unused Method 'void foo()'\n")
    }
  }

  test("Method used from same method") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {public void foo() {foo();}}")) {
      root: PathLike =>
        val org = createOrg(root)
        val module = org.unmanaged.orderedModules.headOption.get
        assert(!org.issues.hasMessages)
        assert(module.reportUnused().getMessages(root.join("Dummy.cls").toString).isEmpty)
    }
  }

  test("Method used from block") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {{foo();} public void foo() {}}")) {
      root: PathLike =>
        val org = createOrg(root)
        val module = org.unmanaged.orderedModules.headOption.get
        assert(!org.issues.hasMessages)
        assert(module.reportUnused().getMessages(root.join("Dummy.cls").toString).isEmpty)
    }
  }

  test("Unused field") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {Object a;}")) { root: PathLike =>
      val org = createOrg(root)
      val module = org.unmanaged.orderedModules.headOption.get
      assert(!org.issues.hasMessages)
      assert(
        module.reportUnused().getMessages(root.join("Dummy.cls").toString) == "" +
          "Unused: line 1 at 27-28: Unused Field or Property 'a'\n")
    }
  }

  test("Field used from method") {
    FileSystemHelper.run(
      Map("Dummy.cls" -> "public class Dummy {Object a; void foo(){foo(); a = null;}}")) {
      root: PathLike =>
        val org = createOrg(root)
        val module = org.unmanaged.orderedModules.headOption.get
        assert(!org.issues.hasMessages)
        assert(module.reportUnused().getMessages(root.join("Dummy.cls").toString).isEmpty)
    }
  }

  test("Field used from block") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {{a = null;} Object a;}")) {
      root: PathLike =>
        val org = createOrg(root)
        val module = org.unmanaged.orderedModules.headOption.get
        assert(!org.issues.hasMessages)
        assert(module.reportUnused().getMessages(root.join("Dummy.cls").toString).isEmpty)
    }
  }

  def assertIsFullDeclaration(pkg: PackageImpl,
                              name: String,
                              namespace: Option[Name] = None): Unit = {
    assert(
      pkg.orderedModules.head
        .findModuleType(TypeName(Name(name)).withNamespace(namespace))
        .head
        .isInstanceOf[FullDeclaration])
  }

  def assertIsSummaryDeclaration(pkg: PackageImpl,
                                 name: String,
                                 namespace: Option[Name] = None): Unit = {
    assert(
      pkg.orderedModules.head
        .findModuleType(TypeName(Name(name)).withNamespace(namespace))
        .head
        .isInstanceOf[SummaryDeclaration])
  }

  test("Used method on summary type") {
    withManualFlush {
      FileSystemHelper.run(
        Map("Dummy.cls" -> "public class Dummy {public static void foo() {}}",
            "Caller.cls" -> "public class Caller {{Dummy.Foo();}}",
        )) { root: PathLike =>
        // Setup as cached
        val org = createOrg(root)
        val pkg = org.unmanaged
        val module = pkg.orderedModules.headOption.get
        assertIsFullDeclaration(pkg, "Dummy")
        assert(!org.issues.hasMessages)
        assert(module.reportUnused().getMessages(root.join("Dummy.cls").toString).isEmpty)
        org.flush()

        val org2 = createOrg(root)
        val pkg2 = org2.unmanaged
        val module2 = pkg2.orderedModules.headOption.get
        assertIsSummaryDeclaration(pkg2, "Dummy")
        OrgImpl.current.withValue(org2) {
          pkg2.propagateAllDependencies()
          assert(module2.reportUnused().getMessages(root.join("Dummy.cls").toString).isEmpty)
        }
      }
    }
  }

  test("Unused method on summary type") {
    withManualFlush {
      FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {public void foo() {}}")) {
        root: PathLike =>
          // Setup as cached
          val org = createOrg(root)
          val pkg = org.unmanaged
          val module = pkg.orderedModules.headOption.get
          assertIsFullDeclaration(pkg, "Dummy")
          assert(!org.issues.hasMessages)
          assert(
            module.reportUnused().getMessages(root.join("Dummy.cls").toString) == "" +
              "Unused: line 1 at 32-35: Unused Method 'void foo()'\n")
          org.flush()

          val org2 = createOrg(root)
          val pkg2 = org2.unmanaged
          val module2 = pkg2.orderedModules.headOption.get
          assertIsSummaryDeclaration(pkg2, "Dummy")
          OrgImpl.current.withValue(org2) {
            assert(
              module2.reportUnused().getMessages(root.join("Dummy.cls").toString) == "" +
                "Unused: line 1 at 32-35: Unused Method 'void foo()'\n")
          }
      }
    }
  }

  test("Trigger referencing class") {
    FileSystemHelper.run(
      Map("Foo.cls" -> "public class Foo {public static String bar;}",
          "Dummy.trigger" ->
            """trigger Dummy on Account (before insert) {
            |  System.debug(Foo.bar);
            |}""".stripMargin)) { root: PathLike =>
      val org = createOrg(root)
      val pkg = org.unmanaged
      val module = pkg.orderedModules.headOption.get
      assert(!org.issues.hasMessages)

      OrgImpl.current.withValue(org) {
        assert(module.reportUnused().getMessages(root.join("Foo.cls").toString).isEmpty)
      }
    }
  }

  test("Method referenced from external function call ") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          |"namespace": "pkg",
          |"packageDirectories": [{"path": "pkg"}],
          |"plugins": {"dependencies": [{"namespace": "ext"}]}
          |}""".stripMargin,
        "pkg/Dummy.cls" -> "public class Dummy {public static void foo() {}}",
        "pkg/Other.cls" -> "public class Other {public void bar() {ext.func(Dummy.foo());}}")) {
      root: PathLike =>
        val org = createOrg(root)
        val pkg = org.packagesByNamespace(Some(Name("pkg")))
        val module = pkg.orderedModules.headOption.get
        assert(!org.issues.hasMessages)
        assert(module.reportUnused().getMessages(root.join("Dummy.cls").toString).isEmpty)
    }
  }
}
