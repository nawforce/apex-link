package io.github.nahforce.apexlink.transform

import io.github.nahforce.apexlink.metadata.{ApexClass, SymbolReaderContext}

class AssertDelete {

  def exec(ctx: SymbolReaderContext): Unit = {
    ctx.getClasses.values.foreach((apexClass : ApexClass) => {
        println(apexClass.scopedName)
    })
  }
}
