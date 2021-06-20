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

import com.nawforce.apexlink.names._
import com.nawforce.apexlink.types.apex.{FullDeclaration, SummaryApex, TriggerDeclaration}
import com.nawforce.apexlink.types.core.TypeDeclaration
import com.nawforce.apexlink.types.other._
import com.nawforce.apexlink.types.platform.PlatformTypes
import com.nawforce.pkgforce.diagnostics._
import com.nawforce.pkgforce.documents._
import com.nawforce.pkgforce.names._
import com.nawforce.pkgforce.stream._
import com.nawforce.runtime.parsers.CodeParser
import com.nawforce.runtime.platform.Environment

import java.util.concurrent.ConcurrentHashMap
import scala.collection.immutable.ArraySeq
import scala.collection.parallel.CollectionConverters._
import scala.collection.{BufferedIterator, mutable}
import scala.jdk.CollectionConverters._

/** 'Deploy' a module from a stream of PackageEvents. Deploying here really means constructing a set of TypeDeclarations
  * and validating them against each other. This process mutates the passed types Map for compatibility with dependency
  * analysis code. The class handling code here is performance sensitive so this may also aid with efficiency.
  *
  * FUTURE: Remove Module dependency.
  */
class StreamDeployer(module: Module, events: Iterator[PackageEvent], types: mutable.Map[TypeName, TypeDeclaration]) {
  load()

  private def load(): Unit = {
    val start = java.lang.System.currentTimeMillis()
    val basicTypesSize = types.size

    // Process package events, these must follow the publishing order from pkgforce
    PlatformTypes.withLoadingObserver(module.schemaSObjectType) {
      val bufferedIterator = events.buffered
      consumeLabels(bufferedIterator)
      consumePages(bufferedIterator)
      consumeFlows(bufferedIterator)
      consumeComponents(bufferedIterator)
      consumeSObjects(bufferedIterator)
      consumeExtendedClasses(bufferedIterator)
      consumeClasses(bufferedIterator)
      consumeTriggers(bufferedIterator)
    }
    CodeParser.clearCaches()
    Environment.gc()

    // Report progress and tidy up
    if (types.size > basicTypesSize) {
      val total = java.lang.System.currentTimeMillis() - start
      val avg = total / types.size
      LoggerOps.info(s"$module loaded ${types.size} types in ${total}ms, average $avg ms/type")
    }
  }

  private def consumeLabels(events: BufferedIterator[PackageEvent]): Unit = {
    val labelRelatedEvents = bufferEvents(Set(classOf[LabelFileEvent], classOf[LabelEvent]), events)
    val labelFileEvents = labelRelatedEvents.collect { case e: LabelFileEvent => e }
    val labelEvents = labelRelatedEvents.collect { case e: LabelEvent         => e }
    val labels = LabelDeclaration(module).merge(labelFileEvents, labelEvents)
    upsertMetadata(labels)
    upsertMetadata(labels, Some(TypeName(labels.name)))
  }

  private def consumePages(events: BufferedIterator[PackageEvent]): Unit = {
    upsertMetadata(PageDeclaration(module).merge(bufferEvents[PageEvent](events)))
  }

  private def consumeFlows(events: BufferedIterator[PackageEvent]): Unit = {
    upsertMetadata(InterviewDeclaration(module).merge(bufferEvents[FlowEvent](events)))
  }

  private def consumeComponents(events: BufferedIterator[PackageEvent]): Unit = {
    upsertMetadata(ComponentDeclaration(module).merge(bufferEvents[ComponentEvent](events)))
  }

  private def consumeSObjects(events: BufferedIterator[PackageEvent]): Unit = {
    val deployer = new SObjectDeployer(module)
    deployer
      .createSObjects(events)
      .foreach(sobject => {
        types.put(sobject.typeName, sobject)
        module.schemaSObjectType.add(sobject)
      })
  }

  /** Consume Apex class events, this is a bit more involved as we try and load first via cache and then fallback
    * to reading the source and parsing.  */
  private def consumeExtendedClasses(events: BufferedIterator[PackageEvent]): Unit = {
    val docs = bufferEvents[ExtendedApexEvent](events).map(e => ExtendedApexDocument(e.path))
    parseAndValidateClasses(ArraySeq.unsafeWrapArray(docs), extendedApex = true)
  }

  /** Consume Apex class events, this is a bit more involved as we try and load first via cache and then fallback
    * to reading the source and parsing.  */
  private def consumeClasses(events: BufferedIterator[PackageEvent]): Unit = {
    val docs = bufferEvents[ApexEvent](events).map(e => ApexClassDocument(e.path))

    // Load summary classes from the cache
    LoggerOps.debugTime(s"Loaded summary classes", docs.nonEmpty) {
      validateSummaryClasses(loadClassesFromCache(docs))
    }

    // Load any classes not found via cache or that have been rejected
    val missingClasses =
      docs.filterNot(doc => types.contains(TypeName(doc.name).withNamespace(module.namespace)))
    LoggerOps.debug(s"${missingClasses.length} of ${docs.length} classes not available from cache")
    parseAndValidateClasses(ArraySeq.unsafeWrapArray(missingClasses), extendedApex = false)
  }

  /** Parse a collection of Apex classes, insert them and validate them. */
  private def parseAndValidateClasses(docs: Seq[ClassDocument], extendedApex: Boolean): Unit = {
    LoggerOps.debugTime(s"Parsed ${docs.length} classes", docs.nonEmpty) {
      val classTypes = docs
        .grouped(500)
        .flatMap(group => {
          val tds = group.flatMap(doc => {
            doc.path.readSourceData() match {
              case Left(_) => None
              case Right(data) =>
                LoggerOps.debugTime(s"Parsed ${doc.path}") {
                  FullDeclaration
                    .create(module, doc, data, extendedApex)
                    .map(td => {
                      types.put(td.typeName, td)
                      td
                    })
                }
            }
          })
          // Clear caches to avoid unconstrained growth, this will slow the parser a bit
          CodeParser.clearCaches()
          tds
        })
        .toSeq

      // Validate the classes, this must be last due to mutual dependence
      classTypes.foreach(_.validate())
    }
  }

  /** Validate summary classes & log diagnostics, those with any invalid dependents are discarded. */
  private def validateSummaryClasses(summaryClasses: Iterator[SummaryApex]): Unit = {
    summaryClasses
      .foreach(summaryClass => {
        if (summaryClass.declaration.hasValidDependencies) {
          // Validate (just for dependency propagation as these are summaries)
          summaryClass.declaration.validate()

          // Report any (existing) diagnostics
          val path = summaryClass.declaration.path.toString
          summaryClass.diagnostics.foreach(diagnostic => module.pkg.org.issues.add(Issue(path, diagnostic)))
        } else {
          // Remove those dependent on non-cached so they are re-validated via loading fresh
          val typeName = summaryClass.declaration.typeName
          LoggerOps.info(s"Cached type $typeName rejected due to invalid dependencies")
          types.remove(typeName)
        }
      })
  }

  /** Load classes from the code cache as types returning TypeNames of those available. Benchmarking has shown
    * running this in parallel helps performance quite a bit with SSDs. */
  private def loadClassesFromCache(classes: Array[ApexClassDocument]): Iterator[SummaryApex] = {
    module.pkg.org.parsedCache
      .map(parsedCache => {

        val pkgContext = module.pkg.packageContext
        val localAccum = new ConcurrentHashMap[TypeName, SummaryApex]()

        classes.par.foreach(doc => {
          val data = doc.path.readBytes()
          val value = parsedCache.get(data.getOrElse(throw new NoSuchElementException), pkgContext)
          val ad = value.map(v => SummaryApex(doc.path, module, v))
          if (ad.nonEmpty && !ad.get.diagnostics.exists(_.category == MISSING_CATEGORY)) {
            localAccum.put(ad.get.declaration.typeName, ad.get)
          }
        })

        localAccum.entrySet.forEach(kv => {
          types.put(kv.getKey, kv.getValue.declaration)
        })

        localAccum.values().iterator().asScala
      })
      .getOrElse(Iterator())
  }

  /** Consume trigger events, these could be cached but they don't consume much time to for we load from disk and
    * parse each time. */
  private def consumeTriggers(events: BufferedIterator[PackageEvent]): Unit = {
    val docs = bufferEvents[TriggerEvent](events).map(e => ApexTriggerDocument(e.path))
    LoggerOps.debugTime(s"Parsed ${docs.length} triggers", docs.nonEmpty) {
      docs
        .flatMap(doc => {
          doc.path.readSourceData() match {
            case Left(_) => None
            case Right(data) =>
              TriggerDeclaration
                .create(module, doc.path, data)
                .map(td => {
                  types.put(td.typeName, td)
                  td.validate()
                })
          }
        })
    }
  }

  // Upsert some metadata to the package
  def upsertMetadata(td: TypeDeclaration, altTypeName: Option[TypeName] = None): Unit = {
    types.put(altTypeName.getOrElse(td.typeName), td)
  }
}
