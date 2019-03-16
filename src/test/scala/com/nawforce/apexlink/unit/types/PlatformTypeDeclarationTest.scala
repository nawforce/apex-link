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

import com.nawforce.types.{CLASS, ENUM, INTERFACE, PlatformTypeDeclaration}
import com.nawforce.utils.DotName
import org.scalatest.FunSuite

class PlatformTypeDeclarationTest extends FunSuite {

  test("Bad class not found") {
    assert(PlatformTypeDeclaration.get(DotName("Hello")).isEmpty)
  }

  test("Unscoped class not found") {
    assert(PlatformTypeDeclaration.get(DotName("String")).isEmpty)
  }

  test("Scoped class") {
    val td = PlatformTypeDeclaration.get(DotName("System.String"))
    assert(td.nonEmpty)
    assert(td.get.name.toString == "String")
    assert(td.get.typeName.toString == "System.String")
    assert(td.get.superClass.isEmpty)
    assert(td.get.interfaces.isEmpty)
    assert(td.get.nature == CLASS)
  }

  test("Case insensitive class name") {
    val td = PlatformTypeDeclaration.get(DotName("System.strIng"))
    assert(td.nonEmpty)
    assert(td.get.name.toString == "String")
    assert(td.get.typeName.toString == "System.String")
    assert(td.get.superClass.isEmpty)
    assert(td.get.interfaces.isEmpty)
    assert(td.get.nature == CLASS)
  }

  test("Case insensitive namespace") {
    val td = PlatformTypeDeclaration.get(DotName("systEm.String"))
    assert(td.nonEmpty)
    assert(td.get.name.toString == "String")
    assert(td.get.typeName.toString == "System.String")
    assert(td.get.superClass.isEmpty)
    assert(td.get.interfaces.isEmpty)
    assert(td.get.nature == CLASS)
  }

  test("Extending class") {
    val td = PlatformTypeDeclaration.get(DotName("ConnectApi.FeedItem"))
    assert(td.nonEmpty)
    assert(td.get.name.toString == "FeedItem")
    assert(td.get.typeName.toString == "ConnectApi.FeedItem")
    assert(td.get.superClass.get.toString == "ConnectApi.FeedElement")
    assert(td.get.interfaces.isEmpty)
    assert(td.get.nature == CLASS)
  }

  test("Implements class") {
    val td = PlatformTypeDeclaration.get(DotName("System.List"))
    assert(td.nonEmpty)
    assert(td.get.name.toString == "List")
    assert(td.get.typeName.toString == "System.List<T>")
    assert(td.get.superClass.isEmpty)
    assert(td.get.interfaces.size == 1)
    assert(td.get.interfaces.head.toString == "System.Iterable<T>")
    assert(td.get.nature == CLASS)
  }

  test("Interface nature") {
    val td = PlatformTypeDeclaration.get(DotName("System.Callable"))
    assert(td.nonEmpty)
    assert(td.get.name.toString == "Callable")
    assert(td.get.typeName.toString == "System.Callable")
    assert(td.get.superClass.isEmpty)
    assert(td.get.interfaces.isEmpty)
    assert(td.get.nature == INTERFACE)
  }

  test("Enum nature") {
    val td = PlatformTypeDeclaration.get(DotName("System.RoundingMode"))
    assert(td.nonEmpty)
    assert(td.get.name.toString == "RoundingMode")
    assert(td.get.typeName.toString == "System.RoundingMode")
    assert(td.get.superClass.isEmpty)
    assert(td.get.interfaces.isEmpty)
    assert(td.get.nature == ENUM)
  }
}
