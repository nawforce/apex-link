package com.nawforce.common.pkg

import com.nawforce.common.path.PathLike
import com.nawforce.common.{FileSystemHelper, TestHelper}
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
        "metadata/Dummy.cls" -> "public class Dummy {public void foo() {baz.MyClass.MyMethod();}}")) {
      root: PathLike =>
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
        "pkg1/MyClass.cls" -> "global class MyClass {global static void MyMethod() {}}")) {
      root: PathLike =>
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
        "pkg1/metadata/MyClass.cls" -> "global class MyClass {global static void MyMethod() {}}")) {
      root: PathLike =>
        createOrg(root)
        assert(!hasIssues)
    }
  }
}
