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
package com.nawforce.apexlink.org

import com.nawforce.apexlink.api.IssuesCollection
import com.nawforce.pkgforce.api.{IssueLocation, Issue => APIIssue}
import com.nawforce.pkgforce.diagnostics
import com.nawforce.pkgforce.diagnostics.{DiagnosticCategory, Issue, IssueLog}
import com.nawforce.pkgforce.path.{Location, PathLike}
import com.nawforce.runtime.platform.Path

import scala.collection.mutable

/** IssuesCollection implementation, holds Issues for each metadata file and tracks when they change to allow
  * clients to be more selective when pulling issues.
  * TODO: Deprecate IssueLog from pkgforce
  */
class IssuesManager extends IssueLog with IssuesCollection {

  private val hasChanged = mutable.HashSet[PathLike]()

  override def hasUpdatedIssues: Array[String] = {
    hasChanged.map(_.toString).toArray
  }

  override def ignoreUpdatedIssues(path: String): Unit = {
    hasChanged.remove(Path(path))
  }

  override def issuesForFile(path: String): Array[APIIssue] = {
    hasChanged.remove(Path(path))
    getIssues.getOrElse(Path(path), Nil).toArray
  }

  override def issuesForFileLocation(path: String, location: IssueLocation): Array[APIIssue] = {
    val loc = Location(location.startLineNumber(), location.startCharOffset(), location.endLineNumber(), location.endCharOffset())
    getIssues.getOrElse(Path(path), Nil)
      .filter(issue => loc.contains(issue.diagnostic.location))
      .toArray[APIIssue]
  }

  override def issuesForFiles(paths: Array[String], includeWarnings: Boolean, maxErrorsPerFile: Int): Array[APIIssue] = {
    val issues = getIssues
    val files = if (paths == null || paths.isEmpty) issues.keys else paths.map(p => Path(p)).toIterable

    val buffer = mutable.ArrayBuffer[Issue]()
    files.foreach(file => {
      var fileIssues = issues(file)
        .filter(issue => includeWarnings || DiagnosticCategory.isErrorType(issue.diagnostic.category))
        .sorted(Issue.ordering)
      if (maxErrorsPerFile > 0)
        fileIssues = fileIssues.take(maxErrorsPerFile)
      buffer.addAll(fileIssues)
      hasChanged.remove(file)
    })
    buffer.toArray
  }

  override def clear(): Unit = {
    hasChanged.clear()
    super.clear()
  }

  override def add(issue: diagnostics.Issue): Unit = {
    hasChanged.add(issue.path)
    super.add(issue)
  }

  override def pop(path: PathLike): List[diagnostics.Issue] = {
    hasChanged.add(path)
    super.pop(path)
  }

  override def push(path: PathLike, issues: List[diagnostics.Issue]): Unit = {
    hasChanged.add(path)
    super.push(path, issues)
  }
}
