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
      assert(!org.issues.hasMessages)

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
      assert(!org.issues.hasMessages)

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
      assert(!org.issues.hasMessages)

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
      assert(!org.issues.hasMessages)

      assert(pkg.getPathOfType("test.Dummy") == "/classes/Dummy.cls")
      assert(pkg.getPathOfType("test.Dummy2").isEmpty)
      assert(pkg.getPathOfType("Dummy").isEmpty)
      assert(pkg.getPathOfType("").isEmpty)
      assert(pkg.getPathOfType(null).isEmpty)
    }
  }

  test("No dependencies") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public class Foo {}"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }

  test("Superclass dependency") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public virtual class Foo {}",
      "classes/Bar.cls" -> "public class Bar extends Foo {}"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }

  test("Interface dependency") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public interface Foo {}",
      "classes/Bar.cls" -> "public class Bar implements Foo {}"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }

  test("Block dependency") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {{Object a = new Foo();}}"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }


  test("Field type dependency") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {Foo a;}"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }

  test("Field expression dependency") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {Object a = new Foo();}"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }

  test("Method type dependency") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {Foo func(){return null;} }"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }

  test("Method argument dependency") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {Object func(Foo a){return null;} }"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }

  test("Method body dependency") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {Object func(){return new Foo();} }"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }

  test("Constructor argument dependency") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar { Bar(Foo a){} }"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }

  test("Constructor body dependency") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar { Bar(){Object a = new Foo();} }"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }

  test("Superclass dependency (nested)") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public class Foo {public virtual class Baz {}}",
      "classes/Bar.cls" -> "public class Bar extends Foo.Baz {}"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }

  test("Interface dependency (nested)") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public class Foo {public interface Baz {}}",
      "classes/Bar.cls" -> "public class Bar implements Foo.Baz {}"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }

  test("Block dependency (nested)") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public class Foo {public class Baz {}}",
      "classes/Bar.cls" -> "public class Bar {{Object a = new Foo.Baz();}}"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }


  test("Field type dependency (nested)") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public class Foo {public class Baz {}}",
      "classes/Bar.cls" -> "public class Bar {Foo.Baz a;}"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }

  test("Field expression dependency (nested)") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public class Foo {public class Baz {}}",
      "classes/Bar.cls" -> "public class Bar {Object a = new Foo.Baz();}"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }

  test("Method type dependency (nested)") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public class Foo {public class Baz {}}",
      "classes/Bar.cls" -> "public class Bar {Foo.Baz func(){return null;} }"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }

  test("Method argument dependency (nested)") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public class Foo {public class Baz {}}",
      "classes/Bar.cls" -> "public class Bar {Object func(Foo.Baz a){return null;} }"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }

  test("Method body dependency (nested)") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public class Foo {public class Baz {}}",
      "classes/Bar.cls" -> "public class Bar {Object func(){return new Foo.Baz();} }"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }

  test("Constructor argument dependency (nested)") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public class Foo {public class Baz {}}",
      "classes/Bar.cls" -> "public class Bar { Bar(Foo.Baz a){} }"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }

  test("Constructor body dependency (nested)") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public class Foo {public class Baz {}}",
      "classes/Bar.cls" -> "public class Bar { Bar(){Object a = new Foo.Baz();} }"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }

  test("Superclass dependency (from nested)") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public virtual class Foo {}",
      "classes/Bar.cls" -> "public class Bar {public class Baz extends Foo {}}"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }

  test("Interface dependency (from nested)") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public interface Foo {}",
      "classes/Bar.cls" -> "public class Bar {public class Baz implements Foo {}}"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }

  test("Block dependency (from nested)") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {public class Baz { {Object a = new Foo();} }}"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }

  test("Field type dependency (from nested)") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {public class Baz {Foo a;} }"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }

  test("Field expression dependency (from nested)") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {public class Baz {Object a = new Foo();} }"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }

  test("Method type dependency (from nested)") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {public class Baz {Foo func(){return null;}} }"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }

  test("Method argument dependency (from nested)") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {public class Baz {Object func(Foo a){return null;} }}"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }

  test("Method body dependency (from nested)") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {public class Baz {Object func(){return new Foo();} }}"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }

  test("Constructor argument dependency (from nested)") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {public class Baz { Bar(Foo a){} }}"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }

  test("Constructor body dependency (from nested)") {
    FileSystemHelper.run(Map(
      "classes/Foo.cls" -> "public class Foo {}",
      "classes/Bar.cls" -> "public class Bar {public class Baz { Bar(){Object a = new Foo();} }}"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      assert(pkg.getDependencyHolders("Bar").sameElements(Array("Foo")))
      assert(pkg.getDependencyHolders("Foo").isEmpty)
    }
  }

  test("Unmanaged to Managed Dependency") {
    FileSystemHelper.run(Map(
      "pkg1/Foo.cls" -> "global virtual class Foo {}",
      "pkg2/Bar.cls" -> "public class Bar extends test.Foo {}"
    )) { root: PathLike =>
      val org = new Org()
      val pkg1 = org.addPackage(Some(Name("test")), Seq(root.join("pkg1")), Seq())
      val pkg2 = org.addPackage(None, Seq(root.join("pkg2")), Seq(pkg1))
      assert(!org.issues.hasMessages)

      assert(pkg2.getDependencyHolders("Bar").sameElements(Array("test.Foo")))
      assert(pkg1.getDependencyHolders("test.Foo").isEmpty)
    }
  }

  test("Managed to Managed Dependency") {
    FileSystemHelper.run(Map(
      "pkg1/Foo.cls" -> "global virtual class Foo {}",
      "pkg2/Bar.cls" -> "public class Bar extends test1.Foo {}"
    )) { root: PathLike =>
      val org = new Org()
      val pkg1 = org.addPackage(Some(Name("test1")), Seq(root.join("pkg1")), Seq())
      val pkg2 = org.addPackage(Some(Name("test2")), Seq(root.join("pkg2")), Seq(pkg1))
      assert(!org.issues.hasMessages)

      assert(pkg2.getDependencyHolders("test2.Bar").sameElements(Array("test1.Foo")))
      assert(pkg1.getDependencyHolders("test1.Foo").isEmpty)
    }
  }

}
