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

import com.nawforce.apexlink.diagnostics.IssueOps
import com.nawforce.apexlink.finding.TypeResolver
import com.nawforce.apexlink.finding.TypeResolver.TypeResponse
import com.nawforce.apexlink.memory.SkinnySet
import com.nawforce.apexlink.org.{Module, OrgImpl}
import com.nawforce.apexlink.plugins.Plugin
import com.nawforce.apexlink.types.apex._
import com.nawforce.apexlink.types.core.{Dependent, TypeDeclaration}
import com.nawforce.apexlink.types.other._
import com.nawforce.apexlink.types.schema.SObjectDeclaration
import com.nawforce.pkgforce.diagnostics._
import com.nawforce.pkgforce.modifiers.SUPPRESS_WARNINGS_ANNOTATION_PMD
import com.nawforce.pkgforce.names.{EncodedName, Name, TypeName}
import com.nawforce.pkgforce.path.{Location, PathLocation}

import scala.collection.mutable

trait VerifyContext {

  def parent(): Option[VerifyContext]

  /** Module for current outer type */
  def module: Module

  /** Get type declaration of 'this', this may be a trigger declaration */
  def thisType: TypeDeclaration

  /** Get type declaration of 'super' */
  def superType: Option[TypeDeclaration]

  /** Declare a dependency on dependent */
  def addDependency(dependent: Dependent): Unit

  /** Locate a type, typeName may be relative so searching must be performed wrt a typeDeclaration */
  def getTypeFor(typeName: TypeName, from: TypeDeclaration): TypeResponse

  /** Helper to locate a relative or absolute type and add as dependency if found */
  def getTypeAndAddDependency(typeName: TypeName, from: TypeDeclaration): TypeResponse

  /** Get Plugin instance for this type */
  def typePlugin: Plugin

  /** Test if result saving is enabled */
  def isSaving: Boolean

  /** Save some result of a validation for later analysis */
  def saveResult(cst: CST, altLocation: Location)(op: => ExprContext): ExprContext

  /** Save some result of a validation for later analysis with local var map */
  def saveResult(
    cst: CST,
    altLocation: Location,
    vars: Option[mutable.Map[Name, VarTypeAndDefinition]]
  )(op: => ExprContext): ExprContext

  /** Test if issues are currently being suppressed */
  def suppressIssues: Boolean = parent().exists(_.suppressIssues) || disableIssueDepth != 0

  /** Turn on/off issue suppression */
  private var disableIssueDepth: Integer = 0

  def disableIssueReporting[T]()(op: => T): T = {
    disableIssueDepth += 1
    try {
      op
    } finally {
      disableIssueDepth -= 1
    }
  }

  def missingType(location: PathLocation, typeName: TypeName): Unit = {
    if (!module.isGhostedType(typeName) && !suppressIssues)
      OrgImpl.log(IssueOps.noTypeDeclaration(location, typeName))
  }

  def missingIdentifier(location: PathLocation, typeName: TypeName, name: Name): Unit = {
    if (!module.isGhostedType(EncodedName(name).asTypeName) && !suppressIssues)
      OrgImpl.log(IssueOps.noVariableOrType(location, name, typeName))
  }

  def logError(location: PathLocation, msg: String): Unit = {
    if (!suppressIssues)
      OrgImpl.logError(location, msg)
  }

  def log(issue: Issue): Unit = {
    if (!suppressIssues)
      OrgImpl.log(issue)
  }
}

/* Dependency holding support, used by other types of context */
trait HolderVerifyContext {
  private val _dependencies = new SkinnySet[Dependent]()

  def dependencies: SkinnySet[Dependent] = _dependencies

  def thisType: TypeDeclaration

  /* Locate a type, typeName may be relative so searching must be performed wrt a typeDeclaration */
  def getTypeFor(typeName: TypeName, from: TypeDeclaration): TypeResponse

  /* Record a dependency, we only store for some elements currently */
  def addDependency(dependent: Dependent): Unit = {
    dependent match {
      case _: ApexClassDeclaration => _dependencies.add(dependent)
      case _: ApexFieldLike        => _dependencies.add(dependent)
      case _: ApexMethodLike       => _dependencies.add(dependent)
      case _: ApexConstructorLike  => _dependencies.add(dependent)
      case _: ApexBlockLike        => _dependencies.add(dependent)

      case _: LabelDeclaration => _dependencies.add(dependent)
      case _: Label            => _dependencies.add(dependent)

      // No InterviewDeclaration as Interview is a type
      case _: Interview => _dependencies.add(dependent)

      case _: PageDeclaration => _dependencies.add(dependent)
      case _: Page            => _dependencies.add(dependent)

      // No ComponentDeclaration as Component is a type
      case _: Component => _dependencies.add(dependent)

      case _: SObjectDeclaration => _dependencies.add(dependent)

      case _ => ()
    }
  }

  /* Find a type and if found log that as a dependency */
  def getTypeAndAddDependency(
    typeName: TypeName,
    from: TypeDeclaration,
    usingModule: Module
  ): TypeResponse = {
    val result =
      getTypeFor(typeName, from) match {
        case Left(err) => Left(err)
        case Right(td) =>
          // Check for an 'extended' version of same type in current module, only applies to SObjects.
          // It's important for cache invalidation handling that we use current module version.
          if (td.isSObject && !td.moduleDeclaration.contains(usingModule)) {
            getTypeFor(td.typeName, thisType).toOption match {
              case Some(moduleTd) => Right(moduleTd)
              case _              => Right(td)
            }
          } else {
            Right(td)
          }
      }

    result.foreach(td => {
      addDependency(td)
      td.typeName.params.foreach(getTypeAndAddDependency(_, from, usingModule))
    })

    result
  }
}

final case class ValidationResult(
  cst: CST,
  result: ExprContext,
  vars: Option[mutable.Map[Name, VarTypeAndDefinition]]
)

class ValidateResultHolder(resultMap: Option[mutable.Map[Location, ValidationResult]]) {

  def isSaving: Boolean = resultMap.nonEmpty

  def saveResult(
    cst: CST,
    altLocation: Location,
    vars: Option[mutable.Map[Name, VarTypeAndDefinition]]
  )(op: => ExprContext): ExprContext = {
    val result = op
    if (resultMap.nonEmpty) {
      resultMap.get.put(altLocation, ValidationResult(cst, result, vars))
      resultMap.get.put(cst.location.location, ValidationResult(cst, result, vars))
    }
    result
  }
}

final class TypeVerifyContext(
  parentContext: Option[VerifyContext],
  typeDeclaration: ApexDeclaration,
  resultMap: Option[mutable.Map[Location, ValidationResult]]
) extends ValidateResultHolder(resultMap)
    with HolderVerifyContext
    with VerifyContext {

  private val typeCache = mutable.Map[(TypeName, TypeDeclaration), TypeResponse]()

  private val plugin = typeDeclaration.module.pkg.org.pluginsManager.createPlugin(typeDeclaration)

  override def parent(): Option[VerifyContext] = parentContext

  override def module: Module = typeDeclaration.module

  override def thisType: TypeDeclaration = typeDeclaration

  override def superType: Option[TypeDeclaration] = typeDeclaration.superClassDeclaration

  override def getTypeFor(typeName: TypeName, from: TypeDeclaration): TypeResponse =
    typeCache.getOrElseUpdate((typeName, from), TypeResolver(typeName, from, Some(module)))

  override def suppressIssues: Boolean =
    typeDeclaration.modifiers.contains(SUPPRESS_WARNINGS_ANNOTATION_PMD) || parent().exists(
      _.suppressIssues
    )

  def saveResult(cst: CST, altLocation: Location)(op: => ExprContext): ExprContext = {
    super.saveResult(cst, altLocation, None)(op)
  }

  def getTypeAndAddDependency(typeName: TypeName, from: TypeDeclaration): TypeResponse =
    super.getTypeAndAddDependency(typeName, from, module)

  def typePlugin: Plugin = plugin
}

final class BodyDeclarationVerifyContext(
  parentContext: TypeVerifyContext,
  classBodyDeclaration: ClassBodyDeclaration,
  exprMap: Option[mutable.Map[Location, ValidationResult]]
) extends ValidateResultHolder(exprMap)
    with HolderVerifyContext
    with VerifyContext {

  override def parent(): Option[VerifyContext] = Some(parentContext)

  override def module: Module = parentContext.module

  override def thisType: TypeDeclaration = parentContext.thisType

  override def superType: Option[TypeDeclaration] = parentContext.superType

  override def getTypeFor(typeName: TypeName, from: TypeDeclaration): TypeResponse =
    parentContext.getTypeFor(typeName, from)

  override def typePlugin: Plugin = parentContext.typePlugin

  override def suppressIssues: Boolean =
    classBodyDeclaration.modifiers.contains(SUPPRESS_WARNINGS_ANNOTATION_PMD) || parent().exists(
      _.suppressIssues
    )

  def saveResult(cst: CST, altLocation: Location)(op: => ExprContext): ExprContext = {
    super.saveResult(cst, altLocation, None)(op)
  }

  def propagateDependencies(): Unit =
    classBodyDeclaration.propagateDependencies()

  def getTypeAndAddDependency(typeName: TypeName, from: TypeDeclaration): TypeResponse =
    super.getTypeAndAddDependency(typeName, from, parentContext.module)
}

case class VarTypeAndDefinition(declaration: TypeDeclaration, definition: Option[CST])

abstract class BlockVerifyContext(parentContext: VerifyContext) extends VerifyContext {

  private val vars     = mutable.Map[Name, VarTypeAndDefinition]()
  private val usedVars = mutable.Set[Name]()

  override def parent(): Option[VerifyContext] = Some(parentContext)

  override def module: Module = parentContext.module

  override def thisType: TypeDeclaration = parentContext.thisType

  override def superType: Option[TypeDeclaration] = parentContext.superType

  override def addDependency(dependent: Dependent): Unit = parentContext.addDependency(dependent)

  override def getTypeFor(typeName: TypeName, from: TypeDeclaration): TypeResponse =
    parentContext.getTypeFor(typeName, from)

  override def getTypeAndAddDependency(typeName: TypeName, from: TypeDeclaration): TypeResponse =
    parentContext.getTypeAndAddDependency(typeName, from)

  override def typePlugin: Plugin = parentContext.typePlugin

  def declaredVars: Map[Name, VarTypeAndDefinition] = vars.toMap

  def referencedVars: Set[Name] = usedVars.toSet

  def collectVars(accum: mutable.Map[Name, VarTypeAndDefinition]): Unit = {
    accum.addAll(vars)
  }

  def getVar(name: Name, markUsed: Boolean): Option[VarTypeAndDefinition] = {
    val varType = vars.get(name)
    if (varType.nonEmpty && markUsed)
      usedVars.add(name)
    varType
  }

  def addVar(name: Name, definition: Option[CST], typeDeclaration: TypeDeclaration): Unit =
    vars.put(name, VarTypeAndDefinition(typeDeclaration, definition))

  def addVar(name: Name, definition: CST, typeName: TypeName): Unit = {
    if (getVar(name, markUsed = false).nonEmpty) {
      logError(definition.location, s"Duplicate variable '$name'")
    }

    val td = getTypeAndAddDependency(typeName, thisType).toOption
    if (td.isEmpty)
      missingType(definition.location, typeName)

    vars.put(
      name,
      td.map(VarTypeAndDefinition(_, Some(definition)))
        .getOrElse(VarTypeAndDefinition(module.any, None))
    )
  }

  def isStatic: Boolean

  override def isSaving: Boolean = parentContext.isSaving

  override def saveResult(cst: CST, altLocation: Location)(op: => ExprContext): ExprContext = {
    if (isSaving) {
      val accum = mutable.Map[Name, VarTypeAndDefinition]()
      collectVars(accum)
      saveResult(cst, altLocation, Some(accum))(op)
    } else {
      op
    }
  }

  override def saveResult(
    cst: CST,
    altLocation: Location,
    vars: Option[mutable.Map[Name, VarTypeAndDefinition]]
  )(op: => ExprContext): ExprContext = {
    parentContext.saveResult(cst, altLocation, vars)(op)
  }
}

final class OuterBlockVerifyContext(parentContext: VerifyContext, isStaticContext: Boolean)
    extends BlockVerifyContext(parentContext) {

  assert(!parentContext.isInstanceOf[BlockVerifyContext])

  override val isStatic: Boolean = isStaticContext
}

final class InnerBlockVerifyContext(parentContext: BlockVerifyContext)
    extends BlockVerifyContext(parentContext) {

  override def isStatic: Boolean = parentContext.isStatic

  override def collectVars(accum: mutable.Map[Name, VarTypeAndDefinition]): Unit = {
    parentContext.collectVars(accum)
    super.collectVars(accum)
  }

  override def getVar(name: Name, markUsed: Boolean): Option[VarTypeAndDefinition] =
    super.getVar(name, markUsed).orElse(parentContext.getVar(name, markUsed))
}

final class ExpressionVerifyContext(parentContext: BlockVerifyContext) extends VerifyContext {

  override def parent(): Option[VerifyContext] = Some(parentContext)

  override def module: Module = parentContext.module

  override def thisType: TypeDeclaration = parentContext.thisType

  override def superType: Option[TypeDeclaration] = parentContext.superType

  override def addDependency(dependent: Dependent): Unit = parentContext.addDependency(dependent)

  override def getTypeFor(typeName: TypeName, from: TypeDeclaration): TypeResponse =
    parentContext.getTypeFor(typeName, from)

  override def getTypeAndAddDependency(typeName: TypeName, from: TypeDeclaration): TypeResponse =
    parentContext.getTypeAndAddDependency(typeName, from)

  override def typePlugin: Plugin = parentContext.typePlugin

  override def isSaving: Boolean = parentContext.isSaving

  override def saveResult(
    cst: CST,
    altLocation: Location,
    vars: Option[mutable.Map[Name, VarTypeAndDefinition]]
  )(op: => ExprContext): ExprContext =
    parentContext.saveResult(cst, altLocation, vars)(op)

  override def saveResult(cst: CST, altLocation: Location)(op: => ExprContext): ExprContext =
    parentContext.saveResult(cst, altLocation)(op)

  def saveResult(cst: CST)(op: => ExprContext): ExprContext =
    parentContext.saveResult(cst, cst.location.location)(op)

  def isVar(name: Name, markUsed: Boolean = false): Option[VarTypeAndDefinition] =
    parentContext.getVar(name, markUsed: Boolean)

  def defaultNamespace(name: Name): Name =
    EncodedName(name).defaultNamespace(module.namespace).fullName
}
