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
package com.nawforce.cst

import com.nawforce.parsers.ApexParser.{ModifierContext, PropertyBlockContext, PropertyBodyDeclarationContext, PropertyDeclarationContext}
import com.nawforce.types.{ApexModifiers, Modifier, TypeName}

import scala.collection.JavaConverters._
import scala.collection.mutable

final case class PropertyDeclaration(_modifiers: Seq[Modifier], typeRef: TypeName,
                                     variableDeclarators: VariableDeclarators,
                                     propertyDeclaration: PropertyBodyDeclaration)
  extends ClassBodyDeclaration(_modifiers) {

  override def children(): List[CST] = variableDeclarators :: propertyDeclaration :: Nil

  override def verify(imports: mutable.Set[TypeName]): Unit = {
    imports.add(typeRef)
    variableDeclarators.verify(imports)
    propertyDeclaration.verify(imports)
  }
}

object PropertyDeclaration {
  def construct(modifiers: Seq[Modifier], propertyDeclaration: PropertyDeclarationContext, context: ConstructContext): PropertyDeclaration = {
    PropertyDeclaration(modifiers, TypeRef.construct(propertyDeclaration.typeRef(), context),
      VariableDeclarators.construct(propertyDeclaration.variableDeclarators(), context),
      PropertyBodyDeclaration.construct(propertyDeclaration.propertyBodyDeclaration(), context)).withContext(propertyDeclaration, context)
  }
}

sealed abstract class PropertyBlock extends CST {
  def verify(imports: mutable.Set[TypeName]): Unit
}

final case class GetterPropertyBlock(modifiers: Seq[Modifier], block: Option[Block]) extends PropertyBlock {
  override def children(): List[CST] = List() ++ block
  override def verify(imports: mutable.Set[TypeName]): Unit = block.foreach(_.verify(imports))
}

final case class SetterPropertyBlock(modifiers: Seq[Modifier], block: Option[Block]) extends PropertyBlock {
  override def children(): List[CST] = List() ++ block
  override def verify(imports: mutable.Set[TypeName]): Unit = block.foreach(_.verify(imports))
}

object PropertyBlock {
  def construct(propertyBlockContext: PropertyBlockContext, context: ConstructContext): PropertyBlock = {
    val modifiers: Seq[ModifierContext] = propertyBlockContext.modifier().asScala
    val cst =
      if (propertyBlockContext.getter() != null) {
        GetterPropertyBlock(ApexModifiers.construct(modifiers.toList, context), Block.constructOption(propertyBlockContext.getter().block(), context))
      } else if (propertyBlockContext.setter() != null) {
        SetterPropertyBlock(ApexModifiers.construct(modifiers.toList, context), Block.constructOption(propertyBlockContext.setter().block(), context))
      } else {
        throw new CSTException()
      }
    cst.withContext(propertyBlockContext, context)
  }
}

final case class PropertyBodyDeclaration(propertyBlocks: List[PropertyBlock]) extends CST {
  override def children(): List[CST] = propertyBlocks

  def verify(imports: mutable.Set[TypeName]): Unit = {
    propertyBlocks.foreach(_.verify(imports))
  }
}

object PropertyBodyDeclaration {
  def construct(propertyBodyDeclarationContext: PropertyBodyDeclarationContext, context: ConstructContext): PropertyBodyDeclaration = {
    val blocks: Seq[PropertyBlockContext] = propertyBodyDeclarationContext.propertyBlock().asScala
    PropertyBodyDeclaration(blocks.toList.map(x => PropertyBlock.construct(x, context))).withContext(propertyBodyDeclarationContext, context)
  }
}
