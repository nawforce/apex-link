/*
 Copyright (c) 2021 Kevin Jones, All rights reserved.
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
package com.nawforce.pkgforce.api

trait IssueLocation {
  def startLineNumber(): Int
  def startCharOffset(): Int
  def endLineNumber(): Int
  def endCharOffset(): Int

  def displayPosition: String = {
    if (
      startLineNumber() == 1 && endLineNumber() == Int.MaxValue && startCharOffset() == 0 && endCharOffset() == 0
    ) {
      s"line 1"
    } else if (startLineNumber() == endLineNumber()) {
      if (startCharOffset() == 0 && endCharOffset() == 0)
        s"line ${startLineNumber()}"
      else if (startCharOffset() == endCharOffset())
        s"line ${startLineNumber()} at ${startCharOffset()}"
      else
        s"line ${startLineNumber()} at ${startCharOffset()}-${endCharOffset()}"
    } else {
      if (startCharOffset() == 0 && endCharOffset() == 0)
        s"line ${startLineNumber()} to ${endLineNumber()}"
      else
        s"line ${startLineNumber()}:${startCharOffset()} to ${endLineNumber()}:${endCharOffset()}"
    }
  }

  def contains(line: Int, offset: Int): Boolean = {
    !(line < startLineNumber() || line > endLineNumber() ||
      (line == startLineNumber() && offset < startCharOffset()) ||
      (line == endLineNumber() && offset > endCharOffset()))
  }

  def contains(other: IssueLocation): Boolean = {
    contains(other.startLineNumber(), other.startCharOffset()) &&
    contains(other.endLineNumber(), other.endCharOffset())
  }
}
