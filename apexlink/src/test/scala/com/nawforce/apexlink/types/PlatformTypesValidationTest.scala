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

package com.nawforce.apexlink.types

import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.types.platform.{PlatformTypeDeclaration, PlatformTypes}
import com.nawforce.pkgforce.modifiers._
import com.nawforce.pkgforce.names.{DotName, Name, TypeName}
import com.nawforce.pkgforce.parsers.{CLASS_NATURE, ENUM_NATURE, INTERFACE_NATURE, TRIGGER_NATURE}
import org.scalatest.funsuite.AnyFunSuite

class PlatformTypesValidationTest extends AnyFunSuite {

  private val generics = Map[String, String](
    "System.List"                     -> "System.List<T>",
    "System.Iterator"                 -> "System.Iterator<T>",
    "System.Map"                      -> "System.Map<K, V>",
    "System.Set"                      -> "System.Set<T>",
    "System.Iterable"                 -> "System.Iterable<T>",
    "Database.Batchable"              -> "Database.Batchable<T>",
    "Internal.RecordSet$"             -> "Internal.RecordSet$<T>",
    "Internal.DescribeSObjectResult$" -> "Internal.DescribeSObjectResult$<T>",
    "Internal.SObjectType$"           -> "Internal.SObjectType$<T>",
    "Internal.SObjectTypeFields$"     -> "Internal.SObjectTypeFields$<T>",
    "Internal.SObjectTypeFieldSets$"  -> "Internal.SObjectTypeFieldSets$<T>",
    "Internal.SObjectFields$"         -> "Internal.SObjectFields$<T>",
    "Internal.Trigger$"               -> "Internal.Trigger$<T>"
  )

  test("Right number of types (should exclude inners)") {
    assert(PlatformTypeDeclaration.classNames.size == 2084)
  }

  test("SObject type is visible") {
    val td = PlatformTypes.get(TypeName(Name("User")), None)
    assert(td.isRight)
    assert(td.map(_.typeName).contains(TypeName(Name("User"), Nil, Some(TypeNames.Schema))))
  }

  test("Ambiguous type is SObject") {
    val td = PlatformTypes.get(TypeName(Name("Site")), None)
    assert(td.isRight)
    assert(td.map(_.typeName).contains(TypeName(Name("Site"), Nil, Some(TypeNames.Schema))))
  }

  test("Ambiguous type is System") {
    val td = PlatformTypes.get(TypeName(Name("Location")), None)
    assert(td.isRight)
    assert(td.map(_.typeName).contains(TypeName(Name("Location"), Nil, Some(TypeNames.System))))
  }

  test("All outer types are valid") {
    PlatformTypeDeclaration.classNames.toSeq
      .sortBy(_.toString)
      .foreach(className => {
        if (!generics.contains(className.toString)) {
          val typeDeclaration = PlatformTypeDeclaration.get(className.asTypeName(), None)
          assert(typeDeclaration.isRight)
          validateTypeDeclaration(
            className,
            typeDeclaration
              .getOrElse(throw new NoSuchElementException)
              .asInstanceOf[PlatformTypeDeclaration]
          )
        }
      })
  }

  def validateTypeDeclaration(
    className: DotName,
    typeDeclaration: PlatformTypeDeclaration
  ): Unit = {
    // name & typeName are valid
    assert(typeDeclaration.name.toString == className.lastName.toString)
    className.toString match {
      case "Internal.Object$"    => assert(typeDeclaration.typeName.toString == "Object")
      case "Internal.Interface$" => assert(typeDeclaration.typeName.toString == "Object")
      case "Internal.Null$"      => assert(typeDeclaration.typeName.toString == "null")
      case "Internal.Any$"       => assert(typeDeclaration.typeName.toString == "any")
      case "Internal.SObjectFieldRowCause$" =>
        assert(typeDeclaration.typeName.toString == "SObjectField")
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
      case _            => assert(false)
    }

    // PlatformModifiers, always public for outer platform classes
    if (typeDeclaration.outer.isEmpty) {
      assert(typeDeclaration.modifiers.contains(PUBLIC_MODIFIER))
      if (typeDeclaration.nature == CLASS_NATURE)
        assert(
          typeDeclaration.modifiers.contains(VIRTUAL_MODIFIER) || typeDeclaration.modifiers
            .contains(ABSTRACT_MODIFIER)
        )
    }

    // Nested classes
    typeDeclaration.nature match {
      case INTERFACE_NATURE =>
        assert(typeDeclaration.nestedTypes.isEmpty)
      case ENUM_NATURE =>
        assert(typeDeclaration.nestedTypes.isEmpty)
      case CLASS_NATURE =>
        typeDeclaration.nestedTypes.foreach(
          nested =>
            validateTypeDeclaration(
              className.append(nested.name),
              nested.asInstanceOf[PlatformTypeDeclaration]
            )
        )
      case _ => assert(false)
    }

    // Fields
    typeDeclaration.nature match {
      case INTERFACE_NATURE =>
        assert(typeDeclaration.fields.isEmpty)
      case ENUM_NATURE =>
        assert(typeDeclaration.fields.nonEmpty)
        assert(
          typeDeclaration.fields.filter(_.typeName.toString == typeDeclaration.typeName.toString)
            sameElements typeDeclaration.fields
        )
      case CLASS_NATURE =>
        typeDeclaration.fields.foreach(f => {
          assert(PlatformTypes.get(f.typeName, Some(typeDeclaration)).isRight)
        })
      case _ => assert(false)
    }

    // Constructors (make sure we can decompose them via toString)
    typeDeclaration.constructors.map(_.toString)

    // Methods (make sure we can decompose them via toString)
    typeDeclaration.methods.map(_.toString)
  }

  test("Exceptions are valid") {
    PlatformTypeDeclaration.classNames
      .filter(_.lastName.toString.endsWith("Exception"))
      .foreach(className => {
        val typeDeclaration = PlatformTypeDeclaration.get(className.asTypeName(), None)
        assert(typeDeclaration.isRight)
        val td = typeDeclaration
          .getOrElse(throw new NoSuchElementException)
          .asInstanceOf[PlatformTypeDeclaration]

        if (td.name.toString() != "Exception" && td.name.toString() != "ProcessException")
          assert(td.superClass.get.toString == "System.Exception")
        assert(td.interfaces.isEmpty)
        assert(td.nature == CLASS_NATURE)
        assert(td.modifiers sameElements Array(PUBLIC_MODIFIER, VIRTUAL_MODIFIER))
        assert(td.outer.isEmpty)
        assert(td.nestedTypes.isEmpty)

        val methods = td.methods.sortBy(_.name.toString)
        assert(methods.length >= 7)
      })
  }

  test("Enums should have ordinal method (bug)") {
    val td = PlatformTypes.get(TypeName(Name("LoggingLevel")), None).getOrElse(null)

    assert(td.fields.exists(_.name == Name("DEBUG")))
    assert(td.methods.exists(_.name == Name("ordinal")))
  }
}
