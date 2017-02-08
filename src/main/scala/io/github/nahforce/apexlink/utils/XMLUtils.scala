package io.github.nahforce.apexlink.utils

import scala.xml.Elem

object XMLUtils {

  def getLine(elem: Elem): Integer = {
    elem.attribute("line").get.toString().toInt
  }

  def getLocation(elem: Elem): Location = {
    new Location(LinkerLog.context.get, getLine(elem))
  }

  def ifNotElemLogAndThrow(elem: Elem, name: String) : Unit = {
    if (ifNotLog(elem.namespace == Constants.sfNamespace, elem, "Expected element in Salesforce namespace, but has namespace '" + elem.namespace + "'")) {
      throw new LinkerException()
    }
    if (ifNotLog(elem.label == name, elem, "Expected element named '" + name + "', but found '" + elem.label + "'")) {
      throw new LinkerException()
    }
  }

  def ifNotLogAndThrow(condition: Boolean, elem: Elem, msg: String) : Unit  = {
    if (ifNotLog(condition, elem, msg)) {
      throw new LinkerException()
    }
  }

  def ifNotLog(condition: Boolean, elem: Elem, msg: String): Boolean = {
    if (!condition) {
      LinkerLog.logMessage(getLine(elem), msg)
    }
    !condition
  }

  def getSingleChildAsString(elem: Elem, name: String): Option[String] = {
    val text = getOptionalSingleChildAsString(elem, name)
    if (text.isEmpty)
      LinkerLog.logMessage(getLine(elem), "Expecting element to have single '" + name + "' child")
    text
  }

  def getOptionalSingleChildAsString(elem: Elem, name: String): Option[String] = {
    getOptionalSingleChild(elem, name).map(e => e.text)
  }

  def getSingleChildAsBoolean(elem: Elem, name: String): Option[Boolean] = {
    val value = getOptionalSingleChildAsBoolean(elem, name)
    if (value.isEmpty)
      LinkerLog.logMessage(getLine(elem), "Expecting element to have single '" + name + "' child")
    value
  }

  def getOptionalSingleChildAsBoolean(elem: Elem, name: String): Option[Boolean] = {
    val matched = getOptionalSingleChild(elem, name)
    if (matched.isDefined) {
      val isBoolean = matched.get.text.matches("true|false")
      if (!isBoolean)
        LinkerLog.logMessage(getLine(matched.get), "Expecting value to be either 'true' or 'false', found '" + matched.get.text + "'")
      Some(isBoolean)
    } else {
      None
    }
  }

  def getSingleChild(elem: Elem, name: String): Option[Elem] = {
    val child = getOptionalSingleChild(elem, name)
    if (child.isEmpty)
      LinkerLog.logMessage(getLine(elem), "Expecting element to have single '" + name + "' child")
    child
  }

  def getOptionalSingleChild(elem: Elem, name: String): Option[Elem] = {
    val matched = (elem \ name).filter(x => x.namespace == Constants.sfNamespace)
    if (matched.length == 1)
      Some(matched.head.asInstanceOf[Elem])
    else {
      None
    }
  }

  def getMultipleChildren(elem: Elem, name: String): List[Elem] = {
    (elem \ name).filter(x => x.namespace == Constants.sfNamespace).asInstanceOf[Seq[Elem]].toList
  }

}
