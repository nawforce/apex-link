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

import com.nawforce.common.api.{Name, ServerOps}
import com.nawforce.common.documents.{ApexClassDocument, DocumentType}
import com.nawforce.common.names.TypeName
import com.nawforce.common.org.OrgImpl
import com.nawforce.common.path.PathLike
import com.nawforce.common.types.core.TypeDeclaration
import com.nawforce.runtime.FileSystemHelper
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class IdDependencyTest extends AnyFunSuite with BeforeAndAfter {
  private var defaultOrg: OrgImpl = new OrgImpl
  private var root: PathLike = _

  def typeDeclarations(classes: Map[String, String]): Seq[TypeDeclaration] = {
    FileSystemHelper.run(classes) { root: PathLike =>
      this.root = root
      OrgImpl.current.withValue(defaultOrg) {
        defaultOrg.unmanaged.deployClasses(
          classes.map(p => DocumentType(root.join(p._1)).get.asInstanceOf[ApexClassDocument]).toSeq)
        defaultOrg.unmanaged.findTypes(classes.keys.map(k => TypeName(Name(k.replaceAll("\\.cls$", "")))).toSeq)
      }
    }
  }

  before {
    defaultOrg = new OrgImpl
    root = null
    ServerOps.setParsedDataCaching(false)
  }

  test("Local func does not create dependencies") {
    val tds = typeDeclarations(Map("Dummy.cls" -> "public class Dummy {void func() {func();} }"))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.dependencies().isEmpty)
    assert(tds.head.methods.head.dependencies().isEmpty)
  }

  test("Missing Static func creates error") {
    val tds = typeDeclarations(Map("Dummy.cls" -> "public class Dummy {void func() {A.func();} }"))
    assert(defaultOrg.issues.getMessages("/Dummy.cls") ==
      "Missing: line 1 at 33-34: No variable or type found for 'A' on 'Dummy'\n")
    assert(tds.head.dependencies().isEmpty)
  }

  test("Static func creates method dependency") {
    val tds = typeDeclarations(Map(
      "Dummy.cls" -> "public class Dummy {static void func() {A.func();} }",
      "A.cls" -> "public class A {public static void func() {}}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.dependencies().isEmpty)
    assert(tds.head.methods.find(_.name == Name("func")).get.dependencies().toSet
      .contains(tds.tail.head.methods.find(_.name == Name("func")).get))
  }

  test("Platform func does not create dependency") {
    val tds = typeDeclarations(Map(
      "Dummy.cls" -> "public class Dummy {static void func() {System.debug('Hello');} }"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.dependencies().isEmpty)
    assert(tds.head.methods.find(_.name == Name("func")).get.dependencies().isEmpty)
  }

  test("Field reference creates method dependency") {
    val tds = typeDeclarations(Map(
      "Dummy.cls" -> "public class Dummy {Object a; void func() {a = null;} }"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.dependencies().isEmpty)

    val func = tds.head.methods.find(_.name == Name("func")).get
    val field = tds.head.fields.find(_.name == Name("a")).get
    assert(func.dependencies().toSet.contains(field))
  }

  test("Superclass field reference creates method dependent") {
    val tds = typeDeclarations(Map(
      "Dummy.cls" -> "public class Dummy extends A {void func() {a = null;} }",
      "A.cls" -> "public virtual class A {Object a;}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.dependencies() == tds.tail.toSet)

    val func = tds.head.methods.find(_.name == Name("func")).get
    val field = tds(1).fields.find(_.name == Name("a")).get
    assert(func.dependencies().toSet.contains(field))
  }

  test("Hidden outer class field reference creates error") {
    val tds = typeDeclarations(Map(
      "Dummy.cls" -> "public class Dummy {Object a; class B {void func() {a = null;} } }",
    ))
    assert(defaultOrg.issues.getMessages("/Dummy.cls") ==
      "Missing: line 1 at 52-53: No variable or type found for 'a' on 'Dummy.B'\n")
    assert(tds.head.dependencies().isEmpty)
    assert(tds.head.nestedTypes.head.dependencies().isEmpty)
    assert(tds.head.nestedTypes.head.methods.head.dependencies().isEmpty)
  }

  test("Outer class static field creates dependency") {
    val tds = typeDeclarations(Map(
      "Dummy.cls" -> "public class Dummy {static Object a; class B {void func() {a = null;} } }",
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.dependencies().isEmpty)
    assert(tds.head.nestedTypes.head.dependencies().isEmpty)

    val func = tds.head.nestedTypes.head.methods.find(_.name == Name("func")).get
    val field = tds.head.fields.find(_.name == Name("a")).get
    assert(func.dependencies().toSet.contains(field))
  }

  test("Property reference creates dependency") {
    val tds = typeDeclarations(Map(
      "Dummy.cls" -> "public class Dummy {Object a {get;} void func() {a = null;} }"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.dependencies().isEmpty)

    val func = tds.head.methods.find(_.name == Name("func")).get
    val field = tds.head.fields.find(_.name == Name("a")).get
    assert(func.dependencies().toSet.contains(field))
  }

  test("Superclass property creates dependency") {
    val tds = typeDeclarations(Map(
      "Dummy.cls" -> "public class Dummy extends A {void func() {a = null;} }",
      "A.cls" -> "public virtual class A {Object a {get;}}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.dependencies() == tds.tail.toSet)

    val func = tds.head.methods.find(_.name == Name("func")).get
    val field = tds(1).fields.find(_.name == Name("a")).get
    assert(func.dependencies().toSet.contains(field))
  }

  test("Local var not dependent") {
    val tds = typeDeclarations(Map(
      "Dummy.cls" -> "public class Dummy {void func() {Object a; a = null;} }"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.methods.find(_.name == Name("func")).get.dependencies().isEmpty)
  }
}
