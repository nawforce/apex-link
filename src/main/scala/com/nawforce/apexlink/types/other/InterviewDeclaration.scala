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
package com.nawforce.apexlink.types.other

import com.nawforce.apexlink.cst.VerifyContext
import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.org.{Module, PackageImpl}
import com.nawforce.apexlink.types.core._
import com.nawforce.apexlink.types.platform.PlatformTypes
import com.nawforce.pkgforce.documents.{MetadataDocument, SourceInfo}
import com.nawforce.pkgforce.names.{Name, TypeName}
import com.nawforce.pkgforce.path.{PathFactory, PathLike}
import com.nawforce.pkgforce.stream.{FlowEvent, PackageStream}

import scala.collection.mutable
import scala.util.hashing.MurmurHash3

/** A individual custom interview being represented as interview derived type. */
final case class Interview(module: Module, path: Option[PathLike], interviewName: Name)
    extends InnerBasicTypeDeclaration(path.toArray,
                                      module,
                                      TypeName(interviewName, Nil, Some(TypeNames.Interview))) {

  override val superClass: Option[TypeName] = Some(TypeNames.Interview)
  override lazy val superClassDeclaration: Option[TypeDeclaration] = Some(
    PlatformTypes.interviewType)

  override def findMethod(name: Name,
                          params: Array[TypeName],
                          staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Array[MethodDeclaration] = {
    PlatformTypes.interviewType.findMethod(name, params, staticContext, verifyContext)
  }
}

object Interview {
  def apply(module: Module, event: FlowEvent): Interview = {
    val path = PathFactory(event.sourceInfo.path)
    val document = MetadataDocument(path)
    new Interview(module, Some(path), document.get.name)
  }
}

/** Flow.Interview implementation. Provides access to interviews in the package as well as interviews that are
  * accessible in base packages via the Flow.Interview.namespace.name format. */
final class InterviewDeclaration(sources: Seq[SourceInfo],
                                 override val module: Module,
                                 interviews: Seq[TypeDeclaration],
                                 nestedInterviews: Seq[NestedInterviews])
    extends BasicTypeDeclaration(PathLike.emptyPaths, module, TypeNames.Interview)
    with DependentType
    with OtherTypeDeclaration {

  val sourceHash: Int = MurmurHash3.unorderedHash(sources.map(_.hash), 0)

  // Propagate dependencies to nested
  nestedInterviews.foreach(_.addTypeDependencyHolder(typeId))

  override def nestedTypes: Array[TypeDeclaration] =
    (interviews ++ nestedInterviews ++ namespaceDeclaration).toArray

  override def findMethod(name: Name,
                          params: Array[TypeName],
                          staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Array[MethodDeclaration] = {
    PlatformTypes.interviewType.findMethod(name, params, staticContext, verifyContext)
  }

  override def collectDependenciesByTypeName(dependsOn: mutable.Set[TypeId]): Unit = {
    nestedInterviews.foreach(ni => ni.interviewTypeId.foreach(dependsOn.add))
  }

  // This is the optional Flow.Interview.namespace implementation
  private var namespaceDeclaration = module.namespace.map(_ => new NamespaceDeclaration())

  class NamespaceDeclaration(
    nestedInterviews: Array[TypeDeclaration] = TypeDeclaration.emptyTypeDeclarations)
      extends InnerBasicTypeDeclaration(
        PathLike.emptyPaths,
        module,
        TypeName(module.namespace.get, Nil, Some(TypeNames.Interview))) {
    override def nestedTypes: Array[TypeDeclaration] = nestedInterviews

    def merge(stream: PackageStream): NamespaceDeclaration = {
      new NamespaceDeclaration(nestedInterviews ++ stream.flows.map(fe => Interview(module, fe)))
    }
  }

  /** Create new from merging those in the provided stream */
  def merge(stream: PackageStream): InterviewDeclaration = {
    val newInterviews = interviews ++ stream.flows.map(fe => Interview(module, fe))
    val sourceInfo = stream.flows.map(_.sourceInfo).distinct
    val interviewDeclaration =
      new InterviewDeclaration(sourceInfo, module, newInterviews, nestedInterviews)
    interviewDeclaration.namespaceDeclaration.foreach(td =>
      interviewDeclaration.namespaceDeclaration = Some(td.merge(stream)))
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
      TypeName(interviewDeclaration.module.pkg.namespace.get, Nil, Some(TypeNames.Interview)))
    with NestedInterviews {

  override val interviewTypeId: Option[TypeId] = Some(interviewDeclaration.typeId)

  override def addTypeDependencyHolder(typeId: TypeId): Unit = {
    interviewDeclaration.addTypeDependencyHolder(typeId)
  }

  override val nestedTypes: Array[TypeDeclaration] = interviewDeclaration.nestedTypes
}

/** Flow.Interview.ns implementation for ghosted packages. This simulates the existence of any flow you ask for. */
final class GhostedInterviews(module: Module, ghostedPackage: PackageImpl)
    extends InnerBasicTypeDeclaration(
      PathLike.emptyPaths,
      module,
      TypeName(ghostedPackage.namespace.get, Nil, Some(TypeNames.Interview)))
    with NestedInterviews {

  override val interviewTypeId: Option[TypeId] = None

  override def addTypeDependencyHolder(typeId: TypeId): Unit = {}

  override def findNestedType(name: Name): Option[TypeDeclaration] = {
    Some(Interview(module, None, name))
  }
}

object InterviewDeclaration {
  def apply(module: Module): InterviewDeclaration = {
    new InterviewDeclaration(Seq(), module, Seq.empty, collectBaseInterviews(module))
  }

  private def collectBaseInterviews(module: Module): Seq[NestedInterviews] = {
    module.basePackages
      .map(basePkg => {
        if (basePkg.isGhosted) {
          new GhostedInterviews(module, basePkg)
        } else {
          new PackageInterviews(module, basePkg.interviews.get)
        }
      })
  }
}
