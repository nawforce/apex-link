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

import com.nawforce.pkgforce.diagnostics.Location

import java.io.{BufferedReader, StringReader}
import scala.collection.mutable
import scala.jdk.CollectionConverters._
import scala.util.{Success, Using}

object TextOps {
  implicit class TestOpsUtils(text: String) {

    /** Extract a dot delimited term constructed from only the allowed character set at the line and offset provided.
      * If inclusive is set the term encompasses the offset, if not the term includes all segments upto the preceding
      * dot. */
    def extractDotTerm(allowed: Set[Char], line: Int, offset: Int, inclusive: Boolean): Option[(String, Location, String)] = {
      text.getLine(line - 1).flatMap(lineText => {
        // Search backwards from -1 as selection cursor position is on next character which is possibly not legal
        lineText.findLimit(allowed, forward = false, offset - 1).flatMap(start => {
          lineText.findLimit(allowed, forward = true, start).map(end => {
            // Split & rebuild so not so sensitive to cursor being close to a "."
            val searchTerm = new mutable.StringBuilder()
            val parts = lineText.substring(start, end + 1).split('.')
            val dotOffset = if (parts.length > 1) 1 else -1
            var canAppend = true
            parts.foreach(part => {
              if (!inclusive)
                canAppend = start + searchTerm.length + part.length + dotOffset < offset
              if (canAppend) {
                if (searchTerm.nonEmpty)
                  searchTerm.append(".")
                searchTerm.append(part)
              }
              if (inclusive)
                canAppend = start + searchTerm.length < offset
            })
            (searchTerm.toString(), Location(line, start, line, start + searchTerm.length()), parts.last)
          })
        })
      })
    }

    /** Search for last allowed character from a set either forwards or backwards from an offset */
    def findLimit(allow: Set[Char], forward: Boolean, offset: Int): Option[Int] = {
      if (offset < 0 || offset >= text.length) {
        None
      } else {
        if (!allow.contains(text(offset))) {
          None
        } else {
          val nextOffset = if (forward) offset + 1 else offset - 1
          Some(findLimit(allow, forward, nextOffset).getOrElse(offset))
        }
      }
    }

    def splitLines: Array[String] = {
      Using(new BufferedReader(new StringReader(text))) { reader =>
        reader.lines().iterator().asScala.toArray
      } match {
        case Success(array) => array
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
        case Success(line) => line
      }
    }
  }
}
