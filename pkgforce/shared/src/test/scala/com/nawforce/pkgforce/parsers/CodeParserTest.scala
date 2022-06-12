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
package com.nawforce.pkgforce.parsers

import com.nawforce.runtime.parsers.{CodeParser, SourceData}
import com.nawforce.runtime.platform.Path
import org.scalatest.funsuite.AnyFunSuite

class CodeParserTest extends AnyFunSuite {

  test("Class well formed") {
    val path   = Path("Dummy.cls")
    val cp     = CodeParser(path, SourceData("public class Dummy {}"))
    val result = cp.parseClass()
    assert(result.issues.isEmpty)
  }

  test("Class with error") {
    val path   = Path("Dummy.cls")
    val cp     = CodeParser(path, SourceData("public class Dummy {"))
    val result = cp.parseClass()
    assert(result.issues.length == 1)
    assert(result.issues.head.diagnostic.location.displayPosition == "line 1 at 20")
    assert(result.issues.head.diagnostic.message.startsWith("mismatched input '<EOF>' expecting {"))
  }

  test("Class multiple errors") {
    val path = Path("Dummy.cls")
    val cp =
      CodeParser(path, SourceData("public class Dummy {void func1(){f()} void func2(){f()} }"))
    val result = cp.parseClass()
    assert(result.issues.length == 2)
    assert(result.issues.head.diagnostic.location.displayPosition == "line 1 at 36")
    assert(result.issues.head.diagnostic.message == "missing ';' at '}'")
    assert(result.issues(1).diagnostic.location.displayPosition == "line 1 at 54")
    assert(result.issues(1).diagnostic.message == "missing ';' at '}'")
  }

  test("Class with keyword name") {
    val path   = Path("Network.cls")
    val cp     = CodeParser(path, SourceData("public class Network {}"))
    val result = cp.parseClass()
    assert(result.issues.isEmpty)
  }

  test("Class with error & surrogate pair") {
    val path   = Path("Dummy.cls")
    val cp     = CodeParser(path, SourceData("public class Dummy {String a = '\uD83E\uDD26'"))
    val result = cp.parseClass()
    assert(result.issues.length == 1)
    // The surrogate pair should only count as 1 unicode code point
    assert(result.issues.head.diagnostic.location.displayPosition == "line 1 at 34")
    assert(result.issues.head.diagnostic.message.startsWith("mismatched input '<EOF>' expecting {"))
  }

}
