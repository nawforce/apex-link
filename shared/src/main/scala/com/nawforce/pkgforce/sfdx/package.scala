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
package com.nawforce.pkgforce

import com.nawforce.pkgforce.names._
import ujson.Value

package object sfdx {

  implicit class JSONConfig(root: Value.Value) {

    def stringValue(config: ValueWithPositions, name: String): String = {
      optStringValue(config, name) match {
        case Some(s) => s
        case None =>
          config
            .lineAndOffsetOf(root)
            .map(lineAndOffset => {
              throw SFDXProjectError(lineAndOffset, s"'$name' is required")
            })
            .orNull
      }
    }

    def optStringValue(config: ValueWithPositions, name: String): Option[String] = {
      try {
        root(name) match {
          case ujson.Str(value) => Some(value)
          case value =>
            config
              .lineAndOffsetOf(value)
              .map(lineAndOffset => {
                throw SFDXProjectError(lineAndOffset, s"'$name' should be a string")
              })
        }
      } catch {
        case _: NoSuchElementException => None
      }
    }

    def optIdentifier(config: ValueWithPositions, name: String): Option[Name] = {
      root.optStringValue(config, name) match {
        case None | Some("") => None
        case Some(str) =>
          val ns = Name(str)
          Identifier.isLegalIdentifier(ns) match {
            case None => Some(ns)
            case Some(error) =>
              config
                .lineAndOffsetOf(root(name))
                .map(lingAndOffset => {
                  throw SFDXProjectError(lingAndOffset, s"'$ns' is not a valid identifier, $error")
                })
          }
      }
    }

    def optVersionNumber(config: ValueWithPositions, name: String): Option[VersionNumber] = {
      optStringValue(config, name).flatMap(value => {
        config
          .lineAndOffsetOf(root(name))
          .map(lineAndOffset => {
            val parts = value.split('.')
            if (parts.length != 4) {
              throw SFDXProjectError(
                lineAndOffset,
                s"'$value' version should contain four parts, major.minor.patch.build"
              )
            }

            VersionNumber(
              parseVersionNumber(lineAndOffset, parts.head),
              parseVersionNumber(lineAndOffset, parts(1)),
              parseVersionNumber(lineAndOffset, parts(2)),
              parseVersionNumberOrLabel(lineAndOffset, parts(3))
            )
          })
      })
    }

    private def parseVersionNumber(lineAndOffset: (Int, Int), part: String): Int = {
      try {
        part.toInt
      } catch {
        case _: NumberFormatException =>
          throw SFDXProjectError(lineAndOffset, s"'$part' should be an integer value")
      }
    }

    private def parseVersionNumberOrLabel(lineAndOffset: (Int, Int), part: String): BuildNumber = {
      if (part == "NEXT")
        NextBuild
      else if (part == "LATEST")
        LatestBuild
      else {
        try {
          Build(part.toInt)
        } catch {
          case _: NumberFormatException =>
            throw SFDXProjectError(
              lineAndOffset,
              s"'$part' should be an integer value, or NEXT or LATEST"
            )
        }
      }
    }
  }
}
