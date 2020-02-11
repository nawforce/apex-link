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
package com.nawforce.common.api

import com.nawforce.common.names.Name
import com.nawforce.common.path.PathLike
import com.nawforce.runtime.FileSystemHelper
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class PackageAPITest extends AnyFunSuite with BeforeAndAfter {

  before {
    ServerOps.setParsedDataCaching(false)
  }

  test("type of path") {
    FileSystemHelper.run(Map(
      "classes/Dummy.cls" -> "public class Dummy {}"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())

      assert(pkg.getTypeOfPath(root.join("classes").join("Dummy.cls").toString) == "Dummy")
      assert(pkg.getTypeOfPath(root.join("classes").join("Dummy2.cls").toString).isEmpty)
      assert(pkg.getTypeOfPath(root.join("classes").join("Dummy.object").toString).isEmpty)
      assert(pkg.getTypeOfPath(root.join("classes2").join("Dummy.cls").toString).isEmpty)
      assert(pkg.getTypeOfPath("").isEmpty)
      assert(pkg.getTypeOfPath(null).isEmpty)
    }
  }

  test("type of path with namespace") {
    FileSystemHelper.run(Map(
      "classes/Dummy.cls" -> "public class Dummy {}"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(Some(Name("test")), Seq(root), Seq())

      assert(pkg.getTypeOfPath(root.join("classes").join("Dummy.cls").toString) == "test.Dummy")
      assert(pkg.getTypeOfPath(root.join("classes").join("Dummy2.cls").toString).isEmpty)
      assert(pkg.getTypeOfPath(root.join("classes").join("Dummy.object").toString).isEmpty)
      assert(pkg.getTypeOfPath(root.join("classes2").join("Dummy.cls").toString).isEmpty)
      assert(pkg.getTypeOfPath("").isEmpty)
      assert(pkg.getTypeOfPath(null).isEmpty)
    }
  }

  test("path of type") {
    FileSystemHelper.run(Map(
      "classes/Dummy.cls" -> "public class Dummy {}"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())

      assert(pkg.getPathOfType("Dummy") == "/classes/Dummy.cls")
      assert(pkg.getPathOfType("Dummy2").isEmpty)
      assert(pkg.getPathOfType("test.Dummy").isEmpty)
      assert(pkg.getPathOfType("").isEmpty)
      assert(pkg.getPathOfType(null).isEmpty)
    }
  }

  test("path of type with namespace") {
    FileSystemHelper.run(Map(
      "classes/Dummy.cls" -> "public class Dummy {}"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(Some(Name("test")), Seq(root), Seq())

      assert(pkg.getPathOfType("test.Dummy") == "/classes/Dummy.cls")
      assert(pkg.getPathOfType("test.Dummy2").isEmpty)
      assert(pkg.getPathOfType("Dummy").isEmpty)
      assert(pkg.getPathOfType("").isEmpty)
      assert(pkg.getPathOfType(null).isEmpty)
    }
  }
}
