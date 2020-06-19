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

import com.nawforce.common.api.TypeName
import com.nawforce.common.names.TypeNames._
import com.nawforce.common.names.{EncodedName, TypeNames, _}
import com.nawforce.common.org.OrgImpl
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
  def construct(from: CreatedNameContext): CreatedName = {
    val pairs: Seq[IdCreatedNamePairContext] = CodeParser.toScala(from.idCreatedNamePair())
    CreatedName(IdCreatedNamePair.construct(pairs.toList)).withContext(from)
  }
}

final case class IdCreatedNamePair(id: Id, types: Seq[TypeName]) extends CST {
  val typeName: TypeName = {
    val encName = EncodedName(id.name)
    if (encName.ext.nonEmpty)
      TypeName(encName.fullName, types, Some(TypeNames.Schema)).intern
    else
      TypeName(encName.fullName, types, None).intern
  }
}

object IdCreatedNamePair {
  def construct(aList: List[IdCreatedNamePairContext]): List[IdCreatedNamePair] = {
    aList.map(x => IdCreatedNamePair.construct(x))
  }

  def construct(from: IdCreatedNamePairContext): IdCreatedNamePair = {
    IdCreatedNamePair(Id.constructAny(from.anyId()),
      CodeParser.toScala(from.typeList())
        .map(tl => TypeList.construct(tl)).getOrElse(Seq())
    ).withContext(from)
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
  def construct(from: CreatorContext): Creator = {
    val rest: Option[CreatorRest] =
      CodeParser.toScala(from.noRest()).map(NoRest.construct)
        .orElse(CodeParser.toScala(from.classCreatorRest()).map(ClassCreatorRest.construct))
        .orElse(CodeParser.toScala(from.arrayCreatorRest()).map(ArrayCreatorRest.construct))
        .orElse(CodeParser.toScala(from.mapCreatorRest()).map(MapCreatorRest.construct))
        .orElse(CodeParser.toScala(from.setCreatorRest()).map(SetCreatorRest.construct))
    Creator(CreatedName.construct(from.createdName()), rest.get).withContext(from)
  }
}

sealed abstract class CreatorRest extends CST {
  def verify(creating: ExprContext, input: ExprContext, context: ExpressionVerifyContext): Unit
}

final class NoRest extends CreatorRest {
  override def verify(creating: ExprContext, input: ExprContext, context: ExpressionVerifyContext): Unit = {}
}

object NoRest {
  def construct(from: NoRestContext): NoRest = {
    new NoRest().withContext(from)
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
  def construct(from: ClassCreatorRestContext): ClassCreatorRest = {
    ClassCreatorRest(Arguments.construct(from.arguments())).withContext(from)
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
  def construct(from: ArrayCreatorRestContext): ArrayCreatorRest = {
    ArrayCreatorRest(
      CodeParser.toScala(from.expression()).map(Expression.construct),
      CodeParser.toScala(from.arrayInitializer()).map(ArrayInitializer.construct)
    )
  }
}

final case class ArrayInitializer(expressions: Seq[Expression]) extends CST {
  def verify(input: ExprContext, context: ExpressionVerifyContext): Unit = {
    expressions.foreach(_.verify(input, context))
  }
}

object ArrayInitializer {
  def construct(from: ArrayInitializerContext): ArrayInitializer = {
    val initializers: Seq[ExpressionContext] = CodeParser.toScala(from.expression())
    ArrayInitializer(Expression.construct(initializers.toList)).withContext(from)
  }
}

final case class MapCreatorRest(pairs: List[MapCreatorRestPair]) extends CreatorRest {
  override def verify(creating: ExprContext, input: ExprContext, context: ExpressionVerifyContext): Unit = {
    assert(creating.typeDeclarationOpt.nonEmpty)
    val td = creating.typeDeclarationOpt.get
    val enclosedTypes = td.typeName.getMapType

    if (enclosedTypes.isEmpty) {
      OrgImpl.logError(location, s"Expression pair list construction is only supported for Map types, not '${td.typeName}'")
      return
    }

    val keyType = context.getTypeAndAddDependency(enclosedTypes.get._1, context.thisType)
    if (keyType.isLeft) {
      OrgImpl.log(keyType.left.get.asIssue(location))
      return
    }

    val valueType = context.getTypeAndAddDependency(enclosedTypes.get._2, context.thisType)
    if (valueType.isLeft) {
      if (!context.pkg.isGhostedType(enclosedTypes.get._2))
        OrgImpl.log(valueType.left.get.asIssue(location))
      return
    }

    pairs.foreach(_.verify(input, context))
  }
}

object MapCreatorRest {
  def construct(from: MapCreatorRestContext): MapCreatorRest = {
    val pairs: Seq[MapCreatorRestPairContext] = CodeParser.toScala(from.mapCreatorRestPair())
    MapCreatorRest(MapCreatorRestPair.construct(pairs.toList)).withContext(from)
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
  def construct(aList: List[MapCreatorRestPairContext]): List[MapCreatorRestPair] = {
    aList.map(x => MapCreatorRestPair.construct(x))
  }

  def construct(from: MapCreatorRestPairContext): MapCreatorRestPair = {
    val expressions = CodeParser.toScala(from.expression())
    MapCreatorRestPair(
      Expression.construct(expressions.head),
      Expression.construct(expressions(1))
    ).withContext(from)
  }
}

/* This is really Set & List creator, where TYPE{expr, expr, ...} form is allowed, it's different from array */
final case class SetCreatorRest(parts: Seq[Expression]) extends CreatorRest {
  override def verify(creating: ExprContext, input: ExprContext, context: ExpressionVerifyContext): Unit = {
     assert(creating.typeDeclarationOpt.nonEmpty)
     val td = creating.typeDeclarationOpt.get
     val enclosedType = td.typeName.getSetOrListType

     if (enclosedType.isEmpty) {
       OrgImpl.logError(location, s"Expression list construction is only supported for Set or List types, not '${td.typeName}'")
       return
     }

     context.getTypeAndAddDependency(enclosedType.get, context.thisType) match {
       case Left(error) =>
         if (!context.pkg.isGhostedType(enclosedType.get))
           OrgImpl.log(error.asIssue(location))
       case Right(_) =>
         // FUTURE: Validate the expressions are assignable to 'creating'
         parts.foreach(_.verify(input, context))
     }
  }
}

object SetCreatorRest {
  def construct(from: SetCreatorRestContext): SetCreatorRest = {
    val parts: Seq[ExpressionContext] = CodeParser.toScala(from.expression())
    SetCreatorRest(Expression.construct(parts.toList)).withContext(from)
  }
}



