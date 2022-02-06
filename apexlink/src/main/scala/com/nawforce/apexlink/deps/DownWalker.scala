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
import com.nawforce.apexlink.types.apex.{ApexClassDeclaration, ApexDeclaration}
import com.nawforce.apexlink.types.core.DependencyHolder
import com.nawforce.pkgforce.names.{Name, TypeIdentifier}
import com.nawforce.pkgforce.parsers.{CLASS_NATURE, INTERFACE_NATURE}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/** Dependency information collected */
case class NodeData(
  id: TypeIdentifier,
  nature: String,
  transitiveCount: Int,
  extending: Array[TypeIdentifier],
  implementing: Array[TypeIdentifier],
  using: Array[TypeIdentifier]
)

/** Downstream dependency walker. Collects information on the dependencies of a type. */
class DownWalker(org: Org, apexOnly: Boolean) {

  private val packagesByNamespace =
    org.getPackages().map(pkg => (Name(pkg.getNamespaces(false).head), pkg)).toMap

  private val transitiveCollector = new TransitiveCollector(org, apexOnly)

  /* Collect information on dependencies of the passed identifiers. The walk depth can be limited but the result will
   * always include the root node(s) information as the first 'n' elements of the returned Array.
   */
  def walk(
    identifiers: Array[TypeIdentifier],
    depth: Int,
    ignoring: Array[TypeIdentifier]
  ): Array[NodeData] = {
    val collectedNodes = new ArrayBuffer[NodeData]()
    val collectedIds   = new mutable.HashMap[TypeIdentifier, Int]()

    val roots = identifiers
      .filterNot(i => ignoring.contains(i))
      .flatMap(i => createNode(i, ignoring))
    collectedNodes.addAll(roots)
    identifiers.foreach(i => collectedIds.addOne(i, 0))

    roots.foreach(root => {
      walkNode(root, collectedNodes, collectedIds, depth, ignoring)
    })
    collectedNodes.toArray
  }

  private def walkNode(
    node: NodeData,
    collector: ArrayBuffer[NodeData],
    collected: mutable.Map[TypeIdentifier, Int],
    depth: Int,
    ignoring: Array[TypeIdentifier]
  ): Unit = {
    if (depth == 0) return
    (node.extending ++ node.implementing ++ node.using)
      .filterNot(n => ignoring.contains(n))
      .foreach(id => {
        if (!collected.contains(id) || collected(id) <= depth) {
          collected.addOne(id, depth)
          createNode(id, ignoring).foreach(node => {
            findAndReplace(collector, node)
            walkNode(node, collector, collected, depth - 1, ignoring)
          })
        }
      })
  }

  private def findAndReplace(collector: ArrayBuffer[NodeData], nodeData: NodeData): Unit = {
    val index = collector.indexWhere(n => n.id.equals(nodeData.id))
    if (index >= 0)
      collector(index) = nodeData
    else
      collector.addOne(nodeData)
  }

  private def createNode(id: TypeIdentifier, ignoring: Array[TypeIdentifier]): Option[NodeData] = {
    org
      .asInstanceOf[OrgImpl]
      .findTypeIdentifier(id)
      .filter(td => !apexOnly || td.isInstanceOf[ApexDeclaration])
      .collect { case td: DependencyHolder => td }
      .map(td => {
        val pkg    = td.moduleDeclaration.map(_.pkg)
        val typeId = pkg.map(pkg => TypeIdentifier(pkg.namespace, td.typeName))

        val inherits =
          td.dependencies()
            .flatMap({
              case dt: ApexClassDeclaration => Some(dt.nature, dt.outerTypeId.asTypeIdentifier)
              case _                        => None
            })
            .filterNot(d => typeId.contains(d._2))
            .filterNot(d => ignoring.contains(d._2))
            .toSet
        val extending    = inherits.filter(id => id._1 == CLASS_NATURE).map(_._2)
        val implementing = inherits.filter(id => id._1 == INTERFACE_NATURE).map(_._2)
        val output       = extending ++ implementing

        val all =
          pkg
            .flatMap(pkg => {
              Option(pkg.getDependencies(id, outerInheritanceOnly = false, apexOnly))
            })
            .getOrElse(Array[TypeIdentifier]())
        val uses = all
          .filterNot(output.contains)
          .filterNot(id => typeId.contains(id))
          .filterNot(id => ignoring.contains(id))
        NodeData(
          id,
          td.nature.value,
          transitiveCollector.count(id, ignoring),
          extending.toArray,
          implementing.toArray,
          uses
        )
      })
  }
}
