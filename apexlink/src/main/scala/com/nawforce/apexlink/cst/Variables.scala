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

package com.nawforce.apexlink.cst

import com.nawforce.apexlink.cst.AssignableSupport.isAssignable
import com.nawforce.apexparser.ApexParser.{
  LocalVariableDeclarationContext,
  VariableDeclaratorContext,
  VariableDeclaratorsContext
}
import com.nawforce.pkgforce.diagnostics.{Diagnostic, ERROR_CATEGORY, Issue, WARNING_CATEGORY}
import com.nawforce.pkgforce.modifiers.{ApexModifiers, ModifierResults}
import com.nawforce.pkgforce.names.TypeName
import com.nawforce.runtime.parsers.CodeParser

final case class VariableDeclarator(typeName: TypeName, id: Id, init: Option[Expression])
    extends CST {
  def verify(input: ExprContext, context: BlockVerifyContext): Unit = {
    id.validate()

    val lhsType = context.getTypeAndAddDependency(typeName, context.thisType).toOption

    val exprContext = new ExpressionVerifyContext(context)
    init.foreach(e => {
      val rhsCtx = e.verify(input, exprContext)
      lhsType.foreach(lhsType => {
        if (
          rhsCtx.isDefined && !isAssignable(
            lhsType.typeName,
            rhsCtx.typeDeclaration,
            strict = false,
            context
          )
        ) {
          context.log(
            Issue(
              location.path,
              ERROR_CATEGORY,
              location.location,
              s"Incompatible types in assignment, from '${rhsCtx.typeDeclaration.typeName}' to '${lhsType.typeName}'"
            )
          )
        }
      })
    })

    // Needed for non-for loop vars where addVars is not called
    addVars(context)
  }

  def addVars(context: BlockVerifyContext): Unit = {
    context.addVar(id.name, this, typeName)
  }
}

object VariableDeclarator {
  def construct(
    typeName: TypeName,
    variableDeclarator: VariableDeclaratorContext
  ): VariableDeclarator = {
    val init = CodeParser.toScala(variableDeclarator.expression()).map(Expression.construct)
    VariableDeclarator(typeName, Id.construct(variableDeclarator.id()), init)
      .withContext(variableDeclarator)
  }
}

final case class VariableDeclarators(declarators: Seq[VariableDeclarator]) extends CST {
  def verify(input: ExprContext, context: BlockVerifyContext): Unit = {
    declarators.foreach(_.verify(input, context))
  }

  def addVars(context: BlockVerifyContext): Unit = {
    declarators.foreach(_.addVars(context))
  }
}

object VariableDeclarators {
  def construct(
    typeName: TypeName,
    variableDeclaratorsContext: VariableDeclaratorsContext
  ): VariableDeclarators = {
    val variableDeclarators: Seq[VariableDeclaratorContext] = {
      Option(variableDeclaratorsContext)
        .map(variableDeclaratorsContext => {
          CodeParser.toScala(variableDeclaratorsContext.variableDeclarator())
        })
        .getOrElse(Seq())
    }
    VariableDeclarators(variableDeclarators.map(x => VariableDeclarator.construct(typeName, x)))
      .withContext(variableDeclaratorsContext)
  }
}

final case class LocalVariableDeclaration(
  modifiers: ModifierResults,
  typeName: TypeName,
  variableDeclarators: VariableDeclarators
) extends CST {
  def verify(context: BlockVerifyContext): Unit = {

    variableDeclarators.declarators.foreach(vd => {
      context.thisType.findField(vd.id.name, None).foreach {
        case field: ApexFieldDeclaration =>
          context.log(
            new Issue(
              location.path,
              new Diagnostic(
                WARNING_CATEGORY,
                location.location,
                s"Local variable is hiding class field '${vd.id.name}', see " +
                  s"${field.location.toString}"
              )
            )
          )
        case _ => ()
      }
    })

    modifiers.issues.foreach(context.log)
    val staticContext = if (context.isStatic) Some(true) else None
    variableDeclarators.verify(ExprContext(isStatic = staticContext, context.thisType), context)
  }

  def addVars(context: BlockVerifyContext): Unit = {
    variableDeclarators.addVars(context)
  }
}

object LocalVariableDeclaration {
  def construct(
    parser: CodeParser,
    from: LocalVariableDeclarationContext,
    isTrigger: Boolean
  ): LocalVariableDeclaration = {
    val typeName = TypeReference.construct(from.typeRef())
    LocalVariableDeclaration(
      ApexModifiers.localVariableModifiers(
        parser,
        CodeParser.toScala(from.modifier()),
        from,
        isTrigger
      ),
      typeName,
      VariableDeclarators.construct(typeName, from.variableDeclarators())
    )
      .withContext(from)
  }
}
