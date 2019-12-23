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
package com.nawforce.common.names

case class TypeName(name: Name, params: Seq[TypeName]=Nil, outer: Option[TypeName]=None) {

  lazy val outerName: Name = outer.map(_.outerName).getOrElse(name)

  def outerNth(index: Int): Option[Name] = {
    if (index > 0 && outer.nonEmpty) {
      outer.get.outerNth(index-1)
    } else if (index == 0) {
      Some(name)
    } else {
      None
    }
  }

  def inner() : TypeName = {
    TypeName(name, params, None)
  }

  def withName(newName: Name): TypeName = {
    if (newName != name)
      TypeName(newName, params, outer)
    else
      this
  }

  def withParams(newParams: Seq[TypeName]): TypeName = {
    if (newParams != params)
      TypeName(name, newParams, outer)
    else
      this
  }

  def withOuter(newOuter: Option[TypeName]): TypeName = {
    if (newOuter != outer)
      TypeName(name, params, newOuter)
    else
      this
  }

  def withTail(newOuter: TypeName): TypeName = {
    if (outer.isEmpty)
      withOuter(Some(newOuter))
    else
      TypeName(name, params, Some(outer.get.withTail(newOuter)))
  }

  def withArraySubscripts(count: Int): TypeName = {
    assert(count >= 0)
    if (count == 0)
      this
    else
      this.asListOf.withArraySubscripts(count-1)
  }

  def withNameReplace(regex: String, replacement: String) : TypeName = {
    TypeName(Name(name.value.replaceAll(regex, replacement)), params, outer)
  }

  def asDotName: DotName = {
    outer match {
      case None => DotName(Seq(name))
      case Some(x) => x.asDotName.append(name)
    }
  }

  def wrap(typeName: TypeName): TypeName = {
    outer match {
      case None => TypeName(name, params, Some(typeName))
      case Some(o) => TypeName(name, params, Some(o.wrap(typeName)))
    }
  }

  def getArrayType: Option[TypeName] = {
    if (name == Name.List$ && outer.contains(TypeName.System) && params.size == 1) {
      params.headOption
    } else if (name == Name.RecordSet$ && outer.contains(TypeName.Internal) && params.size == 1) {
      params.headOption
    } else {
      None
    }
  }

  def getSetOrListType: Option[TypeName] = {
    if ((name == Name.Set$  || name == Name.List$) && outer.contains(TypeName.System) && params.size == 1) {
      params.headOption
    } else {
      None
    }
  }

  def getMapType: Option[(TypeName, TypeName)] = {
    if (name == Name.Map$ && outer.contains(TypeName.System) && params.size == 2) {
      Some(params.head, params(1))
    } else {
      None
    }
  }

  def isStringOrId: Boolean = this == TypeName.String || this == TypeName.Id

  def isList: Boolean = name == Name.List$ && outer.contains(TypeName.System) && params.size == 1
  def asListOf: TypeName = new TypeName(Name.List$, Seq(this), Some(TypeName.System))

  def isRecordSet: Boolean = name == Name.RecordSet$ && outer.contains(TypeName.Internal) && params.size == 1
  def isSObjectList: Boolean = isList && params.head == TypeName.SObject
  def isObjectList: Boolean = isList && params.head == TypeName.InternalObject

  def isBatchable: Boolean = name == Name.Batchable && outer.contains(TypeName.Database)

  override def toString: String = {
    this match {
      case TypeName.Null => "null"
      case TypeName.Any => "any"
      case TypeName.InternalObject => "Object"
      case TypeName.RecordSet => "[SOQL Results]"
      case TypeName.SObjectFieldRowCause$ => "SObjectField"
      case TypeName(Name.DescribeSObjectResult$, Seq(TypeName(name, Nil, None)), Some(TypeName.Internal)) =>
        s"Schema.SObjectType.$name"
      case TypeName(Name.SObjectType$, Seq(TypeName(name, Nil, Some(TypeName.Schema))), Some(TypeName.Internal)) =>
        s"$name.SObjectType"
      case TypeName(Name.SObjectTypeFields$, Seq(TypeName(name, Nil, Some(TypeName.Schema))), Some(TypeName.Internal)) =>
        s"Schema.SObjectType.$name.Fields"
      case TypeName(Name.SObjectTypeFieldSets$, Seq(TypeName(name, Nil, Some(TypeName.Schema))), Some(TypeName.Internal)) =>
        s"Schema.SObjectType.$name.FieldSets"
      case TypeName(Name.SObjectFields$, Seq(TypeName(name, Nil, Some(TypeName.Schema))), Some(TypeName.Internal)) =>
        s"Schema.$name.Fields"

      case _ =>
        (if (outer.isEmpty) "" else outer.get.toString + ".") +
          name.toString +
          (if (params.isEmpty) "" else s"<${params.map(_.toString).mkString(", ")}>")
    }
  }

  def equalsIgnoreParams(other: TypeName): Boolean = {
    this.name == other.name &&
    this.params.size == other.params.size &&
    this.outer.nonEmpty == other.outer.nonEmpty &&
    this.outer.forall(_.equalsIgnoreParams(other.outer.get))
  }
}

object TypeName {
  lazy val Void = TypeName(Name("void"))
  lazy val Object = TypeName(Name("Object"))

  lazy val Internal = TypeName(Name.Internal)
  lazy val Null = TypeName(Name.Null$, Nil, Some(TypeName.Internal))
  lazy val Any = TypeName(Name.Any$, Nil, Some(TypeName.Internal))
  lazy val RecordSet = TypeName(Name.RecordSet$, Seq(TypeName.SObject), Some(TypeName.Internal))
  lazy val InternalObject = TypeName(Name.Object$, Nil, Some(TypeName.Internal))

  lazy val System = TypeName(Name.System)
  lazy val Long = TypeName(Name.Long, Nil, Some(TypeName.System))
  lazy val Integer = TypeName(Name.Integer, Nil, Some(TypeName.System))
  lazy val Double = TypeName(Name.Double, Nil, Some(TypeName.System))
  lazy val Decimal = TypeName(Name.Decimal, Nil, Some(TypeName.System))
  lazy val String = TypeName(Name.String, Nil, Some(TypeName.System))
  lazy val Boolean = TypeName(Name.Boolean, Nil, Some(TypeName.System))
  lazy val Date = TypeName(Name.Date, Nil, Some(TypeName.System))
  lazy val Datetime = TypeName(Name.Datetime, Nil, Some(TypeName.System))
  lazy val Time = TypeName(Name.Time, Nil, Some(TypeName.System))
  lazy val Blob = TypeName(Name.Blob, Nil, Some(TypeName.System))
  lazy val Location = TypeName(Name.Location, Nil, Some(TypeName.System))

  lazy val Id = TypeName(Name.Id, Nil, Some(TypeName.System))
  lazy val TypeType = TypeName(Name.Type, Nil, Some(TypeName.System))
  lazy val PageReference = TypeName(Name.PageReference, Nil, Some(TypeName.System))
  lazy val SObject = TypeName(Name.SObject, Nil, Some(TypeName.System))
  lazy val Label = TypeName(Name.Label, Nil, Some(TypeName.System))

  lazy val ApexPages = TypeName(Name.ApexPages)
  lazy val ApexPagesPageReference = TypeName(Name.PageReference, Nil, Some(TypeName.ApexPages))
  lazy val ApexPagesComponent = TypeName(Name.Component, Nil, Some(TypeName.ApexPages))

  lazy val Schema = TypeName(Name.Schema)
  lazy val SObjectType = TypeName(Name.SObjectType, Nil, Some(TypeName.Schema))
  lazy val SObjectField = TypeName(Name.SObjectField, Nil, Some(TypeName.Schema))
  lazy val FieldSet = TypeName(Name.FieldSet, Nil, Some(TypeName.Schema))
  lazy val DescribeSObjectResult = TypeName(Name.DescribeSObjectResult, Nil, Some(TypeName.Schema))
  lazy val DescribeFieldResult = TypeName(Name.DescribeFieldResult, Nil, Some(TypeName.Schema))
  lazy val SObjectTypeFieldSets = TypeName(Name.SObjectTypeFieldSets, Nil, Some(TypeName.Schema))

  lazy val DescribeSObjectResult$ = TypeName(Name.DescribeSObjectResult$, Nil, Some(TypeName.Internal))
  lazy val SObjectType$ = TypeName(Name.SObjectType$, Nil, Some(TypeName.Internal))
  lazy val SObjectTypeFields$ = TypeName(Name.SObjectTypeFields$, Nil, Some(TypeName.Internal))
  lazy val SObjectTypeFieldSets$ = TypeName(Name.SObjectTypeFieldSets$, Nil, Some(TypeName.Internal))
  lazy val SObjectFields$ = TypeName(Name.SObjectFields$, Nil, Some(TypeName.Internal))
  lazy val SObjectFieldRowCause$ = TypeName(Name.SObjectFieldRowCause$, Nil, Some(TypeName.Internal))

  lazy val Database = TypeName(Name.Database)
  lazy val BatchableContext = TypeName(Name.BatchableContext, Nil, Some(TypeName.Database))

  lazy val User = TypeName(Name.User)
  lazy val UserRecordAccess = TypeName(Name.UserRecordAccess)

  def describeSObjectResultOf(typeName: TypeName): TypeName = DescribeSObjectResult$.withParams(Seq(typeName))
  def sObjectType$(typeName: TypeName): TypeName = SObjectType$.withParams(Seq(typeName))
  def sObjectTypeFields$(typeName: TypeName): TypeName = SObjectTypeFields$.withParams(Seq(typeName))
  def sObjectTypeFieldSets$(typeName: TypeName): TypeName = SObjectTypeFieldSets$.withParams(Seq(typeName))
  def sObjectFields$(typeName: TypeName): TypeName = SObjectFields$.withParams(Seq(typeName))

  def listOf(typeName: TypeName): TypeName = TypeName(Name.List$, Seq(typeName), Some(TypeName.System))
  def mapOf(keyType: TypeName, valueType: TypeName): TypeName = TypeName(Name.Map$, Seq(keyType, valueType), Some(TypeName.System))
  def recordSetOf(typeName: TypeName): TypeName = TypeName(Name.RecordSet$, Seq(typeName), Some(TypeName.Internal))

  def apply(names: Seq[Name]): TypeName = {
    names match {
      case hd +: Nil => new TypeName(hd)
      case hd +: tl => new TypeName(hd, Nil, Some(TypeName(tl)))
    }
  }
}
