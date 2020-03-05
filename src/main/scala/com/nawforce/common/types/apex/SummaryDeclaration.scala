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

trait DependentValidation {
  def isValid(pkg: PackageImpl, dependent: DependentSummary, summaries: Map[TypeName, SummaryDeclaration]): Boolean = {
    val typeName = TypeName.fromString(dependent.name, pkg.namespace)

    isSummaryValid(typeName, dependent.sourceHash, summaries)
      .orElse(isSummaryValid(typeName.withNamespace(pkg.namespace), dependent.sourceHash, summaries))
      .orElse({
        Some(
          if (typeName.maybeNamespace.exists(ns => pkg.namespaces.contains(ns))) {
            TypeRequest(typeName, pkg, excludeSObjects = false) match {
              case Left(_) => false
              case Right(ad: ApexDeclaration) => ad.sourceHash == dependent.sourceHash
              case Right(_) => true
            }
          } else {
            false
          })
      })
      .orElse(Some(false)).get
  }

  private def isSummaryValid(typeName: TypeName, sourceHash: Int,
                             summaries: Map[TypeName, SummaryDeclaration]): Option[Boolean] = {
    // Handle a outer or inner type
    summaries.get(typeName).map(_.sourceHash == sourceHash)
      .orElse(typeName.outer.flatMap(summaries.get).map(_.sourceHash == sourceHash))
  }
}

class SummaryParameter(parameterSummary: ParameterSummary, fromTypeName: String => TypeName)
  extends ParameterDeclaration {

  override val name: Name = Name(parameterSummary.name)
  override val typeName: TypeName = fromTypeName(parameterSummary.typeName)
}

class SummaryMethod(path: PathLike, methodSummary: MethodSummary, fromTypeName: String => TypeName)
  extends ApexMethodLike with DependentValidation {

  override val nameRange: RangeLocationImpl = RangeLocationImpl(path, methodSummary.idRange.get)
  override val name: Name = Name(methodSummary.name)
  override val modifiers: Seq[Modifier] = methodSummary.modifiers.map(Modifier(_))
  override val typeName: TypeName = fromTypeName(methodSummary.typeName)
  override val parameters: Seq[ParameterDeclaration] =
    methodSummary.parameters.map(new SummaryParameter(_, fromTypeName))

  def areDependentsValid(pkg: PackageImpl, summaries: Map[TypeName, SummaryDeclaration]): Boolean = {
    methodSummary.dependents.forall(d => isValid(pkg, d, summaries))
  }

  def collectDependencies(dependsOn: mutable.Set[TypeName]): Unit = {
    methodSummary.dependents.foreach(d => dependsOn.add(TypeName.fromString(d.name)))
  }
}

class SummaryBlock(blockSummary: BlockSummary)
  extends BlockDeclaration with DependentValidation {

  override val isStatic: Boolean = blockSummary.isStatic

  def areDependentsValid(pkg: PackageImpl, summaries: Map[TypeName, SummaryDeclaration]): Boolean = {
    blockSummary.dependents.forall(d => isValid(pkg, d, summaries))
  }

  def collectDependencies(dependsOn: mutable.Set[TypeName]): Unit = {
    blockSummary.dependents.foreach(d => dependsOn.add(TypeName.fromString(d.name)))
  }
}

class SummaryField(path: PathLike, fieldSummary: FieldSummary, fromTypeName: String => TypeName)
  extends ApexFieldLike  with DependentValidation {

  override val nameRange: RangeLocationImpl = RangeLocationImpl(path, fieldSummary.idRange.get)
  override val name: Name = Name(fieldSummary.name)
  override val modifiers: Seq[Modifier] = fieldSummary.modifiers.map(Modifier(_))
  override val typeName: TypeName = fromTypeName(fieldSummary.typeName)
  override val readAccess: Modifier = Modifier(fieldSummary.readAccess)
  override val writeAccess: Modifier = Modifier(fieldSummary.writeAccess)

  def areDependentsValid(pkg: PackageImpl, summaries: Map[TypeName, SummaryDeclaration]): Boolean = {
    fieldSummary.dependents.forall(d => isValid(pkg, d, summaries))
  }

  def collectDependencies(dependsOn: mutable.Set[TypeName]): Unit = {
    fieldSummary.dependents.foreach(d => dependsOn.add(TypeName.fromString(d.name)))
  }
}

class SummaryConstructor(path: PathLike, constructorSummary: ConstructorSummary, fromTypeName: String => TypeName)
  extends ApexConstructorLike with DependentValidation {

  override val nameRange: RangeLocationImpl = RangeLocationImpl(path, constructorSummary.idRange.get)
  override val modifiers: Seq[Modifier] = constructorSummary.modifiers.map(Modifier(_))
  override val parameters: Seq[ParameterDeclaration] =
    constructorSummary.parameters.map(new SummaryParameter(_, fromTypeName))

  def areDependentsValid(pkg: PackageImpl, summaries: Map[TypeName, SummaryDeclaration]): Boolean = {
    constructorSummary.dependents.forall(d => isValid(pkg, d, summaries))
  }
  def collectDependencies(dependsOn: mutable.Set[TypeName]): Unit = {
    constructorSummary.dependents.foreach(d => dependsOn.add(TypeName.fromString(d.name)))
  }
}

class SummaryDeclaration(val path: PathLike, val pkg: PackageImpl, val outerTypeName: Option[TypeName],
                         summary: TypeSummary)
  extends ApexDeclaration with DependentValidation {

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
    summary.fields.map(new SummaryField(path, _, fromTypeName))
  override lazy val constructors: Seq[SummaryConstructor] =
    summary.constructors.map(new SummaryConstructor(path, _, fromTypeName))
  override lazy val localMethods: Seq[SummaryMethod] =
    summary.methods.map(new SummaryMethod(path, _, fromTypeName))

  override def collectDependenciesByTypeName(dependsOn: mutable.Set[TypeName]): Unit = {
    val localDependenies = mutable.Set[TypeName]()
    summary.dependents.foreach(d => localDependenies.add(TypeName.fromString(d.name)))
    blocks.foreach(_.collectDependencies(localDependenies))
    localFields.foreach(_.collectDependencies(localDependenies))
    constructors.foreach(_.collectDependencies(localDependenies))
    localMethods.foreach(_.collectDependencies(localDependenies))
    nestedTypes.foreach(_.collectDependenciesByTypeName(localDependenies))

    localDependenies.foreach(dependentTypeName => {
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

  private def fromOptTypeName(value: String): Option[TypeName] = {
    if (value.isEmpty) None else Some(fromTypeName(value))
  }

  private def fromTypeName(value: String): TypeName = {
    TypeName.fromString(value, pkg.namespace)
  }

  def areDependentsValid(summaries: Map[TypeName, SummaryDeclaration]): Boolean = {
    summary.dependents.forall(d => isValid(pkg, d, summaries)) &&
      blocks.forall(b => b.areDependentsValid(pkg, summaries)) &&
      localFields.forall(f => f.areDependentsValid(pkg, summaries)) &&
      constructors.forall(c => c.areDependentsValid(pkg, summaries)) &&
      localMethods.forall(m => m.areDependentsValid(pkg, summaries)) &&
      nestedTypes.forall(t => t.areDependentsValid(summaries))
  }

  override def dependencies(): Set[Dependent] = {
    summary.dependents.flatMap(dependent => {
      val typeName = TypeName.fromString(dependent.name)
      pkg.getType(typeName, None).toOption
    }).toSet
  }
}

class SummaryApex(path: PathLike, pkg: PackageImpl, data: Array[Byte]) {

  lazy val summary: ApexSummary = readBinary[ApexSummary](data)

  lazy val declaration: SummaryDeclaration = new SummaryDeclaration(path, pkg, None, summary.typeSummary)
  lazy val diagnostics: List[Diagnostic] = summary.diagnostics
}
