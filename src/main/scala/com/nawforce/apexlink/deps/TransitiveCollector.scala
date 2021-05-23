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

package com.nawforce.apexlink.deps

import com.nawforce.apexlink.api.Org
import com.nawforce.pkgforce.names.{Name, Names, TypeIdentifier}

import scala.collection.mutable

/** Transitive dependency helper */
class TransitiveCollector(org: Org) {
  private val packagesByNamespace =
    org.getPackages().map(pkg => (Name(pkg.getNamespaces(false).head), pkg)).toMap

  def count(id: TypeIdentifier): Int = {
    transitives(id).length
  }

  def transitives(id: TypeIdentifier): Array[TypeIdentifier] = {
    val pkg = packagesByNamespace.get(id.namespace.getOrElse(Names.Empty))

    val depsSeen = mutable.Set[TypeIdentifier]()
    depsSeen.add(id)
    val deps = mutable.ArrayBuffer[TypeIdentifier]()
    deps.append(id)

    var current = 0
    while (current < deps.size) {
      pkg
        .map(pkg => {
          pkg.getDependencies(deps(current), outerInheritanceOnly = false)
        })
        .getOrElse(Array[TypeIdentifier]())
        .filterNot(depsSeen.contains)
        .foreach(t => {
          deps.append(t)
          depsSeen.add(t)
        })
      current += 1
    }
    deps.toArray
  }
}
