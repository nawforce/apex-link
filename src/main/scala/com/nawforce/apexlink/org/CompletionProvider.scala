/*
 Copyright (c) 2021 Kevin Jones & FinancialForce, All rights reserved.
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
package com.nawforce.apexlink.org

import com.nawforce.apexlink.cst._
import com.nawforce.apexlink.org.TextOps.TestOpsUtils
import com.nawforce.apexlink.rpc.CompletionItemLink
import com.nawforce.apexlink.types.apex.FullDeclaration
import com.nawforce.apexlink.types.core.TypeDeclaration
import com.nawforce.pkgforce.diagnostics.Location
import com.nawforce.pkgforce.modifiers.{PRIVATE_MODIFIER, PUBLIC_MODIFIER}
import com.nawforce.pkgforce.path.PathLike

import scala.collection.mutable

trait CompletionProvider {
  this: PackageImpl =>

  def getCompletionItems(path: PathLike, line: Int, offset: Int, content: String): Array[CompletionItemLink] = {
    val terminatedContent = injectStatementTerminator(line, offset, content)
    loadClass(path, terminatedContent)
      .map(typeDef => completionsFromValidation(terminatedContent, typeDef, line, offset))
      .getOrElse(Array.empty)
  }

  private def injectStatementTerminator(line: Int, offset: Int, content: String): String = {
    val lines = content.splitLines
    val result = new mutable.StringBuilder()
    for (i <- lines.indices) {
      val currentLine = lines(i)
      if (i == line - 1) {
        result.append(currentLine.substring(0, offset))
        result.append(';')
        result.append(currentLine.substring(offset))
      } else {
        result.append(currentLine)
      }
    }
    result.toString()
  }

  /** Extract a location link from an expression at the passed location */
  private def completionsFromValidation(source: String,
                                        td: FullDeclaration,
                                        line: Int,
                                        offset: Int): Array[CompletionItemLink] = {
    getExpressionFromValidation(source, td, line, offset) match {
      case Right(exprContext) =>
        exprContext._1.declaration match {
          //TODO: Fix modifier issues and add support for protected modifier.
          // For now we assume we have access to private items only when we fall back to using current declaration.
          case Some(typeDec) => return getAllCompletionItems(typeDec, exprContext._1.isStatic, exprContext._2)
          case _             => return getAllCompletionItems(td, exprContext._1.isStatic, exprContext._2, hasPrivateAccess = true)
        }
      case Left(result) =>
        //We only have access to the search term at this point and no context
        result match {
          case Some(searchTerm) => return getCompletionsFromTypeLookup(searchTerm, td)
          case None             =>
        }
    }
    Array.empty
  }

  private def getCompletionsFromTypeLookup(searchTermResult: (String, Location, String),
                                           fd: FullDeclaration): Array[CompletionItemLink] = {
    //TODO: Fix for Variables and other context. This only works when referring to type in a static context
    // as we cant get the type name from the search term.
    // The search term could be a variable in which case we need resolve the type name and the below wont work

    //Always assume a static context for now when we don't have an expression or context to work with
    fd.findDeclarationFromSourceReference(searchTermResult._1, searchTermResult._2) match {
      case Some(ad) => getAllCompletionItems(ad, Some(true), searchTermResult._3)
      case None => Array.empty
    }
  }

  private def getAllCompletionItems(td: TypeDeclaration,
                                    isStatic: Option[Boolean],
                                    filterBy: String,
                                    hasPrivateAccess: Boolean = false): Array[CompletionItemLink] = {
    var items = Array[CompletionItemLink]()
    items = items ++ td.methods
      .filter(_.isStatic == isStatic.getOrElse(false))
      .filter(_.modifiers.contains(if (hasPrivateAccess) PRIVATE_MODIFIER else PUBLIC_MODIFIER))
      .map(x =>
        new CompletionItemLink(x.name.toString + "(" + x.parameters.map(_.name.toString()).mkString(", ") + ")",
                               "Method"))
    items = items ++ td.fields
      .filter(_.isStatic == isStatic.getOrElse(false))
      .filter(_.modifiers.contains(if (hasPrivateAccess) PRIVATE_MODIFIER else PUBLIC_MODIFIER))
      .map(x => new CompletionItemLink(x.name.toString, "Field"))

    items = items ++ td.constructors.map(_ => new CompletionItemLink(td.name.value, "Constructor"))
    items = items ++ td.nestedTypes.map(x => new CompletionItemLink(x.name.value, "TypeParameter"))
    items.filter(x => x.label.toLowerCase().startsWith(filterBy.toLowerCase()))
  }

  private def getExpressionFromValidation(
    source: String,
    td: FullDeclaration,
    line: Int,
    offset: Int): Either[Option[(String, Location, String)], (ExprContext, String)] = {

    source.extractDotTerm(CompletionProvider.allowedCharacters, line, offset, inclusive = false) match {
      case Some(searchTerm) =>
        val resultMap = td.getBodyDeclarationValidationMap(line, offset)
        val exprLocations = resultMap.keys.filter(_.contains(searchTerm._2.endLine, searchTerm._2.endPosition))
        exprLocations
          .find(exprLocation => exprLocations.forall(_.contains(exprLocation))) match {
          case Some(loc) =>
            Right((resultMap(loc)._2, searchTerm._3))
          case None =>
            Left(Some(searchTerm))
        }
      case _ =>
        Left(None)
    }
  }
}

object CompletionProvider {
  val allowedCharacters: Set[Char] = (('0' to '9') ++ ('a' to 'z') ++ ('A' to 'Z') ++ Seq('_', '.', '(', ')')).toSet
}

