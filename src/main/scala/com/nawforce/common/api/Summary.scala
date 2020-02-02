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

import com.nawforce.common.documents.TextRange
import upickle.default.{macroRW, ReadWriter => RW}

case class TypeSummary(version: Int, sourceHash: Int, idRange: Option[TextRange], name: String, typeName: String,
                       nature: String, modifiers: List[String], superClass: String, interfaces: List[String],
                       fields: List[FieldSummary], constructors: List[ConstructorSummary], methods: List[MethodSummary],
                       nestedTypes: List[TypeSummary], dependents: Set[DependentSummary])
case class FieldSummary(version: Int, range: Option[TextRange], name: String, modifiers: List[String],
                        typeName: String, readAccess: String, writeAccess: String, dependents: Set[DependentSummary])
case class ConstructorSummary(version: Int, modifiers: List[String], parameters: List[ParameterSummary],
                              dependents: Set[DependentSummary])
case class MethodSummary(version: Int, name: String, modifiers: List[String], typeName: String,
                         parameters: List[ParameterSummary], dependents: Set[DependentSummary])
case class ParameterSummary(version: Int, name: String, typeName: String)
case class DependentSummary(name: String, sourceHash: Int)

object TypeSummary {
  val defaultVersion = 1
  implicit val rw: RW[TypeSummary] = macroRW
}

object FieldSummary {
  val defaultVersion = 1
  implicit val rw: RW[FieldSummary] = macroRW
}

object ConstructorSummary {
  val defaultVersion = 1
  implicit val rw: RW[ConstructorSummary] = macroRW
}

object MethodSummary {
  val defaultVersion = 1
  implicit val rw: RW[MethodSummary] = macroRW
}

object ParameterSummary {
  val defaultVersion = 1
  implicit val rw: RW[ParameterSummary] = macroRW
}

object DependentSummary {
  val defaultVersion = 1
  implicit val rw: RW[DependentSummary] = macroRW
}
