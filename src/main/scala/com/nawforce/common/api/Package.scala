/*
 [The "BSD licence"]
 Copyright (c) 2019 Kevin Jones
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

import com.nawforce.common.documents._
import com.nawforce.common.finding.TypeRequest
import com.nawforce.common.names.TypeName
import com.nawforce.common.path.PathFactory
import com.nawforce.common.sfdx.Workspace
import com.nawforce.common.types._
import com.nawforce.common.types.apex.{ApexDeclaration, FullDeclaration}
import com.nawforce.common.types.pkg.PackageDeclaration
import com.nawforce.runtime.types.PlatformTypeException

class Package(val org: Org, workspace: Workspace, _basePackages: Seq[Package])
  extends PackageDeclaration(workspace, _basePackages) {

  /** Get a typename (as a String) from the path of a metadata file, returns an empty string if the path does not
    * identify metadata that creates a type within the current package. Currently restricted to only supporting
    * Apex class files. */
  def getTypeOfPath(path: String): String = {
    DocumentType(PathFactory(path)) match {
      case Some(ad: ApexDocument) if isMetadata(ad) =>
        TypeName(ad.name).withNamespace(namespace).toString
      case _ => ""
    }
  }

  /** Get the path (as a String) of the metadata file that defined a type, returns an empty string if the type
    * is not defined within the current package. Currently restricted to only supporting Apex class files. */
  def getPathOfType(typeName: String): String = {
    try {
      types.get(TypeName.fromString(typeName))
        .map {
          case ad: ApexDeclaration => ad.idLocation.path.toString
          case _ => ""
        }
        .getOrElse("")
    } catch {
      case ex: PlatformTypeException =>
        ServerOps.debug(ServerOps.Trace, ex.getMessage); ""
    }
  }

  /** Returns set of names of Apex defined types that depend on the passed Apex type name, if the passed type is
    * invalid or does not identify an Apex type returns an empty array. */
  def getDependencyHolders(typeName: String): Array[String] = {
    try {
      types.get(TypeName.fromString(typeName))
        .map {
          case ad: ApexDeclaration => ad.getTypeDependencyHolders
          case _ => Array[String]()
        }
        .getOrElse(Array[String]())
    } catch {
      case ex: PlatformTypeException =>
        ServerOps.debug(ServerOps.Trace, ex.getMessage); Array[String]()
    }
  }

  /* Deploy some classes to the org, if already present this will replace the existing classes */
  private[nawforce] def deployClasses(documents: Seq[ApexDocument]): Unit = {
    Org.current.withValue(org) {
      val updated = documents.flatMap(doc => {
        val data = doc.path.read()
        val d = ServerOps.debugTime(s"Parsed ${doc.path}") {
          FullDeclaration.create(this, doc.path, data.right.get).toSeq
        }
        d.foreach(upsertMetadata(_))
        d
      })

      updated.foreach(td => td.validate())
    }
  }

  /* Find package accessible type(s) */
  private[nawforce] def findTypes(typeNames: Seq[TypeName]): Seq[TypeDeclaration] = {
    Org.current.withValue(org) {
      typeNames.flatMap(typeName => TypeRequest(typeName, this, excludeSObjects = false).toOption)
    }
  }
}
