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

package com.nawforce.apexlink.types.schema

import com.nawforce.apexlink.cst.VerifyContext
import com.nawforce.apexlink.finding.TypeResolver
import com.nawforce.apexlink.finding.TypeResolver.TypeCache
import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.org.Module
import com.nawforce.apexlink.types.core._
import com.nawforce.apexlink.types.platform.PlatformTypes
import com.nawforce.apexlink.types.synthetic.{CustomMethodDeclaration, CustomParameterDeclaration}
import com.nawforce.pkgforce.documents._
import com.nawforce.pkgforce.modifiers._
import com.nawforce.pkgforce.names.{Name, TypeName}
import com.nawforce.pkgforce.parsers.{CLASS_NATURE, Nature}
import com.nawforce.pkgforce.path.{Location, PathLike, PathLocation, UnsafeLocatable}
import com.nawforce.pkgforce.stream.{HierarchyCustomSetting, ListCustomSetting, SObjectEvent}

import scala.collection.immutable.ArraySeq
import scala.collection.mutable
import scala.util.hashing.MurmurHash3

sealed abstract class SObjectNature(val nature: String, val extendable: Boolean) {
  override def toString: String = nature
}

case object ListCustomSettingNature extends SObjectNature("List Custom Setting", extendable = true)

case object HierarchyCustomSettingsNature
    extends SObjectNature("Hierarchy Custom Setting", extendable = true)

case object CustomObjectNature extends SObjectNature("Custom Object", extendable = true)

case object PlatformEventNature extends SObjectNature("Platform Event", extendable = false)

case object CustomMetadataNature extends SObjectNature("Custom Metadata", extendable = false)

case object BigObjectNature extends SObjectNature("Big Object", extendable = false)

// Special case used for standard objects which need an SObjectDeclaration due to a lookups from elsewhere
case object PlatformObjectNature extends SObjectNature("Platform Object", extendable = true)

object SObjectNature {
  def apply(doc: MetadataDocument, event: SObjectEvent): SObjectNature = {
    doc match {
      case _: CustomMetadataDocument => CustomMetadataNature
      case _: BigObjectDocument      => BigObjectNature
      case _: PlatformEventDocument  => PlatformEventNature
      case _: SObjectDocument =>
        event.customSettingsType match {
          case Some(ListCustomSetting)      => ListCustomSettingNature
          case Some(HierarchyCustomSetting) => HierarchyCustomSettingsNature
          case _                            => CustomObjectNature
        }
    }
  }
}

trait SObjectLikeDeclaration extends DependentType

final case class SObjectDeclaration(
  sources: Array[SourceInfo],
  module: Module,
  typeName: TypeName,
  sobjectNature: SObjectNature,
  fieldSets: ArraySeq[Name],
  sharingReasons: ArraySeq[Name],
  baseFields: ArraySeq[FieldDeclaration],
  _isComplete: Boolean,
  isSynthetic: Boolean = false
) extends SObjectLikeDeclaration
    with SObjectFieldFinder
    with SObjectMethods
    with UnsafeLocatable
    with DependencyHolder
    with Dependent {

  override def location: PathLocation            = sources.headOption.map(_.location).orNull
  override val inTest: Boolean                   = false
  override val moduleDeclaration: Option[Module] = Some(module)
  override lazy val isComplete: Boolean          = _isComplete

  override val paths: ArraySeq[PathLike] =
    ArraySeq.unsafeWrapArray(sources.map(source => source.location.path))
  val sourceHash: Int = MurmurHash3.unorderedHash(sources.map(_.hash), 0)
  private val depends = mutable.Set[Dependent]()

  override val name: Name                      = typeName.name
  override val outerTypeName: Option[TypeName] = None
  override val nature: Nature                  = CLASS_NATURE
  override val modifiers: ArraySeq[Modifier]   = SObjectDeclaration.globalModifiers
  override val interfaces: ArraySeq[TypeName]  = ArraySeq()

  override def nestedTypes: ArraySeq[TypeDeclaration] = TypeDeclaration.emptyTypeDeclarations

  override val constructors: ArraySeq[ConstructorDeclaration] =
    ConstructorDeclaration.emptyConstructorDeclarations
  override val blocks: ArraySeq[BlockDeclaration] = BlockDeclaration.emptyBlockDeclarations

  override val superClass: Option[TypeName] = {
    Some(TypeNames.SObject)
  }

  override lazy val superClassDeclaration: Option[TypeDeclaration] =
    TypeResolver(superClass.get, this).toOption

  override def validate(): Unit = {
    def updateDependencies(typeName: TypeName): Unit = {
      TypeResolver(typeName, module) match {
        case Right(d: Dependent) => addDependency(d)
        case _                   => ()
      }
      typeName.params.foreach(updateDependencies)
    }

    // Crate dependencies on field types, can be ignored for Feed, Share & History synthetic SObjects
    if (!isSynthetic) {
      fields.map(_.typeName).toSet.foreach(updateDependencies)
      propagateOuterDependencies(new TypeCache())
    }
  }

  def addDependency(dependent: Dependent): Unit = depends.add(dependent)

  override def dependencies(): Iterable[Dependent] = depends

  override def gatherDependencies(
    dependsOn: mutable.Set[TypeId],
    apexOnly: Boolean,
    outerTypesOnly: Boolean,
    typeCache: TypeCache
  ): Unit = {
    DependentType.dependentsToTypeIds(module, depends, apexOnly, outerTypesOnly, dependsOn)
  }

  override val fields: ArraySeq[FieldDeclaration] = baseFields

  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    findFieldSObject(name, staticContext)
  }

  override val methods: ArraySeq[MethodDeclaration] = MethodDeclaration.emptyMethodDeclarations

  override def findMethod(
    name: Name,
    params: ArraySeq[TypeName],
    staticContext: Option[Boolean],
    verifyContext: VerifyContext
  ): Either[String, MethodDeclaration] = {
    if (staticContext.contains(true)) {
      val customMethods = sobjectNature match {
        case HierarchyCustomSettingsNature => hierarchyCustomSettingsMethods
        case ListCustomSettingNature       => listCustomSettingsMethods
        case _                             => SObjectDeclaration.sObjectMethodMap
      }
      val customMethod = customMethods.get((name, params.length))
      if (customMethod.nonEmpty)
        return Right(customMethod.get)
    }
    defaultFindMethod(name, params, staticContext, verifyContext)
  }

  private lazy val hierarchyCustomSettingsMethods: Map[(Name, Int), MethodDeclaration] =
    Seq(
      CustomMethodDeclaration(
        Location.empty,
        Name("getInstance"),
        typeName,
        CustomMethodDeclaration.emptyParameters
      ),
      CustomMethodDeclaration(
        Location.empty,
        Name("getInstance"),
        typeName,
        ArraySeq(CustomParameterDeclaration(Name("Id"), TypeNames.IdType))
      ),
      CustomMethodDeclaration(
        Location.empty,
        Name("getOrgDefaults"),
        typeName,
        CustomMethodDeclaration.emptyParameters
      ),
      CustomMethodDeclaration(
        Location.empty,
        Name("getValues"),
        typeName,
        ArraySeq(CustomParameterDeclaration(Name("Id"), TypeNames.IdType))
      )
    ).map(m => ((m.name, m.parameters.length), m)).toMap

  private lazy val listCustomSettingsMethods: Map[(Name, Int), MethodDeclaration] =
    Seq(
      CustomMethodDeclaration(
        Location.empty,
        Name("getAll"),
        TypeNames.mapOf(TypeNames.String, typeName),
        CustomMethodDeclaration.emptyParameters
      ),
      CustomMethodDeclaration(
        Location.empty,
        Name("getInstance"),
        typeName,
        CustomMethodDeclaration.emptyParameters
      ),
      CustomMethodDeclaration(
        Location.empty,
        Name("getInstance"),
        typeName,
        ArraySeq(CustomParameterDeclaration(Name("Name"), TypeNames.String))
      ),
      CustomMethodDeclaration(
        Location.empty,
        Name("getValues"),
        typeName,
        ArraySeq(CustomParameterDeclaration(Name("Name"), TypeNames.String))
      )
    ).map(m => ((m.name, m.parameters.length), m)).toMap
}

object SObjectDeclaration {
  val globalModifiers: ArraySeq[Modifier] = ArraySeq(GLOBAL_MODIFIER)

  lazy val sObjectMethodMap: Map[(Name, Int), MethodDeclaration] =
    PlatformTypes.sObjectType.methods.map(m => ((m.name, m.parameters.length), m)).toMap
}
