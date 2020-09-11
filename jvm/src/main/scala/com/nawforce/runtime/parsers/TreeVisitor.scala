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

package com.nawforce.runtime.parsers

import com.nawforce.runtime.parsers.ApexParser._
import org.antlr.v4.runtime.tree.RuleNode

import scala.collection.compat.immutable.ArraySeq
import scala.reflect.ClassTag

abstract class TreeVisitor[T: ClassTag] extends ApexParserBaseVisitor[ArraySeq[T]] {
  type VisitChildren = RuleNode => ArraySeq[T]

  override def defaultResult(): ArraySeq[T] = ArraySeq[T]()

  override protected def aggregateResult(aggregate: ArraySeq[T],
                                         nextResult: ArraySeq[T]): ArraySeq[T] = {
    aggregate ++ nextResult
  }

  override def visitClassDeclaration(ctx: ApexParser.ClassDeclarationContext): ArraySeq[T] =
    classDeclaration(ctx, super.visitChildren)

  override def visitInterfaceDeclaration(ctx: ApexParser.InterfaceDeclarationContext): ArraySeq[T] =
    interfaceDeclaration(ctx, super.visitChildren)

  override def visitEnumDeclaration(ctx: ApexParser.EnumDeclarationContext): ArraySeq[T] =
    enumDeclaration(ctx, super.visitChildren)

  override def visitConstructorDeclaration(
    ctx: ApexParser.ConstructorDeclarationContext): ArraySeq[T] =
    constructorDeclaration(ctx, super.visitChildren)

  override def visitMethodDeclaration(ctx: ApexParser.MethodDeclarationContext): ArraySeq[T] =
    methodDeclaration(ctx, super.visitChildren)

  override def visitInterfaceMethodDeclaration(
    ctx: ApexParser.InterfaceMethodDeclarationContext): ArraySeq[T] =
    interfaceMethodDeclaration(ctx, super.visitChildren)

  override def visitFieldDeclaration(ctx: ApexParser.FieldDeclarationContext): ArraySeq[T] =
    fieldDeclaration(ctx, super.visitChildren)

  override def visitPropertyDeclaration(ctx: ApexParser.PropertyDeclarationContext): ArraySeq[T] =
    propertyDeclaration(ctx, super.visitChildren)

  override def visitEnumConstants(ctx: ApexParser.EnumConstantsContext): ArraySeq[T] =
    enumConstants(ctx, super.visitChildren)

  def classDeclaration(ctx: ClassDeclarationContext, visitChildren: VisitChildren): ArraySeq[T]
  def interfaceDeclaration(ctx: InterfaceDeclarationContext,
                           visitChildren: VisitChildren): ArraySeq[T]
  def enumDeclaration(ctx: EnumDeclarationContext, visitChildren: VisitChildren): ArraySeq[T]
  def constructorDeclaration(ctx: ConstructorDeclarationContext,
                             visitChildren: VisitChildren): ArraySeq[T]
  def methodDeclaration(ctx: MethodDeclarationContext, visitChildren: VisitChildren): ArraySeq[T]
  def interfaceMethodDeclaration(ctx: InterfaceMethodDeclarationContext,
                                 visitChildren: VisitChildren): ArraySeq[T]
  def fieldDeclaration(ctx: FieldDeclarationContext, visitChildren: VisitChildren): ArraySeq[T]
  def propertyDeclaration(ctx: PropertyDeclarationContext,
                          visitChildren: VisitChildren): ArraySeq[T]
  def enumConstants(ctx: EnumConstantsContext, visitChildren: VisitChildren): ArraySeq[T]
}
