/*
 [The "BSD licence"]
 Copyright (c) 2020 Kevin Jones
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
package com.nawforce.common.org

import com.nawforce.common.api.{Diagnostic, LineLocation, Package, ServerOps, TypeIdentifier, TypeName, TypeSummary, ViewInfo}
import com.nawforce.common.diagnostics.{ERROR_CATEGORY, LocalLogger}
import com.nawforce.common.documents._
import com.nawforce.common.finding.TypeRequest
import com.nawforce.common.org.stream._
import com.nawforce.common.path.{PathFactory, PathLike}
import com.nawforce.common.types.apex._
import com.nawforce.common.types.core.{DependentType, TypeDeclaration, TypeId}
import com.nawforce.common.types.other.{InterviewDeclaration, LabelDeclaration, PageDeclaration}
import com.nawforce.runtime.types.PlatformTypeException

import scala.collection.immutable.Queue
import scala.collection.mutable
import scala.util.hashing.MurmurHash3

trait PackageAPI extends Package {
  this: PackageImpl =>

  override def getNamespace: String = {
    namespace.map(_.value).getOrElse("")
  }

  override def getTypeOfPath(path: String): TypeIdentifier = {
    val pathLike = PathFactory(path)
    MetadataDocument(pathLike) match {
      case Some(md: MetadataDocument) =>
        types.get(md.typeName(namespace)) match {
          case Some(td: TypeDeclaration) if td.paths.contains(pathLike) => TypeIdentifier(namespace, td.typeName)
          case _ => null
        }
      case _ => null
    }
  }

  override def getPathsOfType(typeId: TypeIdentifier): Array[String] = {
    if (typeId != null && typeId.namespace == namespace) {
      types.get(typeId.typeName)
        .map(td => td.paths.map(_.toString).toArray)
        .getOrElse(Array())
    } else {
      Array()
    }
  }

  override def getSummaryOfType(typeId: TypeIdentifier): TypeSummary = {
    if (typeId != null && typeId.namespace == namespace) {
      getApexDeclaration(typeId.typeName)
        .map(_.summary)
        .orNull
    } else {
      null
    }
  }

  override def getDependencies(typeId: TypeIdentifier, inheritanceOnly: Boolean): Array[TypeIdentifier] = {
    if (typeId != null && typeId.namespace == namespace) {
      getDependentType(typeId.typeName)
        .map(ad => {
          if (inheritanceOnly) {
            (ad +: ad.nestedTypes).flatMap(td => {
              td.dependencies().flatMap({
                case dt: ApexClassDeclaration => Some(dt.outerTypeId.asTypeIdentifier)
                case _ => None
              })
            }).toArray[TypeIdentifier]
          } else {
            val dependencies = mutable.Set[TypeId]()
            ad.collectDependenciesByTypeName(dependencies)
            dependencies.map(_.asTypeIdentifier).toArray
          }
        })
        .orNull
    } else {
      null
    }
  }

  override def getDependencyHolders(typeId: TypeIdentifier): Array[TypeIdentifier] = {
    if (typeId != null && typeId.namespace == namespace) {
      getDependentType(typeId.typeName)
        .map(_.getTypeDependencyHolders.map(_.asTypeIdentifier).toArray)
        .orNull
    } else {
      null
    }
  }

  override def getViewOfType(path: String, contents: String): ViewInfo = {
    getViewOfType(PathFactory(path), Option(contents))
  }

  // Create a view for a type, contents are optional
  private[nawforce] def getViewOfType(path: PathLike, contents: Option[String]): ViewInfo = {
    // Check path is something we known how to handle & is not being ignored
    val dt: Option[MetadataDocument] = MetadataDocument(path) match {
      case Some(d: ApexDocument) => Some(d)
      case Some(d: LabelsDocument) => Some(d)
      case Some(d: FlowDocument) => Some(d)
      case Some(d: PageDocument) => Some(d)
      case _ => None
    }

    if (dt.isEmpty) {
      return ViewInfoImpl(IN_ERROR, path, None, Array(Diagnostic(ERROR_CATEGORY.value, LineLocation(0),
        "Path does not identify a supported metadata type")))
    }

    if (!workspace.isVisibleFile(path))
      return ViewInfoImpl(IN_ERROR, path, None, Array(Diagnostic(ERROR_CATEGORY.value, LineLocation(0),
        "Path is being ignored in this workspace")))

    // Read contents from file if we were not provided with
    val source = contents.orElse({
      path.read() match {
        case Left(_) => None
        case Right(data) =>Some(data)
      }
    })

    // Shortcut if data unchanged on a FullDeclaration
    val td = dt.flatMap(ad => {
      types.get(ad.typeName(namespace))
        .flatMap {
          case fd: FullDeclaration => Some(fd)
          case td: TriggerDeclaration => Some(td)
          case _ => None
        }
    })

    if (td.nonEmpty && source.nonEmpty && td.get.sourceHash == MurmurHash3.stringHash(source.get))
      return ViewInfoImpl(EXISTING_TYPE, path, td, org.issues.getDiagnostics(path.toString).toArray)

    // Prepare the view for a replacement or deletion
    dt.get match {
      case _: ApexClassDocument => loadApexAndValidate(path, source, FullDeclaration.create)
      case _: ApexTriggerDocument => loadApexAndValidate(path, source, TriggerDeclaration.create)
      case _: LabelsDocument => loadLabelsAndValidate(dt.get, source)
      case _: FlowDocument =>  loadFlowsAndValidate(dt.get, source)
      case _: PageDocument =>  loadPagesAndValidate(dt.get, source)
    }
  }

  // Load a class and validate it without upsert'ing it into the package, upsertFromView will do that
  private def loadApexAndValidate(path: PathLike, source: Option[String],
                                  create: (PackageImpl, PathLike, String) => Option[ApexFullDeclaration]): ViewInfoImpl = {
    val issues = org.issues.pop(path.toString)
    try {
      OrgImpl.current.withValue(org) {
        val td = source.flatMap(data => create(this, path, data))
        td.foreach(validateInIsolation)
        ViewInfoImpl(NEW_TYPE, path, td, org.issues.getDiagnostics(path.toString).toArray)
      }
    } finally {
      org.issues.push(path.toString, issues)
    }
  }

  // Run validation over the TypeDeclaration without mutating package types or dependencies
  private def validateInIsolation(td: ApexFullDeclaration): Unit = {
    val originalTd = types.get(td.typeName)
    try {
      types.put(td.typeName, td)
      td.validate(withPropagation = false)
    } finally {
      types.remove(td.typeName)
      originalTd.foreach(types.put(td.typeName, _))
    }
  }

  private def loadLabelsAndValidate(docType: MetadataDocument, source: Option[String]): ViewInfoImpl = {
    val path = docType.path
    val issues = org.issues.pop(path.toString)
    try {
      OrgImpl.current.withValue(org) {
        // Re-create labels
        var newLabels = LabelDeclaration(this)
        val metadata = source.map(data => MetadataDocumentWithData(docType, data)).toSeq
        val provider = new OverrideMetadataProvider(metadata, new DocumentIndexMetadataProvider(documents))
        val queue = LabelGenerator.queue(new LocalLogger(org.issues), provider, Queue[PackageEvent]())
        newLabels = newLabels.merge(new PackageStream(namespace, queue))
        labels = newLabels

        ViewInfoImpl(NEW_TYPE, path, Some(labels), org.issues.getDiagnostics(path.toString).toArray)
      }
    } finally {
      org.issues.push(path.toString, issues)
    }
  }

  private def loadFlowsAndValidate(docType: MetadataDocument, source: Option[String]): ViewInfoImpl = {
    val path = docType.path
    val issues = org.issues.pop(path.toString)
    try {
      OrgImpl.current.withValue(org) {
        // Re-create Interviews
        var newInterviews = InterviewDeclaration(this)
        val metadata = source.map(data => MetadataDocumentWithData(docType, data)).toSeq
        val provider = new OverrideMetadataProvider(metadata, new DocumentIndexMetadataProvider(documents))
        val queue = FlowGenerator.queue(new LocalLogger(org.issues), provider, Queue[PackageEvent]())
        newInterviews = newInterviews.merge(new PackageStream(namespace, queue))
        interviews = newInterviews

        ViewInfoImpl(NEW_TYPE, path, Some(interviews), org.issues.getDiagnostics(path.toString).toArray)
      }
    } finally {
      org.issues.push(path.toString, issues)
    }
  }

  private def loadPagesAndValidate(docType: MetadataDocument, source: Option[String]): ViewInfoImpl = {
    val path = docType.path
    val issues = org.issues.pop(path.toString)
    try {
      OrgImpl.current.withValue(org) {
        // Re-create Interviews
        var newPages = PageDeclaration(this)
        val metadata = source.map(data => MetadataDocumentWithData(docType, data)).toSeq
        val provider = new OverrideMetadataProvider(metadata, new DocumentIndexMetadataProvider(documents))
        val queue = PageGenerator.queue(new LocalLogger(org.issues), provider, Queue[PackageEvent]())
        newPages = newPages.merge(new PackageStream(namespace, queue))
        pages = newPages

        ViewInfoImpl(NEW_TYPE, path, Some(pages), org.issues.getDiagnostics(path.toString).toArray)
      }
    } finally {
      org.issues.push(path.toString, issues)
    }
  }

  override def upsertFromView(viewInfo: ViewInfo): Boolean = {
    // Reject views with error state
    val viewInfoImpl = viewInfo.asInstanceOf[ViewInfoImpl]
    if (viewInfoImpl.status == IN_ERROR)
      return false

    // Check we are not trying to circumvent the no duplicates rule
    if (!MetadataDocument(viewInfoImpl.path).exists(documents.checkUpsertableAndIndex))
      return false

    // If there is no td this is a deletion
    if (viewInfoImpl.td.isEmpty) {
      val metadata = MetadataDocument(viewInfoImpl.path).get
      documents.remove(metadata)
      org.issues.pop(viewInfoImpl.path.toString)
      return types.remove(metadata.typeName(namespace)).nonEmpty
    }

    // Handle a replacement
    OrgImpl.current.withValue(org) {
      // If view created TD use it, otherwise we need to create fresh
      val td = viewInfoImpl.td.get
      val updated : Option[DependentType] =
        if (viewInfoImpl.status == NEW_TYPE) {
          Some(td)
        } else {
          td match {
            case fd: FullDeclaration => FullDeclaration.create(this, fd.source.path, fd.source.code)
            case td: TriggerDeclaration => TriggerDeclaration.create(this, td.source.path, td.source.code)
          }
        }

      updated.foreach(utd => {
        // Carry forward holders to limit need for invalidation chaining
        val existing = getDependentType(td.typeName)
        utd.updateTypeDependencyHolders(existing.map(_.getTypeDependencyHolders).getOrElse(mutable.Set()))

        // Clear old errors as we need to validate the upserted
        org.issues.pop(viewInfoImpl.path.toString)

        // Update and validate
        types.put(utd.typeName, utd)
        utd.validate()
      })
      updated.nonEmpty
    }
  }

  private def getApexDeclaration(typeName: TypeName): Option[ApexDeclaration] = {
    try {
      types.get(typeName)
        .flatMap {
          case ad: ApexDeclaration => Some(ad)
          case _ => None
        }
    } catch {
      case ex: PlatformTypeException =>
        ServerOps.debug(ServerOps.Trace, ex.getMessage)
        None
    }
  }

  private def getDependentType(typeName: TypeName): Option[DependentType] = {
    try {
      types.get(typeName)
        .flatMap {
          case dt: DependentType => Some(dt)
          case _ => None
        }
    } catch {
      case ex: PlatformTypeException =>
        ServerOps.debug(ServerOps.Trace, ex.getMessage)
        None
    }
  }

  private[nawforce] def deployClasses(documents: Seq[ApexClassDocument]): Unit = {
    OrgImpl.current.withValue(org) {
      val updated = documents.flatMap(doc => {
        val data = doc.path.read()
        val d = ServerOps.debugTime(s"Parsed ${doc.path}") {
          FullDeclaration.create(this, doc.path, data.right.get).toSeq
        }
        d.foreach(upsertMetadata(_))
        d
      })

      updated.foreach(td => td.validate())
    }
  }

  private[nawforce] def findTypes(typeNames: Seq[TypeName]): Seq[TypeDeclaration] = {
    OrgImpl.current.withValue(org) {
      typeNames.flatMap(typeName => TypeRequest(typeName, this, excludeSObjects = false).toOption)
    }
  }
}
