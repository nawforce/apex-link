/*
 [The "BSD licence"]
 Copyright (c) 2020 Kevin Jones
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
package com.nawforce.common

import com.nawforce.common.api.{Name, TypeName}

/** Name handling support.
  *
  * The two most visible types of name (Name & TypeName) are included in the api package. Additional support is
  * included here for caching of Name objects to reduce GC pressure, simple dot formatted names and the encoded
  * names we see used with SObjects that have suffixes such as `__c` and optional namespaces prefixes. There is
  * also some support here for legal & reserved identifier validation.
  */
package object names {

  /** Name extensions */
  implicit class NameUtils(name: Name) {

    /** Check is name is a legal identifier, None if OK or error message string. */
    def isLegalIdentifier: Option[String] = Identifier.isLegalIdentifier(name)

    /** Check is name is a reserved identifier, None if OK or error message string. */
    def isReservedIdentifier: Boolean = Identifier.isReservedIdentifier(name)

    def isEmpty: Boolean = name.value.isEmpty

    def nonEmpty: Boolean = name.value.nonEmpty

    def contains(seq: CharSequence): Boolean = name.value.contains(seq)

    def replaceAll(regex: String, replace: String): Name = Name(name.value.replaceAll(regex, replace))
  }

  /** TypeName extensions */
  implicit class TypeNameUtils(typeName: TypeName) {

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
      TypeName(Name(typeName.name.value.replaceAll(regex, replacement)), typeName.params, typeName.outer)
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
        case None => DotName(Seq(typeName.name))
        case Some(x) => x.asDotName.append(typeName.name)
      }
    }

    def wrap(wrapType: TypeName): TypeName = {
      typeName.outer match {
        case None => TypeName(typeName.name, typeName.params, Some(wrapType))
        case Some(o) => TypeName(typeName.name, typeName.params, Some(o.wrap(wrapType)))
      }
    }

    def unwrap: Option[TypeName] = {
      typeName.outer match {
        case None => None
        case Some(o) => Some(TypeName(typeName.name, typeName.params, o.unwrap))
      }
    }

    def getArrayType: Option[TypeName] = {
      if (typeName.name == Names.List$ && typeName.outer.contains(TypeNames.System) && typeName.params.size == 1) {
        typeName.params.headOption
      } else if (typeName.name == Names.RecordSet$ && typeName.outer.contains(TypeNames.Internal) &&
        typeName.params.size == 1) {
        typeName.params.headOption
      } else {
        None
      }
    }

    def getSetOrListType: Option[TypeName] = {
      if ((typeName.name == Names.Set$ || typeName.name == Names.List$) && typeName.outer.contains(TypeNames.System) &&
        typeName.params.size == 1) {
        typeName.params.headOption
      } else {
        None
      }
    }

    def getMapType: Option[(TypeName, TypeName)] = {
      if (typeName.name == Names.Map$ && typeName.outer.contains(TypeNames.System) && typeName.params.size == 2) {
        Some(typeName.params.head, typeName.params(1))
      } else {
        None
      }
    }

    def isStringOrId: Boolean = typeName == TypeNames.String || typeName == TypeNames.Id

    def isList: Boolean = typeName.name == Names.List$ && typeName.outer.contains(TypeNames.System) && typeName.params.size == 1

    def asListOf: TypeName = new TypeName(Names.List$, Seq(typeName), Some(TypeNames.System))

    def isRecordSet: Boolean = typeName.name == Names.RecordSet$ && typeName.outer.contains(TypeNames.Internal) && typeName.params.size == 1

    def isSObjectList: Boolean = isList && typeName.params.head == TypeNames.SObject

    def isObjectList: Boolean = isList && typeName.params.head == TypeNames.InternalObject

    def isBatchable: Boolean = typeName.name == Names.Batchable && typeName.outer.contains(TypeNames.Database)

    def equalsIgnoreParams(other: TypeName): Boolean = {
      typeName.name == other.name &&
        typeName.params.size == other.params.size &&
        typeName.outer.nonEmpty == other.outer.nonEmpty &&
        typeName.outer.forall(_.equalsIgnoreParams(other.outer.get))
    }

    def asString: String = {
      typeName match {
        case TypeNames.Null => "null"
        case TypeNames.Any => "any"
        case TypeNames.InternalObject => "Object"
        case TypeNames.RecordSet => "[SOQL Results]"
        case TypeNames.SObjectFieldRowCause$ => "SObjectField"
        case TypeName(Names.DescribeSObjectResult$, Seq(TypeName(name, Nil, None)), Some(TypeNames.Internal)) =>
          s"Schema.SObjectType.$name"
        case TypeName(Names.SObjectType$, Seq(TypeName(name, Nil, Some(TypeNames.Schema))), Some(TypeNames.Internal)) =>
          s"$name.SObjectType"
        case TypeName(Names.SObjectTypeFields$, Seq(TypeName(name, Nil, Some(TypeNames.Schema))), Some(TypeNames.Internal)) =>
          s"Schema.SObjectType.$name.Fields"
        case TypeName(Names.SObjectTypeFieldSets$, Seq(TypeName(name, Nil, Some(TypeNames.Schema))), Some(TypeNames.Internal)) =>
          s"Schema.SObjectType.$name.FieldSets"
        case TypeName(Names.SObjectFields$, Seq(TypeName(name, Nil, Some(TypeNames.Schema))), Some(TypeNames.Internal)) =>
          s"Schema.$name.Fields"
        case _ => basicString
      }
    }

    def basicString: String = {
      (if (typeName.outer.isEmpty) "" else typeName.outer.get.asString + ".") +
        typeName.name.toString +
        (if (typeName.params.isEmpty) "" else s"<${typeName.params.map(_.toString).mkString(", ")}>")
    }
  }
}
