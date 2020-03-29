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

import java.nio.charset.StandardCharsets

import com.nawforce.common.api.{ApexSummary, RangeLocation, ServerOps, TypeSummary}
import com.nawforce.common.cst._
import com.nawforce.common.documents._
import com.nawforce.common.metadata.Dependent
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.org.{OrgImpl, PackageImpl}
import com.nawforce.common.path.PathLike
import com.nawforce.common.types._
import com.nawforce.runtime.parsers.ApexParser.{ModifierContext, TypeDeclarationContext}
import com.nawforce.runtime.parsers.CodeParser
import upickle.default.writeBinary

import scala.collection.mutable

/* Apex type declaration, a wrapper around the Apex parser output. This is the base for classes, interfaces & enums*/
abstract class FullDeclaration(val source: Source, val pkg: PackageImpl, val outerTypeName: Option[TypeName],
                               val id: Id, _modifiers: Seq[Modifier],
                               val superClass: Option[TypeName], val interfaces: Seq[TypeName],
                               val bodyDeclarations: Seq[ClassBodyDeclaration])
  extends ClassBodyDeclaration(_modifiers) with ApexDeclaration {

  lazy val sourceHash: Int = source.hash
  override val packageDeclaration: Option[PackageImpl] = Some(pkg)
  override val nameLocation: LocationImpl = id.location
  override val name: Name = id.name
  override val nature: Nature

  override val nestedTypes: Seq[FullDeclaration] = {
    bodyDeclarations.flatMap {
      case x: FullDeclaration => Some(x)
      case _ => None
    }
  }

  override lazy val blocks: Seq[BlockDeclaration] = {
    bodyDeclarations.flatMap {
      case x: BlockDeclaration => Some(x)
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

  override lazy val constructors: Seq[ConstructorDeclaration] = {
    bodyDeclarations.flatMap {
      case x: ConstructorDeclaration => Some(x)
      case _ => None
    }
  }

  override lazy val localMethods: Seq[MethodDeclaration] = {
    bodyDeclarations.flatMap({
      case m: ApexMethodDeclaration => Some(m)
      case _ => None
    })
  }

  override def flush(pc: ParsedCache, context: PackageContext): Unit = {
    val diagnostics = pkg.org.issues.getDiagnostics(getPath.toString)
    pc.upsert(source.code.getBytes(StandardCharsets.UTF_8), writeBinary(ApexSummary(summary, diagnostics)), context)
  }

  override def propagateAllDependencies(): Unit = {
    // Not needed, dependencies are propagtes by default during validation
  }

  override def validate(): Unit = {
    val start = System.currentTimeMillis()
    val context = new TypeVerifyContext(None, this)
    if (depends.isEmpty) {
      verify(context)
      super.validate()
    }
    val end = System.currentTimeMillis()
    ServerOps.debug(ServerOps.Trace, s"Validated $getPath in ${end - start}ms")
  }

  protected def verify(context: TypeVerifyContext): Unit = {
    if (depends.nonEmpty)
      return

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

    val duplicateNestedType = (this +: nestedTypes).groupBy(_.name).collect { case (_, Seq(_, y, _*)) => y }
    duplicateNestedType.foreach(td =>
      OrgImpl.logError(td.id.location, s"Duplicate type name '${td.name.toString}'"))

    interfaces.foreach(interface => {
      val td = context.getTypeAndAddDependency(interface, context.thisType).toOption
      if (td.isEmpty) {
        if (!context.pkg.isGhostedType(interface))
          context.missingType(id.location, interface)
      } else if (td.get.nature != INTERFACE_NATURE)
        OrgImpl.logError(id.location, s"Type '${interface.toString}' must be an interface")
    })
    bodyDeclarations.foreach(bd => bd.validate(new BodyDeclarationVerifyContext(context, bd)))

    depends = Some(context.dependencies)
  }

  override def collectDependenciesByTypeName(dependsOn: mutable.Set[TypeName]): Unit = {
    val dependents = mutable.Set[Dependent]()
    collectDependencies(dependents)
    dependents.foreach {
      case ad: ApexDeclaration => dependsOn.add(ad.outerTypeName.getOrElse(ad.typeName))
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
      dependencySummary(),
      getTypeDependencyHolders.toSet
    )
  }
}

object FullDeclaration {
  def create(pkg: PackageImpl, path: PathLike, data: String): Option[FullDeclaration] = {
    val parser = new CodeParser(path, data)
    parser.parseClass() match {
      case Left(err) =>
        OrgImpl.logError(LineLocationImpl(path.toString, err.line), err.message)
        None
      case Right(cu) =>
        Some(CompilationUnit.construct(Source(data), pkg, path, cu, new ConstructContext()).typeDeclaration())
    }
  }

  def construct(source: Source, pkg: PackageImpl, outerTypeName: Option[TypeName], typeDecl: TypeDeclarationContext,
                context: ConstructContext): FullDeclaration = {

    val modifiers: Seq[ModifierContext] = CodeParser.toScala(typeDecl.modifier())
    val isOuter = outerTypeName.isEmpty

    val cst = CodeParser.toScala(typeDecl.classDeclaration())
      .map(cd => ClassDeclaration.construct(source, pkg, outerTypeName,
        ApexModifiers.classModifiers(modifiers, context, outer = isOuter, cd.id()),
        cd, context)
      )
    .orElse(CodeParser.toScala(typeDecl.interfaceDeclaration())
      .map(id => InterfaceDeclaration.construct(source, pkg, outerTypeName,
        ApexModifiers.interfaceModifiers(modifiers, context, outer = isOuter, id.id()),
        id, context)
      ))
    .orElse(CodeParser.toScala(typeDecl.enumDeclaration())
      .map(ed => EnumDeclaration.construct(source, pkg, outerTypeName,
        ApexModifiers.enumModifiers(modifiers, context, outer = isOuter, ed.id()),
        ed, context)
      ))

    if (cst.isEmpty)
      throw new CSTException()
    else
       cst.get.withContext(typeDecl, context)
  }
}
