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
package com.nawforce.common.cst

import com.nawforce.common.api.Org
import com.nawforce.common.names.{EncodedName, TypeName}
import com.nawforce.runtime.parsers.ApexParser._
import com.nawforce.runtime.parsers.CodeParser

final case class CreatedName(idPairs: List[IdCreatedNamePair]) extends CST {
  def verify(context: ExpressionVerifyContext): ExprContext = {
    val typeName = idPairs.tail.map(_.typeName).foldLeft(idPairs.head.typeName){
      (acc: TypeName, typeName: TypeName) => typeName.withTail(acc)
    }

    val newType = context.getTypeAndAddDependency(typeName, context.thisType).toOption
    if (newType.nonEmpty) {
      ExprContext(isStatic = Some(false), newType.get)
    } else {
      context.missingType(location, typeName)
      ExprContext.empty
    }
  }
}

object CreatedName {
  def construct(from: CreatedNameContext, context: ConstructContext): CreatedName = {
    val pairs: Seq[IdCreatedNamePairContext] = CodeParser.toScala(from.idCreatedNamePair())
    CreatedName(IdCreatedNamePair.construct(pairs.toList, context)).withContext(from, context)
  }
}

final case class IdCreatedNamePair(id: Id, types: Seq[TypeName]) extends CST {
  val typeName: TypeName = {
    val encName = EncodedName(id.name)
    if (encName.ext.nonEmpty)
      TypeName(encName.fullName, types, Some(TypeName.Schema))
    else
      TypeName(encName.fullName, types, None)
  }
}

object IdCreatedNamePair {
  def construct(aList: List[IdCreatedNamePairContext], context: ConstructContext): List[IdCreatedNamePair] = {
    aList.map(x => IdCreatedNamePair.construct(x, context))
  }

  def construct(from: IdCreatedNamePairContext, context: ConstructContext): IdCreatedNamePair = {
    IdCreatedNamePair(Id.construct(from.id(), context),
      CodeParser.toScala(from.typeList())
        .map(tl => TypeList.construct(tl)).getOrElse(Seq())
    ).withContext(from, context)
  }
}

final case class Creator(createdName: CreatedName, creatorRest: CreatorRest) extends CST {
  def verify(input: ExprContext, context: ExpressionVerifyContext): ExprContext = {
    assert(input.typeDeclarationOpt.nonEmpty)

    val inter = createdName.verify(context)
    if (inter.typeDeclarationOpt.isEmpty) {
      return inter
    }

    creatorRest.verify(inter, input, context)
    if (creatorRest.isInstanceOf[ArrayCreatorRest]) {
      val listType = inter.typeName.asListOf
      val listDeclaration = context.getTypeAndAddDependency(listType, context.thisType)
      ExprContext(isStatic = Some(false), listDeclaration.toOption.get)
    } else {
      inter
    }
  }
}

object Creator {
  def construct(from: CreatorContext, context: ConstructContext): Creator = {
    val rest: Option[CreatorRest] =
      CodeParser.toScala(from.noRest()).map(NoRest.construct(_, context))
        .orElse(CodeParser.toScala(from.classCreatorRest()).map(ClassCreatorRest.construct(_, context)))
        .orElse(CodeParser.toScala(from.arrayCreatorRest()).map(ArrayCreatorRest.construct(_, context)))
        .orElse(CodeParser.toScala(from.mapCreatorRest()).map(MapCreatorRest.construct(_, context)))
        .orElse(CodeParser.toScala(from.setCreatorRest()).map(SetCreatorRest.construct(_, context)))
    Creator(CreatedName.construct(from.createdName(), context), rest.get).withContext(from, context)
  }
}

sealed abstract class CreatorRest extends CST {
  def verify(creating: ExprContext, input: ExprContext, context: ExpressionVerifyContext): Unit
}

final class NoRest extends CreatorRest {
  override def verify(creating: ExprContext, input: ExprContext, context: ExpressionVerifyContext): Unit = {}
}

object NoRest {
  def construct(from: NoRestContext, context: ConstructContext): NoRest = {
    new NoRest().withContext(from, context)
  }
}

final case class ClassCreatorRest(arguments: Seq[Expression]) extends CreatorRest {
  override def verify(creating: ExprContext, input: ExprContext, context: ExpressionVerifyContext): Unit = {
    assert(creating.typeDeclarationOpt.nonEmpty)
    val td = creating.typeDeclarationOpt.get

    if (td.isFieldConstructed)
      td.validateFieldConstructorArguments(input, arguments, context)
    else
      // FUTURE: Is constructor available
      arguments.foreach(_.verify(input, context))
  }
}

object ClassCreatorRest {
  def construct(from: ClassCreatorRestContext, context: ConstructContext): ClassCreatorRest = {
    ClassCreatorRest(Arguments.construct(from.arguments(), context)).withContext(from, context)
  }
}

final case class ArrayCreatorRest(expressions: Option[Expression], arrayInitializer: Option[ArrayInitializer])
  extends CreatorRest {
  override def verify(creating: ExprContext, input: ExprContext, context: ExpressionVerifyContext): Unit = {
    assert(creating.typeDeclarationOpt.nonEmpty)

    // FUTURE: Expression type should be number
    expressions.foreach(_.verify(input, context))

    // FUTURE: initializer type should match 'creating'
    arrayInitializer.foreach(_.verify(input, context))
  }
}

object ArrayCreatorRest {
  def construct(from: ArrayCreatorRestContext, context: ConstructContext): ArrayCreatorRest = {
    ArrayCreatorRest(
      CodeParser.toScala(from.expression()).map(Expression.construct(_, context)),
      CodeParser.toScala(from.arrayInitializer()).map(ArrayInitializer.construct(_, context))
    )
  }
}

final case class ArrayInitializer(expressions: Seq[Expression]) extends CST {
  def verify(input: ExprContext, context: ExpressionVerifyContext): Unit = {
    expressions.foreach(_.verify(input, context))
  }
}

object ArrayInitializer {
  def construct(from: ArrayInitializerContext, context: ConstructContext): ArrayInitializer = {
    val initializers: Seq[ExpressionContext] = CodeParser.toScala(from.expression())
    ArrayInitializer(Expression.construct(initializers.toList, context)).withContext(from, context)
  }
}

final case class MapCreatorRest(pairs: List[MapCreatorRestPair]) extends CreatorRest {
  override def verify(creating: ExprContext, input: ExprContext, context: ExpressionVerifyContext): Unit = {
    assert(creating.typeDeclarationOpt.nonEmpty)
    val td = creating.typeDeclarationOpt.get
    val enclosedTypes = td.typeName.getMapType

    if (enclosedTypes.isEmpty) {
      Org.logMessage(location, s"Expression pair list construction is only supported for Map types, not '${td.typeName}'")
      return
    }

    val keyType = context.getTypeAndAddDependency(enclosedTypes.get._1, context.thisType)
    if (keyType.isLeft) {
      Org.logMessage(location, keyType.left.get.toString)
      return
    }

    val valueType = context.getTypeAndAddDependency(enclosedTypes.get._2, context.thisType)
    if (valueType.isLeft) {
      if (!context.pkg.isGhostedType(enclosedTypes.get._2))
        Org.logMessage(location, valueType.left.get.toString)
      return
    }

    pairs.foreach(_.verify(input, context))
  }
}

object MapCreatorRest {
  def construct(from: MapCreatorRestContext, context: ConstructContext): MapCreatorRest = {
    val pairs: Seq[MapCreatorRestPairContext] = CodeParser.toScala(from.mapCreatorRestPair())
    MapCreatorRest(MapCreatorRestPair.construct(pairs.toList, context)).withContext(from, context)
  }
}

final case class MapCreatorRestPair(from: Expression, to: Expression) extends CST {
  def verify(input: ExprContext, context: ExpressionVerifyContext): Unit = {
    // FUTURE: Validate the expressions are assignable to Map
    from.verify(input, context)
    to.verify(input, context)
  }
}

object MapCreatorRestPair {
  def construct(aList: List[MapCreatorRestPairContext], context: ConstructContext): List[MapCreatorRestPair] = {
    aList.map(x => MapCreatorRestPair.construct(x, context))
  }

  def construct(from: MapCreatorRestPairContext, context: ConstructContext): MapCreatorRestPair = {
    val expressions = CodeParser.toScala(from.expression())
    MapCreatorRestPair(
      Expression.construct(expressions(0), context),
      Expression.construct(expressions(1), context)
    ).withContext(from, context)
  }
}

/* This is really Set & List creator, where TYPE{expr, expr, ...} form is allowed, it's different from array */
final case class SetCreatorRest(parts: Seq[Expression]) extends CreatorRest {
  override def verify(creating: ExprContext, input: ExprContext, context: ExpressionVerifyContext): Unit = {
     assert(creating.typeDeclarationOpt.nonEmpty)
     val td = creating.typeDeclarationOpt.get
     val enclosedType = td.typeName.getSetOrListType

     if (enclosedType.isEmpty) {
       Org.logMessage(location, s"Expression list construction is only supported for Set or List types, not '${td.typeName}'")
       return
     }

     context.getTypeAndAddDependency(enclosedType.get, context.thisType) match {
       case Left(error) =>
         if (!context.pkg.isGhostedType(enclosedType.get))
           Org.logMessage(location, error.toString)
       case Right(_) =>
         // FUTURE: Validate the expressions are assignable to 'creating'
         parts.foreach(_.verify(input, context))
     }
  }
}

object SetCreatorRest {
  def construct(from: SetCreatorRestContext, context: ConstructContext): SetCreatorRest = {
    val parts: Seq[ExpressionContext] = CodeParser.toScala(from.expression())
    SetCreatorRest(Expression.construct(parts.toList, context)).withContext(from, context)
  }
}



