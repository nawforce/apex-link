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

package com.nawforce.apexlink.types.platform

import com.nawforce.apexlink.finding.TypeResolver.TypeResponse
import com.nawforce.apexlink.finding.{MissingType, TypeError, TypeResolver}
import com.nawforce.apexlink.names.TypeNames.TypeNameUtils
import com.nawforce.apexlink.types.core.{
  FieldDeclaration,
  MethodDeclaration,
  ParameterDeclaration,
  TypeDeclaration
}
import com.nawforce.pkgforce.modifiers.Modifier
import com.nawforce.pkgforce.names.{Name, TypeName}
import com.nawforce.pkgforce.path.PathLocation

import scala.collection.immutable.ArraySeq

/* Wrapper for the few generic types we support, this specialises the methods of the type so that
 * List<T> presents as say a List<Foo>.
 */
class GenericPlatformTypeDeclaration(_typeName: TypeName, genericDecl: PlatformTypeDeclaration)
    extends PlatformTypeDeclaration(genericDecl.native, genericDecl.outer) {

  override lazy val typeName: TypeName = _typeName

  private val paramsMap: Map[Name, TypeName] = {
    genericDecl.typeName.params
      .zip(typeName.params)
      .map(p => (p._1.name, p._2))
      .toMap
  }

  override lazy val superClass: Option[TypeName] = getSuperClass.map(replaceParams)

  override lazy val superClassDeclaration: Option[TypeDeclaration] = {
    superClass.flatMap(sc => PlatformTypes.get(sc, None).toOption)
  }

  override lazy val interfaces: ArraySeq[TypeName] = getInterfaces.map(replaceParams)

  override lazy val interfaceDeclarations: ArraySeq[TypeDeclaration] = {
    getInterfaces.flatMap(id => PlatformTypes.get(replaceParams(id), None).toOption)
  }

  override lazy val fields: ArraySeq[FieldDeclaration] = {
    getFields.map(f => new GenericPlatformField(f, this))
  }

  override lazy val methods: ArraySeq[MethodDeclaration] = {
    getMethods.map(m => new GenericPlatformMethod(m, this))
  }

  def replaceParams(typeName: TypeName): TypeName = {
    typeName.withParams(typeName.params.map(p => paramsMap.getOrElse(p.name, p)))
  }

  def getTypeVariable(typeName: TypeName): Option[TypeName] = {
    // Type vars should not really be modeled as TypeNames but they are rare and this is easier
    if (typeName.params.isEmpty && typeName.outer.isEmpty)
      paramsMap.get(typeName.name)
    else
      None
  }
}

class GenericPlatformField(
  platformField: PlatformField,
  _typeDeclaration: GenericPlatformTypeDeclaration
) extends FieldDeclaration {

  override def location: PathLocation        = null
  override val name: Name                    = platformField.name
  override val modifiers: ArraySeq[Modifier] = platformField.modifiers
  override val readAccess: Modifier          = platformField.readAccess
  override val writeAccess: Modifier         = platformField.writeAccess
  override val idTarget: Option[TypeName]    = None

  override lazy val typeName: TypeName = {
    val fieldType = _typeDeclaration.replaceParams(platformField.getGenericTypeName)
    _typeDeclaration.getTypeVariable(fieldType).getOrElse(fieldType)
  }
}

class GenericPlatformMethod(
  platformMethod: PlatformMethod,
  _typeDeclaration: GenericPlatformTypeDeclaration
) extends MethodDeclaration {

  override lazy val name: Name                    = platformMethod.name
  override lazy val modifiers: ArraySeq[Modifier] = platformMethod.modifiers

  override lazy val typeName: TypeName = {
    val paramType = _typeDeclaration.replaceParams(platformMethod.getGenericTypeName)
    _typeDeclaration.getTypeVariable(paramType).getOrElse(paramType)
  }

  override lazy val parameters: ArraySeq[ParameterDeclaration] =
    platformMethod.getParameters
      .collect { case p: PlatformParameter => p }
      .map(p => new GenericPlatformParameter(p, _typeDeclaration))

  override val hasBlock: Boolean = false
}

class GenericPlatformParameter(
  platformParameter: PlatformParameter,
  _typeDeclaration: GenericPlatformTypeDeclaration
) extends ParameterDeclaration {

  override lazy val name: Name = platformParameter.name
  override lazy val typeName: TypeName = {
    val paramType = _typeDeclaration.replaceParams(platformParameter.getGenericTypeName)
    _typeDeclaration.getTypeVariable(paramType).getOrElse(paramType)
  }
}

object GenericPlatformTypeDeclaration {

  /* Get a generic type, in general don't call this direct, use TypeRequest which will delegate here if
   * needed. Implicit in this model is that all generics are currently platform types, hopefully that
   * won't be true forever.
   */
  def get(typeName: TypeName, from: Option[TypeDeclaration]): TypeResponse = {
    // Make sure params are resolvable first
    val params = typeName.params.map(
      pt =>
        (
          pt,
          // Without a 'from' we can only search for platform types, but this is still needed for handling platform types
          if (from.nonEmpty) TypeResolver(pt, from.get)
          else PlatformTypes.get(pt, None)
        )
    )
    val module       = from.flatMap(_.moduleDeclaration)
    val failedParams = params.find(_._2.isLeft).filterNot(p => module.exists(_.isGhostedType(p._1)))
    if (failedParams.nonEmpty) {
      return Left(MissingType(failedParams.get._1))
    }

    // And then create off base type
    val genericDecl = PlatformTypeDeclaration.getDeclaration(typeName.asDotName)
    if (genericDecl.nonEmpty) {
      val absoluteParamTypes = params.map(
        p =>
          p._2 match {
            case Left(error: TypeError)            => error.typeName
            case Right(paramType: TypeDeclaration) => paramType.typeName
          }
      )
      Right(
        new GenericPlatformTypeDeclaration(typeName.withParams(absoluteParamTypes), genericDecl.get)
      )
    } else {
      Left(MissingType(typeName))
    }
  }
}
