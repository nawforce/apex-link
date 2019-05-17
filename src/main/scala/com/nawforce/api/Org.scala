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
package com.nawforce.api

import java.io.FileInputStream
import java.nio.file.Path
import java.util.concurrent.ConcurrentHashMap

import com.nawforce.documents.{ApexDocument, DocumentType, LineLocation, Location, RangeLocation, TextRange}
import com.nawforce.types.{ApexTypeDeclaration, TypeDeclaration, TypeName, TypeStore}
import com.nawforce.utils.{DotName, IssueLog}
import com.typesafe.scalalogging.LazyLogging

import scala.util.DynamicVariable

/** Org abstraction, a simulation of the metadata installed on an org. Us the 'current' dynamic variable to access
  * the org being currently worked on. Typically only one org will be being used but some use cases might require
  * multiple. Problems with the metadata are recorded in the the associated issue log.
  */
class Org extends TypeStore with LazyLogging {
  private var packages: List[Package] = Nil
  private val types = new ConcurrentHashMap[DotName, TypeDeclaration]()

  val issues = new IssueLog

  /** Create a new package in the org, directories are priority ordered. Duplicate detection depends on metadata
    * type. */
  def addPackage(directories: Array[String]): Package = {
    Org.current.withValue(this) {
      packages = Package(this, directories) :: packages
      packages.head
    }
  }

  /** Find a type in the org */
  override def getType(typeName: TypeName): Option[TypeDeclaration] = {
    Org.current.withValue(this) {
      val dotName = typeName.asDotName
      val declaration = getType(dotName)
      if (declaration.isEmpty)
        super.getType(typeName)
      else
        declaration
    }
  }

  /** Deploy some metadata to the org, if already present this will replace the existing metadata */
  def deployMetadata(files: Seq[Path]): Unit = {
    Org.current.withValue(this) {
      val newDeclarations = files.grouped(100).flatMap(group => {
        val parsed = group.par.flatMap(path => {
          DocumentType(path) match {
            case docType: ApexDocument =>
              issues.context.withValue(path) {
                val start = System.currentTimeMillis()
                val typeDeclaration = ApexTypeDeclaration.create(docType.path, new FileInputStream(docType.path.toFile))
                val end = System.currentTimeMillis()
                logger.debug(s"Parsed ${docType.path.toString} in ${end - start}ms")
                typeDeclaration
              }
          }
        })
        System.gc()
        parsed
      })
      newDeclarations.foreach(upsertType)
    }
  }

  /** Upsert a type declaration in the Org */
  def upsertType(declaration: ApexTypeDeclaration): Unit = {
    Org.current.withValue(this) {
      types.put(declaration.typeName.asDotName, declaration)
    }
  }

  private def getType(name: DotName): Option[TypeDeclaration] = {
    val declaration = types.get(name)
    if (declaration == null && name.isCompound)
      getType(name.headNames).flatMap(_.nestedTypes.find(td => td.name == name.lastName))
    else
      Option(declaration)
  }

}

object Org {
  val current: DynamicVariable[Org] = new DynamicVariable[Org](null)

  def logMessage(index: Integer, msg: String): Unit = {
    Org.current.value.issues.logMessage(index, msg)
  }

  def logMessage(range: TextRange, msg: String): Unit = {
    Org.current.value.issues.logMessage(range, msg)
  }

  def logMessage(location: Location, msg: String): Unit = {
    Org.current.value.issues.logMessage(location, msg)
  }
}
