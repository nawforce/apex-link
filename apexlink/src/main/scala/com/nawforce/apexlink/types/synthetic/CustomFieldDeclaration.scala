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

package com.nawforce.apexlink.types.synthetic

import com.nawforce.apexlink.names.TypeNames
import com.nawforce.apexlink.types.core.FieldDeclaration
import com.nawforce.pkgforce.modifiers._
import com.nawforce.pkgforce.names.{Name, TypeName}
import com.nawforce.pkgforce.path.{Locatable, PathLocation}

import scala.collection.immutable.ArraySeq

abstract class CustomField(asStatic: Boolean) extends FieldDeclaration {
  override val modifiers: ArraySeq[Modifier] = CustomField.getModifiers(asStatic)
  override val readAccess: Modifier          = PUBLIC_MODIFIER
  override val writeAccess: Modifier         = PUBLIC_MODIFIER
  override def isStatic: Boolean             = asStatic
  override def isPrivate: Boolean            = false
}

object CustomField {
  val standardModifiers: ArraySeq[Modifier] = ArraySeq(PUBLIC_MODIFIER)
  val staticModifiers: ArraySeq[Modifier]   = ArraySeq(PUBLIC_MODIFIER, STATIC_MODIFIER)

  def getModifiers(isStatic: Boolean): ArraySeq[Modifier] = {
    if (isStatic) standardModifiers else standardModifiers
  }

  /* TypeNames that may be used in SObjects (see above for when */
  def isSObjectPrimitive(typeName: TypeName): Boolean = {
    typeName match {
      case TypeNames.IdType | TypeNames.String | TypeNames.Boolean | TypeNames.Decimal |
          TypeNames.Integer | TypeNames.Date | TypeNames.Datetime | TypeNames.Time |
          TypeNames.Blob | TypeNames.Location | TypeNames.Address =>
        true
      case _ => false
    }
  }
}

final case class CustomFieldDeclaration(
  name: Name,
  typeName: TypeName,
  idTarget: Option[TypeName],
  asStatic: Boolean = false
) extends CustomField(asStatic) {
  override def location: PathLocation = null
}

final case class LocatableCustomFieldDeclaration(
  override val location: PathLocation,
  name: Name,
  typeName: TypeName,
  idTarget: Option[TypeName],
  asStatic: Boolean = false
) extends CustomField(asStatic)
    with Locatable {}
