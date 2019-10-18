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

import java.io.ByteArrayInputStream
import java.nio.file.{Path, Paths}

import com.nawforce.api.Org
import com.nawforce.names.Name
import com.nawforce.types.{ApexTypeDeclaration, TypeDeclaration}
import org.scalatest.{BeforeAndAfter, FunSuite}

class OperationsTests extends FunSuite with BeforeAndAfter {

  private val defaultName: Name = Name("Dummy")
  private val defaultPath: Path = Paths.get(defaultName.toString)
  private var defaultOrg: Org = new Org

  def typeDeclaration(clsText: String): TypeDeclaration = {
    Org.current.withValue(defaultOrg) {
      val td = ApexTypeDeclaration.create(defaultOrg.unmanaged, defaultPath, new ByteArrayInputStream(clsText.getBytes())).head
      defaultOrg.unmanaged.upsertType(td)
      td.validate()
      td
    }
  }

  before {
    defaultOrg = new Org
  }

  test("String prefix bug on SObject") {
    typeDeclaration("public class Dummy {{Object a;  String b = '' + + a;}}")
    defaultOrg.issues.dumpMessages(false)
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Date addition") {
    typeDeclaration("public class Dummy {{Date a;  Date b = a + 1;}}")
    defaultOrg.issues.dumpMessages(false)
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Date subtraction") {
    typeDeclaration("public class Dummy {{Date a;  Date b = a - 12l;}}")
    defaultOrg.issues.dumpMessages(false)
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Datetime addition") {
    typeDeclaration("public class Dummy {{Datetime a;  Datetime b = a + 1;}}")
    defaultOrg.issues.dumpMessages(false)
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Datetime subtraction") {
    typeDeclaration("public class Dummy {{Datetime a;  Datetime b = a - 12l;}}")
    defaultOrg.issues.dumpMessages(false)
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Time addition") {
    typeDeclaration("public class Dummy {{Time a;  Time b = a + 1;}}")
    defaultOrg.issues.dumpMessages(false)
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Time subtraction") {
    typeDeclaration("public class Dummy {{Time a;  Time b = a - 12l;}}")
    defaultOrg.issues.dumpMessages(false)
    assert(!defaultOrg.issues.hasMessages)
  }
}
