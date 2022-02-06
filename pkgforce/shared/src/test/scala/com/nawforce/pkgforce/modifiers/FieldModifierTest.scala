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
package com.nawforce.pkgforce.modifiers

import com.nawforce.pkgforce.diagnostics
import com.nawforce.pkgforce.diagnostics.{Diagnostic, ERROR_CATEGORY, Issue}
import com.nawforce.pkgforce.parsers.ApexNode
import com.nawforce.pkgforce.path.Location
import com.nawforce.runtime.parsers.{CodeParser, SourceData}
import com.nawforce.runtime.platform.Path
import org.scalatest.funsuite.AnyFunSuite

import scala.collection.compat.immutable.ArraySeq

class FieldModifierTest extends AnyFunSuite {

  def legalFieldAccess(use: ArraySeq[Modifier], expected: ArraySeq[Modifier]): Boolean = {
    val modifiers = use.map(_.name).mkString(" ")
    val path      = Path("Dummy.cls")
    val cp        = CodeParser(path, SourceData(s"public class Dummy {$modifiers String foo;}"))
    val result    = cp.parseClass()
    if (result.issues.nonEmpty) {
      false
    } else {
      val root  = ApexNode(cp, result.value).get
      val field = root.children.head
      field.parseIssues.isEmpty && field.modifiers == expected
    }
  }

  def legalFieldAccess(use: ArraySeq[Modifier]): Boolean = {
    legalFieldAccess(use, use)
  }

  def illegalFieldAccess(use: ArraySeq[Modifier]): ArraySeq[Issue] = {
    val modifiers = use.map(_.name).mkString(" ")
    val path      = Path("Dummy.cls")
    val cp        = CodeParser(path, SourceData(s"public class Dummy {$modifiers String foo;}"))
    val result    = cp.parseClass()
    if (result.issues.nonEmpty) {
      ArraySeq()
    } else {
      val root  = ApexNode(cp, result.value).get
      val field = root.children.head
      field.parseIssues
    }
  }

  test("Default field access private") {
    assert(legalFieldAccess(ArraySeq(), ArraySeq(PRIVATE_MODIFIER)))
  }

  test("Private field") {
    assert(legalFieldAccess(ArraySeq(PRIVATE_MODIFIER)))
  }

  test("Protected field") {
    assert(legalFieldAccess(ArraySeq(PROTECTED_MODIFIER)))
  }

  test("Public field") {
    assert(legalFieldAccess(ArraySeq(PUBLIC_MODIFIER)))
  }

  test("Global field") {
    assert(legalFieldAccess(ArraySeq(GLOBAL_MODIFIER)))
  }

  test("Webservice field") {
    assert(
      legalFieldAccess(
        ArraySeq(WEBSERVICE_MODIFIER),
        ArraySeq(GLOBAL_MODIFIER, WEBSERVICE_MODIFIER)
      )
    )
  }

  test("Webservice non-global field") {
    assert(!legalFieldAccess(ArraySeq(PUBLIC_MODIFIER, WEBSERVICE_MODIFIER)))
  }

  test("Transient field") {
    assert(
      legalFieldAccess(ArraySeq(TRANSIENT_MODIFIER), ArraySeq(PRIVATE_MODIFIER, TRANSIENT_MODIFIER))
    )
  }

  test("Transient public field") {
    assert(
      legalFieldAccess(
        ArraySeq(PUBLIC_MODIFIER, TRANSIENT_MODIFIER),
        ArraySeq(PUBLIC_MODIFIER, TRANSIENT_MODIFIER)
      )
    )
  }

  test("Static field") {
    assert(legalFieldAccess(ArraySeq(STATIC_MODIFIER), ArraySeq(PRIVATE_MODIFIER, STATIC_MODIFIER)))
  }

  test("Static public field") {
    assert(
      legalFieldAccess(
        ArraySeq(PUBLIC_MODIFIER, STATIC_MODIFIER),
        ArraySeq(PUBLIC_MODIFIER, STATIC_MODIFIER)
      )
    )
  }

  test("Final field") {
    assert(legalFieldAccess(ArraySeq(FINAL_MODIFIER), ArraySeq(PRIVATE_MODIFIER, FINAL_MODIFIER)))
  }

  test("Final public field") {
    assert(
      legalFieldAccess(
        ArraySeq(PUBLIC_MODIFIER, FINAL_MODIFIER),
        ArraySeq(PUBLIC_MODIFIER, FINAL_MODIFIER)
      )
    )
  }

  test("Mixed field") {
    assert(
      legalFieldAccess(
        ArraySeq(PUBLIC_MODIFIER, STATIC_MODIFIER, TRANSIENT_MODIFIER, FINAL_MODIFIER)
      )
    )
  }

  test("AuraEnabled field") {
    assert(
      legalFieldAccess(
        ArraySeq(AURA_ENABLED_ANNOTATION),
        ArraySeq(PRIVATE_MODIFIER, AURA_ENABLED_ANNOTATION)
      )
    )
  }

  test("Deprecated field") {
    assert(
      legalFieldAccess(
        ArraySeq(DEPRECATED_ANNOTATION),
        ArraySeq(PRIVATE_MODIFIER, DEPRECATED_ANNOTATION)
      )
    )
  }

  test("InvocableVariable field") {
    assert(
      legalFieldAccess(
        ArraySeq(INVOCABLE_VARIABLE_ANNOTATION),
        ArraySeq(PRIVATE_MODIFIER, INVOCABLE_VARIABLE_ANNOTATION)
      )
    )
  }

  test("TestVisible field") {
    assert(
      legalFieldAccess(
        ArraySeq(TEST_VISIBLE_ANNOTATION),
        ArraySeq(PRIVATE_MODIFIER, TEST_VISIBLE_ANNOTATION)
      )
    )
  }

  test("SuppressWarnings PMD field") {
    assert(
      legalFieldAccess(
        ArraySeq(SUPPRESS_WARNINGS_ANNOTATION_PMD),
        ArraySeq(PRIVATE_MODIFIER, SUPPRESS_WARNINGS_ANNOTATION_PMD)
      )
    )
  }

  test("SuppressWarnings UNUSED field") {
    assert(
      legalFieldAccess(
        ArraySeq(SUPPRESS_WARNINGS_ANNOTATION_UNUSED),
        ArraySeq(PRIVATE_MODIFIER, SUPPRESS_WARNINGS_ANNOTATION_UNUSED)
      )
    )
  }

  test("Bad modifier field") {
    val issues = illegalFieldAccess(ArraySeq(OVERRIDE_MODIFIER))
    assert(
      issues == Seq[Issue](
        Issue(
          Path("Dummy.cls"),
          Diagnostic(
            ERROR_CATEGORY,
            Location(1, 20, 1, 28),
            "Modifier 'override' is not supported on fields"
          )
        )
      )
    )
  }

  test("Bad annotation field") {
    val issues = illegalFieldAccess(ArraySeq(TEST_SETUP_ANNOTATION))
    assert(
      issues == Seq[Issue](
        Issue(
          Path("Dummy.cls"),
          diagnostics.Diagnostic(
            ERROR_CATEGORY,
            Location(1, 20, 1, 30),
            "Annotation '@TestSetup' is not supported on fields"
          )
        )
      )
    )
  }

  test("Duplicate modifier field") {
    val issues = illegalFieldAccess(ArraySeq(PROTECTED_MODIFIER, PROTECTED_MODIFIER))
    assert(
      issues == Seq[Issue](
        Issue(
          Path("Dummy.cls"),
          diagnostics.Diagnostic(
            ERROR_CATEGORY,
            Location(1, 47, 1, 50),
            "Modifier 'protected' is used more than once"
          )
        )
      )
    )
  }

  def innerLegalFieldAccess(use: ArraySeq[Modifier], expected: ArraySeq[Modifier]): Boolean = {
    val modifiers = use.map(_.name).mkString(" ")
    val path      = Path("Dummy.cls")
    val cp = CodeParser(
      path,
      SourceData(s"public class Dummy {public class Bar {$modifiers String foo;} }")
    )
    val result = cp.parseClass()
    if (result.issues.nonEmpty) {
      false
    } else {
      val root  = ApexNode(cp, result.value).get
      val inner = root.children.head
      val field = inner.children.head
      field.parseIssues.isEmpty && field.modifiers == expected
    }
  }

  def innerLegalFieldAccess(use: ArraySeq[Modifier]): Boolean = {
    innerLegalFieldAccess(use, use)
  }

  def innerIllegalFieldAccess(use: ArraySeq[Modifier]): ArraySeq[Issue] = {
    val modifiers = use.map(_.name).mkString(" ")
    val path      = Path("Dummy.cls")
    val cp = CodeParser(
      path,
      SourceData(s"public class Dummy {public class Bar {$modifiers String foo;} }")
    )
    val result = cp.parseClass()
    if (result.issues.nonEmpty) {
      ArraySeq()
    } else {
      val root  = ApexNode(cp, result.value).get
      val inner = root.children.head
      val field = inner.children.head
      field.parseIssues
    }
  }

  test("Inner Default field access private") {
    assert(innerLegalFieldAccess(ArraySeq(), ArraySeq(PRIVATE_MODIFIER)))
  }

  test("Inner Private field") {
    assert(innerLegalFieldAccess(ArraySeq(PRIVATE_MODIFIER)))
  }

  test("Inner Protected field") {
    assert(innerLegalFieldAccess(ArraySeq(PROTECTED_MODIFIER)))
  }

  test("Inner Public field") {
    assert(innerLegalFieldAccess(ArraySeq(PUBLIC_MODIFIER)))
  }

  test("Inner Global field") {
    assert(innerLegalFieldAccess(ArraySeq(GLOBAL_MODIFIER)))
  }

  test("Inner Webservice field") {
    assert(
      innerLegalFieldAccess(
        ArraySeq(WEBSERVICE_MODIFIER),
        ArraySeq(GLOBAL_MODIFIER, WEBSERVICE_MODIFIER)
      )
    )
  }

  test("Inner Webservice non-global field") {
    assert(!innerLegalFieldAccess(ArraySeq(PUBLIC_MODIFIER, WEBSERVICE_MODIFIER)))
  }

  test("Inner Transient field") {
    assert(
      innerLegalFieldAccess(
        ArraySeq(TRANSIENT_MODIFIER),
        ArraySeq(PRIVATE_MODIFIER, TRANSIENT_MODIFIER)
      )
    )
  }

  test("Inner Transient public field") {
    assert(
      innerLegalFieldAccess(
        ArraySeq(PUBLIC_MODIFIER, TRANSIENT_MODIFIER),
        ArraySeq(PUBLIC_MODIFIER, TRANSIENT_MODIFIER)
      )
    )
  }

  test("Inner Static field") {
    val issues = innerIllegalFieldAccess(ArraySeq(STATIC_MODIFIER))
    assert(
      issues == Seq[Issue](
        Issue(
          Path("Dummy.cls"),
          diagnostics.Diagnostic(
            ERROR_CATEGORY,
            Location(1, 38, 1, 44),
            "Modifier 'static' is not supported on inner class fields"
          )
        )
      )
    )

  }

  test("Inner Final field") {
    assert(
      innerLegalFieldAccess(ArraySeq(FINAL_MODIFIER), ArraySeq(PRIVATE_MODIFIER, FINAL_MODIFIER))
    )
  }

  test("Inner Final public field") {
    assert(
      innerLegalFieldAccess(
        ArraySeq(PUBLIC_MODIFIER, FINAL_MODIFIER),
        ArraySeq(PUBLIC_MODIFIER, FINAL_MODIFIER)
      )
    )
  }

  test("Inner Mixed field") {
    assert(innerLegalFieldAccess(ArraySeq(PUBLIC_MODIFIER, TRANSIENT_MODIFIER, FINAL_MODIFIER)))
  }

  test("Inner AuraEnabled field") {
    assert(
      innerLegalFieldAccess(
        ArraySeq(AURA_ENABLED_ANNOTATION),
        ArraySeq(PRIVATE_MODIFIER, AURA_ENABLED_ANNOTATION)
      )
    )
  }

  test("Inner Deprecated field") {
    assert(
      innerLegalFieldAccess(
        ArraySeq(DEPRECATED_ANNOTATION),
        ArraySeq(PRIVATE_MODIFIER, DEPRECATED_ANNOTATION)
      )
    )
  }

  test("Inner InvocableVariable field") {
    assert(
      innerLegalFieldAccess(
        ArraySeq(INVOCABLE_VARIABLE_ANNOTATION),
        ArraySeq(PRIVATE_MODIFIER, INVOCABLE_VARIABLE_ANNOTATION)
      )
    )
  }

  test("Inner TestVisible field") {
    assert(
      innerLegalFieldAccess(
        ArraySeq(TEST_VISIBLE_ANNOTATION),
        ArraySeq(PRIVATE_MODIFIER, TEST_VISIBLE_ANNOTATION)
      )
    )
  }

  test("Inner SuppressWarnings PMD field") {
    assert(
      innerLegalFieldAccess(
        ArraySeq(SUPPRESS_WARNINGS_ANNOTATION_PMD),
        ArraySeq(PRIVATE_MODIFIER, SUPPRESS_WARNINGS_ANNOTATION_PMD)
      )
    )
  }

  test("Inner SuppressWarnings Unused field") {
    assert(
      innerLegalFieldAccess(
        ArraySeq(SUPPRESS_WARNINGS_ANNOTATION_UNUSED),
        ArraySeq(PRIVATE_MODIFIER, SUPPRESS_WARNINGS_ANNOTATION_UNUSED)
      )
    )
  }

  test("Inner Bad modifier field") {
    val issues = innerIllegalFieldAccess(ArraySeq(OVERRIDE_MODIFIER))
    assert(
      issues == Seq[Issue](
        Issue(
          Path("Dummy.cls"),
          diagnostics.Diagnostic(
            ERROR_CATEGORY,
            Location(1, 38, 1, 46),
            "Modifier 'override' is not supported on inner class fields"
          )
        )
      )
    )
  }

  test("Inner Bad annotation field") {
    val issues = innerIllegalFieldAccess(ArraySeq(TEST_SETUP_ANNOTATION))
    assert(
      issues == Seq[Issue](
        Issue(
          Path("Dummy.cls"),
          diagnostics.Diagnostic(
            ERROR_CATEGORY,
            Location(1, 38, 1, 48),
            "Annotation '@TestSetup' is not supported on inner class fields"
          )
        )
      )
    )
  }

  test("Inner Duplicate modifier field") {
    val issues = innerIllegalFieldAccess(ArraySeq(PROTECTED_MODIFIER, PROTECTED_MODIFIER))
    assert(
      issues == Seq[Issue](
        Issue(
          Path("Dummy.cls"),
          diagnostics.Diagnostic(
            ERROR_CATEGORY,
            Location(1, 65, 1, 68),
            "Modifier 'protected' is used more than once"
          )
        )
      )
    )
  }

}
