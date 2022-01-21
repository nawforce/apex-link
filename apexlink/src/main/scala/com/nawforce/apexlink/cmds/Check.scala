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

import com.nawforce.apexlink.api.{Org, ServerOps}
import com.nawforce.apexlink.org.OrgImpl
import com.nawforce.apexlink.plugins.{PluginsManager, UnusedPlugin}
import com.nawforce.pkgforce.api.IssueLocation
import com.nawforce.pkgforce.diagnostics.{DefaultLogger, LoggerOps}
import com.nawforce.runtime.platform.Environment

import scala.jdk.CollectionConverters._

/** Basic command line for exercising the project analysis */
object Check {
  final val STATUS_OK: Int = 0
  final val STATUS_ARGS: Int = 1
  final val STATUS_EXCEPTION: Int = 3
  final val STATUS_ISSUES: Int = 4

  def usage(name: String) =
    s"Usage: $name [-json] [-verbose [-unused]] [-info|-debug] [-nocache] [-depends] <directory>"

  def run(args: Array[String]): Int = {
    val flags = Set("-json", "-verbose", "-info", "-debug", "-nocache", "-unused", "-depends")

    val json = args.contains("-json")
    val verbose = !json && args.contains("-verbose")
    val debug = !json && args.contains("-debug")
    val info = !json && !debug && args.contains("-info")
    val depends = args.contains("-depends")
    val noCache = args.contains("-nocache")
    val unused = args.contains("-unused")

    // Check we have some metadata directories to work with
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
      // Setup cache flushing and logging defaults
      ServerOps.setAutoFlush(false)
      LoggerOps.setLogger(new DefaultLogger(System.out))
      if (debug)
        LoggerOps.setLoggingLevel(LoggerOps.DEBUG_LOGGING)
      else if (info)
        LoggerOps.setLoggingLevel(LoggerOps.INFO_LOGGING)

      // Disable loading from the cache
      if (noCache) {
        Environment.setCacheDirOverride(Some(None))
      }

      // Don't use unused analysis unless we have both verbose and unused flags
      if (!verbose || !unused) {
        PluginsManager.removePlugins(Seq(classOf[UnusedPlugin]))
      }

      // Load org and flush to cache if we are using it
      val org = Org.newOrg(dirs.head)
      if (!noCache) {
        org.flush()
      }

      org.asInstanceOf[OrgImpl].getTestClassNames(
        Array("/Users/kjones/ff/prds/bc/force-app/main/opportunities/classes/OpportunityToContractPostPlugin.cls"),
        findTests = true)

      // Output issues
      if (depends) {
        if (json) {
          writeDependenciesAsJSON(org)
        } else {
          writeDependenciesAsCSV(org)
        }
        STATUS_OK
      } else {
        writeIssues(org, json, verbose)
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

      buffer ++= s"""{ "name": "${kv._1}", "dependencies": ["""
      buffer ++= kv._2.map("\"" + _ + "\"").mkString(", ")
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
                          asJSON: Boolean,
                          includeWarnings: Boolean,
                         ): Int = {

    val issues = org.issues.issuesForFiles(null, includeWarnings, 0)
    val writer = if (asJSON) new JSONMessageWriter() else new TextMessageWriter()
    writer.startOutput()
    var hasErrors = false
    var lastPath = ""

    issues.foreach(issue => {
      hasErrors |= issue.isError()
      if (includeWarnings || issue.isError) {

        if (issue.filePath() != lastPath) {
          if (lastPath.nonEmpty)
            writer.endDocument()
          lastPath = issue.filePath()
          writer.startDocument(lastPath)
        }

        writer.writeMessage(issue.category(), issue.fileLocation(), issue.message)

      }
    })
    if (lastPath.nonEmpty)
      writer.endDocument()

    print(writer.output)
    System.out.flush()
    if (hasErrors) STATUS_ISSUES else STATUS_OK
  }

  private trait MessageWriter {
    def startOutput(): Unit

    def startDocument(path: String): Unit

    def writeMessage(category: String, location: IssueLocation, message: String): Unit

    def endDocument(): Unit

    def output: String
  }

  private class TextMessageWriter(showPath: Boolean = true) extends MessageWriter {
    private val buffer = new StringBuilder()

    override def startOutput(): Unit = buffer.clear()

    override def startDocument(path: String): Unit = if (showPath) buffer ++= path + '\n'

    override def writeMessage(
                               category: String,
                               location: IssueLocation,
                               message: String
                             ): Unit =
      buffer ++= s"$category: ${location.displayPosition}: $message\n"

    override def endDocument(): Unit = {}

    override def output: String = buffer.toString()
  }

  private class JSONMessageWriter extends MessageWriter {
    private val buffer = new StringBuilder()
    private var firstDocument: Boolean = _
    private var firstMessage: Boolean = _

    override def startOutput(): Unit = {
      buffer.clear()
      buffer ++= s"""{ "files": [\n"""
      firstDocument = true
    }

    override def startDocument(path: String): Unit = {
      buffer ++= (if (firstDocument) "" else ",\n")
      buffer ++= s"""{ "path": "${JSON.encode(path)}", "messages": [\n"""
      firstDocument = false
      firstMessage = true
    }

    override def writeMessage(
                               category: String,
                               location: IssueLocation,
                               message: String
                             ): Unit = {
      buffer ++= (if (firstMessage) "" else ",\n")
      buffer ++= s"""{${locationAsJSON(location)}, "category": "$category", "message": "${JSON.encode(message)}"}"""
      firstMessage = false
    }

    override def endDocument(): Unit = buffer ++= "\n]}"

    override def output: String = {
      buffer ++= "]}\n"
      buffer.toString()
    }

    private def locationAsJSON(location: IssueLocation): String =
      s""""start": {"line": ${location.startLineNumber()}, "offset": ${location.startCharOffset()} }, "end": {"line": ${location.endLineNumber()}, "offset": ${location.endCharOffset()} }"""
  }

  object JSON {
    def encode(value: String): String = {
      val buf = new StringBuilder()
      value.foreach {
        case '"' => buf.append("\\\"")
        case '\\' => buf.append("\\\\")
        case '\b' => buf.append("\\b")
        case '\f' => buf.append("\\f")
        case '\n' => buf.append("\\n")
        case '\r' => buf.append("\\r")
        case '\t' => buf.append("\\t")
        case char if char < 0x20 => buf.append("\\u%04x".format(char: Int))
        case char => buf.append(char)
      }
      buf.mkString
    }
  }

}
