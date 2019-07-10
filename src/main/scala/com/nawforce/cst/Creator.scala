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
 3. the name of the author may not be used to endorse or promote products
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
package com.nawforce.cst

import com.nawforce.parsers.ApexParser.{CreatedNameContext, CreatorContext, IdCreatedNamePairContext}
import com.nawforce.types.TypeName

import scala.collection.JavaConverters._

final case class CreatedName(idPairs: List[IdCreatedNamePair]) extends CST {
  override def children(): List[CST] = idPairs

  def verify(context: ExpressionVerifyContext): Unit = {
    context.getTypeAndAddDependency(createTypeName(None, idPairs.map(_.typeName)))
    // TODO: Add error if not found
  }

  private def createTypeName(outer: Option[TypeName], names: Seq[TypeName]): TypeName = {
    names match {
      case hd +: Seq() => hd.withOuter(outer)
      case hd +: tl => createTypeName(Some(hd.withOuter(outer)), tl)
    }
  }
}

object CreatedName {
  def construct(from: CreatedNameContext, context: ConstructContext): CreatedName = {
    val pairs: Seq[IdCreatedNamePairContext] = from.idCreatedNamePair().asScala
    CreatedName(IdCreatedNamePair.construct(pairs.toList, context)).withContext(from, context)
  }
}

final case class IdCreatedNamePair(id: Id, types: Seq[TypeName]) extends CST {
  override def children(): List[CST] = Nil

  val typeName: TypeName = TypeName(id.name, types, None)
}

object IdCreatedNamePair {
  def construct(aList: List[IdCreatedNamePairContext], context: ConstructContext): List[IdCreatedNamePair] = {
    aList.map(x => IdCreatedNamePair.construct(x, context))
  }

  def construct(from: IdCreatedNamePairContext, context: ConstructContext): IdCreatedNamePair = {
    IdCreatedNamePair(Id.construct(from.id(), context),
      if (from.typeList() != null)
        TypeList.construct(from.typeList(), context)
      else
        Seq()
    ).withContext(from, context)
  }
}

final case class Creator(createdName: CreatedName,
                         classCreatorRest: Option[ClassCreatorRest],
                         arrayCreatorRest: Option[ArrayCreatorRest],
                         mapCreatorRest: Option[MapCreatorRest],
                         setCreatorRest: Option[SetCreatorRest]) extends CST {
  override def children(): List[CST] =
    List(createdName) ++ classCreatorRest ++ arrayCreatorRest ++ mapCreatorRest ++setCreatorRest

  def verify(context: ExpressionVerifyContext): Unit = {
    createdName.verify(context)
    classCreatorRest.foreach(_.verify(context))
    arrayCreatorRest.foreach(_.verify(context))
    mapCreatorRest.foreach(_.verify(context))
    setCreatorRest.foreach(_.verify(context))
  }
}

object Creator {
  def construct(from: CreatorContext, context: ConstructContext): Creator = {
    Creator(
      CreatedName.construct(from.createdName(), context),
      Option(from.classCreatorRest()).map(ClassCreatorRest.construct(_, context)),
      Option(from.arrayCreatorRest()).map(ArrayCreatorRest.construct(_, context)),
      Option(from.mapCreatorRest()).map(MapCreatorRest.construct(_, context)),
      Option(from.setCreatorRest()).map(SetCreatorRest.construct(_, context))
    ).withContext(from, context)
  }
}
