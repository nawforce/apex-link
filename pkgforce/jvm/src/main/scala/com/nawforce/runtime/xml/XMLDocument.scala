/*
 Copyright (c) 2019 Kevin Jones, All rights reserved.
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
package com.nawforce.runtime.xml

import com.nawforce.pkgforce.diagnostics._
import com.nawforce.pkgforce.path.{Location, PathLike}
import com.nawforce.pkgforce.xml.{XMLDocumentLike, XMLElementLike, XMLName}
import com.nawforce.runtime.parsers.SourceData
import org.xml.sax.Locator

import java.io.ByteArrayInputStream
import javax.xml.parsers.SAXParserFactory
import scala.collection.immutable.ArraySeq
import scala.collection.mutable
import scala.xml._
import scala.xml.parsing.NoBindingFactoryAdapter

final class XMLElement(element: Elem) extends XMLElementLike {
  override lazy val line: Int = element.attribute("line").get.toString().toInt

  override lazy val name: XMLName = XMLName(element.namespace, element.label)

  override lazy val text: String = element.text

  override def getChildren(name: String): Seq[XMLElementLike] = {
    (element \ name)
      .filter(x => x.namespace == XMLDocument.sfNamespace)
      .map(n => new XMLElement(n.asInstanceOf[Elem]))
  }
}

final class XMLDocument(path: PathLike, elem: Elem) extends XMLDocumentLike(path) {
  override lazy val rootElement: XMLElementLike = new XMLElement(elem)
}

object XMLDocument {
  val sfNamespace = "http://soap.sforce.com/2006/04/metadata"

  def apply(path: PathLike, sourceData: SourceData): IssuesAnd[Option[XMLDocument]] = {
    try {
      IssuesAnd(
        Some(new XMLDocument(path, XMLLineLoader.load(new ByteArrayInputStream(sourceData.asUTF8))))
      )
    } catch {
      case e: SAXParseException =>
        IssuesAnd(
          ArraySeq(
            Issue(
              path,
              Diagnostic(
                ERROR_CATEGORY,
                Location(e.getLineNumber, e.getColumnNumber - 1),
                e.getLocalizedMessage
              )
            )
          ),
          None
        )
    }
  }
}

trait WithLocation extends NoBindingFactoryAdapter {
  private var locator: org.xml.sax.Locator = _
  private val startLines                   = mutable.Stack[Int]()

  final override def setDocumentLocator(locator: Locator): Unit = {
    this.locator = locator
    super.setDocumentLocator(locator)
  }

  final override def createNode(
    pre: String,
    label: String,
    attrs: MetaData,
    scope: NamespaceBinding,
    children: List[Node]
  ): Elem = {
    val newAttrs = attrs.append(Attribute("line", Text(startLines.pop().toString), Null))
    super.createNode(pre, label, newAttrs, scope, children)
  }

  final override def startElement(
    uri: scala.Predef.String,
    _localName: scala.Predef.String,
    name: scala.Predef.String,
    attributes: org.xml.sax.Attributes
  ): scala.Unit = {
    startLines.push(locator.getLineNumber)
    super.startElement(uri, _localName, name, attributes)
  }
}

object XMLLineLoader extends factory.XMLLoader[Elem] {
  override def adapter = new parsing.NoBindingFactoryAdapter with WithLocation

  private lazy val cachedParser = {
    val f = SAXParserFactory.newInstance()
    f.setNamespaceAware(false)
    f.newSAXParser()
  }

  override def parser: SAXParser = {
    cachedParser.reset()
    cachedParser
  }
}
