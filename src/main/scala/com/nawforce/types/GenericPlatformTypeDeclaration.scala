package com.nawforce.types

import com.nawforce.names.{Name, TypeName}
import scalaz.Scalaz._
import scalaz._

class GenericPlatformTypeDeclaration(_typeName: TypeName, genericDecl: PlatformTypeDeclaration)
  extends PlatformTypeDeclaration(genericDecl.cls, genericDecl.outer) {

  private val paramsMap: Map[Name, TypeName] = {
    genericDecl.typeName.params.zip(typeName.params)
      .map(p => (p._1.name, p._2)).toMap
  }

  override lazy val typeName: TypeName = _typeName
  override lazy val superClass: Option[TypeName] = getSuperClass.map(replaceParams)
  override lazy val interfaces: Seq[TypeName] = getInterfaces.map(replaceParams)

  override lazy val methods: Seq[MethodDeclaration] = {
    getMethods.map(m => new GenericPlatformMethod(m, this))
  }

  def replaceParams(typeName: TypeName): TypeName = {
    typeName.withParams(typeName.params.map(p => paramsMap(p.name)))
  }

  def getTypeVariable(typeName: TypeName): Option[TypeName] = {
    // Type vars should not really be modeled as TypeNames but they are rare and this is easier
    if (typeName.params.isEmpty && typeName.outer.isEmpty)
      paramsMap.get(typeName.name)
    else
      None
  }
}

class GenericPlatformMethod(platformMethod: PlatformMethod, _typeDeclaration: GenericPlatformTypeDeclaration)
  extends MethodDeclaration {

  override lazy val name: Name = platformMethod.name
  override lazy val modifiers: Seq[Modifier] = platformMethod.modifiers

  override lazy val typeName: TypeName = {
    val paramType = _typeDeclaration.replaceParams(
      PlatformTypeDeclaration.typeNameFromType(platformMethod.method.getGenericReturnType,  platformMethod.getDeclaringClass)
    )
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
    val paramType = _typeDeclaration.replaceParams(
      PlatformTypeDeclaration.typeNameFromType(platformParameter.parameter.getParameterizedType,
        platformParameter.declaringClass)
    )
    _typeDeclaration.getTypeVariable(paramType).getOrElse(paramType)
  }

  override def toString: String = {
    typeName.toString + " " + name.toString
  }
}

object GenericPlatformTypeDeclaration {
  def get(typeName: TypeName): ValidationNel[PlatformTypeGetError, PlatformTypeDeclaration] = {
    declarationCache(typeName)
  }

  private val declarationCache: TypeName => ValidationNel[PlatformTypeGetError, PlatformTypeDeclaration] =
    Memo.immutableHashMapMemo { typeName: TypeName => find(typeName) }

  private def find(typeName: TypeName): ValidationNel[PlatformTypeGetError, PlatformTypeDeclaration] = {

    // Make sure params are resolvable first
    val failedParam = typeName.params.map(pt => (pt, PlatformTypeDeclaration.get(pt))).find(_._2.isFailure)
    if (failedParam.nonEmpty) {
      return (MissingPlatformType(failedParam.get._1): PlatformTypeGetError).failureNel
    }

    // And the base type
    val genericDecl = PlatformTypeDeclaration.get(typeName.asDotName)
    if (genericDecl.nonEmpty)
      new GenericPlatformTypeDeclaration(typeName, genericDecl.get).successNel
    else
      (MissingPlatformType(typeName): PlatformTypeGetError).failureNel
  }
}
