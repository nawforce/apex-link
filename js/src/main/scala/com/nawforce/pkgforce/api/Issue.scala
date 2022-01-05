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

trait Issue {
  /* The file path where the issue was found */
  def filePath(): String

  /* The location within the file */
  def fileLocation(): IssueLocation

  /* The category of the issue, one of "Syntax", "Error", "Missing", "Warning" or "Unused" */
  def category(): String

  /* The issue message */
  def message(): String

  /* Is this considered an error issue, rather than a warning */
  def isError(): java.lang.Boolean

  /* Format as String, filePath is omitted to avoid duplicating over multiple Issues */
  def asString: String = category() + ": " + fileLocation().displayPosition + ": " + message()

  override def toString: String = filePath() + ": " + asString
}
