/*
 [The "BSD licence"]
 Copyright (c) 2019 Kevin Jones
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.
 3. the name of the author may not be used to endorse or promote products
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
package com.nawforce.common.cst

import com.nawforce.common.names.TypeName
import com.nawforce.common.parsers.ApexParser._
import com.nawforce.common.types.{ApexModifiers, Modifier}

import scala.collection.JavaConverters._

final case class VariableDeclarator(typeName: TypeName, id: Id, init: Option[Expression]) extends CST {
  def verify(input: ExprContext, context: BlockVerifyContext): Unit = {
    id.validate()

    // FUTURE: Make sure expression type(s) matches
    val exprContext = new ExpressionVerifyContext(context)
    init.foreach(_.verify(input, exprContext))

    // Needed for non-for loop vars where addVars is not called
    addVars(context)
  }

  def addVars(context: BlockVerifyContext): Unit = {
    context.addVar(id.name, location, typeName)
  }
}

object VariableDeclarator {
  def construct(typeName: TypeName, variableDeclarator: VariableDeclaratorContext, context: ConstructContext): VariableDeclarator = {
    val init = Option(variableDeclarator.expression()).map(Expression.construct(_, context))
    VariableDeclarator(typeName, Id.construct(variableDeclarator.id(), context), init)
      .withContext(variableDeclarator, context)
  }
}

final case class VariableDeclarators(declarators: List[VariableDeclarator]) extends CST {
  def verify(input: ExprContext, context: BlockVerifyContext): Unit = {
    declarators.foreach(_.verify(input, context))
  }

  def addVars(context: BlockVerifyContext): Unit = {
    declarators.foreach(_.addVars(context))
  }
}

object VariableDeclarators {
  def construct(typeName: TypeName, variableDeclaratorsContext: VariableDeclaratorsContext, context: ConstructContext): VariableDeclarators = {
    val variableDeclarators: Seq[VariableDeclaratorContext] = variableDeclaratorsContext.variableDeclarator().asScala
    VariableDeclarators(variableDeclarators.toList
      .map(x => VariableDeclarator.construct(typeName, x, context))).withContext(variableDeclaratorsContext, context)
  }
}

final case class LocalVariableDeclaration(modifiers: Seq[Modifier], typeName: TypeName, variableDeclarators: VariableDeclarators)
  extends CST {
  def verify(context: BlockVerifyContext): Unit = {
    val staticContext = if (context.isStatic) Some(true) else None
    variableDeclarators.verify(ExprContext(isStatic = staticContext, context.thisType), context)
  }

  def addVars(context: BlockVerifyContext): Unit = {
    variableDeclarators.addVars(context)
  }
}

object LocalVariableDeclaration {
  def construct(localVariableDeclaration: LocalVariableDeclarationContext, context: ConstructContext): LocalVariableDeclaration = {
    val typeName = TypeRef.construct(localVariableDeclaration.typeRef())
    LocalVariableDeclaration(
      ApexModifiers.construct(localVariableDeclaration.modifier().asScala, context),
      typeName,
      VariableDeclarators.construct(typeName, localVariableDeclaration.variableDeclarators(),
        context)).withContext(localVariableDeclaration, context)
  }
}
