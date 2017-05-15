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
package io.github.nawforce.apexlink.metadata

import io.github.nawforce.apexlink.utils.{Location, XMLUtils}

import scala.language.implicitConversions
import scala.xml.Elem

case class CustomObject(location: Location, fullName: String, fields: List[CustomObjectField]) extends Symbol {

  fields.foreach(f => f.parent = Some(this))

  val scopedName: String = fullName

}

case class CustomObjectField(location: Location, fullName: String, typeName: Option[String]) extends Symbol {

  lazy val scopedName: String = parent.get.scopedName + "." + fullName

}

object CustomObject {

  def create(fullName: String, elem: Elem): Option[CustomObject] = {

    val fields = XMLUtils.getMultipleChildren(elem, "fields").map(f => createField(f))
    Some(new CustomObject(XMLUtils.getLocation(elem), fullName, fields.flatten))
  }

  def createField(elem: Elem): Option[CustomObjectField] = {
    implicit def optToBool(opt: Option[_]): Boolean = opt.isDefined

    val fullName: Option[String] = XMLUtils.getSingleChildAsString(elem, "fullName")
    val typeName: Option[String] = XMLUtils.getOptionalSingleChildAsString(elem, "type")

    if (fullName && typeName)
      Some(CustomObjectField(XMLUtils.getLocation(elem), fullName.get, typeName))
    else
      None
  }
}
