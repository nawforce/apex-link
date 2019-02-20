package com.nawforce.apexlink.metadata

trait Symbol {
  var parent: Option[Symbol] = None

  val scopedName: String
}
