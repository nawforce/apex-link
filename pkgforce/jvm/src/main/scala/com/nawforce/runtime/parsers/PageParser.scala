/*
 Copyright (c) 2021 Kevin Jones, All rights reserved.
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

import com.nawforce.apexparser.CaseInsensitiveInputStream
import com.nawforce.pkgforce.diagnostics.IssuesAnd
import com.nawforce.pkgforce.path.{PathLike, PathLocation}
import com.nawforce.runtime.parsers.PageParser.ParserRuleContext
import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import org.antlr.v4.runtime.tree.ParseTree

import java.io.ByteArrayInputStream
import java.util
import scala.collection.compat.immutable.ArraySeq
import scala.jdk.CollectionConverters._
import scala.reflect.ClassTag

/** VF Page parser helper */
class PageParser(val source: Source) {
  private val is = source.asStream

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

    val lexer = new VFLexer(is)
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
  type ParserRuleContext = org.antlr.v4.runtime.ParserRuleContext
  type TerminalNode      = org.antlr.v4.runtime.tree.TerminalNode

  def apply(path: PathLike, code: SourceData): PageParser = {
    new PageParser(Source(path, code, 0, 0, None))
  }

  def clearCaches(): Unit = {
    val lexer = new VFLexer(
      new CaseInsensitiveInputStream(
        CharStreams.fromStream(new ByteArrayInputStream(Array[Byte]()))
      )
    )
    val parser = new VFParser(new CommonTokenStream(lexer))
    lexer.clearCache()
    parser.clearCache()
  }

  // Helper for JS Portability
  def childCount(context: ParserRuleContext): Int = {
    context.children.size()
  }

  // Helper for JS Portability
  def getText(context: ParserRuleContext): String = {
    context.getText
  }

  // Helper for JS Portability
  def getText(context: ParseTree): String = {
    context.getText
  }

  // Helper for JS Portability
  def getText(context: TerminalNode): String = {
    context.getText
  }

  // Helper for JS Portability
  def toScala[T: ClassTag](collection: java.util.List[T]): ArraySeq[T] = {
    collection match {
      case null                    => PageParser.emptyArraySeq
      case _ if collection.isEmpty => PageParser.emptyArraySeq
      case al: util.ArrayList[T]   => ArraySeq.unsafeWrapArray(al.toArray().asInstanceOf[Array[T]])
      case l                       => ArraySeq.unsafeWrapArray(l.asScala.toArray)
    }
  }

  // Helper for JS Portability
  def toScala[T](value: T): Option[T] = {
    Option(value)
  }

  private val emptyArraySeq = ArraySeq()
}
