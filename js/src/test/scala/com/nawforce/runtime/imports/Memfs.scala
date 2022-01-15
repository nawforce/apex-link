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
package com.nawforce.runtime.imports

import io.scalajs.nodejs.fs.Fs

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
trait Volume extends Fs {
  def fromJSON(json: js.Dynamic, cwd: String = ""): Unit = js.native
  def toJSON(): js.Object = js.native
  def reset(): Unit = js.native
}

@js.native
trait Memfs extends js.Object {
  val vol: Volume = js.native
  val fs: js.Object = js.native
}

@js.native
@JSImport("memfs", JSImport.Namespace)
object Memfs extends Memfs
