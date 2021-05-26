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
import com.nawforce.apexlink.types.core.TypeDeclaration
import com.nawforce.apexlink.types.other._
import com.nawforce.apexlink.types.schema.SObjectDeclaration
import com.nawforce.pkgforce.diagnostics.{Issue, MISSING_CATEGORY}
import com.nawforce.pkgforce.documents._
import com.nawforce.pkgforce.names.{Name, TypeName}
import com.nawforce.pkgforce.stream._
import com.nawforce.runtime.parsers.CodeParser
import com.nawforce.runtime.platform.Environment

import scala.collection.parallel.CollectionConverters._
import scala.collection.{BufferedIterator, mutable}
import scala.jdk.CollectionConverters._
import scala.reflect.ClassTag

class StreamDeployer(module: Module,
                     namespace: Option[Name],
                     events: Iterator[PackageEvent],
                     types: mutable.Map[TypeName, TypeDeclaration]) {

  private val start = java.lang.System.currentTimeMillis()

  // Prime basic types
  upsertMetadata(AnyDeclaration(module))
  upsertMetadata(module.schema().sobjectTypes)
  upsertMetadata(module.schema().sobjectTypes, Some(TypeName(module.schema().sobjectTypes.name)))
  private val basicTypesSize = types.size

  // Process package events, these must follow the publishing order from pkgforce
  private val bufferedIterator = events.buffered
  consumeLabels(bufferedIterator)
  consumePages(bufferedIterator)
  consumeFlows(bufferedIterator)
  consumeComponents(bufferedIterator)
  consumeCustomObjects(bufferedIterator)
  consumeClasses(bufferedIterator)
  consumeTriggers(bufferedIterator)

  // Report progress and tidy up
  if (types.size > basicTypesSize) {
    val total = java.lang.System.currentTimeMillis() - start
    val avg = total / types.size
    ServerOps.debug(ServerOps.Trace,
                    s"Module($toString) loaded ${types.size}" +
                      s" types in ${total / 1000} seconds, average $avg ms/type")
  }

  CodeParser.clearCaches()
  Environment.gc()

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

  private def consumeCustomObjects(events: BufferedIterator[PackageEvent]): Unit = {
    val objectsEvents = bufferEvents(Set(classOf[SObjectEvent],
                                         classOf[CustomFieldEvent],
                                         classOf[FieldsetEvent],
                                         classOf[SharingReasonEvent]),
                                     events).iterator.buffered
    while (objectsEvents.hasNext) {
      val sObjectEvent = objectsEvents.next().asInstanceOf[SObjectEvent]
      val fields = bufferEvents[CustomFieldEvent](objectsEvents)
      val fieldSets = bufferEvents[FieldsetEvent](objectsEvents)
      val sharingReasons = bufferEvents[SharingReasonEvent](objectsEvents)

      // TODO: Fix to use stream data
      val tds = MetadataDocument(sObjectEvent.path).map {
        case docType: SObjectDocument =>
          SObjectDeclaration.create(module, docType.path)
        case docType: PlatformEventDocument =>
          SObjectDeclaration.create(module, docType.path)
        case docType: CustomMetadataDocument =>
          SObjectDeclaration.create(module, docType.path)
        case docType: BigObjectDocument =>
          SObjectDeclaration.create(module, docType.path)
        case _ => assert(false); Seq()
      }
      tds.map(tds => {
        tds.foreach(upsertMetadata(_))
        tds.foreach(td => td.validate())
      })
    }
    module.schema().relatedLists.validate()
  }

  private def consumeClasses(events: BufferedIterator[PackageEvent]): Unit = {
    val parsedCache = module.pkg.org.parsedCache
    val docs =
      bufferEvents[ApexEvent](events).flatMap(e => MetadataDocument(e.path)).collect {
        case d: ApexClassDocument => d
      }

    ServerOps.debugTime(s"Loaded summary classes", docs.nonEmpty) {

      // Load summary docs that have valid dependents
      if (parsedCache.nonEmpty) {
        val validSummaryDocs = new mutable.HashMap[TypeName, SummaryApex]()
        docs
          .grouped(500)
          .foreach(grp => loadClassesFromCache(grp.toArray, parsedCache.get, validSummaryDocs))

        // Multi-pass removal of those with invalid dependencies to counter resolving cyclic links
        var invalidDocs = validSummaryDocs.filterNot(_._2.declaration.hasValidDependencies)
        while (invalidDocs.nonEmpty) {
          invalidDocs.foreach(pair => {
            validSummaryDocs.remove(pair._1)
            types.remove(pair._2.declaration.typeName)
          })
          invalidDocs = validSummaryDocs.filterNot(_._2.declaration.hasValidDependencies)
        }

        // Validate for dependency propagation purposes
        validSummaryDocs.foreach(_._2.declaration.validate())

        // Report diagnostics for those that get this far
        validSummaryDocs.foreach(pair => {
          val summary: SummaryApex = pair._2
          val path = summary.declaration.path.toString
          summary.diagnostics.foreach(diagnostic =>
            module.pkg.org.issues.add(Issue(path, diagnostic)))
        })

      }
    }

    val missingTypes =
      docs.filterNot(doc => types.contains(TypeName(doc.name).withNamespace(namespace)))
    ServerOps.debugTime(s"Parsed ${missingTypes.length} classes", docs.nonEmpty) {

      // Load full docs for rest of set
      val fullTypes = missingTypes
        .flatMap(doc => {
          val data = doc.path.readSourceData()
          val tdOpt = ServerOps.debugTime(s"Parsed ${doc.path}") {
            FullDeclaration.create(module, doc, data.getOrElse(throw new NoSuchElementException))
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
    val pkgContext = module.pkg.packageContext
    val localAccum = new ConcurrentHashMap[TypeName, SummaryApex]()
    docs.par.foreach(doc => {
      val data = doc.path.readBytes()
      val value = pc.get(data.getOrElse(throw new NoSuchElementException), pkgContext)
      val ad = value.map(v => SummaryApex(doc.path, module, v))
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

  private def consumeTriggers(events: BufferedIterator[PackageEvent]): Unit = {
    val docs = bufferEvents[TriggerEvent](events)
      .flatMap(e => MetadataDocument(e.path))
      .collect { case d: ApexTriggerDocument => d }
    ServerOps.debugTime(s"Parsed ${docs.length} triggers", docs.nonEmpty) {
      val tds = docs.flatMap {
        case docType: ApexTriggerDocument =>
          val data = docType.path.readSourceData()
          TriggerDeclaration.create(module,
                                    docType.path,
                                    data.getOrElse(throw new NoSuchElementException))
        case _ => assert(false); Seq()
      }
      tds.foreach(upsertMetadata(_))
      tds.foreach(_.validate())
    }
  }

  // Upsert some metadata to the package
  def upsertMetadata(td: TypeDeclaration, altTypeName: Option[TypeName] = None): Unit = {
    types.put(altTypeName.getOrElse(td.typeName), td)
  }

  /** Read the maximum events that are all from an accepting set into an Array. IssueEvents are silently consumed
    * and logged against the active org. */
  private def bufferEvents(accept: Set[Class[_]],
                           events: BufferedIterator[PackageEvent]): Array[PackageEvent] = {
    val buffer = mutable.ArrayBuffer[PackageEvent]()
    var continue = events.hasNext
    while (continue) {
      continue = events.head match {
        case _ if accept.contains(events.head.getClass) => buffer.append(events.head); true
        case event: IssuesEvent                         => event.issues.foreach(OrgImpl.log); true
        case _                                          => false
      }
      if (continue) {
        events.next()
        continue = events.hasNext
      }
    }
    buffer.toArray
  }

  /** Read the maximum events that are all of the given type into an Array. IssueEvents are silently consumed
    * and logged against the active org. */
  private def bufferEvents[T: ClassTag](events: BufferedIterator[PackageEvent]): Array[T] = {
    val buffer = mutable.ArrayBuffer[T]()
    var continue = events.hasNext
    while (continue) {
      continue = events.head match {
        case e: T           => buffer.append(e); true
        case e: IssuesEvent => e.issues.foreach(OrgImpl.log); true
        case _              => false
      }
      if (continue) {
        events.next()
        continue = events.hasNext
      }
    }
    buffer.toArray
  }
}
