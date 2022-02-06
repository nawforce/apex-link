/*
 Copyright (c) 2017 Kevin Jones, All rights reserved.
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
package com.nawforce.apexlink.cst

import com.nawforce.apexlink.diagnostics.IssueOps
import com.nawforce.apexlink.names.XNames.NameUtils
import com.nawforce.apexlink.org.OrgImpl
import com.nawforce.apexparser.ApexParser._
import com.nawforce.pkgforce.names.{Name, Names, TypeName}
import com.nawforce.pkgforce.path.{PathLike, Positionable}
import com.nawforce.runtime.parsers.{CodeParser, Source}

import scala.util.DynamicVariable

// Information about current parsing context
case class CSTParsingContext(path: PathLike, lineAdjust: Int = 0, columnAdjust: Int = 0)

/** Base for all CST nodes, provides some basic location handling.
  *
  * This is mutable for historic reasons, you must call withContext() on all CST nodes for them to pick up the
  * location information from the parser. It also supports lines & column adjustments for when we re-parse blocks,
  * see LazyBlock for details.
  */
class CST extends Positionable {

  def withContext(context: CodeParser.ParserRuleContext): this.type = {
    if (context != null)
      CST.sourceContext.value.get.stampLocation(this, context)
    this
  }
}

object CST {
  // Nasty hack to allow content information to be set globally and accessed as needed
  val sourceContext: DynamicVariable[Option[Source]] = new DynamicVariable(None)
}

final case class Id(name: Name) extends CST {
  def validate(): Unit = {
    if (name.nonEmpty) {
      val illegalError = name.isLegalIdentifier
      if (illegalError.nonEmpty)
        OrgImpl.log(IssueOps.illegalIdentifier(location, name, illegalError.get))
      else if (name.isReservedIdentifier)
        OrgImpl.log(IssueOps.reservedIdentifier(location, name))
    }
  }
}

object Id {
  def construct(idContext: IdContext): Id = {
    Id(Names(CodeParser.getText(idContext))).withContext(idContext)
  }

  def constructAny(idContext: AnyIdContext): Id = {
    Id(Names(CodeParser.getText(idContext))).withContext(idContext)
  }
}

final case class QualifiedName(names: Seq[Name]) extends CST {
  def asTypeName(): TypeName = TypeName(names.reverse)
}

object QualifiedName {
  def construct(qualifiedNames: Seq[QualifiedNameContext]): Seq[QualifiedName] = {
    qualifiedNames.flatMap(n => QualifiedName.construct(n))
  }

  def construct(qualifiedName: QualifiedNameContext): Option[QualifiedName] = {
    Option(qualifiedName).map(qualifiedName => {
      val ids = CodeParser.toScala(qualifiedName.id())
      QualifiedName(ids.map(id => Names(CodeParser.getText(id)))).withContext(qualifiedName)
    })
  }
}

final case class Annotation(
  name: Option[QualifiedName],
  elementValuePairs: List[ElementValuePair],
  elementValue: Option[ElementValue]
) extends CST

object Annotation {
  def construct(annotation: AnnotationContext): Annotation = {
    val elementValue =
      CodeParser
        .toScala(annotation.elementValue())
        .flatMap(ElementValue.construct)
    val elementValuePairs =
      CodeParser
        .toScala(annotation.elementValuePairs())
        .map(ElementValuePairs.construct)
        .getOrElse(Nil)

    Annotation(QualifiedName.construct(annotation.qualifiedName()), elementValuePairs, elementValue)
      .withContext(annotation)
  }
}

sealed abstract class ElementValue() extends CST

final case class ExpressionElementValue(expression: Expression) extends ElementValue

final case class AnnotationElementValue(annotation: Annotation) extends ElementValue

final case class ArrayInitializerElementValue(arrayInitializer: ElementValueArrayInitializer)
    extends ElementValue

object ElementValue {
  def construct(elementValue: ElementValueContext): Option[ElementValue] = {
    val expression       = CodeParser.toScala(elementValue.expression())
    val annotation       = CodeParser.toScala(elementValue.annotation())
    val arrayInitializer = CodeParser.toScala(elementValue.elementValueArrayInitializer())

    if (expression.nonEmpty) {
      Some(ExpressionElementValue(Expression.construct(expression.get)).withContext(elementValue))
    } else if (annotation.nonEmpty) {
      Some(AnnotationElementValue(Annotation.construct(annotation.get)).withContext(elementValue))
    } else if (arrayInitializer.nonEmpty) {
      Some(
        ArrayInitializerElementValue(ElementValueArrayInitializer.construct(arrayInitializer.get))
          .withContext(elementValue)
      )
    } else {
      None
    }
  }
}

final case class ElementValueArrayInitializer(elementValues: Seq[ElementValue]) extends CST

object ElementValueArrayInitializer {
  def construct(from: ElementValueArrayInitializerContext): ElementValueArrayInitializer = {
    val elements = CodeParser.toScala(from.elementValue())
    ElementValueArrayInitializer(elements.flatMap(x => ElementValue.construct(x)))
      .withContext(from)
  }
}

final case class ElementValuePair(id: String, elementValue: Option[ElementValue]) extends CST

object ElementValuePair {
  def construct(aList: Seq[ElementValuePairContext]): Seq[ElementValuePair] = {
    aList.map(x => ElementValuePair.construct(x))
  }

  def construct(from: ElementValuePairContext): ElementValuePair = {
    ElementValuePair(CodeParser.getText(from.id()), ElementValue.construct(from.elementValue()))
      .withContext(from)
  }
}

object ElementValuePairs {
  def construct(from: ElementValuePairsContext): List[ElementValuePair] = {
    if (from != null) {
      val pairs = CodeParser.toScala(from.elementValuePair())
      pairs.toList.map(x => ElementValuePair.construct(x))
    } else {
      List()
    }
  }
}
