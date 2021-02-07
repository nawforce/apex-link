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

import com.nawforce.common.diagnostics.Issue
import com.nawforce.common.org.OrgImpl

/** A virtual Org used to present the analysis functionality in a familiar way.
  *
  * All analysis works within the context of a virtual Org. You can manage multiple of these at the same time
  * but most use cases just need one creating, see Org.newOrg(). The Org functions as a container of
  * multiple [[Package]] objects and maintains a set of discovered issues from the analysis of the package
  * metadata. All orgs have at least one 'unmanaged' package identifiable by having no namespace.
  *
  * In the simple case after creating an Org, you should add one or more packages detailing where package
  * metadata is stored. Adding large packages can take considerable CPU and memory resources. Once the packages
  * are loaded the metadata within them can be mutated using the [[Package]] methods. At any point you can list
  * of current issues with the packages from getIssues.
  *
  * When metadata changes are requested (see [[Package.refresh]] they are queued for later processing either
  * via calling [[Org.flush]] or via automatic flushing (the default). Flushing also updates a disk cache
  * that helps significantly reduce initial loading times. The flushing model used by an [[Org]] is set on
  * construction, see [[ServerOps.setAutoFlush]] to change to manual flushing.
  *
  * Orgs and Packages are not thread safe, serialise all calls to them.
  */
trait Org {

  /** Get array of current packages. */
  def getPackages: Array[Package]

  /** Create a new package in the org from MDAPI format metadata.
    *
    * To use this package to replace the 'unmanaged' package modeled in all Orgs pass a null or empty namespace. The
    * directories are scanned in the order provided which influences how duplicate metadata is reported. The
    * basePackages array should contain any packages that this package depends on. References to metadata not
    * included in basePackage is not possible.
    **/
  def newMDAPIPackage(namespace: String,
                      directories: Array[String],
                      basePackages: Array[Package]): Package

  /** Create a new package in the org from SFDX format metadata.
    *
    * The metadata directory must contain a well formed sfdx-project.json file. The package namespace & package
    * directories to use are read from that sfdx-project.json file. If a .forceignore file is present in the
    * metadata directory it will be honoured. Any dependent packages that are also required can be specified
    * in the "plugins" section of sfdx-project.json under in a "dependencies" array.
    **/
  def newSFDXPackage(directory: String): Package

  /** Force syncing of org metadata to the cache when not using automatic flushing (see ServerOps).
    *
    * When using manual flushing this should be called periodically to ensure the cache is kept upto date after
    * metadata changes. If it returns true then getIssues should be called to retrieved the latest set of issues.
    */
  def flush(): Boolean

  /** Test if all metadata changes have been processed.
    *
    * The Package refresh function queues changes so that they may be processed in batches either when you call
    * flush() or via the automatic flushing mechanism. You can use this function to determine if the queue of changes
    * to be processed is empty.
    */
  def isFlushed(): Boolean

  /** Get current issue log.
    *
    * See IssueOptions for control over what is returned.
    */
  def getIssues(options: IssueOptions): String

  /** Get file specific issues.
    *
    * See file issues.
    */
  def getFileIssues(fileName: String, options: FileIssueOptions): Array[Issue]

  /** Get Apex type dependency map for all types in the Org.
    *
    * This is intended to be only used to support exporting of the map for secondary analysis.
    */
  def getDependencies: java.util.Map[String, Array[String]]

  /** Find the location of some form of identifier.
    *
    * Currently this supports locating Outer & Inner classes and Triggers by name. These must include a namespace
    * using the usual conventions to find a location for the identifier. Returns the file & position within that
    * file if the identifier is found, otherwise returns null.
    */
  def getIdentifierLocation(identifier: String): PathLocation

}

object Org {

  /** Create a new empty Org to which you can add packages for code analysis.
    *
    * You can use an Org without code analysis enabled but many of the API methods will not function. This mode is
    * supported to allow the available metadata to be examined specifically via getIdentifierLocation().
    */
  def newOrg(analysis: Boolean = true): Org = {
    new OrgImpl(analysis)
  }
}

/** Options available when retrieving File specific issues. */
class FileIssueOptions {

  /** Include warning messages, these may be ignored when checking for correctness. */
  var includeWarnings: Boolean = true

  /** Include warnings for dead fields, properties & methods, these may be ignored when checking for correctness. */
  var includeZombies: Boolean = false

  /** Maximum errors to include for each file, errors are ordered so this is the first 'n' errors. */
  var maxErrorsPerFile: Integer = 10
}

/** Options available when retrieving Org issues. */
class IssueOptions extends FileIssueOptions {

  /** Override output default text format for issues, valid options are "json" & "pickle" */
  var format: String = ""
}
