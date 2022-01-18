/*
 Copyright (c) 2020 Kevin Jones, All rights reserved.
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

package com.nawforce.runtime.parsers

import com.nawforce.apexparser.ApexParser._
import com.nawforce.runtime.parsers.antlr.{AbstractParseTreeVisitor, ApexParserVisitor, RuleNode}

import scala.collection.compat.immutable.ArraySeq
import scala.reflect.ClassTag
import scala.scalajs.js

abstract class TreeVisitor[T: ClassTag]
    extends AbstractParseTreeVisitor[ArraySeq[T]]
    with ApexParserVisitor[ArraySeq[T]] {
  type VisitChildren = RuleNode => ArraySeq[T]

  override def defaultResult(): ArraySeq[T] = ArraySeq[T]()

  override protected def aggregateResult(
    aggregate: ArraySeq[T],
    nextResult: ArraySeq[T]
  ): ArraySeq[T] = {
    aggregate ++ nextResult
  }

  override val visitClassDeclaration
    : js.UndefOr[js.Function1[ClassDeclarationContext, ArraySeq[T]]] =
    js.defined(ctx => classDeclaration(ctx, super.visitChildren))

  override val visitTriggerUnit: js.UndefOr[js.Function1[TriggerUnitContext, ArraySeq[T]]] =
    js.defined(ctx => triggerDeclaration(ctx, super.visitChildren))

  override val visitInterfaceDeclaration
    : js.UndefOr[js.Function1[InterfaceDeclarationContext, ArraySeq[T]]] =
    js.defined(ctx => interfaceDeclaration(ctx, super.visitChildren))

  override val visitEnumDeclaration: js.UndefOr[js.Function1[EnumDeclarationContext, ArraySeq[T]]] =
    js.defined(ctx => enumDeclaration(ctx, super.visitChildren))

  override val visitConstructorDeclaration
    : js.UndefOr[js.Function1[ConstructorDeclarationContext, ArraySeq[T]]] =
    js.defined(ctx => constructorDeclaration(ctx, super.visitChildren))

  override val visitMethodDeclaration
    : js.UndefOr[js.Function1[MethodDeclarationContext, ArraySeq[T]]] =
    js.defined(ctx => methodDeclaration(ctx, super.visitChildren))

  override val visitInterfaceMethodDeclaration
    : js.UndefOr[js.Function1[InterfaceMethodDeclarationContext, ArraySeq[T]]] =
    js.defined(ctx => interfaceMethodDeclaration(ctx, super.visitChildren))

  override val visitFieldDeclaration
    : js.UndefOr[js.Function1[FieldDeclarationContext, ArraySeq[T]]] =
    js.defined(ctx => fieldDeclaration(ctx, super.visitChildren))

  override val visitPropertyDeclaration
    : js.UndefOr[js.Function1[PropertyDeclarationContext, ArraySeq[T]]] =
    js.defined(ctx => propertyDeclaration(ctx, super.visitChildren))

  override val visitEnumConstants: js.UndefOr[js.Function1[EnumConstantsContext, ArraySeq[T]]] =
    js.defined(ctx => enumConstants(ctx, super.visitChildren))

  def classDeclaration(ctx: ClassDeclarationContext, visitChildren: VisitChildren): ArraySeq[T]
  def triggerDeclaration(ctx: TriggerUnitContext, visitChildren: VisitChildren): ArraySeq[T]
  def interfaceDeclaration(
    ctx: InterfaceDeclarationContext,
    visitChildren: VisitChildren
  ): ArraySeq[T]
  def enumDeclaration(ctx: EnumDeclarationContext, visitChildren: VisitChildren): ArraySeq[T]
  def constructorDeclaration(
    ctx: ConstructorDeclarationContext,
    visitChildren: VisitChildren
  ): ArraySeq[T]
  def methodDeclaration(ctx: MethodDeclarationContext, visitChildren: VisitChildren): ArraySeq[T]
  def interfaceMethodDeclaration(
    ctx: InterfaceMethodDeclarationContext,
    visitChildren: VisitChildren
  ): ArraySeq[T]
  def fieldDeclaration(ctx: FieldDeclarationContext, visitChildren: VisitChildren): ArraySeq[T]
  def propertyDeclaration(
    ctx: PropertyDeclarationContext,
    visitChildren: VisitChildren
  ): ArraySeq[T]
  def enumConstants(ctx: EnumConstantsContext, visitChildren: VisitChildren): ArraySeq[T]
}
