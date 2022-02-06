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

import com.nawforce.pkgforce.diagnostics.{Diagnostic, ERROR_CATEGORY, Issue}
import com.nawforce.pkgforce.parsers.ApexNode
import com.nawforce.pkgforce.path.Location
import com.nawforce.runtime.parsers.{CodeParser, SourceData}
import com.nawforce.runtime.platform.Path
import org.scalatest.funsuite.AnyFunSuite

import scala.collection.compat.immutable.ArraySeq

class MethodModifierTest extends AnyFunSuite {

  def legalInterfaceMethodAccess(use: ArraySeq[Modifier], expected: ArraySeq[Modifier]): Boolean = {
    val modifiers = use.map(_.name).mkString(" ")
    val path      = Path("Dummy.cls")
    val cp        = CodeParser(path, SourceData(s"public interface Dummy {$modifiers String func();}"))
    val result    = cp.parseClass()
    if (result.issues.nonEmpty) {
      false
    } else {
      val root  = ApexNode(cp, result.value).get
      val field = root.children.head
      field.parseIssues.isEmpty &&
      (field.modifiers == expected)
    }
  }

  def legalInterfaceMethodAccess(use: ArraySeq[Modifier]): Boolean = {
    legalInterfaceMethodAccess(use, use)
  }

  def illegalInterfaceMethodAccess(use: Array[Modifier]): ArraySeq[Issue] = {
    val modifiers = use.map(_.name).mkString(" ")
    val path      = Path("Dummy.cls")
    val cp        = CodeParser(path, SourceData(s"public interface Dummy {$modifiers String func();}"))
    val result    = cp.parseClass()
    if (result.issues.nonEmpty) {
      ArraySeq()
    } else {
      val root  = ApexNode(cp, result.value).get
      val field = root.children.head
      field.parseIssues
    }
  }

  test("Interface method no modifiers is virtual, public") {
    assert(legalInterfaceMethodAccess(ArraySeq(), ArraySeq(VIRTUAL_MODIFIER, PUBLIC_MODIFIER)))
  }

  test("Interface method public modifier") {
    val issues = illegalInterfaceMethodAccess(Array(PUBLIC_MODIFIER))
    assert(
      issues == Seq[Issue](
        Issue(
          Path("Dummy.cls"),
          Diagnostic(
            ERROR_CATEGORY,
            Location(1, 24, 1, 30),
            "Modifier 'public' is not supported on interface methods"
          )
        )
      )
    )
  }

  test("Interface method virtual modifier") {
    val issues = illegalInterfaceMethodAccess(Array(VIRTUAL_MODIFIER))
    assert(
      issues == Seq[Issue](
        Issue(
          Path("Dummy.cls"),
          Diagnostic(
            ERROR_CATEGORY,
            Location(1, 24, 1, 31),
            "Modifier 'virtual' is not supported on interface methods"
          )
        )
      )
    )
  }

  test("Interface method isTest annotation") {
    val issues = illegalInterfaceMethodAccess(Array(ISTEST_ANNOTATION))
    assert(
      issues == Seq[Issue](
        Issue(
          Path("Dummy.cls"),
          Diagnostic(
            ERROR_CATEGORY,
            Location(1, 24, 1, 31),
            "Annotation '@IsTest' is not supported on interface methods"
          )
        )
      )
    )
  }
}
