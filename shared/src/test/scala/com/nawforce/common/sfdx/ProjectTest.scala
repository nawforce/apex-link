/*
 [The "BSD licence"]
 Copyright (c) 2019 Kevin Jones
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
package com.nawforce.common.sfdx

import com.nawforce.common.diagnostics
import com.nawforce.common.diagnostics.{CatchingLogger, Diagnostic, ERROR_CATEGORY, Issue, Location}
import com.nawforce.common.names.Name
import com.nawforce.common.path.PathLike
import com.nawforce.runtime.FileSystemHelper
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class ProjectTest extends AnyFunSuite with BeforeAndAfter {

  private var logger: CatchingLogger = _

  before {
    logger = new CatchingLogger
  }

  test("Missing project file") {
    FileSystemHelper.run(Map()) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(project.isEmpty)
      assert(
        logger.issues == List(
          Issue(root.join("sfdx-project.json").toString,
                Diagnostic(ERROR_CATEGORY,
                           Location.empty,
                           "Missing sfdx-project.json file at /sfdx-project.json"))))
    }
  }

  test("Empty project file") {
    FileSystemHelper.run(Map("sfdx-project.json" -> "")) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(project.isEmpty)
      assert(
        logger.issues == List(Issue(root.join("sfdx-project.json").toString,
                                    diagnostics.Diagnostic(
                                      ERROR_CATEGORY,
                                      Location.empty,
                                      "Failed to parse - ujson.IncompleteParseException: exhausted input"))))
    }
  }

  test("Badly formed project file") {
    FileSystemHelper.run(Map("sfdx-project.json" -> "{ 'foo': ''")) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(project.isEmpty)
      assert(
        logger.issues == List(Issue(root.join("sfdx-project.json").toString,
                                    diagnostics.Diagnostic(
                                      ERROR_CATEGORY,
                                      Location.empty,
                                      "Failed to parse - ujson.ParseException: expected \" got ' (line 1, column 3) at index 2"))))
    }
  }

  test("Missing packageDirectories") {
    FileSystemHelper.run(Map("sfdx-project.json" -> "{ }")) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(project.isEmpty)
      assert(
        logger.issues == List(Issue(root.join("sfdx-project.json").toString,
                                    diagnostics.Diagnostic(
                                      ERROR_CATEGORY,
                                      Location.empty,
                                      "$.packageDirectories - 'packageDirectories' is required"))))
    }
  }

  test("Wrong type of packageDirectories") {
    FileSystemHelper.run(Map("sfdx-project.json" -> "{ \"packageDirectories\": \"Hello\"}")) {
      root: PathLike =>
        val project = SFDXProject(root, logger)
        assert(project.isEmpty)
        assert(
          logger.issues == List(Issue(root.join("sfdx-project.json").toString,
                                      diagnostics.Diagnostic(
                                        ERROR_CATEGORY,
                                        Location.empty,
                                        "$.packageDirectories - 'packageDirectories' should be an array"))))
    }
  }

  test("Empty packageDirectories") {
    FileSystemHelper.run(Map("sfdx-project.json" -> "{ \"packageDirectories\": []}")) {
      root: PathLike =>
        val project = SFDXProject(root, logger)
        assert(logger.issues.isEmpty)
        assert(project.nonEmpty)
        assert(project.get.packageDirectories.isEmpty)
    }
  }

  test("Single packageDirectory") {
    FileSystemHelper.run(
      Map("sfdx-project.json" -> "{ \"packageDirectories\": [{\"path\": \"foo\"}]}")) {
      root: PathLike =>
        val project = SFDXProject(root, logger)
        assert(logger.issues.isEmpty)
        assert(project.nonEmpty)
        assert(project.get.packageDirectories.size == 1)
        val pd = project.get.packageDirectories.head
        assert(pd.path == root.join("foo"))
        assert(pd.name.isEmpty)
        assert(pd.version.isEmpty)
        assert(pd.dependencies.isEmpty)
    }
  }

  test("Single packageDirectory, with name & version") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{ \"packageDirectories\": [{\"package\": \"name\", \"versionNumber\": \"1.2.3.NEXT\", \"path\": \"foo\"}]}")) {
      root: PathLike =>
        val project = SFDXProject(root, logger)
        assert(logger.issues.isEmpty)
        assert(project.nonEmpty)
        assert(project.get.packageDirectories.size == 1)
        val pd = project.get.packageDirectories.head
        assert(pd.path == root.join("foo"))
        assert(pd.name.contains("name"))
        assert(pd.version.contains(VersionNumber(1,2,3,NextBuild)))
        assert(pd.dependencies.isEmpty)
    }
  }

  test("Single packageDirectory, with dependencies of wrong type") {
    FileSystemHelper.run(
      Map("sfdx-project.json" ->
        """
          |{ "packageDirectories": [
          | {
          |   "path": "foo",
          |   "dependencies": "deps"
          | }
          |]}
          |""".stripMargin)) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(project.isEmpty)
      assert(
        logger.issues == List(Issue(root.join("sfdx-project.json").toString,
                                    diagnostics.Diagnostic(
                                      ERROR_CATEGORY,
                                      Location.empty,
                                      "$.packageDirectories[0].dependencies - 'dependencies' should be an array"))))
    }
  }

  test("Single packageDirectory, with empty dependencies") {
    FileSystemHelper.run(
      Map("sfdx-project.json" ->
        """
          |{ "packageDirectories": [
          | {
          |   "path": "foo",
          |   "dependencies": []
          | }
          |]}
          |""".stripMargin)) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(project.nonEmpty)
      assert(project.get.packageDirectories.size == 1)
      assert(project.get.packageDirectories.head.dependencies.isEmpty)
    }
  }

  test("Single packageDirectory, with some dependency") {
    FileSystemHelper.run(
      Map("sfdx-project.json" ->
        """
          |{ "packageDirectories": [
          | {
          |   "path": "foo",
          |   "dependencies": [{
          |     "package": "myPackage",
          |     "versionNumber": "1.40.2.12"
          |   }]
          | }
          |]}
          |""".stripMargin)) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(project.nonEmpty)
      assert(project.get.packageDirectories.size == 1)
      assert(project.get.packageDirectories.head.dependencies.size == 1)
      assert(project.get.packageDirectories.head.dependencies.head.name == "myPackage")
      assert(project.get.packageDirectories.head.dependencies.head.version.contains(VersionNumber(1, 40, 2, Build(12))))
    }
  }

  test("Single packageDirectory, with some dependency missing name") {
    FileSystemHelper.run(
      Map("sfdx-project.json" ->
        """
          |{ "packageDirectories": [
          | {
          |   "path": "foo",
          |   "dependencies": [{
          |     "versionNumber": "10.9.8.LATEST"
          |   }]
          | }
          |]}
          |""".stripMargin)) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(project.isEmpty)
      assert(
        logger.issues == List(Issue(root.join("sfdx-project.json").toString,
          diagnostics.Diagnostic(
            ERROR_CATEGORY,
            Location.empty,
            "$.packageDirectories[0].dependencies[0].package - 'package' is required"))))
    }
  }

  test("Single packageDirectories without path") {
    FileSystemHelper.run(Map("sfdx-project.json" -> "{ \"packageDirectories\": [{}]}")) {
      root: PathLike =>
        val project = SFDXProject(root, logger)
        assert(project.isEmpty)
        assert(
          logger.issues == List(Issue(root.join("sfdx-project.json").toString,
                                      diagnostics.Diagnostic(
                                        ERROR_CATEGORY,
                                        Location.empty,
                                        "$.packageDirectories[0].path - 'path' is required"))))
    }
  }

  test("Multiple packageDirectories") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{ \"packageDirectories\": [{\"path\": \"foo\"}, {\"path\": \"bar\"}]}")) {
      root: PathLike =>
        val project = SFDXProject(root, logger)
        assert(logger.issues.isEmpty)
        assert(project.nonEmpty)
        assert(project.get.packageDirectories.size == 2)
        assert(project.get.packageDirectories.head.path == root.join("foo"))
        assert(project.get.packageDirectories(1).path == root.join("bar"))

    }
  }

  test("Bad path type") {
    FileSystemHelper.run(Map("sfdx-project.json" -> "{ \"packageDirectories\": [{\"path\": {}}]}")) {
      root: PathLike =>
        val project = SFDXProject(root, logger)
        assert(project.isEmpty)
        assert(
          logger.issues == List(Issue(root.join("sfdx-project.json").toString,
                                      diagnostics.Diagnostic(
                                        ERROR_CATEGORY,
                                        Location.empty,
                                        "$.packageDirectories[0].path - 'path' should be a string"))))
    }
  }

  test("No namespace") {
    FileSystemHelper.run(Map("sfdx-project.json" -> "{\"packageDirectories\": []}")) {
      root: PathLike =>
        val project = SFDXProject(root, logger)
        assert(logger.issues.isEmpty)
        assert(project.nonEmpty)
        assert(project.get.namespace.isEmpty)
    }
  }

  test("Invalid namespace json") {
    FileSystemHelper.run(
      Map("sfdx-project.json" -> "{\"namespace\": {}, \"packageDirectories\": []}")) {
      root: PathLike =>
        val project = SFDXProject(root, logger)
        assert(project.isEmpty)
        assert(
          logger.issues == List(Issue(root.join("sfdx-project.json").toString,
                                      diagnostics.Diagnostic(
                                        ERROR_CATEGORY,
                                        Location.empty,
                                        "$.namespace - 'namespace' should be a string"))))
    }
  }

  test("Empty namespace") {
    FileSystemHelper.run(
      Map("sfdx-project.json" -> "{\"namespace\": \"\", \"packageDirectories\": []}")) {
      root: PathLike =>
        val project = SFDXProject(root, logger)
        assert(logger.issues.isEmpty)
        assert(project.nonEmpty)
        assert(project.get.namespace.isEmpty)
    }
  }

  test("Whitespace namespace") {
    FileSystemHelper.run(
      Map("sfdx-project.json" -> "{\"namespace\": \" \", \"packageDirectories\": []}")) {
      root: PathLike =>
        val project = SFDXProject(root, logger)
        assert(project.isEmpty)
        assert(
          logger.issues == List(Issue(root.join("sfdx-project.json").toString,
                                      diagnostics.Diagnostic(ERROR_CATEGORY,
                                                 Location.empty,
                                                 "$.namespace - ' ' is not a valid identifier, can only use characters A-Z, a-z, 0-9 or _"))))
    }
  }

  test("Invalid namespace") {
    FileSystemHelper.run(
      Map("sfdx-project.json" -> "{\"namespace\": \"foo__bar\", \"packageDirectories\": []}")) {
      root: PathLike =>
        val project = SFDXProject(root, logger)
        assert(project.isEmpty)
        assert(
          logger.issues == List(Issue(root.join("sfdx-project.json").toString,
                                      diagnostics.Diagnostic(
                                        ERROR_CATEGORY,
                                        Location.empty,
                                        "$.namespace - 'foo__bar' is not a valid identifier, can not use '__'"))))
    }
  }

  test("Valid namespace") {
    FileSystemHelper.run(
      Map("sfdx-project.json" -> "{\"namespace\": \"ns\", \"packageDirectories\": []}")) {
      root: PathLike =>
        val project = SFDXProject(root, logger)
        assert(logger.issues.isEmpty)
        assert(project.nonEmpty)
        assert(project.get.namespace.contains(Name("ns")))
    }
  }

  test("No plugins") {
    FileSystemHelper.run(Map("sfdx-project.json" -> "{\"packageDirectories\": []}")) {
      root: PathLike =>
        val project = SFDXProject(root, logger)
        assert(logger.issues.isEmpty)
        assert(project.nonEmpty)
        assert(project.get.plugins.isEmpty)
    }
  }

  test("Plugins of wrong type") {
    FileSystemHelper.run(
      Map("sfdx-project.json" -> "{\"plugins\": \"foo\", \"packageDirectories\": []}")) {
      root: PathLike =>
        val project = SFDXProject(root, logger)
        assert(project.isEmpty)
        assert(
          logger.issues == List(Issue(root.join("sfdx-project.json").toString,
                                      diagnostics.Diagnostic(
                                        ERROR_CATEGORY,
                                        Location.empty,
                                        "$.plugins - 'plugins' should be an object"))))
    }
  }

  test("Plugins empty") {
    FileSystemHelper.run(
      Map("sfdx-project.json" -> "{\"plugins\": {}, \"packageDirectories\": []}")) {
      root: PathLike =>
        val project = SFDXProject(root, logger)
        assert(logger.issues.isEmpty)
        assert(project.get.plugins.isEmpty)
    }
  }

  test("Plugins multiple keys") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{\"plugins\": {\"foo\": {}, \"bar\": {}}, \"packageDirectories\": []}")) {
      root: PathLike =>
        val project = SFDXProject(root, logger)
        assert(logger.issues.isEmpty)
        assert(project.get.plugins.size == 2)
        assert(project.get.plugins.contains("foo"))
        assert(project.get.plugins.contains("bar"))
        assert(project.get.dependencies.isEmpty)
    }
  }

  test("Empty Dependencies") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{\"plugins\": {\"dependencies\": []}, \"packageDirectories\": []}")) {
      root: PathLike =>
        val project = SFDXProject(root, logger)
        assert(logger.issues.isEmpty)
        assert(project.get.plugins.size == 1)
        assert(project.get.plugins.contains("dependencies"))
        assert(project.get.dependencies.isEmpty)
    }
  }

  test("Dependencies missing namespace") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{\"plugins\": {\"dependencies\": [{}]}, \"packageDirectories\": []}")) {
      root: PathLike =>
        val project = SFDXProject(root, logger)
        assert(project.isEmpty)
        assert(
          logger.issues == List(Issue(root.join("sfdx-project.json").toString,
                                      diagnostics.Diagnostic(
                                        ERROR_CATEGORY,
                                        Location.empty,
                                        "$.plugins.dependencies[0].namespace - 'namespace' is required for each entry in 'dependencies'"))))
    }
  }

  test("Dependencies namespace without uri") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{\"plugins\": {\"dependencies\": [{\"namespace\": \"foo\"}] }, \"packageDirectories\": []}")) {
      root: PathLike =>
        val project = SFDXProject(root, logger)
        assert(logger.issues.isEmpty)
        assert(project.get.dependencies.nonEmpty)
        assert(project.get.dependencies.size == 1)
        assert(project.get.dependencies.exists(_.namespace == Name("foo")))
    }
  }

  test("Dependencies namespace with path") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{\"plugins\": {\"dependencies\": [{\"namespace\": \"foo\", \"path\": \"bar\"}] }, \"packageDirectories\": []}")) {
      root: PathLike =>
        val project = SFDXProject(root, logger)
        assert(logger.issues.isEmpty)
        assert(project.get.dependencies.nonEmpty)
        assert(project.get.dependencies.size == 1)
        assert(project.get.dependencies.exists(_.namespace == Name("foo")))
        assert(project.get.dependencies.exists(_.path.contains(root.join("bar"))))
    }
  }

  test("Dependencies multiple entries") {
    FileSystemHelper.run(
      Map("sfdx-project.json" ->
        """{
        | "packageDirectories": [],
        | "plugins": {
        |   "dependencies": [
        |     {"namespace": "foo", "path": "patha"},
        |     {"namespace": "bar"},
        |     {"namespace": "baz", "path": "pathc"}
        |   ]
        | }
        |}""".stripMargin)) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(logger.issues.isEmpty)
      assert(project.get.dependencies.nonEmpty)
      assert(project.get.dependencies.size == 3)
      assert(project.get.dependencies.head.path.contains(root.join("patha")))
      assert(project.get.dependencies(1).path.isEmpty)
      assert(project.get.dependencies(2).path.contains(root.join("pathc")))
    }
  }
}
