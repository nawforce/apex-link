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
package com.nawforce.common.types.apex

import com.nawforce.common.api._
import com.nawforce.common.cst.Modifier
import com.nawforce.common.documents._
import com.nawforce.common.finding.TypeRequest
import com.nawforce.common.metadata.{DependencyHolder, Dependent}
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.org.PackageImpl
import com.nawforce.common.path.PathLike
import com.nawforce.common.types._
import upickle.default._

import scala.collection.mutable

/* Helper for bulk handling of DependentSummary, normally this logic would be encapsulated by DependentSummary but
 * that is exposed as part of the API so we are using this helper instead to hide logic.
 */
object DependentValidation {

  /* Test if all Type dependencies are valid. Ignore other types of dependency since these can't be checked */
  def areTypeDependenciesValid(dependents: Set[DependentSummary], pkg: PackageImpl) : Boolean = {
    // Horrible iteration, could this be @tailrec
    for (dependent <- dependents) {
      dependent match {
        case d: TypeDependentSummary =>
          val td = findValidTypeDependent(d, pkg)
          if (td.isEmpty) {
            ServerOps.debug(ServerOps.Trace, s"Rejected type dependency $dependent")
            return false
          }
        case _ => ()
      }
    }
    true
  }

  /* Find a valid type dependency, to be valid it must carry correct hash and have valid dependencies itself */
  def findValidTypeDependent(dependent: TypeDependentSummary, pkg: PackageImpl): Option[TypeDeclaration] = {

    // Fallback to outer type if we are given an inner to find
    def findSummaryType(localType: TypeName): Option[TypeDeclaration] = {
      findType(localType, pkg).orElse(localType.outer.flatMap(findType(_, pkg)))
    }

    findSummaryType(TypeName(dependent.typeName))
      .filter({
        case sd: SummaryDeclaration => sd.sourceHash == dependent.sourceHash
        case _ => true
      })
  }

  /* Collect actual dependents from DependentSummary entries. This must run against full package metadata since the
   * dependents may be inherited elements coming from other types in the package.
   */
  def getDependents(dependents: Set[DependentSummary], pkg: PackageImpl): Set[Dependent] = {
    dependents.flatMap(dependent => {
      val dep = findDependent(dependent, pkg)
      if (dep.isEmpty) {
        ServerOps.debug(ServerOps.Trace, s"Rejected other dependency $dependent")
      }
      dep
    })
  }

  def findDependent(dependent: DependentSummary, pkg: PackageImpl)
    : Option[Dependent] = {
    dependent match {
      case d: TypeDependentSummary => findDependent(d, pkg)
      case d: FieldDependentSummary => findDependent(d, pkg)
      case d: MethodDependentSummary => findDependent(d, pkg)
      case _ => None
    }
  }

  /* Find a type dependency, no need to check this as should have been done via areTypeDependenciesValid */
  def findDependent(dependent: TypeDependentSummary, pkg: PackageImpl)
    : Option[TypeDeclaration] = {
    findType(TypeName(dependent.typeName), pkg)
  }

  /* Find a field dependency */
  def findDependent(dependent: FieldDependentSummary, pkg: PackageImpl)
    : Option[FieldDeclaration] = {
    val name = Name(dependent.name)
    findExactType(TypeName(dependent.typeName), pkg)
      .flatMap(_.fields.find(_.name == name))
  }

  /* Find a method dependency */
  def findDependent(dependent: MethodDependentSummary, pkg: PackageImpl)
  : Option[MethodDeclaration] = {
    val name = Name(dependent.name)
    findExactType(TypeName(dependent.typeName), pkg)
      .flatMap(_.methods.find(m => m.name == name &&
        m.parameters.map(_.typeName) == dependent.parameters.map(_.typeName)))
  }

  /* Find an outer or inner type from namespace mapping to a package */
  private def findExactType(typeName: TypeName, pkg: PackageImpl)
    : Option[TypeDeclaration] = {
    findType(typeName, pkg).flatMap(td => {
      if (td.typeName != typeName) {
        td.nestedTypes.find(_.typeName == typeName)
      } else {
        Some(td)
      }
    })
  }

  /* Find an Apex type declaration from a package */
  private def findType(typeName: TypeName, pkg: PackageImpl)
    : Option[TypeDeclaration] = {

    TypeRequest(typeName, pkg, excludeSObjects = false) match {
      case Left(_) => None
      case Right(ad: ApexClassDeclaration) => Some(ad)
      case Right(_) => None
    }
  }
}

/** Common dependency handling for Summary elements */
trait SummaryDependencyHandler extends DependencyHolder {
  val pkg: PackageImpl
  val dependents: Set[DependentSummary]

  // Check any type dependencies are valid
  def areTypeDependenciesValid: Boolean = DependentValidation.areTypeDependenciesValid(dependents, pkg)

  // Get all dependents, this list is only valid if areTypeDependenciesValid returns true
  override lazy val dependencies: Set[Dependent] = DependentValidation.getDependents(dependents, pkg)

  // For summary types we defer propagation of internal dependencies as they are only needed for
  // unused analysis currently but we don't want to re-execute them every time.
  override def propagateDependencies(): Unit = propagated
  private lazy val propagated: Boolean = {super.propagateDependencies(); true}
}

class SummaryParameter(parameterSummary: ParameterSummary)
  extends ParameterDeclaration {

  override val name: Name = Name(parameterSummary.name)
  override val typeName: TypeName = TypeName(parameterSummary.typeName)
}

class SummaryMethod(val pkg: PackageImpl, path: PathLike, defaultNameRange: RangeLocation, val outerTypeName: TypeName,
                    methodSummary: MethodSummary) extends ApexMethodLike with SummaryDependencyHandler {

  override lazy val dependents: Set[DependentSummary] = methodSummary.dependents

  override val nameRange: RangeLocationImpl = RangeLocationImpl(path, methodSummary.idRange.getOrElse(defaultNameRange))
  override val name: Name = Name(methodSummary.name)
  override val modifiers: Seq[Modifier] = methodSummary.modifiers.map(Modifier(_))
  override val typeName: TypeName = TypeName(methodSummary.typeName)
  override val parameters: Seq[ParameterDeclaration] = methodSummary.parameters.map(new SummaryParameter(_))
}

class SummaryBlock(val pkg :PackageImpl, blockSummary: BlockSummary)
  extends BlockDeclaration with SummaryDependencyHandler {

  override lazy val dependents: Set[DependentSummary] = blockSummary.dependents

  override val isStatic: Boolean = blockSummary.isStatic
}

class SummaryField(val pkg: PackageImpl, path: PathLike, val outerTypeName: TypeName, fieldSummary: FieldSummary)
  extends ApexFieldLike with SummaryDependencyHandler {

  override lazy val dependents: Set[DependentSummary] = fieldSummary.dependents

  override val nameRange: RangeLocationImpl = RangeLocationImpl(path, fieldSummary.idRange.get)
  override val name: Name = Name(fieldSummary.name)
  override val modifiers: Seq[Modifier] = fieldSummary.modifiers.map(Modifier(_))
  override val typeName: TypeName = TypeName(fieldSummary.typeName)
  override val readAccess: Modifier = Modifier(fieldSummary.readAccess)
  override val writeAccess: Modifier = Modifier(fieldSummary.writeAccess)
}

class SummaryConstructor(val pkg: PackageImpl, path: PathLike, constructorSummary: ConstructorSummary)
  extends ApexConstructorLike with SummaryDependencyHandler {

  override lazy val dependents: Set[DependentSummary] = constructorSummary.dependents

  override val nameRange: RangeLocationImpl = RangeLocationImpl(path, constructorSummary.idRange.get)
  override val modifiers: Seq[Modifier] = constructorSummary.modifiers.map(Modifier(_))
  override val parameters: Seq[ParameterDeclaration] = constructorSummary.parameters.map(new SummaryParameter(_))
}

class SummaryDeclaration(val path: PathLike, val pkg: PackageImpl, val outerTypeName: Option[TypeName],
                         summary: TypeSummary)
  extends ApexClassDeclaration with SummaryDependencyHandler {

  // For outer types only, update the dependency holders so we can defer dependency propagation
  if (outerTypeName.isEmpty)
    summary.holders.map(TypeName(_)).foreach(addTypeDependencyHolder)

  override lazy val dependents: Set[DependentSummary] = summary.dependents

  override lazy val sourceHash: Int = summary.sourceHash
  override val nameLocation: LocationImpl = RangeLocationImpl(path, summary.idRange.get)
  override val packageDeclaration: Option[PackageImpl] = Some(pkg)

  override lazy val name: Name = Name(summary.name)
  override lazy val nature: Nature = Nature(summary.nature)
  override lazy val modifiers: Seq[Modifier] = summary.modifiers.map(Modifier(_))

  override lazy val superClass: Option[TypeName] = summary.superClass.map(TypeName(_))
  override lazy val interfaces: Seq[TypeName] = summary.interfaces.map(TypeName(_))
  override lazy val nestedTypes: Seq[SummaryDeclaration] = {
    summary.nestedTypes.map(nt => new SummaryDeclaration(path, pkg, Some(typeName), nt))
  }

  override lazy val blocks: Seq[SummaryBlock] =
    summary.blocks.map(new SummaryBlock(pkg, _))
  override lazy val localFields: Seq[SummaryField] =
    summary.fields.map(new SummaryField(pkg, path, typeName, _))
  override lazy val constructors: Seq[SummaryConstructor] =
    summary.constructors.map(new SummaryConstructor(pkg, path, _))
  override lazy val localMethods: Seq[SummaryMethod] =
    summary.methods.map(new SummaryMethod(pkg, path, summary.idRange.get, typeName, _))

  override def flush(pc: ParsedCache, context: PackageContext): Unit = {
    // TODO: Nothing to do here yet, update when upsert metadata supported
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
      blocks.forall(b => b.areTypeDependenciesValid) &&
      localFields.forall(f => f.areTypeDependenciesValid) &&
      constructors.forall(c => c.areTypeDependenciesValid) &&
      localMethods.forall(m => m.areTypeDependenciesValid) &&
      nestedTypes.forall(_.hasValidDependencies)

  override def propagateDependencies(): Unit = propagated
  private lazy val propagated: Boolean = {
    super.propagateDependencies()
    propagateInnerDependencies()
    true
  }

  override def collectDependenciesByTypeName(dependsOn: mutable.Set[TypeName]): Unit = {
    val localDependencies = mutable.Set[TypeName]()
    def collect(dependents: Set[Dependent]): Unit = {
      dependents.foreach({
        case ad: ApexClassDeclaration => localDependencies.add(ad.typeName)
        case _: ApexFieldLike => ()
        case _: ApexMethodLike => ()
      })
    }

    // Collect them all
    collect(dependencies)
    blocks.foreach(x => collect(x.dependencies))
    localFields.foreach(x => collect(x.dependencies))
    constructors.foreach(x => collect(x.dependencies))
    localMethods.foreach(x => collect(x.dependencies))
    nestedTypes.foreach(_.collectDependenciesByTypeName(dependsOn))

    // Use outermost of each to get top-level dependencies
    localDependencies.foreach(dependentTypeName => {
      getOutermostDeclaration(dependentTypeName).foreach(td => dependsOn.add(td.typeName))
    })
  }

  private def getOutermostDeclaration(typeName: TypeName): Option[ApexClassDeclaration] = {
    TypeRequest(typeName, pkg, excludeSObjects = false) match {
      case Right(td: ApexClassDeclaration) =>
        td.outerTypeName.map(getOutermostDeclaration).getOrElse(Some(td))
      case Right(_) => None
      case Left(_) => None
    }
  }
}

class SummaryApex(path: PathLike, pkg: PackageImpl, data: Array[Byte]) {

  lazy val summary: ApexSummary = readBinary[ApexSummary](data)

  lazy val declaration: SummaryDeclaration = new SummaryDeclaration(path, pkg, None, summary.typeSummary)
  lazy val diagnostics: List[Diagnostic] = summary.diagnostics
}
