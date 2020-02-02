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
package com.nawforce.common.types.other

import java.util.concurrent.ConcurrentHashMap

import com.nawforce.common.cst.Modifier
import com.nawforce.common.documents.ComponentDocument
import com.nawforce.common.finding.TypeRequest
import com.nawforce.common.metadata.Dependent
import com.nawforce.common.names.{Name, TypeName}
import com.nawforce.common.path.PathLike
import com.nawforce.common.types._
import com.nawforce.common.types.platform.PlatformTypes

import scala.collection.JavaConverters._
import scala.collection.mutable

final case class ComponentDeclaration(pkg: PackageDeclaration) extends TypeDeclaration {

  private val components = mutable.Map[Name, TypeDeclaration]()
  components.put(Name("Apex"),
    TypeRequest(TypeName(Name("Apex"), Nil, Some(TypeName(Name("Component")))), excludeSObjects = false).toOption.get)
  components.put(Name("Chatter"),
    TypeRequest(TypeName(Name("Chatter"), Nil, Some(TypeName(Name("Component")))), excludeSObjects = false).toOption.get)

  override val packageDeclaration: Option[PackageDeclaration] = Some(pkg)
  override val name: Name = Name.Component
  override val typeName: TypeName = TypeName(name)
  override val outerTypeName: Option[TypeName] = None
  override val nature: Nature = CLASS_NATURE
  override val modifiers: Seq[Modifier] = Nil
  override val isComplete: Boolean = true
  override val isExternallyVisible: Boolean = true

  override val superClass: Option[TypeName] = None
  override val interfaces: Seq[TypeName] = Nil
  override def nestedTypes: Seq[TypeDeclaration] = {
    components.values.toSeq
  }

  override val blocks: Seq[BlockDeclaration] =  Nil
  override val fields: Seq[FieldDeclaration] = Nil
  override val constructors: Seq[ConstructorDeclaration] =  Nil
  override val methods: Seq[MethodDeclaration] = Nil

  override def validate(): Unit = {}
  override def dependencies(): Set[Dependent] = Set.empty
  override def collectDependencies(dependencies: mutable.Set[Dependent]): Unit = {}

  def upsertComponent(namespace: Option[Name], component: ComponentDocument): Unit = {
    getNamespaceContainer(Name.c).foreach(_.upsertComponent(component))
    if (namespace.nonEmpty)
      getNamespaceContainer(namespace.get).foreach(_.upsertComponent(component))

    val typeName = TypeName(component.name, Nil, Some(TypeName(Name.Component)))
    components.put(component.name, CustomComponent(pkg, component.name, typeName, component.path))
  }

  private def getNamespaceContainer(namespace: Name): Option[ComponentNamespace] = {
    if (components.get(namespace).isEmpty) {
      components.put(namespace, ComponentNamespace(pkg, namespace))
    }
    components.get(namespace).asInstanceOf[Option[ComponentNamespace]]
  }
}

final case class CustomComponent(pkg: PackageDeclaration, name: Name, typeName: TypeName, path: PathLike) extends TypeDeclaration {

  override val packageDeclaration: Option[PackageDeclaration] = Some(pkg)
  override lazy val namespace: Option[Name] = None
  override val outerTypeName: Option[TypeName] = None
  override val nature: Nature = CLASS_NATURE
  override val modifiers: Seq[Modifier] = Nil
  override val isComplete: Boolean = true
  override val isExternallyVisible: Boolean = true

  override val superClass: Option[TypeName] = Some(TypeName.ApexPagesComponent)
  override lazy val superClassDeclaration: Option[TypeDeclaration] = Some(PlatformTypes.componentType)
  override val interfaces: Seq[TypeName] = Nil
  override val nestedTypes: Seq[TypeDeclaration] = Nil

  override val blocks: Seq[BlockDeclaration] = Nil
  override val fields: Seq[FieldDeclaration] = PlatformTypes.componentType.fields
  override val constructors: Seq[ConstructorDeclaration] = Nil
  override val methods: Seq[MethodDeclaration] = Nil

  override def validate(): Unit = {}

  override def dependencies(): Set[Dependent] = Set.empty

  override def collectDependencies(dependencies: mutable.Set[Dependent]): Unit = {}
}

final case class ComponentNamespace(pkg: PackageDeclaration, name: Name) extends TypeDeclaration {
  private val components = new ConcurrentHashMap[Name, TypeDeclaration]()

  override val packageDeclaration: Option[PackageDeclaration] = Some(pkg)
  override val typeName: TypeName = TypeName(name)
  override val outerTypeName: Option[TypeName] = None
  override val nature: Nature = CLASS_NATURE
  override val modifiers: Seq[Modifier] = Nil
  override val isComplete: Boolean = true
  override val isExternallyVisible: Boolean = name != Name.c

  override val superClass: Option[TypeName] = None
  override val interfaces: Seq[TypeName] = Nil
  override def nestedTypes: Seq[TypeDeclaration] = components.values.asScala.toSeq

  override val blocks: Seq[BlockDeclaration] = Nil
  override val fields: Seq[FieldDeclaration]= Nil
  override val constructors: Seq[ConstructorDeclaration] = Nil
  override val methods: Seq[MethodDeclaration]= Nil

  override def validate(): Unit = {}
  override def dependencies(): Set[Dependent] = Set.empty
  override def collectDependencies(dependencies: mutable.Set[Dependent]): Unit = {}

  def upsertComponent(component: ComponentDocument): Unit = {
    val typeName = TypeName(component.name, Nil, Some(TypeName(name, Nil, Some(TypeName(Name.Component)))))
    components.put(component.name, CustomComponent(pkg, component.name, typeName, component.path))
  }
}

