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
import com.nawforce.apexlink.finding.TypeResolver
import com.nawforce.apexlink.finding.TypeResolver.TypeCache
import com.nawforce.apexlink.names.TypeNames._
import com.nawforce.apexlink.org.Module
import com.nawforce.apexlink.types.core._
import com.nawforce.apexlink.types.other._
import com.nawforce.apexlink.types.schema.SObjectDeclaration
import com.nawforce.pkgforce.diagnostics._
import com.nawforce.pkgforce.documents._
import com.nawforce.pkgforce.modifiers.Modifier
import com.nawforce.pkgforce.names.{Name, Names, TypeName}
import com.nawforce.pkgforce.parsers.Nature
import com.nawforce.pkgforce.path.{Location, PathLike, PathLocation}
import upickle.default._

import scala.collection.immutable.ArraySeq
import scala.collection.mutable

/* Helper for bulk handling of DependentSummary, normally this logic would be encapsulated by DependentSummary but
 * that is exposed as part of the API so we are using this helper instead to hide logic.
 */
object DependentValidation {

  /* Test if all Type dependencies are valid. Ignore other types of dependency since these can't be checked */
  def areTypeDependenciesValid(
    dependents: Array[DependentSummary],
    module: Module,
    typeCache: TypeCache
  ): Boolean = {
    for (dependent <- dependents) {
      dependent match {
        case d: TypeDependentSummary =>
          val td = findValidTypeDependent(d, module, typeCache)
          if (td.isEmpty) {
            LoggerOps.debug(s"Rejected type dependency $dependent")
            return false
          }
        case _ => ()
      }
    }
    true
  }

  /* Find a valid type dependency, to be valid it must carry correct hash and have valid dependencies itself */
  private def findValidTypeDependent(
    dependent: TypeDependentSummary,
    module: Module,
    typeCache: TypeCache
  ): Option[TypeDeclaration] = {

    // Fallback to outer type if we are given an inner to find
    def findSummaryType(typeId: TypeId): Option[TypeDeclaration] = {
      findDependentType(typeId.typeName, typeId.module, typeCache)
        .orElse(typeId.typeName.outer.flatMap(findDependentType(_, typeId.module, typeCache)))
    }

    TypeId(module, dependent.typeId).flatMap(typeId => {
      findSummaryType(typeId)
        .filter({
          case d: SummaryDeclaration   => d.sourceHash == dependent.sourceHash
          case d: LabelDeclaration     => d.sourceHash == dependent.sourceHash
          case d: InterviewDeclaration => d.sourceHash == dependent.sourceHash
          case d: PageDeclaration      => d.sourceHash == dependent.sourceHash
          case d: ComponentDeclaration => d.sourceHash == dependent.sourceHash
          case d: SObjectDeclaration   => d.sourceHash == dependent.sourceHash
          case _                       => true
        })
    })
  }

  /* Collect actual dependents from DependentSummary entries. This must run against full package metadata since the
   * dependents may be inherited elements coming from other types in the package.
   */
  def getDependents(
    dependents: Array[DependentSummary],
    module: Module,
    typeCache: TypeCache
  ): Array[Dependent] = {
    dependents.flatMap(dependent => {
      val dep = findDependent(dependent, module, typeCache)
      if (dep.isEmpty) {
        LoggerOps.debug(s"Rejected other dependency $dependent")
      }
      dep
    })
  }

  private def findDependent(
    dependent: DependentSummary,
    module: Module,
    typeCache: TypeCache
  ): Option[Dependent] = {
    dependent match {
      case d: TypeDependentSummary   => findDependent(d, module, typeCache)
      case d: FieldDependentSummary  => findDependent(d, module, typeCache)
      case d: MethodDependentSummary => findDependent(d, module, typeCache)
      case _                         => None
    }
  }

  /* Find a type dependency, no need to check this as should have been done via areTypeDependenciesValid */
  private def findDependent(
    dependent: TypeDependentSummary,
    module: Module,
    typeCache: TypeCache
  ): Option[TypeDeclaration] = {
    TypeId(module, dependent.typeId).flatMap(typeId => {
      findDependentType(typeId.typeName, typeId.module, typeCache)
    })
  }

  /* Find a field dependency */
  private def findDependent(
    dependent: FieldDependentSummary,
    module: Module,
    typeCache: TypeCache
  ): Option[FieldDeclaration] = {
    val name = Name(dependent.name)

    TypeId(module, dependent.typeId).flatMap(typeId => {
      findExactDependentType(typeId.typeName, typeId.module, typeCache)
        .flatMap(_.fields.find(_.name == name))
    })
  }

  /* Find a method dependency */
  private def findDependent(
    dependent: MethodDependentSummary,
    module: Module,
    typeCache: TypeCache
  ): Option[MethodDeclaration] = {
    val name = Name(dependent.name)

    TypeId(module, dependent.typeId).flatMap(typeId => {
      findExactDependentType(typeId.typeName, typeId.module, typeCache)
        .flatMap {
          case td: ApexClassDeclaration =>
            td.methodMap.findMethod(name, dependent.parameterTypes)

          case td: TypeDeclaration =>
            td.methods.find(
              m =>
                m.name == name &&
                  m.parameters.map(_.typeName) == dependent.parameterTypes
            )
        }
    })
  }

  /* Find an outer or inner type from namespace mapping to a package */
  private def findExactDependentType(
    typeName: TypeName,
    module: Module,
    typeCache: TypeCache
  ): Option[TypeDeclaration] = {
    findDependentType(typeName, module, typeCache).flatMap(td => {
      if (td.typeName != typeName) {
        td.nestedTypes.find(_.typeName == typeName)
      } else {
        Some(td)
      }
    })
  }

  /* Find an Apex type declaration from a module */
  private def findDependentType(
    typeName: TypeName,
    module: Module,
    typeCache: TypeCache
  ): Option[TypeDeclaration] = {
    val response = typeCache.getOrElseUpdate((typeName, module), TypeResolver(typeName, module))
    response match {
      case Left(_)                        => None
      case Right(d: ApexClassDeclaration) => Some(d)
      case Right(d: LabelDeclaration)     => Some(d)
      case Right(d: InterviewDeclaration) => Some(d)
      case Right(d: PageDeclaration)      => Some(d)
      case Right(d: ComponentDeclaration) => Some(d)
      case Right(d: SObjectDeclaration)   => Some(d)
      case Right(_)                       => None
    }
  }
}

/** Common dependency handling for Summary elements */
trait SummaryDependencyHandler extends DependencyHolder {
  val module: Module
  val dependents: Array[DependentSummary]
  private var _dependents: Option[Seq[Dependent]] = None

  /** Check all type dependencies are valid. */
  def areTypeDependenciesValid(typeCache: TypeCache): Boolean =
    DependentValidation.areTypeDependenciesValid(dependents, module, typeCache)

  /** Get all the dependents, this list is only valid if areTypeDependenciesValid returns true, see also
    * [[populateDependencies]].
    */
  override lazy val dependencies: Seq[Dependent] = populateDependencies(new TypeCache())

  /** Manually populate the dependencies. Using this is optional but can improve performance due to type caching. */
  def populateDependencies(typeCache: TypeCache): Seq[Dependent] = {
    if (_dependents.isEmpty) {
      _dependents = Some(
        DependentValidation.getDependents(dependents, module, typeCache).toIndexedSeq
      )
    }
    _dependents.get
  }
}

class SummaryParameter(parameterSummary: ParameterSummary) extends ParameterDeclaration {

  override val name: Name         = Names(parameterSummary.name)
  override val typeName: TypeName = parameterSummary.typeName.intern
}

class SummaryMethod(
  val module: Module,
  path: PathLike,
  val outerTypeId: TypeId,
  override val inTest: Boolean,
  methodSummary: MethodSummary
) extends ApexMethodLike
    with SummaryDependencyHandler {

  override val dependents: Array[DependentSummary] = methodSummary.dependents.map(_.intern)

  override val location: PathLocation        = PathLocation(path, methodSummary.location)
  override val idLocation: Location          = methodSummary.idLocation
  override val name: Name                    = Names(methodSummary.name)
  override val modifiers: ArraySeq[Modifier] = methodSummary.modifiers
  override val typeName: TypeName            = methodSummary.typeName.intern
  override val parameters: ArraySeq[ParameterDeclaration] =
    methodSummary.parameters.map(new SummaryParameter(_))

  override def hasBlock: Boolean = methodSummary.hasBlock

}

class SummaryBlock(
  val module: Module,
  path: PathLike,
  override val inTest: Boolean,
  blockSummary: BlockSummary
) extends ApexBlockLike
    with SummaryDependencyHandler {

  override val dependents: Array[DependentSummary] = blockSummary.dependents.map(_.intern)

  override def location: PathLocation = PathLocation(path, blockSummary.location)

  override val isStatic: Boolean = blockSummary.isStatic
}

class SummaryField(
  val module: Module,
  path: PathLike,
  val outerTypeId: TypeId,
  override val inTest: Boolean,
  fieldSummary: FieldSummary
) extends ApexFieldLike
    with SummaryDependencyHandler {

  override val dependents: Array[DependentSummary] = fieldSummary.dependents.map(_.intern)

  override val location: PathLocation        = PathLocation(path, fieldSummary.location)
  override val idLocation: Location          = fieldSummary.idLocation
  override val name: Name                    = Names(fieldSummary.name)
  override val nature: Nature                = fieldSummary.nature
  override val modifiers: ArraySeq[Modifier] = fieldSummary.modifiers
  override val typeName: TypeName            = fieldSummary.typeName.intern
  override val readAccess: Modifier          = fieldSummary.readAccess
  override val writeAccess: Modifier         = fieldSummary.writeAccess
}

class SummaryConstructor(
  val module: Module,
  path: PathLike,
  override val inTest: Boolean,
  constructorSummary: ConstructorSummary
) extends ApexConstructorLike
    with SummaryDependencyHandler {

  override val dependents: Array[DependentSummary] = constructorSummary.dependents.map(_.intern)

  override val location: PathLocation        = PathLocation(path, constructorSummary.location)
  override val idLocation: Location          = constructorSummary.idLocation
  override val modifiers: ArraySeq[Modifier] = constructorSummary.modifiers
  override val parameters: ArraySeq[ParameterDeclaration] =
    constructorSummary.parameters.map(new SummaryParameter(_))
}

class SummaryDeclaration(
  path: PathLike,
  val module: Module,
  val outerTypeName: Option[TypeName],
  typeSummary: TypeSummary
) extends ApexClassDeclaration
    with SummaryDependencyHandler {

  override val dependents: Array[DependentSummary] = typeSummary.dependents.map(_.intern)

  override def paths: ArraySeq[PathLike] = ArraySeq(path)

  override val sourceHash: Int        = typeSummary.sourceHash
  override val location: PathLocation = PathLocation(path, typeSummary.location)
  override val idLocation: Location   = typeSummary.idLocation

  override val moduleDeclaration: Option[Module] = Some(module)

  override val name: Name                    = Names(typeSummary.name)
  override val typeName: TypeName            = typeSummary.typeName
  override val nature: Nature                = Nature.forType(typeSummary.nature)
  override val modifiers: ArraySeq[Modifier] = typeSummary.modifiers
  override val inTest: Boolean               = typeSummary.inTest

  override val superClass: Option[TypeName]   = typeSummary.superClass
  override val interfaces: ArraySeq[TypeName] = typeSummary.interfaces
  override val nestedTypes: ArraySeq[SummaryDeclaration] =
    typeSummary.nestedTypes.map(
      new SummaryDeclaration(path, module, Some(typeId.typeName.intern), _)
    )
  override val blocks: ArraySeq[SummaryBlock] =
    typeSummary.blocks.map(new SummaryBlock(module, path, inTest, _))
  override val localFields: ArraySeq[SummaryField] =
    typeSummary.fields.map(new SummaryField(module, path, typeId, inTest, _))
  override val constructors: ArraySeq[SummaryConstructor] =
    typeSummary.constructors.map(new SummaryConstructor(module, path, inTest, _))
  override val localMethods: ArraySeq[SummaryMethod] =
    typeSummary.methods.map(new SummaryMethod(module, path, typeId, inTest, _))

  override def summary: TypeSummary = {
    TypeSummary(
      sourceHash,
      location.location,
      idLocation,
      name.toString,
      typeName,
      nature.value,
      modifiers,
      inTest,
      superClass,
      interfaces,
      blocks.map(_.summary),
      localFields.map(_.summary).sortBy(_.name),
      constructors.map(_.summary).sortBy(_.parameters.length),
      localMethods.map(_.summary).sortBy(_.name),
      nestedTypes
        .collect { case x: SummaryDeclaration => x }
        .map(_.summary)
        .sortBy(_.name),
      dependents
    )
  }

  override def flush(pc: ParsedCache, context: PackageContext): Unit = {
    // Nothing to do here
  }

  override def validate(): Unit = {
    propagateOuterDependencies(new TypeCache())
  }

  def hasValidDependencies(typeCache: TypeCache): Boolean =
    areTypeDependenciesValid(typeCache) &&
      blocks.forall(b => b.areTypeDependenciesValid(typeCache)) &&
      localFields.forall(f => f.areTypeDependenciesValid(typeCache)) &&
      constructors.forall(c => c.areTypeDependenciesValid(typeCache)) &&
      localMethods.forall(m => m.areTypeDependenciesValid(typeCache)) &&
      nestedTypes
        .collect { case x: SummaryDeclaration => x }
        .forall(_.hasValidDependencies(typeCache))

  override def propagateDependencies(): Unit = {
    super.propagateDependencies()

    blocks.foreach(_.propagateDependencies())
    localFields.foreach(_.propagateDependencies())
    constructors.foreach(_.propagateDependencies())
    localMethods.foreach(_.propagateDependencies())
    nestedTypes.foreach(_.propagateDependencies())
  }

  override def gatherDependencies(
    dependsOn: mutable.Set[TypeId],
    apexOnly: Boolean,
    outerTypesOnly: Boolean,
    typeCache: TypeCache
  ): Unit = {
    val localDependencies = mutable.Set[TypeId]()

    def collect(dependents: Seq[Dependent]): Unit = {
      dependents.foreach({
        case d: ApexClassDeclaration =>
          localDependencies.add(d.outerTypeId)
          if (!outerTypesOnly) localDependencies.add(d.typeId)
        case d: LabelDeclaration if !apexOnly     => localDependencies.add(d.typeId)
        case d: InterviewDeclaration if !apexOnly => localDependencies.add(d.typeId)
        case d: PageDeclaration if !apexOnly      => localDependencies.add(d.typeId)
        case d: ComponentDeclaration if !apexOnly => localDependencies.add(d.typeId)
        case d: SObjectDeclaration if !apexOnly   => localDependencies.add(d.typeId)
        case _                                    => ()
      })
    }

    // Collect them all
    collect(populateDependencies(typeCache))
    blocks.foreach(x => collect(x.populateDependencies(typeCache)))
    localFields.foreach(x => collect(x.populateDependencies(typeCache)))
    constructors.foreach(x => collect(x.populateDependencies(typeCache)))
    localMethods.foreach(x => collect(x.populateDependencies(typeCache)))
    nestedTypes
      .collect { case x: SummaryDeclaration => x }
      .foreach(_.gatherDependencies(dependsOn, apexOnly, outerTypesOnly, typeCache))

    dependsOn.addAll(localDependencies)
  }
}

case class SummaryApex(
  module: Module,
  declaration: SummaryDeclaration,
  diagnostics: Array[Diagnostic]
)

object SummaryApex {
  def apply(path: PathLike, module: Module, data: Array[Byte]): SummaryApex = {
    val summary: ApexSummary = readBinary[ApexSummary](data)
    val sd                   = new SummaryDeclaration(path, module, None, summary.typeSummary)
    new SummaryApex(module, sd, summary.diagnostics)
  }
}
