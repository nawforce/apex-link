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

package com.nawforce.apexlink.names

import com.nawforce.pkgforce.memory.InternCache
import com.nawforce.pkgforce.names.{DotName, Name, Names, TypeName}

import scala.collection.immutable.ArraySeq

object TypeNames extends InternCache[TypeName] {

  lazy val Void: TypeName   = TypeName(Name("void")).intern
  lazy val Object: TypeName = TypeName(Name("Object")).intern

  lazy val Internal: TypeName = TypeName(Names.Internal).intern
  lazy val Null: TypeName     = TypeName(Names.Null$, Nil, Some(TypeNames.Internal)).intern
  lazy val Any: TypeName      = TypeName(Names.Any$, Nil, Some(TypeNames.Internal)).intern
  lazy val RecordSet: TypeName =
    TypeName(Names.RecordSet$, Seq(TypeNames.SObject), Some(TypeNames.Internal)).intern
  lazy val InternalObject: TypeName = TypeName(Names.Object$, Nil, Some(TypeNames.Internal)).intern
  lazy val InternalInterface: TypeName =
    TypeName(Names.Interface$, Nil, Some(TypeNames.Internal)).intern

  lazy val System: TypeName   = TypeName(Names.System).intern
  lazy val Long: TypeName     = TypeName(Names.Long, Nil, Some(TypeNames.System)).intern
  lazy val Integer: TypeName  = TypeName(Names.Integer, Nil, Some(TypeNames.System)).intern
  lazy val Double: TypeName   = TypeName(Names.Double, Nil, Some(TypeNames.System)).intern
  lazy val Decimal: TypeName  = TypeName(Names.Decimal, Nil, Some(TypeNames.System)).intern
  lazy val String: TypeName   = TypeName(Names.String, Nil, Some(TypeNames.System)).intern
  lazy val Boolean: TypeName  = TypeName(Names.Boolean, Nil, Some(TypeNames.System)).intern
  lazy val Date: TypeName     = TypeName(Names.Date, Nil, Some(TypeNames.System)).intern
  lazy val Datetime: TypeName = TypeName(Names.Datetime, Nil, Some(TypeNames.System)).intern
  lazy val Time: TypeName     = TypeName(Names.Time, Nil, Some(TypeNames.System)).intern
  lazy val Blob: TypeName     = TypeName(Names.Blob, Nil, Some(TypeNames.System)).intern
  lazy val Location: TypeName = TypeName(Names.Location, Nil, Some(TypeNames.System)).intern
  lazy val Address: TypeName  = TypeName(Names.Address, Nil, Some(TypeNames.System)).intern

  lazy val IdType: TypeName   = TypeName(Names.Id, Nil, Some(TypeNames.System)).intern
  lazy val TypeType: TypeName = TypeName(Names.Type, Nil, Some(TypeNames.System)).intern
  lazy val PageReference: TypeName =
    TypeName(Names.PageReference, Nil, Some(TypeNames.System)).intern
  lazy val SObject: TypeName = TypeName(Names.SObject, Nil, Some(TypeNames.System)).intern

  lazy val RecordType: TypeName = TypeName(Name("RecordType"), Nil, Some(TypeNames.Schema)).intern
  lazy val Note: TypeName       = TypeName(Name("Note"), Nil, Some(TypeNames.Schema)).intern
  lazy val NoteAndAttachment: TypeName =
    TypeName(Name("NoteAndAttachment"), Nil, Some(TypeNames.Schema)).intern
  lazy val Attachment: TypeName = TypeName(Name("Attachment"), Nil, Some(TypeNames.Schema)).intern
  lazy val ContentDocumentLink: TypeName =
    TypeName(Name("ContentDocumentLink"), Nil, Some(TypeNames.Schema)).intern
  lazy val ProcessInstanceHistory: TypeName =
    TypeName(Name("ProcessInstanceHistory"), Nil, Some(TypeNames.Schema)).intern
  lazy val NameSObject: TypeName = TypeName(Name("Name"), Nil, Some(TypeNames.Schema)).intern

  lazy val ApexPages: TypeName = TypeName(Names.ApexPages).intern
  lazy val ApexPagesPageReference: TypeName =
    TypeName(Names.PageReference, Nil, Some(TypeNames.ApexPages)).intern
  lazy val ApexPagesComponent: TypeName =
    TypeName(Names.Component, Nil, Some(TypeNames.ApexPages)).intern
  lazy val ApexComponent: TypeName = TypeName(Names.Apex, Nil, Some(TypeNames.Component)).intern
  lazy val ChatterComponent: TypeName =
    TypeName(Names.Chatter, Nil, Some(TypeNames.Component)).intern

  lazy val Schema: TypeName       = TypeName(Names.Schema).intern
  lazy val SObjectType: TypeName  = TypeName(Names.SObjectType, Nil, Some(TypeNames.Schema)).intern
  lazy val SObjectField: TypeName = TypeName(Names.SObjectField, Nil, Some(TypeNames.Schema)).intern
  lazy val FieldSet: TypeName     = TypeName(Names.FieldSet, Nil, Some(TypeNames.Schema)).intern
  lazy val DescribeSObjectResult: TypeName =
    TypeName(Names.DescribeSObjectResult, Nil, Some(TypeNames.Schema)).intern
  lazy val DescribeFieldResult: TypeName =
    TypeName(Names.DescribeFieldResult, Nil, Some(TypeNames.Schema)).intern
  lazy val SObjectTypeFieldSets: TypeName =
    TypeName(Names.SObjectTypeFieldSets, Nil, Some(TypeNames.Schema)).intern
  lazy val Activity: TypeName = TypeName(Names.Activity, Nil, Some(TypeNames.Schema))
  lazy val Task: TypeName     = TypeName(Names.Task, Nil, Some(TypeNames.Schema))
  lazy val Event: TypeName    = TypeName(Names.Event, Nil, Some(TypeNames.Schema))

  lazy val DescribeSObjectResult$ : TypeName =
    TypeName(Names.DescribeSObjectResult$, Nil, Some(TypeNames.Internal)).intern
  lazy val SObjectType$ : TypeName =
    TypeName(Names.SObjectType$, Nil, Some(TypeNames.Internal)).intern
  lazy val SObjectTypeFields$ : TypeName =
    TypeName(Names.SObjectTypeFields$, Nil, Some(TypeNames.Internal)).intern
  lazy val SObjectTypeFieldSets$ : TypeName =
    TypeName(Names.SObjectTypeFieldSets$, Nil, Some(TypeNames.Internal)).intern
  lazy val SObjectFields$ : TypeName =
    TypeName(Names.SObjectFields$, Nil, Some(TypeNames.Internal)).intern
  lazy val SObjectFieldRowCause$ : TypeName =
    TypeName(Names.SObjectFieldRowCause$, Nil, Some(TypeNames.Internal)).intern
  lazy val Trigger$ : TypeName = TypeName(Names.Trigger$, Nil, Some(TypeNames.Internal)).intern

  lazy val Database: TypeName = TypeName(Names.Database).intern
  lazy val BatchableContext: TypeName =
    TypeName(Names.BatchableContext, Nil, Some(TypeNames.Database)).intern
  lazy val QueryLocator: TypeName =
    TypeName(XNames.QueryLocator, Nil, Some(TypeNames.Database)).intern

  lazy val User: TypeName             = TypeName(Names.User).intern
  lazy val UserRecordAccess: TypeName = TypeName(Names.UserRecordAccess).intern

  lazy val Label: TypeName     = TypeName(Names.Label, Nil, Some(TypeNames.System)).intern
  lazy val Flow: TypeName      = TypeName(Names.Flow).intern
  lazy val Interview: TypeName = TypeName(Names.Interview, Nil, Some(TypeNames.Flow)).intern
  lazy val Component: TypeName = TypeName(Names.Component, Nil, None).intern
  lazy val Page: TypeName      = TypeName(Names.Page, Nil, None).intern

  def describeSObjectResultOf(typeName: TypeName): TypeName =
    DescribeSObjectResult$.withParams(Seq(typeName)).intern
  def sObjectType$(typeName: TypeName): TypeName = SObjectType$.withParams(Seq(typeName)).intern
  def sObjectTypeFields$(typeName: TypeName): TypeName =
    SObjectTypeFields$.withParams(Seq(typeName)).intern
  def sObjectTypeFieldSets$(typeName: TypeName): TypeName =
    SObjectTypeFieldSets$.withParams(Seq(typeName)).intern
  def sObjectFields$(typeName: TypeName): TypeName = SObjectFields$.withParams(Seq(typeName)).intern
  def sObjectFieldRowCause$(typeName: TypeName): TypeName =
    SObjectFieldRowCause$.withParams(Seq(typeName)).intern
  def trigger(typeName: TypeName): TypeName = Trigger$.withParams(Seq(typeName)).intern

  def listOf(typeName: TypeName): TypeName =
    TypeName(Names.List$, Seq(typeName), Some(TypeNames.System)).intern
  def mapOf(keyType: TypeName, valueType: TypeName): TypeName =
    TypeName(Names.Map$, Seq(keyType, valueType), Some(TypeNames.System)).intern
  def recordSetOf(typeName: TypeName): TypeName =
    TypeName(Names.RecordSet$, Seq(typeName), Some(TypeNames.Internal)).intern

  val standardShareNames = Set(
    "AccountShare",
    "AssetShare",
    "AuthorizationFormConsentShare",
    "AuthorizationFormDataUseShare",
    "AuthorizationFormShare",
    "CalendarViewShare",
    "CampaignShare",
    "CaseShare",
    "CommSubscriptionChannelTypeShare",
    "CommSubscriptionConsentShare",
    "CommSubscriptionShare",
    "ConsumptionScheduleShare",
    "ContactPointAddressShare",
    "ContactPointConsentShare",
    "ContactPointEmailShare",
    "ContactPointPhoneShare",
    "ContactPointTypeConsentShare",
    "ContactRequestShare",
    "ContactShare",
    "DataUseLegalBasisShare",
    "DataUsePurposeShare",
    "EngagementChannelTypeShare",
    "ExternalEventMappingShare",
    "FlowInterviewShare",
    "ForecastShare",
    "ForecastingShare",
    "GoalShare",
    "ImageShare",
    "IndividualShare",
    "LeadShare",
    "LegalEntityShare",
    "ListEmailShare",
    "MacroShare",
    "MacroUsageShare",
    "MetricShare",
    "OpportunityShare",
    "OrderShare",
    "OrgDeleteRequestShare",
    "PartyConsentShare",
    "ProfileSkillShare",
    "PromptActionShare",
    "QuickTextShare",
    "QuickTextUsageShare",
    "QuoteShare",
    "SOSSessionShare",
    "StreamingChannelShare",
    "SurveyInvitationShare",
    "SurveyShare",
    "TodayGoalShare",
    "UserAppMenuCustomizationShare",
    "UserEmailPreferredPersonShare",
    "UserProvisioningRequestShare",
    "UserShare",
    "WorkAccessShare",
    "WorkBadgeDefinitionShare",
    "WorkCoachingShare",
    "WorkFeedbackQuestionSetShare",
    "WorkFeedbackQuestionShare",
    "WorkFeedbackRequestShare",
    "WorkFeedbackShare",
    "WorkFeedbackTemplateShare",
    "WorkPerformanceCycleShare",
    "WorkRewardFundShare",
    "WorkRewardFundTypeShare",
    "WorkRewardShare",
    "WorkThanksShare"
  )

  def aliasOrReturn(typeName: TypeName): TypeName = {
    ambiguousAliasMap.getOrElse(typeName, typeName)
  }

  /** Mapping for Ambiguous type names which can resolve to SObject of platform classes. */
  val ambiguousAliasMap: Map[TypeName, TypeName] = Map(
    TypeName(Name("BusinessHours")) -> TypeName(Name("BusinessHours"), Nil, Some(TypeNames.Schema)),
    TypeName(Name("Site"))          -> TypeName(Name("Site"), Nil, Some(TypeNames.Schema)),
    TypeName(Name("Location"))      -> TypeName(Name("Location"), Nil, Some(TypeNames.System)),
    TypeName(Name("Approval"))      -> TypeName(Name("Approval"), Nil, Some(TypeNames.System)),
    TypeName(Name("Address"))       -> TypeName(Name("Address"), Nil, Some(TypeNames.System))
  )

  val emptyTypeNames: ArraySeq[TypeName] = ArraySeq[TypeName]()

  implicit class TypeNameUtils(typeName: TypeName) {

    /** Interning support for TypeName, used to reduce memory load, mainly from cached data. */
    def intern: TypeName = {
      TypeNames.intern(
        TypeName(
          Names(typeName.name.value),
          typeName.params.map(_.intern),
          typeName.outer.map(_.intern)
        )
      )
    }

    // Check on if is a Schema Share type
    def isShare: Boolean = {
      typeName.outer.contains(TypeNames.Schema) &&
      (typeName.name.toString.endsWith("__Share") ||
      standardShareNames.contains(typeName.name.toString))
    }

    def outerName: Name = typeName.outer.map(_.outerName).getOrElse(typeName.name)

    def inner(): TypeName = {
      TypeName(typeName.name, typeName.params, None)
    }

    def withName(newName: Name): TypeName = {
      if (newName != typeName.name)
        TypeName(newName, typeName.params, typeName.outer)
      else
        typeName
    }

    def withParams(newParams: Seq[TypeName]): TypeName = {
      if (newParams != typeName.params)
        TypeName(typeName.name, newParams, typeName.outer)
      else
        typeName
    }

    def withOuter(newOuter: Option[TypeName]): TypeName = {
      if (newOuter != typeName.outer)
        TypeName(typeName.name, typeName.params, newOuter)
      else
        typeName
    }

    def withTail(newOuter: TypeName): TypeName = {
      if (typeName.outer.isEmpty)
        withOuter(Some(newOuter))
      else
        TypeName(typeName.name, typeName.params, Some(typeName.outer.get.withTail(newOuter)))
    }

    def withArraySubscripts(count: Int): TypeName = {
      assert(count >= 0)
      if (count == 0)
        typeName
      else
        typeName.asListOf.withArraySubscripts(count - 1)
    }

    def withNameReplace(regex: String, replacement: String): TypeName = {
      TypeName(
        Name(typeName.name.value.replaceAll(regex, replacement)),
        typeName.params,
        typeName.outer
      )
    }

    def maybeNamespace: Option[Name] = {
      if (typeName.outer.nonEmpty)
        Some(outerName)
      else
        None
    }

    def withNamespace(namespace: Option[Name]): TypeName = {
      namespace.map(ns => withTail(TypeName(ns))).getOrElse(typeName)
    }

    def asOuterType: TypeName = {
      typeName.outer.map(_.asOuterType).getOrElse(typeName)
    }

    def asDotName: DotName = {
      typeName.outer match {
        case None    => DotName(Seq(typeName.name))
        case Some(x) => x.asDotName.append(typeName.name)
      }
    }

    def wrap(wrapType: TypeName): TypeName = {
      typeName.outer match {
        case None    => TypeName(typeName.name, typeName.params, Some(wrapType))
        case Some(o) => TypeName(typeName.name, typeName.params, Some(o.wrap(wrapType)))
      }
    }

    def unwrap: Option[TypeName] = {
      typeName.outer match {
        case None    => None
        case Some(o) => Some(TypeName(typeName.name, typeName.params, o.unwrap))
      }
    }

    def getArrayType: Option[TypeName] = {
      if (
        typeName.name == Names.List$ && typeName.outer.contains(
          TypeNames.System
        ) && typeName.params.size == 1
      ) {
        typeName.params.headOption
      } else if (
        typeName.name == Names.RecordSet$ && typeName.outer.contains(TypeNames.Internal) &&
        typeName.params.size == 1
      ) {
        typeName.params.headOption
      } else {
        None
      }
    }

    def getSetOrListType: Option[TypeName] = {
      if (
        (typeName.name == Names.Set$ || typeName.name == Names.List$) && typeName.outer.contains(
          TypeNames.System
        ) &&
        typeName.params.size == 1
      ) {
        typeName.params.headOption
      } else {
        None
      }
    }

    def getMapType: Option[(TypeName, TypeName)] = {
      if (
        typeName.name == Names.Map$ && typeName.outer.contains(
          TypeNames.System
        ) && typeName.params.size == 2
      ) {
        Some(typeName.params.head, typeName.params(1))
      } else {
        None
      }
    }

    def isStringOrId: Boolean = typeName == TypeNames.String || typeName == TypeNames.IdType

    def isList: Boolean =
      typeName.name == Names.List$ && typeName.outer.contains(
        TypeNames.System
      ) && typeName.params.size == 1

    def asListOf: TypeName = new TypeName(Names.List$, Seq(typeName), Some(TypeNames.System))

    def isRecordSet: Boolean =
      typeName.name == Names.RecordSet$ && typeName.outer.contains(
        TypeNames.Internal
      ) && typeName.params.size == 1

    def isSObjectList: Boolean = isList && typeName.params.head == TypeNames.SObject

    def isObjectList: Boolean = isList && typeName.params.head == TypeNames.InternalObject

    def isBatchable: Boolean =
      typeName.name == Names.Batchable && typeName.outer.contains(TypeNames.Database)

    def isIterable: Boolean =
      typeName.name == XNames.Iterable && typeName.outer.contains(
        TypeNames.System
      ) && typeName.params.size == 1

    def isNonGeneric: Boolean =
      typeName.params.isEmpty && typeName.outer.forall(_.isNonGeneric)

    def equalsIgnoreParamTypes(other: TypeName): Boolean = {
      typeName.name == other.name &&
      typeName.params.size == other.params.size &&
      typeName.outer.nonEmpty == other.outer.nonEmpty &&
      typeName.outer.forall(_.equalsIgnoreParamTypes(other.outer.get))
    }

    def decodedExtendedGeneric(): Option[TypeName] = {
      val parts = typeName.name.value.split('_')
      if (typeName.params.isEmpty && parts.length > 1) {
        Some(new TypeName(Name(parts.head), Seq(), typeName.outer))
      } else {
        None
      }
    }
  }
}
