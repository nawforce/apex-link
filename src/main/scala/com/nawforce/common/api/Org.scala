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
  * The disk cache that supports quicker loading is only updated by calling flush(). You should call this after
  * all packages have been loaded and periodically after that if mutating metadata to ensure the cache is kept
  * upto date.
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
  def newMDAPIPackage(namespace: String, directories: Array[String], basePackages: Array[Package]): Package

  /** Create a new package in the org from SFDX format metadata.
    *
    * The metadata directory must contain a well formed sfdx-project.json file. The package namespace & package
    * directories to use are read from that sfdx-project.json file. If a .forceignore file is present in the
    * metadata directory it will be honoured. The basePackages array should contain any packages that this package
    * depends on. References to metadata not included in basePackage is not possible.
    **/
  def newSFDXPackage(directory: String, basePackages: Array[Package]): Package

  /** Force syncing of org metadata with cache.
    *
    * This should be called periodically to ensure the cache is kept upto date after metadata changes.
    */
  def flush(): Unit

  /** Get current issue log.
    *
    * See IssueOptions for control over what is returned.
    */
  def getIssues(options: IssueOptions): String

  /** Get Apex type dependency map for all types in the Org.
    *
    * This is intended to be used to support exporting of the map for secondary analysis.
    */
  def getDependencies: java.util.Map[String, Array[String]]

  /** Find the location of some form of identifier.
    *
    * Currently this supports locating Outer & Inner classes and Triggers by name. These must include a namespace
    * using the usual conventions to find a location for the identifier. Returns the file & position* within that
    * file if the identifier is found, otherwise returns null.
    */
  def getIdentifierLocation(identifier: String): PathLocation
}

/** Options available when retrieving Org issues. */
class IssueOptions {
  /** Include warning messages, these may be ignored when checking for correctness. */
  var includeWarnings: Boolean = true

  /** Include warnings for dead fields, properties & methods, these may be ignored when checking for correctness. */
  var includeZombies: Boolean = false

  /** Override output default text format for issues, valid options are "json" & "pickle" */
  var format: String = ""

  /** Maximum errors to include for each file, errors are ordered so this is the first 'n' errors. */
  var maxErrorsPerFile: Integer = 10
}

object Org {
  /** Create a new empty Org to which you can add packages for code analysis.
    *
    * You can use an Org without code analysis enabled but many of the API methods will not function. This mode is
    * supported to allow the available metadata to be examined specifically via [[Org.getIdentifierLocation()]].
    */
  def newOrg(analysis: Boolean=true): Org = {
    new OrgImpl(analysis)
  }
}
