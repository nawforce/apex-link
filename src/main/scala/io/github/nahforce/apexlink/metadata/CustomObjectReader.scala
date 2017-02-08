package io.github.nahforce.apexlink.metadata

import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{Files, Path}

import io.github.nahforce.apexlink.utils._

class CustomObjectReader extends SymbolReader {

  override def loadSymbols(ctx: SymbolReaderContext): Unit = {
    try {
      val objectsDir = ctx.getBaseDir.resolve("objects")
      LinkerLog.ifNotLogAndThrow(Files.isDirectory(objectsDir), 0, "Objects directory is not present")

      val traverse = new TraversePath(objectsDir)
      traverse foreach {
        case (file: Path, attr: BasicFileAttributes) =>
          if (attr.isRegularFile && file.toString.endsWith(".object")) {
            loadObject(ctx, file.getFileName.toString.replaceFirst(".object$", ""), file)
          } else if (attr.isRegularFile) {
            LinkerLog.logMessage(file.toString, 0, "Unexpected file in objects directory")
          } else {
            LinkerLog.logMessage(file.toString, 0, "Only expected to find files in objects directory")
          }
      }
    }
    catch {
      case _: LinkerException => () // Ignore, just used to abort processing
    }
  }

  def loadObject(ctx: SymbolReaderContext, fullName: String, objectFile: Path) : Unit = {
    LinkerLog.pushContext(objectFile.toString)
    try {
      val root = XMLLineLoader.loadFile(objectFile.toString)
      XMLUtils.ifNotElemLogAndThrow(root, "CustomObject")

      CustomObject.create(fullName, root).foreach(o => ctx.addCustomObject(o))
    } finally {
      LinkerLog.popContext()
    }
  }
}
