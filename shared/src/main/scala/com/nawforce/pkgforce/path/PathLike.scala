/*
 Copyright (c) 2019 Kevin Jones, All rights reserved.
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
package com.nawforce.pkgforce.path

import com.nawforce.runtime.parsers.SourceData

import scala.collection.compat.immutable.ArraySeq

/* File system path abstraction, to make life simple relative paths are NOT supported */
abstract class PathLike {

  // All paths have a base, it may be empty for root paths
  def basename: String

  // All paths have a parent, it may be the same path for root paths
  def parent: PathLike

  // Test if this path is the parent of another
  def isParentOf(other: PathLike): Boolean =
    other.toString.startsWith(toString) && other.toString != toString

  // Is anything present at path
  def exists: Boolean

  // Is this the root
  def isRoot: Boolean

  // Is this a directory
  def isDirectory: Boolean

  // Is this a regular file
  def isFile: Boolean

  // Size of regular file, returns 0 otherwise
  def size: Long

  // As native representation, platform specific escape
  def native: Any

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

  // Obtain entries in a directory split into regular files and subdirectories
  def splitDirectoryEntries(): (Array[PathLike], Array[PathLike])

  // Delete a file or directory, returns an error message on failure
  def delete(): Option[String]

  // Last modified timestamp
  def lastModified(): Option[Long]
}

object PathLike {
  val emptyPaths: ArraySeq[PathLike] = ArraySeq()
}
