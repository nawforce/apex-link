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

import com.nawforce.common.api.Org
import com.nawforce.common.names.Name
import com.nawforce.common.org.OrgImpl
import com.nawforce.common.path.PathLike
import com.nawforce.runtime.FileSystemHelper
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class InterviewTest extends AnyFunSuite with BeforeAndAfter {

  test("Interview createInterview") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy { {Flow.Interview i = Flow.Interview.createInterview('', new Map<String, Object>());} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Custom flow (MDAPI)") {
    FileSystemHelper.run(Map(
      "Test.flow" -> "",
      "Dummy.cls" -> "public class Dummy { {Flow.Interview.Test;} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Custom flow (SFDX)") {
    FileSystemHelper.run(Map(
      "Test.flow-meta.xml" -> "",
      "Dummy.cls" -> "public class Dummy { {Flow.Interview.Test;} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Missing flow") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy { {Flow.Interview.Test;} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      // TODO: This should be a missing issue
      assert(org.issues.getMessages("/Dummy.cls") ==
        "Missing: line 1 at 22-41: Unknown field or type 'Test' on 'Flow.Interview'\n")
    }
  }

  test("Create flow") {
    FileSystemHelper.run(Map(
      "Test.flow-meta.xml" -> "",
      "Dummy.cls" -> "public class Dummy { {Flow.Interview i = new Flow.Interview.Test(new Map<String, Object>());} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Create flow (namespaced)") {
    FileSystemHelper.run(Map(
      "Test.flow-meta.xml" -> "",
      "Dummy.cls" -> "public class Dummy { {Flow.Interview i = new Flow.Interview.pkg.Test(new Map<String, Object>());} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(Some(Name("pkg")), Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Create flow (namespaced but without namespace)") {
    FileSystemHelper.run(Map(
      "Test.flow-meta.xml" -> "",
      "Dummy.cls" -> "public class Dummy { {Flow.Interview i = new Flow.Interview.Test(new Map<String, Object>());} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(Some(Name("pkg")), Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Create flow (missing flow)") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy { {Flow.Interview i = new Flow.Interview.Test(new Map<String, Object>());} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(Some(Name("pkg")), Seq(root), Seq())
      assert(org.issues.getMessages("/Dummy.cls") ==
        "Missing: line 1 at 45-64: No type declaration found for 'Flow.Interview.Test'\n")
    }
  }

  test("Start flow") {
    FileSystemHelper.run(Map(
      "Test.flow-meta.xml" -> "",
      "Dummy.cls" -> "public class Dummy { {new Flow.Interview.Test(new Map<String, Object>()).start();} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

}
