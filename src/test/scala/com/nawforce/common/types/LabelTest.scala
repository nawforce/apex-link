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

package com.nawforce.common.types

import com.nawforce.common.api.{Name, Org, ServerOps, TypeName}
import com.nawforce.common.org.{OrgImpl, PackageImpl}
import com.nawforce.common.path.PathLike
import com.nawforce.runtime.FileSystemHelper
import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

class LabelTest extends AnyFunSuite with BeforeAndAfter {

  before {
    ServerOps.setParsedDataCaching(false)
  }

  test("Empty labels file") {
    FileSystemHelper.run(Map(
      "CustomLabels.labels" -> "",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(org.issues.getMessages(root.join("CustomLabels.labels").toString).nonEmpty)
      assert(pkg.getTypeOfPath(root.join("CustomLabels.labels").toString) == null)
    }
  }

  test("Minimal labels file") {
    FileSystemHelper.run(Map(
      "CustomLabels.labels" -> "<CustomLabels xmlns=\"http://soap.sforce.com/2006/04/metadata\"/>",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      val typeLike = pkg.getTypeOfPath(root.join("CustomLabels.labels").toString)
      assert(typeLike.toString == "System.Label")
      assert(pkg.getPathsOfType(typeLike).sameElements(Array("/CustomLabels.labels")))
      assert(pkg.getDependencies(typeLike, inheritanceOnly = false) == null)
      assert(pkg.getDependencyHolders(typeLike).isEmpty)
    }
  }

  test("Dual labels file") {
    FileSystemHelper.run(Map(
      "CustomLabels.labels" -> "<CustomLabels xmlns=\"http://soap.sforce.com/2006/04/metadata\"/>",
      "CustomLabels2.labels" -> "<CustomLabels xmlns=\"http://soap.sforce.com/2006/04/metadata\"/>",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      val typeLike1 = pkg.getTypeOfPath(root.join("CustomLabels.labels").toString)
      val typeLike2 = pkg.getTypeOfPath(root.join("CustomLabels2.labels").toString)
      assert(typeLike1.toString == "System.Label")
      assert(typeLike2.toString == "System.Label")
      assert(pkg.getPathsOfType(typeLike1).sorted.sameElements(Array("/CustomLabels.labels", "/CustomLabels2.labels")))
      assert(pkg.getDependencies(typeLike1, inheritanceOnly = false) == null)
      assert(pkg.getDependencyHolders(typeLike1).isEmpty)
    }
  }

  test("Valid label") {
    FileSystemHelper.run(Map(
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
      "Dummy.cls" -> "public class Dummy { {String a = label.TestLabel;} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg = org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)

      val labelsTypeLike = pkg.getTypeOfPath(root.join("CustomLabels.labels").toString)
      assert(labelsTypeLike.toString == "System.Label")
      assert(pkg.getPathsOfType(labelsTypeLike).sameElements(Array("/CustomLabels.labels")))
      assert(pkg.getDependencies(labelsTypeLike, inheritanceOnly = false) == null)

      val dummyTypeLike = pkg.getTypeOfPath(root.join("Dummy.cls").toString)
      assert(pkg.getDependencies(dummyTypeLike, inheritanceOnly = false).sameElements(Array(labelsTypeLike)))
      assert(pkg.getDependencyHolders(labelsTypeLike).sameElements(Array(dummyTypeLike)))
    }
  }

  test("Valid label (case insensitive)") {
    FileSystemHelper.run(Map(
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
      "Dummy.cls" -> "public class Dummy { {String a = laBel.TeStLaBel;} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("Missing label") {
    FileSystemHelper.run(Map(
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
      "Dummy.cls" -> "public class Dummy { {String a = Label.TestLabel2;} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(org.issues.getMessages("/Dummy.cls") ==
        "Missing: line 1 at 33-49: Unknown field or type 'TestLabel2' on 'System.Label'\n")
    }
  }

  test("Missing label (case insensitive)") {
    FileSystemHelper.run(Map(
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
      "Dummy.cls" -> "public class Dummy { {String a = laBel.TestLaBel2;} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(org.issues.getMessages("/Dummy.cls") ==
        "Missing: line 1 at 33-49: Unknown field or type 'TestLaBel2' on 'System.Label'\n")
    }
  }

  test("Base package label") {
    FileSystemHelper.run(Map(
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
      "pkg2/Dummy.cls" -> "public class Dummy { {String a = label.pkg1.TestLabel;} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg1 = org.addPackage(Some(Name("pkg1")), Seq(root.join("pkg1")), Seq()).asInstanceOf[PackageImpl]
      val pkg2 = org.addPackage(Some(Name("pkg2")), Seq(root.join("pkg2")), Seq(pkg1))
      assert(!org.issues.hasMessages)

      val labelsTypeLike = pkg1.getTypeOfPath(root.join("pkg1/CustomLabels.labels").toString)
      assert(labelsTypeLike.toString == "System.Label (pkg1)")
      assert(pkg1.getPathsOfType(labelsTypeLike).sameElements(Array("/pkg1/CustomLabels.labels")))
      assert(pkg1.getDependencies(labelsTypeLike, inheritanceOnly = false) == null)

      val dummyTypeLike = pkg2.getTypeOfPath(root.join("pkg2/Dummy.cls").toString)
      assert(pkg2.getDependencies(dummyTypeLike, inheritanceOnly = false).map(_.toString)
        .sameElements(Array("System.Label (pkg2)")))

      // TODO: Restore when we can access pkg2 Labels
      // assert(pkg2.getDependencyHolders(labelsTypeLike).sameElements(Array(dummyTypeLike)))
    }
  }

  test("Base package label protected") {
    FileSystemHelper.run(Map(
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
      "pkg2/Dummy.cls" -> "public class Dummy { {String a = label.pkg1.TestLabel;} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg1 = org.addPackage(Some(Name("pkg1")), Seq(root.join("pkg1")), Seq()).asInstanceOf[PackageImpl]
      val pkg2 = org.addPackage(Some(Name("pkg2")), Seq(root.join("pkg2")), Seq(pkg1))
      assert(org.issues.getMessages("/pkg2/Dummy.cls") ==
        "Missing: line 1 at 33-53: Unknown field or type 'TestLabel' on 'System.Label.pkg1'\n")

      val labelsTypeLike = pkg1.getTypeOfPath(root.join("pkg1").join("CustomLabels.labels").toString)
      assert(labelsTypeLike.toString == "System.Label (pkg1)")
      assert(pkg1.getPathsOfType(labelsTypeLike).sameElements(Array("/pkg1/CustomLabels.labels")))
      assert(pkg2.getTypeOfPath(root.join("CustomLabels.labels").toString) == null)

      val dummyTypeLike = pkg2.getTypeOfPath(root.join("pkg2/Dummy.cls").toString)
      assert(pkg2.getDependencies(dummyTypeLike, inheritanceOnly = false).sameElements(Array[TypeName]()))

      // TODO: Restore when we can access pkg2 Labels
      // assert(pkg2.getDependencyHolders(labelsTypeLike).sameElements(Array[TypeLike]()))
    }
  }

  test("System reference to label") {
    FileSystemHelper.run(Map(
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
      "Dummy.cls" -> "public class Dummy { {String a = System.Label.TestLabel;} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
    }
  }

  test("System reference to base package label") {
    FileSystemHelper.run(Map(
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
      "pkg2/Dummy.cls" -> "public class Dummy { {String a = System.label.pkg1.TestLabel;} }"
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      val pkg1 = org.addPackage(Some(Name("pkg1")), Seq(root.join("pkg1")), Seq()).asInstanceOf[PackageImpl]
      org.addPackage(Some(Name("pkg2")), Seq(root.join("pkg2")), Seq(pkg1))
      assert(!org.issues.hasMessages)
    }
  }
}
