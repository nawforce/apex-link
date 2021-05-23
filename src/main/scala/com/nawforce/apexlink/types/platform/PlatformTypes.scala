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

import java.lang.ref.WeakReference

import com.nawforce.apexlink.finding.MissingType
import com.nawforce.apexlink.finding.TypeResolver.TypeResponse
import com.nawforce.apexlink.names.{TypeNames, _}
import com.nawforce.apexlink.types.core.TypeDeclaration
import com.nawforce.pkgforce.names.TypeName

object PlatformTypes {
  lazy val nullType: TypeDeclaration = loadType(TypeNames.Null)
  lazy val recordSetType: TypeDeclaration = loadType(TypeNames.RecordSet)
  lazy val objectType: TypeDeclaration = loadType(TypeNames.InternalObject)
  lazy val sObjectType: TypeDeclaration = loadType(TypeNames.SObject)
  lazy val customSObject: TypeDeclaration = loadType(TypeNames.CustomSObject$)
  lazy val sObjectTypeType: TypeDeclaration = loadType(TypeNames.SObjectType)
  lazy val sObjectTypeFieldSets: TypeDeclaration = loadType(TypeNames.SObjectTypeFieldSets)
  lazy val sObjectFieldType: TypeDeclaration = loadType(TypeNames.SObjectField)
  lazy val sObjectFieldRowCause$ : TypeDeclaration = loadType(TypeNames.SObjectFieldRowCause$)
  lazy val typeType: TypeDeclaration = loadType(TypeNames.TypeType)
  lazy val stringType: TypeDeclaration = loadType(TypeNames.String)
  lazy val idType: TypeDeclaration = loadType(TypeNames.IdType)
  lazy val integerType: TypeDeclaration = loadType(TypeNames.Integer)
  lazy val longType: TypeDeclaration = loadType(TypeNames.Long)
  lazy val doubleType: TypeDeclaration = loadType(TypeNames.Double)
  lazy val booleanType: TypeDeclaration = loadType(TypeNames.Boolean)
  lazy val decimalType: TypeDeclaration = loadType(TypeNames.Decimal)
  lazy val dateType: TypeDeclaration = loadType(TypeNames.Date)
  lazy val datetimeType: TypeDeclaration = loadType(TypeNames.Datetime)
  lazy val timeType: TypeDeclaration = loadType(TypeNames.Time)
  lazy val blobType: TypeDeclaration = loadType(TypeNames.Blob)
  lazy val locationType: TypeDeclaration = loadType(TypeNames.Location)
  lazy val componentType: TypeDeclaration = loadType(TypeNames.ApexPagesComponent)
  lazy val interviewType: TypeDeclaration = loadType(TypeNames.Interview)
  lazy val apexComponent: TypeDeclaration = loadType(TypeNames.ApexComponent)
  lazy val chatterComponent: TypeDeclaration = loadType(TypeNames.ChatterComponent)

  private def loadType(typeName: TypeName): TypeDeclaration = {
    PlatformTypeDeclaration.get(typeName, None).getOrElse(throw new NoSuchElementException)
  }

  trait PlatformTypeObserver {
    def loaded(td: PlatformTypeDeclaration): Unit
  }

  private var loadingObservers: Seq[WeakReference[PlatformTypeObserver]] = Seq()

  def addLoadingObserver(observer: PlatformTypeObserver): Unit = {
    loadingObservers = loadingObservers :+ new WeakReference(observer)
  }

  /* Get a type, in general don't call this direct, use TypeRequest which will delegate here if
   * needed. The builds over PlatformTypeDeclaration by adding support for typeName aliases, nested
   * types and namespace defaulting.
   */
  def get(typeName: TypeName,
          from: Option[TypeDeclaration],
          excludeSObjects: Boolean = false): TypeResponse = {

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

    val alias = typeAliasMap.getOrElse(typeName, typeName)

    // TODO: Tidy up with Either orElse
    val firstResult = findOuterOrNestedPlatformType(alias)
    if (firstResult.isRight) {
      fireLoadingEvents(firstResult.getOrElse(throw new NoSuchElementException))
      return firstResult
    }

    if (!excludeSObjects) {
      val schemaResult = findOuterOrNestedPlatformType(alias.wrap(TypeNames.Schema))
      if (schemaResult.isRight) {
        fireLoadingEvents(schemaResult.getOrElse(throw new NoSuchElementException))
        return schemaResult
      }
    }

    val systemResult = findOuterOrNestedPlatformType(alias.wrap(TypeNames.System))
    if (systemResult.isRight) {
      fireLoadingEvents(systemResult.getOrElse(throw new NoSuchElementException))
      return systemResult
    }

    firstResult
  }

  private def fireLoadingEvents(td: TypeDeclaration): Unit = {
    val ptd = td.asInstanceOf[PlatformTypeDeclaration]
    loadingObservers = loadingObservers.filter(_.get != null)
    loadingObservers.foreach(wr => wr.get.loaded(ptd))
  }

  private val typeAliasMap: Map[TypeName, TypeName] = Map(
    TypeNames.Object -> TypeNames.InternalObject,
    TypeNames.ApexPagesPageReference -> TypeNames.PageReference)
}
