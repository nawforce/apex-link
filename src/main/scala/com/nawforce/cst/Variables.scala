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
package com.nawforce.cst

import com.nawforce.names.TypeName
import com.nawforce.parsers.ApexParser._
import com.nawforce.types.{ApexModifiers, Modifier}

import scala.collection.JavaConverters._

sealed abstract class VariableInitializer() extends CST {
  def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext
}

object VariableInitializer {
  def construct(variableInitializers: List[VariableInitializerContext], context: ConstructContext): List[VariableInitializer] = {
    variableInitializers.map(x => VariableInitializer.construct(x, context))
  }

  def construct(variableInitializer: VariableInitializerContext, context: ConstructContext): VariableInitializer = {
    val cst =
      if (variableInitializer.expression() != null) {
        ExpressionVariableInitializer(Expression.construct(variableInitializer.expression(), context))
      } else if (variableInitializer.arrayInitializer() != null) {
        ArrayVariableInitializer.construct(variableInitializer.arrayInitializer(), context)
      } else {
        throw new CSTException()
      }
    cst.withContext(variableInitializer, context)
  }
}

final case class ArrayVariableInitializer(variableInitializers: List[VariableInitializer]) extends VariableInitializer {
  override def children(): List[CST] = variableInitializers

  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    variableInitializers.foreach(_.verify(input, context))
    // TODO
    ExprContext.empty
  }
}

final case class ExpressionVariableInitializer(expression: Expression) extends VariableInitializer {
  override def children(): List[CST] = expression :: Nil

  override def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    expression.verify(input, context)
    // TODO
    ExprContext.empty
  }
}

object ArrayVariableInitializer {
  def construct(arrayInitializer: ArrayInitializerContext, context: ConstructContext): ArrayVariableInitializer = {
    val variableInitializers: Seq[VariableInitializerContext] = arrayInitializer.variableInitializer().asScala
    ArrayVariableInitializer(variableInitializers.toList.map(x =>
      VariableInitializer.construct(x, context)
    )).withContext(arrayInitializer, context)
  }
}

final case class VariableDeclarator(typeName: TypeName, id: Id, init: Option[VariableInitializer]) extends CST {
  override def children(): List[CST] = List[CST](id) ++ init

  def verify(input: ExprContext, context: BlockVerifyContext): ExprContext = {
    addVars(context) // Needed for non-for loop vars where addVars is not called

    val exprContext = new ExpressionVerifyContext(context)
    init.foreach(_.verify(input, exprContext))
    // TODO
    ExprContext.empty
  }

  def addVars(context: BlockVerifyContext): Unit = {
    context.addVar(id.name, location, typeName)
  }
}

object VariableDeclarator {
  def construct(typeName: TypeName, variableDeclarator: VariableDeclaratorContext, context: ConstructContext): VariableDeclarator = {
    val init =
      if (variableDeclarator.variableInitializer() != null) {
        Some(VariableInitializer.construct(variableDeclarator.variableInitializer(), context))
      } else {
        None
      }
    VariableDeclarator(typeName, Id.construct(variableDeclarator.id(), context), init)
      .withContext(variableDeclarator, context)
  }
}

final case class VariableDeclarators(declarators: List[VariableDeclarator]) extends CST {
  override def children(): List[CST] = declarators

  def verify(input: ExprContext, context: BlockVerifyContext): ExprContext = {
    declarators.foreach(_.verify(input, context))
    // TODO
    ExprContext.empty
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

  override def children(): List[CST] = variableDeclarators :: Nil

  def verify(context: BlockVerifyContext): Unit = {
    variableDeclarators.verify(ExprContext(isStatic = context.isStatic, context.thisType), context)
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
