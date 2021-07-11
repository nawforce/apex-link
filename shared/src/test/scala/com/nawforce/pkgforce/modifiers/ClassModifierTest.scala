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
package com.nawforce.pkgforce.modifiers

import com.nawforce.pkgforce.diagnostics
import com.nawforce.pkgforce.diagnostics.{Diagnostic, ERROR_CATEGORY, Issue, Location}
import com.nawforce.pkgforce.parsers.ApexNode
import com.nawforce.pkgforce.path.PathFactory
import com.nawforce.runtime.parsers.{CodeParser, SourceData}
import org.scalatest.funsuite.AnyFunSuite

class ClassModifierTest extends AnyFunSuite {

  def legalClassAccess(use: Array[Modifier], expected: Array[Modifier]): Boolean = {
    val modifiers = use.map(_.name).mkString(" ")
    val path = PathFactory("Dummy.cls")
    val cp = CodeParser(path, SourceData(s"$modifiers class Dummy {}"))
    val result = cp.parseClass()
    if (result.issues.nonEmpty) {
      false
    } else {
      val root = ApexNode(cp, result.value)
      root.modifiers.issues.isEmpty &&
      (root.modifiers.modifiers sameElements expected)
    }
  }

  def legalClassAccess(use: Array[Modifier]): Boolean = {
    legalClassAccess(use, use)
  }

  def illegalClassAccess(use: Array[Modifier]): Array[Issue] = {
    val modifiers = use.map(_.name).mkString(" ")
    val path = PathFactory("Dummy.cls")
    val cp = CodeParser(path, SourceData(s"$modifiers class Dummy {}"))
    val result = cp.parseClass()
    if (result.issues.nonEmpty) {
      Array()
    } else {
      val root = ApexNode(cp, result.value)
      root.modifiers.issues
    }
  }

  test("No modifiers") {
    val issues = illegalClassAccess(Array()).toSeq
    assert(
      issues == Seq[Issue](
        Issue(PathFactory("Dummy.cls").toString,
              Diagnostic(ERROR_CATEGORY,
                         Location(1, 7, 1, 12),
                         "Outer classes must be declared either 'global' or 'public'"))))
  }

  test("Private modifier") {
    val issues = illegalClassAccess(Array(PRIVATE_MODIFIER)).toSeq
    assert(
      issues == Seq[Issue](
        Issue(PathFactory("Dummy.cls").toString,
              diagnostics.Diagnostic(ERROR_CATEGORY,
                                     Location(1, 14, 1, 19),
                                     "Private modifier is not allowed on outer classes"))))
  }

  test("Protected modifier") {
    val issues = illegalClassAccess(Array(PROTECTED_MODIFIER)).toSeq
    assert(
      issues == Seq[Issue](
        Issue(PathFactory("Dummy.cls").toString,
              diagnostics.Diagnostic(ERROR_CATEGORY,
                                     Location(1, 0, 1, 9),
                                     "Modifier 'protected' is not supported on classes"))))
  }

  test("Private modifier (isTest)") {
    assert(legalClassAccess(Array(ISTEST_ANNOTATION, PRIVATE_MODIFIER)))
  }

  test("isTest modifier") {
    assert(legalClassAccess(Array(ISTEST_ANNOTATION)))
  }

  test("Abstract modifier") {
    assert(legalClassAccess(Array(ABSTRACT_MODIFIER, PUBLIC_MODIFIER)))
  }

  test("Virtual modifier") {
    assert(legalClassAccess(Array(VIRTUAL_MODIFIER, PUBLIC_MODIFIER)))
  }

  test("Abstract & virtual modifier") {
    val issues =
      illegalClassAccess(Array(ABSTRACT_MODIFIER, VIRTUAL_MODIFIER, PUBLIC_MODIFIER)).toSeq
    assert(
      issues == Seq[Issue](
        Issue(PathFactory("Dummy.cls").toString,
              diagnostics.Diagnostic(ERROR_CATEGORY,
                                     Location(1, 30, 1, 35),
                                     "Abstract classes are virtual classes"))))
  }

  def innerLegalClassAccess(use: Array[Modifier], expected: Array[Modifier]): Boolean = {
    val modifiers = use.map(_.name).mkString(" ")
    val path = PathFactory("Dummy.cls")
    val cp = CodeParser(path, SourceData(s"public class Dummy {$modifiers class Bar {} }"))
    val result = cp.parseClass()
    if (result.issues.nonEmpty) {
      false
    } else {
      val root = ApexNode(cp, result.value)
      val inner = root.children.head
      inner.modifiers.issues.isEmpty &&
      (inner.modifiers.modifiers sameElements expected)
    }
  }

  def innerLegalClassAccess(use: Array[Modifier]): Boolean = {
    innerLegalClassAccess(use, use)
  }

  def innerIllegalClassAccess(use: Array[Modifier]): Array[Issue] = {
    val modifiers = use.map(_.name).mkString(" ")
    val path = PathFactory("Dummy.cls")
    val cp = CodeParser(path, SourceData(s"public class Dummy {$modifiers class Bar {} }"))
    val result = cp.parseClass()
    if (result.issues.nonEmpty) {
      Array()
    } else {
      val root = ApexNode(cp, result.value)
      val inner = root.children.head
      inner.modifiers.issues
    }
  }

  test("Inner No modifiers") {
    assert(innerLegalClassAccess(Array()))
  }

  test("Inner Private modifier") {
    assert(innerLegalClassAccess(Array(PRIVATE_MODIFIER)))
  }

  test("Inner IsTest modifier") {
    val issues = innerIllegalClassAccess(Array(ISTEST_ANNOTATION)).toSeq
    assert(
      issues == Seq[Issue](
        Issue(PathFactory("Dummy.cls").toString,
              diagnostics.Diagnostic(ERROR_CATEGORY,
                                     Location(1, 34, 1, 37),
                                     "isTest can only be used on outer classes"))))

  }

  test("Inner Abstract modifier") {
    assert(innerLegalClassAccess(Array(ABSTRACT_MODIFIER)))
  }

  test("Inner Virtual modifier") {
    assert(innerLegalClassAccess(Array(VIRTUAL_MODIFIER)))
  }

  test("Inner Abstract & virtual modifier") {
    val issues =
      innerIllegalClassAccess(Array(ABSTRACT_MODIFIER, VIRTUAL_MODIFIER)).toSeq
    assert(
      issues == Seq[Issue](
        Issue(PathFactory("Dummy.cls").toString,
              diagnostics.Diagnostic(ERROR_CATEGORY,
                                     Location(1, 43, 1, 46),
                                     "Abstract classes are virtual classes"))))
  }
}
