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

import com.nawforce.apexlink.cst.VerifyContext
import com.nawforce.apexlink.finding.TypeResolver.TypeCache
import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.org.{Module, PackageImpl}
import com.nawforce.apexlink.types.core._
import com.nawforce.apexlink.types.platform.PlatformTypes
import com.nawforce.pkgforce.documents.{MetadataDocument, SourceInfo}
import com.nawforce.pkgforce.names.{Name, TypeName}
import com.nawforce.pkgforce.path.{PathLike, PathLocation, UnsafeLocatable}
import com.nawforce.pkgforce.stream.{FlowEvent, PackageStream}

import scala.collection.immutable.ArraySeq
import scala.collection.mutable
import scala.util.hashing.MurmurHash3

/** A individual custom interview being represented as interview derived type. */
final case class Interview(module: Module, location: PathLocation, interviewName: Name)
    extends InnerBasicTypeDeclaration(
      ArraySeq.unsafeWrapArray(Option(location).map(_.path).toArray),
      module,
      TypeName(interviewName, Nil, Some(TypeNames.Interview))
    )
    with UnsafeLocatable
    with Dependent {

  override val superClass: Option[TypeName] = Some(TypeNames.Interview)
  override lazy val superClassDeclaration: Option[TypeDeclaration] = Some(
    PlatformTypes.interviewType
  )

  override def findMethod(
    name: Name,
    params: ArraySeq[TypeName],
    staticContext: Option[Boolean],
    verifyContext: VerifyContext
  ): Either[String, MethodDeclaration] = {
    PlatformTypes.interviewType.findMethod(name, params, staticContext, verifyContext)
  }
}

object Interview {
  def apply(module: Module, event: FlowEvent): Interview = {
    val location = event.sourceInfo.location
    val document = MetadataDocument(location.path)
    new Interview(module, location, document.get.name)
  }
}

/** Flow.Interview implementation. Provides access to interviews in the package as well as interviews that are
  * accessible in base packages via the Flow.Interview.namespace.name format.
  */
final class InterviewDeclaration(
  sources: ArraySeq[SourceInfo],
  override val module: Module,
  interviews: Seq[TypeDeclaration],
  nestedInterviews: Seq[NestedInterviews]
) extends BasicTypeDeclaration(PathLike.emptyPaths, module, TypeNames.Interview)
    with DependentType {

  val sourceHash: Int = MurmurHash3.unorderedHash(sources.map(_.hash), 0)

  // Propagate dependencies to nested
  nestedInterviews.foreach(_.addTypeDependencyHolder(typeId))

  override def nestedTypes: ArraySeq[TypeDeclaration] = {
    val nested = interviews ++ nestedInterviews ++ namespaceDeclaration
    ArraySeq.unsafeWrapArray(nested.toArray)
  }

  override def findMethod(
    name: Name,
    params: ArraySeq[TypeName],
    staticContext: Option[Boolean],
    verifyContext: VerifyContext
  ): Either[String, MethodDeclaration] = {
    PlatformTypes.interviewType.findMethod(name, params, staticContext, verifyContext)
  }

  override def gatherDependencies(
    dependsOn: mutable.Set[TypeId],
    apexOnly: Boolean,
    outerTypesOnly: Boolean,
    typeCache: TypeCache
  ): Unit = {
    if (!apexOnly)
      nestedInterviews.foreach(ni => ni.interviewTypeId.foreach(dependsOn.add))
  }

  // This is the optional Flow.Interview.namespace implementation
  private var namespaceDeclaration = module.namespace.map(_ => new NamespaceDeclaration())

  class NamespaceDeclaration(
    nestedInterviews: ArraySeq[TypeDeclaration] = TypeDeclaration.emptyTypeDeclarations
  ) extends InnerBasicTypeDeclaration(
        PathLike.emptyPaths,
        module,
        TypeName(module.namespace.get, Nil, Some(TypeNames.Interview))
      ) {
    override def nestedTypes: ArraySeq[TypeDeclaration] = nestedInterviews

    def merge(events: ArraySeq[FlowEvent]): NamespaceDeclaration = {
      new NamespaceDeclaration(nestedInterviews ++ events.map(fe => Interview(module, fe)))
    }
  }

  /** Create new from merging those in the provided stream */
  def merge(stream: PackageStream): InterviewDeclaration = {
    merge(stream.flows)
  }

  def merge(events: ArraySeq[FlowEvent]): InterviewDeclaration = {
    val newInterviews = interviews ++ events.map(fe => Interview(module, fe))
    val sourceInfo    = events.map(_.sourceInfo).distinct
    val interviewDeclaration =
      new InterviewDeclaration(sourceInfo, module, newInterviews, nestedInterviews)
    interviewDeclaration.namespaceDeclaration.foreach(
      td => interviewDeclaration.namespaceDeclaration = Some(td.merge(events))
    )
    interviewDeclaration
  }

}

trait NestedInterviews extends TypeDeclaration {
  val interviewTypeId: Option[TypeId]

  def addTypeDependencyHolder(typeId: TypeId): Unit
}

/** Flow.Interview.ns implementation for exposing interviews from dependent packages. */
final class PackageInterviews(module: Module, interviewDeclaration: InterviewDeclaration)
    extends InnerBasicTypeDeclaration(
      PathLike.emptyPaths,
      module,
      TypeName(interviewDeclaration.module.pkg.namespace.get, Nil, Some(TypeNames.Interview))
    )
    with NestedInterviews {

  override val interviewTypeId: Option[TypeId] = Some(interviewDeclaration.typeId)

  override def addTypeDependencyHolder(typeId: TypeId): Unit = {
    interviewDeclaration.addTypeDependencyHolder(typeId)
  }

  override val nestedTypes: ArraySeq[TypeDeclaration] = interviewDeclaration.nestedTypes
}

/** Flow.Interview.ns implementation for ghosted packages. This simulates the existence of any flow you ask for. */
final class GhostedInterviews(module: Module, ghostedPackage: PackageImpl)
    extends InnerBasicTypeDeclaration(
      PathLike.emptyPaths,
      module,
      TypeName(ghostedPackage.namespace.get, Nil, Some(TypeNames.Interview))
    )
    with NestedInterviews {

  override val interviewTypeId: Option[TypeId] = None

  override def addTypeDependencyHolder(typeId: TypeId): Unit = {}

  override def findNestedType(name: Name): Option[TypeDeclaration] = {
    Some(Interview(module, null, name))
  }
}

object InterviewDeclaration {
  def apply(module: Module): InterviewDeclaration = {
    new InterviewDeclaration(ArraySeq(), module, Seq.empty, collectBaseInterviews(module))
  }

  private def collectBaseInterviews(module: Module): Seq[NestedInterviews] = {
    module.basePackages
      .map(basePkg => {
        basePkg.orderedModules.headOption
          .map(m => new PackageInterviews(module, m.interviews))
          .getOrElse(new GhostedInterviews(module, basePkg))
      })
  }
}
