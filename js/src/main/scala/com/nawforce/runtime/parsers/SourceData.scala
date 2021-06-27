/*
 [The "BSD licence"]
 Copyright (c) 2020 Kevin Jones
 All rights reserved.

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

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.nawforce.runtime.parsers

import java.nio.charset.StandardCharsets

import com.nawforce.pkgforce.parsers.UTF8Decode
import com.nawforce.runtime.SourceBlob

import scala.util.hashing.MurmurHash3

trait SourceData {
  val hash: Int
  val length: Int

  def subdata(offset: Int, length: Int): SourceData
  def asInsensitiveStream: CaseInsensitiveInputStream
  def asUTF8: Array[Byte]
  def asString: String
}

object SourceData {
  def apply(value: String): SourceData = {
    apply(value.getBytes(StandardCharsets.UTF_8))
  }

  def apply(value: SourceBlob): SourceData = {
    ByteArraySourceData(value, offset = 0, length = value.length)
  }
}

case class ByteArraySourceData(source: Array[Byte], offset: Int, length: Int) extends SourceData {
  val hash: Int = MurmurHash3.bytesHash(source)
  private lazy val isASCII = UTF8Decode.isASCII(source, offset, length)

  override def subdata(startChar: Int, stopBeforeChar: Int): ByteArraySourceData = {
    if (isASCII) {
      ByteArraySourceData(source, offset+startChar, stopBeforeChar-startChar)
    } else {
      val startOffset = UTF8Decode.getCharOffsetFrom(source, offset, startChar)
      val endOffset = UTF8Decode.getCharOffsetFrom(source, startOffset, stopBeforeChar - startChar)
      val subLength = endOffset - startOffset
      ByteArraySourceData(source, startOffset, subLength)
    }
  }

  def asInsensitiveStream: CaseInsensitiveInputStream = {
    new CaseInsensitiveInputStream(null, new String(source, offset, length))
  }

  def asUTF8: Array[Byte] = {
    source.slice(offset, offset + length)
  }

  def asString: String = {
    new String(asUTF8, StandardCharsets.UTF_8)
  }
}
