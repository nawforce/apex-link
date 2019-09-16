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
package com.nawforce.types

import com.nawforce.names.{DotName, Name, TypeName}
import scalaz.Scalaz._
import scalaz.{Failure, Success, ValidationNel}


object PlatformTypes {
  lazy val nullType: TypeDeclaration = loadType(TypeName.Null)
  lazy val recordSetType: TypeDeclaration = loadType(TypeName.RecordSet)
  lazy val objectType: TypeDeclaration = loadType(TypeName.InternalObject)
  lazy val sObjectType: TypeDeclaration = loadType(TypeName.SObject)
  lazy val typeType: TypeDeclaration = loadType(TypeName.TypeType)
  lazy val stringType: TypeDeclaration = loadType(TypeName.String)
  lazy val idType: TypeDeclaration = loadType(TypeName.Id)
  lazy val integerType: TypeDeclaration = loadType(TypeName.Integer)
  lazy val longType: TypeDeclaration = loadType(TypeName.Long)
  lazy val doubleType: TypeDeclaration = loadType(TypeName.Double)
  lazy val booleanType: TypeDeclaration = loadType(TypeName.Boolean)
  lazy val decimalType: TypeDeclaration = loadType(TypeName.Decimal)
  lazy val dateType: TypeDeclaration = loadType(TypeName.Date)
  lazy val datetimeType: TypeDeclaration = loadType(TypeName.Datetime)
  lazy val timeType: TypeDeclaration = loadType(TypeName.Time)
  lazy val blobType: TypeDeclaration = loadType(TypeName.Blob)
  lazy val locationType: TypeDeclaration = loadType(TypeName.Location)

  private def loadType(typeName: TypeName): TypeDeclaration = {
    PlatformTypeDeclaration.get(typeName).toOption.get
  }

  def getType(typeName: TypeName): Either[String, TypeDeclaration] = {
    val alias = typeAliasMap.getOrElse(typeName, typeName)

    val firstResult = findPlatformType(alias)
    if (firstResult.isSuccess)
      return result(firstResult)

    val systemResult = findPlatformType(alias.wrap(TypeName.System))
    if (systemResult.isSuccess)
      return result(systemResult)

    val schemaResult = findPlatformType(alias.wrap(TypeName.Schema))
    if (schemaResult.isSuccess)
      return result(schemaResult)

    result(firstResult)
  }

  private def result(value: ValidationNel[PlatformTypeGetError, PlatformTypeDeclaration]):
    Either[String, TypeDeclaration] = {
    value match {
      case Success(td) => Right(td)
      case Failure(e) => Left(e.head.toString)
    }
  }

  @scala.annotation.tailrec
  private def findPlatformType(typeName: TypeName): ValidationNel[PlatformTypeGetError, PlatformTypeDeclaration] = {
    PlatformTypeDeclaration.get(typeName) match {
      case Success(td) => td.successNel
      case Failure(_) if typeName.outer.nonEmpty => findPlatformType(typeName.outer.get)
      case Failure(_) => (MissingPlatformType(typeName): PlatformTypeGetError).failureNel
    }
  }

  private val typeAliasMap: Map[TypeName, TypeName] = Map(
    TypeName.Object -> TypeName.InternalObject,
    TypeName.ApexPagesPageReference -> TypeName.PageReference
  )

  def getType(dotName: DotName): Option[TypeDeclaration] = {
    aliasMap.get(dotName).flatMap(getPlatformType)
      .orElse(getPlatformType(dotName))
      .orElse(getPlatformType(dotName.prepend(Name.System)))
      .orElse(getPlatformType(dotName.prepend(Name.Schema)))
  }

  private def getPlatformType(name: DotName): Option[TypeDeclaration] = {
    val declaration = PlatformTypeDeclaration.get(name)
    if (declaration.isEmpty && name.isCompound)
      getPlatformType(name.headNames).flatMap(_.nestedTypes.find(td => td.name == name.lastName))
    else
      declaration
  }

  private val aliasMap: Map[DotName, DotName] = Map(
    DotName(Name.Object) -> DotName(Seq(Name.Internal, Name.Object$)),
    DotName(Seq(Name.ApexPages, Name.PageReference)) -> DotName(Seq(Name.System, Name.PageReference))
  )
}
