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

import com.nawforce.types.{ApexTypeDeclaration, TypeDeclaration, TypeName}
import com.nawforce.utils.{IssueLog, Name}
import org.scalatest.FunSuite

class ImportTest extends FunSuite {
  private val defaultName: Name = Name("Dummy")
  private val defaultPath: Path = Paths.get(defaultName.toString)

  def typeDeclaration(clsText: String): TypeDeclaration = {
    IssueLog.clear()
    val td = ApexTypeDeclaration.create(defaultPath, new ByteArrayInputStream(clsText.getBytes()))
    if (td.isEmpty)
      IssueLog.dumpMessages(json=false)
    td.get
  }

  test("Empty class has no imports") {
    assert(typeDeclaration("public class Dummy {}").imports.isEmpty)
  }

  test("Class imports superclass") {
    assert(typeDeclaration("public class Dummy extends A {}").imports ==
      Set((TypeName(Name("A")),TypeName(Name("Dummy")))))
  }

  test("Class imports interface") {
    assert(typeDeclaration("public class Dummy implements A, B {}").imports ==
      Set((TypeName(Name("A")),TypeName(Name("Dummy"))),(TypeName(Name("B")),TypeName(Name("Dummy")))))
  }

  test("Interface imports interface") {
    assert(typeDeclaration("public interface Dummy extends A, B {}").imports ==
      Set((TypeName(Name("A")),TypeName(Name("Dummy"))),(TypeName(Name("B")),TypeName(Name("Dummy")))))
  }

  test("Empty inner class has no imports") {
    assert(typeDeclaration("public class Dummy {class Inner {} }").nestedTypes.head.imports.isEmpty)
  }

  test("Inner class imports superclass") {
    val innerType = TypeName(Name("Inner")).withOuter(Some(TypeName(Name("Dummy"))))
    assert(typeDeclaration("public class Dummy {class Inner extends A {}}").nestedTypes.head.imports ==
      Set((TypeName(Name("A")),innerType)))
  }

  test("Inner class imports interface") {
    val innerType = TypeName(Name("Inner")).withOuter(Some(TypeName(Name("Dummy"))))
    assert(typeDeclaration("public class Dummy {class Inner implements A, B {}}").nestedTypes.head.imports ==
      Set((TypeName(Name("A")),innerType),(TypeName(Name("B")),innerType)))
  }

  test("Inner interface imports interface") {
    val innerType = TypeName(Name("Inner")).withOuter(Some(TypeName(Name("Dummy"))))
    assert(typeDeclaration("public class Dummy {interface Inner extends A, B {}}").nestedTypes.head.imports ==
      Set((TypeName(Name("A")),innerType),(TypeName(Name("B")),innerType)))
  }

  test("Class reference imports") {
    assert(typeDeclaration("public class Dummy {{Type t = Ref.class;}}").imports ==
      Set((TypeName(Name("Ref")).asClassOf,TypeName(Name("Dummy"))), (TypeName(Name("Type")),TypeName(Name("Dummy")))))
  }

  test("Method return type imports") {
    assert(typeDeclaration("public class Dummy {Ref func() {}}").imports ==
      Set((TypeName(Name("Ref")),TypeName(Name("Dummy")))))
  }

  test("Method parameter type imports") {
    assert(typeDeclaration("public class Dummy {void func(Ref a) {}}").imports ==
      Set((TypeName(Name("Ref")),TypeName(Name("Dummy")))))
  }

  test("Field type imports") {
    assert(typeDeclaration("public class Dummy {Ref field;}").imports ==
      Set((TypeName(Name("Ref")),TypeName(Name("Dummy")))))
  }

  test("Property type imports") {
    assert(typeDeclaration("public class Dummy {Ref field {get;}}").imports ==
      Set((TypeName(Name("Ref")),TypeName(Name("Dummy")))))
  }

  test("Local var type imports") {
    assert(typeDeclaration("public class Dummy {static {Ref a;}}").imports ==
      Set((TypeName(Name("Ref")),TypeName(Name("Dummy")))))
  }

  test("Cast expression imports") {
    assert(typeDeclaration("public class Dummy {static {Object a=(Ref)null;}}").imports ==
      Set((TypeName(Name("Ref")),TypeName(Name("Dummy")))))
  }

  test("For control imports") {
    assert(typeDeclaration("public class Dummy {void func() {for(Ref a;;) {}} }").imports ==
      Set((TypeName(Name("Ref")),TypeName(Name("Dummy")))))
  }

  test("Catch imports") {
    assert(typeDeclaration("public class Dummy {void func() {try {} catch(Ref a) {}} }").imports ==
      Set((TypeName(Name("Ref")),TypeName(Name("Dummy")))))
  }

  test("New imports") {
    assert(typeDeclaration("public class Dummy {void func() {Object a = new Ref();} }").imports ==
      Set((TypeName(Name("Ref")),TypeName(Name("Dummy")))))
  }

  test("Complex new imports") {
    assert(typeDeclaration("public class Dummy {void func() {Object a = new Foo.Bar<Baz>();} }").imports ==
      Set((
        TypeName(Name("Bar"), Seq(TypeName(Name("Baz"))), Some(TypeName(Name("Foo"), Nil, None))),
        TypeName(Name("Dummy"))
      )))
  }

  test("Class reference imports in Inner") {
    val innerType = TypeName(Name("Inner")).withOuter(Some(TypeName(Name("Dummy"))))
    assert(typeDeclaration("public class Dummy {class Inner {{Type t = Ref.class;}}}")
      .nestedTypes.head.imports ==
      Set((TypeName(Name("Ref")).asClassOf, innerType), (TypeName(Name("Type")), innerType)))
  }

  test("Method return type imports in Inner") {
    val innerType = TypeName(Name("Inner")).withOuter(Some(TypeName(Name("Dummy"))))
    assert(typeDeclaration("public class Dummy {class Inner {Ref func() {}}}")
      .nestedTypes.head.imports ==
      Set((TypeName(Name("Ref")), innerType)))
  }

  test("Method parameter type imports in Inner") {
    val innerType = TypeName(Name("Inner")).withOuter(Some(TypeName(Name("Dummy"))))
    assert(typeDeclaration("public class Dummy {class Inner {void func(Ref a) {}}}")
      .nestedTypes.head.imports ==
      Set((TypeName(Name("Ref")), innerType)))
  }

  test("Field type imports in Inner") {
    val innerType = TypeName(Name("Inner")).withOuter(Some(TypeName(Name("Dummy"))))
    assert(typeDeclaration("public class Dummy {class Inner {Ref field;}}")
      .nestedTypes.head.imports ==
      Set((TypeName(Name("Ref")), innerType)))
  }

  test("Property type imports in Inner") {
    val innerType = TypeName(Name("Inner")).withOuter(Some(TypeName(Name("Dummy"))))
    assert(typeDeclaration("public class Dummy {class Inner {Ref field {get;}}}")
      .nestedTypes.head.imports ==
      Set((TypeName(Name("Ref")), innerType)))
  }

  test("Local var type imports in Inner") {
    val innerType = TypeName(Name("Inner")).withOuter(Some(TypeName(Name("Dummy"))))
    assert(typeDeclaration("public class Dummy {class Inner {static {Ref a;}}}")
      .nestedTypes.head.imports ==
      Set((TypeName(Name("Ref")), innerType)))
  }

  test("Cast expression imports in Inner") {
    val innerType = TypeName(Name("Inner")).withOuter(Some(TypeName(Name("Dummy"))))
    assert(typeDeclaration("public class Dummy {class Inner {static {Object a=(Ref)null;}}}")
      .nestedTypes.head.imports ==
      Set((TypeName(Name("Ref")), innerType)))
  }

  test("For control imports in Inner") {
    val innerType = TypeName(Name("Inner")).withOuter(Some(TypeName(Name("Dummy"))))
    assert(typeDeclaration("public class Dummy {class Inner {void func() {for(Ref a;;) {}} }}")
      .nestedTypes.head.imports ==
      Set((TypeName(Name("Ref")), innerType)))
  }

  test("Catch imports in Inner") {
    val innerType = TypeName(Name("Inner")).withOuter(Some(TypeName(Name("Dummy"))))
    assert(typeDeclaration("public class Dummy {class Inner {void func() {try {} catch(Ref a) {}} }}")
      .nestedTypes.head.imports ==
      Set((TypeName(Name("Ref")), innerType)))
  }

  test("New imports in Inner") {
    val innerType = TypeName(Name("Inner")).withOuter(Some(TypeName(Name("Dummy"))))
    assert(typeDeclaration("public class Dummy {class Inner {void func() {Object a = new Ref();} }}")
      .nestedTypes.head.imports ==
      Set((TypeName(Name("Ref")), innerType)))
  }

  test("Complex new imports in Inner") {
    val innerType = TypeName(Name("Inner")).withOuter(Some(TypeName(Name("Dummy"))))
    assert(typeDeclaration("public class Dummy {class Inner {void func() {Object a = new Foo.Bar<Baz>();} }}")
      .nestedTypes.head.imports ==
      Set((
        TypeName(Name("Bar"), Seq(TypeName(Name("Baz"))), Some(TypeName(Name("Foo"), Nil, None))),
        innerType
      )))
  }
}
