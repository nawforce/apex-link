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
import com.nawforce.common.documents.{LocationImpl, RangeLocationImpl}
import com.nawforce.common.finding.TypeRequest
import com.nawforce.common.metadata.Dependent
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.path.PathLike
import com.nawforce.common.pkg.PackageImpl
import com.nawforce.common.types._
import upickle.default._

import scala.collection.mutable

/* Helper for bulk handling of DependentSummary, normally this logic would be encapsulated by DependentSummary but
 * that is exposed as part of the API so we are using this helper instead to hide logic.
 */
object DependentValidation {

  /* Test if all Type dependencies are valid. Ignore other types of dependency since these can't be checked */
  def areTypeDependenciesValid(dependents: Set[DependentSummary], pkg: PackageImpl,
                            summaries: Map[TypeName, SummaryDeclaration]) : Boolean = {
    // Horrible iteration, could this be @tailrec
    for (dependent <- dependents) {
      dependent match {
        case d: TypeDependentSummary =>
          val td = findDependent(d, pkg, summaries)
          if (td.isEmpty) {
            ServerOps.debug(ServerOps.Trace, s"Rejected dependency $dependent")
            return false
          }
        case _ => ()
      }
    }
    true
  }

  /* Find a valid type dependency in a summary set*/
  def findDependent(dependent: TypeDependentSummary, pkg: PackageImpl, summaries: Map[TypeName, SummaryDeclaration])
  : Option[TypeDeclaration] = {

    findTypeFromSummaries(dependent.typeName, pkg, summaries)
      .filter({
        case ad: ApexDeclaration => ad.sourceHash == dependent.sourceHash
        case _ => true
      })
  }

  /* Find a type declaration either from the summaries or via namespace mapping to a package */
  private def findTypeFromSummaries(typeName: TypeName, pkg: PackageImpl, summaries: Map[TypeName, SummaryDeclaration])
  : Option[TypeDeclaration] = {

    def findSummaryType(localType: TypeName): Option[TypeDeclaration] = {
      summaries.get(localType).orElse(localType.outer.flatMap(summaries.get))
    }

    findSummaryType(typeName)
      .orElse(findSummaryType(typeName.withNamespace(pkg.namespace)))
      .orElse(findType(typeName, pkg))
  }

  /* Collect actual dependents from DependentSummary entries. This must run against full package metadata since the
   * dependents may be inherited elements coming from other types in the package.
   */
  def getDependents(dependents: Set[DependentSummary], pkg: PackageImpl): Set[Dependent] = {
    dependents.flatMap(dependent => {
      val dep = findDependent(dependent, pkg)
      if (dep.isEmpty) {
        ServerOps.debug(ServerOps.Trace, s"Rejected dependency $dependent")
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
    findType(dependent.typeName, pkg)
  }

  /* Find a field dependency */
  def findDependent(dependent: FieldDependentSummary, pkg: PackageImpl)
    : Option[FieldDeclaration] = {
    val name = Name(dependent.name)
    findExactType(dependent.typeName, pkg)
      .flatMap(_.fields.find(_.name == name))
  }

  /* Find a method dependency */
  def findDependent(dependent: MethodDependentSummary, pkg: PackageImpl)
  : Option[MethodDeclaration] = {
    val name = Name(dependent.name)
    findExactType(dependent.typeName, pkg)
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
      case Right(ad: ApexDeclaration) => Some(ad)
      case Right(_) => None
    }
  }
}

class SummaryParameter(parameterSummary: ParameterSummary)
  extends ParameterDeclaration {

  override val name: Name = Name(parameterSummary.name)
  override val typeName: TypeName = parameterSummary.typeName
}

class SummaryMethod(path: PathLike, val outerTypeName: TypeName, methodSummary: MethodSummary)
  extends ApexMethodLike {

  override val nameRange: RangeLocationImpl = RangeLocationImpl(path, methodSummary.idRange.get)
  override val name: Name = Name(methodSummary.name)
  override val modifiers: Seq[Modifier] = methodSummary.modifiers.map(Modifier(_))
  override val typeName: TypeName = methodSummary.typeName
  override val parameters: Seq[ParameterDeclaration] = methodSummary.parameters.map(new SummaryParameter(_))

  def areTypeDependenciesValid(pkg: PackageImpl, summaries: Map[TypeName, SummaryDeclaration]): Boolean = {
    DependentValidation.areTypeDependenciesValid(methodSummary.dependents, pkg, summaries)
  }

  // Cache of dependents, populated during validation
  var dependents: Set[Dependent] = Set.empty
  override def dependencies(): Iterable[Dependent] = dependents

  def updateDependencies(pkg: PackageImpl): Unit = {
    dependents = DependentValidation.getDependents(methodSummary.dependents, pkg)
    propagateDependencies()
  }
}

class SummaryBlock(blockSummary: BlockSummary)
  extends BlockDeclaration {

  override val isStatic: Boolean = blockSummary.isStatic

  def areTypeDependenciesValid(pkg: PackageImpl, summaries: Map[TypeName, SummaryDeclaration]): Boolean = {
    DependentValidation.areTypeDependenciesValid(blockSummary.dependents, pkg, summaries)
  }

  // Cache of dependents, populated during validation
  var dependents: Set[Dependent] = Set.empty
  override def dependencies(): Set[Dependent] = dependents

  def updateDependencies(pkg: PackageImpl): Unit = {
    dependents = DependentValidation.getDependents(blockSummary.dependents, pkg)
    propagateDependencies()
  }
}

class SummaryField(path: PathLike, val outerTypeName: TypeName, fieldSummary: FieldSummary)
  extends ApexFieldLike {

  override val nameRange: RangeLocationImpl = RangeLocationImpl(path, fieldSummary.idRange.get)
  override val name: Name = Name(fieldSummary.name)
  override val modifiers: Seq[Modifier] = fieldSummary.modifiers.map(Modifier(_))
  override val typeName: TypeName = fieldSummary.typeName
  override val readAccess: Modifier = Modifier(fieldSummary.readAccess)
  override val writeAccess: Modifier = Modifier(fieldSummary.writeAccess)

  def areTypeDependenciesValid(pkg: PackageImpl, summaries: Map[TypeName, SummaryDeclaration]): Boolean = {
    DependentValidation.areTypeDependenciesValid(fieldSummary.dependents, pkg, summaries)
  }

  // Cache of dependents, populated during validation
  var dependents: Set[Dependent] = Set.empty
  override def dependencies(): Set[Dependent] = dependents

  def updateDependencies(pkg: PackageImpl): Unit = {
    dependents = DependentValidation.getDependents(fieldSummary.dependents, pkg)
    propagateDependencies()
  }
}

class SummaryConstructor(path: PathLike, constructorSummary: ConstructorSummary)
  extends ApexConstructorLike {

  override val nameRange: RangeLocationImpl = RangeLocationImpl(path, constructorSummary.idRange.get)
  override val modifiers: Seq[Modifier] = constructorSummary.modifiers.map(Modifier(_))
  override val parameters: Seq[ParameterDeclaration] = constructorSummary.parameters.map(new SummaryParameter(_))

  def areTypeDependenciesValid(pkg: PackageImpl, summaries: Map[TypeName, SummaryDeclaration]): Boolean = {
    DependentValidation.areTypeDependenciesValid(constructorSummary.dependents, pkg, summaries)
  }

  // Cache of dependents, populated during validation
  var dependents: Set[Dependent] = Set.empty
  override def dependencies(): Set[Dependent] = dependents

  def updateDependencies(pkg: PackageImpl): Unit = {
    dependents = DependentValidation.getDependents(constructorSummary.dependents, pkg)
    propagateDependencies()
  }
}

class SummaryDeclaration(val path: PathLike, val pkg: PackageImpl, val outerTypeName: Option[TypeName],
                         summary: TypeSummary)
  extends ApexDeclaration {

  override lazy val sourceHash: Int = summary.sourceHash
  override val nameLocation: LocationImpl = RangeLocationImpl(path, summary.idRange.get)
  override val packageDeclaration: Option[PackageImpl] = Some(pkg)

  override lazy val name: Name = Name(summary.name)
  override lazy val nature: Nature = Nature(summary.nature)
  override lazy val modifiers: Seq[Modifier] = summary.modifiers.map(Modifier(_))

  override lazy val superClass: Option[TypeName] = summary.superClass
  override lazy val interfaces: Seq[TypeName] = summary.interfaces
  override lazy val nestedTypes: Seq[SummaryDeclaration] = {
    summary.nestedTypes.map(nt => new SummaryDeclaration(path, pkg, Some(typeName), nt))
  }

  override lazy val blocks: Seq[SummaryBlock] = summary.blocks.map(new SummaryBlock(_))
  override lazy val localFields: Seq[SummaryField] = summary.fields.map(new SummaryField(path, typeName, _))
  override lazy val constructors: Seq[SummaryConstructor] = summary.constructors.map(new SummaryConstructor(path, _))
  override lazy val localMethods: Seq[SummaryMethod] = summary.methods.map(new SummaryMethod(path, typeName, _))

  def areTypeDependenciesValid(summaries: Map[TypeName, SummaryDeclaration]): Boolean = {
    DependentValidation.areTypeDependenciesValid(summary.dependents, pkg, summaries) &&
      blocks.forall(b => b.areTypeDependenciesValid(pkg, summaries)) &&
      localFields.forall(f => f.areTypeDependenciesValid(pkg, summaries)) &&
      constructors.forall(c => c.areTypeDependenciesValid(pkg, summaries)) &&
      localMethods.forall(m => m.areTypeDependenciesValid(pkg, summaries)) &&
      nestedTypes.forall(t => t.areTypeDependenciesValid(summaries))
  }

  // Cache of dependents, populated by updateDependencies during dependency checking,
  private var dependents: Set[Dependent] = Set.empty

  override def dependencies(): Set[Dependent] = dependents

  private def updateDependencies(): Unit = {
    dependents = DependentValidation.getDependents(summary.dependents, pkg)
    propagateDependencies()

    blocks.foreach(b => b.updateDependencies(pkg))
    localFields.foreach(f => f.updateDependencies(pkg))
    constructors.foreach(c => c.updateDependencies(pkg))
    localMethods.foreach(m => m.updateDependencies(pkg))
    nestedTypes.foreach(t => t.updateDependencies())
  }

  override def collectDependenciesByTypeName(dependsOn: mutable.Set[TypeName]): Unit = {
    val localDependencies = mutable.Set[TypeName]()
    def collect(dependents: Set[Dependent]): Unit = {
      dependents.foreach({
        case ad: ApexDeclaration => localDependencies.add(ad.typeName)
        case _: ApexFieldLike => ()
        case _: ApexMethodLike => ()
      })
    }

    // Collect them all
    collect(dependents)
    blocks.foreach(x => collect(x.dependents))
    localFields.foreach(x => collect(x.dependents))
    constructors.foreach(x => collect(x.dependents))
    localMethods.foreach(x => collect(x.dependents))
    nestedTypes.foreach(_.collectDependenciesByTypeName(dependsOn))

    // Use outermost of each to get top-level dependencies
    localDependencies.foreach(dependentTypeName => {
      getOutermostDeclaration(dependentTypeName).foreach(td => dependsOn.add(td.typeName))
    })
  }

  private def getOutermostDeclaration(typeName: TypeName): Option[ApexDeclaration] = {
    TypeRequest(typeName, pkg, excludeSObjects = false) match {
      case Right(td: ApexDeclaration) =>
        td.outerTypeName.map(getOutermostDeclaration).getOrElse(Some(td))
      case Right(_) => None
      case Left(_) => None
    }
  }

  override def validate(): Unit = {
    updateDependencies()

    // Hack, we only want to propagate outer type dependencies on outer types
    if (outerTypeName.isEmpty)
      super.validate()
  }
}

class SummaryApex(path: PathLike, pkg: PackageImpl, data: Array[Byte]) {

  lazy val summary: ApexSummary = readBinary[ApexSummary](data)

  lazy val declaration: SummaryDeclaration = new SummaryDeclaration(path, pkg, None, summary.typeSummary)
  lazy val diagnostics: List[Diagnostic] = summary.diagnostics
}
