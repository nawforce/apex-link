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
package com.nawforce.common.api

import com.nawforce.common.cst.UnusedLog
import com.nawforce.common.diagnostics.IssueLog
import com.nawforce.common.documents._
import com.nawforce.common.finding.TypeRequest
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.path.PathLike
import com.nawforce.common.sfdx.Workspace
import com.nawforce.common.types._

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

class Package(val org: Org, workspace: Workspace, _basePackages: Seq[Package])
  extends PackageDeclaration(workspace, _basePackages) {

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
    types.values
      .filter(_.isInstanceOf[ApexTypeDeclaration])
      .map(_.typeName.toString).toSeq
  }

  def getTypes(typeNames: Seq[TypeName]): Seq[TypeDeclaration] = {
    typeNames.flatMap(typeName => TypeRequest(typeName, this, excludeSObjects = false).toOption)
  }

  def deployAll(): Unit = {
    // Future: Make fully parallel
    val objects = documentsByExtension(Name("object"))
    ServerOps.debug(ServerOps.Trace, s"Found ${objects.size} custom objects to parse")
    deployMetadata(objects)
    Org.current.withValue(org) {
      schemaManager.relatedLists.validate()
    }

    val components = documentsByExtension(Name("component"))
    ServerOps.debug(ServerOps.Trace, s"Found ${components.size} components to parse")
    deployMetadata(components)

    val classes = documentsByExtension(Name("cls"))
    ServerOps.debug(ServerOps.Trace, s"Found ${classes.size} classes to parse")
    deployMetadata(classes)
  }

  def reportUnused(): IssueLog = {
    new UnusedLog(types.values)
  }

  /** Deploy some metadata to the org, if already present this will replace the existing metadata */
  def deployMetadata(files: Seq[MetadataDocumentType]): Unit = {
    Org.current.withValue(org) {
      loadFromFiles(files.map(_.path))
      validateMetadata()
    }
  }

  private def loadFromFiles(files: Seq[PathLike]): Unit = {
    val newDeclarations = files.flatMap(loadFromFile)
    newDeclarations.foreach(td => upsertType(td))
  }

  private def loadFromFile(path: PathLike): Seq[TypeDeclaration] = {
    Org.current.withValue(org) {
      val start = System.currentTimeMillis()

      val tds = DocumentType(path) match {
        case Some(docType: ApexDocument) =>
          val data = docType.path.read()
          ApexTypeDeclaration.create(this, docType.path, data.right.get)
        case Some(docType: SObjectDocument) =>
          SObjectDeclaration.create(this, docType.path)
        case Some(docType: PlatformEventDocument) =>
          SObjectDeclaration.create(this, docType.path)
        case Some(docType: CustomMetadataDocument) =>
          SObjectDeclaration.create(this, docType.path)
        case Some(docType: ComponentDocument) =>
          upsertComponent(namespace, docType)
          Nil
        case _ => Nil
      }

      val end = System.currentTimeMillis()
      ServerOps.debug(ServerOps.Trace, s"Parsed $path in ${end - start}ms")
      tds
    }
  }

  private def upsertComponent(namespace: Option[Name], component: ComponentDocument): Unit = {
    componentDeclaration.upsertComponent(namespace, component)
  }

  private def validateMetadata(): Unit = {
    types.values.filter(_.isInstanceOf[ApexTypeDeclaration]).foreach(td => {
      Org.current.withValue(org) {
        td.validate()
      }
    })
  }
}
