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

import java.nio.file.{Path, Paths}
import java.util.concurrent.ConcurrentHashMap

import com.nawforce.documents.ComponentDocument
import com.nawforce.utils.{DotName, Name}

import scala.collection.JavaConverters._
import scala.collection.mutable

final case class ComponentDeclaration() extends TypeDeclaration {
  private val components = new ConcurrentHashMap[Name, TypeDeclaration]()
  components.put(Name("Apex"), PlatformTypes.getType(DotName("Component.Apex")).get)
  components.put(Name("Chatter"), PlatformTypes.getType(DotName("Component.Chatter")).get)

  val name: Name = Name.component
  val path: Path = Paths.get("Component")
  val typeName: TypeName = TypeName(name)
  val outerTypeName: Option[TypeName] = None
  val nature: Nature = CLASS_NATURE
  val modifiers: Seq[Modifier] = Nil
  val isComplete: Boolean = true

  val superClass: Option[TypeName] = Some(TypeName.SObject)
  val interfaces: Seq[TypeName] = Nil
  def nestedTypes: Seq[TypeDeclaration] = components.values().asScala.toSeq

  val blocks: Seq[BlockDeclaration] =  Nil
  val fields: Seq[FieldDeclaration] = Nil
  val constructors: Seq[ConstructorDeclaration] =  Nil
  val methods: Seq[MethodDeclaration] = Nil

  def validate(): Unit = {}
  def dependencies(): Set[TypeDeclaration] = Set.empty
  def collectDependencies(dependencies: mutable.Set[TypeDeclaration]): Unit = {}

  def upsertComponent(namespace: Name, component: ComponentDocument): Unit = {
    getNamespaceContainer(namespace).foreach(_.upsertComponent(component))
    components.put(component.name, CustomComponent(component.name, component.path))
  }

  private def getNamespaceContainer(namespace: Name): Option[ComponentNamespace] = {
    val ns = if (namespace.isEmpty) Name("c") else namespace
    components.putIfAbsent(ns, ComponentNamespace(ns))
    components.get(ns) match {
      case componentNamespace: ComponentNamespace => Some(componentNamespace)
      case _ => None
    }
  }
}

final case class CustomComponent(name: Name, path: Path) extends TypeDeclaration {
  val typeName: TypeName = TypeName(name)
  val outerTypeName: Option[TypeName] = None
  val nature: Nature = CLASS_NATURE
  val modifiers: Seq[Modifier] = Nil
  val isComplete: Boolean = true

  val superClass: Option[TypeName] = Some(TypeName.SObject)
  val interfaces: Seq[TypeName] = Nil
  val nestedTypes: Seq[TypeDeclaration] = Nil

  val blocks: Seq[BlockDeclaration] = Nil
  val fields: Seq[FieldDeclaration]= Nil
  val constructors: Seq[ConstructorDeclaration] = Nil
  val methods: Seq[MethodDeclaration]= Nil

  def validate(): Unit = {}
  def dependencies(): Set[TypeDeclaration] = Set.empty
  def collectDependencies(dependencies: mutable.Set[TypeDeclaration]): Unit = {}
}

final case class ComponentNamespace(name: Name) extends TypeDeclaration {
  private val components = new ConcurrentHashMap[Name, TypeDeclaration]()

  // TODO: Fix unknown path handling
  val path: Path = Paths.get("ComponentNamespace")
  val typeName: TypeName = TypeName(name)
  val outerTypeName: Option[TypeName] = None
  val nature: Nature = CLASS_NATURE
  val modifiers: Seq[Modifier] = Nil
  val isComplete: Boolean = true

  val superClass: Option[TypeName] = Some(TypeName.SObject)
  val interfaces: Seq[TypeName] = Nil
  def nestedTypes: Seq[TypeDeclaration] = components.values.asScala.toSeq

  val blocks: Seq[BlockDeclaration] = Nil
  val fields: Seq[FieldDeclaration]= Nil
  val constructors: Seq[ConstructorDeclaration] = Nil
  val methods: Seq[MethodDeclaration]= Nil

  def validate(): Unit = {}
  def dependencies(): Set[TypeDeclaration] = Set.empty
  def collectDependencies(dependencies: mutable.Set[TypeDeclaration]): Unit = {}

  def upsertComponent(component: ComponentDocument): Unit = {
    components.put(component.name, CustomComponent(component.name, component.path))
  }
}

