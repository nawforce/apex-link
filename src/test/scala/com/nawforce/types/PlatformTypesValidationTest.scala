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
package com.nawforce.types

import com.nawforce.names.{DotName, Name, TypeName}
import org.scalatest.FunSuite

class PlatformTypesValidationTest extends FunSuite {

  private val generics = Map[String, String] (
    "System.List" -> "System.List<T>",
    "System.Iterator" -> "System.Iterator<T>",
    "System.Map" -> "System.Map<K, V>",
    "System.Set" -> "System.Set<T>",
    "System.Iterable" -> "System.Iterable<T>",
    "Database.Batchable" -> "Database.Batchable<T>",
    "Internal.RecordSet$" -> "Internal.RecordSet$<T>",
    "Internal.DescribeSObjectResult$" -> "Internal.DescribeSObjectResult$<T>",
    "Internal.SObjectTypeFields$" -> "Internal.SObjectTypeFields$<T>",
    "Internal.SObjectTypeFieldSets$" -> "Internal.SObjectTypeFieldSets$<T>"
  )

  test("Right number of types (should exclude inners)") {
    assert(PlatformTypeDeclaration.classNames.size == 1310)
  }

  test("SObject type is visible") {
    val td = PlatformTypes.get(TypeName(Name("User")), None)
    assert(td.isRight)
    assert(td.right.get.typeName == TypeName(Name("User"), Nil, None))
  }

  test("All outer types are valid") {
    PlatformTypeDeclaration.classNames.foreach(className => {
      if (!generics.contains(className.toString)) {
        val typeDeclaration = PlatformTypeDeclaration.get(className.asTypeName(), None)
        assert(typeDeclaration.isRight)
        validateTypeDeclaration(className, typeDeclaration.right.get.asInstanceOf[PlatformTypeDeclaration])
      }
    })
  }

  def validateTypeDeclaration(className: DotName, typeDeclaration: PlatformTypeDeclaration): Unit = {
    // name & typeName are valid
    assert(typeDeclaration.name.toString == className.lastName.toString)
    className.toString match {
      case "Internal.Object$" => assert(typeDeclaration.typeName.toString == "Object")
      case "Internal.Null$" => assert(typeDeclaration.typeName.toString == "null")
      case "Internal.Any$" => assert(typeDeclaration.typeName.toString == "any")
      case _ => assert(typeDeclaration.typeName.toString == className.toString)
    }

    // superClass & interfaces reference platform types
    if (typeDeclaration.superClass.nonEmpty)
      assert(PlatformTypeDeclaration.get(typeDeclaration.superClass.get, None).isRight)
    typeDeclaration.interfaces.foreach(tn => PlatformTypeDeclaration.get(tn, None).isRight)

    // nature valid and superClass & interfaces are valid for it
    typeDeclaration.nature match {
      case INTERFACE_NATURE =>
        assert(typeDeclaration.superClass.isEmpty)
      case ENUM_NATURE =>
        assert(typeDeclaration.superClass.isEmpty)
        assert(typeDeclaration.interfaces.isEmpty)
      case CLASS_NATURE => ()
    }

    // PlatformModifiers, always public for outer platform classes
    if (typeDeclaration.outer.isEmpty) {
      assert(typeDeclaration.modifiers.contains(PUBLIC_MODIFIER))
      if (typeDeclaration.nature == CLASS_NATURE)
        assert(typeDeclaration.modifiers.contains(VIRTUAL_MODIFIER))
    }

    // Nested classes
    typeDeclaration.nature match {
      case INTERFACE_NATURE =>
        assert(typeDeclaration.nestedTypes.isEmpty)
      case ENUM_NATURE =>
        assert(typeDeclaration.nestedTypes.isEmpty)
      case CLASS_NATURE =>
        typeDeclaration.nestedTypes.foreach(nested => validateTypeDeclaration(className.append(nested.name), nested))
    }

    // Fields
    typeDeclaration.nature match {
      case INTERFACE_NATURE =>
        assert(typeDeclaration.fields.isEmpty)
      case ENUM_NATURE =>
        assert(typeDeclaration.fields.nonEmpty)
        assert(typeDeclaration.fields.filter(_.typeName.toString == typeDeclaration.typeName.toString)
          == typeDeclaration.fields)
      case CLASS_NATURE =>
        typeDeclaration.fields.foreach(f =>{
          assert(PlatformTypes.get(f.typeName, Some(typeDeclaration)).isRight)
        })
    }

    // Constructors (make sure we can decompose them via toString)
    typeDeclaration.constructors.map(_.toString)

    // Methods (make sure we can decompose them via toString)
    typeDeclaration.methods.map(_.toString)
  }

  test("Exceptions are valid") {
    PlatformTypeDeclaration.classNames.filter(_.lastName.toString.endsWith("Exception")).foreach(className => {
      val typeDeclaration = PlatformTypeDeclaration.get(className.asTypeName(), None)
      assert(typeDeclaration.isRight)
      val td = typeDeclaration.right.get.asInstanceOf[PlatformTypeDeclaration]

      if (td.name.toString() != "Exception")
        assert(td.superClass.get.toString == "System.Exception")
      assert(td.interfaces.isEmpty)
      assert(td.nature == CLASS_NATURE)
      assert(td.modifiers == Seq(PUBLIC_MODIFIER, VIRTUAL_MODIFIER))
      assert(td.outer.isEmpty)
      assert(td.nestedTypes.isEmpty)

      val methods = td.methods.sortBy(_.name.toString)
      assert(methods.size >= 7)
    })
  }
}
