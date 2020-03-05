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
package com.nawforce.common.pkg

import com.nawforce.common.api.{Package, ServerOps, TypeLike}
import com.nawforce.common.documents.{ApexDocument, DocumentType}
import com.nawforce.common.finding.TypeRequest
import com.nawforce.common.names.TypeName
import com.nawforce.common.org.OrgImpl
import com.nawforce.common.path.PathFactory
import com.nawforce.common.types.TypeDeclaration
import com.nawforce.common.types.apex.{ApexDeclaration, FullDeclaration}
import com.nawforce.runtime.types.PlatformTypeException

import scala.collection.mutable

trait PackageAPI extends Package {
  this: PackageImpl =>

  override def getNamespace: String = {
    namespace.map(_.value).getOrElse("")
  }

  override def getTypeOfPath(path: String): TypeLike = {
    DocumentType(PathFactory(path)) match {
      case Some(ad: ApexDocument) if isMetadata(ad) =>
        TypeName(ad.name).withNamespace(namespace)
      case _ => null
    }
  }

  override def getPathOfType(typeLike: TypeLike): String = {
    try {
      types.get(TypeName(typeLike))
        .map {
          case ad: ApexDeclaration => ad.nameLocation.path.toString
          case _ => null
        }
        .orNull
    } catch {
      case ex: PlatformTypeException =>
        ServerOps.debug(ServerOps.Trace, ex.getMessage); null
    }
  }

  override def getDependencies(typeLike: TypeLike, inheritanceOnly: Boolean): Array[TypeLike] = {
    try {
      types.get(TypeName(typeLike))
        .map {
          case ad: ApexDeclaration =>
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
          case _ => null
        }
        .orNull
    } catch {
      case ex: PlatformTypeException =>
        ServerOps.debug(ServerOps.Trace, ex.getMessage); null
    }
  }

  override def getDependencyHolders(typeLike: TypeLike): Array[TypeLike] = {
    try {
      types.get(TypeName(typeLike))
        .map {
          case ad: ApexDeclaration => ad.getTypeDependencyHolders
          case _ => null
        }
        .orNull
    } catch {
      case ex: PlatformTypeException =>
        ServerOps.debug(ServerOps.Trace, ex.getMessage); null
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
