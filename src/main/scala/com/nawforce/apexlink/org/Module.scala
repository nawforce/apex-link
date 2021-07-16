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

import com.nawforce.apexlink.cst.UnusedLog
import com.nawforce.apexlink.finding.TypeResolver.{TypeCache, TypeResponse}
import com.nawforce.apexlink.finding.{TypeFinder, TypeResolver}
import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.names.TypeNames.TypeNameUtils
import com.nawforce.apexlink.types.apex.{
  ApexClassDeclaration,
  ApexDeclaration,
  ApexFullDeclaration,
  FullDeclaration,
  TriggerDeclaration
}
import com.nawforce.apexlink.types.core.{DependentType, TypeDeclaration, TypeId}
import com.nawforce.apexlink.types.other.{InterviewDeclaration, _}
import com.nawforce.apexlink.types.platform.PlatformTypes
import com.nawforce.apexlink.types.schema.{SObjectDeclaration, SchemaSObjectType}
import com.nawforce.pkgforce.diagnostics.{IssueLog, LocalLogger}
import com.nawforce.pkgforce.documents._
import com.nawforce.pkgforce.modifiers.GLOBAL_MODIFIER
import com.nawforce.pkgforce.names.{EncodedName, Name, TypeIdentifier, TypeName}
import com.nawforce.pkgforce.path.PathLike
import com.nawforce.pkgforce.stream._
import com.nawforce.runtime.parsers.SourceData

import scala.collection.mutable

class Module(val pkg: PackageImpl, val index: DocumentIndex, dependents: Seq[Module]) extends TypeFinder {

  val namespace: Option[Name] = pkg.namespace
  val baseModules: Seq[Module] = dependents.reverse
  val basePackages: Seq[PackageImpl] = pkg.basePackages.reverse

  private val schemaManager = SchemaSObjectType(this)

  private[nawforce] var types = mutable.Map[TypeName, TypeDeclaration]()

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

  /** Check all summary types have propagated their dependencies */
  def propagateAllDependencies(): Unit = {
    types.values.foreach({
      case ad: ApexClassDeclaration => ad.propagateAllDependencies()
      case _                        => ()
    })
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
  def packageType(typeName: TypeName): Option[TypeDeclaration] = {
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
    types.remove(td.typeName)
  }

  /** Obtain log with unused metadata warnings */
  def reportUnused(): IssueLog = {
    new UnusedLog(types.values)
  }

  // Add dependencies for Apex types to a map
  def populateDependencies(dependencies: java.util.Map[String, Array[String]]): Unit = {
    val typeCache = new TypeCache()
    types.values.foreach {
      case td: ApexClassDeclaration =>
        val depends = mutable.Set[TypeId]()
        td.collectDependenciesByTypeName(depends, apexOnly = false, typeCache)
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

    var td = findPackageType(typeName).map(Right(_))
    if (td.nonEmpty)
      return td.get

    if (namespace.nonEmpty) {
      td = findPackageType(typeName.withTail(TypeName(namespace.get))).map(Right(_))
      if (td.nonEmpty)
        return td.get
    }

    // From may be used to locate type variable types so must be accurate even for a platform type request
    from.map(TypeResolver.platformType(typeName, _)).orElse(Some(TypeResolver.platformTypeOnly(typeName, this))).get
  }

  // Find locally, or fallback to a searching base packages
  def findPackageType(typeName: TypeName, inPackage: Boolean = true): Option[TypeDeclaration] = {
    var declaration = findModuleType(typeName)
    if (declaration.nonEmpty) {
      if (inPackage || declaration.get.modifiers.contains(GLOBAL_MODIFIER))
        return declaration
      else
        return None
    }

    if (typeName.outer.nonEmpty) {
      declaration = findPackageType(typeName.outer.get, inPackage = inPackage)
        .flatMap(_.findNestedType(typeName.name).filter(td => td.isExternallyVisible || inPackage))
      if (declaration.nonEmpty)
        return declaration
    }

    baseModules.view
      .flatMap(_.findPackageType(typeName))
      .headOption
      .orElse(
        basePackages.view
          .flatMap(pkg => pkg.modules.headOption.flatMap(_.findPackageType(typeName, inPackage = false)))
          .headOption)
  }

  /** Find a type just in this module. */
  def findModuleType(typeName: TypeName): Option[TypeDeclaration] = {
    var declaration = types.get(typeName)
    if (declaration.nonEmpty)
      return declaration

    declaration = types.get(typeName.withTail(TypeNames.Schema))
    if (declaration.nonEmpty)
      return declaration

    if (typeName.params.isEmpty && (typeName.outer.isEmpty || typeName.outer.contains(TypeNames.Schema))) {
      val encName = EncodedName(typeName.name).defaultNamespace(namespace)
      if (encName.ext.nonEmpty) {
        return types.get(TypeName(encName.fullName, Nil, Some(TypeNames.Schema)))
      }
    }
    None
  }

  /* Replace a path, returns the TypeId of the type that was updated and a Set of TypeIds for the dependency
   * holders of that type. */
  def refreshInternal(path: PathLike): Seq[(TypeId, Set[TypeId])] = {
    PlatformTypes.withLoadingObserver(schemaSObjectType) {

      checkPathInPackageOrThrow(path)
      val dt = MetadataDocument(path).getOrElse(
        throw new IllegalArgumentException(s"Metadata type is not supported for '$path'"))
      val sourceOpt = resolveSource(path)
      val typeId = TypeId(this, dt.typeName(namespace))

      // Update internal document tracking
      index.upsert(new LocalLogger(pkg.org.issues), dt)
      if (sourceOpt.isEmpty)
        index.remove(dt)

      // Clear errors as might fail to create type
      pkg.org.issues.pop(dt.path.toString)

      // Create type & forward holders to limit need for invalidation chaining
      val newTypes = createTypes(dt, sourceOpt)
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
        types.remove(typeId.typeName)
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

  private def createTypes(dt: MetadataDocument, source: Option[SourceData]): Seq[DependentType] = {
    dt match {
      case doc: ExtendedApexDocument =>
        source.flatMap(s => FullDeclaration.create(this, doc, s, extendedApex = false, forceConstruct = false)).toSeq
      case doc: ApexClassDocument =>
        source.flatMap(s => FullDeclaration.create(this, doc, s, extendedApex = false, forceConstruct = false)).toSeq
      case _: ApexTriggerDocument =>
        source.flatMap(s => TriggerDeclaration.create(this, dt.path, s)).toSeq
      case doc: SObjectDocument =>
        refreshSObject(doc.path.parent)
      case _: SObjectFieldDocument | _: SObjectFieldSetDocument | _: SObjectSharingReasonDocument =>
        val sObjectDir = dt.path.parent.parent
        MetadataDocument(sObjectDir.join(s"${sObjectDir.basename}.object-meta.xml")) match {
          case Some(_: SObjectDocument) => refreshSObject(sObjectDir)
          case _                        => Seq()
        }
      case _: LabelsDocument =>
        val events = LabelGenerator.iterator(index)
        val stream = new PackageStream(events.toArray)
        Seq(LabelDeclaration(this).merge(stream))
      case _: PageDocument =>
        val events = PageGenerator.iterator(index)
        val stream = new PackageStream(events.toArray)
        Seq(PageDeclaration(this).merge(stream))
      case _: ComponentDocument =>
        val events = ComponentGenerator.iterator(index)
        val stream = new PackageStream(events.toArray)
        Seq(ComponentDeclaration(this).merge(stream))
      case _: FlowDocument =>
        val events = FlowGenerator.iterator(index)
        val stream = new PackageStream(events.toArray)
        Seq(InterviewDeclaration(this).merge(stream))
    }
  }

  private def refreshSObject(sObjectPath: PathLike): Seq[DependentType] = {
    val deployer = new SObjectDeployer(this)
    val sobjects = deployer.createSObjects(
      SObjectGenerator.iterator(DocumentIndex(new LocalLogger(pkg.org.issues), namespace, sObjectPath)).buffered)

    sobjects.foreach(sobject => schemaSObjectType.add(sobject.typeName.name, hasFieldSets = true))
    sobjects.toIndexedSeq
  }

  private def getFullDeclaration(dt: MetadataDocument): Option[ApexFullDeclaration] = {
    types
      .get(dt.typeName(namespace))
      .flatMap {
        case fd: FullDeclaration    => Some(fd)
        case td: TriggerDeclaration => Some(td)
        case _                      => None
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
