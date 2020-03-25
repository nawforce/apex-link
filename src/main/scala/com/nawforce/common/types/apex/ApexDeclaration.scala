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

import com.nawforce.common.api.{ConstructorSummary, FieldSummary, MethodSummary}
import com.nawforce.common.cst._
import com.nawforce.common.diagnostics.{Issue, UNUSED_CATEGORY}
import com.nawforce.common.documents._
import com.nawforce.common.finding.TypeRequest
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.org.{OrgImpl, PackageImpl}
import com.nawforce.common.types.{ConstructorDeclaration, FieldDeclaration, MethodDeclaration, TypeDeclaration}

import scala.collection.mutable

/** Apex defined constructor core features, be they full or summary style */
trait ApexConstructorLike extends ConstructorDeclaration {
  val nameRange: RangeLocationImpl

  override def summary(): ConstructorSummary = {
    super.summary(Some(new TextRange(nameRange.start, nameRange.end)))
  }
}

/** Apex defined method core features, be they full or summary style */
trait ApexMethodLike extends MethodDeclaration {
  val nameRange: RangeLocationImpl
  val outerTypeName: TypeName

  // Populated by type MethodMap construction
  lazy val shadows: mutable.Set[MethodDeclaration] = mutable.Set()

  lazy val isEntry: Boolean = {
    modifiers.contains(ISTEST_ANNOTATION) ||
      modifiers.contains(TEST_SETUP_ANNOTATION) ||
      modifiers.contains(TEST_METHOD_MODIFIER) ||
      modifiers.contains(GLOBAL_MODIFIER)
  }

  /* Is the method in use, NOTE: requires a MethodMap is constructed for shadow support first! */
  def isUsed: Boolean = {
    isEntry || hasHolders ||
      shadows.exists({
        case am: ApexMethodLike => am.isUsed
        case _: MethodDeclaration => true
        case _ => false
      })
  }

  override def summary(): MethodSummary = {
    super.summary(Some(new TextRange(nameRange.start, nameRange.end)))
  }
}

/** Apex defined fields core features, be they full or summary style */
trait ApexFieldLike extends FieldDeclaration {
  val nameRange: RangeLocationImpl
  val outerTypeName: TypeName

  override def summary(): FieldSummary = {
    super.summary(Some(new TextRange(nameRange.start, nameRange.end)))
  }
}

/** Apex defined types core features, be they full or summary style */
trait ApexDeclaration extends TypeDeclaration {
  val sourceHash: Int
  val pkg: PackageImpl
  val nameLocation: LocationImpl
  val localFields: Seq[ApexFieldLike]
  val localMethods: Seq[MethodDeclaration]

  /** Override to handle request to flush the type to passed cache if dirty */
  def flush(pc: ParsedCache, context: PackageContext): Unit

  /** Override to handle request to propagate all dependencies in type */
  def propagateAllDependencies(): Unit

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
          OrgImpl.logError(d.nameRange, s"Duplicate field/property: '${d.name}'")
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
        MethodMap(this, Some(nameLocation), at.methodMap, allMethods, interfaceDeclarations)
      case Some(td: TypeDeclaration) =>
        MethodMap(this, Some(nameLocation),
          MethodMap(td, None, MethodMap.empty(), td.methods, Seq()),
          allMethods, interfaceDeclarations)
      case _ =>
        MethodMap(this, Some(nameLocation), MethodMap.empty(), allMethods, interfaceDeclarations)
    }

    mmap.errors.foreach(OrgImpl.log)
    mmap
  }

  override lazy val methods: Seq[MethodDeclaration] = methodMap.allMethods.toSeq

  override def findMethod(name: Name, params: Seq[TypeName], staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Seq[MethodDeclaration] = {
    methodMap.findMethod(name, params, staticContext, verifyContext)
  }

  def collectDependenciesByTypeName(dependents: mutable.Set[TypeName])

  override def validate(): Unit = {
    // Propagate dependency holding to outer type declarations
    val dependsOn = mutable.Set[TypeName]()
    collectDependenciesByTypeName(dependsOn)
    dependsOn.foreach(dependentTypeName =>
      getOutermostDeclaration(dependentTypeName).map(_.addTypeDependencyHolder(typeName)))
  }

  private def getOutermostDeclaration(typeName: TypeName): Option[ApexDeclaration] = {
    TypeRequest(typeName, pkg, excludeSObjects = false) match {
      case Right(td: ApexDeclaration) =>
        td.outerTypeName.map(getOutermostDeclaration).getOrElse(Some(td))
      case Right(_) => None
      case Left(_) => None
    }
  }

  def addTypeDependencyHolder(typeName: TypeName): Unit = {
    if (typeName != this.typeName)
      typeDependencyHolders.add(typeName)
  }

  def getTypeDependencyHolders: mutable.Set[TypeName] = {
    typeDependencyHolders
  }

  def unused(): Seq[Issue] = {
    // Hack: Unused calculation requires a methodMap for shadow support
    methodMap
    localFields.filterNot(_.hasHolders)
      .map(field => new Issue(UNUSED_CATEGORY, field.nameRange, s"Unused Field or Property '${field.name}'")) ++
      localMethods
        .flatMap {
          case am: ApexMethodLike if !am.isUsed => Some(am)
          case _ => None
        }
        .map(method => new Issue(UNUSED_CATEGORY, method.nameRange, s"Unused Method '${method.signature}'"))
  }
}
