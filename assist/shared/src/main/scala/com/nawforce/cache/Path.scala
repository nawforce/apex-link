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

import io.scalajs.nodejs.fs.Fs

import scala.scalajs.js

case class Path(path: String) {
  assert(path.nonEmpty)

  private lazy val pathObject = io.scalajs.nodejs.path.Path.parse(path)

  lazy val root: Option[String] = pathObject.root.toOption
  lazy val dir: Option[String] = pathObject.dir.toOption
  lazy val base: Option[String] = pathObject.base.toOption
  lazy val ext: Option[String] = pathObject.ext.toOption
  lazy val name: Option[String] = pathObject.name.toOption

  lazy val filename: String = base.get
  lazy val parent: Path = Path(dir.get)

  override def toString: String = {
    io.scalajs.nodejs.path.Path.format(pathObject)
  }

  def exists: Boolean = Fs.existsSync(path)

  def isDirectory: Boolean = exists && Fs.lstatSync(path).isDirectory()

  def isEmpty: Boolean = exists && Fs.lstatSync(path).size == 0

  def toAbsolute: Path = Path(io.scalajs.nodejs.path.Path.resolve(path))

  def join(arg: String): Path = Path(io.scalajs.nodejs.path.Path.join(path, arg))

  def createFile(name: String, data: String): Either[String, Path] = {
    val created = join(name)
    created.write(data) match {
      case None => Right(created)
      case Some(err) => Left(err)
    }
  }

  def read(): Either[String, String] = {
    try {
      Right(Fs.readFileSync(path, "utf-8"))
    } catch {
      case ex: js.JavaScriptException => Left(ex.getMessage())
    }
  }

  def write(data: String): Option[String] = {
    try {
      Fs.writeFileSync(path, data)
      None
    } catch {
      case ex: js.JavaScriptException => Some(ex.getMessage())
    }
  }

  def delete(): Option[String] = {
    try {
      Fs.unlinkSync(path)
      None
    } catch {
      case ex: js.JavaScriptException => Some(ex.getMessage())
    }
  }

  def directoryContents(): Either[String, Seq[Path]] = {
    if (isDirectory) {
      try {
        Right(Fs.readdirSync(path).map(Path))
      } catch {
        case ex: js.JavaScriptException => Left(ex.getMessage())
      }
    } else {
      Right(Seq())
    }
  }
}
