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
package com.nawforce.common.types

import com.nawforce.common.FileSystemHelper
import com.nawforce.common.api.{Name, Org, ServerOps, TypeIdentifier}
import com.nawforce.common.names.TypeNames
import com.nawforce.common.org.OrgImpl
import com.nawforce.common.path.PathLike
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class InterviewTest extends AnyFunSuite with BeforeAndAfter {

  before {
    ServerOps.setAutoFlush(false)
  }

  after {
    ServerOps.setAutoFlush(true)
  }

  test("Interview createInterview") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy { {Flow.Interview i = Flow.Interview.createInterview('', new Map<String, Object>());} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Custom flow (MDAPI)") {
    FileSystemHelper.run(
      Map("Test.flow" -> "", "Dummy.cls" -> "public class Dummy { {Flow.Interview.Test;} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Custom flow (SFDX)") {
    FileSystemHelper.run(
      Map("Test.flow-meta.xml" -> "",
          "Dummy.cls" -> "public class Dummy { {Flow.Interview.Test;} }")) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.newMDAPIPackageInternal(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Missing flow") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy { {Flow.Interview.Test;} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        // TODO: This should be a missing issue
        assert(
          org.issues.getMessages("/Dummy.cls") ==
            "Missing: line 1 at 22-41: Unknown field or type 'Test' on 'Flow.Interview'\n")
    }
  }

  test("Create flow") {
    FileSystemHelper.run(
      Map(
        "Test.flow-meta.xml" -> "",
        "Dummy.cls" -> "public class Dummy { {Flow.Interview i = new Flow.Interview.Test(new Map<String, Object>());} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Create flow (namespaced)") {
    FileSystemHelper.run(
      Map(
        "Test.flow-meta.xml" -> "",
        "Dummy.cls" -> "public class Dummy { {Flow.Interview i = new Flow.Interview.pkg.Test(new Map<String, Object>());} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(Some(Name("pkg")), Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Create flow (namespaced but without namespace)") {
    FileSystemHelper.run(
      Map(
        "Test.flow-meta.xml" -> "",
        "Dummy.cls" -> "public class Dummy { {Flow.Interview i = new Flow.Interview.Test(new Map<String, Object>());} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(Some(Name("pkg")), Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Create flow (ghosted)") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy { {Flow.Interview i = new Flow.Interview.ghosted.Test(new Map<String, Object>());} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        val pkg1 = org.newMDAPIPackageInternal(Some(Name("ghosted")), Seq(), Seq())
        org.newMDAPIPackageInternal(Some(Name("pkg")), Seq(root), Seq(pkg1))
        assert(!org.issues.hasMessages)
    }
  }

  test("Create flow (packaged)") {
    FileSystemHelper.run(
      Map("pkg1/Test.flow-meta.xml" -> "",
          "pkg2/Dummy.cls" -> "public class Dummy { {Flow.Interview i = new Flow.Interview.pkg1.Test(new Map<String, Object>());} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        val pkg1 = org.newMDAPIPackageInternal(Some(Name("pkg1")), Seq(root.join("pkg1")), Seq())
        val pkg2 =
          org.newMDAPIPackageInternal(Some(Name("pkg2")), Seq(root.join("pkg2")), Seq(pkg1))
        assert(!org.issues.hasMessages)

        val interviewType1 = TypeIdentifier.fromJava(Name("pkg1"), TypeNames.Interview)
        val interviewType2 = TypeIdentifier.fromJava(Name("pkg2"), TypeNames.Interview)
        assert(
          pkg2
            .getDependencies(interviewType2, inheritanceOnly = false)
            .sameElements(Array(interviewType1)))
        assert(pkg1.getDependencyHolders(interviewType1).sameElements(Array(interviewType2)))

        val dummyType =
          pkg2.getTypeOfPathInternal(root.join("pkg2").join("Dummy.cls")).get.asTypeIdentifier
        assert(
          pkg2
            .getDependencies(dummyType, inheritanceOnly = false)
            .sameElements(Array(interviewType2)))
        assert(pkg2.getDependencyHolders(interviewType2).sameElements(Array(dummyType)))
    }
  }

  test("Create flow (missing flow)") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy { {Flow.Interview i = new Flow.Interview.Test(new Map<String, Object>());} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(Some(Name("pkg")), Seq(root), Seq())
        assert(
          org.issues.getMessages("/Dummy.cls") ==
            "Missing: line 1 at 45-64: No type declaration found for 'Flow.Interview.Test'\n")
    }
  }

  test("Start flow") {
    FileSystemHelper.run(
      Map(
        "Test.flow-meta.xml" -> "",
        "Dummy.cls" -> "public class Dummy { {new Flow.Interview.Test(new Map<String, Object>()).start();} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }
}
