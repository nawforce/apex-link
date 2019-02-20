package com.nawforce.apexlink.alt

import java.nio.file.Paths

import com.nawforce.apexlink.metadata.{ApexClassReader, SymbolReaderContext}
import com.nawforce.apexlink.utils.LinkerLog

object ParseClasses {
  def main(args: Array[String]): Unit = {
    val ctx = new SymbolReaderContext(Paths.get(args.head), true)
    new ApexClassReader().loadSymbolsFrom(ctx, Paths.get(args.head))
    LinkerLog.dumpMessages()
  }
}
