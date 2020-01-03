/*
 [The "BSD licence"]
 Copyright (c) 2017 Kevin Jones
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
package com.nawforce.common.documents

import com.nawforce.common.path._
import com.nawforce.runtime.FileSystemHelper
import org.scalatest.funsuite.AnyFunSuite

class PathTest extends AnyFunSuite {

  test("join absolute path") {
    FileSystemHelper.run(Map[String, String](
      "foo" -> ""
    )) { root: PathLike =>
      val abs = root.join("foo")
      assert(root.join("bar").join(abs.toString).toString == abs.toString)
    }
  }

  test("root node is a root node") {
    FileSystemHelper.run(Map[String, String] (
    )) { root: PathLike =>
      assert(root.basename == "")
      assert(root.parent == root)
      assert(root.nature == DIRECTORY)
      assert(root.toString == "/")
    }
  }

  test("empty file") {
    FileSystemHelper.run(Map[String, String] (
      "Empty.txt" -> ""
    )) { root: PathLike =>
      val file = root.join("Empty.txt")
      assert(file.basename == "Empty.txt")
      assert(file.parent == root)
      assert(file.nature == EMPTY_FILE)
      assert(file.toString == "/Empty.txt")
    }
  }

  test("non-empty file") {
    FileSystemHelper.run(Map[String, String] (
      "Something.txt" -> "Hello"
    )) { root: PathLike =>
      val file = root.join("Something.txt")
      assert(file.basename == "Something.txt")
      assert(file.parent == root)
      assert(file.nature == NONEMPTY_FILE)
      assert(file.toString == "/Something.txt")
    }
  }

  test("directory with file") {
    FileSystemHelper.run(Map[String, String] (
      "Bar/Something.txt" -> "Hello"
    )) { root: PathLike =>
      val file = root.join("Bar/Something.txt")
      val dir = file.parent
      assert(dir.basename == "Bar")
      assert(dir.parent == root)
      assert(dir.nature == DIRECTORY)
      assert(dir.toString == "/Bar")

      assert(file.basename == "Something.txt")
      assert(file.parent.parent == root)
      assert(file.nature == NONEMPTY_FILE)
      assert(file.toString == "/Bar/Something.txt")
    }
  }

  test("delete file") {
    FileSystemHelper.run(Map[String, String] (
      "Something.txt" -> "Hello"
    )) { root: PathLike =>
      val file = root.join("Something.txt")
      assert(file.delete().isEmpty)
      assert(root.join("Something.txt").nature == DOES_NOT_EXIST)
    }
  }

  test("delete directory fails if contains files") {
    FileSystemHelper.run(Map[String, String] (
      "Bar/Something.txt" -> "Hello"
    )) { root: PathLike =>
      val file = root.join("Bar")
      assert(file.delete().nonEmpty)
    }
  }

  test("create directory") {
    FileSystemHelper.run(Map[String, String] (
    )) { root: PathLike =>
      val dir = root.createDirectory("Bar").right.get
      assert(dir.nature == DIRECTORY)
    }
  }

  test("create directory duplicate directory succeeds") {
    FileSystemHelper.run(Map[String, String] (
    )) { root: PathLike =>
      val dir1 = root.createDirectory("Bar").right.get
      val dir2 = root.createDirectory("Bar").right.get
      assert(dir1.nature == DIRECTORY)
      assert(dir1 == dir2)
    }
  }

  test("create directory over file fails") {
    FileSystemHelper.run(Map[String, String] (
      "Bar" -> "Hello"
    )) { root: PathLike =>
      assert(root.createDirectory("Bar").left.get == "Can not create directory '/Bar', file already exists")
    }
  }

  test("delete directory") {
    FileSystemHelper.run(Map[String, String] (
    )) { root: PathLike =>
      val file = root.createDirectory("Bar").right.get
      assert(file.delete().isEmpty)
      assert(root.join("Bar").nature == DOES_NOT_EXIST)
    }
  }

  test("create empty file") {
    FileSystemHelper.run(Map[String, String] (
    )) { root: PathLike =>
      val file = root.createFile("Bar", "").right.get
      assert(file.nature == EMPTY_FILE)
      assert(root.join("Bar").nature == EMPTY_FILE)
    }
  }

  test("create non-empty file") {
    FileSystemHelper.run(Map[String, String] (
    )) { root: PathLike =>
      val file = root.createFile("Bar", "Hello").right.get
      assert(file.nature == NONEMPTY_FILE)
      assert(root.join("Bar").nature == NONEMPTY_FILE)
    }
  }

  test("write/read new file") {
    FileSystemHelper.run(Map[String, String] (
    )) { root: PathLike =>
      val file = root.join("Bar.txt")
      assert(file.write("Hello").isEmpty)
      assert(file.read().right.get == "Hello")
    }
  }

  test("write/read existing file") {
    FileSystemHelper.run(Map[String, String] (
      "Bar.txt" -> "Something"
    )) { root: PathLike =>
      val file = root.join("Bar.txt")
      assert(file.read().right.get == "Something")
      assert(file.write("Hello").isEmpty)
      assert(file.read().right.get == "Hello")
    }
  }
}
