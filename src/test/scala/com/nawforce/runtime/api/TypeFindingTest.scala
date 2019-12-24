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
package com.nawforce.runtime.api

import com.nawforce.common.api.Package
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.path.{PathFactory, PathLike}
import com.nawforce.common.types.ApexTypeDeclaration
import org.scalatest.funsuite.AnyFunSuite

class TypeFindingTest extends AnyFunSuite {

  private val defaultOrg: Org = new Org
  private val defaultPath: PathLike = PathFactory("Dummy.cls")
  private val unmanaged: Package = defaultOrg.unmanaged

  test("Bad type not") {
    assert(unmanaged.getTypeOption(TypeName(Name("Hello"))).isEmpty)
  }

  test("Platform type") {
    val typeName = TypeName(Seq(Name("String"))).withOuter(Some(TypeName(Name.System)))
    assert(unmanaged.getTypeOption(TypeName(Name("String"))).get.typeName == typeName)
  }

  test("Platform type (wrong case)") {
    val typeName = TypeName(Seq(Name("String"))).withOuter(Some(TypeName(Name.System)))
    assert(unmanaged.getTypeOption(TypeName(Name("STRING"))).get.typeName == typeName)
  }

  test("Custom Outer type") {
    val org = new Org()
    Org.current.withValue(org) {
      val td = ApexTypeDeclaration.create(org.unmanaged, defaultPath,
        "public class Dummy {}").head
      org.unmanaged.upsertType(td)
      assert(org.unmanaged.getTypeOption(TypeName(Name("Dummy"))).get.typeName == td.typeName)
    }
  }

  test("Custom Outer type (Wrong Case)") {
    val org = new Org()
    Org.current.withValue(org) {
      val td = ApexTypeDeclaration.create(defaultOrg.unmanaged, defaultPath,
        "public class Dummy {}").head
      org.unmanaged.upsertType(td)
      assert(org.unmanaged.getTypeOption(TypeName(Name("dummy"))).get.typeName == td.typeName)
    }
  }

  test("Custom Inner type") {
    val org = new Org()
    Org.current.withValue(org) {
      val td = ApexTypeDeclaration.create(defaultOrg.unmanaged, defaultPath,
        "public class Dummy {class Inner {}}").head
      org.unmanaged.upsertType(td)
      val innerTypeName = TypeName(Name("Inner"), Nil, Some(TypeName(Name("Dummy"))))
      assert(org.unmanaged.getTypeOption(innerTypeName).get.typeName == innerTypeName)
    }
  }

  test("Custom Inner type (Wrong case)") {
    val org = new Org()
    Org.current.withValue(org) {
      val td = ApexTypeDeclaration.create(defaultOrg.unmanaged, defaultPath,
        "public class Dummy {class Inner {}}").head
      org.unmanaged.upsertType(td)
      val innerTypeName = TypeName(Name("iNner"), Nil, Some(TypeName(Name("Dummy"))))
      assert(org.unmanaged.getTypeOption(innerTypeName).get.typeName == innerTypeName)
    }
  }

  test("Custom Outer type with namespace") {
    val org = new Org()
    val pkg = org.addPackage("NS", Array(), Array())
    Org.current.withValue(org) {
      val td = ApexTypeDeclaration.create(pkg, defaultPath,
        "global class Dummy {}").head
      pkg.upsertType(td)
      assert(org.unmanaged.getTypeOption(TypeName(Name("Dummy"), Nil, Some(TypeName(Name("NS"))))).get.typeName == td.typeName)
      assert(org.unmanaged.getTypeOption(TypeName(Name("Dummy"))).isEmpty)
    }
  }

  test("Custom Outer type with namespace not visible") {
    val org = new Org()
    val pkg = org.addPackage("NS", Array(), Array())
    Org.current.withValue(org) {
      val td = ApexTypeDeclaration.create(pkg, defaultPath,
        "public class Dummy {}").head
      pkg.upsertType(td)
      assert(org.getType("", "NS.Dummy") == null)
      assert(org.getType("", "Dummy") == null)
      assert(org.getType("NS", "NS.Dummy") != null)
      assert(org.getType("NS", "Dummy") != null)
    }
  }

  test("Custom Inner type with namespace") {
    val org = new Org()
    val pkg = org.addPackage("NS", Array(), Array())
    Org.current.withValue(org) {
      val td = ApexTypeDeclaration.create(pkg, defaultPath,
        "global class Dummy {class Inner {}}").head
      pkg.upsertType(td)
      val innerTypeName = TypeName(Name("Inner"), Nil, Some(TypeName(Name("Dummy"), Nil, Some(TypeName(Name("NS"))))))
      assert(org.unmanaged.getTypeOption(innerTypeName).get.typeName == innerTypeName)
      assert(org.unmanaged.getTypeOption(TypeName(Name("Inner"), Nil, Some(TypeName(Name("Dummy"))))).isEmpty)
    }
  }

  test("Custom Inner type with namespace not visible") {
    val org = new Org()
    val pkg = org.addPackage("NS", Array(), Array())
    Org.current.withValue(org) {
      val td = ApexTypeDeclaration.create(pkg, defaultPath,
        "public class Dummy {class Inner {}}").head
      pkg.upsertType(td)

      assert(org.getType("", "NS.Dummy.Inner") == null)
      assert(org.getType("", "Dummy.Inner") == null)
      assert(org.getType("NS", "NS.Dummy.Inner") != null)
      assert(org.getType("NS", "Dummy.Inner") != null)
    }
  }
}
