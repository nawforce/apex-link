package com.nawforce.common.types

import com.nawforce.common.FileSystemHelper
import com.nawforce.common.api.{Name, Org, ServerOps}
import com.nawforce.common.org.OrgImpl
import com.nawforce.common.path.PathLike
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class SchemaManagerTest extends AnyFunSuite with BeforeAndAfter {

  before {
    ServerOps.setAutoFlush(false)
  }

  after {
    ServerOps.setAutoFlush(true)
  }

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
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy { {DescribeSObjectResult r = SObjectType.Account;} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Custom object visible") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", "Text", None))),
          "Dummy.cls" -> "public class Dummy { {DescribeSObjectResult r = SObjectType.Foo__c;} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        org.newMDAPIPackageInternal(None, Seq(root), Seq())
        assert(!org.issues.hasMessages)
    }
  }

  test("Ghosted custom object visible") {
    FileSystemHelper.run(Map(
      "Dummy.cls" -> "public class Dummy { {DescribeSObjectResult r = SObjectType.ghosted__Foo__c;} }")) {
      root: PathLike =>
        val org = Org.newOrg().asInstanceOf[OrgImpl]
        val ghosted = org.newMDAPIPackageInternal(Some(Name("ghosted")), Seq(), Seq())
        org.newMDAPIPackageInternal(None, Seq(root), Seq(ghosted))
        assert(!org.issues.hasMessages)
    }
  }
}
