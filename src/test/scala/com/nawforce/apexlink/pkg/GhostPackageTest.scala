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
package com.nawforce.apexlink.pkg

import com.nawforce.apexlink.{FileSystemHelper, TestHelper}
import com.nawforce.pkgforce.path.PathLike
import org.scalatest.funsuite.AnyFunSuite

class GhostPackageTest extends AnyFunSuite with TestHelper {

  test("Ghost package suppresses declared type error") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "pkg",
            |"packageDirectories": [{"path": "pkg"}],
            |"plugins": {"dependencies": [{"namespace": "ghosted"}]}
            |}""".stripMargin,
        "pkg/Dummy.cls" -> "public class Dummy extends ghosted.SuperClass {}")) { root: PathLike =>
      createOrg(root)
      assert(!hasIssues)
      assert(packagedCustomType("pkg", "Dummy").get.dependencies().isEmpty)
    }
  }

  test("Ghost package with wrong namespace has declared type error") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "pkg",
            |"packageDirectories": [{"path": "pkg"}],
            |"plugins": {"dependencies": [{"namespace": "ghosted"}]}
            |}""".stripMargin,
        "pkg/Dummy.cls" -> "public class Dummy extends package.SuperClass {}")) { root: PathLike =>
      val org = createOrg(root)
      assert(packagedCustomType("pkg", "Dummy").get.dependencies().isEmpty)
      assert(org.issues
        .getMessages("/pkg/Dummy.cls") == "Missing: line 1 at 13-18: No type declaration found for 'package.SuperClass'\n")
    }
  }

  test("Ghost package suppresses declared interface type error") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "pkg",
            |"packageDirectories": [{"path": "pkg"}],
            |"plugins": {"dependencies": [{"namespace": "ghosted"}]}
            |}""".stripMargin,
        "pkg/Dummy.cls" -> "public class Dummy implements ghosted.MyThing {}")) { root: PathLike =>
      createOrg(root)
      assert(!hasIssues)
      assert(packagedCustomType("pkg", "Dummy").get.dependencies().isEmpty)
    }
  }

  test("Ghost package with wrong namespace has declared interface type error") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "pkg",
            |"packageDirectories": [{"path": "pkg"}],
            |"plugins": {"dependencies": [{"namespace": "ghosted"}]}
            |}""".stripMargin,
        "pkg/Dummy.cls" -> "public class Dummy implements package.MyThing {}")) { root: PathLike =>
      val org = createOrg(root)
      assert(packagedCustomType("pkg", "Dummy").get.dependencies().isEmpty)
      assert(org.issues
        .getMessages("/pkg/Dummy.cls") == "Missing: line 1 at 13-18: No type declaration found for 'package.MyThing'\n")
    }
  }

  test("Ghost package suppresses implicit type error") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "pkg",
            |"packageDirectories": [{"path": "pkg"}],
            |"plugins": {"dependencies": [{"namespace": "ghosted"}]}
            |}""".stripMargin,
        "pkg/Dummy.cls" -> "public class Dummy { {Object a = ghosted.A.class;} }")) {
      root: PathLike =>
        createOrg(root)
        assert(!hasIssues)
        assert(packagedCustomType("pkg", "Dummy").get.dependencies().isEmpty)
    }
  }

  test("Ghost package with wrong namespace has implicit type error") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "pkg",
            |"packageDirectories": [{"path": "pkg"}],
            |"plugins": {"dependencies": [{"namespace": "ghosted"}]}
            |}""".stripMargin,
        "pkg/Dummy.cls" -> "public class Dummy { {Object a = package.A.class;} }")) {
      root: PathLike =>
        val org = createOrg(root)
        assert(packagedCustomType("pkg", "Dummy").get.dependencies().isEmpty)
        assert(org.issues
          .getMessages("/pkg/Dummy.cls") == "Missing: line 1 at 33-48: No type declaration found for 'package.A'\n")
    }
  }

  test("Ghost package suppresses custom object error") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          |"namespace": "pkg",
          |"packageDirectories": [{"path": "pkg"}],
          |"plugins": {"dependencies": [{"namespace": "ghosted"}]}
          |}""".stripMargin,
        "pkg/Dummy.cls" -> "public class Dummy { {Object a = new ghosted__Foo__c();} }")) {
      root: PathLike =>
        createOrg(root)
        assert(!hasIssues)
        assert(packagedCustomType("pkg", "Dummy").get.dependencies().isEmpty)
    }
  }

  test("Ghost package with wrong namespace has custom object error") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          |"namespace": "pkg",
          |"packageDirectories": [{"path": "pkg"}],
          |"plugins": {"dependencies": [{"namespace": "ghosted"}]}
          |}""".stripMargin,
        "pkg/Dummy.cls" -> "public class Dummy { {Object a = new package__Foo__c();} }")) {
      root: PathLike =>
        val org = createOrg(root)
        assert(packagedCustomType("pkg", "Dummy").get.dependencies().isEmpty)
        assert(org.issues
          .getMessages("/pkg/Dummy.cls") == "Missing: line 1 at 37-52: No type declaration found for 'Schema.package__Foo__c'\n")
    }
  }

  test("Ghost package suppresses custom metadata error") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          |"namespace": "pkg",
          |"packageDirectories": [{"path": "pkg"}],
          |"plugins": {"dependencies": [{"namespace": "ghosted"}]}
          |}""".stripMargin,
        "pkg/Dummy.cls" -> "public class Dummy { {Object a = new ghosted__Foo__mdt();} }")) {
      root: PathLike =>
        createOrg(root)
        assert(!hasIssues)
        assert(packagedCustomType("pkg", "Dummy").get.dependencies().isEmpty)
    }
  }

  test("Ghost package with wrong namespace has custom metadata error") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          |"namespace": "pkg",
          |"packageDirectories": [{"path": "pkg"}],
          |"plugins": {"dependencies": [{"namespace": "ghosted"}]}
          |}""".stripMargin,
        "pkg/Dummy.cls" -> "public class Dummy { {Object a = new package__Foo__mdt();} }")) {
      root: PathLike =>
        val org = createOrg(root)
        assert(packagedCustomType("pkg", "Dummy").get.dependencies().isEmpty)
        assert(org.issues
          .getMessages("/pkg/Dummy.cls") == "Missing: line 1 at 37-54: No type declaration found for 'Schema.package__Foo__mdt'\n")
    }
  }

  test("Ghost package suppresses platform event error") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          |"namespace": "pkg",
          |"packageDirectories": [{"path": "pkg"}],
          |"plugins": {"dependencies": [{"namespace": "ghosted"}]}
          |}""".stripMargin,
        "pkg/Dummy.cls" -> "public class Dummy { {Object a = new ghosted__Foo__e();} }")) {
      root: PathLike =>
        createOrg(root)
        assert(!hasIssues)
        assert(packagedCustomType("pkg", "Dummy").get.dependencies().isEmpty)
    }

  }

  test("Ghost package with wrong namespace has platform event error") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          |"namespace": "pkg",
          |"packageDirectories": [{"path": "pkg"}],
          |"plugins": {"dependencies": [{"namespace": "ghosted"}]}
          |}""".stripMargin,
        "pkg/Dummy.cls" -> "public class Dummy { {Object a = new package__Foo__e();} }")) {
      root: PathLike =>
        val org = createOrg(root)
        assert(packagedCustomType("pkg", "Dummy").get.dependencies().isEmpty)
        assert(org.issues
          .getMessages("/pkg/Dummy.cls") == "Missing: line 1 at 37-52: No type declaration found for 'Schema.package__Foo__e'\n")
    }
  }

  test("Ghost package suppresses possible field reference error") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          |"namespace": "pkg",
          |"packageDirectories": [{"path": "pkg"}],
          |"plugins": {"dependencies": [{"namespace": "ghosted"}]}
          |}""".stripMargin,
        "pkg/Dummy.cls" -> "public class Dummy extends ghosted.SuperClass { {Object a = b.foo();} }")) {
      root: PathLike =>
        createOrg(root)
        assert(!hasIssues)
        assert(packagedCustomType("pkg", "Dummy").get.dependencies().isEmpty)
    }
  }
}
