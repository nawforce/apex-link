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

import com.nawforce.pkgforce.diagnostics.{Diagnostic, ERROR_CATEGORY, Issue, Location}
import com.nawforce.pkgforce.parsers.ApexNode
import com.nawforce.pkgforce.path.PathFactory
import com.nawforce.runtime.parsers.{CodeParser, SourceData}
import org.scalatest.funsuite.AnyFunSuite

class MethodModifierTest extends AnyFunSuite {

  def legalInterfaceMethodAccess(use: Array[Modifier], expected: Array[Modifier]): Boolean = {
    val modifiers = use.map(_.name).mkString(" ")
    val path = PathFactory("Dummy.cls")
    val cp = CodeParser(path, SourceData(s"public interface Dummy {$modifiers String func();}"))
    cp.parseClass() match {
      case Left(_) => false
      case Right(cu) =>
        val root = ApexNode(cp, cu)
        val field = root.children.head
        field.modifiers.issues.isEmpty &&
        (field.modifiers.modifiers sameElements expected)
    }
  }

  def legalInterfaceMethodAccess(use: Array[Modifier]): Boolean = {
    legalInterfaceMethodAccess(use, use)
  }

  def illegalInterfaceMethodAccess(use: Array[Modifier]): Array[Issue] = {
    val modifiers = use.map(_.name).mkString(" ")
    val path = PathFactory("Dummy.cls")
    val cp = CodeParser(path, SourceData(s"public interface Dummy {$modifiers String func();}"))
    cp.parseClass() match {
      case Left(_) => Array()
      case Right(cu) =>
        val root = ApexNode(cp, cu)
        val field = root.children.head
        field.modifiers.issues
    }
  }

  test("Interface method no modifiers") {
    assert(legalInterfaceMethodAccess(Array(), Array()))
  }

  test("Interface method public modifier") {
    val issues = illegalInterfaceMethodAccess(Array(PUBLIC_MODIFIER)).toSeq
    assert(
      issues == Seq[Issue](
        Issue(PathFactory("Dummy.cls").toString,
          Diagnostic(ERROR_CATEGORY,
            Location(1, 24, 1, 30),
            "Modifier 'public' is not supported on interface methods"))))
  }

  test("Interface method virtual modifier") {
    val issues = illegalInterfaceMethodAccess(Array(VIRTUAL_MODIFIER)).toSeq
    assert(
      issues == Seq[Issue](
        Issue(PathFactory("Dummy.cls").toString,
          Diagnostic(ERROR_CATEGORY,
            Location(1, 24, 1, 31),
            "Modifier 'virtual' is not supported on interface methods"))))
  }

  test("Interface method isTest annotation") {
    val issues = illegalInterfaceMethodAccess(Array(ISTEST_ANNOTATION)).toSeq
    assert(
      issues == Seq[Issue](
        Issue(PathFactory("Dummy.cls").toString,
          Diagnostic(ERROR_CATEGORY,
            Location(1, 24, 1, 31),
            "Annotation '@IsTest' is not supported on interface methods"))))
  }
}
