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
import com.nawforce.common.metadata.MetadataDeclaration
import com.nawforce.common.names.Name
import com.nawforce.common.sfdx.Workspace
import com.nawforce.common.types._

class Package(val org: Org, workspace: Workspace, _basePackages: Seq[Package])
  extends PackageDeclaration(workspace, _basePackages) {

  // Automatic deploy of workspace contents
  deployWorspace()

  def deployWorspace(): Unit = {
    Package.metadataTypes.foreach(kv => {
      documentsByExtension(kv._1).foreach(loadFromDocument)
    })

    // TODO: This needs removing for invalidation handling
    schema().relatedLists.validate()
    validateMetadata()
  }

  /** Deploy some metadata to the org, if already present this will replace the existing metadata
    * TODO: This is really only safe for test use currently
    */
  def deployMetadata(documents: Seq[MetadataDocumentType]): Unit = {
    Org.current.withValue(org) {
      documents.foreach(d => loadFromDocument(d))
      validateMetadata()
    }
  }

  private def loadFromDocument(doc: MetadataDocumentType): Seq[MetadataDeclaration] = {
    Org.current.withValue(org) {
      val start = System.currentTimeMillis()

      val tds = doc match {
        case docType: ApexDocument =>
          val data = docType.path.read()
          ApexTypeDeclaration.create(this, docType.path, data.right.get)
        case docType: ApexTriggerDocument =>
          val data = docType.path.read()
          ApexTriggerDeclaration.create(this, docType.path, data.right.get)
        case docType: SObjectDocument =>
          SObjectDeclaration.create(this, docType.path)
        case docType: PlatformEventDocument =>
          SObjectDeclaration.create(this, docType.path)
        case docType: CustomMetadataDocument =>
          SObjectDeclaration.create(this, docType.path)
        case docType: ComponentDocument =>
          upsertComponent(namespace, docType)
          Nil
        case _ => Nil
      }

      val end = System.currentTimeMillis()
      ServerOps.debug(ServerOps.Trace, s"Parsed ${doc.path} in ${end - start}ms")
      tds.foreach(upsertMetadata(_))
      tds
    }
  }

  private def validateMetadata(): Unit = {
    Org.current.withValue(org) {
      types.values.filter(_.isInstanceOf[ApexTypeDeclaration]).foreach(td => {
        td.validate()
      })
      other.values.foreach(md => {
        md.validate()
      })
    }
  }
}

object Package {
  /** File type to plural name mapping */
  val metadataTypes: Map[Name, String] = Map(
    Name("object") -> "custom objects",
    Name("component") -> "components",
    Name("cls") -> "classes",
    Name("trigger") -> "triggers"
  )
}
