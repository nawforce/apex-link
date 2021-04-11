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
import com.nawforce.runtime.parsers.ApexParser.{IdContext, ModifierContext, PropertyBlockContext}
import com.nawforce.runtime.parsers.CodeParser
import com.nawforce.runtime.parsers.CodeParser.ParserRuleContext

sealed abstract class Modifier(final val name: String,
                               val order: Integer = 0,
                               val methodOrder: Integer = 0) {
  override def toString: String = name
}

case object WEBSERVICE_MODIFIER extends Modifier("webservice", order = 4)
case object GLOBAL_MODIFIER extends Modifier("global", order = 3, methodOrder = 2)
case object PUBLIC_MODIFIER extends Modifier("public", order = 2, methodOrder = 2)
case object PROTECTED_MODIFIER extends Modifier("protected", order = 1, methodOrder = 1)
case object PRIVATE_MODIFIER extends Modifier("private", order = 0, methodOrder = 0)
case object TEST_METHOD_MODIFIER extends Modifier("testmethod")

case object WITH_SHARING_MODIFIER extends Modifier("with sharing")
case object WITHOUT_SHARING_MODIFIER extends Modifier("without sharing")
case object INHERITED_SHARING_MODIFIER extends Modifier("inherited sharing")

case object STATIC_MODIFIER extends Modifier("static")
case object ABSTRACT_MODIFIER extends Modifier("abstract")
case object FINAL_MODIFIER extends Modifier("final")
case object OVERRIDE_MODIFIER extends Modifier("override")
case object VIRTUAL_MODIFIER extends Modifier("virtual")
case object TRANSIENT_MODIFIER extends Modifier("transient")

case object AURA_ENABLED_ANNOTATION extends Modifier("@AuraEnabled")
case object DEPRECATED_ANNOTATION extends Modifier("@Deprecated")
case object FUTURE_ANNOTATION extends Modifier("@Future")
case object INVOCABLE_METHOD_ANNOTATION extends Modifier("@InvocableMethod")
case object INVOCABLE_VARIABLE_ANNOTATION extends Modifier("@InvocableVariable")
case object ISTEST_ANNOTATION extends Modifier("@IsTest")
case object READ_ONLY_ANNOTATION extends Modifier("@ReadOnly")
case object REMOTE_ACTION_ANNOTATION extends Modifier("@RemoteAction")
case object SUPPRESS_WARNINGS_ANNOTATION extends Modifier("@SuppressWarnings")
case object TEST_SETUP_ANNOTATION extends Modifier("@TestSetup")
case object TEST_VISIBLE_ANNOTATION extends Modifier("@TestVisible")
case object NAMESPACE_ACCESSIBLE_ANNOTATION extends Modifier("@NamespaceAccessible")

case object REST_RESOURCE_ANNOTATION extends Modifier("@RestResource")
case object HTTP_DELETE_ANNOTATION extends Modifier("@HttpDelete")
case object HTTP_GET_ANNOTATION extends Modifier("@HttpGet")
case object HTTP_PATCH_ANNOTATION extends Modifier("@HttpPatch")
case object HTTP_POST_ANNOTATION extends Modifier("@HttpPost")
case object HTTP_PUT_ANNOTATION extends Modifier("@HttpPut")

object ModifierOps {
  val emptyModifiers: Array[Modifier] = Array()

  private val bitModifiers =
    Set(WEBSERVICE_MODIFIER,
        GLOBAL_MODIFIER,
        PUBLIC_MODIFIER,
        PROTECTED_MODIFIER,
        PRIVATE_MODIFIER,
        TEST_METHOD_MODIFIER,
        STATIC_MODIFIER,
        ABSTRACT_MODIFIER,
        FINAL_MODIFIER,
        OVERRIDE_MODIFIER,
        VIRTUAL_MODIFIER,
        TRANSIENT_MODIFIER,
        WITH_SHARING_MODIFIER,
        WITHOUT_SHARING_MODIFIER,
        INHERITED_SHARING_MODIFIER)

  private val annotations =
    Set(AURA_ENABLED_ANNOTATION,
        DEPRECATED_ANNOTATION,
        FUTURE_ANNOTATION,
        INVOCABLE_METHOD_ANNOTATION,
        INVOCABLE_VARIABLE_ANNOTATION,
        ISTEST_ANNOTATION,
        READ_ONLY_ANNOTATION,
        REMOTE_ACTION_ANNOTATION,
        SUPPRESS_WARNINGS_ANNOTATION,
        TEST_SETUP_ANNOTATION,
        TEST_VISIBLE_ANNOTATION,
        NAMESPACE_ACCESSIBLE_ANNOTATION,
        REST_RESOURCE_ANNOTATION,
        HTTP_DELETE_ANNOTATION,
        HTTP_GET_ANNOTATION,
        HTTP_PATCH_ANNOTATION,
        HTTP_POST_ANNOTATION,
        HTTP_PUT_ANNOTATION)

  private lazy val byName = (bitModifiers ++ annotations)
  // The parser text strips WS for '* sharing' so we do same
    .map(m => (m.name.replace(" ", "").toLowerCase, m))
    .toMap

  /** Recover from name, use safeApply  */
  def apply(name: String): Option[Modifier] = {
    byName.get(name.toLowerCase())
  }
}

// TODO: Validate arguments of the annotations
// TODO: Cross modifier/annotation checking
// TODO: Protected not supported on class, interface & enum

object ApexModifiers {
  val visibilityModifiers =
    Seq(GLOBAL_MODIFIER, PUBLIC_MODIFIER, PROTECTED_MODIFIER, PRIVATE_MODIFIER)
  private val sharingModifiers =
    Seq(WITH_SHARING_MODIFIER, WITHOUT_SHARING_MODIFIER, INHERITED_SHARING_MODIFIER)

  private val legalTypeAnnotations: Set[Modifier] =
    Set(DEPRECATED_ANNOTATION,
        TEST_VISIBLE_ANNOTATION,
        SUPPRESS_WARNINGS_ANNOTATION,
        NAMESPACE_ACCESSIBLE_ANNOTATION)

  private val legalTypeModifiers: Set[Modifier] =
    Set(GLOBAL_MODIFIER, PUBLIC_MODIFIER, PRIVATE_MODIFIER)

  private val legalTypeModifiersAndAnnotations: Set[Modifier] =
    legalTypeAnnotations ++ legalTypeModifiers

  private val legalClassAnnotations: Set[Modifier] =
    legalTypeAnnotations ++ Set(ISTEST_ANNOTATION, REST_RESOURCE_ANNOTATION)

  private val legalClassModifiers: Set[Modifier] =
    legalTypeModifiers ++ Set(ABSTRACT_MODIFIER, VIRTUAL_MODIFIER) ++ sharingModifiers.toSet

  private val legalClassModifiersAndAnnotations: Set[Modifier] =
    legalClassAnnotations ++ legalClassModifiers

  private val legalInterfaceModifiers: Set[Modifier] =
    Set(GLOBAL_MODIFIER, PUBLIC_MODIFIER, PRIVATE_MODIFIER, VIRTUAL_MODIFIER)

  private val legalInterfaceModifiersAndAnnotations: Set[Modifier] =
    legalTypeAnnotations ++ legalInterfaceModifiers

  private val legalFieldModifiers: Set[Modifier] =
    visibilityModifiers.toSet ++ Set(FINAL_MODIFIER,
                                     STATIC_MODIFIER,
                                     TRANSIENT_MODIFIER,
                                     WEBSERVICE_MODIFIER)

  private val legalFieldAnnotations: Set[Modifier] =
    Set(AURA_ENABLED_ANNOTATION,
        DEPRECATED_ANNOTATION,
        INVOCABLE_VARIABLE_ANNOTATION,
        TEST_VISIBLE_ANNOTATION,
        SUPPRESS_WARNINGS_ANNOTATION)

  private val legalFieldModifiersAndAnnotations
    : Set[Modifier] = legalFieldAnnotations ++ legalFieldModifiers

  private val legalInnerFieldModifiersAndAnnotations
    : Set[Modifier] = legalFieldModifiersAndAnnotations - STATIC_MODIFIER

  private val legalConstructorModifiersAndAnnotations
    : Set[Modifier] = visibilityModifiers.toSet ++ legalTypeAnnotations

  private val legalParameterModifiersAndAnnotations: Set[Modifier] = Set(FINAL_MODIFIER)

  private val legalCatchModifiersAndAnnotations: Set[Modifier] =
    Set(FINAL_MODIFIER, STATIC_MODIFIER)

  private val legalLocalVarsModifiersAndAnnotations: Set[Modifier] =
    Set(FINAL_MODIFIER, TRANSIENT_MODIFIER)

  private val legalTriggerLocalVarsModifiersAndAnnotations: Set[Modifier] =
    Set(PUBLIC_MODIFIER, PRIVATE_MODIFIER, STATIC_MODIFIER, FINAL_MODIFIER, TRANSIENT_MODIFIER, TEST_VISIBLE_ANNOTATION)

  /* Convert parser contexts to Modifiers */
  def asModifiers(modifierContexts: Seq[ModifierContext],
                  allow: Set[Modifier],
                  pluralName: String,
                  logger: CodeParserLogger,
                  idContext: ParserRuleContext): Seq[Modifier] = {

    val modifiers = toModifiers(modifierContexts, allow, pluralName, logger)
    if (modifiers.size == modifierContexts.size) {
      val duplicates = modifiers.groupBy(identity).collect { case (_, Seq(_, y, _*)) => y }
      if (duplicates.nonEmpty) {
        logger.logError(idContext, s"Modifier '${duplicates.head.toString}' is used more than once")
      }
    }
    modifiers.distinct
  }

  private def toModifiers(modifierContexts: Seq[ModifierContext],
                          allow: Set[Modifier],
                          pluralName: String,
                          logger: CodeParserLogger): Seq[Modifier] = {
    modifierContexts.flatMap(modifierContext => {
      val annotation = CodeParser.toScala(modifierContext.annotation())
      var modifier =
        if (annotation.nonEmpty)
          annotation.flatMap(a =>
            ModifierOps("@" + CodeParser.getText(a.qualifiedName()).toLowerCase))
        else
          ModifierOps(CodeParser.getText(modifierContext).toLowerCase)
      if (!modifier.exists(allow.contains)) {
        modifier = None
        val modifierType = if (annotation.nonEmpty) "Annotation" else "Modifier"
        logger.logError(
          modifierContext,
          s"$modifierType '${CodeParser.getText(modifierContext)}' is not supported on $pluralName")
      }
      modifier
    })
  }

  def deduplicateVisibility(modifiers: Seq[Modifier],
                            pluralName: String,
                            logger: CodeParserLogger,
                            idContext: ParserRuleContext): Seq[Modifier] = {
    if (modifiers.intersect(visibilityModifiers).size > 1) {
      if (logger.isEmpty)
        logger.logWarning(
          idContext,
          s"Only one visibility modifier from 'global', 'public' & 'private' should be used on $pluralName")
      PUBLIC_MODIFIER +: modifiers.diff(visibilityModifiers)
    } else {
      modifiers
    }
  }

  private def deduplicateSharing(modifiers: Seq[Modifier],
                                 pluralName: String,
                                 logger: CodeParserLogger,
                                 idContext: ParserRuleContext): Seq[Modifier] = {
    if (modifiers.intersect(sharingModifiers).size > 1) {
      if (logger.isEmpty)
        logger.logWarning(
          idContext,
          s"Only one sharing modifier from 'with sharing', 'without sharing' & 'inherited sharing' should be used on $pluralName")
      WITHOUT_SHARING_MODIFIER +: modifiers.diff(visibilityModifiers)
    } else {
      modifiers
    }
  }

  private def deduplicate(modifiers: Seq[Modifier],
                          pluralName: String,
                          logger: CodeParserLogger,
                          idContext: ParserRuleContext): Seq[Modifier] = {
    deduplicateVisibility(deduplicateSharing(modifiers, pluralName, logger, idContext),
                          pluralName,
                          logger,
                          idContext)
  }

  def classModifiers(parser: CodeParser,
                     modifierContexts: Seq[ModifierContext],
                     outer: Boolean,
                     idContext: IdContext): ModifierResults = {

    val logger = new CodeParserLogger(parser)
    val mods = deduplicate(asModifiers(modifierContexts,
                                       legalClassModifiersAndAnnotations,
                                       "classes",
                                       logger,
                                       idContext),
                           "classes",
                           logger,
                           idContext)

    val results =
      if (logger.isEmpty) {
        if (outer && !mods.contains(ISTEST_ANNOTATION) && mods.contains(PRIVATE_MODIFIER)) {
          logger.logError(idContext, s"Private modifier is not allowed on outer classes")
          mods.filterNot(_ == PRIVATE_MODIFIER)
        } else if (outer && !mods.contains(ISTEST_ANNOTATION) && !(mods.contains(GLOBAL_MODIFIER) || mods
                     .contains(PUBLIC_MODIFIER))) {
          logger.logError(idContext, s"Outer classes must be declared either 'global' or 'public'")
          PUBLIC_MODIFIER +: mods
        } else if (mods.contains(ABSTRACT_MODIFIER) && mods.contains(VIRTUAL_MODIFIER)) {
          logger.logError(idContext, s"Abstract classes are virtual classes")
          mods.filterNot(_ == VIRTUAL_MODIFIER)
        } else if (!outer && mods.contains(ISTEST_ANNOTATION)) {
          logger.logError(idContext, s"isTest can only be used on outer classes")
          mods.filterNot(_ == ISTEST_ANNOTATION)
        } else {
          mods
        }
      } else {
        mods
      }

    ModifierResults(results.toArray, logger.issues).intern
  }

  def interfaceModifiers(parser: CodeParser,
                         modifierContexts: Seq[ModifierContext],
                         outer: Boolean,
                         idContext: IdContext): ModifierResults = {

    val logger = new CodeParserLogger(parser)
    val mods = deduplicateVisibility(asModifiers(modifierContexts,
                                                 legalInterfaceModifiersAndAnnotations,
                                                 "interfaces",
                                                 logger,
                                                 idContext),
                                     "interfaces",
                                     logger,
                                     idContext)

    val results = {
      if (logger.isEmpty) {
        if (outer && mods.contains(PRIVATE_MODIFIER)) {
          logger.logError(idContext, s"Private modifier is not allowed on outer interfaces")
          mods.filterNot(_ == PRIVATE_MODIFIER)
        } else if (outer && !(mods.contains(GLOBAL_MODIFIER) || mods.contains(PUBLIC_MODIFIER))) {
          logger.logError(idContext,
                          s"Outer interfaces must be declared either 'global' or 'public'")
          PUBLIC_MODIFIER +: mods
        } else {
          mods
        }
      } else {
        mods
      }
    }
    ModifierResults(results.toArray, logger.issues).intern
  }

  def enumModifiers(parser: CodeParser,
                    modifierContexts: Seq[ModifierContext],
                    outer: Boolean,
                    idContext: IdContext): ModifierResults = {

    val logger = new CodeParserLogger(parser)
    val mods = deduplicateVisibility(
      asModifiers(modifierContexts, legalTypeModifiersAndAnnotations, "enums", logger, idContext),
      "enums",
      logger,
      idContext)

    val results = {
      if (logger.isEmpty) {
        if (outer && mods.contains(PRIVATE_MODIFIER)) {
          logger.logError(idContext, s"Private modifier is not allowed on outer enums")
          mods.filterNot(_ == PRIVATE_MODIFIER)
        } else if (outer && !(mods.contains(GLOBAL_MODIFIER) || mods.contains(PUBLIC_MODIFIER))) {
          logger.logError(idContext, s"Outer enums must be declared either 'global' or 'public'")
          PUBLIC_MODIFIER +: mods
        } else {
          mods
        }
      } else {
        mods
      }
    }
    ModifierResults(results.toArray, logger.issues).intern
  }

  def fieldModifiers(parser: CodeParser,
                     modifierContexts: Seq[ModifierContext],
                     outer: Boolean,
                     idContext: IdContext): ModifierResults = {

    val logger = new CodeParserLogger(parser)
    val mods = deduplicateVisibility(
      asModifiers(modifierContexts,
                  if (outer) legalFieldModifiersAndAnnotations
                  else legalInnerFieldModifiersAndAnnotations,
                  if (outer) "fields" else "inner class fields",
                  logger,
                  idContext),
      "fields",
      logger,
      idContext)

    val results = {
      if (mods.intersect(visibilityModifiers).isEmpty && mods.contains(WEBSERVICE_MODIFIER)) {
        GLOBAL_MODIFIER +: mods
      } else if (!mods.intersect(visibilityModifiers).contains(GLOBAL_MODIFIER) && mods.contains(
                   WEBSERVICE_MODIFIER)) {
        logger.logError(idContext, s"webservice fields must be global")
        GLOBAL_MODIFIER +: mods.diff(visibilityModifiers)
      } else if (mods.intersect(visibilityModifiers).isEmpty) {
        PRIVATE_MODIFIER +: mods
      } else {
        mods
      }
    }
    ModifierResults(results.toArray, logger.issues).intern
  }

  def propertyBlockModifiers(parser: CodeParser,
                             modifierContexts: Seq[ModifierContext],
                             idContext: PropertyBlockContext): ModifierResults = {

    val logger = new CodeParserLogger(parser)
    val mods = deduplicateVisibility(asModifiers(modifierContexts,
                                                 visibilityModifiers.toSet,
                                                 "property set/get",
                                                 logger,
                                                 idContext),
                                     "property set/get",
                                     logger,
                                     idContext)
    ModifierResults(mods.toArray, logger.issues).intern
  }

  def constructorModifiers(parser: CodeParser,
                           modifierContexts: Seq[ModifierContext],
                           context: ParserRuleContext): ModifierResults = {

    val logger = new CodeParserLogger(parser)
    val mods = deduplicateVisibility(asModifiers(modifierContexts,
                                                 legalConstructorModifiersAndAnnotations,
                                                 "constructors",
                                                 logger,
                                                 context),
                                     "constructors",
                                     logger,
                                     context)

    val results = {
      if (mods.intersect(visibilityModifiers).isEmpty) {
        PRIVATE_MODIFIER +: mods
      } else {
        mods
      }
    }
    ModifierResults(results.toArray, logger.issues).intern
  }

  def parameterModifiers(parser: CodeParser,
                         modifierContexts: Seq[ModifierContext],
                         context: ParserRuleContext): ModifierResults = {

    val logger = new CodeParserLogger(parser)
    val mods = deduplicateVisibility(asModifiers(modifierContexts,
                                                 legalParameterModifiersAndAnnotations,
                                                 "parameters",
                                                 logger,
                                                 context),
                                     "parameters",
                                     logger,
                                     context)

    ModifierResults(mods.toArray, logger.issues).intern
  }

  def catchModifiers(parser: CodeParser,
                     modifierContexts: Seq[ModifierContext],
                     context: ParserRuleContext): ModifierResults = {

    val logger = new CodeParserLogger(parser)
    val mods = deduplicateVisibility(asModifiers(modifierContexts,
                                                 legalCatchModifiersAndAnnotations,
                                                 "catch variables",
                                                 logger,
                                                 context),
                                     "catch variables",
                                     logger,
                                     context)

    ModifierResults(mods.toArray, logger.issues).intern
  }

  def localVariableModifiers(parser: CodeParser,
                             modifierContexts: Seq[ModifierContext],
                             context: ParserRuleContext,
                             isTrigger: Boolean): ModifierResults = {

    val logger = new CodeParserLogger(parser)
    val mods = deduplicateVisibility(
      asModifiers(modifierContexts,
                  if (isTrigger) legalTriggerLocalVarsModifiersAndAnnotations
                  else legalLocalVarsModifiersAndAnnotations,
                  "local variables",
                  logger,
                  context),
      "local variables",
      logger,
      context)

    ModifierResults(mods.toArray, logger.issues).intern
  }
}
