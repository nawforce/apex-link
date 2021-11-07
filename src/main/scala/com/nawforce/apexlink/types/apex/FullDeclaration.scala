/*
 Copyright (c) 2019 Kevin Jones, All rights reserved.
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
 */

package com.nawforce.apexlink.types.apex

import com.nawforce.apexlink.api._
import com.nawforce.apexlink.cst._
import com.nawforce.apexlink.finding.TypeResolver.TypeCache
import com.nawforce.apexlink.finding.{RelativeTypeContext, TypeResolver}
import com.nawforce.apexlink.memory.Monitor
import com.nawforce.apexlink.names.TypeNames.TypeNameUtils
import com.nawforce.apexlink.org.{Module, OrgImpl}
import com.nawforce.apexlink.types.core._
import com.nawforce.apexparser.ApexParser.TypeDeclarationContext
import com.nawforce.pkgforce.diagnostics.LoggerOps
import com.nawforce.pkgforce.documents._
import com.nawforce.pkgforce.modifiers.{ABSTRACT_MODIFIER, ApexModifiers, ModifierResults, VIRTUAL_MODIFIER}
import com.nawforce.pkgforce.names.{Name, TypeName}
import com.nawforce.pkgforce.parsers.{ApexNode, CLASS_NATURE, INTERFACE_NATURE, Nature}
import com.nawforce.pkgforce.path.{Location, PathLike}
import com.nawforce.runtime.parsers.{CodeParser, Source, SourceData}
import upickle.default.writeBinary

import scala.collection.immutable.ArraySeq
import scala.collection.mutable

/* Apex type declaration, a wrapper around the Apex parser output. This is the base for classes, interfaces & enums*/
abstract class FullDeclaration(val source: Source,
                               val module: Module,
                               val typeContext: RelativeTypeContext,
                               override val typeName: TypeName,
                               override val outerTypeName: Option[TypeName],
                               val id: Id,
                               _modifiers: ModifierResults,
                               val superClass: Option[TypeName],
                               val interfaces: ArraySeq[TypeName],
                               val bodyDeclarations: ArraySeq[ClassBodyDeclaration])
    extends ClassBodyDeclaration(_modifiers)
    with ApexClassDeclaration
    with ApexFullDeclaration {

  lazy val sourceHash: Int = source.hash

  override def paths: ArraySeq[PathLike] = ArraySeq(source.path)

  override val moduleDeclaration: Option[Module] = Some(module)
  override val name: Name = typeName.name
  override val idLocation: Location = id.location.location
  override val nature: Nature
  var flushedToCache = false

  // For ApexNode compatibility
  override val children: ArraySeq[ApexNode] = bodyDeclarations

  override def nestedTypes: ArraySeq[FullDeclaration] =
    bodyDeclarations.flatMap {
      case x: FullDeclaration => Some(x)
      case _ => None
    }

  override lazy val blocks: ArraySeq[ApexInitializerBlock] = {
    bodyDeclarations.flatMap {
      case x: ApexInitializerBlock => Some(x)
      case _ => None
    }
  }

  lazy val localFields: Array[ApexFieldLike] = {
    bodyDeclarations.flatMap {
      case x: ApexFieldDeclaration => Some(x)
      case x: ApexPropertyDeclaration => Some(x)
      case _ => None
    }.toArray
  }

  override lazy val constructors: ArraySeq[ApexConstructorDeclaration] = {
    bodyDeclarations.flatMap {
      case x: ApexConstructorDeclaration => Some(x)
      case _ => None
    }
  }

  override lazy val localMethods: Array[MethodDeclaration] =
    _localMethods.asInstanceOf[Array[MethodDeclaration]]
  lazy val _localMethods: Array[ApexVisibleMethodLike] = {
    bodyDeclarations.flatMap({
      case m: ApexVisibleMethodLike => Some(m)
      case _ => None
    }).toArray
  }

  override def flush(pc: ParsedCache, context: PackageContext): Unit = {
    if (!flushedToCache) {
      val diagnostics = module.pkg.org.issues.getDiagnostics(location.path).toArray
      pc.upsert(context, name.value, source.asUTF8, writeBinary(ApexSummary(summary, diagnostics)))
      flushedToCache = true
    }
  }

  override def propagateAllDependencies(): Unit = {
    // Not needed, dependencies are propagated by default during validation
  }

  override def validate(): Unit = {
    LoggerOps.debugTime(s"Validated ${location.path}") {
      // Validate inside a parsing context as LazyBlock may call parser
      CST.sourceContext.withValue(Some(source)) {
        val context = new TypeVerifyContext(None, this, None)
        modifierIssues.foreach(context.log)
        verify(context)
        propagateOuterDependencies(new TypeCache())

        // Re-validation may update diagnostics which now need flushing
        flushedToCache = false
      }
    }
  }

  override def preReValidate(): Unit = {
    // Method maps & relative type contexts may be invalidated by changes to super classes/interfaces
    resetMethodMapIfInvalid()
    typeContext.reset()
    nestedTypes.foreach(_.preReValidate())
  }

  protected def verify(context: TypeVerifyContext): Unit = {
    // Check for name/path mismatch on outer types
    val pathBasename = location.path.basename
    if (outerTypeName.isEmpty && !pathBasename.matches(s"(?i)${id.name}.x?cls")) {
      context.logError(id.location, s"Type name '${id.name}' does not match file name '$pathBasename'")
    }

    // Check super class is visible
    superClassDeclaration.foreach(context.addDependency)
    if (superClass.nonEmpty) {
      if (superClassDeclaration.isEmpty) {
        context.missingType(id.location, superClass.get)
      } else if (superClassDeclaration.get.nature != CLASS_NATURE) {
        OrgImpl.logError(id.location, s"Parent type '${superClass.get.asDotName}' must be a class")
      } else if (superClassDeclaration.get.modifiers
                   .intersect(Seq(VIRTUAL_MODIFIER, ABSTRACT_MODIFIER))
                   .isEmpty) {
        OrgImpl.logError(id.location,
                         s"Parent class '${superClass.get.asDotName}' must be declared virtual or abstract")
      }
    }

    // Check for duplicate nested types
    val duplicateNestedType =
      (this +: nestedTypes).groupBy(_.name).collect { case (_, Seq(_, y, _*)) => y }
    duplicateNestedType.foreach(td =>
      OrgImpl.logError(td.location, s"Duplicate type name '${td.name.toString}'"))

    // Check interfaces are visible
    interfaces.foreach(interface => {
      val td = context.getTypeAndAddDependency(interface, context.thisType).toOption
      if (td.isEmpty) {
        if (!context.module.isGhostedType(interface))
          context.missingType(id.location, interface)
      } else if (td.get.nature != INTERFACE_NATURE)
        OrgImpl.logError(id.location, s"Type '${interface.toString}' must be an interface")
    })

    // Detail check each body declaration
    bodyDeclarations.foreach(bd => bd.validate(new BodyDeclarationVerifyContext(context, bd, None)))

    nestedTypes.filter(t => t.nestedTypes.nonEmpty)
      .foreach(_.nestedTypes.foreach {
        case fd: FullDeclaration => OrgImpl.logError(fd.id.location, s"${fd.id.name}: Inner types of Inner types are not valid.")
        case _ =>
      })

    // Log dependencies logged against this context
    setDepends(context.dependencies)
  }

  override def collectDependenciesByTypeName(dependsOn: mutable.Set[TypeId],
                                             apexOnly: Boolean,
                                             typeCache: TypeCache): Unit = {
    val dependents = mutable.Set[Dependent]()
    collectDependencies(dependents)
    DependentType.dependentsToTypeIds(module, dependents, apexOnly, dependsOn)
  }

  override def collectDependencies(dependsOn: mutable.Set[Dependent]): Unit = {
    super.collectDependencies(dependsOn)
    bodyDeclarations.foreach(_.collectDependencies(dependsOn))
  }

  /** Locate an ApexDeclaration for the passed typeName that was extracted from location. */
  def findDeclarationFromSourceReference(searchTerm: String,
                                         location: Location
                                        ): Option[ApexDeclaration] = {
    /** Find the outer or inner class that contains the passed cursor position */
    def findEnclosingClass(line: Int, offset: Int): Option[FullDeclaration] = {
      nestedTypes
        .collect { case nested: FullDeclaration => nested }
        .find(_.location.location.contains(line, offset))
        .orElse({
          if (this.location.location.contains(line, offset))
            Some(this)
          else
            None
        })
    }

    TypeName(searchTerm).toOption match {
      case Some(typeName: TypeName) =>
        findEnclosingClass(location.startLine, location.startPosition).flatMap(td => {
          TypeResolver(typeName, td).toOption.collect { case td: ApexDeclaration => td }
        })
      case _ => None
    }
  }

  /** Get a validation result map for the body declaration at the specified location. */
  def getBodyDeclarationValidationMap(line: Int, offset: Int): Map[Location, (CST, ExprContext)] = {
    try {
      getBodyDeclarationFromLocation(line, offset).map(typeAndBody => {
        // Validate the body declaration for the side-effect of being able to collect a map of expression results
        val typeContext = new TypeVerifyContext(None, typeAndBody._1, None)
        val resultMap = mutable.Map[Location, (CST, ExprContext)]()
        val context = new BodyDeclarationVerifyContext(typeContext, typeAndBody._2, Some(resultMap))
        context.disableIssueReporting() {
          typeAndBody._2.validate(context)
        }
        resultMap.toMap
      }).getOrElse(Map.empty)
    } catch {
      case ex: Throwable =>
        val at = ex.getStackTrace.headOption.getOrElse("Unknown")
        LoggerOps.debug(s"Body validation failure: ${ex.toString} $at")
        Map.empty
    }
  }

  private def getBodyDeclarationFromLocation(
                                              line: Int,
                                              offset: Int): Option[(FullDeclaration, ClassBodyDeclaration)] = {
    nestedTypes.view
      .collect { case nested: FullDeclaration => nested }
      .flatMap(td => td.getBodyDeclarationFromLocation(line, offset))
      .headOption
      .orElse({
        bodyDeclarations.find(_.location.location.contains(line, offset)).map((this, _))
      })
  }


  // Override to avoid super class access (use local fields & methods) & provide location information
  override def summary: TypeSummary = {
    TypeSummary(sourceHash,
      location.location,
      id.location.location,
      name.toString,
      typeName,
      nature.value,
      modifiers.map(_.toString).sorted,
      superClass,
      interfaces,
      blocks.map(_.summary),
      localFields.map(_.summary).sortBy(_.name),
      constructors.map(_.summary).sortBy(_.parameters.length),
      _localMethods.map(_.summary).sortBy(_.name),
      nestedTypes.map(_.summary).sortBy(_.name),
      dependencySummary())
  }
}

object FullDeclaration {
  def create(module: Module,
             doc: ClassDocument,
             data: SourceData,
             extendedApex: Boolean,
             forceConstruct: Boolean): Option[FullDeclaration] = {
    val parser = CodeParser(doc.path, data)
    val result = parser.parseClass()
    val issues = result.issues
    issues.foreach(OrgImpl.log)
    if (issues.isEmpty || forceConstruct)
      CompilationUnit.construct(parser, module, doc.name, extendedApex, result.value)
        .map(_.typeDeclaration)
    else
      None
  }

  def construct(parser: CodeParser, module: Module, name: Name, extendedApex: Boolean, typeDecl: TypeDeclarationContext): Option[FullDeclaration] = {

    val modifiers = ArraySeq.unsafeWrapArray(CodeParser.toScala(typeDecl.modifier()).toArray)
    val thisType = TypeName(name).withNamespace(module.namespace)

    val cst: Option[FullDeclaration] = CodeParser
      .toScala(typeDecl.classDeclaration())
      .map(
        cd =>
          ClassDeclaration.construct(parser,
            module,
            thisType,
            None,
            extendedApex,
            ApexModifiers.classModifiers(parser, modifiers, outer = true, cd.id()),
            cd))
      .orElse(
        CodeParser
          .toScala(typeDecl.interfaceDeclaration())
          .map(
            id =>
              InterfaceDeclaration.construct(parser,
                                             module,
                                             thisType,
                                             None,
                                             ApexModifiers.interfaceModifiers(parser, modifiers, outer = true, id.id()),
                                             id)))
      .orElse(
        CodeParser
          .toScala(typeDecl.enumDeclaration())
          .map(
            ed =>
              EnumDeclaration.construct(parser,
                module,
                thisType,
                None,
                ApexModifiers.enumModifiers(parser, modifiers, outer = true, ed.id()),
                ed)))

    cst.foreach(Monitor.push(_))
    cst.map(_.withContext(typeDecl))
  }
}
