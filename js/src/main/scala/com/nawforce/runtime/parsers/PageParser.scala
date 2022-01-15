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

import com.nawforce.pkgforce.diagnostics.IssuesAnd
import com.nawforce.pkgforce.path.{PathLike, PathLocation}
import com.nawforce.runtime.parsers.PageParser.ParserRuleContext
import com.nawforce.runtime.parsers.antlr.{CharStreams, CommonTokenStream, ParseTree}

import scala.collection.compat.immutable.ArraySeq
import scala.reflect.ClassTag
import scala.scalajs.js

class PageParser(val source: Source) {
  // We would like to extend this but it angers the JavaScript gods
  private val is = source.asString

  def parsePage(): IssuesAnd[VFParser.VfUnitContext] = {
    parse(parser => parser.vfUnit())
  }

  /** Find a location for a rule, adapts based on source offsets to give absolute position in file */
  def getPathLocation(context: ParserRuleContext): PathLocation = {
    source.getLocation(context)
  }

  /** Extract the source used for a parser rule */
  def extractSource(context: ParserRuleContext): Source = {
    source.extractSource(context)
  }

  def parse[T](parse: VFParser => T): IssuesAnd[T] = {
    val listener = new CollectingErrorListener(source.path)

    val lexer = new VFLexer(CharStreams.fromString(is))
    lexer.removeErrorListeners()
    lexer.addErrorListener(listener)
    val tokenStream = new CommonTokenStream(lexer)
    tokenStream.fill()

    val parser = new VFParser(tokenStream)
    parser.removeErrorListeners()
    parser.addErrorListener(listener)

    val result = parse(parser)
    IssuesAnd(listener.issues, result)
  }
}

object PageParser {
  type ParserRuleContext = com.nawforce.runtime.parsers.antlr.ParserRuleContext
  type TerminalNode      = com.nawforce.runtime.parsers.antlr.TerminalNode

  def apply(path: PathLike, code: SourceData): PageParser = {
    new PageParser(Source(path, code, 0, 0, None))
  }

  def clearCaches(): Unit = {
    // Not supported
  }

  // Helper for JS Portability
  def childCount(context: ParserRuleContext): Int = {
    context.childCount
  }

  // Helper for JS Portability
  def getText(context: ParseTree): String = {
    context.text
  }

  // Helper for JS Portability
  def getText(context: ParserRuleContext): String = {
    if (context.childCount == 0) return ""

    val builder = new StringBuilder
    for (i <- 0 until context.childCount) {
      builder.append(context.getChild(i).text)
    }
    builder.toString
  }

  // Helper for JS Portability
  def getText(node: TerminalNode): String = {
    node.text
  }

  // Helper for JS Portability
  def toScala[T: ClassTag](collection: js.Array[T]): ArraySeq[T] = {
    collection match {
      case _ if collection.isEmpty => PageParser.emptyArraySeq
      case _                       => ArraySeq.unsafeWrapArray(collection.toArray)
    }
  }

  // Helper for JS Portability
  def toScala[T](value: js.UndefOr[T]): Option[T] = {
    value.toOption
  }

  private val emptyArraySeq = ArraySeq()
}
