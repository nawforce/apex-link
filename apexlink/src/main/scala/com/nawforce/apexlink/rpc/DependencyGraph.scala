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
package com.nawforce.apexlink.rpc

import com.nawforce.pkgforce.names.{Name, TypeIdentifier, TypeName}
import io.github.shogowada.scala.jsonrpc.serializers.JSONRPCPickler.{macroRW, ReadWriter => RW}

/** Dependency information for a given, typically this will be depth limited to avoid massive graphs. */
case class DependencyGraph(nodeData: Array[DependencyNode], linkData: Array[DependencyLink])

object DependencyGraph {
  implicit val rw: RW[DependencyGraph]              = macroRW
  implicit val rwNode: RW[DependencyNode]           = macroRW
  implicit val rwLink: RW[DependencyLink]           = macroRW
  implicit val rwTypeIdentifier: RW[TypeIdentifier] = macroRW
  implicit val rwTypeName: RW[TypeName]             = macroRW
  implicit val rwName: RW[Name]                     = macroRW
}

/** Node of a dependency graph, represents some kind of type declaration. */
case class DependencyNode(
  identifier: TypeIdentifier,
  size: Long,                          // Size of metadata in bytes
  nature: String,                      // Nature of types, class, interface or enum
  transitiveCount: Int,                // Sum of all dependant types
  extending: Array[TypeIdentifier],    // Types that this type extends
  implementing: Array[TypeIdentifier], // Types that this type implements
  using: Array[TypeIdentifier]
) // Other types that this type depends on
{
  override def equals(that: Any): Boolean = {
    that match {
      case other: DependencyNode =>
        other.canEqual(this) && doesEqual(other)
      case _ => false
    }
  }

  override def canEqual(that: Any): Boolean = that.isInstanceOf[DependencyNode]

  private def doesEqual(other: DependencyNode): Boolean = {
    identifier == other.identifier &&
    size == other.size &&
    transitiveCount == other.transitiveCount &&
    extending.sameElements(other.extending) &&
    implementing.sameElements(other.implementing) &&
    using.sameElements(other.using)
  }
}

/** Link between nodes in a dependency graph. */
case class DependencyLink(source: Int, target: Int, nature: String)
