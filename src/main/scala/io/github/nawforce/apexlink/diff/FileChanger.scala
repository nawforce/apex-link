/*
 [The "BSD licence"]
 Copyright (c) 2017 Kevin Jones
 All rights reserved.

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

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package io.github.nawforce.apexlink.diff

import scala.collection.mutable
import scala.io.Source

case class Delta(start: Long, stop: Long, replace: Option[String]) {

  def contains(at: Long): Boolean = {
    at >= start && at <= stop && stop != -1
  }

  def disjoint(other: Delta): Boolean = {
    stop == -1 || other.stop == -1 || (start > other.stop || stop < other.start)
  }
}


class FileChanger {
  private val changes: mutable.Map[String, mutable.ArrayBuffer[Delta]] = mutable.Map()
  private val replace: mutable.Map[String, String] = mutable.Map()

  def addChange(filename: String, start: Long, end: Long, replace: Option[String]): Boolean = {
    val d = Delta(start, end, replace)
    val entry = changes.getOrElse(filename, mutable.ArrayBuffer[Delta]())
    if (entry.exists(o => !o.disjoint(d))) {
      return false
    }
    entry.append(d)
    changes.put(filename, entry)
    true
  }

  def replaceFile(filename: String, text: String) = {
    replace.put(filename, text)
  }

  def diff(): Unit = {
    replace.foreach { case (filename, text) =>
      val source = Source.fromFile(filename).toArray
      val modified: Array[String] = new LineIterator(text.toCharArray).toArray
      val original: Array[String] = new LineIterator(source).toArray
      UnifiedDiffer.showDiff(filename, original, modified)
    }

    changes.foreach { case (filename, deltas) =>
      val source = Source.fromFile(filename).toArray
      val modified: Array[String] = new LineIterator(applyDelta(source, deltas.toArray)).toArray
      val original: Array[String] = new LineIterator(source).toArray

      UnifiedDiffer.showDiff(filename, original, modified)
    }
  }

  private def applyDelta(source: Array[Char], deltas: Array[Delta]): Array[Char] = {
    val reverseDeltas = deltas.sortWith(_.start > _.start)
    var atChar = source.length - 1
    var atDelta = 0
    var contents = new StringBuilder
    while (atChar >= 0) {
      val suppress = reverseDeltas.length > atDelta && reverseDeltas(atDelta).contains(atChar)
      if (!suppress) {
        contents += source(atChar)
      }

      atChar = atChar - 1
      if (reverseDeltas.length > atDelta) {
        val delta = reverseDeltas(atDelta)
        if (atChar < delta.start) {
          if (delta.replace.isDefined) {
            contents.append(preWhitespace(source, atChar).reverse)
            contents.append(reverseDeltas(atDelta).replace.getOrElse("").reverse)
          }
          atDelta = atDelta + 1
        }
      }
    }
    contents.reverse.toArray
  }

  private def preWhitespace(source: Array[Char], atChar: Integer) : String = {
    source(atChar) match {
      case ' ' | '\t' => preWhitespace(source, atChar-1) + source(atChar)
      case _ => ""
    }
  }
}

private class LineIterator(source: Array[Char]) extends Iterator[String] {
  private val sb = new StringBuilder
  private val chars = source.iterator.buffered

  def hasNext: Boolean = chars.hasNext

  def next(): String = {
    sb.clear
    while (getc()) {}
    sb.toString
  }

  private def getc() = chars.hasNext && {
    val ch = chars.next
    sb append ch
    if (ch == '\n') {
      false
    } else if (ch == '\r') {
      if (chars.hasNext && chars.head == '\n') {
        sb append chars.next
        false
      } else {
        true
      }
    }
    else {
      true
    }
  }
}


