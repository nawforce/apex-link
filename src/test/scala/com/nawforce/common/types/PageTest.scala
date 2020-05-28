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

import com.nawforce.common.api.{Name, Org, ServerOps}
import com.nawforce.common.org.OrgImpl
import com.nawforce.common.path.PathLike
import com.nawforce.runtime.FileSystemHelper
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class PageTest extends AnyFunSuite with BeforeAndAfter {

  before {
    ServerOps.setParsedDataCaching(false)
  }

  test("Valid page") {
    FileSystemHelper.run(Map(
      "TestPage.page" -> "",
      "Dummy.cls" -> "public class Dummy { {PageReference a = Page.TestPage;} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Valid page (case insensitive)") {
    FileSystemHelper.run(Map(
      "TestPage.page" -> "",
      "Dummy.cls" -> "public class Dummy { {PageReference a = Page.tesTPage;} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Missing page") {
    FileSystemHelper.run(Map(
      "TestPage.page" -> "",
      "Dummy.cls" -> "public class Dummy { {PageReference a = Page.AnotherPage;} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addMDAPITestPackage(None, Seq(root), Seq())
      assert(org.issues.getMessages("/Dummy.cls") ==
        "Missing: line 1 at 40-56: Unknown field or type 'AnotherPage' on 'Page'\n")
    }
  }

  test("Base package page") {
    FileSystemHelper.run(Map(
      "pkg1/TestPage.page" -> "",
      "pkg2/Dummy.cls" -> "public class Dummy { {PageReference a = Page.pkg1__TestPage;} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg1 = org.addMDAPITestPackage(Some(Name("pkg1")), Seq(root.join("pkg1")), Seq())
      org.addMDAPITestPackage(Some(Name("pkg2")), Seq(root.join("pkg2")), Seq(pkg1))
      assert(!org.issues.hasMessages)
    }
  }

  test("Ghost package page") {
    FileSystemHelper.run(Map(
      "pkg2/Dummy.cls" -> "public class Dummy { {PageReference a = Page.pkg1__TestPage;} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg1 = org.addMDAPITestPackage(Some(Name("pkg1")), Seq(), Seq())
      org.addMDAPITestPackage(Some(Name("pkg2")), Seq(root.join("pkg2")), Seq(pkg1))
      assert(!org.issues.hasMessages)
    }
  }
}
