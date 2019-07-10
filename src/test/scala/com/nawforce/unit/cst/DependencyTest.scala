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
package com.nawforce.unit.cst

import java.io.ByteArrayInputStream
import java.nio.file.{Path, Paths}

import com.nawforce.api.Org
import com.nawforce.types.TypeDeclaration
import com.nawforce.utils.{DotName, Name}
import org.scalatest.FunSuite

class DependencyTest extends FunSuite {
  private val defaultName: Name = Name("Dummy")
  private val defaultPath: Path = Paths.get(defaultName.toString)
  private val defaultOrg: Org = new Org

  def typeDeclarations(classes: Map[String, String]): Seq[TypeDeclaration] = {
    defaultOrg.issues.clear()
    val paths = classes.map(kv => {
      val fakePath = Paths.get(kv._1 + ".cls")
      defaultOrg.setInputStream(fakePath, new ByteArrayInputStream(kv._2.getBytes()))
      fakePath
    }).toSeq

    Org.current.withValue(defaultOrg) {
      defaultOrg.deployMetadata(Name.Empty, paths)
      if (defaultOrg.issues.hasMessages)
        defaultOrg.issues.dumpMessages(json = false)
      defaultOrg.getTypes(classes.keys.map(k => DotName(k)).toSeq)
    }
  }

  test("Empty class has no imports") {
    val tds = typeDeclarations(Map("Dummy" -> "public class Dummy {}"))
    assert(tds.head.dependencies().isEmpty)
  }

  test("Class depends on superclass") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy extends A {}",
      "A" -> "public virtual class A {}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.dependencies() == tds.tail.toSet)
  }

  test("Class depends on interface") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy implements A, B {}",
      "A" -> "public interface A {}",
      "B" -> "public interface B {}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.dependencies() == tds.tail.toSet)
  }

  test("Interface depends on interface") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public interface Dummy extends A, B {}",
      "A" -> "public interface A {}",
      "B" -> "public interface B {}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.dependencies() == tds.tail.toSet)
  }

  test("Empty inner class has no dependencies") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy { class Inner {} }"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.nestedTypes.head.dependencies().isEmpty)
  }

  test("Inner class depends on superclass") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy { class Inner extends A {} }",
      "A" -> "public virtual class A {}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.nestedTypes.head.dependencies() == tds.tail.toSet)
  }

  test("Inner class depends on interface") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy { class Inner implements A,B {} }",
      "A" -> "public interface A {}",
      "B" -> "public interface B {}",
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.nestedTypes.head.dependencies() == tds.tail.toSet)
  }

  test("Inner interface depends on interface") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy { interface Inner extends A, B {} }",
      "A" -> "public interface A {}",
      "B" -> "public interface B {}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.nestedTypes.head.dependencies() == tds.tail.toSet)
  }

  test("Class reference creates dependency") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy { {Type t = A.class;} }",
      "A" -> "public class A {}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    val typeClass = defaultOrg.getType(DotName(Seq(Name.System, Name.Type)))
    assert(tds.head.blocks.head.dependencies() == Set(tds.tail.head, typeClass.get))
  }

  test("Method return creates dependency") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy { A func() {return null;} }",
      "A" -> "public class A {}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.methods.head.dependencies() == tds.tail.toSet)
  }

  test("Method parameter creates dependency") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy { void func(A a) {} }",
      "A" -> "public class A {}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.methods.head.dependencies() == tds.tail.toSet)
  }

  test("Field type creates dependency") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy {A a;}",
      "A" -> "public class A {}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    Org.current.withValue(defaultOrg) {
      assert(tds.head.fields.head.dependencies() == tds.tail.toSet)
    }
  }

  test("Property type creates dependency") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy {A a {get;} }",
      "A" -> "public class A {}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    Org.current.withValue(defaultOrg) {
      assert(tds.head.fields.head.dependencies() == tds.tail.toSet)
    }
  }

  test("Local var creates dependency") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy {static {A a;} }",
      "A" -> "public class A {}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.blocks.head.dependencies() == tds.tail.toSet)
  }

  test("Cast creates dependency") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy {static {Object a=(A)null;} }",
      "A" -> "public class A {}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.blocks.head.dependencies() == tds.tail.toSet)
  }

  test("For control creates dependency") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy { void func() { for(A a;;) {}} }",
      "A" -> "public class A {}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.methods.head.dependencies() == tds.tail.toSet)
  }

  test("Catch creates dependency") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy { void func() { try {} catch(A a){} } }",
      "A" -> "public class A {}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.methods.head.dependencies() == tds.tail.toSet)
  }

  test("New creates dependency") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy { void func() { Object a = new A(); } }",
      "A" -> "public class A {}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.methods.head.dependencies() == tds.tail.toSet)
  }

  test("Complex New creates dependency") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy { void func() { Object a = new List<A>(); } }",
      "A" -> "public class A {}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    val listClass = defaultOrg.getType(DotName(Seq(Name.System, Name.List)))
    assert(tds.head.methods.head.dependencies() == Set(tds.tail.head,listClass.get))
  }

  test("Class reference in Inner creates dependency") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy { class Inner { {Type t = A.class;} } }",
      "A" -> "public class A {}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    val typeClass = defaultOrg.getType(DotName(Seq(Name.System, Name.Type)))
    assert(tds.head.nestedTypes.head.blocks.head.dependencies() == Set(tds.tail.head, typeClass.get))
  }

  test("Method return in Inner creates dependency") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy { class Inner { A func() {return null;} } }",
      "A" -> "public class A {}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.nestedTypes.head.methods.head.dependencies() == tds.tail.toSet)
  }

  test("Method parameter in Inner creates dependency") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy { class Inner { void func(A a) {} } }",
      "A" -> "public class A {}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.nestedTypes.head.methods.head.dependencies() == tds.tail.toSet)
  }

  test("Inner Field type creates dependency") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy {class Inner {A a;}}",
      "A" -> "public class A {}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    Org.current.withValue(defaultOrg) {
      assert(tds.head.nestedTypes.head.fields.head.dependencies() == tds.tail.toSet)
    }
  }

  test("Inner Property type creates dependency") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy {class Inner {A a {get;}}}",
      "A" -> "public class A {}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    Org.current.withValue(defaultOrg) {
      assert(tds.head.nestedTypes.head.fields.head.dependencies() == tds.tail.toSet)
    }
  }

  test("Inner Local var creates dependency") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy {class Inner {static {A a;} } }",
      "A" -> "public class A {}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.nestedTypes.head.blocks.head.dependencies() == tds.tail.toSet)
  }

  test("Inner Cast creates dependency") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy {class Inner {static {Object a=(A)null;} } }",
      "A" -> "public class A {}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.nestedTypes.head.blocks.head.dependencies() == tds.tail.toSet)
  }

  test("Inner For control creates dependency") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy { class Inner {void func() { for(A a;;) {}} } }",
      "A" -> "public class A {}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.nestedTypes.head.methods.head.dependencies() == tds.tail.toSet)
  }

  test("Inner Catch creates dependency") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy { class Inner {void func() { try {} catch(A a){} } } }",
      "A" -> "public class A {}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.nestedTypes.head.methods.head.dependencies() == tds.tail.toSet)
  }

  test("Inner New creates dependency") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy { class Inner {void func() { Object a = new A(); } } }",
      "A" -> "public class A {}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.nestedTypes.head.methods.head.dependencies() == tds.tail.toSet)
  }

  test("Inner Complex New creates dependency") {
    val tds = typeDeclarations(Map(
      "Dummy" -> "public class Dummy { class Inner {void func() { Object a = new List<A>(); } } }",
      "A" -> "public class A {}"
    ))
    assert(!defaultOrg.issues.hasMessages)
    val listClass = defaultOrg.getType(DotName(Seq(Name.System, Name.List)))
    assert(tds.head.nestedTypes.head.methods.head.dependencies() == Set(tds.tail.head,listClass.get))
  }
}
