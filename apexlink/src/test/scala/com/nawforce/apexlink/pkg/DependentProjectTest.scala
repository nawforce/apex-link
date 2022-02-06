/*
 Copyright (c) 2019 Kevin Jones, All rights reserved.
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
import com.nawforce.pkgforce.path.PathLike
import org.scalatest.funsuite.AnyFunSuite

class DependentProjectTest extends AnyFunSuite with TestHelper {

  test("Ghosted dependent") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          | "packageDirectories": [{"path": "metadata"}],
          | "plugins": {
          |   "dependencies": [
          |     {"namespace": "baz"}
          |   ]
          | }
          |}""".stripMargin,
        "metadata/Dummy.cls" -> "public class Dummy {public void foo() {baz.MyClass.MyMethod();}}"
      )
    ) { root: PathLike =>
      createOrg(root)
      assert(!hasIssues)
    }
  }

  test("MDAPI dependent") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          | "packageDirectories": [{"path": "metadata"}],
          | "plugins": {
          |   "dependencies": [
          |     {"namespace": "baz", "path": "pkg1"}
          |   ]
          | }
          |}""".stripMargin,
        "metadata/Dummy.cls" -> "public class Dummy {public void foo() {baz.MyClass.MyMethod();}}",
        "pkg1/MyClass.cls"   -> "global class MyClass {global static void MyMethod() {}}"
      )
    ) { root: PathLike =>
      createOrg(root)
      assert(!hasIssues)
    }
  }

  test("SFDX dependent") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          | "packageDirectories": [{"path": "metadata"}],
          | "plugins": {
          |   "dependencies": [
          |     {"namespace": "baz", "path": "pkg1"}
          |   ]
          | }
          |}""".stripMargin,
        "metadata/Dummy.cls" -> "public class Dummy {public void foo() {baz.MyClass.MyMethod();}}",
        "pkg1/sfdx-project.json" ->
          """{
          | "namespace": "baz",
          | "packageDirectories": [{"path": "metadata"}]
          |}""".stripMargin,
        "pkg1/metadata/MyClass.cls" -> "global class MyClass {global static void MyMethod() {}}"
      )
    ) { root: PathLike =>
      createOrg(root)
      assert(!hasIssues)
    }
  }
}
