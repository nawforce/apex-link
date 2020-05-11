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

/** A virtual Package used to constructed around some metadata
  *
  * Packages must be created in the context of a specific [[Org]]. Once constructed you can use the package APIs to
  * introspect dependency relationships, obtain summary and view information about types and replace or delete types.
  *
  * The term dependency can be rather ambiguous, we use it here to mean a 'using' relationship. So if class A calls a
  * method on class B, we say A has a dependency that is B. The reverse relationship is a 'dependency holder', so B has
  * a dependency holder that is A. Transitive dependencies or dependency holder relationships are not exposed by these
  * APIs but can be obtained by recursive iteration.
  *
  * The package upsert & deletion model also do not support invalidation handling that is typically used to cause
  * dependency holders to be re-analysed when a dependency changes. This again can be handled via recursive iteration
  * of the method provided.
  *
  * Summary and View are information provide two different levels of looking at the structure of types. In summary
  * form only the most important details are provided but it is essentially free to access since it is the same
  * format used in the disk cache. View information requires both more CPU and memory to use but can provide full
  * details on the implementations of Apex classes.
  *
  * Packages only handle metadata types that are important to Apex class analysis, other types of metadata that might
  * appear in the package directory are ignored.
  **/
trait Package {
  /** The namespace of the package, maybe empty for the unmanaged package, otherwise is unique within Org */
  def getNamespace: String

  /** Get a typename (as a String) from the path of a metadata file, returns a null if the path does not
    * identify metadata that creates a type within the current package. */
  def getTypeOfPath(path: String): TypeIdentifier

  /** Get the path(s) of the metadata file that defined a type, returns an empty array if the type
    * is not defined within the current package. Currently restricted to only supporting Apex class files. */
  def getPathsOfType(typeId: TypeIdentifier): Array[String]

  /** Get the summary information for a type, returns a null if the type is not defined within the current package.
    * Currently restricted to only supporting Apex class files. */
  def getSummaryOfType(typeId: TypeIdentifier): TypeSummary

  /** Returns set of Apex defined types that are depended on by the passed Apex type, if the passed type is
    * invalid or does not identify an Apex type returns a null. If inheritanceOnly is true only
    * superClass & inheritance dependencies are reported, otherwise all dependencies are included. */
  def getDependencies(typeId: TypeIdentifier, inheritanceOnly: Boolean): Array[TypeIdentifier]

  /** Returns set of Apex defined types that depend on the passed Apex type, if the passed type is invalid or does
    * not identify an Apex type returns a null. The returned array may be stale in that it can contain
    * types which used to hold a dependency but not longer do.*/
  def getDependencyHolders(typeId: TypeIdentifier): Array[TypeIdentifier]

  /** Return view information for a type. You can either pass in a path and contents or a path and null contents. If
    * contents are not provided they will be read from the path if possible. Where contents are provided the path is
    * only used for error identification purposes. The view information contains a detailed description of the type
    * that can either be inspected and/or used to modify the type in the package. In some cases it may not be possible
    * to construct a type at all, in which case the view information will only contain diagnostic information.
    */
  def getViewOfType(path: String, contents: String): ViewInfo

  /** Upsert a type described by a ViewInfo. If the ViewInfo does not contain a type or the identifying path is
    * different from an existing type of the same name then this is a noop that returns false. Otherwise if the type
    * already exists it will be replaced or a new type will be created in the package. NOTE: Types that might be
    * holding a 'missing dependency' error for the upsert'd type are not automatically re-validated during the upsert.
    */
  def upsertFromView(viewInfo: ViewInfo): Boolean

  /** Remove a type from the package. The removes the visibility of the type within the package such that no newly
    * upsert'd type may reference it. To fully remove all existing types that use the removed type must be upsert'd.
    */
  def deleteType(typeId: TypeIdentifier): Boolean
}
