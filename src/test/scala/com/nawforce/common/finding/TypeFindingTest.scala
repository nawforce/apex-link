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
package com.nawforce.common.finding

import com.nawforce.common.api.Org
import com.nawforce.common.names.{DotName, Name, TypeName}
import com.nawforce.common.org.OrgImpl
import com.nawforce.common.path.{PathFactory, PathLike}
import com.nawforce.common.pkg.PackageImpl
import com.nawforce.common.types.TypeDeclaration
import com.nawforce.common.types.apex.FullDeclaration
import org.scalatest.funsuite.AnyFunSuite

class TypeFindingTest extends AnyFunSuite {

  private val defaultOrg: OrgImpl = new OrgImpl
  private val defaultPath: PathLike = PathFactory("Dummy.cls")
  private val unmanaged: PackageImpl = defaultOrg.unmanaged

  private def getType(namespace: String, dotName: String, org: OrgImpl = defaultOrg): TypeDeclaration = {
    val ns = Name.safeApply(namespace)
    val dn = DotName(dotName)
    val pkgOpt = org.packagesByNamespace.get(ns)
    pkgOpt.flatMap(pkg => TypeRequest(dn.asTypeName(), pkg, excludeSObjects = false).toOption).orNull
  }

  test("Bad type not") {
    OrgImpl.current.withValue(defaultOrg) {
      assert(unmanaged.getType(TypeName(Name("Hello")), None).toOption.isEmpty)
    }
  }

  test("Platform type") {
    OrgImpl.current.withValue(defaultOrg) {
      val typeName = TypeName(Seq(Name("String"))).withOuter(Some(TypeName(Name.System)))
      assert(unmanaged.getType(TypeName(Name("String")), None).toOption.get.typeName == typeName)
    }
  }

  test("Platform type (wrong case)") {
    OrgImpl.current.withValue(defaultOrg) {
      val typeName = TypeName(Seq(Name("String"))).withOuter(Some(TypeName(Name.System)))
      assert(unmanaged.getType(TypeName(Name("STRING")), None).toOption.get.typeName == typeName)
    }
  }

  test("Custom Outer type") {
    val org = Org.newOrg().asInstanceOf[OrgImpl]
    OrgImpl.current.withValue(org) {
      val td = FullDeclaration.create(org.unmanaged, defaultPath,
        "public class Dummy {}").head
      org.unmanaged.upsertMetadata(td)
      assert(org.unmanaged.getType(TypeName(Name("Dummy")), None).toOption.get.typeName == td.typeName)
    }
  }

  test("Custom Outer type (Wrong Case)") {
    val org = Org.newOrg().asInstanceOf[OrgImpl]
    OrgImpl.current.withValue(org) {
      val td = FullDeclaration.create(defaultOrg.unmanaged, defaultPath,
        "public class Dummy {}").head
      org.unmanaged.upsertMetadata(td)
      assert(org.unmanaged.getType(TypeName(Name("dummy")), None).toOption.get.typeName == td.typeName)
    }
  }

  test("Custom Inner type") {
    val org = Org.newOrg().asInstanceOf[OrgImpl]
    OrgImpl.current.withValue(org) {
      val td = FullDeclaration.create(defaultOrg.unmanaged, defaultPath,
        "public class Dummy {class Inner {}}").head
      org.unmanaged.upsertMetadata(td)
      val innerTypeName = TypeName(Name("Inner"), Nil, Some(TypeName(Name("Dummy"))))
      assert(org.unmanaged.getType(innerTypeName, None).toOption.get.typeName == innerTypeName)
    }
  }

  test("Custom Inner type (Wrong case)") {
    val org = Org.newOrg().asInstanceOf[OrgImpl]
    OrgImpl.current.withValue(org) {
      val td = FullDeclaration.create(defaultOrg.unmanaged, defaultPath,
        "public class Dummy {class Inner {}}").head
      org.unmanaged.upsertMetadata(td)
      val innerTypeName = TypeName(Name("iNner"), Nil, Some(TypeName(Name("Dummy"))))
      assert(org.unmanaged.getType(innerTypeName, None).toOption.get.typeName == innerTypeName)
    }
  }

  test("Custom Outer type with namespace") {
    val org = Org.newOrg().asInstanceOf[OrgImpl]
    val pkg = org.newPackage("NS", Array(), Array()).asInstanceOf[PackageImpl]
    OrgImpl.current.withValue(org) {
      val td = FullDeclaration.create(pkg, defaultPath,
        "global class Dummy {}").head
      pkg.upsertMetadata(td)
      assert(org.unmanaged.getType(TypeName(Name("Dummy"), Nil, Some(TypeName(Name("NS")))), None)
        .toOption.get.typeName == td.typeName)
      assert(org.unmanaged.getType(TypeName(Name("Dummy")), None).toOption.isEmpty)
    }
  }

  test("Custom Outer type with namespace not visible") {
    val org = Org.newOrg().asInstanceOf[OrgImpl]
    val pkg = org.newPackage("NS", Array(), Array()).asInstanceOf[PackageImpl]
    OrgImpl.current.withValue(org) {
      val td = FullDeclaration.create(pkg, defaultPath,
        "public class Dummy {}").head
      pkg.upsertMetadata(td)
      assert(getType("", "NS.Dummy", org) == null)
      assert(getType("", "Dummy", org) == null)
      assert(getType("NS", "NS.Dummy", org) != null)
      assert(getType("NS", "Dummy", org) != null)
    }
  }

  test("Custom Inner type with namespace") {
    val org = Org.newOrg().asInstanceOf[OrgImpl]
    val pkg = org.newPackage("NS", Array(), Array()).asInstanceOf[PackageImpl]
    OrgImpl.current.withValue(org) {
      val td = FullDeclaration.create(pkg, defaultPath,
        "global class Dummy {class Inner {}}").head
      pkg.upsertMetadata(td)
      val innerTypeName = TypeName(Name("Inner"), Nil, Some(TypeName(Name("Dummy"), Nil, Some(TypeName(Name("NS"))))))
      assert(org.unmanaged.getType(innerTypeName, None).toOption.get.typeName == innerTypeName)
      assert(org.unmanaged.getType(TypeName(Name("Inner"), Nil, Some(TypeName(Name("Dummy")))), None).toOption.isEmpty)
    }
  }

  test("Custom Inner type with namespace not visible") {
    val org = Org.newOrg().asInstanceOf[OrgImpl]
    val pkg = org.newPackage("NS", Array(), Array()).asInstanceOf[PackageImpl]
    OrgImpl.current.withValue(org) {
      val td = FullDeclaration.create(pkg, defaultPath,
        "public class Dummy {class Inner {}}").head
      pkg.upsertMetadata(td)

      assert(getType("", "NS.Dummy.Inner", org) == null)
      assert(getType("", "Dummy.Inner", org) == null)
      assert(getType("NS", "NS.Dummy.Inner", org) != null)
      assert(getType("NS", "Dummy.Inner", org) != null)
    }
  }
}
