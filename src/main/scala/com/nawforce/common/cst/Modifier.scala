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
package com.nawforce.common.cst

import com.nawforce.common.diagnostics.{CodeParserLogger, Issue, IssueLogger}
import com.nawforce.runtime.parsers.ApexParser.{AnnotationContext, IdContext, ModifierContext, PropertyBlockContext}
import com.nawforce.runtime.parsers.CodeParser
import com.nawforce.runtime.parsers.CodeParser.ParserRuleContext

sealed abstract class Modifier(final val name: String, val order: Integer=0) {
  override def toString: String = name
}

case object WEBSERVICE_MODIFIER extends Modifier("webservice", order = 4)
case object GLOBAL_MODIFIER extends Modifier("global", order = 3)
case object PUBLIC_MODIFIER extends Modifier("public", order = 2)
case object PROTECTED_MODIFIER extends Modifier("protected", order = 1)
case object PRIVATE_MODIFIER extends Modifier("private", order = 0)
case object TEST_CLASS_MODIFIER extends Modifier("@isTest")
case object TEST_METHOD_MODIFIER extends Modifier("testmethod")

case object STATIC_MODIFIER extends Modifier("static")
case object ABSTRACT_MODIFIER extends Modifier("abstract")
case object FINAL_MODIFIER extends Modifier("final")
case object OVERRIDE_MODIFIER extends Modifier("override")
case object VIRTUAL_MODIFIER extends Modifier("virtual")
case object TRANSIENT_MODIFIER extends Modifier("transient")

case object WITH_SHARING_MODIFIER extends Modifier("with sharing")
case object WITHOUT_SHARING_MODIFIER extends Modifier("without sharing")
case object INHERITED_SHARING_MODIFIER extends Modifier("inherited sharing")

case object AURA_ENABLED_ANNOTATION extends Modifier("@AuraEnabled")
case object DEPRECATED_ANNOTATION extends Modifier("@Deprecated")
case object FUTURE_ANNOTATION extends Modifier("@Future")
case object INVOCABLE_METHOD_ANNOTATION extends Modifier("@InvocableMethod")
case object INVOCABLE_VARIABLE_ANNOTATION extends Modifier("@InvocableField")
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

object Modifier {
  def apply(value: String): Modifier = {
    // TODO: Why can't this be @switch
    value match {
      case WEBSERVICE_MODIFIER.name => WEBSERVICE_MODIFIER
      case GLOBAL_MODIFIER.name => GLOBAL_MODIFIER
      case PUBLIC_MODIFIER.name => PUBLIC_MODIFIER
      case PROTECTED_MODIFIER.name => PROTECTED_MODIFIER
      case PRIVATE_MODIFIER.name => PRIVATE_MODIFIER
      case TEST_CLASS_MODIFIER.name => TEST_CLASS_MODIFIER
      case TEST_METHOD_MODIFIER.name => TEST_METHOD_MODIFIER

      case STATIC_MODIFIER.name => STATIC_MODIFIER
      case ABSTRACT_MODIFIER.name => ABSTRACT_MODIFIER
      case FINAL_MODIFIER.name => FINAL_MODIFIER
      case OVERRIDE_MODIFIER.name => OVERRIDE_MODIFIER
      case VIRTUAL_MODIFIER.name => VIRTUAL_MODIFIER
      case TRANSIENT_MODIFIER.name => TRANSIENT_MODIFIER

      case WITH_SHARING_MODIFIER.name => WITH_SHARING_MODIFIER
      case WITHOUT_SHARING_MODIFIER.name => WITHOUT_SHARING_MODIFIER
      case INHERITED_SHARING_MODIFIER.name => INHERITED_SHARING_MODIFIER

      case AURA_ENABLED_ANNOTATION.name => AURA_ENABLED_ANNOTATION
      case DEPRECATED_ANNOTATION.name => DEPRECATED_ANNOTATION
      case FUTURE_ANNOTATION.name => FUTURE_ANNOTATION
      case INVOCABLE_METHOD_ANNOTATION.name => INVOCABLE_METHOD_ANNOTATION
      case INVOCABLE_VARIABLE_ANNOTATION.name => INVOCABLE_VARIABLE_ANNOTATION
      case ISTEST_ANNOTATION.name => ISTEST_ANNOTATION
      case READ_ONLY_ANNOTATION.name => READ_ONLY_ANNOTATION
      case REMOTE_ACTION_ANNOTATION.name => REMOTE_ACTION_ANNOTATION
      case SUPPRESS_WARNINGS_ANNOTATION.name => SUPPRESS_WARNINGS_ANNOTATION
      case TEST_SETUP_ANNOTATION.name => TEST_SETUP_ANNOTATION
      case TEST_VISIBLE_ANNOTATION.name => TEST_VISIBLE_ANNOTATION
      case NAMESPACE_ACCESSIBLE_ANNOTATION.name => NAMESPACE_ACCESSIBLE_ANNOTATION

      case REST_RESOURCE_ANNOTATION.name => REST_RESOURCE_ANNOTATION
      case HTTP_DELETE_ANNOTATION.name => HTTP_DELETE_ANNOTATION
      case HTTP_GET_ANNOTATION.name => HTTP_GET_ANNOTATION
      case HTTP_PATCH_ANNOTATION.name => HTTP_PATCH_ANNOTATION
      case HTTP_POST_ANNOTATION.name => HTTP_POST_ANNOTATION
      case HTTP_PUT_ANNOTATION.name => HTTP_PUT_ANNOTATION
    }
  }
}

// Result wrapper
case class ModifierResults(modifiers: Seq[Modifier], issues: Seq[Issue])

// TODO: Validate arguments of the annotations
// TODO: Cross modifier/annotation checking
// TODO: Protected not supported on class, interface & enum

object ApexModifiers {
  val visibilityModifiers: Seq[Modifier] = Seq(GLOBAL_MODIFIER, PUBLIC_MODIFIER, PROTECTED_MODIFIER, PRIVATE_MODIFIER)
  val sharingModifiers: Seq[Modifier] = Seq(WITH_SHARING_MODIFIER, WITHOUT_SHARING_MODIFIER, INHERITED_SHARING_MODIFIER)

  def classModifiers(parser: CodeParser, modifierContexts: Seq[ModifierContext], outer: Boolean, idContext: IdContext)
  : ModifierResults = {

    val logger = new CodeParserLogger(parser)
    val mods: Seq[Modifier] = modifierContexts.flatMap(modifierContext => {
      val annotation = CodeParser.toScala(modifierContext.annotation())
      if (annotation.nonEmpty)
        annotation.flatMap(a => classAnnotation(logger, a))
      else {
        CodeParser.getText(modifierContext).toLowerCase match {
          case "global" => Some(GLOBAL_MODIFIER)
          case "public" => Some(PUBLIC_MODIFIER)
          case "private" => Some(PRIVATE_MODIFIER)
          case "withsharing" => Some(WITH_SHARING_MODIFIER)
          case "withoutsharing" => Some(WITHOUT_SHARING_MODIFIER)
          case "inheritedsharing" => Some(INHERITED_SHARING_MODIFIER)
          case "abstract" => Some(ABSTRACT_MODIFIER)
          case "virtual" => Some(VIRTUAL_MODIFIER)
          case _ =>
            logger.logError(modifierContext,
              s"Modifier '${CodeParser.getText(modifierContext)}' is not supported on classes")
            None
        }
      }
    })

    val results =
      if (mods.size == modifierContexts.size) {
        val duplicates = mods.groupBy(identity).collect { case (_, Seq(_, y, _*)) => y }
        if (duplicates.nonEmpty) {
          logger.logError(idContext, s"Modifier '${duplicates.head.toString}' is used more than once")
          mods.toSet.toSeq
        } else if (outer && !mods.contains(ISTEST_ANNOTATION) && mods.contains(PRIVATE_MODIFIER)) {
          logger.logWarning(idContext, s"Private modifier is not allowed on outer classes")
          mods.filterNot(_ == PRIVATE_MODIFIER)
        } else if (outer && !mods.contains(ISTEST_ANNOTATION) && !(mods.contains(GLOBAL_MODIFIER) || mods.contains(PUBLIC_MODIFIER))) {
          logger.logError(idContext, s"Outer classes must be declared either 'global' or 'public'")
          PUBLIC_MODIFIER +: mods
        } else if (mods.intersect(visibilityModifiers).size > 1) {
          logger.logWarning(idContext,
            s"Only one visibility modifier from 'global', 'public' & 'private' should be used on classes")
          PUBLIC_MODIFIER +: mods.diff(visibilityModifiers)
        } else if (mods.intersect(sharingModifiers).size > 1) {
          logger.logError(idContext,
            s"Only one sharing modifier from 'with sharing', 'without sharing' & 'inherited sharing' may be used on classes")
          mods.diff(sharingModifiers)
        } else {
          mods
        }
      } else {
        mods
      }

    ModifierResults(results, logger.issues)
  }

  def interfaceModifiers(parser: CodeParser, modifierContexts: Seq[ModifierContext], outer: Boolean, idContext: IdContext)
  : ModifierResults = {

    val logger = new CodeParserLogger(parser)
    val mods: Seq[Modifier] = modifierContexts.flatMap(modifierContext => {
      val annotation = CodeParser.toScala(modifierContext.annotation())
      if (annotation.nonEmpty)
        annotation.flatMap(a => typeAnnotation(logger, a))
      else {
        CodeParser.getText(modifierContext).toLowerCase match {
          case "global" => Some(GLOBAL_MODIFIER)
          case "public" => Some(PUBLIC_MODIFIER)
          case "private" => Some(PRIVATE_MODIFIER)
          case "virtual" => Some(VIRTUAL_MODIFIER)
          case _ =>
            logger.logError(modifierContext,
              s"Modifier '${CodeParser.getText(modifierContext)}' is not supported on interfaces")
            None
        }
      }
    })

    val results = {
      if (mods.size == modifierContexts.size) {
        val duplicates = mods.groupBy(identity).collect { case (_, Seq(_, y, _*)) => y }
        if (duplicates.nonEmpty) {
          logger.logError(idContext, s"Modifier '${duplicates.head.toString}' is used more than once")
          mods.toSet.toSeq
        } else if (outer && mods.contains(PRIVATE_MODIFIER)) {
          logger.logError(idContext,
            s"Private modifier is not allowed on outer interfaces")
          mods.filterNot(_ == PRIVATE_MODIFIER)
        } else if (outer && !(mods.contains(GLOBAL_MODIFIER) || mods.contains(PUBLIC_MODIFIER))) {
          logger.logError(idContext, s"Outer interfaces must be declared either 'global' or 'public'")
          PUBLIC_MODIFIER +: mods
        } else if (mods.intersect(visibilityModifiers).size > 1) {
          logger.logWarning(idContext,
            s"Only one visibility modifier from 'global', 'public' & 'private' should be used on interfaces")
          PUBLIC_MODIFIER +: mods.diff(visibilityModifiers)
        } else {
          mods
        }
      } else {
        mods
      }
    }
    ModifierResults(results, logger.issues)
  }

  def enumModifiers(parser: CodeParser, modifierContexts: Seq[ModifierContext], outer: Boolean, idContext: IdContext)
  : ModifierResults = {

    val logger = new CodeParserLogger(parser)
    val mods: Seq[Modifier] = modifierContexts.flatMap(modifierContext => {
      val annotation = CodeParser.toScala(modifierContext.annotation())
      if (annotation.nonEmpty)
        annotation.flatMap(a => typeAnnotation(logger, a))
      else {
        CodeParser.getText(modifierContext).toLowerCase match {
          case "global" => Some(GLOBAL_MODIFIER)
          case "public" => Some(PUBLIC_MODIFIER)
          case "private" => Some(PRIVATE_MODIFIER)
          case _ =>
            logger.logError(modifierContext,
              s"Modifier '${CodeParser.getText(modifierContext)}' is not supported on enums")
            None
        }
      }
    })

    val results = {
      if (mods.size == modifierContexts.size) {
        val duplicates = mods.groupBy(identity).collect { case (_, Seq(_, y, _*)) => y }
        if (duplicates.nonEmpty) {
          logger.logError(idContext, s"Modifier '${duplicates.head.toString}' is used more than once")
          mods.toSet.toSeq
        } else if (outer && mods.contains(PRIVATE_MODIFIER)) {
          logger.logError(idContext,
            s"Private modifier is not allowed on outer enums")
          mods.filterNot(_ == PRIVATE_MODIFIER)
        } else if (outer && !(mods.contains(GLOBAL_MODIFIER) || mods.contains(PUBLIC_MODIFIER))) {
          logger.logError(idContext, s"Outer enums must be declared either 'global' or 'public'")
          PUBLIC_MODIFIER +: mods
        } else if (mods.intersect(visibilityModifiers).size > 1) {
          logger.logWarning(idContext,
            s"Only one visibility modifier from 'global', 'public' & 'private' should be used on enums")
          PUBLIC_MODIFIER +: mods.diff(visibilityModifiers)
        } else {
          mods
        }
      } else {
        mods
      }
    }
    ModifierResults(results, logger.issues)
  }

  def fieldModifiers(parser: CodeParser, modifierContexts: Seq[ModifierContext], idContext: IdContext)
  : ModifierResults = {

    val logger = new CodeParserLogger(parser)
    val mods: Seq[Modifier] = modifierContexts.flatMap(modifierContext => {
      val annotation = CodeParser.toScala(modifierContext.annotation())
      if (annotation.nonEmpty)
        annotation.flatMap(a => fieldAnnotation(logger, a))
      else {
        CodeParser.getText(modifierContext).toLowerCase match {
          case "global" => Some(GLOBAL_MODIFIER)
          case "public" => Some(PUBLIC_MODIFIER)
          case "protected" => Some(PROTECTED_MODIFIER)
          case "private" => Some(PRIVATE_MODIFIER)
          case "final" => Some(FINAL_MODIFIER)
          case "static" => Some(STATIC_MODIFIER)
          case "transient" => Some(TRANSIENT_MODIFIER)
          case "webservice" => Some(WEBSERVICE_MODIFIER)
          case _ =>
            logger.logError(modifierContext,
              s"Modifier '${CodeParser.getText(modifierContext)}' is not supported on fields")
            None
        }
      }
    })

    val results = {
      val duplicates = mods.groupBy(identity).collect { case (_, Seq(_, y, _*)) => y }
      if (duplicates.nonEmpty) {
        logger.logError(idContext, s"Modifier '${duplicates.head.toString}' is used more than once")
        mods.toSet.toSeq
      } else if (mods.intersect(visibilityModifiers).size > 1) {
        logger.logWarning(idContext,
          s"Only one visibility modifier from 'global', 'public', 'protected' & 'private' should be used on fields")
        PUBLIC_MODIFIER +: mods.diff(visibilityModifiers)
      } else if (mods.intersect(visibilityModifiers).isEmpty && mods.contains(WEBSERVICE_MODIFIER)) {
        GLOBAL_MODIFIER +: mods
      } else if (!mods.intersect(visibilityModifiers).contains(GLOBAL_MODIFIER) && mods.contains(WEBSERVICE_MODIFIER)) {
        logger.logError(idContext, s"webservice methods must be global")
        GLOBAL_MODIFIER +: mods.diff(visibilityModifiers)
      } else if (mods.intersect(visibilityModifiers).isEmpty) {
        PRIVATE_MODIFIER +: mods
      } else {
        mods
      }
    }
    ModifierResults(results, logger.issues)
  }

  def propertyBlockModifiers(parser: CodeParser, modifierContexts: Seq[ModifierContext], idContext: PropertyBlockContext)
  : ModifierResults = {

    val logger = new CodeParserLogger(parser)
    val mods: Seq[Modifier] = modifierContexts.flatMap(modifierContext =>
      CodeParser.getText(modifierContext).toLowerCase match {
        case "global" => Some(GLOBAL_MODIFIER)
        case "public" => Some(PUBLIC_MODIFIER)
        case "protected" => Some(PROTECTED_MODIFIER)
        case "private" => Some(PRIVATE_MODIFIER)
        case _ =>
          logger.logError(modifierContext,
            s"Modifier '${CodeParser.getText(modifierContext)}' is not supported on property set/get")
          None
      }
    )

    val results = {
      val duplicates = mods.groupBy(identity).collect { case (_, Seq(_, y, _*)) => y }
      if (duplicates.nonEmpty) {
        logger.logError(idContext, s"Modifier '${duplicates.head.toString}' is used more than once")
        mods.toSet.toSeq
      } else if (mods.intersect(visibilityModifiers).size > 1) {
        logger.logWarning(idContext,
          s"Only one visibility modifier from 'global, 'public', 'protected' & 'private' should be used on property set/get")
        mods.diff(visibilityModifiers)
      } else {
        mods
      }
    }
    ModifierResults(results, logger.issues)
  }

  def constructorModifiers(parser: CodeParser, modifierContexts: Seq[ModifierContext], parserContext: ParserRuleContext)
  : ModifierResults = {

    val logger = new CodeParserLogger(parser)
    val mods: Seq[Modifier] = modifierContexts.flatMap(modifierContext => {
      val annotation = CodeParser.toScala(modifierContext.annotation())
      if (annotation.nonEmpty)
        annotation.flatMap(a => constructorAnnotation(logger, a))
      else {
        CodeParser.getText(modifierContext).toLowerCase match {
          case "global" => Some(GLOBAL_MODIFIER)
          case "public" => Some(PUBLIC_MODIFIER)
          case "protected" => Some(PROTECTED_MODIFIER)
          case "private" => Some(PRIVATE_MODIFIER)
          case _ =>
            logger.logError(modifierContext,
              s"Modifier '${CodeParser.getText(modifierContext)}' is not supported on constructors")
            None
        }
      }
    })

    val results = {
      val duplicates = mods.groupBy(identity).collect { case (_, Seq(_, y, _*)) => y }
      if (duplicates.nonEmpty) {
        logger.logError(parserContext, s"Modifier '${duplicates.head.toString}' is used more than once")
        mods.toSet.toSeq
      } else if (mods.intersect(visibilityModifiers).size > 1) {
        logger.logWarning(parserContext,
          s"Only one visibility modifier from 'global', 'public', 'protected' & 'private' should be used on methods")
        PUBLIC_MODIFIER +: mods.diff(visibilityModifiers)
      } else if (mods.intersect(visibilityModifiers).isEmpty) {
        PRIVATE_MODIFIER +: mods
      } else {
        mods
      }
    }
    ModifierResults(results, logger.issues)
  }

  def methodModifiers(parser: CodeParser, modifierContexts: Seq[ModifierContext], idContext: ParserRuleContext)
  : ModifierResults = {

    val logger = new CodeParserLogger(parser)
    val mods: Seq[Modifier] = modifierContexts.flatMap(modifierContext => {
      val annotation = CodeParser.toScala(modifierContext.annotation())
      if (annotation.nonEmpty)
        annotation.flatMap(a => methodAnnotation(logger, a))
      else {
        CodeParser.getText(modifierContext).toLowerCase match {
          case "abstract" => Some(ABSTRACT_MODIFIER)
          case "global" => Some(GLOBAL_MODIFIER)
          case "override" => Some(OVERRIDE_MODIFIER)
          case "public" => Some(PUBLIC_MODIFIER)
          case "protected" => Some(PROTECTED_MODIFIER)
          case "private" => Some(PRIVATE_MODIFIER)
          case "static" => Some(STATIC_MODIFIER)
          case "testmethod" => Some(TEST_METHOD_MODIFIER)
          case "webservice" => Some(WEBSERVICE_MODIFIER)
          case "virtual" => Some(VIRTUAL_MODIFIER)
          case _ =>
            logger.logError(modifierContext,
              s"Modifier '${CodeParser.getText(modifierContext)}' is not supported on methods")
            None
        }
      }
    })

    // TODO: webservice must be on outer static method

    val results = {
      val duplicates = mods.groupBy(identity).collect { case (_, Seq(_, y, _*)) => y }
      if (duplicates.nonEmpty) {
        logger.logError(idContext, s"Modifier '${duplicates.head.toString}' is used more than once")
        mods.toSet.toSeq
      } else if (mods.intersect(visibilityModifiers).size > 1) {
        logger.logWarning(idContext,
          s"Only one visibility modifier from 'global', 'public', 'protected' & 'private' should be used on methods")
        mods.diff(visibilityModifiers)
      } else if (mods.intersect(visibilityModifiers).isEmpty && mods.contains(WEBSERVICE_MODIFIER)) {
        GLOBAL_MODIFIER +: mods
      } else if (!mods.intersect(visibilityModifiers).contains(GLOBAL_MODIFIER) && mods.contains(WEBSERVICE_MODIFIER)) {
        logger.logError(idContext,
          s"webservice methods must be global")
        GLOBAL_MODIFIER +: mods.diff(visibilityModifiers)
      } else {
        mods
      }
    }
    ModifierResults(results, logger.issues)
  }

  def parameterModifiers(parser: CodeParser, modifierContexts: Seq[ModifierContext], idContext: ParserRuleContext)
  : ModifierResults = {

    val logger = new CodeParserLogger(parser)
    val mods: Seq[Modifier] = modifierContexts.flatMap(modifierContext => {
      val annotation = CodeParser.toScala(modifierContext.annotation())
      if (annotation.nonEmpty)
        annotation.flatMap(a => parameterAnnotation(logger, a))
      else {
        CodeParser.getText(modifierContext).toLowerCase match {
          case "final" => Some(FINAL_MODIFIER)
          case _ =>
            logger.logError(modifierContext,
              s"Modifier '${CodeParser.getText(modifierContext)}' is not supported on parameters")
            None
        }
      }
    })

    val results = {
      val duplicates = mods.groupBy(identity).collect { case (_, Seq(_, y, _*)) => y }
      if (duplicates.nonEmpty) {
        logger.logError(idContext, s"Modifier '${duplicates.head.toString}' is used more than once")
        mods.toSet.toSeq
      } else {
        mods
      }
    }
    ModifierResults(results, logger.issues)
  }

  def catchModifiers(parser: CodeParser, modifierContexts: Seq[ModifierContext], idContext: ParserRuleContext)
  : ModifierResults = {

    val logger = new CodeParserLogger(parser)
    val mods: Seq[Modifier] = modifierContexts.flatMap(modifierContext => {
      val annotation = CodeParser.toScala(modifierContext.annotation())
      if (annotation.nonEmpty)
        annotation.flatMap(a => catchAnnotation(logger, a))
      else {
        CodeParser.getText(modifierContext).toLowerCase match {
          case "final" => Some(FINAL_MODIFIER)
          case "static" => Some(STATIC_MODIFIER)
          case _ =>
            logger.logError(modifierContext,
              s"Modifier '${CodeParser.getText(modifierContext)}' is not supported on parameters")
            None
        }
      }
    })

    val results = {
      val duplicates = mods.groupBy(identity).collect { case (_, Seq(_, y, _*)) => y }
      if (duplicates.nonEmpty) {
        logger.logError(idContext, s"Modifier '${duplicates.head.toString}' is used more than once")
        mods.toSet.toSeq
      } else {
        mods
      }
    }
    ModifierResults(results, logger.issues)
  }

  def localVariableModifiers(parser: CodeParser, modifierContexts: Seq[ModifierContext], idContext: ParserRuleContext)
  : ModifierResults = {

    val logger = new CodeParserLogger(parser)
    val mods: Seq[Modifier] = modifierContexts.flatMap(modifierContext => {
      val annotation = CodeParser.toScala(modifierContext.annotation())
      if (annotation.nonEmpty)
        annotation.flatMap(a => localVariableAnnotation(logger, a))
      else {
        CodeParser.getText(modifierContext).toLowerCase match {
          case "final" => Some(FINAL_MODIFIER)
          case _ =>
            logger.logError(modifierContext,
              s"Modifier '${CodeParser.getText(modifierContext)}' is not supported on local variables")
            None
        }
      }
    })

    val results = {
      val duplicates = mods.groupBy(identity).collect { case (_, Seq(_, y, _*)) => y }
      if (duplicates.nonEmpty) {
        logger.logError(idContext, s"Modifier '${duplicates.head.toString}' is used more than once")
        mods.toSet.toSeq
      } else {
        mods
      }
    }
    ModifierResults(results, logger.issues)
  }

  private def classAnnotation(logger: IssueLogger, context: AnnotationContext): Option[Modifier] = {
    CodeParser.getText(context.qualifiedName()).toLowerCase match {
      case "deprecated" => Some(DEPRECATED_ANNOTATION)
      case "istest" => Some(ISTEST_ANNOTATION)
      case "testvisible" => Some(TEST_VISIBLE_ANNOTATION)
      case "suppresswarnings" => Some(SUPPRESS_WARNINGS_ANNOTATION)
      case "restresource" => Some(REST_RESOURCE_ANNOTATION)
      case "namespaceaccessible" => Some(NAMESPACE_ACCESSIBLE_ANNOTATION)
      case _ =>
        logger.log(Issue.unexpectedAnnotationOnClass(logger.location(context), context.qualifiedName()))
        None
    }
  }

  private def typeAnnotation(logger: IssueLogger, context: AnnotationContext): Option[Modifier] = {
    CodeParser.getText(context.qualifiedName()).toLowerCase match {
      case "deprecated" => Some(DEPRECATED_ANNOTATION)
      case "testvisible" => Some(TEST_VISIBLE_ANNOTATION)
      case "suppresswarnings" => Some(SUPPRESS_WARNINGS_ANNOTATION)
      case "namespaceaccessible" => Some(NAMESPACE_ACCESSIBLE_ANNOTATION)
      case _ =>
        logger.logError(context,
          s"Unexpected annotation '${CodeParser.getText(context.qualifiedName())}' on interface declaration")
        None
    }
  }

  private def fieldAnnotation(logger: IssueLogger, context: AnnotationContext): Option[Modifier] = {
    // TODO: Validate arguments of the annotations
    CodeParser.getText(context.qualifiedName()).toLowerCase match {
      case "auraenabled" => Some(AURA_ENABLED_ANNOTATION)
      case "deprecated" => Some(DEPRECATED_ANNOTATION)
      case "invocablevariable" => Some(INVOCABLE_VARIABLE_ANNOTATION)
      case "testvisible" => Some(TEST_VISIBLE_ANNOTATION)
      case "suppresswarnings" => Some(SUPPRESS_WARNINGS_ANNOTATION)
      case _ =>
        logger.logError(context,
          s"Unexpected annotation '${CodeParser.getText(context.qualifiedName())}' on field/property declaration")
        None
    }
  }

  private def constructorAnnotation(logger: IssueLogger,  context: AnnotationContext): Option[Modifier] = {
    // TODO: Validate arguments of the annotations
    CodeParser.getText(context.qualifiedName()).toLowerCase match {
      case "deprecated" => Some(DEPRECATED_ANNOTATION)
      case "testvisible" => Some(TEST_VISIBLE_ANNOTATION)
      case "namespaceaccessible" => Some(NAMESPACE_ACCESSIBLE_ANNOTATION)
      case "suppresswarnings" => Some(SUPPRESS_WARNINGS_ANNOTATION)
      case _ =>
        logger.logError(context,
          s"Unexpected annotation '${CodeParser.getText(context.qualifiedName())}' on method declaration")
        None
    }
  }

  private def methodAnnotation(logger: IssueLogger,  context: AnnotationContext): Option[Modifier] = {
    // TODO: Validate arguments of the annotations
    CodeParser.getText(context.qualifiedName()).toLowerCase match {
      case "auraenabled" => Some(AURA_ENABLED_ANNOTATION)
      case "deprecated" => Some(DEPRECATED_ANNOTATION)
      case "future" => Some(FUTURE_ANNOTATION)
      case "invocablemethod" => Some(INVOCABLE_VARIABLE_ANNOTATION)
      case "istest" => Some(ISTEST_ANNOTATION)
      case "testvisible" => Some(TEST_VISIBLE_ANNOTATION)
      case "namespaceaccessible" => Some(NAMESPACE_ACCESSIBLE_ANNOTATION)
      case "readonly" => Some(READ_ONLY_ANNOTATION)
      case "suppresswarnings" => Some(SUPPRESS_WARNINGS_ANNOTATION)
      case "testsetup" => Some(TEST_SETUP_ANNOTATION)
      case "httpdelete" => Some(HTTP_DELETE_ANNOTATION)
      case "httpget" => Some(HTTP_GET_ANNOTATION)
      case "httppatch" => Some(HTTP_PATCH_ANNOTATION)
      case "httppost" => Some(HTTP_POST_ANNOTATION)
      case "httpput" => Some(HTTP_PUT_ANNOTATION)
      case "remoteaction" => Some(REMOTE_ACTION_ANNOTATION)
      case _ =>
        logger.logError(context,
          s"Unexpected annotation '${CodeParser.getText(context.qualifiedName())}' on method declaration")
        None
    }
  }

  private def parameterAnnotation(logger: IssueLogger, context: AnnotationContext): Option[Modifier] = {
    logger.logError(context,
      s"Unexpected annotation '${CodeParser.getText(context.qualifiedName())}' on parameter")
    None
  }

  private def catchAnnotation(logger: IssueLogger, context: AnnotationContext): Option[Modifier] = {
    logger.logError(context,
      s"Unexpected annotation '${CodeParser.getText(context.qualifiedName())}' on catch clause")
    None
  }

  private def localVariableAnnotation(logger: IssueLogger, context: AnnotationContext): Option[Modifier] = {
    logger.logError(context,
      s"Unexpected annotation '${CodeParser.getText(context.qualifiedName())}' on catch clause")
    None
  }
}


