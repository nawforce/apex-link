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
package com.nawforce.pkgforce.names

import upickle.default.{macroRW, ReadWriter => RW}

import scala.collection.immutable.ArraySeq.ofRef
import scala.collection.mutable.ArrayBuffer

/** Representation of a type name with optional type arguments.
  *
  * The outer value provides an optional enclosing type name to allow for qualifying inner types. The outer may also
  * be used to scope a type to a specific namespace. The mapping between a type name and its toString value is
  * mostly straight forward but for some internally defined types toString will produce better formatted output so
  * it is advised you always use this when displaying a TypeName.
  */
@upickle.implicits.key("TypeName")
final case class TypeName(name: Name, params: Seq[TypeName], outer: Option[TypeName]) {

  /** Cache hash code as heavily used in collections */
  override val hashCode: Int = scala.util.hashing.MurmurHash3.productHash(this)

  /** Provide custom handling to toString to deal with internal type display */
  override def toString: String = {
    this match {
      case TypeName.Null                                                 => "null"
      case TypeName.Any                                                  => "any"
      case TypeName.InternalObject                                       => "Object"
      case TypeName.InternalInterface                                    => "Object"
      case TypeName.RecordSet                                            => "[SOQL Records]"
      case TypeName(Names.RecordSet$, Seq(arg), Some(TypeName.Internal)) => s"[$arg Records]"
      case TypeName.SObjectFieldRowCause$                                => "SObjectField"
      case TypeName(
            Names.DescribeSObjectResult$,
            Seq(TypeName(name, Nil, None)),
            Some(TypeName.Internal)
          ) =>
        s"Schema.SObjectType.$name"
      case TypeName(
            Names.SObjectType$,
            Seq(TypeName(name, Nil, Some(TypeName.Schema))),
            Some(TypeName.Internal)
          ) =>
        s"$name.SObjectType"
      case TypeName(
            Names.SObjectTypeFields$,
            Seq(TypeName(name, Nil, Some(TypeName.Schema))),
            Some(TypeName.Internal)
          ) =>
        s"Schema.SObjectType.$name.Fields"
      case TypeName(
            Names.SObjectTypeFieldSets$,
            Seq(TypeName(name, Nil, Some(TypeName.Schema))),
            Some(TypeName.Internal)
          ) =>
        s"Schema.SObjectType.$name.FieldSets"
      case TypeName(
            Names.SObjectFields$,
            Seq(TypeName(name, Nil, Some(TypeName.Schema))),
            Some(TypeName.Internal)
          ) =>
        s"Schema.$name.Fields"
      case TypeName(
            Names.SObjectFieldRowCause$,
            Seq(TypeName(name, Nil, Some(TypeName.Schema))),
            Some(TypeName.Internal)
          ) =>
        s"Schema.$name.RowCause"
      case _ => rawString
    }
  }

  def rawString: String = {
    val sb = new StringBuffer()
    rawString(sb)
    sb.toString
  }

  def rawStringLower: String = {
    rawString.toLowerCase()
  }

  def rawString(sb: StringBuffer): Unit = {
    outer.foreach(outer => {
      outer.rawString(sb)
      sb.append('.')
    })
    sb.append(name)
    if (params.nonEmpty) {
      sb.append('<')
      for (i <- params.indices) {
        params(i).rawString(sb)
        if (i < params.length - 1)
          sb.append(", ")
      }
      sb.append('>')
    }
  }

  private[pkgforce] def withOuter(newOuter: Option[TypeName]): TypeName = {
    if (newOuter != outer)
      TypeName(name, params, newOuter)
    else
      this
  }

  private[pkgforce] def withTail(newOuter: TypeName): TypeName = {
    if (outer.isEmpty)
      withOuter(Some(newOuter))
    else
      TypeName(name, params, Some(outer.get.withTail(newOuter)))
  }
}

object TypeName {
  implicit val rw: RW[TypeName] = macroRW

  /** Helper for construction from Java, outer may be null */
  def fromJava(name: Name, params: Array[TypeName], outer: TypeName): TypeName = {
    new TypeName(name, new ofRef(params), Option(outer))
  }

  /** Create a type name from a sequence of names, these should be provided in inner->outer order */
  def apply(names: Seq[Name]): TypeName = {
    names match {
      case hd +: Nil => new TypeName(hd, Nil, None)
      case hd +: tl  => new TypeName(hd, Nil, Some(TypeName(tl)))
    }
  }

  /** Create a simple type name from a single name */
  def apply(name: Name): TypeName = {
    new TypeName(name, Nil, None)
  }

  def apply(typeName: String): Either[String, TypeName] = {
    buildTypeName(safeSplit(typeName, '.'), None)
  }

  private def buildTypeNames(value: String): Either[String, Seq[TypeName]] = {
    val args =
      safeSplit(value, ',').foldLeft(ArrayBuffer[Either[String, TypeName]]())((acc, arg) => {
        acc.append(buildTypeName(safeSplit(arg, '.'), None))
        acc
      })

    args
      .collectFirst { case Left(err) => err }
      .map(err => Left(err))
      .getOrElse(Right(args.collect { case Right(tn) => tn }.toSeq))
  }

  @scala.annotation.tailrec
  private def buildTypeName(
    parts: List[String],
    outer: Option[TypeName]
  ): Either[String, TypeName] = {
    parts match {
      case Nil => Right(outer.get)
      case hd :: tl if hd.contains('<') =>
        val argSplit = hd.split("<", 2)
        if (argSplit.length != 2 || argSplit(1).length < 2 || hd.last != '>')
          Left(s"Unmatched '<' found in '$hd'")
        else {
          buildTypeNames(argSplit(1).take(argSplit(1).length - 1)) match {
            case Right(argTypes) =>
              val name         = Name(argSplit.head)
              val illegalError = Identifier.isLegalIdentifier(name)
              if (illegalError.nonEmpty)
                Left(s"Illegal identifier '$name': ${illegalError.get}")
              else
                buildTypeName(tl, Some(TypeName(name, argTypes, outer)))
            case Left(err) =>
              Left(err)
          }
        }
      case hd :: tl if hd.nonEmpty =>
        val name         = Name(hd)
        val illegalError = Identifier.isLegalIdentifier(name)
        if (illegalError.nonEmpty)
          Left(s"Illegal identifier at '$name': ${illegalError.get}")
        else
          buildTypeName(tl, Some(TypeName(name, Nil, outer)))
      case _ :: _ =>
        Left(s"Empty identifier found in type name")
    }
  }

  /** Split a string at 'separator' but ignoring if within '<...>' blocks. */
  private def safeSplit(value: String, separator: Char): List[String] = {
    var parts: List[String] = Nil
    var current             = new StringBuffer()
    var depth               = 0
    value.foreach {
      case '<' => depth += 1; current.append('<')
      case '>' => depth -= 1; current.append('>')
      case x if x == separator && depth == 0 =>
        parts = current.toString :: parts; current = new StringBuffer()
      case x => current.append(x)
    }
    parts = current.toString :: parts
    parts.reverse
  }

  // Package private to avoid accidental use in apex-link
  private[pkgforce] val Internal: TypeName = TypeName(Names.Internal)
  private[pkgforce] val Null: TypeName     = TypeName(Names.Null$, Nil, Some(TypeName.Internal))
  private[pkgforce] val Any: TypeName      = TypeName(Names.Any$, Nil, Some(TypeName.Internal))
  private[pkgforce] val InternalObject: TypeName =
    TypeName(Names.Object$, Nil, Some(TypeName.Internal))
  private[pkgforce] val InternalInterface: TypeName =
    TypeName(Names.Interface$, Nil, Some(TypeName.Internal))

  private[pkgforce] val System: TypeName  = TypeName(Names.System)
  private[pkgforce] val SObject: TypeName = TypeName(Names.SObject, Nil, Some(TypeName.System))
  private[pkgforce] val RecordSet: TypeName =
    TypeName(Names.RecordSet$, Seq(TypeName.SObject), Some(TypeName.Internal))
  private[pkgforce] val SObjectFieldRowCause$ : TypeName =
    TypeName(Names.SObjectFieldRowCause$, Nil, Some(TypeName.Internal))

  private[pkgforce] val Schema: TypeName    = TypeName(Names.Schema)
  private[pkgforce] val Label: TypeName     = TypeName(Names.Label, Nil, Some(TypeName.System))
  private[pkgforce] val Component: TypeName = TypeName(Names.Component, Nil, None)
  private[pkgforce] val Flow: TypeName      = TypeName(Names.Flow)
  private[pkgforce] val Interview: TypeName = TypeName(Names.Interview, Nil, Some(TypeName.Flow))
  private[pkgforce] val Page: TypeName      = TypeName(Names.Page, Nil, None)

  private[pkgforce] val SObjectTypeFields$ : TypeName =
    TypeName(Names.SObjectTypeFields$, Nil, Some(TypeName.Internal))
  private[pkgforce] val SObjectTypeFieldSets$ : TypeName =
    TypeName(Names.SObjectTypeFieldSets$, Nil, Some(TypeName.Internal))

  def sObjectTypeFields$(typeName: TypeName): TypeName =
    new TypeName(SObjectTypeFields$.name, Seq(typeName), SObjectTypeFields$.outer)
  def sObjectTypeFieldSets$(typeName: TypeName): TypeName =
    new TypeName(SObjectTypeFieldSets$.name, Seq(typeName), SObjectTypeFieldSets$.outer)
  def sObjectTypeRowClause$(typeName: TypeName): TypeName =
    new TypeName(SObjectFieldRowCause$.name, Seq(typeName), SObjectFieldRowCause$.outer)

}
