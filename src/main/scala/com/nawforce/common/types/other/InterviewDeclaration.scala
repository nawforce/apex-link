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

import com.nawforce.common.api.{Name, TypeName}
import com.nawforce.common.cst.VerifyContext
import com.nawforce.common.documents.LocationImpl
import com.nawforce.common.names.TypeNames
import com.nawforce.common.org.PackageImpl
import com.nawforce.common.org.stream.PackageStream
import com.nawforce.common.path.PathFactory
import com.nawforce.common.types.core._
import com.nawforce.common.types.platform.PlatformTypes

import scala.collection.mutable

/** A individual custom interview being represented as interview derived type. */
final case class Interview(pkg: PackageImpl, location: Option[LocationImpl], interviewName: Name)
  extends InnerBasicTypeDeclaration(location.map(l => PathFactory(l.path)).toSeq,
    pkg, TypeName(interviewName, Nil, Some(TypeNames.Interview))) {

  override val superClass: Option[TypeName] = Some(TypeNames.Interview)
  override lazy val superClassDeclaration: Option[TypeDeclaration] = Some(PlatformTypes.interviewType)

  override def findMethod(name: Name, params: Seq[TypeName], staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Seq[MethodDeclaration] = {
    PlatformTypes.interviewType.findMethod(name, params, staticContext, verifyContext)
  }
}

/** Flow.Interview implementation. Provides access to interviews in the package as well as interviews that are
  * accessible in base packages via the Flow.Interview.namespace.name format. */
final class InterviewDeclaration(override val pkg: PackageImpl, interviews: Seq[TypeDeclaration],
                                 nestedInterviews: Seq[NestedInterviews])
  extends BasicTypeDeclaration(Seq(), pkg, TypeNames.Interview) with DependentType {

  // Propagate dependencies to nested
  nestedInterviews.foreach(_.addTypeDependencyHolder(typeId))

  override def nestedTypes: Seq[TypeDeclaration] = interviews ++ nestedInterviews ++ namespaceDeclaration.toSeq

  override def findMethod(name: Name, params: Seq[TypeName], staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Seq[MethodDeclaration] = {
    PlatformTypes.interviewType.findMethod(name, params, staticContext, verifyContext)
  }

  override def collectDependenciesByTypeName(dependsOn: mutable.Set[TypeId]): Unit = {
    nestedInterviews.foreach(ni => ni.interviewTypeId.foreach(dependsOn.add))
  }

  // This is the optional Flow.Interview.namespace implementation
  private var namespaceDeclaration = pkg.namespace.map(_ => new NamespaceDeclaration())

  class NamespaceDeclaration(nestedInterviews: Seq[Interview] = Seq())
    extends InnerBasicTypeDeclaration(Seq(), pkg, TypeName(pkg.namespace.get, Nil, Some(TypeNames.Interview))) {
    override def nestedTypes: Seq[TypeDeclaration] = nestedInterviews

    def merge(stream: PackageStream): NamespaceDeclaration = {
      new NamespaceDeclaration(nestedInterviews ++ stream.flows.map(fe => Interview(pkg, Some(fe.location), fe.name)))
    }
  }

  /** Create new from merging those in the provided stream */
  def merge(stream: PackageStream): InterviewDeclaration = {
    val newInterviews = interviews ++ stream.flows.map(fe => Interview(pkg, Some(fe.location), fe.name))
    val interviewDeclaration = new InterviewDeclaration(pkg, newInterviews, nestedInterviews)
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
final class PackageInterviews(pkg: PackageImpl, interviewDeclaration: InterviewDeclaration)
  extends InnerBasicTypeDeclaration(Seq.empty, pkg,
    TypeName(interviewDeclaration.packageDeclaration.get.namespace.get, Nil, Some(TypeNames.Interview)))
  with NestedInterviews {

  override val interviewTypeId: Option[TypeId] = Some(interviewDeclaration.typeId)

  override def addTypeDependencyHolder(typeId: TypeId): Unit = {
    interviewDeclaration.addTypeDependencyHolder(typeId)
  }

  override val nestedTypes: Seq[TypeDeclaration] = interviewDeclaration.nestedTypes
}

/** Flow.Interview.ns implementation for ghosted packages. This simulates the existence of any flow you ask for. */
final class GhostedInterviews(pkg: PackageImpl, ghostedPackage: PackageImpl)
  extends InnerBasicTypeDeclaration(Seq.empty, pkg,
    TypeName(ghostedPackage.namespace.get, Nil, Some(TypeNames.Interview)))
  with NestedInterviews {

  override val interviewTypeId: Option[TypeId] = None

  override def addTypeDependencyHolder(typeId: TypeId): Unit = {}

  override def findNestedType(name: Name): Option[TypeDeclaration] = {
    Some(Interview(ghostedPackage, None, name))
  }
}

object InterviewDeclaration {
  def apply(pkg: PackageImpl): InterviewDeclaration = {
    new InterviewDeclaration(pkg, Seq.empty, collectBaseInterviews(pkg))
  }

  private def collectBaseInterviews(pkg: PackageImpl): Seq[NestedInterviews] = {
    pkg.transitiveBasePackages.map(basePkg => {
      if (basePkg.isGhosted) {
        new GhostedInterviews(pkg, basePkg)
      } else {
        new PackageInterviews(pkg, basePkg.interviews)
      }
    }).toSeq
  }
}


