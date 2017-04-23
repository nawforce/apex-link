/*
 [The "BSD licence"]
 Copyright (c) 2017 Kevin Jones
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
package io.github.nahforce.apexlink.utils

import java.io.{InputStream, InputStreamReader, Reader}

import org.antlr.v4.runtime.{ANTLRInputStream, IntStream}

/**
  * NOTE - in order for this CaseInsensitiveInputStream to work all constants
  * in .g4 grammar must be either *lower* case or *dual-case*
  */
class CaseInsensitiveInputStream(r: Reader, initialSize: Int, readChunkSize: Int)
  extends ANTLRInputStream(r, initialSize, readChunkSize) {

  //lazy is important here because need initiated data[], which is loaded in super class
  private lazy val lowercaseData: Array[Char] = data.map(_.toLower)

  def this(r: Reader) {
    this(r, initialSize = 1024, readChunkSize = 1024)
  }

  def this(input: InputStream) {
    this(new InputStreamReader(input), initialSize = 1024, readChunkSize = 1024)
  }

  override def LA(index: Int): Int = {
    var i = index
    if (i == 0) {
      return 0
    }
    if (i < 0) {
      i += 1
      if ((p + i - 1) < 0) {
        return IntStream.EOF
      }
    }
    if ((p + i - 1) >= n) {
      return IntStream.EOF
    }

    if (null != lowercaseData) {
      lowercaseData(p + i - 1)
    } else {
      data(p + i - 1).toLower
    }
  }

  def dump(): Unit = {
    var i = 0
    var value = 0

    do {
      value = LA(i)
      i += 1
      print(value.asInstanceOf[Char])
    } while (value != IntStream.EOF)
  }
}
