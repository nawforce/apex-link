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

import java.io.{FileInputStream, InputStream}
import java.nio.file.Path
import java.util.concurrent.ConcurrentHashMap

import com.nawforce.documents._
import com.nawforce.types.{ApexTypeDeclaration, CustomObjectDeclaration, LabelDeclaration, PageDeclaration, TypeDeclaration, TypeStore}
import com.nawforce.utils.{DotName, IssueLog, Name}
import com.typesafe.scalalogging.LazyLogging

import scala.util.DynamicVariable

/** Org abstraction, a simulation of the metadata installed on an org. Use the 'current' dynamic variable to access
  * the org being currently worked on. Typically only one org will be being used but some use cases might require
  * multiple. Problems with the metadata are recorded in the the associated issue log.
  */
class Org extends TypeStore with LazyLogging {
  private var packages: List[Package] = Nil
  private val types = new ConcurrentHashMap[DotName, TypeDeclaration]()
  private val inputStreams = new ConcurrentHashMap[Path, InputStream]()
  private val labelDeclaration = new LabelDeclaration
  private val pageDeclaration = new PageDeclaration
  upsertType(labelDeclaration)
  upsertType(pageDeclaration)

  val issues = new IssueLog
  def issuesAsJSON: String = issues.asJSON(maxErrors = 100)
  def typeCount: Int= types.size()

  def clear(): Unit = {
    LogUtils.setLoggingLevel(verbose = false)
    packages = Nil
    types.clear()
    inputStreams.clear()
    issues.clear()
    upsertType(labelDeclaration)
    upsertType(pageDeclaration)
  }

  /** Create a new package in the org, directories are priority ordered. Duplicate detection depends on metadata
    * type. */
  def addPackage(namespace: String, directories: Array[String]): Package = {
    Org.current.withValue(this) {
      packages = Package(this, Name.safeApply(namespace), directories) :: packages
      packages.head
    }
  }

  def getTypes(dotNames: Seq[DotName]): Seq[TypeDeclaration] = {
    dotNames.flatMap(getType)
  }

  /** Find a type using a global name*/
  override def getType(dotName: DotName): Option[TypeDeclaration] = {
    Org.current.withValue(this) {
      val declaration = getOrgType(dotName)
      if (declaration.isEmpty)
        super.getType(dotName)
      else
        declaration
    }
  }

  /** Deploy some metadata to the org, if already present this will replace the existing metadata */
  def deployMetadata(namespace: Name, files: Seq[Path]): Unit = {
    Org.current.withValue(this) {
      loadFromFiles(namespace, files)
      validateMetadata()
    }
  }

  private def loadFromFiles(namespace: Name, files: Seq[Path]): Unit = {
    val newDeclarations = files.grouped(100).flatMap(group => {
      val parsed = group.par.flatMap(path => {
        DocumentType(path) match {
          case Some(docType: ApexDocument) =>
            issues.context.withValue(path) {
              val start = System.currentTimeMillis()
              val typeDeclaration = ApexTypeDeclaration.create(namespace, docType.path, getInputStream(docType.path))
              val end = System.currentTimeMillis()
              logger.debug(s"Parsed ${docType.path.toString} in ${end - start}ms")
              typeDeclaration
            }
          case Some(docType: CustomObjectDocument) =>
            issues.context.withValue(path) {
              val start = System.currentTimeMillis()
              val typeDeclaration = CustomObjectDeclaration.create(namespace, docType.path, getInputStream(docType.path))
              val end = System.currentTimeMillis()
              logger.debug(s"Parsed ${docType.path.toString} in ${end - start}ms")
              typeDeclaration
            }
          case Some(docType: CustomMetadataDocument) =>
            issues.context.withValue(path) {
              val start = System.currentTimeMillis()
              val typeDeclaration = CustomObjectDeclaration.create(namespace, docType.path, getInputStream(docType.path))
              val end = System.currentTimeMillis()
              logger.debug(s"Parsed ${docType.path.toString} in ${end - start}ms")
              typeDeclaration
            }
          case _ => None
        }
      })
      System.gc()
      parsed
    })
    newDeclarations.foreach(td => upsertType(td))
  }

  def setInputStream(path: Path, inputStream: InputStream): Unit = {
    inputStreams.put(path, inputStream)
  }

  private def getInputStream(path: Path): InputStream = {
    Option(inputStreams.get(path)).getOrElse(new FileInputStream(path.toFile))
  }

  private def validateMetadata(): Unit = {
    types.values.parallelStream().forEach(td => {
      issues.context.withValue(td.path) {
        td.validate()
      }
    })
  }

  /** Upsert a type declaration in the Org */
  def upsertType(declaration: TypeDeclaration): Unit = {
    Org.current.withValue(this) {
      types.put(declaration.typeName.asDotName, declaration)
    }
  }

  private def getOrgType(name: DotName): Option[TypeDeclaration] = {
    val declaration = types.get(name)
    if (declaration == null && name.isCompound)
      getOrgType(name.headNames).flatMap(_.nestedTypes.find(td => td.name == name.lastName))
    else
      Option(declaration)
  }
}

object Org {
  val current: DynamicVariable[Org] = new DynamicVariable[Org](null)

  def getType(dotName: DotName): Option[TypeDeclaration] = {
    Org.current.value.getType(dotName)
  }

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
