/*
 [The "BSD licence"]
 Copyright (c) 2021 Kevin Jones
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
package com.nawforce.pkgforce.stream

import com.nawforce.pkgforce.diagnostics.LocationAnd
import com.nawforce.pkgforce.documents.SourceInfo
import com.nawforce.pkgforce.names.Name
import com.nawforce.runtime.parsers.VFParser.AttributeContext
import com.nawforce.runtime.parsers.{PageParser, Source, VFParser}

import java.util.regex.{Matcher, Pattern}
import scala.collection.mutable.ArrayBuffer

abstract class VFEvent(val controllers: Array[LocationAnd[Name]],
                       val expressions: Array[LocationAnd[String]])
    extends PackageEvent {
  val sourceInfo: SourceInfo
}

object VFEvent {
  lazy val expressionMatcher: Matcher = Pattern.compile("\\{![^}]+}").matcher("")

  def extractControllers(source: Source,
                         component: VFParser.VfUnitContext,
                         isPage: Boolean): Array[LocationAnd[Name]] = {
    val root = component.element()
    PageParser
      .toScala(root.attribute())
      .flatMap(attr => {
        val location = source.getLocation(attr)
        PageParser.getText(attr.attributeName()) match {
          case "controller" => Array((location._2, extractAttributeValue(attr)))
          case "extensions" if isPage =>
            extractAttributeValue(attr).split(',').map(_.trim).map(name => (location._2, name))
          case _ => None
        }
      })
      .map(locationAndName => LocationAnd(locationAndName._1, Name(locationAndName._2)))
      .toArray
  }

  def extractExpressions(source: Source, component: VFParser.VfUnitContext): Array[LocationAnd[String]] = {
    val root: VFParser.ElementContext = component.element()
    val buffer = ArrayBuffer[LocationAnd[String]]()
    collectExpressions(source, root, buffer)
    buffer.toArray
  }

  private def collectExpressions(source: Source, element: VFParser.ElementContext,
                                 exprs: ArrayBuffer[LocationAnd[String]]): Unit = {

    PageParser
      .toScala(element.attribute())
      .foreach(attribute => {
        PageParser
          .toScala(attribute.attributeValues())
          .foreach(attrValue => {
            val attrText = PageParser.getText(attrValue)
            if (attrText.startsWith("{!") && attrText.endsWith("}"))
              exprs.addOne(LocationAnd(source.getLocation(attrValue)._2, attrText.substring(2, attrText.length - 1)))
          })
      })

    val content = PageParser.toScala(element.content())
    content.foreach(
      c =>
        PageParser
          .toScala(c.chardata())
          .foreach(charData => {
            val text = PageParser.getText(charData)
            expressionMatcher.reset(text)
            while (expressionMatcher.find()) exprs.addOne(
              LocationAnd(source.getLocation(charData)._2,
              text.substring(expressionMatcher.start() + 2, expressionMatcher.end() - 1)))
          }))

    content
      .map(c => PageParser.toScala(c.element()))
      .getOrElse(Seq())
      .foreach(el => collectExpressions(source, el, exprs))
  }

  private def extractAttributeValue(context: AttributeContext): String = {
    PageParser.toScala(context.attributeValues()).map(value => PageParser.getText(value)).mkString
  }
}
