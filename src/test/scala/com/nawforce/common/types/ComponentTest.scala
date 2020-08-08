/*
 [The "BSD licence"]
 Copyright (c) 2020 Kevin Jones
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
package com.nawforce.common.types

import com.nawforce.common.api.{Name, Org, ServerOps, TypeIdentifier}
import com.nawforce.common.names.TypeNames
import com.nawforce.common.org.OrgImpl
import com.nawforce.common.path.PathLike
import com.nawforce.runtime.FileSystemHelper
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class ComponentTest extends AnyFunSuite with BeforeAndAfter {

  before {
    ServerOps.setAutoFlush(false)
  }

  after {
    ServerOps.setAutoFlush(true)
  }

  test("Custom component (MDAPI)") {
    FileSystemHelper.run(Map(
      "Test.component" -> "",
      "Dummy.cls" -> "public class Dummy { {Component.Test;} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.newMDAPIPackageInternal(None, Array(root), Array())
      assert(!org.issues.hasMessages)
    }
  }

  test("Missing component") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy { {Component.Test;} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.newMDAPIPackageInternal(None, Array(root), Array())
      // TODO: This should be a missing issue
      assert(org.issues.getMessages("/Dummy.cls") ==
        "Missing: line 1 at 22-36: Unknown field or type 'Test' on 'Component'\n")
    }
  }

  test("Create component") {
    FileSystemHelper.run(Map(
      "Test.component" -> "",
      "Dummy.cls" -> "public class Dummy { {Component c = new Component.Test();} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.newMDAPIPackageInternal(None, Array(root), Array())
      assert(!org.issues.hasMessages)
    }
  }

  test("Create component (c namespace)") {
    FileSystemHelper.run(Map(
      "Test.component" -> "",
      "Dummy.cls" -> "public class Dummy { {Component c = new Component.c.Test();} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.newMDAPIPackageInternal(None, Array(root), Array())
      assert(!org.issues.hasMessages)
    }
  }

  test("Create component (namespaced)") {
    FileSystemHelper.run(Map(
      "Test.component" -> "",
      "Dummy.cls" -> "public class Dummy { {Component c = new Component.pkg.Test();} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.newMDAPIPackageInternal(Some(Name("pkg")), Array(root), Array())
      assert(!org.issues.hasMessages)
    }
  }

  test("Create component (namespaced but without namespace)") {
    FileSystemHelper.run(Map(
      "Test.component" -> "",
      "Dummy.cls" -> "public class Dummy { {Component c = new Component.Test();} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.newMDAPIPackageInternal(Some(Name("pkg")), Array(root), Array())
      assert(!org.issues.hasMessages)
    }
  }

  test("Create component (ghosted)") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy { {Component c = new Component.ghosted.Test();} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg1 = org.newMDAPIPackageInternal(Some(Name("ghosted")), Array(), Array())
      org.newMDAPIPackageInternal(Some(Name("pkg")), Array(root), Array(pkg1))
      assert(!org.issues.hasMessages)
    }
  }

  test("Create component (packaged)") {
    FileSystemHelper.run(Map(
      "pkg1/Test.component" -> "",
      "pkg2/Dummy.cls" -> "public class Dummy { {Component c = new Component.pkg1.Test();} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg1 = org.newMDAPIPackageInternal(Some(Name("pkg1")), Array(root.join("pkg1")), Array())
      val pkg2 = org.newMDAPIPackageInternal(Some(Name("pkg2")), Array(root.join("pkg2")), Array(pkg1))
      assert(!org.issues.hasMessages)

      val componentType1 = TypeIdentifier.fromJava(Name("pkg1"), TypeNames.Component)
      val componentType2 = TypeIdentifier.fromJava(Name("pkg2"), TypeNames.Component)
      assert(pkg2.getDependencies(componentType2, inheritanceOnly = false).sameElements(Array(componentType1)))
      assert(pkg1.getDependencyHolders(componentType1).sameElements(Array(componentType2)))

      val dummyType = pkg2.getTypeOfPathInternal(root.join("pkg2").join("Dummy.cls")).get.asTypeIdentifier
      assert(pkg2.getDependencies(dummyType, inheritanceOnly = false).sameElements(Array(componentType2)))
      assert(pkg2.getDependencyHolders(componentType2).sameElements(Array(dummyType)))
    }
  }
}
