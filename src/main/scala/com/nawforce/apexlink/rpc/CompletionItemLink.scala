package com.nawforce.apexlink.rpc

import com.nawforce.pkgforce.path.Location
import io.github.shogowada.scala.jsonrpc.serializers.JSONRPCPickler.{macroRW, ReadWriter => RW}

case class CompletionItemLink(label: String, kind: String)

object CompletionItemLink {
  implicit val rw: RW[CompletionItemLink] = macroRW
  implicit val rwLocation: RW[Location] = macroRW
}
