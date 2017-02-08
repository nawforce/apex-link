package io.github.nahforce.apexlink.alt

import java.nio.file.Paths

import io.github.nahforce.apexlink.metadata._
import io.github.nahforce.apexlink.utils.LinkerLog

object ParseClasses {
  def main(args: Array[String]): Unit = {
    val ctx = new SymbolReaderContext(Paths.get(args.head))
    new ApexClassReader().loadSymbolsFrom(ctx, Paths.get(args.head))
    LinkerLog.dumpMessage()
  }
}
