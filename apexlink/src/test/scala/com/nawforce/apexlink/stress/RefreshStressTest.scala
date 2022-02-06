/*
 Copyright (c) 2021 Kevin Jones, All rights reserved.
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
package com.nawforce.apexlink.stress

import com.nawforce.apexlink.api.{IssueOptions, Org, ServerOps}
import com.nawforce.apexlink.org.OrgImpl
import com.nawforce.pkgforce.diagnostics.LoggerOps
import com.nawforce.pkgforce.documents.ApexNature

import java.util.concurrent.ThreadLocalRandom

object RefreshStressTest {

  def main(args: Array[String]): Unit = {
    ServerOps.setAutoFlush(false)
    LoggerOps.setLoggingLevel(LoggerOps.DEBUG_LOGGING)

    if (args.isEmpty) {
      System.err.println(s"No workspace directory argument provided.")
      return
    }
    if (args.length > 1) {
      System.err.println(
        s"Multiple arguments provided, expected workspace directory, '${args.mkString(", ")}'}"
      )
      return
    }

    val org          = Org.newOrg(args.head).asInstanceOf[OrgImpl]
    val issueOptions = new IssueOptions
    issueOptions.includeZombies = true
    val baselineIssues = org.issues.issuesForFiles(null, includeWarnings = false, 0).length
    println(s"Starting issue count $baselineIssues")

    val classFiles =
      org.packages.flatten(pkg => pkg.modules.flatMap(m => m.index.get(ApexNature).map(_.path)))

    while (true) {
      val randoFile = classFiles(ThreadLocalRandom.current.nextInt(0, classFiles.size))
      println(s"Refreshing $randoFile")
      randoFile
        .read()
        .toOption
        .foreach(contents => {
          var c = contents
          if (contents.startsWith("/* RND:")) {
            c = contents.replaceFirst("/\\* RND: [0-9]* \\*/", "")
          }
          c = s"/* RND: ${ThreadLocalRandom.current.nextInt(0, 100000)} */" + c
          randoFile.write(c)
        })

      org.packages.foreach(_.refresh(randoFile.toString))
      org.flush()

      if (org.issues.issuesForFiles(null, includeWarnings = false, 0).length != baselineIssues) {
        println("Issue count has changed, quiting")
        return
      }
    }
  }
}
