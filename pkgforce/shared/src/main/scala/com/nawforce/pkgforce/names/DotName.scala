/*
 Copyright (c) 2019 Kevin Jones, All rights reserved.
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
 */
package com.nawforce.pkgforce.names

/**
  * A qualified name with notional 'dot' separators
  */
final case class DotName(names: Seq[Name]) {

  val isCompound: Boolean = names.size > 1

  def head: DotName      = DotName(Seq(names.head))
  def tail: DotName      = DotName(names.tail)
  def headNames: DotName = DotName(names.reverse.tail.reverse)
  def tailNames: DotName = DotName(names.tail)
  def firstName: Name    = names.head
  def lastName: Name     = names.last

  def append(name: Name): DotName  = DotName(names :+ name)
  def prepend(name: Name): DotName = DotName(name +: names)
  def prepend(name: Option[Name]): DotName = {
    name match {
      case Some(value) => DotName(value +: names)
      case _           => this
    }
  }

  def asTypeName(): TypeName = {
    TypeName(names.reverse)
  }

  override def toString: String = names.mkString(".")
}

object DotName {
  def apply(name: String): DotName = {
    DotName(name.split('.').toSeq.map(p => Name(p)))
  }
  def apply(name: Name): DotName = {
    DotName(Seq(name))
  }
}
