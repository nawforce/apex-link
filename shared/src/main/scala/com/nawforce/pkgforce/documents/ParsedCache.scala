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
package com.nawforce.pkgforce.documents

import com.nawforce.pkgforce.path._
import com.nawforce.runtime.platform.Environment
import upickle.default.{macroRW, ReadWriter => RW, _}

import scala.util.hashing.MurmurHash3

// Key of cache entries, update version if the format changes
final case class CacheKey(version: Int, packageContext: PackageContext, sourceKey: Int) {
  def hashParts: Array[String] = {
    val hash = MurmurHash3.bytesHash(writeBinary(this))
    val asHex = hash.toHexString
    val keyString = "0" * (8 - asHex.length) + asHex
    Array(keyString.substring(0, 4), keyString.substring(4, 8))
  }

  override def equals(that: Any): Boolean = {
    that match {
      case other: CacheKey =>
        other.version == version &&
          other.packageContext == packageContext &&
          other.sourceKey == sourceKey
      case _ => false
    }
  }
}

object CacheKey {
  implicit val rw: RW[CacheKey] = macroRW

  def apply(version: Int,
            packageContext: PackageContext,
            name: String,
            contents: Array[Byte]): CacheKey = {
    val keyHash = MurmurHash3.arrayHash(contents, MurmurHash3.stringHash(name))
    CacheKey(version, packageContext, keyHash)
  }
}

// Package details used in key to ensure error messages will be accurate
final case class PackageContext(namespace: Option[String],
                                ghostedPackages: Array[String],
                                analysedPackages: Array[String]) {
  override def equals(that: Any): Boolean = {
    that match {
      case other: PackageContext =>
        other.namespace == namespace &&
          other.ghostedPackages.sameElements(ghostedPackages) &&
          other.analysedPackages.sameElements(analysedPackages)
      case _ => false
    }
  }
}

object PackageContext {
  implicit val rw: RW[PackageContext] = macroRW
}

// Cache entry, a simple key/value pairing
final case class CacheEntry(key: CacheKey, value: Array[Byte])

object CacheEntry {
  implicit val rw: RW[CacheEntry] = macroRW
}

/* Parsed class cache */
final class ParsedCache(val path: PathLike, version: Int) {

  /** Upsert a key -> value pair, ignores storage errors */
  def upsert(packageContext: PackageContext,
             name: String,
             contents: Array[Byte],
             value: Array[Byte]): Unit = {
    val cacheKey = CacheKey(version, packageContext, name, contents)
    val hashParts = cacheKey.hashParts
    path.createDirectory(hashParts.head) match {
      case Left(_) => ()
      case Right(outer) =>
        val inner = outer.join(hashParts(1))
        inner.write(writeBinary(CacheEntry(cacheKey, value)))
    }
  }

  /** Recover a value from a key */
  def get(packageContext: PackageContext,
          name: String,
          contents: Array[Byte]): Option[Array[Byte]] = {
    val cacheKey = CacheKey(version, packageContext, name, contents)
    val hashParts = cacheKey.hashParts
    val outer = path.join(hashParts.head)
    if (outer.isDirectory) {
      val inner = outer.join(hashParts(1))
      inner.readBytes() match {
        case Left(_) => ()
        case Right(data) =>
          val ce = readBinary[CacheEntry](data)
          if (ce.key == cacheKey)
            return Some(ce.value)
      }
    }
    None
  }

  /** Expire old entries in the cache */
  def expire(): Unit = expire(path, System.currentTimeMillis() - ParsedCache.EXPIRE_WINDOW)

  private def expire(path: PathLike, minTimeStamp: Long): Boolean = {
    val (files, directories) = path.splitDirectoryEntries()

    val deletedFiles =
      files.filter(f => f.lastModified().exists(_ < minTimeStamp)).count(_.delete().isEmpty)
    val deletedDirectories = directories.map(d => expire(d, minTimeStamp)).count(_ == true)

    if (deletedFiles == files.length && deletedDirectories == directories.length) {
      path.delete().isEmpty
    } else {
      false
    }
  }

  /** Clear the cache, useful for testing */
  def clear(): Unit = {
    clearContents(path)
  }

  private def clearContents(path: PathLike): Unit = {
    path.directoryList() match {
      case Left(_) => ()
      case Right(names) =>
        names.foreach(name => {
          val pathEntry = path.join(name)
          if (pathEntry.isDirectory) {
            clearContents(pathEntry)
          }
          pathEntry.delete()
        })
    }
    path.delete()
  }
}

object ParsedCache {
  val CACHE_DIR: String = ".apexlink_cache"
  val TEST_FILE: String = "test_file"
  val EXPIRE_WINDOW: Long = 7 * 24 * 60 * 60 * 1000

  def create(version: Int): Either[String, ParsedCache] = {
    val cacheDirOpt =
      Environment
        .variable("APEXLINK_CACHE_DIR")
        .map(d => PathFactory(d))
        .orElse(Environment.homedir.map(_.join(CACHE_DIR)))

    if (cacheDirOpt.isEmpty) {
      return Left(
        s"Cache directory could not be determined from APEXLINK_CACHE_DIR or home directory")
    }

    val cacheDir = cacheDirOpt.get
    if (cacheDir.exists) {
      if (!cacheDir.isDirectory) {
        return Left(s"Cache directory '$cacheDir' exists but is not a directory")
      }

      cacheDir.createFile(TEST_FILE, "") match {
        case Left(err) =>
          Left(s"Cache directory '$cacheDir' exists but is not writable, error '$err'")
        case Right(created) =>
          created.delete()
          Right(new ParsedCache(cacheDir, version))
      }
    } else {
      cacheDir.parent.createDirectory(cacheDir.basename) match {
        case Left(err) =>
          Left(s"Cache directory '$cacheDir' does not exist and can not be created, error '$err'")
        case Right(created) => Right(new ParsedCache(created, version))
      }
    }
  }

  def clear(): Unit = {
    create(0).map(_.clear())
  }
}
