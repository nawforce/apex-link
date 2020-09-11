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
package com.nawforce.common.parsers

import com.nawforce.common.FileSystemHelper
import com.nawforce.common.api.{Org, Package, ServerOps}
import com.nawforce.common.org.OrgImpl
import com.nawforce.common.path.{PathFactory, PathLike}
import com.nawforce.common.sfdx.MDAPIWorkspace
import com.nawforce.runtime.parsers.{CodeParser, SourceData}
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class CodeParserTest extends AnyFunSuite with BeforeAndAfter {

  before {
    ServerOps.setAutoFlush(false)
  }

  after {
    ServerOps.setAutoFlush(true)
  }

  private def addPackage(org: OrgImpl, path: PathLike): Package = {
    org.addPackage(new MDAPIWorkspace(None, Seq(path)), Seq())
  }

  test("Good class") {
    val parser = CodeParser(PathFactory("Hello.cls"), SourceData("public class Hello {}"))
    parser.parseClass() match {
      case Left(_)   => assert(false)
      case Right(cu) => assert(cu != null)
    }
  }

  test("Broken class") {
    val parser = CodeParser(PathFactory("Hello.cls"), SourceData("public class Hello {"))
    parser.parseClass() match {
      case Left(_)  => ()
      case Right(_) => assert(false)
    }
  }

  test("UTF-8 class") {
    FileSystemHelper.run(Map("Dummy.cls" -> "public class Dummy {{String a = 'Kimi Räikkönen';}}")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        addPackage(org, root)
        assert(!org.issues.hasMessages)
    }
  }
}
