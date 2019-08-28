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

import com.nawforce.utils.{DotName, Name}

object PlatformTypes {
  lazy val nullType: TypeDeclaration = getType(DotName(Seq(Name.Internal, Name.Null$))).get
  lazy val recordSetType: TypeDeclaration = getType(DotName(Seq(Name.Internal, Name.RecordSet$))).get
  lazy val sObjectType: TypeDeclaration = getType(DotName(Seq(Name.System, Name.SObject))).get
  lazy val typeType: TypeDeclaration = getType(DotName(Seq(Name.System, Name.Type))).get
  lazy val objectType: TypeDeclaration = getType(DotName(Seq(Name.Internal, Name.Object$))).get
  lazy val stringType: TypeDeclaration = getType(DotName(Seq(Name.System, Name.String))).get
  lazy val idType: TypeDeclaration = getType(DotName(Seq(Name.System, Name.Id))).get
  lazy val booleanType: TypeDeclaration = getType(DotName(Seq(Name.System, Name.Boolean))).get
  lazy val decimalType: TypeDeclaration = getType(DotName(Seq(Name.System, Name.Decimal))).get
  lazy val dateType: TypeDeclaration = getType(DotName(Seq(Name.System, Name.Date))).get
  lazy val datetimeType: TypeDeclaration = getType(DotName(Seq(Name.System, Name.Datetime))).get
  lazy val timeType: TypeDeclaration = getType(DotName(Seq(Name.System, Name.Time))).get
  lazy val blobType: TypeDeclaration = getType(DotName(Seq(Name.System, Name.Blob))).get
  lazy val locationType: TypeDeclaration = getType(DotName(Seq(Name.System, Name.Location))).get

  def getType(typeName: TypeName): Option[TypeDeclaration] = {
    getType(typeName.asDotName)
  }

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
