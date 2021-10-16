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
package com.nawforce.pkgforce.parsers

import com.nawforce.apexparser.ApexParser._
import com.nawforce.pkgforce.diagnostics.{Diagnostic, ERROR_CATEGORY, Issue}
import com.nawforce.pkgforce.modifiers.{GLOBAL_MODIFIER, Modifier, WEBSERVICE_MODIFIER}
import com.nawforce.pkgforce.names.Name
import com.nawforce.pkgforce.path.{IdLocatable, Location, PathLocation}
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

trait ApexNode extends IdLocatable {
  val nature: ApexNodeNature
  val id: Name
  val children: ArraySeq[ApexNode]
  val modifiers: ArraySeq[Modifier]
  val signature: String
  val description: String
  val parseIssues: ArraySeq[Issue]

  def collectIssues(): ArraySeq[Issue] = {
    val issues = new ArrayBuffer[Issue]()
    collectIssues(issues)
    ArraySeq.unsafeWrapArray(issues.toArray)
  }

  protected def collectIssues(issues: ArrayBuffer[Issue]): Unit = {
    issues.addAll(parseIssues)
    children.foreach(_.collectIssues(issues))
  }
}

object ApexNode {
  def apply(parser: CodeParser, ctx: CompilationUnitContext): Option[ApexNode] = {
    val visitor = new ApexClassVisitor(parser)
    visitor.visit(ctx).headOption
  }
}

case class ApexGenericNode(location: PathLocation,
                           nature: ApexNodeNature,
                           id: Name,
                           idLocation: Location,
                           children: ArraySeq[ApexNode],
                           modifiers: ArraySeq[Modifier],
                           signature: String,
                           description: String,
                           parseIssues: ArraySeq[Issue])
  extends ApexNode {}

case class ApexClassNode(location: PathLocation,
                         id: Name,
                         idLocation: Location,
                         children: ArraySeq[ApexNode],
                         modifiers: ArraySeq[Modifier],
                         signature: String,
                         description: String,
                         parseIssues: ArraySeq[Issue])
  extends ApexNode {

  override val nature: ApexNodeNature = ApexClassType

  override def collectIssues(issues: ArrayBuffer[Issue]): Unit = {
    super.collectIssues(issues)
    checkNeedsGlobalOrWebService().foreach(issue => issues.append(issue))
  }

  private def checkNeedsGlobalOrWebService(): Seq[Issue] = {
    if (!modifiers.contains(GLOBAL_MODIFIER)) {
      children
        .filter(_.modifiers.intersect(Seq(GLOBAL_MODIFIER, WEBSERVICE_MODIFIER)).nonEmpty)
        .map(
          child =>
            new Issue(
              location.path,
              Diagnostic(
                ERROR_CATEGORY,
                child.idLocation,
                "Enclosing class must be declared global to use global or webservice modifiers")))
    } else {
      Seq.empty
    }
  }
}
