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

import com.nawforce.pkgforce.names._

/** Name handling support.
  *
  * The two most visible types of name (Name & TypeName) are included in the api package. Additional support is
  * included here for caching of Name objects to reduce GC pressure, simple dot formatted names and the encoded
  * names we see used with SObjects that have suffixes such as `__c` and optional namespaces prefixes. There is
  * also some support here for legal & reserved identifier validation.
  */
object XNames {

  def apply(name: String): Name = com.nawforce.pkgforce.names.Names(name)

  val Iterable: Name             = XNames("Iterable")
  val IsDeleted: Name            = XNames("IsDeleted")
  val RecordTypeId: Name         = XNames("RecordTypeId")
  val RecordType: Name           = XNames("RecordType")
  val OwnerId: Name              = XNames("OwnerId")
  val Owner: Name                = XNames("Owner")
  val CurrencyIsoCode: Name      = XNames("CurrencyIsoCode")
  val CreatedBy: Name            = XNames("CreatedBy")
  val CreatedById: Name          = XNames("CreatedById")
  val CreatedDate: Name          = XNames("CreatedDate")
  val LastModifiedBy: Name       = XNames("LastModifiedBy")
  val LastModifiedById: Name     = XNames("LastModifiedById")
  val LastModifiedDate: Name     = XNames("LastModifiedDate")
  val LastReferencedDate: Name   = XNames("LastReferencedDate")
  val LastViewedDate: Name       = XNames("LastViewedDate")
  val LastActivityDate: Name     = XNames("LastActivityDate")
  val InsertedById: Name         = XNames("InsertedById")
  val InsertedBy: Name           = XNames("InsertedBy")
  val Tasks: Name                = XNames("Tasks")
  val Notes: Name                = XNames("Notes")
  val NotesAndAttachments: Name  = XNames("NotesAndAttachments")
  val Attachments: Name          = XNames("Attachments")
  val ContentDocumentLinks: Name = XNames("ContentDocumentLinks")
  val ProcessSteps: Name         = XNames("ProcessSteps")
  val SystemModstamp: Name       = XNames("SystemModstamp")
  val Field: Name                = XNames("Field")
  val NewValue: Name             = XNames("NewValue")
  val OldValue: Name             = XNames("OldValue")
  val BestCommentId: Name        = XNames("BestCommentId")
  val Body: Name                 = XNames("Body")
  val CommentCount: Name         = XNames("CommentCount")
  val IsRichText: Name           = XNames("IsRichText")
  val LikeCount: Name            = XNames("LikeCount")
  val LinkUrl: Name              = XNames("LinkUrl")
  val NetworkScope: Name         = XNames("NetworkScope")
  val RelatedRecordId: Name      = XNames("RelatedRecordId")
  val RelatedRecord: Name        = XNames("RelatedRecord")
  val Title: Name                = XNames("Title")
  val Visibility: Name           = XNames("Visibility")
  val ParentId: Name             = XNames("ParentId")
  val Type: Name                 = XNames("Type")
  val DataType: Name             = XNames("DataType")
  val List$ : Name               = XNames("List")
  val Set$ : Name                = XNames("Set")
  val Map$ : Name                = XNames("Map")
  val Iterator: Name             = XNames("Iterator")
  val Batchable: Name            = XNames("Batchable")
  val Equals: Name               = XNames("equals")
  val Start: Name                = XNames("start")
  val QueryLocator: Name         = XNames("QueryLocator")

  /** Name extensions */
  implicit class NameUtils(name: Name) {

    /** Check is name is a legal identifier, None if OK or error message string. */
    def isLegalIdentifier: Option[String] = Identifier.isLegalIdentifier(name)

    /** Check is name is a reserved identifier, None if OK or error message string. */
    def isReservedIdentifier: Boolean = Identifier.isReservedIdentifier(name)

    def isEmpty: Boolean = name.value.isEmpty

    def nonEmpty: Boolean = name.value.nonEmpty

    def contains(seq: CharSequence): Boolean = name.value.contains(seq)

    def substring(begin: Int, end: Int): Name = Name(name.value.substring(begin, end))

    def substring(begin: Int): Name = Name(name.value.substring(begin))

    def startsWithIgnoreCase(value: String): Boolean = {
      name.value.length >= value.length && name.value
        .substring(0, value.length)
        .toLowerCase == value.toLowerCase
    }

    def replaceAll(regex: String, replace: String): Name =
      Name(name.value.replaceAll(regex, replace))

  }
}
