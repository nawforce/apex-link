package com.nawforce.apexlink.types

import com.nawforce.apexlink.{FileSystemHelper, TestHelper}
import com.nawforce.pkgforce.path.PathLike
import org.scalatest.funsuite.AnyFunSuite

class SchemaManagerTest extends AnyFunSuite with TestHelper {

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
        val org = createOrg(root)
        assert(!org.issues.hasMessages)
    }
  }

  test("Custom object visible") {
    FileSystemHelper.run(
      Map("Foo__c.object" -> customObject("Foo", Seq(("Bar__c", "Text", None))),
          "Dummy.cls" -> "public class Dummy { {DescribeSObjectResult r = SObjectType.Foo__c;} }")) {
      root: PathLike =>
        val org = createOrg(root)
        assert(!org.issues.hasMessages)
    }
  }

  test("Ghosted custom object visible") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
          |"namespace": "pkg2",
          |"packageDirectories": [{"path": "pkg2"}],
          |"plugins": {"dependencies": [{"namespace": "pkg1"}]}
          |}""".stripMargin,
        "Dummy.cls" -> "public class Dummy { {DescribeSObjectResult r = SObjectType.ghosted__Foo__c;} }")) {
      root: PathLike =>
        val org = createOrg(root)
        assert(!org.issues.hasMessages)
    }
  }
}
