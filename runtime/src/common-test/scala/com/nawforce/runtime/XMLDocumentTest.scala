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
package com.nawforce.runtime

import com.nawforce.path.PathLike
import org.scalatest.funsuite.AnyFunSuite
import com.nawforce.FileSystemHelper
import com.nawforce.documents.{PointLocation, Position}
import com.nawforce.xml.XMLFactory

class XMLDocumentTest extends AnyFunSuite {

  test("Simple doc is parsed") {
    FileSystemHelper.run(Map[String, String] (
      "test.xml" -> "<test/>"
    )) { root: PathLike =>
      XMLFactory.parse(root.join("test.xml")) match {
        case Left(err) => assert(false, err)
        case Right(_) => ()
      }
    }
  }

  test("Bad doc has error") {
    FileSystemHelper.run(Map[String, String] (
      "test.xml" -> "\n  <test>"
    )) { root: PathLike =>
      val file = root.join("test.xml")
      XMLFactory.parse(file) match {
        case Left((PointLocation(f, Position(2,_)), _)) if f == file => ()
        case Left(err) => assert(false, err)
        case Right(_) => assert(false)
      }
    }
  }

}

