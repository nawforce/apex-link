package io.github.nahforce.apexlink.metadata

import java.nio.file.{Files, Path}

import io.github.nahforce.apexlink.utils.LinkerLog

import scala.collection.mutable

class SymbolReaderContext(val baseDir: Path) {

  private val _labels = mutable.HashMap[String, Label]()
  private val _customObjects = mutable.HashMap[String, CustomObject]()
  private val _pages = mutable.HashMap[String, Page]()
  private val _apexClasses = mutable.HashMap[String, ApexClass]()

  require(Files.isDirectory(baseDir), "Expecting to see a directory at '" + baseDir.toString + "'")

  def getBaseDir : Path = {
    baseDir
  }

  def getClasses : Map[String, ApexClass] = {
    _apexClasses.toMap
  }

  def addLabel(label: Label) : Unit = {
    if (_labels.get(label.fullName).isDefined)
      LinkerLog.logMessage(label.location, "Duplicate label found for '" + label.fullName + "'")
    else
      _labels.put(label.fullName, label)
  }

  def addCustomObject(customObject: CustomObject) : Unit = {
    if (_customObjects.get(customObject.fullName).isDefined)
      LinkerLog.logMessage(customObject.location, "Duplicate custom object found for '" + customObject.fullName + "'")
    else
      _customObjects.put(customObject.fullName, customObject)
  }

  def addPage(page: Page) : Unit = {
    if (_pages.get(page.fullName).isDefined)
      LinkerLog.logMessage(page.location, "Duplicate page found for '" + page.fullName + "'")
    else
      _pages.put(page.fullName, page)
  }

  def addApexClass(apexClass: ApexClass) : Unit = {
    if (_apexClasses.get(apexClass.fullName).isDefined)
      LinkerLog.logMessage(apexClass.location, "Duplicate class found for '" + apexClass.fullName + "'")
    else
      _apexClasses.put(apexClass.fullName, apexClass)
  }

  def report() : Unit  = {
    System.out.println("Labels loaded: " + _labels.size)
    System.out.println("CustomObjects loaded: " + _customObjects.size)
    System.out.println("Pages loaded: " + _pages.size)
    System.out.println("Classes loaded: " + _apexClasses.size)
  }
}
