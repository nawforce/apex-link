package io.github.nahforce.apexlink.utils

import org.xml.sax.Locator

import scala.collection.mutable
import scala.xml.parsing.NoBindingFactoryAdapter
import scala.xml.{Elem, factory, parsing, _}

trait WithLocation extends NoBindingFactoryAdapter {
  private var locator: org.xml.sax.Locator = _
  private val startLines = mutable.Stack[Int]()

  // Get location
  abstract override def setDocumentLocator(locator: Locator) {
    this.locator = locator
    super.setDocumentLocator(locator)
  }

  abstract override def createNode(pre: String, label: String, attrs: MetaData, scope: NamespaceBinding, children: List[Node]): Elem = (
    super.createNode(pre, label, attrs, scope, children)
      % Attribute("line", Text(startLines.pop.toString), Null)
    )

  abstract override def startElement(uri: scala.Predef.String, _localName: scala.Predef.String, qname: scala.Predef.String, attributes: org.xml.sax.Attributes): scala.Unit = {
    startLines.push(locator.getLineNumber)
    super.startElement(uri, _localName, qname, attributes)
  }
}

object XMLLineLoader extends factory.XMLLoader[Elem] {
  override def adapter = new parsing.NoBindingFactoryAdapter with WithLocation
}
