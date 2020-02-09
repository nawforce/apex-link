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
package com.nawforce.common.documents

import java.nio.charset.StandardCharsets

import com.nawforce.common.names.Name
import com.nawforce.common.path._
import com.nawforce.runtime.os.Environment
import upickle.default.{macroRW, ReadWriter => RW, _}

import scala.util.hashing.MurmurHash3

case class CacheEntry(version: Int, namespace: Array[Byte], key: Array[Byte], value: Array[Byte])

object CacheEntry {
  val currentVersion = 1
  implicit val rw: RW[CacheEntry] = macroRW
}

class ParsedCache(val path: PathLike) {

  /** Auto expire before use */
  expire()

  /** Upsert a key -> value pair, ignores errors */
  def upsert(key: Array[Byte], value: Array[Byte], namespace: Option[Name]=None): Unit = {
    val nsBytes = namespace.map(_.value).getOrElse("").getBytes(StandardCharsets.UTF_8)
    val hashParts = hashToParts(MurmurHash3.bytesHash(key, MurmurHash3.bytesHash(nsBytes)))
    path.createDirectory(hashParts.head) match {
      case Left(_) => ()
      case Right(outer) =>
        val inner = outer.join(hashParts(1))
        inner.write(writeBinary(CacheEntry(CacheEntry.currentVersion, nsBytes, key, value)))
    }
  }

  /** Recover a value from a key */
  def get(key: Array[Byte], namespace: Option[Name]=None): Option[Array[Byte]] = {
    val nsBytes = namespace.map(_.value).getOrElse("").getBytes(StandardCharsets.UTF_8)
    val hashParts = hashToParts(MurmurHash3.bytesHash(key, MurmurHash3.bytesHash(nsBytes)))
    val outer = path.join(hashParts.head)
    if (outer.nature == DIRECTORY) {
      val inner = outer.join(hashParts(1))
      if (inner.nature == NONEMPTY_FILE) {
        inner.readBytes() match {
          case Left(_) => ()
          case Right(data) =>
            val ce = readBinary[CacheEntry](data)
            if (ce.version == CacheEntry.currentVersion && ce.namespace.sameElements(nsBytes) && ce.key.sameElements(key))
              return Some(ce.value)
        }
      }
    }
    None
  }

  /** Expire old entries in the cache */
  def expire(): Unit = expire(path, System.currentTimeMillis() - ParsedCache.EXPIRE_WINDOW)

  private def expire(path: PathLike, minTimeStamp: Long): Unit = {
    path.directoryList() match {
      case Left(_) => ()
      case Right(names) => names.foreach(name => {
        val pathEntry = path.join(name)
        if (pathEntry.nature == DIRECTORY) {
          expire(pathEntry, minTimeStamp)
        } else if (pathEntry.nature == NONEMPTY_FILE) {
          pathEntry.lastModified() match {
            case Some(ts) if ts < minTimeStamp =>
              pathEntry.delete()
            case _ => ()
          }
        }
      })
    }
  }

  /** Clear the cache, useful for testing */
  def clear(): Unit = clear(path)

  private def clear(path: PathLike): Unit = {
    path.directoryList() match {
      case Left(_) => ()
      case Right(names) => names.foreach(name => {
        val pathEntry = path.join(name)
        if (pathEntry.nature == DIRECTORY) {
          clear(pathEntry)
        }
        pathEntry.delete()
      })
    }
  }

  private def hashToParts(hash: Int): Array[String] = {
    val asHex = hash.toHexString
    val keyString = "0" * (8-asHex.length) + asHex
    Array(keyString.substring(0, 4), keyString.substring(4, 8))
  }
}

object ParsedCache {
  val CACHE_DIR: String = ".apexlink_cache"
  val TEST_FILE: String = "test_file"
  val EXPIRE_WINDOW: Long = 7 * 24 * 60 * 60 * 1000

  def create(): Either[String, ParsedCache] = {
    val cacheDirOpt =
      Environment.variable("APEXLINK_CACHE_DIR").map(d =>PathFactory(d))
        .orElse(Environment.homedir.map(_.join(CACHE_DIR)))

    if (cacheDirOpt.isEmpty) {
      return Left(s"Cache directory could not be determined from APEXLINK_CACHE_DIR or home directory")
    }

    val cacheDir = cacheDirOpt.get
    if (cacheDir.nature != DOES_NOT_EXIST) {
      if (cacheDir.nature != DIRECTORY) {
        return Left(s"Cache directory '$cacheDir' exists but is not a directory")
      }

      cacheDir.createFile(TEST_FILE, "") match {
        case Left(err) => Left(s"Cache directory '$cacheDir' exists but is not writable, error '$err'")
        case Right(created) =>
          created.delete()
          Right(new ParsedCache(cacheDir))
      }
    } else {
      cacheDir.parent.createDirectory(cacheDir.basename) match {
        case Left(err) => Left(s"Cache directory '$cacheDir' does not exist and can not be created, error '$err'")
        case Right(created) => Right(new ParsedCache(created))
      }
    }
  }

  def clear(): Unit = {
    create().map(_.clear())
  }
}
