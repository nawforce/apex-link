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
package com.nawforce.common.api

import upickle.default.{macroRW, ReadWriter => RW}

/**
  * Summary types are used both by the disk cache and to provide a quick & cheap way to examine what is available
  * in a type. See [[Package.getViewOfType()]] for more access to more detailed information.
  */

/** Summary of an Apex class with diagnostic information */
case class ApexSummary(typeSummary: TypeSummary, diagnostics: List[Diagnostic])

/** Summary of a type */
case class TypeSummary(sourceHash: Int, idRange: Option[RangeLocation], name: String, typeName: TypeName,
                       nature: String, modifiers: List[String], superClass: Option[TypeName], interfaces: List[TypeName],
                       blocks: List[BlockSummary], fields: List[FieldSummary], constructors: List[ConstructorSummary],
                       methods: List[MethodSummary], nestedTypes: List[TypeSummary], dependents: Set[DependentSummary])

/** Summary of a initialiser block */
case class BlockSummary(isStatic: Boolean, dependents: Set[DependentSummary])

/** Summary of a type field (or property)*/
case class FieldSummary(idRange: Option[RangeLocation], name: String, modifiers: List[String],
                        typeName: TypeName, readAccess: String, writeAccess: String, dependents: Set[DependentSummary])

/** Summary of a type constructor*/
case class ConstructorSummary(idRange: Option[RangeLocation], modifiers: List[String], parameters: List[ParameterSummary],
                              dependents: Set[DependentSummary])

/** Summary of a type method*/
case class MethodSummary(idRange: Option[RangeLocation], name: String, modifiers: List[String], typeName: TypeName,
                         parameters: List[ParameterSummary], dependents: Set[DependentSummary])

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
case class MethodDependentSummary(typeId: TypeIdentifier, name: String, parameters: List[ParameterSummary]) extends DependentSummary

/** A diagnostic message, category tells us what type of diagnostic this is while location and messages provide details */
case class Diagnostic(category: String, location: Location, message: String)

/** Location interface for identify a sub-part of a file */
sealed trait Location

/** Single line location */
case class LineLocation(line: Int) extends Location

/** Range of lines location */
case class LineRangeLocation(start: Int, end: Int) extends Location

/** Position in a file */
case class Position(line: Int, offset: Int)

/** Single position location */
case class PointLocation(position: Position) extends Location

/** Range between to positions */
case class RangeLocation(start: Position, end: Position) extends Location

/** Combined path and location within the path */
case class PathLocation(path: String, location: Location)

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

object Diagnostic {
  implicit val rw: RW[Diagnostic] = macroRW
}

object Location {
  implicit val rw: RW[Location] = RW.merge(LineLocation.rw, LineRangeLocation.rw, PointLocation.rw, RangeLocation.rw)
}

object LineLocation {
  implicit val rw: RW[LineLocation] = macroRW
}

object LineRangeLocation {
  implicit val rw: RW[LineRangeLocation] = macroRW
}

object PointLocation {
  implicit val rw: RW[PointLocation] = macroRW
}

object RangeLocation {
  implicit val rw: RW[RangeLocation] = macroRW
}

object Position {
  implicit val rw: RW[Position] = macroRW
}

object PathLocation {
  implicit val rw: RW[PathLocation] = macroRW
}
