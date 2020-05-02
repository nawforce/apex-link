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
package com.nawforce.common.org

import com.nawforce.common.api.ServerOps
import com.nawforce.common.diagnostics.{Issue, LocalLogger, MISSING_CATEGORY}
import com.nawforce.common.documents._
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.org.stream.PackageStream
import com.nawforce.common.path.PathLike
import com.nawforce.common.sfdx.Workspace
import com.nawforce.common.types.apex.{ApexClassDeclaration, FullDeclaration, SummaryApex, TriggerDeclaration}
import com.nawforce.common.types.schema.SObjectDeclaration

import scala.collection.mutable

trait PackageDeploy {
  this: PackageImpl =>

  private val epoch = System.currentTimeMillis()

  def deployFromWorkspace(workspace: Workspace): Unit = {
    workspace.paths.foreach(path => deployFromPath(workspace, path))
  }

  def deployFromPath(workspace: Workspace, path: PathLike): Unit = {
    val startingTypes = types.size
    val documents = new DocumentIndex(Seq(path), workspace.forceIgnore)
    val stream = PackageStream(new LocalLogger(org.issues), namespace, documents)

    labels = labels.merge(stream)
    upsertMetadata(labels)
    upsertMetadata(labels, Some(TypeName(labels.name)))

    pages = pages.merge(stream)
    upsertMetadata(pages)

    interviews = interviews.merge(stream)
    upsertMetadata(interviews)

    components = components.merge(stream)
    upsertMetadata(components)

    loadCustomObjects(documents)
    loadComponents(documents)
    loadClasses(documents)
    loadTriggers(documents)

    if (types.size > startingTypes) {
      val total = System.currentTimeMillis() - epoch
      val avg = total / types.size
      ServerOps.debug(ServerOps.Trace, s"Package(${namespace.map(_.value).getOrElse("")}) loaded ${types.size}" +
        s" types in ${total / 1000} seconds, average $avg ms/type")
    }
  }

  private def loadCustomObjects(documents: DocumentIndex): Unit = {
    val docs = documents.getByExtension(Name("object"))
    ServerOps.debugTime(s"Parsed ${docs.size} objects", docs.nonEmpty) {
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

  private def loadComponents(documents: DocumentIndex): Unit = {
    val docs = documents.getByExtension(Name("component"))
    ServerOps.debugTime(s"Parsed ${docs.size} components", docs.nonEmpty) {
      docs.foreach {
        case docType: ComponentDocument => upsertMetadata(docType)
        case _ => assert(false); Seq()
      }
    }
  }

  private def loadClasses(documents: DocumentIndex): Unit = {
    val pcOpt = getParsedCache
    val docs = documents.getByExtension(Name("cls"))
    ServerOps.debugTime(s"Loaded summary classes", docs.nonEmpty) {

      // Load summary docs that have valid dependents
      if (pcOpt.nonEmpty) {
        val summaryDocs = docs.flatMap(doc => {
          val data = doc.path.read()
          val value = pcOpt.flatMap(_.get(data.right.get.getBytes(), packageContext))
          value.map(v => new SummaryApex(doc.path, this, v))
        })

        // Upsert any summary docs that don't have missing diagnostics that might be resolvable
        val validSummaryDocs: mutable.Map[TypeName, SummaryApex] = mutable.Map(summaryDocs
          .filterNot(_.diagnostics.exists(_.category == MISSING_CATEGORY.value))
          .map(doc => (doc.declaration.typeName, doc)): _*)
        validSummaryDocs.foreach(pair => upsertMetadata(pair._2.declaration))

        // Multi-pass removal of those with invalid dependencies to counter resolving cyclic links
        var invalidDocs = validSummaryDocs.filterNot(_._2.declaration.hasValidDependencies)
        while (invalidDocs.nonEmpty) {
          invalidDocs.foreach(pair => {
            validSummaryDocs.remove(pair._1)
            removeMetadata(pair._2.declaration)
          })
          invalidDocs = validSummaryDocs.filterNot(_._2.declaration.hasValidDependencies)
        }

        // Report diagnostics for those that get this far
        validSummaryDocs.foreach(pair => {
          val summary: SummaryApex = pair._2
          val path = summary.declaration.path.toString
          summary.diagnostics.foreach(diagnostic => org.issues.add(Issue.fromDiagnostic(path, diagnostic)))
        })
      }
    }

    val missingTypes = docs.filterNot(doc => types.contains(TypeName(doc.name).withNamespace(namespace)))
    ServerOps.debugTime(s"Parsed ${missingTypes.size} classes", docs.nonEmpty) {

      // Load full docs for rest of set
      val fullTypes = missingTypes
        .flatMap(doc => {
          val data = doc.path.read()
          val tdOpt = ServerOps.debugTime(s"Parsed ${doc.path}") {
            FullDeclaration.create(this, doc.path, data.right.get)
          }
          tdOpt.map(td => {
            upsertMetadata(td)
            (td, data.right.get)
          })
        })

      // Validate the full types
      fullTypes.foreach(_._1.validate())
    }
  }

  private def getParsedCache: Option[ParsedCache] = {
    if (ServerOps.getParsedDataCaching)
      ParsedCache.create match {
        case Left(err) => ServerOps.error(err); None
        case Right(cache) => Some(cache)
      }
    else
      None
  }

  private def loadTriggers(documents: DocumentIndex): Unit = {
    val docs = documents.getByExtension(Name("trigger"))
    ServerOps.debugTime(s"Parsed ${docs.size} triggers", docs.nonEmpty) {
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

  /** Flush all types to the passed cache */
  def flush(pc: ParsedCache): Unit = {
    val context = packageContext
    types.values.foreach({
      case ad: ApexClassDeclaration => ad.flush(pc, context)
      case _ => ()
    })
  }

  /** Check all summary types have propagated their dependencies */
  def propagateAllDependencies(): Unit = {
    types.values.foreach({
      case ad: ApexClassDeclaration => ad.propagateAllDependencies()
      case _ => ()
    })
  }
}
