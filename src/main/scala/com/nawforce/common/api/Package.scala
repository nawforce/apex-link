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

import com.nawforce.runtime.SourceData

/** A virtual Package constructed from metadata.
  *
  * Packages must be created in the context of a specific [[Org]]. Each Package manages a set of Types which are
  * created from the metadata of the package. Once constructed you can use the package APIs to introspect dependency
  * relationships between Types, obtain summary and view information about Types and replace or delete Types.
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
  * The package upsert & deletion model does not support invalidation handling that is typically used to cause
  * dependency holders to be re-analysed when a dependency changes. This again can be handled via recursive iteration
  * of the methods provided.
  *
  * Summary and View information provide two different levels of looking at the structure of Types. In summary
  * form only the most important details are provided but it is essentially free to access since it is the same
  * format used in the disk cache. View information requires both more CPU and memory to use but can provide fuller
  * details on the implementations of Apex classes.
  *
  * Packages only handle metadata that is important to Apex class analysis, other forms of metadata that might
  * appear in the package directory are ignored.
  *
  **/
trait Package {
  /** The namespace of the package.
    *
    * Maybe empty for the unmanaged package, otherwise will be unique within an Org
    */
  def getNamespace: String

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

  /** Returns set of Types that are depended on by the passed Type
    *
    * If inheritanceOnly is true only superClass & inheritance dependencies are reported for Apex defined Types,
    * otherwise all dependencies are included.
    */
  def getDependencies(typeId: TypeIdentifier, inheritanceOnly: Boolean): Array[TypeIdentifier]

  /** Returns set of Types that depend on the passed Type.
    *
    * The returned array may be stale in that it can contain Types which used to hold a dependency but not longer do.
    */
  def getDependencyHolders(typeId: TypeIdentifier): Array[TypeIdentifier]

  /** Obtain view information for a Type.
    *
    * You can either pass in a path and contents or a path and null contents. If contents are not provided they will
    * be read from the path if possible. Where contents are provided the path is only used for error identification
    * purposes. The view information contains a detailed description of the Type that can either be inspected and/or
    * later used to modify the Type in the package, see [[upsertFromView]]. In some cases it may not be possible
    * to construct a Type at all, in which case the view information may only contain diagnostic information.
    *
    * If the path does not identify a supported metadata or that path is not valid for this package no Type will be
    * returned and the view diagnostics will indicate the error.
    *
    * To prepare a view for deletion pass the path of the file that has already been deleted without contents. The
    * returned view may contain a Type constructed from other metadata files still in use in the project. In either
    * case using this view with [[upsertFromView]] will effect the deletion.
    */
  def getViewOfType(path: String, contents: SourceData): ViewInfo

  /** Upsert a Type described by a ViewInfo.
    *
    * This uses the supplied viewInfo to either replace or remove metadata from the package. If the viewInfo was
    * created with unsupported metadata or metadata that might create a duplicate Type then this return false.
    * Otherwise the view Type is replaced or removed as needed to update the package.
    *
    * NOTE: Upserting does not impact other Types in this or other packages. To update diagnostics for other Types
    * to take into account any change made in the Type upserted you should upsert them as well. When a Type is
    * upserted it inherits the dependency holders of the previous version which can be used to identify which other
    * Types need upserting.
    */
  def upsertFromView(viewInfo: ViewInfo): Boolean
}
