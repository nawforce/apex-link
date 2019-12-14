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
package com.nawforce.documents

import java.nio.file.Files

import com.google.common.jimfs.{Configuration, Jimfs}
import com.nawforce.api.Org
import org.scalatest.FunSuite

class GhostSObjectTest extends FunSuite {

  def customField(name: String, fieldType: String, relationshipName: Option[String]): String = {
    s"""<?xml version="1.0" encoding="UTF-8"?>
       |<CustomField xmlns="http://soap.sforce.com/2006/04/metadata">
       |    <fullName>$name</fullName>
       |    <type>$fieldType</type>
       |    ${if (relationshipName.nonEmpty) s"<referenceTo>${relationshipName.get}</referenceTo>" else ""}
       |    ${if (relationshipName.nonEmpty) s"<relationshipName>${name.replaceAll("__c$","")}</relationshipName>" else ""}
       |</CustomField>
       |""".stripMargin
  }

  def customFieldSet(name: String): String = {
    s"""<?xml version="1.0" encoding="UTF-8"?>
       |<FieldSet xmlns="http://soap.sforce.com/2006/04/metadata">
       |    <fullName>$name</fullName>
       |</FieldSet>
       |""".stripMargin
  }

  test("Platform object field") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.createDirectory(fs.getPath("Account"))
    Files.createDirectory(fs.getPath("Account/fields"))
    Files.write(fs.getPath("Account/fields/Foo__c.field-meta.xml"), customField("Foo__c", "Text", None).getBytes())
    Files.write(fs.getPath("Dummy.cls"),"public class Dummy { {SObjectField a = Account.Foo__c;} }".getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("Custom object field") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.createDirectory(fs.getPath("Bar__c"))
    Files.createDirectory(fs.getPath("Bar__c/fields"))
    Files.write(fs.getPath("Bar__c/fields/Foo__c.field-meta.xml"), customField("Foo__c", "Text", None).getBytes())
    Files.write(fs.getPath("Dummy.cls"),"public class Dummy { {SObjectField a = Bar__c.Foo__c;} }".getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("Platform object fieldSet") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.createDirectory(fs.getPath("Account"))
    Files.createDirectory(fs.getPath("Account/fieldSets"))
    Files.write(fs.getPath("Account/fieldSets/TestFS.fieldSet-meta.xml"), customFieldSet("TestFS").getBytes())
    Files.write(fs.getPath("Dummy.cls"),"public class Dummy { {SObjectField a = SObjectType.Account.FieldSets.TestFS;} }".getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("Custom object fieldSet") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.createDirectory(fs.getPath("Bar__c"))
    Files.createDirectory(fs.getPath("Bar__c/fieldSets"))
    Files.write(fs.getPath("Bar__c/fieldSets/TestFS.fieldSet-meta.xml"), customFieldSet("TestFS").getBytes())
    Files.write(fs.getPath("Dummy.cls"),"public class Dummy { {SObjectField a = SObjectType.Bar__c.FieldSets.TestFS;} }".getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }
}
