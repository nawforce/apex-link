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
package com.nawforce.documents

import com.nawforce.names.Name
import com.nawforce.path.Path
import com.nawforce.pkg.imports.{FSMonkey, Memfs}
import org.scalatest.funsuite.AnyFunSuite

import scala.scalajs.js.JSON

class DocumentIndexTest extends AnyFunSuite {

  def index(json: String)(verify: => Unit): Unit = {
    val unpatch = FSMonkey.patchFs(Memfs.vol)
    try {
      Memfs.vol.fromJSON(JSON.parse(json))
      verify
    } finally {
      unpatch()
      Memfs.vol.reset()
    }
  }

  test("bad dir has no files") {
    index(
      """{
        |}""".stripMargin) {
      val index = new DocumentIndex(Seq(Path("/foo")))
      assert(index.size == 0)
    }
  }

  test("empty dir has no files") {
    index(
      """{
        |}""".stripMargin) {
      val index = new DocumentIndex(Seq(Path("/")))
      assert(index.size == 0)
    }
  }

  test("class file found") {
    index(
      """{
        | "/pkg/Foo.cls": "public class Foo {}"
        |}""".stripMargin) {
      val index = new DocumentIndex(Seq(Path("/pkg")))
      assert(index.size == 1)
      assert(index.getByExtension(Name("cls")) == Seq(ApexDocument(Path("/pkg/Foo.cls"), Name("Foo"))))
    }
  }

  test("nested class file found") {
      index(
        """{
          | "/pkg/foo/Foo.cls": "public class Foo {}"
          |}""".stripMargin) {
        val index = new DocumentIndex(Seq(Path("/pkg")))
        assert(index.size == 1)
        assert(index.getByExtension(Name("cls")) == Seq(ApexDocument(Path("/pkg/foo/Foo.cls"), Name("Foo"))))
      }
  }

  test("empty class ignored") {
    index(
      """{
        | "/pkg/Foo.cls": ""
        |}""".stripMargin) {
      val index = new DocumentIndex(Seq(Path("/pkg")))
      assert(index.size == 0)
    }
  }

  test("hidden class ignored") {
    index(
      """{
        | "/pkg/Foo.cls": "(hidden)"
        |}""".stripMargin) {
      val index = new DocumentIndex(Seq(Path("/pkg")))
      assert(index.size == 0)
    }
  }

  test("multiple classes found") {
    index(
      """{
        | "/pkg/Foo.cls": "public class Foo {}",
        | "/pkg/bar/Bar.cls": "public class Bar {}"
        |}""".stripMargin) {
      val index = new DocumentIndex(Seq(Path("/pkg")))
      assert(index.size == 2)
      assert(index.getByExtension(Name("cls")).toSet == Set(
        ApexDocument(Path("/pkg/Foo.cls"), Name("Foo")),
        ApexDocument(Path("/pkg/bar/Bar.cls"), Name("Bar"))
      ))
    }
  }

  test("duplicate classes error") {
    index(
      """{
        | "/pkg/foo/Foo.cls": "public class Foo {}",
        | "/pkg/bar/Foo.cls": "public class Foo {}"
        |}""".stripMargin) {
      val index = new DocumentIndex(Seq(Path("/pkg")))
      assert(index.size == 1)
      assert(index.getByExtension(Name("cls")).toSet == Set(ApexDocument(Path("/pkg/bar/Foo.cls"), Name("Foo"))))
      // TODO: Check duplicate issues exists
    }
  }

  test("object file found") {
    index(
      """{
        | "/pkg/Foo.object": "<CustomObject xmlns=\\\"http://soap.sforce.com/2006/04/metadata\\\"/>"
        |}""".stripMargin) {
      val index = new DocumentIndex(Seq(Path("/pkg")))
      assert(index.size == 1)
      assert(index.getByExtension(Name("object")) == Seq(SObjectDocument(Path("/pkg/Foo.object"), Name("Foo"))))
    }
  }

  test("object file found (sfdx)") {
    index(
      """{
        | "/pkg/Foo.object-meta.xml": "<CustomObject xmlns=\\\"http://soap.sforce.com/2006/04/metadata\\\"/>"
        |}""".stripMargin) {
      val index = new DocumentIndex(Seq(Path("/pkg")))
      assert(index.size == 1)
      assert(index.getByExtension(Name("object")) == Seq(SObjectDocument(Path("/pkg/Foo.object-meta.xml"), Name("Foo"))))
    }
  }

  test("sfdx field ghosts object") {
    index(
      """{
        | "/pkg/Foo/fields/Bar.field-meta.xml": "<CustomField xmlns=\\\"http://soap.sforce.com/2006/04/metadata\\\">"
        |}""".stripMargin) {
      val index = new DocumentIndex(Seq(Path("/pkg")))
      assert(index.size == 2)
      assert(index.getByExtension(Name("field")) == Seq(SObjectFieldDocument(Path("/pkg/Foo/fields/Bar.field-meta.xml"), Name("Bar"))))
      assert(index.getByExtension(Name("object")) == Seq(SObjectDocument(Path("/pkg/Foo/Foo.object-meta.xml"), Name("Foo"))))
    }
  }

  test("sfdx fieldset ghosts object") {
    index(
      """{
        | "/pkg/Foo/fieldSet/Bar.fieldset-meta.xml": "<FieldSet xmlns=\\\"http://soap.sforce.com/2006/04/metadata\\\">"
        |}""".stripMargin) {
      val index = new DocumentIndex(Seq(Path("/pkg")))
      assert(index.size == 2)
      assert(index.getByExtension(Name("fieldset")) == Seq(SObjectFieldSetDocument(Path("/pkg/Foo/fieldSet/Bar.fieldset-meta.xml"), Name("Bar"))))
      assert(index.getByExtension(Name("object")) == Seq(SObjectDocument(Path("/pkg/Foo/Foo.object-meta.xml"), Name("Foo"))))
    }
  }
}
