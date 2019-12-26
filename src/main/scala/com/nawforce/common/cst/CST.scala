/*
 [The "BSD licence"]
 Copyright (c) 2017 Kevin Jones
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
package com.nawforce.common.cst

import com.nawforce.common.api.Org
import com.nawforce.common.documents.{Location, Position, RangeLocation}
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.runtime.parsers.ApexParser._
import com.nawforce.common.parsers.CSTRange
import com.nawforce.common.path.{PathFactory, PathLike}
import com.nawforce.runtime.parsers.CodeParser

import scala.util.DynamicVariable

class CSTException extends Exception

abstract class CST {
  private var range: CSTRange = _
  private var positionAdjust: (Int, Int) = _

  lazy val location: Location = {
    RangeLocation(
      PathFactory(range.path),
      Position(range.startLine, range.startPosition)
        .adjust(positionAdjust._1, positionAdjust._2),
      Position(range.stopLine, range.stopPosition)
        .adjust(positionAdjust._1, positionAdjust._2)
    )
  }

  def getPath: PathLike = PathFactory(range.path)

  def withContext(context: CodeParser.ParserRuleContext, constructContext: ConstructContext): this.type = {
    range = CodeParser.getRange(context)
    positionAdjust = CST.rangeAdjust.value
    this
  }
}

object CST {
  val rangeAdjust: DynamicVariable[(Int, Int)] = new DynamicVariable[(Int,Int)]((0,0))
}

final case class Id(name: Name) extends CST {
  def validate(): Unit = {
    if (!name.isLegalIdentifier)
      Org.logMessage(location, s"This identifier is not legal in Apex, '$name'")
    else if (name.isReservedIdentifier)
      Org.logMessage(location, s"This identifier is reserved in Apex, '$name'")
  }
}

object Id {
  def construct(idContext: IdContext, context: ConstructContext): Id = {
    Id(Name(CodeParser.getText(idContext))).withContext(idContext, context)
  }
}

final case class QualifiedName(names: List[Name]) extends CST {
  def asTypeName(): TypeName = TypeName(names.reverse)
}

object QualifiedName {
  def construct(aList: List[QualifiedNameContext], context: ConstructContext): List[QualifiedName] = {
    aList.map(n => QualifiedName.construct(n, context))
  }

  def construct(qualifiedName: QualifiedNameContext, context: ConstructContext): QualifiedName = {
    val ids: Seq[IdContext] = CodeParser.toScala(qualifiedName.id())
    QualifiedName(ids.toList.map(id => Name(CodeParser.getText(id)))).withContext(qualifiedName, context)
  }
}

final case class Annotation(name: QualifiedName, elementValuePairs: List[ElementValuePair], elementValue: Option[ElementValue]) extends CST

object Annotation {
  def construct(annotation: AnnotationContext, context: ConstructContext): Annotation = {
    val elementValue =
      CodeParser.toScala(annotation.elementValue())
        .map(ElementValue.construct(_, context))
    val elementValuePairs =
      CodeParser.toScala(annotation.elementValuePairs())
        .map(ElementValuePairs.construct(_, context)).getOrElse(Nil)

    Annotation(QualifiedName.construct(annotation.qualifiedName(), context),
      elementValuePairs, elementValue).withContext(annotation, context)
  }
}

sealed abstract class ElementValue() extends CST

//final case class ExpressionElementValue(expression: Expression) extends ElementValue

final case class AnnotationElementValue(annotation: Annotation) extends ElementValue

final case class ArrayInitializerElementValue(arrayInitializer: ElementValueArrayInitializer) extends ElementValue

object ElementValue {
  def construct(aList: List[ElementValueContext], context: ConstructContext): List[ElementValue] = {
    aList.map(x => ElementValue.construct(x, context))
  }

  def construct(elementValue: ElementValueContext, context: ConstructContext): ElementValue = {

    // TODO: Fix up when we have expression support
    //val expresison = CodeParser.toScala(elementValue.expression())
    val annotation = CodeParser.toScala(elementValue.annotation())
    val arrayInitializer = CodeParser.toScala(elementValue.elementValueArrayInitializer())

    /*if (expression.nonEmpty) {
      ExpressionElementValue(Expression.construct(elementValue.expression(), context)).withContext(elementValue, context)
    } else*/ if (annotation.nonEmpty) {
      AnnotationElementValue(Annotation.construct(annotation.get, context)).withContext(elementValue, context)
    } else if (arrayInitializer.nonEmpty) {
      ArrayInitializerElementValue(ElementValueArrayInitializer.construct(
        arrayInitializer.get, context)).withContext(elementValue, context)
    } else {
      throw new CSTException()
    }
  }
}

final case class ElementValueArrayInitializer(elementValues: List[ElementValue]) extends CST

object ElementValueArrayInitializer {
  def construct(from: ElementValueArrayInitializerContext, context: ConstructContext): ElementValueArrayInitializer = {
    val elements: Seq[ElementValueContext] = CodeParser.toScala(from.elementValue())
    ElementValueArrayInitializer(elements.toList.map(x => ElementValue.construct(x, context))).withContext(from, context)
  }
}

final case class ElementValuePair(id: String, elementValue: ElementValue) extends CST

object ElementValuePair {
  def construct(aList: Seq[ElementValuePairContext], context: ConstructContext): Seq[ElementValuePair] = {
    aList.map(x => ElementValuePair.construct(x, context))
  }

  def construct(from: ElementValuePairContext, context: ConstructContext): ElementValuePair = {
    ElementValuePair(CodeParser.getText(from.id()),
      ElementValue.construct(from.elementValue(), context)).withContext(from, context)
  }
}

object ElementValuePairs {
  def construct(from: ElementValuePairsContext, context: ConstructContext): List[ElementValuePair] = {
    if (from != null) {
      val pairs: Seq[ElementValuePairContext] = CodeParser.toScala(from.elementValuePair())
      pairs.toList.map(x => ElementValuePair.construct(x, context))
    } else {
      List()
    }
  }
}


