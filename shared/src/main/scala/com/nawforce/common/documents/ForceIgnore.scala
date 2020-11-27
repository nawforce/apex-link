/*
 [The "BSD licence"]
 Copyright (c) 2020 Kevin Jones
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
package com.nawforce.common.documents

import java.util.regex.Pattern

import com.nawforce.common.path.PathLike
import com.nawforce.runtime.platform.Path

class ForceIgnore(rootPath: PathLike, ignoreRules: Seq[IgnoreRule]) {
  private val rootPathPrefix = {
    val path = rootPath.toString
    if (path.endsWith("/")) path else path + '/'
  }
  private val rootPathPrefixLength = rootPathPrefix.length

  def includeDirectory(path: PathLike): Boolean = {
    include(path, directory = true)
  }

  def includeFile(path: PathLike): Boolean = {
    include(path, directory = false)
  }

  private def include(path: PathLike, directory: Boolean): Boolean = {
    val absPath = path.toString
    if (!absPath.startsWith(rootPathPrefix))
      return false

    val relativePath = absPath.substring(rootPathPrefixLength)
    var include = true
    ignoreRules.foreach(rule => {
      if (directory || !rule.dirOnly) {
        if (include != rule.negation) {
          rule.matcher.reset(relativePath)
          if (rule.matcher.matches()) {
            include = !include
          }
        }
      }
    })
    include
  }
}

object ForceIgnore {
  def apply(path: PathLike): Either[String, ForceIgnore] = {
    path.read() match {
      case Left(err) => Left(err)
      case Right(data) =>
        Right(new ForceIgnore(path.parent, IgnoreRule.read(data)))
    }
  }
}

case class IgnoreRule(dirOnly: Boolean, negation: Boolean, pattern: String) {

  lazy val matcher = Pattern.compile(regex).matcher("")

  // Convert a pattern to a regex
  // See https://github.com/snark/ignorance/blob/master/ignorance/utils.py for reference
  lazy val regex: String = {
    val builder = new StringBuilder()
    var i = 0
    val n = pattern.length
    while (i < n) {
      val c = pattern(i)
      i = i + 1
      if (c == '*') {
        if (i < n && pattern(i) == '*') {
          i = i + 1
          builder.append(".*")
          if (i < n && pattern(i) == '/') {
            i = i + 1
            builder.append(IgnoreRule.escape(Path.separator) + "?")
          }
        } else {
          builder.append(IgnoreRule.nonSep + '*')
        }
      } else if (c == '?') {
        builder.append(IgnoreRule.nonSep)
      } else if (c == '/') {
        builder.append(IgnoreRule.escape(Path.separator))
      } else if (c == '[') {
        var j = i
        if (j < n && pattern(j) == '!')
          j = j + 1
        if (j < n && pattern(j) == ']')
          j = j + 1
        while (j <= n && pattern(j) != ']') {
          j = j + 1
        }
        if (j >= n)
          builder.append("\\[")
        else {
          var stuff = pattern.substring(i, j).replace("\\", "\\\\")
          i = j + 1
          if (stuff(0) == '!')
            stuff = s"^ ${stuff.substring(1)}"
          else if (stuff(0) == '^')
            stuff = s"\\ ${stuff.substring(1)}"
          builder.append(s"[$stuff]")
        }
      } else {
        builder.append(IgnoreRule.escape(c))
      }
    }
    builder.append("$")
    builder.toString()
  }
}

object IgnoreRule {
  val nonSep: String = "[^" + escape(Path.separator) + "]"

  /*
   * Read rules from ignore file
   * See https://github.com/snark/ignorance/blob/master/ignorance/git.py for reference
   */
  def read(content: String): Seq[IgnoreRule] = {
    content
      .split("\n")
      .map(_.trim)
      .filter(_.nonEmpty)
      .filterNot(_.startsWith("#"))
      .filterNot(_.contains("***"))
      .filter(l => l.split("\\*\\*", -1).length == l.split("/\\*\\*/", -1).length)
      .filterNot(_ == "/")
      .map(l => {
        var pattern = l
        var dirOnly = pattern.endsWith("/")
        val negation = pattern.startsWith("!")
        if (negation)
          pattern = pattern.substring(1)
        if (pattern.startsWith("/"))
          pattern = pattern.substring(1)
        if (pattern.startsWith("**")) {
          dirOnly = false
          pattern = pattern.substring(2)
        }
        if (pattern.startsWith("/"))
          pattern = pattern.substring(1)
        if (pattern.endsWith("/"))
          pattern = pattern.substring(0, pattern.length - 1)
        IgnoreRule(dirOnly, negation, pattern)
      })
      .toIndexedSeq
  }

  def escape(s: String): String = {
    s.map(escape).mkString
  }

  def escape(c: Char): String = {
    c match {
      case '-'  => "\\-"
      case '/'  => "\\/"
      case '\\' => "\\\\"
      case '{'  => "\\{"
      case '}'  => "\\}"
      case '('  => "\\("
      case ')'  => "\\)"
      case '*'  => "\\*"
      case '+'  => "\\+"
      case '?'  => "\\?"
      case '.'  => "\\."
      case ','  => "\\,"
      case '^'  => "\\^"
      case '$'  => "\\$"
      case '|'  => "\\|"
      case '#'  => "\\#"
      case _    => c.toString
    }
  }
}
