package io.github.nahforce.apexlink.metadata

import java.nio.file.Files

import io.github.nahforce.apexlink.utils.{LinkerException, LinkerLog, XMLLineLoader, XMLUtils}

import scala.xml.Elem

class LabelReader extends SymbolReader {

  override def loadSymbols(ctx: SymbolReaderContext) : Unit = {
    try {
      val labelsFile = ctx.getBaseDir.resolve("labels").resolve("CustomLabels.labels")
      LinkerLog.pushContext(labelsFile.toString)
      if (Files.exists(labelsFile)) {
        LinkerLog.ifNotLogAndThrow(Files.isRegularFile(labelsFile), 0, "Labels file is not a regular file")
        LinkerLog.ifNotLogAndThrow(Files.isReadable(labelsFile), 0, "Labels file is not readable")

        val root = XMLLineLoader.loadFile(labelsFile.toString)
        XMLUtils.ifNotElemLogAndThrow(root, "CustomLabels")

        root.child.foreach {
          case elem: Elem =>
            XMLUtils.ifNotElemLogAndThrow(elem, "labels")
            Label.create(elem).foreach(l => ctx.addLabel(l))
          case _ =>
        }
      }
    } catch {
      case _: LinkerException => () // Ignore, just used to abort processing
    }
  }
}
