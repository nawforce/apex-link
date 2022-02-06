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
import com.nawforce.apexlink.org.CompletionProvider.{
  emptyCompletions,
  ignoredTokens,
  preferredRules
}
import com.nawforce.apexlink.org.TextOps.TestOpsUtils
import com.nawforce.apexlink.rpc.CompletionItemLink
import com.nawforce.apexlink.types.core._
import com.nawforce.apexparser.{ApexLexer, ApexParser}
import com.nawforce.pkgforce.documents.{ApexClassDocument, ApexTriggerDocument, MetadataDocument}
import com.nawforce.pkgforce.modifiers.PUBLIC_MODIFIER
import com.nawforce.pkgforce.path.PathLike
import com.vmware.antlr4c3.CodeCompletionCore
import org.antlr.v4.runtime.Token

import scala.collection.mutable
import scala.jdk.CollectionConverters._

trait CompletionProvider {
  this: PackageImpl =>

  def getCompletionItems(
    path: PathLike,
    line: Int,
    offset: Int,
    content: String
  ): Array[CompletionItemLink] = {

    // Get basic context of what we are looking at
    val module            = getPackageModule(path)
    val terminatedContent = injectStatementTerminator(line, offset, content)
    val classDetails = MetadataDocument(path)
      .collect {
        case _: ApexClassDocument   => loadClass(path, terminatedContent._1)
        case _: ApexTriggerDocument => loadTrigger(path, terminatedContent._1)
      }
      .getOrElse((None, None))

    if (classDetails._1.isEmpty)
      /* Bail if we did not at least parse the content */
      return emptyCompletions
    val parserAndCU    = classDetails._1.get
    val adjustedOffset = terminatedContent._2

    // Attempt to find a searchTerm for dealing with dot expressions
    lazy val searchTerm =
      content.extractDotTermExclusive(() => new IdentifierAndMethodLimiter, line, offset)

    // Setup to lazy validate the Class block to recover the validationResult for the cursor position
    lazy val validationResult: Option[ValidationResult] = {
      if (classDetails._2.nonEmpty && searchTerm.nonEmpty) {
        val searchEnd     = searchTerm.get.location.endPosition
        val resultMap     = classDetails._2.get.getValidationMap(line, searchEnd)
        val exprLocations = resultMap.keys.filter(_.contains(line, searchEnd))
        val targetExpression =
          exprLocations.find(exprLocation => exprLocations.forall(_.contains(exprLocation)))
        targetExpression.map(targetExpression => resultMap(targetExpression))
      } else {
        None
      }
    }

    // Lazy extract local vars in scope at cursor position from the validation
    lazy val localVars = validationResult.flatMap(_.vars).getOrElse(mutable.Map())

    // Run C3 to get our keywords & rule matches
    val tokenAndIndex =
      findTokenAndIndex(parserAndCU._1, line, adjustedOffset, offset != adjustedOffset)
    val core       = new CodeCompletionCore(parserAndCU._1, preferredRules.asJava, ignoredTokens.asJava)
    val candidates = core.collectCandidates(tokenAndIndex._2, parserAndCU._2)

    // Generate a list of possible keyword matches
    val keywords = candidates.tokens.asScala
      .filter(_._1 >= 1)
      .map(kv => parserAndCU._1.getVocabulary.getDisplayName(kv._1))
      .map(keyword => stripQuotes(keyword))
      .map(keyword => CompletionItemLink(keyword, "Keyword"))
      .toArray

    // Find completions for a dot expression, we use the safe navigation operator here as a trigger as it is unique to
    // dot expressions. We can't use rule matching for these as often the expression before the '.' is complete and
    // we have to remove the '.' so the parser constructs a statement, see injectStatementTerminator().
    val dotCompletions =
      if (keywords.exists(_.label == "?.") && searchTerm.nonEmpty) {
        validationResult
          .filter(_.result.declaration.nonEmpty)
          .map(
            validationResult =>
              getAllCompletionItems(
                validationResult.result.declaration.get,
                validationResult.result.isStatic,
                searchTerm.get.residualExpr
              )
          )
          .getOrElse(emptyCompletions)
      } else {
        emptyCompletions
      }

    /* Now for rule matches. These are not distinct cases, they might combine to give the correct result. */
    var haveTypes = false
    val rules = candidates.rules.asScala
      .collect(
        rule =>
          rule._1.toInt match {
            /* TypeRefs appear in lots of places, e.g. inside Primaries but we just handle once for simplicity. */
            case ApexParser.RULE_typeRef =>
              if (haveTypes) Array[CompletionItemLink]()
              else {
                haveTypes = true
                module.map(_.matchTypeName(terminatedContent._3, offset)).getOrElse(Array())
              }

            /* Primary will appear at the start of an expression (recursively) but this just handles the first primary as
             * dotCompletions covers over cases. At the point it is being handled it is indistinguishable from a MethodCall
             * so we handle both cases. */
            case ApexParser.RULE_primary =>
              /* Also handles start of methodCall */
              searchTerm
                .map(searchTerm => {
                  // Local vars can only be on initial primary
                  if (searchTerm.prefixExpr.isEmpty) {
                    localVars.keys
                      .filter(
                        _.value.take(1).toLowerCase == searchTerm.residualExpr.take(1).toLowerCase
                      )
                      .map(
                        name =>
                          CompletionItemLink(
                            name.value,
                            "Variable",
                            localVars(name).declaration.typeName.toString()
                          )
                      )
                      .toArray ++
                      classDetails._2
                        .map(
                          td =>
                            getAllCompletionItems(
                              td,
                              None,
                              searchTerm.residualExpr,
                              hasPrivateAccess = true
                            )
                        )
                        .getOrElse(Array()) ++
                      (if (haveTypes) Array[CompletionItemLink]()
                       else {
                         haveTypes = true
                         module
                           .map(_.matchTypeName(terminatedContent._3, offset))
                           .getOrElse(Array())
                       })
                  } else {
                    emptyCompletions
                  }
                })
                .getOrElse(emptyCompletions)
          }
      )
      .flatten
      .toArray

    keywords.filterNot(_.label == "?.") ++ dotCompletions ++ rules
  }

  private def findTokenAndIndex(
    parser: ApexParser,
    line: Int,
    offset: Int,
    dotRemoved: Boolean
  ): (Token, Int) = {
    val tokenStream = parser.getInputStream
    tokenStream.seek(0)
    var i     = 0
    var token = tokenStream.get(i)
    while (token.getType != -1 && !tokenContains(token, line, offset)) {
      i += 1
      token = tokenStream.get(i)
    }

    // Adjust cursor, see c3 README for details of cursor positioning
    val idx =
      if (dotRemoved || (i > 1 && tokenStream.get(i - 2).getText == ".")) {
        i
      } else {
        Math.max(0, i - 1)
      }
    (tokenStream.get(idx), idx)
  }

  private def tokenContains(token: Token, line: Int, offset: Int): Boolean = {
    token.getLine == line &&
    token.getCharPositionInLine <= offset &&
    token.getCharPositionInLine + token.getText.length > offset
  }

  private def stripQuotes(keyword: String): String = {
    if (keyword.length > 2)
      keyword.substring(1, keyword.length - 1)
    else
      keyword
  }

  private def injectStatementTerminator(
    line: Int,
    offset: Int,
    content: String
  ): (String, Int, String) = {
    val lines          = content.splitLines
    val result         = new mutable.StringBuilder()
    var adjustedOffset = offset
    var activeLine     = ""

    for (i <- lines.indices) {
      val currentLine = lines(i)
      if (i == line - 1) {
        activeLine = currentLine.substring(0, offset)
        result.append(activeLine)
        // Erase trailing dot so we have a legal expression that ANTLR will construct
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
    (result.toString(), adjustedOffset, activeLine)
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

    var at                = buffer.length - 1
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

  private def getAllCompletionItems(
    td: TypeDeclaration,
    isStatic: Option[Boolean],
    filterBy: String,
    hasPrivateAccess: Boolean = false
  ): Array[CompletionItemLink] = {
    var items = Array[CompletionItemLink]()

    items = items ++ td.methods
      .filter(isStatic.isEmpty || _.isStatic == isStatic.get)
      .filter(hasPrivateAccess || _.modifiers.contains(PUBLIC_MODIFIER))
      .map(method => CompletionItemLink(method))

    items = items ++ td.fields
      .filter(isStatic.isEmpty || _.isStatic == isStatic.get)
      .filter(hasPrivateAccess || _.modifiers.contains(PUBLIC_MODIFIER))
      .map(field => CompletionItemLink(field))

    if (isStatic.isEmpty || isStatic.contains(true)) {
      items = items ++ td.nestedTypes
        .filter(hasPrivateAccess || _.modifiers.contains(PUBLIC_MODIFIER))
        .flatMap(nested => CompletionItemLink(nested))
    }

    if (filterBy.nonEmpty)
      items.filter(x => x.label.take(1).toLowerCase == filterBy.take(1).toLowerCase)
    else
      items
  }
}

object CompletionProvider {
  val emptyCompletions: Array[CompletionItemLink] = Array[CompletionItemLink]()

  val ignoredTokens: Set[Integer] = Set[Integer](
    ApexLexer.LPAREN,
    ApexLexer.RPAREN,
    ApexLexer.LBRACE,
    ApexLexer.RBRACE,
    ApexLexer.LBRACK,
    ApexLexer.RBRACK,
    ApexLexer.SEMI,
    ApexLexer.COMMA,
    ApexLexer.DOT,
    ApexLexer.ASSIGN,
    ApexLexer.GT,
    ApexLexer.LT,
    ApexLexer.BANG,
    ApexLexer.TILDE,
    /* ApexLexer.QUESTIONDOT, - Needed for dotCompletion handling */
    ApexLexer.QUESTION,
    ApexLexer.COLON,
    ApexLexer.EQUAL,
    ApexLexer.TRIPLEEQUAL,
    ApexLexer.NOTEQUAL,
    ApexLexer.LESSANDGREATER,
    ApexLexer.TRIPLENOTEQUAL,
    ApexLexer.AND,
    ApexLexer.OR,
    ApexLexer.INC,
    ApexLexer.DEC,
    ApexLexer.ADD,
    ApexLexer.SUB,
    ApexLexer.MUL,
    ApexLexer.DIV,
    ApexLexer.BITAND,
    ApexLexer.BITOR,
    ApexLexer.CARET,
    ApexLexer.MOD,
    ApexLexer.MAPTO,
    ApexLexer.ADD_ASSIGN,
    ApexLexer.SUB_ASSIGN,
    ApexLexer.MUL_ASSIGN,
    ApexLexer.DIV_ASSIGN,
    ApexLexer.AND_ASSIGN,
    ApexLexer.OR_ASSIGN,
    ApexLexer.XOR_ASSIGN,
    ApexLexer.MOD_ASSIGN,
    ApexLexer.LSHIFT_ASSIGN,
    ApexLexer.RSHIFT_ASSIGN,
    ApexLexer.URSHIFT_ASSIGN,
    ApexLexer.ATSIGN,
    ApexLexer.INSTANCEOF
  )
  val preferredRules: Set[Integer] = Set[Integer](ApexParser.RULE_typeRef, ApexParser.RULE_primary)
}
