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

package com.nawforce.apexlink.types.core

import com.nawforce.apexlink.api._
import com.nawforce.apexlink.cst._
import com.nawforce.apexlink.diagnostics.IssueOps
import com.nawforce.apexlink.finding.TypeResolver
import com.nawforce.apexlink.finding.TypeResolver.TypeResponse
import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.names.TypeNames.TypeNameUtils
import com.nawforce.apexlink.org.{Module, OrgImpl}
import com.nawforce.apexlink.types.other.Component
import com.nawforce.apexlink.types.platform.{PlatformTypeDeclaration, PlatformTypes}
import com.nawforce.apexlink.types.synthetic.CustomFieldDeclaration
import com.nawforce.pkgforce.diagnostics.Location
import com.nawforce.pkgforce.modifiers._
import com.nawforce.pkgforce.names.{Name, Names, TypeName}
import com.nawforce.pkgforce.path.PathLike

import scala.collection.mutable

sealed abstract class Nature(val value: String)
case object CLASS_NATURE extends Nature("class")
case object INTERFACE_NATURE extends Nature("interface")
case object ENUM_NATURE extends Nature("enum")
case object TRIGGER_NATURE extends Nature(value = "trigger")

object Nature {
  def apply(value: String): Nature = {
    value match {
      case CLASS_NATURE.value     => CLASS_NATURE
      case INTERFACE_NATURE.value => INTERFACE_NATURE
      case ENUM_NATURE.value      => ENUM_NATURE
      case TRIGGER_NATURE.value   => TRIGGER_NATURE
    }
  }
}

trait BlockDeclaration extends DependencyHolder {
  val isStatic: Boolean
}

object BlockDeclaration {
  val emptyBlockDeclarations: Array[BlockDeclaration] = Array()
}

trait FieldDeclaration extends DependencyHolder {
  val name: Name
  val modifiers: Array[Modifier]
  val typeName: TypeName
  val idTarget: Option[TypeName]
  val readAccess: Modifier
  val writeAccess: Modifier

  lazy val isStatic: Boolean = modifiers.contains(STATIC_MODIFIER)
  lazy val isPrivate: Boolean = modifiers.contains(PRIVATE_MODIFIER)

  // Create an SObjectField version of this field
  def getSObjectField(shareTypeName: Option[TypeName], module: Option[Module]): CustomFieldDeclaration = {
    def preloadSObject(typeName: TypeName): TypeResponse = {
      module.map(m => TypeResolver(typeName, m)).getOrElse(PlatformTypes.get(typeName, None))
    }

    // Some messy special cases
    if (typeName == TypeNames.IdType && idTarget.nonEmpty) {
      // Id field that carries a target SObjectType returns 'fields'
      preloadSObject(idTarget.get)
      CustomFieldDeclaration(name, TypeNames.sObjectFields$(idTarget.get), None, asStatic = true)
    } else if (CustomFieldDeclaration.isSObjectPrimitive(typeName)) {
      // Primitives (including other Id types)
      if (shareTypeName.nonEmpty && name == Names.RowCause)
        CustomFieldDeclaration(name, TypeNames.sObjectFieldRowCause$(shareTypeName.get), None, asStatic = true)
      else
        CustomFieldDeclaration(name, TypeNames.SObjectField, None, asStatic = true)
    } else if (name.value.endsWith("__r") && typeName.isRecordSet) {
      // Relationship field that needs unwrapping
      val targetSObject = typeName.params.head
      preloadSObject(targetSObject)
      CustomFieldDeclaration(name, TypeNames.sObjectFields$(targetSObject), None, asStatic = true)
    } else {
      // Otherwise must be a SObject, but if Platform it might need loading
      preloadSObject(typeName)
      CustomFieldDeclaration(name, TypeNames.sObjectFields$(typeName), None, asStatic = true)
    }
  }
}

object FieldDeclaration {
  val emptyFieldDeclarations: Array[FieldDeclaration] = Array()
}

trait ParameterDeclaration {
  val name: Name
  def typeName: TypeName

  def serialise: ParameterSummary = {
    ParameterSummary(name.toString, typeName)
  }

  def summary: ParameterSummary = serialise
}

trait ConstructorDeclaration extends DependencyHolder {
  val modifiers: Array[Modifier]
  val parameters: Array[ParameterDeclaration]
}

object ConstructorDeclaration {
  val emptyConstructorDeclarations: Array[ConstructorDeclaration] = Array()
}

trait MethodDeclaration extends DependencyHolder {
  val name: Name
  val modifiers: Array[Modifier]
  val typeName: TypeName
  val parameters: Array[ParameterDeclaration]

  def visibility: Modifier =
    modifiers.find(m => ApexModifiers.visibilityModifiers.contains(m)).getOrElse(PRIVATE_MODIFIER)

  def signature: String = s"$typeName $name($parameterTypes)"
  def parameterTypes: String = parameters.map(_.typeName).mkString(", ")

  def isStatic: Boolean = modifiers.contains(STATIC_MODIFIER)
  def isAbstract: Boolean = modifiers.contains(ABSTRACT_MODIFIER)
  def isVirtual: Boolean = modifiers.contains(VIRTUAL_MODIFIER)
  def isOverride: Boolean = modifiers.contains(OVERRIDE_MODIFIER)
  def isVirtualOrOverride: Boolean = isVirtual || isOverride
  def isVirtualOrAbstract: Boolean = isVirtual || isAbstract

  def hasSameSignature(other: MethodDeclaration): Boolean = {
    name == other.name &&
    typeName == other.typeName &&
    hasSameParameters(other)
  }

  def hasSameParameters(other: MethodDeclaration): Boolean = {
    hasParameters(other.parameters.map(_.typeName))
  }

  def hasParameters(params: Array[TypeName]): Boolean = {
    if (parameters.length == params.length) {
      parameters.zip(params).forall(z => z._1.typeName == z._2)
    } else {
      false
    }
  }

  def hasSameErasedParameters(module: Module, other: MethodDeclaration): Boolean = {
    hasErasedParameters(module, other.parameters.map(_.typeName))
  }

  private def hasErasedParameters(module: Module, params: Array[TypeName]): Boolean = {
    if (parameters.length == params.length) {
      // Future: This is very messy, we need to know the general rules
      parameters
        .zip(params)
        .forall(
          z =>
            (z._1.typeName == z._2) ||
              (z._1.typeName.isStringOrId && z._2.isStringOrId) ||
              (z._2.isSObjectList && z._1.typeName.isList && isSObject(module, z._1.typeName.params.head)) ||
              (z._1.typeName == TypeNames.SObject && isSObject(module, z._2)) ||
              (z._1.typeName.isList && z._1.typeName.params.head == TypeNames.String &&
                z._2.isList && z._2.params.head == TypeNames.IdType))
    } else {
      false
    }
  }

  private def isSObject(module: Module, typeName: TypeName): Boolean = {
    TypeResolver(typeName, module) match {
      case Right(td) => td.isSObject
      case Left(_)   => false
    }
  }

  def hasCallErasedParameters(module: Module, params: Array[TypeName]): Boolean = {
    if (parameters.length == params.length) {
      parameters
        .zip(params)
        .forall(
          z =>
            z._1.typeName == z._2 ||
              (z._1.typeName.equalsIgnoreParams(z._2) &&
                (TypeResolver(z._1.typeName, module) match {
                  case Right(x: PlatformTypeDeclaration) if x.nature == INTERFACE_NATURE =>
                    TypeResolver(z._2, module) match {
                      case Right(y: PlatformTypeDeclaration) if y.nature == INTERFACE_NATURE => true
                      case _                                                                 => false
                    }
                  case _ => false
                })))
    } else {
      false
    }
  }
}

object MethodDeclaration {
  val emptyMethodDeclarations: Array[MethodDeclaration] = Array()
}

trait AbstractTypeDeclaration {
  def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration]
  def findMethod(name: Name,
                 params: Array[TypeName],
                 staticContext: Option[Boolean],
                 verifyContext: VerifyContext): Option[MethodDeclaration]
  def findNestedType(name: Name): Option[AbstractTypeDeclaration]
}

trait TypeDeclaration extends AbstractTypeDeclaration with DependencyHolder {
  val paths: Array[PathLike]
  val moduleDeclaration: Option[Module]

  val name: Name
  val typeName: TypeName
  val outerTypeName: Option[TypeName]
  val nature: Nature
  val modifiers: Array[Modifier]

  lazy val namespace: Option[Name] = {
    val outermostType = outerTypeName.getOrElse(typeName).outer
    assert(outermostType.forall(_.outer.isEmpty))
    outermostType.map(_.name)
  }

  val superClass: Option[TypeName]
  val interfaces: Array[TypeName]

  def superClassDeclaration: Option[TypeDeclaration] = None
  def interfaceDeclarations: Array[TypeDeclaration] = TypeDeclaration.emptyTypeDeclarations
  def nestedTypes: Array[TypeDeclaration]

  val blocks: Array[BlockDeclaration]
  val fields: Array[FieldDeclaration]
  val constructors: Array[ConstructorDeclaration]
  def methods: Array[MethodDeclaration]

  def isComplete: Boolean
  lazy val isExternallyVisible: Boolean = modifiers.contains(GLOBAL_MODIFIER)
  lazy val isAbstract: Boolean = modifiers.contains(ABSTRACT_MODIFIER)
  lazy val isFieldConstructed: Boolean = isSObject || isApexPagesComponent
  lazy val isSObject: Boolean = superClass.contains(TypeNames.SObject)
  lazy val isApexPagesComponent: Boolean = superClass.contains(TypeNames.ApexPagesComponent)

  def outerTypeDeclaration: Option[TypeDeclaration] =
    outerTypeName.flatMap(typeName => TypeResolver(typeName, this).toOption)

  def outermostTypeDeclaration: TypeDeclaration =
    outerTypeName
      .flatMap(typeName => TypeResolver(typeName, this).toOption)
      .getOrElse(this)

  /** Called before validate() when a type is about to be re-validated to allow for cached state cleaning. */
  def preReValidate(): Unit = {}

  def validate(): Unit

  override def findNestedType(name: Name): Option[TypeDeclaration] = {
    nestedTypes.find(_.name == name)
  }

  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    val matches = fieldsByName.get(name)
    staticContext match {
      case Some(x) => matches.find(f => f.isStatic == x)
      case None    => matches
    }
  }

  protected lazy val fieldsByName: mutable.Map[Name, FieldDeclaration] = {
    val fieldsByName = mutable.Map(fields.map(f => (f.name, f)).toIndexedSeq: _*)
    outerTypeDeclaration.foreach(
      td =>
        td.fields
          .filter(_.isStatic)
          .foreach(f => fieldsByName.getOrElseUpdate(f.name, f)))
    superClassDeclaration.foreach(
      td =>
        td.fieldsByName
          .foreach(f => fieldsByName.getOrElseUpdate(f._1, f._2)))
    fieldsByName
  }

  private lazy val methodMap: MethodMap = MethodMap(this, None, MethodMap.empty(), methods, Array())

  override def findMethod(name: Name,
                          params: Array[TypeName],
                          staticContext: Option[Boolean],
                          verifyContext: VerifyContext): Option[MethodDeclaration] = {
    val found = methodMap.findMethod(name, params, staticContext, verifyContext)

    // Horrible skulduggery to support SObject.GetSObjectType()
    if (found.isEmpty && name == Names.GetSObjectType && params.isEmpty && staticContext.contains(true)) {
      findMethod(name, params, Some(false), verifyContext)
    } else {
      found
    }
  }

  def findLocalType(localName: TypeName): Option[TypeDeclaration] = {
    if (moduleDeclaration.isEmpty)
      TypeResolver.platformType(localName.withTail(typeName), this).toOption
    else
      moduleDeclaration.get.getLocalTypeFor(localName, this)
  }

  def validateFieldConstructorArguments(input: ExprContext,
                                        arguments: Array[Expression],
                                        context: ExpressionVerifyContext): Unit = {
    assert(isFieldConstructed)

    // FUTURE: Disable this bypass once VF parsing supported
    if (isInstanceOf[Component])
      return

    val validArgs = arguments.flatMap {
      case BinaryExpression(PrimaryExpression(IdPrimary(id)), rhs, "=") =>
        rhs.verify(input, context)
        // Future: check type against field being assigned

        var field: Option[FieldDeclaration] = None

        if (context.module.namespace.nonEmpty) {
          field = findField(context.defaultNamespace(id.name), staticContext = Some(false))
        }

        if (field.isEmpty)
          field = findField(id.name, staticContext = Some(false))

        if (field.isEmpty) {
          if (!context.module.isGhostedFieldName(id.name))
            context.log(IssueOps.unknownFieldOnSObject(id.location, id.name, typeName))
          None
        } else {
          context.addDependency(field.get)
          Some(id)
        }
      case argument =>
        OrgImpl.logError(argument.location,
                         s"SObject type '$typeName' construction needs '<field name> = <value>' arguments")
        None
    }

    if (validArgs.length == arguments.length) {
      val duplicates = validArgs.groupBy(_.name).collect { case (_, Array(_, y, _*)) => y }
      if (duplicates.nonEmpty) {
        OrgImpl.logError(duplicates.head.location,
                         s"Duplicate assignment to field '${duplicates.head.name}' on SObject type '$typeName'")
      }
    }
  }

  def extendsOrImplements(typeName: TypeName): Boolean = {
    val superclasses = superClassDeclaration
    val interfaces = interfaceDeclarations
    superClassDeclaration.exists(_.typeName == typeName) ||
    interfaces.exists(_.typeName == typeName) ||
    superclasses.exists(_.extendsOrImplements(typeName)) ||
    interfaces.exists(_.extendsOrImplements(typeName))
  }

  def superTypes(): List[TypeName] = {
    lazy val superclasses = superClassDeclaration
    lazy val interfaces = interfaceDeclarations
    superclasses.map(_.typeName).toList ++
      interfaces.map(_.typeName).toList ++
      superclasses.map(_.superTypes()).getOrElse(Nil) ++
      interfaces.flatMap(_.superTypes())
  }
}

object TypeDeclaration {
  val emptyTypeDeclarations: Array[TypeDeclaration] = Array()
}
