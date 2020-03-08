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

  /* Collect actual dependents from DependentSummary entries from a set of summaries. If a dependent can not be found
   * then collection will be aborted and None returned to indicate failure.
   */
  def collectDependencies(dependents: Set[DependentSummary], pkg: PackageImpl, summaries: Map[TypeName, SummaryDeclaration])
    : Option[Set[Dependent]] = {
    // Horrible iteration, could this be @tailrec
    val accum = mutable.Buffer[Dependent]()
    for (dependent <- dependents) {
      val dep = findDependent(dependent, pkg, summaries)
      if (dep.isEmpty) {
        ServerOps.debug(ServerOps.Trace, s"Rejected dependency $dependent")
        return None
      }
      accum.append(dep.get)
    }
    Some(accum.toSet)
  }

  def findDependent(dependent: DependentSummary, pkg: PackageImpl, summaries: Map[TypeName, SummaryDeclaration])
    : Option[Dependent] = {
    dependent match {
      case d: TypeDependentSummary => findDependent(d, pkg, summaries)
      case d: FieldDependentSummary => findDependent(d, pkg, summaries)
      case _ => None
    }
  }

  /* Find a valid type dependency */
  def findDependent(dependent: TypeDependentSummary, pkg: PackageImpl, summaries: Map[TypeName, SummaryDeclaration])
    : Option[TypeDeclaration] = {

    val typeName = TypeName.fromString(dependent.typeName, pkg.namespace)
    findType(typeName, pkg, summaries)
      .filter({
        case ad: ApexDeclaration => ad.sourceHash == dependent.sourceHash
        case _ => true
      })
  }

  /* Find a field dependency */
  def findDependent(dependent: FieldDependentSummary, pkg: PackageImpl, summaries: Map[TypeName, SummaryDeclaration])
    : Option[FieldDeclaration] = {
    val typeName = TypeName.fromString(dependent.typeName, pkg.namespace)
    findType(typeName, pkg, summaries).flatMap(td => {
      // Did we find outer?
      val correctedTd =
        if (td.typeName != typeName) {
          td.nestedTypes.find(_.typeName == typeName)
        } else {
          Some(td)
        }

      correctedTd.flatMap(_.findField(Name(dependent.name), None))
    })
  }

  /* Find a type declaration either from the summaries or via namespace mapping to a package */
  private def findType(typeName: TypeName, pkg: PackageImpl, summaries: Map[TypeName, SummaryDeclaration])
    : Option[TypeDeclaration] = {

    def findSummaryType(localType: TypeName): Option[TypeDeclaration] = {
      summaries.get(localType).orElse(localType.outer.flatMap(summaries.get))
    }

    findSummaryType(typeName)
      .orElse(findSummaryType(typeName.withNamespace(pkg.namespace)))
      .orElse({
        if (typeName.maybeNamespace.exists(ns => pkg.namespaces.contains(ns))) {
          TypeRequest(typeName, pkg, excludeSObjects = false) match {
            case Left(_) => None
            case Right(ad: ApexDeclaration) => Some(ad)
            case Right(_) => None
          }
        } else {
          None
        }
      })
  }
}

class SummaryParameter(parameterSummary: ParameterSummary, fromTypeName: String => TypeName)
  extends ParameterDeclaration {

  override val name: Name = Name(parameterSummary.name)
  override val typeName: TypeName = fromTypeName(parameterSummary.typeName)
}

class SummaryMethod(path: PathLike, methodSummary: MethodSummary, fromTypeName: String => TypeName)
  extends ApexMethodLike {

  override val nameRange: RangeLocationImpl = RangeLocationImpl(path, methodSummary.idRange.get)
  override val name: Name = Name(methodSummary.name)
  override val modifiers: Seq[Modifier] = methodSummary.modifiers.map(Modifier(_))
  override val typeName: TypeName = fromTypeName(methodSummary.typeName)
  override val parameters: Seq[ParameterDeclaration] =
    methodSummary.parameters.map(new SummaryParameter(_, fromTypeName))

  // Cache of dependents, populated during dependency checking,
  var dependents: Option[Set[Dependent]] = None

  override def dependencies(): Set[Dependent] = dependents.get

  def areDependentsValid(pkg: PackageImpl, summaries: Map[TypeName, SummaryDeclaration]): Boolean = {
    dependents = DependentValidation.collectDependencies(methodSummary.dependents, pkg, summaries)
    dependents.nonEmpty
  }
}

class SummaryBlock(blockSummary: BlockSummary)
  extends BlockDeclaration {

  override val isStatic: Boolean = blockSummary.isStatic

  // Cache of dependents, populated during dependency checking,
  var dependents: Option[Set[Dependent]] = None

  override def dependencies(): Set[Dependent] = dependents.get

  def areDependentsValid(pkg: PackageImpl, summaries: Map[TypeName, SummaryDeclaration]): Boolean = {
    dependents = DependentValidation.collectDependencies(blockSummary.dependents, pkg, summaries)
    dependents.nonEmpty
  }
}

class SummaryField(path: PathLike, val outerTypeName: TypeName, fieldSummary: FieldSummary, fromTypeName: String => TypeName)
  extends ApexFieldLike {

  override val nameRange: RangeLocationImpl = RangeLocationImpl(path, fieldSummary.idRange.get)
  override val name: Name = Name(fieldSummary.name)
  override val modifiers: Seq[Modifier] = fieldSummary.modifiers.map(Modifier(_))
  override val typeName: TypeName = fromTypeName(fieldSummary.typeName)
  override val readAccess: Modifier = Modifier(fieldSummary.readAccess)
  override val writeAccess: Modifier = Modifier(fieldSummary.writeAccess)

  // Cache of dependents, populated during dependency checking,
  var dependents: Option[Set[Dependent]] = None

  override def dependencies(): Set[Dependent] = dependents.get

  def areDependentsValid(pkg: PackageImpl, summaries: Map[TypeName, SummaryDeclaration]): Boolean = {
    dependents = DependentValidation.collectDependencies(fieldSummary.dependents, pkg, summaries)
    dependents.nonEmpty
  }
}

class SummaryConstructor(path: PathLike, constructorSummary: ConstructorSummary, fromTypeName: String => TypeName)
  extends ApexConstructorLike {

  override val nameRange: RangeLocationImpl = RangeLocationImpl(path, constructorSummary.idRange.get)
  override val modifiers: Seq[Modifier] = constructorSummary.modifiers.map(Modifier(_))
  override val parameters: Seq[ParameterDeclaration] =
    constructorSummary.parameters.map(new SummaryParameter(_, fromTypeName))

  // Cache of dependents, populated during dependency checking,
  var dependents: Option[Set[Dependent]] = None

  override def dependencies(): Set[Dependent] = dependents.get

  def areDependentsValid(pkg: PackageImpl, summaries: Map[TypeName, SummaryDeclaration]): Boolean = {
    dependents = DependentValidation.collectDependencies(constructorSummary.dependents, pkg, summaries)
    dependents.nonEmpty
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

  override lazy val superClass: Option[TypeName] = fromOptTypeName(summary.superClass)
  override lazy val interfaces: Seq[TypeName] = summary.interfaces.map(fromTypeName)
  override lazy val nestedTypes: Seq[SummaryDeclaration] = {
    summary.nestedTypes.map(nt => new SummaryDeclaration(path, pkg, Some(typeName), nt))
  }

  override lazy val blocks: Seq[SummaryBlock] =
    summary.blocks.map(new SummaryBlock(_))
  override lazy val localFields: Seq[SummaryField] =
    summary.fields.map(new SummaryField(path, typeName, _, fromTypeName))
  override lazy val constructors: Seq[SummaryConstructor] =
    summary.constructors.map(new SummaryConstructor(path, _, fromTypeName))
  override lazy val localMethods: Seq[SummaryMethod] =
    summary.methods.map(new SummaryMethod(path, _, fromTypeName))

  private def fromOptTypeName(value: String): Option[TypeName] = {
    if (value.isEmpty) None else Some(fromTypeName(value))
  }

  private def fromTypeName(value: String): TypeName = {
    TypeName.fromString(value, pkg.namespace)
  }

  // Cache of dependents, populated by updateDependencies during dependency checking,
  private var dependents: Option[Set[Dependent]] = None

  override def dependencies(): Set[Dependent] = dependents.get

  def areDependentsValid(summaries: Map[TypeName, SummaryDeclaration]): Boolean = {
    // Self check first
    dependents = DependentValidation.collectDependencies(summary.dependents, pkg, summaries)
    if (dependents.isEmpty)
      return false

    // Check constituents
    blocks.forall(b => b.areDependentsValid(pkg, summaries)) &&
    localFields.forall(f => f.areDependentsValid(pkg, summaries)) &&
    constructors.forall(c => c.areDependentsValid(pkg, summaries)) &&
    localMethods.forall(m => m.areDependentsValid(pkg, summaries)) &&
    nestedTypes.forall(t => t.areDependentsValid(summaries))
  }

  override def collectDependenciesByTypeName(dependsOn: mutable.Set[TypeName]): Unit = {
    val localDependencies = mutable.Set[TypeName]()
    def collect(dependents: Option[Set[Dependent]]): Unit = {
      dependents.get.foreach({
        case ad: ApexDeclaration => localDependencies.add(ad.typeName)
        case _: ApexFieldLike => ()
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
    super.validate()
    propagateDependencies()
    blocks.foreach(_.propagateDependencies())
    localFields.foreach(_.propagateDependencies())
    constructors.foreach(_.propagateDependencies())
    localMethods.foreach(_.propagateDependencies())
    nestedTypes.foreach(_.validate())
  }
}

class SummaryApex(path: PathLike, pkg: PackageImpl, data: Array[Byte]) {

  lazy val summary: ApexSummary = readBinary[ApexSummary](data)

  lazy val declaration: SummaryDeclaration = new SummaryDeclaration(path, pkg, None, summary.typeSummary)
  lazy val diagnostics: List[Diagnostic] = summary.diagnostics
}
