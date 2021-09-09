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
import com.nawforce.apexlink.finding.RelativeTypeContext
import com.nawforce.apexlink.finding.TypeResolver.TypeCache
import com.nawforce.apexlink.memory.Monitor
import com.nawforce.apexlink.names.TypeNames.TypeNameUtils
import com.nawforce.apexlink.org.{Module, OrgImpl}
import com.nawforce.apexlink.types.apex
import com.nawforce.apexlink.types.core._
import com.nawforce.apexparser.ApexParser.{ModifierContext, TypeDeclarationContext}
import com.nawforce.pkgforce.diagnostics.{Location, LoggerOps}
import com.nawforce.pkgforce.documents._
import com.nawforce.pkgforce.modifiers.{ABSTRACT_MODIFIER, ApexModifiers, ModifierResults, VIRTUAL_MODIFIER}
import com.nawforce.pkgforce.names.{Name, TypeName}
import com.nawforce.pkgforce.path.{PathFactory, PathLike}
import com.nawforce.runtime.parsers.{CodeParser, Source, SourceData}
import upickle.default.writeBinary

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
                               val interfaces: Array[TypeName],
                               val bodyDeclarations: Array[ClassBodyDeclaration])
    extends ClassBodyDeclaration(_modifiers)
    with ApexClassDeclaration
    with ApexFullDeclaration {

  lazy val sourceHash: Int = source.hash
  override def paths: Array[PathLike] = Array(source.path)
  override val moduleDeclaration: Option[Module] = Some(module)
  override val name: Name = typeName.name
  override val idLocation: Location = id.location.location
  override val nature: Nature
  var flushedToCache = false

  override lazy val nestedTypes: Array[TypeDeclaration] =
    _nestedTypes.asInstanceOf[Array[TypeDeclaration]]
  private lazy val _nestedTypes: Array[FullDeclaration] = {
    bodyDeclarations.flatMap {
      case x: FullDeclaration => Some(x)
      case _                  => None
    }
  }

  override lazy val blocks: Array[BlockDeclaration] = _blocks.asInstanceOf[Array[BlockDeclaration]]
  private lazy val _blocks: Array[ApexInitializerBlock] = {
    bodyDeclarations.flatMap {
      case x: ApexInitializerBlock => Some(x)
      case _                       => None
    }
  }

  lazy val localFields: Array[ApexFieldLike] = {
    bodyDeclarations.flatMap {
      case x: ApexFieldDeclaration    => Some(x)
      case x: ApexPropertyDeclaration => Some(x)
      case _                          => None
    }
  }

  override lazy val constructors: Array[ConstructorDeclaration] =
    _constructors.asInstanceOf[Array[ConstructorDeclaration]]
  private lazy val _constructors: Array[ApexConstructorDeclaration] = {
    bodyDeclarations.flatMap {
      case x: ApexConstructorDeclaration => Some(x)
      case _                             => None
    }
  }

  override lazy val localMethods: Array[MethodDeclaration] =
    _localMethods.asInstanceOf[Array[MethodDeclaration]]
  lazy val _localMethods: Array[ApexVisibleMethodLike] = {
    bodyDeclarations.flatMap({
      case m: ApexVisibleMethodLike => Some(m)
      case _                        => None
    })
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
    val pathBasename = PathFactory(location.path).basename
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
      (this +: nestedTypes).toSeq.groupBy(_.name).collect { case (_, Seq(_, y, _*)) => y }
    duplicateNestedType.foreach(td =>
      OrgImpl.logError(td.asInstanceOf[apex.FullDeclaration].location, s"Duplicate type name '${td.name.toString}'"))

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
                _blocks.map(_.summary),
                localFields.map(_.summary).sortBy(_.name),
                _constructors.map(_.summary).sortBy(_.parameters.length),
                _localMethods.map(_.summary).sortBy(_.name),
                _nestedTypes.map(_.summary).sortBy(_.name),
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
      Some(CompilationUnit.construct(parser, module, doc.name, extendedApex, result.value).typeDeclaration)
    else
      None
  }

  def construct(parser: CodeParser, module: Module, name: Name, extendedApex: Boolean, typeDecl: TypeDeclarationContext): FullDeclaration = {

    val modifiers: Seq[ModifierContext] = CodeParser.toScala(typeDecl.modifier())
    val thisType = TypeName(name).withNamespace(module.namespace)

    val cst = CodeParser
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

    if (cst.isEmpty)
      throw new CSTException()
    else {
      Monitor.push(cst.get)
      cst.get.withContext(typeDecl)
    }
  }
}
