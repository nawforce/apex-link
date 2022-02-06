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
package com.nawforce.apexlink.rpc

import com.nawforce.pkgforce.path.Location
import io.github.shogowada.scala.jsonrpc.serializers.JSONRPCPickler.{macroRW, ReadWriter => RW}

/** A link for a given position, can be used for definition or reference links. origin is the calculated extent of
  * the link that was selected. targetPath is where the definition or reference refers to. Within target path, target
  * is the full extend of the definition whereas targetSelection is just the extent of the link itself.
  */
case class LocationLink(
  origin: Location,
  targetPath: String,
  target: Location,
  targetSelection: Location
)

object LocationLink {
  implicit val rw: RW[LocationLink]     = macroRW
  implicit val rwLocation: RW[Location] = macroRW
}
