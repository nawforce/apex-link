/*
 [The "BSD licence"]
 Copyright (c) 2019 Kevin Jones
 All rights reserved.

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

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.nawforce.common.path

import com.nawforce.runtime.parsers.SourceData

/* File system path abstraction, to make life simple relative paths are NOT supported */
abstract class PathLike {

  // All paths have a base, it may be empty for root paths
  val basename: String

  // All paths have a parent, it may be the same path for root paths
  val parent: PathLike

  // Is anything present at path
  val exists: Boolean

  // Is this the root
  val isRoot: Boolean

  // Is this a directory
  val isDirectory: Boolean

  // Is this a regular file
  val isFile: Boolean

  // Size of regular file, returns 0 otherwise
  val size: Long

  // As native representation, platform specific escape
  val native: Any

  // Join some new text to end of path & normalise
  def join(arg: String): PathLike

  // Create a file if this is a directory (silently overwriting), returns new Path or error message
  def createFile(name: String, data: String): Either[String, PathLike]

  // Read the contents of a file or return an error message
  def read(): Either[String, String]

  // Read the contents of a file or return an error message
  def readBytes(): Either[String, Array[Byte]]

  // Read the contents of a file or return an error message
  def readSourceData(): Either[String, SourceData]

  // Write a file or return an error message
  def write(data: String): Option[String]

  // Write a file or return an error message
  def write(data: Array[Byte]): Option[String]

  // Create a directory if this is a directory (ignores if already exists), returns new Path or error message
  def createDirectory(name: String): Either[String, PathLike]

  // List contents of directory or return error message
  def directoryList(): Either[String, Seq[String]]

  // Delete a file or directory, returns an error message on failure
  def delete(): Option[String]

  // Last modified timestamp
  def lastModified(): Option[Long]
}
