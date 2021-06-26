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
import com.nawforce.apexlink.names.TypeNames._
import com.nawforce.apexlink.org.Module
import com.nawforce.apexlink.types.core._
import com.nawforce.apexlink.types.other._
import com.nawforce.apexlink.types.schema.SObjectDeclaration
import com.nawforce.pkgforce.diagnostics._
import com.nawforce.pkgforce.documents._
import com.nawforce.pkgforce.modifiers.{Modifier, ModifierOps}
import com.nawforce.pkgforce.names.{Name, Names, TypeName}
import com.nawforce.pkgforce.path.PathLike
import upickle.default._

import scala.collection.mutable

/* Helper for bulk handling of DependentSummary, normally this logic would be encapsulated by DependentSummary but
 * that is exposed as part of the API so we are using this helper instead to hide logic.
 */
object DependentValidation {

  /* Test if all Type dependencies are valid. Ignore other types of dependency since these can't be checked */
  def areTypeDependenciesValid(dependents: Array[DependentSummary], module: Module): Boolean = {
    // Horrible iteration, could this be @tailrec
    for (dependent <- dependents) {
      dependent match {
        case d: TypeDependentSummary =>
          val td = findValidTypeDependent(d, module)
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
  def findValidTypeDependent(dependent: TypeDependentSummary, module: Module): Option[TypeDeclaration] = {

    // Fallback to outer type if we are given an inner to find
    def findSummaryType(typeId: TypeId): Option[TypeDeclaration] = {
      findDependentType(typeId.typeName, typeId.module)
        .orElse(typeId.typeName.outer.flatMap(findDependentType(_, typeId.module)))
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
  def getDependents(dependents: Array[DependentSummary], module: Module): Array[Dependent] = {
    dependents.flatMap(dependent => {
      val dep = findDependent(dependent, module)
      if (dep.isEmpty) {
        LoggerOps.debug(s"Rejected other dependency $dependent")
      }
      dep
    })
  }

  def findDependent(dependent: DependentSummary, module: Module): Option[Dependent] = {
    dependent match {
      case d: TypeDependentSummary   => findDependent(d, module)
      case d: FieldDependentSummary  => findDependent(d, module)
      case d: MethodDependentSummary => findDependent(d, module)
      case _                         => None
    }
  }

  /* Find a type dependency, no need to check this as should have been done via areTypeDependenciesValid */
  def findDependent(dependent: TypeDependentSummary, module: Module): Option[TypeDeclaration] = {
    TypeId(module, dependent.typeId).flatMap(typeId => {
      findDependentType(typeId.typeName, typeId.module)
    })
  }

  /* Find a field dependency */
  def findDependent(dependent: FieldDependentSummary, module: Module): Option[FieldDeclaration] = {
    val name = Name(dependent.name)

    TypeId(module, dependent.typeId).flatMap(typeId => {
      findExactDependentType(typeId.typeName, typeId.module)
        .flatMap(_.fields.find(_.name == name))
    })
  }

  /* Find a method dependency */
  def findDependent(dependent: MethodDependentSummary, module: Module): Option[MethodDeclaration] = {
    val name = Name(dependent.name)

    TypeId(module, dependent.typeId).flatMap(typeId => {
      findExactDependentType(typeId.typeName, typeId.module)
        .flatMap {
          case td: ApexClassDeclaration =>
            td.methodMap.findMethod(name, dependent.parameterTypes)

          case td: TypeDeclaration =>
            td.methods.find(
              m =>
                m.name == name &&
                  m.parameters.map(_.typeName).sameElements(dependent.parameterTypes))
        }
    })
  }

  /* Find an outer or inner type from namespace mapping to a package */
  private def findExactDependentType(typeName: TypeName, module: Module): Option[TypeDeclaration] = {
    findDependentType(typeName, module).flatMap(td => {
      if (td.typeName != typeName) {
        td.nestedTypes.find(_.typeName == typeName)
      } else {
        Some(td)
      }
    })
  }

  /* Find an Apex type declaration from a module */
  private def findDependentType(typeName: TypeName, module: Module): Option[TypeDeclaration] = {

    TypeResolver(typeName, module) match {
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

  // Check any type dependencies are valid
  def areTypeDependenciesValid: Boolean =
    DependentValidation.areTypeDependenciesValid(dependents, module)

  // Get all dependents, this list is only valid if areTypeDependenciesValid returns true
  override lazy val dependencies: Seq[Dependent] =
    DependentValidation.getDependents(dependents, module).toIndexedSeq

  // For summary types we defer propagation of internal dependencies as they are only needed for
  // unused analysis currently but we don't want to re-execute them every time.
  override def propagateDependencies(): Unit = propagated
  private lazy val propagated: Boolean = { super.propagateDependencies(); true }
}

class SummaryParameter(parameterSummary: ParameterSummary) extends ParameterDeclaration {

  override val name: Name = Names(parameterSummary.name)
  override val typeName: TypeName = parameterSummary.typeName.intern
}

class SummaryMethod(val module: Module,
                    path: PathLike,
                    defaultNameRange: Location,
                    val outerTypeId: TypeId,
                    methodSummary: MethodSummary)
    extends ApexMethodLike
    with SummaryDependencyHandler {

  override val dependents: Array[DependentSummary] = methodSummary.dependents.map(_.intern)

  override val nameRange: PathLocation =
    PathLocation(path.toString, methodSummary.idRange.getOrElse(defaultNameRange))
  override val name: Name = Names(methodSummary.name)
  override val modifiers: Array[Modifier] = methodSummary.modifiers.flatMap(ModifierOps(_))
  override val typeName: TypeName = methodSummary.typeName.intern
  override val parameters: Array[ParameterDeclaration] =
    methodSummary.parameters.map(new SummaryParameter(_))
  override def hasBlock: Boolean = methodSummary.hasBlock
}

class SummaryBlock(val module: Module, blockSummary: BlockSummary) extends ApexBlockLike with SummaryDependencyHandler {

  override val dependents: Array[DependentSummary] = blockSummary.dependents.map(_.intern)

  override val isStatic: Boolean = blockSummary.isStatic
}

class SummaryField(val module: Module, path: PathLike, val outerTypeId: TypeId, fieldSummary: FieldSummary)
    extends ApexFieldLike
    with SummaryDependencyHandler {

  override val dependents: Array[DependentSummary] = fieldSummary.dependents.map(_.intern)

  override val nameRange: PathLocation = PathLocation(path.toString, fieldSummary.idRange.get)
  override val name: Name = Names(fieldSummary.name)
  override val modifiers: Array[Modifier] = fieldSummary.modifiers.flatMap(ModifierOps(_))
  override val typeName: TypeName = fieldSummary.typeName.intern
  override val readAccess: Modifier = ModifierOps(fieldSummary.readAccess).get
  override val writeAccess: Modifier = ModifierOps(fieldSummary.writeAccess).get
}

class SummaryConstructor(val module: Module, path: PathLike, constructorSummary: ConstructorSummary)
    extends ApexConstructorLike
    with SummaryDependencyHandler {

  override val dependents: Array[DependentSummary] = constructorSummary.dependents.map(_.intern)

  override val nameRange: PathLocation = PathLocation(path.toString, constructorSummary.idRange.get)
  override val modifiers: Array[Modifier] = constructorSummary.modifiers.flatMap(ModifierOps(_))
  override val parameters: Array[ParameterDeclaration] =
    constructorSummary.parameters.map(new SummaryParameter(_))
}

class SummaryDeclaration(val path: PathLike,
                         val module: Module,
                         val outerTypeName: Option[TypeName],
                         typeSummary: TypeSummary)
    extends ApexClassDeclaration
    with SummaryDependencyHandler {

  override val dependents: Array[DependentSummary] = typeSummary.dependents.map(_.intern)

  override val paths: Array[PathLike] = Array(path)
  override val sourceHash: Int = typeSummary.sourceHash
  override val nameLocation: PathLocation = PathLocation(path.toString, typeSummary.idRange.get)
  override val moduleDeclaration: Option[Module] = Some(module)

  override val name: Name = Names(typeSummary.name)
  override val typeName: TypeName = typeSummary.typeName
  override val nature: Nature = Nature(typeSummary.nature)
  override val modifiers: Array[Modifier] = typeSummary.modifiers.flatMap(ModifierOps(_))

  override val superClass: Option[TypeName] = typeSummary.superClass
  override val interfaces: Array[TypeName] = typeSummary.interfaces
  override val nestedTypes: Array[TypeDeclaration] = {
    typeSummary.nestedTypes.map(nt => new SummaryDeclaration(path, module, Some(typeId.typeName.intern), nt))
  }

  private val _blocks: Array[SummaryBlock] = typeSummary.blocks.map(new SummaryBlock(module, _))
  override val blocks: Array[BlockDeclaration] = _blocks.asInstanceOf[Array[BlockDeclaration]]
  private val _localFields: Array[SummaryField] =
    typeSummary.fields.map(new SummaryField(module, path, typeId, _))
  override val localFields: Array[ApexFieldLike] = _localFields.asInstanceOf[Array[ApexFieldLike]]
  private val _constructors: Array[SummaryConstructor] =
    typeSummary.constructors.map(new SummaryConstructor(module, path, _))
  override val constructors: Array[ConstructorDeclaration] =
    _constructors.asInstanceOf[Array[ConstructorDeclaration]]
  private val _localMethods: Array[SummaryMethod] = {
    typeSummary.methods.map(new SummaryMethod(module, path, nameLocation.location, typeId, _))
  }
  override val localMethods: Array[MethodDeclaration] =
    _localMethods.asInstanceOf[Array[MethodDeclaration]]

  override def summary: TypeSummary = {
    TypeSummary(sourceHash,
                Some(nameLocation.location),
                name.toString,
                typeName,
                nature.value,
                modifiers.map(_.toString).sorted,
                superClass,
                interfaces,
                _blocks.map(_.summary(shapeOnly = false)),
                _localFields.map(_.summary(shapeOnly = false)).sortBy(_.name),
                _constructors.map(_.summary(shapeOnly = false)).sortBy(_.parameters.length),
                _localMethods.map(_.summary(shapeOnly = false)).sortBy(_.name),
                nestedTypes
                  .collect { case x: SummaryDeclaration => x }
                  .map(_.summary)
                  .sortBy(_.name),
                dependents)
  }

  override def flush(pc: ParsedCache, context: PackageContext): Unit = {
    // Nothing to do here
  }

  override def validate(): Unit = {
    propagateOuterDependencies()
  }

  override def propagateAllDependencies(): Unit = {
    propagateDependencies()
  }

  private def propagateInnerDependencies(): Unit = {
    blocks.foreach(_.propagateDependencies())
    localFields.foreach(_.propagateDependencies())
    constructors.foreach(_.propagateDependencies())
    localMethods.foreach(_.propagateDependencies())
    nestedTypes.foreach(_.propagateDependencies())
  }

  def hasValidDependencies: Boolean =
    areTypeDependenciesValid &&
      _blocks.forall(b => b.areTypeDependenciesValid) &&
      _localFields.forall(f => f.areTypeDependenciesValid) &&
      _constructors.forall(c => c.areTypeDependenciesValid) &&
      _localMethods.forall(m => m.areTypeDependenciesValid) &&
      nestedTypes.collect { case x: SummaryDeclaration => x }.forall(_.hasValidDependencies)

  override def propagateDependencies(): Unit = propagated
  private lazy val propagated: Boolean = {
    super.propagateDependencies()
    propagateInnerDependencies()
    true
  }

  override def collectDependenciesByTypeName(dependsOn: mutable.Set[TypeId]): Unit = {
    val localDependencies = mutable.Set[TypeId]()
    def collect(dependents: Seq[Dependent]): Unit = {
      dependents.foreach({
        case d: ApexClassDeclaration => localDependencies.add(d.typeId)
        case d: LabelDeclaration     => localDependencies.add(d.typeId)
        case d: InterviewDeclaration => localDependencies.add(d.typeId)
        case d: PageDeclaration      => localDependencies.add(d.typeId)
        case d: ComponentDeclaration => localDependencies.add(d.typeId)
        case d: SObjectDeclaration   => localDependencies.add(d.typeId)
        case _: ApexFieldLike        => ()
        case _: ApexMethodLike       => ()
        case _: Label                => ()
        case _: Page                 => ()
      })
    }

    // Collect them all
    collect(dependencies)
    _blocks.foreach(x => collect(x.dependencies))
    _localFields.foreach(x => collect(x.dependencies))
    _constructors.foreach(x => collect(x.dependencies))
    _localMethods.foreach(x => collect(x.dependencies))
    nestedTypes
      .collect { case x: SummaryDeclaration => x }
      .foreach(_.collectDependenciesByTypeName(dependsOn))

    // Use outermost of each to get top-level dependencies
    localDependencies.foreach(dependentTypeName => {
      getOutermostDeclaration(dependentTypeName.typeName).foreach(dependsOn.add)
    })
  }

  private def getOutermostDeclaration(typeName: TypeName): Option[TypeId] = {
    TypeResolver(typeName, module) match {
      case Right(d: ApexClassDeclaration) =>
        d.outerTypeName.map(getOutermostDeclaration).getOrElse(Some(d.typeId))
      case Right(d: LabelDeclaration)     => Some(d.typeId)
      case Right(d: InterviewDeclaration) => Some(d.typeId)
      case Right(d: PageDeclaration)      => Some(d.typeId)
      case Right(d: ComponentDeclaration) => Some(d.typeId)
      case Right(_)                       => None
      case Left(_)                        => None
    }
  }
}

case class SummaryApex(module: Module, declaration: SummaryDeclaration, diagnostics: Array[Diagnostic])

object SummaryApex {
  def apply(path: PathLike, module: Module, data: Array[Byte]): SummaryApex = {
    val summary: ApexSummary = readBinary[ApexSummary](data)
    val sd = new SummaryDeclaration(path, module, None, summary.typeSummary)
    new SummaryApex(module, sd, summary.diagnostics)
  }
}
