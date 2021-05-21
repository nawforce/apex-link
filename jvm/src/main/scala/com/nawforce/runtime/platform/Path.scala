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
package com.nawforce.runtime.platform

import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.{DirectoryStream, Files}

import com.nawforce.common.path._
import com.nawforce.runtime.parsers.SourceData

import scala.collection.mutable

final case class Path(native: java.nio.file.Path) extends PathLike {

  override lazy val basename: String = {
    val filename = native.getFileName
    if (filename == null) "" else filename.toString
  }
  override lazy val parent: Path = join("..")
  override lazy val exists: Boolean = Files.exists(native)
  override lazy val isRoot: Boolean = this.toString == parent.toString
  override lazy val isDirectory: Boolean = Files.isDirectory(native)
  override lazy val isFile: Boolean = Files.isRegularFile(native)
  override lazy val size: Long = if (isFile) Files.size(native) else 0

  override lazy val toString: String = native.toString

  override def join(arg: String): Path = {
    Path(native.resolve(arg).normalize())
  }

  override def createFile(name: String, data: String): Either[String, Path] = {
    val created = join(name)
    created.write(data) match {
      case None      => Right(created)
      case Some(err) => Left(err)
    }
  }

  override def read(): Either[String, String] = {
    readBytes().map(new String(_, StandardCharsets.UTF_8))
  }

  override def readBytes(): Either[String, Array[Byte]] = {
    try {
      Right(Files.readAllBytes(native))
    } catch {
      case ex: java.io.IOException => Left(ex.toString)
    }
  }

  override def readSourceData(): Either[String, SourceData] = {
    readBytes().map(SourceData(_))
  }

  override def write(data: String): Option[String] = {
    write(data.getBytes(StandardCharsets.UTF_8))
  }

  override def write(data: Array[Byte]): Option[String] = {
    try {
      Files.write(native, data)
      None
    } catch {
      case ex: java.io.IOException => Some(ex.toString)
    }
  }

  override def delete(): Option[String] = {
    try {
      Files.delete(native)
      None
    } catch {
      case ex: java.io.IOException => Some(ex.toString)
    }
  }

  override def createDirectory(name: String): Either[String, PathLike] = {
    val dir = join(name)
    if (!Files.exists(dir.native)) {
      try {
        Files.createDirectory(dir.native)
        Right(Path(dir.native))
      } catch {
        case ex: java.io.IOException => Left(ex.toString)
      }
    } else if (dir.isDirectory) {
      Right(dir)
    } else {
      Left(s"Can not create directory '$dir', file already exists")
    }
  }

  override def directoryList(): Either[String, Seq[String]] = {
    if (isDirectory) {
      var files: List[String] = Nil
      val paths = Files.newDirectoryStream(native)
      try {
        paths.forEach(file => {
          files = file.getFileName.toString :: files
        })
      } finally {
        paths.close()
      }
      Right(files)
    } else {
      Left(s"Path '$native' is not a directory'")
    }
  }

  override def splitDirectoryEntries(): (Array[PathLike], Array[PathLike]) = {
    val files = mutable.ArrayBuffer[PathLike]()
    val directories = mutable.ArrayBuffer[PathLike]()

    var paths: DirectoryStream[java.nio.file.Path] = null
    try {
      paths = Files.newDirectoryStream(native)
      paths.forEach(path => {
        val pathLike = Path(path)
        if (pathLike.isFile)
          files.append(pathLike)
        else if (pathLike.isDirectory)
          directories.append(pathLike)
      })
      (files.toArray, directories.toArray)
    } finally {
      if (paths != null) paths.close()
    }
  }

  override def lastModified(): Option[Long] = {
    try {
      Some(native.toFile.lastModified())
    } catch {
      case _: SecurityException             => None
      case _: UnsupportedOperationException => None
    }
  }

  /** As we are using an in-memory FS for testing we need to compare string-wise */
  override def equals(that: Any): Boolean = {
    that match {
      case other: Path =>
        other.canEqual(this) && other.toString == toString
      case _ => false
    }
  }

  def canEqual(that: Any): Boolean = that.isInstanceOf[Path]

  override def hashCode(): Int = toString.hashCode
}

object Path {
  val separator: String = File.separator

  def apply(path: String): Path =
    Path(java.nio.file.Paths.get(Option(path).getOrElse("")).toAbsolutePath.normalize())
}
