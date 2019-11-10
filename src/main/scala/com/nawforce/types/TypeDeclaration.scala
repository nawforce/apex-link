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
package com.nawforce.types

import com.nawforce.api._
import com.nawforce.cst.{BinaryExpression, ExprContext, Expression, ExpressionVerifyContext, IdPrimary, MethodMap, PrimaryExpression, VerifyContext}
import com.nawforce.finding.TypeRequest
import com.nawforce.names.{Name, TypeName}

import scala.collection.mutable

sealed trait Nature {def value: String}
case object CLASS_NATURE extends Nature {override def value: String = "class"}
case object INTERFACE_NATURE extends Nature {override def value: String = "interface"}
case object ENUM_NATURE extends Nature {override def value: String = "enum"}

trait Dependant {
  private val dependencyHolders = mutable.Set[DependencyHolder]()

  def addDependencyHolder(dependencyHolder: DependencyHolder): Unit = {
    dependencyHolders.add(dependencyHolder)
  }

  def hasHolders: Boolean = dependencyHolders.nonEmpty

  def getDependencyHolders: Set[DependencyHolder] = dependencyHolders.toSet
}

trait DependencyHolder extends Dependant {
  def dependencies(): Set[Dependant] = Set.empty
  def propagateDependencies(): Unit = {
    dependencies().foreach(_.addDependencyHolder(this))
  }
}

trait BlockDeclaration extends DependencyHolder {
  val isStatic: Boolean
}

trait FieldDeclaration extends DependencyHolder {
  val name: Name
  val modifiers: Seq[Modifier]
  val typeName: TypeName
  val readAccess: Modifier
  val writeAccess: Modifier

  lazy val isStatic: Boolean = modifiers.contains(STATIC_MODIFIER)

  lazy val summary: FieldSummary = FieldSummary(name.toString, modifiers.map(_.toString).sorted.toList,
    typeName.toString, readAccess.toString, writeAccess.toString)
}

trait ParameterDeclaration {
  val name: Name
  val typeName: TypeName

  lazy val summary: ParameterSummary = ParameterSummary(name.toString, typeName.toString)
}

trait ConstructorDeclaration extends DependencyHolder {
  val modifiers: Seq[Modifier]
  val parameters: Seq[ParameterDeclaration]

  lazy val summary: ConstructorSummary = ConstructorSummary(
    modifiers.map(_.toString).sorted.toList,
    parameters.map(_.summary).sortBy(_.name).toList
  )
}

trait MethodDeclaration extends DependencyHolder {
  val name: Name
  val modifiers: Seq[Modifier]
  val typeName: TypeName
  val parameters: Seq[ParameterDeclaration]

  lazy val signature: String = s"$typeName $name($parameterTypes)"
  lazy val parameterTypes: String = parameters.map(_.typeName).mkString(", ")

  lazy val isStatic: Boolean = modifiers.contains(STATIC_MODIFIER)
  lazy val isAbstract: Boolean = modifiers.contains(ABSTRACT_MODIFIER)
  lazy val isVirtual: Boolean = modifiers.contains(VIRTUAL_MODIFIER)
  lazy val isGlobalOrPublic: Boolean = modifiers.exists(m => m == GLOBAL_MODIFIER || m == PUBLIC_MODIFIER)

  lazy val summary: MethodSummary = MethodSummary(
    name.toString, modifiers.map(_.toString).sorted.toList, typeName.toString,
    parameters.map(_.summary).sortBy(_.name).toList
  )
}

trait TypeDeclaration extends DependencyHolder {
  val packageDeclaration: Option[PackageDeclaration]
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
  lazy val superClassDeclaration: Option[TypeDeclaration] = None
  val interfaces: Seq[TypeName]
  lazy val interfaceDeclarations: Seq[TypeDeclaration] = Nil
  def nestedTypes: Seq[TypeDeclaration]

  val blocks: Seq[BlockDeclaration]
  val fields: Seq[FieldDeclaration]
  val constructors: Seq[ConstructorDeclaration]
  val methods: Seq[MethodDeclaration]

  val isComplete: Boolean
  val isExternallyVisible: Boolean
  val isAny: Boolean = false
  lazy val isFieldConstructed: Boolean = isSObject || isApexPagesComponent
  lazy val isSObject: Boolean = superClass.contains(TypeName.SObject)
  lazy val isApexPagesComponent: Boolean = superClass.contains(TypeName.ApexPagesComponent)

  /* Validate must be called before examining dependencies */
  def validate(): Unit
  def collectDependencies(dependencies: mutable.Set[Dependant]): Unit

  def findField(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    val matches = fieldsByName.get(name)
    staticContext match {
      case Some(x) => matches.find(f => f.isStatic == x)
      case None => matches
    }
  }

  protected def findFieldSObject(name: Name, staticContext: Option[Boolean]): Option[FieldDeclaration] = {
    this.synchronized {
      val fieldOption = fieldsByName.get(name)
      if (fieldOption.isEmpty)
        return None

      val field = fieldOption.get
      if (staticContext.contains(field.isStatic)) {
        fieldOption
      } else if (staticContext.contains(true)) {
        val staticRef = CustomFieldDeclaration(field.name, TypeName.SObjectField, asStatic = true)
        // TODO: Link static field back to instance field
        Some(staticRef)
      } else {
        None
      }
    }
  }

  private lazy val fieldsByName: mutable.Map[Name, FieldDeclaration] = {
    val outerType = outerTypeName.flatMap(typeName => TypeRequest(typeName, this, excludeSObjects = false).toOption)
    val fieldsByName = mutable.Map(fields.map(f => (f.name, f)) : _*)
    outerType.foreach(td => td.fields.filter(_.isStatic).foreach(f => {
      if (!fieldsByName.contains(f.name))
        fieldsByName.put(f.name, f)
    }))
    fieldsByName
  }

  private lazy val methodMap: MethodMap = MethodMap(nature, typeName, MethodMap.empty(), methods, Seq())

  def findMethod(name: Name, params: Seq[TypeName], staticContext: Option[Boolean],
                 verifyContext: VerifyContext): Seq[MethodDeclaration] = {
    val found = methodMap.findMethod(name, params, staticContext, verifyContext)

    // Horrible skulduggery to support SObject.GetSObjectType()
    if (found.isEmpty && name == Name.GetSObjectType && params.isEmpty && staticContext.contains(true)) {
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
    if (isInstanceOf[CustomComponent])
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
              Org.logMessage(id.location, s"Unknown field '${id.name}' on SObject type '$typeName'")
            None
          } else {
            field.get.addDependencyHolder(context.holder)
            Some(id)
          }
        case _ =>
          Org.logMessage(argument.location, s"SObject type '$typeName' construction needs '<field name> = <value>' arguments")
          None
      }
    })

    if (validArgs.size == arguments.size) {
      val duplicates = validArgs.groupBy(_.name).collect { case (_, List(_, y, _*)) => y }
      if (duplicates.nonEmpty) {
        Org.logMessage(duplicates.head.location,
          s"Duplicate assignment to field '${duplicates.head.name}' on SObject type '$typeName'")
      }
    }
  }

  def extendsOrImplements(typeName: TypeName): Boolean = {
    superClassDeclaration.exists(_.typeName == typeName) ||
      interfaceDeclarations.exists(_.typeName == typeName) ||
      superClassDeclaration.exists(_.extendsOrImplements(typeName)) ||
      interfaceDeclarations.exists(_.extendsOrImplements(typeName))
  }

  def superTypes(): List[TypeName] = {
    superClassDeclaration.map(_.typeName).toList ++
      interfaceDeclarations.map(_.typeName).toList ++
      superClassDeclaration.map(_.superTypes()).getOrElse(Nil) ++
      interfaceDeclarations.flatMap(_.superTypes())
  }

  lazy val summary: TypeSummary = TypeSummary(
    name.toString, typeName.toString, nature.value, modifiers.map(_.toString).sorted.toList,
    superClass.map(_.toString).getOrElse(""), interfaces.map(_.toString).sorted.toList,
    fields.map(_.summary).sortBy(_.name).toList,
    constructors.map(_.summary).sortBy(_.parameters.size).toList,
    methods.map(_.summary).sortBy(_.name).toList,
    nestedTypes.map(_.summary).sortBy(_.name).toList
  )
}
