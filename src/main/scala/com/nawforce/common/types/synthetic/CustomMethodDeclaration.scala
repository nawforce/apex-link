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
package com.nawforce.common.types.synthetic

import com.nawforce.common.api.{MethodSummary, Name, Position, RangeLocation}
import com.nawforce.common.cst.{Modifier, PUBLIC_MODIFIER, STATIC_MODIFIER}
import com.nawforce.common.documents.LocationImpl
import com.nawforce.common.names.TypeName
import com.nawforce.common.types.apex.ApexVisibleMethodLike
import com.nawforce.common.types.core.ParameterDeclaration

/** Custom methods are used to inject synthetic methods into types so they fulfil some contract. They extend from
  * ApexVisibleMethodLike so they can be referenced within Apex code and be included in type summary information
  * but otherwise have little in common with the usual ApexMethodLike handling.
  */
final case class CustomMethodDeclaration(nameRange: Option[LocationImpl], name: Name, typeName: TypeName,
                                         parameters: Seq[CustomParameterDeclaration], asStatic: Boolean = false)
  extends ApexVisibleMethodLike {

  override val modifiers: Seq[Modifier] = Seq(PUBLIC_MODIFIER) ++ (if (asStatic) Seq(STATIC_MODIFIER) else Seq())
  override lazy val isStatic: Boolean = asStatic

  def summary: MethodSummary = {
    serialise(nameRange.map(nr =>
      RangeLocation(
        Position(nr.startPosition._1, nr.startPosition._2),
        Position(nr.endPosition._1, nr.endPosition._2))
    ))
  }
}

final case class CustomParameterDeclaration(name: Name, typeName: TypeName) extends ParameterDeclaration
