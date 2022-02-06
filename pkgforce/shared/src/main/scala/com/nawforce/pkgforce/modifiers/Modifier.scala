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

import com.nawforce.apexparser.ApexParser.{IdContext, ModifierContext, PropertyBlockContext}
import com.nawforce.pkgforce.diagnostics.CodeParserLogger
import com.nawforce.pkgforce.diagnostics.Duplicates.IterableOps
import com.nawforce.runtime.parsers.CodeParser
import com.nawforce.runtime.parsers.CodeParser.ParserRuleContext

import scala.collection.compat.immutable.ArraySeq

sealed abstract class Modifier(
  final val name: String,
  val order: Integer = 0,
  val methodOrder: Integer = 0
) {
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

case object SUPPRESS_WARNINGS_ANNOTATION_PMD extends Modifier("@SuppressWarnings('PMD')")

case object SUPPRESS_WARNINGS_ANNOTATION_UNUSED extends Modifier("@SuppressWarnings('Unused')")

case object TEST_SETUP_ANNOTATION extends Modifier("@TestSetup")

case object TEST_VISIBLE_ANNOTATION extends Modifier("@TestVisible")

case object NAMESPACE_ACCESSIBLE_ANNOTATION extends Modifier("@NamespaceAccessible")

case object JSON_ACCESS_ANNOTATION extends Modifier("@JsonAccess")

case object REST_RESOURCE_ANNOTATION extends Modifier("@RestResource")

case object HTTP_DELETE_ANNOTATION extends Modifier("@HttpDelete")

case object HTTP_GET_ANNOTATION extends Modifier("@HttpGet")

case object HTTP_PATCH_ANNOTATION extends Modifier("@HttpPatch")

case object HTTP_POST_ANNOTATION extends Modifier("@HttpPost")

case object HTTP_PUT_ANNOTATION extends Modifier("@HttpPut")

object ModifierOps {
  val emptyModifiers: ArraySeq[Modifier] = ArraySeq()

  private val bitModifiers =
    Set(
      WEBSERVICE_MODIFIER,
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
      INHERITED_SHARING_MODIFIER
    )

  private val annotations =
    Set(
      AURA_ENABLED_ANNOTATION,
      DEPRECATED_ANNOTATION,
      FUTURE_ANNOTATION,
      INVOCABLE_METHOD_ANNOTATION,
      INVOCABLE_VARIABLE_ANNOTATION,
      ISTEST_ANNOTATION,
      READ_ONLY_ANNOTATION,
      REMOTE_ACTION_ANNOTATION,
      SUPPRESS_WARNINGS_ANNOTATION_PMD,
      SUPPRESS_WARNINGS_ANNOTATION_UNUSED,
      TEST_SETUP_ANNOTATION,
      TEST_VISIBLE_ANNOTATION,
      NAMESPACE_ACCESSIBLE_ANNOTATION,
      REST_RESOURCE_ANNOTATION,
      HTTP_DELETE_ANNOTATION,
      HTTP_GET_ANNOTATION,
      HTTP_PATCH_ANNOTATION,
      HTTP_POST_ANNOTATION,
      HTTP_PUT_ANNOTATION,
      JSON_ACCESS_ANNOTATION
    )

  private lazy val byName = (bitModifiers ++ annotations)
    // The parser text strips WS for '* sharing' so we do same
    .map(m => (m.name.replace(" ", "").toLowerCase, m))
    .toMap

  /** Recover from name, use safeApply */
  def apply(name: String, value: String): Array[Modifier] = {
    if (name.startsWith("@suppresswarnings")) {
      val trimmed = value.trim
      if (trimmed.length > 2 && trimmed.head == '\'' && trimmed.last == '\'') {
        val parts =
          trimmed.substring(1, trimmed.length - 1).trim.split(",").map(_.trim.toLowerCase())
        parts.flatMap {
          case "pmd"    => Some(SUPPRESS_WARNINGS_ANNOTATION_PMD)
          case "unused" => Some(SUPPRESS_WARNINGS_ANNOTATION_UNUSED)
          case _        => None
        }
      } else {
        Array.empty
      }
    } else {
      byName.get(name.toLowerCase()).toArray
    }
  }
}

// TODO: Validate arguments of the annotations
// TODO: Cross modifier/annotation checking

object ApexModifiers {
  val visibilityModifiers =
    Seq(GLOBAL_MODIFIER, PUBLIC_MODIFIER, PROTECTED_MODIFIER, PRIVATE_MODIFIER)
  private val sharingModifiers =
    Seq(WITH_SHARING_MODIFIER, WITHOUT_SHARING_MODIFIER, INHERITED_SHARING_MODIFIER)

  private val TypeAnnotations: Set[Modifier] =
    Set(
      DEPRECATED_ANNOTATION,
      TEST_VISIBLE_ANNOTATION,
      SUPPRESS_WARNINGS_ANNOTATION_PMD,
      SUPPRESS_WARNINGS_ANNOTATION_UNUSED,
      NAMESPACE_ACCESSIBLE_ANNOTATION
    )

  private val TypeModifiers: Set[Modifier] =
    Set(GLOBAL_MODIFIER, PUBLIC_MODIFIER, PRIVATE_MODIFIER)

  private val TypeModifiersAndAnnotations: Set[Modifier] =
    TypeAnnotations ++ TypeModifiers

  private val ClassAnnotations: Set[Modifier] =
    TypeAnnotations ++ Set(ISTEST_ANNOTATION, REST_RESOURCE_ANNOTATION, JSON_ACCESS_ANNOTATION)

  private val ClassModifiers: Set[Modifier] =
    TypeModifiers ++ Set(ABSTRACT_MODIFIER, VIRTUAL_MODIFIER) ++ sharingModifiers.toSet

  private val ClassModifiersAndAnnotations: Set[Modifier] =
    ClassAnnotations ++ ClassModifiers

  private val InterfaceModifiers: Set[Modifier] =
    Set(GLOBAL_MODIFIER, PUBLIC_MODIFIER, PRIVATE_MODIFIER, VIRTUAL_MODIFIER)

  private val InterfaceModifiersAndAnnotations: Set[Modifier] =
    TypeAnnotations ++ InterfaceModifiers

  private val legalConstructorModifiersAndAnnotations: Set[Modifier] =
    visibilityModifiers.toSet ++ TypeAnnotations

  private val legalParameterModifiersAndAnnotations: Set[Modifier] = Set(FINAL_MODIFIER)

  private val legalCatchModifiersAndAnnotations: Set[Modifier] =
    Set(FINAL_MODIFIER, STATIC_MODIFIER)

  private val legalLocalVarsModifiersAndAnnotations: Set[Modifier] =
    Set(FINAL_MODIFIER, TRANSIENT_MODIFIER)

  private val legalTriggerLocalVarsModifiersAndAnnotations: Set[Modifier] =
    Set(
      PUBLIC_MODIFIER,
      PRIVATE_MODIFIER,
      STATIC_MODIFIER,
      FINAL_MODIFIER,
      TRANSIENT_MODIFIER,
      TEST_VISIBLE_ANNOTATION
    )

  /* Convert parser contexts to Modifiers */
  def asModifiers(
    modifierContexts: ArraySeq[ModifierContext],
    allow: Set[Modifier],
    pluralName: String,
    logger: CodeParserLogger,
    idContext: ParserRuleContext
  ): ArraySeq[Modifier] = {

    val modifiers = toModifiers(modifierContexts, allow, pluralName, logger)
    if (modifiers.size == modifierContexts.size) {
      val duplicates = modifiers.duplicates(identity)
      if (duplicates.nonEmpty) {
        logger.logError(
          idContext,
          s"Modifier '${duplicates.head._1.toString}' is used more than once"
        )
      }
    }
    modifiers.distinct
  }

  private def toModifiers(
    modifierContexts: ArraySeq[ModifierContext],
    allow: Set[Modifier],
    pluralName: String,
    logger: CodeParserLogger
  ): ArraySeq[Modifier] = {
    modifierContexts.flatMap(modifierContext => {
      val annotation = CodeParser.toScala(modifierContext.annotation())
      val modifiers =
        annotation
          .map(
            a =>
              ModifierOps(
                "@" + CodeParser.getText(a.qualifiedName()).toLowerCase,
                CodeParser.toScala(a.elementValue()).map(ev => CodeParser.getText(ev)).getOrElse("")
              )
          )
          .getOrElse(ModifierOps(CodeParser.getText(modifierContext).toLowerCase, ""))

      val allowable = modifiers.partition(allow.contains)
      if (allowable._2.nonEmpty) {
        val modifierType = if (annotation.nonEmpty) "Annotation" else "Modifier"
        logger.logError(
          modifierContext,
          s"$modifierType '${CodeParser.getText(modifierContext)}' is not supported on $pluralName"
        )
      }
      allowable._1
    })
  }

  def deduplicateVisibility(
    modifiers: ArraySeq[Modifier],
    pluralName: String,
    logger: CodeParserLogger,
    idContext: ParserRuleContext
  ): ArraySeq[Modifier] = {
    if (modifiers.intersect(visibilityModifiers).size > 1) {
      if (logger.isEmpty)
        logger.logWarning(
          idContext,
          s"Only one visibility modifier from 'global', 'public' & 'private' should be used on $pluralName"
        )
      PUBLIC_MODIFIER +: modifiers.diff(visibilityModifiers)
    } else {
      modifiers
    }
  }

  private def deduplicateSharing(
    modifiers: ArraySeq[Modifier],
    pluralName: String,
    logger: CodeParserLogger,
    idContext: ParserRuleContext
  ): ArraySeq[Modifier] = {
    if (modifiers.intersect(sharingModifiers).size > 1) {
      if (logger.isEmpty)
        logger.logWarning(
          idContext,
          s"Only one sharing modifier from 'with sharing', 'without sharing' & 'inherited sharing' should be used on $pluralName"
        )
      WITHOUT_SHARING_MODIFIER +: modifiers.diff(visibilityModifiers)
    } else {
      modifiers
    }
  }

  private def deduplicate(
    modifiers: ArraySeq[Modifier],
    pluralName: String,
    logger: CodeParserLogger,
    idContext: ParserRuleContext
  ): ArraySeq[Modifier] = {
    deduplicateVisibility(
      deduplicateSharing(modifiers, pluralName, logger, idContext),
      pluralName,
      logger,
      idContext
    )
  }

  def classModifiers(
    parser: CodeParser,
    modifierContexts: ArraySeq[ModifierContext],
    outer: Boolean,
    idContext: IdContext
  ): ModifierResults = {

    val logger = new CodeParserLogger(parser)
    val mods = deduplicate(
      asModifiers(modifierContexts, ClassModifiersAndAnnotations, "classes", logger, idContext),
      "classes",
      logger,
      idContext
    )

    val results =
      if (logger.isEmpty) {
        if (outer && !mods.contains(ISTEST_ANNOTATION) && mods.contains(PRIVATE_MODIFIER)) {
          logger.logError(idContext, s"Private modifier is not allowed on outer classes")
          mods.filterNot(_ == PRIVATE_MODIFIER)
        } else if (
          outer && !mods.contains(ISTEST_ANNOTATION) && !(mods.contains(GLOBAL_MODIFIER) || mods
            .contains(PUBLIC_MODIFIER))
        ) {
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

    ModifierResults(results, logger.issues).intern
  }

  def interfaceModifiers(
    parser: CodeParser,
    modifierContexts: ArraySeq[ModifierContext],
    outer: Boolean,
    idContext: IdContext
  ): ModifierResults = {

    val logger = new CodeParserLogger(parser)
    val mods = deduplicateVisibility(
      asModifiers(
        modifierContexts,
        InterfaceModifiersAndAnnotations,
        "interfaces",
        logger,
        idContext
      ),
      "interfaces",
      logger,
      idContext
    )

    val results = {
      if (logger.isEmpty) {
        if (outer && mods.contains(PRIVATE_MODIFIER)) {
          logger.logError(idContext, s"Private modifier is not allowed on outer interfaces")
          mods.filterNot(_ == PRIVATE_MODIFIER)
        } else if (outer && !(mods.contains(GLOBAL_MODIFIER) || mods.contains(PUBLIC_MODIFIER))) {
          logger.logError(
            idContext,
            s"Outer interfaces must be declared either 'global' or 'public'"
          )
          PUBLIC_MODIFIER +: mods
        } else {
          mods
        }
      } else {
        mods
      }
    }
    ModifierResults(results, logger.issues).intern
  }

  def enumModifiers(
    parser: CodeParser,
    modifierContexts: ArraySeq[ModifierContext],
    outer: Boolean,
    idContext: IdContext
  ): ModifierResults = {

    val logger = new CodeParserLogger(parser)
    val mods = deduplicateVisibility(
      asModifiers(modifierContexts, TypeModifiersAndAnnotations, "enums", logger, idContext),
      "enums",
      logger,
      idContext
    )

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
    ModifierResults(results, logger.issues).intern
  }

  def propertyBlockModifiers(
    parser: CodeParser,
    modifierContexts: ArraySeq[ModifierContext],
    idContext: PropertyBlockContext
  ): ModifierResults = {

    val logger = new CodeParserLogger(parser)
    val mods = deduplicateVisibility(
      asModifiers(
        modifierContexts,
        visibilityModifiers.toSet,
        "property set/get",
        logger,
        idContext
      ),
      "property set/get",
      logger,
      idContext
    )
    ModifierResults(mods, logger.issues).intern
  }

  def constructorModifiers(
    parser: CodeParser,
    modifierContexts: ArraySeq[ModifierContext],
    context: ParserRuleContext
  ): ModifierResults = {

    val logger = new CodeParserLogger(parser)
    val mods = deduplicateVisibility(
      asModifiers(
        modifierContexts,
        legalConstructorModifiersAndAnnotations,
        "constructors",
        logger,
        context
      ),
      "constructors",
      logger,
      context
    )

    val results = {
      if (mods.intersect(visibilityModifiers).isEmpty) {
        PRIVATE_MODIFIER +: mods
      } else {
        mods
      }
    }
    ModifierResults(results, logger.issues).intern
  }

  def parameterModifiers(
    parser: CodeParser,
    modifierContexts: ArraySeq[ModifierContext],
    context: ParserRuleContext
  ): ModifierResults = {

    val logger = new CodeParserLogger(parser)
    val mods = deduplicateVisibility(
      asModifiers(
        modifierContexts,
        legalParameterModifiersAndAnnotations,
        "parameters",
        logger,
        context
      ),
      "parameters",
      logger,
      context
    )

    ModifierResults(mods, logger.issues).intern
  }

  def catchModifiers(
    parser: CodeParser,
    modifierContexts: ArraySeq[ModifierContext],
    context: ParserRuleContext
  ): ModifierResults = {

    val logger = new CodeParserLogger(parser)
    val mods = deduplicateVisibility(
      asModifiers(
        modifierContexts,
        legalCatchModifiersAndAnnotations,
        "catch variables",
        logger,
        context
      ),
      "catch variables",
      logger,
      context
    )

    ModifierResults(mods, logger.issues).intern
  }

  def localVariableModifiers(
    parser: CodeParser,
    modifierContexts: ArraySeq[ModifierContext],
    context: ParserRuleContext,
    isTrigger: Boolean
  ): ModifierResults = {

    val logger = new CodeParserLogger(parser)
    val mods = deduplicateVisibility(
      asModifiers(
        modifierContexts,
        if (isTrigger) legalTriggerLocalVarsModifiersAndAnnotations
        else legalLocalVarsModifiersAndAnnotations,
        "local variables",
        logger,
        context
      ),
      "local variables",
      logger,
      context
    )

    ModifierResults(mods, logger.issues).intern
  }
}
