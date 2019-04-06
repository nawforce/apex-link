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

import scala.collection.mutable
import scala.util.DynamicVariable

object IssueLog {
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

  def getMessages(path: Path, showPath: Boolean = false, maxErrors: Int = 10): String = {
    lock.synchronized {
      val buffer = new StringBuilder
      val messages = log.getOrElse(path, List())
      if (messages.nonEmpty) {
        if (showPath)
          buffer ++= path.toString + "\n"
        var count = 0
        messages.sortBy(_._1.line).foreach(message => {
          if (count < maxErrors) {
            buffer ++= message._1.displayPosition + ": " + message._2 + "\n"
          }
          count += 1
        })
        if (count - maxErrors > 0)
          buffer ++= count - maxErrors + " of " + count + " errors not shown"
      }
      buffer.toString()
    }
  }

  def dumpMessages(maxErrors: Integer = 10): Unit = {
    lock.synchronized {
      log.keys.foreach(uri => {
        System.out.println(getMessages(uri, showPath = true, maxErrors))
      })
    }
  }
}
