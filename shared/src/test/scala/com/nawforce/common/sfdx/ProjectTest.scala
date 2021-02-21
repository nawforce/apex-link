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

import com.nawforce.common.api.Name
import com.nawforce.common.path.PathLike
import com.nawforce.runtime.FileSystemHelper
import org.scalatest.funsuite.AnyFunSuite

class ProjectTest extends AnyFunSuite {

  test("Missing project file") {
    FileSystemHelper.run(Map()) { root: PathLike =>
      SFDXProject(root) match {
        case Left(err) => assert(err == "Missing project file at /sfdx-project.json")
        case Right(_)  => assert(false)
      }
    }
  }

  test("Empty project file") {
    FileSystemHelper.run(Map("sfdx-project.json" -> "")) { root: PathLike =>
      SFDXProject(root) match {
        case Left(err) =>
          assert(
            err == "Failed to parse '/sfdx-project.json', error: ujson.IncompleteParseException: exhausted input")
        case _ => assert(false)
      }
    }
  }

  test("Badly formed project file") {
    FileSystemHelper.run(Map("sfdx-project.json" -> "{ 'foo': ''")) { root: PathLike =>
      SFDXProject(root) match {
        case Left(err) =>
          assert(
            err == "Failed to parse '/sfdx-project.json', error: ujson.ParseException: expected \" got ' (line 1, column 3) at index 2")
        case _ => assert(false)
      }
    }
  }

  test("Missing packageDirectories") {
    FileSystemHelper.run(Map("sfdx-project.json" -> "{ }")) { root: PathLike =>
      SFDXProject(root) match {
        case Left(err) =>
          assert(
            err == "Failed to parse '/sfdx-project.json', error: 'packageDirectories' is required")
        case Right(_) => assert(false)
      }
    }
  }

  test("Wrong type of packageDirectories") {
    FileSystemHelper.run(Map("sfdx-project.json" -> "{ \"packageDirectories\": \"Hello\"}")) {
      root: PathLike =>
        SFDXProject(root) match {
          case Left(err) =>
            assert(
              err == "Failed to parse '/sfdx-project.json', error: 'packageDirectories' should be an array")
          case Right(_) => assert(false)
        }
    }
  }

  test("Empty packageDirectories") {
    FileSystemHelper.run(Map("sfdx-project.json" -> "{ \"packageDirectories\": []}")) {
      root: PathLike =>
        SFDXProject(root) match {
          case Left(_)        => assert(false)
          case Right(project) => assert(project.packageDirectories.isEmpty)
        }
    }
  }

  test("Single packageDirectories") {
    FileSystemHelper.run(
      Map("sfdx-project.json" -> "{ \"packageDirectories\": [{\"path\": \"foo\"}]}")) {
      root: PathLike =>
        SFDXProject(root) match {
          case Left(_) => assert(false)
          case Right(project) =>
            assert(project.packageDirectories.size == 1)
            assert(project.packageDirectories.head.path == root.join("foo"))
        }
    }
  }

  test("Single packageDirectories without path") {
    FileSystemHelper.run(Map("sfdx-project.json" -> "{ \"packageDirectories\": [{}]}")) {
      root: PathLike =>
        SFDXProject(root) match {
          case Left(err) =>
            assert(
              err == "Failed to parse '/sfdx-project.json', error: 'path' is required for all 'packageDirectories' elements")
          case Right(_) => assert(false)
        }
    }
  }

  test("Multiple packageDirectories") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{ \"packageDirectories\": [{\"path\": \"foo\"}, {\"path\": \"bar\"}]}")) {
      root: PathLike =>
        SFDXProject(root) match {
          case Left(_) => assert(false)
          case Right(project) =>
            assert(project.packageDirectories.size == 2)
            assert(project.packageDirectories.head.path == root.join("foo"))
            assert(project.packageDirectories(1).path == root.join("bar"))
        }
    }
  }

  test("Bad path type") {
    FileSystemHelper.run(Map("sfdx-project.json" -> "{ \"packageDirectories\": [{\"path\": {}}]}")) {
      root: PathLike =>
        SFDXProject(root) match {
          case Left(err) =>
            assert(err == "Failed to parse '/sfdx-project.json', error: 'path' should be a string")
          case Right(_) => assert(false)
        }
    }
  }

  test("No namespace") {
    FileSystemHelper.run(Map("sfdx-project.json" -> "{\"packageDirectories\": []}")) {
      root: PathLike =>
        SFDXProject(root) match {
          case Left(_)        => assert(false)
          case Right(project) => assert(project.namespace.isEmpty)
        }
    }
  }

  test("Invalid namespace json") {
    FileSystemHelper.run(
      Map("sfdx-project.json" -> "{\"namespace\": {}, \"packageDirectories\": []}")) {
      root: PathLike =>
        SFDXProject(root) match {
          case Left(err) =>
            assert(
              err == "Failed to parse '/sfdx-project.json', error: 'namespace' should be a string")
          case Right(_) => assert(false)
        }
    }
  }

  test("Empty namespace") {
    FileSystemHelper.run(
      Map("sfdx-project.json" -> "{\"namespace\": \"\", \"packageDirectories\": []}")) {
      root: PathLike =>
        SFDXProject(root) match {
          case Left(_)        => assert(false)
          case Right(project) => assert(project.namespace.isEmpty)
        }
    }
  }

  test("Whitespace namespace") {
    FileSystemHelper.run(
      Map("sfdx-project.json" -> "{\"namespace\": \" \", \"packageDirectories\": []}")) {
      root: PathLike =>
        SFDXProject(root) match {
          case Left(err) =>
            assert(
              err == "Failed to parse '/sfdx-project.json', " +
                "error: namespace ' ' is not valid, can only use characters A-Z, a-z, 0-9 or _")
          case Right(_) => assert(false)
        }
    }
  }

  test("Invalid namespace") {
    FileSystemHelper.run(
      Map("sfdx-project.json" -> "{\"namespace\": \"foo__bar\", \"packageDirectories\": []}")) {
      root: PathLike =>
        SFDXProject(root) match {
          case Left(err) =>
            assert(
              err == "Failed to parse '/sfdx-project.json', " +
                "error: namespace 'foo__bar' is not valid, can not use '__'")
          case Right(_) => assert(false)
        }
    }
  }

  test("Valid namespace") {
    FileSystemHelper.run(
      Map("sfdx-project.json" -> "{\"namespace\": \"ns\", \"packageDirectories\": []}")) {
      root: PathLike =>
        SFDXProject(root) match {
          case Left(_) => assert(false)
          case Right(project) =>
            assert(project.namespace.contains(Name("ns")))
        }
    }
  }

  test("No plugins") {
    FileSystemHelper.run(Map("sfdx-project.json" -> "{\"packageDirectories\": []}")) {
      root: PathLike =>
        SFDXProject(root) match {
          case Left(_)        => assert(false)
          case Right(project) => assert(project.plugins.isEmpty)
        }
    }
  }

  test("Plugins of wrong type") {
    FileSystemHelper.run(
      Map("sfdx-project.json" -> "{\"plugins\": \"foo\", \"packageDirectories\": []}")) {
      root: PathLike =>
        SFDXProject(root) match {
          case Left(err) =>
            assert(
              err == "Failed to parse '/sfdx-project.json', error: 'plugins' should be an object")
          case Right(_) => assert(false)
        }
    }
  }

  test("Plugins empty") {
    FileSystemHelper.run(
      Map("sfdx-project.json" -> "{\"plugins\": {}, \"packageDirectories\": []}")) {
      root: PathLike =>
        SFDXProject(root) match {
          case Left(_)        => assert(false)
          case Right(project) => assert(project.plugins.isEmpty)
        }
    }
  }

  test("Plugins multiple keys") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{\"plugins\": {\"foo\": {}, \"bar\": {}}, \"packageDirectories\": []}")) {
      root: PathLike =>
        SFDXProject(root) match {
          case Left(_) => assert(false)
          case Right(project) =>
            assert(project.plugins.size == 2)
            assert(project.plugins.contains("foo"))
            assert(project.plugins.contains("bar"))
            assert(project.dependencies.isEmpty)
        }
    }
  }

  test("Empty Dependencies") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{\"plugins\": {\"dependencies\": []}, \"packageDirectories\": []}")) {
      root: PathLike =>
        SFDXProject(root) match {
          case Left(_) => assert(false)
          case Right(project) =>
            assert(project.plugins.size == 1)
            assert(project.plugins.contains("dependencies"))
            assert(project.dependencies.isEmpty)
        }
    }
  }

  test("Dependencies missing namespace") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{\"plugins\": {\"dependencies\": [{}]}, \"packageDirectories\": []}")) {
      root: PathLike =>
        SFDXProject(root) match {
          case Left(err) =>
            assert(
              err == "Failed to parse '/sfdx-project.json', error: 'namespace' is required for each entry in 'dependencies'")
          case Right(_) => assert(false)
        }
    }
  }

  test("Dependencies namespace without uri") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{\"plugins\": {\"dependencies\": [{\"namespace\": \"foo\"}] }, \"packageDirectories\": []}")) {
      root: PathLike =>
        SFDXProject(root) match {
          case Left(ex) => assert(false, ex)
          case Right(project) =>
            assert(project.dependencies.nonEmpty)
            assert(project.dependencies.size == 1)
            assert(project.dependencies.exists(_.namespace == Name("foo")))
        }
    }
  }

  test("Dependencies namespace with path") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{\"plugins\": {\"dependencies\": [{\"namespace\": \"foo\", \"path\": \"bar\"}] }, \"packageDirectories\": []}")) {
      root: PathLike =>
        SFDXProject(root) match {
          case Left(ex) => assert(false, ex)
          case Right(project) =>
            assert(project.dependencies.nonEmpty)
            assert(project.dependencies.size == 1)
            assert(project.dependencies.exists(_.namespace == Name("foo")))
            assert(project.dependencies.exists(_.path.contains(root.join("bar"))))
        }
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
      SFDXProject(root) match {
        case Left(ex) => assert(false, ex)
        case Right(project) =>
          assert(project.dependencies.nonEmpty)
          assert(project.dependencies.size == 3)
          assert(project.dependencies.head.path.contains(root.join("patha")))
          assert(project.dependencies(1).path.isEmpty)
          assert(project.dependencies(2).path.contains(root.join("pathc")))
      }
    }
  }
}
