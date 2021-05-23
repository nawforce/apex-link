/*
 [The "BSD licence"]
 Copyright (c) 2020 Kevin Jones
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

import com.nawforce.pkgforce.diagnostics._
import com.nawforce.pkgforce.names.{Name, TypeName}
import com.nawforce.runtime.parsers.{ApexParser, CodeParser}

object IssueOps {

  def illegalIdentifier(location: PathLocation, name: Name, error: String): Issue =
    Issue(location.path,
          Diagnostic(ERROR_CATEGORY,
                     location.location,
                     s"'$name' is not legal identifier in Apex, identifiers $error"))

  def reservedIdentifier(location: PathLocation, name: Name): Issue =
    Issue(
      location.path,
      Diagnostic(ERROR_CATEGORY, location.location, s"'$name' is a reserved identifier in Apex"))

  def noTypeDeclaration(location: PathLocation, typeName: TypeName): Issue =
    Issue(
      location.path,
      Diagnostic(MISSING_CATEGORY, location.location, s"No type declaration found for '$typeName'"))

  def noVariableOrType(location: PathLocation, name: Name, typeName: TypeName): Issue =
    Issue(location.path,
          Diagnostic(MISSING_CATEGORY,
                     location.location,
                     s"No variable or type found for '$name' on '$typeName'"))

  def unknownFieldOnSObject(location: PathLocation, name: Name, typeName: TypeName): Issue =
    Issue(location.path,
          Diagnostic(MISSING_CATEGORY,
                     location.location,
                     s"Unknown field '$name' on SObject '$typeName'"))

  def unknownFieldOrType(location: PathLocation, name: Name, typeName: TypeName): Issue =
    Issue(location.path,
          Diagnostic(MISSING_CATEGORY,
                     location.location,
                     s"Unknown field or type '$name' on '$typeName'"))

  def unexpectedAnnotationOnClass(location: PathLocation,
                                  context: ApexParser.QualifiedNameContext): Issue =
    Issue(
      location.path,
      Diagnostic(ERROR_CATEGORY,
                 location.location,
                 s"Unexpected annotation '${CodeParser.getText(context)}' on class declaration"))

}
