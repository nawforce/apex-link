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

import com.nawforce.common.names.TypeLike
import upickle.default.{macroRW, ReadWriter => RW}

case class ApexSummary(typeSummary: TypeSummary, diagnostics: List[Diagnostic])
case class TypeSummary(sourceHash: Int, idRange: Option[RangeLocation], name: String, typeName: TypeLike,
                       nature: String, modifiers: List[String], superClass: Option[TypeLike], interfaces: List[TypeLike],
                       blocks: List[BlockSummary], fields: List[FieldSummary], constructors: List[ConstructorSummary],
                       methods: List[MethodSummary], nestedTypes: List[TypeSummary], dependents: Set[DependentSummary],
                       holders: Set[TypeLike])
case class BlockSummary(isStatic: Boolean, dependents: Set[DependentSummary])
case class FieldSummary(idRange: Option[RangeLocation], name: String, modifiers: List[String],
                        typeName: TypeLike, readAccess: String, writeAccess: String, dependents: Set[DependentSummary])
case class ConstructorSummary(idRange: Option[RangeLocation], modifiers: List[String], parameters: List[ParameterSummary],
                              dependents: Set[DependentSummary])
case class MethodSummary(idRange: Option[RangeLocation], name: String, modifiers: List[String], typeName: TypeLike,
                         parameters: List[ParameterSummary], dependents: Set[DependentSummary])
case class ParameterSummary(name: String, typeName: TypeLike)

sealed trait DependentSummary
@upickle.implicits.key("Type")
case class TypeDependentSummary(typeName: TypeLike, sourceHash: Int) extends DependentSummary
@upickle.implicits.key("Field")
case class FieldDependentSummary(typeName: TypeLike, name: String) extends DependentSummary
@upickle.implicits.key("Method")
case class MethodDependentSummary(typeName: TypeLike, name: String, parameters: List[ParameterSummary]) extends DependentSummary

case class Diagnostic(category: String, location: Location, message: String)

sealed trait Location
case class LineLocation(line: Int) extends Location
case class LineRangeLocation(start: Int, end: Int) extends Location
case class Position(line: Int, offset: Int)
case class PointLocation(position: Position) extends Location
case class RangeLocation(start: Position, end: Position) extends Location

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
