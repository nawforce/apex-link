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

import com.nawforce.parsers.ApexParser.{PropertyBlockContext, PropertyDeclarationContext}
import com.nawforce.types._
import com.nawforce.utils.{IssueLog, Name}

import scala.collection.JavaConverters._

final case class ApexPropertyDeclaration(_modifiers: Seq[Modifier], typeName: TypeName, id: Id,
                                         propertyBlocks: Seq[PropertyBlock])
  extends ClassBodyDeclaration(_modifiers) with FieldDeclaration {

  override val name: Name = id.name
  val setter: Option[SetterPropertyBlock] =
    propertyBlocks.flatMap {
      case x: SetterPropertyBlock => Some(x)
      case _ => None
    }.headOption
  val getter: Option[GetterPropertyBlock] =
    propertyBlocks.flatMap {
      case x: GetterPropertyBlock => Some(x)
      case _ => None
    }.headOption

  private val visibility: Option[Modifier] = _modifiers.find(m => ApexModifiers.allVisibilityModifiers.contains(m))
  override val readAccess: Modifier =
    getter.flatMap(_.modifiers.headOption).getOrElse(visibility.getOrElse(PRIVATE_MODIFIER))
  override val writeAccess: Modifier =
    setter.flatMap(_.modifiers.headOption).getOrElse(visibility.getOrElse(PRIVATE_MODIFIER))
  override def children(): List[CST] = List(id) ++ propertyBlocks.toList

  override def verify(context: VerifyContext): Unit = {
    context.addImport(typeName)
    val setters = propertyBlocks.filter(_.isInstanceOf[SetterPropertyBlock])
    val getters = propertyBlocks.filter(_.isInstanceOf[GetterPropertyBlock])
    if (setters.size > 1 || getters.size > 1 || propertyBlocks.isEmpty) {
      IssueLog.logMessage(textRange, "Properties must have either a single 'get' and/or a single 'set' block")
    }

    if (visibility.nonEmpty && writeAccess.order > visibility.get.order) {
      IssueLog.logMessage(textRange, "Setter visibility must be same or less than property")
    }

    if (visibility.nonEmpty && readAccess.order > visibility.get.order) {
      IssueLog.logMessage(textRange, "Getter visibility must be same or less than property")
    }
  }
}

object ApexPropertyDeclaration {
  def construct(modifiers: Seq[Modifier], propertyDeclaration: PropertyDeclarationContext, context: ConstructContext)
  : ApexPropertyDeclaration = {
    ApexPropertyDeclaration(modifiers,
      TypeRef.construct(propertyDeclaration.typeRef(), context),
      Id.construct(propertyDeclaration.id, context),
      propertyDeclaration.propertyBlock().asScala.map(pb => PropertyBlock.construct(pb, context)),
    ).withContext(propertyDeclaration, context)
  }
}

sealed abstract class PropertyBlock extends CST {
  def verify(context: VerifyContext): Unit
}

final case class GetterPropertyBlock(modifiers: Seq[Modifier], block: Option[Block]) extends PropertyBlock {
  override def children(): List[CST] = List() ++ block
  override def verify(context: VerifyContext): Unit = {
    block.foreach(_.verify(context))
  }
}

final case class SetterPropertyBlock(modifiers: Seq[Modifier], block: Option[Block]) extends PropertyBlock {
  override def children(): List[CST] = List() ++ block
  override def verify(context: VerifyContext): Unit = {
    block.foreach(_.verify(context))
  }
}

object PropertyBlock {
  def construct(propertyBlockContext: PropertyBlockContext, context: ConstructContext): PropertyBlock = {
    val modifiers: Seq[Modifier] = ApexModifiers.propertyBlockModifiers(
      propertyBlockContext.modifier().asScala, context, propertyBlockContext)
    val cst =
      if (propertyBlockContext.getter() != null) {
        GetterPropertyBlock(modifiers,
          Block.constructOption(propertyBlockContext.getter().block(), context))
      } else if (propertyBlockContext.setter() != null) {
        SetterPropertyBlock(modifiers,
          Block.constructOption(propertyBlockContext.setter().block(), context))
      } else {
        throw new CSTException()
      }
    cst.withContext(propertyBlockContext, context)
  }
}
