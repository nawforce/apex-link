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
    FileSystemHelper.run(Map(
    )) { root: PathLike =>
      Project(root) match {
        case Left(err) => assert(err == "Missing project file at /sfdx-project.json")
        case Right(_) => assert(false)
      }
    }
  }

  test("Empty project file") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> ""
    )) { root: PathLike =>
      Project(root) match {
        case Left(err) => assert(err == "Failed to parse '/sfdx-project.json', error: ujson.IncompleteParseException: exhausted input")
        case _ => assert(false)
      }
    }
  }

  test("Badly formed project file") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{ 'foo': ''"
    )) { root: PathLike =>
      Project(root) match {
        case Left(err) => assert(err == "Failed to parse '/sfdx-project.json', error: ujson.ParseException: expected \" got ' (line 1, column 3) at index 2")
        case _ => assert(false)
      }
    }
  }

  test("Missing packageDirectories") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{ }"
    )) { root: PathLike =>
      Project(root) match {
        case Left(_) => assert(false)
        case Right(project) => assert(project.packageDirectories.arr.isEmpty)
      }
    }
  }

  test("Wrong type of packageDirectories") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{ \"packageDirectories\": \"Hello\"}"
    )) { root: PathLike =>
      Project(root) match {
        case Left(_) => assert(false)
        case Right(project) => assert(project.packageDirectories.arr.isEmpty)
      }
    }
  }

  test("Empty packageDirectories") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{ \"packageDirectories\": []}"
    )) { root: PathLike =>
      Project(root) match {
        case Left(_) => assert(false)
        case Right(project) => assert(project.packageDirectories.arr.isEmpty)
      }
    }
  }

  test("Single packageDirectories") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{ \"packageDirectories\": [{}]}"
    )) { root: PathLike =>
      Project(root) match {
        case Left(_) => assert(false)
        case Right(project) => assert(project.packageDirectories.arr.size == 1)
      }
    }
  }

  test("Multiple packageDirectories") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{ \"packageDirectories\": [{}, {}, {}]}"
    )) { root: PathLike =>
      Project(root) match {
        case Left(_) => assert(false)
        case Right(project) => assert(project.packageDirectories.arr.size == 3)
      }
    }
  }

  test("Bad path type") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{ \"packageDirectories\": [{\"path\": {}}]}"
    )) { root: PathLike =>
      Project(root) match {
        case Left(_) => assert(false)
        case Right(project) =>
          assert(project.paths.size == 1)
          assert(project.paths.head ==
            Left("Expecting all 'path' properties to be strings in packageDirectories, error: ujson.Value$InvalidData: Expected ujson.Str (data: {})"))
      }
    }
  }

  test("Good path type") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{ \"packageDirectories\": [{\"path\": \"Hello\"}]}"
    )) { root: PathLike =>
      Project(root) match {
        case Left(_) => assert(false)
        case Right(project) =>
          assert(project.paths.size == 1)
          assert(project.paths.head == Right("Hello"))
      }
    }
  }

  test("Multiple paths") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{ \"packageDirectories\": [{\"path\": \"Foo\"}, {\"path\": \"Bar\"}]}"
    )) { root: PathLike =>
      Project(root) match {
        case Left(_) => assert(false)
        case Right(project) =>
          assert(project.paths.size == 2)
          assert(project.paths.head == Right("Foo"))
          assert(project.paths(1) == Right("Bar"))
      }
    }
  }

  test("No namespace") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{}"
    )) { root: PathLike =>
      Project(root) match {
        case Left(_) => assert(false)
        case Right(project) =>
          assert(project.namespace == Right(None))
      }
    }
  }

  test("Invalid namespace json") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{\"namespace\": {}}"
    )) { root: PathLike =>
      Project(root) match {
        case Left(_) => assert(false)
        case Right(project) =>
          assert(project.namespace ==
            Left("Failed to read namespace from sfdx-project.json, error: ujson.Value$InvalidData: Expected ujson.Str (data: {})"))
      }
    }
  }

  test("Empty namespace") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{\"namespace\": \"\"}"
    )) { root: PathLike =>
      Project(root) match {
        case Left(_) => assert(false)
        case Right(project) =>
          assert(project.namespace == Right(None))
      }
    }
  }

  test("Whitespace namespace") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{\"namespace\": \" \"}"
    )) { root: PathLike =>
      Project(root) match {
        case Left(_) =>
          assert(false)
        case Right(project) =>
          assert(project.namespace ==
            Left("Package namespace ' ' in sfdx-project.json is not valid, can only use characters A-Z, a-z, 0-9 or _"))
      }
    }
  }

  test("Invalid namespace") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{\"namespace\": \"foo__bar\"}"
    )) { root: PathLike =>
      Project(root) match {
        case Left(_) =>
          assert(false)
        case Right(project) =>
          assert(project.namespace ==
            Left("Package namespace 'foo__bar' in sfdx-project.json is not valid, can not use '__'"))
      }
    }
  }

  test("Valid namespace") {
    FileSystemHelper.run(Map(
      "sfdx-project.json" -> "{\"namespace\": \"ns\"}"
    )) { root: PathLike =>
      Project(root) match {
        case Left(_) => assert(false)
        case Right(project) =>
          assert(project.namespace == Right(Some(Name("ns"))))
      }
    }
  }
}
