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

import com.nawforce.pkgforce.names.Name
import com.nawforce.pkgforce.path.{Location, PathLike}
import ujson.Value

case class PackageDependent(projectPath: PathLike, config: ValueWithPositions, value: Value.Value) {

  val location: Location =
    config
      .lineAndOffsetOf(value)
      .map(lineAndOffset => Location(lineAndOffset._1, lineAndOffset._2))
      .getOrElse(Location.empty)

  val namespace: Option[Name] =
    value.optIdentifier(config, "namespace") match {
      case Some(ns) if ns.value.isEmpty =>
        config
          .lineAndOffsetOf(value("namespace"))
          .map(lineAndOffset => {
            throw SFDXProjectError(lineAndOffset, "'namespace' can not be empty")
          })
      case Some(ns) => Some(ns)
      case None     => None
    }

  val relativePath: Option[String] = value.optStringValue(config, "path")

}
