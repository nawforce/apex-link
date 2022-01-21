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

package com.nawforce.apexlink.types.other

import com.nawforce.apexlink.finding.TypeResolver.TypeCache
import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.names.XNames.NameUtils
import com.nawforce.apexlink.org.Module
import com.nawforce.apexlink.types.core._
import com.nawforce.pkgforce.diagnostics.{Diagnostic, Issue, UNUSED_CATEGORY}
import com.nawforce.pkgforce.documents._
import com.nawforce.pkgforce.modifiers.{GLOBAL_MODIFIER, Modifier, PRIVATE_MODIFIER, STATIC_MODIFIER}
import com.nawforce.pkgforce.names.{Name, TypeName}
import com.nawforce.pkgforce.path.{Locatable, PathLike, PathLocation}
import com.nawforce.pkgforce.stream.{LabelEvent, LabelFileEvent, PackageStream}

import scala.collection.immutable.ArraySeq
import scala.collection.mutable
import scala.util.hashing.MurmurHash3

abstract class LabelField extends FieldDeclaration {
  override lazy val modifiers: ArraySeq[Modifier] = LabelField.modifiers
  override lazy val typeName: TypeName            = TypeNames.String
  override lazy val readAccess: Modifier          = GLOBAL_MODIFIER
  override lazy val writeAccess: Modifier         = PRIVATE_MODIFIER
  override val idTarget: Option[TypeName]         = None
}

object LabelField {
  val modifiers: ArraySeq[Modifier] = ArraySeq(STATIC_MODIFIER, GLOBAL_MODIFIER)
}

/** A individual Label being represented as a static field. */
final case class Label(
  outerTypeId: TypeId,
  override val location: PathLocation,
  name: Name,
  isProtected: Boolean
) extends LabelField
    with Locatable
    with Dependent

final case class GhostLabel(name: Name) extends LabelField {
  override def location: PathLocation = null
}

/** System.Label implementation. Provides access to labels in the package as well as labels that are accessible in
  * base packages via the Label.namespace.name format.
  */
final class LabelDeclaration(
  override val module: Module,
  val sources: ArraySeq[SourceInfo],
  val labels: ArraySeq[Label],
  val nestedLabels: ArraySeq[NestedLabels]
) extends BasicTypeDeclaration(sources.map(s => s.location.path), module, TypeNames.Label)
    with DependentType
    with Dependent {

  val sourceHash: Int = MurmurHash3.unorderedHash(sources.map(_.hash), 0)

  // Propagate dependencies to nested
  nestedLabels.foreach(_.addTypeDependencyHolder(typeId))

  override val nestedTypes: ArraySeq[TypeDeclaration] = nestedLabels
  override val fields: ArraySeq[FieldDeclaration]     = labels

  /** Create new labels from merging those in the provided stream */
  def merge(stream: PackageStream): LabelDeclaration = {
    merge(stream.labelsFiles, stream.labels)
  }

  def merge(
    labelFileEvents: ArraySeq[LabelFileEvent],
    labelEvents: ArraySeq[LabelEvent]
  ): LabelDeclaration = {
    val namespacePrefix = module.namespace.map(ns => s"${ns}__").getOrElse("").toLowerCase

    def stripNamespacePrefix(name: Name): Name = {
      if (namespacePrefix.nonEmpty && name.startsWithIgnoreCase(namespacePrefix))
        name.substring(namespacePrefix.length)
      else
        name
    }

    val outerTypeId = TypeId(module, typeName)
    val newLabels = labels ++ labelEvents.map(
      le => Label(outerTypeId, le.location, stripNamespacePrefix(le.name), le.isProtected)
    )
    val sourceInfo = (sources ++ labelFileEvents.map(_.sourceInfo)).distinct
    new LabelDeclaration(module, sourceInfo, newLabels, nestedLabels)
  }

  override def gatherDependencies(
                                   dependsOn: mutable.Set[TypeId],
                                   apexOnly: Boolean,
                                   outerTypesOnly: Boolean,
                                   typeCache: TypeCache
                                 ): Unit = {
    // Labels depend on labels from dependent packages
    if (!apexOnly)
      nestedLabels.foreach(nl => nl.labelTypeId.foreach(dependsOn.add))
  }

  /** Report on unused labels */
  def unused(): ArraySeq[Issue] = {
    labels
      .filterNot(_.hasHolders)
      .map(
        label =>
          new Issue(
            label.location.path,
            Diagnostic(UNUSED_CATEGORY, label.location.location, s"Label '$typeName.${label.name}'")
          )
      )
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
      TypeName(labelDeclaration.module.namespace.get, Nil, Some(TypeNames.Label))
    )
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
    extends InnerBasicTypeDeclaration(
      PathLike.emptyPaths,
      module,
      TypeName(ghostedNamespace, Nil, Some(TypeNames.Label))
    )
    with NestedLabels {

  override val labelTypeId: Option[TypeId] = None

  override def addTypeDependencyHolder(typeId: TypeId): Unit = {}

  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    if (staticContext.contains(true)) {
      Some(GhostLabel(name))
    } else {
      None
    }
  }
}

object LabelDeclaration {

  /** Construct System.Label for a module from any base info. */
  def apply(module: Module): LabelDeclaration = {
    module.baseModules.headOption
      .map(_.labels)
      .map(base => {
        val newLabel = new LabelDeclaration(module, base.sources, base.labels, base.nestedLabels)
        base.addTypeDependencyHolder(newLabel.typeId)
        newLabel
      })
      .getOrElse(new LabelDeclaration(module, ArraySeq(), ArraySeq(), createPackageLabels(module)))
  }

  // Create labels declarations for each base package
  private def createPackageLabels(module: Module): ArraySeq[NestedLabels] = {
    ArraySeq.unsafeWrapArray(
      module.basePackages
        .map(basePkg => {
          if (basePkg.orderedModules.isEmpty) {
            new GhostedLabels(module, basePkg.namespace.get)
          } else {
            new PackageLabels(module, basePkg.orderedModules.head.labels)
          }
        })
        .toArray
    )
  }
}
