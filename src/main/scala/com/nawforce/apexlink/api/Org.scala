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

package com.nawforce.apexlink.api

import com.nawforce.apexlink.org.OrgImpl
import com.nawforce.apexlink.rpc.{DependencyGraph, LocationLink}
import com.nawforce.pkgforce.diagnostics.{Issue, LoggerOps, PathLocation}
import com.nawforce.pkgforce.names.TypeIdentifier
import com.nawforce.pkgforce.path.{PathFactory, PathLike}
import com.nawforce.pkgforce.workspace.Workspace

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
  def getPackages(): Array[Package]

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
  def isDirty(): Boolean

  /** Check issue log for errors, ignores warning messages. */
  def hasErrors(): Boolean

  /** Get current issue log as a possibly very large string.
    *
    * See IssueOptions for control over what is returned.
    */
  def getIssues(options: IssueOptions): String

  /** Get file specific issues.
    *
    * See file issues.
    */
  def getFileIssues(path: String, options: FileIssueOptions): Array[Issue]

  /** Get the package containing the path.
    *
    * Returns null if no package handling this file is found or the file is not a recognised metadata type.
    **/
  def getPackageForPath(path: String): Package

  /** Get a list of type identifiers available in the org across all packages. This is not all available type
    * identifiers, but just those that will make most sense to list in an IDE for selection.
    *
    * Returns an array which may be empty.
    */
  def getTypeIdentifiers(apexOnly: Boolean): Array[TypeIdentifier]

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
  def getIdentifierLocation(identifier: TypeIdentifier): PathLocation

  /** Get a dependency graph for a type identifier.
    *
    * Depth should be a positive integer that indicates how far to search from the starting node for the passed
    * TypeIdentifier. The root node of the search is always returned if it can be found. Depths > 0 will include
    * additional nodes. */
  def getDependencyGraph(identifier: TypeIdentifier, depth: Integer, apexOnly: Boolean): DependencyGraph

  /** Locate a type definition given source file position of the type name to search for.
    *
    * This will attempt to locate the type definition of a type name at the provided line & offset in the path. The
    * returned location provides information of the extent of the symbols used for the search as well as the file and
    * extent of the found definition. If no symbol can be found that links to a definition it returns an empty array.
    * If content is null, path will be used to load the source code. It is not necessary for the file being searched
    * from to be free of errors, but errors may impact the ability to locate inner classes within that file. */
  def getDefinition(path: String, line: Int, offset: Int, content: String): Array[LocationLink]
}

object Org {

  /** Create a new empty Org to which you can add packages for code analysis. */
  def newOrg(path: String): Org = {
    newOrg(PathFactory(path))
  }

  /** Create a new empty Org to which you can add packages for code analysis. */
  def newOrg(path: PathLike): Org = {
    LoggerOps.infoTime(s"Org created",
                       show = true,
                       s" with autoFlush = ${ServerOps.getAutoFlush}, build = ${OrgImpl.implementationBuild}") {
      val ws = Workspace(path)
      val org = new OrgImpl(ws.value)
      ws.issues.foreach(org.issues.add)
      org
    }
  }
}

/** Options available when retrieving File specific issues. */
class FileIssueOptions {

  /** Include warning messages, these may be ignored when checking for correctness. */
  var includeWarnings: Boolean = true

  /** Include warnings for dead fields, properties & methods, these may be ignored when checking for correctness. */
  var includeZombies: Boolean = false

  /** Maximum errors to include for each file, errors are ordered so this is the first 'n' errors. */
  val maxErrorsPerFile: Integer = 10
}

/** Options available when retrieving Org issues. */
class IssueOptions extends FileIssueOptions {

  /** Override output default text format for issues, only supported option is "json" */
  var format: String = ""
}
