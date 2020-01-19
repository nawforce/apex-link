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

package com.nawforce.common.types

import com.nawforce.common.api.Org
import com.nawforce.common.path.{PathFactory, PathLike}
import com.nawforce.runtime.FileSystemHelper
import org.scalatest.funsuite.AnyFunSuite

class TriggerTest extends AnyFunSuite {
  test("Empty trigger") {
    FileSystemHelper.run(Map(
      "Dummy.trigger" -> "trigger Dummy on Account (before insert) { }"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackageInternal(None, Seq(root), Seq())
      pkg.deployAll()
      assert(!org.issues.hasMessages)
    }
  }

  test("Bad object errors") {
    FileSystemHelper.run(Map(
      "Dummy.trigger" -> "trigger Dummy on Stupid (before insert) { }"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackageInternal(None, Seq(root), Seq())
      pkg.deployAll()
      assert(org.issues.getMessages(PathFactory("/Dummy.trigger")) ==
        "Error: line 1 at 17-23: No type declaration found for 'Stupid'\n")
    }
  }

  test("Custom object") {
    FileSystemHelper.run(Map(
      "Stupid__c.object" -> "<CustomObject xmlns=\"http://soap.sforce.com/2006/04/metadata\"><fullName>Stupid</fullName></CustomObject>",
      "Dummy.trigger" -> "trigger Dummy on Stupid__c (before insert) { }"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackageInternal(None, Seq(root), Seq())
      pkg.deployAll()
      assert(!org.issues.hasMessages)
    }
  }

  test("Duplicate trigger type") {
    FileSystemHelper.run(Map(
      "Dummy.trigger" -> "trigger Dummy on Account (before insert, before insert) { }"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackageInternal(None, Seq(root), Seq())
      pkg.deployAll()
      assert(org.issues.getMessages(PathFactory("/Dummy.trigger")) ==
        "Error: line 1 at 17-24: Duplicate trigger case for 'before insert'\n")
    }
  }

  test("this works") {
    FileSystemHelper.run(Map(
      "Dummy.trigger" -> "trigger Dummy on Account (before insert) {Object a = this;}"
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackageInternal(None, Seq(root), Seq())
      pkg.deployAll()
      assert(!org.issues.hasMessages)
    }
  }

  test("Trigger.New") {
    FileSystemHelper.run(Map(
      "Dummy.trigger" ->
        """trigger Dummy on Account (before insert) {
          |  for(Account a: Trigger.New)
          |     System.debug(a.Id);
          |}""".stripMargin
    )) { root: PathLike =>
      val org = new Org()
      val pkg = org.addPackageInternal(None, Seq(root), Seq())
      pkg.deployAll()
      org.issues.dumpMessages(false)
      assert(!org.issues.hasMessages)
    }
  }

}
