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
package com.nawforce.pkgforce.memory

import com.nawforce.runtime.platform.Environment

import scala.collection.mutable

/** For Caches that can be cleaned/reset as needed to recover memory. */
trait CleanableCache {
  Cleanable.register(this)

  def clean(): Unit
}

/** Manager of cleanable caches. */
object Cleanable {
  private val cleanable = mutable.Set[IdentityBox[CleanableCache]]()

  def register(cache: CleanableCache): Unit = {
    cleanable.add(new IdentityBox(cache))
  }

  def clean(): Unit = {
    cleanable.foreach(_.value.clean())
    Environment.gc()
  }
}
