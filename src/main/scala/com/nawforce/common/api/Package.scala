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

import com.nawforce.common.names.TypeLike

trait Package {
  /** The namespace of the package, maybe empty for the unmanaged package, otherwise is unique within Org */
  def getNamespace: String

  /** Get a typename (as a String) from the path of a metadata file, returns a null if the path does not
    * identify metadata that creates a type within the current package. Currently restricted to only supporting
    * Apex class & trigger files. */
  def getTypeOfPath(path: String): TypeLike

  /** Get the path (as a String) of the metadata file that defined a type, returns a null if the type
    * is not defined within the current package. Currently restricted to only supporting Apex class files. */
  def getPathOfType(typeLike: TypeLike): String

  /** Get the summary information for a type, returns a null if the type is not defined within the current package.
    * Currently restricted to only supporting Apex class files. */
  def getSummaryOfType(typeLike: TypeLike): TypeSummary

  /** Returns set of Apex defined types that are depended on by the passed Apex type, if the passed type is
    * invalid or does not identify an Apex type returns a null. If inheritanceOnly is true only
    * superClass & inheritance dependencies are reported, otherwise all dependencies are included. */
  def getDependencies(typeLike: TypeLike, inheritanceOnly: Boolean): Array[TypeLike]

  /** Returns set of Apex defined types that depend on the passed Apex type, if the passed type is invalid or does
    * not identify an Apex type returns a null. The returned array may be stale in that it can contain
    * types which used to hold a dependency but not longer do.*/
  def getDependencyHolders(typeLike: TypeLike): Array[TypeLike]

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
  def deleteType(typeLike: TypeLike): Boolean
}
