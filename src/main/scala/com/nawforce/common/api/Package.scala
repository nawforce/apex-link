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

trait Package {
  /** The namespace of the package, maybe empty for the unmanaged package, otherwise is unique is Org */
  def getNamespace: String

  /** Get a typename (as a String) from the path of a metadata file, returns a null if the path does not
    * identify metadata that creates a type within the current package. Currently restricted to only supporting
    * Apex class files. */
  def getTypeOfPath(path: String): TypeLike

  /** Get the path (as a String) of the metadata file that defined a type, returns a null if the type
    * is not defined within the current package. Currently restricted to only supporting Apex class files. */
  def getPathOfType(typeLike: TypeLike): String

  /** Returns set of Apex defined types that are depended on by the passed Apex type, if the passed type is
    * invalid or does not identify an Apex type returns a null. If inheritanceOnly is true only
    * superClass & inheritance dependencies are reported, otherwise all dependencies are included. */
  def getDependencies(typeLike: TypeLike, inheritanceOnly: Boolean): Array[TypeLike]

  /** Returns set of Apex defined types that depend on the passed Apex type, if the passed type is invalid or does
    * not identify an Apex type returns a null. The returned array may be stale in that it can contain
    * types which used to hold a dependency but not longer do.*/
  def getDependencyHolders(typeLike: TypeLike): Array[TypeLike]
}
