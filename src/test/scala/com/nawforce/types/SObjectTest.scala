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
package com.nawforce.types

import java.nio.file.Files

import com.google.common.jimfs.{Configuration, Jimfs}
import com.nawforce.api.Org
import com.nawforce.utils.Name
import org.scalatest.FunSuite

class SObjectTest extends FunSuite {

  def customObject(label: String, fields: Seq[(String, String)]): String = {
    val fieldMetadata = fields.map(field => {
      s"""
         |    <fields>
         |        <fullName>${field._1}</fullName>
         |        <type>${field._2}</type>
         |    </fields>
         |""".stripMargin
    })

    s"""<?xml version="1.0" encoding="UTF-8"?>
      |<CustomObject xmlns="http://soap.sforce.com/2006/04/metadata">
      |    <fullName>${label}</fullName>
      |    ${fieldMetadata}
      |</CustomObject>
      |""".stripMargin
  }

  test("Valid construction") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Foo__c.object"), customObject("Foo", Seq(("Bar", "Text"))).getBytes())
    Files.write(fs.getPath("Dummy.cls"),"public class Dummy { {Object a = new Foo__c(Bar__c = 'A');} }".getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(Name.Empty, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    org.issues.dumpMessages(false)
    assert(!org.issues.hasMessages)
  }

  // TODO: Complete impl & tests
}
