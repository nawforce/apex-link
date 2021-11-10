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
package com.nawforce.pkgforce.diagnostics

import java.io.{PrintWriter, StringWriter}

/** Minimalistic logging, the best kind of logging system. */
trait Logger {
  def info(message: String): Unit

  def debug(message: String): Unit
}

/** Default logger, sends all messages to stderr. */
class DefaultLogger extends Logger {
  def info(message: String): Unit = {
    System.err.println("[info] " + message)
  }
  def debug(message: String): Unit = { System.err.println("[debug] " + message) }
}

/** Collection of functions for logging and changing the logging behaviour. */
object LoggerOps {
  final val NO_LOGGING: Int = 0
  final val INFO_LOGGING: Int = 1
  final val DEBUG_LOGGING: Int = 2

  private var loggingLevel: Integer = NO_LOGGING
  private var logger: Logger = new DefaultLogger

  /** Set debug logging level, one of NO_LOGGING, INFO_LOGGING or DEBUG_LOGGING. */
  def setLoggingLevel(level: Integer): Integer = {
    val current = loggingLevel
    loggingLevel = level
    current
  }

  /** Override the default logger */
  def setLogger(newLogger: Logger): Logger = {
    val current = logger
    logger = newLogger
    current
  }

  /** Log an information message */
  def info(message: String): Unit = {
    if (loggingLevel >= INFO_LOGGING)
      logger.info(message)
  }

  /** Log an exception at info level */
  def info(message: String, ex: Throwable): Unit = {
    info(message)
    info(exceptionMessage(ex))
  }

  def exceptionMessage(ex: Throwable): String = {
    val writer = new StringWriter
    ex.printStackTrace(new PrintWriter(writer))
    writer.toString
  }

  /** Log a debug message */
  def debug(message: String): Unit = {
    if (loggingLevel >= DEBUG_LOGGING)
      logger.debug(message)
  }

  /** Time an operation and info log how long it took */
  def infoTime[T](msg: String, show: Boolean = true, postMsg: String = "")(op: => T): T = {
    time(info, msg, show, postMsg)(op)
  }

  /** Time an operation and debug log how long it took */
  def debugTime[T](msg: String, show: Boolean = true, postMsg: String = "")(op: => T): T = {
    time(debug, msg, show, postMsg)(op)
  }

  /** Time an operation and debug log how long it took */
  private def time[T](log: String => Unit, msg: String, show: Boolean, postMsg: String)(
    op: => T): T = {
    val start = System.currentTimeMillis()
    try {
      op
    } finally {
      val end = System.currentTimeMillis()
      if (show)
        log(s"$msg in ${end - start}ms$postMsg")
    }
  }

}
