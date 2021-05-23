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
package com.nawforce.apexlink.api

import com.nawforce.pkgforce.diagnostics.{Diagnostic, Location}
import com.nawforce.pkgforce.names.{TypeIdentifier, TypeName}
import upickle.default.{macroRW, ReadWriter => RW}

/**
  * Summary types are used both by the disk cache and to provide a quick & cheap way to examine what is available
  * in a type.
  */
/** Summary of an Apex class with diagnostic information */
case class ApexSummary(typeSummary: TypeSummary, diagnostics: Array[Diagnostic])

/** Summary of a type */
case class TypeSummary(sourceHash: Int,
                       idRange: Option[Location],
                       name: String,
                       typeName: TypeName,
                       nature: String,
                       modifiers: Array[String],
                       superClass: Option[TypeName],
                       interfaces: Array[TypeName],
                       blocks: Array[BlockSummary],
                       fields: Array[FieldSummary],
                       constructors: Array[ConstructorSummary],
                       methods: Array[MethodSummary],
                       nestedTypes: Array[TypeSummary],
                       dependents: Array[DependentSummary]) {

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
    this.idRange == other.idRange &&
    this.name == other.name &&
    this.typeName == other.typeName &&
    this.nature == other.nature &&
    this.modifiers.sameElements(other.modifiers) &&
    this.superClass == other.superClass &&
    this.interfaces.sameElements(other.interfaces) &&
    this.blocks.sameElements(other.blocks) &&
    this.fields.sameElements(other.fields) &&
    this.constructors.sameElements(other.constructors) &&
    this.methods.sameElements(other.methods) &&
    this.nestedTypes.sameElements(other.nestedTypes) &&
    this.dependents.sameElements(other.dependents)
  }
}

case class BlockSummary(isStatic: Boolean, dependents: Array[DependentSummary]) {

  override def equals(that: Any): Boolean = {
    that match {
      case other: BlockSummary => other.canEqual(this) && doesEqual(other)
      case _                   => false
    }
  }

  override def canEqual(that: Any): Boolean = that.isInstanceOf[BlockSummary]

  private def doesEqual(other: BlockSummary): Boolean = {
    this.isStatic == other.isStatic &&
    this.dependents.sameElements(other.dependents)
  }
}

/** Summary of a type field (or property)*/
case class FieldSummary(idRange: Option[Location],
                        name: String,
                        modifiers: Array[String],
                        typeName: TypeName,
                        readAccess: String,
                        writeAccess: String,
                        dependents: Array[DependentSummary]) {
  override def equals(that: Any): Boolean = {
    that match {
      case other: FieldSummary => other.canEqual(this) && doesEqual(other)
      case _                   => false
    }
  }

  override def canEqual(that: Any): Boolean = that.isInstanceOf[FieldSummary]

  private def doesEqual(other: FieldSummary): Boolean = {
    this.idRange == other.idRange &&
    this.name == other.name &&
    this.modifiers.sameElements(other.modifiers) &&
    this.typeName == other.typeName &&
    this.readAccess == other.readAccess &&
    this.writeAccess == other.writeAccess &&
    this.dependents.sameElements(other.dependents)
  }
}

/** Summary of a type constructor*/
case class ConstructorSummary(idRange: Option[Location],
                              modifiers: Array[String],
                              parameters: Array[ParameterSummary],
                              dependents: Array[DependentSummary]) {
  override def equals(that: Any): Boolean = {
    that match {
      case other: ConstructorSummary => other.canEqual(this) && doesEqual(other)
      case _                         => false
    }
  }

  override def canEqual(that: Any): Boolean = that.isInstanceOf[ConstructorSummary]

  private def doesEqual(other: ConstructorSummary): Boolean = {
    this.idRange == other.idRange &&
    this.modifiers.sameElements(other.modifiers) &&
    this.parameters.sameElements(other.parameters) &&
    this.dependents.sameElements(other.dependents)
  }
}

/** Summary of a type method*/
case class MethodSummary(idRange: Option[Location],
                         name: String,
                         modifiers: Array[String],
                         typeName: TypeName,
                         parameters: Array[ParameterSummary],
                         hasBlock: Boolean,
                         dependents: Array[DependentSummary]) {
  override def equals(that: Any): Boolean = {
    that match {
      case other: MethodSummary => other.canEqual(this) && doesEqual(other)
      case _                    => false
    }
  }

  override def canEqual(that: Any): Boolean = that.isInstanceOf[MethodSummary]

  private def doesEqual(other: MethodSummary): Boolean = {
    this.idRange == other.idRange &&
    this.name == other.name &&
    this.modifiers.sameElements(other.modifiers) &&
    this.typeName == other.typeName &&
    this.parameters.sameElements(other.parameters) &&
    this.hasBlock == other.hasBlock &&
    this.dependents.sameElements(other.dependents)
  }
}

/** Summary of a constructor or method parameters*/
case class ParameterSummary(name: String, typeName: TypeName)

/** Dependency information interface for detailing types of dependency */
sealed trait DependentSummary

/** Dependency information for a type */
@upickle.implicits.key("Type")
case class TypeDependentSummary(typeId: TypeIdentifier, sourceHash: Int) extends DependentSummary

/** Dependency information for a field */
@upickle.implicits.key("Field")
case class FieldDependentSummary(typeId: TypeIdentifier, name: String) extends DependentSummary

/** Dependency information for a method */
@upickle.implicits.key("Method")
case class MethodDependentSummary(typeId: TypeIdentifier,
                                  name: String,
                                  parameterTypes: Array[TypeName])
    extends DependentSummary {
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
    this.parameterTypes.sameElements(other.parameterTypes)
  }
}

object ApexSummary {
  implicit val rw: RW[ApexSummary] = macroRW
}

object TypeSummary {
  implicit val rw: RW[TypeSummary] = macroRW
}

object BlockSummary {
  implicit val rw: RW[BlockSummary] = macroRW
}

object FieldSummary {
  implicit val rw: RW[FieldSummary] = macroRW
}

object ConstructorSummary {
  implicit val rw: RW[ConstructorSummary] = macroRW
}

object MethodSummary {
  implicit val rw: RW[MethodSummary] = macroRW
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
