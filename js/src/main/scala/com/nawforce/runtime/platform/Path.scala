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

import com.nawforce.common.path._
import com.nawforce.runtime.parsers.SourceData
import io.scalajs.nodejs.buffer.Buffer
import io.scalajs.nodejs.fs.Fs
import io.scalajs.nodejs.process.Process

import scala.scalajs.js
import scala.scalajs.js.JSConverters._

case class Path private (path: String) extends PathLike {

  private lazy val pathObject = io.scalajs.nodejs.path.Path.parse(path)
  private lazy val stat = {
    try {
      Some(io.scalajs.nodejs.fs.Fs.statSync(path))
    } catch {
      case _: js.JavaScriptException => None
    }
  }
  override val native: Any = path

  override lazy val basename: String = pathObject.base.toOption.get
  override lazy val parent: Path = join("..")
  override lazy val exists: Boolean = stat.nonEmpty
  override lazy val isRoot: Boolean = this.toString == parent.toString
  override lazy val isDirectory: Boolean = stat.exists(_.isDirectory())
  override lazy val isFile: Boolean = stat.exists(_.isFile())
  override lazy val size: Long = stat.map(_.size.toLong).getOrElse(0)

  override def join(arg: String): Path = {
    if (io.scalajs.nodejs.path.Path.isAbsolute(arg))
      Path(arg)
    else
      Path(io.scalajs.nodejs.path.Path.join(path, arg))
  }

  override def createFile(name: String, data: String): Either[String, Path] = {
    val created = join(name)
    created.write(data) match {
      case None => Right(created)
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
      // TODO Simplify
      val data = Fs.readFileSync(path).values().toIterator.toJSArray
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

  override def lastModified(): Option[Long] = {
    try {
      val stats = Fs.lstatSync(path)
      Some(stats.mtimeMs.asInstanceOf[Long])
    } catch {
      case _: js.JavaScriptException => None
    }
  }

  override lazy val toString: String = {
    val value = io.scalajs.nodejs.path.Path.format(pathObject)
    if (Path.separator == "\\") {
      value.replace('\\', '/')
    } else {
      value
    }
  }
}

object Path {
  val separator: String = io.scalajs.nodejs.path.Path.sep

  def apply(path: String): Path = {
    val safePath = Option(path).getOrElse(Process.cwd())
    new Path(io.scalajs.nodejs.path.Path.resolve(safePath))
  }
}



