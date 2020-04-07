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

import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.org.{OrgImpl, PackageImpl}
import com.nawforce.common.path.PathLike
import com.nawforce.runtime.FileSystemHelper
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class UpsertTest extends AnyFunSuite with BeforeAndAfter {

  test("no type definition") {
    FileSystemHelper.run(Map(
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(None, Seq(root), Seq()).asInstanceOf[PackageImpl]
      val view = pkg.getViewOfType(root.join("pkg/Foo.cls"), None)
      assert(!view.hasType)
      assert(!pkg.upsertFromView(view))
      assert(!org.issues.hasMessages)
    }
  }

  test("wrong path for type") {
    FileSystemHelper.run(Map(
      "pkg/Foo.cls" -> "public class Foo {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(None, Seq(root), Seq()).asInstanceOf[PackageImpl]
      val view = pkg.getViewOfType(root.join("pkg2/Foo.cls"), Some("public class Foo {}"))
      assert(view.hasType)
      assert(!pkg.upsertFromView(view))
      assert(!org.issues.hasMessages)
    }
  }

  test("valid upsert") {
    FileSystemHelper.run(Map(
      "pkg/Foo.cls" -> "public class Foo {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(None, Seq(root), Seq()).asInstanceOf[PackageImpl]
      val view = pkg.getViewOfType(root.join("pkg/Foo.cls"), Some("public class Foo {}"))
      assert(view.hasType)
      assert(pkg.upsertFromView(view))
      assert(!org.issues.hasMessages)
    }
  }

  test("valid upsert with changes") {
    FileSystemHelper.run(Map(
      "pkg/Foo.cls" -> "public class Foo {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(None, Seq(root), Seq()).asInstanceOf[PackageImpl]
      val view = pkg.getViewOfType(root.join("pkg/Foo.cls"), Some("public class Foo {Object a;}"))
      assert(view.hasType)
      assert(pkg.upsertFromView(view))
      assert(!org.issues.hasMessages)
    }
  }

  test("upsert creates missing") {
    FileSystemHelper.run(Map(
      "pkg/Foo.cls" -> "public class Foo {Bar.Inner b;}",
      "pkg/Bar.cls" -> "public class Bar {public class Inner {}}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(None, Seq(root), Seq()).asInstanceOf[PackageImpl]
      assert(!org.issues.hasMessages)

      val view = pkg.getViewOfType(root.join("pkg/Bar.cls"), Some("public class Bar {}"))
      assert(pkg.upsertFromView(view))
      assert(!org.issues.hasMessages)

      val view2 = pkg.getViewOfType(root.join("pkg/Foo.cls"), None)
      assert(view2.hasType)
      assert(view2.diagnostics.isEmpty)
      assert(!org.issues.hasMessages)

      assert(pkg.upsertFromView(view2))
      assert(org.issues.getMessages("/pkg/Foo.cls")
        == "Missing: line 1 at 28-29: No type declaration found for 'Bar.Inner'\n")
    }
  }

  test("dependencies created") {
    FileSystemHelper.run(Map(
      "pkg/Foo.cls" -> "public class Foo {}",
      "pkg/Bar.cls" -> "public class Bar {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(None, Seq(root), Seq()).asInstanceOf[PackageImpl]
      val view = pkg.getViewOfType(root.join("pkg/Foo.cls"), Some("public class Foo {Bar b;}"))
      assert(view.hasType)
      assert(pkg.upsertFromView(view))

      assert(pkg.getDependencyHolders(TypeName(Name("Foo"))).isEmpty)
      assert(pkg.getDependencies(TypeName(Name("Foo")), inheritanceOnly = false)
        .sameElements(Array(TypeName(Name("Bar")))))

      assert(pkg.getDependencyHolders(TypeName(Name("Bar")))
        .sameElements(Array(TypeName(Name("Foo")))))
      assert(pkg.getDependencies(TypeName(Name("Bar")), inheritanceOnly = false).isEmpty)
    }
  }

  test("dependencies created cross package") {
    FileSystemHelper.run(Map(
      "pkg1/Bar.cls" -> "global class Bar {}",
      "pkg2/Foo.cls" -> "public class Foo {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg1 = org.addPackage(Some(Name("p1")), Seq(root.join("pkg1")), Seq()).asInstanceOf[PackageImpl]
      val pkg2 = org.addPackage(Some(Name("p2")), Seq(root.join("pkg2")), Seq(pkg1)).asInstanceOf[PackageImpl]
      val view = pkg2.getViewOfType(root.join("pkg2/Foo.cls"), Some("public class Foo {p1.Bar b;}"))
      assert(view.hasType)
      assert(view.diagnostics.isEmpty)
      assert(pkg2.upsertFromView(view))

      val fooType = TypeName(Name("Foo")).withOuter(Some(TypeName(Name("p2"))))
      val barType = TypeName(Name("Bar")).withOuter(Some(TypeName(Name("p1"))))

      assert(pkg2.getDependencyHolders(fooType).isEmpty)
      assert(pkg2.getDependencies(fooType, inheritanceOnly = false).sameElements(Array(barType)))

      assert(pkg1.getDependencyHolders(barType).sameElements(Array(fooType)))
      assert(pkg1.getDependencies(barType, inheritanceOnly = false).isEmpty)
    }
  }

  test("valid delete") {
    FileSystemHelper.run(Map(
      "pkg/Foo.cls" -> "public class Foo {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(None, Seq(root), Seq()).asInstanceOf[PackageImpl]
      val view = pkg.getViewOfType(root.join("pkg/Foo.cls"), Some("public class Foo {}"))
      assert(view.hasType)
      assert(!org.issues.hasMessages)
      assert(pkg.upsertFromView(view))

      val fooType = TypeName(Name("Foo"))

      assert(pkg.deleteType(fooType))
      assert(pkg.getPathOfType(fooType) == null)
      assert(!org.issues.hasMessages)
    }
  }

  test("invalid delete") {
    FileSystemHelper.run(Map(
      "pkg/Foo.cls" -> "public class Foo {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(None, Seq(root), Seq()).asInstanceOf[PackageImpl]
      val view = pkg.getViewOfType(root.join("pkg/Foo.cls"), Some("public class Foo {}"))
      assert(view.hasType)
      assert(!org.issues.hasMessages)
      assert(pkg.upsertFromView(view))

      assert(!pkg.deleteType(TypeName(Name("Foo2"))))
      assert(pkg.getPathOfType(TypeName(Name("Foo"))).nonEmpty)
      assert(!org.issues.hasMessages)
    }
  }

  test("delete creates missing") {
    FileSystemHelper.run(Map(
      "pkg/Foo.cls" -> "public class Foo {Bar b;}",
      "pkg/Bar.cls" -> "public class Bar {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(None, Seq(root), Seq()).asInstanceOf[PackageImpl]
      assert(!org.issues.hasMessages)

      assert(pkg.deleteType(TypeName(Name("Bar"))))
      val view = pkg.getViewOfType(root.join("pkg/Foo.cls"), None)
      assert(view.hasType)
      assert(view.diagnostics.isEmpty)
      assert(!org.issues.hasMessages)

      assert(pkg.upsertFromView(view))
      assert(org.issues.getMessages("/pkg/Foo.cls")
        == "Missing: line 1 at 22-23: No type declaration found for 'Bar'\n")
    }
  }
}
