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
import com.nawforce.apexlink.finding.TypeResolver
import com.nawforce.apexlink.finding.TypeResolver.TypeCache
import com.nawforce.apexlink.memory.SkinnySet
import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.org.{Module, OrgImpl}
import com.nawforce.apexlink.types.core._
import com.nawforce.apexparser.ApexParser.{TriggerCaseContext, TriggerUnitContext}
import com.nawforce.pkgforce.diagnostics.LoggerOps
import com.nawforce.pkgforce.modifiers.{Modifier, ModifierOps}
import com.nawforce.pkgforce.names.{Name, Names, TypeName}
import com.nawforce.pkgforce.parsers.{Nature, TRIGGER_NATURE}
import com.nawforce.pkgforce.path.{Location, PathLike}
import com.nawforce.runtime.parsers.{CodeParser, Source, SourceData}

import scala.collection.immutable.ArraySeq
import scala.collection.mutable

sealed abstract class TriggerCase(val name: String)
case object BEFORE_INSERT   extends TriggerCase("before insert")
case object BEFORE_UPDATE   extends TriggerCase("before update")
case object BEFORE_DELETE   extends TriggerCase("before delete")
case object BEFORE_UNDELETE extends TriggerCase(name = "before undelete")
case object AFTER_INSERT    extends TriggerCase(name = "after insert")
case object AFTER_UPDATE    extends TriggerCase(name = "after update")
case object AFTER_DELETE    extends TriggerCase(name = "after delete")
case object AFTER_UNDELETE  extends TriggerCase(name = "after undelete")

final case class TriggerDeclaration(
  source: Source,
  module: Module,
  nameId: Id,
  objectNameId: Id,
  typeName: TypeName,
  cases: Seq[TriggerCase],
  block: Option[Block]
) extends CST
    with ApexTriggerDeclaration
    with ApexFullDeclaration
    with DependencyHolder {

  override val idLocation: Location      = nameId.location.location
  override lazy val sourceHash: Int      = source.hash
  override def paths: ArraySeq[PathLike] = ArraySeq(location.path)

  override val moduleDeclaration: Option[Module] = Some(module)
  override val name: Name                        = typeName.name
  override val outerTypeName: Option[TypeName]   = None
  override val nature: Nature                    = TRIGGER_NATURE
  override val modifiers: ArraySeq[Modifier]     = ModifierOps.emptyModifiers
  override val isComplete: Boolean               = true
  override val inTest: Boolean                   = false

  override val superClass: Option[TypeName]           = None
  override val interfaces: ArraySeq[TypeName]         = ArraySeq()
  override val nestedTypes: ArraySeq[TypeDeclaration] = TypeDeclaration.emptyTypeDeclarations

  override val blocks: ArraySeq[BlockDeclaration] = BlockDeclaration.emptyBlockDeclarations
  override val fields: ArraySeq[FieldDeclaration] = FieldDeclaration.emptyFieldDeclarations
  override val constructors: ArraySeq[ConstructorDeclaration] =
    ConstructorDeclaration.emptyConstructorDeclarations
  override val methods: ArraySeq[MethodDeclaration] = MethodDeclaration.emptyMethodDeclarations

  private var depends: Option[SkinnySet[Dependent]] = None
  private val objectTypeName                        = TypeName(objectNameId.name, Nil, Some(TypeNames.Schema))

  override def validate(): Unit = {
    LoggerOps.debugTime(s"Validated ${location.path}") {
      nameId.validate()

      val duplicateCases = cases.groupBy(_.name).collect { case (_, Seq(_, y, _*)) => y }
      duplicateCases.foreach(
        triggerCase =>
          OrgImpl
            .logError(objectNameId.location, s"Duplicate trigger case for '${triggerCase.name}'")
      )

      val context = new TypeVerifyContext(None, this, None)
      val tdOpt   = context.getTypeAndAddDependency(objectTypeName, this)

      tdOpt match {
        case Left(error) =>
          if (!module.isGhostedType(objectTypeName))
            OrgImpl.log(error.asIssue(objectNameId.location))
        case Right(_) =>
          val triggerContext = context
            .getTypeFor(TypeNames.trigger(objectTypeName), this)
            .getOrElse(throw new NoSuchElementException)
          val tc = TriggerContext(module, triggerContext)
          module.upsertMetadata(tc)

          block.foreach(block => {
            try {
              val triggerContext = new OuterBlockVerifyContext(context, isStaticContext = false)
              triggerContext.addVar(Names.Trigger, None, tc)
              block.verify(triggerContext)
              context.typePlugin.onBlockValidated(block, isStatic = false, triggerContext)
            } finally {
              module.removeMetadata(tc)
            }
          })
      }

      depends = Some(context.dependencies)
      propagateDependencies()
      propagateOuterDependencies(new TypeCache())
    }
  }

  override def dependencies(): Iterable[Dependent] = {
    depends.map(_.toIterable).getOrElse(Array().toIterable)
  }

  override def gatherDependencies(
    dependents: mutable.Set[TypeId],
    apexOnly: Boolean,
    outerTypesOnly: Boolean,
    typeCache: TypeCache
  ): Unit = {
    depends.foreach(_.toIterable.foreach {
      case ad: ApexClassDeclaration => dependents.add(ad.outerTypeId)
      case _                        => ()
    })
  }

  override def getTypeDependencyHolders: SkinnySet[TypeId] =
    DependentType.emptyTypeDependencyHolders

  override def updateTypeDependencyHolders(holders: SkinnySet[TypeId]): Unit = {}

  override def summary: TypeSummary = {
    TypeSummary(
      sourceHash,
      location.location,
      nameId.location.location,
      name.toString,
      typeName,
      nature.value,
      modifiers,
      inTest = false,
      superClass,
      interfaces,
      ArraySeq(),
      ArraySeq(),
      ArraySeq(),
      ArraySeq(),
      ArraySeq(),
      dependencySummary()
    )
  }

  /** Locate an ApexDeclaration for the passed typeName that was extracted from location. */
  override def findDeclarationFromSourceReference(
    searchTerm: String,
    location: Location
  ): Option[ApexDeclaration] = {
    TypeName(searchTerm).toOption match {
      case Some(typeName: TypeName) =>
        TypeResolver(typeName, this).toOption.collect { case td: ApexClassDeclaration => td }
      case _ => None
    }
  }

  override def getValidationMap(line: Int, offset: Int): Map[Location, ValidationResult] = {
    val resultMap   = mutable.Map[Location, ValidationResult]()
    val typeContext = new TypeVerifyContext(None, this, Some(resultMap))
    val context     = new OuterBlockVerifyContext(typeContext, isStaticContext = false)
    context.disableIssueReporting() {
      block.foreach(block => {
        block.verify(context)
      })
    }
    resultMap.toMap
  }
}

object TriggerDeclaration {
  private val prefix: TypeName = TypeName(Name("__sfdc_trigger"))

  def create(module: Module, path: PathLike, data: SourceData): Option[TriggerDeclaration] = {
    val parser = CodeParser(path, data)
    val result = parser.parseTrigger()
    result.issues.foreach(OrgImpl.log)
    TriggerDeclaration.construct(parser, module, result.value)
  }

  def construct(
    parser: CodeParser,
    module: Module,
    trigger: TriggerUnitContext
  ): Option[TriggerDeclaration] = {
    CST.sourceContext.withValue(Some(parser.source)) {
      val ids   = CodeParser.toScala(trigger.id()).map(Id.construct)
      val cases = CodeParser.toScala(trigger.triggerCase()).map(constructCase)
      val block = CodeParser
        .toScala(trigger.block())
        .map(block => Block.constructLazy(parser, block, isTrigger = true))
      if (ids.length == 2) {
        Some(
          new TriggerDeclaration(
            parser.source,
            module,
            ids.head,
            ids(1),
            constructTypeName(module.namespace, ids.head.name),
            cases,
            block
          ).withContext(trigger)
        )
      } else {
        None
      }
    }
  }

  // Construct the trigger name, looks like a namespace but doc indicates just a prefix
  def constructTypeName(namespace: Option[Name], name: Name): TypeName = {
    val qname: String = namespace
      .map(ns => s"$prefix/${ns.value}/${name.value}")
      .getOrElse(s"$prefix/${name.value}")
    TypeName(Name(qname))
  }

  def constructCase(triggerCase: TriggerCaseContext): TriggerCase = {
    if (CodeParser.toScala(triggerCase.BEFORE()).nonEmpty) {
      if (CodeParser.toScala(triggerCase.INSERT()).nonEmpty)
        BEFORE_INSERT
      else if (CodeParser.toScala(triggerCase.UPDATE()).nonEmpty)
        BEFORE_UPDATE
      else if (CodeParser.toScala(triggerCase.DELETE()).nonEmpty)
        BEFORE_DELETE
      else
        BEFORE_UNDELETE
    } else {
      if (CodeParser.toScala(triggerCase.INSERT()).nonEmpty)
        AFTER_INSERT
      else if (CodeParser.toScala(triggerCase.UPDATE()).nonEmpty)
        AFTER_UPDATE
      else if (CodeParser.toScala(triggerCase.DELETE()).nonEmpty)
        AFTER_DELETE
      else
        AFTER_UNDELETE
    }
  }
}

final case class TriggerContext(module: Module, baseType: TypeDeclaration)
    extends BasicTypeDeclaration(PathLike.emptyPaths, module, TypeName(Names.Trigger)) {

  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    baseType.findField(name, staticContext)
  }

  override def findMethod(
    name: Name,
    params: ArraySeq[TypeName],
    staticContext: Option[Boolean],
    verifyContext: VerifyContext
  ): Either[String, MethodDeclaration] = {
    baseType.findMethod(name, params, staticContext, verifyContext)
  }
}
