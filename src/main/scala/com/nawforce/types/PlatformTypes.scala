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

import com.nawforce.finding.MissingType
import com.nawforce.finding.TypeRequest.TypeRequest
import com.nawforce.names.TypeName

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
    PlatformTypeDeclaration.get(typeName, None).right.get
  }

  /* Get a type, in general don't call this direct, use TypeRequest which will delegate here if
   * needed. The builds over PlatformTypeDeclaration by adding support for typeName aliases, nested
   * types and namespace defaulting.
   */
  def get(typeName: TypeName, from: Option[TypeDeclaration]): TypeRequest = {

    def findOuterOrNestedPlatformType(localTypeName: TypeName): TypeRequest = {
      PlatformTypeDeclaration.get(localTypeName, from) match {
        case Left(_) if localTypeName.outer.nonEmpty =>
          findOuterOrNestedPlatformType(localTypeName.outer.get) match {
            case Left(error) => Left(error)
            case Right(outerTd) => outerTd.nestedTypes.find(_.name == localTypeName.name) match {
              case Some(td) => Right(td)
              case _ => Left(MissingType(localTypeName))
            }
          }
        case Left(_) => Left(MissingType(localTypeName))
        case Right(td) => Right(td)
      }
    }

    val alias = typeAliasMap.getOrElse(typeName, typeName)

    val firstResult = findOuterOrNestedPlatformType(alias)
    if (firstResult.isRight)
      return firstResult

    val systemResult = findOuterOrNestedPlatformType(alias.wrap(TypeName.System))
    if (systemResult.isRight)
      return systemResult

    val schemaResult = findOuterOrNestedPlatformType(alias.wrap(TypeName.Schema))
    if (schemaResult.isRight)
      return schemaResult

    firstResult
  }

  private val typeAliasMap: Map[TypeName, TypeName] = Map(
    TypeName.Object -> TypeName.InternalObject,
    TypeName.ApexPagesPageReference -> TypeName.PageReference
  )
}
