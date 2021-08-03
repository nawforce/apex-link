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

package com.nawforce.apexlink.types.apex

import com.nawforce.apexlink.api._
import com.nawforce.apexlink.cst._
import com.nawforce.apexlink.finding.TypeResolver
import com.nawforce.apexlink.finding.TypeResolver.TypeCache
import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.org.{Module, OrgImpl}
import com.nawforce.apexlink.types.core._
import com.nawforce.pkgforce.diagnostics._
import com.nawforce.pkgforce.documents._
import com.nawforce.pkgforce.modifiers._
import com.nawforce.pkgforce.names.{Name, TypeName}
import com.nawforce.pkgforce.path.PathLike

import scala.collection.mutable
import scala.util.hashing.MurmurHash3

/** Apex block core features, be they full or summary style */
trait ApexBlockLike extends BlockDeclaration {
  def summary: BlockSummary = BlockSummary(isStatic, dependencySummary())
}

/** Apex defined constructor core features, be they full or summary style */
trait ApexConstructorLike extends ConstructorDeclaration {
  def fullLocation: PathLocation
  def nameLocation: PathLocation

  def summary: ConstructorSummary = {
    ConstructorSummary(fullLocation.location,
                       nameLocation.location,
                       modifiers.map(_.toString).sorted,
                       parameters.map(_.serialise).sortBy(_.name),
                       dependencySummary())
  }
}

/** Unifying trait for ApexMethodLike and CustomMethodDeclaration. Both need to be appears to be visible from a
  * a type but have little in common beyond allowing for constructions of a summary. */
trait ApexVisibleMethodLike extends MethodDeclaration {
  def summary: MethodSummary
}

/** Apex defined method core features, be they full or summary style */
trait ApexMethodLike extends ApexVisibleMethodLike {
  val outerTypeId: TypeId
  def fullLocation: PathLocation
  def nameLocation: PathLocation

  def hasBlock: Boolean

  // Populated by type MethodMap construction
  private var shadows: List[MethodDeclaration] = Nil

  def addShadow(method: MethodDeclaration): Unit = {
    if (method ne this) {
      shadows = method :: shadows
    }
  }

  def isEntry: Boolean = {
    modifiers.contains(ISTEST_ANNOTATION) ||
    modifiers.contains(TEST_SETUP_ANNOTATION) ||
    modifiers.contains(TEST_METHOD_MODIFIER) ||
    modifiers.contains(GLOBAL_MODIFIER)
  }

  /** Is the method in use, NOTE: requires a MethodMap is constructed for shadow support first! */
  def isUsed(module: Module): Boolean = {
    isEntry || hasHolders || modifiers.contains(SUPPRESS_WARNINGS_ANNOTATION) ||
    shadows.exists({
      case am: ApexMethodLike   => am.isUsed(module)
      case _: MethodDeclaration => true
      case _                    => false
    }) ||
    parameters.exists(parameter => module.isGhostedType(parameter.typeName))
  }

  def summary: MethodSummary = {
    MethodSummary(fullLocation.location,
                  nameLocation.location,
                  name.toString,
                  modifiers.map(_.toString).sorted,
                  typeName,
                  parameters.map(_.serialise),
                  hasBlock,
                  dependencySummary())
  }
}

/** Apex defined fields core features, be they full or summary style */
trait ApexFieldLike extends FieldDeclaration {
  val outerTypeId: TypeId
  def fullLocation: PathLocation
  def nameLocation: PathLocation
  val idTarget: Option[TypeName] = None

  def isEntry: Boolean = {
    modifiers.contains(GLOBAL_MODIFIER)
  }

  def isUsed: Boolean = {
    isEntry || hasHolders || modifiers.contains(SUPPRESS_WARNINGS_ANNOTATION)
  }

  def summary: FieldSummary = {
    FieldSummary(fullLocation.location,
                 nameLocation.location,
                 name.toString,
                 modifiers.map(_.toString).sorted,
                 typeName,
                 readAccess.toString,
                 writeAccess.toString,
                 dependencySummary())
  }
}

/** Apex defined types core features, be they full or summary style */
trait ApexDeclaration extends TypeDeclaration with DependentType {
  val path: PathLike
  val sourceHash: Int
  val module: Module
  def fullLocation: Location
  val nameLocation: Location

  // Get summary of this type
  def summary: TypeSummary
}

trait ApexFullDeclaration extends ApexDeclaration {
  def summary: TypeSummary
}

trait ApexTriggerDeclaration extends ApexDeclaration

trait ApexClassDeclaration extends ApexDeclaration {
  val localFields: Array[ApexFieldLike]
  val localMethods: Array[MethodDeclaration]

  def isTest: Boolean = modifiers.intersect(ApexClassDeclaration.testModifiers).nonEmpty

  /** Override to handle request to flush the type to passed cache if dirty */
  def flush(pc: ParsedCache, context: PackageContext): Unit

  /** Override to handle request to propagate all dependencies in type */
  def propagateAllDependencies(): Unit

  override def superClassDeclaration: Option[TypeDeclaration] =
    superClass.flatMap(sc => TypeResolver(sc, this).toOption)

  override def interfaceDeclarations: Array[TypeDeclaration] =
    interfaces.flatMap(i => TypeResolver(i, this).toOption)

  /** Obtain a source hash for this class and all it's ancestors */
  def deepHash: Int = {
    MurmurHash3.arrayHash(
      Array(this.sourceHash) ++
        superClassDeclaration.collect { case td: ApexClassDeclaration => td }.map(_.deepHash).toArray ++
        interfaceDeclarations.collect { case td: ApexClassDeclaration => td }.map(_.deepHash))
  }

  override lazy val isComplete: Boolean = {
    (superClassDeclaration.nonEmpty && superClassDeclaration.get.isComplete) || superClass.isEmpty
  }

  override lazy val fields: Array[FieldDeclaration] = {
    localFields
      .groupBy(f => f.name)
      .collect {
        case (_, single) if single.length == 1 => single.head
        case (_, duplicates) =>
          duplicates.tail.foreach {
            case af: ApexFieldLike =>
              OrgImpl.logError(af.nameLocation, s"Duplicate field/property: '${af.name}'")
            case _ => assert(false)
          }
          duplicates.head
      }
      .toArray
  }

  lazy val staticMethods: Array[MethodDeclaration] = {
    localMethods.filter(_.isStatic) ++
      (superClassDeclaration match {
        case Some(td: ApexClassDeclaration) =>
          td.localMethods.filter(_.isStatic) ++ td.staticMethods
        case _ =>
          MethodDeclaration.emptyMethodDeclarations
      })
  }

  lazy val outerStaticMethods: Array[MethodDeclaration] = {
    outerTypeName.flatMap(ot => TypeResolver(ot, this).toOption) match {
      case Some(td: ApexClassDeclaration) => td.staticMethods
      case _                              => MethodDeclaration.emptyMethodDeclarations
    }
  }

  def methodMap: MethodMap = {
    if (_methodMap.isEmpty)
      _methodMap = Some(createMethodMap)
    _methodMap.get
  }

  protected def resetMethodMapIfInvalid(): Unit = {
    if (_methodMap.exists(_.deepHash != deepHash)) {
      _methodMap = None
    }
  }

  private var _methodMap: Option[MethodMap] = None

  def createMethodMap: MethodMap = {
    val errorLocation = Some(PathLocation(path.toString, nameLocation))
    val allMethods = outerStaticMethods ++ localMethods
    val methods = superClassDeclaration match {
      case Some(at: ApexClassDeclaration) =>
        MethodMap(this, errorLocation, at.methodMap, allMethods, interfaceDeclarations)
      case Some(td: TypeDeclaration) =>
        MethodMap(this,
                  errorLocation,
                  MethodMap(td, None, MethodMap.empty(), td.methods, TypeDeclaration.emptyTypeDeclarations),
                  allMethods,
                  interfaceDeclarations)
      case _ =>
        MethodMap(this, errorLocation, MethodMap.empty(), allMethods, interfaceDeclarations)
    }

    methods.errors.foreach(OrgImpl.log)
    methods
  }

  override def methods: Array[MethodDeclaration] = {
    methodMap.allMethods
  }

  override def findMethod(name: Name,
                          params: Array[TypeName],
                          staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Option[MethodDeclaration] = {
    methodMap.findMethod(name, params, staticContext, verifyContext)
  }

  private lazy val isController: Boolean = {
    getTypeDependencyHolders.toIterable.exists(tid =>
      tid.typeName == TypeNames.Page || tid.typeName == TypeNames.Component)
  }

  def unused(): Array[Issue] = {
    // Block at class level
    if (modifiers.contains(SUPPRESS_WARNINGS_ANNOTATION) || isController)
      return Issue.emptyArray

    // Hack: Unused calculation requires a methodMap as its establishes shadow relationships
    methodMap

    val unused = {
      nestedTypes
        .collect { case ad: ApexClassDeclaration => ad }
        .flatMap(ad => ad.unused()) ++
        localFields
          .flatMap {
            case af: ApexFieldLike if !af.isUsed => Some(af)
            case _                               => None
          }
          .map(field =>
            new Issue(
              field.nameLocation.path,
              Diagnostic(UNUSED_CATEGORY, field.nameLocation.location, s"Unused Field or Property '${field.name}'"))) ++
        localMethods
          .flatMap {
            case am: ApexMethodLike if !am.isUsed(module) => Some(am)
            case _                                        => None
          }
          .map(
            method =>
              new Issue(
                method.nameLocation.path,
                Diagnostic(UNUSED_CATEGORY, method.nameLocation.location, s"Unused Method '${method.signature}'")))
    }

    if (!hasHolders && unused.length == nestedTypes.length + localFields.length + localMethods.length) {
      Array(new Issue(path.toString, Diagnostic(UNUSED_CATEGORY, nameLocation, s"Type '$typeName' is unused")))
    } else {
      unused
    }
  }

  def bombScore(total: Int): (Int, Int, Double) = {
    val magicScale = 1.7306 // Places score 0-100

    val typeCache = new TypeCache()
    val dependencies = mutable.Set[TypeId]()
    collectDependenciesByTypeName(dependencies, apexOnly = true, typeCache)
    val uses = dependencies.size
    val usedBy = getTypeDependencyHolders.size
    val score = magicScale * Math.log(1 + (uses * 2000).toDouble / total) * Math.log(
      1 + (usedBy * 2000).toDouble / total)
    val roundScore = BigDecimal(score.toString).setScale(2, BigDecimal.RoundingMode.HALF_UP).doubleValue
    (uses, usedBy, roundScore)
  }
}

object ApexClassDeclaration {
  val testModifiers: Seq[Modifier] = Seq(TEST_METHOD_MODIFIER, ISTEST_ANNOTATION)
}
