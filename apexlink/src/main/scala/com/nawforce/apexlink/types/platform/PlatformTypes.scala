/*
 Copyright (c) 2019 Kevin Jones, All rights reserved.
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

package com.nawforce.apexlink.types.platform

import com.nawforce.apexlink.finding.MissingType
import com.nawforce.apexlink.finding.TypeResolver.TypeResponse
import com.nawforce.apexlink.names.{TypeNames, XNames}
import com.nawforce.apexlink.names.TypeNames.{TypeNameUtils, ambiguousAliasMap}
import com.nawforce.apexlink.types.core.TypeDeclaration
import com.nawforce.pkgforce.names.TypeName

import scala.collection.mutable

object PlatformTypes {
  lazy val nullType: TypeDeclaration               = loadType(TypeNames.Null)
  lazy val recordSetType: TypeDeclaration          = loadType(TypeNames.RecordSet)
  lazy val objectType: TypeDeclaration             = loadType(TypeNames.InternalObject)
  lazy val sObjectType: TypeDeclaration            = loadType(TypeNames.SObject)
  lazy val sObjectTypeType: TypeDeclaration        = loadType(TypeNames.SObjectType)
  lazy val sObjectTypeFieldSets: TypeDeclaration   = loadType(TypeNames.SObjectTypeFieldSets)
  lazy val sObjectFieldType: TypeDeclaration       = loadType(TypeNames.SObjectField)
  lazy val sObjectFieldRowCause$ : TypeDeclaration = loadType(TypeNames.SObjectFieldRowCause$)
  lazy val typeType: TypeDeclaration               = loadType(TypeNames.TypeType)
  lazy val stringType: TypeDeclaration             = loadType(TypeNames.String)
  lazy val idType: TypeDeclaration                 = loadType(TypeNames.IdType)
  lazy val integerType: TypeDeclaration            = loadType(TypeNames.Integer)
  lazy val longType: TypeDeclaration               = loadType(TypeNames.Long)
  lazy val doubleType: TypeDeclaration             = loadType(TypeNames.Double)
  lazy val booleanType: TypeDeclaration            = loadType(TypeNames.Boolean)
  lazy val decimalType: TypeDeclaration            = loadType(TypeNames.Decimal)
  lazy val dateType: TypeDeclaration               = loadType(TypeNames.Date)
  lazy val datetimeType: TypeDeclaration           = loadType(TypeNames.Datetime)
  lazy val timeType: TypeDeclaration               = loadType(TypeNames.Time)
  lazy val blobType: TypeDeclaration               = loadType(TypeNames.Blob)
  lazy val locationType: TypeDeclaration           = loadType(TypeNames.Location)
  lazy val componentType: TypeDeclaration          = loadType(TypeNames.ApexPagesComponent)
  lazy val interviewType: TypeDeclaration          = loadType(TypeNames.Interview)
  lazy val apexComponent: TypeDeclaration          = loadType(TypeNames.ApexComponent)
  lazy val chatterComponent: TypeDeclaration       = loadType(TypeNames.ChatterComponent)

  private val typeCache                                   = mutable.Map[TypeName, TypeResponse]()
  private val firedTypes                                  = mutable.Set[TypeName]()
  private var loadingObservers: Seq[PlatformTypeObserver] = Seq()

  private def loadType(typeName: TypeName): TypeDeclaration = {
    PlatformTypeDeclaration.get(typeName, None).getOrElse(throw new NoSuchElementException)
  }

  trait PlatformTypeObserver {
    def loaded(td: PlatformTypeDeclaration): Unit
  }

  def withLoadingObserver[T](observer: PlatformTypeObserver)(op: => T): T = {
    try {
      loadingObservers = loadingObservers :+ observer
      firedTypes.clear()
      op
    } finally {
      loadingObservers = loadingObservers.filterNot(_ eq observer)
    }
  }

  /* Get a type, in general don't call this direct, use TypeRequest which will delegate here if
   * needed. The builds over PlatformTypeDeclaration by adding support for typeName aliases, nested
   * types and namespace defaulting.
   */
  def get(typeName: TypeName, from: Option[TypeDeclaration]): TypeResponse = {

    def findOuterOrNestedPlatformType(localTypeName: TypeName): TypeResponse = {
      PlatformTypeDeclaration.get(localTypeName, from) match {
        case Left(_) if localTypeName.outer.nonEmpty =>
          findOuterOrNestedPlatformType(localTypeName.outer.get) match {
            case Left(error) => Left(error)
            case Right(outerTd) =>
              outerTd.nestedTypes.find(_.name == localTypeName.name) match {
                case Some(td) => Right(td)
                case _        => Left(MissingType(localTypeName))
              }
          }
        case Left(_)   => Left(MissingType(localTypeName))
        case Right(td) => Right(td)
      }
    }

    def findType(localTypeName: TypeName): TypeResponse = {
      findOuterOrNestedPlatformType(localTypeName)
        .orElse(findOuterOrNestedPlatformType(localTypeName.wrap(TypeNames.Schema)))
        .orElse(findOuterOrNestedPlatformType(localTypeName.wrap(TypeNames.System)))
    }

    val aliasedTypeName = typeAliasMap.getOrElse(typeName, typeName)
    val response =
      if (aliasedTypeName.isNonGeneric) {
        typeCache.getOrElseUpdate(typeName, findType(aliasedTypeName))
      } else {
        findType(aliasedTypeName)
      }

    response match {
      case Right(td) =>
        if (!firedTypes.contains(td.typeName)) {
          fireLoadingEvents(td)
          firedTypes.add(td.typeName)
        }
      case _ => ()
    }

    response
  }

  private def fireLoadingEvents(td: TypeDeclaration): Unit = {
    val ptd = td.asInstanceOf[PlatformTypeDeclaration]
    loadingObservers.foreach(observer => observer.loaded(ptd))
  }

  private val typeAliasMap: Map[TypeName, TypeName] = Map(
    TypeNames.Object                 -> TypeNames.InternalObject,
    TypeNames.ApexPagesPageReference -> TypeNames.PageReference,
    TypeNames.Iterator               -> TypeName(XNames.Iterator, Seq(TypeNames.Any), Some(TypeNames.System))
  ) ++ ambiguousAliasMap
}
