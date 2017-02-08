package io.github.nahforce.apexlink.metadata

import io.github.nahforce.apexlink.utils.{Location, XMLUtils}

import scala.language.implicitConversions
import scala.xml.Elem

case class Label(location: Location, fullName: String, language: String, protect: Boolean, shortDescription: String,
                 value: String, categories: Option[Array[String]]) extends Symbol {

  val scopedName : String = "Label." + fullName

}

object Label {

  def create(elem: Elem): Option[Label] = {
    implicit def optToBool(opt: Option[_]): Boolean = opt.isDefined

    val fullName: Option[String] = XMLUtils.getSingleChildAsString(elem, "fullName")
    val language: Option[String] = XMLUtils.getSingleChildAsString(elem, "language")
    val protect: Option[Boolean] = XMLUtils.getSingleChildAsBoolean(elem, "protected")
    val shortDescription: Option[String] = XMLUtils.getSingleChildAsString(elem, "shortDescription")
    val value: Option[String] = XMLUtils.getSingleChildAsString(elem, "value")
    val categories: Option[String] = XMLUtils.getOptionalSingleChildAsString(elem, "categories")

    if (fullName && language && shortDescription && value && protect)
      Some(new Label(XMLUtils.getLocation(elem), fullName.get, language.get, protect.get, shortDescription.get, value.get,
        categories.flatMap(x => Some(x.split(',')))))
    else
      None
  }
}
