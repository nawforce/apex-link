package io.github.nahforce.apexlink.diff

import scala.collection.mutable
import scala.io.Source

case class Delta(start: Long, stop: Long, replace: Option[String]) {

  def contains(at: Long): Boolean = {
    at >= start && at <= stop
  }

  def disjoint(other: Delta): Boolean = {
    start > other.stop || stop < other.start
  }
}


class FileChanger {
  private val changes: mutable.Map[String, mutable.ArrayBuffer[Delta]] = mutable.Map()

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

  def diff(): Unit = {
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
        if (atChar < reverseDeltas(atDelta).start) {
          atDelta = atDelta + 1
        }
      }
    }
    contents.reverse.toArray
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


