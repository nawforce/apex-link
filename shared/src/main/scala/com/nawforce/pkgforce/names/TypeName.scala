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
package com.nawforce.pkgforce.names

import upickle.default.{macroRW, ReadWriter => RW}

import scala.collection.immutable.ArraySeq.ofRef

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
      case TypeName.Null                  => "null"
      case TypeName.Any                   => "any"
      case TypeName.InternalObject        => "Object"
      case TypeName.InternalInterface     => "Object"
      case TypeName.RecordSet             => "[SOQL Results]"
      case TypeName.SObjectFieldRowCause$ => "SObjectField"
      case TypeName(Names.DescribeSObjectResult$,
                    Seq(TypeName(name, Nil, None)),
                    Some(TypeName.Internal)) =>
        s"Schema.SObjectType.$name"
      case TypeName(Names.SObjectType$,
                    Seq(TypeName(name, Nil, Some(TypeName.Schema))),
                    Some(TypeName.Internal)) =>
        s"$name.SObjectType"
      case TypeName(Names.SObjectTypeFields$,
                    Seq(TypeName(name, Nil, Some(TypeName.Schema))),
                    Some(TypeName.Internal)) =>
        s"Schema.SObjectType.$name.Fields"
      case TypeName(Names.SObjectTypeFieldSets$,
                    Seq(TypeName(name, Nil, Some(TypeName.Schema))),
                    Some(TypeName.Internal)) =>
        s"Schema.SObjectType.$name.FieldSets"
      case TypeName(Names.SObjectFields$,
                    Seq(TypeName(name, Nil, Some(TypeName.Schema))),
                    Some(TypeName.Internal)) =>
        s"Schema.$name.Fields"
      case TypeName(Names.SObjectFieldRowCause$,
                    Seq(TypeName(name, Nil, Some(TypeName.Schema))),
                    Some(TypeName.Internal)) =>
        s"Schema.$name.RowCause"
      case _ => basicString
    }
  }

  private def basicString: String = {
    (if (outer.isEmpty) "" else outer.get.toString + ".") +
      name.toString +
      (if (params.isEmpty) ""
       else s"<${params.map(_.toString).mkString(", ")}>")
  }

}

object TypeName {
  implicit val rw: RW[TypeName] = macroRW

  val emptyTypeName: Array[TypeName] = Array()

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

  val Internal: TypeName = TypeName(Names.Internal)
  val Null: TypeName = TypeName(Names.Null$, Nil, Some(TypeName.Internal))
  val Any: TypeName = TypeName(Names.Any$, Nil, Some(TypeName.Internal))
  val InternalObject: TypeName = TypeName(Names.Object$, Nil, Some(TypeName.Internal))
  val InternalInterface: TypeName = TypeName(Names.Interface$, Nil, Some(TypeName.Internal))

  val System: TypeName = TypeName(Names.System)
  val SObject: TypeName = TypeName(Names.SObject, Nil, Some(TypeName.System))
  val RecordSet: TypeName =
    TypeName(Names.RecordSet$, Seq(TypeName.SObject), Some(TypeName.Internal))
  val SObjectFieldRowCause$ : TypeName =
    TypeName(Names.SObjectFieldRowCause$, Nil, Some(TypeName.Internal))

  val Schema: TypeName = TypeName(Names.Schema)
  val Label: TypeName = TypeName(Names.Label, Nil, Some(TypeName.System))
  val Component: TypeName = TypeName(Names.Component, Nil, None)
  val Flow: TypeName = TypeName(Names.Flow)
  val Interview: TypeName = TypeName(Names.Interview, Nil, Some(TypeName.Flow))
  val Page: TypeName = TypeName(Names.Page, Nil, None)

  val SObjectTypeFields$ : TypeName =
    TypeName(Names.SObjectTypeFields$, Nil, Some(TypeName.Internal))
  val SObjectTypeFieldSets$ : TypeName =
    TypeName(Names.SObjectTypeFieldSets$, Nil, Some(TypeName.Internal))

  def sObjectTypeFields$(typeName: TypeName): TypeName =
    new TypeName(SObjectTypeFields$.name, Seq(typeName), SObjectTypeFields$.outer)
  def sObjectTypeFieldSets$(typeName: TypeName): TypeName =
    new TypeName(SObjectTypeFieldSets$.name, Seq(typeName), SObjectTypeFieldSets$.outer)
}
