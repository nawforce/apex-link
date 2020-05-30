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

import com.nawforce.common.api._
import com.nawforce.common.cst._
import com.nawforce.common.documents._
import com.nawforce.common.names.{TypeNames, _}
import com.nawforce.common.org.{OrgImpl, PackageImpl}
import com.nawforce.common.path.PathLike
import com.nawforce.common.types.core._
import com.nawforce.common.types.other.{Component, Interview, Label, Page}
import com.nawforce.runtime.SourceData
import com.nawforce.runtime.parsers.ApexParser.{ModifierContext, TypeDeclarationContext}
import com.nawforce.runtime.parsers.{CodeParser, Source}
import upickle.default.writeBinary

import scala.collection.mutable

/* Apex type declaration, a wrapper around the Apex parser output. This is the base for classes, interfaces & enums*/
abstract class FullDeclaration(val source: Source, val pkg: PackageImpl, val outerTypeName: Option[TypeName],
                               val id: Id, _modifiers: ModifierResults,
                               val superClass: Option[TypeName], val interfaces: Seq[TypeName],
                               val bodyDeclarations: Seq[ClassBodyDeclaration])
  extends ClassBodyDeclaration(_modifiers) with ApexClassDeclaration with ApexFullDeclaration {

  lazy val sourceHash: Int = source.hash
  override val path: PathLike = source.path
  override val paths: Seq[PathLike] = Seq(path)
  override val packageDeclaration: Option[PackageImpl] = Some(pkg)

  override val nameLocation: LocationImpl = id.location
  override val name: Name = id.name
  override val nature: Nature
  var flushedToCache = false

  override val nestedTypes: Seq[FullDeclaration] = {
    bodyDeclarations.flatMap {
      case x: FullDeclaration => Some(x)
      case _ => None
    }
  }

  override lazy val blocks: Seq[ApexInitialiserBlock] = {
    bodyDeclarations.flatMap {
      case x: ApexInitialiserBlock => Some(x)
      case _ => None
    }
  }

  lazy val localFields: Seq[ApexFieldLike] = {
    bodyDeclarations.flatMap {
      case x: ApexFieldDeclaration => Some(x)
      case x: ApexPropertyDeclaration => Some(x)
      case _ => None
    }
  }

  override lazy val constructors: Seq[ApexConstructorDeclaration] = {
    bodyDeclarations.flatMap {
      case x: ApexConstructorDeclaration => Some(x)
      case _ => None
    }
  }

  override lazy val localMethods: Seq[ApexVisibleMethodLike] = {
    bodyDeclarations.flatMap({
      case m: ApexVisibleMethodLike => Some(m)
      case _ => None
    })
  }

  override def flush(pc: ParsedCache, context: PackageContext): Unit = {
    if (!flushedToCache) {
      val diagnostics = pkg.org.issues.getDiagnostics(location.path)
      pc.upsert(source.asUTF8, writeBinary(ApexSummary(summary, diagnostics)), context)
      flushedToCache = true
    }
  }

  override def propagateAllDependencies(): Unit = {
    // Not needed, dependencies are propagated by default during validation
  }

  def validate(withPropagation: Boolean): Unit = {
    ServerOps.debugTime(s"Validated ${location.path}") {
      // Validate inside a parsing context as LazyBlock may call parser
      CST.sourceContext.withValue(Some(source)) {
        val context = new TypeVerifyContext(None, this, withPropagation)
        modifierResults.issues.foreach(context.log)
        verify(context)
        if (withPropagation)
          propagateOuterDependencies()

        // Re-validation may update diagnostics which now need flushing
        flushedToCache = false
      }
    }
  }

  protected def verify(context: TypeVerifyContext): Unit = {
    // Check super class is visible
    superClassDeclaration.foreach(context.addDependency)
    if (superClass.nonEmpty) {
      if (superClassDeclaration.isEmpty) {
        context.missingType(id.location, superClass.get)
      } else if (superClassDeclaration.get.nature != CLASS_NATURE) {
        OrgImpl.logError(id.location, s"Parent type '${superClass.get.asDotName}' must be a class")
      } else if (superClassDeclaration.get.modifiers.intersect(Seq(VIRTUAL_MODIFIER, ABSTRACT_MODIFIER)).isEmpty) {
        OrgImpl.logError(id.location, s"Parent class '${superClass.get.asDotName}' must be declared virtual or abstract")
      }
    }

    // Check for duplicate nested types
    val duplicateNestedType = (this +: nestedTypes).groupBy(_.name).collect { case (_, Seq(_, y, _*)) => y }
    duplicateNestedType.foreach(td =>
      OrgImpl.logError(td.id.location, s"Duplicate type name '${td.name.toString}'"))

    // Check interfaces are visible
    interfaces.foreach(interface => {
      val td = context.getTypeAndAddDependency(interface, context.thisType).toOption
      if (td.isEmpty) {
        if (!context.pkg.isGhostedType(interface))
          context.missingType(id.location, interface)
      } else if (td.get.nature != INTERFACE_NATURE)
        OrgImpl.logError(id.location, s"Type '${interface.toString}' must be an interface")
    })

    // Detail check each body declaration
    bodyDeclarations.foreach(bd => bd.validate(new BodyDeclarationVerifyContext(context, bd)))

    // Log dependencies logged against this context
    depends = Some(context.dependencies)
  }

  override def collectDependenciesByTypeName(dependsOn: mutable.Set[TypeId]): Unit = {
    val dependents = mutable.Set[Dependent]()
    collectDependencies(dependents)
    dependents.foreach {
      case ad: ApexClassDeclaration =>
        dependsOn.add(ad.outerTypeId)
      case _: Label =>
        dependsOn.add(TypeId(pkg, TypeNames.Label))
      case _: Interview =>
        dependsOn.add(TypeId(pkg, TypeNames.Interview))
      case _: Page =>
        dependsOn.add(TypeId(pkg, TypeNames.Page))
      case _: Component =>
        dependsOn.add(TypeId(pkg, TypeNames.Component))
      case _ => ()
    }
  }

  override def collectDependencies(dependsOn: mutable.Set[Dependent]): Unit = {
    super.collectDependencies(dependsOn)
    bodyDeclarations.foreach(_.collectDependencies(dependsOn))
  }

  // Override to avoid super class access (use local fields & methods) & provide location information
  override lazy val summary: TypeSummary = {
    TypeSummary (
      sourceHash,
      Some(new RangeLocation(id.location.start.toPosition, id.location.end.toPosition)),
      name.toString,
      typeName,
      nature.value, modifiers.map(_.toString).sorted.toList,
      superClass,
      interfaces.toList,
      blocks.map(_.summary).toList,
      localFields.map(_.summary).sortBy(_.name).toList,
      constructors.map(_.summary).sortBy(_.parameters.size).toList,
      localMethods.map(_.summary).sortBy(_.name).toList,
      nestedTypes.map(_.summary).sortBy(_.name).toList,
      dependencySummary()
    )
  }
}

object FullDeclaration {
  def create(pkg: PackageImpl, path: PathLike, data: SourceData): Option[FullDeclaration] = {
    val parser = CodeParser(path, data)
    parser.parseClass() match {
      case Left(err) =>
        OrgImpl.logError(LineLocationImpl(path.toString, err.line), err.message)
        None
      case Right(cu) =>
        Some(CompilationUnit.construct(parser, pkg, cu).typeDeclaration)
    }
  }

  def construct(parser: CodeParser, pkg: PackageImpl,
                outerTypeName: Option[TypeName], typeDecl: TypeDeclarationContext)
      : FullDeclaration = {

    val modifiers: Seq[ModifierContext] = CodeParser.toScala(typeDecl.modifier())
    val isOuter = outerTypeName.isEmpty

    val cst = CodeParser.toScala(typeDecl.classDeclaration())
      .map(cd => ClassDeclaration.construct(parser, pkg, outerTypeName,
        ApexModifiers.classModifiers(parser, modifiers, outer = isOuter, cd.id()),
        cd)
      )
    .orElse(CodeParser.toScala(typeDecl.interfaceDeclaration())
      .map(id => InterfaceDeclaration.construct(parser, pkg, outerTypeName,
        ApexModifiers.interfaceModifiers(parser, modifiers, outer = isOuter, id.id()),
        id)
      ))
    .orElse(CodeParser.toScala(typeDecl.enumDeclaration())
      .map(ed => EnumDeclaration.construct(parser, pkg, outerTypeName,
        ApexModifiers.enumModifiers(parser, modifiers, outer = isOuter, ed.id()),
        ed)
      ))

    if (cst.isEmpty)
      throw new CSTException()
    else
       cst.get.withContext(typeDecl)
  }
}
