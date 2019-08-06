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
package com.nawforce.api

import java.io.ByteArrayInputStream
import java.nio.file.Paths

import com.nawforce.types.{ApexTypeDeclaration, TypeName}
import com.nawforce.utils.{DotName, Name}
import org.scalatest.FunSuite

class TypeFindingTest extends FunSuite {

  private val defaultOrg: Org = new Org

  test("Bad type not") {
    assert(new Org().getType(DotName(Seq(Name("Hello")))).isEmpty)
  }

  test("Platform type") {
    val typeName = TypeName(Seq(Name("String"))).withOuter(Some(TypeName(Name.System)))
    assert(new Org().getType(DotName(Seq(Name("String")))).get.typeName == typeName)
  }

  test("Platform type (wrong case)") {
    val typeName = TypeName(Seq(Name("String"))).withOuter(Some(TypeName(Name.System)))
    assert(new Org().getType(DotName(Seq(Name("STRING")))).get.typeName == typeName)
  }

  test("Custom Outer type") {
    val org = new Org()
    Org.current.withValue(org) {
      val td = ApexTypeDeclaration.create(defaultOrg.emptyUnmanaged, Paths.get("Dummy.cls"),
        new ByteArrayInputStream("public class Dummy {}".getBytes())).head
      org.upsertType(td)
      assert(org.getType(DotName(Seq(Name("Dummy")))).get.typeName == td.typeName)
    }
  }

  test("Custom Outer type (Wrong Case)") {
    val org = new Org()
    Org.current.withValue(org) {
      val td = ApexTypeDeclaration.create(defaultOrg.emptyUnmanaged, Paths.get("Dummy.cls"),
        new ByteArrayInputStream("public class Dummy {}".getBytes())).head
      org.upsertType(td)
      assert(org.getType(DotName(Seq(Name("dummy")))).get.typeName == td.typeName)
    }
  }

  test("Custom Inner type") {
    val org = new Org()
    Org.current.withValue(org) {
      val td = ApexTypeDeclaration.create(defaultOrg.emptyUnmanaged, Paths.get("Dummy.cls"),
        new ByteArrayInputStream("public class Dummy {class Inner {}}".getBytes())).head
      org.upsertType(td)
      val innerTypeName = DotName(Seq(Name("Dummy"), Name("Inner")))
      assert(org.getType(innerTypeName).get.typeName.asDotName == innerTypeName)
    }
  }

  test("Custom Inner type (Wrong case)") {
    val org = new Org()
    Org.current.withValue(org) {
      val td = ApexTypeDeclaration.create(defaultOrg.emptyUnmanaged, Paths.get("Dummy.cls"),
        new ByteArrayInputStream("public class Dummy {class Inner {}}".getBytes())).head
      org.upsertType(td)
      val innerTypeName = DotName(Seq(Name("Dummy"), Name("INner")))
      assert(org.getType(innerTypeName).get.typeName.asDotName == innerTypeName)
    }
  }

  test("Custom Outer type with namespace") {
    val org = new Org()
    val pkg = new Package(org, Name("NS"), Seq())
    Org.current.withValue(org) {
      val td = ApexTypeDeclaration.create(pkg, Paths.get("Dummy.cls"),
        new ByteArrayInputStream("public class Dummy {}".getBytes())).head
      org.upsertType(td)
      assert(org.getType(DotName(Seq(Name("NS"), Name("Dummy")))).get.typeName == td.typeName)
      assert(org.getType(DotName(Name("Dummy"))).isEmpty)
    }
  }

  test("Custom Inner type with namespace") {
    val org = new Org()
    val pkg = new Package(org, Name("NS"), Seq())
    Org.current.withValue(org) {
      val td = ApexTypeDeclaration.create(pkg, Paths.get("Dummy.cls"),
        new ByteArrayInputStream("public class Dummy {class Inner {}}".getBytes())).head
      org.upsertType(td)
      val innerTypeName = DotName(Seq(Name("NS"),Name("Dummy"), Name("Inner")))
      assert(org.getType(innerTypeName).get.typeName.asDotName == innerTypeName)
      assert(org.getType(DotName(Seq(Name("Dummy"), Name("Inner")))).isEmpty)
    }
  }
}
