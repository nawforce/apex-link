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

import com.nawforce.common.FileSystemHelper
import com.nawforce.common.names._
import com.nawforce.common.org.{OrgImpl, PackageImpl}
import com.nawforce.common.path.PathLike
import com.nawforce.common.types.apex.{FullDeclaration, SummaryDeclaration}
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class UnusedTest extends AnyFunSuite with BeforeAndAfter {

  before {
    ServerOps.setAutoFlush(false)
  }

  after {
    ServerOps.setAutoFlush(true)
  }

  test("Unused method") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {public void foo() {}}")) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.newMDAPIPackageInternal(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
      assert(
        pkg.reportUnused().getMessages(root.join("Dummy.cls").toString) == "" +
          "Unused: line 1 at 32-35: Unused Method 'void foo()'\n")
    }
  }

  test("Method used from method") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {public void foo() {foo();}}")) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.newMDAPIPackageInternal(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
      assert(pkg.reportUnused().getMessages(root.join("Dummy.cls").toString).isEmpty)
    }
  }

  test("Method used from block") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {{foo();} public void foo() {}}")) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.newMDAPIPackageInternal(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
      assert(pkg.reportUnused().getMessages(root.join("Dummy.cls").toString).isEmpty)
    }
  }

  test("Unused field") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {Object a;}")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        val pkg = org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
        assert(
          pkg.reportUnused().getMessages(root.join("Dummy.cls").toString) == "" +
            "Unused: line 1 at 27-28: Unused Field or Property 'a'\n")
    }
  }

  test("Field used from method") {
    FileSystemHelper.run(
      Map("Dummy.cls" -> "public class Dummy {Object a; void foo(){foo(); a = null;}}")) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.newMDAPIPackageInternal(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
      assert(pkg.reportUnused().getMessages(root.join("Dummy.cls").toString).isEmpty)
    }
  }

  test("Field used from block") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {{a = null;} Object a;}")) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.newMDAPIPackageInternal(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
      assert(pkg.reportUnused().getMessages(root.join("Dummy.cls").toString).isEmpty)
    }
  }

  def assertIsFullDeclaration(pkg: PackageImpl,
                              name: String,
                              namespace: Option[Name] = None): Unit = {
    assert(
      pkg
        .findTypes(Seq(TypeName(Name(name)).withNamespace(namespace)))
        .head
        .isInstanceOf[FullDeclaration])
  }

  def assertIsSummaryDeclaration(pkg: PackageImpl,
                                 name: String,
                                 namespace: Option[Name] = None): Unit = {
    assert(
      pkg
        .findTypes(Seq(TypeName(Name(name)).withNamespace(namespace)))
        .head
        .isInstanceOf[SummaryDeclaration])
  }

  test("Used method on summary type") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {public static void foo() {}}",
                             "Caller.cls" -> "public class Caller {{Dummy.Foo();}}",
                         )) { root: PathLike =>
      // Setup as cached
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.newMDAPIPackageInternal(None, Seq(root), Seq())
      assertIsFullDeclaration(pkg, "Dummy")
      assert(!org.issues.hasMessages)
      assert(pkg.reportUnused().getMessages(root.join("Dummy.cls").toString).isEmpty)
      org.flush()

      val org2 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg2 = org2.newMDAPIPackageInternal(None, Seq(root), Seq())
      assertIsSummaryDeclaration(pkg2, "Dummy")
      OrgImpl.current.withValue(org2) {
        pkg2.propagateAllDependencies()
        assert(pkg2.reportUnused().getMessages(root.join("Dummy.cls").toString).isEmpty)
      }
    }
  }

  test("Unused method on summary type") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {public void foo() {}}")) { root: PathLike =>
      // Setup as cached
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.newMDAPIPackageInternal(None, Seq(root), Seq())
      assertIsFullDeclaration(pkg, "Dummy")
      assert(!org.issues.hasMessages)
      assert(
        pkg.reportUnused().getMessages(root.join("Dummy.cls").toString) == "" +
          "Unused: line 1 at 32-35: Unused Method 'void foo()'\n")
      org.flush()

      val org2 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg2 = org2.newMDAPIPackageInternal(None, Seq(root), Seq())
      assertIsSummaryDeclaration(pkg2, "Dummy")
      OrgImpl.current.withValue(org2) {
        assert(
          pkg2.reportUnused().getMessages(root.join("Dummy.cls").toString) == "" +
            "Unused: line 1 at 32-35: Unused Method 'void foo()'\n")
      }
    }
  }
}
