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

import upickle.default.{macroRW, ReadWriter => RW}

/** Case insensitive string for symbol names.
  *
  * The value of the Name is stored as is but equality and hashing are performed against a normalised lower case
  * value.
  */
@upickle.implicits.key("Name")
final case class Name(value: String) {

  override val hashCode: Int = value.toLowerCase.hashCode

  def canEqual(that: Any): Boolean = that.isInstanceOf[Name]

  override def equals(that: Any): Boolean = {
    that match {
      case otherName: Name =>
        otherName.canEqual(this) && otherName.value.equalsIgnoreCase(value)
      case _ => false
    }
  }

  override def toString: String = value
}

object Name {
  implicit val rw: RW[Name] = macroRW

  val empty: Name = new Name("")
}
