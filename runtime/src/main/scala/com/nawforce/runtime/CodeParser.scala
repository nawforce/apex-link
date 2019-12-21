package com.nawforce.runtime

import java.io.ByteArrayInputStream

import com.nawforce.parsers._
import com.nawforce.path.PathLike
import com.nawforce.types.{SyntaxException, ThrowingErrorListener}
import org.antlr.v4.runtime.CommonTokenStream

object CodeParser {
  def parseCompilationUnit(path: PathLike, data: String): Either[SyntaxException, ApexParser.CompilationUnitContext] = {
    try {
      Right(createParser(path, data.getBytes()).compilationUnit())
    } catch {
      case ex: SyntaxException => Left(ex)
    }
  }

  def parseBlock(path: PathLike, data: Array[Byte]): Either[SyntaxException, ApexParser.BlockContext] = {
    try {
      Right(createParser(path, data).block())
    } catch {
      case ex: SyntaxException => Left(ex)
    }
  }

  private def createParser(path: PathLike, data: Array[Byte]): ApexParser = {
    val listener = new ThrowingErrorListener()
    val cis: CaseInsensitiveInputStream = new CaseInsensitiveInputStream(path.toString,
      new ByteArrayInputStream(data))
    val lexer: ApexLexer = new ApexLexer(cis)

    val tokens: CommonTokenStream = new CommonTokenStream(lexer)
    tokens.fill()

    val parser: ApexParser = new ApexParser(tokens)
    parser.removeErrorListeners()
    parser.addErrorListener(listener)
    parser
  }
}
