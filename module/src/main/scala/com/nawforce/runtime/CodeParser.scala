package com.nawforce.runtime

import com.nawforce.parsers._
import com.nawforce.path.PathLike

import scala.scalajs.js.JavaScriptException

object CodeParser {
  def parseCompilationUnit(path: PathLike, data: String): Either[SyntaxException, CompilationUnit] = {
    val listener = new ThrowingErrorListener()
    val cis: CaseInsensitiveInputStream = new CaseInsensitiveInputStream(path.absolute.toString, data)
    val lexer: ApexLexer = new ApexLexer(cis)

    val tokens: CommonTokenStream = new CommonTokenStream(lexer)
    tokens.fill()

    val parser: ApexParser = new ApexParser(tokens)
    parser.removeErrorListeners()
    parser.addErrorListener(listener)
    try {
      Right(parser.compilationUnit())
    } catch {
      case ex: JavaScriptException => Left(ex.exception.asInstanceOf[SyntaxException])
    }
  }

}
