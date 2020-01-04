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
package com.nawforce.common.cmds

import com.nawforce.common.api.{Org, ServerOps}
import com.nawforce.common.diagnostics.IssueLog
import com.nawforce.common.documents.MetadataDocumentType
import com.nawforce.common.sfdx.Workspace
import com.nawforce.common.types.{ApexTypeDeclaration, TypeDeclaration}

class Check(workspace: Workspace, zombies: Boolean) {
  private val org = new Org()
  private val pkg = org.addPackageInternal(workspace, Seq())
  private var schemaValidated = false
  private var queued: List[Seq[MetadataDocumentType]] = pkg.getDocuments
  private var tds: List[TypeDeclaration] = Nil

  def run(): Option[IssueLog] = {
    if (queued.nonEmpty) {
      val types = queued.head.flatMap(d => pkg.loadFromDocument(d))
      tds = tds ++ types
      queued = queued.tail
      if (!schemaValidated) {
        schemaValidated = true
        pkg.schemaValidate()
      }
      None
    } else if (tds.nonEmpty) {
      Org.current.withValue(org) {
        var i = 0
        while(i < 10 && tds.nonEmpty) {
          if (tds.head.isInstanceOf[ApexTypeDeclaration])
            tds.head.validate()
          i = i + 1
          tds = tds.tail
        }
      }
      None
    } else {
      if (zombies) {
        org.packages.values.foreach(pkg => {
          if (!pkg.isGhosted) {
            org.issues.merge(pkg.reportUnused())
          }
        })
      }
      ServerOps.debug(ServerOps.Trace,s"Returned Issues")
      Some(org.issues)
    }
  }
}
