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

package com.nawforce.apexlink.cmds

import com.nawforce.apexlink.api.{IssueOptions, Org, ServerOps}
import com.nawforce.pkgforce.diagnostics.LoggerOps
import com.nawforce.runtime.json.JSON

import scala.jdk.CollectionConverters._

object Check {
  final val STATUS_OK: Int = 0
  final val STATUS_ARGS: Int = 1
  final val STATUS_EXCEPTION: Int = 3
  final val STATUS_ISSUES: Int = 4

  def usage(name: String) =
    s"Usage: $name [-json] [-verbose|-debug] [-zombie] [-depends] <directory>"

  def main(name: String, args: Array[String]): Int = {
    val flags = Set("-verbose", "-debug", "-json", "-pickle", "-zombie", "-depends")

    val json = args.contains("-json")
    val pickle = args.contains("-pickle")
    val verbose = !json && args.contains("-verbose")
    val debug = !json && args.contains("-debug")
    val depends = args.contains("-depends")
    val zombie = args.contains("-zombie")

    ServerOps.setAutoFlush(false)
    if (debug)
      LoggerOps.setLoggingLevel(LoggerOps.DEBUG_LOGGING)
    else if (verbose)
      LoggerOps.setLoggingLevel(LoggerOps.INFO_LOGGING)

    if (json && pickle) {
      System.err.println("-json and -pickle can not be used together")
      return STATUS_ARGS
    }

    val dirs = args.filterNot(flags.contains)
    if (dirs.isEmpty) {
      System.err.println(s"No workspace directory argument provided.")
      return STATUS_ARGS
    }
    if (dirs.length > 1) {
      System.err.println(
        s"Multiple arguments provided, expected workspace directory, '${dirs.mkString(", ")}'}")
      return STATUS_ARGS
    }

    try {
      val org = Org.newOrg(dirs.head)
      if (depends) {
        if (json) {
          writeDependenciesAsJSON(org)
        } else {
          writeDependenciesAsCSV(org)
        }
        STATUS_OK
      } else {
        val format = if (pickle) "pickle" else if (json) "json" else ""
        writeIssues(org, format, verbose, zombie)
      }

    } catch {
      case ex: Throwable =>
        ex.printStackTrace(System.err)
        STATUS_EXCEPTION
    }
  }

  private def writeDependenciesAsJSON(org: Org): Unit = {
    val buffer = new StringBuilder()
    var first = true
    buffer ++= s"""{ "dependencies": [\n"""
    org.getDependencies.asScala.foreach(kv => {
      if (!first)
        buffer ++= ",\n"
      first = false

      buffer ++= s"""{ "name": "${JSON.encode(kv._1)}", "dependencies": ["""
      buffer ++= kv._2.map("\"" + JSON.encode(_) + "\"").mkString(", ")
      buffer ++= s"]}"
    })
    buffer ++= "]}\n"
    print(buffer.mkString)
  }

  private def writeDependenciesAsCSV(org: Org): Unit = {
    org.getDependencies.asScala.foreach(kv => {
      println(s"${kv._1}, ${kv._2.mkString(", ")}")
    })
  }

  private def writeIssues(org: Org,
                          format: String,
                          includeWarnings: Boolean,
                          includeZombies: Boolean): Int = {
    val issueOptions = new IssueOptions()
    issueOptions.format = format
    issueOptions.includeWarnings = false
    issueOptions.includeZombies = false
    val hasErrors = org.getIssues(issueOptions).nonEmpty

    issueOptions.includeWarnings = includeWarnings
    issueOptions.includeZombies = includeZombies
    val issues = org.getIssues(issueOptions)
    // Workaround for pickle output having an extra unicode character at end of stream if just print() it
    if (format == "pickle")
      println(issues)
    else
      print(issues)
    System.out.flush()
    if (hasErrors) STATUS_ISSUES else STATUS_OK
  }
}
