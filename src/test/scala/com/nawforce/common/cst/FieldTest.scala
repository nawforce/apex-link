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
package com.nawforce.common.cst

import com.nawforce.common.api.{ServerOps}
import com.nawforce.common.documents.ApexClassDocument
import com.nawforce.common.modifiers._
import com.nawforce.common.org.OrgImpl
import com.nawforce.common.path.PathFactory
import com.nawforce.common.types.apex.FullDeclaration
import com.nawforce.common.types.core.TypeDeclaration
import com.nawforce.runtime.parsers.SourceData
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class FieldTest extends AnyFunSuite with BeforeAndAfter {
  /* TODO
  private val defaultPath = PathFactory("Dummy.cls").toString
  private val defaultDoc = ApexClassDocument(PathFactory("Dummy.cls"), Name("Dummy"))
  private var defaultOrg: OrgImpl = _

  def typeDeclaration(clsText: String, hasMessages: Boolean = false): TypeDeclaration = {
    OrgImpl.current.withValue(defaultOrg) {
      val td = FullDeclaration.create(defaultOrg.unmanaged, defaultDoc, SourceData(clsText))
      if (td.isEmpty) {
        defaultOrg.dumpIssues()
      } else {
        td.head.validate()
        td.head.fields
      }
      assert(defaultOrg.issues.hasMessages == hasMessages)
      td.head
    }
  }

  before {
    ServerOps.setAutoFlush(false)
    defaultOrg = new OrgImpl
  }

  after {
    ServerOps.setAutoFlush(true)
  }

  test("Empty class has no fields") {
    assert(typeDeclaration("public class Dummy {}").fields.isEmpty)
  }

  test("Field visible") {
    val field = typeDeclaration("public class Dummy {String foo;}").fields.head
    assert(field.name == Name("foo"))
    assert(field.readAccess == PRIVATE_MODIFIER)
    assert(field.writeAccess == PRIVATE_MODIFIER)
  }

  test("Multiple fields visible") {
    val fields = typeDeclaration("public class Dummy {String foo; Integer bar;}").fields
    assert(fields.map(_.name).toSet == Set(Name("foo"), Name("bar")))
  }

  test("Duplicate field reports error on duplicate") {
    val fields =
      typeDeclaration("public class Dummy {String foo; String foo;}", hasMessages = true).fields
    assert(fields.length == 1)
    assert(fields.head.name == Name("foo"))
    assert(
      defaultOrg.issues.getMessages(defaultPath) ==
        "Error: line 1 at 39-42: Duplicate field/property: 'foo'\n")
  }

  test("More than one duplicate field reports error on duplicates") {
    val fields = typeDeclaration("public class Dummy {String foo; Integer foo; String foo;}",
                                 hasMessages = true).fields
    assert(fields.length == 1)
    assert(fields.head.name == Name("foo"))
    assert(
      defaultOrg.issues.getMessages(defaultPath) ==
        "Error: line 1 at 40-43: Duplicate field/property: 'foo'\nError: line 1 at 52-55: Duplicate field/property: 'foo'\n")
  }
   */
}
