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

import com.nawforce.apexlink.finding.TypeResolver
import com.nawforce.apexlink.names._
import com.nawforce.apexlink.types.apex.{FullDeclaration, SummaryApex, TriggerDeclaration}
import com.nawforce.apexlink.types.core.{FieldDeclaration, TypeDeclaration}
import com.nawforce.apexlink.types.other._
import com.nawforce.apexlink.types.platform.{PlatformTypeDeclaration, PlatformTypes}
import com.nawforce.apexlink.types.schema.{SObjectNature, _}
import com.nawforce.apexlink.types.synthetic.CustomFieldDeclaration
import com.nawforce.pkgforce.diagnostics._
import com.nawforce.pkgforce.documents._
import com.nawforce.pkgforce.names._
import com.nawforce.pkgforce.path.PathLike
import com.nawforce.pkgforce.stream._
import com.nawforce.runtime.parsers.CodeParser
import com.nawforce.runtime.platform.Environment

import java.util.concurrent.ConcurrentHashMap
import scala.collection.immutable.ArraySeq
import scala.collection.mutable.ArrayBuffer
import scala.collection.parallel.CollectionConverters._
import scala.collection.{BufferedIterator, mutable}
import scala.jdk.CollectionConverters._
import scala.reflect.ClassTag

/** 'Deploy' a module from a stream of PackageEvents. Deploying here really means constructing a set of TypeDeclarations
  * and validating them against each other. This process mutates the passed types Map for compatibility with dependency
  * analysis code. The class handling code here is performance sensitive so this may also aid with efficiency.
  *
  * FUTURE: Remove Module dependency.
  */
class StreamDeployer(module: Module,
                     events: Iterator[PackageEvent],
                     types: mutable.Map[TypeName, TypeDeclaration]) {
  private val classGroupSize = 500

  load()

  private def load(): Unit = {
    val start = java.lang.System.currentTimeMillis()
    val basicTypesSize = types.size

    // Process package events, these must follow the publishing order from pkgforce
    val bufferedIterator = events.buffered
    consumeLabels(bufferedIterator)
    consumePages(bufferedIterator)
    consumeFlows(bufferedIterator)
    consumeComponents(bufferedIterator)
    consumeSObjects(bufferedIterator)
    consumeExtendedClasses(bufferedIterator)
    consumeClasses(bufferedIterator)
    consumeTriggers(bufferedIterator)
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
    val objectsEvents = bufferEvents(Set(classOf[SObjectEvent],
                                         classOf[CustomFieldEvent],
                                         classOf[FieldsetEvent],
                                         classOf[SharingReasonEvent]),
                                     events).iterator.buffered

    val referenceFields = ArrayBuffer[(CustomFieldEvent, SObjectEvent, TypeName)]()

    while (objectsEvents.hasNext) {
      val sObjectEvent = objectsEvents.next().asInstanceOf[SObjectEvent]

      val doc = MetadataDocument(sObjectEvent.path)
      assert(doc.exists(_.isInstanceOf[SObjectLike]))
      val encodedName = EncodedName(doc.get.name).defaultNamespace(module.namespace)
      val typeName = TypeName(encodedName.fullName, Nil, Some(TypeNames.Schema))

      val fieldEvents = bufferEvents[CustomFieldEvent](objectsEvents)
      val fields = fieldEvents.flatMap(f => createCustomField(sObjectEvent.path, typeName, f))
      val fieldSets = bufferEvents[FieldsetEvent](objectsEvents).map(_.name)
      val sharingReasons = bufferEvents[SharingReasonEvent](objectsEvents).map(_.name)

      // As there may be cyclic referencing between SObjects, we save these for later processing
      referenceFields.addAll(fieldEvents.collect {
        case e if StreamDeployer.referenceFieldTypes.contains(e.rawType) =>
          (e, sObjectEvent, typeName)
      })

      doc
        .foreach {
          case doc: SObjectDocument if doc.name.value.endsWith("__c") =>
            // Custom SObject or Custom Setting
            createCustomObject(sObjectEvent,
                               encodedName,
                               typeName,
                               fields,
                               fieldSets,
                               sharingReasons)
          case doc: SObjectDocument =>
            // Platform SObject
            createReplacementSObject(doc.path,
                                     typeName,
                                     PlatformObjectNature,
                                     fields,
                                     fieldSets,
                                     sharingReasons)
          case _: PlatformEventDocument =>
            createSObjectLike(typeName, PlatformEventNature, platformEventFields(typeName, fields))
          case _: CustomMetadataDocument =>
            createSObjectLike(typeName,
                              CustomMetadataNature,
                              customMetadataFields(typeName, fields))
          case _: BigObjectDocument =>
            createSObjectLike(typeName, BigObjectNature, fields)
        }
    }

    // Apply 'reverse' reference fields over the created SObjects
    referenceFields.foreach(fieldObject => {
      val referenceTo = fieldObject._1.referenceTo.get._1
      val relationshipName = Name(fieldObject._1.referenceTo.get._2.value + "__r")
      val refTypeName = schemaTypeNameOf(referenceTo)
      val reverseFieldName =
        EncodedName(relationshipName).defaultNamespace(module.namespace).fullName

      addReferenceFieldToSObject(refTypeName,
                                 reverseFieldName,
                                 fieldObject._1.name,
                                 fieldObject._3,
                                 PathLocation(fieldObject._2.path.toString, Location(0)))
    })
  }

  private def createCustomField(path: PathLike, typeName: TypeName, field: CustomFieldEvent,
  ): Array[FieldDeclaration] = {
    val fieldType = StreamDeployer.platformTypeOfFieldType(field).typeName
    val fieldDeclaration =
      CustomFieldDeclaration(field.name,
                             fieldType,
                             field.referenceTo.map(to => schemaTypeNameOf(to._1)))

    // Create additional fields & lookup relationships for special fields types
    field.rawType.value match {
      case "Lookup" | "MasterDetail" | "MetadataRelationship" =>
        val refTypeName = schemaTypeNameOf(field.referenceTo.get._1)

        Array(fieldDeclaration,
              CustomFieldDeclaration(field.name.replaceAll("__c$", "__r"), refTypeName, None))
      case "Location" =>
        Array(fieldDeclaration,
              CustomFieldDeclaration(field.name.replaceAll("__c$", "__latitude__s"),
                                     TypeNames.Double,
                                     None),
              CustomFieldDeclaration(field.name.replaceAll("__c$", "__longitude__s"),
                                     TypeNames.Double,
                                     None))
      case _ => Array(fieldDeclaration)
    }
  }

  private def addReferenceFieldToSObject(targetTypeName: TypeName,
                                         targetFieldName: Name,
                                         originatingFieldName: Name,
                                         originatingTypeName: TypeName,
                                         location: PathLocation): Unit = {
    val td = TypeResolver(targetTypeName, module).toOption
    if ((td.isEmpty || !td.exists(_.isSObject)) && !module.isGhostedType(targetTypeName)) {
      OrgImpl.logError(
        location,
        s"Lookup object $targetTypeName does not exist for field '$originatingFieldName'")
    }

    td.map(td => {
      val sobjectNature = td match {
        case _: PlatformTypeDeclaration => PlatformObjectNature
        case decl: SObjectDeclaration   => decl.sobjectNature
      }

      extendExistingSObject(Some(td),
                            Array(),
                            td.typeName,
                            sobjectNature,
                            Array(
                              CustomFieldDeclaration(targetFieldName,
                                                     TypeNames.recordSetOf(originatingTypeName),
                                                     None)),
                            Array(),
                            Array())
    })
  }

  private def schemaTypeNameOf(name: Name): TypeName = {
    TypeName(EncodedName(name).defaultNamespace(module.namespace).fullName,
             Nil,
             Some(TypeNames.Schema))
  }

  private def createCustomObject(event: SObjectEvent,
                                 encodedName: EncodedName,
                                 typeName: TypeName,
                                 fields: Array[FieldDeclaration],
                                 fieldSets: Array[Name],
                                 sharingReasons: Array[Name]): Array[SObjectDeclaration] = {
    val path = event.path

    val customObjectNature = event.customSettingsType match {
      case Some("List")      => ListCustomSettingNature
      case Some("Hierarchy") => HierarchyCustomSettingsNature
      case _                 => CustomObjectNature
    }
    if (encodedName.namespace == module.namespace)
      createNewSObject(path, typeName, customObjectNature, fields, fieldSets, sharingReasons)
    else if (module.isGhostedType(typeName))
      Array(
        extendExistingSObject(None,
                              Array(path),
                              typeName,
                              customObjectNature,
                              fields,
                              fieldSets,
                              sharingReasons))
    else
      createReplacementSObject(path,
                               typeName,
                               customObjectNature,
                               fields,
                               fieldSets,
                               sharingReasons)
  }

  /** Create a new SObject along withs it's supporting objects. */
  private def createNewSObject(path: PathLike,
                               typeName: TypeName,
                               nature: SObjectNature,
                               fields: Array[FieldDeclaration],
                               fieldSets: Array[Name],
                               sharingReasons: Array[Name]): Array[SObjectDeclaration] = {
    val allObjects =
      Array(
            // FUTURE: Check fields & when these should be available
            createShare(typeName, sharingReasons),
            createFeed(typeName),
            createHistory(typeName),
            new SObjectDeclaration(Array(path),
                                   module,
                                   typeName,
                                   nature,
                                   fieldSets,
                                   Name.emptyNames,
                                   customObjectFields(typeName, nature, fields),
                                   _isComplete = true))

    allObjects.foreach(td => {
      types.put(td.typeName, td)
      module.schemaSObjectType().add(td)
    })
    allObjects
  }

  private def createShare(typeName: TypeName, sharingReasons: Array[Name]): SObjectDeclaration = {
    SObjectDeclaration(Array.empty,
                       module,
                       typeName.withNameReplace("__c$", "__Share"),
                       CustomObjectNature,
                       Array(),
                       sharingReasons,
                       customObjectFields(typeName, CustomObjectNature, StreamDeployer.shareFields),
                       _isComplete = true)
  }

  private def createFeed(typeName: TypeName): SObjectDeclaration = {
    SObjectDeclaration(Array.empty,
                       module,
                       typeName.withNameReplace("__c$", "__Feed"),
                       CustomObjectNature,
                       Name.emptyNames,
                       Name.emptyNames,
                       customObjectFields(typeName, CustomObjectNature, StreamDeployer.feedFields),
                       _isComplete = true)
  }

  private def createHistory(typeName: TypeName): SObjectDeclaration = {
    SObjectDeclaration(PathLike.emptyPaths,
                       module,
                       typeName.withNameReplace("__c$", "__History"),
                       CustomObjectNature,
                       Name.emptyNames,
                       Name.emptyNames,
                       customObjectFields(typeName,
                                          CustomObjectNature,
                                          StreamDeployer.historyFields),
                       _isComplete = true)
  }

  /** Create an SObject to replace some existing SObject so that it can be extended. If no existing can be found then
    * an error is raised and the operation is new SObject is not created. */
  private def createReplacementSObject(path: PathLike,
                                       typeName: TypeName,
                                       nature: SObjectNature,
                                       fields: Array[FieldDeclaration],
                                       fieldSets: Array[Name],
                                       sharingReasons: Array[Name]): Array[SObjectDeclaration] = {

    if (typeName == TypeNames.Activity) {
      // Fake Activity as applying to Task & Event, how bizarre is that
      createReplacementSObject(path, TypeNames.Task, nature, fields, fieldSets, sharingReasons) ++
        createReplacementSObject(path, TypeNames.Event, nature, fields, fieldSets, sharingReasons)
    } else {
      val sobjectType = TypeResolver(typeName, module).toOption
      if (sobjectType.isEmpty || !sobjectType.get.superClassDeclaration.exists(superClass =>
            superClass.typeName == TypeNames.SObject)) {
        OrgImpl.logError(PathLocation(path.toString, Location.empty),
                         s"No SObject declaration found for '$typeName'")
        return Array()
      }
      Array(
        extendExistingSObject(sobjectType,
                              Array(path),
                              typeName,
                              nature,
                              fields,
                              fieldSets,
                              sharingReasons))
    }
  }

  /** Create an SObject by extending a base SObject with new fields, fieldSets and sharing reasons. If you don't pass
    * a base object than this will extend System.SObject. */
  private def extendExistingSObject(base: Option[TypeDeclaration],
                                    paths: Array[PathLike],
                                    typeName: TypeName,
                                    nature: SObjectNature,
                                    fields: Array[FieldDeclaration],
                                    fieldSets: Array[Name],
                                    sharingReasons: Array[Name]): SObjectDeclaration = {

    implicit class TypeDeclarationOps(td: TypeDeclaration) {
      def fieldSets: Array[Name] = {
        td match {
          case td: SObjectDeclaration => td.fieldSets
          case _                      => Array()
        }
      }

      def sharingReasons: Array[Name] = {
        td match {
          case td: SObjectDeclaration => td.sharingReasons
          case _                      => Array()
        }
      }
    }

    val extend = base.getOrElse(PlatformTypes.sObjectType)
    val combinedField = fields
      .foldLeft(extend.fields.map(field => (field.name, field)).toMap)((acc, field) =>
        acc + (field.name -> field))
      .values
      .toArray
    val combinedFieldsets = fieldSets
      .foldLeft(base.map(_.fieldSets).getOrElse(Array()).toSet)((acc, fieldset) => acc + fieldset)
      .toArray
    val combinedSharingReasons = sharingReasons
      .foldLeft(base.map(_.sharingReasons).getOrElse(Array()).toSet)((acc, sharingReason) =>
        acc + sharingReason)
      .toArray

    val td = new SObjectDeclaration(paths,
                                    module,
                                    typeName,
                                    nature,
                                    combinedFieldsets,
                                    combinedSharingReasons,
                                    combinedField,
                                    base.nonEmpty && base.get.isComplete)
    types.put(td.typeName, td)
    module.schemaSObjectType().add(td)
    td
  }

  /** Create a type declaration for a SObjectLike, such as a platform event. This just defaults a couple of fields
    * and make the new type declaration available in the module. */
  private def createSObjectLike(typeName: TypeName,
                                nature: SObjectNature,
                                fields: Array[FieldDeclaration]): Array[SObjectDeclaration] = {
    val sobjectType = new SObjectDeclaration(Array.empty,
                                             module,
                                             typeName,
                                             nature,
                                             Name.emptyNames,
                                             Name.emptyNames,
                                             fields,
                                             _isComplete = true)
    types.put(sobjectType.typeName, sobjectType)
    module.schemaSObjectType().add(sobjectType)
    Array(sobjectType)
  }

  /** Construct a full set of fields for a custom objects from the custom fields defined in the event. */
  private def customObjectFields(typeName: TypeName,
                                 nature: SObjectNature,
                                 fields: Array[FieldDeclaration]): Array[FieldDeclaration] = {
    Array(
      CustomFieldDeclaration(Names.SObjectType,
                             TypeNames.sObjectType$(typeName),
                             None,
                             asStatic = true),
      CustomFieldDeclaration(Names.Fields,
                             TypeNames.sObjectFields$(typeName),
                             None,
                             asStatic = true),
      CustomFieldDeclaration(Names.Id, TypeNames.IdType, Some(typeName))) ++
      StreamDeployer.standardCustomObjectFields ++
      fields ++
      (if (nature == HierarchyCustomSettingsNature)
         Array(CustomFieldDeclaration(Names.SetupOwnerId, PlatformTypes.idType.typeName, None))
       else
         Array[FieldDeclaration]())
  }

  /** Construct a full set of fields for a custom metadata from the custom fields defined in the event. */
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

  /** Construct a full set of fields for a platform event from the custom fields defined in the event. */
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
      validateSummaryClasses(docs.grouped(classGroupSize).flatMap(loadClassesFromCache))
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
        .grouped(classGroupSize)
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
          CodeParser.clearCaches()
          tds
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

  /** Standard fields for custom objects. */
  lazy val standardCustomObjectFields: Seq[FieldDeclaration] = {
    Seq(CustomFieldDeclaration(Names.NameName, TypeNames.String, None),
        CustomFieldDeclaration(Names.RecordTypeId, TypeNames.IdType, None),
        CustomFieldDeclaration(Name("CreatedBy"), TypeNames.User, None),
        CustomFieldDeclaration(Name("CreatedById"), TypeNames.IdType, None),
        CustomFieldDeclaration(Name("CreatedDate"), TypeNames.Datetime, None),
        CustomFieldDeclaration(Name("LastModifiedBy"), TypeNames.User, None),
        CustomFieldDeclaration(Name("LastModifiedById"), TypeNames.IdType, None),
        CustomFieldDeclaration(Name("LastModifiedDate"), TypeNames.Datetime, None),
        CustomFieldDeclaration(Name("IsDeleted"), TypeNames.Boolean, None),
        CustomFieldDeclaration(Name("SystemModstamp"), TypeNames.Datetime, None)) ++
      PlatformTypes.sObjectType.fields.filterNot(f => f.name == Names.SObjectType)
  }

  /** Standard fields for platform events. */
  lazy val standardPlatformEventFields: Array[FieldDeclaration] = {
    Array(CustomFieldDeclaration(Names.ReplayId, TypeNames.String, None),
          CustomFieldDeclaration(Name("CreatedBy"), TypeNames.User, None),
          CustomFieldDeclaration(Name("CreatedById"), TypeNames.IdType, None),
          CustomFieldDeclaration(Name("CreatedDate"), TypeNames.Datetime, None))
  }

  /** Standard fields for custom metadata. */
  lazy val standardCustomMetadataFields: Array[FieldDeclaration] = {
    Array(CustomFieldDeclaration(Names.DeveloperName, TypeNames.String, None),
          CustomFieldDeclaration(Names.IsProtected, TypeNames.Boolean, None),
          CustomFieldDeclaration(Names.Label, TypeNames.String, None),
          CustomFieldDeclaration(Names.Language, TypeNames.String, None),
          CustomFieldDeclaration(Names.MasterLabel, TypeNames.String, None),
          CustomFieldDeclaration(Names.NamespacePrefix, TypeNames.String, None),
          CustomFieldDeclaration(Names.QualifiedAPIName, TypeNames.String, None))
  }

  /** Standard fields for a \_\_Share SObject. */
  lazy val shareFields: Array[FieldDeclaration] = PlatformTypes.sObjectType.fields ++ Seq(
    CustomFieldDeclaration(Names.AccessLevel, PlatformTypes.stringType.typeName, None),
    CustomFieldDeclaration(Names.ParentId, PlatformTypes.idType.typeName, None),
    CustomFieldDeclaration(Names.RowCause, PlatformTypes.stringType.typeName, None),
    CustomFieldDeclaration(Names.UserOrGroupId, PlatformTypes.idType.typeName, None))

  /** Standard fields for a \_\_Feed SObject. */
  lazy val feedFields: Array[FieldDeclaration] = PlatformTypes.sObjectType.fields ++ Seq(
    CustomFieldDeclaration(Names.BestCommentId, PlatformTypes.idType.typeName, None),
    CustomFieldDeclaration(Names.Body, PlatformTypes.stringType.typeName, None),
    CustomFieldDeclaration(Names.CommentCount, PlatformTypes.decimalType.typeName, None),
    CustomFieldDeclaration(Names.ConnectionId, PlatformTypes.idType.typeName, None),
    CustomFieldDeclaration(Names.InsertedById, PlatformTypes.idType.typeName, None),
    CustomFieldDeclaration(Names.IsRichText, PlatformTypes.booleanType.typeName, None),
    CustomFieldDeclaration(Names.LikeCount, PlatformTypes.decimalType.typeName, None),
    CustomFieldDeclaration(Names.LinkUrl, PlatformTypes.stringType.typeName, None),
    CustomFieldDeclaration(Names.NetworkScope, PlatformTypes.stringType.typeName, None),
    CustomFieldDeclaration(Names.ParentId, PlatformTypes.idType.typeName, None),
    CustomFieldDeclaration(Names.RelatedRecordId, PlatformTypes.idType.typeName, None),
    CustomFieldDeclaration(Names.Title, PlatformTypes.stringType.typeName, None),
    CustomFieldDeclaration(Names.Type, PlatformTypes.stringType.typeName, None),
    CustomFieldDeclaration(Names.Visibility, PlatformTypes.stringType.typeName, None),
  )

  lazy val historyFields: Array[FieldDeclaration] = PlatformTypes.sObjectType.fields ++ Seq(
    CustomFieldDeclaration(Names.Field, PlatformTypes.stringType.typeName, None),
    CustomFieldDeclaration(Names.NewValue, PlatformTypes.objectType.typeName, None),
    CustomFieldDeclaration(Names.OldValue, PlatformTypes.objectType.typeName, None),
  )

  val referenceFieldTypes: Set[Name] =
    Set(Name("Lookup"), Name("MasterDetail"), Name("MetadataRelationship"))

  /** Convert a field type string to the platform type used for it in Apex. */
  def platformTypeOfFieldType(field: CustomFieldEvent): TypeDeclaration = {
    field.rawType.value match {
      case "MasterDetail"        => PlatformTypes.idType
      case "Lookup"              => PlatformTypes.idType
      case "AutoNumber"          => PlatformTypes.stringType
      case "Checkbox"            => PlatformTypes.booleanType
      case "Currency"            => PlatformTypes.decimalType
      case "Date"                => PlatformTypes.dateType
      case "DateTime"            => PlatformTypes.datetimeType
      case "Email"               => PlatformTypes.stringType
      case "EncryptedText"       => PlatformTypes.stringType
      case "Number"              => PlatformTypes.decimalType
      case "Percent"             => PlatformTypes.decimalType
      case "Phone"               => PlatformTypes.stringType
      case "Picklist"            => PlatformTypes.stringType
      case "MultiselectPicklist" => PlatformTypes.stringType
      case "Summary"             => PlatformTypes.decimalType
      case "Text"                => PlatformTypes.stringType
      case "TextArea"            => PlatformTypes.stringType
      case "LongTextArea"        => PlatformTypes.stringType
      case "Url"                 => PlatformTypes.stringType
      case "File"                => PlatformTypes.stringType
      case "Location"            => PlatformTypes.locationType
      case "Time"                => PlatformTypes.timeType
      case "Html"                => PlatformTypes.stringType
      case "MetadataRelationship"
          if field.referenceTo
            .map(_._1.value)
            .exists(to => to == "FieldDefinition" || to == "EntityDefinition") =>
        PlatformTypes.stringType
      case "MetadataRelationship" => PlatformTypes.idType
      // pkgforce validates on loading, so need for default handling here
    }
  }
}
