package com.nawforce.runtime

import com.nawforce.path._
import io.scalajs.nodejs.fs.Fs

import scala.scalajs.js

case class Path(path: String) extends PathLike {
  assert(path.nonEmpty)

  private lazy val pathObject = io.scalajs.nodejs.path.Path.parse(path)

  override lazy val basename: String = pathObject.base.toOption.get
  override lazy val parent: Path = join("..")
  override lazy val absolute: Path = Path(io.scalajs.nodejs.path.Path.resolve(path))
  override val native: Any = path

  override def toString: String = {
    io.scalajs.nodejs.path.Path.format(pathObject)
  }

  override lazy val nature: PathNature = {
    try {
      val stats = Fs.lstatSync(path)
      if (stats.isDirectory()) DIRECTORY
      else if (stats.isFile()) {
        if (stats.size == 0) EMPTY_FILE else NONEMPTY_FILE
      }
      else UNKNOWN
    } catch {
      case _: js.JavaScriptException => DOES_NOT_EXIST
    }
  }

  override def join(arg: String): Path = {
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

  override def write(data: String): Option[String] = {
    try {
      Fs.writeFileSync(path, data)
      None
    } catch {
      case ex: js.JavaScriptException => Some(ex.getMessage())
    }
  }

  override def delete(): Option[String] = {
    try {
      Fs.unlinkSync(path)
      None
    } catch {
      case ex: js.JavaScriptException => Some(ex.getMessage())
    }
  }

  override def createDirectory(name: String): Either[String, PathLike] = {
    val dir = join(name)
    if (dir.nature == DOES_NOT_EXIST) {
      try {
        Fs.mkdirSync(dir.toString)
        Right(Path(dir.toString))
      } catch {
        case ex: js.JavaScriptException => Left(ex.getMessage())
      }
    } else if (dir.nature == DIRECTORY) {
      Right(dir)
    } else {
      Left(s"Can not create directory '$dir', file already exists")
    }
  }

  override def directoryList(): Either[String, Seq[String]] = {
    if (nature == DIRECTORY) {
      try {
        Right(Fs.readdirSync(path))
      } catch {
        case ex: js.JavaScriptException => Left(ex.getMessage())
      }
    } else {
      Right(Seq())
    }
  }
}
