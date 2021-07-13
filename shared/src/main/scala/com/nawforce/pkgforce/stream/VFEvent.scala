package com.nawforce.pkgforce.stream

import com.nawforce.pkgforce.names.Name
import com.nawforce.runtime.parsers.VFParser.AttributeContext
import com.nawforce.runtime.parsers.{PageParser, VFParser}

import java.util.regex.{Matcher, Pattern}
import scala.collection.mutable.ArrayBuffer

abstract class VFEvent(controllers: Array[Name], expressions: Array[String]) extends PackageEvent

object VFEvent {
  lazy val expressionMatcher: Matcher = Pattern.compile("\\{![^}]+}").matcher("")

  def extractControllers(component: VFParser.VfUnitContext, isPage: Boolean): Array[Name] = {
    val root = component.element()
    PageParser
      .toScala(root.attribute())
      .flatMap(attr => {
        PageParser.getText(attr.attributeName()) match {
          case "controller"           => Array(extractAttributeValue(attr))
          case "extensions" if isPage => extractAttributeValue(attr).split(',').map(_.trim)
          case _                      => None
        }
      })
      .map(Name(_))
      .toArray
  }

  def extractExpressions(component: VFParser.VfUnitContext): Array[String] = {
    val root: VFParser.ElementContext = component.element()
    val buffer = ArrayBuffer[String]()
    collectExpressions(root, buffer)
    buffer.toArray
  }

  private def collectExpressions(element: VFParser.ElementContext,
                                 exprs: ArrayBuffer[String]): Unit = {

    PageParser
      .toScala(element.attribute())
      .foreach(attribute => {
        PageParser
          .toScala(attribute.attributeValues())
          .foreach(attrValue => {
            val attrText = PageParser.getText(attrValue)
            if (attrText.startsWith("{!") && attrText.endsWith("}"))
              exprs.addOne(attrText.substring(2, attrText.length-1))
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
              text.substring(expressionMatcher.start() + 2, expressionMatcher.end() - 1))
          }))

    content
      .map(c => PageParser.toScala(c.element()))
      .getOrElse(Seq())
      .foreach(el => collectExpressions(el, exprs))
  }

  private def extractAttributeValue(context: AttributeContext): String = {
    PageParser.toScala(context.attributeValues()).map(value => PageParser.getText(value)).mkString
  }
}
