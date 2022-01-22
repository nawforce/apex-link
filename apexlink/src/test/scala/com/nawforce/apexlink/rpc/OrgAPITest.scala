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

package com.nawforce.apexlink.rpc

import com.nawforce.apexlink.api.ServerOps
import com.nawforce.pkgforce.diagnostics.{Diagnostic, ERROR_CATEGORY, Issue}
import com.nawforce.pkgforce.names.{Name, TypeIdentifier, TypeName}
import com.nawforce.pkgforce.path.Location
import com.nawforce.runtime.platform.{Environment, Path}
import org.scalatest.Assertion
import org.scalatest.funsuite.AsyncFunSuite

import scala.concurrent.Future

class OrgAPITest extends AsyncFunSuite {

  val syntheticDir: Path = {
    if (Path("samples/synthetic").isDirectory)
      Path("samples/synthetic")
    else
      Path("../samples/synthetic")
  }

  test("Version not empty") {
    val orgAPI = OrgAPI()
    for {
      result <- orgAPI.version()
    } yield {
      assert(result.nonEmpty)
    }
  }

  test("Set cache dir empty") {
    val orgAPI = OrgAPI()
    for {
      _ <- orgAPI.setCacheDirectory(None)
    } yield {
      assert(Environment.cacheDir.isEmpty)
      assert(!ServerOps.getAutoFlush)
    }
  }

  test("Set cache dir pwd") {
    val orgAPI = OrgAPI()
    Path("").createDirectory("cacheTest")
    val testPath = Path("").join("cacheTest")
    for {
      _ <- orgAPI.setCacheDirectory(Some(testPath.toString))
    } yield {
      val result: Assertion = assert(
        Environment.cacheDir.contains(testPath) && ServerOps.getAutoFlush)
      result.onComplete(_ => {
        Environment.setCacheDirOverride(None)
        testPath.delete()
      })
      result
    }
  }

  test("Add package not bad directory") {
    val orgAPI = OrgAPI()
    for {
      result <- orgAPI.open("/silly")
      issues <- orgAPI.getIssues(includeWarnings = false, maxIssuesPerFile = 0)
    } yield {
      assert(result.error.isEmpty)
      assert(
        issues.issues sameElements Array(
          Issue(Path("/silly"), Diagnostic(ERROR_CATEGORY, Location.empty, "No directory at /silly"))))
    }
  }

  test("Add package MDAPI directory") {
    val workspace = syntheticDir.join("mdapi-test")
    val orgAPI = OrgAPI()
    for {
      result <- orgAPI.open(workspace.toString)
      issues <- orgAPI.getIssues(includeWarnings = false, maxIssuesPerFile = 0)
    } yield {
      assert(result.error.isEmpty)
      assert(issues.issues.forall(_.diagnostic.category != ERROR_CATEGORY))
    }
  }

  test("Add package sfdx directory (relative)") {
    val orgAPI = OrgAPI()
    for {
      result <- orgAPI.open(syntheticDir.join("sfdx-test").toString())
      issues <- orgAPI.getIssues(includeWarnings = false, maxIssuesPerFile = 0)
    } yield {
      assert(result.error.isEmpty && result.namespaces.sameElements(Array("")))
      assert(issues.issues.forall(_.diagnostic.category != ERROR_CATEGORY))
    }
  }

  test("Add package sfdx directory (absolute)") {
    val workspace = syntheticDir.join("sfdx-test")
    val orgAPI = OrgAPI()
    for {
      result <- orgAPI.open(workspace.toString)
      issues <- orgAPI.getIssues(includeWarnings = false, maxIssuesPerFile = 0)
    } yield {
      assert(result.error.isEmpty && result.namespaces.sameElements(Array("")))
      assert(issues.issues.forall(_.diagnostic.category != ERROR_CATEGORY))
    }
  }

  test("Add package sfdx directory with ns (relative)") {
    val orgAPI = OrgAPI()
    for {
      result <- orgAPI.open(syntheticDir.join("sfdx-ns-test").toString())
      issues <- orgAPI.getIssues(includeWarnings = false, maxIssuesPerFile = 0)
    } yield {
      assert(result.error.isEmpty && result.namespaces.sameElements(Array("sfdx_test", "")))
      assert(!issues.issues.exists(_.diagnostic.category == ERROR_CATEGORY))
    }
  }

  test("Add package sfdx directory with ns (absolute)") {
    val workspace = syntheticDir.join("sfdx-ns-test")
    val orgAPI = OrgAPI()
    for {
      result <- orgAPI.open(workspace.toString)
      issues <- orgAPI.getIssues(includeWarnings = false, maxIssuesPerFile = 0)
    } yield {
      assert(result.error.isEmpty && result.namespaces.sameElements(Array("sfdx_test", "")))
      assert(!issues.issues.exists(_.diagnostic.category == ERROR_CATEGORY))
    }
  }

  test("Get Issues") {
    val workspace = syntheticDir.join("sfdx-ns-test")
    val orgAPI = OrgAPI()

    val pkg: Future[Assertion] = orgAPI.open(workspace.toString) map { result =>
      assert(result.error.isEmpty && result.namespaces.sameElements(Array("sfdx_test", "")))
    }

    val issues: Future[Assertion] = pkg flatMap { _ =>
      orgAPI.getIssues(includeWarnings = true, maxIssuesPerFile = 0) map { issuesResult =>
        assert(issuesResult.issues.length == 4)
        assert(issuesResult.issues.count(_.path.toString.contains("SingleError")) == 1)
        assert(issuesResult.issues.count(_.path.toString.contains("DoubleError")) == 2)
      }
    }

    issues
  }

  test("Get Dependency Graph (zero depth)") {
    val workspace = syntheticDir.join("mdapi-test")
    val orgAPI = OrgAPI()
    for {
      result <- orgAPI.open(workspace.toString)
      graph <- orgAPI.dependencyGraph(
        IdentifiersRequest(Array(TypeIdentifier(None, TypeName(Name("Hello"))))),
        depth = 0, apexOnly = true, IdentifiersRequest(Array()))
    } yield {
      assert(result.error.isEmpty)
      assert(graph.nodeData.length == 1)
      assert(graph.linkData.isEmpty)
    }
  }

  test("Get Dependency Graph (some depth)") {
    val workspace = syntheticDir.join("mdapi-test")
    val orgAPI = OrgAPI()
    for {
      result <- orgAPI.open(workspace.toString)
      graph <- orgAPI.dependencyGraph(
        IdentifiersRequest(Array(TypeIdentifier(None, TypeName(Name("Hello"))))),
        depth = 1, apexOnly = true, IdentifiersRequest(Array()))
    } yield {
      assert(result.error.isEmpty)
      assert(
        graph.nodeData sameElements Array(
          DependencyNode(TypeIdentifier(None, TypeName(Name("Hello"))),
            85,
            "class",
            1,
            Array(),
            Array(),
            Array(TypeIdentifier(None, TypeName(Name("World"))))),
          DependencyNode(TypeIdentifier(None, TypeName(Name("World"))),
            71,
            "class",
            0,
            Array(),
            Array(),
            Array()),
        ))
      assert(graph.linkData sameElements Array(DependencyLink(0, 1, "uses")))
    }
  }

  test("Get Dependency Graph (some depth) with ignored identifiers") {
    val workspace = syntheticDir.join("mdapi-test")
    val orgAPI = OrgAPI()
    for {
      result <- orgAPI.open(workspace.toString)
      graph <- orgAPI.dependencyGraph(
        IdentifiersRequest(Array(TypeIdentifier(None, TypeName(Name("Hello"))))),
        depth = 1, apexOnly = true, IdentifiersRequest(Array(TypeIdentifier(None, TypeName(Name("World"))))))
    } yield {
      assert(result.error.isEmpty)
      assert(
        graph.nodeData sameElements Array(
          DependencyNode(TypeIdentifier(None, TypeName(Name("Hello"))),
            85,
            "class",
            0,
            Array(),
            Array(),
            Array())
        ))
      assert(graph.linkData.isEmpty)
    }
  }

  test("Get Dependency Graph (bad identifier))") {
    val workspace = syntheticDir.join("mdapi-test")
    val orgAPI = OrgAPI()
    for {
      result <- orgAPI.open(workspace.toString)
      graph <- orgAPI.dependencyGraph(
        IdentifiersRequest(Array(TypeIdentifier(None, TypeName(Name("Dummy"))))),
        depth = 0, apexOnly = true, IdentifiersRequest(Array()))
    } yield {
      assert(result.error.isEmpty)
      assert(graph.nodeData.isEmpty)
      assert(graph.linkData.isEmpty)
    }
  }

  test("Get Test Class Names (with test class)") {
    val workspace = syntheticDir.join("test-classes")
    val orgAPI = OrgAPI()
    for {
      result <- orgAPI.open(workspace.toString)
      classes <- orgAPI.getTestClassNames(new GetTestClassNamesRequest(
        Array(workspace.toString + "/force-app/main/default/classes/HelloTest.cls"), false))
    } yield {
      assert(result.error.isEmpty)
      assert(classes.testClassNames.length == 1)
      assert(classes.testClassNames(0) == "HelloTest")
    }
  }

  test("Get Test Class Names (find test class)") {
    val workspace = syntheticDir.join("test-classes")
    val orgAPI = OrgAPI()
    for {
      result <- orgAPI.open(workspace.toString)
      classes <- orgAPI.getTestClassNames(new GetTestClassNamesRequest(
        Array(workspace.toString + "/force-app/main/default/classes/Hello.cls"), true))
    } yield {
      assert(result.error.isEmpty)
      assert(classes.testClassNames.toSet == Set("HelloTest"))
    }
  }

  test("Get Test Class Names (no test class)") {
    val workspace = syntheticDir.join("test-classes")
    val orgAPI = OrgAPI()
    for {
      result <- orgAPI.open(workspace.toString)
      classes <- orgAPI.getTestClassNames(new GetTestClassNamesRequest(
        Array(workspace.toString + "/force-app/main/default/classes/NoTest.cls"), true))
    } yield {
      assert(result.error.isEmpty)
      assert(classes.testClassNames.isEmpty)
    }
  }

  test("Get Test Class Names (indirect to inner interface)") {
    val workspace = syntheticDir.join("test-classes")
    val orgAPI = OrgAPI()
    for {
      result <- orgAPI.open(workspace.toString)
      classes <- orgAPI.getTestClassNames(new GetTestClassNamesRequest(
        Array(workspace.toString + "/force-app/main/default/classes/ServiceImpl.cls"), true))
    } yield {
      assert(result.error.isEmpty)
      assert(classes.testClassNames.toSet == Set("ServiceAPITest"))
    }
  }

  test("Get Test Class Names (indirect to interface)") {
    val workspace = syntheticDir.join("test-classes")
    val orgAPI = OrgAPI()
    for {
      result <- orgAPI.open(workspace.toString)
      classes <- orgAPI.getTestClassNames(new GetTestClassNamesRequest(
        Array(workspace.toString + "/force-app/main/default/classes/APIImpl.cls"), true))
    } yield {
      assert(result.error.isEmpty)
      assert(classes.testClassNames.toSet == Set("APITest"))
    }
  }

  test("Get Test Class Names (indirect to inner implementation)") {
    val workspace = syntheticDir.join("test-classes")
    val orgAPI = OrgAPI()
    for {
      result <- orgAPI.open(workspace.toString)
      classes <- orgAPI.getTestClassNames(new GetTestClassNamesRequest(
        Array(workspace.toString + "/force-app/main/default/classes/InnerServiceImpl.cls"), true))
    } yield {
      assert(result.error.isEmpty)
      assert(classes.testClassNames.toSet == Set("ServiceAPITest"))
    }
  }

  test("Get Test Class Names (service)") {
    val workspace = syntheticDir.join("test-classes")
    val orgAPI = OrgAPI()
    for {
      result <- orgAPI.open(workspace.toString)
      classes <- orgAPI.getTestClassNames(new GetTestClassNamesRequest(
        Array(workspace.toString + "/force-app/main/default/classes/Service.cls"), true))
    } yield {
      assert(result.error.isEmpty)
      assert(classes.testClassNames.toSet == Set("ServiceAPITest", "ServiceTest"))
    }
  }

  test("Get Test Class Names (with superclass)") {
    val workspace = syntheticDir.join("test-classes")
    val orgAPI = OrgAPI()
    for {
      result <- orgAPI.open(workspace.toString)
      classes <- orgAPI.getTestClassNames(new GetTestClassNamesRequest(
        Array(workspace.toString + "/force-app/main/default/classes/Derived.cls"), true))
    } yield {
      assert(result.error.isEmpty)
      assert(classes.testClassNames.toSet == Set("DerivedTest", "BaseTest", "APITest"))
    }
  }

  test("Get DependencyCounts") {
    val workspace = syntheticDir.join("dependency-counts")
    val orgAPI = OrgAPI()
    for {
      result <- orgAPI.open(workspace.toString)
      dependencyCounts <- orgAPI.getDependencyCounts(new GetDependencyCountsRequest(
        Array(
          workspace.toString + "/force-app/main/default/classes/NoDeps.cls",
          workspace.toString + "/force-app/main/default/classes/SingleDep.cls",
          workspace.toString + "/force-app/main/default/classes/TransDep.cls")))
    } yield {
      assert(result.error.isEmpty)
      assert(dependencyCounts.counts.length == 3)
      assert(dependencyCounts.counts.filter(c => c.path.contains("TransDep")).map(_.count).apply(0) == 2)
      assert(dependencyCounts.counts.filter(c => c.path.contains("SingleDep")).map(_.count).apply(0) == 1)
      assert(dependencyCounts.counts.filter(c => c.path.contains("NoDeps")).map(_.count).apply(0) == 0)
    }
  }

}
