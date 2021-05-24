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
import com.nawforce.apexlink.finding.{TypeError, TypeResolver}
import com.nawforce.apexlink.memory.SkinnySet
import com.nawforce.apexlink.org.{Module, OrgImpl}
import com.nawforce.apexlink.types.apex._
import com.nawforce.apexlink.types.core.{Dependent, TypeDeclaration}
import com.nawforce.apexlink.types.other._
import com.nawforce.pkgforce.diagnostics.{Issue, PathLocation}
import com.nawforce.pkgforce.modifiers.SUPPRESS_WARNINGS_ANNOTATION
import com.nawforce.pkgforce.names.{EncodedName, Name, TypeName}

import scala.collection.mutable

trait VerifyContext {
  def parent(): Option[VerifyContext]

  /* Module for current outer type */
  def module: Module

  /* Get type declaration of 'this', option as not set in trigger */
  def thisType: Option[TypeDeclaration]

  /* Get type declaration of 'super' */
  def superType: Option[TypeDeclaration]

  /* Declare a dependency on dependent */
  def addDependency(dependent: Dependent): Unit

  /* Locate a type, typeName may be relative so searching must be performed wrt a typeDeclaration */
  def getTypeFor(typeName: TypeName,
                 from: Option[TypeDeclaration],
                 excludeSObjects: Boolean = false): Either[TypeError, TypeDeclaration]

  /* Helper to locate a relative or absolute type and add as dependency if found */
  def getTypeAndAddDependency(typeName: TypeName,
                              from: Option[TypeDeclaration],
                              excludeSObjects: Boolean = false): Either[TypeError, TypeDeclaration]

  def suppressWarnings: Boolean = parent().exists(_.suppressWarnings)

  def missingType(location: PathLocation, typeName: TypeName): Unit = {
    if (!module.isGhostedType(typeName) && !suppressWarnings)
      OrgImpl.log(IssueOps.noTypeDeclaration(location, typeName))
  }

  def missingIdentifier(location: PathLocation, typeName: TypeName, name: Name): Unit = {
    if (!module.isGhostedType(EncodedName(name).asTypeName) && !suppressWarnings)
      OrgImpl.log(IssueOps.noVariableOrType(location, name, typeName))
  }

  def logError(location: PathLocation, msg: String): Unit = {
    if (!suppressWarnings)
      OrgImpl.logError(location, msg)
  }

  def log(issue: Issue): Unit = {
    if (!suppressWarnings)
      OrgImpl.log(issue)
  }
}

/* Dependency holding support, used by other types of context */
trait HolderVerifyContext {
  private val _dependencies = new SkinnySet[Dependent]()

  def dependencies: SkinnySet[Dependent] = _dependencies

  /* Locate a type, typeName may be relative so searching must be performed wrt a typeDeclaration */
  def getTypeFor(typeName: TypeName,
                 from: Option[TypeDeclaration],
                 excludeSObjects: Boolean = false): Either[TypeError, TypeDeclaration]

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

      case _ => ()
    }
  }

  /* Find a type and if found log that as a dependency */
  def getTypeAndAddDependency(
    typeName: TypeName,
    from: Option[TypeDeclaration],
    excludeSObjects: Boolean = false): Either[TypeError, TypeDeclaration] = {
    val result = getTypeFor(typeName, from, excludeSObjects)
    result.foreach(td => {
      addDependency(td)
      td.typeName.params.foreach(getTypeAndAddDependency(_, from, excludeSObjects))
    })
    result
  }
}

class TypeVerifyContext(parentContext: Option[VerifyContext],
                        typeDeclaration: ApexDeclaration,
                        propagateDependencies: Boolean)
    extends HolderVerifyContext
    with VerifyContext {

  private val typeResolver = new TypeResolver

  override def parent(): Option[VerifyContext] = parentContext

  override def module: Module = typeDeclaration.module

  override def thisType: Option[TypeDeclaration] = Some(typeDeclaration)

  override def superType: Option[TypeDeclaration] = typeDeclaration.superClassDeclaration

  override def getTypeFor(typeName: TypeName,
                          from: Option[TypeDeclaration],
                          excludeSObjects: Boolean = false): Either[TypeError, TypeDeclaration] = {
    typeResolver.find(typeName, from, thisType.flatMap(_.moduleDeclaration), excludeSObjects)
  }

  override def suppressWarnings: Boolean =
    typeDeclaration.modifiers.contains(SUPPRESS_WARNINGS_ANNOTATION) || parent().exists(
      _.suppressWarnings)

  def shouldPropagateDependencies: Boolean = propagateDependencies

  def propagateDependencies(): Unit = {
    if (shouldPropagateDependencies)
      typeDeclaration.propagateDependencies()
  }
}

class BodyDeclarationVerifyContext(parentContext: TypeVerifyContext,
                                   classBodyDeclaration: ClassBodyDeclaration)
    extends HolderVerifyContext
    with VerifyContext {

  override def parent(): Option[VerifyContext] = Some(parentContext)

  override def module: Module = parentContext.module

  override def thisType: Option[TypeDeclaration] = parentContext.thisType

  override def superType: Option[TypeDeclaration] = parentContext.superType

  override def getTypeFor(typeName: TypeName,
                          from: Option[TypeDeclaration],
                          excludeSObjects: Boolean = false): Either[TypeError, TypeDeclaration] = {
    parentContext.getTypeFor(typeName, from, excludeSObjects)
  }

  override def suppressWarnings: Boolean =
    classBodyDeclaration.modifiers.contains(SUPPRESS_WARNINGS_ANNOTATION) || parent().exists(
      _.suppressWarnings)

  def shouldPropagateDependencies: Boolean = parentContext.shouldPropagateDependencies

  def propagateDependencies(): Unit = {
    if (parentContext.shouldPropagateDependencies)
      classBodyDeclaration.propagateDependencies()
  }
}

abstract class BlockVerifyContext(parentContext: VerifyContext) extends VerifyContext {

  private val vars = mutable.Map[Name, TypeDeclaration]()

  override def parent(): Option[VerifyContext] = Some(parentContext)

  override def module: Module = parentContext.module

  override def thisType: Option[TypeDeclaration] = parentContext.thisType

  override def superType: Option[TypeDeclaration] = parentContext.superType

  override def addDependency(dependent: Dependent): Unit = parentContext.addDependency(dependent)

  override def getTypeFor(typeName: TypeName,
                          from: Option[TypeDeclaration],
                          excludeSObjects: Boolean = false): Either[TypeError, TypeDeclaration] = {
    parentContext.getTypeFor(typeName, from, excludeSObjects)
  }

  override def getTypeAndAddDependency(
    typeName: TypeName,
    from: Option[TypeDeclaration],
    excludeSObjects: Boolean = false): Either[TypeError, TypeDeclaration] = {
    parentContext.getTypeAndAddDependency(typeName, from, excludeSObjects)
  }

  def getVar(name: Name): Option[TypeDeclaration] = {
    vars.get(name)
  }

  def addVar(name: Name, typeDeclaration: TypeDeclaration): Unit = {
    vars.put(name, typeDeclaration)
  }

  def addVar(name: Name, location: PathLocation, typeName: TypeName): Unit = {
    if (getVar(name).nonEmpty) {
      logError(location, s"Duplicate variable '$name'")
    }

    val td = getTypeAndAddDependency(typeName, thisType).toOption
    if (td.isEmpty)
      missingType(location, typeName)

    vars.put(name, td.getOrElse(module.any))
  }

  def isStatic: Boolean
}

class OuterBlockVerifyContext(parentContext: VerifyContext, isStaticContext: Boolean)
    extends BlockVerifyContext(parentContext) {

  assert(!parentContext.isInstanceOf[BlockVerifyContext])

  override val isStatic: Boolean = isStaticContext
}

class InnerBlockVerifyContext(parentContext: BlockVerifyContext)
    extends BlockVerifyContext(parentContext) {

  override def isStatic: Boolean = parentContext.isStatic

  override def getVar(name: Name): Option[TypeDeclaration] = {
    super.getVar(name).orElse(parentContext.getVar(name))
  }
}

class ExpressionVerifyContext(parentContext: BlockVerifyContext) extends VerifyContext {

  override def parent(): Option[VerifyContext] = Some(parentContext)

  override def module: Module = parentContext.module

  override def thisType: Option[TypeDeclaration] = parentContext.thisType

  override def superType: Option[TypeDeclaration] = parentContext.superType

  override def addDependency(dependent: Dependent): Unit = parentContext.addDependency(dependent)

  override def getTypeFor(typeName: TypeName,
                          from: Option[TypeDeclaration],
                          excludeSObjects: Boolean = false): Either[TypeError, TypeDeclaration] = {
    parentContext.getTypeFor(typeName, from, excludeSObjects)
  }

  override def getTypeAndAddDependency(
    typeName: TypeName,
    from: Option[TypeDeclaration],
    excludeSObjects: Boolean = false): Either[TypeError, TypeDeclaration] = {
    parentContext.getTypeAndAddDependency(typeName, from, excludeSObjects)
  }

  def isVar(name: Name): Option[TypeDeclaration] = parentContext.getVar(name)

  def defaultNamespace(name: Name): Name = {
    EncodedName(name).defaultNamespace(module.namespace).fullName
  }
}
