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

package com.nawforce.apexlink.types.core

import com.nawforce.apexlink.org.Module
import com.nawforce.pkgforce.modifiers.{Modifier, ModifierOps}
import com.nawforce.pkgforce.names.{Name, TypeName}
import com.nawforce.pkgforce.parsers.{CLASS_NATURE, Nature}
import com.nawforce.pkgforce.path.PathLike

import scala.collection.immutable.ArraySeq

class BasicTypeDeclaration(val paths: ArraySeq[PathLike], module: Module, val typeName: TypeName)
    extends TypeDeclaration {

  override val moduleDeclaration: Option[Module] = Some(module)
  override val name: Name                        = typeName.name
  override val outerTypeName: Option[TypeName]   = None
  override val nature: Nature                    = CLASS_NATURE
  override val modifiers: ArraySeq[Modifier]     = ModifierOps.emptyModifiers
  override lazy val isComplete: Boolean          = true

  override val superClass: Option[TypeName]           = None
  override val interfaces: ArraySeq[TypeName]         = ArraySeq()
  override def nestedTypes: ArraySeq[TypeDeclaration] = TypeDeclaration.emptyTypeDeclarations

  override val blocks: ArraySeq[BlockDeclaration] = BlockDeclaration.emptyBlockDeclarations
  override val fields: ArraySeq[FieldDeclaration] = FieldDeclaration.emptyFieldDeclarations
  override val constructors: ArraySeq[ConstructorDeclaration] =
    ConstructorDeclaration.emptyConstructorDeclarations
  override val methods: ArraySeq[MethodDeclaration] = MethodDeclaration.emptyMethodDeclarations

  override def validate(): Unit = {}
}

class InnerBasicTypeDeclaration(_paths: ArraySeq[PathLike], _module: Module, _typeName: TypeName)
    extends BasicTypeDeclaration(_paths, _module, _typeName) {
  override val outerTypeName: Option[TypeName] = typeName.outer
}
