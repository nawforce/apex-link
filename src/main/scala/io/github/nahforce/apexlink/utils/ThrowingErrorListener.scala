package io.github.nahforce.apexlink.utils

import org.antlr.v4.runtime.{BaseErrorListener, RecognitionException, Recognizer}

class SyntaxException(val line: Int, val position: Int, val msg: String) extends Exception {
}

class ThrowingErrorListener extends BaseErrorListener {
  override def syntaxError(recognizer: Recognizer[_, _], offendingSymbol: Any, line: Int, charPositionInLine: Int, msg: String, e: RecognitionException) {
    throw new SyntaxException(line, charPositionInLine, msg)
  }
}