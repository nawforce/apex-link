/*
 [The "BSD licence"]
 Copyright (c) 2020 Kevin Jones
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

import com.nawforce.common.path.PathLike
import com.nawforce.common.sfdx.ForceIgnore
import com.nawforce.runtime.FileSystemHelper
import org.scalatest.funsuite.AnyFunSuite

class ForceIgnoreTests extends AnyFunSuite {

  test("Empty ignore") {
    FileSystemHelper.run(Map[String, String](".forceignore" -> "")) { root: PathLike =>
      val ignore =
        ForceIgnore(root.join(".forceignore")).value.getOrElse(throw new NoSuchElementException())
      assert(ignore.includeDirectory(root.join("foo")))
      assert(ignore.includeFile(root.join("foo")))
    }
  }

  test("Simple ignore") {
    FileSystemHelper.run(Map[String, String](".forceignore" -> "foo")) { root: PathLike =>
      val ignore =
        ForceIgnore(root.join(".forceignore")).value.getOrElse(throw new NoSuchElementException())
      assert(!ignore.includeDirectory(root.join("foo")))
      assert(!ignore.includeFile(root.join("foo")))
      assert(ignore.includeDirectory(root.join("foo2")))
      assert(ignore.includeFile(root.join("2foo")))
    }
  }

  test("Directory ignore") {
    FileSystemHelper.run(Map[String, String](".forceignore" -> "foo/")) { root: PathLike =>
      val ignore =
        ForceIgnore(root.join(".forceignore")).value.getOrElse(throw new NoSuchElementException())
      assert(!ignore.includeDirectory(root.join("foo")))
      assert(ignore.includeFile(root.join("foo")))
      assert(ignore.includeDirectory(root.join("foo2")))
      assert(ignore.includeFile(root.join("2foo")))
    }
  }

  test("Simple negate") {
    FileSystemHelper.run(Map[String, String](".forceignore" -> "f*\n!foo")) { root: PathLike =>
      val ignore =
        ForceIgnore(root.join(".forceignore")).value.getOrElse(throw new NoSuchElementException())
      assert(ignore.includeDirectory(root.join("foo")))
      assert(ignore.includeFile(root.join("foo")))
      assert(!ignore.includeDirectory(root.join("foo2")))
      assert(ignore.includeFile(root.join("2foo")))
    }
  }

  test("Directory negate") {
    FileSystemHelper.run(Map[String, String](".forceignore" -> "f*\n!foo/")) { root: PathLike =>
      val ignore =
        ForceIgnore(root.join(".forceignore")).value.getOrElse(throw new NoSuchElementException())
      assert(ignore.includeDirectory(root.join("foo")))
      assert(!ignore.includeFile(root.join("foo")))
      assert(!ignore.includeDirectory(root.join("foo2")))
      assert(ignore.includeFile(root.join("2foo")))
    }
  }
}
