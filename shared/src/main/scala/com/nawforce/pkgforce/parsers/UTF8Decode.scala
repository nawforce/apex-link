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

import java.nio.charset.StandardCharsets

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
      val charLength = sequenceLength(buffer(at))
      if (charLength == 1) {
        remaining -= 1
      } else {
        remaining -= new String(buffer, at, charLength, StandardCharsets.UTF_8).length
      }
      at += charLength
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
