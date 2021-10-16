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
package com.nawforce.pkgforce.documents

import com.nawforce.pkgforce.diagnostics
import com.nawforce.pkgforce.diagnostics.{CatchingLogger, ERROR_CATEGORY, Issue}
import com.nawforce.pkgforce.names.Name
import com.nawforce.pkgforce.path.{Location, PathLike}
import com.nawforce.runtime.FileSystemHelper
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

import scala.collection.immutable.ArraySeq

class DocumentIndexTest extends AnyFunSuite with BeforeAndAfter {

  private var logger: CatchingLogger = _

  before {
    logger = new CatchingLogger()
  }

  test("bad dir has no files") {
    FileSystemHelper.run(Map[String, String]()) { root: PathLike =>
      val index = DocumentIndex(logger, None, root.join("foo"))
      assert(logger.issues.isEmpty)
      assert(index.size == 0)
    }
  }

  test("empty dir has no files") {
    FileSystemHelper.run(Map[String, String]()) { root: PathLike =>
      val index = DocumentIndex(logger, None, root)
      assert(logger.issues.isEmpty)
      assert(index.size == 0)
    }
  }

  test("dot dir is ignored") {
    FileSystemHelper.run(Map[String, String](".pkg/Foo.cls" -> "")) { root: PathLike =>
      val index = DocumentIndex(logger, None, root)
      assert(logger.issues.isEmpty)
      assert(index.size == 0)
    }
  }

  test("node_modules dir is ignored") {
    FileSystemHelper.run(Map[String, String]("node_modules/Foo.cls" -> "")) { root: PathLike =>
      val index = DocumentIndex(logger, None, root)
      assert(logger.issues.isEmpty)
      assert(index.size == 0)
    }
  }

  test("class file found") {
    FileSystemHelper.run(Map[String, String]("pkg/Foo.cls" -> "public class Foo {}")) {
      root: PathLike =>
        val index = DocumentIndex(logger, None, root.join("pkg"))
        assert(logger.issues.isEmpty)
        assert(index.get(ApexNature).size == 1)
        assert(
          index.get(ApexNature).toList ==
            List(ApexClassDocument(root.join("pkg").join("Foo.cls"), Name("Foo"))))
    }
  }

  test("nested class file found") {
    FileSystemHelper.run(Map[String, String]("pkg/foo/Foo.cls" -> "public class Foo {}")) {
      root: PathLike =>
        val index = DocumentIndex(logger, None, root.join("pkg"))
        assert(logger.issues.isEmpty)
        assert(index.get(ApexNature).size == 1)
        assert(
          index.get(ApexNature).toList ==
            List(ApexClassDocument(root.join("pkg").join("foo").join("Foo.cls"), Name("Foo"))))
    }
  }

  test("multiple classes found") {
    FileSystemHelper.run(
      Map[String, String]("pkg/Foo.cls" -> "public class Foo {}",
                          "/pkg/bar/Bar.cls" -> "public class Bar {}")) { root: PathLike =>
      val index = DocumentIndex(logger, None, root.join("pkg"))
      assert(logger.issues.isEmpty)
      assert(
        index.get(ApexNature).map(_.toString()).toSet == Set(
          ApexClassDocument(root.join("pkg").join("Foo.cls"), Name("Foo")).toString,
          ApexClassDocument(root.join("pkg").join("bar").join("Bar.cls"), Name("Bar")).toString))
    }
  }

  test("duplicate classes error") {
    FileSystemHelper.run(
      Map[String, String]("pkg/foo/Foo.cls" -> "public class Foo {}",
                          "/pkg/bar/Foo.cls" -> "public class Foo {}")) { root: PathLike =>
      val index = DocumentIndex(logger, None, root.join("pkg"))
      assert(index.get(ApexNature).size == 1)
      assert(logger.issues.head.diagnostic.category == ERROR_CATEGORY)
      assert(logger.issues.head.diagnostic.message.contains("File creates duplicate type 'Foo'"))
    }
  }

  test("xcls file found") {
    FileSystemHelper.run(Map[String, String]("pkg/Foo.xcls" -> "public class Foo {}")) {
      root: PathLike =>
        val index = DocumentIndex(logger, None, root.join("pkg"))
        assert(logger.issues.isEmpty)
        assert(index.get(ExtendedApexNature).size == 1)
        assert(
          index.get(ExtendedApexNature).toList ==
            List(ExtendedApexDocument(root.join("pkg").join("Foo.xcls"), Name("Foo"))))
    }
  }

  test("cls/xcls duplicate classes don't error") {
    FileSystemHelper.run(
      Map[String, String]("pkg/foo/Foo.cls" -> "public class Foo {}",
        "/pkg/bar/Foo.xcls" -> "public class Foo {}")) { root: PathLike =>
      val index = DocumentIndex(logger, None, root.join("pkg"))
      assert(index.get(ApexNature).isEmpty)
      assert(index.get(ExtendedApexNature).size == 1)
      assert(
        logger.issues == ArraySeq(Issue(root.join("pkg").join("foo").join("Foo.cls"),
          diagnostics.Diagnostic(
            ERROR_CATEGORY,
            Location.empty,
            "File creates duplicate type 'Foo' as '/pkg/bar/Foo.xcls', ignoring"))))
    }
  }

  test("duplicate labels no error") {
    FileSystemHelper.run(
      Map[String, String](
        "pkg/foo/CustomLabels.labels" -> "<CustomLabels xmlns=\"http://soap.sforce.com/2006/04/metadata\"/>",
        "/pkg/bar/CustomLabels.labels" -> "<CustomLabels xmlns=\"http://soap.sforce.com/2006/04/metadata\"/>")) {
      root: PathLike =>
        val index = DocumentIndex(logger, None, root.join("pkg"))
        assert(logger.issues.isEmpty)
        assert(index.get(LabelNature).size == 2)
    }
  }

  test("object file found") {
    FileSystemHelper.run(Map[String, String](
      "pkg/Foo.object" -> "<CustomObject xmlns=\\\"http://soap.sforce.com/2006/04/metadata\\\"/>")) {
      root: PathLike =>
        val index = DocumentIndex(logger, None, root.join("pkg"))
        assert(logger.issues.isEmpty)
        assert(
          index.get(SObjectNature).toList ==
            List(SObjectDocument(root.join("pkg").join("Foo.object"), Name("Foo"))))
    }
  }

  test("object file found (sfdx)") {
    FileSystemHelper.run(Map[String, String](
      "pkg/Foo.object-meta.xml" -> "<CustomObject xmlns=\\\"http://soap.sforce.com/2006/04/metadata\\\"/>")) {
      root: PathLike =>
        val index = DocumentIndex(logger, None, root.join("pkg"))
        assert(logger.issues.isEmpty)
        assert(
          index.get(SObjectNature).toList ==
            List(SObjectDocument(root.join("pkg").join("Foo.object-meta.xml"), Name("Foo"))))
    }
  }

  test("sfdx field ghosts object") {
    FileSystemHelper.run(Map[String, String](
      "pkg/Foo/fields/Bar.field-meta.xml" -> "<CustomField xmlns=\\\"http://soap.sforce.com/2006/04/metadata\\\"/>")) {
      root: PathLike =>
        val index = DocumentIndex(logger, None, root.join("pkg"))
        assert(logger.issues.isEmpty)
        assert(
          index.get(FieldNature).toList ==
            List(
              SObjectFieldDocument(
                root.join("pkg").join("Foo").join("fields").join("Bar.field-meta.xml"),
                Name("Bar"))))
        assert(
          index.get(SObjectNature).toList ==
            List(SObjectDocument(root.join("pkg").join("Foo").join("Foo.object-meta.xml"),
                                 Name("Foo"))))
    }
  }

  test("sfdx fieldset ghosts object") {
    FileSystemHelper.run(Map[String, String](
      "pkg/Foo/fieldSets/Bar.fieldSet-meta.xml" -> "<FieldSet xmlns=\\\"http://soap.sforce.com/2006/04/metadata\\\"/>")) {
      root: PathLike =>
        val index = DocumentIndex(logger, None, root.join("pkg"))
        assert(logger.issues.isEmpty)
        assert(
          index.get(FieldSetNature).toList ==
            List(
              SObjectFieldSetDocument(
                root.join("pkg").join("Foo").join("fieldSets").join("Bar.fieldSet-meta.xml"),
                Name("Bar"))))
        assert(
          index.get(SObjectNature).toList ==
            List(SObjectDocument(root.join("pkg").join("Foo").join("Foo.object-meta.xml"),
                                 Name("Foo"))))
    }
  }
}
