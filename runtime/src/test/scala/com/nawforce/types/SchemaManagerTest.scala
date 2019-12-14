package com.nawforce.types

import java.nio.file.Files

import com.google.common.jimfs.{Configuration, Jimfs}
import com.nawforce.api.Org
import com.nawforce.names.Name
import org.scalatest.FunSuite

class SchemaManagerTest extends FunSuite {

  def customObject(label: String, fields: Seq[(String, String, Option[String])]): String = {
    val fieldMetadata = fields.map(field => {
      s"""
         |    <fields>
         |        <fullName>${field._1}</fullName>
         |        <type>${field._2}</type>
         |        ${if (field._3.nonEmpty) s"<referenceTo>field._3.get</referenceTo>" else ""}
         |    </fields>
         |""".stripMargin
    })

    s"""<?xml version="1.0" encoding="UTF-8"?>
       |<CustomObject xmlns="http://soap.sforce.com/2006/04/metadata">
       |    <fullName>$label</fullName>
       |    $fieldMetadata
       |</CustomObject>
       |""".stripMargin
  }

  test("Standard object visible") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Dummy.cls"),"public class Dummy { {DescribeSObjectResult r = SObjectType.Account;} }".getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("Custom object visible") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Foo__c.object"), customObject("Foo", Seq(("Bar__c", "Text", None))).getBytes())
    Files.write(fs.getPath("Dummy.cls"),"public class Dummy { {DescribeSObjectResult r = SObjectType.Foo__c;} }".getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("Ghosted custom object visible") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Dummy.cls"),"public class Dummy { {DescribeSObjectResult r = SObjectType.ghosted__Foo__c;} }".getBytes())

    val org = new Org()
    val ghosted = org.addPackageInternal(Some(Name("ghosted")), Seq(), Seq())
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq(ghosted))
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }
}
