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
import com.nawforce.common.documents.FlowDocument
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.org.PackageImpl
import com.nawforce.common.path.PathLike
import com.nawforce.common.types._
import com.nawforce.common.types.platform.PlatformTypes

import scala.collection.mutable

/** Flow.Interview intercept */
final class InterviewDeclaration(pkg: PackageImpl)
  extends NamedTypeDeclaration(pkg, TypeName.Interview) {

  // Map of interviews and namespace wrappers of interviews
  private val interviews = mutable.Map[Name, TypeDeclaration]()
  override def nestedTypes: Seq[TypeDeclaration] = interviews.values.toSeq

  override def findMethod(name: Name, params: Seq[TypeName], staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Seq[MethodDeclaration] = {
    PlatformTypes.interviewType.findMethod(name, params, staticContext, verifyContext)
  }

  def upsert(namespace: Option[Name], flow: FlowDocument): Unit = {
    interviews.put(flow.name, new CustomInterview(pkg, TypeName(flow.name, Nil, Some(TypeName.Interview)), flow.path))
    if (namespace.nonEmpty) {
      val typeName = TypeName(flow.name, Nil, Some(TypeName(namespace.get, Nil, Some(TypeName.Interview))))
      getNamespaceContainer(namespace.get).upsert(new CustomInterview(pkg, typeName, flow.path))
    }
  }

  private def getNamespaceContainer(namespace: Name): InterviewNamespace = {
    interviews.getOrElseUpdate(namespace, {
      new InterviewNamespace(pkg, TypeName(namespace, Nil, Some(TypeName.Interview)))
    }).asInstanceOf[InterviewNamespace]
  }
}

/** The type for an a custom interview, the only kind */
final class CustomInterview(_pkg: PackageImpl, _typeName: TypeName, path: PathLike)
  extends InnerNamedTypeDeclaration(_pkg, _typeName) {

  override val superClass: Option[TypeName] = Some(TypeName.Interview)
  override lazy val superClassDeclaration: Option[TypeDeclaration] = Some(PlatformTypes.interviewType)

  override def findMethod(name: Name, params: Seq[TypeName], staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Seq[MethodDeclaration] = {
    PlatformTypes.interviewType.findMethod(name, params, staticContext, verifyContext)
  }
}

/** Interviews wrapped into a namespace */
final class InterviewNamespace(_pkg: PackageImpl, _typeName: TypeName)
  extends InnerNamedTypeDeclaration(_pkg, _typeName) {

  private var interviews: Seq[CustomInterview] = Nil
  override def nestedTypes: Seq[TypeDeclaration] = interviews

  def upsert(interview: CustomInterview): Unit = {
    interviews = interview +: interviews
  }
}

