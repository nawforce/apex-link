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
package com.nawforce.pkgforce.sfdx

import com.nawforce.pkgforce.path.{Location, PathLike}
import ujson.Value

case class PackageDirectory(
  relativePath: String,
  location: Location,
  name: Option[String],
  version: Option[VersionNumber],
  dependencies: Seq[ModuleDependent]
)

object PackageDirectory {
  def apply(
    projectPath: PathLike,
    config: ValueWithPositions,
    value: Value.Value
  ): PackageDirectory = {
    val location = config
      .lineAndOffsetOf(value)
      .map(lineAndOffset => Location(lineAndOffset._1, lineAndOffset._2))
      .getOrElse(Location.empty)
    val relativePath = value.stringValue(config, "path")

    new PackageDirectory(
      relativePath,
      location,
      value.optStringValue(config, "package"),
      value.optVersionNumber(config, "versionNumber"),
      try {
        value("dependencies") match {
          case value: ujson.Arr =>
            value.value.toSeq.zipWithIndex.map(d => ModuleDependent(config, d._1))
          case value =>
            config
              .lineAndOffsetOf(value)
              .map(lineAndOffset => {
                throw SFDXProjectError(lineAndOffset, "'dependencies' should be an array")
              })
              .getOrElse(Seq.empty)
        }
      } catch {
        case _: NoSuchElementException => Seq.empty
      }
    )
  }

  def fromUnpackagedMetadata(
    projectPath: PathLike,
    config: ValueWithPositions,
    value: Value.Value
  ): PackageDirectory = {
    val location = config
      .lineAndOffsetOf(value)
      .map(lineAndOffset => Location(lineAndOffset._1, lineAndOffset._2))
      .getOrElse(Location.empty)

    val relativePath = value match {
      case ujson.Str(value) => Some(value)
      case _ =>
        config
          .lineAndOffsetOf(value)
          .map(
            lineAndOffset =>
              throw SFDXProjectError(
                lineAndOffset,
                "'unpackagedMetadata' entries should all be strings"
              )
          )
        None
    }

    new PackageDirectory(relativePath.get, location, None, None, Seq())
  }

}
