/*
 Copyright (c) 2021 Kevin Jones, All rights reserved.
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
package com.nawforce.pkgforce.parsers

/* Utilities for helping find character positions in UTF-8 byte arrays. Character positions can be ambiguous as they
 * have commonly be used to refer to UTF-16 positions rather than Unicode code points. This code assumes the latter
 * model to match ANTLR CharStream handling of positions.
 */
object UTF8Decode {

  def isASCII(buffer: Array[Byte], offset: Int, length: Int): Boolean = {
    var at    = offset
    val limit = offset + length
    while (at < limit) {
      if ((0xff & buffer(at).asInstanceOf[Int]) >= 0x80)
        return false
      at += 1
    }
    true
  }

  def getCharOffsetFrom(buffer: Array[Byte], offset: Int, charCount: Int): Int = {
    var remaining = charCount
    var at        = offset
    while (remaining > 0) {
      at += sequenceLength(buffer(at))
      remaining -= 1
    }
    at
  }

  private def sequenceLength(leadingByte: Byte): Int = {
    val unsigned: Int = 0xff & leadingByte.asInstanceOf[Int]
    if (unsigned < 0x80) 1
    else if ((unsigned >> 5) == 0x6) 2
    else if ((unsigned >> 4) == 0xe) 3
    else if ((unsigned >> 3) == 0x1e) 4
    else
      throw new IllegalArgumentException(s"Expecting UTF-8 data, found leading byte: $leadingByte")
  }
}
