/*
 Copyright (c) 2019 Kevin Jones, All rights reserved.
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

package com.nawforce.runtime.platform

import com.nawforce.pkgforce.path.PathLike

object Environment {
  private val CACHE_DIR: String                          = ".apexlink_cache"
  private var cacheDirOverride: Option[Option[PathLike]] = None

  def gc(): Unit = {
    System.gc()
  }

  def homedir: Option[PathLike] = {
    Option(System.getProperty("user.home")).map(Path(_))
  }

  def cacheDir: Option[PathLike] = {
    if (cacheDirOverride.nonEmpty)
      return cacheDirOverride.get

    try {
      Option(System.getenv("APEXLINK_CACHE_DIR"))
        .filter(_.nonEmpty)
        .map(Path(_))
        .orElse(Environment.homedir.map(_.join(CACHE_DIR)))
    } catch {
      case _: Throwable => None
    }
  }

  // Only for test usage
  def setCacheDirOverride(value: Option[Option[PathLike]]): Unit = {
    cacheDirOverride = value
  }
}
