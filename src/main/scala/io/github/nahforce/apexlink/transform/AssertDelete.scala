package io.github.nahforce.apexlink.transform

import io.github.nahforce.apexlink.cst._
import io.github.nahforce.apexlink.metadata.{ApexClass, SymbolReaderContext}

class AssertDelete {

  def exec(ctx: SymbolReaderContext): Unit = {
    ctx.getClasses.values.foreach((apexClass : ApexClass) => {
      val found : List[Expression] = apexClass.expressions.flatMap(e => {
        e match {
          case LHSExpression(LHSExpression(PrimaryExpression(FieldPrimary(field)),RHSId(id)), RHSExpressionBlock(_,List()))
            if field.toLowerCase() == "system" && id.toLowerCase() == "assert"  => Some(e)
          case _ => None
        }
      })
      if (found.nonEmpty ) {
        println(apexClass.scopedName)
        found.foreach(x => println(x.location()))
      }
    })
  }
}
