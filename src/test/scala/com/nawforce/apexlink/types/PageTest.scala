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
package com.nawforce.apexlink.types

import com.nawforce.apexlink.{FileSystemHelper, TestHelper}
import com.nawforce.pkgforce.path.PathLike
import org.scalatest.funsuite.AnyFunSuite

class PageTest extends AnyFunSuite with TestHelper {

  test("Valid page") {
    FileSystemHelper.run(
      Map("TestPage.page" -> "",
          "Dummy.cls" -> "public class Dummy { {PageReference a = Page.TestPage;} }")) {
      root: PathLike =>
        val org = createOrg(root)
        assert(!org.issues.hasMessages)
    }
  }

  test("Valid page (case insensitive)") {
    FileSystemHelper.run(
      Map("TestPage.page" -> "",
          "Dummy.cls" -> "public class Dummy { {PageReference a = Page.tesTPage;} }")) {
      root: PathLike =>
        val org = createOrg(root)
        assert(!org.issues.hasMessages)
    }
  }

  test("Missing page") {
    FileSystemHelper.run(
      Map("TestPage.page" -> "",
          "Dummy.cls" -> "public class Dummy { {PageReference a = Page.AnotherPage;} }")) {
      root: PathLike =>
        val org = createOrg(root)
        assert(
          org.issues.getMessages("/Dummy.cls") ==
            "Missing: line 1 at 40-56: Unknown field or type 'AnotherPage' on 'Page'\n")
    }
  }

  test("Base package page") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "pkg2",
            |"packageDirectories": [{"path": "pkg2"}],
            |"plugins": {"dependencies": [{"namespace": "pkg1", "path": "pkg1"}]}
            |}""".stripMargin,
        "pkg1/TestPage.page" -> "",
        "pkg2/Dummy.cls" -> "public class Dummy { {PageReference a = Page.pkg1__TestPage;} }")) {
      root: PathLike =>
        val org = createOrg(root)
        assert(!org.issues.hasMessages)
    }
  }

  test("Ghost package page") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          |"namespace": "pkg2",
          |"packageDirectories": [{"path": "pkg2"}],
          |"plugins": {"dependencies": [{"namespace": "pkg1"}]}
          |}""".stripMargin,
        "pkg2/Dummy.cls" -> "public class Dummy { {PageReference a = Page.pkg1__TestPage;} }")) {
      root: PathLike =>
        val org = createOrg(root)
        assert(!org.issues.hasMessages)
    }
  }

}
