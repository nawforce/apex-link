package com.nawforce.common.api

import upickle.default.{macroRW, ReadWriter => RW}

/**
  * identifier for a specific type within an Org. This is used to avoid external dependency on the type name
  * structures used in the library. Currently we only support conversion to a string representation for display
  * purposes.
  */
case class TypeIdentifier(namespace: Name, typeName: TypeName) {

  lazy val safeNamespace: Option[Name] = Option(namespace)

  override def toString: String = {
    typeName.toString + Option(namespace).map(n => s" (${n.toString})").getOrElse("")
  }

}

object TypeIdentifier {
  implicit val rw: RW[TypeIdentifier] = macroRW
}




