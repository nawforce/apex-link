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

import com.nawforce.apexlink.api.ServerOps
import com.nawforce.apexlink.cst.UnusedLog
import com.nawforce.apexlink.finding.TypeResolver.TypeResponse
import com.nawforce.apexlink.finding.{TypeFinder, TypeResolver}
import com.nawforce.apexlink.names.{TypeNames, _}
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
import com.nawforce.apexlink.types.schema.SchemaManager
import com.nawforce.pkgforce.diagnostics.{IssueLog, LocalLogger, PathLocation}
import com.nawforce.pkgforce.documents._
import com.nawforce.pkgforce.modifiers.GLOBAL_MODIFIER
import com.nawforce.pkgforce.names.{EncodedName, Name, TypeName}
import com.nawforce.pkgforce.path.PathLike
import com.nawforce.pkgforce.stream._
import com.nawforce.runtime.parsers.SourceData

import scala.collection.mutable

class Module(val pkg: PackageImpl, val index: DocumentIndex, dependents: Seq[Module])
    extends TypeFinder {

  val namespace: Option[Name] = pkg.namespace
  val baseModules: Seq[Module] = dependents.reverse
  val basePackages: Seq[PackageImpl] = pkg.basePackages.reverse

  private val schemaManager = new SchemaManager(this)

  private[nawforce] var types = mutable.Map[TypeName, TypeDeclaration]()

  def freeze(): Unit = {
    // TODO: Have return types, currently can't be done because class loading code needs access to in-flight types
    new StreamDeployer(this, pkg.namespace, PackageStream.eventStream(index), types)
  }

  def typeCount: Int = types.size

  def schema(): SchemaManager = schemaManager

  def any: AnyDeclaration = findModuleType(TypeNames.Any).get.asInstanceOf[AnyDeclaration]
  def labels: LabelDeclaration = findModuleType(TypeNames.Label).get.asInstanceOf[LabelDeclaration]
  def interviews: InterviewDeclaration =
    findModuleType(TypeNames.Interview).get.asInstanceOf[InterviewDeclaration]
  def pages: PageDeclaration = findModuleType(TypeNames.Page).get.asInstanceOf[PageDeclaration]
  def components: ComponentDeclaration =
    findModuleType(TypeNames.Component).get.asInstanceOf[ComponentDeclaration]

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

  /* All available types */
  def getApexTypeIdentifiers: Set[String] = {
    types.values.collect { case ad: ApexDeclaration => ad }.map(_.typeName.toString()).toSet
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

  /* Find a document location for the type */
  def getTypeLocation(typeName: TypeName): Option[PathLocation] = {
    packageType(typeName) match {
      case Some(ad: ApexDeclaration) => Some(ad.nameLocation)
      case _                         => None
    }
  }

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
    types.values.foreach {
      case td: ApexClassDeclaration =>
        val depends = mutable.Set[TypeId]()
        td.collectDependenciesByTypeName(depends)
        depends.remove(td.typeId)
        if (depends.nonEmpty)
          dependencies.put(td.typeName.toString, depends.map(_.typeName.toString).toArray)
      case _ => ()
    }
  }

  /* Find a package/platform type. For general needs don't call this direct, use TypeRequest which will delegate here
   * if needed. This is the fallback handling for the TypeFinder which performs local searching for types, so this is
   * only useful if you know local searching is not required. */
  def findType(typeName: TypeName,
               from: Option[TypeDeclaration],
               excludeSObjects: Boolean = false): TypeResponse = {

    if (!excludeSObjects) {
      var td = findPackageType(typeName).map(Right(_))
      if (td.nonEmpty)
        return td.get

      if (namespace.nonEmpty) {
        td = findPackageType(typeName.withTail(TypeName(namespace.get))).map(Right(_))
        if (td.nonEmpty)
          return td.get
      }
    }

    // From may be used to locate type variable types so must be accurate even for a platform type request
    PlatformTypes.get(typeName, from, excludeSObjects)
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
          .flatMap(pkg =>
            pkg.modules.headOption.flatMap(_.findPackageType(typeName, inPackage = false)))
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

    if (typeName.params.isEmpty && (typeName.outer.isEmpty || typeName.outer.contains(
          TypeNames.Schema))) {
      val encName = EncodedName(typeName.name).defaultNamespace(namespace)
      if (encName.ext.nonEmpty) {
        return types.get(TypeName(encName.fullName, Nil, Some(TypeNames.Schema)))
      }
    }
    None
  }

  /* Replace a path, returns the TypeId of the type that was updated and a Set of TypeIds for the dependency
   * holders of that type. */
  def refreshInternal(path: PathLike): (TypeId, Set[TypeId]) = {
    checkPathInPackageOrThrow(path)
    val dt = getUpdateableDocumentTypeOrThrow(path)
    val sourceOpt = resolveSource(path)
    val typeId = TypeId(this, dt.typeName(namespace))

    // If we have source & it's not changed then ignore
    if (sourceOpt.exists(
          s => getFullDeclaration(dt).exists(fd => fd.path == path && fd.sourceHash == s.hash)))
      return (typeId, Set.empty)

    // Update internal document tracking
    index.upsert(new LocalLogger(pkg.org.issues), dt)
    if (sourceOpt.isEmpty)
      index.remove(dt)

    // Clear errors as might fail to create type
    pkg.org.issues.pop(dt.path.toString)

    // Create type & forward holders to limit need for invalidation chaining
    val newType = createType(dt, sourceOpt)
    val typeName = newType.map(_.typeName).getOrElse(dt.typeName(namespace))
    val existingType = getDependentType(typeName)
    val holders = existingType
      .map(_.getTypeDependencyHolders)
      .getOrElse(DependentType.emptyTypeDependencyHolders)
    newType.foreach(_.updateTypeDependencyHolders(holders))

    // Update and validate
    replaceType(typeName, newType)
    newType.foreach(_.validate())

    (typeId, holders.toSet)
  }

  private def getDependentType(typeName: TypeName): Option[DependentType] = {
    types
      .get(typeName)
      .flatMap {
        case dt: DependentType => Some(dt)
        case _                 => None
      }
  }

  private[nawforce] def deployClasses(documents: Seq[ApexClassDocument]): Unit = {
    OrgImpl.current.withValue(pkg.org) {
      val updated = documents.flatMap(doc => {
        val data = doc.path.readSourceData()
        val d = ServerOps.debugTime(s"Parsed ${doc.path}") {
          FullDeclaration.create(this, doc, data.getOrElse(throw new NoSuchElementException)).toSeq
        }
        d.foreach(upsertMetadata(_))
        d
      })

      updated.foreach(td => td.validate())
    }
  }

  private[nawforce] def findTypes(typeNames: Seq[TypeName]): Seq[TypeDeclaration] = {
    OrgImpl.current.withValue(pkg.org) {
      typeNames.flatMap(typeName => TypeResolver(typeName, this, excludeSObjects = false).toOption)
    }
  }

  private def createType(dt: UpdatableMetadata,
                         source: Option[SourceData]): Option[DependentType] = {
    dt match {
      case ad: ApexClassDocument =>
        source.flatMap(s => FullDeclaration.create(this, ad, s))
      case _: ApexTriggerDocument =>
        source.flatMap(s => TriggerDeclaration.create(this, dt.path, s))
      case _ =>
        Some(createOtherType(dt))
    }
  }

  private def createOtherType(dt: UpdatableMetadata): DependentType = {
    // TODO: This is tmp while we refactor index/stream usage
    dt match {
      case _: LabelsDocument =>
        val events = LabelGenerator.iterator(index)
        val stream = new PackageStream(events.toArray)
        LabelDeclaration(this).merge(stream)
      case _: PageDocument =>
        val events = PageGenerator.iterator(index)
        val stream = new PackageStream(events.toArray)
        PageDeclaration(this).merge(stream)
      case _: ComponentDocument =>
        val events = ComponentGenerator.iterator(index)
        val stream = new PackageStream(events.toArray)
        ComponentDeclaration(this).merge(stream)
      case _: FlowDocument =>
        val events = FlowGenerator.iterator(index)
        val stream = new PackageStream(events.toArray)
        InterviewDeclaration(this).merge(stream)
    }
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

  private def getUpdateableDocumentTypeOrThrow(path: PathLike): UpdatableMetadata = {
    (MetadataDocument(path) collect {
      case dt: UpdatableMetadata => dt
    }).getOrElse(throw new IllegalArgumentException(s"Metadata type is not supported for '$path'"))
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
