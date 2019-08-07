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

import java.nio.file.Path
import java.util.concurrent.ConcurrentHashMap

import com.nawforce.cst.UnusedLog
import com.nawforce.documents._
import com.nawforce.types._
import com.nawforce.utils.{DotName, Name}
import com.typesafe.scalalogging.LazyLogging

import scala.collection.JavaConverters._

class Package(val org: Org, _namespace: Name, _paths: Seq[Path], var basePackages: Seq[Package])
  extends PackageDeclaration(_namespace, _paths) with LazyLogging {

  private val labelDeclaration = new LabelDeclaration
  private val pageDeclaration = new PageDeclaration
  private val flowDeclaration = new FlowDeclaration
  private val componentDeclaration = new ComponentDeclaration
  private val types = initTypes()

  private def initTypes() = {
    val types = new ConcurrentHashMap[DotName, TypeDeclaration]()
    types.put(labelDeclaration.typeName.asDotName, labelDeclaration)
    types.put(pageDeclaration.typeName.asDotName, pageDeclaration)
    types.put(flowDeclaration.typeName.asDotName, flowDeclaration)
    types.put(componentDeclaration.typeName.asDotName, componentDeclaration)
    types
  }

  def typeCount: Int = types.size

  def addDependency(pkg: Package): Unit = basePackages = basePackages :+ pkg

  def getApexTypeNames: Seq[String] = {
    types.elements().asScala
      .filter(_.isInstanceOf[ApexTypeDeclaration])
      .map(_.typeName.toString).toSeq
  }

  def getTypes(dotNames: Seq[DotName]): Seq[TypeDeclaration] = {
    dotNames.flatMap(getType)
  }

  /** Find a type using a global name*/
  def getType(dotName: DotName): Option[TypeDeclaration] = {
    val declaration = getPackageType(dotName)
    if (declaration.isEmpty)
      PlatformTypes.getType(dotName)
    else
      declaration
  }

  private def getPackageType(name: DotName): Option[TypeDeclaration] = {
    val declaration = Option(types.get(name)).orElse(getDependentPackageType(name))
    if (declaration.isEmpty && name.isCompound)
      getPackageType(name.headNames).flatMap(_.nestedTypes.find(td => td.name == name.lastName))
    else
      declaration
  }

  private def getDependentPackageType(name: DotName): Option[TypeDeclaration] = {
    basePackages.view.flatMap(pkg => pkg.getPackageType(name)).headOption
  }

  def upsertType(declaration: TypeDeclaration): Unit = {
    types.put(declaration.typeName.asDotName, declaration)
  }

  def deployAll(): Unit = {
    val components = documents.getByExtension(Name("component"))
    logger.debug(s"Found ${components.size} components to parse")
    deployMetadata(components)

    val objects = documents.getByExtension(Name("object"))
    logger.debug(s"Found ${objects.size} custom objects to parse")
    deployMetadata(objects)

    val classes = documents.getByExtension(Name("cls"))
    logger.debug(s"Found ${classes.size} classes to parse")
    deployMetadata(classes)
  }

  def reportUnused(): Unit = {
    new UnusedLog(types.values().asScala)
  }

  /** Deploy some metadata to the org, if already present this will replace the existing metadata */
  def deployMetadata(files: Seq[Path]): Unit = {
    Org.current.withValue(org) {
      loadFromFiles(files)
      validateMetadata()
    }
  }

  private def loadFromFiles(files: Seq[Path]): Unit = {
    val newDeclarations = files.grouped(100).flatMap(group => {
      val parsed = group.par.flatMap(path => {
        org.issues.context.withValue(path) {
          val start = System.currentTimeMillis()

          val tds = DocumentType(path) match {
            case Some(docType: ApexDocument) =>
              ApexTypeDeclaration.create(this, docType.path, StreamProxy.getInputStream(docType.path))
            case Some(docType: CustomObjectDocument) =>
              CustomObjectDeclaration.create(this, docType.path, StreamProxy.getInputStream(docType.path))
            case Some(docType: PlatformEventDocument) =>
              PlatformEventDeclaration.create(this, docType.path, StreamProxy.getInputStream(docType.path))
            case Some(docType: CustomMetadataDocument) =>
              CustomMetadataDeclaration.create(this, docType.path, StreamProxy.getInputStream(docType.path))
            case Some(docType: ComponentDocument) =>
              upsertComponent(namespace, docType)
              Nil
            case _ => Nil
          }

          val end = System.currentTimeMillis()
          logger.debug(s"Parsed $path in ${end - start}ms")
          tds
        }
      })
      System.gc()
      parsed
    })
    newDeclarations.foreach(td => upsertType(td))
  }

  private def upsertComponent(namespace: Name, component: ComponentDocument): Unit = {
    componentDeclaration.upsertComponent(namespace, component)
  }

  private def validateMetadata(): Unit = {
    types.values.parallelStream().filter(_.isInstanceOf[ApexTypeDeclaration])forEach(td => {
      org.issues.context.withValue(td.path) {
        td.validate()
      }
    })
  }
}