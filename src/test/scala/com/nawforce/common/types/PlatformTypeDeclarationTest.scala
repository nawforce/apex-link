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
package com.nawforce.common.types

import com.nawforce.common.api.{Name, TypeName}
import com.nawforce.common.cst.{PUBLIC_MODIFIER, STATIC_MODIFIER, VIRTUAL_MODIFIER}
import com.nawforce.common.finding.{MissingType, WrongTypeArguments}
import com.nawforce.common.names.{Names, TypeNames}
import com.nawforce.common.types.core.{CLASS_NATURE, ENUM_NATURE, INTERFACE_NATURE}
import com.nawforce.runtime.types.PlatformTypeDeclaration
import org.scalatest.funsuite.AnyFunSuite

class PlatformTypeDeclarationTest extends AnyFunSuite  {

  test("Bad class not found") {
    assert(PlatformTypeDeclaration.get(TypeName(Name("Hello")), None) == Left(MissingType(TypeName(Name("Hello")))))
  }

  test("Unscoped class not found") {
    assert(PlatformTypeDeclaration.get(TypeName(Name("String")), None) == Left(MissingType(TypeName(Name("String")))))
  }

  test("Scoped class") {
    val td = PlatformTypeDeclaration.get(TypeName(Name("String"), Nil, Some(TypeNames.System)), None).toOption
    assert(td.nonEmpty)
    assert(td.get.name.toString == "String")
    assert(td.get.typeName.toString == "System.String")
    assert(td.get.superClass.isEmpty)
    assert(td.get.interfaces.isEmpty)
    assert(td.get.nature == CLASS_NATURE)
    assert(td.get.modifiers sameElements  Array(PUBLIC_MODIFIER, VIRTUAL_MODIFIER))
    assert(td.get.outerTypeName.isEmpty)
    assert(td.get.nestedTypes.isEmpty)
  }

  test("Case insensitive class name") {
    val td = PlatformTypeDeclaration.get(TypeName(Name("StrIng"), Nil, Some(TypeNames.System)), None).toOption
    assert(td.nonEmpty)
    assert(td.get eq PlatformTypeDeclaration.get(TypeNames.String, None).toOption.get)
  }

  test("Case insensitive namespace") {
    val td = PlatformTypeDeclaration.get(TypeName(Name("String"), Nil, Some(TypeName(Name("SyStem")))), None).toOption
    assert(td.nonEmpty)
    assert(td.get eq PlatformTypeDeclaration.get(TypeNames.String, None).toOption.get)
  }

  test("Extending class") {
    val td = PlatformTypeDeclaration.get(TypeName(Name("FeedItem"), Nil, Some(TypeName(Name("ConnectApi")))), None).toOption
    assert(td.nonEmpty)
    assert(td.get.name.toString == "FeedItem")
    assert(td.get.typeName.toString == "ConnectApi.FeedItem")
    assert(td.get.superClass.get.toString == "ConnectApi.FeedElement")
    assert(td.get.interfaces.isEmpty)
    assert(td.get.nature == CLASS_NATURE)
    assert(td.get.modifiers sameElements Array(PUBLIC_MODIFIER, VIRTUAL_MODIFIER))
    assert(td.get.outerTypeName.isEmpty)
    assert(td.get.nestedTypes.isEmpty)
  }

  test("Interface nature") {
    val td = PlatformTypeDeclaration.get(TypeName(Name("Callable"), Nil, Some(TypeNames.System)), None).toOption
    assert(td.nonEmpty)
    assert(td.get.name.toString == "Callable")
    assert(td.get.typeName.toString == "System.Callable")
    assert(td.get.superClass.isEmpty)
    assert(td.get.interfaces.isEmpty)
    assert(td.get.nature == INTERFACE_NATURE)
    assert(td.get.modifiers sameElements Array(PUBLIC_MODIFIER))
    assert(td.get.outerTypeName.isEmpty)
    assert(td.get.nestedTypes.isEmpty)
  }

  test("Enum nature") {
    val td = PlatformTypeDeclaration.get(TypeName(Name("RoundingMode"), Nil, Some(TypeNames.System)), None).toOption
    assert(td.nonEmpty)
    assert(td.get.name.toString == "RoundingMode")
    assert(td.get.typeName.toString == "System.RoundingMode")
    assert(td.get.superClass.isEmpty)
    assert(td.get.interfaces.isEmpty)
    assert(td.get.nature == ENUM_NATURE)
    assert(td.get.modifiers sameElements Array(PUBLIC_MODIFIER))
    assert(td.get.outerTypeName.isEmpty)
    assert(td.get.nestedTypes.isEmpty)
  }

  test("Nested class") {
    val td = PlatformTypeDeclaration.get(TypeName(Name("InboundEmail"), Nil, Some(TypeName(Name("Messaging")))), None).toOption
    assert(td.nonEmpty)
    assert(td.get.name.toString == "InboundEmail")
    assert(td.get.typeName.toString == "Messaging.InboundEmail")
    assert(td.get.superClass.isEmpty)
    assert(td.get.interfaces.isEmpty)
    assert(td.get.nature == CLASS_NATURE)
    assert(td.get.modifiers sameElements Array(PUBLIC_MODIFIER, VIRTUAL_MODIFIER))
    assert(td.get.outerTypeName.isEmpty)

    val nested = td.get.nestedTypes.sortBy(_.name.toString)
    assert(nested.size == 3)
    assert(nested.map(_.name.toString) == Seq("BinaryAttachment", "Header", "TextAttachment"))
    assert(nested.filter(_.modifiers.toSet == Set(PUBLIC_MODIFIER, VIRTUAL_MODIFIER, STATIC_MODIFIER)) == nested)
    assert(nested.filter(_.outerTypeName.get == td.get.typeName) == nested)
  }

  test("Field access") {
    val td = PlatformTypeDeclaration.get(TypeName(Name("Address"), Nil, Some(TypeNames.System)), None).toOption
    assert(td.nonEmpty)
    assert(td.get.name.toString == "Address")
    assert(td.get.typeName.toString == "System.Address")
    assert(td.get.superClass.isEmpty)
    assert(td.get.interfaces.isEmpty)
    assert(td.get.nature == CLASS_NATURE)
    assert(td.get.modifiers sameElements Array(PUBLIC_MODIFIER, VIRTUAL_MODIFIER))
    assert(td.get.outerTypeName.isEmpty)
    assert(td.get.nestedTypes.isEmpty)

    val fields = td.get.fields.sortBy(_.name.toString)
    assert(fields.size == 8)
    assert(fields.map(_.name.toString) ==
      Seq("city", "country", "countryCode", "geocodeAccuracy", "postalCode", "state", "stateCode", "street"))
    assert(fields.filter(_.modifiers sameElements Array(PUBLIC_MODIFIER)) == fields)
    assert(fields.filter(_.typeName.toString == "System.String") == fields)
  }

  test("Constructor access") {
    val td = PlatformTypeDeclaration.get(TypeName(Name("DmlException"), Nil, Some(TypeNames.System)), None).toOption
    assert(td.nonEmpty)
    assert(td.get.name.toString == "DmlException")
    assert(td.get.typeName.toString == "System.DmlException")
    assert(td.get.superClass.get.toString == "System.Exception")
    assert(td.get.interfaces.isEmpty)
    assert(td.get.nature == CLASS_NATURE)
    assert(td.get.modifiers sameElements Array(PUBLIC_MODIFIER, VIRTUAL_MODIFIER))
    assert(td.get.outerTypeName.isEmpty)
    assert(td.get.nestedTypes.isEmpty)

    val constructors = td.get.constructors.sortBy(_.toString)
    assert(constructors.size == 4)
    assert(constructors.filter(_.modifiers sameElements Array(PUBLIC_MODIFIER)) == constructors)
    assert(constructors.head.toString == "public System.DmlException()")
    assert(constructors(1).toString == "public System.DmlException(System.Exception param1)")
    assert(constructors(2).toString == "public System.DmlException(System.String param1)")
    assert(constructors(3).toString == "public System.DmlException(System.String param1, System.Exception param2)")
  }

  test("Method access") {
    val td = PlatformTypeDeclaration.get(TypeName(Name("Address"), Nil, Some(TypeNames.System)), None).toOption
    assert(td.nonEmpty)
    assert(td.get.name.toString == "Address")
    assert(td.get.typeName.toString == "System.Address")
    assert(td.get.superClass.isEmpty)
    assert(td.get.interfaces.isEmpty)
    assert(td.get.nature == CLASS_NATURE)
    assert(td.get.modifiers sameElements Array(PUBLIC_MODIFIER, VIRTUAL_MODIFIER))
    assert(td.get.outerTypeName.isEmpty)
    assert(td.get.nestedTypes.isEmpty)

    val methods = td.get.methods.sortBy(_.name.toString)
    assert(methods.size == 11)
    assert(methods.map(_.name.toString) ==
      Seq("getCity", "getCountry", "getCountryCode", "getDistance", "getGeocodeAccuracy", "getLatitude",
        "getLongitude", "getPostalCode", "getState", "getStateCode", "getStreet"))
    assert(methods.filter(_.modifiers sameElements Array(PUBLIC_MODIFIER)) == methods)
    assert(methods.filter(_.name.toString == "getCity").head.toString == "public System.String getCity()")
    assert(methods.filter(_.name.toString == "getDistance").head.toString ==
      "public System.Double getDistance(System.Location other, System.String unit)")
  }

  test("Exception") {
    val td = PlatformTypeDeclaration.get(TypeName(Name("RetryableException"), Nil, Some(TypeName(Name("eventbus")))), None).toOption
    assert(td.nonEmpty)
    assert(td.get.name.toString == "RetryableException")
    assert(td.get.typeName.toString == "eventbus.RetryableException")
    assert(td.get.superClass.get.toString == "System.Exception")
    assert(td.get.interfaces.isEmpty)
    assert(td.get.nature == CLASS_NATURE)
    assert(td.get.modifiers sameElements Array(PUBLIC_MODIFIER, VIRTUAL_MODIFIER))
    assert(td.get.outerTypeName.isEmpty)
    assert(td.get.nestedTypes.isEmpty)

    val methods = td.get.methods.sortBy(_.name.toString)
    assert(methods.size == 15)
    assert(methods.map(_.name.toString) == Seq("getCause", "getDmlFieldNames", "getDmlFields", "getDmlId",
      "getDmlIndex", "getDmlMessage", "getDmlStatusCode", "getDmlType", "getLineNumber", "getMessage", "getNumDml",
      "getStackTraceString", "getTypeName", "initCause", "setMessage"))
    assert(methods.filter(_.modifiers sameElements Array(PUBLIC_MODIFIER)) == methods)
  }

  test("Generic class") {
    val td = PlatformTypeDeclaration.get(TypeName(Name("List"), Seq(TypeNames.String), Some(TypeNames.System)), None).toOption
    assert(td.nonEmpty)
    assert(td.get.name.toString == "List")
    assert(td.get.typeName.toString == "System.List<System.String>")
    assert(td.get.superClass.isEmpty)
    assert(td.get.interfaces.size == 1)
    assert(td.get.interfaces.head.toString == "System.Iterable<System.String>")
    assert(td.get.nature == CLASS_NATURE)
    assert(td.get.modifiers sameElements Array(PUBLIC_MODIFIER, VIRTUAL_MODIFIER))
    assert(td.get.outerTypeName.isEmpty)
    assert(td.get.nestedTypes.isEmpty)

    assert (td.get.methods.map(_.toString).sorted.mkString("\n") == Seq(
      "public System.List<System.String> clone()",
      "public void add(System.String listElement)",
      "public void add(System.Integer index, System.String listElement)",
      "public void addAll(System.List<System.String> fromList)",
      "public void addAll(System.Set<System.String> fromSet)",
      "public void clear()",
      "public System.Boolean contains(System.String listElement)",
      "public System.List<System.String> deepClone()",
      "public System.List<System.String> deepClone(System.Boolean preserveId)",
      "public System.List<System.String> deepClone(System.Boolean preserveId, System.Boolean preserveReadonlyTimestamps)",
      "public System.List<System.String> deepClone(System.Boolean preserveId, System.Boolean preserveReadonlyTimestamps, System.Boolean preserveAutonumber)",
      "public System.String get(System.Integer index)",
      "public Schema.SObjectType getSObjectType()",
      "public System.Integer indexOf(System.String listElement)",
      "public System.Iterator<System.String> iterator()",
      "public System.Boolean isEmpty()",
      "public System.String remove(System.Integer index)",
      "public void set(System.Integer index, System.String listElement)",
      "public System.Integer size()",
      "public void sort()",
      "public System.String toString()",
      "public System.Boolean equals(System.List<System.String> other)",
      "public System.Integer hashCode()"
    ).sorted.mkString("\n"))
  }

  test("Nested Generic class") {
    val inner = TypeName(Name("List"), Seq(TypeNames.String), Some(TypeNames.System))
    val td = PlatformTypeDeclaration.get(TypeName(Name("Iterable"), Seq(inner), Some(TypeNames.System)), None).toOption
    assert(td.nonEmpty)
    assert(td.get.name.toString == "Iterable")
    assert(td.get.typeName.toString == "System.Iterable<System.List<System.String>>")
    assert(td.get.superClass.isEmpty)
    assert(td.get.interfaces.isEmpty)
    assert(td.get.nature == INTERFACE_NATURE)
    assert(td.get.modifiers sameElements Array(PUBLIC_MODIFIER))
    assert(td.get.outerTypeName.isEmpty)
    assert(td.get.nestedTypes.isEmpty)

    assert (td.get.methods.map(_.toString).sorted.mkString("\n") == Seq(
      "public System.Iterator<System.List<System.String>> iterator()",
    ).sorted.mkString("\n"))
  }

  test("Non-generic type") {
    val typeName = TypeName(Name("String"), Seq(TypeNames.Integer), Some(TypeNames.System))
    val td = PlatformTypeDeclaration.get(typeName, None)
    td match {
      case Left(e: WrongTypeArguments) => assert(e.typeName == typeName)
      case _ => assert(true)
    }
  }

  test("Too many type params") {
    val typeName = TypeName(Name("List"), Seq(TypeName(Names.String), TypeName(Names.String)), Some(TypeNames.System))
    val td = PlatformTypeDeclaration.get(typeName, None)
    td match {
      case Left(e: WrongTypeArguments) => assert(e.typeName == typeName)
      case _ => assert(false)
    }
  }

  test("Too few type params") {
    val typeName = TypeName(Name("List"), Seq(), Some(TypeNames.System))
    val td = PlatformTypeDeclaration.get(typeName, None)
    td match {
      case Left(e: WrongTypeArguments) => assert(e.typeName == typeName)
      case _ => assert(false)
    }
  }

  test("Relative type in param") {
    val td = PlatformTypeDeclaration.get(TypeName(Name("List"), Seq(TypeName(Names.String)), Some(TypeNames.System)), None)
    assert(td.isRight)
    assert(td.right.get.typeName == TypeName(Name("List"), Seq(TypeNames.String), Some(TypeNames.System)))
  }
}
