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
package com.nawforce.runtime.parsers

import com.nawforce.apexparser.{ApexLexer, ApexParser, CaseInsensitiveInputStream}
import com.nawforce.pkgforce.diagnostics.IssuesAnd
import com.nawforce.pkgforce.path.{PathLike, PathLocation}
import com.nawforce.runtime.parsers.CodeParser.ParserRuleContext
import com.nawforce.runtime.parsers.antlr.CommonTokenStream

import scala.collection.compat.immutable.ArraySeq
import scala.reflect.ClassTag
import scala.scalajs.js

class CodeParser(val source: Source) {
  // We would like to extend this but it angers the JavaScript gods
  val cis: CaseInsensitiveInputStream = source.asInsensitiveStream

  var lastTokenStream: Option[CommonTokenStream] = None

  def parseClass(): IssuesAnd[ApexParser.CompilationUnitContext] = {
    parse(parser => parser.compilationUnit())
  }

  def parseTrigger(): IssuesAnd[ApexParser.TriggerUnitContext] = {
    parse(parser => parser.triggerUnit())
  }

  def parseBlock(): IssuesAnd[ApexParser.BlockContext] = {
    parse(parser => parser.block())
  }

  def parseSOQL(): IssuesAnd[ApexParser.QueryContext] = {
    parse(parser => parser.query())
  }

  def parseSOSL(): IssuesAnd[ApexParser.SoslLiteralContext] = {
    parse(parser => parser.soslLiteral())
  }

  def parseExpression(): IssuesAnd[ApexParser.ExpressionContext] = {
    parse(parser => parser.expression())
  }

  // Test use only
  def parseLiteral(): IssuesAnd[ApexParser.LiteralContext] = {
    parse(parser => parser.literal())
  }

  /** Find a location for a rule, adapts based on source offsets to give absolute position in file */
  def getPathLocation(context: ParserRuleContext): PathLocation = {
    source.getLocation(context)
  }

  /** Extract the source used for a parser rule */
  def extractSource(context: ParserRuleContext): Source = {
    source.extractSource(context)
  }

  def parse[T](parse: ApexParser => T): IssuesAnd[T] = {
    lastTokenStream = None
    val tokenStream = new CommonTokenStream(new ApexLexer(cis))
    tokenStream.fill()

    val listener = new CollectingErrorListener(source.path)
    val parser   = new ApexParser(tokenStream)
    parser.removeErrorListeners()
    parser.addErrorListener(listener)

    val result = parse(parser)
    lastTokenStream = Some(tokenStream)
    IssuesAnd(listener.issues, result)
  }
}

object CodeParser {
  type ParserRuleContext = com.nawforce.runtime.parsers.antlr.ParserRuleContext
  type TerminalNode      = com.nawforce.runtime.parsers.antlr.TerminalNode

  def apply(path: PathLike, code: SourceData): CodeParser = {
    new CodeParser(Source(path, code, 0, 0, None))
  }

  def clearCaches(): Unit = {
    // Not supported
  }

  // Helper for JS Portability
  def getText(context: ParserRuleContext): String = {
    context.childCount match {
      case 0 => ""
      case 1 => context.getChild(0).text
      case _ =>
        val builder = new StringBuilder
        for (i <- 0 until context.childCount) {
          builder.append(context.getChild(i).text)
        }
        builder.toString
    }
  }

  // Helper for JS Portability
  def getText(node: TerminalNode): String = {
    node.text
  }

  // Helper for JS Portability
  def toScala[T: ClassTag](collection: js.Array[T]): ArraySeq[T] = {
    collection match {
      case _ if collection.isEmpty => CodeParser.emptyArraySeq
      case _                       => ArraySeq.unsafeWrapArray(collection.toArray)
    }
  }

  // Helper for JS Portability
  def toScala[T](value: js.UndefOr[T]): Option[T] = {
    value.toOption
  }

  private val emptyArraySeq = ArraySeq()
}
