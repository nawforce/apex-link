package com.nawforce.runtime.cmds

import com.nawforce.common.path.PathFactory
import com.nawforce.common.workspace.Workspace

object Scan {

  def main(args: Array[String]): Unit = {
    if (args.length == 0) {
      println("Missing argument, expecting directory to scan")
      System.exit(-1)
    }

    if (args.length > 1) {
      println("Too many arguments, expecting directory to scan")
      System.exit(-1)
    }

    //Thread.sleep(30*1000)

    val start = System.currentTimeMillis()
    val path = PathFactory(args(0))
    val issuesAndWs = Workspace(path)
    issuesAndWs.issues.foreach(issue => println(issue.asString))
    val scanned = System.currentTimeMillis()
    println(issuesAndWs.value
      .map(ws => {
        val eventCount = ws.events.size
        val events = System.currentTimeMillis()
        s"Scanned $eventCount events, disk scan took ${scanned - start}ms, event loading took ${events - scanned}ms"
      })
      .getOrElse({
        "Workspace failed to load"
      }))
  }
}
