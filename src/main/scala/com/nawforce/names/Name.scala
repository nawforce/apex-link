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
package com.nawforce.names

import scalaz.Memo

/**
  * A case insensitive string typically used for holding symbol names
  */
case class Name(value: String) {
  private val normalised = value.toLowerCase

  lazy val isLegalIdentifier: Boolean = {
    value.nonEmpty && value(0) != '_' && !value(0).isDigit && !value.contains("__") && !value.contains('$')
  }

  lazy val isReservedIdentifier: Boolean = {
    Name.allReservedIdentifiers.contains(this)
  }

  def canEqual(that: Any): Boolean = that.isInstanceOf[Name]

  override def equals(that: Any): Boolean = {
    that match {
      case otherName: Name =>
        otherName.canEqual(this) && otherName.normalised == normalised
      case _ => false
    }
  }

  override def hashCode(): Int = normalised.hashCode

  override def toString: String = value

  def isEmpty: Boolean = value.isEmpty
  def nonEmpty: Boolean = value.nonEmpty

  def replaceAll(regex: String, replace: String): Name = {
    Name(value.replaceAll(regex, replace))
  }
}

object Name {

  def apply(name: String): Name = cache(name)
  def safeApply(name: String): Option[Name] = Option(name).filterNot(_.isEmpty).map(n => Name(n))

  lazy val Empty: Name = cache("")
  lazy val System: Name = cache("System")
  lazy val Schema: Name = cache("Schema")
  lazy val Void: Name = cache("void")
  lazy val Class: Name = cache("Class")
  lazy val List$: Name = cache("List")
  lazy val Set$: Name = cache("Set")
  lazy val Map$: Name = cache("Map")
  lazy val Type: Name = cache("Type")
  lazy val Object: Name = cache("Object")
  lazy val Object$: Name = cache("Object$")
  lazy val Null$: Name = cache("Null$")
  lazy val Any$: Name = cache("Any$")
  lazy val RecordSet$: Name = cache("RecordSet$")
  lazy val SObject: Name = cache("SObject")
  lazy val SObjects: Name = cache("SObjects")
  lazy val Internal: Name = cache("Internal")
  lazy val Boolean: Name = cache("Boolean")
  lazy val ApexPages: Name = cache("ApexPages")
  lazy val PageReference: Name = cache("PageReference")
  lazy val Label: Name = cache("Label")
  lazy val Page: Name = cache("Page")
  lazy val Component: Name = cache("Component")
  lazy val Flow: Name = cache("Flow")
  lazy val c: Name = cache("c")
  lazy val String: Name = cache("String")
  lazy val Id: Name = cache("Id")
  lazy val Date: Name = cache("Date")
  lazy val Datetime: Name = cache("Datetime")
  lazy val Time: Name = cache("Time")
  lazy val Decimal: Name = cache("Decimal")
  lazy val Double: Name = cache("Double")
  lazy val Blob: Name = cache("Blob")
  lazy val Location: Name = cache("Location")
  lazy val NameName: Name = cache("Name")
  lazy val AccessLevel: Name = cache("AccessLevel")
  lazy val ParentId: Name = cache("ParentId")
  lazy val RowCause: Name = cache("RowCause")
  lazy val UserOrGroupId: Name = cache("UserOrGroupId")
  lazy val BestCommentId: Name = cache("BestCommentId")
  lazy val Body: Name = cache("Body")
  lazy val CommentCount: Name = cache("CommentCount")
  lazy val ConnectionId: Name = cache("ConnectionId")
  lazy val InsertedById: Name = cache("InsertedById")
  lazy val IsRichText: Name = cache("IsRichText")
  lazy val LikeCount: Name = cache("LikeCount")
  lazy val LinkUrl: Name = cache("LinkUrl")
  lazy val NetworkScope: Name = cache("NetworkScope")
  lazy val RelatedRecordId: Name = cache("RelatedRecordId")
  lazy val Title: Name = cache("Title")
  lazy val Visibility: Name = cache("Visibility")
  lazy val Field: Name = cache("Field")
  lazy val NewValue: Name = cache("NewValue")
  lazy val OldValue: Name = cache("OldValue")
  lazy val Integer: Name = cache("Integer")
  lazy val Long: Name = cache("Long")
  lazy val RecordTypeId: Name = cache("RecordTypeId")
  lazy val SetupOwnerId: Name = cache("SetupOwnerId")
  lazy val SObjectType: Name = cache("SObjectType")
  lazy val SObjectField: Name = cache("SObjectField")
  lazy val FieldSet: Name = cache("FieldSet")
  lazy val DescribeSObjectResult: Name = cache("DescribeSObjectResult")
  lazy val DescribeFieldResult: Name = cache("DescribeFieldResult")
  lazy val DescribeSObjectResult$: Name = cache("DescribeSObjectResult$")
  lazy val SObjectType$: Name = cache("SObjectType$")
  lazy val SObjectTypeFields$: Name = cache("SObjectTypeFields$")
  lazy val SObjectTypeFieldSets$: Name = cache("SObjectTypeFieldSets$")
  lazy val Activity: Name = cache("Activity")
  lazy val Task: Name = cache("Task")
  lazy val Event: Name = cache("Event")
  lazy val DeveloperName: Name = cache("DeveloperName")
  lazy val IsProtected: Name = cache("IsProtected")
  lazy val Language: Name = cache("Language")
  lazy val MasterLabel: Name = cache("MasterLabel")
  lazy val NamespacePrefix: Name = cache("NamespacePrefix")
  lazy val QualifiedAPIName: Name = cache("QualifiedAPIName")
  lazy val UserRecordAccess: Name = cache("UserRecordAccess")
  lazy val ReplayId: Name = cache("ReplayId")
  lazy val Fields: Name = cache("Fields")
  lazy val FieldSets: Name = cache("FieldSets")
  lazy val GetSObjectType: Name = cache("GetSObjectType")

  private val cache: String => Name = Memo.immutableHashMapMemo { name: String => new Name(name) }

  // This is the official reserved keyword list, not all are actually reserved, some are for "future" use, I have
  // removed those known not to be enforced
  lazy val reservedIdentifiers: Set[Name] = Set(
    Name("abstract"), Name("activate"), Name("and"), Name("any"), Name("array"), Name("as"), Name("asc"), Name("autonomous"),
    Name("begin"), Name("bigdecimal"), Name("blob"), Name("break"), Name("bulk"), Name("by"), Name("byte"), Name("case"),
    Name("cast"), Name("catch"), Name("char"), Name("class"), Name("collect"), Name("commit"), Name("const"), Name("continue"),
    Name("decimal"), Name("default"), Name("delete"), Name("desc"), Name("do"), Name("else"), Name("end"),
    Name("enum"), Name("exception"), Name("exit"), Name("export"), Name("extends"), Name("false"), Name("final"), Name("finally"),
    Name("float"), Name("for"), Name("from"), Name("global"), Name("goto"), Name("group"), Name(""), Name(" "),
    Name(""), Name("having"), Name("hint"), Name("if"), Name("implements"), Name("import"), Name("in"), Name("inner"),
    Name("insert"), Name("instanceof"), Name("interface"), Name("into"), Name("int"), Name("join"), Name("like"), Name("limit"),
    Name("list"), Name("long"),Name("loop"), Name("map"), Name("merge"), Name("new"),  Name("not"), Name("null"), Name("nulls"),
    Name("number"), Name("object"), Name("of"), Name("on"), Name("or"), Name("outer"), Name("override"), Name("package"),
    Name("parallel"), Name("pragma"), Name("private"), Name("protected"), Name("public"), Name("retrieve"), Name("return"),
    Name("rollback"), Name("select"), Name("set"), Name("short"), Name("sort"), Name("static"), Name("super"),
    Name("switch"), Name("synchronized"), Name("system"), Name("testmethod"), Name("then"), Name("this"), Name("throw"),
    Name("transaction"), Name("trigger"), Name("true"), Name("try"), Name("undelete"), Name("update"), Name("upsert"),
    Name("using"), Name("virtual"), Name("webservice"), Name("when"), Name("where"), Name("while"),
  )

  // These are identifiers that don't work but are not reserved, yeah go figure
  lazy val badIdentifiers: Set[Name] = Set(
    Name("boolean"), Name("currency"), Name("date"), Name("datetime"), Name("double"), Name("integer"), Name("string"),
    Name("time")
  )

  lazy val allReservedIdentifiers: Set[Name] = reservedIdentifiers ++ badIdentifiers
}


