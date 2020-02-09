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
import com.nawforce.common.documents.{Location, RangeLocation}
import com.nawforce.common.finding.TypeRequest
import com.nawforce.common.metadata.Dependent
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.path.PathLike
import com.nawforce.common.types._
import upickle.default._

import scala.collection.mutable

trait DependentValidation {
  def isValid(pkg: PackageDeclaration, dependent: DependentSummary, summaries: Map[TypeName, SummaryDeclaration]): Boolean = {
    TypeName.fromString(dependent.name) match {
      // Package namespace
      case typeName if typeName.maybeNamespace.exists(ns => pkg.namespace.contains(ns)) =>
        isSummaryValid(typeName, dependent.sourceHash, summaries)

      // Dependent package namespace
      case typeName if typeName.maybeNamespace.exists(ns => pkg.namespaces.contains(ns)) =>
        TypeRequest(typeName, pkg, excludeSObjects = false) match {
          case Left(_) => false
          case Right(ad: ApexDeclaration) => ad.sourceHash == dependent.sourceHash
          case Right(_) => true
        }

      // Unmanaged
      case typeName =>
        isSummaryValid(typeName.withNamespace(pkg.namespace), dependent.sourceHash, summaries)
    }
  }

  private def isSummaryValid(typeName: TypeName, sourceHash: Int, summaries: Map[TypeName, SummaryDeclaration]): Boolean = {
    summaries.get(typeName).exists(_.sourceHash == sourceHash) ||
      (typeName.outer.nonEmpty && summaries.get(typeName.outer.get).exists(_.sourceHash == sourceHash))
  }
}

class SummaryParameter(parameterSummary: ParameterSummary, fromTypeName: String => TypeName)
  extends ParameterDeclaration {

  override val name: Name = Name(parameterSummary.name)
  override val typeName: TypeName = fromTypeName(parameterSummary.typeName)
}

class SummaryMethod(methodSummary: MethodSummary, fromTypeName: String => TypeName)
  extends MethodDeclaration with DependentValidation {

  override val name: Name = Name(methodSummary.name)
  override val modifiers: Seq[Modifier] = methodSummary.modifiers.map(Modifier(_))
  override val typeName: TypeName = fromTypeName(methodSummary.typeName)
  override val parameters: Seq[ParameterDeclaration] =
    methodSummary.parameters.map(new SummaryParameter(_, fromTypeName))

  def areDependentsValid(pkg: PackageDeclaration, summaries: Map[TypeName, SummaryDeclaration]): Boolean = {
    methodSummary.dependents.forall(d => isValid(pkg, d, summaries))
  }
}

class SummaryField(path: PathLike, fieldSummary: FieldSummary, fromTypeName: String => TypeName)
  extends ApexFieldLike  with DependentValidation {

  override val location: RangeLocation = RangeLocation(path, fieldSummary.range.get)
  override val name: Name = Name(fieldSummary.name)
  override val modifiers: Seq[Modifier] = fieldSummary.modifiers.map(Modifier(_))
  override val typeName: TypeName = fromTypeName(fieldSummary.typeName)
  override val readAccess: Modifier = Modifier(fieldSummary.readAccess)
  override val writeAccess: Modifier = Modifier(fieldSummary.writeAccess)

  def areDependentsValid(pkg: PackageDeclaration, summaries: Map[TypeName, SummaryDeclaration]): Boolean = {
    fieldSummary.dependents.forall(d => isValid(pkg, d, summaries))
  }
}

class SummaryConstructor(constructorSummary: ConstructorSummary, fromTypeName: String => TypeName)
  extends ConstructorDeclaration with DependentValidation {

  override val modifiers: Seq[Modifier] = constructorSummary.modifiers.map(Modifier(_))
  override val parameters: Seq[ParameterDeclaration] =
    constructorSummary.parameters.map(new SummaryParameter(_, fromTypeName))

  def areDependentsValid(pkg: PackageDeclaration, summaries: Map[TypeName, SummaryDeclaration]): Boolean = {
    constructorSummary.dependents.forall(d => isValid(pkg, d, summaries))
  }
}

class SummaryDeclaration(path: PathLike, val pkg: PackageDeclaration, val outerTypeName: Option[TypeName],
                         data: Array[Byte], typeSummary: Option[TypeSummary]=None)
  extends ApexDeclaration with DependentValidation {

  override lazy val summary: TypeSummary = typeSummary.getOrElse(readBinary[TypeSummary](data))

  override lazy val sourceHash: Int = summary.sourceHash
  override val idLocation: Location = RangeLocation(path, summary.idRange.get)
  override val packageDeclaration: Option[PackageDeclaration] = Some(pkg)

  override lazy val name: Name = Name(summary.name)
  override lazy val nature: Nature = Nature(summary.nature)
  override lazy val modifiers: Seq[Modifier] = summary.modifiers.map(Modifier(_))

  override lazy val superClass: Option[TypeName] = fromOptTypeName(summary.superClass)
  override lazy val interfaces: Seq[TypeName] = summary.interfaces.map(fromTypeName)
  override lazy val nestedTypes: Seq[SummaryDeclaration] = {
    summary.nestedTypes.map(nt => new SummaryDeclaration(path, pkg, Some(typeName), Array(), Some(nt)))
  }

  override lazy val blocks: Seq[BlockDeclaration] = Seq()
  override lazy val localFields: Seq[SummaryField] =
    summary.fields.map(new SummaryField(path, _, fromTypeName))
  override lazy val constructors: Seq[SummaryConstructor] =
    summary.constructors.map(new SummaryConstructor(_, fromTypeName))
  override lazy val localMethods: Seq[SummaryMethod] =
    summary.methods.map(new SummaryMethod(_, fromTypeName))

  def validate(): Unit = {}
  def collectDependencies(dependencies: mutable.Set[Dependent]): Unit = {}

  private def fromOptTypeName(value: String): Option[TypeName] = {
    if (value.isEmpty) None else Some(fromTypeName(value))
  }

  private def fromTypeName(value: String): TypeName = {
    val typeName = TypeName.fromString(value)
    if (typeName.maybeNamespace.exists(ns => pkg.namespaces.contains(ns))) {
      typeName
    } else {
      pkg.namespaceAsTypeName.map(typeName.withTail).getOrElse(typeName)
    }
  }

  def areDependentsValid(summaries: Map[TypeName, SummaryDeclaration]): Boolean = {
    summary.dependents.forall(d => isValid(pkg, d, summaries)) &&
      localFields.forall(f => f.areDependentsValid(pkg, summaries)) &&
      constructors.forall(c => c.areDependentsValid(pkg, summaries)) &&
      localMethods.forall(m => m.areDependentsValid(pkg, summaries)) &&
      nestedTypes.forall(t => t.areDependentsValid(summaries))
  }
}
