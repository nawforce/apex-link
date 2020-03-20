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

import com.nawforce.common.api.ServerOps
import com.nawforce.common.cst._
import com.nawforce.common.documents.LineLocationImpl
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.org.{OrgImpl, PackageImpl}
import com.nawforce.common.path.PathLike
import com.nawforce.common.types._
import com.nawforce.runtime.parsers.ApexParser.{TriggerCaseContext, TriggerUnitContext}
import com.nawforce.runtime.parsers.CodeParser

sealed abstract class TriggerCase(val name: String)
case object BEFORE_INSERT extends TriggerCase("before insert")
case object BEFORE_UPDATE extends TriggerCase("before update")
case object BEFORE_DELETE extends TriggerCase("before delete")
case object BEFORE_UNDELETE extends TriggerCase(name = "before undelete")
case object AFTER_INSERT extends TriggerCase(name = "after insert")
case object AFTER_UPDATE extends TriggerCase(name = "after update")
case object AFTER_DELETE extends TriggerCase(name= "after delete")
case object AFTER_UNDELETE extends TriggerCase(name = "after undelete")

class TriggerDeclaration(path: PathLike, val pkg: PackageImpl, name: Id, objectName: Id,
                         cases: Seq[TriggerCase], block: Block)
  extends NamedTypeDeclaration(pkg, TypeName(Name(s"__sfdc_trigger/${objectName.name}"))) {

  override val isSearchable: Boolean = false

  private val objectTypeName = TypeName(objectName.name)

  override def validate(): Unit = {
    ServerOps.debugTime(s"Validated $path") {
      name.validate()

      val duplicateCases = cases.groupBy(_.name).collect { case (_, Seq(_, y, _*)) => y }
      duplicateCases.foreach(triggerCase =>
        OrgImpl.logError(objectName.location, s"Duplicate trigger case for '${triggerCase.name}'"))

      val context = new TriggerVerifyContext(pkg, this)
      val tdOpt = context.getTypeAndAddDependency(objectTypeName, Some(this))
      tdOpt match {
        case Left(error) =>
          OrgImpl.log(error.asIssue(objectName.location))
        case Right(_) =>
          val triggerContext = context.getTypeFor(TypeName.trigger(objectTypeName), Some(this)).right.get
          val tc = TriggerContext(pkg, triggerContext)
          pkg.upsertMetadata(tc)

          try {
            val blockContext = new OuterBlockVerifyContext(context, isStaticContext = false)
            blockContext.addVar(Name.Trigger, tc)
            block.verify(blockContext)
          } finally {
            pkg.removeMetadata(tc)
          }
      }
    }
  }
}

object TriggerDeclaration {
  def create(pkg: PackageImpl, path: PathLike, data: String): Seq[TriggerDeclaration] = {
    val parser = new CodeParser(path, data)
    parser.parseTrigger() match {
      case Left(err) =>
        OrgImpl.logError(LineLocationImpl(path.toString, err.line), err.message)
        Nil
      case Right(cu) =>
        Seq(TriggerDeclaration.construct(pkg, path, cu, new ConstructContext()))
    }
  }

  def construct(pkg: PackageImpl, path: PathLike, trigger: TriggerUnitContext, context: ConstructContext)
    : TriggerDeclaration = {
    val ids = CodeParser.toScala(trigger.id())
    val cases = CodeParser.toScala(trigger.triggerCase()).map(constructCase)
    new TriggerDeclaration(path, pkg,
      Id.construct(ids.head, context), Id.construct(ids(1), context), cases, Block.construct(trigger.block(), context))
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
  extends NamedTypeDeclaration(pkg, TypeName(Name.Trigger)) {

  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    baseType.findField(name, staticContext)
  }

  override def findMethod(name: Name, params: Seq[TypeName], staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Seq[MethodDeclaration] = {
    baseType.findMethod(name, params, staticContext, verifyContext)
  }
}


