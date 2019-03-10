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
package com.nawforce.apexlink.unit.types

import com.nawforce.types.PlatformTypeDeclaration
import com.nawforce.utils.QName
import org.scalatest.FunSuite

class PlatformTypeDeclarationTest extends FunSuite {

  test("Bad class not found") {
    assert(PlatformTypeDeclaration.get(QName("Hello")).isEmpty)
  }

  test("Unscoped class not found") {
    assert(PlatformTypeDeclaration.get(QName("String")).isEmpty)
  }

  test("Scoped class not found") {
    assert(PlatformTypeDeclaration.get(QName("System.String")).nonEmpty)
  }

  test("Case insensitive class name") {
    assert(PlatformTypeDeclaration.get(QName("System.strIng")).nonEmpty)
  }

  test("Case insensitive namespace") {
    assert(PlatformTypeDeclaration.get(QName("systEm.String")).nonEmpty)
  }
}
