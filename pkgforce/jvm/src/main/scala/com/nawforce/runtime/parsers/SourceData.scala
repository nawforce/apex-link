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

package com.nawforce.runtime.parsers

import com.nawforce.apexparser.CaseInsensitiveInputStream
import com.nawforce.pkgforce.parsers.UTF8Decode
import com.nawforce.runtime.SourceBlob
import org.antlr.v4.runtime.{CharStream, CharStreams}

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets
import scala.util.hashing.MurmurHash3

final case class SourceData(
  source: Array[Byte],
  offset: Int,
  length: Int,
  var sourceHash: Option[Int] = None,
  var isASCII: Option[Boolean] = None
) {

  lazy val hash: Int = sourceHash.getOrElse(MurmurHash3.bytesHash(source))

  def subdata(startChar: Int, stopBeforeChar: Int): SourceData = {

    if (isASCII.isEmpty)
      isASCII = Some(UTF8Decode.isASCII(source, offset, length))

    if (isASCII.contains(true)) {
      new SourceData(source, offset + startChar, stopBeforeChar - startChar, Some(hash), isASCII)
    } else {
      val startOffset = UTF8Decode.getCharOffsetFrom(source, offset, startChar)
      val endOffset   = UTF8Decode.getCharOffsetFrom(source, startOffset, stopBeforeChar - startChar)
      val subLength   = endOffset - startOffset
      new SourceData(source, startOffset, subLength, Some(hash), isASCII)
    }
  }

  def asStream: CharStream = {
    CharStreams.fromStream(new ByteArrayInputStream(source, offset, length), StandardCharsets.UTF_8)
  }

  def asInsensitiveStream: CaseInsensitiveInputStream = {
    new CaseInsensitiveInputStream(
      CharStreams.fromStream(new ByteArrayInputStream(source, offset, length))
    )
  }

  def asUTF8: Array[Byte] = {
    if (offset == 0 && length == source.length)
      source
    else
      source.slice(offset, offset + length)
  }

  def asString: String = {
    new String(asUTF8, StandardCharsets.UTF_8)
  }
}

object SourceData {
  def apply(value: String): SourceData = {
    apply(value.getBytes(StandardCharsets.UTF_8))
  }

  def apply(value: SourceBlob): SourceData = {
    new SourceData(value, offset = 0, length = value.length)
  }
}
