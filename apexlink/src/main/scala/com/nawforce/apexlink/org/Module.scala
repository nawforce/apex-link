/*
 Copyright (c) 2019 Kevin Jones, All rights reserved.
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

import com.nawforce.apexlink.finding.TypeResolver.{TypeCache, TypeResponse}
import com.nawforce.apexlink.finding.{TypeFinder, TypeResolver}
import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.names.TypeNames.TypeNameUtils
import com.nawforce.apexlink.types.apex.{ApexClassDeclaration, ApexDeclaration, FullDeclaration, TriggerDeclaration}
import com.nawforce.apexlink.types.core.{DependentType, TypeDeclaration, TypeId}
import com.nawforce.apexlink.types.other._
import com.nawforce.apexlink.types.platform.PlatformTypes
import com.nawforce.apexlink.types.schema.{SObjectDeclaration, SchemaSObjectType}
import com.nawforce.pkgforce.documents._
import com.nawforce.pkgforce.modifiers.GLOBAL_MODIFIER
import com.nawforce.pkgforce.names.{EncodedName, Name, TypeIdentifier, TypeName}
import com.nawforce.pkgforce.path.PathLike
import com.nawforce.pkgforce.stream._
import com.nawforce.runtime.parsers.SourceData

import scala.collection.immutable.ArraySeq
import scala.collection.mutable

class Module(val pkg: PackageImpl, val index: DocumentIndex, dependents: Seq[Module])
  extends TypeFinder with ModuleCompletions {

  val baseModules: Seq[Module] = dependents.reverse
  val basePackages: Seq[PackageImpl] = pkg.basePackages.reverse
  val namespace: Option[Name] = pkg.namespace

  def namespaces: Set[Name] = pkg.namespaces

  private[nawforce] var types = mutable.Map[TypeName, TypeDeclaration]()
  private val schemaManager = SchemaSObjectType(this)

  def freeze(): Unit = {
    // FUTURE: Have return types, currently can't be done because class loading code needs access to in-flight types
    upsertMetadata(AnyDeclaration(this))
    upsertMetadata(schemaSObjectType)
    upsertMetadata(schemaSObjectType, Some(TypeName(schemaSObjectType.name)))

    new StreamDeployer(this, PackageStream.eventStream(index), types)
  }

  override def toString: String = s"Module(${index.path})"

  def schemaSObjectType: SchemaSObjectType = schemaManager
  def any: AnyDeclaration = types(TypeNames.Any).asInstanceOf[AnyDeclaration]
  def labels: LabelDeclaration = types(TypeNames.Label).asInstanceOf[LabelDeclaration]
  def interviews: InterviewDeclaration =
    types(TypeNames.Interview).asInstanceOf[InterviewDeclaration]
  def pages: PageDeclaration = types(TypeNames.Page).asInstanceOf[PageDeclaration]
  def components: ComponentDeclaration =
    types(TypeNames.Component).asInstanceOf[ComponentDeclaration]
  def nonTestClasses: Iterable[ApexClassDeclaration] = types.values.collect {
    case ac: ApexClassDeclaration if !ac.inTest => ac
  }
  def testClasses: Iterable[ApexClassDeclaration] = types.values.collect {
    case ac: ApexClassDeclaration if ac.inTest => ac
  }

  /** Count of loaded types, for debug info */
  def typeCount: Int = types.size

  /** Test if a file is visible to this module, i.e. in scope & not ignored */
  def isVisibleFile(path: PathLike): Boolean = {
    index.isVisibleFile(path)
  }

  /* Transitive Bases (dependent modules for this modules & its dependents) */
  def transitiveBaseModules: Set[Module] = {
    namespace
      .map(_ => dependents.toSet ++ dependents.flatMap(_.transitiveBaseModules))
      .getOrElse(baseModules.toSet)
  }

  /* Iterator over available types */
  def getTypes: Iterable[TypeDeclaration] = {
    types.values
  }

  /** Iterate metadata defined types, this will include referenced platform SObjects irrespective of if they have been
    * extended or not which is perhaps not quite accurate to the method name. */
  def getMetadataDefinedTypeIdentifiers(apexOnly: Boolean): Iterable[TypeIdentifier] = {
    types.values
      .collect {
        case x: ApexDeclaration                 => x
        case x: SObjectDeclaration if !apexOnly => x
      }
      .map(td => TypeIdentifier(namespace, td.typeName))
  }

  /* Search for a specific outer or inner type */
  def moduleType(typeName: TypeName): Option[TypeDeclaration] = {
    types
      .get(typeName)
      .orElse(
        typeName.outer
          .flatMap(types.get)
          .flatMap(_.nestedTypes.find(_.typeName == typeName)))
  }

  def replaceType(typeName: TypeName, typeDeclaration: Option[TypeDeclaration]): Unit = {
    if (typeDeclaration.nonEmpty) {
      val td = typeDeclaration.get
      types.put(typeName, td)

      // Labels must also be updated against just 'Label' to simulate the defaulting of the System namespace, this is
      // a design flaw but I am living it for now, if this is skipped Label would resolve against an empty platform
      // type.
      typeName match {
        case TypeNames.Label => types.put(TypeName(labels.name), labels)
        case _               => ()
      }

    } else {
      types.remove(typeName)
    }
  }

  def isGhostedType(typeName: TypeName): Boolean = pkg.isGhostedType(typeName)

  /* Check if a field name is ghosted in this package */
  def isGhostedFieldName(name: Name): Boolean = pkg.isGhostedFieldName(name)

  // Upsert some metadata to the package
  def upsertMetadata(td: TypeDeclaration, altTypeName: Option[TypeName] = None): Unit = {
    types.put(altTypeName.getOrElse(td.typeName), td)
  }

  // Remove some metadata from the package
  // Future: Support component & flow removal
  def removeMetadata(td: TypeDeclaration): Unit = {
    removeMetadata(td.typeName)
  }

  def removeMetadata(typeName: TypeName): Unit = {
    types.remove(typeName)
  }

  // Add dependencies for Apex types to a map
  def populateDependencies(dependencies: java.util.Map[String, Array[String]]): Unit = {
    val typeCache = new TypeCache()
    types.values.foreach {
      case td: ApexClassDeclaration =>
        val depends = mutable.Set[TypeId]()
        td.gatherDependencies(depends, apexOnly = false, outerTypesOnly = true, typeCache)
        depends.remove(td.typeId)
        if (depends.nonEmpty)
          dependencies.put(td.typeName.toString, depends.map(_.typeName.toString).toArray)
      case _ => ()
    }
  }

  /* Find a package/platform type. For general needs don't call this direct, use TypeRequest which will delegate here
   * if needed. This is the fallback handling for the TypeFinder which performs local searching for types, so this is
   * only useful if you know local searching is not required. */
  def findType(typeName: TypeName, from: TypeDeclaration): TypeResponse = {
    findType(typeName, Some(from))
  }

  def findType(typeName: TypeName): TypeResponse = {
    findType(typeName, None)
  }

  private def findType(typeName: TypeName, from: Option[TypeDeclaration]): TypeResponse = {
    if (namespace.nonEmpty) {
      val td = findPackageType(typeName.withTail(TypeName(namespace.get)), from).map(Right(_))
      if (td.nonEmpty)
        return td.get
    }

    val td = findPackageType(typeName, from).map(Right(_))
    if (td.nonEmpty)
      return td.get

    // From may be used to locate type variable types so must be accurate even for a platform type request
    from.map(TypeResolver.platformType(typeName, _)).orElse(Some(TypeResolver.platformTypeOnly(typeName, this))).get
  }

  // Find locally, or fallback to a searching base packages
  def findPackageType(typeName: TypeName, from: Option[TypeDeclaration], inPackage: Boolean = true): Option[TypeDeclaration] = {
    // Might be an outer in this module
    var declaration = findModuleType(typeName)
    if (declaration.nonEmpty) {
      if (inPackage || declaration.get.modifiers.contains(GLOBAL_MODIFIER))
        return declaration
      else
        return None
    }

    // Or maybe an inner
    if (typeName.outer.nonEmpty) {
      declaration = findPackageType(typeName.outer.get, from, inPackage = inPackage)
        .flatMap(_.findNestedType(typeName.name).filter(td => td.isExternallyVisible || inPackage))
      if (declaration.nonEmpty)
        return declaration
    }

    if (declaration.nonEmpty) {
      upsertMetadata(declaration.get)
      return declaration
    }

    // Try base modules & packages of this module
    baseModules.view
      .flatMap(_.findPackageType(typeName, from))
      .headOption
      .orElse(
        basePackages.view
          .flatMap(pkg => pkg.modules.headOption.flatMap(_.findPackageType(typeName, from, inPackage = false)))
          .headOption)
  }

  /** Find a type just in this module. */
  def findModuleType(typeName: TypeName): Option[TypeDeclaration] = {
    // Use aliased type name here so we don't mishandle an ambiguous typename when searching
    val targetType = TypeNames.aliasOrReturn(typeName)

    // Direct hit
    var declaration = types.get(targetType)
    if (declaration.nonEmpty)
      return declaration

    // SObject and alike, we want module specific version of these
    declaration = types.get(targetType.withTail(TypeNames.Schema))
    if (declaration.nonEmpty)
      return declaration

    if (targetType.params.isEmpty && (targetType.outer.isEmpty || targetType.outer.contains(TypeNames.Schema))) {
      val encName = EncodedName(targetType.name).defaultNamespace(namespace)
      if (encName.ext.nonEmpty) {
        return types.get(TypeName(encName.fullName, Nil, Some(TypeNames.Schema)))
      }
    }
    None
  }

  def refreshInternal(existingLabels: LabelDeclaration): Seq[(TypeId, Set[TypeId])] = {
    val newLabels = createLabelDeclaration()
    val holders = existingLabels.getTypeDependencyHolders
    newLabels.updateTypeDependencyHolders(holders)
    replaceType(newLabels.typeName, Some(newLabels))
    newLabels.validate()
    Seq( (newLabels.typeId, holders.toSet) )
  }

  /* Replace a path, returns the TypeId of the type that was updated and a Set of TypeIds for the dependency
   * holders of that type. */
  def refreshInternal(path: PathLike): Seq[(TypeId, Set[TypeId])] = {
    PlatformTypes.withLoadingObserver(schemaSObjectType) {

      checkPathInPackageOrThrow(path)
      val doc = MetadataDocument(path).getOrElse(
        throw new IllegalArgumentException(s"Metadata type is not supported for '$path'"))
      val sourceOpt = resolveSource(path)
      val typeId = TypeId(this, doc.typeName(namespace))

      // Update internal document tracking
      index.upsert(pkg.org.issueManager, doc)
      if (sourceOpt.isEmpty)
        index.remove(doc)

      // Clear errors as might fail to create type, SObjects are handled later due to multiple files
      pkg.org.issueManager.pop(doc.path)

      // Create type & forward holders to limit need for invalidation chaining
      val newTypes = createTypes(doc, sourceOpt)
      if (newTypes.nonEmpty) {
        newTypes.map(newType => {
          val existingType = getDependentType(newType.typeName)
          val holders = existingType
            .map(_.getTypeDependencyHolders)
            .getOrElse(DependentType.emptyTypeDependencyHolders)
          newType.updateTypeDependencyHolders(holders)

          // Update and validate
          replaceType(newType.typeName, Some(newType))
          newType.validate()
          (typeId, holders.toSet)
        })
      } else {
        val existingType = getDependentType(typeId.typeName)
        val holders = existingType
          .map(_.getTypeDependencyHolders)
          .getOrElse(DependentType.emptyTypeDependencyHolders)
        removeTypes(doc)
        Seq((typeId, holders.toSet))
      }
    }
  }

  private def getDependentType(typeName: TypeName): Option[DependentType] = {
    types
      .get(typeName)
      .flatMap {
        case dt: DependentType => Some(dt)
        case _                 => None
      }
  }

  private def createLabelDeclaration(): LabelDeclaration ={
    val events = LabelGenerator.iterator(index)
    val stream = new PackageStream(ArraySeq.unsafeWrapArray(events.toArray))
    LabelDeclaration(this).merge(stream)
  }

  private def createTypes(doc: MetadataDocument, source: Option[SourceData]): Seq[DependentType] = {
    doc match {
      case doc: ApexClassDocument =>
        source.flatMap(s => FullDeclaration.create(this, doc, s, forceConstruct = true)).toSeq
      case _: ApexTriggerDocument =>
        source.flatMap(s => TriggerDeclaration.create(this, doc.path, s)).toSeq
      case doc: SObjectLike =>
        if (doc.path.toString.endsWith("object-meta.xml"))
          refreshSObject(doc.path.parent)
        else
          refreshSObject(doc.path)
      case _: SObjectFieldDocument | _: SObjectFieldSetDocument | _: SObjectSharingReasonDocument =>
        val sObjectDir = doc.path.parent.parent
        MetadataDocument(sObjectDir.join(s"${sObjectDir.basename}.object-meta.xml")) match {
          case Some(_: SObjectLike) => refreshSObject(sObjectDir)
          case _                        => Seq()
        }
      case _: LabelsDocument =>
        Seq(createLabelDeclaration())
      case _: PageDocument =>
        val events = PageGenerator.iterator(index)
        val stream = new PackageStream(ArraySeq.unsafeWrapArray(events.toArray))
        Seq(PageDeclaration(this).merge(stream))
      case _: ComponentDocument =>
        val events = ComponentGenerator.iterator(index)
        val stream = new PackageStream(ArraySeq.unsafeWrapArray(events.toArray))
        Seq(ComponentDeclaration(this).merge(stream))
      case _: FlowDocument =>
        val events = FlowGenerator.iterator(index)
        val stream = new PackageStream(ArraySeq.unsafeWrapArray(events.toArray))
        Seq(InterviewDeclaration(this).merge(stream))
    }
  }

  private def removeTypes(doc: MetadataDocument): Unit = {
    doc match {
      case doc: SObjectDocument =>
        if (doc.path.toString.endsWith("object-meta.xml"))
          removeSObjectTypes(doc.path.parent.basename)
        else
          removeSObjectTypes(doc.path.basename.replaceFirst("\\.object$", ""))
      case _: SObjectFieldDocument | _: SObjectFieldSetDocument | _: SObjectSharingReasonDocument =>
        val sObjectDir = doc.path.parent.parent
        removeSObjectTypes(sObjectDir.basename)
      case _ => types.remove(doc.typeName(namespace))
    }
  }

  private def removeSObjectTypes(sobjectName: String): Unit = {
    val name = EncodedName(sobjectName)
    if (name.ext.contains(Name("c"))) {
      val typeName = TypeName(name.fullName, Nil, Some(TypeNames.Schema))
      val objectNames = Seq(typeName,
                            typeName.withNameReplace("__c$", "__Share"),
                            typeName.withNameReplace("__c$", "__Feed"),
                            typeName.withNameReplace("__c$", "__History"))
      objectNames.foreach(typeName => schemaSObjectType.remove(typeName.name))
      objectNames.foreach(types.remove)
    }
  }

  private def refreshSObject(sObjectPath: PathLike): Seq[DependentType] = {
    if (sObjectPath.exists) {
      clearSObjectErrors(sObjectPath)
      val deployer = new SObjectDeployer(this)
      val sobjects = deployer.createSObjects(
        SObjectGenerator.iterator(DocumentIndex(pkg.org.issueManager, namespace, sObjectPath)).buffered)

      sobjects.foreach(sobject => schemaSObjectType.add(sobject.typeName.name, hasFieldSets = true))
      sobjects.toIndexedSeq
    } else {
      Seq()
    }
  }

  private def clearSObjectErrors(path: PathLike): Unit = {
    if (!path.isDirectory) {
      pkg.org.issueManager.pop(path)
    } else {
      val (files, directories) = path.splitDirectoryEntries()
      files.foreach(file => pkg.org.issueManager.pop(file))
      directories.foreach(clearSObjectErrors)
    }
  }

  private def checkPathInPackageOrThrow(path: PathLike): Unit = {
    if (!index.isVisibleFile(path))
      throw new IllegalArgumentException(s"Metadata is not part of this package for '$path'")
  }

  private def resolveSource(path: PathLike): Option[SourceData] = {
    path.readSourceData() match {
      case Left(_)     => None
      case Right(data) => Some(data)
    }
  }
}
