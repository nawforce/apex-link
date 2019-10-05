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
import com.nawforce.finding.TypeRequest
import com.nawforce.names.{EncodedName, Name, TypeName}

import scala.collection.mutable

/* Support for Schema.* handling in Apex */
class SchemaManager(pkg: PackageDeclaration) extends PlatformTypes.PlatformTypeObserver {
  val sobjectTypes: SchemaSObjectType = SchemaSObjectType(pkg)
  val relatedLists: RelatedLists = new RelatedLists(pkg)

  PlatformTypes.addLoadingObserver(this)

  override def loaded(td: PlatformTypeDeclaration): Unit = {
    if (td.isSObject) {
      sobjectTypes.createSObjectTypeDeclarations(td.name)
    }
  }
}

/* Relationship field tracker, handles finding related lists */
class RelatedLists(pkg: PackageDeclaration) {
  private val relationshipFields = mutable.Map[TypeName, Seq[(CustomFieldDeclaration, Name, Location)]]() withDefaultValue Seq()

  /* Declare a new relationship field */
  def add(sObject: TypeName, relationshipName: Name, holdingFieldName: Name, holdingSObject: TypeName, location: Location): Unit = {
    val field = CustomFieldDeclaration(relationshipName, TypeName(Name.List, Seq(holdingSObject), None))
    synchronized {
      relationshipFields.put(sObject, (field, holdingFieldName, location) +: relationshipFields(sObject))
    }
  }

  /* Post object loading validation to make sure relationships exist */
  def validate(): Unit = {
    val changedObjects = mutable.Set[TypeDeclaration]()

    // Validate lookups will function
    val sobjects = relationshipFields.keys.toSet
    sobjects.foreach(sobject => {
      val td = TypeRequest(sobject, pkg).toOption
      if ((td.isEmpty || !td.exists(_.isSObject)) && !pkg.isGhostedType(sobject)) {
        relationshipFields(sobject).foreach(field => {
          Org.logMessage(field._3,
            s"Lookup object $sobject does not exist for field '${field._2}'")
        })
      } else if (td.exists(sobject => sobject.isInstanceOf[PlatformTypeDeclaration] && sobject.isSObject)) {
        changedObjects.add(td.get)
      }
      if (td.nonEmpty && sobject != td.get.typeName) {
        relationshipFields.put(td.get.typeName, relationshipFields(sobject))
        relationshipFields.remove(sobject)
      }
    })

    // Wrap any objects with lookups relationships so they are visible
    changedObjects.foreach(td => {
      pkg.upsertType(SObjectDeclaration(pkg, td.typeName, CustomObjectNature, Set(), td.fields, isComplete = true))
    })
  }

  /* Find for a relationship field on an SObject*/
  def findField(sobjectType: TypeName, name: Name, staticOnly: Boolean): Option[FieldDeclaration] = {
    relationshipFields(sobjectType).find(field => field._1.name == name).map(_._1)
  }
}

/* Schema.SObjectType implementation */
final case class SchemaSObjectType(pkg: PackageDeclaration) extends NamedTypeDeclaration(pkg, TypeName.SObjectType) {
  private val sobjectFields: mutable.Map[Name, FieldDeclaration] = mutable.Map()
  private val sobjectTypeDeclarationsCreated = mutable.Set[Name]()

  /* Allow adding of virtual SObjects such as for shares etc */
  def add(sObject: SObjectDeclaration): Unit = {
    createSObjectDescribeField(sObject.name, sObject.typeName)
  }

  /* Find a specific SObject */
  override def findField(name: Name, staticOnly: Boolean): Option[FieldDeclaration] = {
    if (!staticOnly)
      return None

    val typeName = EncodedName(name).asTypeName
    if (pkg.isGhostedType(typeName)) {
      return Some(createSObjectDescribeField(name, typeName))
    }

    sobjectFields.get(name).orElse({
      /* If not yet present check if we should create and cache */
      val td = TypeRequest(typeName, pkg).toOption
      if (td.nonEmpty && td.get.superClassDeclaration.exists(superClass => superClass.typeName == TypeName.SObject)) {
        Some(createSObjectDescribeField(name, td.get.typeName))
      } else {
        None
      }
    })
  }

  /* Create the describe entries for an SObject, note we are using generics to tunnel the type so that
   * we can support Field & FieldSet access via injecting virtual TypeDeclarations for these.
   */
  private def createSObjectDescribeField(sobjectName: Name, typeName: TypeName): FieldDeclaration = {
    createSObjectTypeDeclarations(sobjectName)

    val describeField = CustomFieldDeclaration(sobjectName, TypeName.describeSObjectResultOf(typeName), asStatic = true)
    sobjectFields.put(sobjectName, describeField)
    describeField
  }

  /* Inject virtual type declarations for an sobject to override the platform generic versions. This is done
   * dynamically so we don't need to create for every SObject */
  def createSObjectTypeDeclarations(sobjectName: Name): Unit = {
    if (!sobjectTypeDeclarationsCreated.contains(sobjectName)) {
      sobjectTypeDeclarationsCreated.add(sobjectName)
      pkg.upsertType(SchemaSObjectTypeFields(sobjectName, pkg))
      pkg.upsertType(SchemaSObjectTypeFieldSets(sobjectName, pkg))
    }
  }
}

final case class SchemaSObjectTypeFields(sobjectName: Name, pkg: PackageDeclaration)
  extends NamedTypeDeclaration(pkg, TypeName.sObjectTypeFields$(TypeName(sobjectName, Nil, Some(TypeName.Schema)))) {

  private lazy val sobjectFields: Map[Name, FieldDeclaration] = {
    TypeRequest(TypeName(sobjectName), pkg).toOption match {
      case Some(sobject: TypeDeclaration) =>
        sobject.fields.map(field => (field.name, CustomFieldDeclaration(field.name, TypeName.DescribeFieldResult))).toMap
      case _ => Map()
    }
  }

  private val ghostedSobjectFields: mutable.Map[Name, FieldDeclaration] = mutable.Map()

  override def findField(name: Name, staticOnly: Boolean): Option[FieldDeclaration] = {
    sobjectFields.get(name)
      .orElse(ghostedSobjectFields.get(name))
      .orElse(synchronized {
        val typeName = EncodedName(name).asTypeName
        if (pkg.isGhostedType(typeName)) {
          ghostedSobjectFields.put(name, CustomFieldDeclaration(name, TypeName.DescribeFieldResult))
        }
        ghostedSobjectFields.get(name)
      })
  }
}

final case class SchemaSObjectTypeFieldSets(sobjectName: Name, pkg: PackageDeclaration)
  extends NamedTypeDeclaration(pkg, TypeName.sObjectTypeFieldSets$(TypeName(sobjectName, Nil, Some(TypeName.Schema)))) {

  private lazy val sobjectFieldSets: Map[Name, FieldDeclaration] = {
    TypeRequest(TypeName(sobjectName), pkg).toOption match {
      case Some(sobject: SObjectDeclaration) =>
        sobject.fieldSets.map(name => (name, CustomFieldDeclaration(name, TypeName.FieldSet))).toMap
      case _ => Map()
    }
  }

  override def findField(name: Name, staticOnly: Boolean): Option[FieldDeclaration] = {
    sobjectFieldSets.get(name)
  }
}
