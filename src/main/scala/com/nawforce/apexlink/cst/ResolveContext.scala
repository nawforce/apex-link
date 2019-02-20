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
package com.nawforce.apexlink.cst

import com.nawforce.apexlink.utils.LinkerException

trait VarIntroducer {
  private var assignments: List[Expression] = Nil

  def addAssign(statement: Expression): Unit = {
    assignments = statement :: assignments
  }

  def getAssignments: List[Expression] = assignments
}

case class VarDeclaration(name: Identifier, typeRef: TypeRef, introducer: VarIntroducer) {
  def addAssign(statement: Expression): Unit = introducer.addAssign(statement)
}

class ResolveStmtContext(index: CSTIndex, parentScope: ResolveStmtContext = null) {

  private var blockStack: List[List[VarDeclaration]] = Nil
  private var vars: List[VarDeclaration] = Nil

  def pushBlock(): Unit = blockStack = vars :: blockStack

  def popBlock(): Unit = {
    vars = blockStack.head
    blockStack = blockStack.tail
  }

  def complete(): Unit = if (blockStack.nonEmpty) throw new LinkerException()

  def addVarDeclaration(varDeclaration: VarDeclaration): Unit = vars = varDeclaration :: vars

  def getVarDeclaration(name: String): Option[VarDeclaration] = {
    vars.find(_.name.text.compareToIgnoreCase(name) == 0)
  }
}

class ResolveExprContext(stmtContext: ResolveStmtContext) {
  def getVarDeclaration(name: String): Option[VarDeclaration] = {
    stmtContext.getVarDeclaration(name)
  }
}
