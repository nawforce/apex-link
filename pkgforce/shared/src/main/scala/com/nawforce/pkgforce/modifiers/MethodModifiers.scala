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
package com.nawforce.pkgforce.modifiers

import com.nawforce.apexparser.ApexParser.ModifierContext
import com.nawforce.pkgforce.diagnostics.CodeParserLogger
import com.nawforce.pkgforce.modifiers.ApexModifiers.{asModifiers, visibilityModifiers}
import com.nawforce.runtime.parsers.CodeParser
import com.nawforce.runtime.parsers.CodeParser.ParserRuleContext

import scala.collection.compat.immutable.ArraySeq

sealed abstract class MethodOwnerNature(final val name: String) {
  override def toString: String = name
}

case object FINAL_METHOD_NATURE     extends MethodOwnerNature("final class")
case object VIRTUAL_METHOD_NATURE   extends MethodOwnerNature("virtual class")
case object ABSTRACT_METHOD_NATURE  extends MethodOwnerNature("abstract class")
case object INTERFACE_METHOD_NATURE extends MethodOwnerNature("interface")
case object ENUM_METHOD_NATURE      extends MethodOwnerNature("enum")

object MethodModifiers {

  private val MethodModifiers: Set[Modifier] = visibilityModifiers.toSet ++ Set(
    ABSTRACT_MODIFIER,
    OVERRIDE_MODIFIER,
    STATIC_MODIFIER,
    TEST_METHOD_MODIFIER,
    WEBSERVICE_MODIFIER,
    VIRTUAL_MODIFIER
  )

  private val MethodAnnotations: Set[Modifier] = Set(
    AURA_ENABLED_ANNOTATION,
    DEPRECATED_ANNOTATION,
    FUTURE_ANNOTATION,
    INVOCABLE_METHOD_ANNOTATION,
    ISTEST_ANNOTATION,
    TEST_VISIBLE_ANNOTATION,
    NAMESPACE_ACCESSIBLE_ANNOTATION,
    READ_ONLY_ANNOTATION,
    SUPPRESS_WARNINGS_ANNOTATION,
    TEST_SETUP_ANNOTATION,
    HTTP_DELETE_ANNOTATION,
    HTTP_GET_ANNOTATION,
    HTTP_PATCH_ANNOTATION,
    HTTP_POST_ANNOTATION,
    HTTP_PUT_ANNOTATION,
    REMOTE_ACTION_ANNOTATION
  )

  private val MethodModifiersAndAnnotations: Set[Modifier] = MethodAnnotations ++ MethodModifiers

  def classMethodModifiers(
    parser: CodeParser,
    modifierContexts: ArraySeq[ModifierContext],
    context: ParserRuleContext,
    ownerNature: MethodOwnerNature,
    isOuter: Boolean
  ): ModifierResults = {

    val logger = new CodeParserLogger(parser)
    val mods = ApexModifiers.deduplicateVisibility(
      asModifiers(modifierContexts, MethodModifiersAndAnnotations, "methods", logger, context),
      "methods",
      logger,
      context
    )

    val results = {
      if (mods.intersect(visibilityModifiers).isEmpty && mods.contains(WEBSERVICE_MODIFIER)) {
        GLOBAL_MODIFIER +: mods
      } else if (
        !mods.intersect(visibilityModifiers).contains(GLOBAL_MODIFIER) && mods.contains(
          WEBSERVICE_MODIFIER
        )
      ) {
        logger.logError(context, s"webservice methods must be global")
        GLOBAL_MODIFIER +: mods.diff(visibilityModifiers)
      } else if (isOuter && mods.contains(WEBSERVICE_MODIFIER)) {
        logger.logError(context, s"webservice methods can only be declared on outer classes")
        GLOBAL_MODIFIER +: mods.diff(visibilityModifiers)
      } else if (mods.contains(VIRTUAL_MODIFIER) && mods.contains(ABSTRACT_MODIFIER)) {
        logger.logError(context, s"abstract methods are virtual methods")
        mods.filterNot(_ == VIRTUAL_MODIFIER)
      } else if (ownerNature != ABSTRACT_METHOD_NATURE && mods.contains(ABSTRACT_MODIFIER)) {
        logger.logError(context, s"abstract methods can only be declared on abstract classes")
        mods
      } else {
        mods
      }
    }
    ModifierResults(results, logger.issues).intern
  }

  def interfaceMethodModifiers(
    parser: CodeParser,
    modifierContexts: ArraySeq[ModifierContext],
    context: ParserRuleContext,
    isOuter: Boolean
  ): ModifierResults = {
    val logger = new CodeParserLogger(parser)
    val mods = ApexModifiers.deduplicateVisibility(
      asModifiers(modifierContexts, Set.empty, "interface methods", logger, context),
      "methods",
      logger,
      context
    )

    ModifierResults((mods ++ ArraySeq(VIRTUAL_MODIFIER, PUBLIC_MODIFIER)), logger.issues).intern
  }
}
