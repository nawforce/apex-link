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
package com.nawforce.common.modifiers

import com.nawforce.common.diagnostics.CodeParserLogger
import com.nawforce.common.modifiers.ApexModifiers.{asModifiers, visibilityModifiers}
import com.nawforce.runtime.parsers.ApexParser.ModifierContext
import com.nawforce.runtime.parsers.CodeParser
import com.nawforce.runtime.parsers.CodeParser.ParserRuleContext

sealed abstract class MethodOwnerNature(final val name: String) {
  override def toString: String = name
}

case object FINAL_METHOD_NATURE extends MethodOwnerNature("final class")
case object VIRTUAL_METHOD_NATURE extends MethodOwnerNature("virtual class")
case object ABSTRACT_METHOD_NATURE extends MethodOwnerNature("abstract class")
case object INTERFACE_METHOD_NATURE extends MethodOwnerNature("interface")
case object ENUM_METHOD_NATURE extends MethodOwnerNature("enum")

object MethodModifiers {

  private val legalMethodModifiers: Set[Modifier] = visibilityModifiers.toSet ++ Set(
    ABSTRACT_MODIFIER,
    OVERRIDE_MODIFIER,
    STATIC_MODIFIER,
    TEST_METHOD_MODIFIER,
    WEBSERVICE_MODIFIER,
    VIRTUAL_MODIFIER)

  private val legalMethodAnnotations: Set[Modifier] = Set(AURA_ENABLED_ANNOTATION,
                                                          DEPRECATED_ANNOTATION,
                                                          FUTURE_ANNOTATION,
                                                          INVOCABLE_METHOD_ANNOTATION,
                                                          ISTEST_ANNOTATION,
                                                          TEST_VISIBLE_ANNOTATION,
                                                          NAMESPACE_ACCESSIBLE_ANNOTATION,
                                                          READ_ONLY_ANNOTATION,
                                                          SUPPRESS_WARNINGS_ANNOTATION,
                                                          READ_ONLY_ANNOTATION,
                                                          SUPPRESS_WARNINGS_ANNOTATION,
                                                          TEST_SETUP_ANNOTATION,
                                                          HTTP_DELETE_ANNOTATION,
                                                          HTTP_GET_ANNOTATION,
                                                          HTTP_PATCH_ANNOTATION,
                                                          HTTP_POST_ANNOTATION,
                                                          HTTP_PUT_ANNOTATION,
                                                          REMOTE_ACTION_ANNOTATION)

  private val legalMethodModifiersAndAnnotations
    : Set[Modifier] = legalMethodAnnotations ++ legalMethodModifiers

  def classMethodModifiers(parser: CodeParser,
                           modifierContexts: Seq[ModifierContext],
                           context: ParserRuleContext,
                           ownerNature: MethodOwnerNature,
                           isOuter: Boolean): ModifierResults = {

    val logger = new CodeParserLogger(parser)
    val mods = ApexModifiers.deduplicateVisibility(
      asModifiers(modifierContexts, legalMethodModifiersAndAnnotations, "methods", logger, context),
      "methods",
      logger,
      context)

    val results = {
      if (mods.intersect(visibilityModifiers).isEmpty && mods.contains(WEBSERVICE_MODIFIER)) {
        GLOBAL_MODIFIER +: mods
      } else if (!mods.intersect(visibilityModifiers).contains(GLOBAL_MODIFIER) && mods.contains(
                   WEBSERVICE_MODIFIER)) {
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
    ModifierResults(results.toArray, logger.issues).intern
  }

  def interfaceMethodModifiers(parser: CodeParser,
                               modifierContexts: Seq[ModifierContext],
                               context: ParserRuleContext,
                               isOuter: Boolean): ModifierResults = {
    // TODO: Replace with more specific handling
    classMethodModifiers(parser, modifierContexts, context, INTERFACE_METHOD_NATURE, isOuter)
  }
}
