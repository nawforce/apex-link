package com.nawforce.apexlink.memory

import com.nawforce.apexlink.api.ServerOps
import com.nawforce.apexlink.types.core.TypeDeclaration

object Monitor {
  val map = new SkinnyWeakSet[AnyRef]()
  var duplicateTypes: SkinnyWeakSet[TypeDeclaration] = _

  def push[T <: AnyRef](t: T): Unit = {
    if (ServerOps.getDuplicateObjectMonitoring)
      map.add(t)
  }

  def size: Int = map.size

  def reportDuplicateTypes(): Unit = {
    if (ServerOps.getDuplicateObjectMonitoring) {
      val tdsByName = map.toSet.toArray.collect { case td: TypeDeclaration => (td.typeName, td) }
      val typeNames = tdsByName.map(_._1)
      val duplicates = typeNames.toSeq.groupBy(identity).collect { case (t, Seq(_, _, _*)) => t }
      if (duplicates.nonEmpty) {
        duplicates.foreach(typeName => {
          ServerOps.debug(ServerOps.Trace, s"Duplicate types found for $typeName")
        })
        duplicateTypes = new SkinnyWeakSet[TypeDeclaration]()
        duplicates.foreach(dup =>
          tdsByName.filter(_._1 == dup).foreach(x => duplicateTypes.add(x._2)))
      }
    }
  }
}
