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
package com.nawforce.common.memory

import scala.collection.mutable

/** Low memory set.
  *
  * Uses a an array for small size before using a set.
  */
class SkinnySet[T <: AnyRef] {
  private var arrayOf = new mutable.ArrayBuffer[T](4)
  private var setOf: mutable.Set[T] = _

  def isEmpty: Boolean = {
    if (setOf != null)
      setOf.isEmpty
    else
      arrayOf.isEmpty
  }

  def nonEmpty: Boolean = !isEmpty

  def size: Int = {
    if (setOf != null)
      setOf.size
    else
      arrayOf.size
  }

  def add(t: T): Unit = {
    if (setOf != null)
      setOf.add(t)
    else
      arrayOf.append(t)

    if (arrayOf != null && arrayOf.length > 64) {
      setOf = new mutable.HashSet[T]()
      arrayOf.foreach(setOf.add)
      arrayOf = null
    }
  }

  def toSet: Set[T] = {
    if (setOf != null)
      setOf.toSet
    else
      arrayOf.toSet
  }

  def toIterable: mutable.Iterable[T] = {
    if (setOf != null)
      setOf
    else
      arrayOf.distinct
  }
}
