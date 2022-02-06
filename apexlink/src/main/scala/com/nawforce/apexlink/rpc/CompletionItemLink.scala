package com.nawforce.apexlink.rpc

import com.nawforce.apexlink.types.core.{FieldDeclaration, MethodDeclaration, TypeDeclaration}
import com.nawforce.pkgforce.parsers.{CLASS_NATURE, ENUM_NATURE, INTERFACE_NATURE}
import com.nawforce.pkgforce.path.Location
import io.github.shogowada.scala.jsonrpc.serializers.JSONRPCPickler.{macroRW, ReadWriter => RW}

case class CompletionItemLink(label: String, kind: String, detail: String = null)

object CompletionItemLink {
  implicit val rw: RW[CompletionItemLink] = macroRW
  implicit val rwLocation: RW[Location]   = macroRW

  def apply(td: TypeDeclaration): Option[CompletionItemLink] = {
    val detail = td.modifiers.map(_.name).mkString(" ")
    td.nature match {
      case CLASS_NATURE     => Some(CompletionItemLink(td.typeName.name.value, "Class", detail))
      case INTERFACE_NATURE => Some(CompletionItemLink(td.typeName.name.value, "Interface", detail))
      case ENUM_NATURE      => Some(CompletionItemLink(td.typeName.name.value, "Enum", detail))
      case _                => None
    }
  }

  def apply(field: FieldDeclaration): CompletionItemLink = {
    CompletionItemLink(field.name.toString, "Field", field.toString)
  }

  def apply(method: MethodDeclaration): CompletionItemLink = {
    CompletionItemLink(
      method.name.toString + "(" + method.parameters.map(_.name.toString()).mkString(", ") + ")",
      "Method",
      method.toString
    )
  }

}
