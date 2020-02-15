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

import com.nawforce.common.api.{Org, ServerOps}
import com.nawforce.common.names.Name
import com.nawforce.common.org.OrgImpl
import com.nawforce.common.path.{PathFactory, PathLike}
import com.nawforce.common.pkg.PackageImpl
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
      org.addPackage(None, Seq(root), Seq())
      assert(org.issues.getMessages(root.join("CustomLabels.labels")).nonEmpty)
    }
  }

  test("Minimal labels file") {
    FileSystemHelper.run(Map(
      "CustomLabels.labels" -> "<CustomLabels xmlns=\"http://soap.sforce.com/2006/04/metadata\"/>",
    )) { root: PathLike =>
      val org = Org.newOrg().asInstanceOf[OrgImpl]
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
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
      org.addPackage(None, Seq(root), Seq())
      assert(!org.issues.hasMessages)
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
      assert(org.issues.getMessages(PathFactory("/Dummy.cls")) ==
        "Error: line 1 at 33-49: Unknown field or type 'TestLabel2' on 'System.Label'\n")
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
      assert(org.issues.getMessages(PathFactory("/Dummy.cls")) ==
        "Error: line 1 at 33-49: Unknown field or type 'TestLaBel2' on 'System.Label'\n")
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
      org.addPackage(Some(Name("pkg2")), Seq(root.join("pkg2")), Seq(pkg1))
      assert(!org.issues.hasMessages)
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
      org.addPackage(Some(Name("pkg2")), Seq(root.join("pkg2")), Seq(pkg1))
      assert(org.issues.getMessages(PathFactory("/pkg2/Dummy.cls")) ==
        "Error: line 1 at 33-53: Unknown field or type 'TestLabel' on 'System.Label.pkg1'\n")
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
