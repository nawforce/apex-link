package com.nawforce.types

import com.nawforce.utils.{DotName, Name}

import scala.collection.mutable

class SchemaManager(pkg: PackageDeclaration) {
  val sobjectTypes: SchemaSObjectType = new SchemaSObjectType(pkg)
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

    sobjectFields.get(name).orElse({
      val td = pkg.getType(DotName(name))
      if (td.nonEmpty && td.get.superClassDeclaration.exists(superClass => superClass.typeName == TypeName.SObject)) {
        val fd = CustomFieldDeclaration(name, TypeName.DescribeSObjectResult, asStatic = true)
        sobjectFields.put(name, fd)
        Some(fd)
      } else {
        None
      }
    })
  }
}
