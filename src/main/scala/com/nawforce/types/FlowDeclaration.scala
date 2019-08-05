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

import java.nio.file.{Path, Paths}

import com.nawforce.utils.Name

import scala.collection.mutable

final case class FlowDeclaration() extends TypeDeclaration {
  val name: Name = Name.flow
  val path: Path = Paths.get("Flow")
  val typeName: TypeName = TypeName(name)
  val outerTypeName: Option[TypeName] = None
  val nature: Nature = CLASS_NATURE
  val modifiers: Seq[Modifier] = Seq.empty
  val isComplete: Boolean = true

  val superClass: Option[TypeName] = Some(TypeName.SObject)
  val interfaces: Seq[TypeName] = Seq.empty
  val nestedTypes: Seq[TypeDeclaration] = Seq.empty

  val blocks: Seq[BlockDeclaration] = Seq.empty
  val fields: Seq[FieldDeclaration]= Seq.empty
  val constructors: Seq[ConstructorDeclaration] = Seq.empty
  val methods: Seq[MethodDeclaration]= Seq.empty

  def validate(): Unit = {}
  def dependencies(): Set[TypeDeclaration] = Set.empty
  def collectDependencies(dependencies: mutable.Set[TypeDeclaration]): Unit = {}
}
