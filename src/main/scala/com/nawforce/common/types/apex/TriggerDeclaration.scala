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
import com.nawforce.common.documents.{LineLocationImpl, LocationImpl}
import com.nawforce.common.names.{Names, TypeNames}
import com.nawforce.common.org.{OrgImpl, PackageImpl}
import com.nawforce.common.path.PathLike
import com.nawforce.common.types.core._
import com.nawforce.runtime.gc.SkinnySet
import com.nawforce.runtime.parsers.ApexParser.{TriggerCaseContext, TriggerUnitContext}
import com.nawforce.runtime.parsers.{CodeParser, Source, SourceData}

import scala.collection.mutable

sealed abstract class TriggerCase(val name: String)
case object BEFORE_INSERT extends TriggerCase("before insert")
case object BEFORE_UPDATE extends TriggerCase("before update")
case object BEFORE_DELETE extends TriggerCase("before delete")
case object BEFORE_UNDELETE extends TriggerCase(name = "before undelete")
case object AFTER_INSERT extends TriggerCase(name = "after insert")
case object AFTER_UPDATE extends TriggerCase(name = "after update")
case object AFTER_DELETE extends TriggerCase(name= "after delete")
case object AFTER_UNDELETE extends TriggerCase(name = "after undelete")

final case class TriggerDeclaration(source: Source, pkg: PackageImpl, nameId: Id, objectNameId: Id,
                                    typeName: TypeName, cases: Seq[TriggerCase], block: Block)
  extends ApexTriggerDeclaration with ApexFullDeclaration {

  override val path: PathLike  = source.path
  override val nameLocation: LocationImpl = nameId.location
  override lazy val sourceHash: Int = source.hash
  override val paths: Seq[PathLike] = Seq(path)

  override val packageDeclaration: Option[PackageImpl] = Some(pkg)
  override val name: Name = typeName.name
  override val outerTypeName: Option[TypeName] = None
  override val nature: Nature = TRIGGER_NATURE
  override val modifiers: Seq[Modifier] = Seq.empty
  override val isComplete: Boolean = true

  override val superClass: Option[TypeName] = None
  override val interfaces: Seq[TypeName] = Seq.empty
  override val nestedTypes: Seq[ApexFullDeclaration] = Seq.empty

  override val blocks: Seq[ApexInitialiserBlock] = Seq.empty
  override val fields: Seq[ApexFieldDeclaration]= Seq.empty
  override val constructors: Seq[ApexConstructorDeclaration] = Seq.empty
  override val methods: Seq[ApexMethodDeclaration]= Seq.empty

  private var depends: Option[SkinnySet[Dependent]] = None
  private val objectTypeName = TypeName(objectNameId.name, Nil, Some(TypeNames.Schema))

  override def validate(withPropagation: Boolean): Unit = {
    ServerOps.debugTime(s"Validated $path") {
      nameId.validate()

      val duplicateCases = cases.groupBy(_.name).collect { case (_, Seq(_, y, _*)) => y }
      duplicateCases.foreach(triggerCase =>
        OrgImpl.logError(objectNameId.location, s"Duplicate trigger case for '${triggerCase.name}'"))

      val context = new TypeVerifyContext(None, this, withPropagation)
      val tdOpt = context.getTypeAndAddDependency(objectTypeName, Some(this))

      tdOpt match {
        case Left(error) =>
          if (!pkg.isGhostedType(objectTypeName))
            OrgImpl.log(error.asIssue(objectNameId.location))
        case Right(_) =>
          val triggerContext = context.getTypeFor(TypeNames.trigger(objectTypeName), Some(this)).right.get
          val tc = TriggerContext(pkg, triggerContext)
          pkg.upsertMetadata(tc)

          try {
            val blockContext = new OuterBlockVerifyContext(context, isStaticContext = false)
            blockContext.addVar(Names.Trigger, tc)
            block.verify(blockContext)
          } finally {
            pkg.removeMetadata(tc)
          }
      }

      depends = Some(context.dependencies)
      if (withPropagation)
        propagateOuterDependencies()
    }
  }

  override def collectDependenciesByTypeName(dependents: mutable.Set[TypeId]): Unit = {
    depends.foreach(_.toIterable.foreach {
      case ad: ApexClassDeclaration => dependents.add(ad.outerTypeId)
      case _ => ()
    })
  }

  override def getTypeDependencyHolders: mutable.Set[TypeId] = mutable.Set.empty

  override def updateTypeDependencyHolders(holders: mutable.Set[TypeId]): Unit = {}

  // Override to provide location information
  override def summary: TypeSummary = {
    TypeSummary(
      0,
      Some(new RangeLocation(nameId.location.start.toPosition, nameId.location.end.toPosition)),
      name.toString,
      typeName,
      nature.value, modifiers.map(_.toString).sorted.toList,
      superClass,
      interfaces.toList,
      blocks.map(_.summary).toList,
      fields.map(_.summary).sortBy(_.name).toList,
      constructors.map(_.summary).sortBy(_.parameters.size).toList,
      methods.map(_.summary).sortBy(_.name).toList,
      nestedTypes.map(_.summary).sortBy(_.name).toList,
      dependencySummary()
    )
  }
}

object TriggerDeclaration {
  private val prefix: TypeName = TypeName(Name("__sfdc_trigger"))

  def create(pkg: PackageImpl, path: PathLike, data: SourceData): Option[TriggerDeclaration] = {
    val parser = CodeParser(path, data)
    parser.parseTrigger() match {
      case Left(err) =>
        OrgImpl.logError(LineLocationImpl(path.toString, err.line), err.message)
        None
      case Right(cu) =>
        Some(TriggerDeclaration.construct(parser, pkg, path, cu))
    }
  }

  def construct(parser: CodeParser, pkg: PackageImpl, path: PathLike, trigger: TriggerUnitContext)
    : TriggerDeclaration = {
    CST.sourceContext.withValue(Some(parser.source)) {
      val ids = CodeParser.toScala(trigger.id()).map(Id.construct)
      val cases = CodeParser.toScala(trigger.triggerCase()).map(constructCase)
      new TriggerDeclaration(parser.source, pkg, ids.head, ids(1), constructTypeName(pkg.namespace, ids.head.name), cases,
        Block.construct(parser, trigger.block()))
    }
  }

  // Construct the trigger name, looks like a namespace but doc indicates just a prefix
  def constructTypeName(namespace: Option[Name], name: Name): TypeName = {
    val qname: String = namespace.map(ns => s"$prefix/${ns.value}/${name.value}")
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

final case class TriggerContext(pkg: PackageImpl, baseType: TypeDeclaration)
  extends BasicTypeDeclaration(Seq.empty, pkg, TypeName(Names.Trigger)) {

  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    baseType.findField(name, staticContext)
  }

  override def findMethod(name: Name, params: Seq[TypeName], staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Seq[MethodDeclaration] = {
    baseType.findMethod(name, params, staticContext, verifyContext)
  }
}


