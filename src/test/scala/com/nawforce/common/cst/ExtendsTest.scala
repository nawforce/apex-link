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

import com.nawforce.common.api.Org
import com.nawforce.common.documents.{DocumentType, MetadataDocumentType}
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.path.{PathFactory, PathLike}
import com.nawforce.common.types.TypeDeclaration
import com.nawforce.runtime.FileSystemHelper
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class ExtendsTest extends AnyFunSuite with BeforeAndAfter {

  private var defaultOrg: Org = new Org
  private var root: PathLike = _

  def typeDeclarations(classes: Map[String, String]): Seq[TypeDeclaration] = {
    FileSystemHelper.run(classes) { root: PathLike =>
      this.root = root
      Org.current.withValue(defaultOrg) {
        defaultOrg.unmanaged.deployMetadata(
          classes.map(p => DocumentType(root.join(p._1)).get.asInstanceOf[MetadataDocumentType]).toSeq)
        defaultOrg.unmanaged.getTypes(classes.keys.map(k => TypeName(Name(k.replaceAll("\\.cls$", "")))).toSeq)
      }
    }
  }

  before {
    defaultOrg = new Org
    root = null
  }

  test("Duplicate inner type names") {
    assert(typeDeclarations(Map("Dummy.cls" -> "global class Dummy {class Inner {} interface Inner{}}")).nonEmpty)
    assert(defaultOrg.issues.getMessages(PathFactory("/Dummy.cls")) ==
      "Error: line 1 at 45-50: Duplicate type name 'Inner'\n")
  }

  test("Duplicate outer & inner type names") {
    assert(typeDeclarations(Map("Dummy.cls" -> "global class Dummy {class Dummy{}}")).nonEmpty)
    assert(defaultOrg.issues.getMessages(PathFactory("/Dummy.cls")) ==
      "Error: line 1 at 26-31: Duplicate type name 'Dummy'\n")
  }

  test("Missing superclass") {
    assert(typeDeclarations(Map("Dummy.cls" -> "global class Dummy extends Foo {}")).nonEmpty)
    assert(defaultOrg.issues.getMessages(PathFactory("/Dummy.cls")) ==
      "Error: line 1 at 13-18: No type declaration found for 'Foo'\n")
  }

  test("Non-virtual superclass") {
    assert(typeDeclarations(Map(
      "SuperClass.cls" -> "global class SuperClass {}",
      "Dummy.cls" -> "global class Dummy extends SuperClass {}")).size == 2)
    assert(defaultOrg.issues.getMessages(PathFactory("/Dummy.cls")) ==
      "Error: line 1 at 13-18: Parent class 'SuperClass' must be declared virtual or abstract\n")
  }

  test("Interface superclass") {
    assert(typeDeclarations(Map(
      "SuperInterface.cls" -> "global interface SuperInterface {}",
      "Dummy.cls" -> "global class Dummy extends SuperInterface {}")).size == 2)
    assert(defaultOrg.issues.getMessages(PathFactory("/Dummy.cls")) ==
      "Error: line 1 at 13-18: Parent type 'SuperInterface' must be a class\n")
  }

  test("Enum superclass") {
    assert(typeDeclarations(Map(
      "SuperEnum.cls" -> "global enum SuperEnum {}",
      "Dummy.cls" -> "global class Dummy extends SuperEnum {}")).size == 2)
    assert(defaultOrg.issues.getMessages(PathFactory("/Dummy.cls")) ==
      "Error: line 1 at 13-18: Parent type 'SuperEnum' must be a class\n")
  }

  test("External superclass") {
    assert(typeDeclarations(Map(
      "SuperClass.cls" -> "global virtual class SuperClass {}",
      "Dummy.cls" -> "global class Dummy extends SuperClass {}")).size == 2)
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Inner superclass") {
    assert(typeDeclarations(Map("Dummy.cls" -> "global class Dummy extends Inner {virtual class Inner{}}")).nonEmpty)
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Outer superclass") {
    assert(typeDeclarations(Map("Dummy.cls" -> "global virtual class Dummy {class Inner extends Dummy {}}")).nonEmpty)
    assert(!defaultOrg.issues.hasMessages)
  }
}
