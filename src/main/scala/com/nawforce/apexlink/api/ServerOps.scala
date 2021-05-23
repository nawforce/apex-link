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

package com.nawforce.apexlink.api

import com.nawforce.pkgforce.diagnostics.{Logger, LoggerOps}

/** Collection of Ops functions for changing global behaviours */
object ServerOps {
  private var lazyBlocks: Boolean = true
  private var duplicateObjectMonitor: Boolean = false
  private var autoFlush: Boolean = true

  val Trace: String = "TRACE"

  /** Set debug logging categories, only currently supported option is 'ALL', debug logging is disabled by default. */
  def setDebugLogging(flags: Array[String]): Unit = {
    LoggerOps.setDebugLogging(flags)
  }

  /** Override the default logger */
  def setLogger(newLogger: Logger): Logger = {
    LoggerOps.setLogger(newLogger)
  }

  /** Log an information message */
  def info(message: String): Unit = LoggerOps.info(message)

  /** Log an error */
  def error(message: String): Unit = LoggerOps.error(message)

  /** Log a debug message against a category */
  def debug(category: String, message: String): Unit = LoggerOps.debug(category, message)

  /** Time an operation and debug log how long it took */
  def debugTime[T](msg: String, show: Boolean = true, postMsg: String = "")(op: => T): T =
    LoggerOps.debugTime(msg, show, postMsg)(op)

  /** Are we using lazy blocks, this is enabled by default */
  def getLazyBlocks: Boolean = {
    lazyBlocks
  }

  /** Update lazy block flag */
  def setLazyBlocks(enable: Boolean): Unit = {
    lazyBlocks = enable
  }

  /** Is duplicate object monitor enabled, this is disabled by default */
  def getDuplicateObjectMonitoring: Boolean = {
    duplicateObjectMonitor
  }

  /** Update duplicate object monitor flag */
  def setDuplicateObjectMonitoring(enable: Boolean): Unit = {
    duplicateObjectMonitor = enable
  }

  /** Is auto flushing, this is enabled by default */
  def getAutoFlush: Boolean = {
    autoFlush
  }

  /** Update auto flushing flag */
  def setAutoFlush(enable: Boolean): Boolean = {
    val current = autoFlush
    autoFlush = enable
    current
  }

}
