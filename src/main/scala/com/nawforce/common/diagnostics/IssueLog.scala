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

import com.nawforce.common.api.Diagnostic
import com.nawforce.common.documents.LocationImpl
import com.nawforce.runtime.json.JSON

import scala.collection.mutable

class IssueLog {
  var logCount: Int = 0
  private val log = mutable.HashMap[String, List[Issue]]() withDefaultValue List()

  def getIssues: Map[String, List[Issue]] = log.toMap

  def clear(): Unit = {
   log.clear()
  }

  def add(issue: Issue): Unit = {
    log.put(issue.location.path, issue :: log(issue.location.path))
    logCount += 1
  }

  def hasMessages: Boolean = log.nonEmpty

  def merge(issueLog: IssueLog): Unit = {
    issueLog.log.foreach(kv => kv._2.foreach(add))
  }

  def getDiagnostics(path: String): List[Diagnostic] = {
    log.getOrElse(path, Nil).map(_.toDiagnostic)
  }

  private trait MessageWriter {
    def startOutput()
    def startDocument(path: String)
    def writeMessage(category: IssueCategory, location: LocationImpl, message: String)
    def writeSummary(notShown: Int, total: Int)
    def endDocument()
    def output: String
  }

  private class TextMessageWriter(showPath: Boolean=true) extends MessageWriter {
    private val buffer = new StringBuilder()

    override def startOutput(): Unit = buffer.clear()
    override def startDocument(path: String): Unit = if (showPath) buffer ++= path + '\n'
    override def writeMessage(category: IssueCategory, location: LocationImpl, message: String): Unit =
      buffer ++= s"${category.value}: ${location.displayPosition}: $message\n"
    override def writeSummary(notShown: Int, total: Int): Unit =
      buffer ++= notShown + " of " + total + " errors not shown" + "\n"
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
    override def startDocument(path: String): Unit = {
      buffer ++= (if (firstDocument) "" else ",\n")
      buffer ++= s"""{ "path": ${encode(path)}, "messages": [\n"""
      firstDocument = false
      firstMessage = true
    }
    override def writeMessage(category: IssueCategory, location: LocationImpl, message: String): Unit = {
      buffer ++= (if (firstMessage) "" else ",\n")
      buffer ++= s"""{${location.asJSON}, "category": ${encode(category.value)}, "message": ${encode(message)}}"""
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

  private def writeMessages(writer: MessageWriter, path: String, warnings: Boolean, maxErrors: Int): Unit = {
    val messages = log.getOrElse(path, List())
      .filterNot(!warnings && _.category == WARNING_CATEGORY)
    if (messages.nonEmpty) {
      writer.startDocument(path)
      var count = 0
      messages.sortBy(_.location.startPosition)
        .foreach(message => {
          if (count < maxErrors) {
            writer.writeMessage(message.category, message.location, message.message)
          }
          count += 1
        })
      if (maxErrors < messages.size)
        writer.writeSummary(messages.size - maxErrors, messages.size)
      writer.endDocument()
    }
  }

  def getMessages(path: String, showPath: Boolean = false, maxErrors: Int = 10): String = {
    val writer: MessageWriter= new TextMessageWriter(showPath = showPath)
    writeMessages(writer, path, warnings = true, maxErrors)
    writer.output
  }

  private def writeMessages(writer: MessageWriter, warnings: Boolean, maxErrors: Int): String = {
    writer.startOutput()
    log.keys.toSeq.sortBy(_.toString).foreach(path => {
      writeMessages(writer, path, warnings, maxErrors)
    })
    writer.output

  }
  def asString(warnings: Boolean, maxErrors: Int, asJSON: Boolean=false): String = {
    writeMessages(
      if (asJSON) new JSONMessageWriter() else new TextMessageWriter(),
      warnings, maxErrors)
  }

  def dump(): Unit = {
    println(asString(warnings = true, maxErrors = 100))
  }
}

object IssueLog {
  def apply(issueLog: IssueLog): IssueLog = {
    val newLog = new IssueLog
    newLog.merge(issueLog)
    issueLog
  }
}
