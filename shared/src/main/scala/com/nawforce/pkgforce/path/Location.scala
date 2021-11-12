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

package com.nawforce.pkgforce.path

import com.nawforce.pkgforce.api.IssueLocation
import upickle.default.{macroRW, ReadWriter => RW}

/** Location for identifying a sub-part of a file */
@upickle.implicits.key("Location")
final case class Location(
  val startLine: Int,
  val startPosition: Int,
  val endLine: Int,
  val endPosition: Int
) extends IssueLocation {

  override def startLineNumber(): Integer = startLine

  override def startCharOffset(): Integer = startPosition

  override def endLineNumber(): Integer = endLine

  override def endCharOffset(): Integer = endPosition

  def asJSON: String =
    s""""start": {"line": $startLine, "offset": $startPosition}, "end": {"line": $endLine, "offset": $endPosition}"""

  def displayPosition: String = {
    if (this == Location.all) {
      s"line $startLine"
    } else if (startLine == endLine) {
      if (startPosition == 0 && endPosition == 0)
        s"line $startLine"
      else if (startPosition == endPosition)
        s"line $startLine at $startPosition"
      else
        s"line $startLine at $startPosition-$endPosition"
    } else {
      if (startPosition == 0 && endPosition == 0)
        s"line $startLine to $endLine"
      else
        s"line $startLine:$startPosition to $endLine:$endPosition"
    }
  }

  def contains(line: Int, offset: Int): Boolean = {
    !(line < startLine || line > endLine ||
      (line == startLine && offset < startPosition) ||
      (line == endLine && offset > endPosition))
  }

  def contains(other: Location): Boolean = {
    contains(other.startLine, other.startPosition) &&
    contains(other.endLine, other.endPosition)
  }
}

object Location {
  implicit val rw: RW[Location] = macroRW

  val empty: Location = Location(1)
  val all: Location   = Location(1, 0, Int.MaxValue, 0)

  def apply(line: Int) = new Location(line, 0, line, 0)

  def apply(line: Int, position: Int) = new Location(line, position, line, position)

  def apply(startLine: Int, startPosition: Int, endLine: Int, endPosition: Int): Location = {
    new Location(startLine, startPosition, endLine, endPosition)
  }
}

sealed case class LocationAnd[T](location: Location, value: T)

/** Location within a specific file. */
@upickle.implicits.key("PathLocation")
final case class PathLocation(path: PathLike, location: Location) {
  override def toString: String = {
    s"$path: ${location.displayPosition}"
  }
}

object PathLocation {
  implicit val rw: RW[Location] = macroRW
}

/** Trait for things we can locate in some file at some position. */
trait Locatable {
  def location: PathLocation
}

/** Variation on locatable for when we don't know, may return null! */
trait UnsafeLocatable extends Locatable {
  def location: PathLocation

  def safeLocation: Option[PathLocation] = {
    val l = location
    if (l.path != null)
      Some(location)
    else
      None
  }
}

/** Base for things that might be positioned at some location, data is stored unwrapped to avoid object overhead. It's
  * an UnsafeLocatable because we can't be sure the mutable location will ever be set
  */
class Positionable extends UnsafeLocatable {
  private var locationPath: PathLike = _
  private var startLine: Int         = _
  private var startOffset: Int       = _
  private var endLine: Int           = _
  private var endOffset: Int         = _

  def setLocation(
    path: PathLike,
    startLine: Int,
    startOffset: Int,
    endLine: Int,
    endOffset: Int
  ): Unit = {
    this.locationPath = path
    this.startLine = startLine
    this.startOffset = startOffset
    this.endLine = endLine
    this.endOffset = endOffset
  }

  def location: PathLocation = {
    PathLocation(locationPath, Location(startLine, startOffset, endLine, endOffset))
  }
}

/** Extension of Locatable for things that can also provide an additional location for some form of identifier. */
trait IdLocatable extends Locatable {
  def idLocation: Location
  def idPathLocation: PathLocation = PathLocation(location.path, idLocation)
}
