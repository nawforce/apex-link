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
package com.nawforce.common.api

import com.nawforce.common.org.OrgImpl
import com.nawforce.common.path.PathLike
import com.nawforce.runtime.{FileSystemHelper, SourceBlob}
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class BatchingTest extends AnyFunSuite with BeforeAndAfter {

  before {
    ServerOps.setParsedDataCaching(false)
  }

  test("Simple refresh") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy {}",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      pkg.enableBatching()
      assert(!org.issues.hasMessages)

      pkg.refreshOrBatch(root.join("Dummy.cls"), None)
      assert(org.flush())
      assert(!org.issues.hasMessages)
    }
  }

  test("Deferred issue reporting") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy {}",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addMDAPITestPackage(None, Seq(root), Seq())
      pkg.enableBatching()
      assert(!org.issues.hasMessages)

      assert(pkg.refreshOrBatch(root.join("Dummy.cls"), Some(SourceBlob("public class Dummy {"))) == null)
      assert(!org.issues.hasMessages)

      assert(org.flush())
      assert(org.issues.getMessages("/Dummy.cls")
        .startsWith("Error: line 1: mismatched input '<EOF>' expecting {"))
    }
  }
}
