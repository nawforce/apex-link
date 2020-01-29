/*
 [The "BSD licence"]
 Copyright (c) 2019 Kevin Jones
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
package com.nawforce.common.api

trait Logger {
  def error(message: String): Unit
  def info(message: String): Unit
  def debug(message: String): Unit
}

class DefaultLogger extends Logger {
  def error(message: String): Unit = {println("[error] " + message)}
  def info(message: String): Unit = {println(message)}
  def debug(message: String): Unit = {println("[debug] " + message)}
}

/* Collection of Ops functions for changing behaviour */
object ServerOps  {
  private var logging: Boolean = false
  private var logger: Logger = new DefaultLogger

  val Trace: String = "TRACE"

  /* Set debug logging categories, only supported option is 'ALL' */
  def setDebugLogging(flags: Array[String]): Unit = {
    logging = flags.contains("ALL")
  }

  def setLogger(newLogger: Logger): Logger = {
    val old = logger
    logger = newLogger
    old
  }

  def error(message: String): Unit = logger.error(message)

  def info(message: String): Unit = logger.info(message)

  def debug(category: String, message: String): Unit = {
    if (logging)
      logger.debug(message)
  }

  def debugTime[T](msg: String)(op: => T): T = {
    val start = System.currentTimeMillis()
    try {
      op
    } finally {
      val end = System.currentTimeMillis()
      ServerOps.debug(ServerOps.Trace, s"$msg in ${end - start}ms")
    }
  }
}
