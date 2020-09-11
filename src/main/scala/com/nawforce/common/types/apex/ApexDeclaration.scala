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

import com.nawforce.common.api.{TypeName, _}
import com.nawforce.common.cst._
import com.nawforce.common.diagnostics.Issue
import com.nawforce.common.documents._
import com.nawforce.common.finding.TypeResolver
import com.nawforce.common.modifiers.{GLOBAL_MODIFIER, ISTEST_ANNOTATION, TEST_METHOD_MODIFIER, TEST_SETUP_ANNOTATION}
import com.nawforce.common.org.{OrgImpl, PackageImpl}
import com.nawforce.common.path.PathLike
import com.nawforce.common.types.core._

/** Apex block core features, be they full or summary style */
trait ApexBlockLike extends BlockDeclaration {
  def summary(shapeOnly: Boolean): BlockSummary = serialise(shapeOnly)
}

/** Apex defined constructor core features, be they full or summary style */
trait ApexConstructorLike extends ConstructorDeclaration {
  val nameRange: PathLocation

  def summary(shapeOnly: Boolean): ConstructorSummary = {
    serialise(shapeOnly, Some(nameRange.location))
  }
}

/** Unifying trait for ApexMethodLike and CustomMethodDeclaration. Both need to be appears to be visible from a
  * a type but have little in common beyond allowing for constructions of a summary. */
trait ApexVisibleMethodLike extends MethodDeclaration {
  def summary(shapeOnly: Boolean): MethodSummary
}

/** Apex defined method core features, be they full or summary style */
trait ApexMethodLike extends ApexVisibleMethodLike {
  val outerTypeId: TypeId
  def nameRange: PathLocation

  // Populated by type MethodMap construction
  private var shadows: List[MethodDeclaration] = Nil

  def addShadow(method: MethodDeclaration): Unit = {
    shadows = method :: shadows
  }

  def isEntry: Boolean = {
    modifiers.contains(ISTEST_ANNOTATION) ||
    modifiers.contains(TEST_SETUP_ANNOTATION) ||
    modifiers.contains(TEST_METHOD_MODIFIER) ||
    modifiers.contains(GLOBAL_MODIFIER)
  }

  /* Is the method in use, NOTE: requires a MethodMap is constructed for shadow support first! */
  def isUsed: Boolean = {
    isEntry || hasHolders ||
    shadows.exists({
      case am: ApexMethodLike   => am.isUsed
      case _: MethodDeclaration => true
      case _                    => false
    })
  }

  def summary(shapeOnly: Boolean): MethodSummary = {
    serialise(shapeOnly, Some(nameRange.location))
  }
}

/** Apex defined fields core features, be they full or summary style */
trait ApexFieldLike extends FieldDeclaration {
  val outerTypeId: TypeId
  val nameRange: PathLocation
  val idTarget: Option[TypeName] = None

  def summary(shapeOnly: Boolean): FieldSummary = {
    serialise(shapeOnly, Some(nameRange.location))
  }
}

/** Apex defined types core features, be they full or summary style */
trait ApexDeclaration extends TypeDeclaration with DependentType {
  val path: PathLike
  val sourceHash: Int
  val pkg: PackageImpl
  val nameLocation: PathLocation

  // Get summary of this type
  def summary: TypeSummary
}

trait ApexFullDeclaration extends ApexDeclaration {
  // Specialised validation with option to turn off propagation
  def validate(withPropagation: Boolean): Unit

  override def validate(): Unit = {
    validate(withPropagation = true)
  }

  def summary(shapeOnly: Boolean): TypeSummary
}

trait ApexTriggerDeclaration extends ApexDeclaration

trait ApexClassDeclaration extends ApexDeclaration {
  val path: PathLike
  val sourceHash: Int
  val pkg: PackageImpl
  val nameLocation: PathLocation
  val localFields: Array[ApexFieldLike]
  val localMethods: Array[MethodDeclaration]

  /** Override to handle request to flush the type to passed cache if dirty */
  def flush(pc: ParsedCache, context: PackageContext): Unit

  /** Override to handle request to propagate all dependencies in type */
  def propagateAllDependencies(): Unit

  override def superClassDeclaration: Option[TypeDeclaration] = {
    superClass.flatMap(sc => pkg.getTypeFor(sc, this))
  }

  override def interfaceDeclarations: Array[TypeDeclaration] = {
    interfaces.flatMap(i => pkg.getTypeFor(i, this))
  }

  override def isComplete: Boolean = {
    (superClassDeclaration.nonEmpty && superClassDeclaration.get.isComplete) || superClass.isEmpty
  }

  override lazy val fields: Array[FieldDeclaration] = {
    val uniqueLocalFields: Iterable[FieldDeclaration] = localFields
      .groupBy(f => f.name)
      .collect {
        case (_, single) if single.length == 1 => single.head
        case (_, duplicates) =>
          duplicates.tail.foreach {
            case af: ApexFieldLike =>
              OrgImpl.logError(af.nameRange, s"Duplicate field/property: '${af.name}'")
            case _ => assert(false)
          }
          duplicates.head
      }

    val allFields: Array[FieldDeclaration] =
      superClassDeclaration
        .map(_.fields)
        .getOrElse(FieldDeclaration.emptyFieldDeclarations) ++ uniqueLocalFields
    allFields.map(f => (f.name, f)).toMap.values.toArray
  }

  lazy val staticMethods: Array[MethodDeclaration] = {
    localMethods.filter(_.isStatic) ++
      (superClassDeclaration match {
        case Some(td: ApexClassDeclaration) =>
          td.localMethods.filter(_.isStatic) ++ td.staticMethods
        case _ =>
          MethodDeclaration.emptyMethodDeclarations
      })
  }

  lazy val outerStaticMethods: Array[MethodDeclaration] = {
    outerTypeName.flatMap(ot => TypeResolver(ot, this, excludeSObjects = false).toOption) match {
      case Some(td: ApexClassDeclaration) => td.staticMethods
      case _                              => MethodDeclaration.emptyMethodDeclarations
    }
  }

  def methodMap: MethodMap = {
    if (_methodMap.isEmpty)
      _methodMap = Some(createMethodMap)
    _methodMap.get
  }

  protected def clearMethodMap(): Unit = {
    _methodMap = None
  }

  private var _methodMap: Option[MethodMap] = None

  def createMethodMap: MethodMap = {
    val allMethods = outerStaticMethods ++ localMethods
    val methods = superClassDeclaration match {
      case Some(at: ApexClassDeclaration) =>
        MethodMap(this, Some(nameLocation), at.methodMap, allMethods, interfaceDeclarations)
      case Some(td: TypeDeclaration) =>
        MethodMap(
          this,
          Some(nameLocation),
          MethodMap(td, None, MethodMap.empty(), td.methods, TypeDeclaration.emptyTypeDeclarations),
          allMethods,
          interfaceDeclarations)
      case _ =>
        MethodMap(this, Some(nameLocation), MethodMap.empty(), allMethods, interfaceDeclarations)
    }

    methods.errors.foreach(OrgImpl.log)
    methods
  }

  override def methods: Array[MethodDeclaration] = methodMap.allMethods

  override def findMethod(name: Name,
                          params: Array[TypeName],
                          staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Array[MethodDeclaration] = {
    methodMap.findMethod(name, params, staticContext, verifyContext)
  }

  def unused(): Array[Issue] = {
    // Hack: Unused calculation requires a methodMap for shadow support
    methodMap
    localFields
      .filterNot(_.hasHolders)
      .map(
        field =>
          new Issue(field.nameRange.path,
                    Diagnostic(UNUSED_CATEGORY,
                               field.nameRange.location,
                               s"Unused Field or Property '${field.name}'"))) ++
      localMethods
        .flatMap {
          case am: ApexMethodLike if !am.isUsed => Some(am)
          case _                                => None
        }
        .map(
          method =>
            new Issue(method.nameRange.path,
                      Diagnostic(UNUSED_CATEGORY,
                                 method.nameRange.location,
                                 s"Unused Method '${method.signature}'")))
  }
}
