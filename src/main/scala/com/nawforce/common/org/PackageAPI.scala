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
import com.nawforce.common.diagnostics.ERROR_CATEGORY
import com.nawforce.common.documents._
import com.nawforce.common.finding.TypeRequest
import com.nawforce.common.path.{PathFactory, PathLike}
import com.nawforce.common.types.apex._
import com.nawforce.common.types.core.{DependentType, TypeDeclaration, TypeId}
import com.nawforce.runtime.types.PlatformTypeException

import scala.collection.mutable
import scala.util.hashing.MurmurHash3

trait PackageAPI extends Package {
  this: PackageImpl =>

  override def getNamespace: String = {
    namespace.map(_.value).getOrElse("")
  }

  override def getTypeOfPath(path: String): TypeIdentifier = {
    val pathLike = PathFactory(path)
    DocumentType(pathLike) match {
      case Some(md: MetadataDocumentType) =>
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
      getApexDeclaration(typeId.typeName)
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
    // Check path is OK
    val dt: Option[ApexDocument] = DocumentType(path) match {
      case Some(ad: ApexDocument) => Some(ad)
      case _ => None
    }

    if (dt.isEmpty) {
      return ViewInfoImpl(isNew = false, path, None, Array(Diagnostic(ERROR_CATEGORY.value, LineLocation(0),
        "Path does not identify a supported metadata type")))
    }

    if (!workspace.isVisibleFile(path))
      return ViewInfoImpl(isNew = false, path, None, Array(Diagnostic(ERROR_CATEGORY.value, LineLocation(0),
        "Path is being ignored in this workspace")))

    // Read contents from file if needed
    val source = contents.getOrElse({
      path.read() match {
        case Left(err) =>
          return ViewInfoImpl(isNew = false, path, None, Array(Diagnostic(ERROR_CATEGORY.value, LineLocation(0), err)))
        case Right(data) =>
          data
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

    if (td.nonEmpty && td.get.sourceHash == MurmurHash3.stringHash(source))
      return ViewInfoImpl(isNew = false, path, td, org.issues.getDiagnostics(path.toString).toArray)

    dt.get match {
      case _: ApexClassDocument => loadAndValidate(path, source, FullDeclaration.create)
      case _: ApexTriggerDocument => loadAndValidate(path, source, TriggerDeclaration.create)
    }
  }

  // Load a class and validate it without upsert'ing it into the package, upsertFromView will do that
  private def loadAndValidate(absPath: PathLike, source: String,
                                   create: (PackageImpl, PathLike, String) => Option[ApexFullDeclaration]): ViewInfoImpl = {
    val issues = org.issues.pop(absPath.toString)
    try {
      OrgImpl.current.withValue(org) {
        val td = create(this, absPath, source)
        td.foreach(validateInIsolation)
        ViewInfoImpl(isNew = true, absPath, td, org.issues.getDiagnostics(absPath.toString).toArray)
      }
    } finally {
      org.issues.push(absPath.toString, issues)
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

  override def upsertFromView(viewInfo: ViewInfo): Boolean = {
    if (!viewInfo.hasType) return false
    val viewInfoImpl = viewInfo.asInstanceOf[ViewInfoImpl]
    val td = viewInfoImpl.td.get

    // Check we are not trying to circumvent the no duplicates rule
    val existing = getApexDeclaration(td.typeName)
    if (existing match {
      case Some(ad: ApexDeclaration) => ad.path != viewInfoImpl.path
      case None => false
      case _ => true
    }) return false

    OrgImpl.current.withValue(org) {
      // If view created TD use it, otherwise we need to create fresh
      val updated : Option[ApexDeclaration] =
        if (viewInfoImpl.isNew) {
          Some(td)
        } else {
          td match {
            case fd: FullDeclaration => FullDeclaration.create(this, fd.source.path, fd.source.code)
            case td: TriggerDeclaration => TriggerDeclaration.create(this, td.source.path, td.source.code)
          }
        }

      updated.foreach(utd => {
        // Carry forward holders to limit need for invalidation chaining
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

  override def deleteType(typeId: TypeIdentifier): Boolean = {
    if (typeId != null && typeId.namespace == namespace) {
      types.get(typeId.typeName) match {
        case Some(_: ApexDeclaration) => types.remove(typeId.typeName); true
        case _ => false
      }
    } else {
      false
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
