/*
 [The "BSD licence"]
 Copyright (c) 2019 Kevin Jones
 All rights reserved.

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

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package com.nawforce.common.types.platform

import com.nawforce.common.cst.Modifier
import com.nawforce.common.finding.TypeRequest.TypeRequest
import com.nawforce.common.finding.{MissingType, TypeError, TypeRequest}
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.types.{FieldDeclaration, MethodDeclaration, ParameterDeclaration, TypeDeclaration}
import com.nawforce.runtime.types.{PlatformField, PlatformMethod, PlatformParameter, PlatformTypeDeclaration}

import scala.collection.mutable

/* Wrapper for the few generic types we support, this specialises the methods of the type so that
 * List<T> presents as say a List<Foo>.
 */
class GenericPlatformTypeDeclaration(_typeName: TypeName, genericDecl: PlatformTypeDeclaration)
  extends PlatformTypeDeclaration(genericDecl.native, genericDecl.outer) {

  private val paramsMap: Map[Name, TypeName] = {
    genericDecl.typeName.params.zip(typeName.params)
      .map(p => (p._1.name, p._2)).toMap
  }

  override lazy val typeName: TypeName = _typeName

  override lazy val superClass: Option[TypeName] = getSuperClass.map(replaceParams)

  override lazy val superClassDeclaration: Option[TypeDeclaration] = {
    superClass.flatMap(sc => PlatformTypes.get(sc, None).toOption)
  }

  override lazy val interfaces: Seq[TypeName] = getInterfaces.map(replaceParams)

  override lazy val interfaceDeclarations: Seq[TypeDeclaration] = {
    getInterfaces.flatMap(id => PlatformTypes.get(replaceParams(id), None).toOption)
  }

  override lazy val fields: Seq[FieldDeclaration] = {
    getFields.map(f => new GenericPlatformField(f, this))
  }

  override lazy val methods: Seq[MethodDeclaration] = {
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

class GenericPlatformField(platformField: PlatformField, _typeDeclaration: GenericPlatformTypeDeclaration)
  extends FieldDeclaration {

  override val name: Name = platformField.name
  override val modifiers: Seq[Modifier] = platformField.modifiers
  override val readAccess: Modifier = platformField.readAccess
  override val writeAccess: Modifier = platformField.writeAccess

  override lazy val typeName: TypeName = {
    val fieldType = _typeDeclaration.replaceParams(platformField.getGenericTypeName)
    _typeDeclaration.getTypeVariable(fieldType).getOrElse(fieldType)
  }
}

class GenericPlatformMethod(platformMethod: PlatformMethod, _typeDeclaration: GenericPlatformTypeDeclaration)
  extends MethodDeclaration {

  override lazy val name: Name = platformMethod.name
  override lazy val modifiers: Seq[Modifier] = platformMethod.modifiers

  override lazy val typeName: TypeName = {
    val paramType = _typeDeclaration.replaceParams(platformMethod.getGenericTypeName)
    _typeDeclaration.getTypeVariable(paramType).getOrElse(paramType)
  }

  override lazy val parameters: Seq[ParameterDeclaration] =
    platformMethod.getParameters.map(p => new GenericPlatformParameter(p, _typeDeclaration))

  override def toString: String =
    modifiers.map(_.toString).mkString(" ") + " " + typeName.toString + " " + name.toString + "(" +
      parameters.map(_.toString).mkString(", ") + ")"
}

class GenericPlatformParameter(platformParameter: PlatformParameter, _typeDeclaration: GenericPlatformTypeDeclaration)
  extends ParameterDeclaration {

  override lazy val name: Name = platformParameter.name
  override lazy val typeName: TypeName = {
    val paramType = _typeDeclaration.replaceParams(platformParameter.getGenericTypeName)
    _typeDeclaration.getTypeVariable(paramType).getOrElse(paramType)
  }

  override def toString: String = {
    typeName.toString + " " + name.toString
  }
}

object GenericPlatformTypeDeclaration {

  // Cache of generic type requests
  private val declarationCache = mutable.Map[(TypeName, Option[TypeDeclaration.TID]), TypeRequest]()

  /* Get a generic type, in general don't call this direct, use TypeRequest which will delegate here if
   * needed. Implicit in this model is that all generics are currently platform types, hopefully that
   * won't be true forever.
   */
  def get(typeName: TypeName, from: Option[TypeDeclaration]): TypeRequest = {
    declarationCache.getOrElseUpdate((typeName, from.map(_.tid)), {
      create(typeName, from)
    })
  }

  private def create(typeName: TypeName, from: Option[TypeDeclaration]): TypeRequest = {
    // Make sure params are resolvable first
    val params = typeName.params.map(pt => (pt, TypeRequest(pt, from, None, excludeSObjects = false)))
    val pkg = from.flatMap(_.packageDeclaration)
    val failedParams = params.find(_._2.isLeft).filterNot(p => pkg.exists(_.isGhostedType(p._1)))
    if (failedParams.nonEmpty) {
      return Left(MissingType(failedParams.get._1))
    }

    // And then create off base type
    val genericDecl = PlatformTypeDeclaration.getDeclaration(typeName.asDotName)
    if (genericDecl.nonEmpty) {
      val absoluteParamTypes = params.map(p => p._2 match {
        case Left(error: TypeError) => error.typeName
        case Right(paramType: TypeDeclaration) => paramType.typeName
      })
      Right(new GenericPlatformTypeDeclaration(typeName.withParams(absoluteParamTypes), genericDecl.get))
    } else {
      Left(MissingType(typeName))
    }
  }
}
