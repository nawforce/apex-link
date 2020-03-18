package com.nawforce.common.org

import com.nawforce.common.api.{Package, ServerOps, TypeLike}
import com.nawforce.common.documents.{ApexDocument, DocumentType}
import com.nawforce.common.finding.TypeRequest
import com.nawforce.common.names.TypeName
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
          case ad: ApexDeclaration => ad.getTypeDependencyHolders.toArray[TypeLike]
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
