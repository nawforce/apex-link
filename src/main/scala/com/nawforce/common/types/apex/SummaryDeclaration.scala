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
import com.nawforce.common.metadata.Dependent
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.path.PathLike
import com.nawforce.common.types._
import upickle.default._

import scala.collection.mutable

class SummaryParameter(pkg: PackageDeclaration, outerTypeName: TypeName, parameterSummary: ParameterSummary)
  extends ParameterDeclaration {
  override lazy val name: Name = Name(parameterSummary.name)
  override lazy val typeName: TypeName = TypeName.fromString(parameterSummary.typeName).get
}

class SummaryMethod(pkg: PackageDeclaration, outerTypeName: TypeName, methodSummary: MethodSummary)
  extends MethodDeclaration {
  override lazy val name: Name = Name(methodSummary.name)
  override lazy val modifiers: Seq[Modifier] = methodSummary.modifiers.map(Modifier(_))
  override lazy val typeName: TypeName = TypeName.fromString(methodSummary.typeName).get
  override lazy val parameters: Seq[ParameterDeclaration] =
    methodSummary.parameters.map(new SummaryParameter(pkg, outerTypeName, _))

  def areDependentsValid(others: Map[Name, SummaryDeclaration]): Boolean = {
    !methodSummary.dependents.exists(dep => {
      val other = others.get(Name(dep.name))
      other.isEmpty || dep.sourceHash != other.get.sourceHash
    })
  }
}

class SummaryField(path: PathLike, fieldSummary: FieldSummary) extends ApexFieldLike {
  override lazy val location: RangeLocation = RangeLocation(path, fieldSummary.range.get)
  override lazy val name: Name = Name(fieldSummary.name)
  override lazy val modifiers: Seq[Modifier] = fieldSummary.modifiers.map(Modifier(_))
  override lazy val typeName: TypeName = TypeName.fromString(fieldSummary.typeName).get
  override lazy val readAccess: Modifier = Modifier(fieldSummary.readAccess)
  override lazy val writeAccess: Modifier = Modifier(fieldSummary.writeAccess)

  def areDependentsValid(others: Map[Name, SummaryDeclaration]): Boolean = {
    !fieldSummary.dependents.exists(dep => {
      val other = others.get(Name(dep.name))
      other.isEmpty || dep.sourceHash != other.get.sourceHash
    })
  }
}

class SummaryConstructor(pkg: PackageDeclaration, outerTypeName: TypeName, constructorSummary: ConstructorSummary)
  extends ConstructorDeclaration {
  override lazy val modifiers: Seq[Modifier] = constructorSummary.modifiers.map(Modifier(_))
  override lazy val parameters: Seq[ParameterDeclaration] =
    constructorSummary.parameters.map(new SummaryParameter(pkg, outerTypeName, _))

  def areDependentsValid(others: Map[Name, SummaryDeclaration]): Boolean = {
    !constructorSummary.dependents.exists(dep => {
      val other = others.get(Name(dep.name))
      other.isEmpty || dep.sourceHash != other.get.sourceHash
    })
  }
}

class SummaryDeclaration(path: PathLike, val pkg: PackageDeclaration, val outerTypeName: Option[TypeName],
                         data: Array[Byte], typeSummary: Option[TypeSummary]=None) extends ApexDeclaration {

  override lazy val summary: TypeSummary = typeSummary.getOrElse(readBinary[TypeSummary](data))

  override lazy val sourceHash: Int = summary.sourceHash
  override val idLocation: Location = RangeLocation(path, summary.idRange.get)
  override val packageDeclaration: Option[PackageDeclaration] = Some(pkg)

  override lazy val name: Name = Name(summary.name)
  override lazy val typeName: TypeName = TypeName.fromString(summary.typeName).get
  override lazy val nature: Nature = Nature(summary.nature)
  override lazy val modifiers: Seq[Modifier] = summary.modifiers.map(Modifier(_))

  override lazy val superClass: Option[TypeName] = TypeName.fromString(summary.superClass)
  override lazy val interfaces: Seq[TypeName] = summary.interfaces.map(TypeName.fromString(_).get)
  override def nestedTypes: Seq[SummaryDeclaration] = {
    summary.nestedTypes.map(nt => new SummaryDeclaration(path, pkg, Some(typeName), Array(), Some(nt)))
  }

  override lazy val blocks: Seq[BlockDeclaration] = Seq()
  override lazy val localFields: Seq[SummaryField] =
    summary.fields.map(new SummaryField(path, _))
  override lazy val constructors: Seq[SummaryConstructor] =
    summary.constructors.map(new SummaryConstructor(pkg, typeName, _))
  override lazy val localMethods: Seq[SummaryMethod] =
    summary.methods.map(new SummaryMethod(pkg, typeName, _))

  def validate(): Unit = {}
  def collectDependencies(dependencies: mutable.Set[Dependent]): Unit = {}

  def areDependentsValid(others: Map[Name, SummaryDeclaration]): Boolean = {
    if (summary.dependents.exists(dep => {
      val other = others.get(Name(dep.name.split('.').head))
      other.isEmpty || dep.sourceHash != other.get.sourceHash
    }))
      return false

    if (localFields.exists(f => !f.areDependentsValid(others)))
      return false

    if (constructors.exists(m => !m.areDependentsValid(others)))
      return false

    if (localMethods.exists(m => !m.areDependentsValid(others)))
      return false

    if (nestedTypes.exists(nt => !nt.areDependentsValid(others)))
      return false

    true
  }
}
