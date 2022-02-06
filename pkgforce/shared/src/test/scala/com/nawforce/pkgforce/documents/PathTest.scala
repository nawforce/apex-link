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
package com.nawforce.pkgforce.documents

import com.nawforce.pkgforce.path._
import com.nawforce.runtime.FileSystemHelper
import org.scalatest.funsuite.AnyFunSuite

class PathTest extends AnyFunSuite {

  test("join absolute path") {
    FileSystemHelper.run(Map[String, String]("foo" -> "")) { root: PathLike =>
      val abs = root.join("foo")
      assert(root.join("bar").join(abs.toString).toString == abs.toString)
    }
  }

  def stripDrive(path: PathLike): String = {
    val asString = path.toString
    if (asString.matches("^[A-Z]:.+")) {
      asString.substring(2)
    } else {
      asString
    }
  }

  test("root node is a root node") {
    FileSystemHelper.run(Map[String, String]()) { root: PathLike =>
      assert(root.basename == "")
      assert(root.parent == root)
      assert(root.exists)
      assert(root.isRoot)
      assert(root.isDirectory)
      assert(!root.isFile)
      assert(root.size == 0)
      assert(stripDrive(root) == "/")
    }
  }

  test("empty file") {
    FileSystemHelper.run(Map[String, String]("Empty.txt" -> "")) { root: PathLike =>
      val file = root.join("Empty.txt")
      assert(file.basename == "Empty.txt")
      assert(file.parent == root)
      assert(file.exists)
      assert(!file.isRoot)
      assert(!file.isDirectory)
      assert(file.isFile)
      assert(file.size == 0)
      assert(stripDrive(file) == "/Empty.txt")
    }
  }

  test("non-empty file") {
    FileSystemHelper.run(Map[String, String]("Something.txt" -> "Hello")) { root: PathLike =>
      val file = root.join("Something.txt")
      assert(file.basename == "Something.txt")
      assert(file.parent == root)
      assert(file.exists)
      assert(!file.isRoot)
      assert(!file.isDirectory)
      assert(file.isFile)
      assert(file.size == 5)
      assert(stripDrive(file) == "/Something.txt")
    }
  }

  test("directory with file") {
    FileSystemHelper.run(Map[String, String]("Bar/Something.txt" -> "Hello")) { root: PathLike =>
      val file = root.join("Bar/Something.txt")
      val dir  = file.parent
      assert(dir.basename == "Bar")
      assert(dir.parent == root)
      assert(dir.exists)
      assert(!file.isRoot)
      assert(dir.isDirectory)
      assert(!dir.isFile)
      assert(dir.size == 0)
      assert(stripDrive(dir) == "/Bar")

      assert(file.basename == "Something.txt")
      assert(file.parent.parent == root)
      assert(file.exists)
      assert(!file.isRoot)
      assert(!file.isDirectory)
      assert(file.isFile)
      assert(file.size == 5)
      assert(stripDrive(file) == "/Bar/Something.txt")
    }
  }

  test("delete file") {
    FileSystemHelper.run(Map[String, String]("Something.txt" -> "Hello")) { root: PathLike =>
      val file = root.join("Something.txt")
      assert(file.delete().isEmpty)
      assert(!root.join("Something.txt").exists)
    }
  }

  test("delete directory fails if contains files") {
    FileSystemHelper.run(Map[String, String]("Bar/Something.txt" -> "Hello")) { root: PathLike =>
      val file = root.join("Bar")
      assert(file.delete().nonEmpty)
    }
  }

  test("create directory") {
    FileSystemHelper.run(Map[String, String]()) { root: PathLike =>
      val dir = root.createDirectory("Bar").getOrElse(throw new NoSuchElementException())
      assert(dir.isDirectory)
    }
  }

  test("create directory duplicate directory succeeds") {
    FileSystemHelper.run(Map[String, String]()) { root: PathLike =>
      val dir1 = root.createDirectory("Bar").getOrElse(throw new NoSuchElementException())
      val dir2 = root.createDirectory("Bar").getOrElse(throw new NoSuchElementException())
      assert(dir1.isDirectory)
      assert(dir1 == dir2)
    }
  }

  test("create directory over file fails") {
    FileSystemHelper.run(Map[String, String]("Bar" -> "Hello")) { root: PathLike =>
      assert(
        root.createDirectory("Bar").swap.getOrElse(throw new NoSuchElementException()) ==
          s"Can not create directory '${root.join("Bar")}', file already exists"
      )
    }
  }

  test("delete directory") {
    FileSystemHelper.run(Map[String, String]()) { root: PathLike =>
      val file = root.createDirectory("Bar").getOrElse(throw new NoSuchElementException())
      assert(file.delete().isEmpty)
      assert(!root.join("Bar").exists)
    }
  }

  test("create empty file") {
    FileSystemHelper.run(Map[String, String]()) { root: PathLike =>
      val file = root.createFile("Bar", "").getOrElse(throw new NoSuchElementException())
      assert(file.size == 0)
      assert(root.join("Bar").size == 0)
    }
  }

  test("create non-empty file") {
    FileSystemHelper.run(Map[String, String]()) { root: PathLike =>
      val file = root.createFile("Bar", "Hello").getOrElse(throw new NoSuchElementException())
      assert(file.size == 5)
      assert(root.join("Bar").size == 5)
    }
  }

  test("write/read new file") {
    FileSystemHelper.run(Map[String, String]()) { root: PathLike =>
      val file = root.join("Bar.txt")
      assert(file.write("Hello").isEmpty)
      assert(file.read().getOrElse(throw new NoSuchElementException()) == "Hello")
    }
  }

  test("write/read existing file") {
    FileSystemHelper.run(Map[String, String]("Bar.txt" -> "Something")) { root: PathLike =>
      val file = root.join("Bar.txt")
      assert(file.read().getOrElse(throw new NoSuchElementException()) == "Something")
      assert(file.write("Hello").isEmpty)
      assert(file.read().getOrElse(throw new NoSuchElementException()) == "Hello")
    }
  }
}
