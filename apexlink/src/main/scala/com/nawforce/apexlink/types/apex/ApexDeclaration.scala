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
import com.nawforce.apexlink.org.{Module, OrgImpl}
import com.nawforce.apexlink.types.core._
import com.nawforce.pkgforce.documents._
import com.nawforce.pkgforce.modifiers._
import com.nawforce.pkgforce.names.{Name, TypeName}
import com.nawforce.pkgforce.parsers.Nature
import com.nawforce.pkgforce.path.{IdLocatable, Locatable, Location, PathLocation}

import scala.collection.immutable.ArraySeq
import scala.collection.mutable
import scala.util.hashing.MurmurHash3

/** Apex block core features, be they full or summary style */
trait ApexBlockLike extends BlockDeclaration with Locatable {
  def location: PathLocation

  def summary: BlockSummary = BlockSummary(location.location, isStatic, dependencySummary())
}

/** Apex defined constructor core features, be they full or summary style */
trait ApexConstructorLike extends ConstructorDeclaration with IdLocatable {
  def summary: ConstructorSummary = {
    ConstructorSummary(location.location,
                       idLocation,
                       modifiers,
                       parameters.map(_.serialise),
                       dependencySummary())
  }
}

/** Unifying trait for ApexMethodLike and CustomMethodDeclaration. Both need to appear to be visible from a
  * a type but have little in common beyond allowing for constructions of a summary. */
trait ApexVisibleMethodLike extends MethodDeclaration {
  def summary: MethodSummary
}

/** Apex defined method core features, be they full or summary style */
trait ApexMethodLike extends ApexVisibleMethodLike with IdLocatable {
  val outerTypeId: TypeId

  // Synthetic methods are generated locally & so can be excluded from issue reporting
  def isSynthetic: Boolean = false

  // Populated by type MethodMap construction
  var shadows: List[MethodDeclaration] = Nil

  def resetShadows(): Unit = {
    shadows = Nil
  }

  def addShadow(method: MethodDeclaration): Unit = {
    if (method ne this) {
      shadows = method :: shadows
    }
  }

  def summary: MethodSummary = {
    MethodSummary(location.location,
                  idLocation,
                  name.toString,
                  modifiers,
                  typeName,
                  parameters.map(_.serialise),
                  hasBlock,
                  dependencySummary())
  }
}

/** Apex defined fields core features, be they full or summary style */
trait ApexFieldLike extends FieldDeclaration with IdLocatable {
  val outerTypeId: TypeId
  val nature: Nature
  val idTarget: Option[TypeName] = None

  def summary: FieldSummary = {
    FieldSummary(location.location,
      idLocation,
      name.toString,
      nature,
      modifiers,
      typeName,
      readAccess,
      writeAccess,
      dependencySummary())
  }
}

/** Apex defined types core features, be they full or summary style */
trait ApexDeclaration extends DependentType with IdLocatable {
  val sourceHash: Int
  val module: Module

  def summary: TypeSummary
}

/** Apex defined type for parsed (aka Full) classes, interfaces, enums & triggers */
trait ApexFullDeclaration extends ApexDeclaration {
  def getValidationMap(line: Int, offset: Int): Map[Location, ValidationResult]
  def findDeclarationFromSourceReference(searchTerm: String,location: Location): Option[ApexDeclaration]
}

/** Apex defined trigger of either full or summary type */
trait ApexTriggerDeclaration extends ApexDeclaration

/** Apex defined classes, interfaces, enum of either full or summary type */
trait ApexClassDeclaration extends ApexDeclaration with DependencyHolder {
  val localFields: ArraySeq[ApexFieldLike]
  val localMethods: ArraySeq[ApexMethodLike]

  override def nestedTypes: ArraySeq[ApexClassDeclaration]

  /** Override to resolve conflict, TypeDeclaration & DependencyHolder both default false */
  override val inTest: Boolean = false

  /** Override to handle request to flush the type to passed cache if dirty */
  def flush(pc: ParsedCache, context: PackageContext): Unit

  override def superClassDeclaration: Option[TypeDeclaration] =
    superClass.flatMap(sc => TypeResolver(sc, this).toOption)

  override def interfaceDeclarations: ArraySeq[TypeDeclaration] =
    interfaces.flatMap(i => TypeResolver(i, this).toOption)

  /** Obtain a source hash for this class and all it's ancestors */
  def deepHash: Int = {
    deepHash(mutable.Set())
  }

  private def deepHash(accum: mutable.Set[ApexClassDeclaration]): Int = {
    if (accum.contains(this)) {
      0
    } else {
      accum.add(this)
      MurmurHash3.arrayHash(
        Array(this.sourceHash) ++
          superClassDeclaration.collect { case td: ApexClassDeclaration => td }.map(_.deepHash(accum)).toArray ++
          interfaceDeclarations.collect { case td: ApexClassDeclaration => td }.map(_.deepHash(accum)))
    }
  }

  override lazy val isComplete: Boolean = {
    (superClassDeclaration.nonEmpty && superClassDeclaration.get.isComplete) || superClass.isEmpty
  }

  override lazy val fields: ArraySeq[FieldDeclaration] = {
    ArraySeq.unsafeWrapArray(localFields
      .groupBy(f => f.name)
      .collect {
        case (_, single) if single.length == 1 => single.head
        case (_, duplicates) =>
          duplicates.tail.foreach {
            case af: ApexFieldLike =>
              OrgImpl.logError(af.idPathLocation, s"Duplicate field/property: '${af.name}'")
            case _ => assert(false)
          }
          duplicates.head
      }
      .toArray)
  }

  lazy val staticMethods: ArraySeq[MethodDeclaration] = {
    localMethods.filter(_.isStatic) ++
      (superClassDeclaration match {
        case Some(td: ApexClassDeclaration) =>
          td.staticMethods
        case _ =>
          MethodDeclaration.emptyMethodDeclarations
      })
  }

  lazy val outerStaticMethods: ArraySeq[MethodDeclaration] = {
    outerTypeName.flatMap(ot => TypeResolver(ot, this).toOption) match {
      case Some(td: ApexClassDeclaration) => td.staticMethods
      case _ => MethodDeclaration.emptyMethodDeclarations
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

  private def createMethodMap: MethodMap = {
    val errorLocation = Some(idPathLocation)
    val methods = superClassDeclaration match {
      case Some(at: ApexClassDeclaration) =>
        MethodMap(this, errorLocation, at.methodMap, localMethods, outerStaticMethods, interfaceDeclarations)
      case Some(td: TypeDeclaration) =>
        MethodMap(this, errorLocation, MethodMap(td), localMethods, outerStaticMethods, interfaceDeclarations)
      case _ =>
        MethodMap(this, errorLocation, MethodMap.empty(), localMethods, outerStaticMethods, interfaceDeclarations)
    }

    methods.errors.foreach(OrgImpl.log)
    methods
  }

  override def methods: ArraySeq[MethodDeclaration] = {
    methodMap.allMethods
  }

  override def findMethod(name: Name,
                          params: ArraySeq[TypeName],
                          staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Either[String, MethodDeclaration] = {
    methodMap.findMethod(name, params, staticContext, verifyContext)
  }

  def bombScore(total: Int): (Int, Int, Double) = {
    val magicScale = 1.7306 // Places score 0-100

    val typeCache = new TypeCache()
    val dependencies = mutable.Set[TypeId]()
    gatherDependencies(dependencies, apexOnly = true, outerTypesOnly = true, typeCache)
    dependencies.remove(typeId)
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
