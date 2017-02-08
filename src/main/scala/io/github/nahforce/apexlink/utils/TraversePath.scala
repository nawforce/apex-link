package io.github.nahforce.apexlink.utils

import java.nio.file._
import java.nio.file.attribute.BasicFileAttributes

import scala.collection.Traversable

class TraversePath(path: Path) extends Traversable[(Path, BasicFileAttributes)] {

  // Make foreach receive a function from Tuple2 to Unit
  def foreach[U](f: ((Path, BasicFileAttributes)) => U) {

    class Visitor extends SimpleFileVisitor[Path] {
      var error :Option[Throwable] = None

      override def visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult = try {
        // Pass a tuple to f
        f(file -> attrs)
        FileVisitResult.CONTINUE
      } catch {
        case ex : Throwable =>
          error = Some(ex)
          FileVisitResult.TERMINATE
      }
    }

    val v = new Visitor
    Files.walkFileTree(path, v)
    v.error.exists(ex => throw ex)
  }
}