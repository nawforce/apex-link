/*
 Copyright (c) 2020 Kevin Jones, All rights reserved.
 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
    derived from this software without specific prior written permission.
 */
package com.nawforce.runtime.parsers

import com.nawforce.pkgforce.diagnostics.{Diagnostic, Issue, SYNTAX_CATEGORY}
import com.nawforce.pkgforce.path.{Location, PathLike}
import org.antlr.v4.runtime.{BaseErrorListener, RecognitionException, Recognizer}

import scala.collection.compat.immutable.ArraySeq
import scala.collection.mutable

class CollectingErrorListener(path: PathLike) extends BaseErrorListener {
  var _issues: mutable.ArrayBuffer[Issue] = _

  override def syntaxError(
    recognizer: Recognizer[_, _],
    offendingSymbol: Any,
    line: Int,
    charPositionInLine: Int,
    msg: String,
    e: RecognitionException
  ): Unit = {
    if (_issues == null)
      _issues = new mutable.ArrayBuffer[Issue]()

    _issues.addOne(
      new Issue(path, Diagnostic(SYNTAX_CATEGORY, Location(line, charPositionInLine), msg))
    )
  }

  def issues: ArraySeq[Issue] = {
    if (_issues != null)
      ArraySeq.unsafeWrapArray(_issues.toArray)
    else
      Issue.emptyArray
  }
}
