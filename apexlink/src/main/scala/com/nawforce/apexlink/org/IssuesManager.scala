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
import com.nawforce.pkgforce.diagnostics._
import com.nawforce.pkgforce.path.{Location, PathLike}
import com.nawforce.runtime.platform.Path

import scala.collection.mutable

/** IssuesCollection implementation, holds Issues for each metadata file and tracks when they change to allow
  * clients to be more selective when pulling issues.
  */
class IssuesManager extends IssuesCollection with IssueLogger {
  private val log             = mutable.HashMap[PathLike, List[Issue]]() withDefaultValue List()
  private val possibleMissing = mutable.HashSet[PathLike]()
  private val hasChanged      = mutable.HashSet[PathLike]()

  def isEmpty: Boolean = log.isEmpty

  def nonEmpty: Boolean = log.nonEmpty

  override def log(issue: Issue): Unit = add(issue)

  def clear(): Unit = {
    hasChanged.clear()
    log.clear()
  }

  def add(issue: diagnostics.Issue): Unit = {
    hasChanged.add(issue.path)
    log.put(issue.path, issue :: log(issue.path))
    if (issue.diagnostic.category == MISSING_CATEGORY)
      possibleMissing.add(issue.path)
  }

  def pop(path: PathLike): List[diagnostics.Issue] = {
    hasChanged.add(path)
    val issues = log.getOrElse(path, Nil)
    log.remove(path)
    issues
  }

  def push(path: PathLike, issues: List[diagnostics.Issue]): Unit = {
    hasChanged.add(path)
    if (issues.nonEmpty)
      log.put(path, issues)
  }

  override def hasUpdatedIssues: Array[String] = {
    hasChanged.map(_.toString).toArray
  }

  override def ignoreUpdatedIssues(path: String): Unit = {
    ignoreUpdatedIssuesInternal(Path(path))
  }

  def ignoreUpdatedIssuesInternal(path: PathLike): Unit = {
    hasChanged.remove(path)
  }

  override def issuesForFile(path: String): Array[APIIssue] = {
    issuesForFileInternal(Path(path)).toArray
  }

  def issuesForFileInternal(path: PathLike): Seq[Issue] = {
    hasChanged.remove(path)
    log.getOrElse(path, Nil).sorted(Issue.ordering)
  }

  override def issuesForFileLocation(path: String, location: IssueLocation): Array[APIIssue] = {
    issuesForFileLocationInternal(Path(path), location)
  }

  def issuesForFileLocationInternal(path: PathLike, location: IssueLocation): Array[APIIssue] = {
    val loc = Location(
      location.startLineNumber(),
      location.startCharOffset(),
      location.endLineNumber(),
      location.endCharOffset()
    )
    log
      .getOrElse(path, Nil)
      .filter(issue => loc.contains(issue.diagnostic.location))
      .toArray[APIIssue]
  }

  override def issuesForFiles(
    paths: Array[String],
    includeWarnings: Boolean,
    maxIssuesPerFile: Int
  ): Array[APIIssue] = {
    val internalPaths: Array[PathLike] =
      Option(paths).map(paths => paths.map(Path.apply(_).asInstanceOf[PathLike])).orNull
    issuesForFilesInternal(internalPaths, includeWarnings, maxIssuesPerFile).toArray
  }

  def issuesForFilesInternal(
    paths: Array[PathLike],
    includeWarnings: Boolean,
    maxIssuesPerFile: Int
  ): Seq[Issue] = {
    val files =
      if (paths == null || paths.isEmpty)
        log.keys.toSeq.sortBy(_.toString)
      else
        paths.toIterable

    val buffer = mutable.ArrayBuffer[Issue]()
    files.foreach(file => {
      var fileIssues = log
        .getOrElse(file, Nil)
        .filter(
          issue => includeWarnings || DiagnosticCategory.isErrorType(issue.diagnostic.category)
        )
        .sorted(Issue.ordering)
      if (maxIssuesPerFile > 0)
        fileIssues = fileIssues.take(maxIssuesPerFile)
      buffer.addAll(fileIssues)
      hasChanged.remove(file)
    })
    buffer.toSeq
  }

  def getDiagnostics(path: PathLike): List[Diagnostic] =
    log.getOrElse(path, Nil).map(_.diagnostic)

  def getMissing: Seq[PathLike] = {
    val missing = new mutable.ArrayBuffer[PathLike]()
    possibleMissing.foreach(possible => {
      val issues = log.getOrElse(possible, Nil).filter(_.diagnostic.category == MISSING_CATEGORY)
      if (issues.nonEmpty) {
        missing.append(possible)
      }
    })
    possibleMissing.clear()
    missing.foreach(possibleMissing.add)
    missing.toSeq
  }
}
