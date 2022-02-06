/*
 Copyright (c) 2021 Kevin Jones, All rights reserved.
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

class ClassModifierTest extends AnyFunSuite {

  def legalClassAccess(use: ArraySeq[Modifier], expected: ArraySeq[Modifier]): Boolean = {
    val modifiers = use.map(_.name).mkString(" ")
    val path      = Path("Dummy.cls")
    val cp        = CodeParser(path, SourceData(s"$modifiers class Dummy {}"))
    val result    = cp.parseClass()
    if (result.issues.nonEmpty) {
      false
    } else {
      val root = ApexNode(cp, result.value).get
      root.parseIssues.isEmpty && root.modifiers == expected
    }
  }

  def legalClassAccess(use: ArraySeq[Modifier]): Boolean = {
    legalClassAccess(use, use)
  }

  def illegalClassAccess(use: ArraySeq[Modifier]): ArraySeq[Issue] = {
    val modifiers = use.map(_.name).mkString(" ")
    val path      = Path("Dummy.cls")
    val cp        = CodeParser(path, SourceData(s"$modifiers class Dummy {}"))
    val result    = cp.parseClass()
    if (result.issues.nonEmpty) {
      ArraySeq()
    } else {
      val root = ApexNode(cp, result.value).get
      root.parseIssues
    }
  }

  test("No modifiers") {
    val issues = illegalClassAccess(ArraySeq())
    assert(
      issues == Seq[Issue](
        Issue(
          Path("Dummy.cls"),
          Diagnostic(
            ERROR_CATEGORY,
            Location(1, 7, 1, 12),
            "Outer classes must be declared either 'global' or 'public'"
          )
        )
      )
    )
  }

  test("Private modifier") {
    val issues = illegalClassAccess(ArraySeq(PRIVATE_MODIFIER))
    assert(
      issues == Seq[Issue](
        Issue(
          Path("Dummy.cls"),
          diagnostics.Diagnostic(
            ERROR_CATEGORY,
            Location(1, 14, 1, 19),
            "Private modifier is not allowed on outer classes"
          )
        )
      )
    )
  }

  test("Protected modifier") {
    val issues = illegalClassAccess(ArraySeq(PROTECTED_MODIFIER))
    assert(
      issues == Seq[Issue](
        Issue(
          Path("Dummy.cls"),
          diagnostics.Diagnostic(
            ERROR_CATEGORY,
            Location(1, 0, 1, 9),
            "Modifier 'protected' is not supported on classes"
          )
        )
      )
    )
  }

  test("Private modifier (isTest)") {
    assert(legalClassAccess(ArraySeq(ISTEST_ANNOTATION, PRIVATE_MODIFIER)))
  }

  test("isTest modifier") {
    assert(legalClassAccess(ArraySeq(ISTEST_ANNOTATION)))
  }

  test("Abstract modifier") {
    assert(legalClassAccess(ArraySeq(ABSTRACT_MODIFIER, PUBLIC_MODIFIER)))
  }

  test("Virtual modifier") {
    assert(legalClassAccess(ArraySeq(VIRTUAL_MODIFIER, PUBLIC_MODIFIER)))
  }

  test("Json Access annotation") {
    assert(legalClassAccess(ArraySeq(JSON_ACCESS_ANNOTATION, PUBLIC_MODIFIER)))
  }

  test("Abstract & virtual modifier") {
    val issues =
      illegalClassAccess(ArraySeq(ABSTRACT_MODIFIER, VIRTUAL_MODIFIER, PUBLIC_MODIFIER))
    assert(
      issues == Seq[Issue](
        Issue(
          Path("Dummy.cls"),
          diagnostics.Diagnostic(
            ERROR_CATEGORY,
            Location(1, 30, 1, 35),
            "Abstract classes are virtual classes"
          )
        )
      )
    )
  }

  def innerLegalClassAccess(use: ArraySeq[Modifier], expected: ArraySeq[Modifier]): Boolean = {
    val modifiers = use.map(_.name).mkString(" ")
    val path      = Path("Dummy.cls")
    val cp        = CodeParser(path, SourceData(s"public class Dummy {$modifiers class Bar {} }"))
    val result    = cp.parseClass()
    if (result.issues.nonEmpty) {
      false
    } else {
      val root  = ApexNode(cp, result.value).get
      val inner = root.children.head
      inner.parseIssues.isEmpty && inner.modifiers == expected
    }
  }

  def innerLegalClassAccess(use: ArraySeq[Modifier]): Boolean = {
    innerLegalClassAccess(use, use)
  }

  def innerIllegalClassAccess(use: ArraySeq[Modifier]): ArraySeq[Issue] = {
    val modifiers = use.map(_.name).mkString(" ")
    val path      = Path("Dummy.cls")
    val cp        = CodeParser(path, SourceData(s"public class Dummy {$modifiers class Bar {} }"))
    val result    = cp.parseClass()
    if (result.issues.nonEmpty) {
      ArraySeq()
    } else {
      val root  = ApexNode(cp, result.value).get
      val inner = root.children.head
      inner.parseIssues
    }
  }

  test("Inner No modifiers") {
    assert(innerLegalClassAccess(ArraySeq()))
  }

  test("Inner Private modifier") {
    assert(innerLegalClassAccess(ArraySeq(PRIVATE_MODIFIER)))
  }

  test("Inner IsTest modifier") {
    val issues = innerIllegalClassAccess(ArraySeq(ISTEST_ANNOTATION))
    assert(
      issues == Seq[Issue](
        Issue(
          Path("Dummy.cls"),
          diagnostics.Diagnostic(
            ERROR_CATEGORY,
            Location(1, 34, 1, 37),
            "isTest can only be used on outer classes"
          )
        )
      )
    )

  }

  test("Inner Abstract modifier") {
    assert(innerLegalClassAccess(ArraySeq(ABSTRACT_MODIFIER)))
  }

  test("Inner Virtual modifier") {
    assert(innerLegalClassAccess(ArraySeq(VIRTUAL_MODIFIER)))
  }

  test("Inner Json Access annotation") {
    assert(innerLegalClassAccess(ArraySeq(JSON_ACCESS_ANNOTATION)))
  }

  test("Inner Abstract & virtual modifier") {
    val issues =
      innerIllegalClassAccess(ArraySeq(ABSTRACT_MODIFIER, VIRTUAL_MODIFIER))
    assert(
      issues == Seq[Issue](
        Issue(
          Path("Dummy.cls"),
          diagnostics.Diagnostic(
            ERROR_CATEGORY,
            Location(1, 43, 1, 46),
            "Abstract classes are virtual classes"
          )
        )
      )
    )
  }
}
