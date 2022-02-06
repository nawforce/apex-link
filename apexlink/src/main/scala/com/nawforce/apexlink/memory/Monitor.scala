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
package com.nawforce.apexlink.memory

import com.nawforce.apexlink.api.ServerOps
import com.nawforce.apexlink.types.core.TypeDeclaration
import com.nawforce.pkgforce.diagnostics.LoggerOps

object Monitor {
  val map                                            = new SkinnyWeakSet[AnyRef]()
  var duplicateTypes: SkinnyWeakSet[TypeDeclaration] = _

  def push[T <: AnyRef](t: T): Unit = {
    if (ServerOps.getDuplicateObjectMonitoring)
      map.add(t)
  }

  def size: Int = map.size

  def reportDuplicateTypes(): Unit = {
    if (ServerOps.getDuplicateObjectMonitoring) {
      val tdsByName  = map.toSet.toArray.collect { case td: TypeDeclaration => (td.typeName, td) }
      val typeNames  = tdsByName.map(_._1)
      val duplicates = typeNames.toSeq.groupBy(identity).collect { case (t, Seq(_, _, _*)) => t }
      if (duplicates.nonEmpty) {
        duplicates.foreach(typeName => {
          LoggerOps.debug(s"Duplicate types found for $typeName")
        })
        duplicateTypes = new SkinnyWeakSet[TypeDeclaration]()
        duplicates.foreach(
          dup => tdsByName.filter(_._1 == dup).foreach(x => duplicateTypes.add(x._2))
        )
      }
    }
  }
}
