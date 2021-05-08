package com.nawforce.common.api

import com.nawforce.common.FileSystemHelper
import com.nawforce.common.org.OrgImpl
import com.nawforce.common.path.PathLike
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class DependentProjectTest extends AnyFunSuite with BeforeAndAfter {

  /*
  before {
    ServerOps.setAutoFlush(false)
  }

  after {
    ServerOps.setAutoFlush(true)
  }

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
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newSFDXPackageInternal(root)
        assert(!org.issues.hasMessages)
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
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newSFDXPackageInternal(root)
        assert(!org.issues.hasMessages)
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
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newSFDXPackageInternal(root)
        assert(!org.issues.hasMessages)
    }
  }
   */
}
