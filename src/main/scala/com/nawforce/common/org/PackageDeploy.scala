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

import java.util.concurrent.ConcurrentHashMap

import com.nawforce.common.api.{MISSING_CATEGORY, Name, ServerOps, TypeName}
import com.nawforce.common.diagnostics.{Issue, LocalLogger}
import com.nawforce.common.documents._
import com.nawforce.common.names._
import com.nawforce.common.org.stream.PackageStream
import com.nawforce.common.types.apex.{ApexClassDeclaration, FullDeclaration, SummaryApex, TriggerDeclaration}
import com.nawforce.common.types.schema.SObjectDeclaration
import com.nawforce.runtime.parsers.CodeParser
import com.nawforce.runtime.platform.Environment

import scala.collection.mutable
import scala.collection.parallel.CollectionConverters._
import scala.jdk.CollectionConverters._

trait PackageDeploy {
  this: PackageImpl =>

  private val epoch = System.currentTimeMillis()

  def deployFromWorkspace(): Unit = {
    val startingTypes = types.size
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
    loadClasses(documents)
    loadTriggers(documents)

    CodeParser.clearCaches()
    Environment.gc()

    if (types.size > startingTypes) {
      val total = System.currentTimeMillis() - epoch
      val avg = total / types.size
      ServerOps.debug(ServerOps.Trace,
                      s"Package(${namespace.map(_.value).getOrElse("")}) loaded ${types.size}" +
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

  private def loadClasses(documents: DocumentIndex): Unit = {
    val pcOpt = org.parsedCache
    val docs =
      documents.getByExtensionIterable(Name("cls")).collect { case ad: ApexClassDocument => ad }

    ServerOps.debugTime(s"Loaded summary classes", docs.nonEmpty) {

      // Load summary docs that have valid dependents
      if (pcOpt.nonEmpty) {
        val validSummaryDocs = new mutable.HashMap[TypeName, SummaryApex]()
        docs.grouped(500).foreach(grp => loadClassesFromCache(grp.toArray, pcOpt.get, validSummaryDocs))

        // Multi-pass removal of those with invalid dependencies to counter resolving cyclic links
        var invalidDocs = validSummaryDocs.filterNot(_._2.declaration.hasValidDependencies)
        while (invalidDocs.nonEmpty) {
          invalidDocs.foreach(pair => {
            validSummaryDocs.remove(pair._1)
            removeMetadata(pair._2.declaration)
          })
          invalidDocs = validSummaryDocs.filterNot(_._2.declaration.hasValidDependencies)
        }

        // Validate for dependency propagation purposes
        validSummaryDocs.foreach(_._2.declaration.validate())

        // Report diagnostics for those that get this far
        validSummaryDocs.foreach(pair => {
          val summary: SummaryApex = pair._2
          val path = summary.declaration.path.toString
          summary.diagnostics.foreach(diagnostic => org.issues.add(Issue(path, diagnostic)))
        })

      }
    }

    val missingTypes =
      docs.filterNot(doc => types.contains(TypeName(doc.name).withNamespace(namespace)))
    ServerOps.debugTime(s"Parsed ${missingTypes.size} classes", docs.nonEmpty) {

      // Load full docs for rest of set
      val fullTypes = missingTypes
        .flatMap(doc => {
          val data = doc.path.readSourceData()
          val tdOpt = ServerOps.debugTime(s"Parsed ${doc.path}") {
            FullDeclaration.create(this, doc, data.getOrElse(throw new NoSuchElementException))
          }
          tdOpt.map(td => {
            upsertMetadata(td)
            (td, data.getOrElse(throw new NoSuchElementException))
          })
        }).toSeq

      // Validate the full types
      fullTypes.foreach(_._1.validate())
    }
  }

  private def loadClassesFromCache(docs: Array[MetadataDocument],
                                   pc: ParsedCache,
                                   accum: mutable.Map[TypeName, SummaryApex]): Unit = {
    val pkgContext = packageContext
    val localAccum = new ConcurrentHashMap[TypeName, SummaryApex]()
    docs.par.foreach(doc => {
      val data = doc.path.readBytes()
      val value = pc.get(data.getOrElse(throw new NoSuchElementException), pkgContext)
      val ad = value.map(v => SummaryApex(doc.path, this, v))
      if (ad.nonEmpty && !ad.get.diagnostics.exists(_.category == MISSING_CATEGORY)) {
        localAccum.put(ad.get.declaration.typeName, ad.get)
      }
    })

    localAccum.entrySet().asScala.foreach(kv => {
      accum.put(kv.getKey, kv.getValue)
      upsertMetadata(kv.getValue.declaration)
    })

  }

  private def loadTriggers(documents: DocumentIndex): Unit = {
    val docs = documents.getByExtension(Name("trigger"))
    ServerOps.debugTime(s"Parsed ${docs.size} triggers", docs.nonEmpty) {
      val tds = docs.flatMap {
        case docType: ApexTriggerDocument =>
          val data = docType.path.readSourceData()
          TriggerDeclaration.create(this,
                                    docType.path,
                                    data.getOrElse(throw new NoSuchElementException))
        case _ => assert(false); Seq()
      }
      tds.foreach(upsertMetadata(_))
      tds.foreach(_.validate())
    }
  }

  /** Check all summary types have propagated their dependencies */
  def propagateAllDependencies(): Unit = {
    types.values.foreach({
      case ad: ApexClassDeclaration => ad.propagateAllDependencies()
      case _                        => ()
    })
  }
}
