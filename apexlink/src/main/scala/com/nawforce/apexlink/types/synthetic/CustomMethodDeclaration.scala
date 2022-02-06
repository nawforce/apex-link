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

import com.nawforce.apexlink.api._
import com.nawforce.apexlink.types.apex.ApexVisibleMethodLike
import com.nawforce.apexlink.types.core.ParameterDeclaration
import com.nawforce.pkgforce.modifiers._
import com.nawforce.pkgforce.names.{Name, TypeName}
import com.nawforce.pkgforce.path.Location

import scala.collection.immutable.ArraySeq

/** Custom methods are used to inject synthetic methods into types so they fulfil some contract. They extend from
  * ApexVisibleMethodLike so they can be referenced within Apex code and be included in type summary information
  * but otherwise have little in common with the usual ApexMethodLike handling.
  */
final case class CustomMethodDeclaration(
  nameLocation: Location,
  name: Name,
  typeName: TypeName,
  parameters: ArraySeq[ParameterDeclaration],
  asStatic: Boolean = false
) extends ApexVisibleMethodLike {

  override val modifiers: ArraySeq[Modifier] = CustomMethodDeclaration.getModifiers(asStatic)
  override val hasBlock: Boolean             = false
  override lazy val isStatic: Boolean        = asStatic

  def summary: MethodSummary = {
    MethodSummary(
      nameLocation,
      nameLocation,
      name.toString,
      modifiers,
      typeName,
      parameters.map(_.serialise),
      hasBlock = true,
      dependencySummary()
    )
  }
}

object CustomMethodDeclaration {
  val standardModifiers: ArraySeq[Modifier]           = ArraySeq(PUBLIC_MODIFIER)
  val staticModifiers: ArraySeq[Modifier]             = ArraySeq(PUBLIC_MODIFIER, STATIC_MODIFIER)
  val emptyParameters: ArraySeq[ParameterDeclaration] = ArraySeq()

  def getModifiers(isStatic: Boolean): ArraySeq[Modifier] = {
    if (isStatic) staticModifiers else standardModifiers
  }
}

final case class CustomParameterDeclaration(name: Name, typeName: TypeName)
    extends ParameterDeclaration
