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
package com.nawforce.common.documents

import com.nawforce.common.names.Name
import com.nawforce.common.path.{PathFactory, PathLike}
import com.nawforce.runtime.FileSystemHelper
import org.scalatest.funsuite.AnyFunSuite

class DocumentIndexTest extends AnyFunSuite {

  test("bad dir has no files") {
    FileSystemHelper.run(Map[String, String](
    )) {root: PathLike =>
      val index = new DocumentIndex(Seq(root.join("foo")))
      assert(index.size == 0)
    }
  }

  test("empty dir has no files") {
    FileSystemHelper.run(Map[String, String](
    )) {root: PathLike =>
      val index = new DocumentIndex(Seq(root))
      assert(index.size == 0)
    }
  }

  test("class file found") {
    FileSystemHelper.run(Map[String, String](
      "pkg/Foo.cls" -> "public class Foo {}"
    )) {root: PathLike =>
      val index = new DocumentIndex(Seq(root.join("pkg")))
      assert(index.size == 1)
      assert(index.getByExtension(Name("cls")).toString() ==
        Seq(ApexDocument(PathFactory("/pkg/Foo.cls"), Name("Foo"))).toString())
    }
  }

  test("nested class file found") {
    FileSystemHelper.run(Map[String, String](
      "pkg/foo/Foo.cls" -> "public class Foo {}"
    )) { root: PathLike =>
      val index = new DocumentIndex(Seq(root.join("pkg")))
      assert(index.size == 1)
      assert(index.getByExtension(Name("cls")).toString ==
        Seq(ApexDocument(PathFactory("/pkg/foo/Foo.cls"), Name("Foo"))).toString())
    }
  }

  test("empty class ignored") {
    FileSystemHelper.run(Map[String, String](
      "pkg/foo/Foo.cls" -> ""
    )) { root: PathLike =>
      val index = new DocumentIndex(Seq(root.join("pkg")))
      assert(index.size == 0)
    }
  }

  test("hidden class ignored") {
    FileSystemHelper.run(Map[String, String](
      "pkg/foo/Foo.cls" -> "(hidden)"
    )) { root: PathLike =>
      val index = new DocumentIndex(Seq(root.join("pkg")))
      assert(index.size == 0)
    }
  }

  test("multiple classes found") {
    FileSystemHelper.run(Map[String, String](
      "pkg/Foo.cls" -> "public class Foo {}",
      "/pkg/bar/Bar.cls" -> "public class Bar {}"
    )) { root: PathLike =>
      val index = new DocumentIndex(Seq(root.join("pkg")))
      assert(index.size == 2)
      assert(index.getByExtension(Name("cls")).map(_.toString()).toSet == Set(
        ApexDocument(PathFactory("/pkg/Foo.cls"), Name("Foo")).toString,
        ApexDocument(PathFactory("/pkg/bar/Bar.cls"), Name("Bar")).toString
      ))
    }
  }

  test("duplicate classes error") {
    FileSystemHelper.run(Map[String, String](
      "pkg/foo/Foo.cls" -> "public class Foo {}",
      "/pkg/bar/Foo.cls" -> "public class Foo {}"
    )) { root: PathLike =>
      val index = new DocumentIndex(Seq(root.join("pkg")))
      assert(index.size == 1)
      // TODO: Check duplicate issues exists
    }
  }

  test("object file found") {
    FileSystemHelper.run(Map[String, String](
      "pkg/Foo.object" -> "<CustomObject xmlns=\\\"http://soap.sforce.com/2006/04/metadata\\\"/>"
    )) { root: PathLike =>
      val index = new DocumentIndex(Seq(root.join("pkg")))
      assert(index.size == 1)
      assert(index.getByExtension(Name("object")).toString ==
        Seq(SObjectDocument(PathFactory("/pkg/Foo.object"), Name("Foo"))).toString())
    }
  }

  test("object file found (sfdx)") {
    FileSystemHelper.run(Map[String, String](
      "pkg/Foo.object-meta.xml" -> "<CustomObject xmlns=\\\"http://soap.sforce.com/2006/04/metadata\\\"/>"
    )) { root: PathLike =>
      val index = new DocumentIndex(Seq(root.join("pkg")))
      assert(index.size == 1)
      assert(index.getByExtension(Name("object")).toString ==
        Seq(SObjectDocument(PathFactory("/pkg/Foo.object-meta.xml"), Name("Foo"))).toString())
    }
  }

  test("sfdx field ghosts object") {
    FileSystemHelper.run(Map[String, String](
      "pkg/Foo/fields/Bar.field-meta.xml" -> "<CustomField xmlns=\\\"http://soap.sforce.com/2006/04/metadata\\\"/>"
    )) { root: PathLike =>
      val index = new DocumentIndex(Seq(root.join("pkg")))
      assert(index.size == 2)
      assert(index.getByExtension(Name("field")).toString() ==
        Seq(SObjectFieldDocument(PathFactory("/pkg/Foo/fields/Bar.field-meta.xml"), Name("Bar"))).toString())
      assert(index.getByExtension(Name("object")).toString() ==
        Seq(SObjectDocument(PathFactory("/pkg/Foo/Foo.object-meta.xml"), Name("Foo"))).toString())
    }
  }

  test("sfdx fieldset ghosts object") {
    FileSystemHelper.run(Map[String, String](
      "pkg/Foo/fieldSet/Bar.fieldset-meta.xml" -> "<FieldSet xmlns=\\\"http://soap.sforce.com/2006/04/metadata\\\"/>"
    )) { root: PathLike =>
      val index = new DocumentIndex(Seq(root.join("pkg")))
      assert(index.size == 2)
      assert(index.getByExtension(Name("fieldset")).toString() ==
        Seq(SObjectFieldSetDocument(PathFactory("/pkg/Foo/fieldSet/Bar.fieldset-meta.xml"), Name("Bar"))).toString())
      assert(index.getByExtension(Name("object")).toString() ==
        Seq(SObjectDocument(PathFactory("/pkg/Foo/Foo.object-meta.xml"), Name("Foo"))).toString())
    }
  }
}
