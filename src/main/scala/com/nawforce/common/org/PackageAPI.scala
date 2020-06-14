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
    getTypeOfPathInternal(PathFactory(path)).map(_.asTypeIdentifier).orNull
  }

  private[nawforce] def getTypeOfPathInternal(path: PathLike): Option[TypeId] = {
    MetadataDocument(path) match {
      case Some(md: MetadataDocument) =>
        types.get(md.typeName(namespace)) match {
          case Some(td: TypeDeclaration) if td.paths.contains(path) => Some(TypeId(this, td.typeName))
          case _ => None
        }
      case _ => None
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
    val sourceOpt = resolveSource(path, contents)

    // If we have source & it's not changed then ignore
    if (sourceOpt.exists(s => getFullDeclaration(dt).exists(_.sourceHash == s.hash)))
      return

    // Update internal document tracking
    if (sourceOpt.isEmpty) {
      documents.remove(dt)
    } else {
      if (!documents.checkUpsertableAndIndex(dt))
        throw new IllegalArgumentException(s"Metadata would create duplicate type, ignoring '$path'")
    }

    // Clear errors as might fail to create type
    org.issues.pop(dt.path.toString)

    // Create type & forward holders to limit need for invalidation chaining
    val newType = createType(dt, sourceOpt)
    val typeName = newType.map(_.typeName).getOrElse(dt.typeName(namespace))
    val existingType = getDependentType(typeName)
    val holders = existingType.map(_.getTypeDependencyHolders).getOrElse(mutable.Set())
    newType.foreach(_.updateTypeDependencyHolders(holders))

    // Update and validate
    replaceType(typeName, newType)
    newType.foreach(_.validate())

    // Re-validate references to detect errors and release existing refs
    // TODO: This is not handling inheritance correctly, needs multi-level handling
    if (invalidateReferences) {
      val references = holders ++ getTypesWithMissingIssues
      if (references.nonEmpty) {
        // Check for a shape change
        val sameShape = (existingType, newType) match {
          case (Some(ed: ApexDeclaration), nd: ApexDeclaration) => summaryShape(ed.summary) == summaryShape(nd.summary)
          case _ => false
        }

        if (!sameShape) {
          references.foreach(typeId => {
            typeId.pkg.packageType(typeId.typeName).foreach {
              case ref: SummaryDeclaration =>
                refreshInternal(ref.path, None, invalidateReferences = false)
              case ref =>
                ref.paths.foreach(p => org.issues.pop(p.toString))
                ref.validate()
            }
          })
        }
      }
    }
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
      val td = createType(dt, Some(source))
      td match {
        case Some(td: ApexFullDeclaration) => validateInIsolation(td)
        case _ => ()
      }
      ViewInfoImpl(NEW_TYPE, path, td, org.issues.getDiagnostics(path.toString).toArray)
    } finally {
      org.issues.push(path.toString, issues)
    }
  }

  private def createType(dt: UpdatableMetadata, source: Option[SourceData]): Option[DependentType] = {
    dt match {
      case _: ApexClassDocument =>
        source.flatMap(s => FullDeclaration.create(this, dt.path, s))
      case _: ApexTriggerDocument =>
        source.flatMap(s => TriggerDeclaration.create(this, dt.path, s))
      case _ =>
        Some(createOtherType(dt, source))
    }
  }

  private def createOtherType(dt: UpdatableMetadata, source: Option[SourceData]): DependentType = {
    val metadata = source.map(s => MetadataDocumentWithData(dt, s))
    val provider = new OverrideMetadataProvider(metadata.toSeq, new DocumentIndexMetadataProvider(documents))
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

  private def summaryShape(summary: TypeSummary): TypeSummary = {
    TypeSummary(0, summary.idRange, summary.name, summary.typeName, summary.nature, summary.modifiers,
      summary.superClass, summary.interfaces, summary.blocks, summary.fields, summary.constructors,
      summary.methods, summary.nestedTypes.map(summaryShape), Set.empty)
  }

  private def getTypesWithMissingIssues: Seq[TypeId] = {
    org.issues.getMissing
      .flatMap(path => findTypeIdOfPath(PathFactory(path)))
  }

  private def findTypeIdOfPath(path: PathLike): Option[TypeId] = {
    org.packagesByNamespace.values
      .flatMap(p => p.getTypeOfPathInternal(path))
      .headOption
  }
}
