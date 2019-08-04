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
package com.nawforce.cst

import java.io.ByteArrayInputStream
import java.nio.file.{Path, Paths}

import com.nawforce.api.Org
import com.nawforce.types.TypeDeclaration
import com.nawforce.utils.{DotName, Name}
import org.scalatest.FunSuite

class IdDependencyTest extends FunSuite {
  private val defaultName: Name = Name("Dummy.cls")
  private val defaultPath: Path = Paths.get(defaultName.toString)
  private val defaultOrg: Org = new Org

  private val systemClass = defaultOrg.getType(DotName(Name.System)).get
  private val objectClass = defaultOrg.getType(DotName(Name.Object)).get

  def typeDeclarations(classes: Map[String, String]): Seq[TypeDeclaration] = {
    defaultOrg.clear()
    val paths = classes.map(kv => {
      val fakePath = Paths.get(kv._1 + ".cls")
      defaultOrg.setInputStream(fakePath, new ByteArrayInputStream(kv._2.getBytes()))
      fakePath
    }).toSeq

    Org.current.withValue(defaultOrg) {
      defaultOrg.deployMetadata(Name.Empty, paths)
      defaultOrg.getTypes(classes.keys.map(k => DotName(k)).toSeq)
    }
  }

  test("Local func not dependent") {
    val tds = typeDeclarations(Map("Dummy" -> "public class Dummy {void func() {func();} }"))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.dependencies().isEmpty)
  }

  test("Static func is dependent") {
    val tds = typeDeclarations(Map("Dummy" -> "public class Dummy {void func() {A.func();} }"))
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 33-39: No type declaration found for 'A'\n")
    assert(tds.head.dependencies().isEmpty)
  }

  test("Static func creates dependency") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy {static void func() {A.func();} }",
      "A" -> "public class A {}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.methods.head.dependencies() == tds.tail.toSet)
  }

  test("Platform func creates dependency") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy {static void func() {System.debug('Hello');} }"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.methods.head.dependencies() == Set(systemClass))
  }

  test("Field not dependent") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy {Object a; void func() {a.b = null;} }"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.methods.head.dependencies().isEmpty)
  }

  test("Superclass field not dependent") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy extends A {void func() {a.b = null;} }",
      "A" -> "public virtual class A {Object a;}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.methods.head.dependencies().isEmpty)
  }

  test("Outer class field is dependent") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy {Object a; class B {void func() {a.b = null;} } }",
    ))
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 52-55: No type declaration found for 'a'\n")
    assert(tds.head.nestedTypes.head.methods.head.dependencies().isEmpty)
  }

  test("Outer class static field not dependent") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy {static Object a; class B {void func() {a.b = null;} } }",
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.nestedTypes.head.methods.head.dependencies().isEmpty)
  }

  test("Property not dependent") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy {Object a {get;} void func() {a.b = null;} }"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.methods.head.dependencies().isEmpty)
  }

  test("Superclass property not dependent") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy extends A {void func() {a.b = null;} }",
      "A" -> "public virtual class A {Object a {get;}}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.methods.head.dependencies().isEmpty)
  }

  test("Local var not dependent") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy {void func() {Object a; a.b = null;} }"
    ))
    defaultOrg.issues.dumpMessages(false)
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.methods.head.dependencies() == Set(objectClass))
  }
}
