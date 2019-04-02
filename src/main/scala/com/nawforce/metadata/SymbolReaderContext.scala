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
package com.nawforce.metadata

import java.nio.file.{Files, Path}

import com.nawforce.types.ApexTypeDeclaration
import com.nawforce.utils.{IssueLog, Name}

import scala.collection.mutable

class SymbolReaderContext(val baseDir: Path, verbose: Boolean) {

  private val _labels = mutable.HashMap[String, Label]()
  private val _customObjects = mutable.HashMap[String, CustomObject]()
  private val _apexClasses = mutable.HashMap[Name, ApexTypeDeclaration]()

  require(Files.isDirectory(baseDir), "Expecting to see a directory at '" + baseDir.toString + "'")

  def getBaseDir: Path = baseDir

  def isVerbose: Boolean = verbose

  def getClasses: Map[Name, ApexTypeDeclaration] = _apexClasses.toMap

  def getLabels: Map[String, Label] = _labels.toMap

  def addLabel(label: Label): Unit = {
    if (_labels.get(label.fullName).isDefined)
      IssueLog.logMessage(label.location, "Duplicate label found for '" + label.fullName + "'")
    else
      _labels.put(label.fullName, label)
  }

  def addCustomObject(customObject: CustomObject): Unit = {
    if (_customObjects.get(customObject.fullName).isDefined)
      IssueLog.logMessage(customObject.location, "Duplicate custom object found for '" + customObject.fullName + "'")
    else
      _customObjects.put(customObject.fullName, customObject)
  }

  def addApexClass(apexClass: ApexTypeDeclaration): Unit = {
    _apexClasses.put(apexClass.name, apexClass)
  }

  def report(): Unit = {
    System.out.println("Labels loaded: " + _labels.size)
    System.out.println("CustomObjects loaded: " + _customObjects.size)
    System.out.println("Classes loaded: " + _apexClasses.size)
  }
}
