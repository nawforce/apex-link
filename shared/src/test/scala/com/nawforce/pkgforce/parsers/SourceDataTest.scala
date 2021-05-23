/*
 [The "BSD licence"]
 Copyright (c) 2021 Kevin Jones
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

import com.nawforce.runtime.parsers.SourceData
import org.scalatest.funsuite.AnyFunSuite

class SourceDataTest extends AnyFunSuite {

  test("Basic string handling") {
    val sd = SourceData("A basic test string")
    assert(sd.subdata(2,2).asString.isEmpty)
    assert(sd.subdata(2,3).asString == "b")
    assert(sd.subdata(2,7).asString == "basic")
  }

  test("UTF-8 String handling") {
    val sd = SourceData("A UTF-8 \uD83E\uDD26 test string")
    assert(sd.subdata(8,9).asString == "\uD83E\uDD26")
    assert(sd.subdata(11,12).asString == "t")
    assert(sd.subdata(11,15).asString == "test")
    assert(sd.subdata(6,15).asString == "8 \uD83E\uDD26 test")
  }
}
