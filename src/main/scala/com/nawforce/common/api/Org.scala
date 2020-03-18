/*
 [The "BSD licence"]
 Copyright (c) 2020 Kevin Jones
 All rights reserved.

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

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.nawforce.common.api

import com.nawforce.common.org.OrgImpl

trait Org {
  /** Get array of current packages. All orgs have at least one 'unmanaged' package. */
  def getPackages(): Array[Package]

  /** Get current issue log. See IssueOptions for control over what is returned. */
  def getIssues(options: IssueOptions): String

  /** Create a new package in the org, directories should be priority ordered for duplicate detection. Use
    * namespaces to indicate dependent packages which must already have been created as packages. */
  def newPackage(namespace: String, directories: Array[String], basePackages: Array[Package]): Package

  /** Force syncing of org metadata with cache. THis should be called periodically to ensure the cache
    * is kept upto date after metadata changes. */
  def flush(): Unit
}

class IssueOptions {
  /** Include warning messages, these may be safely ignored when checking for correctness. */
  var includeWarnings: Boolean = true

  /** Include warnings for dead fields, properties & methods, these may be safely ignored when checking
    * for correctness. */
  var includeZombies: Boolean = false

  /** Format output as JSON if true, the default is a text format suitable for display */
  var formatJSON: Boolean = false

  /** Maximum errors to include for each file, errors are ordered so this is the first 'n' errors. */
  var maxErrorsPerFile: Integer = 10
}

object Org {
  def newOrg(): Org = {
    new OrgImpl
  }
}
