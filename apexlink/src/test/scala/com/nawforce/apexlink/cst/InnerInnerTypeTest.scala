package com.nawforce.apexlink.cst

import com.nawforce.apexlink.{FileSystemHelper, TestHelper}
import com.nawforce.pkgforce.path.PathLike
import com.nawforce.runtime.platform.Path
import org.scalatest.funsuite.AnyFunSuite

class InnerInnerTypeTest extends AnyFunSuite with TestHelper {

  test("Inner inner types (no namespace)") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "",
            |"packageDirectories": [{"path": "pkg"}]
            |}""".stripMargin,
        "pkg/Outer.cls" -> """public class Outer { class Inner {
          |class InnerInnerClass {}
          |interface InnerInnerInterface {}
          |enum InnerInnerEnum {}
          |} }""".stripMargin
      )
    ) { root: PathLike =>
      createOrg(root)
      assert(
        getMessages(root.join("pkg").join("Outer.cls")) ==
          "Error: line 2 at 6-21: InnerInnerClass: Inner types of Inner types are not valid.\n" +
            "Error: line 3 at 10-29: InnerInnerInterface: Inner types of Inner types are not valid.\n" +
            "Error: line 4 at 5-19: InnerInnerEnum: Inner types of Inner types are not valid.\n"
      )
    }
  }

  test("Inner types (no namespace)") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "",
            |"packageDirectories": [{"path": "pkg"}]
            |}""".stripMargin,
        "pkg/Outer.cls" -> """public class Outer {
                      |class InnerClass { }
                      |interface InnerInterface { }
                      |enum InnerEnum { } }""".stripMargin
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
    }
  }

  test("Inner inner types (with namespace)") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "pkg",
            |"packageDirectories": [{"path": "pkg"}]
            |}""".stripMargin,
        "pkg/Outer.cls" -> """public class Outer { class Inner {
                             |class InnerInnerClass {}
                             |interface InnerInnerInterface {}
                             |enum InnerInnerEnum {}
                             |} }""".stripMargin
      )
    ) { root: PathLike =>
      createOrg(root)
      assert(
        getMessages(root.join("pkg").join("Outer.cls")) ==
          "Error: line 2 at 6-21: InnerInnerClass: Inner types of Inner types are not valid.\n" +
            "Error: line 3 at 10-29: InnerInnerInterface: Inner types of Inner types are not valid.\n" +
            "Error: line 4 at 5-19: InnerInnerEnum: Inner types of Inner types are not valid.\n"
      )
    }
  }

  test("Inner types (with namespace)") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "pkg",
            |"packageDirectories": [{"path": "pkg"}]
            |}""".stripMargin,
        "pkg/Outer.cls" -> """public class Outer {
                             |class InnerClass { }
                             |interface InnerInterface { }
                             |enum InnerEnum { } }""".stripMargin
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
    }
  }

}
