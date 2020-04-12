/*
 [The "BSD licence"]
 Copyright (c) 2017 Kevin Jones
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

package com.nawforce.common.diagnostics

import com.nawforce.common.api.Diagnostic
import com.nawforce.common.documents.LocationImpl
import com.nawforce.common.names.{Name, TypeName}
import upickle.default.{macroRW, ReadWriter => RW}

sealed class IssueCategory(val value: String)

case object ERROR_CATEGORY extends IssueCategory("Error")
case object WARNING_CATEGORY extends IssueCategory("Warning")
case object MISSING_CATEGORY extends IssueCategory("Missing")
case object UNUSED_CATEGORY extends IssueCategory("Unused")
case class UNKNOWN_CATEGORY(_value: String) extends IssueCategory(_value)

object IssueCategory {
  def apply(value: String): IssueCategory = {
    value match {
      case ERROR_CATEGORY.value => ERROR_CATEGORY
      case WARNING_CATEGORY.value => WARNING_CATEGORY
      case MISSING_CATEGORY.value => MISSING_CATEGORY
      case UNUSED_CATEGORY.value => UNUSED_CATEGORY
      case _ => UNKNOWN_CATEGORY(value)
    }
  }

  implicit val rw: RW[IssueCategory] = macroRW
}

sealed case class Issue(category: IssueCategory, location: LocationImpl, message: String) {
  def toDiagnostic: Diagnostic = Diagnostic(category.value, location.toLocation, message)
}

object Issue {
  def fromDiagnostic(path: String, diagnostic: Diagnostic): Issue = {
    new Issue(IssueCategory(diagnostic.category), LocationImpl(path, diagnostic.location), diagnostic.message)
  }

  def illegalIdentifier(_location: LocationImpl, name: Name, error: String): Issue =
    Issue(ERROR_CATEGORY, _location, s"'$name' is not legal identifier in Apex, identifiers $error")

  def reservedIdentifier(_location: LocationImpl, name: Name): Issue =
    Issue(ERROR_CATEGORY, _location,s"'$name' is a reserved identifier in Apex")

  def noTypeDeclaration(_location: LocationImpl, typeName: TypeName): Issue =
    Issue(MISSING_CATEGORY, _location,s"No type declaration found for '$typeName'")

  def noVariableOrType(_location: LocationImpl, name: Name, typeName: TypeName): Issue =
    Issue(MISSING_CATEGORY, _location,s"No variable or type found for '$name' on '$typeName'")

  def unknownFieldOnSObject(_location: LocationImpl, name: Name, typeName: TypeName): Issue =
    Issue(MISSING_CATEGORY, _location,s"Unknown field '$name' on SObject '$typeName'")

  def unknownFieldOrType(_location: LocationImpl, name: Name, typeName: TypeName): Issue =
    Issue(MISSING_CATEGORY, _location,s"Unknown field or type '$name' on '$typeName'")

  implicit val rw: RW[Issue] = macroRW
}



