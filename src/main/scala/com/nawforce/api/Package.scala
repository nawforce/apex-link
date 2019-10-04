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

import com.nawforce.cst.UnusedLog
import com.nawforce.documents._
import com.nawforce.finding.TypeRequest
import com.nawforce.names.{Name, TypeName}
import com.nawforce.types._
import com.nawforce.utils.IssueLog
import com.typesafe.scalalogging.LazyLogging

import scala.collection.JavaConverters._

class Package(val org: Org, _namespace: Option[Name], _paths: Seq[Path], _basePackages: Seq[Package])
  extends PackageDeclaration(_namespace, _paths, _basePackages) with LazyLogging {

  private val schemaManager = new SchemaManager(this)
  private val anyDeclaration = AnyDeclaration(this)
  private val labelDeclaration = LabelDeclaration(this)
  private val pageDeclaration = PageDeclaration(this)
  private val flowDeclaration = FlowDeclaration(this)
  private val componentDeclaration = ComponentDeclaration(this)
  initTypes()

  private def initTypes(): Unit = {
    upsertType(anyDeclaration.typeName, anyDeclaration)
    upsertType(schemaManager.sobjectTypes.typeName, schemaManager.sobjectTypes)
    upsertType(TypeName(schemaManager.sobjectTypes.name), schemaManager.sobjectTypes)
    upsertType(labelDeclaration.typeName, labelDeclaration)
    upsertType(TypeName(labelDeclaration.name), labelDeclaration)
    upsertType(pageDeclaration.typeName, pageDeclaration)
    upsertType(flowDeclaration.typeName, flowDeclaration)
    upsertType(componentDeclaration.typeName, componentDeclaration)
  }

  override def any(): AnyDeclaration = anyDeclaration
  override def schema(): SchemaManager = schemaManager
  override def labels(): LabelDeclaration = labelDeclaration
  override def pages(): PageDeclaration = pageDeclaration

  def typeCount: Int = types.size

  def getApexTypeNames: Seq[String] = {
    types.elements().asScala
      .filter(_.isInstanceOf[ApexTypeDeclaration])
      .map(_.typeName.toString).toSeq
  }

  def getTypes(typeNames: Seq[TypeName]): Seq[TypeDeclaration] = {
    typeNames.flatMap(typeName => TypeRequest(typeName, this).toOption)
  }

  def deployAll(): Unit = {
    val objects = documentsByExtension(Name("object"))
    logger.debug(s"Found ${objects.size} custom objects to parse")
    deployMetadata(objects)
    Org.current.withValue(org) {
      schemaManager.relatedLists.validate()
    }

    val components = documentsByExtension(Name("component"))
    logger.debug(s"Found ${components.size} components to parse")
    deployMetadata(components)

    val classes = documentsByExtension(Name("cls"))
    logger.debug(s"Found ${classes.size} classes to parse")
    deployMetadata(classes)
  }

  def reportUnused(): IssueLog = {
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
    val org = Org.current.value
    val newDeclarations = files.grouped(100).flatMap(group => {
      val parsed = group.par.flatMap(path => {
        Org.current.withValue(org) {
          val start = System.currentTimeMillis()

          val tds = DocumentType(path) match {
            case Some(docType: ApexDocument) =>
              ApexTypeDeclaration.create(this, docType.path, StreamProxy.getInputStream(docType.path))
            case Some(docType: SObjectDocument) =>
              SObjectDeclaration.create(this, docType.path)
            case Some(docType: PlatformEventDocument) =>
              PlatformEventDeclaration.create(this, docType.path, StreamProxy.getInputStream(docType.path))
            case Some(docType: CustomMetadataDocument) =>
              SObjectDeclaration.create(this, docType.path)
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

  private def upsertComponent(namespace: Option[Name], component: ComponentDocument): Unit = {
    componentDeclaration.upsertComponent(namespace, component)
  }

  private def validateMetadata(): Unit = {
    val org = Org.current.value
    types.values.parallelStream().filter(_.isInstanceOf[ApexTypeDeclaration]).forEach(td => {
      Org.current.withValue(org) {
        td.validate()
      }
    })
  }
}