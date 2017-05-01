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
package io.github.nawforce.apexlink.metadata

import java.nio.file.{Files, Path}

import io.github.nawforce.apexlink.utils.LinkerLog

import scala.collection.mutable

class SymbolReaderContext(val baseDir: Path, verbose: Boolean) {

  private val _labels = mutable.HashMap[String, Label]()
  private val _customObjects = mutable.HashMap[String, CustomObject]()
  private val _pages = mutable.HashMap[String, Page]()
  private val _apexClasses = mutable.HashMap[String, ApexClass]()

  require(Files.isDirectory(baseDir), "Expecting to see a directory at '" + baseDir.toString + "'")

  def getBaseDir: Path = baseDir

  def isVerbose: Boolean = verbose

  def getClasses: Map[String, ApexClass] = _apexClasses.toMap

  def addLabel(label: Label): Unit = {
    if (_labels.get(label.fullName).isDefined)
      LinkerLog.logMessage(label.location, "Duplicate label found for '" + label.fullName + "'")
    else
      _labels.put(label.fullName, label)
  }

  def addCustomObject(customObject: CustomObject): Unit = {
    if (_customObjects.get(customObject.fullName).isDefined)
      LinkerLog.logMessage(customObject.location, "Duplicate custom object found for '" + customObject.fullName + "'")
    else
      _customObjects.put(customObject.fullName, customObject)
  }

  def addPage(page: Page): Unit = {
    if (_pages.get(page.fullName).isDefined)
      LinkerLog.logMessage(page.location, "Duplicate page found for '" + page.fullName + "'")
    else
      _pages.put(page.fullName, page)
  }

  def addApexClass(apexClass: ApexClass): Unit = {
    if (_apexClasses.get(apexClass.fullName).isDefined)
      LinkerLog.logMessage(apexClass.location, "Duplicate class found for '" + apexClass.fullName + "'")
    else
      _apexClasses.put(apexClass.fullName, apexClass)
  }

  def report(): Unit = {
    System.out.println("Labels loaded: " + _labels.size)
    System.out.println("CustomObjects loaded: " + _customObjects.size)
    System.out.println("Pages loaded: " + _pages.size)
    System.out.println("Classes loaded: " + _apexClasses.size)
  }
}
