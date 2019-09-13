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
import com.nawforce.documents.StreamProxy
import com.nawforce.names.{DotName, Name}
import com.nawforce.types.TypeDeclaration
import org.scalatest.{BeforeAndAfter, FunSuite}

class ExtendsTest extends FunSuite with BeforeAndAfter {

  private val defaultName: Name = Name("Dummy")
  private val defaultPath: Path = Paths.get(defaultName.toString + ".cls")
  private var defaultOrg: Org = new Org

  def typeDeclarations(classes: Map[String, String]): Seq[TypeDeclaration] = {
    val paths = classes.map(kv => {
      val fakePath = Paths.get(kv._1 + ".cls")
      StreamProxy.setInputStream(fakePath, new ByteArrayInputStream(kv._2.getBytes()))
      fakePath
    }).toSeq

    Org.current.withValue(defaultOrg) {
      defaultOrg.unmanaged.deployMetadata(paths)
      defaultOrg.unmanaged.getTypes(classes.keys.map(k => DotName(k)).toSeq)
    }
  }

  before {
    StreamProxy.clear()
    defaultOrg = new Org
  }

  test("Duplicate inner type names") {
    assert(typeDeclarations(Map("Dummy" -> "global class Dummy {class Inner {} interface Inner{}}")).nonEmpty)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 45-50: Duplicate type name 'Inner'\n")
  }

  test("Duplicate outer & inner type names") {
    assert(typeDeclarations(Map("Dummy" -> "global class Dummy {class Dummy{}}")).nonEmpty)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 26-31: Duplicate type name 'Dummy'\n")
  }

  test("Missing superclass") {
    assert(typeDeclarations(Map("Dummy" -> "global class Dummy extends Foo {}")).nonEmpty)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 13-18: No type declaration found for 'Foo'\n")
  }

  test("Non-virtual superclass") {
    assert(typeDeclarations(Map(
      "SuperClass" -> "global class SuperClass {}",
      "Dummy" -> "global class Dummy extends SuperClass {}")).size == 2)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 13-18: Parent class 'SuperClass' must be declared virtual or abstract\n")
  }

  test("Interface superclass") {
    assert(typeDeclarations(Map(
      "SuperInterface" -> "global interface SuperInterface {}",
      "Dummy" -> "global class Dummy extends SuperInterface {}")).size == 2)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 13-18: Parent type 'SuperInterface' must be a class\n")
  }

  test("Enum superclass") {
    assert(typeDeclarations(Map(
      "SuperEnum" -> "global enum SuperEnum {}",
      "Dummy" -> "global class Dummy extends SuperEnum {}")).size == 2)
    assert(defaultOrg.issues.getMessages(defaultPath) ==
      "line 1 at 13-18: Parent type 'SuperEnum' must be a class\n")
  }

  test("External superclass") {
    assert(typeDeclarations(Map(
      "SuperClass" -> "global virtual class SuperClass {}",
      "Dummy" -> "global class Dummy extends SuperClass {}")).size == 2)
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Inner superclass") {
    assert(typeDeclarations(Map("Dummy" -> "global class Dummy extends Inner {virtual class Inner{}}")).nonEmpty)
    assert(!defaultOrg.issues.hasMessages)
  }

  test("Outer superclass") {
    assert(typeDeclarations(Map("Dummy" -> "global virtual class Dummy {class Inner extends Dummy {}}")).nonEmpty)
    assert(!defaultOrg.issues.hasMessages)
  }
}
