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
package com.nawforce.pkgforce.sfdx

import com.nawforce.pkgforce.diagnostics
import com.nawforce.pkgforce.diagnostics._
import com.nawforce.pkgforce.names.Name
import com.nawforce.pkgforce.path.PathLike
import com.nawforce.pkgforce.workspace.{ModuleLayer, NamespaceLayer}
import com.nawforce.runtime.FileSystemHelper
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class ProjectLayerTest extends AnyFunSuite with BeforeAndAfter with Matchers {

  private var logger: CatchingLogger = _

  before {
    logger = new CatchingLogger
  }

  test("Empty packageDirectories") {
    FileSystemHelper.run(Map("sfdx-project.json" -> "{ \"packageDirectories\": []}")) {
      root: PathLike =>
        val project = SFDXProject(root, logger)
        assert(logger.issues.isEmpty)
        assert(project.nonEmpty)
        assert(project.get.layers(logger).isEmpty)
        assert(
          logger.issues sameElements Array(
            Issue(root.join("sfdx-project.json").toString,
                  diagnostics.Diagnostic(ERROR_CATEGORY,
                    Location(1, 24),
                    "packageDirectories must have at least one entry"))))
    }
  }

  test("Single packageDirectory") {
    FileSystemHelper.run(
      Map("sfdx-project.json" -> "{ \"packageDirectories\": [{\"path\": \"foo\"}]}")) {
      root: PathLike =>
        val project = SFDXProject(root, logger)
        assert(logger.issues.isEmpty)
        assert(project.nonEmpty)

        val dir = root.join("foo")
        project.get.layers(logger) should matchPattern {
          case List(NamespaceLayer(None, List(ModuleLayer(projectPath, path, List()))))
              if projectPath == root && path == dir =>
        }

        assert(logger.issues.isEmpty)
    }
  }

  test("Single packageDirectory, named without version") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{ \"packageDirectories\": [{\"package\": \"name\", \"path\": \"foo\"}]}")) {
      root: PathLike =>
        val project = SFDXProject(root, logger)
        assert(logger.issues.isEmpty)
        assert(project.nonEmpty)

        val dir = root.join("foo")
        project.get.layers(logger) should matchPattern {
          case List(NamespaceLayer(None, List(ModuleLayer(projectPath, path, List()))))
              if projectPath == root && path == dir =>
        }

        assert(
          logger.issues sameElements Array(
            Issue(root.join("sfdx-project.json").toString,
                  Diagnostic(ERROR_CATEGORY,
                             Location(1, 25),
                             "Package 'name' should have a versionNumber"))))
    }
  }

  test("Single packageDirectory, named with version") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{ \"packageDirectories\": [{\"package\": \"name\", \"versionNumber\": \"1.2.3.4\", \"path\": \"foo\"}]}")) {
      root: PathLike =>
        val project = SFDXProject(root, logger)
        assert(logger.issues.isEmpty)
        assert(project.nonEmpty)

        val dir = root.join("foo")
        project.get.layers(logger) should matchPattern {
          case List(NamespaceLayer(None, List(ModuleLayer(projectPath, path, List()))))
              if projectPath == root && path == dir =>
        }

        assert(logger.issues.isEmpty)
    }
  }

  test("Dual packageDirectories, independent") {
    FileSystemHelper.run(
      Map("sfdx-project.json" ->
        """{
          |  "packageDirectories": [
          |    { "package": "first", "versionNumber": "1.2.3.4", "path": "foo"},
          |    { "package": "second", "versionNumber": "4.5.6.7", "path": "bar"}
          |  ]
          |}
          |""".stripMargin)) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(logger.issues.isEmpty)
      assert(project.nonEmpty)

      val dir1 = root.join("foo")
      val dir2 = root.join("bar")
      project.get.layers(logger) should matchPattern {
        case List(
            NamespaceLayer(None,
                           List(ModuleLayer(projectPath1, path1, List()),
                                ModuleLayer(projectPath2, path2, List()))))
            if projectPath1 == root && path1 == dir1 && projectPath2 == root && path2 == dir2 =>
      }

      assert(logger.issues.isEmpty)
    }
  }

  test("Dual packageDirectories, dependent, matching version") {
    FileSystemHelper.run(
      Map("sfdx-project.json" ->
        """{
          |  "packageDirectories": [
          |    { "package": "first", "versionNumber": "1.2.3.4", "path": "foo"},
          |    { "package": "second", "versionNumber": "4.5.6.7", "path": "bar",
          |      "dependencies": [
          |        {"package": "first", "versionNumber": "1.2.3.4" }
          |      ]
          |    }
          |  ]
          |}
          |""".stripMargin)) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(logger.issues.isEmpty)
      assert(project.nonEmpty)

      val dir1 = root.join("foo")
      val dir2 = root.join("bar")
      project.get.layers(logger) should matchPattern {
        case List(
            NamespaceLayer(
              None,
              List(ModuleLayer(projectPath1, path1, List()),
                   ModuleLayer(projectPath2, path2, List(ModuleLayer(projectPath3, path3, List()))),
              )))
            if path1 == dir1 && path2 == dir2 && path3 == dir1 && projectPath1 == root && projectPath2 == root && projectPath3 == root =>
      }

      assert(logger.issues.isEmpty)
    }
  }

  test("Dual packageDirectories, dependent, mismatched version") {
    FileSystemHelper.run(
      Map("sfdx-project.json" ->
        """{
          |  "packageDirectories": [
          |    { "package": "first", "versionNumber": "1.2.3.4", "path": "foo"},
          |    { "package": "second", "versionNumber": "4.5.6.7", "path": "bar",
          |      "dependencies": [
          |        {"package": "first", "versionNumber": "1.2.3.5" }
          |      ]
          |    }
          |  ]
          |}
          |""".stripMargin)) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(logger.issues.isEmpty)
      assert(project.nonEmpty)

      val dir1 = root.join("foo")
      val dir2 = root.join("bar")
      project.get.layers(logger) should matchPattern {
        case List(
            NamespaceLayer(
              None,
              List(ModuleLayer(projectPath1, path1, List()),
                   ModuleLayer(projectPath2, path2, List(ModuleLayer(projectPath3, path3, List()))),
              )))
            if path1 == dir1 && path2 == dir2 && path3 == dir1 && projectPath1 == root && projectPath2 == root && projectPath3 == root =>
      }

      assert(
        logger.issues sameElements Array(Issue(root.join("sfdx-project.json").toString,
                                    diagnostics.Diagnostic(
                                      WARNING_CATEGORY,
                                      Location(6, 8),
                                      "Dependency version '1.2.3.5' for 'first' is not compatible with '1.2.3.4'"))))

    }
  }

  test("Dual packageDirectories, dependent, next & latest version") {
    FileSystemHelper.run(
      Map("sfdx-project.json" ->
        """{
          |  "packageDirectories": [
          |    { "package": "first", "versionNumber": "1.2.3.NEXT", "path": "foo"},
          |    { "package": "second", "versionNumber": "4.5.6.7", "path": "bar",
          |      "dependencies": [
          |        {"package": "first", "versionNumber": "1.2.3.LATEST" }
          |      ]
          |    }
          |  ]
          |}
          |""".stripMargin)) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(logger.issues.isEmpty)
      assert(project.nonEmpty)

      val dir1 = root.join("foo")
      val dir2 = root.join("bar")
      project.get.layers(logger) should matchPattern {
        case List(
            NamespaceLayer(
              None,
              List(ModuleLayer(projectPath1, path1, List()),
                   ModuleLayer(projectPath2, path2, List(ModuleLayer(projectPath3, path3, List()))),
              )))
            if path1 == dir1 && path2 == dir2 && path3 == dir1 && projectPath1 == root && projectPath2 == root && projectPath3 == root =>
      }

      assert(logger.issues.isEmpty)
    }
  }

  test("Single packageDirectory with external ghosted package") {
    FileSystemHelper.run(
      Map("sfdx-project.json" ->
        """{
          |  "packageDirectories": [
          |    { "package": "first", "versionNumber": "1.2.3.4", "path": "foo"}
          |  ],
          |  "plugins": {
          |    "dependencies": [
          |       {"namespace": "ext"}
          |    ]
          |  }
          |}
          |""".stripMargin)) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(logger.issues.isEmpty)
      assert(project.nonEmpty)

      val dir1 = root.join("foo")
      project.get.layers(logger) should matchPattern {
        case List(NamespaceLayer(Some(Name("ext")), List()),
                  NamespaceLayer(None, List(ModuleLayer(projectPath, path1, List()))))
            if path1 == dir1 && projectPath == root =>
      }

      assert(logger.issues.isEmpty)
    }
  }

  test("Single packageDirectory with external MDAPI package") {
    FileSystemHelper.run(
      Map("sfdx-project.json" ->
        """{
          |  "packageDirectories": [
          |    { "package": "first", "versionNumber": "1.2.3.4", "path": "foo"}
          |  ],
          |  "plugins": {
          |    "dependencies": [
          |       {"namespace": "ext", "path": "bar"}
          |    ]
          |  }
          |}
          |""".stripMargin)) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(logger.issues.isEmpty)
      assert(project.nonEmpty)

      val dir1 = root.join("foo")
      val dir2 = root.join("bar")
      project.get.layers(logger) should matchPattern {
        case List(NamespaceLayer(Some(Name("ext")), List(ModuleLayer(projectPath2, path2, Seq()))),
                  NamespaceLayer(None, List(ModuleLayer(projectPath1, path1, List()))))
            if path1 == dir1 && path2 == dir2 && projectPath1 == root && projectPath2 == dir2 =>
      }

      assert(logger.issues.isEmpty)
    }
  }

  test("Single packageDirectory with external SFDX package") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          |  "packageDirectories": [
          |    { "package": "first", "versionNumber": "1.2.3.4", "path": "foo"}
          |  ],
          |  "plugins": {
          |    "dependencies": [
          |       {"path": "pkg"}
          |    ]
          |  }
          |}
          |""".stripMargin,
        "pkg/sfdx-project.json" ->
          """{
            |  "namespace": "pkg",
            |  "packageDirectories": [
            |    { "path": "bar"}
            |  ]
            |}
            |""".stripMargin,
      )) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(logger.issues.isEmpty)
      assert(project.nonEmpty)

      val dir1 = root.join("foo")
      val dir2 = root.join("pkg").join("bar")
      project.get.layers(logger) should matchPattern {
        case List(NamespaceLayer(Some(Name("pkg")), List(ModuleLayer(projectPath2, path2, Seq()))),
                  NamespaceLayer(None, List(ModuleLayer(projectPath1, path1, List()))))
            if path1 == dir1 && path2 == dir2 && projectPath1 == root && projectPath2 == root.join("pkg") =>
      }

      assert(logger.issues.isEmpty)
    }
  }

  test("Single packageDirectory with dual external package") {
    FileSystemHelper.run(
      Map("sfdx-project.json" ->
        """{
          |  "packageDirectories": [
          |    { "package": "first", "versionNumber": "1.2.3.4", "path": "foo"}
          |  ],
          |  "plugins": {
          |    "dependencies": [
          |       {"namespace": "ext1", "path": "bar"},
          |       {"namespace": "ext2", "path": "baz"}
          |    ]
          |  }
          |}
          |""".stripMargin)) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(logger.issues.isEmpty)
      assert(project.nonEmpty)

      val dir1 = root.join("foo")
      val dir2 = root.join("bar")
      val dir3 = root.join("baz")
      project.get.layers(logger) should matchPattern {
        case List(NamespaceLayer(Some(Name("ext1")), List(ModuleLayer(projectPath2, path2, Seq()))),
                  NamespaceLayer(Some(Name("ext2")), List(ModuleLayer(projectPath3, path3, Seq()))),
                  NamespaceLayer(None, List(ModuleLayer(projectPath1, path1, List()))))
            if path1 == dir1 && path2 == dir2 && path3 == dir3 && projectPath1 == root && projectPath2 == dir2 && projectPath3 == dir3 =>
      }

      assert(logger.issues.isEmpty)
    }
  }

  test("Template add layer") {
    FileSystemHelper.run(
      Map("sfdx-project.json" ->
        """{
          |  "packageDirectories": [
          |    { "path": "foo"}
          |  ],
          |  "plugins": {
          |    "templates": {"path": "template", "target": "target"}
          |  }
          |}
          |""".stripMargin)) { root: PathLike =>
      val project = SFDXProject(root, logger)
      assert(logger.issues.isEmpty)
      assert(project.nonEmpty)

      val dir = root.join("foo")
      val dir2 = root.join("template")
      project.get.layers(logger) should matchPattern {
        case List(
            NamespaceLayer(None,
                           List(ModuleLayer(projectPath1, template, List()),
                                ModuleLayer(projectPath2, path, List()))))
            if path == dir && template == dir2 && projectPath1 == root && projectPath2 == root =>
      }

      assert(logger.issues.isEmpty)
    }
  }

}
