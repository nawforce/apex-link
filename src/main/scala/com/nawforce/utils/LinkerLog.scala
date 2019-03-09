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

import scala.collection.mutable

class Location(val filepath: String, val line: Integer) {
}

object LinkerLog {
  private val log = mutable.HashMap[String, mutable.HashMap[Int, List[String]]]()
  private val contexts = mutable.Stack[String]()

  def pushContext(context: String): Unit = {
    contexts.push(context)
  }

  def popContext(): Unit = {
    contexts.pop()
  }

  def context: Option[String] = {
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
      logMessage(contexts.head, index, msg)
    }
  }

  def logMessage(location: Location, msg: String): Unit = {
    if (contexts.nonEmpty) {
      logMessage(location.filepath, location.line, msg)
    }
  }

  def logMessage(context: String, index: Integer, msg: String): Unit = {
    if (!log.contains(context)) {
      log.put(context, mutable.HashMap[Int, List[String]]())
    }
    val ctxLog = log(context)
    if (!ctxLog.contains(index)) {
      ctxLog.put(index, List[String]())
    }
    val indexLog = ctxLog(index)
    ctxLog.put(index, msg :: indexLog)
  }

  def hasMessages: Boolean = log.nonEmpty

  def dumpMessages(maxErrors: Integer = 10): Unit = {
    log.foreach(context => {
      System.out.println(context._1)
      val seq = context._2.toIndexedSeq
      val sorted = seq.sortBy { case (line, _) => line }
      var count = 0
      sorted.foreach(messages => {
        messages._2.foreach(message => {
          if (count < maxErrors) {
            println(messages._1 + ": " + message)
          }
          count += 1
        })
      })
      if (count - maxErrors > 0)
        println(count - maxErrors + " of " + count + " errors not shown")
    })
  }
}
