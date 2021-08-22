/*
 [The "BSD licence"]
 Copyright (c) 2021 Kevin Jones
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
package com.nawforce.pkgforce.workspace

import com.nawforce.pkgforce.diagnostics.{LocationAnd, _}
import com.nawforce.pkgforce.documents.SourceInfo
import com.nawforce.pkgforce.names.Name
import com.nawforce.pkgforce.path.PathLike
import com.nawforce.pkgforce.stream._
import com.nawforce.runtime.FileSystemHelper
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.collection.immutable.ArraySeq

class WorkspaceTest extends AnyFunSuite with Matchers {

  test("Empty dir has no events") {
    FileSystemHelper.run(Map[String, String]()) { root: PathLike =>
      val issuesAndWS = Workspace(root)
      assert(issuesAndWS.issues.isEmpty)
      assert(issuesAndWS.value.nonEmpty)
      assert(issuesAndWS.value.get.events.isEmpty)
    }
  }

  test("Label file event") {
    FileSystemHelper.run(
      Map[String, String](
        "pkg/CustomLabels.labels" -> "<CustomLabels xmlns=\"http://soap.sforce.com/2006/04/metadata\"/>",
      )) { root: PathLike =>
      val issuesAndWS = Workspace(root)
      assert(issuesAndWS.issues.isEmpty)
      assert(issuesAndWS.value.nonEmpty)
      issuesAndWS.value.get.events.toList should matchPattern {
        case List(LabelFileEvent(SourceInfo(labelsPath, _)))
            if labelsPath == root.join("pkg").join("CustomLabels.labels") =>
      }
    }
  }

  test("Label parse error") {
    FileSystemHelper.run(Map[String, String]("pkg/CustomLabels.labels" -> "<CustomLabels")) {
      root: PathLike =>
        val issuesAndWS = Workspace(root)
        assert(issuesAndWS.issues.isEmpty)
        assert(issuesAndWS.value.nonEmpty)
        issuesAndWS.value.get.events.toList should matchPattern {
          case List(
              IssuesEvent(ArraySeq(Issue("/pkg/CustomLabels.labels",
                                         Diagnostic(ERROR_CATEGORY, Location(1, _, 1, _), _))))) =>
        }
    }
  }

  test("Label events") {
    FileSystemHelper.run(
      Map[String, String]("pkg/CustomLabels.labels" ->
        """<?xml version="1.0" encoding="UTF-8"?>
          |<CustomLabels xmlns="http://soap.sforce.com/2006/04/metadata">
          |    <labels><fullName>TestLabel1</fullName><protected>false</protected></labels>
          |    <labels><fullName>TestLabel2</fullName><protected>true</protected></labels>
          |</CustomLabels>
          |""".stripMargin)) { root: PathLike =>
      val issuesAndWS = Workspace(root)
      assert(issuesAndWS.issues.isEmpty)
      assert(issuesAndWS.value.nonEmpty)

      issuesAndWS.value.get.events.toList should matchPattern {
        case List(LabelEvent(PathLocation("/pkg/CustomLabels.labels", Location(3, 0, 3, 0)),
                             Name("TestLabel1"),
                             false),
                  LabelEvent(PathLocation("/pkg/CustomLabels.labels", Location(4, 0, 4, 0)),
                             Name("TestLabel2"),
                             true),
                  LabelFileEvent(SourceInfo(labelsPath, _)))
            if labelsPath == root.join("pkg").join("CustomLabels.labels") =>
      }
    }
  }

  test("Page event") {
    FileSystemHelper.run(Map[String, String]("pkg/MyPage.page" -> "<apex:page/>")) {
      root: PathLike =>
        val issuesAndWS = Workspace(root)
        assert(issuesAndWS.issues.isEmpty)
        assert(issuesAndWS.value.nonEmpty)
        issuesAndWS.value.get.events.toList should matchPattern {
          case List(PageEvent(SourceInfo(pagePath, _), _, _))
              if pagePath == root.join("pkg").join("MyPage.page") =>
        }
    }
  }

  test("Page event with controller") {
    FileSystemHelper.run(
      Map[String, String]("pkg/MyPage.page" -> "<apex:page controller='MyController'/>")) {
      root: PathLike =>
        val issuesAndWS = Workspace(root)
        assert(issuesAndWS.issues.isEmpty)
        assert(issuesAndWS.value.nonEmpty)
        issuesAndWS.value.get.events.toList should matchPattern {
          case List(PageEvent(SourceInfo(pagePath, _), controllers, _))
              if pagePath == root.join("pkg").join("MyPage.page") &&
                (controllers sameElements Array(
                  LocationAnd(Location(1, 11, 1, 36), Name("MyController")))) =>
        }
    }
  }

  test("Page event with controller & extensions") {
    FileSystemHelper.run(
      Map[String, String](
        "pkg/MyPage.page" -> "<apex:page controller='MyController' extensions='Ext1, Ext2'/>")) {
      root: PathLike =>
        val issuesAndWS = Workspace(root)
        assert(issuesAndWS.issues.isEmpty)
        assert(issuesAndWS.value.nonEmpty)
        issuesAndWS.value.get.events.toList should matchPattern {
          case List(PageEvent(SourceInfo(pagePath, _), controllers, _))
              if pagePath == root.join("pkg").join("MyPage.page") &&
                (controllers sameElements Array(
                  LocationAnd(Location(1, 11, 1, 36), Name("MyController")),
                  LocationAnd(Location(1, 37, 1, 60), Name("Ext1")),
                  LocationAnd(Location(1, 37, 1, 60), Name("Ext2")))) =>
        }
    }
  }

  test("Page event with attribute expressions") {
    FileSystemHelper.run(Map[String, String](
      "pkg/MyPage.page" -> "<apex:page a = '{!foo}'><a href='{!bar}' other='{!baz}'/></apex:page>")) {
      root: PathLike =>
        val issuesAndWS = Workspace(root)
        assert(issuesAndWS.issues.isEmpty)
        assert(issuesAndWS.value.nonEmpty)
        issuesAndWS.value.get.events.toList should matchPattern {
          case List(PageEvent(SourceInfo(pagePath, _), _, exprs))
              if pagePath == root.join("pkg").join("MyPage.page") &&
                (exprs.toSet == Set(LocationAnd(Location(1, 33, 1, 39), "bar"),
                                    LocationAnd(Location(1, 48, 1, 54), "baz"),
                                    LocationAnd(Location(1, 16, 1, 22), "foo"))) =>
        }
    }
  }

  test("Page event with char data expressions") {
    FileSystemHelper.run(
      Map[String, String](
        "pkg/MyPage.page" -> s"<apex:page>{!foo} xx <a> {!bar} </a>{!baz}</apex:page>")) {
      root: PathLike =>
        val issuesAndWS = Workspace(root)
        assert(issuesAndWS.issues.isEmpty)
        assert(issuesAndWS.value.nonEmpty)
        issuesAndWS.value.get.events.toList should matchPattern {
          case List(PageEvent(SourceInfo(pagePath, _), _, exprs))
              if pagePath == root.join("pkg").join("MyPage.page") &&
                (exprs.toSet == Set(LocationAnd(Location(1, 24, 1, 32), "bar"),
                                    LocationAnd(Location(1, 36, 1, 42), "baz"),
                                    LocationAnd(Location(1, 11, 1, 21), "foo"))) =>
        }
    }
  }

  test("Flow event") {
    FileSystemHelper.run(Map[String, String]("pkg/MyFlow.flow" -> "")) { root: PathLike =>
      val issuesAndWS = Workspace(root)
      assert(issuesAndWS.issues.isEmpty)
      assert(issuesAndWS.value.nonEmpty)
      issuesAndWS.value.get.events.toList should matchPattern {
        case List(FlowEvent(SourceInfo(flowPath, _)))
            if flowPath == root.join("pkg").join("MyFlow.flow") =>
      }
    }
  }

  test("Component event") {
    FileSystemHelper.run(
      Map[String, String]("pkg/MyComponent.component" ->
        """<apex:component>
            |  <apex:attribute name="test" type="String"/>
            |</apex:component>
            |""".stripMargin)) { root: PathLike =>
      val issuesAndWS = Workspace(root)
      assert(issuesAndWS.issues.isEmpty)
      assert(issuesAndWS.value.nonEmpty)
      issuesAndWS.value.get.events.toList should matchPattern {
        case List(ComponentEvent(SourceInfo(componentPath, _), Array(Name("test")), _, _))
            if componentPath == root.join("pkg").join("MyComponent.component") =>
      }
    }
  }

  test("Component event with controller") {
    FileSystemHelper.run(
      Map[String, String]("pkg/MyComponent.component" ->
        """<apex:component controller='MyController'>
          |  <apex:attribute name="test" type="String"/>
          |</apex:component>
          |""".stripMargin)) { root: PathLike =>
      val issuesAndWS = Workspace(root)
      assert(issuesAndWS.issues.isEmpty)
      assert(issuesAndWS.value.nonEmpty)
      issuesAndWS.value.get.events.toList should matchPattern {
        case List(ComponentEvent(SourceInfo(componentPath, _), Array(Name("test")), controllers, _))
            if componentPath == root.join("pkg").join("MyComponent.component") &&
              (controllers sameElements Array(
                LocationAnd(Location(1, 16, 1, 41), Name("MyController")))) =>
      }
    }
  }

  test("Component event with attribute expressions") {
    FileSystemHelper.run(Map[String, String](
      "pkg/MyComponent.component" -> "<apex:component a = '{!foo}'><a href='{!bar}' other='{!baz}'/></apex:component>")) {
      root: PathLike =>
        val issuesAndWS = Workspace(root)
        assert(issuesAndWS.issues.isEmpty)
        assert(issuesAndWS.value.nonEmpty)
        issuesAndWS.value.get.events.toList should matchPattern {
          case List(ComponentEvent(SourceInfo(componentPath, _), _, _, exprs))
              if componentPath == root.join("pkg").join("MyComponent.component") &&
                (exprs.toSet == Set(LocationAnd(Location(1, 38, 1, 44), "bar"),
                                    LocationAnd(Location(1, 53, 1, 59), "baz"),
                                    LocationAnd(Location(1, 21, 1, 27), "foo"))) =>
        }
    }
  }

  test("Component event with char data expressions") {
    FileSystemHelper.run(Map[String, String](
      "pkg/MyComponent.component" -> s"<apex:component>{!foo} xx <a> {!bar} </a>{!baz}</apex:component>")) {
      root: PathLike =>
        val issuesAndWS = Workspace(root)
        assert(issuesAndWS.issues.isEmpty)
        assert(issuesAndWS.value.nonEmpty)
        issuesAndWS.value.get.events.toList should matchPattern {
          case List(ComponentEvent(SourceInfo(componentPath, _), _, _, exprs))
              if componentPath == root.join("pkg").join("MyComponent.component") &&
                (exprs.toSet == Set(LocationAnd(Location(1, 29, 1, 37), "bar"),
                                    LocationAnd(Location(1, 41, 1, 47), "baz"),
                                    LocationAnd(Location(1, 16, 1, 26), "foo"))) =>
        }
    }
  }

  test("Component parse error") {
    FileSystemHelper.run(
      Map[String, String]("pkg/MyComponent.component" ->
        """<apex:component
          |""".stripMargin)) { root: PathLike =>
      val issuesAndWS = Workspace(root)
      assert(issuesAndWS.issues.isEmpty)
      assert(issuesAndWS.value.nonEmpty)
      issuesAndWS.value.get.events.toList should matchPattern {
        case List(
            IssuesEvent(
              ArraySeq(
                Issue("/pkg/MyComponent.component",
                      Diagnostic(SYNTAX_CATEGORY,
                                 Location(2, 0, 2, 0),
                                 "no viable alternative at input '<apex:component'"))))) =>
      }
    }
  }

  test("Component structure error") {
    FileSystemHelper.run(
      Map[String, String]("pkg/MyComponent.component" ->
        """<apex:foo/>
          |""".stripMargin)) { root: PathLike =>
      val issuesAndWS = Workspace(root)
      assert(issuesAndWS.issues.isEmpty)
      assert(issuesAndWS.value.nonEmpty)
      issuesAndWS.value.get.events.toList should matchPattern {
        case List(
            IssuesEvent(
              ArraySeq(
                Issue("/pkg/MyComponent.component",
                      Diagnostic(ERROR_CATEGORY,
                                 Location(1, 0, 2, 5),
                                 "Root element must be 'apex:component'"))))) =>
      }
    }
  }

  test("Custom Object event") {
    FileSystemHelper.run(
      Map[String, String]("pkg/MyObject.object" ->
        "<CustomObject xmlns=\"http://soap.sforce.com/2006/04/metadata\"/>")) { root: PathLike =>
      val issuesAndWS = Workspace(root)
      assert(issuesAndWS.issues.isEmpty)
      assert(issuesAndWS.value.nonEmpty)
      val reportingPath = root.join("pkg").join("MyObject.object")
      issuesAndWS.value.get.events.toList should matchPattern {
        case List(SObjectEvent(sourceInfo, path, false, None, None))
          if sourceInfo.get.path == reportingPath && path == reportingPath =>
      }
    }
  }

  test("Custom Object event parse error") {
    FileSystemHelper.run(
      Map[String, String]("pkg/MyObject.object" ->
        "<CustomObject xmlns=\"http://soap.sforce.com/2006/04/metadata\"")) { root: PathLike =>
      val issuesAndWS = Workspace(root)
      assert(issuesAndWS.issues.isEmpty)
      assert(issuesAndWS.value.nonEmpty)
      issuesAndWS.value.get.events.toList should matchPattern {
        case List(
            IssuesEvent(ArraySeq(Issue("/pkg/MyObject.object",
                                       Diagnostic(ERROR_CATEGORY, Location(1, _, 1, _), _))))) =>
      }
    }
  }

  test("List Custom Setting") {
    FileSystemHelper.run(
      Map[String, String]("pkg/MyObject.object" ->
        """<CustomObject xmlns="http://soap.sforce.com/2006/04/metadata">
          |  <customSettingsType>List</customSettingsType>
          |</CustomObject>
          |""".stripMargin)) { root: PathLike =>
      val issuesAndWS = Workspace(root)
      assert(issuesAndWS.issues.isEmpty)
      assert(issuesAndWS.value.nonEmpty)
      val reportingPath = root.join("pkg").join("MyObject.object")
      issuesAndWS.value.get.events.toList should matchPattern {
        case List(SObjectEvent(sourceInfo, path, false, Some(ListCustomSetting), None))
          if sourceInfo.get.path == reportingPath && path == reportingPath =>
      }
    }
  }

  test("Hierarchical Custom Setting") {
    FileSystemHelper.run(
      Map[String, String]("pkg/MyObject.object" ->
        """<CustomObject xmlns="http://soap.sforce.com/2006/04/metadata">
          |  <customSettingsType>Hierarchy</customSettingsType>
          |</CustomObject>
          |""".stripMargin)) { root: PathLike =>
      val issuesAndWS = Workspace(root)
      assert(issuesAndWS.issues.isEmpty)
      assert(issuesAndWS.value.nonEmpty)
      val reportingPath = root.join("pkg").join("MyObject.object")
      issuesAndWS.value.get.events.toList should matchPattern {
        case List(SObjectEvent(sourceInfo, path, false, Some(HierarchyCustomSetting), None))
          if sourceInfo.get.path == reportingPath && path == reportingPath =>
      }
    }
  }

  test("Bad Custom Setting") {
    FileSystemHelper.run(
      Map[String, String]("pkg/MyObject.object" ->
        """<CustomObject xmlns="http://soap.sforce.com/2006/04/metadata">
          |  <customSettingsType>Bad</customSettingsType>
          |</CustomObject>
          |""".stripMargin)) { root: PathLike =>
      val issuesAndWS = Workspace(root)
      assert(issuesAndWS.issues.isEmpty)
      assert(issuesAndWS.value.nonEmpty)
      val reportingPath = root.join("pkg").join("MyObject.object")
      issuesAndWS.value.get.events.toList should matchPattern {
        case List(
        SObjectEvent(sourceInfo, path, false, None, None),
        IssuesEvent(ArraySeq(
        Issue("/pkg/MyObject.object", Diagnostic(ERROR_CATEGORY, Location(1, _, 1, _), _)))))
          if sourceInfo.get.path == reportingPath && path == reportingPath =>
      }
    }
  }

  test("ReadWrite SharingModel") {
    FileSystemHelper.run(
      Map[String, String]("pkg/MyObject.object" ->
        """<CustomObject xmlns="http://soap.sforce.com/2006/04/metadata">
          |  <sharingModel>ReadWrite</sharingModel>
          |</CustomObject>
          |""".stripMargin)) { root: PathLike =>
      val issuesAndWS = Workspace(root)
      assert(issuesAndWS.issues.isEmpty)
      assert(issuesAndWS.value.nonEmpty)
      val reportingPath = root.join("pkg").join("MyObject.object")
      issuesAndWS.value.get.events.toList should matchPattern {
        case List(SObjectEvent(sourceInfo, path, false, None, Some(ReadWriteSharingModel)))
          if sourceInfo.get.path == reportingPath && path == reportingPath =>
      }
    }
  }

  test("Bad SharingModel") {
    FileSystemHelper.run(
      Map[String, String]("pkg/MyObject.object" ->
        """<CustomObject xmlns="http://soap.sforce.com/2006/04/metadata">
          |   <sharingModel>Something</sharingModel>
          |</CustomObject>
          |""".stripMargin)) { root: PathLike =>
      val issuesAndWS = Workspace(root)
      assert(issuesAndWS.issues.isEmpty)
      assert(issuesAndWS.value.nonEmpty)
      val reportingPath = root.join("pkg").join("MyObject.object")
      issuesAndWS.value.get.events.toList should matchPattern {
        case List(
        SObjectEvent(sourceInfo, path, false, None, None),
        IssuesEvent(ArraySeq(
        Issue("/pkg/MyObject.object", Diagnostic(ERROR_CATEGORY, Location(1, _, 1, _), _)))))
          if sourceInfo.get.path == reportingPath && path == reportingPath =>
      }
    }
  }

  test("Custom Object with fields") {
    FileSystemHelper.run(
      Map[String, String]("pkg/MyObject.object" ->
        """<CustomObject xmlns="http://soap.sforce.com/2006/04/metadata">
          |  <fields><fullName>Name__c</fullName><type>Text</type></fields>
          |</CustomObject>
          |""".stripMargin)) { root: PathLike =>
      val issuesAndWS = Workspace(root)
      assert(issuesAndWS.issues.isEmpty)
      assert(issuesAndWS.value.nonEmpty)
      val reportingPath = root.join("pkg").join("MyObject.object")
      issuesAndWS.value.get.events.toList should matchPattern {
        case List(SObjectEvent(sourceInfo, path, false, None, None),
        CustomFieldEvent(None, Name("Name__c"), Name("Text"), None))
          if sourceInfo.get.path == reportingPath && path == reportingPath =>
      }
    }
  }

  test("Custom Object with fieldsset") {
    FileSystemHelper.run(
      Map[String, String]("pkg/MyObject.object" ->
        """<CustomObject xmlns="http://soap.sforce.com/2006/04/metadata">
          |  <fieldSets><fullName>Name</fullName></fieldSets>
          |</CustomObject>
          |""".stripMargin)) { root: PathLike =>
      val issuesAndWS = Workspace(root)
      assert(issuesAndWS.issues.isEmpty)
      assert(issuesAndWS.value.nonEmpty)
      val reportingPath = root.join("pkg").join("MyObject.object")
      issuesAndWS.value.get.events.toList should matchPattern {
        case List(SObjectEvent(sourceInfo, path, false, None, None), FieldsetEvent(None, Name("Name")))
          if sourceInfo.get.path == reportingPath && path == reportingPath =>
      }
    }
  }

  test("Custom Object with sharing reason") {
    FileSystemHelper.run(
      Map[String, String]("pkg/MyObject.object" ->
        """<CustomObject xmlns="http://soap.sforce.com/2006/04/metadata">
          |  <sharingReasons><fullName>Name</fullName></sharingReasons>
          |</CustomObject>
          |""".stripMargin)) { root: PathLike =>
      val issuesAndWS = Workspace(root)
      assert(issuesAndWS.issues.isEmpty)
      assert(issuesAndWS.value.nonEmpty)
      val reportingPath = root.join("pkg").join("MyObject.object")
      issuesAndWS.value.get.events.toList should matchPattern {
        case List(SObjectEvent(sourceInfo, path, false, None, None),
        SharingReasonEvent(None, Name("Name")))
          if sourceInfo.get.path == reportingPath && path == reportingPath =>
      }
    }
  }

  test("Custom Object with fields (sfdx)") {
    FileSystemHelper.run(
      Map[String, String](
        "pkg/MyObject/MyObject.object-meta.xml" -> "<CustomObject xmlns=\"http://soap.sforce.com/2006/04/metadata\"/>",
        "pkg/MyObject/fields/Name__c.field-meta.xml" ->
          s"""<?xml version="1.0" encoding="UTF-8"?>
          |<CustomField xmlns="http://soap.sforce.com/2006/04/metadata">
          |    <fullName>Name__c</fullName>
          |    <type>Text</type>
          |</CustomField>
          |""".stripMargin)) { root: PathLike =>
      val issuesAndWS = Workspace(root)
      assert(issuesAndWS.issues.isEmpty)
      assert(issuesAndWS.value.nonEmpty)
      val objectPath = root.join("pkg").join("MyObject")
      issuesAndWS.value.get.events.toList should matchPattern {
        case List(SObjectEvent(sourceInfo, path, false, None, None),
        CustomFieldEvent(sourceInfoField, Name("Name__c"), Name("Text"), None))
          if sourceInfo.get.path == root
            .join("pkg")
            .join("MyObject")
            .join("MyObject.object-meta.xml") &&
            sourceInfoField.get.path == objectPath
              .join("fields")
              .join("Name__c.field-meta.xml") && path == objectPath =>
      }
    }
  }

  test("Custom Object with fieldSet (sfdx)") {
    FileSystemHelper.run(
      Map[String, String](
        "pkg/MyObject/MyObject.object-meta.xml" -> "<CustomObject xmlns=\"http://soap.sforce.com/2006/04/metadata\"/>",
        "pkg/MyObject/fieldSets/Name.fieldSet-meta.xml" ->
          s"""<?xml version="1.0" encoding="UTF-8"?>
             |<FieldSet xmlns="http://soap.sforce.com/2006/04/metadata">
             |    <fullName>Name</fullName>
             |</FieldSet>
             |""".stripMargin)) { root: PathLike =>
      val issuesAndWS = Workspace(root)
      assert(issuesAndWS.issues.isEmpty)
      assert(issuesAndWS.value.nonEmpty)
      val objectPath = root.join("pkg").join("MyObject")
      issuesAndWS.value.get.events.toList should matchPattern {
        case List(SObjectEvent(sourceInfo, path, false, None, None),
        FieldsetEvent(sourceInfoFieldset, Name("Name")))
          if sourceInfo.get.path == root
            .join("pkg")
            .join("MyObject")
            .join("MyObject.object-meta.xml") &&
            sourceInfoFieldset.get.path == objectPath
              .join("fieldSets")
              .join("Name.fieldSet-meta.xml") && path == objectPath =>
      }
    }
  }

  test("Custom Object with sharingReason (sfdx)") {
    FileSystemHelper.run(
      Map[String, String](
        "pkg/MyObject/MyObject.object-meta.xml" -> "<CustomObject xmlns=\"http://soap.sforce.com/2006/04/metadata\"/>",
        "pkg/MyObject/sharingReasons/Name.sharingReason-meta.xml" ->
          s"""<?xml version="1.0" encoding="UTF-8"?>
             |<SharingReason xmlns="http://soap.sforce.com/2006/04/metadata">
             |    <fullName>Name</fullName>
             |</SharingReason>
             |""".stripMargin)) { root: PathLike =>
      val issuesAndWS = Workspace(root)
      assert(issuesAndWS.issues.isEmpty)
      assert(issuesAndWS.value.nonEmpty)
      val objectPath = root.join("pkg").join("MyObject")
      issuesAndWS.value.get.events.toList should matchPattern {
        case List(SObjectEvent(sourceInfo, path, false, None, None),
        SharingReasonEvent(sourceInfoSharingReason, Name("Name")))
          if sourceInfo.get.path == root
            .join("pkg")
            .join("MyObject")
            .join("MyObject.object-meta.xml") &&
            sourceInfoSharingReason.get.path == objectPath
              .join("sharingReasons")
              .join("Name.sharingReason-meta.xml") && path == objectPath =>
      }
    }
  }

  test("Master/Detail natural ordered") {
    FileSystemHelper.run(
      Map[String, String](
        "pkg/MyMaster.object" ->
          """<CustomObject xmlns="http://soap.sforce.com/2006/04/metadata">
          |</CustomObject>
          |""".stripMargin,
        "pkg/sub/MyDetail.object" ->
          """<CustomObject xmlns="http://soap.sforce.com/2006/04/metadata">
            |  <fields>
            |     <fullName>Lookup__c</fullName>
            |     <type>MasterDetail</type>
            |     <referenceTo>MyMaster</referenceTo>
            |     <relationshipName>Master</relationshipName>
            |   </fields>
            |</CustomObject>
            |""".stripMargin)) { root: PathLike =>
      val issuesAndWS = Workspace(root)
      assert(issuesAndWS.issues.isEmpty)
      assert(issuesAndWS.value.nonEmpty)
      val masterReportingPath = root.join("pkg").join("MyMaster.object")
      val detailReportingPath = root.join("pkg").join("sub").join("MyDetail.object")
      issuesAndWS.value.get.events.toList should matchPattern {
        case List(SObjectEvent(sourceInfo1, masterPath, false, None, None),
        SObjectEvent(sourceInfo2, detailPath, false, None, None),
        CustomFieldEvent(None,
        Name("Lookup__c"),
        Name("MasterDetail"),
        Some((Name("MyMaster"), Name("Master")))))
          if sourceInfo1.get.path == masterReportingPath &&
            sourceInfo2.get.path == detailReportingPath &&
            masterPath == masterReportingPath && detailPath == detailReportingPath =>
      }
    }
  }

  test("Master/Detail reverse ordered") {
    FileSystemHelper.run(
      Map[String, String](
        "pkg/sub/MyMaster.object" ->
          """<CustomObject xmlns="http://soap.sforce.com/2006/04/metadata">
          |</CustomObject>
          |""".stripMargin,
        "pkg/MyDetail.object" ->
          """<CustomObject xmlns="http://soap.sforce.com/2006/04/metadata">
            |  <fields>
            |    <fullName>Lookup__c</fullName>
            |    <type>MasterDetail</type>
            |    <referenceTo>MyMaster</referenceTo>
            |    <relationshipName>Master</relationshipName>
            |  </fields>
            |</CustomObject>
            |""".stripMargin)) { root: PathLike =>
      val issuesAndWS = Workspace(root)
      assert(issuesAndWS.issues.isEmpty)
      assert(issuesAndWS.value.nonEmpty)
      val masterReportingPath = root.join("pkg").join("sub").join("MyMaster.object")
      val detailReportingPath = root.join("pkg").join("MyDetail.object")
      issuesAndWS.value.get.events.toList should matchPattern {
        case List(SObjectEvent(sourceInfo1, masterPath, false, None, None),
        SObjectEvent(sourceInfo2, detailPath, false, None, None),
        CustomFieldEvent(None,
        Name("Lookup__c"),
        Name("MasterDetail"),
        Some((Name("MyMaster"), Name("Master")))))
          if sourceInfo1.get.path == masterReportingPath &&
            sourceInfo2.get.path == detailReportingPath &&
            masterPath == masterReportingPath && detailPath == detailReportingPath =>
      }
    }
  }

  test("Apex event") {
    FileSystemHelper.run(Map[String, String]("pkg/MyClass.cls" -> "")) { root: PathLike =>
      val issuesAndWS = Workspace(root)
      assert(issuesAndWS.issues.isEmpty)
      assert(issuesAndWS.value.nonEmpty)
      issuesAndWS.value.get.events.toList should matchPattern {
        case List(ApexEvent(classPath)) if classPath == root.join("pkg").join("MyClass.cls") =>
      }
    }
  }

  test("Trigger event") {
    FileSystemHelper.run(Map[String, String]("pkg/MyTrigger.trigger" -> "")) { root: PathLike =>
      val issuesAndWS = Workspace(root)
      assert(issuesAndWS.issues.isEmpty)
      assert(issuesAndWS.value.nonEmpty)
      issuesAndWS.value.get.events.toList should matchPattern {
        case List(TriggerEvent(triggerPath))
            if triggerPath == root.join("pkg").join("MyTrigger.trigger") =>
      }
    }
  }
}
