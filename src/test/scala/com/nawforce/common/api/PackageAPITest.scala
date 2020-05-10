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

import com.nawforce.common.documents.ParsedCache
import com.nawforce.common.org.{OrgImpl, PackageImpl}
import com.nawforce.common.path.PathLike
import com.nawforce.common.types.apex.SummaryDeclaration
import com.nawforce.runtime.FileSystemHelper
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class PackageAPITest extends AnyFunSuite with BeforeAndAfter {

  before {
    ServerOps.setParsedDataCaching(false)
  }

  test("type of path") {
    FileSystemHelper.run(Map(
      "classes/Dummy.cls" -> "public class Dummy {}",
      "triggers/Foo.trigger" -> "trigger Foo on Account (before insert) {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getTypeOfPath(null) == null)
      assert(pkg.getTypeOfPath("") == null)

      assert(pkg.getTypeOfPath(root.join("classes").join("Dummy.cls").toString).toString == "Dummy")
      assert(pkg.getTypeOfPath(root.join("classes").join("Dummy2.cls").toString) == null)
      assert(pkg.getTypeOfPath(root.join("classes").join("Dummy.object").toString) == null)
      assert(pkg.getTypeOfPath(root.join("classes2").join("Dummy.cls").toString) == null)

      assert(pkg.getTypeOfPath(root.join("triggers").join("Foo.trigger").toString).toString == "__sfdc_trigger/Foo")
      assert(pkg.getTypeOfPath(root.join("triggers").join("Foo2.trigger").toString) == null)
      assert(pkg.getTypeOfPath(root.join("triggers").join("Foo.object").toString) == null)
      assert(pkg.getTypeOfPath(root.join("triggers2").join("Foo2.trigger").toString) == null)
    }
  }

  test("type of path with namespace") {
    FileSystemHelper.run(Map(
      "classes/Dummy.cls" -> "public class Dummy {}",
      "triggers/Foo.trigger" -> "trigger Foo on Account (before insert) {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(Some(Name("test")), Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getTypeOfPath(null) == null)
      assert(pkg.getTypeOfPath("") == null)

      assert(pkg.getTypeOfPath(root.join("classes").join("Dummy.cls").toString).toString == "test.Dummy (test)")
      assert(pkg.getTypeOfPath(root.join("classes").join("Dummy2.cls").toString) == null)
      assert(pkg.getTypeOfPath(root.join("classes").join("Dummy.object").toString) == null)
      assert(pkg.getTypeOfPath(root.join("classes2").join("Dummy.cls").toString) == null)

      assert(pkg.getTypeOfPath(root.join("triggers").join("Foo.trigger").toString).toString == "__sfdc_trigger/test/Foo (test)")
      assert(pkg.getTypeOfPath(root.join("triggers").join("Foo2.trigger").toString) == null)
      assert(pkg.getTypeOfPath(root.join("triggers").join("Foo.object").toString) == null)
      assert(pkg.getTypeOfPath(root.join("triggers2").join("Foo2.trigger").toString) == null)
    }
  }

  test("path of type") {
    FileSystemHelper.run(Map(
      "classes/Dummy.cls" -> "public class Dummy {}",
      "triggers/Foo.trigger" -> "trigger Foo on Account (before insert) {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      val dummyType = pkg.getTypeOfPath(root.join("classes").join("Dummy.cls").toString)
      val fooType = pkg.getTypeOfPath(root.join("triggers").join("Foo.trigger").toString)

      assert(pkg.getPathsOfType(null).isEmpty)

      assert(dummyType.toString == "Dummy")
      assert(pkg.getPathsOfType(dummyType).sameElements(Array("/classes/Dummy.cls")))

      assert(fooType.toString == "__sfdc_trigger/Foo")
      assert(pkg.getPathsOfType(fooType).sameElements(Array("/triggers/Foo.trigger")))
    }
  }

  test("path of type with namespace") {
    FileSystemHelper.run(Map(
      "classes/Dummy.cls" -> "public class Dummy {}",
      "triggers/Foo.trigger" -> "trigger Foo on Account (before insert) {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(Some(Name("test")), Seq(root), Seq())
      assert(!org.issues.hasMessages)

      val dummyType = pkg.getTypeOfPath(root.join("classes").join("Dummy.cls").toString)
      val fooType = pkg.getTypeOfPath(root.join("triggers").join("Foo.trigger").toString)

      assert(pkg.getPathsOfType(null).isEmpty)

      assert(dummyType.toString == "test.Dummy (test)")
      assert(pkg.getPathsOfType(dummyType).sameElements(Array("/classes/Dummy.cls")))

      assert(fooType.toString == "__sfdc_trigger/test/Foo (test)")
      assert(pkg.getPathsOfType(fooType).sameElements(Array("/triggers/Foo.trigger")))
    }
  }

  test("summary of type") {
    FileSystemHelper.run(Map(
      "classes/Dummy.cls" -> "public class Dummy {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      val typeLike = pkg.getTypeOfPath(root.join("classes").join("Dummy.cls").toString)
      val summary = pkg.getSummaryOfType(typeLike)

      assert(summary.name == "Dummy")
      assert(summary.typeName.toString == "Dummy")
      assert(summary.idRange.contains(RangeLocation(Position(1,13), Position(1,18))))
      assert(summary.modifiers == List("public"))
    }
  }

  test("summary of type with namespace") {
    FileSystemHelper.run(Map(
      "classes/Dummy.cls" -> "@isTest puBlic class Dummy {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(Some(Name("test")), Seq(root), Seq())
      assert(!org.issues.hasMessages)

      val typeLike = pkg.getTypeOfPath(root.join("classes").join("Dummy.cls").toString)
      val summary = pkg.getSummaryOfType(typeLike)

      assert(summary.name == "Dummy")
      assert(summary.typeName.toString == "test.Dummy")
      assert(summary.idRange.contains(RangeLocation(Position(1,21), Position(1,26))))
      assert(summary.modifiers == List("@IsTest", "public"))
    }
  }

  test("summary of type with namespace (cached)") {
    ParsedCache.clear()
    ServerOps.setParsedDataCaching(true)

    FileSystemHelper.run(Map(
      "classes/Dummy.cls" -> "@isTest puBlic class Dummy {}"
    ), setupCache = true) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(Some(Name("test")), Seq(root), Seq())
      assert(!org.issues.hasMessages)
      org.flush()

      val org2 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg2 = org2.addPackage(Some(Name("test")), Seq(root), Seq()).asInstanceOf[PackageImpl]
      assert(!org2.issues.hasMessages)

      val typeLike = pkg2.getTypeOfPath(root.join("classes").join("Dummy.cls").toString)
      val summary = pkg2.getSummaryOfType(typeLike)

      assert(pkg2.getType(typeLike.typeName, None).toOption.exists(_.isInstanceOf[SummaryDeclaration]))
      assert(summary.name == "Dummy")
      assert(summary.typeName.toString == "test.Dummy")
      assert(summary.idRange.contains(RangeLocation(Position(1,21), Position(1,26))))
      assert(summary.modifiers == List("@IsTest", "public"))
    }

    ServerOps.setParsedDataCaching(false)
  }

  test("summary of trigger") {
    FileSystemHelper.run(Map(
      "triggers/Dummy.trigger" -> "trigger Dummy on Account (before insert) {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(Some(Name("test")), Seq(root), Seq())
      assert(!org.issues.hasMessages)

      val typeLike = pkg.getTypeOfPath(root.join("triggers").join("Dummy.trigger").toString)
      val summary = pkg.getSummaryOfType(typeLike)

      assert(summary.name == "__sfdc_trigger/test/Dummy")
      assert(summary.typeName.toString == "__sfdc_trigger/test/Dummy")
      assert(summary.idRange.contains(RangeLocation(Position(1,8), Position(1,13))))
      assert(summary.modifiers.isEmpty)
    }
  }

  test("summary of trigger with namespace") {
    FileSystemHelper.run(Map(
      "triggers/Dummy.trigger" -> "trigger Dummy on Account (before insert) {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      val typeLike = pkg.getTypeOfPath(root.join("triggers").join("Dummy.trigger").toString)
      val summary = pkg.getSummaryOfType(typeLike)

      assert(summary.name == "__sfdc_trigger/Dummy")
      assert(summary.typeName.toString == "__sfdc_trigger/Dummy")
      assert(summary.idRange.contains(RangeLocation(Position(1,8), Position(1,13))))
      assert(summary.modifiers.isEmpty)
    }
  }

  test("No dependencies") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public class Foo {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      val fooTypeLike = pkg.getTypeOfPath(root.join("classes").join("Foo.cls").toString)

      assert(pkg.getDependencyHolders(fooTypeLike).isEmpty)
    }
  }

  def fooHoldsBarCached(files: Map[String, String], inheritanceOnly: Boolean = false): Unit = {
    ParsedCache.clear()
    ServerOps.setParsedDataCaching(true)
    FileSystemHelper.run(files, setupCache = true) { root: PathLike =>
      // Basic non-cached test
      {
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        val pkg = org.addPackage(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)

        val fooTypeLike = pkg.getTypeOfPath(root.join("classes").join("Foo.cls").toString)
        val barTypeLike = pkg.getTypeOfPath(root.join("classes").join("Bar.cls").toString)

        assert(pkg.getDependencyHolders(fooTypeLike).sameElements(Array(barTypeLike)))
        assert(pkg.getDependencyHolders(barTypeLike).isEmpty)

        if (inheritanceOnly) {
          assert(pkg.getDependencies(barTypeLike, inheritanceOnly=true).sameElements(Array(fooTypeLike)))
        } else {
          assert(pkg.getDependencies(barTypeLike, inheritanceOnly=true).isEmpty)
        }
        assert(pkg.getDependencies(barTypeLike, inheritanceOnly=false).sameElements(Array(fooTypeLike)))
        assert(pkg.getDependencies(fooTypeLike, inheritanceOnly=false).isEmpty)
        org.flush()
      }

      // Extended cache test
      {
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        val pkg = org.addPackage(None, Seq(root), Seq()).asInstanceOf[PackageImpl]
        assert(!org.issues.hasMessages)

        val fooTypeLike = pkg.getTypeOfPath(root.join("classes").join("Foo.cls").toString)
        val barTypeLike = pkg.getTypeOfPath(root.join("classes").join("Bar.cls").toString)

        assert(pkg.getType(fooTypeLike.typeName.asInstanceOf[TypeName], None).toOption.exists(_.isInstanceOf[SummaryDeclaration]))
        assert(pkg.getType(barTypeLike.typeName.asInstanceOf[TypeName], None).toOption.exists(_.isInstanceOf[SummaryDeclaration]))

        assert(pkg.getDependencyHolders(fooTypeLike).sameElements(Array(barTypeLike)))
        assert(pkg.getDependencyHolders(barTypeLike).isEmpty)

        if (inheritanceOnly) {
          assert(pkg.getDependencies(barTypeLike, inheritanceOnly = true).sameElements(Array(fooTypeLike)))
        } else {
          assert(pkg.getDependencies(barTypeLike, inheritanceOnly = true).isEmpty)
        }
        assert(pkg.getDependencies(barTypeLike, inheritanceOnly = false).sameElements(Array(fooTypeLike)))
        assert(pkg.getDependencies(fooTypeLike, inheritanceOnly = false).isEmpty)
      }
    }
    ServerOps.setParsedDataCaching(false)
  }

  test("Superclass dependency") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public virtual class Foo {}",
      "classes/Bar.cls" -> "public class Bar extends Foo {}"
    ), inheritanceOnly = true)
  }

  test("Interface dependency") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public interface Foo {}",
      "classes/Bar.cls" -> "public class Bar implements Foo {}"
    ), inheritanceOnly = true)
  }

  test("Block dependency") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {{Object a = new Foo();}}"
    ))
  }

  test("Field type dependency") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {Foo a;}"
    ))
  }

  test("Field expression dependency") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {Object a = new Foo();}"
    ))
  }

  test("Method type dependency") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {Foo func(){return null;} }"
    ))
  }

  test("Method argument dependency") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {Object func(Foo a){return null;} }"
    ))
  }

  test("Method body dependency") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {Object func(){return new Foo();} }"
    ))
  }

  test("Constructor argument dependency") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar { Bar(Foo a){} }"
    ))
  }

  test("Constructor body dependency") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar { Bar(){Object a = new Foo();} }"
    ))
  }

  test("Superclass dependency (nested)") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public class Foo {public virtual class Baz {}}",
      "classes/Bar.cls" -> "public class Bar extends Foo.Baz {}"
    ), inheritanceOnly = true)
  }

  test("Interface dependency (nested)") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public class Foo {public interface Baz {}}",
      "classes/Bar.cls" -> "public class Bar implements Foo.Baz {}"
    ), inheritanceOnly = true)
  }

  test("Block dependency (nested)") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public class Foo {public class Baz {}}",
      "classes/Bar.cls" -> "public class Bar {{Object a = new Foo.Baz();}}"
    ))
  }

  test("Field type dependency (nested)") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public class Foo {public class Baz {}}",
      "classes/Bar.cls" -> "public class Bar {Foo.Baz a;}"
    ))
  }

  test("Field expression dependency (nested)") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public class Foo {public class Baz {}}",
      "classes/Bar.cls" -> "public class Bar {Object a = new Foo.Baz();}"
    ))
  }

  test("Method type dependency (nested)") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public class Foo {public class Baz {}}",
      "classes/Bar.cls" -> "public class Bar {Foo.Baz func(){return null;} }"
    ))
  }

  test("Method argument dependency (nested)") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public class Foo {public class Baz {}}",
      "classes/Bar.cls" -> "public class Bar {Object func(Foo.Baz a){return null;} }"
    ))
  }

  test("Method body dependency (nested)") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public class Foo {public class Baz {}}",
      "classes/Bar.cls" -> "public class Bar {Object func(){return new Foo.Baz();} }"
    ))
  }

  test("Constructor argument dependency (nested)") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public class Foo {public class Baz {}}",
      "classes/Bar.cls" -> "public class Bar { Bar(Foo.Baz a){} }"
    ))
  }

  test("Constructor body dependency (nested)") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public class Foo {public class Baz {}}",
      "classes/Bar.cls" -> "public class Bar { Bar(){Object a = new Foo.Baz();} }"
    ))
  }

  test("Superclass dependency (from nested)") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public virtual class Foo {}",
      "classes/Bar.cls" -> "public class Bar {public class Baz extends Foo {}}"
    ), inheritanceOnly = true)
  }

  test("Interface dependency (from nested)") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public interface Foo {}",
      "classes/Bar.cls" -> "public class Bar {public class Baz implements Foo {}}"
    ), inheritanceOnly = true)
  }

  test("Block dependency (from nested)") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {public class Baz { {Object a = new Foo();} }}"
    ))
  }

  test("Field type dependency (from nested)") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {public class Baz {Foo a;} }"
    ))
  }

  test("Field expression dependency (from nested)") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {public class Baz {Object a = new Foo();} }"
    ))
  }

  test("Method type dependency (from nested)") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {public class Baz {Foo func(){return null;}} }"
    ))
  }

  test("Method argument dependency (from nested)") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {public class Baz {Object func(Foo a){return null;} }}"
    ))
  }

  test("Method body dependency (from nested)") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {public class Baz {Object func(){return new Foo();} }}"
    ))
  }

  test("Constructor argument dependency (from nested)") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {public class Baz { Bar(Foo a){} }}"
    ))
  }

  test("Constructor body dependency (from nested)") {
    fooHoldsBarCached(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {public class Baz { Bar(){Object a = new Foo();} }}"
    ))
  }

  test("Unmanaged to Managed Dependency") {
    FileSystemHelper.run(Map(
      "pkg1/Foo.cls" -> "global virtual class Foo {}",
      "pkg2/Bar.cls" -> "public class Bar extends test.Foo {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg1 = org.addPackage(Some(Name("test")), Seq(root.join("pkg1")), Seq()).asInstanceOf[PackageImpl]
      val pkg2 = org.addPackage(None, Seq(root.join("pkg2")), Seq(pkg1))
      assert(!org.issues.hasMessages)

      val fooTypeLike = pkg1.getTypeOfPath(root.join("pkg1").join("Foo.cls").toString)
      val barTypeLike = pkg2.getTypeOfPath(root.join("pkg2").join("Bar.cls").toString)

      assert(pkg1.getDependencyHolders(fooTypeLike).sameElements(Array(barTypeLike)))
      assert(pkg2.getDependencyHolders(barTypeLike).isEmpty)
    }
  }

  test("Unmanaged to Managed Dependency (cached)") {
    ParsedCache.clear()
    ServerOps.setParsedDataCaching(true)

    FileSystemHelper.run(Map(
      "pkg1/Foo.cls" -> "global virtual class Foo {}",
      "pkg2/Bar.cls" -> "public class Bar extends test.Foo {}"
    ), setupCache = true) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg1 = org.addPackage(Some(Name("test")), Seq(root.join("pkg1")), Seq()).asInstanceOf[PackageImpl]
      val pkg2 = org.addPackage(None, Seq(root.join("pkg2")), Seq(pkg1))
      assert(!org.issues.hasMessages)
      org.flush()

      val org2 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg21 = org2.addPackage(Some(Name("test")), Seq(root.join("pkg1")), Seq()).asInstanceOf[PackageImpl]
      val pkg22 = org2.addPackage(None, Seq(root.join("pkg2")), Seq(pkg21)).asInstanceOf[PackageImpl]
      assert(!org2.issues.hasMessages)

      val fooTypeLike = pkg21.getTypeOfPath(root.join("pkg1").join("Foo.cls").toString)
      val barTypeLike = pkg22.getTypeOfPath(root.join("pkg2").join("Bar.cls").toString)

      assert(pkg21.getType(fooTypeLike.typeName.asInstanceOf[TypeName], None).toOption.exists(_.isInstanceOf[SummaryDeclaration]))
      assert(pkg22.getType(barTypeLike.typeName.asInstanceOf[TypeName], None).toOption.exists(_.isInstanceOf[SummaryDeclaration]))

      assert(pkg21.getDependencyHolders(fooTypeLike).sameElements(Array(barTypeLike)))
      assert(pkg22.getDependencyHolders(barTypeLike).isEmpty)
    }

    ServerOps.setParsedDataCaching(false)
  }

  test("Managed to Managed Dependency") {
    FileSystemHelper.run(Map(
      "pkg1/Foo.cls" -> "global virtual class Foo {}",
      "pkg2/Bar.cls" -> "public class Bar extends test1.Foo {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg1 = org.addPackage(Some(Name("test1")), Seq(root.join("pkg1")), Seq()).asInstanceOf[PackageImpl]
      val pkg2 = org.addPackage(Some(Name("test2")), Seq(root.join("pkg2")), Seq(pkg1))
      assert(!org.issues.hasMessages)

      val fooTypeLike = pkg1.getTypeOfPath(root.join("pkg1").join("Foo.cls").toString)
      val barTypeLike = pkg2.getTypeOfPath(root.join("pkg2").join("Bar.cls").toString)

      assert(pkg1.getDependencyHolders(fooTypeLike).sameElements(Array(barTypeLike)))
      assert(pkg2.getDependencyHolders(barTypeLike).isEmpty)
    }
  }

  test("Managed to Managed Dependency (cached)") {
    ParsedCache.clear()
    ServerOps.setParsedDataCaching(true)

    FileSystemHelper.run(Map(
      "pkg1/Foo.cls" -> "global virtual class Foo {}",
      "pkg2/Bar.cls" -> "public class Bar extends test1.Foo {}"
    ), setupCache = true) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg1 = org.addPackage(Some(Name("test1")), Seq(root.join("pkg1")), Seq()).asInstanceOf[PackageImpl]
      val pkg2 = org.addPackage(Some(Name("test2")), Seq(root.join("pkg2")), Seq(pkg1))
      assert(!org.issues.hasMessages)
      org.flush()

      val org2 = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg21 = org2.addPackage(Some(Name("test1")), Seq(root.join("pkg1")), Seq()).asInstanceOf[PackageImpl]
      val pkg22 = org2.addPackage(Some(Name("test2")), Seq(root.join("pkg2")), Seq(pkg21)).asInstanceOf[PackageImpl]
      assert(!org2.issues.hasMessages)

      val fooTypeLike = pkg21.getTypeOfPath(root.join("pkg1").join("Foo.cls").toString)
      val barTypeLike = pkg22.getTypeOfPath(root.join("pkg2").join("Bar.cls").toString)

      assert(pkg21.getType(fooTypeLike.typeName.asInstanceOf[TypeName], None).toOption.exists(_.isInstanceOf[SummaryDeclaration]))
      assert(pkg22.getType(barTypeLike.typeName.asInstanceOf[TypeName], None).toOption.exists(_.isInstanceOf[SummaryDeclaration]))

      assert(pkg21.getDependencyHolders(fooTypeLike).sameElements(Array(barTypeLike)))
      assert(pkg22.getDependencyHolders(barTypeLike).isEmpty)
    }

    ServerOps.setParsedDataCaching(false)
  }

  test("Trigger with no block") {
    FileSystemHelper.run(Map(
      "triggers/Foo.trigger" -> "trigger Foo on Account (before insert) {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      val fooTypeLike = pkg.getTypeOfPath(root.join("triggers").join("Foo.trigger").toString)

      assert(pkg.getDependencies(fooTypeLike, inheritanceOnly = false).isEmpty)
    }
  }

  test("Trigger with block") {
    FileSystemHelper.run(Map(
      "classes/Bar.cls" -> "public class Bar {}",
      "triggers/Foo.trigger" -> "trigger Foo on Account (before insert) {Bar b;}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      val barTypeLike = pkg.getTypeOfPath(root.join("classes").join("Bar.cls").toString)
      val fooTypeLike = pkg.getTypeOfPath(root.join("triggers").join("Foo.trigger").toString)

      assert(pkg.getDependencies(fooTypeLike, inheritanceOnly = false).sameElements(Array(barTypeLike)))
    }
  }

  test("Trigger with block with namespace") {
    FileSystemHelper.run(Map(
      "classes/Bar.cls" -> "public class Bar {}",
      "triggers/Foo.trigger" -> "trigger Foo on Account (before insert) {Bar b;}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(Some(Name("test")), Seq(root), Seq())
      assert(!org.issues.hasMessages)

      val barTypeLike = pkg.getTypeOfPath(root.join("classes").join("Bar.cls").toString)
      val fooTypeLike = pkg.getTypeOfPath(root.join("triggers").join("Foo.trigger").toString)

      assert(pkg.getDependencies(fooTypeLike, inheritanceOnly = false).sameElements(Array(barTypeLike)))
    }
  }

  test("location of type") {
    FileSystemHelper.run(Map(
      "classes/Dummy.cls" -> "public class Dummy {}",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(org.getTypeLocation("Dummy") ==
        PathLocation("/classes/Dummy.cls", RangeLocation(Position(1,13), Position(1,18))))
    }
  }

  test("location of type (packaged)") {
    FileSystemHelper.run(Map(
      "classes/Dummy.cls" -> "public class Dummy {}",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(Some(Name("test")), Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(org.getTypeLocation("Dummy") == null)
      assert(org.getTypeLocation("test.Dummy") ==
        PathLocation("/classes/Dummy.cls", RangeLocation(Position(1,13), Position(1,18))))
    }
  }

  test("location of type (packaged & nested)") {
    FileSystemHelper.run(Map(
      "classes/Dummy.cls" -> "public class Dummy {class Inner {}}",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(Some(Name("test")), Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(org.getTypeLocation("Dummy") == null)
      assert(org.getTypeLocation("Dummy.Inner") == null)
      assert(org.getTypeLocation("test.Dummy.Inner") ==
        PathLocation("/classes/Dummy.cls", RangeLocation(Position(1,26), Position(1,31))))
    }
  }

  test("location of trigger") {
    FileSystemHelper.run(Map(
      "triggers/Foo.trigger" -> "trigger Foo on Account (before insert) {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(org.getTypeLocation("Foo") == null)
      assert(org.getTypeLocation("__sfdc_trigger/Foo") ==
        PathLocation("/triggers/Foo.trigger", RangeLocation(Position(1,8), Position(1,11))))
    }
  }

  test("location of trigger (packaged)") {
    FileSystemHelper.run(Map(
      "triggers/Foo.trigger" -> "trigger Foo on Account (before insert) {}"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(Some(Name("test")), Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(org.getTypeLocation("Foo") == null)
      assert(org.getTypeLocation("__sfdc_trigger/Foo") == null)
      assert(org.getTypeLocation("__sfdc_trigger/test/Foo") ==
        PathLocation("/triggers/Foo.trigger", RangeLocation(Position(1,8), Position(1,11))))
    }
  }
}
