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
package com.nawforce.path

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path => JPath, Paths => JPaths}

case class Path(path: String) extends PathLike {
  assert(path.nonEmpty)

  private lazy val jpath: JPath = JPaths.get(path)

  override lazy val basename: String = Option(jpath.getFileName).getOrElse(jpath.getRoot).toString
  override lazy val parent: Path = join("..")
  override lazy val absolute: Path = Path(jpath.toAbsolutePath.toString)

  override def toString: String = jpath.toString

  override lazy val nature: PathNature = {
    val f = jpath.toFile
    if (!f.exists()) DOES_NOT_EXIST
    else if (f.isDirectory) DIRECTORY
    else if (f.isFile) {
      if (Files.size(jpath) == 0) EMPTY_FILE else NONEMPTY_FILE
    }
    else UNKNOWN
  }

  override def join(arg: String): Path = {
    Path(jpath.resolve(arg).toString)
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
      Right(new String(Files.readAllBytes(jpath), StandardCharsets.UTF_8))
    } catch {
      case ex: java.io.IOException => Left(ex.getMessage)
    }
  }

  override def write(data: String): Option[String] = {
    try {
      Files.write(jpath, data.getBytes(StandardCharsets.UTF_8))
      None
    } catch {
      case ex: java.io.IOException => Some(ex.getMessage)
    }
  }

  override def delete(): Option[String] = {
    try {
      Files.delete(jpath)
      None
    } catch {
      case ex: java.io.IOException => Some(ex.getMessage)
    }
  }

  override def directoryList(): Either[String, Seq[String]] = {
    if (nature == DIRECTORY) {
      val f = jpath.toFile
      Right(Option(f.listFiles()).getOrElse(Array()).map(_.getName))
    } else {
      Left(s"Path '$jpath' is not a directory'")
    }
  }
}
