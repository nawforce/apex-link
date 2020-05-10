package com.nawforce.common.names

import com.nawforce.common.api.{Name, TypeName}
import upickle.default.{macroRW, ReadWriter => RW}

object TypeNames {
  implicit val rw: RW[TypeName] = macroRW

  lazy val Void: TypeName = TypeName(Name("void"))
  lazy val Object: TypeName = TypeName(Name("Object"))

  lazy val Internal: TypeName = TypeName(Names.Internal)
  lazy val Null: TypeName = TypeName(Names.Null$, Nil, Some(TypeNames.Internal))
  lazy val Any: TypeName = TypeName(Names.Any$, Nil, Some(TypeNames.Internal))
  lazy val RecordSet: TypeName = TypeName(Names.RecordSet$, Seq(TypeNames.SObject), Some(TypeNames.Internal))
  lazy val InternalObject: TypeName = TypeName(Names.Object$, Nil, Some(TypeNames.Internal))

  lazy val System: TypeName = TypeName(Names.System)
  lazy val Long: TypeName = TypeName(Names.Long, Nil, Some(TypeNames.System))
  lazy val Integer: TypeName = TypeName(Names.Integer, Nil, Some(TypeNames.System))
  lazy val Double: TypeName = TypeName(Names.Double, Nil, Some(TypeNames.System))
  lazy val Decimal: TypeName = TypeName(Names.Decimal, Nil, Some(TypeNames.System))
  lazy val String: TypeName = TypeName(Names.String, Nil, Some(TypeNames.System))
  lazy val Boolean: TypeName = TypeName(Names.Boolean, Nil, Some(TypeNames.System))
  lazy val Date: TypeName = TypeName(Names.Date, Nil, Some(TypeNames.System))
  lazy val Datetime: TypeName = TypeName(Names.Datetime, Nil, Some(TypeNames.System))
  lazy val Time: TypeName = TypeName(Names.Time, Nil, Some(TypeNames.System))
  lazy val Blob: TypeName = TypeName(Names.Blob, Nil, Some(TypeNames.System))
  lazy val Location: TypeName = TypeName(Names.Location, Nil, Some(TypeNames.System))
  lazy val Address: TypeName = TypeName(Names.Address, Nil, Some(TypeNames.System))

  lazy val Id: TypeName = TypeName(Names.Id, Nil, Some(TypeNames.System))
  lazy val TypeType: TypeName = TypeName(Names.Type, Nil, Some(TypeNames.System))
  lazy val PageReference: TypeName = TypeName(Names.PageReference, Nil, Some(TypeNames.System))
  lazy val SObject: TypeName = TypeName(Names.SObject, Nil, Some(TypeNames.System))
  lazy val Label: TypeName = TypeName(Names.Label, Nil, Some(TypeNames.System))

  lazy val ApexPages: TypeName = TypeName(Names.ApexPages)
  lazy val ApexPagesPageReference: TypeName = TypeName(Names.PageReference, Nil, Some(TypeNames.ApexPages))
  lazy val ApexPagesComponent: TypeName = TypeName(Names.Component, Nil, Some(TypeNames.ApexPages))
  lazy val ApexComponent: TypeName = TypeName(Names.Apex, Nil, Some(TypeNames.Component))
  lazy val ChatterComponent: TypeName = TypeName(Names.Chatter, Nil, Some(TypeNames.Component))

  lazy val Schema: TypeName = TypeName(Names.Schema)
  lazy val SObjectType: TypeName = TypeName(Names.SObjectType, Nil, Some(TypeNames.Schema))
  lazy val SObjectField: TypeName = TypeName(Names.SObjectField, Nil, Some(TypeNames.Schema))
  lazy val FieldSet: TypeName = TypeName(Names.FieldSet, Nil, Some(TypeNames.Schema))
  lazy val DescribeSObjectResult: TypeName = TypeName(Names.DescribeSObjectResult, Nil, Some(TypeNames.Schema))
  lazy val DescribeFieldResult: TypeName = TypeName(Names.DescribeFieldResult, Nil, Some(TypeNames.Schema))
  lazy val SObjectTypeFieldSets: TypeName = TypeName(Names.SObjectTypeFieldSets, Nil, Some(TypeNames.Schema))

  lazy val DescribeSObjectResult$: TypeName = TypeName(Names.DescribeSObjectResult$, Nil, Some(TypeNames.Internal))
  lazy val SObjectType$: TypeName = TypeName(Names.SObjectType$, Nil, Some(TypeNames.Internal))
  lazy val SObjectTypeFields$: TypeName = TypeName(Names.SObjectTypeFields$, Nil, Some(TypeNames.Internal))
  lazy val SObjectTypeFieldSets$: TypeName = TypeName(Names.SObjectTypeFieldSets$, Nil, Some(TypeNames.Internal))
  lazy val SObjectFields$: TypeName = TypeName(Names.SObjectFields$, Nil, Some(TypeNames.Internal))
  lazy val SObjectFieldRowCause$: TypeName = TypeName(Names.SObjectFieldRowCause$, Nil, Some(TypeNames.Internal))
  lazy val Trigger$: TypeName = TypeName(Names.Trigger$, Nil, Some(TypeNames.Internal))

  lazy val Database: TypeName = TypeName(Names.Database)
  lazy val BatchableContext: TypeName = TypeName(Names.BatchableContext, Nil, Some(TypeNames.Database))

  lazy val User: TypeName = TypeName(Names.User)
  lazy val UserRecordAccess: TypeName = TypeName(Names.UserRecordAccess)

  lazy val Flow: TypeName = TypeName(Names.Flow)
  lazy val Interview: TypeName = TypeName(Names.Interview, Nil, Some(TypeNames.Flow))

  lazy val Component: TypeName = TypeName(Names.Component, Nil, None)

  lazy val Page: TypeName = TypeName(Names.Page, Nil, None)

  def describeSObjectResultOf(typeName: TypeName): TypeName = DescribeSObjectResult$.withParams(Seq(typeName))
  def sObjectType$(typeName: TypeName): TypeName = SObjectType$.withParams(Seq(typeName))
  def sObjectTypeFields$(typeName: TypeName): TypeName = SObjectTypeFields$.withParams(Seq(typeName))
  def sObjectTypeFieldSets$(typeName: TypeName): TypeName = SObjectTypeFieldSets$.withParams(Seq(typeName))
  def sObjectFields$(typeName: TypeName): TypeName = SObjectFields$.withParams(Seq(typeName))
  def trigger(typeName: TypeName): TypeName = Trigger$.withParams(Seq(typeName))

  def listOf(typeName: TypeName): TypeName = TypeName(Names.List$, Seq(typeName), Some(TypeNames.System))
  def mapOf(keyType: TypeName, valueType: TypeName): TypeName = TypeName(Names.Map$, Seq(keyType, valueType), Some(TypeNames.System))
  def recordSetOf(typeName: TypeName): TypeName = TypeName(Names.RecordSet$, Seq(typeName), Some(TypeNames.Internal))

}
