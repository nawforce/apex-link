package io.github.nahforce.apexlink.metadata

import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{Files, Path}

import io.github.nahforce.apexlink.utils.{LinkerException, LinkerLog, TraversePath, XMLLineLoader}

class PageReader extends SymbolReader {

  override def loadSymbols(ctx: SymbolReaderContext): Unit = {
    try {
      val pagesDir = ctx.getBaseDir.resolve("pages")
      LinkerLog.ifNotLogAndThrow(Files.isDirectory(pagesDir), 0, "Pages directory is not present")

      val traverse = new TraversePath(pagesDir)
      traverse foreach {
        case (file: Path, attr: BasicFileAttributes) =>
          if (attr.isRegularFile && file.toString.endsWith(".page")) {
            loadPage(ctx, file.getFileName.toString.replaceFirst(".page$", ""), file)
          } else if (attr.isRegularFile && file.toString.endsWith(".page-meta.xml")) {
            // Ignore
          } else if (attr.isRegularFile) {
            LinkerLog.logMessage(file.toString, 0, "Unexpected file in pages directory")
          } else {
            LinkerLog.logMessage(file.toString, 0, "Only expected to find files in pages directory")
          }
      }
    }
    catch {
      case _: LinkerException => () // Ignore, just used to abort processing
    }
  }

  private def loadPage(ctx: SymbolReaderContext, fullName: String, objectFile: Path) : Unit = {
    LinkerLog.pushContext(objectFile.toString)
    try {
      val root = XMLLineLoader.loadFile(objectFile.toString)

      Page.create(fullName, root).foreach(o => ctx.addPage(o))
    } finally {
      LinkerLog.popContext()
    }
  }
}
