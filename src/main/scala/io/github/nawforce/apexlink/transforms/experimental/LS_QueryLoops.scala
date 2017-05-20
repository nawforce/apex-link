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
package io.github.nawforce.apexlink.transforms.experimental

import io.github.nawforce.apexlink.cst._
import io.github.nawforce.apexlink.diff.FileChanger
import io.github.nawforce.apexlink.metadata.{ApexClass, SymbolReaderContext}

import scala.language.reflectiveCalls

class LS_QueryLoops {

  implicit class StringInterpolations(sc: StringContext) {
    def ci = new {
      def unapply(other: String): Boolean = sc.parts.mkString.equalsIgnoreCase(other)
    }
  }

  def exec(ctx: SymbolReaderContext, fileChanger: FileChanger): Unit = {

    // Filter classes for for statements with Enhanced Control
    ctx.getClasses.values.foreach((apexClass: ApexClass) => {
      apexClass.methodDeclarations.foreach((method: MethodDeclaration) => {
        method.findStatements(false).collect { case x: ForStatement => x } foreach ((stmt: ForStatement) => {
          stmt match {
            case ForStatement(control@EnhancedForControl(_, _, _, _), _) =>
              // Filter for simple type, likely sObject style
              control.typeRef match {
                case ClassOrInterfaceTypeRef(ClassOrInterfaceType(ClassOrInterfaceTypePart(_, TypeList(Nil)) :: Nil), 0) =>
                  // Filter for expression is identifier assigned once
                  control.expression match {
                    case PrimaryExpression(VarRef(decl)) =>
                      val assignments = decl.introducer.getAssignments
                      if (assignments.length == 1 && isQueryExpression(assignments.head)) {
                        fileChanger.addChange(apexClass.location.filepath, stmt.start(), -1, Some(LS_QueryLoops.warningMsg))
                      }
                    case _ =>
                  }
                case _ =>
              }
            case _ =>
          }
        })
      })
    })
  }

  private def isQueryExpression(expr: Expression) = {
    expr match {
      case PrimaryExpression(SOQL(_)) => true
      case FunctionCall(QName(ci"database" :: ci"query" :: Nil), _) => true
      case _ => false
    }
  }
}

object LS_QueryLoops {
  val warningMsg = "/* ApexLink ls-query-loops WARNING: using SOQL directly in for loop is more efficient than passing via variable */\n"
}
