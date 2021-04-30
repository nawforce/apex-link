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
package com.nawforce.common.workspace

import com.nawforce.common.diagnostics._
import com.nawforce.common.names.Name
import com.nawforce.common.path.PathLike
import com.nawforce.common.stream._
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
        case List(LabelFileEvent("/pkg/CustomLabels.labels")) =>
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
              IssuesEvent(
                ArraySeq(
                  Issue(
                    "/pkg/CustomLabels.labels",
                    Diagnostic(
                      ERROR_CATEGORY,
                      Location(1, _, 1, _),
                      _))))) =>
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
                  LabelFileEvent("/pkg/CustomLabels.labels")) =>
      }
    }
  }

  test("Page event") {
    FileSystemHelper.run(Map[String, String]("pkg/MyPage.page" -> "")) { root: PathLike =>
      val issuesAndWS = Workspace(root)
      assert(issuesAndWS.issues.isEmpty)
      assert(issuesAndWS.value.nonEmpty)
      issuesAndWS.value.get.events.toList should matchPattern {
        case List(PageEvent("/pkg/MyPage.page")) =>
      }
    }
  }

  test("Flow event") {
    FileSystemHelper.run(Map[String, String]("pkg/MyFlow.flow" -> "")) { root: PathLike =>
      val issuesAndWS = Workspace(root)
      assert(issuesAndWS.issues.isEmpty)
      assert(issuesAndWS.value.nonEmpty)
      issuesAndWS.value.get.events.toList should matchPattern {
        case List(FlowEvent("/pkg/MyFlow.flow")) =>
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
        case List(ComponentEvent("/pkg/MyComponent.component", Array(Name("test")))) =>
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

}
