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
import com.nawforce.pkgforce.diagnostics.Location
import com.nawforce.pkgforce.documents.SourceInfo
import com.nawforce.pkgforce.modifiers._
import com.nawforce.pkgforce.names.{Name, TypeName}
import com.nawforce.pkgforce.path.PathLike

import scala.collection.mutable
import scala.util.hashing.MurmurHash3

sealed abstract class SObjectNature(val nature: String) {
  override def toString: String = nature
}

case object ListCustomSettingNature extends SObjectNature("List")
case object HierarchyCustomSettingsNature extends SObjectNature("Hierarchy")
case object CustomObjectNature extends SObjectNature("CustomObject")
case object CustomMetadataNature extends SObjectNature("CustomMetadata")
case object BigObjectNature extends SObjectNature("BigObject")
case object PlatformObjectNature extends SObjectNature("PlatformObject")
case object PlatformEventNature extends SObjectNature("PlatformEvent")

trait SObjectLikeDeclaration extends DependentType

final case class SObjectDeclaration(sources: Array[SourceInfo],
                                    module: Module,
                                    typeName: TypeName,
                                    sobjectNature: SObjectNature,
                                    fieldSets: Array[Name],
                                    sharingReasons: Array[Name],
                                    baseFields: Array[FieldDeclaration],
                                    _isComplete: Boolean,
                                    isSynthetic: Boolean = false)
    extends SObjectLikeDeclaration
    with SObjectFieldFinder
    with SObjectMethods {

  override val moduleDeclaration: Option[Module] = Some(module)
  override lazy val isComplete: Boolean = _isComplete

  override val paths: Array[PathLike] = sources.map(_.path)
  val sourceHash: Int = MurmurHash3.unorderedHash(sources.map(_.hash), 0)
  private val depends = mutable.Set[Dependent]()

  override val name: Name = typeName.name
  override val outerTypeName: Option[TypeName] = None
  override val nature: Nature = CLASS_NATURE
  override val modifiers: Array[Modifier] = SObjectDeclaration.globalModifiers
  override val interfaces: Array[TypeName] = TypeName.emptyTypeName
  override def nestedTypes: Array[TypeDeclaration] = TypeDeclaration.emptyTypeDeclarations
  override val constructors: Array[ConstructorDeclaration] =
    ConstructorDeclaration.emptyConstructorDeclarations
  override val blocks: Array[BlockDeclaration] = BlockDeclaration.emptyBlockDeclarations

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

  override def collectDependenciesByTypeName(dependsOn: mutable.Set[TypeId],
                                             apexOnly: Boolean,
                                             typeCache: TypeCache): Unit = {
    DependentType.dependentsToTypeIds(module, depends, apexOnly, dependsOn)
  }

  override val fields: Array[FieldDeclaration] = {
    if (sobjectNature == CustomObjectNature) {
      (PlatformTypes.customSObject.fields ++ baseFields).map(f => (f.name, f)).toMap.values.toArray
    } else {
      baseFields
    }
  }

  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    findFieldSObject(name, staticContext)
  }

  override val methods: Array[MethodDeclaration] = MethodDeclaration.emptyMethodDeclarations

  override def findMethod(name: Name,
                          params: Array[TypeName],
                          staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Option[MethodDeclaration] = {
    if (staticContext.contains(true)) {
      val customMethods = sobjectNature match {
        case HierarchyCustomSettingsNature => hierarchyCustomSettingsMethods
        case ListCustomSettingNature       => listCustomSettingsMethods
        case _                             => SObjectDeclaration.sObjectMethodMap
      }
      val customMethod = customMethods.get((name, params.length))
      if (customMethod.nonEmpty)
        return customMethod
    }
    defaultFindMethod(name, params, staticContext, verifyContext)
  }

  private lazy val hierarchyCustomSettingsMethods: Map[(Name, Int), MethodDeclaration] =
    Seq(CustomMethodDeclaration(Location.empty, Name("getInstance"), typeName, Array()),
        CustomMethodDeclaration(Location.empty,
                                Name("getInstance"),
                                typeName,
                                Array(CustomParameterDeclaration(Name("Id"), TypeNames.IdType))),
        CustomMethodDeclaration(Location.empty, Name("getOrgDefaults"), typeName, Array()),
        CustomMethodDeclaration(Location.empty,
                                Name("getValues"),
                                typeName,
                                Array(CustomParameterDeclaration(Name("Id"), TypeNames.IdType))),
    ).map(m => ((m.name, m.parameters.length), m)).toMap

  private lazy val listCustomSettingsMethods: Map[(Name, Int), MethodDeclaration] =
    Seq(CustomMethodDeclaration(Location.empty, Name("getAll"), TypeNames.mapOf(TypeNames.String, typeName), Array()),
        CustomMethodDeclaration(Location.empty, Name("getInstance"), typeName, Array()),
        CustomMethodDeclaration(Location.empty,
                                Name("getInstance"),
                                typeName,
                                Array(CustomParameterDeclaration(Name("Name"), TypeNames.String))),
        CustomMethodDeclaration(Location.empty,
                                Name("getValues"),
                                typeName,
                                Array(CustomParameterDeclaration(Name("Name"), TypeNames.String))),
    ).map(m => ((m.name, m.parameters.length), m)).toMap
}

object SObjectDeclaration {
  val globalModifiers: Array[Modifier] = Array(GLOBAL_MODIFIER)

  lazy val sObjectMethodMap: Map[(Name, Int), MethodDeclaration] =
    PlatformTypes.sObjectType.methods.map(m => ((m.name, m.parameters.length), m)).toMap
}
