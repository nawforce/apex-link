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
package com.nawforce.utils

import java.nio.file.Path

import com.nawforce.documents.{LineLocation, Location, RangeLocation, TextRange}
import net.liftweb.json._

import scala.collection.mutable
import scala.util.DynamicVariable

class IssueLog {
  val context: DynamicVariable[Path] = new DynamicVariable[Path](null)

  private[this] val lock = new Object()
  private val log = mutable.HashMap[Path, List[(Location, String)]]() withDefaultValue List()

  def clear(): Unit = {
    lock.synchronized {
      log.clear()
    }
  }

  def ifNotLogAndThrow(condition: Boolean, index: Integer, msg: String): Unit = {
    if (ifNotLog(condition, index, msg)) {
      throw new LinkerException()
    }
  }

  def ifNotLog(condition: Boolean, index: Integer, msg: String): Boolean = {
    if (!condition) {
      logMessage(index, msg)
    }
    !condition
  }

  def logMessage(index: Integer, msg: String): Unit = {
    logMessage(LineLocation(context.value, index), msg)
  }

  def logMessage(range: TextRange, msg: String): Unit = {
    logMessage(RangeLocation(context.value, range), msg)
  }

  def logMessage(location: Location, msg: String): Unit = {
    lock.synchronized {
      log.put(location.path, (location, msg) :: log(location.path))
    }
  }

  def hasMessages: Boolean = lock.synchronized {log.nonEmpty}

  private trait MessageWriter {
    def startOutput()
    def startDocument(path: Path)
    def writeMessage(location: Location, message: String)
    def writeSummary(notShown: Int, total: Int)
    def endDocument()
    def output: String
  }

  private class TextMessageWriter(showPath: Boolean) extends MessageWriter {
    private val buffer = new StringBuilder()

    override def startOutput(): Unit = buffer.clear()
    override def startDocument(path: Path): Unit = if (showPath) buffer ++= path.toString + '\n'
    override def writeMessage(location: Location, message: String): Unit =
      buffer ++= location.displayPosition + ": " + message + "\n"
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
    override def startDocument(path: Path): Unit = {
      buffer ++= (if (firstDocument) "" else ",\n")
      buffer ++= s"""{ "path": ${encode(path.toString)}, "messages": [\n"""
      firstDocument = false
      firstMessage = true
    }
    override def writeMessage(location: Location, message: String): Unit = {
      buffer ++= (if (firstMessage) "" else ",\n")
      buffer ++= s"""{${location.asJSON}, "message": ${encode(message)}}"""
      firstMessage = false
    }
    override def writeSummary(notShown: Int, total: Int): Unit = ()
    override def endDocument(): Unit = buffer ++= "\n]}"
    override def output: String = {
      buffer ++= "]}\n"
      buffer.toString()
    }

    private def encode(value: String): String = compactRender(JString(value))
  }

  private def writeMessages(writer: MessageWriter, path: Path, maxErrors: Int): Unit = {
    val messages = log.getOrElse(path, List())
    if (messages.nonEmpty) {
      writer.startDocument(path)
      var count = 0
      messages.sortBy(_._1.startPosition).foreach(message => {
        if (count < maxErrors) {
          writer.writeMessage(message._1, message._2)
        }
        count += 1
      })
      if (count - maxErrors > 0)
        writer.writeSummary(count - maxErrors, count)
      writer.endDocument()
    }
  }

  def getMessages(path: Path, showPath: Boolean = false, maxErrors: Int = 10): String = {
    lock.synchronized {
      val writer: MessageWriter= new TextMessageWriter(showPath = showPath)
      writeMessages(writer, path, maxErrors)
      writer.output
    }
  }

  def asJSON(maxErrors: Int): String = {
    val writer = new JSONMessageWriter()
    writer.startOutput()
    lock.synchronized {
      log.keys.foreach(path => {
        writeMessages(writer, path, maxErrors)
      })
    }
    writer.output
  }

  def dumpMessages(json: Boolean): Unit = {
    val writer: MessageWriter=
      if (json)
        new JSONMessageWriter()
      else
        new TextMessageWriter(true)
    writer.startOutput()
    lock.synchronized {
      log.keys.foreach(path => {
        writeMessages(writer, path, if (json) 100 else 10)
      })
    }
    print(writer.output)
  }
}
