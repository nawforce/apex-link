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

package com.nawforce.apexlink.api

import com.nawforce.apexlink.names.TypeIdentifiers.TypeIdentifierUtils
import com.nawforce.apexlink.names.TypeNames.TypeNameUtils
import com.nawforce.pkgforce.diagnostics.Diagnostic
import com.nawforce.pkgforce.modifiers.Modifier
import com.nawforce.pkgforce.names.{TypeIdentifier, TypeName}
import com.nawforce.pkgforce.parsers.Nature
import com.nawforce.pkgforce.path.Location
import upickle.default.{macroRW, ReadWriter => RW}

import scala.collection.immutable.ArraySeq

/**
  * Summary types are used both by the disk cache and to provide a quick & cheap way to examine what is available
  * in a type.
  */
/** Summary of an Apex class with diagnostic information */
case class ApexSummary(typeSummary: TypeSummary, diagnostics: Array[Diagnostic])

/** Summary of a type */
case class TypeSummary(
  sourceHash: Int,
  location: Location,
  idLocation: Location,
  name: String,
  typeName: TypeName,
  nature: String,
  modifiers: ArraySeq[Modifier],
  inTest: Boolean,
  superClass: Option[TypeName],
  interfaces: ArraySeq[TypeName],
  blocks: ArraySeq[BlockSummary],
  fields: ArraySeq[FieldSummary],
  constructors: ArraySeq[ConstructorSummary],
  methods: ArraySeq[MethodSummary],
  nestedTypes: ArraySeq[TypeSummary],
  dependents: Array[DependentSummary]
) {

  // For backwards compatibility in ApexFlow
  def nameLocation: Location = idLocation

  override def equals(that: Any): Boolean = {
    that match {
      case other: TypeSummary =>
        other.canEqual(this) && doesEqual(other)
      case _ => false
    }
  }

  override def canEqual(that: Any): Boolean = that.isInstanceOf[TypeSummary]

  private def doesEqual(other: TypeSummary): Boolean = {
    this.sourceHash == other.sourceHash &&
    this.location == other.location &&
    this.idLocation == other.idLocation &&
    this.name == other.name &&
    this.typeName == other.typeName &&
    this.nature == other.nature &&
    this.modifiers == other.modifiers &&
    this.superClass == other.superClass &&
    this.interfaces == other.interfaces &&
    this.blocks == other.blocks &&
    this.fields == other.fields &&
    this.constructors == other.constructors &&
    this.methods == other.methods &&
    this.nestedTypes == other.nestedTypes &&
    this.dependents.sameElements(other.dependents)
  }
}

case class BlockSummary(
  location: Location,
  isStatic: Boolean,
  dependents: Array[DependentSummary]
) {

  override def equals(that: Any): Boolean = {
    that match {
      case other: BlockSummary => other.canEqual(this) && doesEqual(other)
      case _                   => false
    }
  }

  override def canEqual(that: Any): Boolean = that.isInstanceOf[BlockSummary]

  private def doesEqual(other: BlockSummary): Boolean = {
    this.location == other.location &&
    this.isStatic == other.isStatic &&
    this.dependents.sameElements(other.dependents)
  }
}

/** Summary of a type field (or property) */
case class FieldSummary(
  location: Location,
  idLocation: Location,
  name: String,
  nature: Nature,
  modifiers: ArraySeq[Modifier],
  typeName: TypeName,
  readAccess: Modifier,
  writeAccess: Modifier,
  dependents: Array[DependentSummary]
) {
  override def equals(that: Any): Boolean = {
    that match {
      case other: FieldSummary => other.canEqual(this) && doesEqual(other)
      case _                   => false
    }
  }

  override def canEqual(that: Any): Boolean = that.isInstanceOf[FieldSummary]

  private def doesEqual(other: FieldSummary): Boolean = {
    this.location == other.location &&
    this.idLocation == other.idLocation &&
    this.name == other.name &&
    this.modifiers == other.modifiers &&
    this.typeName == other.typeName &&
    this.readAccess == other.readAccess &&
    this.writeAccess == other.writeAccess &&
    this.dependents.sameElements(other.dependents)
  }
}

/** Summary of a type constructor */
case class ConstructorSummary(
  location: Location,
  idLocation: Location,
  modifiers: ArraySeq[Modifier],
  parameters: ArraySeq[ParameterSummary],
  dependents: Array[DependentSummary]
) {
  override def equals(that: Any): Boolean = {
    that match {
      case other: ConstructorSummary => other.canEqual(this) && doesEqual(other)
      case _                         => false
    }
  }

  override def canEqual(that: Any): Boolean = that.isInstanceOf[ConstructorSummary]

  private def doesEqual(other: ConstructorSummary): Boolean = {
    this.location == other.location &&
    this.idLocation == other.idLocation &&
    this.modifiers == other.modifiers &&
    this.parameters == other.parameters &&
    this.dependents.sameElements(other.dependents)
  }
}

/** Summary of a type method */
case class MethodSummary(
  location: Location,
  idLocation: Location,
  name: String,
  modifiers: ArraySeq[Modifier],
  var typeName: TypeName,
  parameters: ArraySeq[ParameterSummary],
  hasBlock: Boolean,
  dependents: Array[DependentSummary]
) {

  typeName = typeName.intern

  override def equals(that: Any): Boolean = {
    that match {
      case other: MethodSummary => other.canEqual(this) && doesEqual(other)
      case _                    => false
    }
  }

  override def canEqual(that: Any): Boolean = that.isInstanceOf[MethodSummary]

  private def doesEqual(other: MethodSummary): Boolean = {
    this.location == other.location &&
    this.idLocation == other.idLocation &&
    this.name == other.name &&
    this.modifiers == other.modifiers &&
    this.typeName == other.typeName &&
    this.parameters == other.parameters &&
    this.hasBlock == other.hasBlock &&
    this.dependents.sameElements(other.dependents)
  }
}

/** Summary of a constructor or method parameters */
case class ParameterSummary(name: String, var typeName: TypeName) {
  typeName = typeName.intern
}

/** Dependency information interface for detailing types of dependency */
sealed trait DependentSummary

/** Dependency information for a type */
@upickle.implicits.key("Type")
case class TypeDependentSummary(var typeId: TypeIdentifier, sourceHash: Int)
    extends DependentSummary {
  typeId = typeId.intern
}

/** Dependency information for a field */
@upickle.implicits.key("Field")
case class FieldDependentSummary(var typeId: TypeIdentifier, name: String)
    extends DependentSummary {
  typeId = typeId.intern
}

/** Dependency information for a method */
@upickle.implicits.key("Method")
case class MethodDependentSummary(
  var typeId: TypeIdentifier,
  name: String,
  parameterTypes: ArraySeq[TypeName]
) extends DependentSummary {

  typeId = typeId.intern

  override def equals(that: Any): Boolean = {
    that match {
      case other: MethodDependentSummary => other.canEqual(this) && doesEqual(other)
      case _                             => false
    }
  }

  override def canEqual(that: Any): Boolean = that.isInstanceOf[MethodDependentSummary]

  private def doesEqual(other: MethodDependentSummary): Boolean = {
    this.typeId == other.typeId &&
    this.name == other.name &&
    this.parameterTypes == other.parameterTypes
  }
}

object ApexSummary {
  implicit val rw: RW[ApexSummary] = macroRW
}

object TypeSummary {
  implicit val rw: RW[TypeSummary]      = macroRW
  implicit val rwModifier: RW[Modifier] = macroRW
}

object BlockSummary {
  implicit val rw: RW[BlockSummary] = macroRW
}

object FieldSummary {
  implicit val rw: RW[FieldSummary]     = macroRW
  implicit val rwNature: RW[Nature]     = macroRW
  implicit val rwModifier: RW[Modifier] = macroRW
}

object ConstructorSummary {
  implicit val rw: RW[ConstructorSummary] = macroRW
  implicit val rwModifier: RW[Modifier]   = macroRW
}

object MethodSummary {
  implicit val rw: RW[MethodSummary]    = macroRW
  implicit val rwModifier: RW[Modifier] = macroRW
}

object ParameterSummary {
  implicit val rw: RW[ParameterSummary] = macroRW
}

object DependentSummary {
  implicit val rw: RW[DependentSummary] = macroRW
}

object TypeDependentSummary {
  implicit val rw: RW[TypeDependentSummary] = macroRW
}

object FieldDependentSummary {
  implicit val rw: RW[FieldDependentSummary] = macroRW
}

object MethodDependentSummary {
  implicit val rw: RW[MethodDependentSummary] = macroRW
}
