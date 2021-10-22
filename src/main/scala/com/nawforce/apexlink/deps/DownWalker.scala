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
import com.nawforce.apexlink.org.OrgImpl
import com.nawforce.apexlink.types.apex.ApexDeclaration
import com.nawforce.pkgforce.names.{Name, Names, TypeIdentifier}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/** Dependency information collected */
case class NodeData(id: TypeIdentifier,
                    nature: String,
                    transitiveCount: Int,
                    extending: Array[TypeIdentifier],
                    implementing: Array[TypeIdentifier],
                    using: Array[TypeIdentifier])

/** Downstream dependency walker. Collects information on the dependencies of a type. */
class DownWalker(org: Org, apexOnly: Boolean) {

  private val packagesByNamespace =
    org.getPackages().map(pkg => (Name(pkg.getNamespaces(false).head), pkg)).toMap

  private val transitiveCollector = new TransitiveCollector(org, apexOnly)

  /* Collect information on dependencies of the passed identifiers. The walk depth can be limited but the result will
   * always include the root node(s) information as the first 'n' elements of the returned Array.
   */
  def walk(identifiers: Array[TypeIdentifier], depth: Int): Array[NodeData] = {
    val collectedNodes = new ArrayBuffer[NodeData]()
    val collectedIds = new mutable.HashSet[TypeIdentifier]()

    val roots = identifiers.flatMap(createNode)
    collectedNodes.addAll(roots)
    collectedIds.addAll(identifiers)

    roots.foreach(root => {
      walkNode(root, collectedNodes, collectedIds, depth)
    })
    collectedNodes.toArray
  }

  private def walkNode(node: NodeData,
                       collector: ArrayBuffer[NodeData],
                       collected: mutable.Set[TypeIdentifier],
                       depth: Int): Unit = {
    if (depth == 0) return
    (node.extending ++ node.implementing ++ node.using).foreach(id => {
      if (!collected.contains(id)) {
        collected.add(id)
        createNode(id).foreach(node => {
          collector.append(node)
          walkNode(node, collector, collected, depth - 1)
        })
      }
    })
  }

  private def createNode(id: TypeIdentifier): Option[NodeData] = {
    org
      .asInstanceOf[OrgImpl]
      .findTypeIdentifier(id)
      .filter(td => !apexOnly || td.isInstanceOf[ApexDeclaration])
      .map(td => {
        val pkg = td.moduleDeclaration.map(_.pkg)

        val inherits =
          pkg
            .flatMap(pkg => {
              Option(pkg.getDependencies(id, outerInheritanceOnly = true, apexOnly))
            })
            .getOrElse(Array[TypeIdentifier]())
            .toSet
        val extending = inherits.filter(id => nature(id) == "class")
        val implementing = inherits.filter(id => nature(id) == "interface")

        val all =
          pkg
            .flatMap(pkg => {
              Option(pkg.getDependencies(id, outerInheritanceOnly = false, apexOnly))
            })
            .getOrElse(Array[TypeIdentifier]())
        val uses = all.filterNot(inherits.contains)

        NodeData(id, nature(id), transitiveCollector.count(id), extending.toArray, implementing.toArray, uses)
      })
  }

  private def nature(id: TypeIdentifier): String = {
    val pkg = packagesByNamespace.get(id.namespace.getOrElse(Names.Empty))
    pkg.flatMap(pkg => Option(pkg.getSummaryOfType(id)).map(_.nature)).getOrElse("")
  }
}
