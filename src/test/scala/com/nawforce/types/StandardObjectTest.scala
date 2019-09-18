package com.nawforce.types

import java.nio.file.Files

import com.google.common.jimfs.{Configuration, Jimfs}
import com.nawforce.api.Org
import com.nawforce.names.Name
import org.scalatest.FunSuite

class StandardObjectTest extends FunSuite {

  def customObject(label: String, fields: Seq[(String, String, Option[String])]): String = {
    val fieldMetadata = fields.map(field => {
      s"""
         |    <fields>
         |        <fullName>${field._1}</fullName>
         |        <type>${field._2}</type>
         |        ${if (field._3.nonEmpty) s"<referenceTo>${field._3.get}</referenceTo>" else ""}
         |        ${if (field._3.nonEmpty) s"<relationshipName>${field._1.replaceAll("__c$", "")}</relationshipName>" else ""}
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

  test("Not a standard object") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Foo.object"), customObject("Foo", Seq(("Bar__c", "Text", None))).getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(org.issues.getMessages(fs.getPath("/work/Foo.object")) ==
      "line 0: No sObject declaration found for 'Foo'\n")
  }

  test("Not a sObject") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("String.object"), customObject("String", Seq(("Bar__c", "Text", None))).getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(org.issues.getMessages(fs.getPath("/work/String.object")) ==
      "line 0: No sObject declaration found for 'String'\n")
  }

  test("Custom field") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Account.object"), customObject("Account", Seq(("Bar__c", "Text", None))).getBytes())
    Files.write(fs.getPath("Dummy.cls"),"public class Dummy { {Account a; a.Bar__c = '';} }".getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("Custom field (wrong name)") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Account.object"), customObject("Account", Seq(("Bar__c", "Text", None))).getBytes())
    Files.write(fs.getPath("Dummy.cls"),"public class Dummy { {Account a; a.Baz__c = '';} }".getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(org.issues.getMessages(fs.getPath("/work/Dummy.cls")) ==
      "line 1 at 33-41: Unknown field or type 'Baz__c' on 'Account'\n")
  }

  test("Custom base package field") {
    val fs = Jimfs.newFileSystem(Configuration.unix)

    Files.createDirectory(fs.getPath("pkg1"))
    Files.write(fs.getPath("pkg1/Account.object"), customObject("Account", Seq(("Bar__c", "Text", None))).getBytes())
    Files.createDirectory(fs.getPath("pkg2"))
    Files.write(fs.getPath("pkg2/Dummy.cls"),"public class Dummy { {Account a; a.pkg1__Bar__c = '';} }".getBytes())

    val org = new Org()
    val pkg1 = org.addPackageInternal(Some(Name("pkg1")), Seq(fs.getPath("/work/pkg1")), Seq())
    val pkg2 = org.addPackageInternal(Some(Name("pkg2")), Seq(fs.getPath("/work/pkg2")), Seq(pkg1))
    pkg1.deployAll()
    pkg2.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("Custom base package without namespace") {
    val fs = Jimfs.newFileSystem(Configuration.unix)

    Files.createDirectory(fs.getPath("pkg1"))
    Files.write(fs.getPath("pkg1/Account.object"), customObject("Account", Seq(("Bar__c", "Text", None))).getBytes())
    Files.createDirectory(fs.getPath("pkg2"))
    Files.write(fs.getPath("pkg2/Dummy.cls"),"public class Dummy { {Account a; a.Bar__c = '';} }".getBytes())

    val org = new Org()
    val pkg1 = org.addPackageInternal(Some(Name("pkg1")), Seq(fs.getPath("/work/pkg1")), Seq())
    val pkg2 = org.addPackageInternal(Some(Name("pkg2")), Seq(fs.getPath("/work/pkg2")), Seq(pkg1))
    pkg1.deployAll()
    pkg2.deployAll()
    assert(org.issues.getMessages(fs.getPath("/work/pkg2/Dummy.cls")) ==
      "line 1 at 33-41: Unknown field or type 'Bar__c' on 'Account'\n")
  }

  test("RecordTypeId field") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Dummy.cls"),"public class Dummy { {Account a; a.RecordTypeId = '';} }".getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("Standard field reference") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Dummy.cls"),"public class Dummy { {SObjectField a = Account.Fax;} }".getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("Custom field reference") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Account.object"), customObject("Account", Seq(("Bar__c", "Text", None))).getBytes())
    Files.write(fs.getPath("Dummy.cls"),"public class Dummy { {SObjectField a = Account.Bar__c;} }".getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("Invalid field reference") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Account.object"), customObject("Account", Seq(("Bar__c", "Text", None))).getBytes())
    Files.write(fs.getPath("Dummy.cls"),"public class Dummy { {SObjectField a = Account.Baz__c;} }".getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(org.issues.getMessages(fs.getPath("/work/Dummy.cls")) ==
      "line 1 at 39-53: Unknown field or type 'Baz__c' on 'Account'\n")
  }

  test("Lookup related list") {
    val fs = Jimfs.newFileSystem(Configuration.unix)
    Files.write(fs.getPath("Foo__c.object"), customObject("Foo", Seq(("Lookup__c", "Lookup", Some("Account")))).getBytes())
    Files.write(fs.getPath("Dummy.cls"),"public class Dummy { {SObjectField a = Account.Lookup__r;} }".getBytes())

    val org = new Org()
    val pkg = org.addPackageInternal(None, Seq(fs.getPath("/")), Seq())
    pkg.deployAll()
    assert(!org.issues.hasMessages)
  }

  test("Lookup related list (packaged)") {
    val fs = Jimfs.newFileSystem(Configuration.unix)

    Files.createDirectory(fs.getPath("pkg1"))
    Files.write(fs.getPath("pkg1/Foo__c.object"), customObject("Foo", Seq(("Lookup__c", "Lookup", Some("Account")))).getBytes())
    Files.createDirectory(fs.getPath("pkg2"))
    Files.write(fs.getPath("Dummy.cls"),"public class Dummy { {SObjectField a = Account.pkg1__Lookup__r;} }".getBytes())

    val org = new Org()
    val pkg1 = org.addPackageInternal(Some(Name("pkg1")), Seq(fs.getPath("/work/pkg1")), Seq())
    val pkg2 = org.addPackageInternal(None, Seq(fs.getPath("/work/pkg2")), Seq(pkg1))
    pkg2.deployAll()
    assert(!org.issues.hasMessages)
  }
}
