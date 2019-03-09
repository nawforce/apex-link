package com.nawforce.metadata

trait Symbol {
  var parent: Option[Symbol] = None

  val scopedName: String
}
