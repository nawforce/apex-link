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

import com.nawforce.pkgforce.path._
import com.nawforce.runtime.parsers.SourceData
import io.scalajs.nodejs.buffer.Buffer
import io.scalajs.nodejs.fs.Fs
import io.scalajs.nodejs.process.Process

import scala.collection.mutable
import scala.scalajs.js
import scala.scalajs.js.JSConverters._

final class Path private (path: String) extends PathLike {

  private val pathObject = io.scalajs.nodejs.path.Path.parse(path)
  private def stat = {
    try {
      Some(io.scalajs.nodejs.fs.Fs.statSync(path))
    } catch {
      case _: js.JavaScriptException => None
    }
  }
  override def native: Any = path

  override def basename: String     = pathObject.base.toOption.get
  override def parent: Path         = join("..")
  override def exists: Boolean      = stat.nonEmpty
  override def isRoot: Boolean      = this.toString == parent.toString
  override def isDirectory: Boolean = stat.exists(_.isDirectory())
  override def isFile: Boolean      = stat.exists(_.isFile())
  override def size: Long           = stat.map(_.size.toLong).getOrElse(0)

  override def join(arg: String): Path = {
    if (io.scalajs.nodejs.path.Path.isAbsolute(arg))
      Path(arg)
    else
      Path(io.scalajs.nodejs.path.Path.join(path, arg))
  }

  override def createFile(name: String, data: String): Either[String, Path] = {
    val created = join(name)
    created.write(data) match {
      case None      => Right(created)
      case Some(err) => Left(err)
    }
  }

  override def read(): Either[String, String] = {
    try {
      Right(Fs.readFileSync(path, "utf-8"))
    } catch {
      case ex: js.JavaScriptException => Left(ex.getMessage())
    }
  }

  override def readBytes(): Either[String, Array[Byte]] = {
    try {
      // Scala thinks we must store Buffer values as Short values as they are typed 'unsigned' and there is
      // no unsigned byte in the JVM, workaround is to map them. We could leave them as Buffers but that
      // will causes issues downstream and Strings need more memory.
      val data = Fs.readFileSync(path).values().toIterator
      Right(data.map(_.toByte).toArray)
    } catch {
      case ex: js.JavaScriptException => Left(ex.getMessage())
    }
  }

  override def readSourceData(): Either[String, SourceData] = {
    readBytes().map(SourceData(_))
  }

  override def write(data: String): Option[String] = {
    try {
      Fs.writeFileSync(path, data)
      None
    } catch {
      case ex: js.JavaScriptException => Some(ex.getMessage())
    }
  }

  override def write(data: Array[Byte]): Option[String] = {
    try {
      val asInt = data.map(_.toInt)
      Fs.writeFileSync(path, Buffer.from(asInt.toJSArray))
      None
    } catch {
      case ex: js.JavaScriptException => Some(ex.getMessage())
    }
  }

  override def delete(): Option[String] = {
    try {
      if (isDirectory)
        Fs.rmdirSync(path)
      else
        Fs.unlinkSync(path)
      None
    } catch {
      case ex: js.JavaScriptException => Some(ex.getMessage())
    }
  }

  override def createDirectory(name: String): Either[String, PathLike] = {
    val dir = join(name)
    if (!dir.exists) {
      try {
        Fs.mkdirSync(dir.toString)
        Right(Path(dir.toString))
      } catch {
        case ex: js.JavaScriptException => Left(ex.getMessage())
      }
    } else if (dir.isDirectory) {
      Right(dir)
    } else {
      Left(s"Can not create directory '$dir', file already exists")
    }
  }

  override def directoryList(): Either[String, Seq[String]] = {
    if (isDirectory) {
      try {
        Right(Fs.readdirSync(path).toSeq)
      } catch {
        case ex: js.JavaScriptException => Left(ex.getMessage())
      }
    } else {
      Right(Seq())
    }
  }

  override def splitDirectoryEntries(): (Array[PathLike], Array[PathLike]) = {
    val files       = mutable.ArrayBuffer[PathLike]()
    val directories = mutable.ArrayBuffer[PathLike]()

    Fs.readdirSync(path)
      .foreach(path => {
        val pathLike = this.join(path)
        if (pathLike.isFile)
          files.append(pathLike)
        else if (pathLike.isDirectory)
          directories.append(pathLike)
      })
    (files.toArray, directories.toArray)
  }

  override def lastModified(): Option[Long] = {
    try {
      val stats = Fs.lstatSync(path)
      Some(stats.mtimeMs.asInstanceOf[Long])
    } catch {
      case _: js.JavaScriptException => None
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

  override def hashCode(): Int = {
    toString.hashCode
  }

  override lazy val toString: String = {
    io.scalajs.nodejs.path.Path.format(pathObject)
  }
}

object Path {
  val separator: String = io.scalajs.nodejs.path.Path.sep

  def apply(path: String): Path = {
    new Path(io.scalajs.nodejs.path.Path.resolve(path))
  }

  def unapply(path: Path): Option[String] = {
    Some(path.toString)
  }

  def safeApply(path: String): Path = {
    apply(Option(path).getOrElse(Process.cwd()))
  }
}
