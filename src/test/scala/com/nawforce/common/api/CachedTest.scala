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

import com.nawforce.common.names._
import com.nawforce.common.org.{OrgImpl, PackageImpl}
import com.nawforce.common.path.PathLike
import com.nawforce.common.types.apex.{FullDeclaration, SummaryDeclaration}
import com.nawforce.runtime.FileSystemHelper
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class CachedTest extends AnyFunSuite with BeforeAndAfter {

  before {
    ServerOps.setParsedDataCaching(true)
  }

  after {
    ServerOps.setParsedDataCaching(false)
  }

  def assertIsNotDeclaration(pkg: PackageImpl, name: String, namespace: Option[Name]=None): Unit = {
    assert(pkg.findTypes(Seq(TypeName(Name(name)).withNamespace(namespace))).isEmpty)
  }

  def assertIsFullDeclaration(pkg: PackageImpl, name: String, namespace: Option[Name]=None): Unit = {
    assert(pkg.findTypes(Seq(TypeName(Name(name)).withNamespace(namespace))).head.isInstanceOf[FullDeclaration])
  }

  def assertIsSummaryDeclaration(pkg: PackageImpl, name: String, namespace: Option[Name]=None): Unit = {
    assert(pkg.findTypes(Seq(TypeName(Name(name)).withNamespace(namespace))).head.isInstanceOf[SummaryDeclaration])
  }

  def cacheTest(bar: String, foo:String, newBar: String): Unit = {
    FileSystemHelper.run(Map(
      "Bar.cls" -> bar,
      "Foo.cls" -> foo
    ), setupCache = true) { root: PathLike =>
      // Cache classes
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(None, Seq(root), Seq()).asInstanceOf[PackageImpl]
      assert(!org.issues.hasMessages)
      assertIsFullDeclaration(pkg, "Bar")
      assertIsFullDeclaration(pkg, "Foo")
      org.flush()

      // Reload from cache
      val org2 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg2 = org2.addPackage(None, Seq(root), Seq()).asInstanceOf[PackageImpl]
      assert(!org2.issues.hasMessages)
      assertIsSummaryDeclaration(pkg2, "Bar")
      assertIsSummaryDeclaration(pkg2, "Foo")
      org2.flush()

      // Change super class
      root.createFile("Bar.cls", newBar)
      root.join("Foo.cls").delete()
      val org3 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg3 = org3.addPackage(None, Seq(root), Seq()).asInstanceOf[PackageImpl]
      assert(!org3.issues.hasMessages)
      assertIsFullDeclaration(pkg3, "Bar")
      assertIsNotDeclaration(pkg3, "Foo")
      org3.flush()

      // Test if we notice change, i.e. Foo should not be cached
      root.createFile("Foo.cls", foo)
      val org4 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg4 = org4.addPackage(None, Seq(root), Seq()).asInstanceOf[PackageImpl]
      assert(!org4.issues.hasMessages)
      assertIsSummaryDeclaration(pkg4, "Bar")
      assertIsFullDeclaration(pkg4, "Foo")
      org4.flush()
    }
  }

  test("Cached super class") {
    cacheTest(
      "public virtual class Bar {}",
      "public class Foo extends Bar {}",
      "public virtual class Bar {/* Changed */}"
    )
  }

  test("Cached interface") {
    cacheTest(
      "public interface Bar {}",
      "public class Foo implements Bar {}",
      "public interface Bar {/* Changed */}"
    )
  }

  test("Cached field type") {
    cacheTest(
      "public class Bar {}",
      "public class Foo {Bar field;}",
      "public class Bar {/* Changed */}"
    )
  }

  test("Cached field expr type") {
    cacheTest(
      "public class Bar {}",
      "public class Foo {Object field = new Bar();}",
      "public class Bar {/* Changed */}"
    )
  }

  test("Cached method return type") {
    cacheTest(
      "public class Bar {}",
      "public class Foo {Bar func() {return null;}}",
      "public class Bar {/* Changed */}"
    )
  }

  test("Cached method parameter type") {
    cacheTest(
      "public class Bar {}",
      "public class Foo {void func(Bar a) {}}",
      "public class Bar {/* Changed */}"
    )
  }

  test("Cached method body type") {
    cacheTest(
      "public class Bar {}",
      "public class Foo {void func() {Bar a;}}",
      "public class Bar {/* Changed */}"
    )
  }

  test("Cached constructor parameter type") {
    cacheTest(
      "public class Bar {}",
      "public class Foo {Foo(Bar a) {}}",
      "public class Bar {/* Changed */}"
    )
  }

  test("Cached constructor body type") {
    cacheTest(
      "public class Bar {}",
      "public class Foo {Foo() {Bar a;}}",
      "public class Bar {/* Changed */}"
    )
  }

  test("Cached nested type super class") {
    cacheTest(
      "public virtual class Bar {}",
      "public class Foo {class Nested extends Bar {}}",
      "public virtual class Bar {/* Changed */}"
    )
  }

  test("Cached super class (nested type)") {
    cacheTest(
      "public class Bar {public virtual class Nested {}}",
      "public class Foo extends Bar.Nested {}",
      "public class Bar {public virtual class Nested {} /* Changed */}"
    )
  }

  test("Missing error is not cached") {
    FileSystemHelper.run(Map(
      "pkg1/Foo.cls" -> "public class Foo extends Bar {}",
      "pkg2/Foo.cls" -> "public class Foo extends Bar {}"
    ), setupCache = true) { root: PathLike =>
      // Setup as cached
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(None, Seq(root.join("pkg1")), Seq()).asInstanceOf[PackageImpl]
      assertIsFullDeclaration(pkg, "Foo")
      assert(org.getIssues(new IssueOptions()) == "/pkg1/Foo.cls\nMissing: line 1 at 13-16: No type declaration found for 'Bar'\n")

      val org2 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg2 = org2.addPackage(None, Seq(root.join("pkg2")), Seq()).asInstanceOf[PackageImpl]
      assertIsFullDeclaration(pkg2, "Foo")
      assert(org2.getIssues(new IssueOptions()) == "/pkg2/Foo.cls\nMissing: line 1 at 13-16: No type declaration found for 'Bar'\n")
    }
  }

  test("General error is cached") {
    FileSystemHelper.run(Map(
      "pkg1/Foo.cls" -> "public class Foo {{bar();}}",
      "pkg2/Foo.cls" -> "public class Foo {{bar();}}"
    ), setupCache = true) { root: PathLike =>
      // Setup as cached
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(None, Seq(root.join("pkg1")), Seq()).asInstanceOf[PackageImpl]
      assertIsFullDeclaration(pkg, "Foo")
      assert(org.getIssues(new IssueOptions()) ==
        "/pkg1/Foo.cls\nError: line 1 at 19-24: No matching method found for 'bar' on 'Foo' taking no arguments\n")
      org.flush()

      val org2 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg2 = org2.addPackage(None, Seq(root.join("pkg2")), Seq()).asInstanceOf[PackageImpl]
      assertIsSummaryDeclaration(pkg2, "Foo")
      assert(org2.getIssues(new IssueOptions()) ==
        "/pkg2/Foo.cls\nError: line 1 at 19-24: No matching method found for 'bar' on 'Foo' taking no arguments\n")
    }
  }
}
