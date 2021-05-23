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
package com.nawforce.common.types.other

import com.nawforce.common.names.TypeNames
import com.nawforce.common.org.Module
import com.nawforce.common.types.core._
import com.nawforce.pkgforce.diagnostics.{Diagnostic, Issue, PathLocation, UNUSED_CATEGORY}
import com.nawforce.pkgforce.documents._
import com.nawforce.pkgforce.modifiers.{GLOBAL_MODIFIER, Modifier, PRIVATE_MODIFIER, STATIC_MODIFIER}
import com.nawforce.pkgforce.names.{Name, TypeName}
import com.nawforce.pkgforce.path.{PathFactory, PathLike}
import com.nawforce.pkgforce.stream.PackageStream

import scala.collection.mutable
import scala.util.hashing.MurmurHash3

/** A individual Label being represented as a static field. */
case class Label(outerTypeId: Option[TypeId],
                 location: Option[PathLocation],
                 name: Name,
                 isProtected: Boolean)
    extends FieldDeclaration {
  override lazy val modifiers: Array[Modifier] = Label.modifiers
  override lazy val typeName: TypeName = TypeNames.String
  override lazy val readAccess: Modifier = GLOBAL_MODIFIER
  override lazy val writeAccess: Modifier = PRIVATE_MODIFIER
  override val idTarget: Option[TypeName] = None
}

object Label {
  val modifiers: Array[Modifier] = Array(STATIC_MODIFIER, GLOBAL_MODIFIER)
}

/** System.Label implementation. Provides access to labels in the package as well as labels that are accessible in
  * base packages via the Label.namespace.name format. */
final class LabelDeclaration(sources: Array[SourceInfo],
                             override val module: Module,
                             labels: Array[Label],
                             nestedLabels: Array[NestedLabels])
    extends BasicTypeDeclaration(sources.map(s => PathFactory(s.path)), module, TypeNames.Label)
    with DependentType
    with OtherTypeDeclaration {

  val sourceHash: Int = MurmurHash3.unorderedHash(sources.map(_.hash), 0)

  // Propagate dependencies to nested
  nestedLabels.foreach(_.addTypeDependencyHolder(typeId))

  override val nestedTypes: Array[TypeDeclaration] =
    nestedLabels.asInstanceOf[Array[TypeDeclaration]]
  override val fields: Array[FieldDeclaration] = labels.asInstanceOf[Array[FieldDeclaration]]

  /** Create new labels from merging those in the provided stream */
  def merge(stream: PackageStream): LabelDeclaration = {
    val outerTypeId = TypeId(module, typeName)
    val newLabels = labels ++ stream.labels.map(le =>
      Label(Some(outerTypeId), Some(le.location), le.name, le.isProtected))
    val sourceInfo = stream.labelsFiles.map(_.sourceInfo).distinct.toArray
    new LabelDeclaration(sourceInfo, module, newLabels, nestedLabels)
  }

  override def collectDependenciesByTypeName(dependsOn: mutable.Set[TypeId]): Unit = {
    // Labels depend on labels from dependent packages
    nestedLabels.foreach(nl => nl.labelTypeId.foreach(dependsOn.add))
  }

  /** Report on unused labels */
  def unused(): Array[Issue] = {
    labels
      .filterNot(_.hasHolders)
      .filterNot(_.location.isEmpty)
      .map(
        label =>
          new Issue(label.location.get.path,
                    Diagnostic(UNUSED_CATEGORY,
                               label.location.get.location,
                               s"Label '$typeName.${label.name}'")))
  }
}

/** Access to label type id for dependent packages label wrappers */
trait NestedLabels extends TypeDeclaration {
  val labelTypeId: Option[TypeId]

  def addTypeDependencyHolder(typeId: TypeId): Unit
}

/** System.Label.ns implementation for exposing labels from dependent packages. Only public labels are visible through
  * this. As the exposed labels are owned elsewhere (by the passed LabelDeclaration) there is no need to set a
  * controller here.
  */
private final class PackageLabels(module: Module, labelDeclaration: LabelDeclaration)
    extends InnerBasicTypeDeclaration(
      PathLike.emptyPaths,
      module,
      TypeName(labelDeclaration.module.namespace.get, Nil, Some(TypeNames.Label)))
    with NestedLabels {

  override val labelTypeId: Option[TypeId] = Some(labelDeclaration.typeId)

  override def addTypeDependencyHolder(typeId: TypeId): Unit = {
    labelDeclaration.addTypeDependencyHolder(typeId)
  }

  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    labelDeclaration.findField(name, staticContext) match {
      case Some(label: Label) if !label.isProtected => Some(label)
      case _                                        => None
    }
  }
}

/** System.Label.ns implementation for ghosted packages. This simulates the existence of any label you ask for. */
final class GhostedLabels(module: Module, ghostedNamespace: Name)
    extends InnerBasicTypeDeclaration(PathLike.emptyPaths,
                                      module,
                                      TypeName(ghostedNamespace, Nil, Some(TypeNames.Label)))
    with NestedLabels {

  override val labelTypeId: Option[TypeId] = None

  override def addTypeDependencyHolder(typeId: TypeId): Unit = {}

  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    if (staticContext.contains(true)) {
      Some(Label(None, None, name, isProtected = false))
    } else {
      None
    }
  }
}

object LabelDeclaration {

  /** Construct System.Label for a package. */
  def apply(module: Module): LabelDeclaration = {
    new LabelDeclaration(Array(), module, Array(), createPackageLabels(module))
  }

  // Create labels declarations for each base package
  private def createPackageLabels(module: Module): Array[NestedLabels] = {
    module.basePackages
      .map(basePkg => {
        if (basePkg.isGhosted) {
          new GhostedLabels(module, basePkg.namespace.get)
        } else {
          new PackageLabels(module, basePkg.labels.get)
        }
      })
      .toArray
  }
}
