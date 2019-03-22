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
package com.nawforce.unit.types

import com.nawforce.platform.System.{Double, Location, String}
import com.nawforce.types._
import com.nawforce.utils.DotName
import org.scalatest.FunSuite

class PlatformTypeDeclarationTest extends FunSuite {

  test("Bad class not found") {
    assert(PlatformTypeDeclaration.get(DotName("Hello")).isEmpty)
  }

  test("Unscoped class not found") {
    assert(PlatformTypeDeclaration.get(DotName("String")).isEmpty)
  }

  test("Scoped class") {
    val td = PlatformTypeDeclaration.get(DotName("System.String"))
    assert(td.nonEmpty)
    assert(td.get.name.toString == "String")
    assert(td.get.typeName.toString == "System.String")
    assert(td.get.superClass.isEmpty)
    assert(td.get.interfaces.isEmpty)
    assert(td.get.nature == CLASS)
    assert(td.get.modifiers == Seq(PUBLIC))
    assert(td.get.parent.isEmpty)
    assert(td.get.nestedClasses.isEmpty)
  }

  test("Case insensitive class name") {
    val td = PlatformTypeDeclaration.get(DotName("System.strIng"))
    assert(td.nonEmpty)
    assert(td eq PlatformTypeDeclaration.get(DotName("System.String")))
  }

  test("Case insensitive namespace") {
    val td = PlatformTypeDeclaration.get(DotName("systEm.String"))
    assert(td.nonEmpty)
    assert(td eq PlatformTypeDeclaration.get(DotName("System.String")))
  }

  test("Extending class") {
    val td = PlatformTypeDeclaration.get(DotName("ConnectApi.FeedItem"))
    assert(td.nonEmpty)
    assert(td.get.name.toString == "FeedItem")
    assert(td.get.typeName.toString == "ConnectApi.FeedItem")
    assert(td.get.superClass.get.toString == "ConnectApi.FeedElement")
    assert(td.get.interfaces.isEmpty)
    assert(td.get.nature == CLASS)
    assert(td.get.modifiers == Seq(PUBLIC))
    assert(td.get.parent.isEmpty)
    assert(td.get.nestedClasses.isEmpty)
  }

  test("Implements class") {
    val td = PlatformTypeDeclaration.get(DotName("System.List"))
    assert(td.nonEmpty)
    assert(td.get.name.toString == "List")
    assert(td.get.typeName.toString == "System.List<T>")
    assert(td.get.superClass.isEmpty)
    assert(td.get.interfaces.size == 1)
    assert(td.get.interfaces.head.toString == "System.Iterable<T>")
    assert(td.get.nature == CLASS)
    assert(td.get.modifiers == Seq(PUBLIC))
    assert(td.get.parent.isEmpty)
    assert(td.get.nestedClasses.isEmpty)
  }

  test("Interface nature") {
    val td = PlatformTypeDeclaration.get(DotName("System.Callable"))
    assert(td.nonEmpty)
    assert(td.get.name.toString == "Callable")
    assert(td.get.typeName.toString == "System.Callable")
    assert(td.get.superClass.isEmpty)
    assert(td.get.interfaces.isEmpty)
    assert(td.get.nature == INTERFACE)
    assert(td.get.modifiers == Seq(PUBLIC))
    assert(td.get.parent.isEmpty)
    assert(td.get.nestedClasses.isEmpty)
  }

  test("Enum nature") {
    val td = PlatformTypeDeclaration.get(DotName("System.RoundingMode"))
    assert(td.nonEmpty)
    assert(td.get.name.toString == "RoundingMode")
    assert(td.get.typeName.toString == "System.RoundingMode")
    assert(td.get.superClass.isEmpty)
    assert(td.get.interfaces.isEmpty)
    assert(td.get.nature == ENUM)
    assert(td.get.modifiers == Seq(PUBLIC))
    assert(td.get.parent.isEmpty)
    assert(td.get.nestedClasses.isEmpty)
  }

  test("Nested class") {
    val td = PlatformTypeDeclaration.get(DotName("Messaging.InboundEmail"))
    assert(td.nonEmpty)
    assert(td.get.name.toString == "InboundEmail")
    assert(td.get.typeName.toString == "Messaging.InboundEmail")
    assert(td.get.superClass.isEmpty)
    assert(td.get.interfaces.isEmpty)
    assert(td.get.nature == CLASS)
    assert(td.get.modifiers == Seq(PUBLIC))
    assert(td.get.parent.isEmpty)

    val nested = td.get.nestedClasses.sortBy(_.name.toString)
    assert(nested.size == 3)
    assert(nested.map(_.name.toString) == Seq("BinaryAttachment", "Header", "TextAttachment"))
    assert(nested.filter(_.modifiers == Seq(PUBLIC, STATIC)) == nested)
    assert(nested.filter(_.parent == td) == nested)
  }

  test("Field access") {
    val td = PlatformTypeDeclaration.get(DotName("System.Address"))
    assert(td.nonEmpty)
    assert(td.get.name.toString == "Address")
    assert(td.get.typeName.toString == "System.Address")
    assert(td.get.superClass.isEmpty)
    assert(td.get.interfaces.isEmpty)
    assert(td.get.nature == CLASS)
    assert(td.get.modifiers == Seq(PUBLIC))
    assert(td.get.parent.isEmpty)
    assert(td.get.nestedClasses.isEmpty)

    val fields = td.get.fields.sortBy(_.name.toString)
    assert(fields.size == 8)
    assert(fields.map(_.name.toString) ==
      Seq("city", "country", "countryCode", "geocodeAccuracy", "postalCode", "state", "stateCode", "street"))
    assert(fields.filter(_.modifiers == Seq(PUBLIC)) == fields)
    assert(fields.filter(_.typeName.toString == "System.String") == fields)
  }

  test("Constructor access") {
    val td = PlatformTypeDeclaration.get(DotName("System.DmlException"))
    assert(td.nonEmpty)
    assert(td.get.name.toString == "DmlException")
    assert(td.get.typeName.toString == "System.DmlException")
    assert(td.get.superClass.get.toString == "System.Exception")
    assert(td.get.interfaces.isEmpty)
    assert(td.get.nature == CLASS)
    assert(td.get.modifiers == Seq(PUBLIC))
    assert(td.get.parent.isEmpty)
    assert(td.get.nestedClasses.isEmpty)

    val constructors = td.get.constructors.sortBy(_.toString)
    assert(constructors.size == 4)
    assert(constructors.filter(_.modifiers == Seq(PUBLIC)) == constructors)
    assert(constructors.head.toString == "public System.DmlException()")
    assert(constructors(1).toString == "public System.DmlException(System.Exception param1)")
    assert(constructors(2).toString == "public System.DmlException(System.String param1)")
    assert(constructors(3).toString == "public System.DmlException(System.String param1, System.Exception param2)")
  }

  test("Method access") {
    val td = PlatformTypeDeclaration.get(DotName("System.Address"))
    assert(td.nonEmpty)
    assert(td.get.name.toString == "Address")
    assert(td.get.typeName.toString == "System.Address")
    assert(td.get.superClass.isEmpty)
    assert(td.get.interfaces.isEmpty)
    assert(td.get.nature == CLASS)
    assert(td.get.modifiers == Seq(PUBLIC))
    assert(td.get.parent.isEmpty)
    assert(td.get.nestedClasses.isEmpty)

    val methods = td.get.methods.sortBy(_.name.toString)
    assert(methods.size == 11)
    assert(methods.map(_.name.toString) ==
      Seq("getCity", "getCountry", "getCountryCode", "getDistance", "getGeocodeAccuracy", "getLatitude",
        "getLongitude", "getPostalCode", "getState", "getStateCode", "getStreet"))
    assert(methods.filter(_.modifiers == Seq(PUBLIC)) == methods)
    assert(methods.filter(_.name.toString == "getCity").head.toString == "public System.String getCity()")
    assert(methods.filter(_.name.toString == "getDistance").head.toString ==
      "public System.Double getDistance(System.Location other, System.String unit)")
  }

  test("Exception") {
    val td = PlatformTypeDeclaration.get(DotName("eventbus.RetryableException"))
    assert(td.nonEmpty)
    assert(td.get.name.toString == "RetryableException")
    assert(td.get.typeName.toString == "eventbus.RetryableException")
    assert(td.get.superClass.get.toString == "System.Exception")
    assert(td.get.interfaces.isEmpty)
    assert(td.get.nature == CLASS)
    assert(td.get.modifiers == Seq(PUBLIC))
    assert(td.get.parent.isEmpty)
    assert(td.get.nestedClasses.isEmpty)

    val methods = td.get.methods.sortBy(_.name.toString)
    assert(methods.size == 7)
    assert(methods.map(_.name.toString) == Seq("getCause", "getLineNumber", "getMessage", "getStackTraceString",
      "getTypeName", "initCause", "setMessage"))
    assert(methods.filter(_.modifiers == Seq(PUBLIC)) == methods)
    assert(methods.filter(_.name.toString == "getCause").head.toString == "public System.Exception getCause()")
    assert(methods.filter(_.name.toString == "getLineNumber").head.toString == "public System.Integer getLineNumber()")
    assert(methods.filter(_.name.toString == "getMessage").head.toString == "public System.String getMessage()")
    assert(methods.filter(_.name.toString == "getStackTraceString").head.toString == "public System.String getStackTraceString()")
    assert(methods.filter(_.name.toString == "getTypeName").head.toString == "public System.String getTypeName()")
    assert(methods.filter(_.name.toString == "initCause").head.toString == "public void initCause(System.Exception cause)")
    assert(methods.filter(_.name.toString == "setMessage").head.toString == "public void setMessage(System.String message)")
  }

}
