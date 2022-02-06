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
package com.nawforce.apexlink.org

import com.nawforce.pkgforce.path.Location

import java.io.{BufferedReader, StringReader}
import scala.collection.mutable
import scala.jdk.CollectionConverters._
import scala.util.{Failure, Success, Using}

/** A char sequence limiter, return false on non-acceptable characters. */
trait Limiter {
  def allow(char: Char, forward: Boolean): Boolean
}

/** A limiter that accepts identifier characters (and dot) */
class IdentifierLimiter extends Limiter {
  override def allow(char: Char, forward: Boolean): Boolean =
    IdentifierLimiter.allowedCharacters.contains(char)
}

object IdentifierLimiter {
  final val allowedCharacters: Set[Char] =
    (('0' to '9') ++ ('a' to 'z') ++ ('A' to 'Z') ++ Seq('_', '.')).toSet
}

/** A limiter that accepts identifiers with matched parentheses for method calls. Inside parentheses it allows any
  * character to allow for complex expressions used as method arguments.
  */
class IdentifierAndMethodLimiter extends IdentifierLimiter {
  private var bracketDepth = 0

  override def allow(char: Char, forward: Boolean): Boolean = {
    if (char == '(' || char == ')') {
      val leadingChar = if (forward) '(' else ')'
      bracketDepth += (if (char == leadingChar) 1 else -1)
      bracketDepth >= 0
    } else if (bracketDepth > 0) {
      true
    } else {
      IdentifierLimiter.allowedCharacters.contains(char)
    }
  }
}

case class ExclusiveDotTerm(prefixExpr: String, location: Location, residualExpr: String)

object TextOps {
  implicit class TestOpsUtils(text: String) {

    /** Extract a dot delimited term constructed from only the characters allowed by the limiter searching in both
      * directions from the line and offset position provided. The term is split into two strings, the first contains
      * dot-delimited segments prior to the one containing the offset (if present). The second contains any residual
      * text, which may be empty if the term ends with a dot.
      */
    def extractDotTermExclusive(
      limiterFactory: () => Limiter,
      line: Int,
      offset: Int
    ): Option[ExclusiveDotTerm] = {
      text
        .getLine(line - 1)
        .flatMap(lineText => {
          // Search backwards from -1 as selection cursor position is on next character which is possibly not legal
          lineText
            .findLimit(limiterFactory(), forward = false, offset - 1)
            .flatMap(start => {
              lineText
                .findLimit(limiterFactory(), forward = true, start)
                .map(end => {
                  // Split & rebuild so not so sensitive to cursor being close to a "."
                  val rawText    = lineText.substring(start, end + 1)
                  val parts      = rawText.split('.')
                  val searchTerm = new mutable.StringBuilder()
                  parts.foreach(part => {
                    val appendDot = searchTerm.nonEmpty
                    if (
                      start + searchTerm.length + (if (appendDot) 1 else 0) + part.length < offset
                    ) {
                      if (appendDot) searchTerm.append(".")
                      searchTerm.append(part)
                    }
                  })
                  val residual =
                    if (searchTerm.isEmpty)
                      rawText
                    else if (rawText.length > searchTerm.length())
                      rawText.substring(searchTerm.length() + 1)
                    else
                      ""
                  ExclusiveDotTerm(
                    searchTerm.toString(),
                    Location(line, start, line, start + searchTerm.length()),
                    residual
                  )
                })
            })
        })
    }

    /** Extract a dot delimited term constructed from only the characters allowed the limiter at the line and offset provided.
      * If inclusive is set the term encompasses the offset, if not the term includes all segments upto the preceding
      * dot.
      */
    def extractDotTermInclusive(
      limiterFactory: () => Limiter,
      line: Int,
      offset: Int
    ): Option[(String, Location)] = {
      text
        .getLine(line - 1)
        .flatMap(lineText => {
          // Search backwards from -1 as selection cursor position is on next character which is possibly not legal
          lineText
            .findLimit(limiterFactory(), forward = false, offset - 1)
            .flatMap(start => {
              lineText
                .findLimit(limiterFactory(), forward = true, start)
                .map(end => {
                  // Split & rebuild so not so sensitive to cursor being close to a "."
                  val parts      = lineText.substring(start, end + 1).split('.')
                  val searchTerm = new mutable.StringBuilder()
                  var canAppend  = true
                  parts.foreach(part => {
                    if (canAppend) {
                      if (searchTerm.nonEmpty)
                        searchTerm.append(".")
                      searchTerm.append(part)
                    }
                    canAppend = start + searchTerm.length < offset
                  })
                  (searchTerm.toString(), Location(line, start, line, start + searchTerm.length()))
                })
            })
        })
    }

    /** Search for last allowed character from a set either forwards or backwards from an offset */
    def findLimit(limiter: Limiter, forward: Boolean, offset: Int): Option[Int] = {
      if (offset < 0 || offset >= text.length) {
        None
      } else {
        if (!limiter.allow(text(offset), forward)) {
          None
        } else {
          val nextOffset = if (forward) offset + 1 else offset - 1
          Some(findLimit(limiter, forward, nextOffset).getOrElse(offset))
        }
      }
    }

    def splitLines: Array[String] = {
      Using(new BufferedReader(new StringReader(text))) { reader =>
        reader.lines().iterator().asScala.toArray
      } match {
        case Success(array)     => array
        case Failure(exception) => throw exception
      }
    }

    /** Find a specific line in text contents */
    def getLine(line: Int): Option[String] = {
      Using(new BufferedReader(new StringReader(text))) { reader =>
        val lines = reader.lines().iterator().asScala.toArray
        if (line >= 0 && line < lines.length)
          Some(lines(line))
        else
          None
      } match {
        case Success(line)      => line
        case Failure(exception) => throw exception
      }
    }
  }
}
