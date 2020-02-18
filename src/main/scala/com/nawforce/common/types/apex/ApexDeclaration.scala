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

import com.nawforce.common.api.{FieldSummary, TypeLike}
import com.nawforce.common.cst.{GLOBAL_MODIFIER, MethodMap, VerifyContext}
import com.nawforce.common.documents.{Location, RangeLocation, TextRange}
import com.nawforce.common.finding.TypeRequest
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.org.OrgImpl
import com.nawforce.common.pkg.PackageImpl
import com.nawforce.common.types.{FieldDeclaration, MethodDeclaration, TypeDeclaration}

import scala.collection.mutable

/** Field or Property with location information for error reporting */
trait ApexFieldLike extends FieldDeclaration {
  val location: RangeLocation

  override def summary(excludeNamespace: Option[Name]): FieldSummary = {
    super.summary(excludeNamespace, Some(new TextRange(location.start, location.end)))
  }
}

/** Core features for Apex defined types be they full or summary style */
trait ApexDeclaration extends TypeDeclaration {

  val sourceHash: Int
  val pkg: PackageImpl
  val idLocation: Location
  val localFields: Seq[ApexFieldLike]
  val localMethods: Seq[MethodDeclaration]

  private val typeDependencyHolders = mutable.Set[TypeName]()

  override lazy val typeName: TypeName = {
    outerTypeName.map(outer => TypeName(name).withOuter(Some(outer)))
      .getOrElse(TypeName(name, Nil, pkg.namespace.map(TypeName(_))))
  }

  override lazy val superClassDeclaration: Option[TypeDeclaration] = {
    superClass.flatMap(sc => pkg.getTypeFor(sc, this))
  }

  override lazy val interfaceDeclarations: Seq[TypeDeclaration] = {
    interfaces.flatMap(i => pkg.getTypeFor(i, this))
  }

  override lazy val isComplete: Boolean = {
    (superClassDeclaration.nonEmpty && superClassDeclaration.get.isComplete) || superClass.isEmpty
  }

  override lazy val isExternallyVisible: Boolean = {
    modifiers.contains(GLOBAL_MODIFIER)
  }

  override lazy val fields: Seq[FieldDeclaration] = {
    val allFields = superClassDeclaration.map(_.fields).getOrElse(Seq()) ++ localFields.groupBy(f => f.name).collect {
      case (_, y :: Nil) => y
      case (_, duplicates) =>
        duplicates.tail.foreach(d => {
          OrgImpl.logMessage(d.location, s"Duplicate field/property: '${d.name}'")
        })
        duplicates.head
    }.toSeq
    allFields.map(f => (f.name, f)).toMap.values.toSeq
  }

  lazy val staticMethods: Seq[MethodDeclaration] = {
    localMethods.filter(_.isStatic) ++
      (superClassDeclaration match {
        case Some(td: ApexDeclaration) =>
          td.localMethods.filter(_.isStatic) ++ td.staticMethods
        case _ =>
          Seq()
      })
  }

  lazy val outerStaticMethods: Seq[MethodDeclaration] = {
    outerTypeName.flatMap(ot => TypeRequest(ot, this, excludeSObjects = false).toOption) match {
      case Some(td: ApexDeclaration) => td.staticMethods
      case _ => Seq()
    }
  }

  lazy val methodMap: MethodMap = {
    val allMethods = outerStaticMethods ++ localMethods
    val mmap = superClassDeclaration match {
      case Some(at: ApexDeclaration) =>
        MethodMap(this, Some(idLocation), at.methodMap, allMethods, interfaceDeclarations)
      case Some(td: TypeDeclaration) =>
        MethodMap(this, Some(idLocation),
          MethodMap(td, None, MethodMap.empty(), td.methods, Seq()),
          allMethods, interfaceDeclarations)
      case _ =>
        MethodMap(this, Some(idLocation), MethodMap.empty(), allMethods, interfaceDeclarations)
    }

    mmap.errors.foreach(err => OrgImpl.log(err._1, err._2._2, err._2._1))
    mmap
  }

  override lazy val methods: Seq[MethodDeclaration] = methodMap.allMethods.toSeq

  override def findMethod(name: Name, params: Seq[TypeName], staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Seq[MethodDeclaration] = {
    methodMap.findMethod(name, params, staticContext, verifyContext)
  }

  def collectDependenciesByTypeName(dependents: mutable.Set[TypeName])

  override def validate(): Unit = {
    // Propagate type level dependencies
    val dependsOn = mutable.Set[TypeName]()
    collectDependenciesByTypeName(dependsOn)
    dependsOn.foreach(d => {
      TypeRequest(d, pkg, excludeSObjects = false) match {
        case Left(_) => ()
        case Right(td: ApexDeclaration) => td.addTypeDependencyHolder(typeName)
        case Right(_) => ()
      }
    })
  }

  def addTypeDependencyHolder(typeName: TypeName): Unit = {
    typeDependencyHolders.add(typeName)
  }

  def getTypeDependencyHolders: Array[TypeLike] = {
    typeDependencyHolders.toArray
  }
}
