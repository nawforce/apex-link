package io.github.nahforce.apexlink.metadata

import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{Files, Path}

import io.github.nahforce.apexlink.utils._

class ApexClassReader extends SymbolReader {

  override def loadSymbols(ctx: SymbolReaderContext): Unit = {
    try {
      val classesDir = ctx.getBaseDir.resolve("classes")
      if (Files.exists(classesDir)) {
        LinkerLog.ifNotLogAndThrow(Files.isDirectory(classesDir), 0, "classes is present but not a directory")

        val traverse = new TraversePath(classesDir)
        traverse foreach {
          case (file: Path, attr: BasicFileAttributes) =>
            if (attr.isRegularFile && file.toString.endsWith(".cls")) {
              loadApexClass(ctx, file.getFileName.toString.replaceFirst(".cls$", ""), file).foreach(o => ctx.addApexClass(o))
            } else if (attr.isRegularFile && file.toString.endsWith(".cls-meta.xml")) {
              // Ignore
            } else if (attr.isRegularFile) {
              LinkerLog.logMessage(file.toString, 0, "Unexpected file in classes directory")
            } else {
              LinkerLog.logMessage(file.toString, 0, "Only expected to find files in classes directory")
            }
        }
      }

    } catch {
      case _: LinkerException => () // Ignore, just used to abort processing
    }
  }

  def loadSymbolsFrom(ctx: SymbolReaderContext, path: Path): Unit = {
    try {
      LinkerLog.ifNotLogAndThrow(Files.isDirectory(path), 0, "Directory is not present")

      val traverse = new TraversePath(path)
      traverse foreach {
        case (file: Path, attr: BasicFileAttributes) =>
          if (attr.isRegularFile && file.toString.endsWith(".cls")) {
            loadApexClass(null, file.getFileName.toString.replaceFirst(".cls$", ""), file)
          }
      }
    }
    catch {
      case _: LinkerException => () // Ignore, just used to abort processing
    }
  }

  def loadApexClass(ctx: SymbolReaderContext, fullName: String, path: Path) : Option[ApexClass] = {
    LinkerLog.pushContext(path.toString)
    try {
      ApexClass.create(fullName, path.toString)
    } finally {
      LinkerLog.popContext()
    }
  }
}
