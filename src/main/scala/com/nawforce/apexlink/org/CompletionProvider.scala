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
import com.nawforce.apexlink.types.core._
import com.nawforce.pkgforce.modifiers.PUBLIC_MODIFIER
import com.nawforce.pkgforce.parsers.{CLASS_NATURE, ENUM_NATURE, INTERFACE_NATURE}
import com.nawforce.pkgforce.path.PathLike
import com.vmware.antlr4c3.CodeCompletionCore

import scala.collection.mutable
import scala.jdk.CollectionConverters._

trait CompletionProvider {
  this: PackageImpl =>

  def getCompletionItems(path: PathLike, line: Int, offset: Int, content: String): Array[CompletionItemLink] = {
    val parserAndCU = loadClass(path, content)
    parserAndCU._1.map(parserAndCU => {
      val core = new CodeCompletionCore(parserAndCU._1, new java.util.HashSet[Integer](), new java.util.HashSet[Integer]())
      val candidates = core.collectCandidates(0, parserAndCU._2)
      candidates.tokens.asScala
        .map(kv => parserAndCU._1.getVocabulary.getDisplayName(kv._1))
        .map(keyword => stripQuotes(keyword))
        .map(keyword => CompletionItemLink(keyword, "Method")).toArray
    }).getOrElse(Array())
  }

  private def stripQuotes(keyword: String) : String = {
    if (keyword.length > 3)
      keyword.substring(1, keyword.length-1)
    else
      keyword
  }

  private def injectStatementTerminator(line: Int, offset: Int, content: String): (String, Int) = {
    val lines = content.splitLines
    val result = new mutable.StringBuilder()
    var adjustedOffset = offset

    for (i <- lines.indices) {
      val currentLine = lines(i)
      if (i == line - 1) {
        result.append(currentLine.substring(0, offset))
        if (result.last == '.') {
          result.deleteCharAt(result.length() - 1)
          adjustedOffset -= 1
        }
        appendTerminators(result)
        result.append(currentLine.substring(offset))
        result.append('\n')
      } else {
        result.append(currentLine)
        result.append('\n')
      }
    }
    (result.toString(), adjustedOffset)
  }

  private def appendTerminators(buffer: mutable.StringBuilder): Unit = {
    def appendStack(buffer: mutable.StringBuilder, stack: List[Char]): Unit = {
      stack match {
        case '(' :: tl =>
          appendStack(buffer, tl)
          buffer.append(')')
        case '[' :: tl =>
          appendStack(buffer, tl)
          buffer.append(']')
        case _ => ()
      }
    }

    var at = buffer.length - 1
    var stack: List[Char] = Nil
    while (at > 0 && buffer.charAt(at) != '{' && buffer.charAt(at) != ';') {
      val char = buffer(at)
      if (char == ')' || char == ']')
        stack = char :: stack
      else if (char == '(') {
        if (stack.headOption.contains(')'))
          stack = stack.tail
        else
          stack = char :: stack
      } else if (char == '[') {
        if (stack.headOption.contains(']'))
          stack = stack.tail
        else
          stack = char :: stack
      }
      at -= 1
    }
    appendStack(buffer, stack)
    buffer.append(";")
  }

  /** Extract a location link from an expression at the passed location */
  private def completionsFromValidation(searchTerm: ExclusiveDotTerm,
                                        td: FullDeclaration,
                                        line: Int,
                                        offset: Int): Array[CompletionItemLink] = {
    if (searchTerm.prefixExpr.isEmpty) {
      getAllCompletionItems(td, None, searchTerm.residualExpr, hasPrivateAccess = true)
    } else {
      getExpressionFromValidation(searchTerm, td, line, offset)
        .filter(_._1.declaration.nonEmpty)
        .map(exprContext => getAllCompletionItems(exprContext._1.declaration.get, exprContext._1.isStatic, exprContext._2))
        .getOrElse(Array.empty)
    }
  }

  private def getAllCompletionItems(td: TypeDeclaration,
                                    isStatic: Option[Boolean],
                                    filterBy: String,
                                    hasPrivateAccess: Boolean = false): Array[CompletionItemLink] = {
    var items = Array[CompletionItemLink]()

    items = items ++ td.methods
      .filter(isStatic.isEmpty || _.isStatic == isStatic.get)
      .filter(hasPrivateAccess || _.modifiers.contains(PUBLIC_MODIFIER))
      .map(method =>
        new CompletionItemLink(method.name.toString + "(" + method.parameters.map(_.name.toString()).mkString(", ") + ")",
          "Method"))

    items = items ++ td.fields
      .filter(isStatic.isEmpty || _.isStatic == isStatic.get)
      .filter(hasPrivateAccess || _.modifiers.contains(PUBLIC_MODIFIER))
      .map(x => new CompletionItemLink(x.name.toString, "Field"))

    items = items ++ td.constructors.map(ctor =>
      new CompletionItemLink(td.name.value + "(" + ctor.parameters.map(_.name.toString()).mkString(", ") + ")", "Constructor"))

    if (isStatic.isEmpty || isStatic.contains(true)) {
      items = items ++ td.nestedTypes
        .filter(hasPrivateAccess || _.modifiers.contains(PUBLIC_MODIFIER))
        .flatMap(nested => nested.nature match {
          case CLASS_NATURE => Some(CompletionItemLink(nested.name.value, "Class"))
          case INTERFACE_NATURE => Some(CompletionItemLink(nested.name.value, "Interface"))
          case ENUM_NATURE => Some(CompletionItemLink(nested.name.value, "Enum"))
          case _ => None
        })
    }

    if (filterBy.nonEmpty)
      items.filter(x => x.label.toLowerCase().startsWith(filterBy.toLowerCase()))
    else
      items
  }

  private def getExpressionFromValidation(
                                           searchTerm: ExclusiveDotTerm,
                                           td: FullDeclaration,
                                           line: Int,
                                           offset: Int): Option[(ExprContext, String)] = {
    val resultMap = td.getBodyDeclarationValidationMap(line, offset)
    val exprLocations = resultMap.keys.filter(_.contains(searchTerm.location.endLine, searchTerm.location.endPosition))
    exprLocations
      .find(exprLocation => exprLocations.forall(_.contains(exprLocation)))
      .map(loc => (resultMap(loc)._2, searchTerm.residualExpr))
  }
}
