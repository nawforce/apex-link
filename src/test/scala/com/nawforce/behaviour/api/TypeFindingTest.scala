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
package com.nawforce.behaviour.api

import java.io.ByteArrayInputStream
import java.nio.file.Paths

import com.nawforce.api.Org
import com.nawforce.types.{ApexTypeDeclaration, TypeName}
import com.nawforce.utils.Name
import org.scalatest.FunSuite

class TypeFindingTest extends FunSuite {

  test("Bad type not found") {
    assert(new Org().getType(TypeName(Seq(Name("Hello")))).isEmpty)
  }

  test("Platform type found") {
    val typeName = TypeName(Seq(Name("String"))).withOuter(Some(TypeName(Name.System)))
    assert(new Org().getType(TypeName(Seq(Name("String")))).get.typeName == typeName)
  }

  test("Platform type found (wrong case)") {
    val typeName = TypeName(Seq(Name("String"))).withOuter(Some(TypeName(Name.System)))
    assert(new Org().getType(TypeName(Seq(Name("STRING")))).get.typeName == typeName)
  }

  test("Custom Outer type found") {
    val org = new Org()
    Org.current.withValue(org) {
      val td = ApexTypeDeclaration.create(Paths.get("Dummy.cls"),
        new ByteArrayInputStream("public class Dummy {}".getBytes())).get
      org.upsertType(td)
      assert(org.getType(TypeName(Seq(Name("Dummy")))).get.typeName == td.typeName)
    }
  }

  test("Custom Outer type found (Wrong Case)") {
    val org = new Org()
    Org.current.withValue(org) {
      val td = ApexTypeDeclaration.create(Paths.get("Dummy.cls"),
        new ByteArrayInputStream("public class Dummy {}".getBytes())).get
      org.upsertType(td)
      assert(org.getType(TypeName(Seq(Name("dummy")))).get.typeName == td.typeName)
    }
  }

  test("Custom Inner type found") {
    val org = new Org()
    Org.current.withValue(org) {
      val td = ApexTypeDeclaration.create(Paths.get("Dummy.cls"),
        new ByteArrayInputStream("public class Dummy {class Inner {}}".getBytes())).get
      org.upsertType(td)
      val innerTypeName = TypeName(Name("Inner")).withOuter(Some(td.typeName))
      assert(org.getType(innerTypeName).get.typeName == innerTypeName)
    }
  }

  test("Custom Inner type found (Wrong case)") {
    val org = new Org()
    Org.current.withValue(org) {
      val td = ApexTypeDeclaration.create(Paths.get("Dummy.cls"),
        new ByteArrayInputStream("public class Dummy {class Inner {}}".getBytes())).get
      org.upsertType(td)
      val innerTypeName = TypeName(Name("INner")).withOuter(Some(td.typeName))
      assert(org.getType(innerTypeName).get.typeName == innerTypeName)
    }
  }
}
