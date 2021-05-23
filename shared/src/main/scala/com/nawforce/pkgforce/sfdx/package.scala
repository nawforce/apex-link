/*
 [The "BSD licence"]
 Copyright (c) 2020 Kevin Jones
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
package com.nawforce.pkgforce

import com.nawforce.pkgforce.names.{Name, _}
import ujson.Value

package object sfdx {

  implicit class JSONConfig(config: Value.Value) {

    def stringValue(jsonPath: String, name: String): String = {
      optStringValue(jsonPath, name) match {
        case Some(s) => s
        case None =>
          throw new SFDXProjectError(s"$jsonPath.$name", s"'$name' is required")
      }
    }

    def optStringValue(jsonPath: String, name: String): Option[String] = {
      try {
        config(name) match {
          case ujson.Str(value) => Some(value)
          case _                => throw new SFDXProjectError(s"$jsonPath.$name", s"'$name' should be a string")
        }
      } catch {
        case _: NoSuchElementException => None
      }
    }

    def optIdentifier(jsonPath: String, name: String): Option[Name] = {
      config.optStringValue(jsonPath, name) match {
        case None | Some("") => None
        case Some(str) =>
          val ns = Name(str)
          Identifier.isLegalIdentifier(ns) match {
            case None => Some(ns)
            case Some(error) =>
              throw new SFDXProjectError(s"$jsonPath.$name",
                                         s"'$ns' is not a valid identifier, $error")
          }
      }
    }

    def optVersionNumber(jsonPath: String, name: String): Option[VersionNumber] = {
      val path = s"$jsonPath.$name"
      optStringValue(jsonPath, name).map(value => {

        val parts = value.split('.')
        if (parts.length != 4)
          throw new SFDXProjectError(
            path,
            s"'$value' version should contain four parts, major.minor.patch.build")

        VersionNumber(parseVersionNumber(path, parts.head),
                      parseVersionNumber(path, parts(1)),
                      parseVersionNumber(path, parts(2)),
                      parseVersionNumberOrLabel(path, parts(3)),
        )
      })
    }

    private def parseVersionNumber(jsonPath: String, part: String): Int = {
      try {
        part.toInt
      } catch {
        case _: NumberFormatException =>
          throw new SFDXProjectError(s"$jsonPath", s"'$part' should be an integer value")
      }
    }

    private def parseVersionNumberOrLabel(jsonPath: String, part: String): BuildNumber = {
      if (part == "NEXT")
        NextBuild
      else if (part == "LATEST")
        LatestBuild
      else
        Build(parseVersionNumber(jsonPath, part))
    }
  }
}
