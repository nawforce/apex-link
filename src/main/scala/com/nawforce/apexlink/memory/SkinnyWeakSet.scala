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

/** Low memory weak reference set.
  *
  * The natural representation for a WeakHasMap but the hashing can create a lot of memory overhead. To avoid this
  * we use an ArrayBuffer for small sets and convert to a WeakHashMap as the WeakSet grows. For small sets contained
  * in the array there is some overhead for accessing the values as the array is converted to a set.
  */
class SkinnyWeakSet[T <: AnyRef] {
  private var arrayOf = new mutable.ArrayBuffer[WeakReference[T]](4)
  private var setOf: mutable.WeakHashMap[T, Boolean] = _

  def isEmpty: Boolean = {
    if (setOf != null)
      setOf.isEmpty
    else
      arrayOf.forall(_.get == null)
  }

  def nonEmpty: Boolean = !isEmpty

  def size: Int = {
    if (setOf != null)
      setOf.size
    else
      arrayOf.count(_.get != null)
  }

  def add(t: T): Unit = {
    if (setOf != null)
      setOf.put(t, true)
    else
      arrayOf.append(new WeakReference(t))

    if (arrayOf != null && arrayOf.length > 64) {
      setOf = new mutable.WeakHashMap[T, Boolean]()
      arrayOf.filter(_.get != null).foreach(wr => setOf.put(wr.get, true))
      arrayOf = null
    }
  }

  def toSet: Set[T] = {
    if (setOf != null)
      setOf.keys.toSet
    else
      arrayOf.filter(_.get != null).map(_.get).toSet
  }
}
