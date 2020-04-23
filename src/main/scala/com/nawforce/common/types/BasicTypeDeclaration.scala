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

import com.nawforce.common.cst.Modifier
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.org.PackageImpl

class BasicTypeDeclaration(pkg: PackageImpl, val typeName: TypeName)
  extends TypeDeclaration {

  override val packageDeclaration: Option[PackageImpl] = Some(pkg)
  override val name: Name = typeName.name
  override val outerTypeName: Option[TypeName] = None
  override val nature: Nature = CLASS_NATURE
  override val modifiers: Seq[Modifier] = Seq.empty
  override val isComplete: Boolean = true

  override val superClass: Option[TypeName] = None
  override val interfaces: Seq[TypeName] = Seq.empty
  override def nestedTypes: Seq[TypeDeclaration] = Seq.empty

  override val blocks: Seq[BlockDeclaration] = Seq.empty
  override val fields: Seq[FieldDeclaration]= Seq.empty
  override val constructors: Seq[ConstructorDeclaration] = Seq.empty
  override val methods: Seq[MethodDeclaration]= Seq.empty

  override def validate(): Unit = {}
}

class InnerBasicTypeDeclaration(_pkg: PackageImpl, _typeName: TypeName)
  extends BasicTypeDeclaration(_pkg, _typeName) {
  override val outerTypeName: Option[TypeName] = typeName.outer
}
