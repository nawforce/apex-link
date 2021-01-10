package com.nawforce.common.cmds

import com.nawforce.common.api.{Org, Package}
import com.nawforce.common.path.PathFactory

object OrgLoader {

  def load(args: Array[String], org: Org): Unit = {

    var paths: Array[String] = args
    if (paths.isEmpty)
      paths = Array(PathFactory("").toString)
    val nsSplit = paths.map(path => {
      if (path.endsWith("="))
        (path.take(path.length - 1), "")
      else
        path.split("=") match {
          case Array(d)     => ("", d)
          case Array(ns, d) => (ns, d)
          case _ =>
            throw new IllegalArgumentException(
              s"Badly formatted argument, expecting [namespace=]path or namespace=, got '$path'")
        }
    })

    // Check for bad use of a namespace on SFDX dir
    val namespacedSFDX = nsSplit
      .filter(_._1.nonEmpty)
      .map(nsDirPair => PathFactory(nsDirPair._2))
      .filter(_.join("sfdx-project.json").exists)
    if (namespacedSFDX.nonEmpty) {
      throw new IllegalArgumentException(
        s"Namespaces should not be provided for SFDX directories such as '${namespacedSFDX.head}''")
    }

    var nsLoaded: List[String] = Nil
    var loaded: List[Package] = Nil
    nsSplit.foreach(nsDirPair => {
      if (!nsLoaded.contains(nsDirPair._1)) {
        val path = PathFactory(nsDirPair._2)
        if (path.join("sfdx-project.json").exists) {
          loaded = org.newSFDXPackage(path.toString) :: loaded
        } else {
          val paths = nsSplit.filter(_._1 == nsDirPair._1).map(_._2).filterNot(_.isEmpty)
          val nonSfdxPaths =
            paths.map(PathFactory(_)).filterNot(_.join("sfdx-project.json").exists)
          val pkg =
            org.newMDAPIPackage(nsDirPair._1, nonSfdxPaths.map(_.toString), loaded.toArray)
          loaded = pkg :: loaded
          nsLoaded = nsDirPair._1 :: nsLoaded
        }
      }
    })
    org.flush()
  }
}
