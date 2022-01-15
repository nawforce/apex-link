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

package com.nawforce.apexlink.memory

import scala.collection.mutable

/** Low memory set. Uses an array for small sizes before swapping to a set. */
final class SkinnySet[T <: AnyRef] {
  private var arrayOf: mutable.ArrayBuffer[T] = _
  private var setOf: mutable.Set[T]           = _

  def isEmpty: Boolean = {
    if (setOf != null)
      setOf.isEmpty
    else if (arrayOf != null)
      arrayOf.isEmpty
    else
      true
  }

  def nonEmpty: Boolean = !isEmpty

  def size: Int = {
    if (setOf != null)
      setOf.size
    else if (arrayOf != null)
      arrayOf.size
    else
      0
  }

  def add(t: T): Unit = {
    if (setOf != null)
      setOf.add(t)
    else {
      if (arrayOf == null)
        arrayOf = new mutable.ArrayBuffer[T](4)
      arrayOf.append(t)
      if (arrayOf.size > 64)
        arrayOf = arrayOf.distinct
    }

    if (arrayOf != null && arrayOf.length > 64) {
      setOf = new mutable.HashSet[T]()
      arrayOf.foreach(setOf.add)
      arrayOf = null
    }
  }

  def toSet: Set[T] = {
    if (setOf != null)
      setOf.toSet
    else if (arrayOf != null)
      arrayOf.toSet
    else
      Set.empty
  }

  def toIterable: mutable.Iterable[T] = {
    if (setOf != null) {
      setOf
    } else if (arrayOf != null) {
      arrayOf = arrayOf.distinct
      arrayOf
    } else {
      mutable.Iterable()
    }
  }
}
