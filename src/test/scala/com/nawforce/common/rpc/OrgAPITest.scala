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
package com.nawforce.common.rpc

import com.nawforce.common.path.PathFactory
import org.scalatest.funsuite.AsyncFunSuite

class OrgAPITest extends AsyncFunSuite {

  test("Identifier not empty") {
    val orgAPI = OrgAPI()
    orgAPI.identifier() map {
      result => assert(result.nonEmpty)
    }
  }

  test("Add package not bad directory") {
    val workspace = PathFactory("silly")
    val orgAPI = OrgAPI()
    orgAPI.addPackage("silly") map {
      result => assert(result.error.exists(_.message == s"Workspace '$workspace' is not a directory"))
    }
  }

  test("Add package not sfdx directory") {
    val workspace = PathFactory("")
    val orgAPI = OrgAPI()
    orgAPI.addPackage("") map {
      result => assert(result.error.exists(_.message == s"Missing project file at $workspace/sfdx-project.json"))
    }
  }

  test("Add package sfdx directory (relative)") {
    val orgAPI = OrgAPI()
    orgAPI.addPackage("samples/synthetic/sfdx-test") map {
      result => assert(result.error.isEmpty && result.namespaces.sameElements(Array("")))
    }
  }

  test("Add package sfdx directory (absolute)") {
    val workspace = PathFactory("samples/synthetic/sfdx-test")
    val orgAPI = OrgAPI()
    orgAPI.addPackage(workspace.toString) map {
      result => assert(result.error.isEmpty && result.namespaces.sameElements(Array("")))
    }
  }

  test("Add package sfdx directory with ns (relative)") {
    val orgAPI = OrgAPI()
    orgAPI.addPackage("samples/synthetic/sfdx-ns-test") map {
      result => assert(result.error.isEmpty && result.namespaces.sameElements(Array("sfdx_test")))
    }
  }

  test("Add package sfdx directory with ns (absolute)") {
    val workspace = PathFactory("samples/synthetic/sfdx-ns-test")
    val orgAPI = OrgAPI()
    orgAPI.addPackage(workspace.toString) map {
      result => assert(result.error.isEmpty && result.namespaces.sameElements(Array("sfdx_test")))
    }
  }

}
