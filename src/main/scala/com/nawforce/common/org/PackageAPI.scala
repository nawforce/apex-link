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

import com.nawforce.common.api.{Package, ServerOps, TypeIdentifier, TypeName, TypeSummary, ViewInfo}
import com.nawforce.common.diagnostics.LocalLogger
import com.nawforce.common.documents._
import com.nawforce.common.finding.TypeResolver
import com.nawforce.common.org.stream._
import com.nawforce.common.path.{PathFactory, PathLike}
import com.nawforce.common.types.apex._
import com.nawforce.common.types.core.{DependentType, TypeDeclaration, TypeId}
import com.nawforce.common.types.other._
import com.nawforce.runtime.SourceBlob
import com.nawforce.runtime.parsers.SourceData
import com.nawforce.runtime.types.PlatformTypeException

import scala.collection.mutable

trait PackageAPI extends Package {
  this: PackageImpl =>

  override def getNamespace: String = {
    namespace.map(_.value).getOrElse("")
  }

  override def getTypeOfPath(path: String): TypeIdentifier = {
    getTypeOfPathInternal(PathFactory(path))
  }

  private[nawforce] def getTypeOfPathInternal(path: PathLike): TypeIdentifier = {
    MetadataDocument(path) match {
      case Some(md: MetadataDocument) =>
        types.get(md.typeName(namespace)) match {
          case Some(td: TypeDeclaration) if td.paths.contains(path) => TypeIdentifier(namespace, td.typeName)
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

  override def refresh(path: String, contents: SourceBlob): String = {
    refresh(PathFactory(path), Option(contents))
  }

  private[nawforce] def refresh(path: PathLike, contents: Option[SourceBlob]): String = {
    try {
      OrgImpl.current.withValue(org) {
        refreshInternal(path, contents)
        null
      }
    } catch {
      case ex: IllegalArgumentException => ex.getMessage
    }
  }

  private def refreshInternal(path: PathLike, contents: Option[SourceBlob], invalidateReferences: Boolean=true): Unit = {
    checkPathInPackageOrThrow(path)
    val dt = getUpdateableDocumentTypeOrThrow(path)
    val source = getPathSourceOrThrow(path, contents)

    val td = getFullDeclaration(dt)
    if (td.exists(_.sourceHash == source.hash))
      return

    // Clear errors as might fail to create type
    org.issues.pop(dt.path.toString)

    createType(dt, source).foreach(newType => {
      // Carry forward holders to limit need for invalidation chaining
      val existing = getDependentType(newType.typeName)
      val holders = existing.map(_.getTypeDependencyHolders).getOrElse(mutable.Set())
      newType.updateTypeDependencyHolders(holders)

      // Update and validate
      insertType(newType)
      newType.validate()

      // Re-validate holders to detect errors and release existing refs
      // TODO: This is not handling inheritance or missing invalidation
      // TODO: Check if shape has changed?
      if (invalidateReferences) {
        holders.foreach(typeId => {
          typeId.pkg.packageType(typeId.typeName).foreach {
            case holder: SummaryDeclaration =>
              refreshInternal(holder.path, None, invalidateReferences = false)
            case holder =>
              holder.validate()
          }
        })
      }
    })
  }

  override def getViewOfType(path: String, contents: SourceBlob): ViewInfo = {
    getViewOfType(PathFactory(path), Option(contents))
  }

  private[nawforce] def getViewOfType(path: PathLike, contents: Option[SourceBlob]): ViewInfo = {
    try {
      OrgImpl.current.withValue(org) {
        getViewOfTypeInternal(path, contents)
      }
    } catch {
      case ex: IllegalArgumentException => ViewInfoImpl(IN_ERROR, path, None, Array(), ex.getMessage)
    }
  }

  private def getViewOfTypeInternal(path: PathLike, contents: Option[SourceBlob]): ViewInfo = {
    checkPathInPackageOrThrow(path)
    val dt = getUpdateableDocumentTypeOrThrow(path)
    val source = getPathSourceOrThrow(path, contents)

    val td = getFullDeclaration(dt)
    if (td.exists(_.sourceHash == source.hash))
      return ViewInfoImpl(EXISTING_TYPE, path, td, org.issues.getDiagnostics(path.toString).toArray)

    createTypeInIsolation(dt, source)
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
        val holders = existing.map(_.getTypeDependencyHolders).getOrElse(mutable.Set())
        utd.updateTypeDependencyHolders(holders)

        // Clear old errors as we need to validate the upserted
        org.issues.pop(viewInfoImpl.path.toString)

        // Update and validate
        insertType(utd)
        utd.validate()

        // Revalidate holders to detect errors and release existing refs
        holders.foreach(typeId => {
          typeId.pkg.packageType(typeId.typeName).foreach(_.validate())
        })

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
        val data = doc.path.readSourceData()
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
      typeNames.flatMap(typeName => TypeResolver(typeName, this, excludeSObjects = false).toOption)
    }
  }

  private def createTypeInIsolation(dt: UpdatableMetadata, source: SourceData): ViewInfoImpl = {
    val path = dt.path
    val issues = org.issues.pop(path.toString)
    try {
      val td = createType(dt, source)
      td match {
        case Some(td: ApexFullDeclaration) => validateInIsolation(td)
        case _ => ()
      }
      ViewInfoImpl(NEW_TYPE, path, td, org.issues.getDiagnostics(path.toString).toArray)
    } finally {
      org.issues.push(path.toString, issues)
    }
  }

  private def createType(dt: UpdatableMetadata, source: SourceData): Option[DependentType] = {
    dt match {
      case _: ApexClassDocument => FullDeclaration.create(this, dt.path, source)
      case _: ApexTriggerDocument => TriggerDeclaration.create(this, dt.path, source)
      case _ => Some(createOtherType(dt, source))
    }
  }

  private def createOtherType(dt: UpdatableMetadata, source: SourceData): DependentType = {
    val metadata = MetadataDocumentWithData(dt, source)
    val provider = new OverrideMetadataProvider(Seq(metadata), new DocumentIndexMetadataProvider(documents))
    val queue = Generator.queue(dt, new LocalLogger(org.issues), provider)
    Other(dt, this).merge(new PackageStream(namespace, queue))
  }

  private def getFullDeclaration(dt: MetadataDocument): Option[ApexFullDeclaration] = {
    types.get(dt.typeName(namespace))
      .flatMap {
        case fd: FullDeclaration => Some(fd)
        case td: TriggerDeclaration => Some(td)
        case _ => None
      }
  }

  private def getUpdateableDocumentTypeOrThrow(path: PathLike): UpdatableMetadata = {
    (MetadataDocument (path) collect {
      case dt: UpdatableMetadata => dt
    })
    .getOrElse (throw new IllegalArgumentException (s"Metadata type is not supported for '$path'") )
  }

  private def checkPathInPackageOrThrow(path: PathLike): Unit = {
    Some(workspace.isVisibleFile(path)).filterNot(v => v)
      .foreach{_ => throw new IllegalArgumentException(s"Metadata is not part of this package for '$path'")}
  }

  private def getPathSourceOrThrow(path: PathLike, contents: Option[SourceBlob]): SourceData = {
    resolveSource(path, contents)
      .getOrElse(throw new IllegalArgumentException(s"Metatdata could not be loaded for '$path'"))
  }

  private def resolveSource(path: PathLike, contents: Option[SourceBlob]): Option[SourceData] = {
    contents.map(blob => SourceData(blob)).orElse({
      path.readSourceData() match {
        case Left(_) => None
        case Right(data) => Some(data)
      }
    })
  }
}
