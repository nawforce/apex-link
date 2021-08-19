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
package com.nawforce.pkgforce.sfdx

import com.nawforce.pkgforce.diagnostics.Location
import com.nawforce.pkgforce.path.PathLike
import ujson.Value

case class PackageDirectory(path: PathLike,
                            relativePath: String,
                            location: Location,
                            name: Option[String],
                            version: Option[VersionNumber],
                            dependencies: Seq[ModuleDependent])

object PackageDirectory {
  def apply(projectPath: PathLike, config: ValueWithPositions, value: Value.Value): PackageDirectory = {
    val location = config.lineAndOffsetOf(value)
      .map(lineAndOffset => Location(lineAndOffset._1, lineAndOffset._2)).getOrElse(Location.empty)
    val relativePath = value.stringValue(config, "path")

    new PackageDirectory(projectPath.join(relativePath), relativePath, location,
      value.optStringValue(config, "package"),
      value.optVersionNumber(config, "versionNumber"),
      try {
        value("dependencies") match {
          case value: ujson.Arr =>
            value.value.toSeq.zipWithIndex.map(d =>
              ModuleDependent(config, d._1))
          case value =>
            config.lineAndOffsetOf(value).map(lineAndOffset => {
              throw SFDXProjectError(lineAndOffset, "'dependencies' should be an array")
            }).getOrElse(Seq.empty)
        }
      } catch {
        case _: NoSuchElementException => Seq.empty
      })
  }
}
