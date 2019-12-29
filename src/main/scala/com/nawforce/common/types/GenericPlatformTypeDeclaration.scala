package com.nawforce.common.types

import com.nawforce.common.finding.TypeRequest.TypeRequest
import com.nawforce.common.finding.{MissingType, TypeError, TypeRequest}
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.runtime.types.{PlatformField, PlatformMethod, PlatformParameter, PlatformTypeDeclaration}
import scalaz._

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

  /* Get a generic type, in general don't call this direct, use TypeRequest which will delegate here if
   * needed. Implicit in this model is that all generics are currently platform types, hopefully that
   * won't be true forever.
   */
  def get(typeName: TypeName, from: Option[TypeDeclaration]): TypeRequest = {
    declarationCache((typeName, from))
  }

  private val declarationCache = Memo.immutableHashMapMemo
    [(TypeName, Option[TypeDeclaration]), TypeRequest] {
    case (typeName: TypeName, from: Option[TypeDeclaration]) => create(typeName, from)
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
