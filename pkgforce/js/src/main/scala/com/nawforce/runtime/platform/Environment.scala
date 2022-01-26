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
import io.scalajs.nodejs.process.Process

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object Environment {
  private val CACHE_DIR: String                          = ".apexlink_cache"
  private var cacheDirOverride: Option[Option[PathLike]] = None

  def gc(): Unit = {
    // Not implemented
  }

  def homedir: Option[Path] = {
    Option(OSExtra.homedir()).filter(_.nonEmpty).map(Path(_))
  }

  def cacheDir: Option[PathLike] = {
    if (cacheDirOverride.nonEmpty)
      return cacheDirOverride.get

    try {
      Process
        .env("APEXLINK_CACHE_DIR")
        .toOption
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

@js.native
trait OSExtra extends js.Object {
  def homedir(): String = js.native
}

@js.native
@JSImport("os", JSImport.Namespace)
object OSExtra extends OSExtra
