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

import com.nawforce.api.Org
import com.nawforce.documents.Location
import com.nawforce.utils.{DotName, Name}

import scala.collection.{mutable, _}

class SchemaManager(pkg: PackageDeclaration) {
  val sobjectTypes: SchemaSObjectType = new SchemaSObjectType(pkg)
  val relatedLists: RelatedLists = new RelatedLists(pkg)
}

class RelatedLists(pkg: PackageDeclaration) {
  private val relationshipFields = mutable.Map[TypeName, Seq[(CustomFieldDeclaration, Name, Location)]]() withDefaultValue Seq()

  def add(sObject: TypeName, relationshipName: Name, holdingFieldName: Name, holdingSObject: TypeName, location: Location): Unit = {
    val field = CustomFieldDeclaration(relationshipName, TypeName(Name.List, Seq(holdingSObject), None))
    synchronized {
      relationshipFields.put(sObject, (field, holdingFieldName, location) +: relationshipFields(sObject))
    }
  }

  def validate(): Unit = {
    val changedObjects = mutable.Set[TypeName]()
    val names = relationshipFields.keys.toSet
    names.foreach(sObject => {
      val td = pkg.getType(sObject.asDotName)
      if ((td.isEmpty || !td.exists(_.isSObject)) && !pkg.isGhostedType(sObject)) {
        relationshipFields(sObject).foreach(field => {
          Org.logMessage(field._3,
            s"Lookup object $sObject does not exist for field '${field._2}'")
        })
      } else if (td.nonEmpty && td.exists(_.isInstanceOf[PlatformTypeDeclaration])) {
        changedObjects.add(sObject)
      }
    })

    changedObjects.foreach(sObject => {
      pkg.wrapSObject(sObject)
    })
  }

  def findField(sobjectType: TypeName, name: Name, staticOnly: Boolean): Option[FieldDeclaration] = {
    relationshipFields(sobjectType).find(field => field._1.name == name).map(_._1)
  }
}

class SchemaSObjectType(pkg: PackageDeclaration) extends NamedTypeDeclaration(TypeName.SObjectType) {
  private val sobjectFields: mutable.Map[Name, FieldDeclaration] = mutable.Map()

  def add(sObject: SObjectDeclaration): Unit = {
    val fd = CustomFieldDeclaration(sObject.name, TypeName.DescribeSObjectResult, asStatic = true)
    sobjectFields.put(name, fd)
  }

  override def findField(name: Name, staticOnly: Boolean): Option[FieldDeclaration] = {
    if (!staticOnly)
      return None

    if (pkg.isGhostedName(name)) {
      return Some(createField(name))
    }

    sobjectFields.get(name).orElse({
      val td = pkg.getType(DotName(name))
      if (td.nonEmpty && td.get.superClassDeclaration.exists(superClass => superClass.typeName == TypeName.SObject)) {
        Some(createField(name))
      } else {
        None
      }
    })
  }

  private def createField(name:Name): FieldDeclaration = {
    val fd = CustomFieldDeclaration(name, TypeName.DescribeSObjectResult, asStatic = true)
    sobjectFields.put(name, fd)
    fd
  }
}
