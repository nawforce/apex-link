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
