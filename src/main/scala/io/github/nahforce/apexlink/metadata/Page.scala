package io.github.nahforce.apexlink.metadata

import io.github.nahforce.apexlink.utils.{Location, XMLUtils}

import scala.xml.Elem

case class Page(location: Location, fullName: String) extends Symbol {
  val scopedName : String = fullName
}

object Page {
  def create(fullName: String, elem: Elem): Option[Page] = {
    Some(new Page(XMLUtils.getLocation(elem), fullName))
  }
}

