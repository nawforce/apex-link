/*
 Copyright (c) 2020 Kevin Jones, All rights reserved.
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
 */

package com.nawforce.apexlink.org

import java.util.concurrent.ConcurrentHashMap

import com.nawforce.apexlink.api.ServerOps
import com.nawforce.apexlink.names._
import com.nawforce.apexlink.types.apex.{FullDeclaration, SummaryApex, TriggerDeclaration}
import com.nawforce.apexlink.types.other.{ComponentDeclaration, InterviewDeclaration, LabelDeclaration, PageDeclaration}
import com.nawforce.apexlink.types.schema.SObjectDeclaration
import com.nawforce.pkgforce.diagnostics.{Issue, MISSING_CATEGORY}
import com.nawforce.pkgforce.documents._
import com.nawforce.pkgforce.names.TypeName
import com.nawforce.pkgforce.stream.PackageStream
import com.nawforce.runtime.parsers.CodeParser
import com.nawforce.runtime.platform.Environment

import scala.collection.mutable
import scala.collection.parallel.CollectionConverters._
import scala.jdk.CollectionConverters._

trait ModuleDeploy {
  this: Module =>

  private val epoch = System.currentTimeMillis()

  def deploy(): Unit = {
    val startingTypes = typeCount
    val stream = PackageStream(namespace, index)

    stream.issues.foreach(e => e.issues.foreach(OrgImpl.log))

    labels = LabelDeclaration(this)
    pages = PageDeclaration(this)
    interviews = InterviewDeclaration(this)
    components = ComponentDeclaration(this)

    upsertMetadata(anyDeclaration)
    upsertMetadata(schemaManager.sobjectTypes)
    upsertMetadata(schemaManager.sobjectTypes, Some(TypeName(schemaManager.sobjectTypes.name)))
    upsertMetadata(labels)
    upsertMetadata(labels, Some(TypeName(labels.name)))
    upsertMetadata(pages)
    upsertMetadata(interviews)
    upsertMetadata(components)

    // TODO: Convert to load via stream events
    labels = labels.merge(stream)
    upsertMetadata(labels)
    upsertMetadata(labels, Some(TypeName(labels.name)))

    pages = pages.merge(stream)
    upsertMetadata(pages)

    interviews = interviews.merge(stream)
    upsertMetadata(interviews)

    components = components.merge(stream)
    upsertMetadata(components)

    loadCustomObjects()
    loadClasses()
    loadTriggers()

    CodeParser.clearCaches()
    Environment.gc()

    if (typeCount > startingTypes) {
      val total = System.currentTimeMillis() - epoch
      val avg = total / typeCount
      ServerOps.debug(ServerOps.Trace,
                      s"Module($toString) loaded $typeCount" +
                        s" types in ${total / 1000} seconds, average $avg ms/type")
    }
  }

  private def loadCustomObjects(): Unit = {
    val docs = index.get(SObjectNature).toSeq
    ServerOps.debugTime(s"Parsed ${docs.size} objects", docs.nonEmpty) {
      val tds = docs.flatMap {
        case docType: SObjectDocument =>
          SObjectDeclaration.create(this, docType.path)
        case docType: PlatformEventDocument =>
          SObjectDeclaration.create(this, docType.path)
        case docType: CustomMetadataDocument =>
          SObjectDeclaration.create(this, docType.path)
        case docType: BigObjectDocument =>
          SObjectDeclaration.create(this, docType.path)
        case _ => assert(false); Seq()
      }
      tds.foreach(upsertMetadata(_))
      tds.foreach(_.validate())
      schema().relatedLists.validate()
    }
  }

  private def loadClasses(): Unit = {
    val pcOpt = pkg.org.parsedCache
    val docs = index.get(ApexNature).collect { case ad: ApexClassDocument => ad }.toSeq

    ServerOps.debugTime(s"Loaded summary classes", docs.nonEmpty) {

      // Load summary docs that have valid dependents
      if (pcOpt.nonEmpty) {
        val validSummaryDocs = new mutable.HashMap[TypeName, SummaryApex]()
        docs
          .grouped(500)
          .foreach(grp => loadClassesFromCache(grp.toArray, pcOpt.get, validSummaryDocs))

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
          summary.diagnostics.foreach(diagnostic => pkg.org.issues.add(Issue(path, diagnostic)))
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
        })

      // Validate the full types
      fullTypes.foreach(_._1.validate())
    }
  }

  private def loadClassesFromCache(docs: Array[MetadataDocument],
                                   pc: ParsedCache,
                                   accum: mutable.Map[TypeName, SummaryApex]): Unit = {
    val pkgContext = pkg.packageContext
    val localAccum = new ConcurrentHashMap[TypeName, SummaryApex]()
    docs.par.foreach(doc => {
      val data = doc.path.readBytes()
      val value = pc.get(data.getOrElse(throw new NoSuchElementException), pkgContext)
      val ad = value.map(v => SummaryApex(doc.path, this, v))
      if (ad.nonEmpty && !ad.get.diagnostics.exists(_.category == MISSING_CATEGORY)) {
        localAccum.put(ad.get.declaration.typeName, ad.get)
      }
    })

    localAccum
      .entrySet()
      .asScala
      .foreach(kv => {
        accum.put(kv.getKey, kv.getValue)
        upsertMetadata(kv.getValue.declaration)
      })

  }

  private def loadTriggers(): Unit = {
    val docs = index.get(TriggerNature).toSeq
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
}
