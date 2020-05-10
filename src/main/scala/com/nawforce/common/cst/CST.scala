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

import com.nawforce.common.api.{Name, TypeName}
import com.nawforce.common.diagnostics.Issue
import com.nawforce.common.documents.RangeLocationImpl
import com.nawforce.common.names._
import com.nawforce.common.org.OrgImpl
import com.nawforce.common.path.PathLike
import com.nawforce.runtime.parsers.ApexParser._
import com.nawforce.runtime.parsers.{CodeParser, Source}

import scala.util.DynamicVariable

// Internal exception for when things go badly wrong in the CST handling
class CSTException extends Exception

// Information about current parsing context
case class CSTParsingContext(path: PathLike, lineAdjust: Int=0, columnAdjust: Int=0)

/** Base for all CST nodes, provides some basic location handling, this is mutable for historic reasons, you must
  * call withContext() on all CST nodes for them to pick up the location information from the parser. It also
  * supports lines & column adjustments for when we re-parse blocks, see LazyBlock for details.
  */
abstract class CST {
  var location: RangeLocationImpl = _

  def withContext(context: CodeParser.ParserRuleContext): this.type = {
    location = CST.sourceContext.value.get.getRangeLocation(context)
    this
  }
}

object CST {
  // Nasty hack to allow content information to be set globally and accessed as needed
  val sourceContext: DynamicVariable[Option[Source]] = new DynamicVariable(None)
}

final case class Id(name: Name) extends CST {
  def validate(): Unit = {
    val illegalError = name.isLegalIdentifier
    if (illegalError.nonEmpty)
      OrgImpl.log(Issue.illegalIdentifier(location, name, illegalError.get))
    else if (name.isReservedIdentifier)
      OrgImpl.log(Issue.reservedIdentifier(location, name))
  }
}

object Id {
  def construct(idContext: IdContext): Id = {
    Id(Name(CodeParser.getText(idContext))).withContext(idContext)
  }

  def constructAny(idContext: AnyIdContext): Id = {
    Id(Name(CodeParser.getText(idContext))).withContext(idContext)
  }
}

final case class QualifiedName(names: List[Name]) extends CST {
  def asTypeName(): TypeName = TypeName(names.reverse)
}

object QualifiedName {
  def construct(aList: List[QualifiedNameContext]): List[QualifiedName] = {
    aList.map(n => QualifiedName.construct(n))
  }

  def construct(qualifiedName: QualifiedNameContext): QualifiedName = {
    val ids: Seq[IdContext] = CodeParser.toScala(qualifiedName.id())
    QualifiedName(ids.toList.map(id => Name(CodeParser.getText(id)))).withContext(qualifiedName)
  }
}

final case class Annotation(name: QualifiedName, elementValuePairs: List[ElementValuePair], elementValue: Option[ElementValue]) extends CST

object Annotation {
  def construct(annotation: AnnotationContext): Annotation = {
    val elementValue =
      CodeParser.toScala(annotation.elementValue())
        .map(ElementValue.construct)
    val elementValuePairs =
      CodeParser.toScala(annotation.elementValuePairs())
        .map(ElementValuePairs.construct).getOrElse(Nil)

    Annotation(QualifiedName.construct(annotation.qualifiedName()),
      elementValuePairs, elementValue).withContext(annotation)
  }
}

sealed abstract class ElementValue() extends CST

final case class ExpressionElementValue(expression: Expression) extends ElementValue

final case class AnnotationElementValue(annotation: Annotation) extends ElementValue

final case class ArrayInitializerElementValue(arrayInitializer: ElementValueArrayInitializer) extends ElementValue

object ElementValue {
  def construct(aList: List[ElementValueContext]): List[ElementValue] = {
    aList.map(x => ElementValue.construct(x))
  }

  def construct(elementValue: ElementValueContext): ElementValue = {
    val expression = CodeParser.toScala(elementValue.expression())
    val annotation = CodeParser.toScala(elementValue.annotation())
    val arrayInitializer = CodeParser.toScala(elementValue.elementValueArrayInitializer())

    if (expression.nonEmpty) {
      ExpressionElementValue(Expression.construct(expression.get)).withContext(elementValue)
    } else if (annotation.nonEmpty) {
      AnnotationElementValue(Annotation.construct(annotation.get)).withContext(elementValue)
    } else if (arrayInitializer.nonEmpty) {
      ArrayInitializerElementValue(ElementValueArrayInitializer.construct(
        arrayInitializer.get)).withContext(elementValue)
    } else {
      throw new CSTException()
    }
  }
}

final case class ElementValueArrayInitializer(elementValues: List[ElementValue]) extends CST

object ElementValueArrayInitializer {
  def construct(from: ElementValueArrayInitializerContext): ElementValueArrayInitializer = {
    val elements: Seq[ElementValueContext] = CodeParser.toScala(from.elementValue())
    ElementValueArrayInitializer(elements.toList.map(x => ElementValue.construct(x))).withContext(from)
  }
}

final case class ElementValuePair(id: String, elementValue: ElementValue) extends CST

object ElementValuePair {
  def construct(aList: Seq[ElementValuePairContext]): Seq[ElementValuePair] = {
    aList.map(x => ElementValuePair.construct(x))
  }

  def construct(from: ElementValuePairContext): ElementValuePair = {
    ElementValuePair(CodeParser.getText(from.id()),
      ElementValue.construct(from.elementValue())).withContext(from)
  }
}

object ElementValuePairs {
  def construct(from: ElementValuePairsContext): List[ElementValuePair] = {
    if (from != null) {
      val pairs: Seq[ElementValuePairContext] = CodeParser.toScala(from.elementValuePair())
      pairs.toList.map(x => ElementValuePair.construct(x))
    } else {
      List()
    }
  }
}


