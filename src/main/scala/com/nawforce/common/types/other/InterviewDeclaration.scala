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

import com.nawforce.common.cst.VerifyContext
import com.nawforce.common.documents.LocationImpl
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.org.PackageImpl
import com.nawforce.common.org.stream.PackageStream
import com.nawforce.common.types._
import com.nawforce.common.types.platform.PlatformTypes

/** A individual custom interview being represented as interview derived type. */
final case class CustomInterview(pkg: PackageImpl, location: LocationImpl, interviewName: Name)
  extends InnerBasicTypeDeclaration(pkg, TypeName(interviewName, Nil, Some(TypeName.Interview))) {

  override val superClass: Option[TypeName] = Some(TypeName.Interview)
  override lazy val superClassDeclaration: Option[TypeDeclaration] = Some(PlatformTypes.interviewType)

  override def findMethod(name: Name, params: Seq[TypeName], staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Seq[MethodDeclaration] = {
    PlatformTypes.interviewType.findMethod(name, params, staticContext, verifyContext)
  }
}

/** Flow.Interview implementation. Provides access to interviews in the package as well as interviews that are
  * accessible in base packages via the Flow.Interview.namespace.name format. */
final class InterviewDeclaration(pkg: PackageImpl, nestedInterviews: Seq[TypeDeclaration])
  extends BasicTypeDeclaration(pkg, TypeName.Interview) {

  override def nestedTypes: Seq[TypeDeclaration] = nestedInterviews ++ namespaceDeclaration.toSeq

  override def findMethod(name: Name, params: Seq[TypeName], staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Seq[MethodDeclaration] = {
    PlatformTypes.interviewType.findMethod(name, params, staticContext, verifyContext)
  }

  // This is the optional Flow.Interview.namespace implementation
  private var namespaceDeclaration = pkg.namespace.map(_ => new NamespaceDeclaration())

  class NamespaceDeclaration(nestedInterviews: Seq[CustomInterview] = Seq()) extends InnerBasicTypeDeclaration(pkg,
    TypeName(pkg.namespace.get, Nil, Some(TypeName.Interview))) {
    override def nestedTypes: Seq[TypeDeclaration] = nestedInterviews

    def merge(stream: PackageStream): NamespaceDeclaration = {
      new NamespaceDeclaration(nestedInterviews ++ stream.flows.map(fe => CustomInterview(pkg, fe.location, fe.name)))
    }
  }

  /** Create new from merging those in the provided stream */
  def merge(stream: PackageStream): InterviewDeclaration = {
    val interviews = stream.flows.map(fe => CustomInterview(pkg, fe.location, fe.name))
    val interviewDeclaration = new InterviewDeclaration(pkg, nestedInterviews ++ interviews)
    interviewDeclaration.namespaceDeclaration.foreach(td =>
      interviewDeclaration.namespaceDeclaration = Some(td.merge(stream)))
    interviewDeclaration
  }
}

/** Flow.Interview.ns implementation for exposing interviews from dependent packages. As the exposed interviews are
  * owned elsewhere (by the passed LabelDeclaration) there is no need to set a controller here.
  */
final class PackageInterviews(pkg: PackageImpl, interviewDeclaration: InterviewDeclaration)
  extends InnerBasicTypeDeclaration(pkg,
    TypeName(interviewDeclaration.packageDeclaration.get.namespace.get, Nil, Some(TypeName.Interview))) {

  override def nestedTypes: Seq[TypeDeclaration] = interviewDeclaration.nestedTypes
}

object InterviewDeclaration {
  def apply(pkg: PackageImpl): InterviewDeclaration = {
    new InterviewDeclaration(pkg, collectBaseInterviews(pkg))
  }

  private def collectBaseInterviews(pkg: PackageImpl): Seq[PackageInterviews] = {
    // TODO: We should support ghosted packages here but that would require the ability to on-demand created
    // nested types which is not currently handled by TypeDeclaration
    pkg.transitiveBasePackages.toSeq.map(basePkg => new PackageInterviews(pkg, basePkg.interviews))
  }
}


