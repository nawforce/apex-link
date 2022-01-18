/*
 Copyright (c) 2019 Kevin Jones, All rights reserved.
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
 */
package com.nawforce.pkgforce.diagnostics

import java.io.{PrintStream, PrintWriter, StringWriter}

/** Minimalistic logging, the best kind of logging system. */
trait Logger {
  def info(message: String): Unit
  def debug(message: String): Unit
  def trace(message: String): Unit
}

/** Default logger, sends all messages to stderr. */
final class DefaultLogger(stream: PrintStream) extends Logger {
  override def info(message: String): Unit = {
    stream.println("[info] " + message)
  }
  override def debug(message: String): Unit = {
    stream.println("[debug] " + message)
  }
  override def trace(message: String): Unit = {
    stream.println("[trace] " + message)
  }
}

/** Collection of functions for logging and changing the logging behaviour. */
object LoggerOps {
  final val NO_LOGGING: Int    = 0
  final val INFO_LOGGING: Int  = 1
  final val DEBUG_LOGGING: Int = 2
  final val TRACE_LOGGING: Int = 3

  private var loggingLevel: Integer = NO_LOGGING
  private var logger: Logger        = new DefaultLogger(System.err)

  /** Set debug logging level, one of NO_LOGGING, INFO_LOGGING, DEBUG_LOGGING or TRACE_LOGGING */
  def setLoggingLevel(level: Integer): Integer = {
    val current = loggingLevel
    loggingLevel = level
    current
  }

  /** Set debug logging level from name, one of none, info, debug or trace */
  def setLoggingLevel(level: String): Integer = {
    setLoggingLevel(
      level.toLowerCase match {
        case "none" => LoggerOps.NO_LOGGING
        case "info" => LoggerOps.INFO_LOGGING
        case "debug" => LoggerOps.DEBUG_LOGGING
        case "trace" => LoggerOps.TRACE_LOGGING
      }
    )
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

  /** Log a trace message */
  def trace(message: String): Unit = {
    if (loggingLevel >= TRACE_LOGGING)
      logger.trace(message)
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
  def traceTime[T](msg: String, show: Boolean = true, postMsg: String = "")(op: => T): T = {
    time(trace, msg, show, postMsg)(op)
  }

  /** Time an operation and debug log how long it took */
  private def time[T](log: String => Unit, msg: String, show: Boolean, postMsg: String)(
    op: => T
  ): T = {
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
