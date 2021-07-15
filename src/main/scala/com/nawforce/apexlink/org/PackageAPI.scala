/*
 Copyright (c) 2020 Kevin Jones, All rights reserved.
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
 */

package com.nawforce.apexlink.org

import com.nawforce.apexlink.api.{Package, TypeSummary}
import com.nawforce.apexlink.finding.TypeResolver
import com.nawforce.apexlink.finding.TypeResolver.TypeCache
import com.nawforce.apexlink.types.apex._
import com.nawforce.apexlink.types.core.{DependentType, TypeDeclaration, TypeId}
import com.nawforce.pkgforce.diagnostics.LoggerOps
import com.nawforce.pkgforce.documents._
import com.nawforce.pkgforce.names.{TypeIdentifier, TypeName}
import com.nawforce.pkgforce.path.{PathFactory, PathLike}
import upickle.default._

import scala.collection.mutable

trait PackageAPI extends Package {
  this: PackageImpl =>

  override def getNamespaces(withDependents: Boolean): Array[String] = {
    OrgImpl.current.withValue(org) {
      val ns = namespace.map(_.value).getOrElse("")
      if (withDependents)
        (ns +: basePackages.map(_.namespace.map(_.value).getOrElse(""))).toArray
      else
        Array(ns)
    }
  }

  override def isPackagePath(path: String): Boolean = {
    getPackageModule(PathFactory(path)).nonEmpty
  }

  override def getTypeIdentifier(typeName: TypeName): TypeIdentifier = {
    orderedModules.headOption
      .flatMap(module =>
        TypeResolver(typeName, module) match {
          case Right(td: TypeDeclaration) => Some(TypeIdentifier(this.namespace, td.typeName))
          case _                          => None
      })
      .orNull
  }

  override def getTypeOfPath(path: String): TypeIdentifier = {
    getTypeOfPathInternal(PathFactory(path)).map(_.asTypeIdentifier).orNull
  }

  private[nawforce] def getTypeOfPathInternal(path: PathLike): Option[TypeId] = {
    getModuleOfPath(path).flatMap(module => {
      MetadataDocument(path) match {
        // Page handling is weird, they are modeled as static fields
        case Some(pd: PageDocument) =>
          val typeName = pd.typeName(namespace)
          module.pages.fields
            .find(_.name == pd.name)
            .map(_ => { TypeId(module, typeName) })
            .orElse(None)
        case Some(md: MetadataDocument) =>
          module.types.get(md.typeName(namespace)) match {
            case Some(td: TypeDeclaration) if td.paths.contains(path) =>
              Some(TypeId(module, td.typeName))
            case _ => None
          }
        case _ => None
      }
    })
  }

  private def getModuleOfPath(path: PathLike): Option[Module] = {
    orderedModules.view.find(m => m.isVisibleFile(path))
  }

  override def getPathsOfType(typeId: TypeIdentifier): Array[String] = {
    if (typeId != null && typeId.namespace == namespace) {
      orderedModules
        .flatMap(module => {
          module.types
            .get(typeId.typeName)
            .map(td => td.paths.map(_.toString))
        })
        .headOption
        .getOrElse(Array())
    } else {
      Array()
    }
  }

  override def getSummaryOfType(typeId: TypeIdentifier): TypeSummary = {
    if (typeId != null && typeId.namespace == namespace) {
      orderedModules
        .flatMap(_.types.get(typeId.typeName))
        .filter(_.isInstanceOf[ApexDeclaration])
        .map(_.asInstanceOf[ApexDeclaration].summary)
        .headOption
        .orNull
    } else {
      null
    }
  }

  override def getSummaryOfTypeAsJSON(typeId: TypeIdentifier): String = {
    Option(getSummaryOfType(typeId)).map(summary => write(summary)).orNull
  }

  override def getDependencies(typeId: TypeIdentifier,
                               outerInheritanceOnly: Boolean,
                               apexOnly: Boolean): Array[TypeIdentifier] = {
    if (typeId != null && typeId.namespace == namespace) {
      getDependentType(typeId.typeName)
        .map(ad => {
          if (outerInheritanceOnly) {
            ad.dependencies()
              .flatMap({
                case dt: ApexClassDeclaration => Some(dt.outerTypeId.asTypeIdentifier)
                case _                        => None
              })
              .toArray
          } else {
            val typeCache = new TypeCache()
            val dependencies = mutable.Set[TypeId]()
            ad.collectDependenciesByTypeName(dependencies, apexOnly, typeCache)
            dependencies.map(_.asTypeIdentifier).toArray
          }
        })
        .orNull
    } else {
      null
    }
  }

  private def getDependentType(typeName: TypeName): Option[DependentType] = {
    orderedModules.view
      .find(_.types.contains(typeName))
      .flatMap(module =>
        module.types(typeName) match {
          case td: DependentType => Some(td)
          case _                 => None
      })
  }

  override def getDependencyHolders(typeId: TypeIdentifier, apexOnly: Boolean): Array[TypeIdentifier] = {
    if (typeId != null && typeId.namespace == namespace) {
      getDependentType(typeId.typeName)
        .map(
          _.getTypeDependencyHolders.toSet
            .filter(id =>
              !apexOnly || TypeResolver(id.typeName, orderedModules.head).toOption.exists(
                _.isInstanceOf[ApexDeclaration]))
            .map(_.asTypeIdentifier)
            .toArray)
        .orNull
    } else {
      null
    }
  }

  /** Get a array of type identifiers from this packages modules. */
  override def getTypeIdentifiers(apexOnly: Boolean): Array[TypeIdentifier] = {
    modules
      .foldLeft(Set[TypeIdentifier]())((acc, module) =>
        acc ++ module.getMetadataDefinedTypeIdentifiers.filter(id =>
          !apexOnly || TypeResolver(id.typeName, module).toOption.exists(
            _.isInstanceOf[ApexDeclaration])))
          .toArray
  }

  /** Flush all types to the passed cache */
  def flush(pc: ParsedCache): Unit = {
    val context = packageContext
    modules.foreach(module => {
      module.types.values.foreach({
        case ad: ApexClassDeclaration => ad.flush(pc, context)
        case _                        => ()
      })
    })
  }

  override def refresh(path: String): Unit = {
    refresh(PathFactory(path))
  }

  private[nawforce] def refresh(path: PathLike): Unit = {
    org.queueMetadataRefresh(RefreshRequest(this, path))
  }

  /* Replace a path, returns the TypeId of the type that was updated and a Set of TypeIds for the dependency
   * holders of that type. */
  def refreshInternal(path: PathLike): Seq[(TypeId, Set[TypeId])] = {
    modules.find(_.isVisibleFile(path)).map(_.refreshInternal(path)).getOrElse(Seq())
  }

  def refreshBatched(refreshRequests: Seq[RefreshRequest]): Boolean = {
    val requests = refreshRequests.map(r => (r.path, r)).toMap
    if (requests.isEmpty)
      return false

    val splitRequests = requests
      .filter(r => orderedModules.exists(_.isVisibleFile(r._1)))
      .groupBy(r => !r._1.exists)

    // Do removals first to avoid duplicate type issues if source is being moved
    val references = mutable.Set[TypeId]()
    val removed = mutable.Set[TypeId]()
    splitRequests
      .getOrElse(true, Seq())
      .foreach(r => {
        LoggerOps.debug(s"Removing ${r._1}")
        try {
          refreshInternal(r._1).map(refreshResult => {
            removed += refreshResult._1
            references ++= refreshResult._2
          })
        } catch {
          case ex: IllegalArgumentException => LoggerOps.debug(ex.getMessage)
        }
      })
    removed.foreach(references.remove)

    // Then additions or modifications
    splitRequests
      .getOrElse(false, Seq())
      .foreach(r => {
        LoggerOps.debug(s"Refreshing ${r._1}")
        try {
          refreshInternal(r._1).map(refreshResult => {
            references ++= refreshResult._2
            references.remove(refreshResult._1)
          })
        } catch {
          case ex: IllegalArgumentException => LoggerOps.debug(ex.getMessage)
        }
      })

    // Finally batched invalidation
    reValidate(references.toSet ++ getTypesWithMissingIssues.toSet)
    true
  }

  /* Re-validate a set of types. A side effect of re-validation is that summary types are replaced by full types as
   * they are needed to re-establish the dependency graph. This is not done recursively as the full type should
   * be of the exact same shape as the summary it replaces. */
  private def reValidate(references: Set[TypeId]): Unit = {

    val tds = references.flatMap(typeId =>
      typeId.module.packageType(typeId.typeName) match {
        case Some(ref: SummaryDeclaration) =>
          // Replace direct use summary types, no need to revalidate these
          refreshInternal(ref.path)
          None
        case x => x
    })

    // Everything else needs re-validation
    tds.foreach(td => {
      td.paths.foreach(path => org.issues.pop(path.toString))
      td.preReValidate()
    })
    tds.foreach(_.validate())
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
