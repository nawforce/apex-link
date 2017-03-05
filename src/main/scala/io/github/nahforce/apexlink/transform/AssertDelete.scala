package io.github.nahforce.apexlink.transform

import io.github.nahforce.apexlink.cst._
import io.github.nahforce.apexlink.diff.FileChanger
import io.github.nahforce.apexlink.metadata.{ApexClass, SymbolReaderContext}

class AssertDelete {

  def exec(ctx: SymbolReaderContext, fileChanger: FileChanger): Unit = {
    ctx.getClasses.values.foreach((apexClass: ApexClass) => {
      apexClass.statements.foreach {
        case e@ExpressionStatement(LHSExpression(LHSExpression(PrimaryExpression(FieldPrimary(field)), RHSId(id)), RHSExpressionBlock(_, List())))
          if field.toLowerCase() == "system" && id.toLowerCase() == "assert" =>
          fileChanger.addChange(apexClass.location.filepath, e.start(), e.end(), None)
        case _ => None
      }
    })
  }
}
