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

import com.nawforce.pkgforce.names.{TypeIdentifier, TypeName}

/** A virtual Package constructed from metadata.
  *
  * Packages must be created in the context of a specific [[Org]]. Each Package manages a set of Types which are
  * created from the metadata of the package. Once constructed you can use the package APIs to introspect dependency
  * relationships between Types, obtain summary information about Types and replace or delete Types.
  *
  * A key concept to understand in this API is that a Type may be created from multiple metadata files, e.g. the
  * System.Labels Type is always present but can be constructed from several labels files. The API methods expose
  * Types via the TypeIdentifiers which you don't need to understand to use the API as long as you keep in mind
  * the 1:M mapping between Types and Paths.
  *
  * The term dependency can be rather ambiguous, we use it here to mean a 'using' relationship. So if class A calls a
  * method on class B, we say A has a dependency that is B. The reverse relationship is a 'dependency holder', so B has
  * a dependency holder that is A. Transitives of dependencies or dependency holder relationships are not exposed by
  * these APIs but can be easily obtained by recursive iteration.
  *
  * A Summary provides a way of looking at the structure of Types. In summary form only the most important details
  * are provided but it is essentially free to access since it is the same format used in the disk cache.
  *
  * Packages only handle metadata that is important to Apex class analysis, other forms of metadata that might
  * appear in the package directory are ignored.
  *
  **/
trait Package {

  /** The namespace of the package or the package and its dependent packages.
    *
    * An empty namespace indicates the "unmanaged" package.
    */
  def getNamespaces(withDependents: Boolean): Array[String]

  /** Get a list of type identifiers available in from this package. This is not all available type identifiers, but
    * just those that will make most sense to list in an IDE for selection.
    *
    * Returns an array which may be empty.
    */
  def getTypeIdentifiers(apexOnly: Boolean): Array[TypeIdentifier]

  /** Get a TypeIdentifier for a TypeName resolved against this package.
    *
    * Returns null if the TypeName is not visible in this package.
    */
  def getTypeIdentifier(typeName: TypeName): TypeIdentifier

  /** Test if a metadata file path is part of this package.
    *
    * Return null if this either not a recognised metadata file type or it is not part of this package.
    */
  def isPackagePath(path: String): Boolean

  /** Get a Type from the path of a metadata file.
    *
    * Returns a null if the path does not identify metadata that creates a Type within the current package.
    */
  def getTypeOfPath(path: String): TypeIdentifier

  /** Get the path(s) of the metadata file that defined a Type.
    *
    * Returns an empty array if the Type is not defined within the current package.
    */
  def getPathsOfType(typeId: TypeIdentifier): Array[String]

  /** Get the summary information for a Type.
    *
    * Returns a null if the Type is not defined within the current package.
    */
  def getSummaryOfType(typeId: TypeIdentifier): TypeSummary

  /** JSON encoding of summary information for a type.
    *
    * Returns a null if the Type is not defined within the current package.
    */
  def getSummaryOfTypeAsJSON(typeId: TypeIdentifier): String

  /** Returns set of Types that are depended on by the passed Type
    *
    * If outerInheritanceOnly is true only extending and implementing dependencies are reported for the outer Type
    * of Apex defined types. If apexOnly is true then only Apex defined types are returned.
    */
  def getDependencies(typeId: TypeIdentifier, outerInheritanceOnly: Boolean, apexOnly: Boolean): Array[TypeIdentifier]

  /** Returns set of Types that depend on the passed Type.
    *
    * The returned array may be stale in that it can contain Types which used to hold a dependency but not longer do.
    * If apexOnly is true then only Apex defined types are returned.
    */
  def getDependencyHolders(typeId: TypeIdentifier, apexOnly: Boolean): Array[TypeIdentifier]

  /** Refresh a type in the package.
    *
    * This registers that the metadata in a file may need updating. The refresh is queued until the Org metadata is
    * next flushed so that changes are made in batches for efficiency. Refreshing causes dependent metadata to be
    * re-validated so issues may be reported against other metadata types after the flush has completed.
    *
    * If there is no file at the given path then any previous contribution to the package metadata from this file
    * will be removed so you call this after file deletion.
    */
  def refresh(path: String): Unit
}
