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
package com.nawforce.common.types.core

import com.nawforce.common.api._
import com.nawforce.common.cst._
import com.nawforce.common.diagnostics.Issue
import com.nawforce.common.finding.TypeResolver
import com.nawforce.common.names.{Names, TypeNames, _}
import com.nawforce.common.org.{OrgImpl, PackageImpl}
import com.nawforce.common.path.PathLike
import com.nawforce.common.types.other.Component
import com.nawforce.common.types.platform.PlatformTypes
import com.nawforce.common.types.synthetic.CustomFieldDeclaration
import com.nawforce.runtime.types._

import scala.collection.mutable

sealed abstract class Nature(val value: String)
case object CLASS_NATURE extends Nature("class")
case object INTERFACE_NATURE extends Nature("interface")
case object ENUM_NATURE extends Nature("enum")
case object TRIGGER_NATURE extends Nature(value = "trigger")

object Nature {
  def apply(value: String): Nature = {
    value match {
      case CLASS_NATURE.value => CLASS_NATURE
      case INTERFACE_NATURE.value => INTERFACE_NATURE
      case ENUM_NATURE.value => ENUM_NATURE
      case TRIGGER_NATURE.value => TRIGGER_NATURE
    }
  }
}

trait BlockDeclaration extends DependencyHolder {
  val isStatic: Boolean

  def serialise(): BlockSummary = {
    serialise(shapeOnly = false)
  }

  def serialise(shapeOnly: Boolean): BlockSummary = {
    BlockSummary(isStatic, if (shapeOnly) Set.empty else dependencySummary())
  }
}

trait FieldDeclaration extends DependencyHolder {
  val name: Name
  val modifiers: Seq[Modifier]
  val typeName: TypeName
  val idTarget: Option[TypeName]
  val readAccess: Modifier
  val writeAccess: Modifier

  lazy val isStatic: Boolean = modifiers.contains(STATIC_MODIFIER)

  def serialise: FieldSummary = {
    serialise(shapeOnly = false, None)
  }

  protected def serialise(shapeOnly: Boolean, range: Option[RangeLocation]): FieldSummary = {
    FieldSummary(range, name.toString,
      modifiers.map(_.toString).sorted.toList,
      typeName,
      readAccess.toString, writeAccess.toString,
      if (shapeOnly) Set.empty else dependencySummary()
    )
  }

  // Create an SObjectField version of this field
  def getSObjectField: CustomFieldDeclaration = {
    // Some messy special cases
    if (typeName == TypeNames.Id && idTarget.nonEmpty) {
      // Id field that carries a target SObjectType returns 'fields'
      PlatformTypes.get(idTarget.get, None)
      CustomFieldDeclaration(name, TypeNames.sObjectFields$(idTarget.get), None, asStatic = true)
    } else if (CustomFieldDeclaration.isSObjectPrimitive(typeName)) {
      // Primitives (including other Id types)
      // TODO: Identify Share
      if (name == Names.RowCause)
        CustomFieldDeclaration(name, TypeNames.SObjectFieldRowCause$, None, asStatic = true)
      else
        CustomFieldDeclaration(name, TypeNames.SObjectField, None, asStatic = true)
    } else {
      // Otherwise must be a SObject, but if Platform it might need loading
      PlatformTypes.get(typeName, None)
      CustomFieldDeclaration(name, TypeNames.sObjectFields$(typeName), None, asStatic = true)
    }
  }
}

trait ParameterDeclaration {
  val name: Name
  val typeName: TypeName

  def serialise: ParameterSummary = {
    ParameterSummary(name.toString, typeName)
  }

  def summary: ParameterSummary = serialise
}

trait ConstructorDeclaration extends DependencyHolder {
  val modifiers: Seq[Modifier]
  val parameters: Seq[ParameterDeclaration]

  def serialise: ConstructorSummary = {
    serialise(shapeOnly = false, None)
  }

  protected def serialise(shapeOnly: Boolean, range: Option[RangeLocation]): ConstructorSummary = {
    ConstructorSummary(range,
      modifiers.map(_.toString).sorted.toList,
      parameters.map(_.serialise).sortBy(_.name).toList,
      if (shapeOnly) Set.empty else dependencySummary()
    )
  }
}

trait MethodDeclaration extends DependencyHolder {
  val name: Name
  val modifiers: Seq[Modifier]
  val typeName: TypeName
  val parameters: Seq[ParameterDeclaration]

  def signature: String = s"$typeName $name($parameterTypes)"
  def parameterTypes: String = parameters.map(_.typeName).mkString(", ")

  def isStatic: Boolean = modifiers.contains(STATIC_MODIFIER)
  def isAbstract: Boolean = modifiers.contains(ABSTRACT_MODIFIER)
  def isVirtual: Boolean = modifiers.contains(VIRTUAL_MODIFIER)
  def isVirtualOrOverride: Boolean = isVirtual || modifiers.contains(OVERRIDE_MODIFIER)
  def isGlobalOrPublic: Boolean = modifiers.exists(m => m == GLOBAL_MODIFIER || m == PUBLIC_MODIFIER)

  def hasSameSignature(other: MethodDeclaration): Boolean = {
    name == other.name &&
    typeName == other.typeName &&
    hasSameParameters(other)
  }

  def hasSameParameters(other: MethodDeclaration): Boolean = {
    hasParameters(other.parameters.map(_.typeName))
  }

  def hasParameters(params: Seq[TypeName]): Boolean = {
    if (parameters.size == params.size) {
      parameters.zip(params).forall(z => z._1.typeName == z._2)
    } else {
      false
    }
  }

  def hasSameErasedParameters(pkg: Option[PackageImpl], other: MethodDeclaration): Boolean = {
    hasErasedParameters(pkg, other.parameters.map(_.typeName))
  }

  def hasErasedParameters(pkg: Option[PackageImpl], params: Seq[TypeName]): Boolean = {
    if (parameters.size == params.size) {
      parameters.zip(params).forall(z =>
        (z._1.typeName == z._2) ||
          (z._1.typeName.isStringOrId && z._2.isStringOrId) ||
          (z._2.isSObjectList && z._1.typeName.isList &&
            (TypeResolver(z._1.typeName.params.head, None, pkg, excludeSObjects = false) match {
              case Right(td) => td.isSObject
              case Left(_) => false
            }))
      )
    } else {
      false
    }
  }

  def hasCallErasedParameters(pkg: PackageImpl, params: Seq[TypeName]): Boolean = {
    if (parameters.size == params.size) {
      parameters.zip(params).forall(z =>
        z._1.typeName == z._2 ||
          (z._1.typeName.equalsIgnoreParams(z._2) &&
            (TypeResolver(z._1.typeName, None, Some(pkg), excludeSObjects = false) match {
              case Right(x: PlatformTypeDeclaration) if x.nature == INTERFACE_NATURE =>
                TypeResolver(z._2, None, Some(pkg), excludeSObjects = false) match {
                  case Right(y: PlatformTypeDeclaration) if y.nature == INTERFACE_NATURE => true
                  case _ => false
                }
              case _ => false
            })
            ))
    } else {
      false
    }
  }

  def serialise: MethodSummary = {
    serialise(shapeOnly = false, None)
  }

  protected def serialise(shapeOnly: Boolean, range: Option[RangeLocation]): MethodSummary = {
    MethodSummary(range,
      name.toString, modifiers.map(_.toString).sorted.toList,
      typeName,
      parameters.map(_.serialise).toList,
      if (shapeOnly) Set.empty else dependencySummary()
    )
  }
}

trait AbstractTypeDeclaration {
  def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration]
  def findMethod(name: Name, params: Seq[TypeName], staticContext: Option[Boolean], verifyContext: VerifyContext): Seq[MethodDeclaration]
  def findNestedType(name: Name): Option[AbstractTypeDeclaration]
}

trait TypeDeclaration extends AbstractTypeDeclaration with DependencyHolder {
  val paths: Seq[PathLike]
  val packageDeclaration: Option[PackageImpl]

  val name: Name
  val typeName: TypeName
  val outerTypeName: Option[TypeName]
  val nature: Nature
  val modifiers: Seq[Modifier]

  lazy val namespace: Option[Name] = {
    val outermostType = outerTypeName.getOrElse(typeName).outer
    assert(outermostType.forall(_.outer.isEmpty))
    outermostType.map(_.name)
  }

  val superClass: Option[TypeName]
  val interfaces: Seq[TypeName]

  def superClassDeclaration: Option[TypeDeclaration] = None
  def interfaceDeclarations: Seq[TypeDeclaration] = Nil
  def nestedTypes: Seq[TypeDeclaration]

  val blocks: Seq[BlockDeclaration]
  val fields: Seq[FieldDeclaration]
  val constructors: Seq[ConstructorDeclaration]
  val methods: Seq[MethodDeclaration]

  def isComplete: Boolean
  lazy val isExternallyVisible: Boolean = modifiers.contains(GLOBAL_MODIFIER)
  lazy val isAbstract: Boolean = modifiers.contains(ABSTRACT_MODIFIER)
  lazy val isFieldConstructed: Boolean = isSObject || isApexPagesComponent
  lazy val isSObject: Boolean = superClass.contains(TypeNames.SObject)
  lazy val isApexPagesComponent: Boolean = superClass.contains(TypeNames.ApexPagesComponent)

  def validate(): Unit

  override def findNestedType(name: Name): Option[TypeDeclaration] = {
    nestedTypes.find(_.name == name)
  }

  override def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    val matches = fieldsByName.get(name)
    staticContext match {
      case Some(x) => matches.find(f => f.isStatic == x)
      case None => matches
    }
  }

  protected def findFieldSObject(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    val fieldOption = fieldsByName.get(name)

    // Handle the synthetic static SObjectField or abort
    if (fieldOption.isEmpty) {
      if (name == Names.SObjectField && staticContext.contains(true))
        return Some(CustomFieldDeclaration(Names.SObjectField, TypeNames.sObjectFields$(typeName), None))
      else
        return None
    }

    val field = fieldOption.get
    if (staticContext.contains(field.isStatic)) {
      fieldOption
    } else if (staticContext.contains(true)) {
      Some(field.getSObjectField)
    } else {
      None
    }
  }

  private lazy val fieldsByName: mutable.Map[Name, FieldDeclaration] = {
    val outerType = outerTypeName.flatMap(typeName => TypeResolver(typeName, this, excludeSObjects = false).toOption)
    val fieldsByName = mutable.Map(fields.map(f => (f.name, f)) : _*)
    outerType.foreach(td => td.fields.filter(_.isStatic).foreach(f => {
      if (!fieldsByName.contains(f.name))
        fieldsByName.put(f.name, f)
    }))
    fieldsByName
  }

  private lazy val methodMap: MethodMap = MethodMap(this, None, MethodMap.empty(), methods, Seq())

  override def findMethod(name: Name, params: Seq[TypeName], staticContext: Option[Boolean],
                 verifyContext: VerifyContext): Seq[MethodDeclaration] = {
    val found = methodMap.findMethod(name, params, staticContext, verifyContext)

    // Horrible skulduggery to support SObject.GetSObjectType()
    if (found.isEmpty && name == Names.GetSObjectType && params.isEmpty && staticContext.contains(true)) {
      findMethod(name, params, Some(false), verifyContext)
    } else {
      found
    }
  }

  def findLocalType(typeName: TypeName): Option[TypeDeclaration] = {
    packageDeclaration.flatMap(pkg => pkg.getLocalTypeFor(typeName, this))
  }

  def validateFieldConstructorArguments(input: ExprContext, arguments: Seq[Expression], context: ExpressionVerifyContext): Unit = {
    assert(isFieldConstructed)

    // FUTURE: Disable this bypass once VF parsing supported
    if (isInstanceOf[Component])
      return

    val validArgs = arguments.flatMap(argument => {
      argument match {
        case BinaryExpression(PrimaryExpression(IdPrimary(id)), rhs, "=") =>
          rhs.verify(input, context)
          // Future: check type against field being assigned

          var field : Option[FieldDeclaration] = None

          if (context.pkg.namespace.nonEmpty) {
            field = findField(context.defaultNamespace(id.name), staticContext = Some(false))
          }

          if (field.isEmpty)
            field = findField(id.name, staticContext = Some(false))

          if (field.isEmpty) {
            if (isComplete)
              context.log(Issue.unknownFieldOnSObject(id.location, id.name, typeName))
            None
          } else {
            context.addDependency(field.get)
            Some(id)
          }
        case _ =>
          OrgImpl.logError(argument.location, s"SObject type '$typeName' construction needs '<field name> = <value>' arguments")
          None
      }
    })

    if (validArgs.size == arguments.size) {
      val duplicates = validArgs.groupBy(_.name).collect { case (_, Seq(_, y, _*)) => y }
      if (duplicates.nonEmpty) {
        OrgImpl.logError(duplicates.head.location,
          s"Duplicate assignment to field '${duplicates.head.name}' on SObject type '$typeName'")
      }
    }
  }

  def extendsOrImplements(typeName: TypeName): Boolean = {
    lazy val superclasses = superClassDeclaration
    lazy val interfaces = interfaceDeclarations
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

  /** Create a type summary for serialisation purposes. Although this uses the same format as summaries for
    * consistency, the location fields are not set so that we can serialise platform types that come via Java
    * reflection for use with the scala.js version of the library.
    */
  def serialise: TypeSummary = {
    TypeSummary (
      0,
      None,
      name.toString,
      typeName,
      nature.value, modifiers.map(_.toString).sorted.toList,
      superClass,
      interfaces.toList,
      blocks.map(_.serialise).toList,
      fields.map(_.serialise).sortBy(_.name).toList,
      constructors.map(_.serialise).sortBy(_.parameters.size).toList,
      methods.map(_.serialise).sortBy(_.name).toList,
      nestedTypes.map(_.serialise).sortBy(_.name).toList,
      dependencySummary()
    )
  }
}
