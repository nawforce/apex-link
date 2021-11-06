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

import com.nawforce.pkgforce.path.{Location, PathLike}
import com.nawforce.runtime.json.JSON

import scala.collection.mutable

/** A simple issue logger. We use a central log for this as not all files we process map cleanly to type declarations
  * which would be the other obvious way to collate them.
  */
class IssueLog {
  private val log = mutable.HashMap[PathLike, List[Issue]]() withDefaultValue List()
  private val possibleMissing = mutable.HashSet[PathLike]()

  /** Access all issues. */
  def getIssues: Map[PathLike, List[Issue]] = log.toMap

  /** Clear the log. */
  def clear(): Unit = {
    log.clear()
  }

  /** Do we have any issues, of any category. */
  def hasMessages: Boolean = log.nonEmpty

  /** Do we have any issues with an error category.*/
  def hasErrors: Boolean =
    log.values.exists(_.exists(issue => DiagnosticCategory.isErrorType(issue.diagnostic.category)))

  /** Do we have any issues with an error or warning category.*/
  def hasErrorsOrWarnings: Boolean =
    log.values.exists(_.exists(issue =>
      DiagnosticCategory.isErrorOrWarningType(issue.diagnostic.category)))

  /** Add an issue. */
  def add(issue: Issue): Unit = {
    assert(issue.path != null)
    log.put(issue.path, issue :: log(issue.path))
    if (issue.diagnostic.category == MISSING_CATEGORY)
      possibleMissing.add(issue.path)
  }

  /** Extract & remove issues for a specific path. */
  def pop(path: PathLike): List[Issue] = {
    val issues = log.getOrElse(path, Nil)
    log.remove(path)
    issues
  }

  /** Replace issues for a specific path. */
  def push(path: PathLike, issues: List[Issue]): Unit = {
    if (issues.nonEmpty)
      log.put(path, issues)
  }

  /** Merge in issues for another log. */
  def merge(issueLog: IssueLog): Unit =
    issueLog.log.foreach(kv => kv._2.foreach(add))

  /** Get issues for a specific file in Diagnostic form. */
  def getDiagnostics(path: PathLike): List[Diagnostic] =
    log.getOrElse(path, Nil).map(_.diagnostic)

  /** Get paths that have a MISSING_CATEGORY issue. */
  def getMissing: Seq[PathLike] = {
    val missing = new mutable.ArrayBuffer[PathLike]()
    possibleMissing.foreach(possible => {
      val issues = log.getOrElse(possible, Nil).filter(_.diagnostic.category == MISSING_CATEGORY)
      if (issues.nonEmpty) {
        missing.append(possible)
      }
    })
    possibleMissing.clear()
    missing.foreach(possibleMissing.add)
    missing.toSeq
  }

  private trait MessageWriter {
    def startOutput(): Unit
    def startDocument(path: PathLike): Unit
    def writeMessage(category: DiagnosticCategory, location: Location, message: String): Unit
    def writeSummary(notShown: Int, total: Int): Unit
    def endDocument(): Unit
    def output: String
  }

  private class TextMessageWriter(showPath: Boolean = true) extends MessageWriter {
    private val buffer = new StringBuilder()

    override def startOutput(): Unit = buffer.clear()
    override def startDocument(path: PathLike): Unit = if (showPath) buffer ++= path.toString + '\n'
    override def writeMessage(category: DiagnosticCategory,
                              location: Location,
                              message: String): Unit =
      buffer ++= s"${category.value}: ${location.displayPosition}: $message\n"
    override def writeSummary(notShown: Int, total: Int): Unit =
      buffer ++= s"$notShown of $total errors not shown" + "\n"
    override def endDocument(): Unit = {}
    override def output: String = buffer.toString()
  }

  private class JSONMessageWriter extends MessageWriter {
    private val buffer = new StringBuilder()
    private var firstDocument: Boolean = _
    private var firstMessage: Boolean = _

    override def startOutput(): Unit = {
      buffer.clear()
      buffer ++= s"""{ "files": [\n"""
      firstDocument = true
    }
    override def startDocument(path: PathLike): Unit = {
      buffer ++= (if (firstDocument) "" else ",\n")
      buffer ++= s"""{ "path": "${encode(path.toString)}", "messages": [\n"""
      firstDocument = false
      firstMessage = true
    }
    override def writeMessage(category: DiagnosticCategory,
                              location: Location,
                              message: String): Unit = {
      buffer ++= (if (firstMessage) "" else ",\n")
      buffer ++= s"""{${location.asJSON}, "category": "${encode(category.value)}", "message": "${encode(
        message)}"}"""
      firstMessage = false
    }
    override def writeSummary(notShown: Int, total: Int): Unit = ()
    override def endDocument(): Unit = buffer ++= "\n]}"
    override def output: String = {
      buffer ++= "]}\n"
      buffer.toString()
    }

    private def encode(value: String): String = {
      JSON.encode(value)
    }
  }

  private def writeMessages(writer: MessageWriter,
                            path: PathLike,
                            warnings: Boolean,
                            unused: Boolean,
                            maxErrors: Int): Unit = {
    val messages = log
      .getOrElse(path, List())
      .filterNot(!warnings && _.diagnostic.category == WARNING_CATEGORY)
      .filterNot(!unused && _.diagnostic.category == UNUSED_CATEGORY)
    if (messages.nonEmpty) {
      writer.startDocument(path)
      var count = 0
      messages
        .sorted(Issue.ordering)
        .foreach(message => {
          if (count < maxErrors) {
            writer.writeMessage(message.diagnostic.category,
                                message.diagnostic.location,
                                message.diagnostic.message)
          }
          count += 1
        })
      if (maxErrors < messages.size)
        writer.writeSummary(messages.size - maxErrors, messages.size)
      writer.endDocument()
    }
  }

  def getMessages(path: PathLike, unused: Boolean = false): String = {
    val writer: MessageWriter = new TextMessageWriter(false)
    writeMessages(writer, path, warnings = true, unused, maxErrors = 10)
    writer.output
  }

  def asString(includeWarnings: Boolean,
               includeUnused: Boolean,
               maxErrors: Int,
               format: String = ""): String = {
    val writer = if (format == "json") new JSONMessageWriter() else new TextMessageWriter()
    writer.startOutput()
    log.keys.toSeq
      .sortBy(_.toString)
      .foreach(path => {
        writeMessages(writer, path, includeWarnings, includeUnused, maxErrors)
      })
    writer.output
  }
}

object IssueLog {
  def apply(issueLog: IssueLog): IssueLog = {
    val newLog = new IssueLog
    newLog.merge(issueLog)
    newLog
  }
}
