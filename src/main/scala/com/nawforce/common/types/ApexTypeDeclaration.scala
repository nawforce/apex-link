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
package com.nawforce.common.types

import com.nawforce.common.cst._
import com.nawforce.common.diagnostics.{Issue, UNUSED_CATEGORY}
import com.nawforce.common.documents.LineLocation
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.parsers.ApexParser.{ModifierContext, TypeDeclarationContext}
import com.nawforce.common.path.PathLike
import com.nawforce.runtime.api.Org
import com.nawforce.runtime.parsers.CodeParser

import scala.collection.JavaConverters._
import scala.collection.mutable

/* Apex type declaration, a wrapper around the Apex parser output. This is the base for classes, interfaces & enums*/
abstract class ApexTypeDeclaration(val pkg: PackageDeclaration, val outerTypeName: Option[TypeName],
                                   val id: Id, _modifiers: Seq[Modifier],
                                   val superClass: Option[TypeName], val interfaces: Seq[TypeName],
                                   val bodyDeclarations: Seq[ClassBodyDeclaration])
  extends ClassBodyDeclaration(_modifiers) with TypeDeclaration {

  override val packageDeclaration: Option[PackageDeclaration] = Some(pkg)
  override val name: Name = id.name
  override val typeName: TypeName = {
    outerTypeName.map(outer => TypeName(name).withOuter(Some(outer)))
      .getOrElse(TypeName(name, Nil, pkg.namespace.map(TypeName(_))))
  }

  override val nature: Nature

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

  override val nestedTypes: Seq[ApexTypeDeclaration] = {
    bodyDeclarations.flatMap {
      case x: ApexTypeDeclaration => Some(x)
      case _ => None
    }
  }

  override lazy val blocks: Seq[BlockDeclaration] = {
    bodyDeclarations.flatMap {
      case x: BlockDeclaration => Some(x)
      case _ => None
    }
  }

  override lazy val fields: Seq[FieldDeclaration] = {
    val fields = bodyDeclarations.flatMap {
      case x: FieldDeclaration => Some(x)
      case _ => None
    }
    val allFields = superClassDeclaration.map(_.fields).getOrElse(Seq()) ++ fields.groupBy(f => f.name).collect {
      case (_, y :: Nil) => y
      case (_, duplicates) =>
        duplicates.tail.foreach(d => {
          Org.logMessage(d.location, s"Duplicate field/property: '${d.name}'")
        })
        duplicates.head
    }.toSeq

    allFields.map(f => (f.name, f)).toMap.values.toSeq
  }

  lazy val localFields: Seq[FieldDeclaration] = {
    bodyDeclarations.flatMap {
      case x: ApexFieldDeclaration => Some(x)
      case x: ApexPropertyDeclaration => Some(x)
      case _ => None
    }
  }

  override lazy val constructors: Seq[ConstructorDeclaration] = {
    bodyDeclarations.flatMap {
      case x: ConstructorDeclaration => Some(x)
      case _ => None
    }
  }

  def localMethods: Seq[ApexMethodDeclaration] = {
    bodyDeclarations.flatMap({
      case m: ApexMethodDeclaration => Some(m)
      case _ => None
    })
  }

  def staticMethods: Seq[MethodDeclaration] = {
    localMethods.filter(_.isStatic) ++
      (superClassDeclaration match {
        case Some(td: ApexTypeDeclaration) =>
          td.localMethods.filter(_.isStatic) ++ td.staticMethods
        case _ =>
          Seq()
      })
  }

  val methodMap: MethodMap

  override lazy val methods: Seq[MethodDeclaration] = methodMap.allMethods.toSeq

  override def findMethod(name: Name, params: Seq[TypeName], staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Seq[MethodDeclaration] = {
    methodMap.findMethod(name, params, staticContext, verifyContext)
  }

  override def validate(): Unit = {
    val context = new TypeVerifyContext(None, this)
    if (depends.isEmpty) {
      verify(context)
      depends = Some(context.dependencies)
    }
  }

  protected def verify(context: TypeVerifyContext): Unit = {
    if (depends.nonEmpty)
      return

    superClassDeclaration.foreach(context.addDependency)
    if (superClass.nonEmpty) {
      if (superClassDeclaration.isEmpty) {
        context.missingType(id.location, superClass.get)
      } else if (superClassDeclaration.get.nature != CLASS_NATURE) {
        Org.logMessage(id.location, s"Parent type '${superClass.get.asDotName}' must be a class")
      } else if (superClassDeclaration.get.modifiers.intersect(Seq(VIRTUAL_MODIFIER, ABSTRACT_MODIFIER)).isEmpty) {
        Org.logMessage(id.location, s"Parent class '${superClass.get.asDotName}' must be declared virtual or abstract")
      }
    }

    val duplicateNestedType = (this +: nestedTypes).groupBy(_.name).collect { case (_, List(_, y, _*)) => y }
    duplicateNestedType.foreach(td =>
      Org.logMessage(td.id.location, s"Duplicate type name '${td.name.toString}'"))

    interfaces.foreach(interface => {
      val td = context.getTypeAndAddDependency(interface, context.thisType).toOption
      if (td.isEmpty) {
        if (!context.pkg.isGhostedType(interface))
          Org.logMessage(id.location, s"No declaration found for interface '${interface.toString}'")
      } else if (td.get.nature != INTERFACE_NATURE)
        Org.logMessage(id.location, s"Type '${interface.toString}' must be an interface")
    })
    bodyDeclarations.foreach(bd => bd.validate(new BodyDeclarationVerifyContext(context, bd)))

    depends = Some(context.dependencies)
  }

  override def collectDependencies(dependsOn: mutable.Set[Dependant]): Unit = {
    super.collectDependencies(dependsOn)
    bodyDeclarations.foreach(_.collectDependencies(dependsOn))
  }

  def unused(): Seq[Issue] = {
    localFields.filterNot(_.hasHolders)
      .map({
        case field: ApexFieldDeclaration =>
          Issue(UNUSED_CATEGORY, field.location, s"Field '${field.name}'")
        case property: ApexPropertyDeclaration =>
          Issue(UNUSED_CATEGORY, property.location, s"Property '${property.name}'")
      }) ++
    localMethods.filterNot(_.isUsed)
      .map(method => Issue(UNUSED_CATEGORY, method.location, s"Method '${method.signature}'"))
  }
}

object ApexTypeDeclaration {
  def create(pkg: PackageDeclaration, path: PathLike, data: String): Seq[ApexTypeDeclaration] = {
    CodeParser.parseCompilationUnit(path, data) match {
      case Left(err) =>
        Org.logMessage(LineLocation(path, err.line), err.msg)
        Nil
      case Right(cu) =>
        Seq(CompilationUnit.construct(pkg, path, cu, new ConstructContext()).typeDeclaration())
    }
  }

  def construct(pkg: PackageDeclaration, outerTypeName: Option[TypeName], typeDecl: TypeDeclarationContext, context: ConstructContext)
  : ApexTypeDeclaration = {

    val modifiers: Seq[ModifierContext] = typeDecl.modifier().asScala
    val isOuter = outerTypeName.isEmpty
    val cst =
      if (typeDecl.classDeclaration() != null) {
        ClassDeclaration.construct(
          pkg, outerTypeName,
          ApexModifiers.classModifiers(modifiers, context, outer = isOuter, typeDecl.classDeclaration().id()),
          typeDecl.classDeclaration(), context)
      } else if (typeDecl.interfaceDeclaration() != null) {
        InterfaceDeclaration.construct(
          pkg, outerTypeName,
          ApexModifiers.interfaceModifiers(modifiers, context, outer = isOuter, typeDecl.interfaceDeclaration().id()),
          typeDecl.interfaceDeclaration(), context)
      } else {
        assert(typeDecl.enumDeclaration() != null)
        EnumDeclaration.construct(
          pkg, outerTypeName,
          ApexModifiers.enumModifiers(modifiers, context, outer = isOuter, typeDecl.enumDeclaration().id()),
          typeDecl.enumDeclaration(), context)
      }
    cst.withContext(typeDecl, context)
  }
}
