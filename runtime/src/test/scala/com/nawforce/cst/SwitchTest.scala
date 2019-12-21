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
package com.nawforce.cst

import com.nawforce.api.Org
import com.nawforce.path.PathFactory
import com.nawforce.types._
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class SwitchTest extends AnyFunSuite with BeforeAndAfter {
  private val defaultPath = PathFactory("Dummy.cls")
  private var defaultOrg: Org = new Org

  def typeDeclaration(clsText: String): Option[TypeDeclaration] = {
    Org.current.withValue(defaultOrg) {
      val td = ApexTypeDeclaration.create(defaultOrg.unmanaged, defaultPath, clsText)
      td.headOption.foreach(_.validate())
      td.headOption
    }
  }

  before {
    defaultOrg = new Org
  }

  test("Single control switch") {
    typeDeclaration("public class Dummy {{switch on 'A' {when 'A' {} }}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Multi control switch") {
    typeDeclaration("public class Dummy {{switch on 'A' {when 'A' {} when 'B' {}}}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Else switch") {
    typeDeclaration("public class Dummy {{switch on 'A' {when 'A' {} when else {}}}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Empty switch") {
    typeDeclaration("public class Dummy {{switch on 'A' {}}}")
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "Error: line 1: mismatched input '}' expecting 'when'\n")
  }

  test("Enum switch") {
    typeDeclaration("public class Dummy {{switch on 'A' {when EnumValue {} when else {}}}}")
    assert(!defaultOrg.issues.hasMessages)
  }

  // TODO: Examine error handling of switch
}
