package io.github.nahforce.apexlink.metadata

trait Symbol {
  var parent : Option[Symbol] = None

  val scopedName : String
}
