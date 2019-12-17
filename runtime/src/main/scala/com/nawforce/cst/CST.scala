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
package com.nawforce.cst

import java.nio.file.Path

import com.nawforce.api.Org
import com.nawforce.documents.{Location, Position, RangeLocation}
import com.nawforce.names.{Name, TypeName}
import com.nawforce.parsers.ApexParser._
import com.nawforce.parsers.CaseInsensitiveInputStream
import com.nawforce.runtime.Path
import org.antlr.v4.runtime.ParserRuleContext

import scala.collection.JavaConverters._
import scala.util.DynamicVariable

class CSTException extends Exception

abstract class CST {
  private var path: java.nio.file.Path = _
  private var startLine: Int = _
  private var startPosition: Int = _
  private var stopLine: Int = _
  private var stopPosition: Int = _
  private var positionAdjust: (Int, Int) = _

  lazy val location: Location = {
    RangeLocation(
      com.nawforce.runtime.Path(path),
      Position(startLine, startPosition)
        .adjust(positionAdjust._1, positionAdjust._2),
      Position(stopLine, stopPosition)
        .adjust(positionAdjust._1, positionAdjust._2)
    )
  }

  def getPath: java.nio.file.Path = path

  def withContext(context: ParserRuleContext, constructContext: ConstructContext): this.type = {
    startLine = context.getStart.getLine
    startPosition = context.getStart.getCharPositionInLine
    stopLine = context.getStop.getLine
    stopPosition = context.getStop.getCharPositionInLine + context.getStop.getText.length
    path = context.getStart.getInputStream.asInstanceOf[CaseInsensitiveInputStream].path
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
    Id(Name(idContext.getText)).withContext(idContext, context)
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
    val ids: Seq[IdContext] = qualifiedName.id().asScala
    QualifiedName(ids.toList.map(id => Name(id.getText))).withContext(qualifiedName, context)
  }
}

final case class Annotation(name: QualifiedName, elementValuePairs: List[ElementValuePair], elementValue: Option[ElementValue]) extends CST

object Annotation {
  def construct(annotation: AnnotationContext, context: ConstructContext): Annotation = {
    val elementValue =
      if (annotation.elementValue() != null) {
        Some(ElementValue.construct(annotation.elementValue(), context))
      } else {
        None
      }
    Annotation(QualifiedName.construct(annotation.qualifiedName(), context),
      ElementValuePairs.construct(annotation.elementValuePairs(), context),
      elementValue).withContext(annotation, context)
  }
}

sealed abstract class ElementValue() extends CST

final case class ExpressionElementValue(expression: Expression) extends ElementValue

final case class AnnotationElementValue(annotation: Annotation) extends ElementValue

final case class ArrayInitializerElementValue(arrayInitializer: ElementValueArrayInitializer) extends ElementValue

object ElementValue {
  def construct(aList: List[ElementValueContext], context: ConstructContext): List[ElementValue] = {
    aList.map(x => ElementValue.construct(x, context))
  }

  def construct(elementValue: ElementValueContext, context: ConstructContext): ElementValue = {
    if (elementValue.expression() != null) {
      ExpressionElementValue(Expression.construct(elementValue.expression(), context)).withContext(elementValue, context)
    } else if (elementValue.annotation() != null) {
      AnnotationElementValue(Annotation.construct(elementValue.annotation(), context)).withContext(elementValue, context)
    } else if (elementValue.elementValueArrayInitializer() != null) {
      ArrayInitializerElementValue(ElementValueArrayInitializer.construct(
        elementValue.elementValueArrayInitializer(), context)).withContext(elementValue, context)
    } else {
      throw new CSTException()
    }
  }
}

final case class ElementValueArrayInitializer(elementValues: List[ElementValue]) extends CST

object ElementValueArrayInitializer {
  def construct(from: ElementValueArrayInitializerContext, context: ConstructContext): ElementValueArrayInitializer = {
    val elements: Seq[ElementValueContext] = from.elementValue().asScala
    ElementValueArrayInitializer(elements.toList.map(x => ElementValue.construct(x, context))).withContext(from, context)
  }
}

final case class ElementValuePair(id: String, elementValue: ElementValue) extends CST

object ElementValuePair {
  def construct(aList: List[ElementValuePairContext], context: ConstructContext): List[ElementValuePair] = {
    aList.map(x => ElementValuePair.construct(x, context))
  }

  def construct(from: ElementValuePairContext, context: ConstructContext): ElementValuePair = {
    ElementValuePair(from.id().getText, ElementValue.construct(from.elementValue(), context)).withContext(from, context)
  }
}

object ElementValuePairs {
  def construct(from: ElementValuePairsContext, context: ConstructContext): List[ElementValuePair] = {
    if (from != null) {
      val pairs: Seq[ElementValuePairContext] = from.elementValuePair().asScala
      pairs.toList.map(x => ElementValuePair.construct(x, context))
    } else {
      List()
    }
  }
}


