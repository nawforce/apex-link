/*
 [The "BSD licence"]
 Copyright (c) 2020 Kevin Jones
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
package com.nawforce.common.parsers

import com.nawforce.common.api.{Location, Name}
import com.nawforce.common.diagnostics.Issue
import com.nawforce.common.modifiers.ModifierResults
import com.nawforce.runtime.parsers.ApexParser._
import com.nawforce.runtime.parsers.CodeParser

import scala.collection.compat.immutable.ArraySeq
import scala.collection.mutable.ArrayBuffer

sealed trait ApexNodeNature
case object ApexClassType extends ApexNodeNature
case object ApexInterfaceType extends ApexNodeNature
case object ApexEnumType extends ApexNodeNature
case object ApexConstructorType extends ApexNodeNature
case object ApexMethodType extends ApexNodeNature
case object ApexFieldType extends ApexNodeNature
case object ApexPropertyType extends ApexNodeNature
case object ApexEnumConstantType extends ApexNodeNature

case class IdAndRange(name: Name, range: Location)

object IdAndRange {
  def apply(codeParser: CodeParser, idContext: IdContext): IdAndRange = {
    new IdAndRange(Name(CodeParser.getText(idContext)), codeParser.getPathAndLocation(idContext)._2)
  }

  def apply(codeParser: CodeParser, qnameContext: QualifiedNameContext): IdAndRange = {
    new IdAndRange(Name(CodeParser.getText(qnameContext)),
                   codeParser.getPathAndLocation(qnameContext)._2)
  }
}

case class ApexNode(range: Location,
                    nature: ApexNodeNature,
                    id: IdAndRange,
                    children: ArraySeq[ApexNode],
                    modifiers: ModifierResults,
                    description: String) {

  def collectIssues(): ArraySeq[Issue] = {
    val issues = new ArrayBuffer[Issue]()
    collectIssues(issues)
    ArraySeq.unsafeWrapArray(issues.toArray)
  }

  private def collectIssues(issues: ArrayBuffer[Issue]): Unit = {
    issues.addAll(modifiers.issues)
    children.foreach(_.collectIssues(issues))
  }
}

object ApexNode {
  def apply(parser: CodeParser, ctx: CompilationUnitContext): ApexNode = {
    val visitor = new ApexClassVisitor(parser)
    visitor.visit(ctx).head
  }
}
