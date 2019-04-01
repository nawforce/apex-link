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

import java.net.URI

import scala.collection.mutable

object IssueLog {
  private val log = mutable.HashMap[URI, List[(Location, String)]]() withDefaultValue List()
  private val contexts = mutable.Stack[URI]()

  def clear(): Unit = {
    log.clear()
    contexts.clear()
  }

  def pushContext(context: URI): Unit = {
    contexts.push(context)
  }

  def popContext(): Unit = {
    contexts.pop()
  }

  def context: Option[URI] = {
    contexts.headOption
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
    if (contexts.nonEmpty) {
      logMessage(LineLocation(contexts.head, index), msg)
    }
  }

  def logMessage(range: TextRange, msg: String): Unit = {
    assert(contexts.nonEmpty)
    logMessage(RangeLocation(contexts.head, range), msg)
  }

  def logMessage(location: Location, msg: String): Unit = {
    log.put(location.uri, (location, msg) :: log(location.uri))
  }

  def hasMessages: Boolean = log.nonEmpty

  def getMessages(uri: URI, showURI: Boolean = false, maxErrors: Int = 10): String = {
    val buffer = new StringBuilder
    val messages = log.getOrElse(uri, List())
    if (messages.nonEmpty) {
      if (showURI)
        buffer ++= uri.toString + "\n"
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

  def dumpMessages(maxErrors: Integer = 10): Unit = {
    log.keys.foreach(uri => {
      System.out.println(getMessages(uri, showURI = true, maxErrors))
    })
  }
}
