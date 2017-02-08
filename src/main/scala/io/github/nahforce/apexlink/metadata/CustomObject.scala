package io.github.nahforce.apexlink.metadata

import io.github.nahforce.apexlink.utils.{Location, XMLUtils}

import scala.language.implicitConversions
import scala.xml.Elem

case class CustomObject(location: Location, fullName: String, fields: List[CustomObjectField]) extends Symbol {

  fields.foreach(f => f.parent = Some(this))

  val scopedName : String = fullName

}

case class CustomObjectField(location: Location, fullName: String, typeName: String) extends Symbol {

  lazy val scopedName : String = parent.get.scopedName + "." + fullName

}

object CustomObject {

  def create(fullName: String, elem: Elem): Option[CustomObject] = {

    val fields = XMLUtils.getMultipleChildren(elem, "fields").map(f => createField(f))
    Some(new CustomObject(XMLUtils.getLocation(elem), fullName, fields.flatten))
  }

  def createField(elem: Elem): Option[CustomObjectField] = {
    implicit def optToBool(opt: Option[_]): Boolean = opt.isDefined

    val fullName: Option[String] = XMLUtils.getSingleChildAsString(elem, "fullName")
    val typeName: Option[String] = XMLUtils.getSingleChildAsString(elem, "type")

    if (fullName && typeName)
      Some(CustomObjectField(XMLUtils.getLocation(elem), fullName.get, typeName.get))
    else
      None
  }
}
