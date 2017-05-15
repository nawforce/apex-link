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

import java.io.File

import io.github.nawforce.apexlink.diff.OwenDiff._

object UnifiedDiffer {
  def showDiff(filename: String, original: Array[String], modified: Array[String]): Unit = {
    val aPath = joinPaths("a", filename)
    val bPath = joinPaths("b", filename)

    print("diff -u " + aPath + " " + bPath + '\n')
    print("---  " + aPath + '\n')
    print("+++  " + bPath + '\n')

    val groups: List[List[DiffResult]] = groupDiffs(OwenDiff.Diff.diff(original, modified).toList).flatMap(simplifyGroup)
    groups.foreach(g => showGroup(g, original, modified))
  }

  private def joinPaths(prefix: String, path: String): String = {
    val f = new File(path)
    if (f.isAbsolute)
      prefix + path
    else
      prefix + File.separator + path
  }

  private def showGroup(group: List[DiffResult], original: Array[String], modified: Array[String]): Unit = {

    val startAt = Math.max(0, group.head.file1Index - 3)
    val endAt = Math.min(original.length - 1, group.last.file1Index + group.last.lengths._1 + 2)
    if (!hasDifferences(original, modified, group.head.file1Index, group.head.file2Index, endAt - startAt))
      return

    val added = group.map(d => d.lengths._2 - d.lengths._1).sum
    val sourceLines = endAt - startAt + 1
    print("@@ -%d,%d +%d,%d @@\n".format(startAt + 1, sourceLines,
      group.head.file2Index - (group.head.file1Index - startAt) + 1, sourceLines + added))

    val at = showResult(startAt, group, original)
    for (line <- at to endAt) {
      writeLine(' ', original(line))
    }
  }

  private def showResult(startAt: Integer, group: List[DiffResult], original: Array[String]): Int = {
    var line = startAt
    while (line < group.head.file1Index) {
      writeLine(' ', original(line))
      line += 1
    }

    val dr = group.head
    dr match {
      case m: Modify =>
        m.oldLines.foreach(l => writeLine('-', l))
        m.newLines.foreach(l => writeLine('+', l))
        line += m.lengths._1
      case i: Insert =>
        i.lines.foreach(l => writeLine('+', l))
      case d: Delete =>
        d.oldLines.foreach(l => writeLine('-', l))
        line += d.lengths._1
    }

    if (group.tail != Nil)
      showResult(line, group.tail, original)
    else
      line
  }

  /*
   * Groups diff results that have overlapping contexts
   */
  private def groupDiffs(diffs: List[DiffResult]): List[List[DiffResult]] = {
    var groups: List[List[DiffResult]] = List()
    for (diff <- diffs.filter(!_.isInstanceOf[Equal]).reverse) {
      if (groups.isEmpty) {
        groups = List(List(diff))
      } else {
        if (overlaps(diff, groups.head.head)) {
          groups = (diff :: groups.head) :: groups.tail
        } else {
          groups = List(diff) :: groups
        }
      }
    }
    groups
  }

  /*
   * Determine if two results overlap taking into account three line context
   */
  private def overlaps(diff1: DiffResult, diff2: DiffResult): Boolean = {
    diff1.file1Index + diff1.lengths._1 + 3 > diff2.file1Index - 3
  }

  /*
   * Simplify a grouped list of diffs
   */
  private def simplifyGroup(diffs: List[DiffResult]): Option[List[DiffResult]] = {
    val simpler: List[DiffResult] = diffs.flatMap(simplifyResult)
    if (simpler.isEmpty) None else Some(simpler)
  }

  /*
   * Simplify a single DiffResult, this should not be need but the output can be less than ideal sometimes. Note
   * we can't make changes here that would change the line numbers of subsequent DiffResults
   */
  private def simplifyResult(dr: DiffResult): Option[DiffResult] = {
    dr match {
      case _: Equal =>
        None
      case m: Modify =>
        // If we end up with no lines then drop change
        if (m.oldLines.isEmpty && m.newLines.isEmpty) {
          None
        }
        // Strip identical start lines
        else if (m.oldLines.nonEmpty && m.newLines.nonEmpty && m.oldLines.head == m.newLines.head) {
          simplifyResult(Modify(m.file1Index + 1, m.file2Index + 1, (m.lengths._1 - 1, m.lengths._2 - 1),
            m.oldLines.tail, m.newLines.tail))
          // Strip identical last lines
        } else if (m.oldLines.nonEmpty && m.newLines.nonEmpty && m.oldLines.last == m.newLines.last) {
          simplifyResult(Modify(m.file1Index, m.file2Index, (m.lengths._1 - 1, m.lengths._2 - 1),
            m.oldLines.take(m.oldLines.length - 1), m.newLines.take(m.newLines.length - 1)))
        } else {
          Some(m)
        }
      case _ => Some(dr)
    }
  }

  /*
   * Write a diff line, adding a '\n' if needed
   */
  private def writeLine(marker: Char, line: String): Unit = {
    if (line.last != '\n') {
      print(marker + line + '\n')
      print("\\ No newline at end of file")
    }
    else
      print(marker + line)
  }

  /*
   * Test if there are some differences over a range of lines, used to reduce output clutter
   */
  private def hasDifferences(original: Array[String], modified: Array[String], originalStart: Int,
                             modifiedStart: Int, lines: Int): Boolean = {
    for (line <- 0 to lines) {
      if (original(originalStart + line) != modified(modifiedStart + line))
        return true
    }
    false
  }
}
