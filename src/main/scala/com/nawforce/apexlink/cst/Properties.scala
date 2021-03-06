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

package com.nawforce.apexlink.cst

import com.nawforce.apexlink.types.apex.ApexFieldLike
import com.nawforce.apexlink.types.core.{TypeDeclaration, TypeId}
import com.nawforce.pkgforce.diagnostics.PathLocation
import com.nawforce.pkgforce.modifiers.{ApexModifiers, Modifier, ModifierResults, PRIVATE_MODIFIER}
import com.nawforce.pkgforce.names.{Name, TypeName}
import com.nawforce.runtime.parsers.ApexParser.{PropertyBlockContext, PropertyDeclarationContext}
import com.nawforce.runtime.parsers.CodeParser

final case class ApexPropertyDeclaration(outerTypeId: TypeId,
                                         _modifiers: ModifierResults,
                                         typeName: TypeName,
                                         id: Id,
                                         propertyBlocks: Seq[PropertyBlock])
    extends ClassBodyDeclaration(_modifiers)
    with ApexFieldLike {

  override val name: Name = id.name
  override val idLocation: Option[PathLocation] = Some(id.location)
  override val nameLocation: PathLocation = id.location
  override def fullLocation: PathLocation = location

  val setter: Option[SetterPropertyBlock] =
    propertyBlocks.flatMap {
      case x: SetterPropertyBlock => Some(x)
      case _                      => None
    }.headOption

  val getter: Option[GetterPropertyBlock] =
    propertyBlocks.flatMap {
      case x: GetterPropertyBlock => Some(x)
      case _                      => None
    }.headOption

  private val visibility: Option[Modifier] =
    _modifiers.modifiers.find(m => ApexModifiers.visibilityModifiers.contains(m))
  override val readAccess: Modifier =
    getter
      .flatMap(_.modifiers.modifiers.headOption)
      .getOrElse(visibility.getOrElse(PRIVATE_MODIFIER))
  override val writeAccess: Modifier =
    setter
      .flatMap(_.modifiers.modifiers.headOption)
      .getOrElse(visibility.getOrElse(PRIVATE_MODIFIER))

  override def verify(context: BodyDeclarationVerifyContext): Unit = {
    val propertyType = context.getTypeAndAddDependency(typeName, context.thisType).toOption
    if (propertyType.isEmpty)
      context.missingType(id.location, typeName)

    val setters = propertyBlocks.filter(_.isInstanceOf[SetterPropertyBlock])
    propertyType.foreach(td => setters.foreach(_.verify(context, isStatic, td)))
    val getters = propertyBlocks.filter(_.isInstanceOf[GetterPropertyBlock])
    propertyType.foreach(td => getters.foreach(_.verify(context, isStatic, td)))

    if (setters.size > 1 || getters.size > 1 || propertyBlocks.isEmpty) {
      context.logError(location, "Properties must have either a single 'get' and/or a single 'set' block")
    }

    if (visibility.nonEmpty && writeAccess.order > visibility.get.order) {
      context.logError(location, "Setter visibility must be same or less than property")
    }

    if (visibility.nonEmpty && readAccess.order > visibility.get.order) {
      context.logError(location, "Getter visibility must be same or less than property")
    }

    setDepends(context.dependencies)
    context.propagateDependencies()
  }
}

object ApexPropertyDeclaration {
  def construct(parser: CodeParser,
                outerTypeId: TypeId,
                modifiers: ModifierResults,
                propertyDeclaration: PropertyDeclarationContext): ApexPropertyDeclaration = {
    val typeName = TypeReference.construct(propertyDeclaration.typeRef())
    ApexPropertyDeclaration(outerTypeId,
                            modifiers,
                            typeName,
                            Id.construct(propertyDeclaration.id()),
                            CodeParser
                              .toScala(propertyDeclaration.propertyBlock())
                              .map(pb => PropertyBlock.construct(parser, pb, typeName)),
    ).withContext(propertyDeclaration)
  }
}

sealed abstract class PropertyBlock extends CST {
  def verify(context: BodyDeclarationVerifyContext, isStatic: Boolean, propertyType: TypeDeclaration): Unit
}

final case class GetterPropertyBlock(modifiers: ModifierResults, block: Option[Block]) extends PropertyBlock {
  override def verify(context: BodyDeclarationVerifyContext, isStatic: Boolean, propertyType: TypeDeclaration): Unit = {
    block.foreach(blk =>
      context.withOuterBlockVerifyContext(isStatic) { blockContext =>
        blk.verify(blockContext)
    })
  }
}

final case class SetterPropertyBlock(modifiers: ModifierResults, typeName: TypeName, block: Option[Block])
    extends PropertyBlock {
  override def verify(context: BodyDeclarationVerifyContext, isStatic: Boolean, propertyType: TypeDeclaration): Unit = {
    context.withOuterBlockVerifyContext(isStatic) { blockContext =>
      blockContext.addVar(Name("value"), None, propertyType)
      block.foreach(_.verify(blockContext))
    }
  }
}

object PropertyBlock {
  def construct(parser: CodeParser, propertyBlockContext: PropertyBlockContext, typeName: TypeName): PropertyBlock = {
    val modifiers: ModifierResults = ApexModifiers.propertyBlockModifiers(
      parser,
      CodeParser.toScala(propertyBlockContext.modifier()),
      propertyBlockContext)
    val cst = {
      val getter = CodeParser.toScala(propertyBlockContext.getter())
      val setter = CodeParser.toScala(propertyBlockContext.setter())

      if (getter.nonEmpty) {
        GetterPropertyBlock(modifiers, Block.constructOption(parser, CodeParser.toScala(getter.get.block())))
      } else if (setter.nonEmpty) {
        SetterPropertyBlock(modifiers, typeName, Block.constructOption(parser, CodeParser.toScala(setter.get.block())))
      } else {
        throw new CSTException()
      }
    }
    cst.withContext(propertyBlockContext)
  }
}
