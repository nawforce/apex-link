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
import com.nawforce.apexlink.types.core.{FieldDeclaration, TypeDeclaration}
import com.nawforce.apexlink.types.other._
import com.nawforce.apexlink.types.platform.PlatformTypes
import com.nawforce.apexlink.types.schema.{SObjectNature, _}
import com.nawforce.apexlink.types.synthetic.CustomFieldDeclaration
import com.nawforce.pkgforce.diagnostics.{Issue, MISSING_CATEGORY}
import com.nawforce.pkgforce.documents._
import com.nawforce.pkgforce.names.{Name, Names, TypeName}
import com.nawforce.pkgforce.stream._
import com.nawforce.runtime.parsers.CodeParser
import com.nawforce.runtime.platform.Environment

import scala.collection.parallel.CollectionConverters._
import scala.collection.{BufferedIterator, mutable}
import scala.jdk.CollectionConverters._
import scala.reflect.ClassTag

/** 'Deploy' a module from a stream of PackageEvents. Deploying here really means constructing a set of TypeDeclarations
  * and validating them against each other. This process mutates the passed types Map for compatibility with dependency
  * analysis code. The class handling code here is performance sensitive so this may also aid with efficiency.
  *
  * TODO: Remove Module dependency.
  */
class StreamDeployer(module: Module,
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
      val fields: Array[FieldDeclaration] = bufferEvents[CustomFieldEvent](objectsEvents).map(
        field =>
          CustomFieldDeclaration(field.name,
                                 StreamDeployer.platformTypeOfFieldType(field.rawType).typeName,
                                 field.idTarget.map(TypeName(_))))
      val fieldSets = bufferEvents[FieldsetEvent](objectsEvents).map(_.name)
      val sharingReasons = bufferEvents[SharingReasonEvent](objectsEvents)

      // TODO: Fix to use stream data
      val tds = MetadataDocument(sObjectEvent.path).map {
        case docType: SObjectDocument =>
          SObjectDeclaration.create(module, docType.path).toArray
        case doc: PlatformEventDocument =>
          val typeName = doc.typeName(module.namespace)
          createSObject(typeName,
                        PlatformEventNature,
                        platformEventFields(typeName, fields),
                        fieldSets)
        case doc: CustomMetadataDocument =>
          val typeName = doc.typeName(module.namespace)
          createSObject(typeName,
                        CustomMetadataNature,
                        customMetadataFields(typeName, fields),
                        fieldSets)
        case doc: BigObjectDocument =>
          createSObject(doc.typeName(module.namespace), BigObjectNature, fields, fieldSets)
        case _ => assert(false); Array()
      }
      tds.map(tds => {
        tds.foreach(td => types.put(td.typeName, td))
        tds.foreach(td => td.validate())
      })
    }
    module.schema().relatedLists.validate()
  }

  private def createSObject(typeName: TypeName,
                            nature: SObjectNature,
                            fields: Array[FieldDeclaration],
                            fieldSets: Array[Name]): Array[SObjectDeclaration] = {
    val sobjectType = new SObjectDeclaration(Array.empty,
                                             module,
                                             typeName,
                                             nature,
                                             fieldSets,
                                             Name.emptyNames,
                                             fields,
                                             _isComplete = true)
    types.put(sobjectType.typeName, sobjectType)
    Array(sobjectType)
  }

  private def customMetadataFields(typeName: TypeName,
                                   fields: Array[FieldDeclaration]): Array[FieldDeclaration] = {
    Array(CustomFieldDeclaration(Names.Id, TypeNames.IdType, Some(typeName)),
          CustomFieldDeclaration(Names.SObjectType,
                                 TypeNames.sObjectType$(typeName),
                                 None,
                                 asStatic = true)) ++
      StreamDeployer.standardCustomMetadataFields ++
      fields
  }

  private def platformEventFields(typeName: TypeName,
                                  fields: Array[FieldDeclaration]): Array[FieldDeclaration] = {
    StreamDeployer.standardPlatformEventFields ++
      fields :+
      CustomFieldDeclaration(Names.SObjectType,
                             TypeNames.sObjectType$(typeName),
                             None,
                             asStatic = true)
  }

  /** Consume Apex class events, this is a bit more involved as we try and load first via cache and then fallback
    * to reading the source and parsing.  */
  private def consumeClasses(events: BufferedIterator[PackageEvent]): Unit = {
    val docs = bufferEvents[ApexEvent](events).map(e => ApexClassDocument(e.path))

    // Load summary classes from the cache
    ServerOps.debugTime(s"Loaded summary classes", docs.nonEmpty) {
      validateSummaryClasses(docs.grouped(500).flatMap(loadClassesFromCache))
    }

    // Load any classes not found via cache or that have been rejected
    val missingClasses =
      docs.filterNot(doc => types.contains(TypeName(doc.name).withNamespace(module.namespace)))

    ServerOps.debugTime(s"Parsed ${missingClasses.length} classes", missingClasses.nonEmpty) {
      val classTypes = missingClasses
        .flatMap(doc => {
          doc.path.readSourceData() match {
            case Left(_) => None
            case Right(data) =>
              ServerOps.debugTime(s"Parsed ${doc.path}") {
                FullDeclaration
                  .create(module, doc, data)
                  .map(td => {
                    types.put(td.typeName, td)
                    td
                  })
              }
          }
        })

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
          summaryClass.diagnostics.foreach(diagnostic =>
            module.pkg.org.issues.add(Issue(path, diagnostic)))
        } else {
          // Remove those dependent on non-cached so they are re-validated via loading fresh
          types.remove(summaryClass.declaration.typeName)
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
    ServerOps.debugTime(s"Parsed ${docs.length} triggers", docs.nonEmpty) {
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

object StreamDeployer {
  val standardPlatformEventFields: Array[FieldDeclaration] = {
    Array(CustomFieldDeclaration(Names.ReplayId, TypeNames.String, None),
          CustomFieldDeclaration(Name("CreatedBy"), TypeNames.User, None),
          CustomFieldDeclaration(Name("CreatedById"), TypeNames.IdType, None),
          CustomFieldDeclaration(Name("CreatedDate"), TypeNames.Datetime, None))
  }

  val standardCustomMetadataFields: Array[FieldDeclaration] = {
    Array(CustomFieldDeclaration(Names.DeveloperName, TypeNames.String, None),
          CustomFieldDeclaration(Names.IsProtected, TypeNames.Boolean, None),
          CustomFieldDeclaration(Names.Label, TypeNames.String, None),
          CustomFieldDeclaration(Names.Language, TypeNames.String, None),
          CustomFieldDeclaration(Names.MasterLabel, TypeNames.String, None),
          CustomFieldDeclaration(Names.NamespacePrefix, TypeNames.String, None),
          CustomFieldDeclaration(Names.QualifiedAPIName, TypeNames.String, None))
  }

  def platformTypeOfFieldType(fieldType: Name): TypeDeclaration = {
    fieldType.value match {
      case "MasterDetail"         => PlatformTypes.idType
      case "Lookup"               => PlatformTypes.idType
      case "MetadataRelationship" => PlatformTypes.idType
      case "AutoNumber"           => PlatformTypes.stringType
      case "Checkbox"             => PlatformTypes.booleanType
      case "Currency"             => PlatformTypes.decimalType
      case "Date"                 => PlatformTypes.dateType
      case "DateTime"             => PlatformTypes.datetimeType
      case "Email"                => PlatformTypes.stringType
      case "EncryptedText"        => PlatformTypes.stringType
      case "Number"               => PlatformTypes.decimalType
      case "Percent"              => PlatformTypes.decimalType
      case "Phone"                => PlatformTypes.stringType
      case "Picklist"             => PlatformTypes.stringType
      case "MultiselectPicklist"  => PlatformTypes.stringType
      case "Summary"              => PlatformTypes.decimalType
      case "Text"                 => PlatformTypes.stringType
      case "TextArea"             => PlatformTypes.stringType
      case "LongTextArea"         => PlatformTypes.stringType
      case "Url"                  => PlatformTypes.stringType
      case "File"                 => PlatformTypes.stringType
      case "Location"             => PlatformTypes.locationType
      case "Time"                 => PlatformTypes.timeType
      case "Html"                 => PlatformTypes.stringType
      // pkgforce validates on loading, so need for default handling here
    }
  }
}
