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

package com.nawforce.apexlink.types.extended

import com.nawforce.apexlink.cst.ClassDeclaration
import com.nawforce.apexlink.finding.TypeResolver
import com.nawforce.apexlink.names.TypeNames.TypeNameUtils
import com.nawforce.apexlink.org.Module
import com.nawforce.apexlink.types.core._
import com.nawforce.pkgforce.modifiers.Modifier
import com.nawforce.pkgforce.names.{Name, TypeName}
import com.nawforce.pkgforce.parsers.Nature
import com.nawforce.pkgforce.path.{PathLike, PathLocation}

import scala.collection.immutable.ArraySeq

class GenericTypeDeclaration(module: Module, override val typeName: TypeName, val baseType: ClassDeclaration) extends TypeDeclaration {

  baseType.addDependencyHolder(this)

  override def paths: Array[PathLike] = baseType.paths

  override def isComplete: Boolean = baseType.isComplete

  override def dependencies(): Iterable[Dependent] = Iterable(baseType)

  override val outerTypeName: Option[TypeName] = None
  override val modifiers: ArraySeq[Modifier] = baseType.modifiers
  override val moduleDeclaration: Option[Module] = Some(module)
  override val name: Name = typeName.name
  override val nature: Nature = baseType.nature
  override val blocks: Array[BlockDeclaration] = Array()

  override def validate(): Unit = {}

  private val paramsMap: Map[Name, TypeName] = {
    baseType.typeArguments
      .zip(typeName.params)
      .map(p => (p._1.name, p._2))
      .toMap
  }

  override lazy val superClass: Option[TypeName] = baseType.superClass.map(replaceParams)

  override lazy val superClassDeclaration: Option[TypeDeclaration] =
    superClass.flatMap(typeName => TypeResolver(typeName, module).toOption)

  override lazy val interfaces: Array[TypeName] = baseType.interfaces.map(replaceParams)

  override lazy val interfaceDeclarations: Array[TypeDeclaration] =
    interfaces.flatMap(typeName => TypeResolver(typeName, module).toOption)

  override def nestedTypes: Array[TypeDeclaration] = Array()

  override lazy val fields: Array[FieldDeclaration] = {
    baseType.fields.map(f =>
      if (f.isStatic)
        f
      else
        new GenericField(f, this))
  }

  override lazy val constructors: ArraySeq[ConstructorDeclaration] = {
    baseType.constructors.map(c => new GenericConstructor(c, this))
  }

  override lazy val methods: Array[MethodDeclaration] = {
    baseType.methods.map(m =>
      if (m.isStatic)
        m
      else
        new GenericMethod(m, this))
  }

  def replaceParams(typeName: TypeName): TypeName = {
    typeName.withParams(typeName.params.map(p => paramsMap.getOrElse(p.name, p)))
  }

  def mapTypeName(typeName: TypeName): TypeName = {
    if (typeName.outer.isEmpty || typeName.outer.get == baseType.typeName) {
      paramsMap.getOrElse(typeName.name, typeName)
    } else {
      typeName.withParams(typeName.params.map(mapTypeName))
    }
  }
}

class GenericField(field: FieldDeclaration, owningTypeDeclaration: GenericTypeDeclaration)
  extends FieldDeclaration {

  override def location: PathLocation = null

  override val name: Name = field.name
  override val modifiers: ArraySeq[Modifier] = field.modifiers
  override val readAccess: Modifier = field.readAccess
  override val writeAccess: Modifier = field.writeAccess
  override val idTarget: Option[TypeName] = None

  override lazy val typeName: TypeName = owningTypeDeclaration.mapTypeName(field.typeName)
}

class GenericConstructor(constructor: ConstructorDeclaration, owningTypeDeclaration: GenericTypeDeclaration)
  extends ConstructorDeclaration {

  //override lazy val name: Name = constructor.name
  override lazy val modifiers: ArraySeq[Modifier] = constructor.modifiers

  override lazy val parameters: ArraySeq[ParameterDeclaration] =
    constructor.parameters.map(p => new GenericParameter(p, owningTypeDeclaration))
}

class GenericMethod(method: MethodDeclaration, owningTypeDeclaration: GenericTypeDeclaration)
  extends MethodDeclaration {

  override lazy val name: Name = method.name
  override lazy val modifiers: ArraySeq[Modifier] = method.modifiers

  override lazy val typeName: TypeName = owningTypeDeclaration.mapTypeName(method.typeName)

  override lazy val parameters: ArraySeq[ParameterDeclaration] = {
    method.parameters
      .map(p => new GenericParameter(p, owningTypeDeclaration))
  }

  override val hasBlock: Boolean = false
}

class GenericParameter(parameter: ParameterDeclaration, owningTypeDeclaration: GenericTypeDeclaration)
  extends ParameterDeclaration {

  override lazy val name: Name = parameter.name
  override lazy val typeName: TypeName = owningTypeDeclaration.mapTypeName(parameter.typeName)
}
