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

package com.nawforce.apexlink.types

import com.nawforce.apexlink.{FileSystemHelper, TestHelper}
import com.nawforce.pkgforce.names.{Name, TypeIdentifier, TypeName}
import com.nawforce.pkgforce.path.PathLike
import org.scalatest.funsuite.AnyFunSuite

class LabelTest extends AnyFunSuite with TestHelper {

  test("Empty labels file") {
    FileSystemHelper.run(Map("CustomLabels.labels" -> "")) { root: PathLike =>
      val org = createOrg(root)
      assert(org.issues.getMessages(root.join("CustomLabels.labels").toString).nonEmpty)
      assert(org.unmanaged.getTypeOfPathInternal(root.join("CustomLabels.labels")).isEmpty)
    }
  }

  test("Minimal labels file") {
    FileSystemHelper.run(Map(
      "CustomLabels.labels" -> "<CustomLabels xmlns=\"http://soap.sforce.com/2006/04/metadata\"/>",
    )) { root: PathLike =>
      val org = createOrg(root)
      assert(!org.issues.hasMessages)

      val typeId =
        org.unmanaged.getTypeOfPathInternal(root.join("CustomLabels.labels")).get.asTypeIdentifier
      assert(typeId.toString == "System.Label")
      assert(org.unmanaged.getPathsOfType(typeId).sameElements(Array("/CustomLabels.labels")))
      assert(org.unmanaged.getDependencies(typeId, outerInheritanceOnly = false).isEmpty)
      assert(org.unmanaged.getDependencyHolders(typeId).isEmpty)
    }
  }

  test("Dual labels file") {
    FileSystemHelper.run(
      Map(
        "CustomLabels.labels" -> "<CustomLabels xmlns=\"http://soap.sforce.com/2006/04/metadata\"/>",
        "CustomLabels2.labels" -> "<CustomLabels xmlns=\"http://soap.sforce.com/2006/04/metadata\"/>",
      )) { root: PathLike =>
      val org = createOrg(root)
      assert(!org.issues.hasMessages)

      val typeId1 =
        org.unmanaged.getTypeOfPathInternal(root.join("CustomLabels.labels")).get.asTypeIdentifier
      val typeId2 =
        org.unmanaged.getTypeOfPathInternal(root.join("CustomLabels2.labels")).get.asTypeIdentifier
      assert(typeId1.toString == "System.Label")
      assert(typeId2.toString == "System.Label")
      assert(
        org.unmanaged
          .getPathsOfType(typeId1)
          .sorted
          .sameElements(Array("/CustomLabels.labels", "/CustomLabels2.labels")))
      assert(org.unmanaged.getDependencies(typeId1, outerInheritanceOnly = false).isEmpty)
      assert(org.unmanaged.getDependencyHolders(typeId1).isEmpty)
    }
  }

  test("Valid label") {
    FileSystemHelper.run(
      Map(
        "CustomLabels.labels" ->
          """<?xml version="1.0" encoding="UTF-8"?>
          |<CustomLabels xmlns="http://soap.sforce.com/2006/04/metadata">
          |    <labels>
          |        <fullName>TestLabel</fullName>
          |        <language>en_US</language>
          |        <protected>true</protected>
          |        <shortDescription>TestLabel Description</shortDescription>
          |        <value>TestLabel Value</value>
          |    </labels>
          |</CustomLabels>
          |""".stripMargin,
        "Dummy.cls" -> "public class Dummy { {String a = label.TestLabel;} }")) { root: PathLike =>
      val org = createOrg(root)
      assert(!org.issues.hasMessages)

      val labelsTypeId =
        org.unmanaged.getTypeOfPathInternal(root.join("CustomLabels.labels")).get.asTypeIdentifier
      assert(labelsTypeId.toString == "System.Label")
      assert(org.unmanaged.getPathsOfType(labelsTypeId).sameElements(Array("/CustomLabels.labels")))
      assert(org.unmanaged.getDependencies(labelsTypeId, outerInheritanceOnly = false).isEmpty)

      val dummyTypeId =
        org.unmanaged.getTypeOfPathInternal(root.join("Dummy.cls")).get.asTypeIdentifier
      assert(
        org.unmanaged
          .getDependencies(dummyTypeId, outerInheritanceOnly = false)
          .sameElements(Array(labelsTypeId)))
      assert(org.unmanaged.getDependencyHolders(labelsTypeId).sameElements(Array(dummyTypeId)))
    }
  }

  test("Valid label (case insensitive)") {
    FileSystemHelper.run(
      Map(
        "CustomLabels.labels" ->
          """<?xml version="1.0" encoding="UTF-8"?>
          |<CustomLabels xmlns="http://soap.sforce.com/2006/04/metadata">
          |    <labels>
          |        <fullName>TestLabel</fullName>
          |        <language>en_US</language>
          |        <protected>true</protected>
          |        <shortDescription>TestLabel Description</shortDescription>
          |        <value>TestLabel Value</value>
          |    </labels>
          |</CustomLabels>
          |""".stripMargin,
        "Dummy.cls" -> "public class Dummy { {String a = laBel.TeStLaBel;} }")) { root: PathLike =>
      val org = createOrg(root)
      assert(!org.issues.hasMessages)
    }
  }

  test("Missing label") {
    FileSystemHelper.run(
      Map(
        "CustomLabels.labels" ->
          """<?xml version="1.0" encoding="UTF-8"?>
          |<CustomLabels xmlns="http://soap.sforce.com/2006/04/metadata">
          |    <labels>
          |        <fullName>TestLabel</fullName>
          |        <language>en_US</language>
          |        <protected>true</protected>
          |        <shortDescription>TestLabel Description</shortDescription>
          |        <value>TestLabel Value</value>
          |    </labels>
          |</CustomLabels>
          |""".stripMargin,
        "Dummy.cls" -> "public class Dummy { {String a = Label.TestLabel2;} }")) { root: PathLike =>
      val org = createOrg(root)
      assert(
        org.issues.getMessages("/Dummy.cls") ==
          "Missing: line 1 at 33-49: Unknown field or type 'TestLabel2' on 'System.Label'\n")
    }
  }

  test("Missing label (case insensitive)") {
    FileSystemHelper.run(
      Map(
        "CustomLabels.labels" ->
          """<?xml version="1.0" encoding="UTF-8"?>
          |<CustomLabels xmlns="http://soap.sforce.com/2006/04/metadata">
          |    <labels>
          |        <fullName>TestLabel</fullName>
          |        <language>en_US</language>
          |        <protected>true</protected>
          |        <shortDescription>TestLabel Description</shortDescription>
          |        <value>TestLabel Value</value>
          |    </labels>
          |</CustomLabels>
          |""".stripMargin,
        "Dummy.cls" -> "public class Dummy { {String a = laBel.TestLaBel2;} }")) { root: PathLike =>
      val org = createOrg(root)
      assert(
        org.issues.getMessages("/Dummy.cls") ==
          "Missing: line 1 at 33-49: Unknown field or type 'TestLaBel2' on 'System.Label'\n")
    }
  }

  test("Base package label") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "pkg2",
            |"packageDirectories": [{"path": "pkg2"}],
            |"plugins": {"dependencies": [{"namespace": "pkg1", "path": "pkg1"}]}
            |}""".stripMargin,
        "pkg1/CustomLabels.labels" ->
          """<?xml version="1.0" encoding="UTF-8"?>
          |<CustomLabels xmlns="http://soap.sforce.com/2006/04/metadata">
          |    <labels>
          |        <fullName>TestLabel</fullName>
          |        <language>en_US</language>
          |        <protected>false</protected>
          |        <shortDescription>TestLabel Description</shortDescription>
          |        <value>TestLabel Value</value>
          |    </labels>
          |</CustomLabels>
          |""".stripMargin,
        "pkg2/Dummy.cls" -> "public class Dummy { {String a = label.pkg1.TestLabel;} }")) {
      root: PathLike =>
        val org = createOrg(root)
        assert(!org.issues.hasMessages)
        val pkg1 = org.packagesByNamespace(Some(Name("pkg1")))
        val pkg2 = org.packagesByNamespace(Some(Name("pkg2")))

        val labels1TypeId =
          pkg1.getTypeOfPathInternal(root.join("pkg1/CustomLabels.labels")).get.asTypeIdentifier
        assert(labels1TypeId.toString == "System.Label (pkg1)")
        assert(pkg1.getPathsOfType(labels1TypeId).sameElements(Array("/pkg1/CustomLabels.labels")))
        assert(pkg1.getDependencies(labels1TypeId, outerInheritanceOnly = false).isEmpty)

        val dummyTypeId =
          pkg2.getTypeOfPathInternal(root.join("pkg2/Dummy.cls")).get.asTypeIdentifier
        assert(
          pkg2
            .getDependencies(dummyTypeId, outerInheritanceOnly = false)
            .map(_.toString)
            .sameElements(Array("System.Label (pkg2)")))

        val label2TypeId =
          TypeIdentifier.fromJava(Name("pkg2"),
                                  TypeName(Name("Label"), Seq(), Some(TypeName(Name("System")))))
        assert(pkg2.getDependencyHolders(label2TypeId).sameElements(Array(dummyTypeId)))
        assert(
          pkg2
            .getDependencies(label2TypeId, outerInheritanceOnly = false)
            .sameElements(Array(labels1TypeId)))
    }
  }

  test("Base package label protected") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "pkg2",
            |"packageDirectories": [{"path": "pkg2"}],
            |"plugins": {"dependencies": [{"namespace": "pkg1", "path": "pkg1"}]}
            |}""".stripMargin,
        "pkg1/CustomLabels.labels" ->
          """<?xml version="1.0" encoding="UTF-8"?>
          |<CustomLabels xmlns="http://soap.sforce.com/2006/04/metadata">
          |    <labels>
          |        <fullName>TestLabel</fullName>
          |        <language>en_US</language>
          |        <protected>true</protected>
          |        <shortDescription>TestLabel Description</shortDescription>
          |        <value>TestLabel Value</value>
          |    </labels>
          |</CustomLabels>
          |""".stripMargin,
        "pkg2/Dummy.cls" -> "public class Dummy { {String a = label.pkg1.TestLabel;} }")) {
      root: PathLike =>
        val org = createOrg(root)
        val pkg1 = org.packagesByNamespace(Some(Name("pkg1")))
        val pkg2 = org.packagesByNamespace(Some(Name("pkg2")))
        assert(
          org.issues.getMessages("/pkg2/Dummy.cls") ==
            "Missing: line 1 at 33-53: Unknown field or type 'TestLabel' on 'System.Label.pkg1'\n")

        val labels1TypeId = pkg1
          .getTypeOfPathInternal(root.join("pkg1").join("CustomLabels.labels"))
          .get
          .asTypeIdentifier
        assert(labels1TypeId.toString == "System.Label (pkg1)")
        assert(pkg1.getPathsOfType(labels1TypeId).sameElements(Array("/pkg1/CustomLabels.labels")))
        assert(pkg2.getTypeOfPathInternal(root.join("CustomLabels.labels")).isEmpty)

        val dummyTypeId =
          pkg2.getTypeOfPathInternal(root.join("pkg2/Dummy.cls")).get.asTypeIdentifier
        assert(
          pkg2
            .getDependencies(dummyTypeId, outerInheritanceOnly = false)
            .sameElements(Array[TypeName]()))

        val label2TypeId =
          TypeIdentifier.fromJava(Name("pkg2"),
                                  TypeName(Name("Label"), Seq(), Some(TypeName(Name("System")))))
        assert(pkg2.getDependencyHolders(label2TypeId).isEmpty)
        assert(
          pkg2
            .getDependencies(label2TypeId, outerInheritanceOnly = false)
            .sameElements(Array(labels1TypeId)))
    }
  }

  test("System reference to label") {
    FileSystemHelper.run(
      Map(
        "CustomLabels.labels" ->
          """<?xml version="1.0" encoding="UTF-8"?>
          |<CustomLabels xmlns="http://soap.sforce.com/2006/04/metadata">
          |    <labels>
          |        <fullName>TestLabel</fullName>
          |        <language>en_US</language>
          |        <protected>true</protected>
          |        <shortDescription>TestLabel Description</shortDescription>
          |        <value>TestLabel Value</value>
          |    </labels>
          |</CustomLabels>
          |""".stripMargin,
        "Dummy.cls" -> "public class Dummy { {String a = System.Label.TestLabel;} }")) {
      root: PathLike =>
        val org = createOrg(root)
        assert(!org.issues.hasMessages)
    }
  }

  test("System reference to base package label") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "pkg2",
            |"packageDirectories": [{"path": "pkg2"}],
            |"plugins": {"dependencies": [{"namespace": "pkg1", "path": "pkg1"}]}
            |}""".stripMargin,
        "pkg1/CustomLabels.labels" ->
          """<?xml version="1.0" encoding="UTF-8"?>
          |<CustomLabels xmlns="http://soap.sforce.com/2006/04/metadata">
          |    <labels>
          |        <fullName>TestLabel</fullName>
          |        <language>en_US</language>
          |        <protected>false</protected>
          |        <shortDescription>TestLabel Description</shortDescription>
          |        <value>TestLabel Value</value>
          |    </labels>
          |</CustomLabels>
          |""".stripMargin,
        "pkg2/Dummy.cls" -> "public class Dummy { {String a = System.label.pkg1.TestLabel;} }")) {
      root: PathLike =>
        val org = createOrg(root)
        assert(!org.issues.hasMessages)
    }
  }

  test("Label dependencies") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "pkg2",
            |"packageDirectories": [{"path": "pkg2"}],
            |"plugins": {"dependencies": [{"namespace": "pkg1", "path": "pkg1"}]}
            |}""".stripMargin,
        "pkg1/CustomLabels.labels" ->
          """<?xml version="1.0" encoding="UTF-8"?>
          |<CustomLabels xmlns="http://soap.sforce.com/2006/04/metadata">
          |    <labels>
          |        <fullName>TestLabel</fullName>
          |        <language>en_US</language>
          |        <protected>false</protected>
          |        <shortDescription>TestLabel Description</shortDescription>
          |        <value>TestLabel Value</value>
          |    </labels>
          |</CustomLabels>
          |""".stripMargin,
        "pkg2/CustomLabels.labels" ->
          """<?xml version="1.0" encoding="UTF-8"?>
          |<CustomLabels xmlns="http://soap.sforce.com/2006/04/metadata"/>
          |""".stripMargin,
      )) { root: PathLike =>
      val org = createOrg(root)
      assert(!org.issues.hasMessages)
      val pkg1 = org.packagesByNamespace(Some(Name("pkg1")))
      val pkg2 = org.packagesByNamespace(Some(Name("pkg2")))

      val label1Type = pkg1
        .getTypeOfPathInternal(root.join("pkg1").join("CustomLabels.labels"))
        .get
        .asTypeIdentifier
      assert(label1Type.toString == "System.Label (pkg1)")
      val label2Type = pkg2
        .getTypeOfPathInternal(root.join("pkg2").join("CustomLabels.labels"))
        .get
        .asTypeIdentifier
      assert(label2Type.toString == "System.Label (pkg2)")

      assert(pkg1.getDependencies(label1Type, outerInheritanceOnly = false).isEmpty)
      assert(pkg2.getDependencyHolders(label2Type).isEmpty)

      assert(
        pkg2
          .getDependencies(label2Type, outerInheritanceOnly = false)
          .sameElements(Array(label1Type)))
      assert(pkg1.getDependencyHolders(label1Type).sameElements(Array(label2Type)))
    }
  }

  test("Label dependencies (multi file)") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "pkg2",
            |"packageDirectories": [{"path": "pkg2"}],
            |"plugins": {"dependencies": [{"namespace": "pkg1", "path": "pkg1"}]}
            |}""".stripMargin,
        "pkg1/CustomLabels.labels" ->
          """<?xml version="1.0" encoding="UTF-8"?>
          |<CustomLabels xmlns="http://soap.sforce.com/2006/04/metadata">
          |    <labels>
          |        <fullName>TestLabel</fullName>
          |        <language>en_US</language>
          |        <protected>false</protected>
          |        <shortDescription>TestLabel Description</shortDescription>
          |        <value>TestLabel Value</value>
          |    </labels>
          |</CustomLabels>
          |""".stripMargin,
        "pkg2/CustomLabels.labels" ->
          """<?xml version="1.0" encoding="UTF-8"?>
          |<CustomLabels xmlns="http://soap.sforce.com/2006/04/metadata"/>
          |""".stripMargin,
        "pkg2/Secondary.labels" ->
          """<?xml version="1.0" encoding="UTF-8"?>
          |<CustomLabels xmlns="http://soap.sforce.com/2006/04/metadata">
          |    <labels>
          |        <fullName>TestLabel2</fullName>
          |        <language>en_US</language>
          |        <protected>false</protected>
          |        <shortDescription>TestLabel2 Description</shortDescription>
          |        <value>TestLabel2 Value</value>
          |    </labels>
          |</CustomLabels>
          |""".stripMargin,
        "pkg2/Dummy.cls" -> "public class Dummy { {String a = System.label.pkg1.TestLabel;} }")) {
      root: PathLike =>
        val org = createOrg(root)
        assert(!org.issues.hasMessages)
        val pkg1 = org.packagesByNamespace(Some(Name("pkg1")))
        val pkg2 = org.packagesByNamespace(Some(Name("pkg2")))

        val label1Type = pkg1
          .getTypeOfPathInternal(root.join("pkg1").join("CustomLabels.labels"))
          .get
          .asTypeIdentifier
        assert(label1Type.toString == "System.Label (pkg1)")
        val label2Type = pkg2
          .getTypeOfPathInternal(root.join("pkg2").join("CustomLabels.labels"))
          .get
          .asTypeIdentifier
        assert(label2Type.toString == "System.Label (pkg2)")
        val secondaryType = pkg2
          .getTypeOfPathInternal(root.join("pkg2").join("Secondary.labels"))
          .get
          .asTypeIdentifier
        assert(secondaryType == label2Type)

        val dummyTypeId =
          pkg2.getTypeOfPathInternal(root.join("pkg2/Dummy.cls")).get.asTypeIdentifier

        assert(pkg1.getDependencies(label1Type, outerInheritanceOnly = false).isEmpty)
        assert(pkg2.getDependencyHolders(label2Type).sameElements(Array(dummyTypeId)))

        assert(
          pkg2
            .getDependencies(label2Type, outerInheritanceOnly = false)
            .sameElements(Array(label1Type)))
        assert(pkg1.getDependencyHolders(label1Type).sameElements(Array(label2Type)))
    }
  }

  test("Label dependencies (multi file reversed)") {
    FileSystemHelper.run(
      Map(
        "sfdx-project.json" ->
          """{
            |"namespace": "pkg2",
            |"packageDirectories": [{"path": "pkg2"}],
            |"plugins": {"dependencies": [{"namespace": "pkg1", "path": "pkg1"}]}
            |}""".stripMargin,
        "pkg1/CustomLabels.labels" ->
          """<?xml version="1.0" encoding="UTF-8"?>
          |<CustomLabels xmlns="http://soap.sforce.com/2006/04/metadata">
          |    <labels>
          |        <fullName>TestLabel</fullName>
          |        <language>en_US</language>
          |        <protected>false</protected>
          |        <shortDescription>TestLabel Description</shortDescription>
          |        <value>TestLabel Value</value>
          |    </labels>
          |</CustomLabels>
          |""".stripMargin,
        "pkg1/Secondary.labels" ->
          """<?xml version="1.0" encoding="UTF-8"?>
          |<CustomLabels xmlns="http://soap.sforce.com/2006/04/metadata">
          |    <labels>
          |        <fullName>TestLabel2</fullName>
          |        <language>en_US</language>
          |        <protected>false</protected>
          |        <shortDescription>TestLabel2 Description</shortDescription>
          |        <value>TestLabel2 Value</value>
          |    </labels>
          |</CustomLabels>
          |""".stripMargin,
        "pkg2/CustomLabels.labels" ->
          """<?xml version="1.0" encoding="UTF-8"?>
          |<CustomLabels xmlns="http://soap.sforce.com/2006/04/metadata"/>
          |""".stripMargin,
        "pkg2/Dummy.cls" -> "public class Dummy { {String a = System.label.pkg1.TestLabel2;} }")) {
      root: PathLike =>
        val org = createOrg(root)
        assert(!org.issues.hasMessages)
        val pkg1 = org.packagesByNamespace(Some(Name("pkg1")))
        val pkg2 = org.packagesByNamespace(Some(Name("pkg2")))

        val label1Type = pkg1
          .getTypeOfPathInternal(root.join("pkg1").join("CustomLabels.labels"))
          .get
          .asTypeIdentifier
        assert(label1Type.toString == "System.Label (pkg1)")
        val secondaryType = pkg1
          .getTypeOfPathInternal(root.join("pkg1").join("Secondary.labels"))
          .get
          .asTypeIdentifier
        assert(secondaryType == label1Type)

        val label2Type = pkg2
          .getTypeOfPathInternal(root.join("pkg2").join("CustomLabels.labels"))
          .get
          .asTypeIdentifier
        assert(label2Type.toString == "System.Label (pkg2)")
        val dummyTypeId =
          pkg2.getTypeOfPathInternal(root.join("pkg2/Dummy.cls")).get.asTypeIdentifier

        assert(pkg1.getDependencies(label1Type, outerInheritanceOnly = false).isEmpty)
        assert(pkg2.getDependencyHolders(label2Type).sameElements(Array(dummyTypeId)))

        assert(
          pkg2
            .getDependencies(label2Type, outerInheritanceOnly = false)
            .sameElements(Array(label1Type)))
        assert(pkg1.getDependencyHolders(label1Type).sameElements(Array(label2Type)))
    }
  }
}
