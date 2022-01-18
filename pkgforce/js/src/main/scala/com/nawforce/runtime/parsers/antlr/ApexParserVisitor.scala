/*
 Copyright (c) 2019 Kevin Jones, All rights reserved.
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
package com.nawforce.runtime.parsers.antlr

import com.nawforce.apexparser.ApexParser._

import scala.scalajs.js

trait ApexParserVisitor[Result] extends js.Object {
  val visitCompilationUnit: js.UndefOr[js.Function1[CompilationUnitContext, Result]] = js.undefined

  val visitTriggerUnit: js.UndefOr[js.Function1[TriggerUnitContext, Result]] = js.undefined

  val visitTypeDeclaration: js.UndefOr[js.Function1[TypeDeclarationContext, Result]] = js.undefined

  val visitClassDeclaration: js.UndefOr[js.Function1[ClassDeclarationContext, Result]] =
    js.undefined
  val visitInterfaceDeclaration: js.UndefOr[js.Function1[InterfaceDeclarationContext, Result]] =
    js.undefined
  val visitEnumDeclaration: js.UndefOr[js.Function1[EnumDeclarationContext, Result]] = js.undefined

  val visitFieldDeclaration: js.UndefOr[js.Function1[FieldDeclarationContext, Result]] =
    js.undefined
  val visitPropertyDeclaration: js.UndefOr[js.Function1[PropertyDeclarationContext, Result]] =
    js.undefined
  val visitConstructorDeclaration: js.UndefOr[js.Function1[ConstructorDeclarationContext, Result]] =
    js.undefined
  val visitMethodDeclaration: js.UndefOr[js.Function1[MethodDeclarationContext, Result]] =
    js.undefined
  val visitInterfaceMethodDeclaration
    : js.UndefOr[js.Function1[InterfaceMethodDeclarationContext, Result]]        = js.undefined
  val visitEnumConstants: js.UndefOr[js.Function1[EnumConstantsContext, Result]] = js.undefined
}
