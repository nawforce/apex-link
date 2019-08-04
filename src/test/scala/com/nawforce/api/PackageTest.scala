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
package com.nawforce.api

import java.io.ByteArrayInputStream
import java.nio.file.{Path, Paths}

import com.nawforce.types.TypeDeclaration
import com.nawforce.utils.{DotName, Name}
import org.scalatest.FunSuite

class PackageTest extends FunSuite {

  private val defaultName: Name = Name("Dummy.cls")
  private val defaultPath: Path = Paths.get(defaultName.toString)
  private val defaultOrg: Org = new Org

  def typeDeclarations(classes: Map[String, String]): Seq[TypeDeclaration] = {
    LogUtils.setLoggingLevel(false)
    val paths = classes.map(kv => {
      val fakePath = Paths.get(kv._1 + ".cls")
      defaultOrg.setInputStream(fakePath, new ByteArrayInputStream(kv._2.getBytes()))
      fakePath
    }).toSeq

    Org.current.withValue(defaultOrg) {
      defaultOrg.deployMetadata(Name.Empty, paths)
      defaultOrg.getTypes(classes.keys.map(k => DotName(k)).toSeq)
    }
  }

  test("Ghost package suppresses declared type error") {
    defaultOrg.addPackage("package", Array())

    val tds = typeDeclarations(Map("Dummy" -> "public class Dummy extends package.Super {}"))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.dependencies().isEmpty)

    defaultOrg.clear()
  }

  test("Ghost package with wrong namespace has declared type error") {
    defaultOrg.addPackage("silly", Array())

    val tds = typeDeclarations(Map("Dummy" -> "public class Dummy extends package.Super {}"))
    assert(defaultOrg.issues.getMessages(defaultPath) == "line 1 at 13-18: No type declaration found for 'package.Super'\n")
    assert(tds.head.dependencies().isEmpty)

    defaultOrg.clear()
  }

  test("Ghost package suppresses implicit type error") {
    defaultOrg.addPackage("package", Array())

    val tds = typeDeclarations(Map("Dummy" -> "public class Dummy { {Object a = package.class;} }"))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.dependencies().isEmpty)

    defaultOrg.clear()
  }

  test("Ghost package with wrong namespace has implicit type error") {
    defaultOrg.addPackage("silly", Array())

    val tds = typeDeclarations(Map("Dummy" -> "public class Dummy { {Object a = package.class;} }"))
    assert(defaultOrg.issues.getMessages(defaultPath) == "line 1 at 33-46: No type declaration found for 'package'\n")
    assert(tds.head.dependencies().isEmpty)

    defaultOrg.clear()
  }

  test("Ghost package suppresses custom object error") {
    defaultOrg.addPackage("package", Array())

    val tds = typeDeclarations(Map("Dummy" -> "public class Dummy { {Object a = new package__Foo__c();} }"))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.dependencies().isEmpty)

    defaultOrg.clear()
  }

  test("Ghost package with wrong namespace has custom object error") {
    defaultOrg.addPackage("silly", Array())

    val tds = typeDeclarations(Map("Dummy" -> "public class Dummy { {Object a = new package__Foo__c();} }"))
    assert(defaultOrg.issues.getMessages(defaultPath) == "line 1 at 37-52: No type declaration found for 'package__Foo__c'\n")
    assert(tds.head.dependencies().isEmpty)

    defaultOrg.clear()
  }

  test("Ghost package suppresses custom metadata error") {
    defaultOrg.addPackage("package", Array())

    val tds = typeDeclarations(Map("Dummy" -> "public class Dummy { {Object a = new package__Foo__mdt();} }"))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.dependencies().isEmpty)

    defaultOrg.clear()
  }

  test("Ghost package with wrong namespace has custom metadata error") {
    defaultOrg.addPackage("silly", Array())

    val tds = typeDeclarations(Map("Dummy" -> "public class Dummy { {Object a = new package__Foo__mdt();} }"))
    assert(defaultOrg.issues.getMessages(defaultPath) == "line 1 at 37-54: No type declaration found for 'package__Foo__mdt'\n")
    assert(tds.head.dependencies().isEmpty)

    defaultOrg.clear()
  }

  test("Ghost package suppresses platform event error") {
    defaultOrg.addPackage("package", Array())

    val tds = typeDeclarations(Map("Dummy" -> "public class Dummy { {Object a = new package__Foo__e();} }"))
    assert(!defaultOrg.issues.hasMessages)
    assert(tds.head.dependencies().isEmpty)

    defaultOrg.clear()
  }

  test("Ghost package with wrong namespace has platform event error") {
    defaultOrg.addPackage("silly", Array())

    val tds = typeDeclarations(Map("Dummy" -> "public class Dummy { {Object a = new package__Foo__e();} }"))
    assert(defaultOrg.issues.getMessages(defaultPath) == "line 1 at 37-52: No type declaration found for 'package__Foo__e'\n")
    assert(tds.head.dependencies().isEmpty)

    defaultOrg.clear()
  }
}
