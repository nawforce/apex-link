/*
 [The "BSD licence"]
 Copyright (c) 2019 Kevin Jones
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
package com.nawforce.cache

import com.nawforce.imports.OSExtra
import io.scalajs.nodejs.process

class Cache(path: Path) {

}

object Cache {
  val CACHE_DIR: String = ".apexassist_cache"
  val TEST_FILE: String = "test_file"

  def apply(): Either[String, Cache] = {
    val cacheDir =
      process.env.get("APEXASSIST_CACHE_DIR").map(Path)
        .getOrElse(Path(OSExtra.homedir()).join(CACHE_DIR))

    if (cacheDir.nature != DOES_NOT_EXIST) {
      if (cacheDir.nature != DIRECTORY) {
        return Left(s"Cache directory '$cacheDir' exists but is not a directory")
      }

      cacheDir.createFile(TEST_FILE, "") match {
        case Left(err) => Left(s"Cache directory '$cacheDir' exists but is not writable, error '$err'")
        case Right(created) =>
          created.delete()
          Right(new Cache(cacheDir))
      }
    } else {
      // TODO
      Left("Create Dir")
    }
  }
}
