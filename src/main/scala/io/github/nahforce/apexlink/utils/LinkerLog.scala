package io.github.nahforce.apexlink.utils

import scala.collection.mutable

class Location(val filepath: String, val line: Integer) {
}

object LinkerLog {
  private val log = mutable.HashMap[String, mutable.HashMap[Int, List[String]]]()
  private val contexts = mutable.Stack[String]()

  def pushContext(context: String) : Unit = {
    contexts.push(context)
  }

  def popContext() : Unit = {
    contexts.pop()
  }

  def context: Option[String] = {
    contexts.headOption
  }

  def ifNotLogAndThrow(condition: Boolean, index: Integer, msg: String) : Unit = {
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

  def hasMessages() : Boolean = {
    log.size != 0
  }

  def dumpMessage() : Unit = {
    log.foreach(context => {
      System.out.println(context._1)
      context._2.foreach(index => {
        index._2.foreach(msg => {
          System.out.println(index._1 + ": " + msg)
        })
      })
    })
  }
}
