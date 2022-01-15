package com.nawforce.runtime.parsers.antlr

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("antlr4ts", "BufferedTokenStream")
class BufferedTokenStream extends js.Object {
  def getHiddenTokensToRight(tokenIndex: Int): js.Array[Token] = js.native
  def getHiddenTokensToLeft(tokenIndex: Int): js.Array[Token]  = js.native
}
