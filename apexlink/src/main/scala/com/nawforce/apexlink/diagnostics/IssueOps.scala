/*
 Copyright (c) 2020 Kevin Jones, All rights reserved.
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

package com.nawforce.apexlink.diagnostics

import com.nawforce.apexlink.types.schema.SObjectNature
import com.nawforce.apexparser.ApexParser
import com.nawforce.pkgforce.diagnostics._
import com.nawforce.pkgforce.names.{Name, TypeName}
import com.nawforce.pkgforce.path.{PathLike, PathLocation}
import com.nawforce.runtime.parsers.CodeParser

object IssueOps {

  def illegalIdentifier(location: PathLocation, name: Name, error: String): Issue =
    Issue(
      location.path,
      Diagnostic(
        ERROR_CATEGORY,
        location.location,
        s"'$name' is not legal identifier in Apex, identifiers $error"
      )
    )

  def reservedIdentifier(location: PathLocation, name: Name): Issue =
    Issue(
      location.path,
      Diagnostic(ERROR_CATEGORY, location.location, s"'$name' is a reserved identifier in Apex")
    )

  def noTypeDeclaration(location: PathLocation, typeName: TypeName): Issue =
    Issue(
      location.path,
      Diagnostic(MISSING_CATEGORY, location.location, s"No type declaration found for '$typeName'")
    )

  def noVariableOrType(location: PathLocation, name: Name, typeName: TypeName): Issue =
    Issue(
      location.path,
      Diagnostic(
        MISSING_CATEGORY,
        location.location,
        s"No variable or type found for '$name' on '$typeName'"
      )
    )

  def unknownFieldOnSObject(location: PathLocation, name: Name, typeName: TypeName): Issue =
    Issue(
      location.path,
      Diagnostic(
        MISSING_CATEGORY,
        location.location,
        s"Unknown field '$name' on SObject '$typeName'"
      )
    )

  def unknownFieldOrType(location: PathLocation, name: Name, typeName: TypeName): Issue =
    Issue(
      location.path,
      Diagnostic(
        MISSING_CATEGORY,
        location.location,
        s"Unknown field or type '$name' on '$typeName'"
      )
    )

  def unexpectedAnnotationOnClass(
    location: PathLocation,
    context: ApexParser.QualifiedNameContext
  ): Issue =
    Issue(
      location.path,
      Diagnostic(
        ERROR_CATEGORY,
        location.location,
        s"Unexpected annotation '${CodeParser.getText(context)}' on class declaration"
      )
    )

  def extendingUnknownSObject(location: PathLocation, sobjectPath: PathLike): Issue = {
    Issue(
      location.path,
      Diagnostic(
        ERROR_CATEGORY,
        location.location,
        s"SObject is extending an unknown SObject, '$sobjectPath'"
      )
    )
  }

  def extendingOverNamespace(
    location: PathLocation,
    nature: SObjectNature,
    baseNS: Name,
    extendingNS: Name
  ): Issue = {
    Issue(
      location.path,
      Diagnostic(
        ERROR_CATEGORY,
        location.location,
        s"${nature} can not be extended in namespace '$extendingNS' when defined in namespace '$baseNS'"
      )
    )
  }

  def redefiningSObject(location: PathLocation, sobjectPath: PathLike): Issue = {
    Issue(
      location.path,
      Diagnostic(
        WARNING_CATEGORY,
        location.location,
        s"SObject appears to be re-defining an SObject that already exists, remove the 'label' field if it is extending an existing SObject,'$sobjectPath'"
      )
    )
  }

}
