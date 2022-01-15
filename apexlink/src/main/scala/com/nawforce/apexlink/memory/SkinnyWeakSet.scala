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

import java.lang.ref.WeakReference
import scala.collection.mutable

/** Low memory weak reference set. Uses an array for small sizes before swapping to a set. */
final class SkinnyWeakSet[T <: AnyRef] {
  private var arrayOf: mutable.ArrayBuffer[WeakReference[T]] = _
  private var setOf: mutable.WeakHashMap[T, Boolean]         = _

  def isEmpty: Boolean = {
    if (setOf != null)
      setOf.isEmpty
    else if (arrayOf != null)
      arrayOf.forall(_.get == null)
    else
      true
  }

  def nonEmpty: Boolean = !isEmpty

  def size: Int = {
    if (setOf != null)
      setOf.size
    else if (arrayOf != null)
      arrayOf.count(_.get != null)
    else
      0
  }

  def add(t: T): Unit = {
    if (setOf != null)
      setOf.put(t, true)
    else {
      if (arrayOf == null)
        arrayOf = new mutable.ArrayBuffer[WeakReference[T]](4)
      arrayOf.append(new WeakReference(t))
      if (arrayOf.size > 64)
        arrayOf = arrayOf.filter(_.get != null).distinct
    }

    if (arrayOf != null && arrayOf.length > 64) {
      setOf = new mutable.WeakHashMap[T, Boolean]()
      arrayOf.filter(_.get != null).foreach(wr => setOf.put(wr.get, true))
      arrayOf = null
    }
  }

  def toIterator: Iterator[T] = {
    if (setOf != null)
      setOf.keys.iterator
    else if (arrayOf != null)
      arrayOf.filter(_.get != null).map(_.get).iterator
    else
      Iterator.empty
  }

  def toSet: Set[T] = {
    if (setOf != null)
      setOf.keys.toSet
    else if (arrayOf != null)
      arrayOf.filter(_.get != null).map(_.get).toSet
    else
      Set.empty
  }
}
