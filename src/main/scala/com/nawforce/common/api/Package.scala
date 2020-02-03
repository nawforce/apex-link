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

import java.nio.charset.StandardCharsets

import com.nawforce.common.documents._
import com.nawforce.common.finding.TypeRequest
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.sfdx.Workspace
import com.nawforce.common.types._
import com.nawforce.common.types.apex.{FullDeclaration, SummaryDeclaration, TriggerDeclaration}
import com.nawforce.common.types.schema.SObjectDeclaration
import upickle.default._

class Package(val org: Org, workspace: Workspace, _basePackages: Seq[Package])
  extends PackageDeclaration(workspace, _basePackages) {

  // Automatic deploy of workspace contents
  deployWorkspace()

  def deployWorkspace(): Unit = {
    loadCustomObjects()
    loadComponents()
    loadClasses()
    loadTriggers()
  }

  private def loadCustomObjects(): Unit = {
    val docs = documentsByExtension(Name("object"))
    ServerOps.debugTime(s"Parsed ${docs.size} objects") {
      val tds = docs.flatMap {
        case docType: SObjectDocument =>
          SObjectDeclaration.create(this, docType.path)
        case docType: PlatformEventDocument =>
          SObjectDeclaration.create(this, docType.path)
        case docType: CustomMetadataDocument =>
          SObjectDeclaration.create(this, docType.path)
        case _ => assert(false); Seq()
      }
      tds.foreach(upsertMetadata(_))
      tds.foreach(_.validate())
      schema().relatedLists.validate()
    }
  }

  private def loadComponents(): Unit = {
    val docs = documentsByExtension(Name("component"))
    ServerOps.debugTime(s"Parsed ${docs.size} components") {
      docs.foreach {
        case docType: ComponentDocument => upsertComponent(namespace, docType)
        case _ => assert(false); Seq()
      }
    }
  }

  private def loadClasses(): Unit = {
    val pc = Package.parsedCache
    val docs = documentsByExtension(Name("cls"))

    ServerOps.debugTime(s"Parsed ${docs.size} classes") {

      // Load summary docs that have valid dependents
      if (ServerOps.isParsedDataCaching) {
        val summaryDocs = docs.flatMap(doc => {
          val data = doc.path.read()
          val value = pc.flatMap(_.get(data.right.get.getBytes()))
          value.map(v => new SummaryDeclaration(doc.path, this, None, v))
        }).map(sd => (sd.name, sd)).toMap

        val valid = summaryDocs.filter(kv => kv._2.areDependentsValid(summaryDocs))
        valid.foreach(vd => upsertMetadata(vd._2))
      }

      // Load full docs for rest of set
      val fullTypes = docs.filterNot(doc => types.contains(TypeName(doc.name))).map(doc => {
        val data = doc.path.read()
        val td = ServerOps.debugTime(s"Parsed ${doc.path}") {
          FullDeclaration.create(this, doc.path, data.right.get)
        }
        td.foreach(upsertMetadata(_))
        (data.right.get, td)
      })

      // Validate the full types & write back to cache
      fullTypes.filter(_._2.nonEmpty).foreach(ld => {
        ld._2.get.validate()
        if (ServerOps.isParsedDataCaching) {
          pc.get.upsert(ld._1.getBytes(StandardCharsets.UTF_8), writeBinary(ld._2.get.summary))
        }
      })
    }
  }

  private def loadTriggers(): Unit = {
    val docs = documentsByExtension(Name("trigger"))
    ServerOps.debugTime(s"Parsed ${docs.size} triggers") {
      val tds = docs.flatMap {
        case docType: ApexTriggerDocument =>
          val data = docType.path.read()
          TriggerDeclaration.create(this, docType.path, data.right.get)
        case _ => assert(false); Seq()
      }
      tds.foreach(upsertMetadata(_))
      tds.foreach(_.validate())
    }
  }

  /** Deploy some classes to the org, if already present this will replace the existing classes */
  def deployClasses(documents: Seq[ApexDocument]): Unit = {
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

object Package {
  lazy val parsedCache: Option[ParsedCache] = {
    ParsedCache.create() match {
      case Left(err) => ServerOps.error(err); None
      case Right(cache) => Some(cache)
    }
  }
}
