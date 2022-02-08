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

import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.{FileSystemHelper, TestHelper}
import com.nawforce.pkgforce.names.{Name, TypeIdentifier}
import com.nawforce.pkgforce.path.PathLike
import com.nawforce.runtime.platform.Path
import org.scalatest.funsuite.AnyFunSuite

class InterviewTest extends AnyFunSuite with TestHelper {

  test("Interview createInterview") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy { {Flow.Interview i = Flow.Interview.createInterview('', new Map<String, Object>());} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
    }
  }

  test("Custom flow (MDAPI)") {
    FileSystemHelper.run(
      Map("Test.flow" -> "", "Dummy.cls" -> "public class Dummy { {Flow.Interview.Test;} }")
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
    }
  }

  test("Custom flow (SFDX)") {
    FileSystemHelper.run(
      Map(
        "Test.flow-meta.xml" -> "",
        "Dummy.cls"          -> "public class Dummy { {Flow.Interview.Test;} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
    }
  }

  test("Missing flow") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy { {Flow.Interview.Test;} }")) {
      root: PathLike =>
        createOrg(root)
        // TODO: This should be a missing issue
        assert(
          getMessages(root.join("Dummy.cls")) ==
            "Missing: line 1 at 22-41: Unknown field or type 'Test' on 'Flow.Interview'\n"
        )
    }
  }

  test("Create flow") {
    FileSystemHelper.run(
      Map(
        "Test.flow-meta.xml" -> "",
        "Dummy.cls"          -> "public class Dummy { {Flow.Interview i = new Flow.Interview.Test(new Map<String, Object>());} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
    }
  }

  test("Create flow (namespaced)") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "pkg",
            |"packageDirectories": [{"path": "pkg"}]
            |}""".stripMargin,
        "pkg/Test.flow-meta.xml" -> "",
        "pkg/Dummy.cls"          -> "public class Dummy { {Flow.Interview i = new Flow.Interview.pkg.Test(new Map<String, Object>());} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
    }
  }

  test("Create flow (namespaced but without namespace)") {
    FileSystemHelper.run(
      Map(
        "Test.flow-meta.xml" -> "",
        "Dummy.cls"          -> "public class Dummy { {Flow.Interview i = new Flow.Interview.Test(new Map<String, Object>());} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
    }
  }

  test("Create flow (ghosted)") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          |"namespace": "pkg",
          |"packageDirectories": [{"path": "pkg"}],
          |"plugins": {"dependencies": [{"namespace": "ghosted"}]}
          |}""".stripMargin,
        "pkg/Dummy.cls" -> "public class Dummy { {Flow.Interview i = new Flow.Interview.ghosted.Test(new Map<String, Object>());} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
    }
  }

  test("Create flow (packaged)") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "pkg2",
            |"packageDirectories": [{"path": "pkg2"}],
            |"plugins": {"dependencies": [{"namespace": "pkg1", "path": "pkg1"}]}
            |}""".stripMargin,
        "pkg1/Test.flow-meta.xml" -> "",
        "pkg2/Dummy.cls"          -> "public class Dummy { {Flow.Interview i = new Flow.Interview.pkg1.Test(new Map<String, Object>());} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)

      val pkg1           = org.packagesByNamespace(Some(Name("pkg1")))
      val pkg2           = org.packagesByNamespace(Some(Name("pkg2")))
      val interviewType1 = TypeIdentifier.fromJava(Name("pkg1"), TypeNames.Interview)
      val interviewType2 = TypeIdentifier.fromJava(Name("pkg2"), TypeNames.Interview)
      assert(
        pkg2
          .getDependencies(interviewType2, outerInheritanceOnly = false, apexOnly = false)
          .sameElements(Array(interviewType1))
      )
      assert(
        pkg1
          .getDependencyHolders(interviewType1, apexOnly = false)
          .sameElements(Array(interviewType2))
      )

      val dummyType =
        pkg2.getTypeOfPathInternal(root.join("pkg2").join("Dummy.cls")).get.asTypeIdentifier
      assert(
        pkg2
          .getDependencies(dummyType, outerInheritanceOnly = false, apexOnly = false)
          .sameElements(Array(interviewType2))
      )
      assert(
        pkg2.getDependencyHolders(interviewType2, apexOnly = false).sameElements(Array(dummyType))
      )
    }
  }

  test("Create flow (missing flow)") {
    FileSystemHelper.run(
      Map(
        "Dummy.cls" -> "public class Dummy { {Flow.Interview i = new Flow.Interview.Test(new Map<String, Object>());} }"
      )
    ) { root: PathLike =>
      createOrg(root)
      assert(
        getMessages(root.join("Dummy.cls")) ==
          "Missing: line 1 at 45-64: No type declaration found for 'Flow.Interview.Test'\n"
      )
    }
  }

  test("Start flow") {
    FileSystemHelper.run(
      Map(
        "Test.flow-meta.xml" -> "",
        "Dummy.cls"          -> "public class Dummy { {new Flow.Interview.Test(new Map<String, Object>()).start();} }"
      )
    ) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.isEmpty)
    }
  }
}
