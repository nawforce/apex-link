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

package com.nawforce.common.diagnostics

import com.nawforce.common.api.{Diagnostic, ERROR_CATEGORY, Location, WARNING_CATEGORY}
import com.nawforce.common.path.PathLike
import com.nawforce.runtime.parsers.CodeParser
import com.nawforce.runtime.parsers.CodeParser.ParserRuleContext

import scala.collection.mutable.ArrayBuffer

/** Trait to assist with logging in a context specific way */
trait IssueLogger {
  // Simply log an issue
  def log(issue: Issue): Unit

  def logError(path: PathLike, location: Location, message: String): Unit = {
    log(Issue(path.toString, Diagnostic(ERROR_CATEGORY, location, message)))
  }

  def logWarning(path: PathLike, location: Location, message: String): Unit = {
    log(Issue(path.toString, Diagnostic(WARNING_CATEGORY, location, message)))
  }
}

class LocalLogger(val log: IssueLog) extends IssueLogger {
  override def log(issue: Issue): Unit = log.add(issue)
}

object LocalLogger {
  def apply(): LocalLogger = new LocalLogger(new IssueLog)
}

class CatchingLogger extends IssueLogger {
  var issues: List[Issue] = Nil

  override def log(issue: Issue): Unit = issues = issue :: issues
}

trait ParserIssueLogger extends IssueLogger {
  // Get location for an AST context
  def location(context: ParserRuleContext): (PathLike, Location)

  def logError(context: ParserRuleContext, message: String): Unit = {
    val l = location(context)
    log(Issue(l._1.toString, Diagnostic(ERROR_CATEGORY, l._2, message)))
  }

  def logWarning(context: ParserRuleContext, message: String): Unit = {
    val l = location(context)
    log(Issue(l._1.toString, Diagnostic(WARNING_CATEGORY, l._2, message)))
  }
}

/** Logger using a specific CodaParser */
class CodeParserLogger(parser: CodeParser) extends ParserIssueLogger {
  private val issueLog = new ArrayBuffer[Issue]()

  def isEmpty: Boolean = issueLog.isEmpty

  override def log(issue: Issue): Unit = {
    issueLog.append(issue)
  }

  override def location(context: ParserRuleContext): (PathLike, Location) = {
    parser.getPathAndLocation(context)
  }

  def issues: Array[Issue] = issueLog.toArray
}
