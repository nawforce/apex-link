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
package com.nawforce.common.modifiers

import com.nawforce.common.api.{Diagnostic, ERROR_CATEGORY, Location}
import com.nawforce.common.diagnostics.Issue
import com.nawforce.common.parsers.ApexNode
import com.nawforce.common.path.PathFactory
import com.nawforce.runtime.parsers.{CodeParser, SourceData}
import org.scalatest.funsuite.AnyFunSuite

class FieldModifierTest extends AnyFunSuite {

  def legalFieldAccess(use: Array[Modifier], expected: Array[Modifier]): Boolean = {
    val modifiers = use.map(_.name).mkString(" ")
    val path = PathFactory("Dummy.cls")
    val cp = CodeParser(path, SourceData(s"public class Dummy {$modifiers String foo;}"))
    cp.parseClass() match {
      case Left(_) => false
      case Right(cu) =>
        val root = ApexNode(cp, cu)
        val field = root.children.head
        field.modifiers.issues.isEmpty &&
        (field.modifiers.modifiers sameElements expected)
    }
  }

  def legalFieldAccess(use: Array[Modifier]): Boolean = {
    legalFieldAccess(use, use)
  }

  def illegalFieldAccess(use: Array[Modifier]): Array[Issue] = {
    val modifiers = use.map(_.name).mkString(" ")
    val path = PathFactory("Dummy.cls")
    val cp = CodeParser(path, SourceData(s"public class Dummy {$modifiers String foo;}"))
    cp.parseClass() match {
      case Left(_) => Array()
      case Right(cu) =>
        val root = ApexNode(cp, cu)
        val field = root.children.head
        field.modifiers.issues
    }
  }

  test("Default field access private") {
    assert(legalFieldAccess(Array(), Array(PRIVATE_MODIFIER)))
  }

  test("Private field") {
    assert(legalFieldAccess(Array(PRIVATE_MODIFIER)))
  }

  test("Protected field") {
    assert(legalFieldAccess(Array(PROTECTED_MODIFIER)))
  }

  test("Public field") {
    assert(legalFieldAccess(Array(PUBLIC_MODIFIER)))
  }

  test("Global field") {
    assert(legalFieldAccess(Array(GLOBAL_MODIFIER)))
  }

  test("Webservice field") {
    assert(
      legalFieldAccess(Array(WEBSERVICE_MODIFIER), Array(GLOBAL_MODIFIER, WEBSERVICE_MODIFIER)))
  }

  test("Webservice non-global field") {
    assert(!legalFieldAccess(Array(PUBLIC_MODIFIER, WEBSERVICE_MODIFIER)))
  }

  test("Transient field") {
    assert(legalFieldAccess(Array(TRANSIENT_MODIFIER), Array(PRIVATE_MODIFIER, TRANSIENT_MODIFIER)))
  }

  test("Transient public field") {
    assert(
      legalFieldAccess(Array(PUBLIC_MODIFIER, TRANSIENT_MODIFIER),
                       Array(PUBLIC_MODIFIER, TRANSIENT_MODIFIER)))
  }

  test("Static field") {
    assert(legalFieldAccess(Array(STATIC_MODIFIER), Array(PRIVATE_MODIFIER, STATIC_MODIFIER)))
  }

  test("Static public field") {
    assert(
      legalFieldAccess(Array(PUBLIC_MODIFIER, STATIC_MODIFIER),
                       Array(PUBLIC_MODIFIER, STATIC_MODIFIER)))
  }

  test("Final field") {
    assert(legalFieldAccess(Array(FINAL_MODIFIER), Array(PRIVATE_MODIFIER, FINAL_MODIFIER)))
  }

  test("Final public field") {
    assert(
      legalFieldAccess(Array(PUBLIC_MODIFIER, FINAL_MODIFIER),
                       Array(PUBLIC_MODIFIER, FINAL_MODIFIER)))
  }

  test("Mixed field") {
    assert(
      legalFieldAccess(Array(PUBLIC_MODIFIER, STATIC_MODIFIER, TRANSIENT_MODIFIER, FINAL_MODIFIER)))
  }

  test("AuraEnabled field") {
    assert(
      legalFieldAccess(Array(AURA_ENABLED_ANNOTATION),
                       Array(PRIVATE_MODIFIER, AURA_ENABLED_ANNOTATION)))
  }

  test("Deprecated field") {
    assert(
      legalFieldAccess(Array(DEPRECATED_ANNOTATION),
                       Array(PRIVATE_MODIFIER, DEPRECATED_ANNOTATION)))
  }

  test("InvocableVariable field") {
    assert(
      legalFieldAccess(Array(INVOCABLE_VARIABLE_ANNOTATION),
                       Array(PRIVATE_MODIFIER, INVOCABLE_VARIABLE_ANNOTATION)))
  }

  test("TestVisible field") {
    assert(
      legalFieldAccess(Array(TEST_VISIBLE_ANNOTATION),
        Array(PRIVATE_MODIFIER, TEST_VISIBLE_ANNOTATION)))
  }

  test("SuppressWarnings field") {
    assert(
      legalFieldAccess(Array(SUPPRESS_WARNINGS_ANNOTATION),
        Array(PRIVATE_MODIFIER, SUPPRESS_WARNINGS_ANNOTATION)))
  }

  test("Bad modifier field") {
    val issues = illegalFieldAccess(Array(OVERRIDE_MODIFIER)).toSeq
    assert(
      issues == Seq[Issue](
        Issue(PathFactory("Dummy.cls").toString,
          Diagnostic(ERROR_CATEGORY,
            Location(1, 20, 1, 28),
            "Modifier 'override' is not supported on fields"))))
  }

  test("Bad annotation field") {
    val issues = illegalFieldAccess(Array(TEST_SETUP_ANNOTATION)).toSeq
    assert(
      issues == Seq[Issue](
        Issue(PathFactory("Dummy.cls").toString,
          Diagnostic(ERROR_CATEGORY,
            Location(1, 20, 1, 30),
            "Annotation '@TestSetup' is not supported on fields"))))
  }

  test("Duplicate modifier field") {
    val issues = illegalFieldAccess(Array(PROTECTED_MODIFIER, PROTECTED_MODIFIER)).toSeq
    assert(
      issues == Seq[Issue](
        Issue(PathFactory("Dummy.cls").toString,
              Diagnostic(ERROR_CATEGORY,
                         Location(1, 47, 1, 50),
                         "Modifier 'protected' is used more than once"))))
  }

  def innerLegalFieldAccess(use: Array[Modifier], expected: Array[Modifier]): Boolean = {
    val modifiers = use.map(_.name).mkString(" ")
    val path = PathFactory("Dummy.cls")
    val cp = CodeParser(path, SourceData(s"public class Dummy {public class Bar {$modifiers String foo;} }"))
    cp.parseClass() match {
      case Left(_) => false
      case Right(cu) =>
        val root = ApexNode(cp, cu)
        val inner = root.children.head
        val field = inner.children.head
        field.modifiers.issues.isEmpty &&
          (field.modifiers.modifiers sameElements expected)
    }
  }

  def innerLegalFieldAccess(use: Array[Modifier]): Boolean = {
    innerLegalFieldAccess(use, use)
  }

  def innerIllegalFieldAccess(use: Array[Modifier]): Array[Issue] = {
    val modifiers = use.map(_.name).mkString(" ")
    val path = PathFactory("Dummy.cls")
    val cp = CodeParser(path, SourceData(s"public class Dummy {public class Bar {$modifiers String foo;} }"))
    cp.parseClass() match {
      case Left(_) => Array()
      case Right(cu) =>
        val root = ApexNode(cp, cu)
        val inner = root.children.head
        val field = inner.children.head
        field.modifiers.issues
    }
  }


  test("Inner Default field access private") {
    assert(innerLegalFieldAccess(Array(), Array(PRIVATE_MODIFIER)))
  }

  test("Inner Private field") {
    assert(innerLegalFieldAccess(Array(PRIVATE_MODIFIER)))
  }

  test("Inner Protected field") {
    assert(innerLegalFieldAccess(Array(PROTECTED_MODIFIER)))
  }

  test("Inner Public field") {
    assert(innerLegalFieldAccess(Array(PUBLIC_MODIFIER)))
  }

  test("Inner Global field") {
    assert(innerLegalFieldAccess(Array(GLOBAL_MODIFIER)))
  }

  test("Inner Webservice field") {
    assert(
      innerLegalFieldAccess(Array(WEBSERVICE_MODIFIER), Array(GLOBAL_MODIFIER, WEBSERVICE_MODIFIER)))
  }

  test("Inner Webservice non-global field") {
    assert(!innerLegalFieldAccess(Array(PUBLIC_MODIFIER, WEBSERVICE_MODIFIER)))
  }

  test("Inner Transient field") {
    assert(innerLegalFieldAccess(Array(TRANSIENT_MODIFIER), Array(PRIVATE_MODIFIER, TRANSIENT_MODIFIER)))
  }

  test("Inner Transient public field") {
    assert(
      innerLegalFieldAccess(Array(PUBLIC_MODIFIER, TRANSIENT_MODIFIER),
        Array(PUBLIC_MODIFIER, TRANSIENT_MODIFIER)))
  }

  test("Inner Static field") {
    val issues = innerIllegalFieldAccess(Array(STATIC_MODIFIER)).toSeq
    assert(
      issues == Seq[Issue](
        Issue(PathFactory("Dummy.cls").toString,
          Diagnostic(ERROR_CATEGORY,
            Location(1, 38, 1, 44),
            "Modifier 'static' is not supported on inner class fields"))))

  }

  test("Inner Final field") {
    assert(innerLegalFieldAccess(Array(FINAL_MODIFIER), Array(PRIVATE_MODIFIER, FINAL_MODIFIER)))
  }

  test("Inner Final public field") {
    assert(
      innerLegalFieldAccess(Array(PUBLIC_MODIFIER, FINAL_MODIFIER),
        Array(PUBLIC_MODIFIER, FINAL_MODIFIER)))
  }

  test("Inner Mixed field") {
    assert(
      innerLegalFieldAccess(Array(PUBLIC_MODIFIER, TRANSIENT_MODIFIER, FINAL_MODIFIER)))
  }

  test("Inner AuraEnabled field") {
    assert(
      innerLegalFieldAccess(Array(AURA_ENABLED_ANNOTATION),
        Array(PRIVATE_MODIFIER, AURA_ENABLED_ANNOTATION)))
  }

  test("Inner Deprecated field") {
    assert(
      innerLegalFieldAccess(Array(DEPRECATED_ANNOTATION),
        Array(PRIVATE_MODIFIER, DEPRECATED_ANNOTATION)))
  }

  test("Inner InvocableVariable field") {
    assert(
      innerLegalFieldAccess(Array(INVOCABLE_VARIABLE_ANNOTATION),
        Array(PRIVATE_MODIFIER, INVOCABLE_VARIABLE_ANNOTATION)))
  }

  test("Inner TestVisible field") {
    assert(
      innerLegalFieldAccess(Array(TEST_VISIBLE_ANNOTATION),
        Array(PRIVATE_MODIFIER, TEST_VISIBLE_ANNOTATION)))
  }

  test("Inner SuppressWarnings field") {
    assert(
      innerLegalFieldAccess(Array(SUPPRESS_WARNINGS_ANNOTATION),
        Array(PRIVATE_MODIFIER, SUPPRESS_WARNINGS_ANNOTATION)))
  }

  test("Inner Bad modifier field") {
    val issues = innerIllegalFieldAccess(Array(OVERRIDE_MODIFIER)).toSeq
    assert(
      issues == Seq[Issue](
        Issue(PathFactory("Dummy.cls").toString,
          Diagnostic(ERROR_CATEGORY,
            Location(1, 38, 1, 46),
            "Modifier 'override' is not supported on inner class fields"))))
  }

  test("Inner Bad annotation field") {
    val issues = innerIllegalFieldAccess(Array(TEST_SETUP_ANNOTATION)).toSeq
    assert(
      issues == Seq[Issue](
        Issue(PathFactory("Dummy.cls").toString,
          Diagnostic(ERROR_CATEGORY,
            Location(1, 38, 1, 48),
            "Annotation '@TestSetup' is not supported on inner class fields"))))
  }

  test("Inner Duplicate modifier field") {
    val issues = innerIllegalFieldAccess(Array(PROTECTED_MODIFIER, PROTECTED_MODIFIER)).toSeq
    assert(
      issues == Seq[Issue](
        Issue(PathFactory("Dummy.cls").toString,
          Diagnostic(ERROR_CATEGORY,
            Location(1, 65, 1, 68),
            "Modifier 'protected' is used more than once"))))
  }

}
