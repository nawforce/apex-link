/*
 Copyright (c) 2020 Kevin Jones, All rights reserved.
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

package com.nawforce.apexlink.names

import com.nawforce.pkgforce.names._

/** Name handling support.
  *
  * The two most visible types of name (Name & TypeName) are included in the api package. Additional support is
  * included here for caching of Name objects to reduce GC pressure, simple dot formatted names and the encoded
  * names we see used with SObjects that have suffixes such as `__c` and optional namespaces prefixes. There is
  * also some support here for legal & reserved identifier validation.
  */
object XNames {

  def apply(name: String): Name = com.nawforce.pkgforce.names.Names(name)

  lazy val Iterable: Name = XNames("Iterable")

  /** Name extensions */
  implicit class NameUtils(name: Name) {

    /** Check is name is a legal identifier, None if OK or error message string. */
    def isLegalIdentifier: Option[String] = Identifier.isLegalIdentifier(name)

    /** Check is name is a reserved identifier, None if OK or error message string. */
    def isReservedIdentifier: Boolean = Identifier.isReservedIdentifier(name)

    def isEmpty: Boolean = name.value.isEmpty

    def nonEmpty: Boolean = name.value.nonEmpty

    def contains(seq: CharSequence): Boolean = name.value.contains(seq)

    def replaceAll(regex: String, replace: String): Name =
      Name(name.value.replaceAll(regex, replace))
  }
}
