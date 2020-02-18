package com.nawforce.common.api

import com.nawforce.common.org.OrgImpl

trait Org {
  /** Create a new package in the org, directories should be priority ordered for duplicate detection. Use
    * namespaces to indicate dependent packages which must already have been created as packages. */
  def newPackage(namespace: String, directories: Array[String], basePackages: Array[Package]): Package

  /** Get current issue log. See IssueOptions for control over what is returned. */
  def getIssues(options: IssueOptions): String
}

class IssueOptions {
  /** Include warning messages, these may be safely ignored when checking for correctness. */
  var includeWarnings: Boolean = true

  /** Include warnings for dead fields, properties & methods, these may be safely ignored when checking
    * for correctness. */
  var includeZombies: Boolean = false

  /** Format output as JSON if true, the default is a text format suitable for display */
  var formatJSON: Boolean = false;

  /** Maximum errors to include for each file, errors are ordered so this is the first 'n' errors. */
  var maxErrorsPerFile: Integer = 10;
}

object Org {
  def newOrg(): Org = {
    new OrgImpl
  }
}
