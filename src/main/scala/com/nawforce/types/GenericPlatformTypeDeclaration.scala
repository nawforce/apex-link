package com.nawforce.types

import com.nawforce.finding.TypeRequest.TypeRequest
import com.nawforce.finding.{MissingType, TypeError, TypeRequest}
import com.nawforce.names.{Name, TypeName}
import scalaz._

/* Wrapper for the few generic types we support, this specialises the methods of the type so that
 * List<T> presents as say a List<Foo>.
 */
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

  /* Get a generic type, in general don't call this direct, use TypeRequest which will delegate here if
   * needed. Implicit in this model is that all generics are currently platform types, hopefully that
   * won't be true forever.
   */
  def get(typeName: TypeName, from: Option[TypeDeclaration]): TypeRequest = {
    declarationCache((typeName, from))
  }

  private val declarationCache = Memo.immutableHashMapMemo
    [(TypeName, Option[TypeDeclaration]), TypeRequest] {
    case (typeName: TypeName, from: Option[TypeDeclaration]) => find(typeName, from)
  }

  private def find(typeName: TypeName, from: Option[TypeDeclaration]): TypeRequest = {
    // Make sure params are resolvable first
    val params = typeName.params.map(pt => (pt, TypeRequest(pt, from, None)))
    val failedParams = params.find(_._2.isLeft)
    if (failedParams.nonEmpty) {
      return Left(MissingType(failedParams.get._1))
    }

    // And then create off base type
    val genericDecl = PlatformTypeDeclaration.getDeclaration(typeName.asDotName)
    if (genericDecl.nonEmpty) {
      val absoluteParamTypes = params.map(_._2.right.get.typeName)
      Right(new GenericPlatformTypeDeclaration(typeName.withParams(absoluteParamTypes), genericDecl.get))
    } else {
      Left(MissingType(typeName))
    }
  }
}
