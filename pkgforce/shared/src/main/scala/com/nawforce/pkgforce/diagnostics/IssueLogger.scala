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

package com.nawforce.pkgforce.diagnostics

import com.nawforce.pkgforce.path.{Location, PathLike, PathLocation}
import com.nawforce.runtime.parsers.CodeParser
import com.nawforce.runtime.parsers.CodeParser.ParserRuleContext

import scala.collection.compat.immutable.ArraySeq
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/** Trait to assist with logging in a context specific way */
trait IssueLogger {
  def log(issue: Issue): Unit

  def logAll(issues: ArraySeq[Issue]): Unit = issues.foreach(log)

  def logAndGet[T](andIssues: IssuesAnd[T]): T = {
    logAll(andIssues.issues)
    andIssues.value
  }

  def logError(path: PathLike, location: Location, message: String): Unit = {
    log(Issue(path, Diagnostic(ERROR_CATEGORY, location, message)))
  }

  def logWarning(path: PathLike, location: Location, message: String): Unit = {
    log(Issue(path, Diagnostic(WARNING_CATEGORY, location, message)))
  }
}

class CatchingLogger extends IssueLogger {
  private var _issues: mutable.ArrayBuffer[Issue] = _

  override def log(issue: Issue): Unit = {
    if (_issues == null)
      _issues = new mutable.ArrayBuffer()
    _issues.addOne(issue)
  }

  def issues: ArraySeq[Issue] = {
    if (_issues != null)
      ArraySeq.unsafeWrapArray(_issues.toArray)
    else {
      Issue.emptyArray
    }
  }
}

sealed trait ParserIssueLogger extends IssueLogger {
  // Get location for an AST context
  def location(context: ParserRuleContext): PathLocation

  def logError(context: ParserRuleContext, message: String): Unit = {
    val l = location(context)
    log(Issue(l.path, Diagnostic(ERROR_CATEGORY, l.location, message)))
  }

  def logWarning(context: ParserRuleContext, message: String): Unit = {
    val l = location(context)
    log(Issue(l.path, Diagnostic(WARNING_CATEGORY, l.location, message)))
  }
}

/** Logger using a specific CodaParser */
class CodeParserLogger(parser: CodeParser) extends ParserIssueLogger {
  private var issueLog: ArrayBuffer[Issue] = _

  def isEmpty: Boolean = issueLog == null

  override def log(issue: Issue): Unit = {
    if (issueLog == null)
      issueLog = new ArrayBuffer[Issue]()
    issueLog.append(issue)
  }

  override def location(context: ParserRuleContext): PathLocation = {
    parser.getPathLocation(context)
  }

  def issues: ArraySeq[Issue] = {
    if (isEmpty)
      Issue.emptyArray
    else
      ArraySeq.unsafeWrapArray(issueLog.toArray)
  }
}
