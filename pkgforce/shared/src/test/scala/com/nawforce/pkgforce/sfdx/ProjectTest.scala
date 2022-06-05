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
package com.nawforce.pkgforce.sfdx

import com.nawforce.pkgforce.diagnostics
import com.nawforce.pkgforce.diagnostics._
import com.nawforce.pkgforce.names.Name
import com.nawforce.pkgforce.path.{Location, PathLike}
import com.nawforce.runtime.FileSystemHelper
import com.nawforce.runtime.platform.Environment
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

import scala.collection.immutable.ArraySeq

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
        logger.issues == ArraySeq(
          Issue(
            root.join("sfdx-project.json"),
            Diagnostic(
              ERROR_CATEGORY,
              Location.empty,
              s"Missing sfdx-project.json file at ${root.join("sfdx-project.json")}"
            )
          )
        )
      )
    }
  }

  test("Empty project file") {
    FileSystemHelper.run(Map("sfdx-project.json" -> "")) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(project.isEmpty)
      assert(
        logger.issues == ArraySeq(
          Issue(
            root.join("sfdx-project.json"),
            diagnostics.Diagnostic(
              ERROR_CATEGORY,
              Location.empty,
              "Failed to parse - ujson.IncompleteParseException: exhausted input"
            )
          )
        )
      )
    }
  }

  test("Badly formed project file") {
    FileSystemHelper.run(Map("sfdx-project.json" -> "{ 'foo': ''")) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(project.isEmpty)
      assert(
        logger.issues == ArraySeq(
          Issue(
            root.join("sfdx-project.json"),
            diagnostics.Diagnostic(
              ERROR_CATEGORY,
              Location.empty,
              "Failed to parse - ujson.ParseException: expected \" got ' (line 1, column 3) at index 2"
            )
          )
        )
      )
    }
  }

  test("Missing packageDirectories") {
    FileSystemHelper.run(Map("sfdx-project.json" -> "{ }")) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(project.isEmpty)
      assert(
        logger.issues == ArraySeq(
          Issue(
            root.join("sfdx-project.json"),
            diagnostics
              .Diagnostic(ERROR_CATEGORY, Location(1, 0), "'packageDirectories' is required")
          )
        )
      )
    }
  }

  test("Wrong type of packageDirectories") {
    FileSystemHelper.run(Map("sfdx-project.json" -> "{ \"packageDirectories\": \"Hello\"}")) {
      root: PathLike =>
        val project = SFDXProject(root, logger)
        assert(project.isEmpty)
        assert(
          logger.issues == ArraySeq(
            Issue(
              root.join("sfdx-project.json"),
              diagnostics.Diagnostic(
                ERROR_CATEGORY,
                Location(1, 24),
                "'packageDirectories' should be an array"
              )
            )
          )
        )
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
      Map("sfdx-project.json" -> "{ \"packageDirectories\": [{\"path\": \"foo\"}]}")
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(logger.issues.isEmpty)
      assert(project.nonEmpty)
      assert(project.get.packageDirectories.size == 1)
      val pd = project.get.packageDirectories.head
      assert(pd.relativePath == "foo")
      assert(pd.name.isEmpty)
      assert(pd.version.isEmpty)
      assert(pd.dependencies.isEmpty)
    }
  }

  test("Single packageDirectory, with name & version") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" -> "{ \"packageDirectories\": [{\"package\": \"name\", \"versionNumber\": \"1.2.3.NEXT\", \"path\": \"foo\"}]}"
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(logger.issues.isEmpty)
      assert(project.nonEmpty)
      assert(project.get.packageDirectories.size == 1)
      val pd = project.get.packageDirectories.head
      assert(pd.relativePath == "foo")
      assert(pd.name.contains("name"))
      assert(pd.version.contains(VersionNumber(1, 2, 3, NextBuild)))
      assert(pd.dependencies.isEmpty)
    }
  }

  test("Single packageDirectory, with dependencies of wrong type") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """
          |{ "packageDirectories": [
          | {
          |   "path": "foo",
          |   "dependencies": "deps"
          | }
          |]}
          |""".stripMargin
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(project.isEmpty)
      assert(
        logger.issues == ArraySeq(
          Issue(
            root.join("sfdx-project.json"),
            diagnostics
              .Diagnostic(ERROR_CATEGORY, Location(5, 19), "'dependencies' should be an array")
          )
        )
      )
    }
  }

  test("Single packageDirectory, with empty dependencies") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """
          |{ "packageDirectories": [
          | {
          |   "path": "foo",
          |   "dependencies": []
          | }
          |]}
          |""".stripMargin
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(project.nonEmpty)
      assert(project.get.packageDirectories.size == 1)
      assert(project.get.packageDirectories.head.dependencies.isEmpty)
    }
  }

  test("Single packageDirectory, with some dependency") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
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
          |""".stripMargin
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(project.nonEmpty)
      assert(project.get.packageDirectories.size == 1)
      assert(project.get.packageDirectories.head.dependencies.size == 1)
      assert(project.get.packageDirectories.head.dependencies.head.name == "myPackage")
      assert(
        project.get.packageDirectories.head.dependencies.head.version
          .contains(VersionNumber(1, 40, 2, Build(12)))
      )
    }
  }

  test("Single packageDirectory, with some dependency missing name") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """
          |{ "packageDirectories": [
          | {
          |   "path": "foo",
          |   "dependencies": [{
          |     "versionNumber": "10.9.8.LATEST"
          |   }]
          | }
          |]}
          |""".stripMargin
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(project.isEmpty)
      assert(
        logger.issues == ArraySeq(
          Issue(
            root.join("sfdx-project.json"),
            diagnostics.Diagnostic(ERROR_CATEGORY, Location(5, 20), "'package' is required")
          )
        )
      )
    }
  }

  test("Single packageDirectories without path") {
    FileSystemHelper.run(Map("sfdx-project.json" -> "{ \"packageDirectories\": [{}]}")) {
      root: PathLike =>
        val project = SFDXProject(root, logger)
        assert(project.isEmpty)
        assert(
          logger.issues == ArraySeq(
            Issue(
              root.join("sfdx-project.json"),
              diagnostics.Diagnostic(ERROR_CATEGORY, Location(1, 25), "'path' is required")
            )
          )
        )
    }
  }

  test("Multiple packageDirectories") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" -> "{ \"packageDirectories\": [{\"path\": \"foo\"}, {\"path\": \"bar\"}]}"
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(logger.issues.isEmpty)
      assert(project.nonEmpty)
      assert(project.get.packageDirectories.size == 2)
      assert(project.get.packageDirectories.head.relativePath == "foo")
      assert(project.get.packageDirectories(1).relativePath == "bar")

    }
  }

  test("Bad path type") {
    FileSystemHelper.run(
      Map("sfdx-project.json" -> "{ \"packageDirectories\": [{\"path\": {}}]}")
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(project.isEmpty)
      assert(
        logger.issues == ArraySeq(
          Issue(
            root.join("sfdx-project.json"),
            diagnostics.Diagnostic(ERROR_CATEGORY, Location(1, 34), "'path' should be a string")
          )
        )
      )
    }
  }

  test("Path outside project") {
    FileSystemHelper.run(
      Map("pkg/sfdx-project.json" -> "{ \"packageDirectories\": [{\"path\": \"../../somewhere\"}]}")
    ) { root: PathLike =>
      val project = SFDXProject(root.join("pkg"), logger)
      assert(project.nonEmpty)
      assert(logger.issues.isEmpty)

      // Path are checked during layer construction
      project.get.layers(logger)

      val pathPrefix = if (Environment.isWindows) "C:\\" else "/"
      assert(
        logger.issues == ArraySeq(
          Issue(
            root.join("pkg").join("sfdx-project.json"),
            diagnostics.Diagnostic(
              ERROR_CATEGORY,
              Location.empty,
              s"Package directory '${pathPrefix}somewhere' is not within the project directory '${pathPrefix}pkg'"
            )
          )
        )
      )
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
      Map("sfdx-project.json" -> "{\"namespace\": {}, \"packageDirectories\": []}")
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(project.isEmpty)
      assert(
        logger.issues == ArraySeq(
          Issue(
            root.join("sfdx-project.json"),
            diagnostics
              .Diagnostic(ERROR_CATEGORY, Location(1, 14), "'namespace' should be a string")
          )
        )
      )
    }
  }

  test("Empty namespace") {
    FileSystemHelper.run(
      Map("sfdx-project.json" -> "{\"namespace\": \"\", \"packageDirectories\": []}")
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(logger.issues.isEmpty)
      assert(project.nonEmpty)
      assert(project.get.namespace.isEmpty)
    }
  }

  test("Whitespace namespace") {
    FileSystemHelper.run(
      Map("sfdx-project.json" -> "{\"namespace\": \" \", \"packageDirectories\": []}")
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(project.isEmpty)
      assert(
        logger.issues == ArraySeq(
          Issue(
            root.join("sfdx-project.json"),
            diagnostics.Diagnostic(
              ERROR_CATEGORY,
              Location(1, 14),
              "' ' is not a valid identifier, can only use characters A-Z, a-z, 0-9 or _"
            )
          )
        )
      )
    }
  }

  test("Invalid namespace") {
    FileSystemHelper.run(
      Map("sfdx-project.json" -> "{\"namespace\": \"foo__bar\", \"packageDirectories\": []}")
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(project.isEmpty)
      assert(
        logger.issues == ArraySeq(
          Issue(
            root.join("sfdx-project.json"),
            diagnostics.Diagnostic(
              ERROR_CATEGORY,
              Location(1, 14),
              "'foo__bar' is not a valid identifier, can not use '__'"
            )
          )
        )
      )
    }
  }

  test("Valid namespace") {
    FileSystemHelper.run(
      Map("sfdx-project.json" -> "{\"namespace\": \"ns\", \"packageDirectories\": []}")
    ) { root: PathLike =>
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
      Map("sfdx-project.json" -> "{\"plugins\": \"foo\", \"packageDirectories\": []}")
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(project.isEmpty)
      assert(
        logger.issues == ArraySeq(
          Issue(
            root.join("sfdx-project.json"),
            diagnostics.Diagnostic(ERROR_CATEGORY, Location(1, 12), "'plugins' should be an object")
          )
        )
      )
    }
  }

  test("Plugins empty") {
    FileSystemHelper.run(
      Map("sfdx-project.json" -> "{\"plugins\": {}, \"packageDirectories\": []}")
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(logger.issues.isEmpty)
      assert(project.get.plugins.isEmpty)
    }
  }

  test("Plugins multiple keys") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" -> "{\"plugins\": {\"foo\": {}, \"bar\": {}}, \"packageDirectories\": []}"
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(logger.issues.isEmpty)
      assert(project.get.plugins.size == 2)
      assert(project.get.plugins.contains("foo"))
      assert(project.get.plugins.contains("bar"))
      assert(project.get.dependencies.isEmpty)
    }
  }

  test("Empty Dependencies") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" -> "{\"plugins\": {\"dependencies\": []}, \"packageDirectories\": []}"
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(logger.issues.isEmpty)
      assert(project.get.plugins.size == 1)
      assert(project.get.plugins.contains("dependencies"))
      assert(project.get.dependencies.isEmpty)
    }
  }

  test("Dependencies missing namespace") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" -> "{\"plugins\": {\"dependencies\": [{}]}, \"packageDirectories\": [{\"path\": \"foo\"}]}"
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(project.nonEmpty)
      assert(logger.issues.isEmpty)

      // Namespace & path are checked during layer construction
      project.get.layers(logger)
      assert(
        logger.issues == ArraySeq(
          Issue(
            root.join("sfdx-project.json"),
            diagnostics.Diagnostic(
              ERROR_CATEGORY,
              Location(1, 30),
              "plugin dependencies must include either a namespace, a path or both"
            )
          )
        )
      )
    }
  }

  test("Dependencies namespace without path") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" -> "{\"plugins\": {\"dependencies\": [{\"namespace\": \"foo\"}] }, \"packageDirectories\": []}"
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(logger.issues.isEmpty)
      assert(project.get.dependencies.nonEmpty)
      assert(project.get.dependencies.size == 1)
      assert(project.get.dependencies.exists(_.namespace.contains(Name("foo"))))
    }
  }

  test("Dependencies namespace with path") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" -> "{\"plugins\": {\"dependencies\": [{\"namespace\": \"foo\", \"path\": \"bar\"}] }, \"packageDirectories\": []}"
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(logger.issues.isEmpty)
      assert(project.get.dependencies.nonEmpty)
      assert(project.get.dependencies.size == 1)
      assert(project.get.dependencies.exists(_.namespace.contains(Name("foo"))))
      assert(project.get.dependencies.exists(_.relativePath.contains("bar")))
    }
  }

  test("Dependencies duplicate namespace") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" -> "{\"namespace\": \"foo\", \"plugins\": {\"dependencies\": [{\"namespace\": \"foo\", \"path\": \"bar\"}] }, \"packageDirectories\": [{\"path\": \"path\"}]}"
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(logger.issues.isEmpty)

      // Duplicate namespace is checked during layer construction
      project.get.layers(logger)
      assert(
        logger.issues == ArraySeq(
          Issue(
            root.join("sfdx-project.json"),
            diagnostics.Diagnostic(
              ERROR_CATEGORY,
              Location(1, 50),
              "plugin additionalNamespaces/dependencies must use unique namespaces"
            )
          )
        )
      )
    }
  }

  test("Dependencies multiple entries") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
        | "packageDirectories": [],
        | "plugins": {
        |   "dependencies": [
        |     {"namespace": "foo", "path": "patha"},
        |     {"namespace": "bar"},
        |     {"namespace": "baz", "path": "pathc"}
        |   ]
        | }
        |}""".stripMargin
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(logger.issues.isEmpty)
      assert(project.get.dependencies.nonEmpty)
      assert(project.get.dependencies.size == 3)
      assert(project.get.dependencies.head.relativePath.contains("patha"))
      assert(project.get.dependencies(1).relativePath.isEmpty)
      assert(project.get.dependencies(2).relativePath.contains("pathc"))
    }
  }

  test("sourceApiVersion") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          | "sourceApiVersion": "Hello",
          | "packageDirectories": []
          |}""".stripMargin
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(project.get.sourceApiVersion.contains("Hello"))
    }
  }

  test("sourceApiVersion bad type") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          | "sourceApiVersion": 23.4,
          | "packageDirectories": []
          |}""".stripMargin
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(project.isEmpty)
      assert(
        logger.issues == ArraySeq(
          Issue(
            root.join("sfdx-project.json"),
            diagnostics
              .Diagnostic(ERROR_CATEGORY, Location(2, 21), "'sourceApiVersion' should be a string")
          )
        )
      )
    }
  }

  test("max dependency count valid") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          | "packageDirectories": [],
          | "plugins": {
          |   "maxDependencyCount": 123
          | }
          |}""".stripMargin
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(project.get.maxDependencyCount.contains(123))
    }
  }

  test("max dependency count zero valid") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          | "packageDirectories": [],
          | "plugins": {
          |   "maxDependencyCount": 0
          | }
          |}""".stripMargin
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(project.get.maxDependencyCount.contains(0))
    }
  }

  test("max dependency count negative invalid") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          | "packageDirectories": [],
          | "plugins": {
          |   "maxDependencyCount": -2
          | }
          |}""".stripMargin
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(project.isEmpty)
      assert(
        logger.issues == ArraySeq(
          Issue(
            root.join("sfdx-project.json"),
            Diagnostic(
              ERROR_CATEGORY,
              Location(4, 25),
              "'maxDependencyCount' value '-2' should be a positive integer"
            )
          )
        )
      )

    }
  }

  test("max dependency count too big invalid") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          | "packageDirectories": [],
          | "plugins": {
          |   "maxDependencyCount": 2147483648
          | }
          |}""".stripMargin
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(project.isEmpty)
      assert(
        logger.issues == ArraySeq(
          Issue(
            root.join("sfdx-project.json"),
            Diagnostic(
              ERROR_CATEGORY,
              Location(4, 25),
              "'maxDependencyCount' value '2147483648' is not an integer"
            )
          )
        )
      )
    }
  }

  test("max dependency count not number") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          | "packageDirectories": [],
          | "plugins": {
          |   "maxDependencyCount": "foo"
          | }
          |}""".stripMargin
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(project.isEmpty)
      assert(
        logger.issues == ArraySeq(
          Issue(
            root.join("sfdx-project.json"),
            Diagnostic(
              ERROR_CATEGORY,
              Location(4, 25),
              "'maxDependencyCount' value '\"foo\"' should be a positive integer"
            )
          )
        )
      )
    }
  }

  test("Empty unpackagedMetadata") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" -> "{\"plugins\": {\"unpackagedMetadata\": []}, \"packageDirectories\": []}"
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(logger.issues.isEmpty)
      assert(project.get.plugins.size == 1)
      assert(project.get.plugins.contains("unpackagedMetadata"))
      assert(project.get.unpackagedMetadata.isEmpty)
    }
  }

  test("unpackagedMetadata not an array") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" -> "{\"plugins\": {\"unpackagedMetadata\": 42}, \"packageDirectories\": []}"
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)

      assert(project.isEmpty)
      assert(
        logger.issues == ArraySeq(
          Issue(
            root.join("sfdx-project.json"),
            Diagnostic(ERROR_CATEGORY, Location(1, 35), "'unpackagedMetadata' should be an array")
          )
        )
      )
    }
  }

  test("unpackagedMetadata single path") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" -> "{\"plugins\": {\"unpackagedMetadata\": [\"path\"]}, \"packageDirectories\": []}"
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(logger.issues.isEmpty)

      assert(project.get.unpackagedMetadata.size == 1)
      val pd = project.get.unpackagedMetadata.head
      assert(pd.relativePath == "path")
      assert(pd.name.isEmpty)
      assert(pd.version.isEmpty)
      assert(pd.dependencies.isEmpty)
    }
  }

  test("unpackagedMetadata multiple paths") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" -> "{\"plugins\": {\"unpackagedMetadata\": [\"path1\", \"path2\"]}, \"packageDirectories\": []}"
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(logger.issues.isEmpty)

      assert(project.get.unpackagedMetadata.size == 2)
      val pd1 = project.get.unpackagedMetadata.head
      assert(pd1.relativePath == "path1")
      val pd2 = project.get.unpackagedMetadata(1)
      assert(pd2.relativePath == "path2")
    }
  }

  test("unpackagedMetadata bad path element") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" -> "{\"plugins\": {\"unpackagedMetadata\": [\"path1\", 10]}, \"packageDirectories\": []}"
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)

      assert(project.isEmpty)
      assert(
        logger.issues == ArraySeq(
          Issue(
            root.join("sfdx-project.json"),
            Diagnostic(
              ERROR_CATEGORY,
              Location(1, 45),
              "'unpackagedMetadata' entries should all be strings"
            )
          )
        )
      )
    }
  }

  test("Empty additionalNamespaces") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" -> "{\"plugins\": {\"additionalNamespaces\": []}, \"packageDirectories\": []}"
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(logger.issues.isEmpty)
      assert(project.get.plugins.size == 1)
      assert(project.get.plugins.contains("additionalNamespaces"))
      assert(project.get.additionalNamespaces.isEmpty)
    }
  }

  test("additionalNamespaces not an array") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" -> "{\"plugins\": {\"additionalNamespaces\": 42}, \"packageDirectories\": []}"
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)

      assert(project.isEmpty)
      assert(
        logger.issues == ArraySeq(
          Issue(
            root.join("sfdx-project.json"),
            Diagnostic(ERROR_CATEGORY, Location(1, 37), "'additionalNamespaces' should be an array")
          )
        )
      )
    }
  }

  test("additionalNamespaces single ns") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" -> "{\"plugins\": {\"additionalNamespaces\": [\"ns\"]}, \"packageDirectories\": []}"
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(logger.issues.isEmpty)

      assert(project.get.additionalNamespaces sameElements Array(Some(Name("ns"))))
      val globs = project.get.metadataGlobs
      assert(globs.length == 1)
      assert(globs.exists(_.startsWith(".apexlink/gulp/ns/**/")))
    }
  }

  test("additionalNamespaces multiple ns") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" -> "{\"plugins\": {\"additionalNamespaces\": [\"ns1\", \"unmanaged\"]}, \"packageDirectories\": []}"
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(logger.issues.isEmpty)

      assert(project.get.additionalNamespaces sameElements Array(Some(Name("ns1")), None))
      val globs = project.get.metadataGlobs
      assert(globs.length == 2)
      assert(globs.exists(_.startsWith(".apexlink/gulp/ns1/**/")))
      assert(globs.exists(_.startsWith(".apexlink/gulp/unmanaged/**/")))
    }
  }

  test("additionalNamespaces bad path element") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" -> "{\"plugins\": {\"additionalNamespaces\": [\"ns\", 10]}, \"packageDirectories\": []}"
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)

      assert(project.isEmpty)
      assert(
        logger.issues == ArraySeq(
          Issue(
            root.join("sfdx-project.json"),
            Diagnostic(
              ERROR_CATEGORY,
              Location(1, 44),
              "'additionalNamespaces' entries should all be strings"
            )
          )
        )
      )
    }
  }

  test("additionalNamespaces duplicate") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" -> "{\"plugins\": {\"additionalNamespaces\": [\"other\", \"ns\", \"another\", \"ns\"]}, \"packageDirectories\": []}"
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)

      assert(project.isEmpty)
      assert(
        logger.issues == ArraySeq(
          Issue(
            root.join("sfdx-project.json"),
            Diagnostic(
              ERROR_CATEGORY,
              Location(1, 37),
              "namespace 'ns' is duplicated in additionalNamespaces'"
            )
          )
        )
      )
    }
  }

  test("additionalNamespaces duplicate (unmanaged)") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" -> "{\"plugins\": {\"additionalNamespaces\": [\"other\", \"unmanaged\", \"another\", \"unmanaged\"]}, \"packageDirectories\": []}"
      )
    ) { root: PathLike =>
      val project = SFDXProject(root, logger)

      assert(project.isEmpty)
      assert(
        logger.issues == ArraySeq(
          Issue(
            root.join("sfdx-project.json"),
            Diagnostic(
              ERROR_CATEGORY,
              Location(1, 37),
              "namespace 'unmanaged' is duplicated in additionalNamespaces'"
            )
          )
        )
      )
    }
  }

}
