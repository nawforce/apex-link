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

import com.nawforce.common.api.{Diagnostic, LineLocation, Package, ServerOps, TypeSummary, ViewInfo}
import com.nawforce.common.diagnostics.ERROR_CATEGORY
import com.nawforce.common.documents.{ApexDocument, DocumentType, MetadataDocumentType}
import com.nawforce.common.finding.TypeRequest
import com.nawforce.common.metadata.DependencyHolder
import com.nawforce.common.names.{TypeLike, TypeName}
import com.nawforce.common.path.{PathFactory, PathLike}
import com.nawforce.common.types.TypeDeclaration
import com.nawforce.common.types.apex.{ApexDeclaration, FullDeclaration}
import com.nawforce.runtime.types.PlatformTypeException

import scala.collection.mutable
import scala.util.hashing.MurmurHash3

trait PackageAPI extends Package {
  this: PackageImpl =>

  override def getNamespace: String = {
    namespace.map(_.value).getOrElse("")
  }

  override def getTypeOfPath(path: String): TypeLike = {
    DocumentType(PathFactory(path)) match {
      // TODO: Handle upsert'd metadata
      case Some(ad: ApexDocument) if isMetadata(ad) =>
        TypeName(ad.name).withNamespace(namespace)
      case _ => null
    }
  }

  override def getPathOfType(typeLike: TypeLike): String = {
    getApexDeclaration(typeLike)
      .map(_.nameLocation.path.toString)
      .orNull
  }

  override def getSummaryOfType(typeLike: TypeLike): TypeSummary = {
    getApexDeclaration(typeLike)
      .map(_.summary)
      .orNull
  }

  override def getDependencies(typeLike: TypeLike, inheritanceOnly: Boolean): Array[TypeLike] = {
    getApexDeclaration(typeLike)
      .map(ad => {
        if (inheritanceOnly) {
          (ad +: ad.nestedTypes).flatMap(td => {
            td.dependencies().flatMap({
              case dt: ApexDeclaration => Some(dt.typeName.asOuterType.asInstanceOf[TypeLike])
              case _ => None
            })
          }).toArray
        } else {
          val dependencies = mutable.Set[TypeName]()
          ad.collectDependenciesByTypeName(dependencies)
          dependencies.toArray[TypeLike]
        }
      })
      .orNull
  }

  override def getDependencyHolders(typeLike: TypeLike): Array[TypeLike] = {
    getApexDeclaration(typeLike)
      .map(_.getTypeDependencyHolders.toArray[TypeLike])
      .orNull
  }

  override def getViewOfType(path: String, contents: String): ViewInfo = {
    getViewOfType(PathFactory(path), Option(contents))
  }

  // Create a view for a type, contents are optional
  private[nawforce] def getViewOfType(path: PathLike, contents: Option[String]): ViewInfo = {
    // Check path is OK
    val dt = DocumentType(path) match {
      case Some(ad: ApexDocument) => Some(ad)
      case _ => None
    }

    if (dt.isEmpty) {
      return ViewInfoImpl(Array(Diagnostic(ERROR_CATEGORY.value, LineLocation(0),
        "Path does not identify a supported metadata type")), None)
    }

    if (!documents.isVisibleFile(path))
      return ViewInfoImpl(Array(Diagnostic(ERROR_CATEGORY.value, LineLocation(0),
        "Path is being ignored in this workspace")), None)

    // Read contents from file if needed
    val source = contents.getOrElse({
      path.read() match {
        case Left(err) =>
          return ViewInfoImpl(Array(Diagnostic(ERROR_CATEGORY.value, LineLocation(0), err)), None)
        case Right(data) =>
          data
      }
    })

    // Shortcut if data unchanged on a FullDeclaration
    val td = dt.flatMap(ad => {
      val typeName = TypeName(ad.name).withNamespace(namespace)
      types.get(typeName)
        .flatMap {
          case fd: FullDeclaration => Some(fd)
          case _ => None
        }
    })

    if (td.nonEmpty && td.get.sourceHash == MurmurHash3.stringHash(source))
      return ViewInfoImpl(Array(), td)

    loadAndValidate(path, source)
  }

  // Load a class and validate it without upsert'ing it to package, upsertFromView will do that
  private def loadAndValidate(path: PathLike, source: String): ViewInfoImpl = {
    val issuesPath = path.absolute.toString
    val issues = org.issues.pop(issuesPath)
    try {
      OrgImpl.current.withValue(org) {
        val td = FullDeclaration.create(this, path, source)
        td.foreach(decl =>
          DependencyHolder.withoutPropagation(){() =>
            decl.validate(withOuterPropagation = false)
          }
        )
        ViewInfoImpl(org.issues.getDiagnostics(issuesPath).toArray, td)
      }
    } finally {
      org.issues.push(issuesPath, issues)
    }
  }

  override def upsertFromView(viewInfo: ViewInfo): Boolean = {
    true
  }

  override def deleteType(typeLike: TypeLike): Boolean = {
    true
  }

  private def getApexDeclaration(typeLike: TypeLike): Option[ApexDeclaration] = {
    try {
      types.get(TypeName(typeLike))
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

  private[nawforce] def deployClasses(documents: Seq[ApexDocument]): Unit = {
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
