/*
 Copyright (c) 2021 Kevin Jones, All rights reserved.
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
package com.nawforce.apexlink.plugins

import com.nawforce.apexlink.cst._
import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.org.Module
import com.nawforce.apexlink.plugins.UnusedPlugin._
import com.nawforce.apexlink.types.apex.{ApexFieldLike, ApexMethodLike, FullDeclaration}
import com.nawforce.apexlink.types.core.{DependentType, MethodDeclaration}
import com.nawforce.pkgforce.diagnostics.{Diagnostic, Issue, UNUSED_CATEGORY}
import com.nawforce.pkgforce.modifiers._
import com.nawforce.pkgforce.parsers.{FIELD_NATURE, PROPERTY_NATURE}

import scala.collection.immutable.ArraySeq

class UnusedPlugin(td: DependentType) extends Plugin(td) {

  override def onClassValidated(td: ClassDeclaration): Unit = {
    if (td.outerTypeName.isEmpty)
      td.unusedIssues.foreach(td.module.pkg.org.issues.add)
  }

  override def onEnumValidated(td: EnumDeclaration): Unit = {
    if (td.outerTypeName.isEmpty)
      td.unusedIssues.foreach(td.module.pkg.org.issues.add)
  }

  override def onInterfaceValidated(td: InterfaceDeclaration): Unit = {
    if (td.outerTypeName.isEmpty)
      td.unusedIssues.foreach(td.module.pkg.org.issues.add)
  }

  override def onBlockValidated(
    block: Block,
    isStatic: Boolean,
    context: BlockVerifyContext
  ): Unit = {
    context.declaredVars
      .filter(v => !context.referencedVars.contains(v._1) && v._2.definition.nonEmpty)
      .foreach(v => {
        val definition = v._2.definition.get
        context.log(
          new Issue(
            definition.location.path,
            Diagnostic(
              UNUSED_CATEGORY,
              definition.location.location,
              s"Unused local variable '${v._1}'"
            )
          )
        )
      })
  }

  private implicit class DeclarationOps(td: FullDeclaration) {

    def unusedIssues: ArraySeq[Issue] = {
      // Block at class level
      if (td.modifiers.exists(excludedClassModifiers.contains) || td.isPageController)
        return Issue.emptyArray

      // Hack: Unused calculation requires a methodMap as it establishes shadow relationships
      td.methodMap

      val issues =
        td.nestedTypes.flatMap(ad => ad.unusedIssues) ++
          td.unusedFields ++
          td.unusedMethods

      if (
        !td.hasNonTestHolders && issues.length == td.nestedTypes.length + td.localFields.length + td.localMethods.length
      ) {
        val nature = td match {
          case _: ClassDeclaration     => "class"
          case _: InterfaceDeclaration => "interface"
          case _: EnumDeclaration      => "enum"
          case _                       => "type"
        }

        val suffix =
          if (
            td.hasHolders || (issues.nonEmpty && issues.forall(
              _.diagnostic.message.contains(onlyTestCodeReferenceText)
            ))
          )
            s", $onlyTestCodeReferenceText"
          else
            ""
        ArraySeq(
          new Issue(
            td.location.path,
            Diagnostic(UNUSED_CATEGORY, td.idLocation, s"Unused $nature '${td.typeName}'$suffix")
          )
        )
      } else {
        issues
      }
    }

    def unusedFields: ArraySeq[Issue] = {
      td.localFields
        .filterNot(_.isUsed(td.inTest))
        .map(field => {
          val nature = field.nature match {
            case FIELD_NATURE    => "field"
            case PROPERTY_NATURE => "property"
            case _               => "field or property"
          }
          val suffix = if (field.hasHolders) s", $onlyTestCodeReferenceText" else ""
          new Issue(
            field.location.path,
            Diagnostic(UNUSED_CATEGORY, field.idLocation, s"Unused $nature '${field.name}'$suffix")
          )
        })
    }

    def unusedMethods: ArraySeq[Issue] = {
      td.localMethods
        .flatMap {
          case am: ApexMethodLike if !am.isUsed(td.module, td.inTest) => Some(am)
          case _                                                      => None
        }
        .map(method => {
          val suffix = if (method.hasHolders) s", $onlyTestCodeReferenceText" else ""
          new Issue(
            method.location.path,
            Diagnostic(
              UNUSED_CATEGORY,
              method.idLocation,
              s"Unused method '${method.signature}'$suffix"
            )
          )
        })
    }

    def isPageController: Boolean = {
      td.getTypeDependencyHolders.toIterable.exists(
        tid => tid.typeName == TypeNames.Page || tid.typeName == TypeNames.Component
      )
    }
  }

  private implicit class FieldOps(field: ApexFieldLike) {
    def isUsed(inTest: Boolean): Boolean = {
      if (inTest)
        field.hasHolders || field.modifiers.exists(excludedTestFieldModifiers)
      else
        field.hasNonTestHolders || field.modifiers.exists(excludedFieldModifiers)
    }
  }

  private implicit class MethodOps(method: ApexMethodLike) {

    /** Is the method in use, NOTE: requires a MethodMap is constructed for shadow support first! */
    def isUsed(module: Module, inTest: Boolean): Boolean = {
      method.isSynthetic ||
      (if (inTest)
         method.hasHolders || method.modifiers.exists(excludedTestMethodModifiers.contains)
       else
         method.hasNonTestHolders || method.modifiers.exists(excludedMethodModifiers.contains)) ||
      method.shadows.exists({
        case am: ApexMethodLike   => am.isUsed(module, inTest)
        case _: MethodDeclaration => true
        case _                    => false
      }) ||
      method.parameters.exists(parameter => module.isGhostedType(parameter.typeName))
    }
  }
}

object UnusedPlugin {
  val onlyTestCodeReferenceText =
    "only referenced by test code, remove or make private @TestVisible"
  val excludedClassModifiers: Set[Modifier] =
    Set(
      TEST_VISIBLE_ANNOTATION,
      GLOBAL_MODIFIER,
      SUPPRESS_WARNINGS_ANNOTATION_PMD,
      SUPPRESS_WARNINGS_ANNOTATION_UNUSED
    )
  val excludedMethodModifiers: Set[Modifier] =
    Set(
      TEST_VISIBLE_ANNOTATION,
      GLOBAL_MODIFIER,
      AURA_ENABLED_ANNOTATION,
      SUPPRESS_WARNINGS_ANNOTATION_PMD,
      SUPPRESS_WARNINGS_ANNOTATION_UNUSED
    )
  val excludedTestMethodModifiers: Set[Modifier] =
    Set(
      ISTEST_ANNOTATION,
      TEST_SETUP_ANNOTATION,
      TEST_METHOD_MODIFIER,
      SUPPRESS_WARNINGS_ANNOTATION_PMD,
      SUPPRESS_WARNINGS_ANNOTATION_UNUSED
    )
  val excludedFieldModifiers: Set[Modifier] =
    Set(
      TEST_VISIBLE_ANNOTATION,
      GLOBAL_MODIFIER,
      AURA_ENABLED_ANNOTATION,
      SUPPRESS_WARNINGS_ANNOTATION_PMD,
      SUPPRESS_WARNINGS_ANNOTATION_UNUSED
    )
  val excludedTestFieldModifiers: Set[Modifier] =
    Set(SUPPRESS_WARNINGS_ANNOTATION_PMD, SUPPRESS_WARNINGS_ANNOTATION_UNUSED)
}
