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
package com.nawforce.pkgforce.documents

import com.nawforce.pkgforce.diagnostics.{Diagnostic, ERROR_CATEGORY, Issue, IssuesAnd}
import com.nawforce.pkgforce.path.{Location, PathLike}
import com.nawforce.pkgforce.xml.{XMLException, XMLFactory, XMLName}
import com.nawforce.runtime.FileSystemHelper
import com.nawforce.runtime.xml.XMLDocument
import org.scalatest.funsuite.AnyFunSuite

class XMLDocumentTest extends AnyFunSuite {

  // Backward compatability helper
  def parse(path: PathLike): Either[Issue, XMLDocument] = {
    XMLFactory.parse(path) match {
      case IssuesAnd(errors, doc) if errors.nonEmpty => Left(errors.head)
      case IssuesAnd(_, doc)                         => Right(doc.get)
    }
  }

  test("Simple doc is parsed") {
    FileSystemHelper.run(Map[String, String]("test.xml" -> "<test/>")) { root: PathLike =>
      parse(root.join("test.xml")) match {
        case Left(err) => assert(false, err)
        case Right(_)  => ()
      }
    }
  }

  test("Bad doc has error") {
    FileSystemHelper.run(Map[String, String]("test.xml" -> "\n  <test>")) { root: PathLike =>
      val file = root.join("test.xml")
      parse(file) match {
        case Left(Issue(f, Diagnostic(ERROR_CATEGORY, Location(2, _, 2, _), _))) if f == file =>
          ()
        case Left(err) => assert(false, err)
        case Right(_)  => assert(false)
      }
    }
  }

  test("Empty doc has error") {
    FileSystemHelper.run(Map[String, String]("test.xml" -> "")) { root: PathLike =>
      val file = root.join("test.xml")
      parse(file) match {
        case Left(Issue(f, Diagnostic(ERROR_CATEGORY, Location(1, 0, 1, 0), _))) if f == file =>
          ()
        case Left(err) => assert(false, err)
        case Right(_)  => assert(false)
      }
    }
  }

  test("root node") {
    FileSystemHelper.run(
      Map[String, String](
        "test.xml" -> "<test xmlns=\"http://soap.sforce.com/2006/04/metadata\">Hello</test>"
      )
    ) { root: PathLike =>
      val file = root.join("test.xml")
      parse(file) match {
        case Left(err) => assert(false, err)
        case Right(doc) =>
          assert(doc.path == file)
          val node = doc.rootElement
          assert(node.line == 1)
          assert(node.name == XMLName(XMLDocument.sfNamespace, "test"))
          assert(node.text == "Hello")
      }
    }
  }

  test("single child node") {
    FileSystemHelper.run(
      Map[String, String](
        "test.xml" -> "<test xmlns=\"http://soap.sforce.com/2006/04/metadata\">Bar<a>Foo</a>Baz</test>"
      )
    ) { root: PathLike =>
      val file = root.join("test.xml")
      parse(file) match {
        case Left(err) => assert(false, err)
        case Right(doc) =>
          val node = doc.rootElement.getOptionalSingleChild("a")
          assert(node.nonEmpty)
          assert(node.get.line == 1)
          assert(node.get.name == XMLName(XMLDocument.sfNamespace, "a"))
          assert(node.get.text == "Foo")
          node.get.checkIsOrThrow("a")
      }
    }
  }

  test("dual child node not matched") {
    FileSystemHelper.run(
      Map[String, String](
        "test.xml" -> "<test xmlns=\"http://soap.sforce.com/2006/04/metadata\">Bar<a>Foo</a><a>Baz</a></test>"
      )
    ) { root: PathLike =>
      val file = root.join("test.xml")
      parse(file) match {
        case Left(err) => assert(false, err)
        case Right(doc) =>
          val node = doc.rootElement.getOptionalSingleChild("a")
          assert(node.isEmpty)
      }
    }
  }

  test("no child node not matched") {
    FileSystemHelper.run(
      Map[String, String](
        "test.xml" -> "<test xmlns=\"http://soap.sforce.com/2006/04/metadata\">Bar</test>"
      )
    ) { root: PathLike =>
      val file = root.join("test.xml")
      parse(file) match {
        case Left(err) => assert(false, err)
        case Right(doc) =>
          val node = doc.rootElement.getOptionalSingleChild("a")
          assert(node.isEmpty)
      }
    }
  }

  test("optional single child as string") {
    FileSystemHelper.run(
      Map[String, String](
        "test.xml" -> "<test xmlns=\"http://soap.sforce.com/2006/04/metadata\">Bar<a>Foo</a>Baz</test>"
      )
    ) { root: PathLike =>
      val file = root.join("test.xml")
      parse(file) match {
        case Left(err) => assert(false, err)
        case Right(doc) =>
          assert(doc.rootElement.getOptionalSingleChildAsString("a").contains("Foo"))
      }
    }
  }

  test("optional single child as boolean") {
    FileSystemHelper.run(
      Map[String, String](
        "test.xml" -> "<test xmlns=\"http://soap.sforce.com/2006/04/metadata\">Bar<a>true</a>Baz</test>"
      )
    ) { root: PathLike =>
      val file = root.join("test.xml")
      parse(file) match {
        case Left(err) => assert(false, err)
        case Right(doc) =>
          assert(doc.rootElement.getOptionalSingleChildAsBoolean("a").contains(true))
      }
    }
  }

  test("mandatory single child as string") {
    FileSystemHelper.run(
      Map[String, String](
        "test.xml" -> "<test xmlns=\"http://soap.sforce.com/2006/04/metadata\">Bar<a>Foo</a>Baz</test>"
      )
    ) { root: PathLike =>
      val file = root.join("test.xml")
      parse(file) match {
        case Left(err) => assert(false, err)
        case Right(doc) =>
          assert(doc.rootElement.getSingleChildAsString("a") == "Foo")
      }
    }
  }

  test("mandatory single child as boolean") {
    FileSystemHelper.run(
      Map[String, String](
        "test.xml" -> "<test xmlns=\"http://soap.sforce.com/2006/04/metadata\">Bar<a>false</a>Baz</test>"
      )
    ) { root: PathLike =>
      val file = root.join("test.xml")
      parse(file) match {
        case Left(err) => assert(false, err)
        case Right(doc) =>
          assert(!doc.rootElement.getSingleChildAsBoolean("a"))
      }
    }
  }

  test("mandatory single child as string throws") {
    FileSystemHelper.run(
      Map[String, String](
        "test.xml" -> "<test xmlns=\"http://soap.sforce.com/2006/04/metadata\">Bar</test>"
      )
    ) { root: PathLike =>
      val file = root.join("test.xml")
      parse(file) match {
        case Left(err) => assert(false, err)
        case Right(doc) =>
          try {
            doc.rootElement.getSingleChildAsString("a")
            assert(false)
          } catch {
            case ex: XMLException =>
              assert(ex.msg == "Expecting element 'test' to have a single 'a' child element")
            case _: Throwable => assert(false)
          }
      }
    }
  }

  test("mandatory single child as boolean throws") {
    FileSystemHelper.run(
      Map[String, String](
        "test.xml" -> "<test xmlns=\"http://soap.sforce.com/2006/04/metadata\">Bar</test>"
      )
    ) { root: PathLike =>
      val file = root.join("test.xml")
      parse(file) match {
        case Left(err) => assert(false, err)
        case Right(doc) =>
          try {
            doc.rootElement.getSingleChildAsBoolean("a")
            assert(false)
          } catch {
            case ex: XMLException =>
              assert(ex.msg == "Expecting element 'test' to have a single 'a' child element")
            case _: Throwable => assert(false)
          }
      }
    }
  }
}
