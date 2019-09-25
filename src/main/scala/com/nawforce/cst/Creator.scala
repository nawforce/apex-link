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

import com.nawforce.names.{EncodedName, TypeName}
import com.nawforce.parsers.ApexParser._
import com.nawforce.types.SObjectDeclaration

import scala.collection.JavaConverters._

final case class CreatedName(idPairs: List[IdCreatedNamePair]) extends CST {
  override def children(): List[CST] = idPairs

  def verify(context: ExpressionVerifyContext): ExprContext = {
    // TODO: withOuter may overwite inner namespace, how do we check for nonsense
    val typeName = idPairs.tail.map(_.typeName).foldLeft(idPairs.head.typeName){
      (acc: TypeName, typeName: TypeName) => typeName.withOuter(Some(acc))
    }

    val newType = context.getTypeAndAddDependency(typeName, context.thisType).toOption
    if (newType.nonEmpty) {
      ExprContext(isStatic = false, Some(newType.get))
    } else {
      context.missingType(location, typeName)
      ExprContext.empty
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

  val typeName: TypeName = EncodedName(id.name).asTypeName.withParams(types)
}

object IdCreatedNamePair {
  def construct(aList: List[IdCreatedNamePairContext], context: ConstructContext): List[IdCreatedNamePair] = {
    aList.map(x => IdCreatedNamePair.construct(x, context))
  }

  def construct(from: IdCreatedNamePairContext, context: ConstructContext): IdCreatedNamePair = {
    IdCreatedNamePair(Id.construct(from.id(), context),
      if (from.typeList() != null)
        TypeList.construct(from.typeList())
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

  def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    assert(input.declaration.nonEmpty)

    val inter = createdName.verify(context)
    if (inter.declaration.isEmpty) {
      return inter
    }

    // Intercept CustomObject construction to handle name=value args
    if (arrayCreatorRest.isEmpty) {
      inter.declaration.get match {
        case co: SObjectDeclaration =>
          return co.validateConstructor(input, this, context)
        case _ => ()
      }
    }

    /* TODO
    classCreatorRest.foreach(_.verify(input, context))
    arrayCreatorRest.foreach(_.verify(input, context))
    mapCreatorRest.foreach(_.verify(input, context))
    setCreatorRest.foreach(_.verify(input, context))
     */

    // TODO
    ExprContext.empty
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

final case class ClassCreatorRest(arguments: List[Expression]) extends CST {
  override def children(): List[CST] = arguments

  def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    arguments.foreach(_.verify(input, context))
    // TODO
    ExprContext.empty
  }
}

object ClassCreatorRest {
  def construct(from: ClassCreatorRestContext, context: ConstructContext): ClassCreatorRest = {
    ClassCreatorRest(Arguments.construct(from.arguments(), context)).withContext(from, context)
  }
}

final case class ArrayCreatorRest(expressions: Option[Expression], arrayInitializer: Option[ArrayInitializer],
                                 ) extends CST {
  override def children(): List[CST] = List[CST]() ++ expressions ++ arrayInitializer

  def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    expressions.foreach(_.verify(input, context))
    arrayInitializer.foreach(_.verify(input, context))
    // TODO
    ExprContext.empty
  }
}

object ArrayCreatorRest {
  def construct(from: ArrayCreatorRestContext, context: ConstructContext): ArrayCreatorRest = {
    ArrayCreatorRest(
      Option(from.expression()).map(Expression.construct(_, context)),
      Option(from.arrayInitializer()).map(ArrayInitializer.construct(_, context))
    )
  }
}

final case class ArrayInitializer(variableInitializers: List[VariableInitializer]) extends CST {
  override def children(): List[CST] = variableInitializers

  def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    variableInitializers.foreach(_.verify(input, context))
    // TODO
    ExprContext.empty
  }
}

object ArrayInitializer {
  def construct(from: ArrayInitializerContext, context: ConstructContext): ArrayInitializer = {
    val initializers: Seq[VariableInitializerContext] = from.variableInitializer().asScala
    ArrayInitializer(VariableInitializer.construct(initializers.toList, context)).withContext(from, context)
  }
}

final case class MapCreatorRest(pairs: List[MapCreatorRestPair]) extends CST {
  override def children(): List[CST] = pairs

  def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    pairs.foreach(_.verify(input, context))
    // TODO
    ExprContext.empty
  }
}

object MapCreatorRest {
  def construct(from: MapCreatorRestContext, context: ConstructContext): MapCreatorRest = {
    val pairs: Seq[MapCreatorRestPairContext] = from.mapCreatorRestPair().asScala
    MapCreatorRest(MapCreatorRestPair.construct(pairs.toList, context)).withContext(from, context)
  }
}

final case class MapCreatorRestPair(from: Expression, to: Expression) extends CST {
  override def children(): List[CST] = from :: to :: Nil

  def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    from.verify(input, context)
    to.verify(input, context)
    // TODO
    ExprContext.empty
  }
}

object MapCreatorRestPair {
  def construct(aList: List[MapCreatorRestPairContext], context: ConstructContext): List[MapCreatorRestPair] = {
    aList.map(x => MapCreatorRestPair.construct(x, context))
  }

  def construct(from: MapCreatorRestPairContext, context: ConstructContext): MapCreatorRestPair = {
    MapCreatorRestPair(
      Expression.construct(from.expression(0), context),
      Expression.construct(from.expression(1), context)
    ).withContext(from, context)
  }
}

final case class SetCreatorRest(parts: List[Expression]) extends CST {
  override def children(): List[CST] = parts

   def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    parts.foreach(_.verify(input, context))
    // TODO
    ExprContext.empty
  }
}

object SetCreatorRest {
  def construct(from: SetCreatorRestContext, context: ConstructContext): SetCreatorRest = {
    val parts: Seq[ExpressionContext] = from.expression().asScala
    SetCreatorRest(Expression.construct(parts.toList, context)).withContext(from, context)
  }
}


